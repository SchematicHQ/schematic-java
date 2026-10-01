package com.schematic.api.credits.conformance;

import java.time.Clock;
import java.time.Instant;

/**
 * The clock a vector runs against. Vectors express every {@code *_at_ms} field as an absolute
 * offset from a fixed start instant, and only {@code advance_clock} moves time.
 */
interface VectorClock {

    /** The clock the stores and the manager read. */
    Clock clock();

    /** Moves the vector's timeline forward. */
    void advance(long millis);

    /** An absolute position on the vector's timeline. */
    Instant at(double offsetMillis);
}
