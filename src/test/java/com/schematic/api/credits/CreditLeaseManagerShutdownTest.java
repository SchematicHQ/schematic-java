package com.schematic.api.credits;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
