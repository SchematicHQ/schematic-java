package com.schematic.api.credits;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import org.junit.jupiter.api.Test;

/** What a settle bills the server, and what it debits the lease. */
class ReservationSettlementTest {

    private static final Instant NOW = Instant.parse("2026-01-01T00:00:00Z");
    private static final Clock CLOCK = Clock.fixed(NOW, ZoneOffset.UTC);

    @Test
    void aFractionalSettleDebitsAndBillsTheSameWholeUnits() {
        InMemoryLeaseStore leases = new InMemoryLeaseStore(CLOCK);
        InMemoryReservationStore holds = new InMemoryReservationStore(leases, CLOCK);
        leases.replace(new LeaseGrant("lse_1", "co_1", "ct_1", 1000, NOW.plusSeconds(300)));
        // A hold sized from the caller's declared usage of ten, at two credits a unit.
        leases.tryReserve("co_1", "ct_1", 20);
        Reservation reservation = new Reservation(
                "rsv_1",
                "lse_1",
                CreditLeaseMode.CLIENT,
                "co_1",
                "ct_1",
                "inference_tokens",
                10,
                20,
                2,
                NOW.plusSeconds(60),
                null,
                null);
        holds.add(reservation);

        ReservationSettlement.SettleOutcome outcome = ReservationSettlement.settle(holds, reservation, 7.2);

        assertTrue(outcome.isSettledLocally());
        // The event's quantity is an integer, so 7.2 bills as 8.
        assertEquals(8L, outcome.getTrack().getQuantity().get());
        // And the lease is debited for those same eight units at 2 credits apiece, with the 4
        // credits left of the 20-credit hold going back. Debiting the raw 7.2 instead would move
        // the local ledger by less than the event bills, and the two would drift over a session.
        assertEquals(984.0, leases.get("co_1", "ct_1").getLocalRemainingCredits(), 1e-9);
    }

    @Test
    void aSettleBelowOneWholeUnitStillBillsOne() {
        InMemoryLeaseStore leases = new InMemoryLeaseStore(CLOCK);
        InMemoryReservationStore holds = new InMemoryReservationStore(leases, CLOCK);
        leases.replace(new LeaseGrant("lse_1", "co_1", "ct_1", 1000, NOW.plusSeconds(300)));
        leases.tryReserve("co_1", "ct_1", 2);
        Reservation reservation = new Reservation(
                "rsv_1",
                "lse_1",
                CreditLeaseMode.CLIENT,
                "co_1",
                "ct_1",
                "inference_tokens",
                1,
                2,
                2,
                NOW.plusSeconds(60),
                null,
                null);
        holds.add(reservation);

        ReservationSettlement.SettleOutcome outcome = ReservationSettlement.settle(holds, reservation, 0.5);

        // The API takes the quantity as a float only to deserialize it, and rejects a non-integer
        // while processing the event, so a raw 0.5 would be dropped server-side and never billed
        // while the lease had already been debited for it.
        assertEquals(1L, outcome.getTrack().getQuantity().get());
        assertEquals(998.0, leases.get("co_1", "ct_1").getLocalRemainingCredits(), 1e-9);
    }
}
