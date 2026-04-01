package com.schematic.api.datastream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.schematic.api.datastream.DataStreamMessages.DataStreamResp;
import com.schematic.api.datastream.DataStreamMessages.EntityType;
import com.schematic.api.datastream.DataStreamMessages.MessageType;
import com.schematic.api.logger.SchematicLogger;
import com.schematic.api.types.RulesengineCheckFlagResult;
import com.schematic.api.types.RulesengineCompany;
import com.schematic.api.types.RulesengineFlag;
import com.schematic.api.types.RulesengineUser;
import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DataStreamClientTest {

    @Mock
    private SchematicLogger logger;

    private ObjectMapper objectMapper;
    private DataStreamClient client;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        DatastreamOptions options =
                DatastreamOptions.builder().cacheTTL(Duration.ofMinutes(5)).build();
        client = new DataStreamClient(options, "test-api-key", "https://api.schematichq.com", logger);
    }

    @AfterEach
    void tearDown() {
        if (client != null) {
            client.close();
        }
    }

    // --- Flag caching tests ---

    @Test
    void handleMessage_fullFlag_cachesFlag() {
        DataStreamResp resp =
                buildResp(EntityType.FLAG.getValue(), MessageType.FULL.getValue(), null, flagNode("test-flag", true));

        client.handleMessage(resp);

        RulesengineFlag cached = client.getCachedFlag("test-flag");
        assertNotNull(cached);
        assertEquals("test-flag", cached.getKey());
        assertTrue(cached.getDefaultValue());
    }

    @Test
    void handleMessage_fullFlags_cachesMultipleFlags() {
        ObjectNode flag1 = flagNode("flag-1", true);
        ObjectNode flag2 = flagNode("flag-2", false);
        ArrayNode arrayData = objectMapper.createArrayNode();
        arrayData.add(flag1).add(flag2);

        DataStreamResp resp = buildResp(EntityType.FLAGS.getValue(), MessageType.FULL.getValue(), null, arrayData);

        client.handleMessage(resp);

        assertNotNull(client.getCachedFlag("flag-1"));
        assertNotNull(client.getCachedFlag("flag-2"));
    }

    @Test
    void handleMessage_deleteFlag_removesFromCache() {
        // Cache a flag
        client.handleMessage(
                buildResp(EntityType.FLAG.getValue(), MessageType.FULL.getValue(), null, flagNode("delete-me", true)));
        assertNotNull(client.getCachedFlag("delete-me"));

        // Delete it
        ObjectNode deleteData = objectMapper.createObjectNode();
        deleteData.put("key", "delete-me");
        client.handleMessage(buildResp(EntityType.FLAG.getValue(), MessageType.DELETE.getValue(), null, deleteData));

        assertNull(client.getCachedFlag("delete-me"));
    }

    @Test
    void getCachedFlag_returnsNullWhenNotCached() {
        assertNull(client.getCachedFlag("nonexistent"));
    }

    // --- Company caching tests ---

    @Test
    void handleMessage_fullCompany_cachesCompanyAndKeys() {
        ObjectNode company = companyNode("comp-1", "customer_id", "cust-123");

        client.handleMessage(buildResp(EntityType.COMPANY.getValue(), MessageType.FULL.getValue(), "comp-1", company));

        Map<String, String> keys = new HashMap<>();
        keys.put("customer_id", "cust-123");
        RulesengineCompany cached = client.getCachedCompany(keys);
        assertNotNull(cached);
        assertEquals("comp-1", cached.getId());
    }

    @Test
    void handleMessage_fullCompanies_cachesMultiple() {
        ObjectNode comp1 = companyNode("c1", "id", "c1-key");
        ObjectNode comp2 = companyNode("c2", "id", "c2-key");
        ArrayNode arrayData = objectMapper.createArrayNode();
        arrayData.add(comp1).add(comp2);

        client.handleMessage(buildResp(EntityType.COMPANIES.getValue(), MessageType.FULL.getValue(), null, arrayData));

        assertNotNull(client.getCachedCompany(Collections.singletonMap("id", "c1-key")));
        assertNotNull(client.getCachedCompany(Collections.singletonMap("id", "c2-key")));
    }

    @Test
    void getCachedCompany_returnsNullForNullKeys() {
        assertNull(client.getCachedCompany(null));
        assertNull(client.getCachedCompany(new HashMap<>()));
    }

    @Test
    void handleMessage_partialCompany_mergesKeys() {
        // Cache a company
        client.handleMessage(buildResp(
                EntityType.COMPANY.getValue(),
                MessageType.FULL.getValue(),
                "comp-1",
                companyNode("comp-1", "customer_id", "cust-123")));

        // Send partial update adding a new key
        ObjectNode partial = objectMapper.createObjectNode();
        partial.put("id", "comp-1");
        ObjectNode newKeys = objectMapper.createObjectNode();
        newKeys.put("external_id", "ext-456");
        partial.set("keys", newKeys);

        client.handleMessage(
                buildResp(EntityType.COMPANY.getValue(), MessageType.PARTIAL.getValue(), "comp-1", partial));

        // Both old and new keys should work for lookup
        assertNotNull(client.getCachedCompany(Collections.singletonMap("customer_id", "cust-123")));
        assertNotNull(client.getCachedCompany(Collections.singletonMap("external_id", "ext-456")));
    }

    @Test
    void handleMessage_partialUser_mergesKeys() {
        client.handleMessage(buildResp(
                EntityType.USER.getValue(),
                MessageType.FULL.getValue(),
                "user-1",
                userNode("user-1", "email", "test@example.com")));

        ObjectNode partial = objectMapper.createObjectNode();
        partial.put("id", "user-1");
        ObjectNode newKeys = objectMapper.createObjectNode();
        newKeys.put("external_id", "ext-789");
        partial.set("keys", newKeys);

        client.handleMessage(buildResp(EntityType.USER.getValue(), MessageType.PARTIAL.getValue(), "user-1", partial));

        assertNotNull(client.getCachedUser(Collections.singletonMap("email", "test@example.com")));
        assertNotNull(client.getCachedUser(Collections.singletonMap("external_id", "ext-789")));
    }

    // --- User caching tests ---

    @Test
    void handleMessage_fullUser_cachesUserAndKeys() {
        ObjectNode user = userNode("user-1", "email", "test@example.com");

        client.handleMessage(buildResp(EntityType.USER.getValue(), MessageType.FULL.getValue(), "user-1", user));

        Map<String, String> keys = new HashMap<>();
        keys.put("email", "test@example.com");
        RulesengineUser cached = client.getCachedUser(keys);
        assertNotNull(cached);
        assertEquals("user-1", cached.getId());
    }

    @Test
    void getCachedUser_returnsNullForNullKeys() {
        assertNull(client.getCachedUser(null));
        assertNull(client.getCachedUser(new HashMap<>()));
    }

    // --- Error handling tests ---

    @Test
    void handleMessage_errorMessage_logsError() {
        ObjectNode errorData = objectMapper.createObjectNode();
        errorData.put("error", "something went wrong");

        client.handleMessage(buildResp(EntityType.FLAG.getValue(), MessageType.ERROR.getValue(), null, errorData));

        verify(logger).error(contains("DataStream error"));
    }

    @Test
    void handleMessage_unknownEntityType_logsWarning() {
        client.handleMessage(
                buildResp("rulesengine.Unknown", MessageType.FULL.getValue(), null, objectMapper.createObjectNode()));

        verify(logger).warn(contains("unknown entity type"));
    }

    @Test
    void handleMessage_nullData_doesNotThrow() {
        DataStreamResp resp = buildResp(EntityType.FLAG.getValue(), MessageType.FULL.getValue(), null, null);
        assertDoesNotThrow(() -> client.handleMessage(resp));
    }

    // --- checkFlag tests ---

    @Test
    void checkFlag_throwsWhenFlagNotInCache() {
        assertThrows(DataStreamException.class, () -> client.checkFlag("nonexistent", null, null));
    }

    @Test
    void checkFlag_returnsDefaultWhenNoRulesEngine() {
        client.handleMessage(
                buildResp(EntityType.FLAG.getValue(), MessageType.FULL.getValue(), null, flagNode("my-flag", true)));

        RulesengineCheckFlagResult result = client.checkFlag("my-flag", null, null);
        assertEquals("my-flag", result.getFlagKey());
        assertTrue(result.getValue());
        assertEquals("RULES_ENGINE_UNAVAILABLE", result.getReason());
    }

    @Test
    void checkFlag_returnsDefaultFalseWhenNoRulesEngine() {
        client.handleMessage(buildResp(
                EntityType.FLAG.getValue(), MessageType.FULL.getValue(), null, flagNode("false-flag", false)));

        RulesengineCheckFlagResult result = client.checkFlag("false-flag", null, null);
        assertFalse(result.getValue());
        assertEquals("RULES_ENGINE_UNAVAILABLE", result.getReason());
    }

    @Test
    void checkFlag_withRulesEngine_evaluatesLocally() throws Exception {
        RulesEngine mockEngine = mock(RulesEngine.class);
        when(mockEngine.isInitialized()).thenReturn(true);
        when(mockEngine.checkFlag(any(), any(), any()))
                .thenReturn(RulesengineCheckFlagResult.builder()
                        .flagKey("engine-flag")
                        .reason("RULES_ENGINE_EVALUATION")
                        .value(true)
                        .ruleId("rule-123")
                        .build());

        DatastreamOptions opts = DatastreamOptions.builder().build();
        DataStreamClient clientWithEngine =
                new DataStreamClient(opts, "test-key", "https://api.schematichq.com", logger, mockEngine);

        clientWithEngine.handleMessage(buildResp(
                EntityType.FLAG.getValue(), MessageType.FULL.getValue(), null, flagNode("engine-flag", false)));

        RulesengineCheckFlagResult result = clientWithEngine.checkFlag("engine-flag", null, null);
        assertTrue(result.getValue());
        assertEquals("RULES_ENGINE_EVALUATION", result.getReason());
        assertEquals("rule-123", result.getRuleId().orElse(null));

        clientWithEngine.close();
    }

    @Test
    void checkFlag_withRulesEngine_fallsBackOnError() throws Exception {
        RulesEngine mockEngine = mock(RulesEngine.class);
        when(mockEngine.isInitialized()).thenReturn(true);
        when(mockEngine.checkFlag(any(), any(), any())).thenThrow(new RuntimeException("WASM error"));

        DatastreamOptions opts = DatastreamOptions.builder().build();
        DataStreamClient clientWithEngine =
                new DataStreamClient(opts, "test-key", "https://api.schematichq.com", logger, mockEngine);

        clientWithEngine.handleMessage(
                buildResp(EntityType.FLAG.getValue(), MessageType.FULL.getValue(), null, flagNode("error-flag", true)));

        RulesengineCheckFlagResult result = clientWithEngine.checkFlag("error-flag", null, null);
        assertTrue(result.getValue());
        assertEquals("RULES_ENGINE_ERROR", result.getReason());
        assertEquals("WASM error", result.getErr().orElse(null));

        clientWithEngine.close();
    }

    @Test
    void checkFlag_withCachedCompanyAndUser() {
        client.handleMessage(
                buildResp(EntityType.FLAG.getValue(), MessageType.FULL.getValue(), null, flagNode("ctx-flag", true)));
        client.handleMessage(buildResp(
                EntityType.COMPANY.getValue(),
                MessageType.FULL.getValue(),
                "comp-1",
                companyNode("comp-1", "customer_id", "cust-123")));
        client.handleMessage(buildResp(
                EntityType.USER.getValue(),
                MessageType.FULL.getValue(),
                "user-1",
                userNode("user-1", "email", "test@example.com")));

        Map<String, String> companyKeys = Collections.singletonMap("customer_id", "cust-123");
        Map<String, String> userKeys = Collections.singletonMap("email", "test@example.com");

        RulesengineCheckFlagResult result = client.checkFlag("ctx-flag", companyKeys, userKeys);
        assertEquals("ctx-flag", result.getFlagKey());
        assertEquals("RULES_ENGINE_UNAVAILABLE", result.getReason());
        assertEquals("comp-1", result.getCompanyId().orElse(null));
        assertEquals("user-1", result.getUserId().orElse(null));
    }

    // --- evaluateFlag tests ---

    @Test
    void evaluateFlag_extractsFlagId() {
        RulesengineFlag flag = RulesengineFlag.builder()
                .accountId("acc_1")
                .defaultValue(true)
                .environmentId("env_1")
                .id("flag-uuid-123")
                .key("id-flag")
                .rules(Collections.emptyList())
                .build();

        RulesengineCheckFlagResult result = client.evaluateFlag(flag, null, null);
        assertEquals("flag-uuid-123", result.getFlagId().orElse(null));
        assertTrue(result.getValue());
    }

    // --- Replicator mode tests ---

    @Test
    void isReplicatorMode_falseByDefault() {
        assertFalse(client.isReplicatorMode());
    }

    @Test
    void isReplicatorMode_trueWhenConfigured() {
        DatastreamOptions options = DatastreamOptions.builder()
                .withReplicatorMode("http://localhost:8090/ready")
                .build();
        DataStreamClient replicatorClient =
                new DataStreamClient(options, "test-key", "https://api.schematichq.com", logger);

        assertTrue(replicatorClient.isReplicatorMode());
        replicatorClient.close();
    }

    @Test
    void isConnected_falseInitially() {
        assertFalse(client.isConnected());
    }

    // --- Close tests ---

    @Test
    void close_isIdempotent() {
        client.close();
        assertDoesNotThrow(() -> client.close());
    }

    @Test
    void start_throwsAfterClose() {
        client.close();
        assertThrows(IllegalStateException.class, () -> client.start());
    }

    // --- Helper methods: build JSON matching the generated type's @JsonProperty format ---

    private ObjectNode flagNode(String key, boolean defaultValue) {
        ObjectNode node = objectMapper.createObjectNode();
        node.put("key", key);
        node.put("id", "flag_" + key);
        node.put("account_id", "acc_1");
        node.put("environment_id", "env_1");
        node.put("default_value", defaultValue);
        node.set("rules", objectMapper.createArrayNode());
        return node;
    }

    private ObjectNode companyNode(String id, String keyName, String keyValue) {
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
        return node;
    }

    private ObjectNode userNode(String id, String keyName, String keyValue) {
        ObjectNode node = objectMapper.createObjectNode();
        node.put("id", id);
        node.put("account_id", "acc_1");
        node.put("environment_id", "env_1");
        ObjectNode keys = objectMapper.createObjectNode();
        keys.put(keyName, keyValue);
        node.set("keys", keys);
        node.set("traits", objectMapper.createArrayNode());
        node.set("rules", objectMapper.createArrayNode());
        return node;
    }

    private DataStreamResp buildResp(String entityType, String messageType, String entityId, JsonNode data) {
        ObjectNode respNode = objectMapper.createObjectNode();
        respNode.put("entity_type", entityType);
        respNode.put("message_type", messageType);
        if (entityId != null) {
            respNode.put("entity_id", entityId);
        }
        if (data != null) {
            respNode.set("data", data);
        }
        return objectMapper.convertValue(respNode, DataStreamResp.class);
    }
}
