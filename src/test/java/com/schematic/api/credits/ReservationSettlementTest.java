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
    void aFractionalSettleDebitsTheRawProductAndBillsTheWholeUnit() {
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
        // The lease is debited the raw 7.2 at 2 credits a unit, which is the figure every SDK
        // sharing this lease computes, and the 5.6 left of the hold goes back. The event billing
        // marginally more than the ledger debits is a property of the shared spec, not of this
        // port, and closing it would put this SDK out of step with the others on one Redis.
        assertEquals(985.6, leases.get("co_1", "ct_1").getLocalRemainingCredits(), 1e-9);
    }
}
