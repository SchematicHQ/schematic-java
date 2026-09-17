package com.schematic.api.credits;

import com.schematic.api.core.BaseSchematicApiException;
import com.schematic.api.core.ObjectMappers;
import com.schematic.api.core.RequestOptions;
import com.schematic.api.logger.SchematicLogger;
import com.schematic.api.resources.credits.CreditsClient;
import com.schematic.api.resources.features.FeaturesClient;
import com.schematic.api.resources.features.requests.CheckAndReserveFlagRequestBody;
import com.schematic.api.types.ApiError;
import com.schematic.api.types.CheckAndReserveFlagResponseData;
import com.schematic.api.types.FeatureEntitlement;
import com.schematic.api.types.FlagCheckReservationResponseData;
import com.schematic.api.types.PreflightRequestBody;
import com.schematic.api.types.RulesengineFeatureEntitlement;
import java.time.Clock;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.function.BooleanSupplier;

/**
 * Gates one check server-side. A single check-and-reserve call does everything the client flow
 * spreads across a lease acquire, a local reserve, and a rules evaluation: the server evaluates the
 * flag against the company's real balance, applies the preflight cost, and takes the hold in the
 * same round trip. There is no lease, no local store, and no rules engine involved.
 *
 * <p>The failure contract differs from client mode in one place. Fail-open there means re-run the
 * engine with the credit balance assumed sufficient, so plan targeting and every non-credit
 * condition still apply. Server mode has no local engine to re-run, since the call that would have
 * answered is the one that failed, so fail-open returns the caller's default value instead.
 *
 * <p>No flag_check event is reported here: the server logs the flag check for check-and-reserve
 * itself, the way the REST check path does.
 */
public final class ServerCreditCheck {

    // Mirrors the reason the API returns on a 200 with value false for the same denial, so a
    // caller matching on the reason has one string to match either way.
    private static final String INSUFFICIENT_CREDITS_REASON = "Insufficient credits";

    private final FeaturesClient features;
    private final CreditsClient credits;
    private final SchematicLogger logger;
    private final Duration reservationTtl;
    private final Clock clock;

    public ServerCreditCheck(
            FeaturesClient features,
            CreditsClient credits,
            SchematicLogger logger,
            Duration reservationTtl,
            Clock clock) {
        this.features = features;
        this.credits = credits;
        this.logger = logger;
        this.reservationTtl = reservationTtl != null ? reservationTtl : CreditLeaseDefaults.RESERVATION_TTL;
        this.clock = clock != null ? clock : Clock.systemUTC();
    }

