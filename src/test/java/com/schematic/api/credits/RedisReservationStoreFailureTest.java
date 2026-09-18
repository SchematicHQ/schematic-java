package com.schematic.api.credits;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.Clock;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import redis.clients.jedis.JedisPooled;

@ExtendWith(MockitoExtension.class)
class RedisReservationStoreFailureTest {

    @Mock
    private JedisPooled jedis;

    private RedisReservationStore store() {
        return new RedisReservationStore(jedis, refuserRefunder(), "test:", Clock.systemUTC());
    }

    @Test
    void aRedisFailureReadsAsNothingReserved() {
        when(jedis.hgetAll(anyString())).thenThrow(new RuntimeException("redis is down"));

        assertEquals(0.0, store().reservedCredits("comp-1", "tokens"), 0);
    }

    @Test
    void aRedisFailureReadsAsNoOpenReservations() {
        when(jedis.zcard(anyString())).thenThrow(new RuntimeException("redis is down"));

        assertEquals(0, store().count());
    }

    /** Neither read touches the lease store, so any call through this one is a test failure. */
    private static ReservationRefunder refuserRefunder() {
        return (companyId, creditTypeId, credits, pinLeaseId) -> {
            throw new AssertionError("a display-path read must not refund");
        };
    }
}
