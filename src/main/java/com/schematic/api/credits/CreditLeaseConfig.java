package com.schematic.api.credits;

import java.time.Duration;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import redis.clients.jedis.JedisPooled;

/**
 * Enables credit reservations on {@code check()} and {@code trackWithReservation()}. Leave it off
 * the builder to keep the SDK credit-unaware, where {@code check()} is a plain flag check.
 *
 * <p>Client mode (local leases) needs datastream, so the SDK has the cached flag and company
 * state it gates against. Without datastream the SDK gates in server mode instead: one
 * check-and-reserve API call per check.
 */
public final class CreditLeaseConfig {

    private final CreditLeaseMode mode;
    private final Duration defaultLeaseDuration;
    private final Duration defaultReservationTtl;
    private final Double defaultLeaseSize;
    private final Double lowWaterMark;
    private final Duration sweepInterval;
    private final Duration prewarmResolveTimeout;
    private final JedisPooled redisClient;
    private final String redisKeyPrefix;
    private final Map<String, CreditLeaseOverride> overrides;

    private CreditLeaseConfig(Builder builder) {
        this.mode = builder.mode != null ? builder.mode : CreditLeaseMode.AUTO;
        this.defaultLeaseDuration = builder.defaultLeaseDuration;
        this.defaultReservationTtl = builder.defaultReservationTtl;
        this.defaultLeaseSize = builder.defaultLeaseSize;
        this.lowWaterMark = builder.lowWaterMark;
        this.sweepInterval = builder.sweepInterval;
        this.prewarmResolveTimeout = builder.prewarmResolveTimeout;
        this.redisClient = builder.redisClient;
        this.redisKeyPrefix = builder.redisKeyPrefix;
        this.overrides = Collections.unmodifiableMap(new LinkedHashMap<>(builder.overrides));
    }

    public static Builder builder() {
        return new Builder();
    }

    public CreditLeaseMode getMode() {
        return mode;
    }

    public Duration getDefaultLeaseDuration() {
        return defaultLeaseDuration;
    }

    public Duration getDefaultReservationTtl() {
        return defaultReservationTtl;
    }

    public Double getDefaultLeaseSize() {
        return defaultLeaseSize;
    }

    public Double getLowWaterMark() {
        return lowWaterMark;
    }

    public Duration getSweepInterval() {
        return sweepInterval;
    }

    public Duration getPrewarmResolveTimeout() {
        return prewarmResolveTimeout;
    }

    public JedisPooled getRedisClient() {
        return redisClient;
    }

    public String getRedisKeyPrefix() {
        return redisKeyPrefix;
    }

    public Map<String, CreditLeaseOverride> getOverrides() {
        return overrides;
    }

    /** The knobs for one credit type: its override wins, then this config, then the default. */
    public ResolvedLeaseConfig resolve(String creditTypeId) {
        CreditLeaseOverride override = overrides.get(creditTypeId);
        Duration leaseDuration = firstNonNull(
                override != null ? override.getDefaultLeaseDuration() : null,
                defaultLeaseDuration,
                CreditLeaseDefaults.LEASE_DURATION);
        Duration reservationTtl = firstNonNull(
                override != null ? override.getDefaultReservationTtl() : null,
                defaultReservationTtl,
                CreditLeaseDefaults.RESERVATION_TTL);
        Double leaseSize = firstNonNull(
                override != null ? override.getDefaultLeaseSize() : null,
                defaultLeaseSize,
                CreditLeaseDefaults.LEASE_SIZE);
        Double resolvedWaterMark = firstNonNull(
                override != null ? override.getLowWaterMark() : null, lowWaterMark, CreditLeaseDefaults.LOW_WATER_MARK);
        return new ResolvedLeaseConfig(leaseDuration, reservationTtl, leaseSize, resolvedWaterMark);
    }

    private static <T> T firstNonNull(T override, T configured, T fallback) {
        if (override != null) {
            return override;
        }
        return configured != null ? configured : fallback;
    }

    public static final class Builder {

        private CreditLeaseMode mode;
        private Duration defaultLeaseDuration;
        private Duration defaultReservationTtl;
        private Double defaultLeaseSize;
        private Double lowWaterMark;
        private Duration sweepInterval;
        private Duration prewarmResolveTimeout;
        private JedisPooled redisClient;
        private String redisKeyPrefix;
        private final Map<String, CreditLeaseOverride> overrides = new LinkedHashMap<>();

        /** Where the credit hold lives. Defaults to {@link CreditLeaseMode#AUTO}. */
        public Builder mode(CreditLeaseMode mode) {
            this.mode = mode;
            return this;
        }

        /** Lease lifetime requested at acquire and extend. Defaults to 5 minutes. */
        public Builder defaultLeaseDuration(Duration defaultLeaseDuration) {
            this.defaultLeaseDuration = defaultLeaseDuration;
            return this;
        }

