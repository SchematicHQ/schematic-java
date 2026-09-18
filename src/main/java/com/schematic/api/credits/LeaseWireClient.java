package com.schematic.api.credits;

import java.time.Duration;
import java.time.Instant;

/**
 * The three lease calls the manager makes. Narrow on purpose: it keeps the manager independent of
 * the generated client's request and response models, and lets tests script the server.
 *
 * <p>Every method throws on a wire failure; the manager resolves that to "no lease" so callers
 * route it through their fail-open or fail-closed handling.
 */
public interface LeaseWireClient {

    LeaseGrant acquire(String companyId, String creditTypeId, double requestedAmount, Instant expiresAt);

    /**
     * Acquires under the caller's per-check timeout. A null timeout means the client's own, which
     * is what a background top-up nobody is waiting on takes.
     */
    default LeaseGrant acquire(
            String companyId, String creditTypeId, double requestedAmount, Instant expiresAt, Duration timeout) {
        return acquire(companyId, creditTypeId, requestedAmount, expiresAt);
    }

    LeaseGrant extend(String leaseId, double additionalAmount, Instant expiresAt);

    /** Extends under the caller's per-check timeout, or the client's own when null. */
    default LeaseGrant extend(String leaseId, double additionalAmount, Instant expiresAt, Duration timeout) {
        return extend(leaseId, additionalAmount, expiresAt);
    }

    void release(String leaseId);
}
