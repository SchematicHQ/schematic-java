package com.schematic.api.credits;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.schematic.api.core.RequestOptions;
import com.schematic.api.errors.PaymentRequiredError;
import com.schematic.api.resources.credits.CreditsClient;
import com.schematic.api.resources.features.FeaturesClient;
import com.schematic.api.resources.features.requests.CheckAndReserveFlagRequestBody;
import com.schematic.api.resources.features.types.CheckAndReserveFlagResponse;
import com.schematic.api.types.ApiError;
import com.schematic.api.types.CheckAndReserveFlagResponseData;
import com.schematic.api.types.FlagCheckReservationResponseData;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.Callable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

class ServerCreditCheckTest {

    private static final Instant NOW = Instant.parse("2026-01-01T00:00:00Z");
    private static final Map<String, String> COMPANY = Collections.singletonMap("id", "co_1");

    private FeaturesClient features;
    private CreditsClient credits;
    private ServerCreditCheck check;

    @BeforeEach
    void setUp() {
        features = mock(FeaturesClient.class);
        credits = mock(CreditsClient.class);
        check = new ServerCreditCheck(
                features, credits, null, Duration.ofSeconds(60), Clock.fixed(NOW, ZoneOffset.UTC));
    }

    private static CheckRequest request(double usage, String eventSubtype, boolean failOpen) {
        return new CheckRequest("inference", COMPANY, null, usage, eventSubtype, failOpen);
    }

    private static Callable<CheckResult> fallback(boolean[] called) {
        return () -> {
            called[0] = true;
            return new CheckResult(true, true, "fallback", "inference", null, null, null, null);
        };
    }

    private static CheckAndReserveFlagResponse response(
            boolean value, String reason, FlagCheckReservationResponseData reservation) {
        CheckAndReserveFlagResponseData._FinalStage data = CheckAndReserveFlagResponseData.builder()
                .flag("inference")
                .reason(reason)
                .value(value)
                .flagId("flag_1");
        if (reservation != null) {
            data.reservation(reservation);
        }
        return CheckAndReserveFlagResponse.builder().data(data.build()).build();
    }

    private static FlagCheckReservationResponseData hold(String eventSubtype) {
        FlagCheckReservationResponseData._FinalStage held = FlagCheckReservationResponseData.builder()
                .companyId("co_1")
                .consumptionRate(10)
                .creditTypeId("ct_1")
                .creditsReserved(100)
                .expiresAt(OffsetDateTime.ofInstant(NOW.plusSeconds(60), ZoneOffset.UTC))
                .id("res_1")
                .quantityReserved(10);
        if (eventSubtype != null) {
            held.eventSubtype(eventSubtype);
        }
        return held.build();
    }

    @Test
    void takesAHoldAndReturnsAServerModeHandle() {
        when(features.checkAndReserveFlag(eq("inference"), any(CheckAndReserveFlagRequestBody.class), any()))
                .thenReturn(response(true, "ok", hold("inference_tokens")));

        CheckResult result = check.check(request(10, null, false), null, () -> false, fallback(new boolean[1]));

        assertTrue(result.isAllowed());
        Reservation reservation = result.getReservation();
        assertNotNull(reservation);
        assertEquals("res_1", reservation.getId());
        // No lease exists server-side, so the handle mirrors the hold id and settles by it.
        assertEquals("res_1", reservation.getLeaseId());
        assertEquals(CreditLeaseMode.SERVER, reservation.getMode());
        assertEquals("inference_tokens", reservation.getEventSubtype());
        assertEquals(100.0, reservation.getCreditsReserved());
    }

    @Test
    void sendsTheHoldWindowThePreflightAndAnIdempotencyKey() {
        when(features.checkAndReserveFlag(eq("inference"), any(CheckAndReserveFlagRequestBody.class), any()))
                .thenReturn(response(true, "ok", hold("inference_tokens")));

        check.check(request(10, "inference_tokens", false), null, () -> false, fallback(new boolean[1]));

        ArgumentCaptor<CheckAndReserveFlagRequestBody> body =
                ArgumentCaptor.forClass(CheckAndReserveFlagRequestBody.class);
        ArgumentCaptor<RequestOptions> options = ArgumentCaptor.forClass(RequestOptions.class);
        verify(features).checkAndReserveFlag(eq("inference"), body.capture(), options.capture());
        assertEquals(10.0, body.getValue().getQuantity().orElse(null));
        assertEquals(
                OffsetDateTime.ofInstant(NOW.plusSeconds(60), ZoneOffset.UTC),
                body.getValue().getExpiresAt().orElse(null));
        assertEquals(
                "inference_tokens",
                body.getValue().getPreflight().get().getEventUsage().get().getEventSubtype());
        assertTrue(body.getValue().getIdempotencyKey().isPresent());
        // The key makes the transport's retries safe, so they stay on.
        assertFalse(options.getValue().getMaxRetries().isPresent());
    }

