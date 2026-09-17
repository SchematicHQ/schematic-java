package com.schematic.api;

import java.util.List;

/**
 * Optional metadata for an {@link Schematic#identify} event.
 *
 * <p>Omit any field you don't need; the SDK only sends fields that are explicitly set.
 */
public final class IdentifyOptions {

    private final String idempotencyKey;
    private final List<String> prewarm;

    private IdentifyOptions(Builder builder) {
        this.idempotencyKey = builder.idempotencyKey;
        this.prewarm = builder.prewarm;
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

    /**
     * Credit type ids to warm a lease for once the identify is enqueued, so the first
     * credit-gated check does not pay the acquire round trip. A no-op unless credit leases are
     * configured on the client.
     */
    public List<String> getPrewarm() {
        return prewarm;
    }

    public static final class Builder {
        private String idempotencyKey;
        private List<String> prewarm;

        public Builder idempotencyKey(String idempotencyKey) {
            this.idempotencyKey = idempotencyKey;
            return this;
        }

        /** Credit type ids to warm a lease for after this identify. */
        public Builder prewarm(List<String> prewarm) {
            this.prewarm = prewarm;
            return this;
        }

        public IdentifyOptions build() {
            return new IdentifyOptions(this);
        }
    }
}
