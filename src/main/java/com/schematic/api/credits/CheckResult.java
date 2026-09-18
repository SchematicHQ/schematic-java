package com.schematic.api.credits;

import com.schematic.api.types.RulesengineFeatureEntitlement;

/** What a credit-aware check resolved, and the hold it took if it took one. */
public final class CheckResult {

    private final boolean allowed;
    private final boolean value;
    private final String reason;
    private final String flagKey;
    private final String flagId;
    private final RulesengineFeatureEntitlement entitlement;
    private final Reservation reservation;
    private final String err;

    public CheckResult(
            boolean allowed,
            boolean value,
            String reason,
            String flagKey,
            String flagId,
            RulesengineFeatureEntitlement entitlement,
            Reservation reservation,
            String err) {
        this.allowed = allowed;
        this.value = value;
        this.reason = reason;
        this.flagKey = flagKey;
        this.flagId = flagId;
        this.entitlement = entitlement;
        this.reservation = reservation;
        this.err = err;
    }

    /** Whether the caller may proceed. */
    public boolean isAllowed() {
        return allowed;
    }

    /** The flag's boolean value, which the non-credit paths mirror onto {@link #isAllowed()}. */
    public boolean getValue() {
        return value;
    }

    public String getReason() {
        return reason;
    }

    public String getFlagKey() {
        return flagKey;
    }

    public String getFlagId() {
        return flagId;
    }

    public RulesengineFeatureEntitlement getEntitlement() {
        return entitlement;
    }

    /**
     * The hold this check carved out, or null when it allowed without taking one. Pass it to
     * {@code trackWithReservation} when the work completes.
     */
    public Reservation getReservation() {
        return reservation;
    }

    /** The failure behind a result the SDK resolved itself, or null. */
    public String getErr() {
        return err;
    }
}
