package com.schematic.api.credits;

import java.time.Duration;

/** The per-call knobs a credit-aware check takes. */
public final class CheckOptions {

    private final Double usage;
    private final String eventSubtype;
    private final OnAcquireFailure onAcquireFailure;
    private final Boolean defaultValue;
    private final Duration timeout;

    private CheckOptions(Builder builder) {
        this.usage = builder.usage;
        this.eventSubtype = builder.eventSubtype;
        this.onAcquireFailure = builder.onAcquireFailure;
        this.defaultValue = builder.defaultValue;
        this.timeout = builder.timeout;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Double getUsage() {
        return usage;
    }

    public String getEventSubtype() {
        return eventSubtype;
    }

    public OnAcquireFailure getOnAcquireFailure() {
        return onAcquireFailure == null ? OnAcquireFailure.FAIL_CLOSED : onAcquireFailure;
    }

    public Boolean getDefaultValue() {
        return defaultValue;
    }

    public Duration getTimeout() {
        return timeout;
    }

    public static final class Builder {
        private Double usage;
        private String eventSubtype;
        private OnAcquireFailure onAcquireFailure;
        private Boolean defaultValue;
        private Duration timeout;

        /**
         * The units of the metered event the operation is about to record. The check holds
         * {@code usage} times the entitlement's consumption rate. Omit it for a plain flag check.
         */
        public Builder usage(double usage) {
            this.usage = usage;
            return this;
        }

        /**
         * The event the usage applies to, which picks the credit condition to gate on when a flag
         * meters more than one event. Defaults to the entitlement's own subtype.
         */
        public Builder eventSubtype(String eventSubtype) {
            this.eventSubtype = eventSubtype;
            return this;
        }

        /**
         * What to do when the check cannot gate at all. Defaults to
         * {@link OnAcquireFailure#FAIL_CLOSED}.
         */
        public Builder onAcquireFailure(OnAcquireFailure onAcquireFailure) {
            this.onAcquireFailure = onAcquireFailure;
            return this;
        }

        /** The value to fall back to, in place of the client's configured flag default. */
        public Builder defaultValue(boolean defaultValue) {
            this.defaultValue = defaultValue;
            return this;
        }

        /**
         * A per-call timeout for the call this check waits on: the check-and-reserve call in
         * server mode, and the REST flag check when the check falls back to one. Client-mode
         * lease acquires and extends take the client's own timeouts instead, since they are
         * single-flighted per company and credit type and one caller's timeout would govern every
         * caller that joins that flight.
         */
        public Builder timeout(Duration timeout) {
            this.timeout = timeout;
            return this;
        }

        public CheckOptions build() {
            return new CheckOptions(this);
        }
    }
}
