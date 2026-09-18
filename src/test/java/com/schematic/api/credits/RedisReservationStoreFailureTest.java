package com.schematic.api.credits;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.Clock;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
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

    /**
     * The SDKs write the check's request body as the eval context, so a sibling can put fields
     * beside company and user. Losing the entity keys over one of them would leave a recovered
     * hold with nothing to attribute its usage to.
     */
    @Test
    void anEvalContextWithExtraFieldsStillYieldsTheEntityKeys() {
        Map<String, String> raw = new HashMap<>();
        raw.put("id", "res_1");
        raw.put("leaseId", "lse_1");
        raw.put("companyId", "comp_1");
        raw.put("creditTypeId", "tokens");
        raw.put("eventSubtype", "inference_tokens");
        raw.put("quantityReserved", "2");
        raw.put("creditsReserved", "4");
        raw.put("consumptionRate", "2");
        raw.put("expiresAt", "1767225600000");
        raw.put(
                "evalCtx",
                "{\"company\":{\"id\":\"comp_1\"},\"user\":{\"user_id\":\"u_1\"},"
                        + "\"preflight\":{\"event_usage\":{\"event_subtype\":\"inference_tokens\",\"quantity\":2}}}");
        when(jedis.hgetAll(anyString())).thenReturn(raw);

        Reservation reservation = store().get("res_1");

        assertEquals(Collections.singletonMap("id", "comp_1"), reservation.getCompany());
        assertEquals(Collections.singletonMap("user_id", "u_1"), reservation.getUser());
    }

    /** Neither read touches the lease store, so any call through this one is a test failure. */
    private static ReservationRefunder refuserRefunder() {
        return (companyId, creditTypeId, credits, pinLeaseId) -> {
            throw new AssertionError("a display-path read must not refund");
        };
    }
}
