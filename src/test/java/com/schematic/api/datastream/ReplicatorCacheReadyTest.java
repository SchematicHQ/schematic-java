package com.schematic.api.datastream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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
import com.schematic.api.resources.features.types.CheckFlagResponse;
import com.schematic.api.resources.features.types.CheckFlagsResponse;
import com.schematic.api.types.CheckFlagRequestBody;
import com.schematic.api.types.CheckFlagResponseData;
import com.schematic.api.types.CheckFlagsResponseData;
import com.schematic.api.types.RulesengineCheckFlagResult;
import com.schematic.api.types.RulesengineFlag;
import com.sun.net.httpserver.HttpServer;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Replicator mode serves flag checks from the cache only once the replicator reports ready.
 * Before that, single and bulk flag checks both skip the cache and use the API.
 */
@ExtendWith(MockitoExtension.class)
class ReplicatorCacheReadyTest {

    private static final String CACHE_VERSION = "v1";
    private static final Map<String, String> COMPANY = Collections.singletonMap("customer_id", "cust-1");
    private static final List<String> KEYS = Arrays.asList("flag-on", "flag-off");

    @Mock
    private SchematicLogger logger;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final AtomicInteger healthStatus = new AtomicInteger(200);
    private final AtomicReference<String> healthBody = new AtomicReference<>("{}");
    private HttpServer server;
    private String healthUrl;
    private LocalCache<RulesengineFlag> flagCache;
    private Schematic schematic;

