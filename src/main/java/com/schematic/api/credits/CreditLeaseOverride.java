package com.schematic.api.credits;

import java.time.Duration;

/**
 * Overrides the four resolvable lease knobs for one credit type. An unset field leaves the
 * client-wide value in place.
 */
public final class CreditLeaseOverride {

    private final Duration defaultLeaseDuration;
    private final Duration defaultReservationTtl;
    private final Double defaultLeaseSize;
    private final Double lowWaterMark;

    private CreditLeaseOverride(Builder builder) {
        this.defaultLeaseDuration = builder.defaultLeaseDuration;
        this.defaultReservationTtl = builder.defaultReservationTtl;
        this.defaultLeaseSize = builder.defaultLeaseSize;
        this.lowWaterMark = builder.lowWaterMark;
    }

    public static Builder builder() {
        return new Builder();
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

    public static final class Builder {

        private Duration defaultLeaseDuration;
        private Duration defaultReservationTtl;
        private Double defaultLeaseSize;
        private Double lowWaterMark;

        public Builder defaultLeaseDuration(Duration defaultLeaseDuration) {
            this.defaultLeaseDuration = defaultLeaseDuration;
            return this;
        }

        public Builder defaultReservationTtl(Duration defaultReservationTtl) {
            this.defaultReservationTtl = defaultReservationTtl;
            return this;
        }

        public Builder defaultLeaseSize(double defaultLeaseSize) {
            this.defaultLeaseSize = defaultLeaseSize;
            return this;
        }

        public Builder lowWaterMark(double lowWaterMark) {
            this.lowWaterMark = lowWaterMark;
            return this;
        }

        public CreditLeaseOverride build() {
            return new CreditLeaseOverride(this);
        }
    }
}
