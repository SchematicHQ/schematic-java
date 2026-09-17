package com.schematic.api.credits;

import java.time.Instant;

/**
 * Holds at most one lease per (company, credit type) slot. Every mutation is atomic per slot:
 * {@link InMemoryLeaseStore} gets that from a per-slot lock, {@link RedisLeaseStore} from
 * single-key Lua.
 */
public interface LeaseStore extends ReservationRefunder {

    /** The (company, credit type) slot key, shared by every backend. */
    static String leaseKey(String companyId, String creditTypeId) {
        return companyId + ":" + creditTypeId;
    }

    /**
     * A snapshot of the slot, expired or not, or null when the slot is empty. Callers re-guard on
     * expiry.
     */
    LeaseState get(String companyId, String creditTypeId);

    /**
     * Installs a fresh lease at its full grant, if the slot is free to take, and reports whether
     * it wrote.
     *
     * <p>A live lease holds the slot even when it carries a different id (a sibling process won
     * the acquire race): its already-debited balance wins and this reports false. An expired row
     * carrying the SAME id is not rewritten either, since that would reset the balance and erase
     * debits whose reservations are still open; it is reconciled like an extend (granted to the
     * incoming total, expiry forward only, balance untouched) and also reports false. Only a
     * fresh write reports true, which is what tells the manager whether the lease it just
     * acquired is redundant.
     */
    boolean replace(LeaseGrant grant);

    /**
     * Atomically checks and debits, returning the post-debit balance and the lease the credits
     * came out of, or null when there is no lease, the lease has expired, the balance is short,
     * or {@code credits} is not a finite non-negative number. Returning the balance rather than a
     * boolean lets the caller derive the pre-debit figure as {@code balance + credits} without a
     * racy follow-up read.
     *
     * <p>The lease id is read in the same atomic step as the debit. The slot's lease can be
     * replaced between a caller's acquire and its reserve, so a hold pinned to the lease the
     * caller last saw would send its refunds to a lease that never held the credits, and bill
     * that lease for the usage.
     */
    ReserveResult tryReserve(String companyId, String creditTypeId, double credits);

    /**
     * Returns credits to the slot's balance, clamped at the granted amount. With a non-null
     * {@code pinLeaseId} the refund applies only while the slot still holds that lease: a hold
     * carved out of an expired lease must never inflate its successor, whose grant the server
     * already issued whole.
     */
    @Override
    void refund(String companyId, String creditTypeId, double credits, String pinLeaseId);

    /**
     * Reconciles the slot to the server-authoritative total. The delta is computed inside the
     * store against the currently stored total, never from a caller-held pre-wire-call read: two
     * processes extending concurrently from the same stale read would each apply a delta and mint
     * phantom credits. A total a sibling already applied is a no-op, so applies converge in any
     * order. Expiry only ever moves forward. A non-null {@code pinLeaseId} drops the whole extend
     * when the slot holds a different lease.
     */
    void extend(String companyId, String creditTypeId, double grantedTotal, Instant newExpiresAt, String pinLeaseId);

    /** Removes the slot entry, after a remote release. */
    void drop(String companyId, String creditTypeId);
}
