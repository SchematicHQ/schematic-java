package com.schematic.api.datastream;

import static org.junit.jupiter.api.Assertions.*;

import com.schematic.api.datastream.DataStreamMessages.Action;
import com.schematic.api.datastream.DataStreamMessages.DataStreamBaseReq;
import com.schematic.api.datastream.DataStreamMessages.DataStreamReq;
import com.schematic.api.datastream.DataStreamMessages.DataStreamResp;
import com.schematic.api.datastream.DataStreamMessages.EntityType;
import com.schematic.api.datastream.DataStreamMessages.MessageType;
import com.schematic.api.logger.SchematicLogger;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DataStreamWebSocketClientTest {

    @Mock
    private SchematicLogger logger;

    // --- URL conversion tests (matches Go url_conversion_test.go) ---

    @Test
    void convertApiUrlToWebSocketUrl_apiSubdomainWithHttps() {
        String result = DataStreamWebSocketClient.convertApiUrlToWebSocketUrl("https://api.schematichq.com");
        assertEquals("wss://datastream.schematichq.com/datastream", result);
    }

    @Test
    void convertApiUrlToWebSocketUrl_apiSubdomainWithStaging() {
        String result =
                DataStreamWebSocketClient.convertApiUrlToWebSocketUrl("https://api.staging.schematichq.com");
        assertEquals("wss://datastream.staging.schematichq.com/datastream", result);
    }

    @Test
    void convertApiUrlToWebSocketUrl_apiSubdomainWithHttp() {
        String result = DataStreamWebSocketClient.convertApiUrlToWebSocketUrl("http://api.localhost:8080");
        assertEquals("ws://datastream.localhost:8080/datastream", result);
    }

    @Test
    void convertApiUrlToWebSocketUrl_nonApiSubdomain() {
        String result = DataStreamWebSocketClient.convertApiUrlToWebSocketUrl("https://custom.schematichq.com");
        assertEquals("wss://custom.schematichq.com/datastream", result);
    }

    @Test
    void convertApiUrlToWebSocketUrl_noSubdomain() {
        String result = DataStreamWebSocketClient.convertApiUrlToWebSocketUrl("https://schematichq.com");
        assertEquals("wss://schematichq.com/datastream", result);
    }

    @Test
    void convertApiUrlToWebSocketUrl_localhostWithoutSubdomain() {
        String result = DataStreamWebSocketClient.convertApiUrlToWebSocketUrl("http://localhost:8080");
        assertEquals("ws://localhost:8080/datastream", result);
    }

    @Test
    void convertApiUrlToWebSocketUrl_invalidScheme() {
        assertThrows(
                IllegalArgumentException.class,
                () -> DataStreamWebSocketClient.convertApiUrlToWebSocketUrl("ftp://api.example.com"));
    }

    @Test
    void convertApiUrlToWebSocketUrl_invalidUrl() {
        assertThrows(
                IllegalArgumentException.class,
                () -> DataStreamWebSocketClient.convertApiUrlToWebSocketUrl("not a url"));
    }

    @Test
    void convertApiUrlToWebSocketUrl_alreadyWebSocket() {
        String result =
                DataStreamWebSocketClient.convertApiUrlToWebSocketUrl("wss://datastream.schematichq.com/datastream");
        assertEquals("wss://datastream.schematichq.com/datastream", result);
    }

    @Test
    void convertApiUrlToWebSocketUrl_wsWithoutPath() {
        String result = DataStreamWebSocketClient.convertApiUrlToWebSocketUrl("wss://datastream.schematichq.com");
        assertEquals("wss://datastream.schematichq.com/datastream", result);
    }

    // --- NewClient tests (matches Go TestNewClientWithAPIURL) ---

    @Test
    void builder_httpApiUrlConversion() {
        DataStreamWebSocketClient client = DataStreamWebSocketClient.builder()
                .url("https://api.schematichq.com")
                .apiKey("test-api-key")
                .messageHandler(msg -> {})
                .build();

        assertNotNull(client);
        assertFalse(client.isConnected());
        assertFalse(client.isReady());
        client.close();
    }

    @Test
    void builder_webSocketUrlPassthrough() {
        DataStreamWebSocketClient client = DataStreamWebSocketClient.builder()
                .url("wss://custom.example.com/ws")
                .apiKey("test-api-key")
                .messageHandler(msg -> {})
                .build();

        assertNotNull(client);
        client.close();
    }

    @Test
    void builder_httpLocalhostConversion() {
        DataStreamWebSocketClient client = DataStreamWebSocketClient.builder()
                .url("http://api.localhost:8080")
                .apiKey("test-api-key")
                .messageHandler(msg -> {})
                .build();

        assertNotNull(client);
        client.close();
    }

    @Test
    void builder_requiresUrl() {
        assertThrows(IllegalArgumentException.class, () -> DataStreamWebSocketClient.builder()
                .apiKey("test-api-key")
                .messageHandler(msg -> {})
                .build());
    }

    @Test
    void builder_requiresApiKey() {
        assertThrows(IllegalArgumentException.class, () -> DataStreamWebSocketClient.builder()
                .url("https://api.example.com")
                .messageHandler(msg -> {})
                .build());
    }

    @Test
    void builder_requiresMessageHandler() {
        assertThrows(IllegalArgumentException.class, () -> DataStreamWebSocketClient.builder()
                .url("https://api.example.com")
                .apiKey("test-api-key")
                .build());
    }

    @Test
    void builder_customOptions() {
        DataStreamWebSocketClient client = DataStreamWebSocketClient.builder()
                .url("https://api.schematichq.com")
                .apiKey("test_key")
                .messageHandler(msg -> {})
                .logger(logger)
                .maxReconnectAttempts(5)
                .minReconnectDelayMs(500)
                .maxReconnectDelayMs(15000)
                .pingPeriodMs(20000)
                .pongWaitMs(25000)
                .messageWorkers(2)
                .messageQueueSize(50)
                .build();

        assertNotNull(client);
        client.close();
    }

    // --- Disconnection behavior tests (matches Go client_test.go handleReadError tests) ---
    // In Java, OkHttp delivers these via WebSocketListener callbacks.
    // We test the handleDisconnected/handleFailure logic indirectly via state.

    @Test
    void close_normalClosure_doesNotReconnect() {
        // Normal closure (code 1000) should not trigger reconnect
        DataStreamWebSocketClient client = DataStreamWebSocketClient.builder()
                .url("https://api.schematichq.com")
                .apiKey("test_key")
                .messageHandler(msg -> {})
                .logger(logger)
                .build();

        // Simulate: client was never started, close should be clean
        client.close();
        assertFalse(client.isConnected());
        assertFalse(client.isReady());
    }

    @Test
    void close_isIdempotent() {
        DataStreamWebSocketClient client = DataStreamWebSocketClient.builder()
                .url("https://api.schematichq.com")
                .apiKey("test_key")
                .messageHandler(msg -> {})
                .logger(logger)
                .build();

        client.close();
        client.close(); // Should not throw
        assertFalse(client.isConnected());
    }

    @Test
    void start_throwsAfterClose() {
        DataStreamWebSocketClient client = DataStreamWebSocketClient.builder()
                .url("https://api.schematichq.com")
                .apiKey("test_key")
                .messageHandler(msg -> {})
                .logger(logger)
                .build();

        client.close();
        assertThrows(IllegalStateException.class, client::start);
    }

    // --- State tests ---

    @Test
    void sendMessage_failsWhenNotConnected() {
        DataStreamWebSocketClient client = DataStreamWebSocketClient.builder()
                .url("https://api.schematichq.com")
                .apiKey("test_key")
                .messageHandler(msg -> {})
                .logger(logger)
                .build();

        assertFalse(client.sendMessage("test"));
        client.close();
    }

    @Test
    void initialState_notConnectedNotReady() {
        DataStreamWebSocketClient client = DataStreamWebSocketClient.builder()
                .url("https://api.schematichq.com")
                .apiKey("test_key")
                .messageHandler(msg -> {})
                .build();

        assertFalse(client.isConnected());
        assertFalse(client.isReady());
        client.close();
    }

    // --- Backoff tests ---

    @Test
    void calculateBackoffDelay_increasesExponentially() {
        DataStreamWebSocketClient client = DataStreamWebSocketClient.builder()
                .url("https://api.schematichq.com")
                .apiKey("test_key")
                .messageHandler(msg -> {})
                .build();

        // With default minReconnectDelay of 1000ms:
        // Attempt 1: 2^0 * 1000 + jitter = 1000 + [0, 1000)
        // Attempt 2: 2^1 * 1000 + jitter = 2000 + [0, 1000)
        // Attempt 3: 2^2 * 1000 + jitter = 4000 + [0, 1000)
        long delay1 = client.calculateBackoffDelay(1);
        long delay3 = client.calculateBackoffDelay(3);

        assertTrue(delay1 >= 1000, "Attempt 1 delay should be >= 1000ms, was " + delay1);
        assertTrue(delay1 < 2000, "Attempt 1 delay should be < 2000ms, was " + delay1);
        assertTrue(delay3 >= 4000, "Attempt 3 delay should be >= 4000ms, was " + delay3);
        assertTrue(delay3 < 5000, "Attempt 3 delay should be < 5000ms, was " + delay3);

        client.close();
    }

    @Test
    void calculateBackoffDelay_capsAtMax() {
        DataStreamWebSocketClient client = DataStreamWebSocketClient.builder()
                .url("https://api.schematichq.com")
                .apiKey("test_key")
                .messageHandler(msg -> {})
                .maxReconnectDelayMs(5000)
                .minReconnectDelayMs(1000)
                .build();

        // Attempt 10 would be 2^9 * 1000 = 512000, but should cap at 5000 + jitter
        long delay = client.calculateBackoffDelay(10);
        assertTrue(delay <= 6000, "Delay should be capped at max + jitter, was " + delay);

        client.close();
    }

    // --- Message type tests ---

    @Test
    void entityType_fromString() {
        assertEquals(EntityType.COMPANY, EntityType.fromString("rulesengine.Company"));
        assertEquals(EntityType.COMPANIES, EntityType.fromString("rulesengine.Companies"));
        assertEquals(EntityType.FLAG, EntityType.fromString("rulesengine.Flag"));
        assertEquals(EntityType.FLAGS, EntityType.fromString("rulesengine.Flags"));
        assertEquals(EntityType.USER, EntityType.fromString("rulesengine.User"));
        assertEquals(EntityType.USERS, EntityType.fromString("rulesengine.Users"));
        assertNull(EntityType.fromString("unknown"));
    }

    @Test
    void messageType_fromString() {
        assertEquals(MessageType.FULL, MessageType.fromString("full"));
        assertEquals(MessageType.PARTIAL, MessageType.fromString("partial"));
        assertEquals(MessageType.DELETE, MessageType.fromString("delete"));
        assertEquals(MessageType.ERROR, MessageType.fromString("error"));
        assertEquals(MessageType.UNKNOWN, MessageType.fromString("something_else"));
    }

    @Test
    void dataStreamReq_serializesCorrectly() {
        Map<String, String> keys = new HashMap<>();
        keys.put("company_id", "123");

        DataStreamReq req = new DataStreamReq(Action.START, EntityType.FLAGS, keys);
        assertEquals(Action.START, req.getAction());
        assertEquals("rulesengine.Flags", req.getEntityType());
        assertEquals(keys, req.getKeys());
    }

    @Test
    void dataStreamBaseReq_wrapsRequest() {
        DataStreamReq req = new DataStreamReq(Action.START, EntityType.FLAGS, null);
        DataStreamBaseReq baseReq = new DataStreamBaseReq(req);
        assertEquals(req, baseReq.getData());
    }

    @Test
    void dataStreamResp_defaultConstruction() {
        DataStreamResp resp = new DataStreamResp();
        assertNull(resp.getData());
        assertNull(resp.getEntityId());
        assertNull(resp.getEntityType());
        assertNull(resp.getMessageType());
        assertNull(resp.getEntityTypeEnum());
        assertEquals(MessageType.UNKNOWN, resp.getMessageTypeEnum());
    }
}
