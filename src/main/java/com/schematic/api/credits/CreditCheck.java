package com.schematic.api.credits;

import com.schematic.api.logger.SchematicLogger;
import com.schematic.api.types.EventBodyFlagCheck;
import com.schematic.api.types.RulesengineCheckFlagResult;
import com.schematic.api.types.RulesengineCompany;
import com.schematic.api.types.RulesengineEntitlementValueType;
import com.schematic.api.types.RulesengineFeatureEntitlement;
import com.schematic.api.types.RulesengineFlag;
import com.schematic.api.types.RulesengineUser;
import java.time.Clock;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

/**
 * Gates one check against a local lease, returning a hold when it allows.
 *
 * <p>One check runs the rules engine twice. The first run is a probe against the company's real
 * balance that names the credit being metered; the second gates the call against the lease's local
 * balance, after the credits have already been debited. conformance/SPEC.md explains why each step
 * is ordered the way it is, and the vectors pin it.
 */
public final class CreditCheck {

    private final CreditCheckDataStream dataStream;
    private final LeaseStore leases;
    private final ReservationStore reservations;
    private final CreditLeaseManager manager;
    private final SchematicLogger logger;
    private final Clock clock;
    private final FlagCheckReporter flagChecks;
    private final Supplier<String> reservationIds;

    public CreditCheck(
            CreditCheckDataStream dataStream,
            LeaseStore leases,
            ReservationStore reservations,
            CreditLeaseManager manager,
            SchematicLogger logger,
            Clock clock,
            FlagCheckReporter flagChecks,
            Supplier<String> reservationIds) {
        this.dataStream = dataStream;
        this.leases = leases;
        this.reservations = reservations;
        this.manager = manager;
        this.logger = logger;
        this.clock = clock != null ? clock : Clock.systemUTC();
        this.flagChecks = flagChecks;
        this.reservationIds = reservationIds != null
                ? reservationIds
                : () -> UUID.randomUUID().toString();
    }

