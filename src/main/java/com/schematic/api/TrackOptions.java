package com.schematic.api;

import java.time.OffsetDateTime;

/**
 * Optional metadata for a {@link Schematic#track} event.
 *
 * <p>Fields map directly to the corresponding {@code CreateEventRequestBody} properties. Omit any
 * field you don't need; the SDK only sends fields that are explicitly set.
 */
public final class TrackOptions {

    private final String idempotencyKey;
    private final OffsetDateTime sentAt;
    private final Boolean trustedClientClock;
    private final Boolean backfill;

    private TrackOptions(Builder builder) {
        this.idempotencyKey = builder.idempotencyKey;
        this.sentAt = builder.sentAt;
        this.trustedClientClock = builder.trustedClientClock;
        this.backfill = builder.backfill;
    }

    public static Builder builder() {
        return new Builder();
    }

    /**
     * Client-supplied dedupe key. Duplicate events with the same key (scoped to the environment) are
     * dropped server-side for 24 hours.
     */
    public String getIdempotencyKey() {
        return idempotencyKey;
    }

    /** Timestamp the event was sent. Required when {@code trustedClientClock} is true. */
    public OffsetDateTime getSentAt() {
        return sentAt;
    }

    /**
     * When true, use {@code sentAt} as the effective event timestamp instead of server receipt time.
     * Requires a secret API key and {@code sentAt}.
     */
    public Boolean getTrustedClientClock() {
        return trustedClientClock;
    }

    /**
     * Import historical data without affecting billing. Requires a secret API key and
     * {@code trustedClientClock}.
     */
    public Boolean getBackfill() {
        return backfill;
    }

    public static final class Builder {
        private String idempotencyKey;
        private OffsetDateTime sentAt;
        private Boolean trustedClientClock;
        private Boolean backfill;

        public Builder idempotencyKey(String idempotencyKey) {
            this.idempotencyKey = idempotencyKey;
            return this;
        }

        public Builder sentAt(OffsetDateTime sentAt) {
            this.sentAt = sentAt;
            return this;
        }

        public Builder trustedClientClock(Boolean trustedClientClock) {
            this.trustedClientClock = trustedClientClock;
            return this;
        }

        public Builder backfill(Boolean backfill) {
            this.backfill = backfill;
            return this;
        }

        public TrackOptions build() {
            return new TrackOptions(this);
        }
    }
}
