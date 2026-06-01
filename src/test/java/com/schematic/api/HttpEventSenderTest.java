package com.schematic.api;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.schematic.api.core.ObjectMappers;
import com.schematic.api.types.CreateEventRequestBody;
import com.schematic.api.types.EventType;
import java.time.OffsetDateTime;
import java.util.Collections;
import org.junit.jupiter.api.Test;

/** Pins the capture-service wire format produced by {@link HttpEventSender#serializeBatch}. */
class HttpEventSenderTest {

    private final HttpEventSender sender = new HttpEventSender(null, "test_api_key", null, null);

    private JsonNode firstEvent(CreateEventRequestBody event) throws Exception {
        String json = sender.serializeBatch(Collections.singletonList(event));
        return ObjectMappers.JSON_MAPPER.readTree(json).get("events").get(0);
    }

    @Test
    void serializeBatch_alwaysIncludesApiKeyAndType() throws Exception {
        JsonNode wire = firstEvent(
                CreateEventRequestBody.builder().eventType(EventType.TRACK).build());

        assertEquals("test_api_key", wire.get("api_key").asText());
        assertEquals("track", wire.get("type").asText());
    }

    @Test
    void serializeBatch_excludesUnsetOptionalFields() throws Exception {
        JsonNode wire = firstEvent(
                CreateEventRequestBody.builder().eventType(EventType.TRACK).build());

        // Unset optional fields must not appear as explicit nulls on the wire.
        assertFalse(wire.has("idempotency_key"));
        assertFalse(wire.has("sent_at"));
        assertFalse(wire.has("trusted_client_clock"));
        assertFalse(wire.has("backfill"));
    }

    @Test
    void serializeBatch_includesSetOptionalFields() throws Exception {
        OffsetDateTime sentAt = OffsetDateTime.parse("2026-01-01T00:00:00Z");
        JsonNode wire = firstEvent(CreateEventRequestBody.builder()
                .eventType(EventType.TRACK)
                .idempotencyKey("evt_xyz")
                .sentAt(sentAt)
                .trustedClientClock(true)
                // backfill=false is explicitly set, so it must still reach the wire.
                .backfill(false)
                .build());

        assertEquals("evt_xyz", wire.get("idempotency_key").asText());
        assertEquals(sentAt.toString(), wire.get("sent_at").asText());
        assertTrue(wire.get("trusted_client_clock").asBoolean());
        assertTrue(wire.has("backfill"));
        assertFalse(wire.get("backfill").asBoolean());
    }
}
