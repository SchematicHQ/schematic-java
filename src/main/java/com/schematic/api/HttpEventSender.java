package com.schematic.api;

import com.fasterxml.jackson.core.JsonProcessingException;
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

        // Build batch matching the capture service format (same as Go SDK's EventPayload)
        ArrayNode eventsArray = ObjectMappers.JSON_MAPPER.createArrayNode();
        for (CreateEventRequestBody event : events) {
            ObjectNode eventNode = ObjectMappers.JSON_MAPPER.createObjectNode();
            eventNode.put("api_key", apiKey);
            eventNode.put("type", event.getEventType().toString());
            if (event.getBody().isPresent()) {
                eventNode.set(
                        "body",
                        ObjectMappers.JSON_MAPPER.valueToTree(event.getBody().get()));
            }
            if (event.getSentAt().isPresent()) {
                eventNode.put("sent_at", event.getSentAt().get().toString());
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

        String url = baseUrl + "/batch";

        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(json, JSON))
                .addHeader("X-Schematic-Api-Key", apiKey)
                .addHeader("Content-Type", "application/json")
                .build();

        if (logger != null) {
            logger.debug("Sending event batch (" + events.size() + " events) to " + url);
            logger.debug("Event batch payload: " + json);
        }

        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                String responseBody = response.body() != null ? response.body().string() : "";
                if (logger != null) {
                    logger.error("Event batch failed: HTTP " + response.code() + " from " + url + ": " + responseBody);
                }
                throw new IOException("HTTP " + response.code() + ": " + responseBody);
            }
            if (logger != null) {
                logger.debug("Event batch sent successfully to " + url);
            }
        }
    }

    @Override
    public void close() {
        httpClient.dispatcher().executorService().shutdownNow();
        httpClient.connectionPool().evictAll();
    }
}
