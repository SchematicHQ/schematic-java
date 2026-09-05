package com.schematic.api.datastream;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.schematic.api.datastream.DataStreamMessages.DataStreamError;
import com.schematic.api.datastream.DataStreamMessages.DataStreamResp;
import org.junit.jupiter.api.Test;

public class DataStreamMessagesTest {
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void responseParsesStreamIdAndIgnoresUnknownEnvelopeFields() throws Exception {
        String json = "{\"data\":{\"id\":\"flag_1\"},\"entity_id\":\"flag_1\",\"entity_type\":\"rulesengine.Flag\","
                + "\"message_type\":\"full\",\"stream_id\":\"1725494400000-0\",\"some_future_field\":true}";

        DataStreamResp resp = mapper.readValue(json, DataStreamResp.class);

        assertEquals("flag_1", resp.getEntityId());
        assertEquals("rulesengine.Flag", resp.getEntityType());
        assertEquals("full", resp.getMessageType());
        assertEquals("1725494400000-0", resp.getStreamId());
        assertEquals("flag_1", resp.getData().get("id").asText());
    }

    @Test
    public void errorIgnoresUnknownEnvelopeFields() throws Exception {
        String json = "{\"error\":\"not found\",\"entity_type\":\"rulesengine.Company\",\"stream_id\":\"x\"}";

        DataStreamError err = mapper.readValue(json, DataStreamError.class);

        assertEquals("not found", err.getError());
    }
}
