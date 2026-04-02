package com.schematic.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.schematic.api.core.ObjectMappers;
import com.schematic.api.logger.SchematicLogger;
import com.schematic.api.types.CreateEventRequestBody;
import java.io.Closeable;
import java.io.IOException;
import java.util.List;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Sends event batches directly to the Schematic event capture service
 * by posting to https://c.schematichq.com/batch.
 *
 * <p>Each event payload is built from the Fern-generated {@link CreateEventRequestBody} model
 * with {@code api_key} injected, so any fields added to the generated model are automatically
 * included in the capture service payload.
 */
public class HttpEventSender implements Closeable {
    private static final String DEFAULT_EVENT_CAPTURE_BASE_URL = "https://c.schematichq.com";
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    private final OkHttpClient httpClient;
    private final String apiKey;
    private final String baseUrl;
    private final SchematicLogger logger;

    public HttpEventSender(OkHttpClient httpClient, String apiKey, String baseUrl, SchematicLogger logger) {
        this.httpClient = httpClient != null ? httpClient : new OkHttpClient();
        this.apiKey = apiKey;
        this.baseUrl = baseUrl != null ? baseUrl : DEFAULT_EVENT_CAPTURE_BASE_URL;
        this.logger = logger;
    }

    /**
     * Sends a batch of events to the capture service.
     *
     * @param events The events to send
     * @throws IOException if the request fails
     */
    public void sendBatch(List<CreateEventRequestBody> events) throws IOException {
        if (events == null || events.isEmpty()) {
            return;
        }

        ArrayNode eventsArray = ObjectMappers.JSON_MAPPER.createArrayNode();
        for (CreateEventRequestBody event : events) {
            // Serialize the Fern model to a JSON tree, preserving all current and future fields
            JsonNode eventNode = ObjectMappers.JSON_MAPPER.valueToTree(event);
            if (eventNode.isObject()) {
                ((ObjectNode) eventNode).put("api_key", apiKey);
            }
            eventsArray.add(eventNode);
        }

        ObjectNode batchPayload = ObjectMappers.JSON_MAPPER.createObjectNode();
        batchPayload.set("events", eventsArray);

        String json;
        try {
            json = ObjectMappers.JSON_MAPPER.writeValueAsString(batchPayload);
        } catch (JsonProcessingException e) {
            throw new IOException("Failed to serialize event batch", e);
        }

        Request request = new Request.Builder()
                .url(baseUrl + "/batch")
                .post(RequestBody.create(json, JSON))
                .addHeader("X-Schematic-Api-Key", apiKey)
                .addHeader("Content-Type", "application/json")
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                String responseBody = response.body() != null ? response.body().string() : "";
                throw new IOException("HTTP " + response.code() + ": " + responseBody);
            }
        }
    }

    @Override
    public void close() {
        httpClient.dispatcher().executorService().shutdownNow();
        httpClient.connectionPool().evictAll();
    }
}