    /**
     * Runs the credit-gated check.
     *
     * <p>{@code fallback} is the plain flag check. Every step that cannot resolve a credit to meter
     * defers to it, since the plain check has its own degradation story and issues no hold. A step
     * that can resolve the credit but cannot gate on it goes through the caller's fail-open or
     * fail-closed contract instead.
     */
    public CheckResult check(CheckRequest request, Callable<CheckResult> fallback) {
        // A malformed usage must never reach the stores: NaN slips through every numeric
        // comparison, and a NaN balance on a possibly shared lease would approve every later
        // reserve. The caller asked for a contract covering exactly this, so resolve it through
        // that rather than letting it surface as an opaque reserve failure.
        if (!CreditAmounts.isValidQuantity(request.getUsage())) {
            error("Lease check: invalid usage " + request.getUsage() + " for flag " + request.getFlagKey()
                    + "; must be a finite, non-negative number");
            return emit(request, staticFailure(request, "invalid_usage", null), null, null, null);
        }

        // Nothing to reserve. The plain check still carries the preflight, so every rule evaluates
        // normally; a zero-credit hold would only be a no-op.
        if (request.getUsage() == 0) {
            debug("Lease check: usage is 0 for flag " + request.getFlagKey()
                    + ", nothing to reserve, using a plain check");
            return fallBack(fallback);
        }

        if (dataStream == null) {
            debug("Lease check: no DataStream, using a plain check");
            return fallBack(fallback);
        }

        RulesengineFlag flag;
        try {
            flag = dataStream.getFlag(request.getFlagKey());
        } catch (RuntimeException e) {
            warn("Lease check: failed to load flag " + request.getFlagKey() + ": " + e);
            flag = null;
        }
        if (flag == null) {
            debug("Lease check: no cached flag for " + request.getFlagKey() + ", using a plain check");
            return fallBack(fallback);
        }

        if (request.getCompany().isEmpty()) {
            debug("Lease check: no company keys, using a plain check");
            return fallBack(fallback);
        }

        // Resolve company and user the way a plain DataStream check does: cache first, then a live
        // fetch. Evaluating without an entity the caller named would silently skip its targeted
        // rules and overrides, so a miss defers to the plain check instead.
        RulesengineCompany company;
        try {
            company = dataStream.getCompany(request.getCompany());
        } catch (RuntimeException e) {
            debug("Lease check: company fetch failed (" + e + "), using a plain check");
            company = null;
        }
        if (company == null) {
            return fallBack(fallback);
        }

        RulesengineUser user = null;
        if (!request.getUser().isEmpty()) {
            try {
                user = dataStream.getUser(request.getUser());
            } catch (RuntimeException e) {
                debug("Lease check: user fetch failed (" + e + "), using a plain check");
            }
            if (user == null) {
                return fallBack(fallback);
            }
        }

        // Entitlement-first resolution. The probe runs against the real balance with no preflight:
        // applying a credit cost to a lease-depleted server balance could fail the credit
        // condition, drop the engine to a lower-priority rule, and hide the entitlement being
        // looked for.
        RulesengineCheckFlagResult probe;
        try {
            probe = dataStream.evaluateFlag(flag, company, user, null);
        } catch (Exception e) {
            // A probe failure is a resolution miss, not the gate, and no hold exists yet to cancel.
            warn("Lease check: entitlement probe failed for flag " + request.getFlagKey() + " (" + e
                    + "), using a plain check");
            return fallBack(fallback);
        }
        if (probe == null) {
            return fallBack(fallback);
        }

        RulesengineFeatureEntitlement entitlement = probe.getEntitlement().orElse(null);
        if (entitlement == null || !RulesengineEntitlementValueType.CREDIT.equals(entitlement.getValueType())) {
            // A boolean or override grant, a numeric allocation, unlimited, or simply not
            // entitled. The feature resolves without drawing a credit, so skip the lease and the
            // reserve round trip entirely.
            debug("Lease check: flag " + request.getFlagKey() + " matched a non-credit entitlement, using a plain "
                    + "check, no reservation");
            return fallBack(fallback);
        }

        String creditId = entitlement.getCreditId().orElse(null);
        double consumptionRate = entitlement.getConsumptionRate().orElse(0.0);
        // The caller's subtype wins; otherwise the entitlement names the metered event. A credit
        // entitlement with neither a resolvable subtype nor a positive rate can never be billed,
        // so it is not gateable.
        String eventSubtype = request.getEventSubtype();
        if (eventSubtype == null || eventSubtype.isEmpty()) {
            eventSubtype = entitlement.getEventSubtype().orElse(null);
        }
        if (creditId == null
                || creditId.isEmpty()
                || consumptionRate <= 0
                || eventSubtype == null
                || eventSubtype.isEmpty()) {
            debug("Lease check: flag " + request.getFlagKey() + " has an incomplete credit entitlement, using a "
                    + "plain check");
            return fallBack(fallback);
        }

        // Sized from the quantity the settle will bill, not the raw usage: the track event's
        // quantity is an integer, so a fractional usage settles as a whole unit. Holding the
        // fraction would under-reserve every fractional check by the difference.
        double reservedQuantity = ReservationSettlement.settleQuantity(request.getUsage());
        double creditCost = reservedQuantity * consumptionRate;
        String companyId = company.getId();
        String userId = user == null ? null : user.getId();

        LeaseState lease = manager.acquireIfNeeded(companyId, creditId);
        if (lease == null) {
            return failure(request, "lease_acquire_failed", flag, company, user, creditId, companyId, userId);
        }

        // Resolved before the debit, not after it. Each of these can throw, and between the debit
        // and the record that pins it there is nothing to refund a stranded slice: it would sit on
        // the lease until expiry with no hold naming it.
        String reservationId;
        Instant expiresAt;
        try {
            ResolvedLeaseConfig resolved = manager.resolveConfig(creditId);
            reservationId = reservationIds.get();
            expiresAt = clock.instant().plus(resolved.getReservationTtl());
        } catch (RuntimeException e) {
            error("Lease check: could not prepare a reservation for " + companyId + "/" + creditId + ": " + e);
            return failure(request, "lease_store_error", flag, company, user, creditId, companyId, userId);
        }

        // tryReserve is the atomic gate: check and debit in one step, returning the post-debit
        // balance so the pre-debit figure needs no second read, and the lease it debited.
        ReserveResult reserve;
        try {
            reserve = leases.tryReserve(companyId, creditId, creditCost);
            if (reserve == null) {
                // Pass the cost as required credits so a single large request extends even while
                // the ratio still sits above the water mark.
                manager.maybeExtend(companyId, creditId, creditCost);
                reserve = leases.tryReserve(companyId, creditId, creditCost);
            }
        } catch (RuntimeException e) {
            error("Lease check: reserve against " + companyId + "/" + creditId + " failed: " + e);
            return failure(request, "lease_store_error", flag, company, user, creditId, companyId, userId);
        }
        if (reserve == null) {
            return failure(request, "insufficient_lease_balance", flag, company, user, creditId, companyId, userId);
        }
        String debitedLeaseId = reserve.getLeaseId();
        if (debitedLeaseId == null || debitedLeaseId.isEmpty()) {
            // A store that debited without naming the lease leaves the hold nothing to pin its
            // refunds to, and pinning the acquired lease instead would refund and bill a lease
            // that never held these credits. Hand the debit straight back, unpinned since there is
            // no id to pin it to, and resolve through the caller's contract.
            error("Lease check: reserve against " + companyId + "/" + creditId + " named no lease");
            try {
                leases.refund(companyId, creditId, creditCost, null);
            } catch (RuntimeException e) {
                warn("Lease check: could not return an unattributed debit for " + companyId + "/" + creditId + " (" + e
                        + "); the slice is reclaimed at lease expiry");
            }
            return failure(request, "lease_store_error", flag, company, user, creditId, companyId, userId);
        }

        // Record the hold after the debit and before the gate. A crash between the debit and this
        // add leaks at most this one hold, reclaimed when the lease expires server-side; recording
        // first would instead leave a record with no debit, which a later consume would refund
        // into a double-spend.
        Reservation reservation = new Reservation(
                reservationId,
                // The lease the debit came out of, which the slot may have taken on since the
                // acquire above: the window between them spans the extend's network call. A hold
                // pinned to the lease the acquire returned would have its refunds dropped and
                // would bill the wrong lease, so this is never the acquired id.
                debitedLeaseId,
                CreditLeaseMode.CLIENT,
                companyId,
                creditId,
                eventSubtype,
                reservedQuantity,
                creditCost,
                consumptionRate,
                expiresAt,
                request.getCompany(),
                request.getUser());
        try {
            reservations.add(reservation);
        } catch (RuntimeException e) {
            error("Lease check: failed to persist reservation " + reservation.getId() + ": " + e);
            undoDebit(reservation);
            return failure(request, "lease_store_error", flag, company, user, creditId, companyId, userId);
        }

        // Gate against the lease's local view rather than the server's balance. The substituted
        // figure is the pre-reservation balance (what tryReserve returned plus what it debited,
        // exact as of the debit), and the credit cost tells the engine what this call costs, so it
        // evaluates the same arithmetic tryReserve just enforced, plus every non-credit rule.
        RulesengineCompany substituted = substituteCreditBalance(company, creditId, reserve.getBalance() + creditCost);
        RulesengineCheckFlagResult result;
        try {
            result = dataStream.evaluateFlag(
                    flag, substituted, user, PreflightOptions.forCreditCost(creditId, creditCost));
        } catch (Exception e) {
            error("Lease check: rules evaluation failed for flag " + request.getFlagKey() + ": " + e);
            // The engine itself is down, so there is no fail-open re-evaluation to run: resolve
            // the mode statically.
            cancelReservation(reservation);
            return emit(request, staticFailure(request, "wasm_error: " + e, flag), companyId, userId, null);
        }
        if (result == null) {
            cancelReservation(reservation);
            return emit(request, staticFailure(request, "wasm_error: no result", flag), companyId, userId, null);
        }

        // Engine-evaluated exits report the engine's resolved ids, mirroring the plain DataStream
        // path's flag_check event.
        String resolvedCompanyId = result.getCompanyId().orElse(companyId);
        String resolvedUserId = result.getUserId().orElse(userId);
        String ruleId = result.getRuleId().orElse(null);

        if (!result.getValue()) {
            cancelReservation(reservation);
            return emit(
                    request,
                    new CheckResult(
                            false,
                            false,
                            orElse(result.getReason(), "denied_by_engine"),
                            orElse(result.getFlagKey(), request.getFlagKey()),
                            result.getFlagId().orElse(null),
                            result.getEntitlement().orElse(null),
                            null,
                            null),
                    resolvedCompanyId,
                    resolvedUserId,
                    ruleId);
        }

        // Allowed against the substituted balance, so the hold stands. Top the lease up in the
        // background now that it has been drawn down: the check that drew it down should not pay
        // for the top-up.
        manager.extendInBackground(companyId, creditId);
        return emit(
                request,
                new CheckResult(
                        true,
                        true,
                        orElse(result.getReason(), "lease_reserved"),
                        orElse(result.getFlagKey(), request.getFlagKey()),
                        result.getFlagId().orElse(null),
                        result.getEntitlement().orElse(null),
                        reservation,
                        null),
                resolvedCompanyId,
                resolvedUserId,
                ruleId);
    }

