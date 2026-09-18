package com.schematic.api.credits;

/**
 * Holds the open credit holds carved out of leases.
 *
 * <p>{@link #add} does not debit: the debit already landed in {@link LeaseStore#tryReserve}, and
 * that ordering is what bounds a crash to a leaked hold rather than a double-spend.
 */
public interface ReservationStore {

    /** Registers a reservation. Idempotent on id. */
    void add(Reservation reservation);

    /** Looks up a reservation, or returns null once it has been claimed or swept. */
    Reservation get(String id);

    /**
     * Claims a reservation exactly once and refunds its unspent slice.
     *
     * <p>The claim is atomic and comes first: a racing settle or sweep finds nothing to claim and
     * refunds nothing. On a successful claim {@code creditsConsumed} is clamped to
     * {@code [0, creditsReserved]}, the remainder is refunded to the lease (pinned to the
     * reservation's lease), and the clamped figure is returned. Returns null when there was
     * nothing to claim. A crash between the claim and the refund loses the refund; it never
     * double-refunds.
     */
    Double consume(String id, double creditsConsumed);

    /**
     * Sums {@code creditsReserved} across the slot's open reservations. A hold counts exactly
     * while it is in the table, so {@code localRemainingCredits + reservedCredits} stays exact
     * between operations.
     */
    double reservedCredits(String companyId, String creditTypeId);

    /**
     * Removes every reservation past its TTL, refunding each full hold, and returns how many it
     * swept. Refunds are pinned to the originating lease, so a hold carved from a lease that has
     * since expired is dropped rather than credited to its successor.
     */
    int sweepExpired();

    /** The open reservations across every slot. */
    int count();
}
