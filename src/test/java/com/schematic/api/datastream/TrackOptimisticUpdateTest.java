package com.schematic.api.datastream;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.schematic.api.Schematic;
import com.schematic.api.datastream.DataStreamMessages.DataStreamResp;
import com.schematic.api.datastream.DataStreamMessages.EntityType;
import com.schematic.api.datastream.DataStreamMessages.MessageType;
import com.schematic.api.logger.SchematicLogger;
import com.schematic.api.types.RulesengineCompany;
import java.time.Duration;
import java.util.Collections;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * The optimistic company metric update in {@code track} runs even when the datastream is not
 * connected (replicator reporting {@code ready: false}, or a dropped WebSocket), so usage keeps
 * counting against the cached company.
 */
@ExtendWith(MockitoExtension.class)
class TrackOptimisticUpdateTest {

    // Nothing listens here: health checks and WebSocket connects fail, so the client stays
    // not connected for the whole test.
    private static final String UNREACHABLE = "http://127.0.0.1:1";

    private static final Map<String, String> COMPANY_KEYS = Collections.singletonMap("customer_id", "cust-1");

    @Mock
    private SchematicLogger logger;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private Schematic schematic;

    @AfterEach
    void tearDown() {
        if (schematic != null) {
            schematic.close();
        }
    }

    @Test
    void track_replicatorNotReady_updatesCachedCompanyMetrics() {
        schematic = buildSchematic(DatastreamOptions.builder()
                .withReplicatorMode(UNREACHABLE + "/ready")
                .replicatorHealthCheckInterval(Duration.ofHours(1))
                .build());
        assertTrue(schematic.isReplicatorMode());

        assertTrackIncrementsWhileDisconnected();
    }

    @Test
    void track_webSocketNotConnected_updatesCachedCompanyMetrics() {
        schematic = buildSchematic(DatastreamOptions.builder().build());
        assertFalse(schematic.isReplicatorMode());

        assertTrackIncrementsWhileDisconnected();
    }

    @Test
    void track_replicatorNotReady_companyNotCached_isNoOp() {
        schematic = buildSchematic(DatastreamOptions.builder()
                .withReplicatorMode(UNREACHABLE + "/ready")
                .replicatorHealthCheckInterval(Duration.ofHours(1))
                .build());

        assertDoesNotThrow(() -> schematic.track("api_calls", COMPANY_KEYS, null, null, 5L));
        assertNull(schematic.getDataStreamClient().getCachedCompany(COMPANY_KEYS));
    }

    private void assertTrackIncrementsWhileDisconnected() {
        DataStreamClient ds = schematic.getDataStreamClient();
        ds.handleMessage(companyResp("comp-1", "api_calls", 10));
        assertFalse(schematic.isDatastreamConnected());

        schematic.track("api_calls", COMPANY_KEYS, null, null, 5L);
        schematic.track("other_event", COMPANY_KEYS, null, null, 3L);

        RulesengineCompany updated = ds.getCachedCompany(COMPANY_KEYS);
        assertNotNull(updated);
        assertEquals(15, updated.getMetrics().get(0).getValue());
    }

    private Schematic buildSchematic(DatastreamOptions options) {
        return Schematic.builder()
                .apiKey("test_api_key")
                .logger(logger)
                .basePath(UNREACHABLE)
                .eventCaptureBaseUrl(UNREACHABLE)
                .datastreamOptions(options)
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
        return buildResp(EntityType.COMPANY.getValue(), id, node);
    }

    private DataStreamResp buildResp(String entityType, String entityId, JsonNode data) {
        ObjectNode respNode = objectMapper.createObjectNode();
        respNode.put("entity_type", entityType);
        respNode.put("message_type", MessageType.FULL.getValue());
        respNode.put("entity_id", entityId);
        respNode.set("data", data);
        return objectMapper.convertValue(respNode, DataStreamResp.class);
    }
}
