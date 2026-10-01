package com.schematic.api.credits.conformance;

import java.time.Clock;
import java.time.Instant;
import java.util.List;
import java.util.Set;
import redis.clients.jedis.JedisPooled;
import redis.clients.jedis.resps.Tuple;

/**
 * The vector timeline against a real Redis.
 *
 * <p>The lease scripts decide expiry against the Redis server's own clock, and a real server's
 * clock cannot be moved, so this moves the data instead: advancing the timeline by N milliseconds
 * brings every stored deadline N milliseconds nearer, which is the same comparison from the other
 * side. The SDK-side clock stays the system clock, so both sides read one frame. Vector instants
 * are translated into that frame by {@link #at}.
 */
final class RedisVectorClock implements VectorClock {

    private final JedisPooled jedis;
    private final String keyPrefix;
    private final Instant start = Instant.now();
    private long shiftedMillis;

    RedisVectorClock(JedisPooled jedis, String keyPrefix) {
        this.jedis = jedis;
        this.keyPrefix = keyPrefix;
    }

    @Override
    public Clock clock() {
        return Clock.systemUTC();
    }

    @Override
    public void advance(long millis) {
        shiftedMillis += millis;
        shiftHashDeadlines(keyPrefix + "credit-lease:*", millis);
        shiftHashDeadlines(keyPrefix + "credit-reservation:*", millis);
        shiftIndexScores(keyPrefix + "credit-reservations:byExpiry", millis);
    }

    @Override
    public Instant at(double offsetMillis) {
        return start.plusMillis((long) offsetMillis - shiftedMillis);
    }

    private void shiftHashDeadlines(String pattern, long millis) {
        Set<String> keys = jedis.keys(pattern);
        for (String key : keys) {
            String raw = jedis.hget(key, "expiresAt");
            if (raw != null) {
                jedis.hset(key, "expiresAt", Long.toString(Long.parseLong(raw) - millis));
            }
            long pttl = jedis.pttl(key);
            if (pttl <= 0) {
                continue;
            }
            // Past its grace window the row is one Redis would already have evicted.
            if (pttl - millis <= 0) {
                jedis.del(key);
            } else {
                jedis.pexpire(key, pttl - millis);
            }
        }
    }

    private void shiftIndexScores(String key, long millis) {
        List<Tuple> members = jedis.zrangeWithScores(key, 0, -1);
        for (Tuple member : members) {
            jedis.zadd(key, member.getScore() - millis, member.getElement());
        }
    }
}
