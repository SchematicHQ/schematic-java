package com.schematic.api.credits;

/**
 * A successful {@link LeaseStore#tryReserve}: the post-debit balance plus the id of the lease the
 * credits actually came out of.
 */
public final class ReserveResult {

    private final double balance;
    private final String leaseId;

    public ReserveResult(double balance, String leaseId) {
        this.balance = balance;
        this.leaseId = leaseId;
    }

    /** The balance left on the lease after the debit. */
    public double getBalance() {
        return balance;
    }

    /**
     * The lease the debit landed on. A caller pins its reservation to this, never to the lease
     * its acquire handed back: the slot's lease can be replaced in between.
     */
    public String getLeaseId() {
        return leaseId;
    }
}
