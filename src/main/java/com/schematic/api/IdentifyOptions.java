package com.schematic.api;

/**
 * Optional metadata for an {@link Schematic#identify} event.
 *
 * <p>Omit any field you don't need; the SDK only sends fields that are explicitly set.
 */
public final class IdentifyOptions {

    private final String idempotencyKey;

    private IdentifyOptions(Builder builder) {
        this.idempotencyKey = builder.idempotencyKey;
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

    public static final class Builder {
        private String idempotencyKey;

        public Builder idempotencyKey(String idempotencyKey) {
            this.idempotencyKey = idempotencyKey;
            return this;
        }

        public IdentifyOptions build() {
            return new IdentifyOptions(this);
        }
    }
}
