package com.schematic.api.credits.conformance;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;

/** A clock that only moves when a test moves it, so nothing depends on wall time. */
final class MutableClock extends Clock {

    private volatile Instant now;

    MutableClock(Instant start) {
        this.now = start;
    }

    @Override
    public ZoneId getZone() {
        return ZoneOffset.UTC;
    }

    @Override
    public Clock withZone(ZoneId zone) {
        return this;
    }

    @Override
    public Instant instant() {
        return now;
    }

    void advance(long millis) {
        now = now.plusMillis(millis);
    }
}
