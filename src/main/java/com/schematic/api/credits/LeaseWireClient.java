package com.schematic.api.credits;

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

    LeaseGrant extend(String leaseId, double additionalAmount, Instant expiresAt);

    void release(String leaseId);
}
