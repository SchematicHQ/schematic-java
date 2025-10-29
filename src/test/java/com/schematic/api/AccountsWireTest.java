package com.schematic.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.schematic.api.core.ObjectMappers;
import com.schematic.api.resources.accounts.requests.CountApiKeysRequest;
import com.schematic.api.resources.accounts.requests.CountApiRequestsRequest;
import com.schematic.api.resources.accounts.requests.CreateApiKeyRequestBody;
import com.schematic.api.resources.accounts.requests.CreateEnvironmentRequestBody;
import com.schematic.api.resources.accounts.requests.ListApiKeysRequest;
import com.schematic.api.resources.accounts.requests.ListApiRequestsRequest;
import com.schematic.api.resources.accounts.requests.ListEnvironmentsRequest;
import com.schematic.api.resources.accounts.requests.UpdateApiKeyRequestBody;
import com.schematic.api.resources.accounts.requests.UpdateEnvironmentRequestBody;
import com.schematic.api.resources.accounts.types.CountApiKeysResponse;
import com.schematic.api.resources.accounts.types.CountApiRequestsResponse;
import com.schematic.api.resources.accounts.types.CreateApiKeyResponse;
import com.schematic.api.resources.accounts.types.CreateEnvironmentRequestBodyEnvironmentType;
import com.schematic.api.resources.accounts.types.CreateEnvironmentResponse;
import com.schematic.api.resources.accounts.types.DeleteApiKeyResponse;
import com.schematic.api.resources.accounts.types.DeleteEnvironmentResponse;
import com.schematic.api.resources.accounts.types.GetApiKeyResponse;
import com.schematic.api.resources.accounts.types.GetApiRequestResponse;
import com.schematic.api.resources.accounts.types.GetEnvironmentResponse;
import com.schematic.api.resources.accounts.types.ListApiKeysResponse;
import com.schematic.api.resources.accounts.types.ListApiRequestsResponse;
import com.schematic.api.resources.accounts.types.ListEnvironmentsResponse;
import com.schematic.api.resources.accounts.types.QuickstartResponse;
import com.schematic.api.resources.accounts.types.UpdateApiKeyResponse;
import com.schematic.api.resources.accounts.types.UpdateEnvironmentResponse;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AccountsWireTest {
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
    public void testListApiKeys() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"description\":\"description\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"last_used_at\":\"2024-01-15T09:30:00Z\",\"name\":\"name\",\"scopes\":[\"scopes\"],\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"params\":{\"environment_id\":\"environment_id\",\"limit\":1,\"offset\":1,\"require_environment\":true}}"));
        ListApiKeysResponse response = client.accounts()
                .listApiKeys(ListApiKeysRequest.builder()
                        .requireEnvironment(true)
                        .environmentId("environment_id")
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
                + "      \"description\": \"description\",\n"
                + "      \"environment_id\": \"environment_id\",\n"
                + "      \"id\": \"id\",\n"
                + "      \"last_used_at\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"name\": \"name\",\n"
                + "      \"scopes\": [\n"
                + "        \"scopes\"\n"
                + "      ],\n"
                + "      \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "    }\n"
                + "  ],\n"
                + "  \"params\": {\n"
                + "    \"environment_id\": \"environment_id\",\n"
                + "    \"limit\": 1,\n"
                + "    \"offset\": 1,\n"
                + "    \"require_environment\": true\n"
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
    public void testCreateApiKey() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"description\":\"description\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"last_used_at\":\"2024-01-15T09:30:00Z\",\"name\":\"name\",\"scopes\":[\"scopes\"],\"secret\":\"secret\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"params\":{\"key\":\"value\"}}"));
        CreateApiKeyResponse response = client.accounts()
                .createApiKey(CreateApiKeyRequestBody.builder().name("name").build());
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("POST", request.getMethod());
        // Validate request body
        String actualRequestBody = request.getBody().readUtf8();
        String expectedRequestBody = "" + "{\n" + "  \"name\": \"name\"\n" + "}";
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
                + "    \"description\": \"description\",\n"
                + "    \"environment_id\": \"environment_id\",\n"
                + "    \"id\": \"id\",\n"
                + "    \"last_used_at\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"name\": \"name\",\n"
                + "    \"scopes\": [\n"
                + "      \"scopes\"\n"
                + "    ],\n"
                + "    \"secret\": \"secret\",\n"
                + "    \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
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
    public void testGetApiKey() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"description\":\"description\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"last_used_at\":\"2024-01-15T09:30:00Z\",\"name\":\"name\",\"scopes\":[\"scopes\"],\"updated_at\":\"2024-01-15T09:30:00Z\"},\"params\":{\"key\":\"value\"}}"));
        GetApiKeyResponse response = client.accounts().getApiKey("api_key_id");
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
                + "    \"description\": \"description\",\n"
                + "    \"environment_id\": \"environment_id\",\n"
                + "    \"id\": \"id\",\n"
                + "    \"last_used_at\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"name\": \"name\",\n"
                + "    \"scopes\": [\n"
                + "      \"scopes\"\n"
                + "    ],\n"
                + "    \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
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
    public void testUpdateApiKey() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"description\":\"description\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"last_used_at\":\"2024-01-15T09:30:00Z\",\"name\":\"name\",\"scopes\":[\"scopes\"],\"updated_at\":\"2024-01-15T09:30:00Z\"},\"params\":{\"key\":\"value\"}}"));
        UpdateApiKeyResponse response = client.accounts()
                .updateApiKey("api_key_id", UpdateApiKeyRequestBody.builder().build());
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
                + "    \"description\": \"description\",\n"
                + "    \"environment_id\": \"environment_id\",\n"
                + "    \"id\": \"id\",\n"
                + "    \"last_used_at\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"name\": \"name\",\n"
                + "    \"scopes\": [\n"
                + "      \"scopes\"\n"
                + "    ],\n"
                + "    \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
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
    public void testDeleteApiKey() throws Exception {
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody("{\"data\":{\"deleted\":true},\"params\":{\"key\":\"value\"}}"));
        DeleteApiKeyResponse response = client.accounts().deleteApiKey("api_key_id");
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
    public void testCountApiKeys() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":{\"count\":1},\"params\":{\"environment_id\":\"environment_id\",\"limit\":1,\"offset\":1,\"require_environment\":true}}"));
        CountApiKeysResponse response = client.accounts()
                .countApiKeys(CountApiKeysRequest.builder()
                        .requireEnvironment(true)
                        .environmentId("environment_id")
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
                + "    \"environment_id\": \"environment_id\",\n"
                + "    \"limit\": 1,\n"
                + "    \"offset\": 1,\n"
                + "    \"require_environment\": true\n"
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
    public void testListApiRequests() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":[{\"api_key_id\":\"api_key_id\",\"ended_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"method\":\"method\",\"req_body\":\"req_body\",\"request_type\":\"request_type\",\"resource_id\":1,\"resource_id_string\":\"resource_id_string\",\"resource_name\":\"resource_name\",\"resource_type\":\"resource_type\",\"resp_code\":1,\"secondary_resource\":\"secondary_resource\",\"started_at\":\"2024-01-15T09:30:00Z\",\"url\":\"url\",\"user_name\":\"user_name\"}],\"params\":{\"environment_id\":\"environment_id\",\"limit\":1,\"offset\":1,\"q\":\"q\",\"request_type\":\"request_type\"}}"));
        ListApiRequestsResponse response = client.accounts()
                .listApiRequests(ListApiRequestsRequest.builder()
                        .q("q")
                        .requestType("request_type")
                        .environmentId("environment_id")
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
                + "      \"api_key_id\": \"api_key_id\",\n"
                + "      \"ended_at\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"environment_id\": \"environment_id\",\n"
                + "      \"id\": \"id\",\n"
                + "      \"method\": \"method\",\n"
                + "      \"req_body\": \"req_body\",\n"
                + "      \"request_type\": \"request_type\",\n"
                + "      \"resource_id\": 1,\n"
                + "      \"resource_id_string\": \"resource_id_string\",\n"
                + "      \"resource_name\": \"resource_name\",\n"
                + "      \"resource_type\": \"resource_type\",\n"
                + "      \"resp_code\": 1,\n"
                + "      \"secondary_resource\": \"secondary_resource\",\n"
                + "      \"started_at\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"url\": \"url\",\n"
                + "      \"user_name\": \"user_name\"\n"
                + "    }\n"
                + "  ],\n"
                + "  \"params\": {\n"
                + "    \"environment_id\": \"environment_id\",\n"
                + "    \"limit\": 1,\n"
                + "    \"offset\": 1,\n"
                + "    \"q\": \"q\",\n"
                + "    \"request_type\": \"request_type\"\n"
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
    public void testGetApiRequest() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":{\"api_key\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"description\":\"description\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"last_used_at\":\"2024-01-15T09:30:00Z\",\"name\":\"name\",\"scopes\":[\"scopes\"],\"updated_at\":\"2024-01-15T09:30:00Z\"},\"api_key_id\":\"api_key_id\",\"ended_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"method\":\"method\",\"req_body\":\"req_body\",\"request_type\":\"request_type\",\"resource_id\":1,\"resource_id_string\":\"resource_id_string\",\"resource_name\":\"resource_name\",\"resource_type\":\"resource_type\",\"resp_body\":\"resp_body\",\"resp_code\":1,\"secondary_resource\":\"secondary_resource\",\"started_at\":\"2024-01-15T09:30:00Z\",\"url\":\"url\",\"user_agent\":\"user_agent\",\"user_id\":\"user_id\",\"user_name\":\"user_name\"},\"params\":{\"key\":\"value\"}}"));
        GetApiRequestResponse response = client.accounts().getApiRequest("api_request_id");
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("GET", request.getMethod());

        // Validate response body
        Assertions.assertNotNull(response, "Response should not be null");
        String actualResponseJson = objectMapper.writeValueAsString(response);
        String expectedResponseBody = ""
                + "{\n"
                + "  \"data\": {\n"
                + "    \"api_key\": {\n"
                + "      \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"description\": \"description\",\n"
                + "      \"environment_id\": \"environment_id\",\n"
                + "      \"id\": \"id\",\n"
                + "      \"last_used_at\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"name\": \"name\",\n"
                + "      \"scopes\": [\n"
                + "        \"scopes\"\n"
                + "      ],\n"
                + "      \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "    },\n"
                + "    \"api_key_id\": \"api_key_id\",\n"
                + "    \"ended_at\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"environment_id\": \"environment_id\",\n"
                + "    \"id\": \"id\",\n"
                + "    \"method\": \"method\",\n"
                + "    \"req_body\": \"req_body\",\n"
                + "    \"request_type\": \"request_type\",\n"
                + "    \"resource_id\": 1,\n"
                + "    \"resource_id_string\": \"resource_id_string\",\n"
                + "    \"resource_name\": \"resource_name\",\n"
                + "    \"resource_type\": \"resource_type\",\n"
                + "    \"resp_body\": \"resp_body\",\n"
                + "    \"resp_code\": 1,\n"
                + "    \"secondary_resource\": \"secondary_resource\",\n"
                + "    \"started_at\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"url\": \"url\",\n"
                + "    \"user_agent\": \"user_agent\",\n"
                + "    \"user_id\": \"user_id\",\n"
                + "    \"user_name\": \"user_name\"\n"
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
    public void testCountApiRequests() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":{\"count\":1},\"params\":{\"environment_id\":\"environment_id\",\"limit\":1,\"offset\":1,\"q\":\"q\",\"request_type\":\"request_type\"}}"));
        CountApiRequestsResponse response = client.accounts()
                .countApiRequests(CountApiRequestsRequest.builder()
                        .q("q")
                        .requestType("request_type")
                        .environmentId("environment_id")
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
                + "    \"environment_id\": \"environment_id\",\n"
                + "    \"limit\": 1,\n"
                + "    \"offset\": 1,\n"
                + "    \"q\": \"q\",\n"
                + "    \"request_type\": \"request_type\"\n"
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
    public void testListEnvironments() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_type\":\"environment_type\",\"id\":\"id\",\"name\":\"name\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"params\":{\"ids\":[\"ids\"],\"limit\":1,\"offset\":1}}"));
        ListEnvironmentsResponse response = client.accounts()
                .listEnvironments(
                        ListEnvironmentsRequest.builder().limit(1).offset(1).build());
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
                + "      \"environment_type\": \"environment_type\",\n"
                + "      \"id\": \"id\",\n"
                + "      \"name\": \"name\",\n"
                + "      \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "    }\n"
                + "  ],\n"
                + "  \"params\": {\n"
                + "    \"ids\": [\n"
                + "      \"ids\"\n"
                + "    ],\n"
                + "    \"limit\": 1,\n"
                + "    \"offset\": 1\n"
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
    public void testCreateEnvironment() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":{\"api_keys\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"id\":\"id\",\"name\":\"name\",\"scopes\":[\"scopes\"],\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_type\":\"environment_type\",\"id\":\"id\",\"name\":\"name\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"params\":{\"key\":\"value\"}}"));
        CreateEnvironmentResponse response = client.accounts()
                .createEnvironment(CreateEnvironmentRequestBody.builder()
                        .environmentType(CreateEnvironmentRequestBodyEnvironmentType.DEVELOPMENT)
                        .name("name")
                        .build());
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("POST", request.getMethod());
        // Validate request body
        String actualRequestBody = request.getBody().readUtf8();
        String expectedRequestBody =
                "" + "{\n" + "  \"environment_type\": \"development\",\n" + "  \"name\": \"name\"\n" + "}";
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
                + "    \"api_keys\": [\n"
                + "      {\n"
                + "        \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"id\": \"id\",\n"
                + "        \"name\": \"name\",\n"
                + "        \"scopes\": [\n"
                + "          \"scopes\"\n"
                + "        ],\n"
                + "        \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "      }\n"
                + "    ],\n"
                + "    \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"environment_type\": \"environment_type\",\n"
                + "    \"id\": \"id\",\n"
                + "    \"name\": \"name\",\n"
                + "    \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
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
    public void testGetEnvironment() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_type\":\"environment_type\",\"id\":\"id\",\"name\":\"name\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"params\":{\"key\":\"value\"}}"));
        GetEnvironmentResponse response = client.accounts().getEnvironment("environment_id");
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
                + "    \"environment_type\": \"environment_type\",\n"
                + "    \"id\": \"id\",\n"
                + "    \"name\": \"name\",\n"
                + "    \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
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
    public void testUpdateEnvironment() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_type\":\"environment_type\",\"id\":\"id\",\"name\":\"name\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"params\":{\"key\":\"value\"}}"));
        UpdateEnvironmentResponse response = client.accounts()
                .updateEnvironment(
                        "environment_id", UpdateEnvironmentRequestBody.builder().build());
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
                + "    \"environment_type\": \"environment_type\",\n"
                + "    \"id\": \"id\",\n"
                + "    \"name\": \"name\",\n"
                + "    \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
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
    public void testDeleteEnvironment() throws Exception {
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody("{\"data\":{\"deleted\":true},\"params\":{\"key\":\"value\"}}"));
        DeleteEnvironmentResponse response = client.accounts().deleteEnvironment("environment_id");
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
    public void testQuickstart() throws Exception {
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody("{\"data\":{\"ok\":true},\"params\":{\"key\":\"value\"}}"));
        QuickstartResponse response = client.accounts().quickstart();
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("POST", request.getMethod());

        // Validate response body
        Assertions.assertNotNull(response, "Response should not be null");
        String actualResponseJson = objectMapper.writeValueAsString(response);
        String expectedResponseBody = ""
                + "{\n"
                + "  \"data\": {\n"
                + "    \"ok\": true\n"
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
