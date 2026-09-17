package com.schematic.api.credits;

/**
 * The slice of {@link LeaseStore} a reservation store needs. Narrowing it here is what lets the
 * unspent-slice refund stay an ordinary single-key step rather than a cross-key script, and lets
 * a test interpose on the claim-then-refund window.
 */
public interface ReservationRefunder {

    void refund(String companyId, String creditTypeId, double credits, String pinLeaseId);
}
