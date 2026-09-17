package com.schematic.api.credits;

import java.time.Instant;

/**
 * What the server says a lease is, after an acquire or an extend. It is also what installs a
 * lease into a store: the local balance is derived, never supplied.
 */
public final class LeaseGrant {

    private final String leaseId;
    private final String companyId;
    private final String creditTypeId;
    private final double grantedAmount;
    private final Instant expiresAt;

    public LeaseGrant(String leaseId, String companyId, String creditTypeId, double grantedAmount, Instant expiresAt) {
        this.leaseId = leaseId;
        this.companyId = companyId;
        this.creditTypeId = creditTypeId;
        this.grantedAmount = grantedAmount;
        this.expiresAt = expiresAt;
    }

    public String getLeaseId() {
        return leaseId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public String getCreditTypeId() {
        return creditTypeId;
    }

    /** The server-authoritative TOTAL, not the increment an extend asked for. */
    public double getGrantedAmount() {
        return grantedAmount;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }
}