    @BeforeEach
    void setUp() throws Exception {
        server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
        server.createContext("/ready", exchange -> {
            byte[] bytes = healthBody.get().getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().add("Content-Type", "application/json");
            exchange.sendResponseHeaders(healthStatus.get(), bytes.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(bytes);
            }
        });
        server.start();
        healthUrl = "http://127.0.0.1:" + server.getAddress().getPort() + "/ready";
        flagCache = new LocalCache<>();
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

    // --- Flag checks, cache not ready ---

    @Test
    void notReady_singleAndBulkSkipCacheAndUseApi() {
        setHealth(503, false, CACHE_VERSION);
        Schematic spySchematic = spy(buildSchematic(Collections.emptyMap()));
        assertFalse(spySchematic.isCacheReady());
        assertEquals(CACHE_VERSION, schematic.getDataStreamClient().getReplicatorCacheVersion());
        seedReplicatorCache(schematic.getDataStreamClient());

        // The API answers the opposite of the cache, so each result shows which one was read.
        FeaturesClient featuresClient = mock(FeaturesClient.class);
        when(spySchematic.features()).thenReturn(featuresClient);
        when(featuresClient.checkFlag(eq("flag-on"), any(CheckFlagRequestBody.class)))
                .thenReturn(singleApiResponse("flag-on", false));
        when(featuresClient.checkFlag(eq("flag-off"), any(CheckFlagRequestBody.class)))
                .thenReturn(singleApiResponse("flag-off", true));
        when(featuresClient.checkFlags(any(CheckFlagRequestBody.class)))
                .thenReturn(bulkApiResponse(Arrays.asList("flag-on", "flag-off"), Arrays.asList(false, true)));

        RulesengineCheckFlagResult singleOn = spySchematic.checkFlagWithEntitlement("flag-on", COMPANY, null);
        RulesengineCheckFlagResult singleOff = spySchematic.checkFlagWithEntitlement("flag-off", COMPANY, null);
        List<RulesengineCheckFlagResult> bulk = spySchematic.checkFlags(KEYS, COMPANY, null);

        assertFalse(singleOn.getValue());
        assertTrue(singleOff.getValue());
        assertEquals("api", singleOn.getReason());
        assertEquals(2, bulk.size());
        assertEquals("flag-on", bulk.get(0).getFlagKey());
        assertFalse(bulk.get(0).getValue());
        assertEquals("flag-off", bulk.get(1).getFlagKey());
        assertTrue(bulk.get(1).getValue());
        assertEquals("api", bulk.get(0).getReason());

        verify(featuresClient).checkFlag(eq("flag-on"), any(CheckFlagRequestBody.class));
        verify(featuresClient).checkFlag(eq("flag-off"), any(CheckFlagRequestBody.class));
        verify(featuresClient).checkFlags(any(CheckFlagRequestBody.class));
    }

    @Test
    void notReady_apiFails_singleAndBulkReturnFlagDefaults() {
        setHealth(503, false, CACHE_VERSION);
        // SDK flag defaults are the opposite of the cached flags' default values, so a
        // result read from the cache would not match.
        Map<String, Boolean> flagDefaults = new HashMap<>();
        flagDefaults.put("flag-on", false);
        flagDefaults.put("flag-off", true);
        Schematic spySchematic = spy(buildSchematic(flagDefaults));
        assertFalse(spySchematic.isCacheReady());
        seedReplicatorCache(schematic.getDataStreamClient());

        FeaturesClient featuresClient = mock(FeaturesClient.class);
        when(spySchematic.features()).thenReturn(featuresClient);
        when(featuresClient.checkFlag(any(String.class), any(CheckFlagRequestBody.class)))
                .thenThrow(new RuntimeException("API unavailable"));
        when(featuresClient.checkFlags(any(CheckFlagRequestBody.class)))
                .thenThrow(new RuntimeException("API unavailable"));

        assertFalse(spySchematic.checkFlag("flag-on", COMPANY, null));
        assertTrue(spySchematic.checkFlag("flag-off", COMPANY, null));

        List<RulesengineCheckFlagResult> bulk = spySchematic.checkFlags(KEYS, COMPANY, null);
        assertEquals(2, bulk.size());
        assertFalse(bulk.get(0).getValue());
        assertTrue(bulk.get(1).getValue());
    }

    // --- Flag checks, cache ready ---

    @Test
    void ready_singleAndBulkEvaluateFromCacheWithoutApi() {
        setHealth(200, true, CACHE_VERSION);
        Schematic spySchematic = spy(buildSchematic(Collections.emptyMap()));
        assertTrue(spySchematic.isCacheReady());
        seedReplicatorCache(schematic.getDataStreamClient());

        FeaturesClient featuresClient = mock(FeaturesClient.class);
        lenient().when(spySchematic.features()).thenReturn(featuresClient);

        RulesengineCheckFlagResult singleOn = spySchematic.checkFlagWithEntitlement("flag-on", COMPANY, null);
        RulesengineCheckFlagResult singleOff = spySchematic.checkFlagWithEntitlement("flag-off", COMPANY, null);
        List<RulesengineCheckFlagResult> bulk = spySchematic.checkFlags(KEYS, COMPANY, null);

        assertTrue(singleOn.getValue());
        assertFalse(singleOff.getValue());
        assertEquals("flag_flag-on", singleOn.getFlagId().orElse(null));
        assertEquals("comp-1", singleOn.getCompanyId().orElse(null));

        assertEquals(2, bulk.size());
        assertSameEvaluation(singleOn, bulk.get(0));
        assertSameEvaluation(singleOff, bulk.get(1));

        verifyNoInteractions(featuresClient);
    }

    @Test
    void ready_flagMissingFromCache_singleAndBulkFallBackToApi() {
        setHealth(200, true, CACHE_VERSION);
        Schematic spySchematic = spy(buildSchematic(Collections.emptyMap()));
        assertTrue(spySchematic.isCacheReady());
        seedReplicatorCache(schematic.getDataStreamClient());

        FeaturesClient featuresClient = mock(FeaturesClient.class);
        when(spySchematic.features()).thenReturn(featuresClient);
        when(featuresClient.checkFlag(eq("uncached"), any(CheckFlagRequestBody.class)))
                .thenReturn(singleApiResponse("uncached", true));
        when(featuresClient.checkFlags(any(CheckFlagRequestBody.class)))
                .thenReturn(bulkApiResponse(Arrays.asList("flag-on", "uncached"), Arrays.asList(true, true)));

        assertTrue(spySchematic.checkFlag("uncached", COMPANY, null));
        List<RulesengineCheckFlagResult> bulk =
                spySchematic.checkFlags(Arrays.asList("flag-on", "uncached"), COMPANY, null);
        assertEquals(2, bulk.size());
        assertTrue(bulk.get(1).getValue());
        assertEquals("api", bulk.get(1).getReason());

        verify(featuresClient).checkFlag(eq("uncached"), any(CheckFlagRequestBody.class));
        verify(featuresClient).checkFlags(any(CheckFlagRequestBody.class));
    }

    // --- Health polling ---

    @Test
    void health503_setsNotReadyAndRecordsCacheVersion() {
        DataStreamClient client = newReplicatorClient();
        try {
            // A replicator that is still loading when the SDK starts.
            setHealth(503, false, "vX");
            client.checkReplicatorHealth();
            assertFalse(client.isCacheReady());
            assertFalse(client.isConnected());
            assertEquals("vX", client.getReplicatorCacheVersion());

            // Ready, then back to loading under a new cache version.
            setHealth(200, true, "vX");
            client.checkReplicatorHealth();
            assertTrue(client.isCacheReady());

            setHealth(503, false, "vY");
            client.checkReplicatorHealth();
            assertFalse(client.isCacheReady());
            assertEquals("vY", client.getReplicatorCacheVersion());
        } finally {
            client.close();
        }
    }

    @Test
    void healthUnreachable_setsNotReadyAndKeepsCacheVersion() {
        DataStreamClient client = newReplicatorClient();
        try {
            setHealth(200, true, CACHE_VERSION);
            client.checkReplicatorHealth();
            assertTrue(client.isCacheReady());
            assertEquals(CACHE_VERSION, client.getReplicatorCacheVersion());

            server.stop(0);
            server = null;
            client.checkReplicatorHealth();

            assertFalse(client.isCacheReady());
            assertEquals(CACHE_VERSION, client.getReplicatorCacheVersion());
        } finally {
            client.close();
        }
    }

    @Test
    void healthUnparseableBody_setsNotReadyAndKeepsCacheVersion() {
        DataStreamClient client = newReplicatorClient();
        try {
            setHealth(200, true, CACHE_VERSION);
            client.checkReplicatorHealth();
            assertTrue(client.isCacheReady());

            healthStatus.set(200);
            healthBody.set("not json");
            client.checkReplicatorHealth();

            assertFalse(client.isCacheReady());
            assertEquals(CACHE_VERSION, client.getReplicatorCacheVersion());
        } finally {
            client.close();
        }
    }

    @Test
    void healthWithoutCacheVersion_keepsCacheVersion() {
        DataStreamClient client = newReplicatorClient();
        try {
            setHealth(200, true, CACHE_VERSION);
            client.checkReplicatorHealth();

            healthStatus.set(200);
            healthBody.set("{\"ready\":true,\"cache_version\":\"\"}");
            client.checkReplicatorHealth();

            assertTrue(client.isCacheReady());
            assertEquals(CACHE_VERSION, client.getReplicatorCacheVersion());
        } finally {
            client.close();
        }
    }

    // --- Helpers ---

    private Schematic buildSchematic(Map<String, Boolean> flagDefaults) {
        schematic = Schematic.builder()
                .apiKey("test_api_key")
                .logger(logger)
                .basePath("http://127.0.0.1:1")
                .eventCaptureBaseUrl("http://127.0.0.1:1")
                .flagDefaults(new HashMap<>(flagDefaults))
                // No flag-check result cache, so every API-path check reaches the API mock.
                .cacheProviders(Collections.emptyList())
                .datastreamOptions(DatastreamOptions.builder()
                        .withReplicatorMode(healthUrl)
                        .replicatorHealthCheckInterval(Duration.ofHours(1))
                        .flagCacheProvider(flagCache)
                        .build())
                .build();
        // The client polls once on start in the background; poll again here so the state is
        // settled before the test continues. Both polls see the same response.
        schematic.getDataStreamClient().checkReplicatorHealth();
        return schematic;
    }

    private DataStreamClient newReplicatorClient() {
        DatastreamOptions options = DatastreamOptions.builder()
                .withReplicatorMode(healthUrl)
                .flagCacheProvider(flagCache)
                .build();
        return new DataStreamClient(options, "test-key", "https://api.schematichq.com", logger);
    }

    private void setHealth(int status, boolean ready, String cacheVersion) {
        healthStatus.set(status);
        healthBody.set("{\"ready\":" + ready + ",\"cache_version\":\"" + cacheVersion + "\"}");
    }

    /**
     * Seeds the cache the way the replicator lays it out: flags at
     * {@code flags:{cache_version}:{key}}, and the company at
     * {@code company:{cache_version}:{id}} with a key lookup at
     * {@code company:{cache_version}:{key}:{value}}.
     */
    private void seedReplicatorCache(DataStreamClient client) {
        flagCache.set("flags:" + CACHE_VERSION + ":flag-on", flag("flag-on", true));
        flagCache.set("flags:" + CACHE_VERSION + ":flag-off", flag("flag-off", false));
        client.handleMessage(companyResp("comp-1", "customer_id", "cust-1"));
        assertNotNull(client.getCachedCompany(COMPANY));
    }

    private static void assertSameEvaluation(RulesengineCheckFlagResult expected, RulesengineCheckFlagResult actual) {
        assertEquals(expected.getFlagKey(), actual.getFlagKey());
        assertEquals(expected.getValue(), actual.getValue());
        assertEquals(expected.getReason(), actual.getReason());
        assertEquals(expected.getFlagId(), actual.getFlagId());
        assertEquals(expected.getCompanyId(), actual.getCompanyId());
    }

    private static CheckFlagResponse singleApiResponse(String key, boolean value) {
        return CheckFlagResponse.builder()
                .data(CheckFlagResponseData.builder()
                        .flag(key)
                        .reason("api")
                        .value(value)
                        .build())
                .build();
    }

    private static CheckFlagsResponse bulkApiResponse(List<String> keys, List<Boolean> values) {
        CheckFlagResponseData[] flags = new CheckFlagResponseData[keys.size()];
        for (int i = 0; i < keys.size(); i++) {
            flags[i] = CheckFlagResponseData.builder()
                    .flag(keys.get(i))
                    .reason("api")
                    .value(values.get(i))
                    .build();
        }
        return CheckFlagsResponse.builder()
                .data(CheckFlagsResponseData.builder()
                        .flags(Arrays.asList(flags))
                        .build())
                .build();
    }

    private RulesengineFlag flag(String key, boolean defaultValue) {
        ObjectNode node = objectMapper.createObjectNode();
        node.put("key", key);
        node.put("id", "flag_" + key);
        node.put("account_id", "acc_1");
        node.put("environment_id", "env_1");
        node.put("default_value", defaultValue);
        node.set("rules", objectMapper.createArrayNode());
        try {
            return objectMapper.treeToValue(node, RulesengineFlag.class);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
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