    @Test
    void sendsTheGenericUsagePreflightWithoutASubtype() {
        when(features.checkAndReserveFlag(eq("inference"), any(CheckAndReserveFlagRequestBody.class), any()))
                .thenReturn(response(true, "ok", hold("inference_tokens")));

        check.check(request(7.2, null, false), null, () -> false, fallback(new boolean[1]));

        ArgumentCaptor<CheckAndReserveFlagRequestBody> body =
                ArgumentCaptor.forClass(CheckAndReserveFlagRequestBody.class);
        verify(features).checkAndReserveFlag(eq("inference"), body.capture(), any());
        // A preflight asks an upper-bound question, so a fraction rounds up.
        assertEquals(8L, body.getValue().getPreflight().get().getUsage().orElse(null));
    }

    @Test
    void deniesWithoutAHoldWhenTheServerSaysTheCreditsAreShort() {
        when(features.checkAndReserveFlag(eq("inference"), any(CheckAndReserveFlagRequestBody.class), any()))
                .thenReturn(response(false, "Insufficient credits", null));

        CheckResult result = check.check(request(10, null, false), null, () -> false, fallback(new boolean[1]));

        assertFalse(result.isAllowed());
        assertNull(result.getReservation());
        assertEquals("Insufficient credits", result.getReason());
    }

    @Test
    void treatsA402AsADefinitiveDenialEvenWhenFailingOpen() {
        when(features.checkAndReserveFlag(eq("inference"), any(CheckAndReserveFlagRequestBody.class), any()))
                .thenThrow(new PaymentRequiredError(
                        ApiError.builder().error("out of credits").build()));

        CheckResult result = check.check(request(10, null, true), null, () -> true, fallback(new boolean[1]));

        assertFalse(result.isAllowed());
        assertEquals("Insufficient credits", result.getReason());
        assertEquals("out of credits", result.getErr());
    }

    @Test
    void failsClosedWhenTheCallErrors() {
        when(features.checkAndReserveFlag(eq("inference"), any(CheckAndReserveFlagRequestBody.class), any()))
                .thenThrow(new RuntimeException("wire down"));

        CheckResult result = check.check(request(10, null, false), null, () -> true, fallback(new boolean[1]));

        assertFalse(result.isAllowed());
        assertEquals("server_reservation_failed", result.getReason());
        assertEquals("server_reservation_failed", result.getErr());
    }

    @Test
    void failsOpenToTheCallersDefaultWhenTheCallErrors() {
        when(features.checkAndReserveFlag(eq("inference"), any(CheckAndReserveFlagRequestBody.class), any()))
                .thenThrow(new RuntimeException("wire down"));

        CheckResult result = check.check(request(10, null, true), null, () -> true, fallback(new boolean[1]));

        assertTrue(result.isAllowed());
        assertEquals("server_reservation_failed_fail_open", result.getReason());
        assertEquals("server_reservation_failed", result.getErr());
    }

    @Test
    void releasesAHoldThatNamesNoEventSubtype() {
        when(features.checkAndReserveFlag(eq("inference"), any(CheckAndReserveFlagRequestBody.class), any()))
                .thenReturn(response(true, "ok", hold(null)));

        CheckResult result = check.check(request(10, null, false), null, () -> false, fallback(new boolean[1]));

        verify(credits).releaseCreditReservation("res_1");
        assertFalse(result.isAllowed());
        assertEquals("missing_event_subtype", result.getErr());
    }

    @Test
    void keepsTheServersVerdictForAnUnsettleableHoldWhenFailingOpen() {
        when(features.checkAndReserveFlag(eq("inference"), any(CheckAndReserveFlagRequestBody.class), any()))
                .thenReturn(response(true, "ok", hold(null)));

        CheckResult result = check.check(request(10, null, true), null, () -> false, fallback(new boolean[1]));

        verify(credits).releaseCreditReservation("res_1");
        assertTrue(result.isAllowed());
        assertNull(result.getReservation());
        assertEquals("missing_event_subtype", result.getErr());
    }

    @Test
    void fallsBackToAPlainCheckWhenTheUsageIsZero() {
        boolean[] called = {false};

        CheckResult result = check.check(request(0, null, false), null, () -> false, fallback(called));

        assertTrue(called[0]);
        assertEquals("fallback", result.getReason());
        verify(features, never()).checkAndReserveFlag(any(), any(CheckAndReserveFlagRequestBody.class), any());
    }

    @Test
    void resolvesAnInvalidUsageThroughTheFailureContractWithoutCallingTheApi() {
        boolean[] called = {false};

        CheckResult closed = check.check(request(-5, null, false), null, () -> true, fallback(called));
        CheckResult open = check.check(request(Double.NaN, null, true), null, () -> true, fallback(called));

        assertFalse(called[0]);
        assertFalse(closed.isAllowed());
        assertEquals("invalid_usage", closed.getReason());
        assertTrue(open.isAllowed());
        assertEquals("invalid_usage_fail_open", open.getReason());
        verify(features, never()).checkAndReserveFlag(any(), any(CheckAndReserveFlagRequestBody.class), any());
    }
}
