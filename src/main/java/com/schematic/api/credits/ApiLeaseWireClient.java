package com.schematic.api.credits;

import com.schematic.api.core.RequestOptions;
import com.schematic.api.resources.credits.CreditsClient;
import com.schematic.api.resources.credits.requests.AcquireCreditLeaseRequestBody;
import com.schematic.api.resources.credits.requests.ExtendCreditLeaseRequestBody;
import com.schematic.api.types.CreditLeaseResponseData;
import java.time.Duration;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/** Adapts the generated credits client to {@link LeaseWireClient}. */
public final class ApiLeaseWireClient implements LeaseWireClient {

    private final CreditsClient credits;

    public ApiLeaseWireClient(CreditsClient credits) {
        this.credits = credits;
    }

    /**
     * Acquire takes the client's default retry policy: the server hands back the slot's existing
     * active lease rather than opening a second one, so a retry after a lost response returns the
     * lease the first attempt created.
     */
    @Override
    public LeaseGrant acquire(String companyId, String creditTypeId, double requestedAmount, Instant expiresAt) {
        return acquire(companyId, creditTypeId, requestedAmount, expiresAt, null);
    }

    @Override
    public LeaseGrant acquire(
            String companyId, String creditTypeId, double requestedAmount, Instant expiresAt, Duration timeout) {
        AcquireCreditLeaseRequestBody body = AcquireCreditLeaseRequestBody.builder()
                .companyId(companyId)
                .creditTypeId(creditTypeId)
                .requestedAmount(requestedAmount)
                .expiresAt(toOffsetDateTime(expiresAt))
                .build();
        return grantFrom(
                timeout == null
                        ? credits.acquireCreditLease(body).getData()
                        : credits.acquireCreditLease(body, requestOptions(timeout))
                                .getData());
    }

    @Override
    public LeaseGrant extend(String leaseId, double additionalAmount, Instant expiresAt) {
        return extend(leaseId, additionalAmount, expiresAt, null);
    }

    @Override
    public LeaseGrant extend(String leaseId, double additionalAmount, Instant expiresAt, Duration timeout) {
        // An extend is an increment, so a retry without a key would grant the tranche twice. The
        // key is minted once per extend, outside the call, so the transport's retries resend the
        // same one and every attempt of this extend collapses to one grow, while a later extend
        // gets its own key.
        String idempotencyKey = UUID.randomUUID().toString();
        ExtendCreditLeaseRequestBody body = ExtendCreditLeaseRequestBody.builder()
                .additionalAmount(additionalAmount)
                .expiresAt(toOffsetDateTime(expiresAt))
                .idempotencyKey(idempotencyKey)
                .build();
        return grantFrom(
                timeout == null
                        ? credits.extendCreditLease(leaseId, body).getData()
                        : credits.extendCreditLease(leaseId, body, requestOptions(timeout))
                                .getData());
    }

    private static RequestOptions requestOptions(Duration timeout) {
        return RequestOptions.builder()
                .timeout(CreditAmounts.millisAsInt(timeout), TimeUnit.MILLISECONDS)
                .build();
    }

    @Override
    public void release(String leaseId) {
        credits.releaseCreditLease(leaseId);
    }

    private static LeaseGrant grantFrom(CreditLeaseResponseData data) {
        if (data == null) {
            throw new IllegalStateException("credit lease response carried no data");
        }
        return new LeaseGrant(
                data.getId(),
                data.getCompanyId(),
                data.getCreditTypeId(),
                data.getGrantedAmount(),
                data.getExpiresAt().toInstant());
    }

    private static OffsetDateTime toOffsetDateTime(Instant instant) {
        return OffsetDateTime.ofInstant(instant, ZoneOffset.UTC);
    }
}
