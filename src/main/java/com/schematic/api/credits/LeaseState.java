package com.schematic.api.credits;

import java.time.Instant;

/** The local view of the one lease a (company, credit type) slot holds. */
public final class LeaseState {

    private final String leaseId;
    private final String companyId;
    private final String creditTypeId;
    private final double grantedAmount;
    private final double localRemainingCredits;
    private final Instant expiresAt;

    public LeaseState(
            String leaseId,
            String companyId,
            String creditTypeId,
            double grantedAmount,
            double localRemainingCredits,
            Instant expiresAt) {
        this.leaseId = leaseId;
        this.companyId = companyId;
        this.creditTypeId = creditTypeId;
        this.grantedAmount = grantedAmount;
        this.localRemainingCredits = localRemainingCredits;
        this.expiresAt = expiresAt;
    }

    /** Server-issued lease id. */
    public String getLeaseId() {
        return leaseId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public String getCreditTypeId() {
        return creditTypeId;
    }

    /** Server-authoritative total granted to this lease. It grows on extend. */
    public double getGrantedAmount() {
        return grantedAmount;
    }

    /**
     * Granted minus outstanding holds and consumption. It starts at the full grant when the lease
     * is installed.
     */
    public double getLocalRemainingCredits() {
        return localRemainingCredits;
    }

    /**
     * The instant past which the lease is dead: the server has refunded the remainder to the
     * company balance, so the local balance is stale and must never serve another reserve.
     */
    public Instant getExpiresAt() {
        return expiresAt;
    }

    /** Whether the lease is still live at {@code now}. */
    public boolean isLiveAt(Instant now) {
        return expiresAt.isAfter(now);
    }

    @Override
    public String toString() {
        return "LeaseState{leaseId=" + leaseId
                + ", companyId=" + companyId
                + ", creditTypeId=" + creditTypeId
                + ", grantedAmount=" + grantedAmount
                + ", localRemainingCredits=" + localRemainingCredits
                + ", expiresAt=" + expiresAt
                + "}";
    }
}
