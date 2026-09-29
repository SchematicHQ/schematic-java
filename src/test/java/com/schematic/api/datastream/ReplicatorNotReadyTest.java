package com.schematic.api.datastream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.schematic.api.Schematic;
import com.schematic.api.cache.LocalCache;
import com.schematic.api.datastream.DataStreamMessages.DataStreamResp;
import com.schematic.api.datastream.DataStreamMessages.EntityType;
import com.schematic.api.datastream.DataStreamMessages.MessageType;
import com.schematic.api.logger.SchematicLogger;
import com.schematic.api.resources.features.FeaturesClient;
import com.schematic.api.types.RulesengineCheckFlagResult;
import com.schematic.api.types.RulesengineFlag;
import com.sun.net.httpserver.HttpServer;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Replicator mode when the replicator reports {@code ready: false} (for example when it has
 * lost its connection to Schematic but still holds its cache). Flag checks should evaluate
 * from the cache rather than falling back to the API.
 */
@ExtendWith(MockitoExtension.class)
class ReplicatorNotReadyTest {

    // Nothing listens here, so every health check fails and the replicator stays not ready.
    private static final String UNREACHABLE_HEALTH_URL = "http://127.0.0.1:1/ready";

    @Mock
    private SchematicLogger logger;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private Schematic schematic;
    private HttpServer server;

    @BeforeEach
    void setUp() {
        schematic = Schematic.builder()
                .apiKey("test_api_key")
                .logger(logger)
                .basePath("http://127.0.0.1:1")
                .eventCaptureBaseUrl("http://127.0.0.1:1")
                .datastreamOptions(DatastreamOptions.builder()
                        .withReplicatorMode(UNREACHABLE_HEALTH_URL)
                        .replicatorHealthCheckInterval(Duration.ofHours(1))
                        .build())
                .build();
    }

    @AfterEach
    void tearDown() {
        if (schematic != null) {
            schematic.close();
        }
        if (server != null) {
            server.stop(0);
        }
    }

    @Test
    void checkFlag_replicatorNotReady_evaluatesFromCache() {
        DataStreamClient ds = schematic.getDataStreamClient();
        ds.handleMessage(flagResp("cached-flag", true));
        ds.handleMessage(companyResp("comp-1", "customer_id", "cust-1"));

        assertTrue(schematic.isReplicatorMode());
        assertFalse(schematic.isDatastreamConnected());

        Schematic spySchematic = spy(schematic);
        FeaturesClient featuresClient = mock(FeaturesClient.class);
        lenient().when(spySchematic.features()).thenReturn(featuresClient);

        Map<String, String> company = Collections.singletonMap("customer_id", "cust-1");
        RulesengineCheckFlagResult result = spySchematic.checkFlagWithEntitlement("cached-flag", company, null);

        assertEquals("cached-flag", result.getFlagKey());
        assertTrue(result.getValue());
        assertEquals("comp-1", result.getCompanyId().orElse(null));
        assertTrue(spySchematic.checkFlag("cached-flag", company, null));
        verifyNoInteractions(featuresClient);
    }

    @Test
    void checkFlags_replicatorNotReady_evaluatesFromCache() {
        DataStreamClient ds = schematic.getDataStreamClient();
        ds.handleMessage(flagResp("flag-on", true));
        ds.handleMessage(flagResp("flag-off", false));
        ds.handleMessage(companyResp("comp-1", "customer_id", "cust-1"));

        assertFalse(schematic.isDatastreamConnected());

        Schematic spySchematic = spy(schematic);
        FeaturesClient featuresClient = mock(FeaturesClient.class);
        lenient().when(spySchematic.features()).thenReturn(featuresClient);

        List<RulesengineCheckFlagResult> results = spySchematic.checkFlags(
                Arrays.asList("flag-on", "flag-off"), Collections.singletonMap("customer_id", "cust-1"), null);

        assertEquals(2, results.size());
        assertEquals("flag-on", results.get(0).getFlagKey());
        assertTrue(results.get(0).getValue());
        assertEquals("flag-off", results.get(1).getFlagKey());
        assertFalse(results.get(1).getValue());
        verifyNoInteractions(featuresClient);
    }

    @Test
    void checkReplicatorHealth_notReady503_stillAdoptsCacheVersion() throws Exception {
        server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
        server.createContext("/ready", exchange -> {
            byte[] bytes =
                    "{\"ready\":false,\"connected\":false,\"cache_version\":\"v42\"}".getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().add("Content-Type", "application/json");
            exchange.sendResponseHeaders(503, bytes.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(bytes);
            }
        });
        server.start();

        LocalCache<RulesengineFlag> flagCache = new LocalCache<>();
        DatastreamOptions options = DatastreamOptions.builder()
                .withReplicatorMode("http://127.0.0.1:" + server.getAddress().getPort() + "/ready")
                .flagCacheProvider(flagCache)
                .build();
        DataStreamClient client = new DataStreamClient(options, "test-key", "https://api.schematichq.com", logger);
        try {
            client.checkReplicatorHealth();
            assertFalse(client.isConnected());

            // Entries are written and read under the replicator's cache version, even though
            // the replicator reported not ready.
            client.handleMessage(flagResp("versioned-flag", true));
            assertNotNull(flagCache.get("flags:v42:versioned-flag"));
            assertTrue(client.checkFlag("versioned-flag", null, null).getValue());
        } finally {
            client.close();
        }
    }

    private DataStreamResp flagResp(String key, boolean defaultValue) {
        ObjectNode node = objectMapper.createObjectNode();
        node.put("key", key);
        node.put("id", "flag_" + key);
        node.put("account_id", "acc_1");
        node.put("environment_id", "env_1");
        node.put("default_value", defaultValue);
        node.set("rules", objectMapper.createArrayNode());
        return buildResp(EntityType.FLAG.getValue(), null, node);
    }

    private DataStreamResp companyResp(String id, String keyName, String keyValue) {
        ObjectNode node = objectMapper.createObjectNode();
        node.put("id", id);
        node.put("account_id", "acc_1");
        node.put("environment_id", "env_1");
        ObjectNode keys = objectMapper.createObjectNode();
        keys.put(keyName, keyValue);
        node.set("keys", keys);
        node.set("traits", objectMapper.createArrayNode());
        node.set("metrics", objectMapper.createArrayNode());
        node.set("rules", objectMapper.createArrayNode());
        node.set("billing_product_ids", objectMapper.createArrayNode());
        node.set("credit_balances", objectMapper.createObjectNode());
        node.set("plan_ids", objectMapper.createArrayNode());
        node.set("plan_version_ids", objectMapper.createArrayNode());
        return buildResp(EntityType.COMPANY.getValue(), id, node);
    }

    private DataStreamResp buildResp(String entityType, String entityId, JsonNode data) {
        ObjectNode respNode = objectMapper.createObjectNode();
        respNode.put("entity_type", entityType);
        respNode.put("message_type", MessageType.FULL.getValue());
        if (entityId != null) {
            respNode.put("entity_id", entityId);
        }
        respNode.set("data", data);
        return objectMapper.convertValue(respNode, DataStreamResp.class);
    }
}
