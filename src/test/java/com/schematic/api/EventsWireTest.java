package com.schematic.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.schematic.api.core.ObjectMappers;
import com.schematic.api.resources.events.requests.CreateEventBatchRequestBody;
import com.schematic.api.resources.events.requests.GetEventSummariesRequest;
import com.schematic.api.resources.events.requests.ListEventsRequest;
import com.schematic.api.resources.events.types.CreateEventBatchResponse;
import com.schematic.api.resources.events.types.CreateEventResponse;
import com.schematic.api.resources.events.types.GetEventResponse;
import com.schematic.api.resources.events.types.GetEventSummariesResponse;
import com.schematic.api.resources.events.types.GetSegmentIntegrationStatusResponse;
import com.schematic.api.resources.events.types.ListEventsResponse;
import com.schematic.api.types.CreateEventRequestBody;
import com.schematic.api.types.CreateEventRequestBodyEventType;
import java.util.Arrays;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EventsWireTest {
    private MockWebServer server;
    private BaseSchematic client;
    private ObjectMapper objectMapper = ObjectMappers.JSON_MAPPER;

    @BeforeEach
    public void setup() throws Exception {
        server = new MockWebServer();
        server.start();
        client = BaseSchematic.builder()
                .url(server.url("/").toString())
                .apiKey("test-api-key")
                .build();
    }

    @AfterEach
    public void teardown() throws Exception {
        server.shutdown();
    }

    @Test
    public void testCreateEventBatch() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":{\"events\":[{\"captured_at\":\"2024-01-15T09:30:00Z\",\"remote_addr\":\"remote_addr\",\"remote_ip\":\"remote_ip\",\"user_agent\":\"user_agent\"}]},\"params\":{\"key\":\"value\"}}"));
        CreateEventBatchResponse response = client.events()
                .createEventBatch(CreateEventBatchRequestBody.builder()
                        .events(Arrays.asList(CreateEventRequestBody.builder()
                                .eventType(CreateEventRequestBodyEventType.IDENTIFY)
                                .build()))
                        .build());
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("POST", request.getMethod());
        // Validate request body
        String actualRequestBody = request.getBody().readUtf8();
        String expectedRequestBody = ""
                + "{\n"
                + "  \"events\": [\n"
                + "    {\n"
                + "      \"event_type\": \"identify\"\n"
                + "    }\n"
                + "  ]\n"
                + "}";
        JsonNode actualJson = objectMapper.readTree(actualRequestBody);
        JsonNode expectedJson = objectMapper.readTree(expectedRequestBody);
        Assertions.assertEquals(expectedJson, actualJson, "Request body structure does not match expected");
        if (actualJson.has("type") || actualJson.has("_type") || actualJson.has("kind")) {
            String discriminator = null;
            if (actualJson.has("type")) discriminator = actualJson.get("type").asText();
            else if (actualJson.has("_type"))
                discriminator = actualJson.get("_type").asText();
            else if (actualJson.has("kind"))
                discriminator = actualJson.get("kind").asText();
            Assertions.assertNotNull(discriminator, "Union type should have a discriminator field");
            Assertions.assertFalse(discriminator.isEmpty(), "Union discriminator should not be empty");
        }

        if (!actualJson.isNull()) {
            Assertions.assertTrue(
                    actualJson.isObject() || actualJson.isArray() || actualJson.isValueNode(),
                    "request should be a valid JSON value");
        }

        if (actualJson.isArray()) {
            Assertions.assertTrue(actualJson.size() >= 0, "Array should have valid size");
        }
        if (actualJson.isObject()) {
            Assertions.assertTrue(actualJson.size() >= 0, "Object should have valid field count");
        }

        // Validate response body
        Assertions.assertNotNull(response, "Response should not be null");
        String actualResponseJson = objectMapper.writeValueAsString(response);
        String expectedResponseBody = ""
                + "{\n"
                + "  \"data\": {\n"
                + "    \"events\": [\n"
                + "      {\n"
                + "        \"captured_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"remote_addr\": \"remote_addr\",\n"
                + "        \"remote_ip\": \"remote_ip\",\n"
                + "        \"user_agent\": \"user_agent\"\n"
                + "      }\n"
                + "    ]\n"
                + "  },\n"
                + "  \"params\": {\n"
                + "    \"key\": \"value\"\n"
                + "  }\n"
                + "}";
        JsonNode actualResponseNode = objectMapper.readTree(actualResponseJson);
        JsonNode expectedResponseNode = objectMapper.readTree(expectedResponseBody);
        Assertions.assertEquals(
                expectedResponseNode, actualResponseNode, "Response body structure does not match expected");
        if (actualResponseNode.has("type") || actualResponseNode.has("_type") || actualResponseNode.has("kind")) {
            String discriminator = null;
            if (actualResponseNode.has("type"))
                discriminator = actualResponseNode.get("type").asText();
            else if (actualResponseNode.has("_type"))
                discriminator = actualResponseNode.get("_type").asText();
            else if (actualResponseNode.has("kind"))
                discriminator = actualResponseNode.get("kind").asText();
            Assertions.assertNotNull(discriminator, "Union type should have a discriminator field");
            Assertions.assertFalse(discriminator.isEmpty(), "Union discriminator should not be empty");
        }

        if (!actualResponseNode.isNull()) {
            Assertions.assertTrue(
                    actualResponseNode.isObject() || actualResponseNode.isArray() || actualResponseNode.isValueNode(),
                    "response should be a valid JSON value");
        }

        if (actualResponseNode.isArray()) {
            Assertions.assertTrue(actualResponseNode.size() >= 0, "Array should have valid size");
        }
        if (actualResponseNode.isObject()) {
            Assertions.assertTrue(actualResponseNode.size() >= 0, "Object should have valid field count");
        }
    }

    @Test
    public void testGetEventSummaries() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":[{\"company_count\":1,\"environment_id\":\"environment_id\",\"event_count\":1,\"event_subtype\":\"event_subtype\",\"last_seen_at\":\"2024-01-15T09:30:00Z\",\"user_count\":1}],\"params\":{\"event_subtypes\":[\"event_subtypes\"],\"limit\":1,\"offset\":1,\"q\":\"q\"}}"));
        GetEventSummariesResponse response = client.events()
                .getEventSummaries(GetEventSummariesRequest.builder()
                        .q("q")
                        .limit(1)
                        .offset(1)
                        .build());
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("GET", request.getMethod());

        // Validate response body
        Assertions.assertNotNull(response, "Response should not be null");
        String actualResponseJson = objectMapper.writeValueAsString(response);
        String expectedResponseBody = ""
                + "{\n"
                + "  \"data\": [\n"
                + "    {\n"
                + "      \"company_count\": 1,\n"
                + "      \"environment_id\": \"environment_id\",\n"
                + "      \"event_count\": 1,\n"
                + "      \"event_subtype\": \"event_subtype\",\n"
                + "      \"last_seen_at\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"user_count\": 1\n"
                + "    }\n"
                + "  ],\n"
                + "  \"params\": {\n"
                + "    \"event_subtypes\": [\n"
                + "      \"event_subtypes\"\n"
                + "    ],\n"
                + "    \"limit\": 1,\n"
                + "    \"offset\": 1,\n"
                + "    \"q\": \"q\"\n"
                + "  }\n"
                + "}";
        JsonNode actualResponseNode = objectMapper.readTree(actualResponseJson);
        JsonNode expectedResponseNode = objectMapper.readTree(expectedResponseBody);
        Assertions.assertEquals(
                expectedResponseNode, actualResponseNode, "Response body structure does not match expected");
        if (actualResponseNode.has("type") || actualResponseNode.has("_type") || actualResponseNode.has("kind")) {
            String discriminator = null;
            if (actualResponseNode.has("type"))
                discriminator = actualResponseNode.get("type").asText();
            else if (actualResponseNode.has("_type"))
                discriminator = actualResponseNode.get("_type").asText();
            else if (actualResponseNode.has("kind"))
                discriminator = actualResponseNode.get("kind").asText();
            Assertions.assertNotNull(discriminator, "Union type should have a discriminator field");
            Assertions.assertFalse(discriminator.isEmpty(), "Union discriminator should not be empty");
        }

        if (!actualResponseNode.isNull()) {
            Assertions.assertTrue(
                    actualResponseNode.isObject() || actualResponseNode.isArray() || actualResponseNode.isValueNode(),
                    "response should be a valid JSON value");
        }

        if (actualResponseNode.isArray()) {
            Assertions.assertTrue(actualResponseNode.size() >= 0, "Array should have valid size");
        }
        if (actualResponseNode.isObject()) {
            Assertions.assertTrue(actualResponseNode.size() >= 0, "Object should have valid field count");
        }
    }

    @Test
    public void testListEvents() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":[{\"api_key\":\"api_key\",\"body\":{\"key\":\"value\"},\"body_preview\":\"body_preview\",\"captured_at\":\"2024-01-15T09:30:00Z\",\"company\":{\"id\":\"id\",\"name\":\"name\"},\"company_id\":\"company_id\",\"enriched_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"error_message\":\"error_message\",\"feature_ids\":[\"feature_ids\"],\"features\":[{\"id\":\"id\",\"name\":\"name\"}],\"id\":\"id\",\"loaded_at\":\"2024-01-15T09:30:00Z\",\"processed_at\":\"2024-01-15T09:30:00Z\",\"quantity\":1,\"sent_at\":\"2024-01-15T09:30:00Z\",\"status\":\"status\",\"subtype\":\"subtype\",\"type\":\"type\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"user\":{\"id\":\"id\",\"name\":\"name\"},\"user_id\":\"user_id\"}],\"params\":{\"company_id\":\"company_id\",\"event_subtype\":\"event_subtype\",\"event_types\":[\"identify\"],\"flag_id\":\"flag_id\",\"limit\":1,\"offset\":1,\"user_id\":\"user_id\"}}"));
        ListEventsResponse response = client.events()
                .listEvents(ListEventsRequest.builder()
                        .companyId("company_id")
                        .eventSubtype("event_subtype")
                        .flagId("flag_id")
                        .userId("user_id")
                        .limit(1)
                        .offset(1)
                        .build());
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("GET", request.getMethod());

        // Validate response body
        Assertions.assertNotNull(response, "Response should not be null");
        String actualResponseJson = objectMapper.writeValueAsString(response);
        String expectedResponseBody = ""
                + "{\n"
                + "  \"data\": [\n"
                + "    {\n"
                + "      \"api_key\": \"api_key\",\n"
                + "      \"body\": {\n"
                + "        \"key\": \"value\"\n"
                + "      },\n"
                + "      \"body_preview\": \"body_preview\",\n"
                + "      \"captured_at\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"company\": {\n"
                + "        \"id\": \"id\",\n"
                + "        \"name\": \"name\"\n"
                + "      },\n"
                + "      \"company_id\": \"company_id\",\n"
                + "      \"enriched_at\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"environment_id\": \"environment_id\",\n"
                + "      \"error_message\": \"error_message\",\n"
                + "      \"feature_ids\": [\n"
                + "        \"feature_ids\"\n"
                + "      ],\n"
                + "      \"features\": [\n"
                + "        {\n"
                + "          \"id\": \"id\",\n"
                + "          \"name\": \"name\"\n"
                + "        }\n"
                + "      ],\n"
                + "      \"id\": \"id\",\n"
                + "      \"loaded_at\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"processed_at\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"quantity\": 1,\n"
                + "      \"sent_at\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"status\": \"status\",\n"
                + "      \"subtype\": \"subtype\",\n"
                + "      \"type\": \"type\",\n"
                + "      \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"user\": {\n"
                + "        \"id\": \"id\",\n"
                + "        \"name\": \"name\"\n"
                + "      },\n"
                + "      \"user_id\": \"user_id\"\n"
                + "    }\n"
                + "  ],\n"
                + "  \"params\": {\n"
                + "    \"company_id\": \"company_id\",\n"
                + "    \"event_subtype\": \"event_subtype\",\n"
                + "    \"event_types\": [\n"
                + "      \"identify\"\n"
                + "    ],\n"
                + "    \"flag_id\": \"flag_id\",\n"
                + "    \"limit\": 1,\n"
                + "    \"offset\": 1,\n"
                + "    \"user_id\": \"user_id\"\n"
                + "  }\n"
                + "}";
        JsonNode actualResponseNode = objectMapper.readTree(actualResponseJson);
        JsonNode expectedResponseNode = objectMapper.readTree(expectedResponseBody);
        Assertions.assertEquals(
                expectedResponseNode, actualResponseNode, "Response body structure does not match expected");
        if (actualResponseNode.has("type") || actualResponseNode.has("_type") || actualResponseNode.has("kind")) {
            String discriminator = null;
            if (actualResponseNode.has("type"))
                discriminator = actualResponseNode.get("type").asText();
            else if (actualResponseNode.has("_type"))
                discriminator = actualResponseNode.get("_type").asText();
            else if (actualResponseNode.has("kind"))
                discriminator = actualResponseNode.get("kind").asText();
            Assertions.assertNotNull(discriminator, "Union type should have a discriminator field");
            Assertions.assertFalse(discriminator.isEmpty(), "Union discriminator should not be empty");
        }

        if (!actualResponseNode.isNull()) {
            Assertions.assertTrue(
                    actualResponseNode.isObject() || actualResponseNode.isArray() || actualResponseNode.isValueNode(),
                    "response should be a valid JSON value");
        }

        if (actualResponseNode.isArray()) {
            Assertions.assertTrue(actualResponseNode.size() >= 0, "Array should have valid size");
        }
        if (actualResponseNode.isObject()) {
            Assertions.assertTrue(actualResponseNode.size() >= 0, "Object should have valid field count");
        }
    }

    @Test
    public void testCreateEvent() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":{\"captured_at\":\"2024-01-15T09:30:00Z\",\"event_id\":\"event_id\",\"remote_addr\":\"remote_addr\",\"remote_ip\":\"remote_ip\",\"user_agent\":\"user_agent\"},\"params\":{\"key\":\"value\"}}"));
        CreateEventResponse response = client.events()
                .createEvent(CreateEventRequestBody.builder()
                        .eventType(CreateEventRequestBodyEventType.IDENTIFY)
                        .build());
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("POST", request.getMethod());
        // Validate request body
        String actualRequestBody = request.getBody().readUtf8();
        String expectedRequestBody = "" + "{\n" + "  \"event_type\": \"identify\"\n" + "}";
        JsonNode actualJson = objectMapper.readTree(actualRequestBody);
        JsonNode expectedJson = objectMapper.readTree(expectedRequestBody);
        Assertions.assertEquals(expectedJson, actualJson, "Request body structure does not match expected");
        if (actualJson.has("type") || actualJson.has("_type") || actualJson.has("kind")) {
            String discriminator = null;
            if (actualJson.has("type")) discriminator = actualJson.get("type").asText();
            else if (actualJson.has("_type"))
                discriminator = actualJson.get("_type").asText();
            else if (actualJson.has("kind"))
                discriminator = actualJson.get("kind").asText();
            Assertions.assertNotNull(discriminator, "Union type should have a discriminator field");
            Assertions.assertFalse(discriminator.isEmpty(), "Union discriminator should not be empty");
        }

        if (!actualJson.isNull()) {
            Assertions.assertTrue(
                    actualJson.isObject() || actualJson.isArray() || actualJson.isValueNode(),
                    "request should be a valid JSON value");
        }

        if (actualJson.isArray()) {
            Assertions.assertTrue(actualJson.size() >= 0, "Array should have valid size");
        }
        if (actualJson.isObject()) {
            Assertions.assertTrue(actualJson.size() >= 0, "Object should have valid field count");
        }

        // Validate response body
        Assertions.assertNotNull(response, "Response should not be null");
        String actualResponseJson = objectMapper.writeValueAsString(response);
        String expectedResponseBody = ""
                + "{\n"
                + "  \"data\": {\n"
                + "    \"captured_at\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"event_id\": \"event_id\",\n"
                + "    \"remote_addr\": \"remote_addr\",\n"
                + "    \"remote_ip\": \"remote_ip\",\n"
                + "    \"user_agent\": \"user_agent\"\n"
                + "  },\n"
                + "  \"params\": {\n"
                + "    \"key\": \"value\"\n"
                + "  }\n"
                + "}";
        JsonNode actualResponseNode = objectMapper.readTree(actualResponseJson);
        JsonNode expectedResponseNode = objectMapper.readTree(expectedResponseBody);
        Assertions.assertEquals(
                expectedResponseNode, actualResponseNode, "Response body structure does not match expected");
        if (actualResponseNode.has("type") || actualResponseNode.has("_type") || actualResponseNode.has("kind")) {
            String discriminator = null;
            if (actualResponseNode.has("type"))
                discriminator = actualResponseNode.get("type").asText();
            else if (actualResponseNode.has("_type"))
                discriminator = actualResponseNode.get("_type").asText();
            else if (actualResponseNode.has("kind"))
                discriminator = actualResponseNode.get("kind").asText();
            Assertions.assertNotNull(discriminator, "Union type should have a discriminator field");
            Assertions.assertFalse(discriminator.isEmpty(), "Union discriminator should not be empty");
        }

        if (!actualResponseNode.isNull()) {
            Assertions.assertTrue(
                    actualResponseNode.isObject() || actualResponseNode.isArray() || actualResponseNode.isValueNode(),
                    "response should be a valid JSON value");
        }

        if (actualResponseNode.isArray()) {
            Assertions.assertTrue(actualResponseNode.size() >= 0, "Array should have valid size");
        }
        if (actualResponseNode.isObject()) {
            Assertions.assertTrue(actualResponseNode.size() >= 0, "Object should have valid field count");
        }
    }

    @Test
    public void testGetEvent() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":{\"api_key\":\"api_key\",\"body\":{\"key\":\"value\"},\"body_preview\":\"body_preview\",\"captured_at\":\"2024-01-15T09:30:00Z\",\"company\":{\"description\":\"description\",\"id\":\"id\",\"image_url\":\"image_url\",\"name\":\"name\"},\"company_id\":\"company_id\",\"enriched_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"error_message\":\"error_message\",\"feature_ids\":[\"feature_ids\"],\"features\":[{\"id\":\"id\",\"name\":\"name\"}],\"id\":\"id\",\"loaded_at\":\"2024-01-15T09:30:00Z\",\"processed_at\":\"2024-01-15T09:30:00Z\",\"quantity\":1,\"sent_at\":\"2024-01-15T09:30:00Z\",\"status\":\"status\",\"subtype\":\"subtype\",\"type\":\"type\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"user\":{\"description\":\"description\",\"id\":\"id\",\"image_url\":\"image_url\",\"name\":\"name\"},\"user_id\":\"user_id\"},\"params\":{\"key\":\"value\"}}"));
        GetEventResponse response = client.events().getEvent("event_id");
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("GET", request.getMethod());

        // Validate response body
        Assertions.assertNotNull(response, "Response should not be null");
        String actualResponseJson = objectMapper.writeValueAsString(response);
        String expectedResponseBody = ""
                + "{\n"
                + "  \"data\": {\n"
                + "    \"api_key\": \"api_key\",\n"
                + "    \"body\": {\n"
                + "      \"key\": \"value\"\n"
                + "    },\n"
                + "    \"body_preview\": \"body_preview\",\n"
                + "    \"captured_at\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"company\": {\n"
                + "      \"description\": \"description\",\n"
                + "      \"id\": \"id\",\n"
                + "      \"image_url\": \"image_url\",\n"
                + "      \"name\": \"name\"\n"
                + "    },\n"
                + "    \"company_id\": \"company_id\",\n"
                + "    \"enriched_at\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"environment_id\": \"environment_id\",\n"
                + "    \"error_message\": \"error_message\",\n"
                + "    \"feature_ids\": [\n"
                + "      \"feature_ids\"\n"
                + "    ],\n"
                + "    \"features\": [\n"
                + "      {\n"
                + "        \"id\": \"id\",\n"
                + "        \"name\": \"name\"\n"
                + "      }\n"
                + "    ],\n"
                + "    \"id\": \"id\",\n"
                + "    \"loaded_at\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"processed_at\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"quantity\": 1,\n"
                + "    \"sent_at\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"status\": \"status\",\n"
                + "    \"subtype\": \"subtype\",\n"
                + "    \"type\": \"type\",\n"
                + "    \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"user\": {\n"
                + "      \"description\": \"description\",\n"
                + "      \"id\": \"id\",\n"
                + "      \"image_url\": \"image_url\",\n"
                + "      \"name\": \"name\"\n"
                + "    },\n"
                + "    \"user_id\": \"user_id\"\n"
                + "  },\n"
                + "  \"params\": {\n"
                + "    \"key\": \"value\"\n"
                + "  }\n"
                + "}";
        JsonNode actualResponseNode = objectMapper.readTree(actualResponseJson);
        JsonNode expectedResponseNode = objectMapper.readTree(expectedResponseBody);
        Assertions.assertEquals(
                expectedResponseNode, actualResponseNode, "Response body structure does not match expected");
        if (actualResponseNode.has("type") || actualResponseNode.has("_type") || actualResponseNode.has("kind")) {
            String discriminator = null;
            if (actualResponseNode.has("type"))
                discriminator = actualResponseNode.get("type").asText();
            else if (actualResponseNode.has("_type"))
                discriminator = actualResponseNode.get("_type").asText();
            else if (actualResponseNode.has("kind"))
                discriminator = actualResponseNode.get("kind").asText();
            Assertions.assertNotNull(discriminator, "Union type should have a discriminator field");
            Assertions.assertFalse(discriminator.isEmpty(), "Union discriminator should not be empty");
        }

        if (!actualResponseNode.isNull()) {
            Assertions.assertTrue(
                    actualResponseNode.isObject() || actualResponseNode.isArray() || actualResponseNode.isValueNode(),
                    "response should be a valid JSON value");
        }

        if (actualResponseNode.isArray()) {
            Assertions.assertTrue(actualResponseNode.size() >= 0, "Array should have valid size");
        }
        if (actualResponseNode.isObject()) {
            Assertions.assertTrue(actualResponseNode.size() >= 0, "Object should have valid field count");
        }
    }

    @Test
    public void testGetSegmentIntegrationStatus() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":{\"connected\":true,\"environment_id\":\"environment_id\",\"last_event_at\":\"2024-01-15T09:30:00Z\"},\"params\":{\"key\":\"value\"}}"));
        GetSegmentIntegrationStatusResponse response = client.events().getSegmentIntegrationStatus();
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("GET", request.getMethod());

        // Validate response body
        Assertions.assertNotNull(response, "Response should not be null");
        String actualResponseJson = objectMapper.writeValueAsString(response);
        String expectedResponseBody = ""
                + "{\n"
                + "  \"data\": {\n"
                + "    \"connected\": true,\n"
                + "    \"environment_id\": \"environment_id\",\n"
                + "    \"last_event_at\": \"2024-01-15T09:30:00Z\"\n"
                + "  },\n"
                + "  \"params\": {\n"
                + "    \"key\": \"value\"\n"
                + "  }\n"
                + "}";
        JsonNode actualResponseNode = objectMapper.readTree(actualResponseJson);
        JsonNode expectedResponseNode = objectMapper.readTree(expectedResponseBody);
        Assertions.assertEquals(
                expectedResponseNode, actualResponseNode, "Response body structure does not match expected");
        if (actualResponseNode.has("type") || actualResponseNode.has("_type") || actualResponseNode.has("kind")) {
            String discriminator = null;
            if (actualResponseNode.has("type"))
                discriminator = actualResponseNode.get("type").asText();
            else if (actualResponseNode.has("_type"))
                discriminator = actualResponseNode.get("_type").asText();
            else if (actualResponseNode.has("kind"))
                discriminator = actualResponseNode.get("kind").asText();
            Assertions.assertNotNull(discriminator, "Union type should have a discriminator field");
            Assertions.assertFalse(discriminator.isEmpty(), "Union discriminator should not be empty");
        }

        if (!actualResponseNode.isNull()) {
            Assertions.assertTrue(
                    actualResponseNode.isObject() || actualResponseNode.isArray() || actualResponseNode.isValueNode(),
                    "response should be a valid JSON value");
        }

        if (actualResponseNode.isArray()) {
            Assertions.assertTrue(actualResponseNode.size() >= 0, "Array should have valid size");
        }
        if (actualResponseNode.isObject()) {
            Assertions.assertTrue(actualResponseNode.size() >= 0, "Object should have valid field count");
        }
    }
}
