package com.schematic.api.credits.conformance;

import com.schematic.api.credits.InMemoryLeaseStore;
import com.schematic.api.credits.InMemoryReservationStore;
import com.schematic.api.credits.LeaseStore;
import com.schematic.api.credits.RedisLeaseStore;
import com.schematic.api.credits.RedisReservationStore;
import com.schematic.api.credits.ReservationStore;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import redis.clients.jedis.JedisPooled;

/** One store pair plus the seams a vector needs to drive it. */
final class Backend {

    /** The backend names the vectors use in their {@code backends} field. */
    static final String IN_MEMORY = "in_memory";

    static final String REDIS = "redis";

    /** The fixed instant the in-memory timeline starts from. */
    private static final Instant T0 =
            ZonedDateTime.of(2026, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC).toInstant();

    private static final String TEST_KEY_PREFIX = "schematic:";

    final String name;
    final VectorClock clock;
    final LeaseStore leases;
    final ReservationStore reservations;
    final CrashingRefundLeaseStore crash;

    private Backend(
            String name,
            VectorClock clock,
            LeaseStore leases,
            ReservationStore reservations,
            CrashingRefundLeaseStore crash) {
        this.name = name;
        this.clock = clock;
        this.leases = leases;
        this.reservations = reservations;
        this.crash = crash;
    }

    static Backend create(String name) {
        if (IN_MEMORY.equals(name)) {
            MutableClock clock = new MutableClock(T0);
            VectorClock vectorClock = new VectorClock() {
                @Override
                public java.time.Clock clock() {
                    return clock;
                }

                @Override
                public void advance(long millis) {
                    clock.advance(millis);
                }

                @Override
                public Instant at(double offsetMillis) {
                    return T0.plusMillis((long) offsetMillis);
                }
            };
            InMemoryLeaseStore leases = new InMemoryLeaseStore(clock);
            CrashingRefundLeaseStore crash = new CrashingRefundLeaseStore(leases);
            return new Backend(name, vectorClock, leases, new InMemoryReservationStore(crash, clock), crash);
        }
        if (REDIS.equals(name)) {
            JedisPooled jedis = EmbeddedRedis.client();
            jedis.flushAll();
            RedisVectorClock clock = new RedisVectorClock(jedis, TEST_KEY_PREFIX);
            RedisLeaseStore leases = new RedisLeaseStore(jedis, TEST_KEY_PREFIX, null, clock.clock());
            CrashingRefundLeaseStore crash = new CrashingRefundLeaseStore(leases);
            return new Backend(
                    name,
                    clock,
                    leases,
                    new RedisReservationStore(jedis, crash, TEST_KEY_PREFIX, clock.clock()),
                    crash);
        }
        throw new IllegalArgumentException("unknown conformance backend: " + name);
    }
}
