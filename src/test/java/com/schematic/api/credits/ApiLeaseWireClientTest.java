package com.schematic.api.credits;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.schematic.api.resources.credits.CreditsClient;
import com.schematic.api.resources.credits.requests.AcquireCreditLeaseRequestBody;
import com.schematic.api.resources.credits.requests.ExtendCreditLeaseRequestBody;
import com.schematic.api.resources.credits.types.AcquireCreditLeaseResponse;
import com.schematic.api.resources.credits.types.ExtendCreditLeaseResponse;
import com.schematic.api.types.CreditLeaseResponseData;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

class ApiLeaseWireClientTest {

    private static final Instant EXPIRES_AT = Instant.parse("2026-01-01T00:05:00Z");

    private CreditsClient credits;
    private ApiLeaseWireClient wire;

    @BeforeEach
    void setUp() {
        credits = mock(CreditsClient.class);
        wire = new ApiLeaseWireClient(credits);
    }

    private static CreditLeaseResponseData lease(double grantedAmount) {
        return CreditLeaseResponseData.builder()
                .companyId("co_1")
                .createdAt(OffsetDateTime.ofInstant(EXPIRES_AT.minusSeconds(300), ZoneOffset.UTC))
                .creditTypeId("ct_1")
                .expiresAt(OffsetDateTime.ofInstant(EXPIRES_AT, ZoneOffset.UTC))
                .grantedAmount(grantedAmount)
                .id("lse_1")
                .trackedAmount(0)
                .updatedAt(OffsetDateTime.ofInstant(EXPIRES_AT.minusSeconds(300), ZoneOffset.UTC))
                .build();
    }

    @Test
    void acquireMapsTheGrantOntoALeaseState() {
        when(credits.acquireCreditLease(any(AcquireCreditLeaseRequestBody.class)))
                .thenReturn(
                        AcquireCreditLeaseResponse.builder().data(lease(1000)).build());

        LeaseGrant grant = wire.acquire("co_1", "ct_1", 1000, EXPIRES_AT);

        assertEquals("lse_1", grant.getLeaseId());
        assertEquals("co_1", grant.getCompanyId());
        assertEquals(1000.0, grant.getGrantedAmount());
        assertEquals(EXPIRES_AT, grant.getExpiresAt());
    }

    @Test
    void everyExtendCarriesItsOwnIdempotencyKey() {
        when(credits.extendCreditLease(eq("lse_1"), any(ExtendCreditLeaseRequestBody.class)))
                .thenReturn(
                        ExtendCreditLeaseResponse.builder().data(lease(2000)).build());

        wire.extend("lse_1", 1000, EXPIRES_AT);
        wire.extend("lse_1", 1000, EXPIRES_AT);

        ArgumentCaptor<ExtendCreditLeaseRequestBody> body = ArgumentCaptor.forClass(ExtendCreditLeaseRequestBody.class);
        verify(credits, org.mockito.Mockito.times(2)).extendCreditLease(eq("lse_1"), body.capture());
        String first = body.getAllValues().get(0).getIdempotencyKey().orElse(null);
        String second = body.getAllValues().get(1).getIdempotencyKey().orElse(null);
        assertTrue(first != null && !first.isEmpty());
        // An extend is an increment, so one key per extend: retries of the same call collapse,
        // while a later extend still grows the lease.
        assertNotEquals(first, second);
    }
}
