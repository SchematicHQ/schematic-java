package com.schematic.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.schematic.api.core.ObjectMappers;
import com.schematic.api.resources.webhooks.requests.CountWebhookEventsRequest;
import com.schematic.api.resources.webhooks.requests.CountWebhooksRequest;
import com.schematic.api.resources.webhooks.requests.CreateWebhookRequestBody;
import com.schematic.api.resources.webhooks.requests.ListWebhookEventsRequest;
import com.schematic.api.resources.webhooks.requests.ListWebhooksRequest;
import com.schematic.api.resources.webhooks.requests.UpdateWebhookRequestBody;
import com.schematic.api.resources.webhooks.types.CountWebhookEventsResponse;
import com.schematic.api.resources.webhooks.types.CountWebhooksResponse;
import com.schematic.api.resources.webhooks.types.CreateWebhookRequestBodyRequestTypesItem;
import com.schematic.api.resources.webhooks.types.CreateWebhookResponse;
import com.schematic.api.resources.webhooks.types.DeleteWebhookResponse;
import com.schematic.api.resources.webhooks.types.GetWebhookEventResponse;
import com.schematic.api.resources.webhooks.types.GetWebhookResponse;
import com.schematic.api.resources.webhooks.types.ListWebhookEventsResponse;
import com.schematic.api.resources.webhooks.types.ListWebhooksResponse;
import com.schematic.api.resources.webhooks.types.UpdateWebhookResponse;
import java.util.Arrays;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WebhooksWireTest {
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
    public void testListWebhookEvents() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"id\":\"id\",\"payload\":\"payload\",\"request_type\":\"request_type\",\"response_code\":1,\"sent_at\":\"2024-01-15T09:30:00Z\",\"status\":\"status\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"webhook\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"id\":\"id\",\"name\":\"name\",\"request_types\":[\"request_types\"],\"secret\":\"secret\",\"status\":\"status\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"url\":\"url\"},\"webhook_id\":\"webhook_id\"}],\"params\":{\"ids\":[\"ids\"],\"limit\":1,\"offset\":1,\"q\":\"q\",\"webhook_id\":\"webhook_id\"}}"));
        ListWebhookEventsResponse response = client.webhooks()
                .listWebhookEvents(ListWebhookEventsRequest.builder()
                        .q("q")
                        .webhookId("webhook_id")
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
                + "      \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"id\": \"id\",\n"
                + "      \"payload\": \"payload\",\n"
                + "      \"request_type\": \"request_type\",\n"
                + "      \"response_code\": 1,\n"
                + "      \"sent_at\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"status\": \"status\",\n"
                + "      \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"webhook\": {\n"
                + "        \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"id\": \"id\",\n"
                + "        \"name\": \"name\",\n"
                + "        \"request_types\": [\n"
                + "          \"request_types\"\n"
                + "        ],\n"
                + "        \"secret\": \"secret\",\n"
                + "        \"status\": \"status\",\n"
                + "        \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"url\": \"url\"\n"
                + "      },\n"
                + "      \"webhook_id\": \"webhook_id\"\n"
                + "    }\n"
                + "  ],\n"
                + "  \"params\": {\n"
                + "    \"ids\": [\n"
                + "      \"ids\"\n"
                + "    ],\n"
                + "    \"limit\": 1,\n"
                + "    \"offset\": 1,\n"
                + "    \"q\": \"q\",\n"
                + "    \"webhook_id\": \"webhook_id\"\n"
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
    public void testGetWebhookEvent() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"id\":\"id\",\"payload\":\"payload\",\"request_type\":\"request_type\",\"response_code\":1,\"sent_at\":\"2024-01-15T09:30:00Z\",\"status\":\"status\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"webhook\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"credit_trigger_configs\":[{\"credit_id\":\"credit_id\"}],\"entitlement_trigger_configs\":[{\"feature_id\":\"feature_id\"}],\"id\":\"id\",\"name\":\"name\",\"request_types\":[\"request_types\"],\"secret\":\"secret\",\"status\":\"status\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"url\":\"url\"},\"webhook_id\":\"webhook_id\"},\"params\":{\"key\":\"value\"}}"));
        GetWebhookEventResponse response = client.webhooks().getWebhookEvent("webhook_event_id");
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("GET", request.getMethod());

        // Validate response body
        Assertions.assertNotNull(response, "Response should not be null");
        String actualResponseJson = objectMapper.writeValueAsString(response);
        String expectedResponseBody = ""
                + "{\n"
                + "  \"data\": {\n"
                + "    \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"id\": \"id\",\n"
                + "    \"payload\": \"payload\",\n"
                + "    \"request_type\": \"request_type\",\n"
                + "    \"response_code\": 1,\n"
                + "    \"sent_at\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"status\": \"status\",\n"
                + "    \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"webhook\": {\n"
                + "      \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"credit_trigger_configs\": [\n"
                + "        {\n"
                + "          \"credit_id\": \"credit_id\"\n"
                + "        }\n"
                + "      ],\n"
                + "      \"entitlement_trigger_configs\": [\n"
                + "        {\n"
                + "          \"feature_id\": \"feature_id\"\n"
                + "        }\n"
                + "      ],\n"
                + "      \"id\": \"id\",\n"
                + "      \"name\": \"name\",\n"
                + "      \"request_types\": [\n"
                + "        \"request_types\"\n"
                + "      ],\n"
                + "      \"secret\": \"secret\",\n"
                + "      \"status\": \"status\",\n"
                + "      \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"url\": \"url\"\n"
                + "    },\n"
                + "    \"webhook_id\": \"webhook_id\"\n"
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
    public void testCountWebhookEvents() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":{\"count\":1},\"params\":{\"ids\":[\"ids\"],\"limit\":1,\"offset\":1,\"q\":\"q\",\"webhook_id\":\"webhook_id\"}}"));
        CountWebhookEventsResponse response = client.webhooks()
                .countWebhookEvents(CountWebhookEventsRequest.builder()
                        .q("q")
                        .webhookId("webhook_id")
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
                + "  \"data\": {\n"
                + "    \"count\": 1\n"
                + "  },\n"
                + "  \"params\": {\n"
                + "    \"ids\": [\n"
                + "      \"ids\"\n"
                + "    ],\n"
                + "    \"limit\": 1,\n"
                + "    \"offset\": 1,\n"
                + "    \"q\": \"q\",\n"
                + "    \"webhook_id\": \"webhook_id\"\n"
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
    public void testListWebhooks() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"credit_trigger_configs\":[{\"credit_id\":\"credit_id\"}],\"entitlement_trigger_configs\":[{\"feature_id\":\"feature_id\"}],\"id\":\"id\",\"name\":\"name\",\"request_types\":[\"request_types\"],\"secret\":\"secret\",\"status\":\"status\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"url\":\"url\"}],\"params\":{\"limit\":1,\"offset\":1,\"q\":\"q\"}}"));
        ListWebhooksResponse response = client.webhooks()
                .listWebhooks(
                        ListWebhooksRequest.builder().q("q").limit(1).offset(1).build());
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
                + "      \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"credit_trigger_configs\": [\n"
                + "        {\n"
                + "          \"credit_id\": \"credit_id\"\n"
                + "        }\n"
                + "      ],\n"
                + "      \"entitlement_trigger_configs\": [\n"
                + "        {\n"
                + "          \"feature_id\": \"feature_id\"\n"
                + "        }\n"
                + "      ],\n"
                + "      \"id\": \"id\",\n"
                + "      \"name\": \"name\",\n"
                + "      \"request_types\": [\n"
                + "        \"request_types\"\n"
                + "      ],\n"
                + "      \"secret\": \"secret\",\n"
                + "      \"status\": \"status\",\n"
                + "      \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"url\": \"url\"\n"
                + "    }\n"
                + "  ],\n"
                + "  \"params\": {\n"
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
    public void testCreateWebhook() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"credit_trigger_configs\":[{\"credit_id\":\"credit_id\"}],\"entitlement_trigger_configs\":[{\"feature_id\":\"feature_id\"}],\"id\":\"id\",\"name\":\"name\",\"request_types\":[\"request_types\"],\"secret\":\"secret\",\"status\":\"status\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"url\":\"url\"},\"params\":{\"key\":\"value\"}}"));
        CreateWebhookResponse response = client.webhooks()
                .createWebhook(CreateWebhookRequestBody.builder()
                        .name("name")
                        .requestTypes(Arrays.asList(CreateWebhookRequestBodyRequestTypesItem.COMPANY_UPDATED))
                        .url("url")
                        .build());
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("POST", request.getMethod());
        // Validate request body
        String actualRequestBody = request.getBody().readUtf8();
        String expectedRequestBody = ""
                + "{\n"
                + "  \"name\": \"name\",\n"
                + "  \"request_types\": [\n"
                + "    \"company.updated\"\n"
                + "  ],\n"
                + "  \"url\": \"url\"\n"
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
                + "    \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"credit_trigger_configs\": [\n"
                + "      {\n"
                + "        \"credit_id\": \"credit_id\"\n"
                + "      }\n"
                + "    ],\n"
                + "    \"entitlement_trigger_configs\": [\n"
                + "      {\n"
                + "        \"feature_id\": \"feature_id\"\n"
                + "      }\n"
                + "    ],\n"
                + "    \"id\": \"id\",\n"
                + "    \"name\": \"name\",\n"
                + "    \"request_types\": [\n"
                + "      \"request_types\"\n"
                + "    ],\n"
                + "    \"secret\": \"secret\",\n"
                + "    \"status\": \"status\",\n"
                + "    \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"url\": \"url\"\n"
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
    public void testGetWebhook() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"credit_trigger_configs\":[{\"credit_id\":\"credit_id\"}],\"entitlement_trigger_configs\":[{\"feature_id\":\"feature_id\"}],\"id\":\"id\",\"name\":\"name\",\"request_types\":[\"request_types\"],\"secret\":\"secret\",\"status\":\"status\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"url\":\"url\"},\"params\":{\"key\":\"value\"}}"));
        GetWebhookResponse response = client.webhooks().getWebhook("webhook_id");
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("GET", request.getMethod());

        // Validate response body
        Assertions.assertNotNull(response, "Response should not be null");
        String actualResponseJson = objectMapper.writeValueAsString(response);
        String expectedResponseBody = ""
                + "{\n"
                + "  \"data\": {\n"
                + "    \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"credit_trigger_configs\": [\n"
                + "      {\n"
                + "        \"credit_id\": \"credit_id\"\n"
                + "      }\n"
                + "    ],\n"
                + "    \"entitlement_trigger_configs\": [\n"
                + "      {\n"
                + "        \"feature_id\": \"feature_id\"\n"
                + "      }\n"
                + "    ],\n"
                + "    \"id\": \"id\",\n"
                + "    \"name\": \"name\",\n"
                + "    \"request_types\": [\n"
                + "      \"request_types\"\n"
                + "    ],\n"
                + "    \"secret\": \"secret\",\n"
                + "    \"status\": \"status\",\n"
                + "    \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"url\": \"url\"\n"
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
    public void testUpdateWebhook() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"credit_trigger_configs\":[{\"credit_id\":\"credit_id\"}],\"entitlement_trigger_configs\":[{\"feature_id\":\"feature_id\"}],\"id\":\"id\",\"name\":\"name\",\"request_types\":[\"request_types\"],\"secret\":\"secret\",\"status\":\"status\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"url\":\"url\"},\"params\":{\"key\":\"value\"}}"));
        UpdateWebhookResponse response = client.webhooks()
                .updateWebhook("webhook_id", UpdateWebhookRequestBody.builder().build());
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("PUT", request.getMethod());
        // Validate request body
        String actualRequestBody = request.getBody().readUtf8();
        String expectedRequestBody = "" + "{}";
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
                + "    \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"credit_trigger_configs\": [\n"
                + "      {\n"
                + "        \"credit_id\": \"credit_id\"\n"
                + "      }\n"
                + "    ],\n"
                + "    \"entitlement_trigger_configs\": [\n"
                + "      {\n"
                + "        \"feature_id\": \"feature_id\"\n"
                + "      }\n"
                + "    ],\n"
                + "    \"id\": \"id\",\n"
                + "    \"name\": \"name\",\n"
                + "    \"request_types\": [\n"
                + "      \"request_types\"\n"
                + "    ],\n"
                + "    \"secret\": \"secret\",\n"
                + "    \"status\": \"status\",\n"
                + "    \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"url\": \"url\"\n"
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
    public void testDeleteWebhook() throws Exception {
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody("{\"data\":{\"deleted\":true},\"params\":{\"key\":\"value\"}}"));
        DeleteWebhookResponse response = client.webhooks().deleteWebhook("webhook_id");
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("DELETE", request.getMethod());

        // Validate response body
        Assertions.assertNotNull(response, "Response should not be null");
        String actualResponseJson = objectMapper.writeValueAsString(response);
        String expectedResponseBody = ""
                + "{\n"
                + "  \"data\": {\n"
                + "    \"deleted\": true\n"
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
    public void testCountWebhooks() throws Exception {
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody("{\"data\":{\"count\":1},\"params\":{\"limit\":1,\"offset\":1,\"q\":\"q\"}}"));
        CountWebhooksResponse response = client.webhooks()
                .countWebhooks(
                        CountWebhooksRequest.builder().q("q").limit(1).offset(1).build());
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("GET", request.getMethod());

        // Validate response body
        Assertions.assertNotNull(response, "Response should not be null");
        String actualResponseJson = objectMapper.writeValueAsString(response);
        String expectedResponseBody = ""
                + "{\n"
                + "  \"data\": {\n"
                + "    \"count\": 1\n"
                + "  },\n"
                + "  \"params\": {\n"
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
}