    /**
     * Runs the server-gated check. {@code getDefault} answers the caller's default for this flag,
     * which the fail-open branch returns. {@code fallback} is the plain flag check, for the asks
     * that need no hold at all.
     */
    public CheckResult check(
            CheckRequest request, Duration timeout, BooleanSupplier getDefault, Callable<CheckResult> fallback) {
        // The same guard as the client path: a malformed usage must never reach the wire. NaN
        // slips through every numeric comparison, so the server would size a hold off a value no
        // comparison can reject.
        if (!CreditAmounts.isValidQuantity(request.getUsage())) {
            error("Server reservation: invalid usage " + request.getUsage() + " for flag " + request.getFlagKey()
                    + "; must be a finite, non-negative number");
            return failureResult(request, getDefault, "invalid_usage");
        }

        // Nothing to hold. The plain check still carries the preflight.
        if (request.getUsage() == 0) {
            debug("Server reservation: usage is 0 for flag " + request.getFlagKey()
                    + ", nothing to reserve, using a plain check");
            return fallBack(fallback);
        }

        CheckAndReserveFlagRequestBody.Builder body = CheckAndReserveFlagRequestBody.builder()
                .quantity(request.getUsage())
                .expiresAt(OffsetDateTime.ofInstant(clock.instant().plus(reservationTtl), ZoneOffset.UTC));
        if (!request.getCompany().isEmpty()) {
            body.company(request.getCompany());
        }
        if (!request.getUser().isEmpty()) {
            body.user(request.getUser());
        }
        PreflightOptions preflight = PreflightOptions.fromUsage(request.getUsage(), request.getEventSubtype());
        PreflightRequestBody preflightBody = preflight != null ? preflight.toRequestBody() : null;
        if (preflightBody != null) {
            body.preflight(preflightBody);
        }
        // One key per check, minted before the call so the transport's retries resend the same
        // one: a 502 from a load balancer after the API committed the hold then collapses onto
        // that hold instead of taking a second one and parking the first until its TTL.
        body.idempotencyKey(UUID.randomUUID().toString());
        RequestOptions.Builder options = RequestOptions.builder();
        if (timeout != null) {
            options.timeout((int) timeout.toMillis(), TimeUnit.MILLISECONDS);
        }

        CheckAndReserveFlagResponseData data;
        try {
            data = features.checkAndReserveFlag(request.getFlagKey(), body.build(), options.build())
                    .getData();
        } catch (BaseSchematicApiException e) {
            // A 402 is the server's definitive answer, not a can't-gate: it knows the credits are
            // not there. Deny regardless of the failure mode, since failing open here would hand
            // out credit the balance cannot cover.
            if (e.statusCode() == 402) {
                return new CheckResult(
                        false, false, INSUFFICIENT_CREDITS_REASON, request.getFlagKey(), null, null, null, message(e));
            }
            error("Server reservation: check-and-reserve for flag " + request.getFlagKey() + " failed: " + e);
            return failureResult(request, getDefault, "server_reservation_failed");
        } catch (RuntimeException e) {
            error("Server reservation: check-and-reserve for flag " + request.getFlagKey() + " failed: " + e);
            return failureResult(request, getDefault, "server_reservation_failed");
        }

        RulesengineFeatureEntitlement entitlement =
                toRulesengineEntitlement(data.getEntitlement().orElse(null));
        CheckResult base = new CheckResult(
                data.getValue(),
                data.getValue(),
                data.getReason(),
                orElse(data.getFlag(), request.getFlagKey()),
                data.getFlagId().orElse(null),
                entitlement,
                null,
                data.getError().orElse(null));

        // No hold comes back when the flag denied, the credits were insufficient (a 200 with value
        // false), or the feature is not credit-metered. Nothing was held, so nothing to release.
        FlagCheckReservationResponseData held = data.getReservation().orElse(null);
        if (!data.getValue() || held == null) {
            return base;
        }

        // The settling event is named by the event subtype; the caller's explicit one wins,
        // otherwise the server names it on the hold. With neither, the hold could never be
        // settled, so release it now rather than leaving credits parked until the TTL.
        String eventSubtype = request.getEventSubtype();
        if (eventSubtype == null || eventSubtype.isEmpty()) {
            eventSubtype = held.getEventSubtype().orElse(null);
        }
        if (eventSubtype == null || eventSubtype.isEmpty()) {
            error("Server reservation: reservation " + held.getId() + " for flag " + request.getFlagKey()
                    + " has no event subtype, releasing it, since it could never be settled");
            try {
                credits.releaseCreditReservation(held.getId());
            } catch (RuntimeException e) {
                warn("Server reservation: failed to release " + held.getId() + " (" + e
                        + "); its hold is refunded when it expires");
            }
            if (!request.isFailOpen()) {
                return failureResult(request, getDefault, "missing_event_subtype");
            }
            // Fail-open means assume the credits are there, and the server has already evaluated
            // the flag and allowed this check. Only the settle is impossible, so keep the server's
            // verdict rather than falling back to the caller's default, which could deny what the
            // server allowed.
            return new CheckResult(
                    base.isAllowed(),
                    base.getValue(),
                    base.getReason(),
                    base.getFlagKey(),
                    base.getFlagId(),
                    base.getEntitlement(),
                    null,
                    "missing_event_subtype");
        }

        Reservation reservation = new Reservation(
                held.getId(),
                // No lease exists in server mode; mirror the id so the field stays populated and a
                // handle round-trips through code that reads it.
                held.getId(),
                CreditLeaseMode.SERVER,
                held.getCompanyId(),
                held.getCreditTypeId(),
                eventSubtype,
                held.getQuantityReserved(),
                held.getCreditsReserved(),
                held.getConsumptionRate(),
                held.getExpiresAt().toInstant(),
                request.getCompany(),
                request.getUser());
        return new CheckResult(
                true,
                true,
                data.getReason(),
                orElse(data.getFlag(), request.getFlagKey()),
                data.getFlagId().orElse(null),
                entitlement,
                reservation,
                null);
    }

    /**
     * Resolves a can't-gate outcome. Fail-closed denies; fail-open returns the caller's default
     * value, since there is no local engine to re-evaluate with an assumed-sufficient balance.
     */
    private static CheckResult failureResult(CheckRequest request, BooleanSupplier getDefault, String reason) {
        if (!request.isFailOpen()) {
            return new CheckResult(false, false, reason, request.getFlagKey(), null, null, null, reason);
        }
        boolean value = getDefault != null && getDefault.getAsBoolean();
        return new CheckResult(value, value, reason + "_fail_open", request.getFlagKey(), null, null, null, reason);
    }

    /**
     * The API and the rules engine describe an entitlement with the same fields under two
     * generated types, so the server's answer is remapped through its JSON rather than dropped. A
     * shape the mapper cannot bridge costs the caller the entitlement detail, never the verdict.
     */
    private RulesengineFeatureEntitlement toRulesengineEntitlement(FeatureEntitlement entitlement) {
        if (entitlement == null) {
            return null;
        }
        try {
            return ObjectMappers.JSON_MAPPER.convertValue(entitlement, RulesengineFeatureEntitlement.class);
        } catch (RuntimeException e) {
            debug("Server reservation: could not read the entitlement off the response: " + e);
            return null;
        }
    }

    private static String message(BaseSchematicApiException e) {
        Object body = e.body();
        if (body instanceof ApiError) {
            String error = ((ApiError) body).getError();
            if (error != null && !error.isEmpty()) {
                return error;
            }
        }
        return e.getMessage();
    }

    private static CheckResult fallBack(Callable<CheckResult> fallback) {
        try {
            return fallback.call();
        } catch (Exception e) {
            throw new IllegalStateException("plain flag check failed", e);
        }
    }

    private static String orElse(String value, String fallback) {
        return value == null || value.isEmpty() ? fallback : value;
    }

    private void debug(String message) {
        if (logger != null) {
            logger.debug(message);
        }
    }

    private void warn(String message) {
        if (logger != null) {
            logger.warn(message);
        }
    }

    private void error(String message) {
        if (logger != null) {
            logger.error(message);
        }
    }
}