        /**
         * Reservation lifetime. Defaults to 60 seconds. In server mode it is capped at an hour
         * less a minute of room for clock skew, since an hour out is the furthest the API will
         * hold credits and it measures that against its own clock. Size it above the longest
         * expected gap between a check and its settle: a settle arriving after the TTL still
         * bills the server but no longer re-debits the local lease, so the local balance reads
         * high until the lease rolls over.
         */
        public Builder defaultReservationTtl(Duration defaultReservationTtl) {
            this.defaultReservationTtl = defaultReservationTtl;
            return this;
        }

        /** Credits requested per acquire, and the minimum extend tranche. Defaults to 10000. */
        public Builder defaultLeaseSize(double defaultLeaseSize) {
            this.defaultLeaseSize = defaultLeaseSize;
            return this;
        }

        /**
         * Fraction of the lease below which the SDK kicks off a background extend. Defaults to
         * 0.25.
         */
        public Builder lowWaterMark(double lowWaterMark) {
            this.lowWaterMark = lowWaterMark;
            return this;
        }

        /** Expired-reservation sweep cadence. Defaults to 1 second. */
        public Builder sweepInterval(Duration sweepInterval) {
            this.sweepInterval = sweepInterval;
            return this;
        }

        /**
         * How long a prewarm waits for a freshly identified company to surface in the datastream
         * cache. Zero skips the wait, so a prewarm gives up unless the company is already cached.
         * Defaults to 5 seconds.
         */
        public Builder prewarmResolveTimeout(Duration prewarmResolveTimeout) {
            this.prewarmResolveTimeout = prewarmResolveTimeout;
            return this;
        }

        /**
         * A pre-connected Redis client for lease and reservation state. Optional: without one the
         * SDK reuses the datastream cache's Redis client, so an existing Redis setup backs leases
         * automatically. Set this only to point lease state at a different Redis.
         *
         * <p>With no Redis at all the SDK falls back to per-process in-memory stores, which gate
         * one process only, and says so in a warning.
         */
        public Builder redisClient(JedisPooled redisClient) {
            this.redisClient = redisClient;
            return this;
        }

        /** Key prefix for lease state. Falls back to the datastream cache's, then {@code schematic:}. */
        public Builder redisKeyPrefix(String redisKeyPrefix) {
            this.redisKeyPrefix = redisKeyPrefix;
            return this;
        }

        /** Overrides the resolvable knobs for one credit type. */
        public Builder override(String creditTypeId, CreditLeaseOverride override) {
            this.overrides.put(creditTypeId, override);
            return this;
        }

        public Builder overrides(Map<String, CreditLeaseOverride> overrides) {
            if (overrides != null) {
                this.overrides.putAll(overrides);
            }
            return this;
        }

        public CreditLeaseConfig build() {
            // Caught here rather than at the first check: a lease sized NaN or a water mark above
            // one turns every later comparison into a silent no-op, and the symptom surfaces as
            // checks that never gate rather than as the misconfiguration it is.
            positiveAmount(defaultLeaseSize, "defaultLeaseSize");
            fraction(lowWaterMark, "lowWaterMark");
            positiveDuration(defaultLeaseDuration, "defaultLeaseDuration");
            positiveDuration(defaultReservationTtl, "defaultReservationTtl");
            positiveDuration(sweepInterval, "sweepInterval");
            for (Map.Entry<String, CreditLeaseOverride> entry : overrides.entrySet()) {
                CreditLeaseOverride override = entry.getValue();
                if (override == null) {
                    continue;
                }
                String where = " for credit type " + entry.getKey();
                positiveAmount(override.getDefaultLeaseSize(), "defaultLeaseSize" + where);
                fraction(override.getLowWaterMark(), "lowWaterMark" + where);
                positiveDuration(override.getDefaultLeaseDuration(), "defaultLeaseDuration" + where);
                positiveDuration(override.getDefaultReservationTtl(), "defaultReservationTtl" + where);
            }
            return new CreditLeaseConfig(this);
        }

        private static void positiveAmount(Double value, String name) {
            if (value == null) {
                return;
            }
            if (Double.isNaN(value) || Double.isInfinite(value) || value <= 0) {
                throw new IllegalArgumentException(name + " must be a positive finite number, got " + value);
            }
        }

        private static void fraction(Double value, String name) {
            if (value == null) {
                return;
            }
            if (Double.isNaN(value) || value <= 0 || value >= 1) {
                throw new IllegalArgumentException(
                        name + " must be a fraction between 0 and 1, exclusive, got " + value);
            }
        }

        private static void positiveDuration(Duration value, String name) {
            if (value == null) {
                return;
            }
            if (value.isZero() || value.isNegative()) {
                throw new IllegalArgumentException(name + " must be a positive duration, got " + value);
            }
        }
    }
}
