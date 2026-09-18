package com.schematic.api.credits;

import static com.schematic.api.credits.TestThreads.parked;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.Test;

/** What the manager does with background top-ups, and what it refuses to spend a thread on. */
class CreditLeaseManagerExtendTest {

    private static final Instant NOW = Instant.parse("2026-01-01T00:00:00Z");
    private static final Clock CLOCK = Clock.fixed(NOW, ZoneOffset.UTC);
    private static final Instant LIVE = NOW.plusSeconds(300);

    /** Counts extends, and can hold them open until released. */
    private static class CountingWire implements LeaseWireClient {
        final CountDownLatch started = new CountDownLatch(1);
        final CountDownLatch release = new CountDownLatch(1);
        final AtomicInteger extends_ = new AtomicInteger();
        final List<String> released = Collections.synchronizedList(new ArrayList<>());
        private final boolean block;

        CountingWire(boolean block) {
            this.block = block;
        }

        @Override
        public LeaseGrant acquire(String companyId, String creditTypeId, double amount, Instant expiresAt) {
            return new LeaseGrant("lse_acquired", companyId, creditTypeId, amount, expiresAt);
        }

        @Override
        public LeaseGrant extend(String leaseId, double additionalAmount, Instant expiresAt) {
            extends_.incrementAndGet();
            started.countDown();
            if (block) {
                try {
                    release.await();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            return new LeaseGrant(leaseId, "co_1", "ct_1", 3000, expiresAt);
        }

        @Override
        public void release(String leaseId) {
            released.add(leaseId);
        }
    }

    /**
     * Serves the caller's first read as it stands, and every later one as a lease that has since
     * been topped up. That is the shape of a stale read: a concurrent extend lands between the
     * row the caller decided on and the moment it owns the slot's flight.
     */
    private static class ToppedUpAfterFirstRead implements LeaseStore {
        private final InMemoryLeaseStore delegate;
        private final AtomicInteger reads = new AtomicInteger();

        ToppedUpAfterFirstRead(InMemoryLeaseStore delegate) {
            this.delegate = delegate;
        }

        @Override
        public LeaseState get(String companyId, String creditTypeId) {
            LeaseState entry = delegate.get(companyId, creditTypeId);
            if (entry == null || reads.incrementAndGet() == 1) {
                return entry;
            }
            return new LeaseState(entry.getLeaseId(), companyId, creditTypeId, 3000, 2900, entry.getExpiresAt());
        }

        @Override
        public boolean replace(LeaseGrant grant) {
            return delegate.replace(grant);
        }

        @Override
        public ReserveResult tryReserve(String companyId, String creditTypeId, double credits) {
            return delegate.tryReserve(companyId, creditTypeId, credits);
        }

        @Override
        public void refund(String companyId, String creditTypeId, double credits, String pinLeaseId) {
            delegate.refund(companyId, creditTypeId, credits, pinLeaseId);
        }

        @Override
        public void extend(
                String companyId, String creditTypeId, double grantedTotal, Instant newExpiresAt, String pinLeaseId) {
            delegate.extend(companyId, creditTypeId, grantedTotal, newExpiresAt, pinLeaseId);
        }

        @Override
        public void drop(String companyId, String creditTypeId) {
            delegate.drop(companyId, creditTypeId);
        }
    }

    /** Counts reads, so a task that would have read again is visible. */
    private static class CountingReads implements LeaseStore {
        private final LeaseStore delegate;
        final AtomicInteger reads = new AtomicInteger();

        CountingReads(LeaseStore delegate) {
            this.delegate = delegate;
        }

        @Override
        public LeaseState get(String companyId, String creditTypeId) {
            reads.incrementAndGet();
            return delegate.get(companyId, creditTypeId);
        }

        @Override
        public boolean replace(LeaseGrant grant) {
            return delegate.replace(grant);
        }

        @Override
        public ReserveResult tryReserve(String companyId, String creditTypeId, double credits) {
            return delegate.tryReserve(companyId, creditTypeId, credits);
        }

        @Override
        public void refund(String companyId, String creditTypeId, double credits, String pinLeaseId) {
            delegate.refund(companyId, creditTypeId, credits, pinLeaseId);
        }

        @Override
        public void extend(
                String companyId, String creditTypeId, double grantedTotal, Instant newExpiresAt, String pinLeaseId) {
            delegate.extend(companyId, creditTypeId, grantedTotal, newExpiresAt, pinLeaseId);
        }

        @Override
        public void drop(String companyId, String creditTypeId) {
            delegate.drop(companyId, creditTypeId);
        }
    }

    /** Counts sweeps and holds nothing. */
    private static final class CountingReservations implements ReservationStore {
        final AtomicInteger sweeps = new AtomicInteger();
        final CountDownLatch swept = new CountDownLatch(1);

        @Override
        public void add(Reservation reservation) {}

        @Override
        public Reservation get(String id) {
            return null;
        }

        @Override
        public Double consume(String id, double creditsConsumed) {
            return null;
        }

        @Override
        public double reservedCredits(String companyId, String creditTypeId) {
            return 0;
        }

        @Override
        public int sweepExpired() {
            swept.countDown();
            return sweeps.incrementAndGet();
        }

        @Override
        public int count() {
            return 0;
        }
    }

    private static CreditLeaseManager manager(LeaseWireClient wire, LeaseStore leases, ReservationStore holds) {
        return manager(wire, leases, holds, CreditLeaseConfig.builder().build());
    }

    private static CreditLeaseManager manager(
            LeaseWireClient wire, LeaseStore leases, ReservationStore holds, CreditLeaseConfig config) {
        return new CreditLeaseManager(wire, leases, holds, config, null, CLOCK);
    }

    /** How many threads are parked inside a lease flight right now, whichever pool they came from. */
    private static int threadsParkedOnAFlight() {
        int count = 0;
        for (StackTraceElement[] stack : Thread.getAllStackTraces().values()) {
            for (StackTraceElement frame : stack) {
                if (frame.getClassName().startsWith(CreditLeaseManager.class.getName() + "$Flight")
                        && "await".equals(frame.getMethodName())) {
                    count++;
                    break;
                }
            }
        }
        return count;
    }

    @Test
    void manyChecksDuringOneSlowExtendSendOneExtendAndParkNoThreads() throws Exception {
        InMemoryLeaseStore leases = new InMemoryLeaseStore(CLOCK);
        InMemoryReservationStore holds = new InMemoryReservationStore(leases, CLOCK);
        CountingWire wire = new CountingWire(true);
        CreditLeaseManager manager = manager(wire, leases, holds);
        leases.replace(new LeaseGrant("lse_1", "co_1", "ct_1", 1000, LIVE));
        // Draw the lease under its water mark, so every check that follows warrants a top-up.
        leases.tryReserve("co_1", "ct_1", 900);

        manager.extendInBackground("co_1", "ct_1");
        assertTrue(wire.started.await(5, TimeUnit.SECONDS));
        for (int i = 0; i < 200; i++) {
            manager.extendInBackground("co_1", "ct_1");
        }
        Thread.sleep(200);

        // Each of those checks found a top-up already on the wire. Joining it would have parked a
        // pool thread apiece for the length of one network call.
        assertEquals(1, wire.extends_.get());
        assertEquals(0, threadsParkedOnAFlight());
        wire.release.countDown();
        manager.close();
    }

    @Test
    void aHealthyLeaseSendsNoBackgroundWorkToThePool() {
        InMemoryLeaseStore backing = new InMemoryLeaseStore(CLOCK);
        InMemoryReservationStore holds = new InMemoryReservationStore(backing, CLOCK);
        CountingReads leases = new CountingReads(backing);
        CountingWire wire = new CountingWire(false);
        CreditLeaseManager manager = manager(wire, leases, holds);
        backing.replace(new LeaseGrant("lse_1", "co_1", "ct_1", 1000, LIVE));
        backing.tryReserve("co_1", "ct_1", 10);

        for (int i = 0; i < 200; i++) {
            manager.extendInBackground("co_1", "ct_1");
        }

        // The water-mark test runs on the caller's thread, so a comfortable lease queues nothing:
        // exactly one store read per call, and no task behind it to read again.
        assertEquals(0, wire.extends_.get());
        assertEquals(200, leases.reads.get());
        manager.close();
    }

    @Test
    void aStaleRowDoesNotSendASecondExtend() {
        InMemoryLeaseStore backing = new InMemoryLeaseStore(CLOCK);
        backing.replace(new LeaseGrant("lse_1", "co_1", "ct_1", 1000, LIVE));
        backing.tryReserve("co_1", "ct_1", 900);
        ToppedUpAfterFirstRead leases = new ToppedUpAfterFirstRead(backing);
        InMemoryReservationStore holds = new InMemoryReservationStore(backing, CLOCK);
        CountingWire wire = new CountingWire(false);
        CreditLeaseManager manager = manager(wire, leases, holds);

        assertNotNull(manager.maybeExtend("co_1", "ct_1", null));

        // The re-read once the flight is ours is what catches it. Without one the stale row bills
        // a second tranche onto a lease the previous extend already topped up.
        assertEquals(0, wire.extends_.get());
        manager.close();
    }

    @Test
    void aLeaseThatLandsAfterStopIsLeftAloneRatherThanReleased() throws Exception {
        InMemoryLeaseStore leases = new InMemoryLeaseStore(CLOCK);
        InMemoryReservationStore holds = new InMemoryReservationStore(leases, CLOCK);
        CountDownLatch onTheWire = new CountDownLatch(1);
        CountDownLatch swept = new CountDownLatch(1);
        List<String> released = Collections.synchronizedList(new ArrayList<>());
        LeaseWireClient wire = new LeaseWireClient() {
            @Override
            public LeaseGrant acquire(String companyId, String creditTypeId, double amount, Instant expiresAt) {
                onTheWire.countDown();
                try {
                    swept.await();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                return new LeaseGrant("lse_late", companyId, creditTypeId, amount, expiresAt);
            }

            @Override
            public LeaseGrant extend(String leaseId, double additionalAmount, Instant expiresAt) {
                return null;
            }

            @Override
            public void release(String leaseId) {
                released.add(leaseId);
            }
        };
        CreditLeaseManager manager = manager(wire, leases, holds);

        Thread caller = new Thread(() -> manager.acquireIfNeeded("co_1", "ct_1"));
        caller.start();
        assertTrue(onTheWire.await(5, TimeUnit.SECONDS));
        manager.stop();
        manager.releaseAllLocalLeases();
        swept.countDown();
        caller.join(5000);

        // A lease that lands this late is left to the drain or to server-side expiry. Releasing
        // it here would refund, on a shared backend, a lease sibling pods are still reserving
        // against.
        assertTrue(released.isEmpty(), "released " + released);
        manager.close();
    }

    @Test
    void aJoinerWhoseFlightSentNothingIssuesItsOwnExtend() throws Exception {
        InMemoryLeaseStore backing = new InMemoryLeaseStore(CLOCK);
        backing.replace(new LeaseGrant("lse_1", "co_1", "ct_1", 1000, LIVE));
        backing.tryReserve("co_1", "ct_1", 900);
        InMemoryReservationStore holds = new InMemoryReservationStore(backing, CLOCK);
        CountDownLatch ownerReRead = new CountDownLatch(1);
        CountDownLatch joinerRegistered = new CountDownLatch(1);
        AtomicInteger readOrder = new AtomicInteger();
        // The owner's first read sees the drawn-down row and decides to extend; its re-read, once
        // the flight is registered, sees a slot another extend already topped up, so it sends
        // nothing. The re-read is held open long enough for a joiner to queue behind that flight.
        LeaseStore stalling = new ToppedUpAfterFirstRead(backing) {
            @Override
            public LeaseState get(String companyId, String creditTypeId) {
                if (readOrder.incrementAndGet() == 2) {
                    ownerReRead.countDown();
                    try {
                        joinerRegistered.await(5, TimeUnit.SECONDS);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                return super.get(companyId, creditTypeId);
            }
        };
        CountingWire wire = new CountingWire(false);
        CreditLeaseManager manager = manager(wire, stalling, holds);

        Thread owner = new Thread(() -> manager.maybeExtend("co_1", "ct_1", null));
        owner.start();
        assertTrue(ownerReRead.await(5, TimeUnit.SECONDS));
        LeaseState[] joined = new LeaseState[1];
        Thread joiner = new Thread(() -> joined[0] = manager.maybeExtend("co_1", "ct_1", 4000.0));
        joiner.start();
        // The joiner has to reach the flight it is queuing behind before the owner finishes, and
        // its thread state is the signal: Flight.await is the one place maybeExtend blocks.
        assertTrue(parked(joiner), "the joiner never parked on the owner's flight");
        joinerRegistered.countDown();
        owner.join(5000);
        joiner.join(5000);

        // The owner asked for a tranche but never sent it, so its ask stands in for nobody. A
        // joiner that took it as covering its own shortfall would deny a check whose credits are
        // still sitting on the server.
        assertEquals(1, wire.extends_.get());
        assertNotNull(joined[0]);
        manager.close();
    }

    @Test
    void theCallersTimeoutReachesTheAcquireAndTheExtendButNotABackgroundTopUp() throws Exception {
        InMemoryLeaseStore leases = new InMemoryLeaseStore(CLOCK);
        InMemoryReservationStore holds = new InMemoryReservationStore(leases, CLOCK);
        List<Duration> acquireTimeouts = Collections.synchronizedList(new ArrayList<>());
        List<Duration> extendTimeouts = Collections.synchronizedList(new ArrayList<>());
        LeaseWireClient wire = new LeaseWireClient() {
            @Override
            public LeaseGrant acquire(String companyId, String creditTypeId, double amount, Instant expiresAt) {
                throw new UnsupportedOperationException("the timeout-carrying overload is the one under test");
            }

            @Override
            public LeaseGrant acquire(
                    String companyId, String creditTypeId, double amount, Instant expiresAt, Duration timeout) {
                acquireTimeouts.add(timeout);
                return new LeaseGrant("lse_1", companyId, creditTypeId, amount, expiresAt);
            }

            @Override
            public LeaseGrant extend(String leaseId, double additionalAmount, Instant expiresAt) {
                throw new UnsupportedOperationException("the timeout-carrying overload is the one under test");
            }

            @Override
            public LeaseGrant extend(String leaseId, double additionalAmount, Instant expiresAt, Duration timeout) {
                extendTimeouts.add(timeout);
                return new LeaseGrant(leaseId, "co_1", "ct_1", 5000, expiresAt);
            }

            @Override
            public void release(String leaseId) {}
        };
        CreditLeaseManager manager = manager(wire, leases, holds);
        Duration perCheck = Duration.ofMillis(250);

        manager.acquireIfNeeded("co_1", "ct_1", perCheck);
        leases.tryReserve("co_1", "ct_1", 9900);
        manager.maybeExtend("co_1", "ct_1", null, perCheck);
        manager.extendInBackground("co_1", "ct_1");
        manager.drain(Duration.ofSeconds(5));

        // The caller is waiting on these two, so its deadline is the one that counts.
        assertEquals(Collections.singletonList(perCheck), acquireTimeouts);
        // And the background top-up, which nobody is waiting on, keeps the client's own.
        assertEquals(Arrays.asList(perCheck, null), extendTimeouts);
        manager.close();
    }

    @Test
    void startSweepSchedulesOneSweeperHoweverOftenItIsCalled() throws Exception {
        InMemoryLeaseStore leases = new InMemoryLeaseStore(CLOCK);
        CountingReservations holds = new CountingReservations();
        // An interval no test run reaches, so the queue holds the scheduled sweeps themselves
        // rather than whatever happens to be pending at the moment of the assertion.
        CreditLeaseManager manager = manager(
                new CountingWire(false),
                leases,
                holds,
                CreditLeaseConfig.builder().sweepInterval(Duration.ofHours(1)).build());

        manager.startSweep();
        manager.startSweep();
        manager.startSweep();

        Field field = CreditLeaseManager.class.getDeclaredField("sweeper");
        field.setAccessible(true);
        ScheduledExecutorService sweeper = (ScheduledExecutorService) field.get(manager);
        // Nothing has run at an hourly interval, so every schedule is still queued and
        // shutdownNow hands them all back. Three of them would sweep three times as often as the
        // one interval asks for, and counting them says so outright rather than inferring it from
        // a tick count over a sleep.
        assertEquals(1, sweeper.shutdownNow().size(), "startSweep scheduled more than one sweeper");
        assertEquals(0, holds.sweeps.get());
        manager.close();
    }

    @Test
    void theScheduledSweeperActuallySweeps() throws Exception {
        InMemoryLeaseStore leases = new InMemoryLeaseStore(CLOCK);
        CountingReservations holds = new CountingReservations();
        CreditLeaseManager manager = manager(
                new CountingWire(false),
                leases,
                holds,
                CreditLeaseConfig.builder().sweepInterval(Duration.ofMillis(20)).build());

        manager.startSweep();

        assertTrue(holds.swept.await(5, TimeUnit.SECONDS), "the sweeper never ran");
        manager.stop();
        manager.close();
    }

    @Test
    void aJoinerWaitsNoLongerThanItsOwnDeadline() throws Exception {
        InMemoryLeaseStore leases = new InMemoryLeaseStore(CLOCK);
        InMemoryReservationStore holds = new InMemoryReservationStore(leases, CLOCK);
        CountingWire wire = new CountingWire(true);
        CreditLeaseManager manager = manager(wire, leases, holds);
        leases.replace(new LeaseGrant("lse_1", "co_1", "ct_1", 1000, LIVE));
        leases.tryReserve("co_1", "ct_1", 900);

        // Somebody else's top-up, held open on the wire for far longer than the joiner has.
        Thread owner = new Thread(() -> manager.maybeExtend("co_1", "ct_1", null), "owner");
        owner.start();
        assertTrue(wire.started.await(5, TimeUnit.SECONDS));

        LeaseState[] joined = new LeaseState[1];
        Thread impatient = new Thread(
                () -> joined[0] = manager.maybeExtend("co_1", "ct_1", null, Duration.ofMillis(50)), "impatient");
        impatient.start();
        impatient.join(5000);

        // The flight runs on whatever timeout started it, a background refresh included, so a
        // check with 50ms to spend must not inherit it.
        assertFalse(impatient.isAlive(), "the joiner sat behind the owner's wire call");
        assertNull(joined[0]);
        // It walked away from the wait rather than racing a second extend onto the same lease.
        assertEquals(1, wire.extends_.get());

        wire.release.countDown();
        owner.join(5000);
        manager.close();
    }

    @Test
    void checksDuringAnInFlightExtendQueueNoBackgroundWork() throws Exception {
        InMemoryLeaseStore backing = new InMemoryLeaseStore(CLOCK);
        InMemoryReservationStore holds = new InMemoryReservationStore(backing, CLOCK);
        CountingReads leases = new CountingReads(backing);
        CountingWire wire = new CountingWire(true);
        CreditLeaseManager manager = manager(wire, leases, holds);
        backing.replace(new LeaseGrant("lse_1", "co_1", "ct_1", 1000, LIVE));
        backing.tryReserve("co_1", "ct_1", 900);

        manager.extendInBackground("co_1", "ct_1");
        assertTrue(wire.started.await(5, TimeUnit.SECONDS));
        int readsWhileTheExtendRuns = leases.reads.get();
        for (int i = 0; i < 200; i++) {
            manager.extendInBackground("co_1", "ct_1");
        }

        // The slot stays under its water mark for as long as the top-up is on the wire, so the
        // water-mark test alone would have queued a task per check, each of them to read the
        // store and find the flight it must not join.
        assertEquals(readsWhileTheExtendRuns, leases.reads.get());
        assertEquals(1, wire.extends_.get());
        wire.release.countDown();
        manager.close();
    }

    @Test
    void aCallerWaitsOutFlightsTooSmallForItAndThenExtendsForItself() throws Exception {
        InMemoryLeaseStore backing = new InMemoryLeaseStore(CLOCK);
        InMemoryReservationStore holds = new InMemoryReservationStore(backing, CLOCK);
        List<Double> asks = Collections.synchronizedList(new ArrayList<>());
        CountDownLatch firstOnTheWire = new CountDownLatch(1);
        CountDownLatch releaseFirst = new CountDownLatch(1);
        CountDownLatch secondOnTheWire = new CountDownLatch(1);
        CountDownLatch releaseSecond = new CountDownLatch(1);
        AtomicInteger wireCalls = new AtomicInteger();
        AtomicLong grantedTotal = new AtomicLong(20000);
        LeaseWireClient wire = new LeaseWireClient() {
            @Override
            public LeaseGrant acquire(String companyId, String creditTypeId, double amount, Instant expiresAt) {
                return new LeaseGrant("lse_1", companyId, creditTypeId, amount, expiresAt);
            }

            @Override
            public LeaseGrant extend(String leaseId, double additionalAmount, Instant expiresAt) {
                asks.add(additionalAmount);
                int call = wireCalls.incrementAndGet();
                try {
                    if (call == 1) {
                        firstOnTheWire.countDown();
                        releaseFirst.await(10, TimeUnit.SECONDS);
                    } else if (call == 2) {
                        secondOnTheWire.countDown();
                        releaseSecond.await(10, TimeUnit.SECONDS);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                return new LeaseGrant(
                        leaseId, "co_1", "ct_1", grantedTotal.addAndGet((long) additionalAmount), expiresAt);
            }

            @Override
            public void release(String leaseId) {}
        };
        AtomicInteger hungryReads = new AtomicInteger();
        // The hungry caller is held at the re-read that follows its first join, until a second,
        // smaller flight is registered. That is the race under test: the flight it finds on the
        // way back was sized for somebody else's shortfall.
        LeaseStore sequenced = new CountingReads(backing) {
            @Override
            public LeaseState get(String companyId, String creditTypeId) {
                if ("hungry".equals(Thread.currentThread().getName()) && hungryReads.incrementAndGet() == 2) {
                    try {
                        secondOnTheWire.await(10, TimeUnit.SECONDS);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                return super.get(companyId, creditTypeId);
            }
        };
        CreditLeaseManager manager = manager(
                wire,
                sequenced,
                holds,
                CreditLeaseConfig.builder()
                        .defaultLeaseSize(2000)
                        .lowWaterMark(0.5)
                        .build());
        backing.replace(new LeaseGrant("lse_1", "co_1", "ct_1", 20000, LIVE));
        backing.tryReserve("co_1", "ct_1", 19000);

        // A modest shortfall claims the slot and sits on the wire: 4000 wanted, 1000 left, and a
        // 2000 tranche floor, so it asks for 3000.
        Thread small = new Thread(() -> manager.maybeExtend("co_1", "ct_1", 4000.0), "small");
        small.start();
        assertTrue(firstOnTheWire.await(5, TimeUnit.SECONDS));

        // The hungry caller needs 18000 more than it has, so that flight covers nothing of its
        // shortfall, and it joins.
        LeaseState[] hungryGot = new LeaseState[1];
        Thread hungry = new Thread(() -> hungryGot[0] = manager.maybeExtend("co_1", "ct_1", 19000.0), "hungry");
        hungry.start();
        assertTrue(parked(hungry), "the hungry caller never joined the first flight");
        releaseFirst.countDown();
        small.join(5000);

        // A second caller claims the slot while the hungry one is re-reading, and asks for as
        // little as the tranche floor allows.
        Thread second = new Thread(() -> manager.maybeExtend("co_1", "ct_1", 5000.0), "second");
        second.start();
        assertTrue(secondOnTheWire.await(5, TimeUnit.SECONDS));
        releaseSecond.countDown();
        second.join(5000);
        hungry.join(10000);

        // Two joins, then an extend of its own, sized against the balance those two flights left
        // rather than the one it started from: 6000 held, 19000 wanted, so it asks for 13000.
        // Inheriting either ask would have sent it back to its caller still short.
        assertEquals(Arrays.asList(3000.0, 2000.0, 13000.0), asks);
        assertNotNull(hungryGot[0]);
        assertTrue(
                hungryGot[0].getLocalRemainingCredits() >= 19000,
                "the hungry caller came back with " + hungryGot[0].getLocalRemainingCredits());
        manager.close();
    }
}
