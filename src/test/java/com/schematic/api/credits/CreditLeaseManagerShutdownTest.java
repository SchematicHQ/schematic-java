package com.schematic.api.credits;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.jupiter.api.Test;

/** What the manager still owes once it is stopping. */
class CreditLeaseManagerShutdownTest {

    private static final Instant NOW = Instant.parse("2026-01-01T00:00:00Z");
    private static final Clock CLOCK = Clock.fixed(NOW, ZoneOffset.UTC);

    /** Grants a fixed lease, and runs a hook while the acquire is on the wire. */
    private static final class RacingWire implements LeaseWireClient {
        private final Runnable duringAcquire;
        final List<String> released = Collections.synchronizedList(new ArrayList<>());
        final List<String> extended = Collections.synchronizedList(new ArrayList<>());

        RacingWire(Runnable duringAcquire) {
            this.duringAcquire = duringAcquire;
        }

        @Override
        public LeaseGrant acquire(String companyId, String creditTypeId, double requestedAmount, Instant expiresAt) {
            duringAcquire.run();
            return new LeaseGrant("lse_wire", companyId, creditTypeId, requestedAmount, expiresAt);
        }

        @Override
        public LeaseGrant extend(String leaseId, double additionalAmount, Instant expiresAt) {
            extended.add(leaseId);
            return new LeaseGrant(leaseId, "co_1", "ct_1", additionalAmount, expiresAt);
        }

        @Override
        public void release(String leaseId) {
            released.add(leaseId);
        }
    }

    /** Grants a fixed lease, and takes its time handing one back. */
    private static final class SlowWire implements LeaseWireClient {
        private final long releaseMillis;
        final List<String> released = Collections.synchronizedList(new ArrayList<>());

        SlowWire(long releaseMillis) {
            this.releaseMillis = releaseMillis;
        }

        @Override
        public LeaseGrant acquire(String companyId, String creditTypeId, double requestedAmount, Instant expiresAt) {
            return new LeaseGrant("lse_wire", companyId, creditTypeId, requestedAmount, expiresAt);
        }

        @Override
        public LeaseGrant extend(String leaseId, double additionalAmount, Instant expiresAt) {
            return new LeaseGrant(leaseId, "co_1", "ct_1", additionalAmount, expiresAt);
        }

        @Override
        public void release(String leaseId) {
            try {
                Thread.sleep(releaseMillis);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            released.add(leaseId);
        }
    }

