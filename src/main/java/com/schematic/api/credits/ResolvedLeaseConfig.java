package com.schematic.api.credits;

import java.time.Duration;

/** The lease knobs for one credit type, after the credit type's override and the defaults. */
public final class ResolvedLeaseConfig {

    private final Duration leaseDuration;
    private final Duration reservationTtl;
    private final double leaseSize;
    private final double lowWaterMark;

    public ResolvedLeaseConfig(Duration leaseDuration, Duration reservationTtl, double leaseSize, double lowWaterMark) {
        this.leaseDuration = leaseDuration;
        this.reservationTtl = reservationTtl;
        this.leaseSize = leaseSize;
        this.lowWaterMark = lowWaterMark;
    }

    public Duration getLeaseDuration() {
        return leaseDuration;
    }

    public Duration getReservationTtl() {
        return reservationTtl;
    }

    public double getLeaseSize() {
        return leaseSize;
    }

    public double getLowWaterMark() {
        return lowWaterMark;
    }
}
