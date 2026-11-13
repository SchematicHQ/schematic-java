package com.schematic.api.datastream;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.schematic.api.cache.RedisCacheConfig;
import com.schematic.api.datastream.DataStreamMessages.DataStreamResp;
import com.schematic.api.datastream.DataStreamMessages.EntityType;
import com.schematic.api.datastream.DataStreamMessages.MessageType;
import com.schematic.api.types.EventBodyTrack;
import com.schematic.api.types.RulesengineCompany;
import java.io.IOException;
import java.net.ServerSocket;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import redis.clients.jedis.JedisPooled;
import redis.embedded.RedisServer;

/**
 * In replicator mode the replicator owns the company keys and sets their lifetime (no expiry by
 * default). The optimistic metric bump in {@code track} rewrites those keys, and must keep each
 * key's TTL rather than apply the SDK's cache TTL.
 */
class TrackKeepTtlTest {

    private static final String UNREACHABLE = "http://127.0.0.1:1";
    private static final String KEY_PREFIX = "keepttl-test:";
    private static final Map<String, String> COMPANY_KEYS = Collections.singletonMap("customer_id", "cust-1");

    private static RedisServer server;
    private static JedisPooled jedis;
    private static int port;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private DataStreamClient client;

    @BeforeAll
    static void startRedis() throws IOException {
        try (ServerSocket socket = new ServerSocket(0)) {
            port = socket.getLocalPort();
        }
        server = RedisServer.newRedisServer().port(port).build();
        server.start();
        jedis = new JedisPooled("localhost", port);
    }

    @AfterAll
    static void stopRedis() throws IOException {
        if (jedis != null) {
            jedis.close();
        }
        if (server != null) {
            server.stop();
        }
    }

    @BeforeEach
    void flush() {
        jedis.flushAll();
    }

    @AfterEach
    void tearDown() {
        if (client != null) {
            client.close();
        }
    }

    @Test
    void replicatorMode_bumpKeepsNoExpiry() {
        client = buildClient(true);
        List<String> keys = seedCompany();
        assertEquals(2, keys.size());
        for (String key : keys) {
            jedis.persist(key);
        }

        client.updateCompanyMetrics(track(5));

        assertEquals(
                15, client.getCachedCompany(COMPANY_KEYS).getMetrics().get(0).getValue());
        for (String key : keys) {
            assertEquals(-1, jedis.ttl(key), key + " should still have no expiry");
        }
    }

    @Test
    void replicatorMode_bumpKeepsExistingTtl() {
        client = buildClient(true);
        List<String> keys = seedCompany();
        for (String key : keys) {
            jedis.expire(key, 600);
        }

        client.updateCompanyMetrics(track(5));

        assertEquals(
                15, client.getCachedCompany(COMPANY_KEYS).getMetrics().get(0).getValue());
        for (String key : keys) {
            long ttl = jedis.ttl(key);
            assertTrue(ttl > 0 && ttl <= 600, key + " TTL was " + ttl);
        }
    }

    @Test
    void directMode_bumpAppliesSdkTtl() {
        client = buildClient(false);
        List<String> keys = seedCompany();
        for (String key : keys) {
            jedis.persist(key);
        }

        client.updateCompanyMetrics(track(5));

        assertEquals(
                15, client.getCachedCompany(COMPANY_KEYS).getMetrics().get(0).getValue());
        for (String key : keys) {
            long ttl = jedis.ttl(key);
            assertTrue(ttl > 600 && ttl <= Duration.ofHours(1).getSeconds(), key + " TTL was " + ttl);
        }
    }

    private DataStreamClient buildClient(boolean replicatorMode) {
        DatastreamOptions.Builder builder = DatastreamOptions.builder()
                .cacheTTL(Duration.ofHours(1))
                .redisCache(RedisCacheConfig.builder()
                        .endpoint("localhost:" + port)
                        .keyPrefix(KEY_PREFIX)
                        .build());
        if (replicatorMode) {
            builder.withReplicatorMode(UNREACHABLE + "/ready");
        }
        return new DataStreamClient(builder.build(), "test_api_key", UNREACHABLE, null);
    }

    /** Caches the company as a datastream message would and returns its Redis keys. */
    private List<String> seedCompany() {
        client.handleMessage(companyResp("comp-1", "api_calls", 10));
        RulesengineCompany cached = client.getCachedCompany(COMPANY_KEYS);
        assertNotNull(cached);
        return new ArrayList<>(jedis.keys(KEY_PREFIX + "*"));
    }

    private EventBodyTrack track(long quantity) {
        return EventBodyTrack.builder()
                .event("api_calls")
                .company(COMPANY_KEYS)
                .quantity(quantity)
                .build();
    }

    private DataStreamResp companyResp(String id, String eventSubtype, long value) {
        ObjectNode node = objectMapper.createObjectNode();
        node.put("id", id);
        node.put("account_id", "acc_1");
        node.put("environment_id", "env_1");
        ObjectNode keys = objectMapper.createObjectNode();
        for (Map.Entry<String, String> entry : COMPANY_KEYS.entrySet()) {
            keys.put(entry.getKey(), entry.getValue());
        }
        node.set("keys", keys);
        node.set("traits", objectMapper.createArrayNode());
        ArrayNode metrics = objectMapper.createArrayNode();
        ObjectNode metric = objectMapper.createObjectNode();
        metric.put("account_id", "acc_1");
        metric.put("company_id", id);
        metric.put("environment_id", "env_1");
        metric.put("event_subtype", eventSubtype);
        metric.put("period", "current_month");
        metric.put("month_reset", "first");
        metric.put("value", value);
        metric.put("created_at", "2026-01-01T00:00:00Z");
        metrics.add(metric);
        node.set("metrics", metrics);
        node.set("rules", objectMapper.createArrayNode());
        node.set("billing_product_ids", objectMapper.createArrayNode());
        node.set("credit_balances", objectMapper.createObjectNode());
        node.set("plan_ids", objectMapper.createArrayNode());
        node.set("plan_version_ids", objectMapper.createArrayNode());

        ObjectNode respNode = objectMapper.createObjectNode();
        respNode.put("entity_type", EntityType.COMPANY.getValue());
        respNode.put("message_type", MessageType.FULL.getValue());
        respNode.put("entity_id", id);
        respNode.set("data", node);
        return objectMapper.convertValue(respNode, DataStreamResp.class);
    }
}