    @Test
    void anErrorDuringAnAcquireDoesNotStrandTheJoinersWaitingOnIt() throws Exception {
        InMemoryLeaseStore leases = new InMemoryLeaseStore(CLOCK);
        InMemoryReservationStore holds = new InMemoryReservationStore(leases, CLOCK);
        CountDownLatch acquiring = new CountDownLatch(1);
        CountDownLatch release = new CountDownLatch(1);
        LeaseWireClient wire = new LeaseWireClient() {
            @Override
            public LeaseGrant acquire(String companyId, String creditTypeId, double amount, Instant expiresAt) {
                acquiring.countDown();
                try {
                    release.await();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                throw new StackOverflowError("the wire blew the stack");
            }

            @Override
            public LeaseGrant extend(String leaseId, double additionalAmount, Instant expiresAt) {
                return null;
            }

            @Override
            public void release(String leaseId) {}
        };
        CreditLeaseManager manager = new CreditLeaseManager(
                wire, leases, holds, CreditLeaseConfig.builder().build(), null, CLOCK);

        Thread first = new Thread(() -> {
            try {
                manager.acquireIfNeeded("co_1", "ct_1");
            } catch (Error expected) {
                // An Error is the caller's to deal with; what matters is who else it takes down.
            }
        });
        first.start();
        assertTrue(acquiring.await(5, TimeUnit.SECONDS));

        AtomicReference<LeaseState> joined = new AtomicReference<>();
        AtomicReference<Throwable> joinerThrew = new AtomicReference<>();
        CountDownLatch joinerDone = new CountDownLatch(1);
        Thread joiner = new Thread(() -> {
            try {
                joined.set(manager.acquireIfNeeded("co_1", "ct_1"));
            } catch (Throwable t) {
                // Counted either way, so a regression reads as a failed assertion here rather
                // than as a thread that dies quietly and a latch that never finishes.
                joinerThrew.set(t);
            } finally {
                joinerDone.countDown();
            }
        });
        joiner.start();
        // Only fail the wire once the joiner is parked on the first flight. Releasing any sooner
        // lets that flight finish and deregister, after which the joiner runs an acquire of its
        // own and the test no longer covers joining a failed flight. Its thread state is the
        // signal, since Flight.await is the one place acquireIfNeeded blocks.
        assertTrue(parked(joiner), "the joiner never parked on the flight");
        release.countDown();

        // Completing only on the return and the RuntimeException paths would park this joiner on
        // an unfinished future for the life of the process.
        assertTrue(joinerDone.await(10, TimeUnit.SECONDS), "the joiner never came back");
        assertNull(joinerThrew.get());
        // Null because it joined the failed flight, rather than acquiring on its own.
        assertNull(joined.get());
        first.join(5000);
        joiner.join(5000);
        manager.close();
    }

    /** Waits, boundedly, for a thread to block. */
    private static boolean parked(Thread thread) throws InterruptedException {
        long deadline = System.nanoTime() + TimeUnit.SECONDS.toNanos(10);
        while (System.nanoTime() - deadline < 0) {
            Thread.State state = thread.getState();
            if (state == Thread.State.WAITING || state == Thread.State.TIMED_WAITING) {
                return true;
            }
            if (state == Thread.State.TERMINATED) {
                return false;
            }
            Thread.sleep(1);
        }
        return false;
    }

    @Test
    void shuttingDownStaysInsideItsBudgetWithManySlotsAndASlowWire() {
        InMemoryLeaseStore leases = new InMemoryLeaseStore(CLOCK);
        InMemoryReservationStore holds = new InMemoryReservationStore(leases, CLOCK);
        SlowWire wire = new SlowWire(100);
        CreditLeaseManager manager = new CreditLeaseManager(
                wire, leases, holds, CreditLeaseConfig.builder().build(), null, CLOCK);
        for (int i = 0; i < 50; i++) {
            leases.replace(new LeaseGrant("lse_" + i, "co_" + i, "ct_1", 1000, NOW.plusSeconds(300)));
        }

        long startedAt = System.nanoTime();
        manager.releaseAllLocalLeases(Duration.ofMillis(300));
        manager.close(Duration.ofMillis(100));
        long tookMillis = (System.nanoTime() - startedAt) / 1_000_000;

        // Releasing all fifty in turn is five seconds of shutdown. A caller that asked for a
        // bounded close gets one, and the leases left behind expire server-side.
        assertTrue(tookMillis < 2000, "shutdown took " + tookMillis + "ms");
        assertFalse(wire.released.isEmpty());
        assertTrue(wire.released.size() < 50, "released " + wire.released.size() + " of 50");
    }

    @Test
    void aRedundantLeaseIsStillReleasedWhenTheAcquireLandsMidShutdown() {
        InMemoryLeaseStore leases = new InMemoryLeaseStore(CLOCK);
        InMemoryReservationStore holds = new InMemoryReservationStore(leases, CLOCK);
        CreditLeaseManager[] manager = new CreditLeaseManager[1];
        // A sibling installs its own lease and the client starts closing, both while this acquire
        // is on the wire: the lease it is granted is redundant the moment it lands.
        RacingWire wire = new RacingWire(() -> {
            leases.replace(new LeaseGrant("lse_sibling", "co_1", "ct_1", 1000, NOW.plusSeconds(300)));
            manager[0].stop();
        });
        manager[0] = new CreditLeaseManager(
                wire, leases, holds, CreditLeaseConfig.builder().build(), null, CLOCK);

        LeaseState result = manager[0].acquireIfNeeded("co_1", "ct_1");
        manager[0].drain(Duration.ofSeconds(5));

        assertEquals("lse_sibling", result.getLeaseId());
        // Refusing this release would hold the granted credits until the server expires them.
        assertEquals(Collections.singletonList("lse_wire"), wire.released);
        manager[0].close();
    }

    @Test
    void ordinaryBackgroundWorkIsStillRefusedOnceStopped() {
        InMemoryLeaseStore leases = new InMemoryLeaseStore(CLOCK);
        InMemoryReservationStore holds = new InMemoryReservationStore(leases, CLOCK);
        RacingWire wire = new RacingWire(() -> {});
        CreditLeaseManager manager = new CreditLeaseManager(
                wire, leases, holds, CreditLeaseConfig.builder().build(), null, CLOCK);
        leases.replace(new LeaseGrant("lse_1", "co_1", "ct_1", 1000, NOW.plusSeconds(300)));
        // Draw the lease under its low water mark, so a top-up is warranted.
        leases.tryReserve("co_1", "ct_1", 900);

        manager.extendInBackground("co_1", "ct_1");
        manager.drain(Duration.ofSeconds(5));
        assertEquals(Collections.singletonList("lse_1"), wire.extended);

        manager.stop();
        manager.extendInBackground("co_1", "ct_1");
        manager.drain(Duration.ofSeconds(5));

        // A top-up is work the shutdown has no reason to finish: it would install credits the
        // release is about to hand back.
        assertEquals(1, wire.extended.size());
        manager.close();
    }
}
