package com.schematic.api.credits.conformance;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.schematic.api.credits.CreditLeaseMode;
import com.schematic.api.credits.LeaseGrant;
import com.schematic.api.credits.Reservation;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ReservationStoreParityTest {

    /**
     * Both lease stores read an empty pin as no pin at all and would credit whichever lease holds
     * the slot. Fleets mix SDKs on one Redis, so every SDK's reservation stores make the call
     * themselves, and all of them decline.
     */
    @ParameterizedTest
    @ValueSource(strings = {Backend.IN_MEMORY, Backend.REDIS})
    void neitherBackendRefundsAHoldThatCannotNameItsLease(String name) {
        Backend backend = Backend.create(name);
        backend.leases.replace(new LeaseGrant("lse_successor", "co_1", "ct_1", 1000, backend.clock.at(5 * 60_000)));
        backend.leases.tryReserve("co_1", "ct_1", 100);

        backend.reservations.add(new Reservation(
                "res_1",
                "",
                CreditLeaseMode.CLIENT,
                "co_1",
                "ct_1",
                "inference_tokens",
                1,
                100,
                100,
                backend.clock.at(60_000),
                null,
                null));
        backend.reservations.consume("res_1", 0);

        // 900, not 1000: the successor's balance is untouched.
        assertEquals(900.0, backend.leases.get("co_1", "ct_1").getLocalRemainingCredits(), 0);
    }
}