    /**
     * Resolves a check that could not gate: acquire failed, store unreachable, or the lease is
     * exhausted.
     *
     * <p>Fail-closed denies. Fail-open means assume the credits are there, not skip the
     * evaluation: the rules still run with the balance substituted to an effectively unlimited
     * value, so plan targeting, overrides, and every non-credit condition still apply, and a
     * company that is not entitled stays denied with the lease backend down. Only an error in that
     * evaluation drops to a blanket allow.
     */
    private CheckResult failure(
            CheckRequest request,
            String reason,
            RulesengineFlag flag,
            RulesengineCompany company,
            RulesengineUser user,
            String creditId,
            String companyId,
            String userId) {
        if (!request.isFailOpen()) {
            return emit(request, staticFailure(request, reason, flag), companyId, userId, null);
        }

        RulesengineCheckFlagResult result;
        try {
            RulesengineCompany substituted =
                    substituteCreditBalance(company, creditId, CreditLeaseDefaults.FAIL_OPEN_BALANCE);
            result = dataStream.evaluateFlag(
                    flag, substituted, user, PreflightOptions.fromUsage(request.getUsage(), request.getEventSubtype()));
        } catch (Exception e) {
            warn("Lease check: the fail-open evaluation failed (" + e + "); allowing");
            return emit(request, staticFailure(request, reason, flag), companyId, userId, null);
        }
        if (result == null) {
            return emit(request, staticFailure(request, reason, flag), companyId, userId, null);
        }
        return emit(
                request,
                new CheckResult(
                        result.getValue(),
                        result.getValue(),
                        orElse(result.getReason(), "evaluated") + " (" + reason + "_fail_open)",
                        orElse(result.getFlagKey(), request.getFlagKey()),
                        result.getFlagId().orElse(flag.getId()),
                        result.getEntitlement().orElse(null),
                        null,
                        reason),
                companyId,
                userId,
                null);
    }

    /**
     * Resolves a mode with no evaluation behind it: deny for fail-closed, blanket allow for
     * fail-open. Used when the engine is the thing that failed, and when the fail-open evaluation
     * itself errors.
     */
    private static CheckResult staticFailure(CheckRequest request, String reason, RulesengineFlag flag) {
        boolean allowed = request.isFailOpen();
        return new CheckResult(
                allowed,
                allowed,
                allowed ? reason + "_fail_open" : reason,
                request.getFlagKey(),
                flag == null ? null : flag.getId(),
                null,
                null,
                reason);
    }

    /**
     * Returns a debit whose reservation record never landed, rather than stranding it until lease
     * expiry. Consume claims whatever slice of the add made it to the store and refunds it;
     * nothing claimed means nothing landed, so the debit is refunded directly. Both are pinned to
     * the lease the debit came out of. If the undo itself fails, accept the bounded leak: the
     * slice comes back at lease expiry, which beats risking a double refund.
     */
    private void undoDebit(Reservation reservation) {
        try {
            Double claimed = reservations.consume(reservation.getId(), 0);
            if (claimed == null) {
                leases.refund(
                        reservation.getCompanyId(),
                        reservation.getCreditTypeId(),
                        reservation.getCreditsReserved(),
                        reservation.getLeaseId());
            }
        } catch (RuntimeException e) {
            warn("Lease check: could not undo the local debit for " + reservation.getId() + " (" + e
                    + "); the slice is reclaimed at lease expiry");
        }
    }

    /** Claims the hold and refunds all of it. Best effort: a failure leaves it for the sweeper. */
    private void cancelReservation(Reservation reservation) {
        try {
            reservations.consume(reservation.getId(), 0);
        } catch (RuntimeException e) {
            warn("Lease check: failed to cancel reservation " + reservation.getId() + " (" + e
                    + "); its hold is reclaimed by the sweeper or at lease expiry");
        }
    }

    /** Copies the company with one credit balance replaced, leaving the cached entity untouched. */
    static RulesengineCompany substituteCreditBalance(RulesengineCompany company, String creditId, double balance) {
        Map<String, Double> balances = new HashMap<>(company.getCreditBalances());
        balances.put(creditId, balance);
        return RulesengineCompany.builder()
                .from(company)
                .creditBalances(balances)
                .build();
    }

    /**
     * Reports a credit-path resolution and passes the result straight through. Analytics must
     * never change a verdict the caller is already acting on, so this only ever adds an event.
     */
    private CheckResult emit(CheckRequest request, CheckResult result, String companyId, String userId, String ruleId) {
        if (flagChecks == null) {
            return result;
        }
        try {
            flagChecks.report(EventBodyFlagCheck.builder()
                    .flagKey(result.getFlagKey())
                    .reason(result.getReason())
                    .value(result.getValue())
                    .companyId(companyId)
                    .error(result.getErr())
                    .flagId(result.getFlagId())
                    .reqCompany(request.getCompany().isEmpty() ? null : request.getCompany())
                    .reqUser(request.getUser().isEmpty() ? null : request.getUser())
                    .ruleId(ruleId)
                    .userId(userId)
                    .build());
        } catch (RuntimeException e) {
            error("Lease check: failed to report the flag check: " + e);
        }
        return result;
    }

    private CheckResult fallBack(Callable<CheckResult> fallback) {
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
