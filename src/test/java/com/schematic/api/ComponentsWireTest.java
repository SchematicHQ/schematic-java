package com.schematic.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.schematic.api.core.ObjectMappers;
import com.schematic.api.resources.components.requests.CountComponentsRequest;
import com.schematic.api.resources.components.requests.CreateComponentRequestBody;
import com.schematic.api.resources.components.requests.ListComponentsRequest;
import com.schematic.api.resources.components.requests.PreviewComponentDataRequest;
import com.schematic.api.resources.components.requests.UpdateComponentRequestBody;
import com.schematic.api.resources.components.types.CountComponentsResponse;
import com.schematic.api.resources.components.types.CreateComponentRequestBodyEntityType;
import com.schematic.api.resources.components.types.CreateComponentResponse;
import com.schematic.api.resources.components.types.DeleteComponentResponse;
import com.schematic.api.resources.components.types.GetComponentResponse;
import com.schematic.api.resources.components.types.ListComponentsResponse;
import com.schematic.api.resources.components.types.PreviewComponentDataResponse;
import com.schematic.api.resources.components.types.UpdateComponentResponse;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ComponentsWireTest {
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
    public void testListComponents() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":[{\"ast\":{\"key\":1.1},\"created_at\":\"2024-01-15T09:30:00Z\",\"id\":\"id\",\"name\":\"name\",\"state\":\"state\",\"type\":\"type\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"params\":{\"limit\":1,\"offset\":1,\"q\":\"q\"}}"));
        ListComponentsResponse response = client.components()
                .listComponents(ListComponentsRequest.builder()
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
                + "      \"ast\": {\n"
                + "        \"key\": 1.1\n"
                + "      },\n"
                + "      \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"id\": \"id\",\n"
                + "      \"name\": \"name\",\n"
                + "      \"state\": \"state\",\n"
                + "      \"type\": \"type\",\n"
                + "      \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
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
    public void testCreateComponent() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":{\"ast\":{\"key\":1.1},\"created_at\":\"2024-01-15T09:30:00Z\",\"id\":\"id\",\"name\":\"name\",\"state\":\"state\",\"type\":\"type\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"params\":{\"key\":\"value\"}}"));
        CreateComponentResponse response = client.components()
                .createComponent(CreateComponentRequestBody.builder()
                        .entityType(CreateComponentRequestBodyEntityType.ENTITLEMENT)
                        .name("name")
                        .build());
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("POST", request.getMethod());
        // Validate request body
        String actualRequestBody = request.getBody().readUtf8();
        String expectedRequestBody =
                "" + "{\n" + "  \"entity_type\": \"entitlement\",\n" + "  \"name\": \"name\"\n" + "}";
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
                + "    \"ast\": {\n"
                + "      \"key\": 1.1\n"
                + "    },\n"
                + "    \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"id\": \"id\",\n"
                + "    \"name\": \"name\",\n"
                + "    \"state\": \"state\",\n"
                + "    \"type\": \"type\",\n"
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
    public void testGetComponent() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":{\"ast\":{\"key\":1.1},\"created_at\":\"2024-01-15T09:30:00Z\",\"id\":\"id\",\"name\":\"name\",\"state\":\"state\",\"type\":\"type\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"params\":{\"key\":\"value\"}}"));
        GetComponentResponse response = client.components().getComponent("component_id");
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("GET", request.getMethod());

        // Validate response body
        Assertions.assertNotNull(response, "Response should not be null");
        String actualResponseJson = objectMapper.writeValueAsString(response);
        String expectedResponseBody = ""
                + "{\n"
                + "  \"data\": {\n"
                + "    \"ast\": {\n"
                + "      \"key\": 1.1\n"
                + "    },\n"
                + "    \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"id\": \"id\",\n"
                + "    \"name\": \"name\",\n"
                + "    \"state\": \"state\",\n"
                + "    \"type\": \"type\",\n"
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
    public void testUpdateComponent() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":{\"ast\":{\"key\":1.1},\"created_at\":\"2024-01-15T09:30:00Z\",\"id\":\"id\",\"name\":\"name\",\"state\":\"state\",\"type\":\"type\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"params\":{\"key\":\"value\"}}"));
        UpdateComponentResponse response = client.components()
                .updateComponent(
                        "component_id", UpdateComponentRequestBody.builder().build());
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
                + "    \"ast\": {\n"
                + "      \"key\": 1.1\n"
                + "    },\n"
                + "    \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"id\": \"id\",\n"
                + "    \"name\": \"name\",\n"
                + "    \"state\": \"state\",\n"
                + "    \"type\": \"type\",\n"
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
    public void testDeleteComponent() throws Exception {
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody("{\"data\":{\"deleted\":true},\"params\":{\"key\":\"value\"}}"));
        DeleteComponentResponse response = client.components().deleteComponent("component_id");
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
    public void testCountComponents() throws Exception {
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody("{\"data\":{\"count\":1},\"params\":{\"limit\":1,\"offset\":1,\"q\":\"q\"}}"));
        CountComponentsResponse response = client.components()
                .countComponents(CountComponentsRequest.builder()
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

    @Test
    public void testPreviewComponentData() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":{\"active_add_ons\":[{\"charge_type\":\"charge_type\",\"company_can_trial\":true,\"company_count\":1,\"compatible_plan_ids\":[\"compatible_plan_ids\"],\"controlled_by\":\"controlled_by\",\"created_at\":\"2024-01-15T09:30:00Z\",\"current\":true,\"custom\":true,\"description\":\"description\",\"entitlements\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"feature_id\":\"feature_id\",\"id\":\"id\",\"plan_id\":\"plan_id\",\"rule_id\":\"rule_id\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"value_type\":\"value_type\"}],\"features\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"description\":\"description\",\"feature_type\":\"feature_type\",\"flags\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"default_value\":true,\"description\":\"description\",\"flag_type\":\"flag_type\",\"id\":\"id\",\"key\":\"key\",\"name\":\"name\",\"rules\":[{\"condition_groups\":[{\"conditions\":[{\"condition_type\":\"condition_type\",\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"operator\":\"operator\",\"resource_ids\":[\"resource_ids\"],\"resources\":[{\"id\":\"id\",\"name\":\"name\"}],\"rule_id\":\"rule_id\",\"trait_value\":\"trait_value\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"rule_id\":\"rule_id\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"conditions\":[{\"condition_type\":\"condition_type\",\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"operator\":\"operator\",\"resource_ids\":[\"resource_ids\"],\"resources\":[{\"id\":\"id\",\"name\":\"name\"}],\"rule_id\":\"rule_id\",\"trait_value\":\"trait_value\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"name\":\"name\",\"priority\":1,\"rule_type\":\"rule_type\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"value\":true}],\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"icon\":\"icon\",\"id\":\"id\",\"name\":\"name\",\"plans\":[{\"id\":\"id\",\"name\":\"name\"}],\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"icon\":\"icon\",\"id\":\"id\",\"included_credit_grants\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"credit_amount\":1,\"credit_description\":\"credit_description\",\"credit_id\":\"credit_id\",\"credit_name\":\"credit_name\",\"id\":\"id\",\"plan_id\":\"plan_id\",\"plan_name\":\"plan_name\",\"reset_cadence\":\"reset_cadence\",\"reset_start\":\"reset_start\",\"reset_type\":\"reset_type\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"is_custom\":true,\"is_default\":true,\"is_free\":true,\"is_trialable\":true,\"name\":\"name\",\"plan_type\":\"plan_type\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"usage_violations\":[{\"access\":true,\"allocation_type\":\"boolean\",\"entitlement_id\":\"entitlement_id\",\"entitlement_type\":\"entitlement_type\"}],\"valid\":true}],\"active_plans\":[{\"charge_type\":\"charge_type\",\"company_can_trial\":true,\"company_count\":1,\"compatible_plan_ids\":[\"compatible_plan_ids\"],\"controlled_by\":\"controlled_by\",\"created_at\":\"2024-01-15T09:30:00Z\",\"current\":true,\"custom\":true,\"description\":\"description\",\"entitlements\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"feature_id\":\"feature_id\",\"id\":\"id\",\"plan_id\":\"plan_id\",\"rule_id\":\"rule_id\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"value_type\":\"value_type\"}],\"features\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"description\":\"description\",\"feature_type\":\"feature_type\",\"flags\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"default_value\":true,\"description\":\"description\",\"flag_type\":\"flag_type\",\"id\":\"id\",\"key\":\"key\",\"name\":\"name\",\"rules\":[{\"condition_groups\":[{\"conditions\":[{\"condition_type\":\"condition_type\",\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"operator\":\"operator\",\"resource_ids\":[\"resource_ids\"],\"resources\":[{\"id\":\"id\",\"name\":\"name\"}],\"rule_id\":\"rule_id\",\"trait_value\":\"trait_value\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"rule_id\":\"rule_id\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"conditions\":[{\"condition_type\":\"condition_type\",\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"operator\":\"operator\",\"resource_ids\":[\"resource_ids\"],\"resources\":[{\"id\":\"id\",\"name\":\"name\"}],\"rule_id\":\"rule_id\",\"trait_value\":\"trait_value\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"name\":\"name\",\"priority\":1,\"rule_type\":\"rule_type\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"value\":true}],\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"icon\":\"icon\",\"id\":\"id\",\"name\":\"name\",\"plans\":[{\"id\":\"id\",\"name\":\"name\"}],\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"icon\":\"icon\",\"id\":\"id\",\"included_credit_grants\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"credit_amount\":1,\"credit_description\":\"credit_description\",\"credit_id\":\"credit_id\",\"credit_name\":\"credit_name\",\"id\":\"id\",\"plan_id\":\"plan_id\",\"plan_name\":\"plan_name\",\"reset_cadence\":\"reset_cadence\",\"reset_start\":\"reset_start\",\"reset_type\":\"reset_type\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"is_custom\":true,\"is_default\":true,\"is_free\":true,\"is_trialable\":true,\"name\":\"name\",\"plan_type\":\"plan_type\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"usage_violations\":[{\"access\":true,\"allocation_type\":\"boolean\",\"entitlement_id\":\"entitlement_id\",\"entitlement_type\":\"entitlement_type\"}],\"valid\":true}],\"active_usage_based_entitlements\":[{\"feature_id\":\"feature_id\",\"value_type\":\"value_type\"}],\"add_on_compatibilities\":[{\"compatible_plan_ids\":[\"compatible_plan_ids\"],\"source_plan_id\":\"source_plan_id\"}],\"capabilities\":{\"badge_visibility\":true,\"checkout\":true},\"checkout_settings\":{\"collect_address\":true,\"collect_email\":true,\"collect_phone\":true,\"tax_collection_enabled\":true},\"company\":{\"add_ons\":[{\"id\":\"id\",\"name\":\"name\"}],\"billing_credit_balances\":{\"key\":1.1},\"billing_subscription\":{\"cancel_at_period_end\":true,\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"customer_external_id\":\"customer_external_id\",\"discounts\":[{\"coupon_id\":\"coupon_id\",\"coupon_name\":\"coupon_name\",\"discount_external_id\":\"discount_external_id\",\"duration\":\"duration\",\"is_active\":true,\"started_at\":\"2024-01-15T09:30:00Z\",\"subscription_external_id\":\"subscription_external_id\"}],\"id\":\"id\",\"interval\":\"interval\",\"period_end\":1,\"period_start\":1,\"products\":[{\"billing_scheme\":\"billing_scheme\",\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"id\":\"id\",\"interval\":\"interval\",\"name\":\"name\",\"package_size\":1,\"price\":1,\"price_external_id\":\"price_external_id\",\"price_id\":\"price_id\",\"price_tier\":[{}],\"quantity\":1.1,\"subscription_id\":\"subscription_id\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"usage_type\":\"usage_type\"}],\"status\":\"status\",\"subscription_external_id\":\"subscription_external_id\",\"total_price\":1},\"billing_subscriptions\":[{\"cancel_at_period_end\":true,\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"customer_external_id\":\"customer_external_id\",\"discounts\":[{\"coupon_id\":\"coupon_id\",\"coupon_name\":\"coupon_name\",\"discount_external_id\":\"discount_external_id\",\"duration\":\"duration\",\"is_active\":true,\"started_at\":\"2024-01-15T09:30:00Z\",\"subscription_external_id\":\"subscription_external_id\"}],\"id\":\"id\",\"interval\":\"interval\",\"period_end\":1,\"period_start\":1,\"products\":[{\"billing_scheme\":\"billing_scheme\",\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"id\":\"id\",\"interval\":\"interval\",\"name\":\"name\",\"package_size\":1,\"price\":1,\"price_external_id\":\"price_external_id\",\"price_id\":\"price_id\",\"price_tier\":[{}],\"quantity\":1.1,\"subscription_id\":\"subscription_id\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"usage_type\":\"usage_type\"}],\"status\":\"status\",\"subscription_external_id\":\"subscription_external_id\",\"total_price\":1}],\"created_at\":\"2024-01-15T09:30:00Z\",\"default_payment_method\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"customer_external_id\":\"customer_external_id\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"id\":\"id\",\"payment_method_type\":\"payment_method_type\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"entity_traits\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"definition_id\":\"definition_id\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"value\":\"value\"}],\"environment_id\":\"environment_id\",\"id\":\"id\",\"keys\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"definition_id\":\"definition_id\",\"entity_id\":\"entity_id\",\"entity_type\":\"entity_type\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"key\":\"key\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"value\":\"value\"}],\"last_seen_at\":\"2024-01-15T09:30:00Z\",\"logo_url\":\"logo_url\",\"metrics\":[{\"account_id\":\"account_id\",\"captured_at_max\":\"2024-01-15T09:30:00Z\",\"captured_at_min\":\"2024-01-15T09:30:00Z\",\"company_id\":\"company_id\",\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"event_subtype\":\"event_subtype\",\"month_reset\":\"month_reset\",\"period\":\"period\",\"value\":1}],\"name\":\"name\",\"payment_methods\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"customer_external_id\":\"customer_external_id\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"id\":\"id\",\"payment_method_type\":\"payment_method_type\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"plan\":{\"id\":\"id\",\"name\":\"name\"},\"plans\":[{\"id\":\"id\",\"name\":\"name\"}],\"rules\":[{\"account_id\":\"account_id\",\"condition_groups\":[{\"conditions\":[{\"account_id\":\"account_id\",\"condition_type\":\"condition_type\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"operator\":\"operator\",\"resource_ids\":[\"resource_ids\"],\"trait_value\":\"trait_value\"}]}],\"conditions\":[{\"account_id\":\"account_id\",\"condition_type\":\"condition_type\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"operator\":\"operator\",\"resource_ids\":[\"resource_ids\"],\"trait_value\":\"trait_value\"}],\"environment_id\":\"environment_id\",\"id\":\"id\",\"name\":\"name\",\"priority\":1,\"rule_type\":\"rule_type\",\"value\":true}],\"traits\":{\"key\":\"value\"},\"updated_at\":\"2024-01-15T09:30:00Z\",\"user_count\":1},\"component\":{\"ast\":{\"key\":1.1},\"created_at\":\"2024-01-15T09:30:00Z\",\"id\":\"id\",\"name\":\"name\",\"state\":\"state\",\"type\":\"type\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"credit_bundles\":[{\"bundle_type\":\"bundle_type\",\"created_at\":\"2024-01-15T09:30:00Z\",\"credit_id\":\"credit_id\",\"credit_name\":\"credit_name\",\"expiry_type\":\"expiry_type\",\"expiry_unit\":\"expiry_unit\",\"has_grants\":true,\"id\":\"id\",\"name\":\"name\",\"status\":\"status\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"credit_grants\":[{\"billing_credit_id\":\"billing_credit_id\",\"company_id\":\"company_id\",\"company_name\":\"company_name\",\"created_at\":\"2024-01-15T09:30:00Z\",\"credit_description\":\"credit_description\",\"credit_name\":\"credit_name\",\"grant_reason\":\"grant_reason\",\"id\":\"id\",\"quantity\":1,\"quantity_remaining\":1.1,\"quantity_used\":1.1,\"source_label\":\"source_label\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"default_plan\":{\"billing_product\":{\"account_id\":\"account_id\",\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"is_active\":true,\"name\":\"name\",\"price\":1.1,\"prices\":[{\"currency\":\"currency\",\"external_price_id\":\"external_price_id\",\"id\":\"id\",\"interval\":\"interval\",\"price\":1,\"scheme\":\"scheme\"}],\"product_id\":\"product_id\",\"quantity\":1.1,\"subscription_count\":1,\"updated_at\":\"2024-01-15T09:30:00Z\"},\"charge_type\":\"charge_type\",\"company_count\":1,\"controlled_by\":\"controlled_by\",\"created_at\":\"2024-01-15T09:30:00Z\",\"description\":\"description\",\"features\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"description\":\"description\",\"feature_type\":\"feature_type\",\"flags\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"default_value\":true,\"description\":\"description\",\"flag_type\":\"flag_type\",\"id\":\"id\",\"key\":\"key\",\"name\":\"name\",\"rules\":[{\"condition_groups\":[{\"conditions\":[{\"condition_type\":\"condition_type\",\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"operator\":\"operator\",\"resource_ids\":[\"resource_ids\"],\"resources\":[{\"id\":\"id\",\"name\":\"name\"}],\"rule_id\":\"rule_id\",\"trait_value\":\"trait_value\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"rule_id\":\"rule_id\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"conditions\":[{\"condition_type\":\"condition_type\",\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"operator\":\"operator\",\"resource_ids\":[\"resource_ids\"],\"resources\":[{\"id\":\"id\",\"name\":\"name\"}],\"rule_id\":\"rule_id\",\"trait_value\":\"trait_value\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"name\":\"name\",\"priority\":1,\"rule_type\":\"rule_type\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"value\":true}],\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"icon\":\"icon\",\"id\":\"id\",\"name\":\"name\",\"plans\":[{\"id\":\"id\",\"name\":\"name\"}],\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"icon\":\"icon\",\"id\":\"id\",\"included_credit_grants\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"credit_amount\":1,\"credit_id\":\"credit_id\",\"credit_name\":\"credit_name\",\"id\":\"id\",\"plan_id\":\"plan_id\",\"plan_name\":\"plan_name\",\"reset_cadence\":\"reset_cadence\",\"reset_start\":\"reset_start\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"is_default\":true,\"is_free\":true,\"is_trialable\":true,\"monthly_price\":{\"currency\":\"currency\",\"external_price_id\":\"external_price_id\",\"id\":\"id\",\"interval\":\"interval\",\"price\":1,\"scheme\":\"scheme\"},\"name\":\"name\",\"one_time_price\":{\"currency\":\"currency\",\"external_price_id\":\"external_price_id\",\"id\":\"id\",\"interval\":\"interval\",\"price\":1,\"scheme\":\"scheme\"},\"plan_type\":\"plan_type\",\"trial_days\":1,\"updated_at\":\"2024-01-15T09:30:00Z\",\"yearly_price\":{\"currency\":\"currency\",\"external_price_id\":\"external_price_id\",\"id\":\"id\",\"interval\":\"interval\",\"price\":1,\"scheme\":\"scheme\"}},\"feature_usage\":{\"features\":[{\"access\":true,\"allocation_type\":\"boolean\",\"entitlement_id\":\"entitlement_id\",\"entitlement_type\":\"entitlement_type\"}]},\"invoices\":[{\"amount_due\":1,\"amount_paid\":1,\"amount_remaining\":1,\"collection_method\":\"collection_method\",\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"customer_external_id\":\"customer_external_id\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"subtotal\":1,\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"post_trial_plan\":{\"billing_product\":{\"account_id\":\"account_id\",\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"is_active\":true,\"name\":\"name\",\"price\":1.1,\"prices\":[{\"currency\":\"currency\",\"external_price_id\":\"external_price_id\",\"id\":\"id\",\"interval\":\"interval\",\"price\":1,\"scheme\":\"scheme\"}],\"product_id\":\"product_id\",\"quantity\":1.1,\"subscription_count\":1,\"updated_at\":\"2024-01-15T09:30:00Z\"},\"charge_type\":\"charge_type\",\"company_count\":1,\"controlled_by\":\"controlled_by\",\"created_at\":\"2024-01-15T09:30:00Z\",\"description\":\"description\",\"features\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"description\":\"description\",\"feature_type\":\"feature_type\",\"flags\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"default_value\":true,\"description\":\"description\",\"flag_type\":\"flag_type\",\"id\":\"id\",\"key\":\"key\",\"name\":\"name\",\"rules\":[{\"condition_groups\":[{\"conditions\":[{\"condition_type\":\"condition_type\",\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"operator\":\"operator\",\"resource_ids\":[\"resource_ids\"],\"resources\":[{\"id\":\"id\",\"name\":\"name\"}],\"rule_id\":\"rule_id\",\"trait_value\":\"trait_value\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"rule_id\":\"rule_id\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"conditions\":[{\"condition_type\":\"condition_type\",\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"operator\":\"operator\",\"resource_ids\":[\"resource_ids\"],\"resources\":[{\"id\":\"id\",\"name\":\"name\"}],\"rule_id\":\"rule_id\",\"trait_value\":\"trait_value\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"name\":\"name\",\"priority\":1,\"rule_type\":\"rule_type\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"value\":true}],\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"icon\":\"icon\",\"id\":\"id\",\"name\":\"name\",\"plans\":[{\"id\":\"id\",\"name\":\"name\"}],\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"icon\":\"icon\",\"id\":\"id\",\"included_credit_grants\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"credit_amount\":1,\"credit_id\":\"credit_id\",\"credit_name\":\"credit_name\",\"id\":\"id\",\"plan_id\":\"plan_id\",\"plan_name\":\"plan_name\",\"reset_cadence\":\"reset_cadence\",\"reset_start\":\"reset_start\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"is_default\":true,\"is_free\":true,\"is_trialable\":true,\"monthly_price\":{\"currency\":\"currency\",\"external_price_id\":\"external_price_id\",\"id\":\"id\",\"interval\":\"interval\",\"price\":1,\"scheme\":\"scheme\"},\"name\":\"name\",\"one_time_price\":{\"currency\":\"currency\",\"external_price_id\":\"external_price_id\",\"id\":\"id\",\"interval\":\"interval\",\"price\":1,\"scheme\":\"scheme\"},\"plan_type\":\"plan_type\",\"trial_days\":1,\"updated_at\":\"2024-01-15T09:30:00Z\",\"yearly_price\":{\"currency\":\"currency\",\"external_price_id\":\"external_price_id\",\"id\":\"id\",\"interval\":\"interval\",\"price\":1,\"scheme\":\"scheme\"}},\"show_credits\":true,\"show_period_toggle\":true,\"show_zero_price_as_free\":true,\"stripe_embed\":{\"account_id\":\"account_id\",\"publishable_key\":\"publishable_key\",\"schematic_publishable_key\":\"schematic_publishable_key\",\"setup_intent_client_secret\":\"setup_intent_client_secret\"},\"subscription\":{\"cancel_at\":\"2024-01-15T09:30:00Z\",\"cancel_at_period_end\":true,\"currency\":\"currency\",\"customer_external_id\":\"customer_external_id\",\"discounts\":[{\"coupon_id\":\"coupon_id\",\"coupon_name\":\"coupon_name\",\"discount_external_id\":\"discount_external_id\",\"duration\":\"duration\",\"is_active\":true,\"started_at\":\"2024-01-15T09:30:00Z\",\"subscription_external_id\":\"subscription_external_id\"}],\"expired_at\":\"2024-01-15T09:30:00Z\",\"interval\":\"interval\",\"latest_invoice\":{\"amount_due\":1,\"amount_paid\":1,\"amount_remaining\":1,\"collection_method\":\"collection_method\",\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"customer_external_id\":\"customer_external_id\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"subtotal\":1,\"updated_at\":\"2024-01-15T09:30:00Z\"},\"payment_method\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"customer_external_id\":\"customer_external_id\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"id\":\"id\",\"payment_method_type\":\"payment_method_type\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"products\":[{\"billing_scheme\":\"billing_scheme\",\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"id\":\"id\",\"interval\":\"interval\",\"name\":\"name\",\"package_size\":1,\"price\":1,\"price_external_id\":\"price_external_id\",\"price_id\":\"price_id\",\"price_tier\":[{}],\"quantity\":1.1,\"subscription_id\":\"subscription_id\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"usage_type\":\"usage_type\"}],\"status\":\"status\",\"subscription_external_id\":\"subscription_external_id\",\"total_price\":1,\"trial_end\":\"2024-01-15T09:30:00Z\"},\"trial_payment_method_required\":true,\"upcoming_invoice\":{\"amount_due\":1,\"amount_paid\":1,\"amount_remaining\":1,\"collection_method\":\"collection_method\",\"company_id\":\"company_id\",\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"customer_external_id\":\"customer_external_id\",\"due_date\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"id\":\"id\",\"payment_method_external_id\":\"payment_method_external_id\",\"subscription_external_id\":\"subscription_external_id\",\"subtotal\":1,\"updated_at\":\"2024-01-15T09:30:00Z\",\"url\":\"url\"}},\"params\":{\"company_id\":\"company_id\",\"component_id\":\"component_id\"}}"));
        PreviewComponentDataResponse response = client.components()
                .previewComponentData(PreviewComponentDataRequest.builder()
                        .companyId("company_id")
                        .componentId("component_id")
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
                + "    \"active_add_ons\": [\n"
                + "      {\n"
                + "        \"charge_type\": \"charge_type\",\n"
                + "        \"company_can_trial\": true,\n"
                + "        \"company_count\": 1,\n"
                + "        \"compatible_plan_ids\": [\n"
                + "          \"compatible_plan_ids\"\n"
                + "        ],\n"
                + "        \"controlled_by\": \"controlled_by\",\n"
                + "        \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"current\": true,\n"
                + "        \"custom\": true,\n"
                + "        \"description\": \"description\",\n"
                + "        \"entitlements\": [\n"
                + "          {\n"
                + "            \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"environment_id\": \"environment_id\",\n"
                + "            \"feature_id\": \"feature_id\",\n"
                + "            \"id\": \"id\",\n"
                + "            \"plan_id\": \"plan_id\",\n"
                + "            \"rule_id\": \"rule_id\",\n"
                + "            \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"value_type\": \"value_type\"\n"
                + "          }\n"
                + "        ],\n"
                + "        \"features\": [\n"
                + "          {\n"
                + "            \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"description\": \"description\",\n"
                + "            \"feature_type\": \"feature_type\",\n"
                + "            \"flags\": [\n"
                + "              {\n"
                + "                \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "                \"default_value\": true,\n"
                + "                \"description\": \"description\",\n"
                + "                \"flag_type\": \"flag_type\",\n"
                + "                \"id\": \"id\",\n"
                + "                \"key\": \"key\",\n"
                + "                \"name\": \"name\",\n"
                + "                \"rules\": [\n"
                + "                  {\n"
                + "                    \"condition_groups\": [\n"
                + "                      {\n"
                + "                        \"conditions\": [\n"
                + "                          {\n"
                + "                            \"condition_type\": \"condition_type\",\n"
                + "                            \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "                            \"environment_id\": \"environment_id\",\n"
                + "                            \"id\": \"id\",\n"
                + "                            \"operator\": \"operator\",\n"
                + "                            \"resource_ids\": [\n"
                + "                              \"resource_ids\"\n"
                + "                            ],\n"
                + "                            \"resources\": [\n"
                + "                              {\n"
                + "                                \"id\": \"id\",\n"
                + "                                \"name\": \"name\"\n"
                + "                              }\n"
                + "                            ],\n"
                + "                            \"rule_id\": \"rule_id\",\n"
                + "                            \"trait_value\": \"trait_value\",\n"
                + "                            \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "                          }\n"
                + "                        ],\n"
                + "                        \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "                        \"environment_id\": \"environment_id\",\n"
                + "                        \"id\": \"id\",\n"
                + "                        \"rule_id\": \"rule_id\",\n"
                + "                        \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "                      }\n"
                + "                    ],\n"
                + "                    \"conditions\": [\n"
                + "                      {\n"
                + "                        \"condition_type\": \"condition_type\",\n"
                + "                        \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "                        \"environment_id\": \"environment_id\",\n"
                + "                        \"id\": \"id\",\n"
                + "                        \"operator\": \"operator\",\n"
                + "                        \"resource_ids\": [\n"
                + "                          \"resource_ids\"\n"
                + "                        ],\n"
                + "                        \"resources\": [\n"
                + "                          {\n"
                + "                            \"id\": \"id\",\n"
                + "                            \"name\": \"name\"\n"
                + "                          }\n"
                + "                        ],\n"
                + "                        \"rule_id\": \"rule_id\",\n"
                + "                        \"trait_value\": \"trait_value\",\n"
                + "                        \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "                      }\n"
                + "                    ],\n"
                + "                    \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "                    \"environment_id\": \"environment_id\",\n"
                + "                    \"id\": \"id\",\n"
                + "                    \"name\": \"name\",\n"
                + "                    \"priority\": 1,\n"
                + "                    \"rule_type\": \"rule_type\",\n"
                + "                    \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "                    \"value\": true\n"
                + "                  }\n"
                + "                ],\n"
                + "                \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "              }\n"
                + "            ],\n"
                + "            \"icon\": \"icon\",\n"
                + "            \"id\": \"id\",\n"
                + "            \"name\": \"name\",\n"
                + "            \"plans\": [\n"
                + "              {\n"
                + "                \"id\": \"id\",\n"
                + "                \"name\": \"name\"\n"
                + "              }\n"
                + "            ],\n"
                + "            \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "          }\n"
                + "        ],\n"
                + "        \"icon\": \"icon\",\n"
                + "        \"id\": \"id\",\n"
                + "        \"included_credit_grants\": [\n"
                + "          {\n"
                + "            \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"credit_amount\": 1,\n"
                + "            \"credit_description\": \"credit_description\",\n"
                + "            \"credit_id\": \"credit_id\",\n"
                + "            \"credit_name\": \"credit_name\",\n"
                + "            \"id\": \"id\",\n"
                + "            \"plan_id\": \"plan_id\",\n"
                + "            \"plan_name\": \"plan_name\",\n"
                + "            \"reset_cadence\": \"reset_cadence\",\n"
                + "            \"reset_start\": \"reset_start\",\n"
                + "            \"reset_type\": \"reset_type\",\n"
                + "            \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "          }\n"
                + "        ],\n"
                + "        \"is_custom\": true,\n"
                + "        \"is_default\": true,\n"
                + "        \"is_free\": true,\n"
                + "        \"is_trialable\": true,\n"
                + "        \"name\": \"name\",\n"
                + "        \"plan_type\": \"plan_type\",\n"
                + "        \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"usage_violations\": [\n"
                + "          {\n"
                + "            \"access\": true,\n"
                + "            \"allocation_type\": \"boolean\",\n"
                + "            \"entitlement_id\": \"entitlement_id\",\n"
                + "            \"entitlement_type\": \"entitlement_type\"\n"
                + "          }\n"
                + "        ],\n"
                + "        \"valid\": true\n"
                + "      }\n"
                + "    ],\n"
                + "    \"active_plans\": [\n"
                + "      {\n"
                + "        \"charge_type\": \"charge_type\",\n"
                + "        \"company_can_trial\": true,\n"
                + "        \"company_count\": 1,\n"
                + "        \"compatible_plan_ids\": [\n"
                + "          \"compatible_plan_ids\"\n"
                + "        ],\n"
                + "        \"controlled_by\": \"controlled_by\",\n"
                + "        \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"current\": true,\n"
                + "        \"custom\": true,\n"
                + "        \"description\": \"description\",\n"
                + "        \"entitlements\": [\n"
                + "          {\n"
                + "            \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"environment_id\": \"environment_id\",\n"
                + "            \"feature_id\": \"feature_id\",\n"
                + "            \"id\": \"id\",\n"
                + "            \"plan_id\": \"plan_id\",\n"
                + "            \"rule_id\": \"rule_id\",\n"
                + "            \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"value_type\": \"value_type\"\n"
                + "          }\n"
                + "        ],\n"
                + "        \"features\": [\n"
                + "          {\n"
                + "            \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"description\": \"description\",\n"
                + "            \"feature_type\": \"feature_type\",\n"
                + "            \"flags\": [\n"
                + "              {\n"
                + "                \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "                \"default_value\": true,\n"
                + "                \"description\": \"description\",\n"
                + "                \"flag_type\": \"flag_type\",\n"
                + "                \"id\": \"id\",\n"
                + "                \"key\": \"key\",\n"
                + "                \"name\": \"name\",\n"
                + "                \"rules\": [\n"
                + "                  {\n"
                + "                    \"condition_groups\": [\n"
                + "                      {\n"
                + "                        \"conditions\": [\n"
                + "                          {\n"
                + "                            \"condition_type\": \"condition_type\",\n"
                + "                            \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "                            \"environment_id\": \"environment_id\",\n"
                + "                            \"id\": \"id\",\n"
                + "                            \"operator\": \"operator\",\n"
                + "                            \"resource_ids\": [\n"
                + "                              \"resource_ids\"\n"
                + "                            ],\n"
                + "                            \"resources\": [\n"
                + "                              {\n"
                + "                                \"id\": \"id\",\n"
                + "                                \"name\": \"name\"\n"
                + "                              }\n"
                + "                            ],\n"
                + "                            \"rule_id\": \"rule_id\",\n"
                + "                            \"trait_value\": \"trait_value\",\n"
                + "                            \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "                          }\n"
                + "                        ],\n"
                + "                        \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "                        \"environment_id\": \"environment_id\",\n"
                + "                        \"id\": \"id\",\n"
                + "                        \"rule_id\": \"rule_id\",\n"
                + "                        \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "                      }\n"
                + "                    ],\n"
                + "                    \"conditions\": [\n"
                + "                      {\n"
                + "                        \"condition_type\": \"condition_type\",\n"
                + "                        \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "                        \"environment_id\": \"environment_id\",\n"
                + "                        \"id\": \"id\",\n"
                + "                        \"operator\": \"operator\",\n"
                + "                        \"resource_ids\": [\n"
                + "                          \"resource_ids\"\n"
                + "                        ],\n"
                + "                        \"resources\": [\n"
                + "                          {\n"
                + "                            \"id\": \"id\",\n"
                + "                            \"name\": \"name\"\n"
                + "                          }\n"
                + "                        ],\n"
                + "                        \"rule_id\": \"rule_id\",\n"
                + "                        \"trait_value\": \"trait_value\",\n"
                + "                        \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "                      }\n"
                + "                    ],\n"
                + "                    \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "                    \"environment_id\": \"environment_id\",\n"
                + "                    \"id\": \"id\",\n"
                + "                    \"name\": \"name\",\n"
                + "                    \"priority\": 1,\n"
                + "                    \"rule_type\": \"rule_type\",\n"
                + "                    \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "                    \"value\": true\n"
                + "                  }\n"
                + "                ],\n"
                + "                \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "              }\n"
                + "            ],\n"
                + "            \"icon\": \"icon\",\n"
                + "            \"id\": \"id\",\n"
                + "            \"name\": \"name\",\n"
                + "            \"plans\": [\n"
                + "              {\n"
                + "                \"id\": \"id\",\n"
                + "                \"name\": \"name\"\n"
                + "              }\n"
                + "            ],\n"
                + "            \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "          }\n"
                + "        ],\n"
                + "        \"icon\": \"icon\",\n"
                + "        \"id\": \"id\",\n"
                + "        \"included_credit_grants\": [\n"
                + "          {\n"
                + "            \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"credit_amount\": 1,\n"
                + "            \"credit_description\": \"credit_description\",\n"
                + "            \"credit_id\": \"credit_id\",\n"
                + "            \"credit_name\": \"credit_name\",\n"
                + "            \"id\": \"id\",\n"
                + "            \"plan_id\": \"plan_id\",\n"
                + "            \"plan_name\": \"plan_name\",\n"
                + "            \"reset_cadence\": \"reset_cadence\",\n"
                + "            \"reset_start\": \"reset_start\",\n"
                + "            \"reset_type\": \"reset_type\",\n"
                + "            \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "          }\n"
                + "        ],\n"
                + "        \"is_custom\": true,\n"
                + "        \"is_default\": true,\n"
                + "        \"is_free\": true,\n"
                + "        \"is_trialable\": true,\n"
                + "        \"name\": \"name\",\n"
                + "        \"plan_type\": \"plan_type\",\n"
                + "        \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"usage_violations\": [\n"
                + "          {\n"
                + "            \"access\": true,\n"
                + "            \"allocation_type\": \"boolean\",\n"
                + "            \"entitlement_id\": \"entitlement_id\",\n"
                + "            \"entitlement_type\": \"entitlement_type\"\n"
                + "          }\n"
                + "        ],\n"
                + "        \"valid\": true\n"
                + "      }\n"
                + "    ],\n"
                + "    \"active_usage_based_entitlements\": [\n"
                + "      {\n"
                + "        \"feature_id\": \"feature_id\",\n"
                + "        \"value_type\": \"value_type\"\n"
                + "      }\n"
                + "    ],\n"
                + "    \"add_on_compatibilities\": [\n"
                + "      {\n"
                + "        \"compatible_plan_ids\": [\n"
                + "          \"compatible_plan_ids\"\n"
                + "        ],\n"
                + "        \"source_plan_id\": \"source_plan_id\"\n"
                + "      }\n"
                + "    ],\n"
                + "    \"capabilities\": {\n"
                + "      \"badge_visibility\": true,\n"
                + "      \"checkout\": true\n"
                + "    },\n"
                + "    \"checkout_settings\": {\n"
                + "      \"collect_address\": true,\n"
                + "      \"collect_email\": true,\n"
                + "      \"collect_phone\": true,\n"
                + "      \"tax_collection_enabled\": true\n"
                + "    },\n"
                + "    \"company\": {\n"
                + "      \"add_ons\": [\n"
                + "        {\n"
                + "          \"id\": \"id\",\n"
                + "          \"name\": \"name\"\n"
                + "        }\n"
                + "      ],\n"
                + "      \"billing_credit_balances\": {\n"
                + "        \"key\": 1.1\n"
                + "      },\n"
                + "      \"billing_subscription\": {\n"
                + "        \"cancel_at_period_end\": true,\n"
                + "        \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"currency\": \"currency\",\n"
                + "        \"customer_external_id\": \"customer_external_id\",\n"
                + "        \"discounts\": [\n"
                + "          {\n"
                + "            \"coupon_id\": \"coupon_id\",\n"
                + "            \"coupon_name\": \"coupon_name\",\n"
                + "            \"discount_external_id\": \"discount_external_id\",\n"
                + "            \"duration\": \"duration\",\n"
                + "            \"is_active\": true,\n"
                + "            \"started_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"subscription_external_id\": \"subscription_external_id\"\n"
                + "          }\n"
                + "        ],\n"
                + "        \"id\": \"id\",\n"
                + "        \"interval\": \"interval\",\n"
                + "        \"period_end\": 1,\n"
                + "        \"period_start\": 1,\n"
                + "        \"products\": [\n"
                + "          {\n"
                + "            \"billing_scheme\": \"billing_scheme\",\n"
                + "            \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"currency\": \"currency\",\n"
                + "            \"environment_id\": \"environment_id\",\n"
                + "            \"external_id\": \"external_id\",\n"
                + "            \"id\": \"id\",\n"
                + "            \"interval\": \"interval\",\n"
                + "            \"name\": \"name\",\n"
                + "            \"package_size\": 1,\n"
                + "            \"price\": 1,\n"
                + "            \"price_external_id\": \"price_external_id\",\n"
                + "            \"price_id\": \"price_id\",\n"
                + "            \"price_tier\": [\n"
                + "              {}\n"
                + "            ],\n"
                + "            \"quantity\": 1.1,\n"
                + "            \"subscription_id\": \"subscription_id\",\n"
                + "            \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"usage_type\": \"usage_type\"\n"
                + "          }\n"
                + "        ],\n"
                + "        \"status\": \"status\",\n"
                + "        \"subscription_external_id\": \"subscription_external_id\",\n"
                + "        \"total_price\": 1\n"
                + "      },\n"
                + "      \"billing_subscriptions\": [\n"
                + "        {\n"
                + "          \"cancel_at_period_end\": true,\n"
                + "          \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "          \"currency\": \"currency\",\n"
                + "          \"customer_external_id\": \"customer_external_id\",\n"
                + "          \"discounts\": [\n"
                + "            {\n"
                + "              \"coupon_id\": \"coupon_id\",\n"
                + "              \"coupon_name\": \"coupon_name\",\n"
                + "              \"discount_external_id\": \"discount_external_id\",\n"
                + "              \"duration\": \"duration\",\n"
                + "              \"is_active\": true,\n"
                + "              \"started_at\": \"2024-01-15T09:30:00Z\",\n"
                + "              \"subscription_external_id\": \"subscription_external_id\"\n"
                + "            }\n"
                + "          ],\n"
                + "          \"id\": \"id\",\n"
                + "          \"interval\": \"interval\",\n"
                + "          \"period_end\": 1,\n"
                + "          \"period_start\": 1,\n"
                + "          \"products\": [\n"
                + "            {\n"
                + "              \"billing_scheme\": \"billing_scheme\",\n"
                + "              \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "              \"currency\": \"currency\",\n"
                + "              \"environment_id\": \"environment_id\",\n"
                + "              \"external_id\": \"external_id\",\n"
                + "              \"id\": \"id\",\n"
                + "              \"interval\": \"interval\",\n"
                + "              \"name\": \"name\",\n"
                + "              \"package_size\": 1,\n"
                + "              \"price\": 1,\n"
                + "              \"price_external_id\": \"price_external_id\",\n"
                + "              \"price_id\": \"price_id\",\n"
                + "              \"price_tier\": [\n"
                + "                {}\n"
                + "              ],\n"
                + "              \"quantity\": 1.1,\n"
                + "              \"subscription_id\": \"subscription_id\",\n"
                + "              \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "              \"usage_type\": \"usage_type\"\n"
                + "            }\n"
                + "          ],\n"
                + "          \"status\": \"status\",\n"
                + "          \"subscription_external_id\": \"subscription_external_id\",\n"
                + "          \"total_price\": 1\n"
                + "        }\n"
                + "      ],\n"
                + "      \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"default_payment_method\": {\n"
                + "        \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"customer_external_id\": \"customer_external_id\",\n"
                + "        \"environment_id\": \"environment_id\",\n"
                + "        \"external_id\": \"external_id\",\n"
                + "        \"id\": \"id\",\n"
                + "        \"payment_method_type\": \"payment_method_type\",\n"
                + "        \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "      },\n"
                + "      \"entity_traits\": [\n"
                + "        {\n"
                + "          \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "          \"definition_id\": \"definition_id\",\n"
                + "          \"environment_id\": \"environment_id\",\n"
                + "          \"id\": \"id\",\n"
                + "          \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "          \"value\": \"value\"\n"
                + "        }\n"
                + "      ],\n"
                + "      \"environment_id\": \"environment_id\",\n"
                + "      \"id\": \"id\",\n"
                + "      \"keys\": [\n"
                + "        {\n"
                + "          \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "          \"definition_id\": \"definition_id\",\n"
                + "          \"entity_id\": \"entity_id\",\n"
                + "          \"entity_type\": \"entity_type\",\n"
                + "          \"environment_id\": \"environment_id\",\n"
                + "          \"id\": \"id\",\n"
                + "          \"key\": \"key\",\n"
                + "          \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "          \"value\": \"value\"\n"
                + "        }\n"
                + "      ],\n"
                + "      \"last_seen_at\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"logo_url\": \"logo_url\",\n"
                + "      \"metrics\": [\n"
                + "        {\n"
                + "          \"account_id\": \"account_id\",\n"
                + "          \"captured_at_max\": \"2024-01-15T09:30:00Z\",\n"
                + "          \"captured_at_min\": \"2024-01-15T09:30:00Z\",\n"
                + "          \"company_id\": \"company_id\",\n"
                + "          \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "          \"environment_id\": \"environment_id\",\n"
                + "          \"event_subtype\": \"event_subtype\",\n"
                + "          \"month_reset\": \"month_reset\",\n"
                + "          \"period\": \"period\",\n"
                + "          \"value\": 1\n"
                + "        }\n"
                + "      ],\n"
                + "      \"name\": \"name\",\n"
                + "      \"payment_methods\": [\n"
                + "        {\n"
                + "          \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "          \"customer_external_id\": \"customer_external_id\",\n"
                + "          \"environment_id\": \"environment_id\",\n"
                + "          \"external_id\": \"external_id\",\n"
                + "          \"id\": \"id\",\n"
                + "          \"payment_method_type\": \"payment_method_type\",\n"
                + "          \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "        }\n"
                + "      ],\n"
                + "      \"plan\": {\n"
                + "        \"id\": \"id\",\n"
                + "        \"name\": \"name\"\n"
                + "      },\n"
                + "      \"plans\": [\n"
                + "        {\n"
                + "          \"id\": \"id\",\n"
                + "          \"name\": \"name\"\n"
                + "        }\n"
                + "      ],\n"
                + "      \"rules\": [\n"
                + "        {\n"
                + "          \"account_id\": \"account_id\",\n"
                + "          \"condition_groups\": [\n"
                + "            {\n"
                + "              \"conditions\": [\n"
                + "                {\n"
                + "                  \"account_id\": \"account_id\",\n"
                + "                  \"condition_type\": \"condition_type\",\n"
                + "                  \"environment_id\": \"environment_id\",\n"
                + "                  \"id\": \"id\",\n"
                + "                  \"operator\": \"operator\",\n"
                + "                  \"resource_ids\": [\n"
                + "                    \"resource_ids\"\n"
                + "                  ],\n"
                + "                  \"trait_value\": \"trait_value\"\n"
                + "                }\n"
                + "              ]\n"
                + "            }\n"
                + "          ],\n"
                + "          \"conditions\": [\n"
                + "            {\n"
                + "              \"account_id\": \"account_id\",\n"
                + "              \"condition_type\": \"condition_type\",\n"
                + "              \"environment_id\": \"environment_id\",\n"
                + "              \"id\": \"id\",\n"
                + "              \"operator\": \"operator\",\n"
                + "              \"resource_ids\": [\n"
                + "                \"resource_ids\"\n"
                + "              ],\n"
                + "              \"trait_value\": \"trait_value\"\n"
                + "            }\n"
                + "          ],\n"
                + "          \"environment_id\": \"environment_id\",\n"
                + "          \"id\": \"id\",\n"
                + "          \"name\": \"name\",\n"
                + "          \"priority\": 1,\n"
                + "          \"rule_type\": \"rule_type\",\n"
                + "          \"value\": true\n"
                + "        }\n"
                + "      ],\n"
                + "      \"traits\": {\n"
                + "        \"key\": \"value\"\n"
                + "      },\n"
                + "      \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"user_count\": 1\n"
                + "    },\n"
                + "    \"component\": {\n"
                + "      \"ast\": {\n"
                + "        \"key\": 1.1\n"
                + "      },\n"
                + "      \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"id\": \"id\",\n"
                + "      \"name\": \"name\",\n"
                + "      \"state\": \"state\",\n"
                + "      \"type\": \"type\",\n"
                + "      \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "    },\n"
                + "    \"credit_bundles\": [\n"
                + "      {\n"
                + "        \"bundle_type\": \"bundle_type\",\n"
                + "        \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"credit_id\": \"credit_id\",\n"
                + "        \"credit_name\": \"credit_name\",\n"
                + "        \"expiry_type\": \"expiry_type\",\n"
                + "        \"expiry_unit\": \"expiry_unit\",\n"
                + "        \"has_grants\": true,\n"
                + "        \"id\": \"id\",\n"
                + "        \"name\": \"name\",\n"
                + "        \"status\": \"status\",\n"
                + "        \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "      }\n"
                + "    ],\n"
                + "    \"credit_grants\": [\n"
                + "      {\n"
                + "        \"billing_credit_id\": \"billing_credit_id\",\n"
                + "        \"company_id\": \"company_id\",\n"
                + "        \"company_name\": \"company_name\",\n"
                + "        \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"credit_description\": \"credit_description\",\n"
                + "        \"credit_name\": \"credit_name\",\n"
                + "        \"grant_reason\": \"grant_reason\",\n"
                + "        \"id\": \"id\",\n"
                + "        \"quantity\": 1,\n"
                + "        \"quantity_remaining\": 1.1,\n"
                + "        \"quantity_used\": 1.1,\n"
                + "        \"source_label\": \"source_label\",\n"
                + "        \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "      }\n"
                + "    ],\n"
                + "    \"default_plan\": {\n"
                + "      \"billing_product\": {\n"
                + "        \"account_id\": \"account_id\",\n"
                + "        \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"environment_id\": \"environment_id\",\n"
                + "        \"external_id\": \"external_id\",\n"
                + "        \"is_active\": true,\n"
                + "        \"name\": \"name\",\n"
                + "        \"price\": 1.1,\n"
                + "        \"prices\": [\n"
                + "          {\n"
                + "            \"currency\": \"currency\",\n"
                + "            \"external_price_id\": \"external_price_id\",\n"
                + "            \"id\": \"id\",\n"
                + "            \"interval\": \"interval\",\n"
                + "            \"price\": 1,\n"
                + "            \"scheme\": \"scheme\"\n"
                + "          }\n"
                + "        ],\n"
                + "        \"product_id\": \"product_id\",\n"
                + "        \"quantity\": 1.1,\n"
                + "        \"subscription_count\": 1,\n"
                + "        \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "      },\n"
                + "      \"charge_type\": \"charge_type\",\n"
                + "      \"company_count\": 1,\n"
                + "      \"controlled_by\": \"controlled_by\",\n"
                + "      \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"description\": \"description\",\n"
                + "      \"features\": [\n"
                + "        {\n"
                + "          \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "          \"description\": \"description\",\n"
                + "          \"feature_type\": \"feature_type\",\n"
                + "          \"flags\": [\n"
                + "            {\n"
                + "              \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "              \"default_value\": true,\n"
                + "              \"description\": \"description\",\n"
                + "              \"flag_type\": \"flag_type\",\n"
                + "              \"id\": \"id\",\n"
                + "              \"key\": \"key\",\n"
                + "              \"name\": \"name\",\n"
                + "              \"rules\": [\n"
                + "                {\n"
                + "                  \"condition_groups\": [\n"
                + "                    {\n"
                + "                      \"conditions\": [\n"
                + "                        {\n"
                + "                          \"condition_type\": \"condition_type\",\n"
                + "                          \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "                          \"environment_id\": \"environment_id\",\n"
                + "                          \"id\": \"id\",\n"
                + "                          \"operator\": \"operator\",\n"
                + "                          \"resource_ids\": [\n"
                + "                            \"resource_ids\"\n"
                + "                          ],\n"
                + "                          \"resources\": [\n"
                + "                            {\n"
                + "                              \"id\": \"id\",\n"
                + "                              \"name\": \"name\"\n"
                + "                            }\n"
                + "                          ],\n"
                + "                          \"rule_id\": \"rule_id\",\n"
                + "                          \"trait_value\": \"trait_value\",\n"
                + "                          \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "                        }\n"
                + "                      ],\n"
                + "                      \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "                      \"environment_id\": \"environment_id\",\n"
                + "                      \"id\": \"id\",\n"
                + "                      \"rule_id\": \"rule_id\",\n"
                + "                      \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "                    }\n"
                + "                  ],\n"
                + "                  \"conditions\": [\n"
                + "                    {\n"
                + "                      \"condition_type\": \"condition_type\",\n"
                + "                      \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "                      \"environment_id\": \"environment_id\",\n"
                + "                      \"id\": \"id\",\n"
                + "                      \"operator\": \"operator\",\n"
                + "                      \"resource_ids\": [\n"
                + "                        \"resource_ids\"\n"
                + "                      ],\n"
                + "                      \"resources\": [\n"
                + "                        {\n"
                + "                          \"id\": \"id\",\n"
                + "                          \"name\": \"name\"\n"
                + "                        }\n"
                + "                      ],\n"
                + "                      \"rule_id\": \"rule_id\",\n"
                + "                      \"trait_value\": \"trait_value\",\n"
                + "                      \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "                    }\n"
                + "                  ],\n"
                + "                  \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "                  \"environment_id\": \"environment_id\",\n"
                + "                  \"id\": \"id\",\n"
                + "                  \"name\": \"name\",\n"
                + "                  \"priority\": 1,\n"
                + "                  \"rule_type\": \"rule_type\",\n"
                + "                  \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "                  \"value\": true\n"
                + "                }\n"
                + "              ],\n"
                + "              \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "            }\n"
                + "          ],\n"
                + "          \"icon\": \"icon\",\n"
                + "          \"id\": \"id\",\n"
                + "          \"name\": \"name\",\n"
                + "          \"plans\": [\n"
                + "            {\n"
                + "              \"id\": \"id\",\n"
                + "              \"name\": \"name\"\n"
                + "            }\n"
                + "          ],\n"
                + "          \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "        }\n"
                + "      ],\n"
                + "      \"icon\": \"icon\",\n"
                + "      \"id\": \"id\",\n"
                + "      \"included_credit_grants\": [\n"
                + "        {\n"
                + "          \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "          \"credit_amount\": 1,\n"
                + "          \"credit_id\": \"credit_id\",\n"
                + "          \"credit_name\": \"credit_name\",\n"
                + "          \"id\": \"id\",\n"
                + "          \"plan_id\": \"plan_id\",\n"
                + "          \"plan_name\": \"plan_name\",\n"
                + "          \"reset_cadence\": \"reset_cadence\",\n"
                + "          \"reset_start\": \"reset_start\",\n"
                + "          \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "        }\n"
                + "      ],\n"
                + "      \"is_default\": true,\n"
                + "      \"is_free\": true,\n"
                + "      \"is_trialable\": true,\n"
                + "      \"monthly_price\": {\n"
                + "        \"currency\": \"currency\",\n"
                + "        \"external_price_id\": \"external_price_id\",\n"
                + "        \"id\": \"id\",\n"
                + "        \"interval\": \"interval\",\n"
                + "        \"price\": 1,\n"
                + "        \"scheme\": \"scheme\"\n"
                + "      },\n"
                + "      \"name\": \"name\",\n"
                + "      \"one_time_price\": {\n"
                + "        \"currency\": \"currency\",\n"
                + "        \"external_price_id\": \"external_price_id\",\n"
                + "        \"id\": \"id\",\n"
                + "        \"interval\": \"interval\",\n"
                + "        \"price\": 1,\n"
                + "        \"scheme\": \"scheme\"\n"
                + "      },\n"
                + "      \"plan_type\": \"plan_type\",\n"
                + "      \"trial_days\": 1,\n"
                + "      \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"yearly_price\": {\n"
                + "        \"currency\": \"currency\",\n"
                + "        \"external_price_id\": \"external_price_id\",\n"
                + "        \"id\": \"id\",\n"
                + "        \"interval\": \"interval\",\n"
                + "        \"price\": 1,\n"
                + "        \"scheme\": \"scheme\"\n"
                + "      }\n"
                + "    },\n"
                + "    \"feature_usage\": {\n"
                + "      \"features\": [\n"
                + "        {\n"
                + "          \"access\": true,\n"
                + "          \"allocation_type\": \"boolean\",\n"
                + "          \"entitlement_id\": \"entitlement_id\",\n"
                + "          \"entitlement_type\": \"entitlement_type\"\n"
                + "        }\n"
                + "      ]\n"
                + "    },\n"
                + "    \"invoices\": [\n"
                + "      {\n"
                + "        \"amount_due\": 1,\n"
                + "        \"amount_paid\": 1,\n"
                + "        \"amount_remaining\": 1,\n"
                + "        \"collection_method\": \"collection_method\",\n"
                + "        \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"currency\": \"currency\",\n"
                + "        \"customer_external_id\": \"customer_external_id\",\n"
                + "        \"environment_id\": \"environment_id\",\n"
                + "        \"id\": \"id\",\n"
                + "        \"subtotal\": 1,\n"
                + "        \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "      }\n"
                + "    ],\n"
                + "    \"post_trial_plan\": {\n"
                + "      \"billing_product\": {\n"
                + "        \"account_id\": \"account_id\",\n"
                + "        \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"environment_id\": \"environment_id\",\n"
                + "        \"external_id\": \"external_id\",\n"
                + "        \"is_active\": true,\n"
                + "        \"name\": \"name\",\n"
                + "        \"price\": 1.1,\n"
                + "        \"prices\": [\n"
                + "          {\n"
                + "            \"currency\": \"currency\",\n"
                + "            \"external_price_id\": \"external_price_id\",\n"
                + "            \"id\": \"id\",\n"
                + "            \"interval\": \"interval\",\n"
                + "            \"price\": 1,\n"
                + "            \"scheme\": \"scheme\"\n"
                + "          }\n"
                + "        ],\n"
                + "        \"product_id\": \"product_id\",\n"
                + "        \"quantity\": 1.1,\n"
                + "        \"subscription_count\": 1,\n"
                + "        \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "      },\n"
                + "      \"charge_type\": \"charge_type\",\n"
                + "      \"company_count\": 1,\n"
                + "      \"controlled_by\": \"controlled_by\",\n"
                + "      \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"description\": \"description\",\n"
                + "      \"features\": [\n"
                + "        {\n"
                + "          \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "          \"description\": \"description\",\n"
                + "          \"feature_type\": \"feature_type\",\n"
                + "          \"flags\": [\n"
                + "            {\n"
                + "              \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "              \"default_value\": true,\n"
                + "              \"description\": \"description\",\n"
                + "              \"flag_type\": \"flag_type\",\n"
                + "              \"id\": \"id\",\n"
                + "              \"key\": \"key\",\n"
                + "              \"name\": \"name\",\n"
                + "              \"rules\": [\n"
                + "                {\n"
                + "                  \"condition_groups\": [\n"
                + "                    {\n"
                + "                      \"conditions\": [\n"
                + "                        {\n"
                + "                          \"condition_type\": \"condition_type\",\n"
                + "                          \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "                          \"environment_id\": \"environment_id\",\n"
                + "                          \"id\": \"id\",\n"
                + "                          \"operator\": \"operator\",\n"
                + "                          \"resource_ids\": [\n"
                + "                            \"resource_ids\"\n"
                + "                          ],\n"
                + "                          \"resources\": [\n"
                + "                            {\n"
                + "                              \"id\": \"id\",\n"
                + "                              \"name\": \"name\"\n"
                + "                            }\n"
                + "                          ],\n"
                + "                          \"rule_id\": \"rule_id\",\n"
                + "                          \"trait_value\": \"trait_value\",\n"
                + "                          \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "                        }\n"
                + "                      ],\n"
                + "                      \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "                      \"environment_id\": \"environment_id\",\n"
                + "                      \"id\": \"id\",\n"
                + "                      \"rule_id\": \"rule_id\",\n"
                + "                      \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "                    }\n"
                + "                  ],\n"
                + "                  \"conditions\": [\n"
                + "                    {\n"
                + "                      \"condition_type\": \"condition_type\",\n"
                + "                      \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "                      \"environment_id\": \"environment_id\",\n"
                + "                      \"id\": \"id\",\n"
                + "                      \"operator\": \"operator\",\n"
                + "                      \"resource_ids\": [\n"
                + "                        \"resource_ids\"\n"
                + "                      ],\n"
                + "                      \"resources\": [\n"
                + "                        {\n"
                + "                          \"id\": \"id\",\n"
                + "                          \"name\": \"name\"\n"
                + "                        }\n"
                + "                      ],\n"
                + "                      \"rule_id\": \"rule_id\",\n"
                + "                      \"trait_value\": \"trait_value\",\n"
                + "                      \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "                    }\n"
                + "                  ],\n"
                + "                  \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "                  \"environment_id\": \"environment_id\",\n"
                + "                  \"id\": \"id\",\n"
                + "                  \"name\": \"name\",\n"
                + "                  \"priority\": 1,\n"
                + "                  \"rule_type\": \"rule_type\",\n"
                + "                  \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "                  \"value\": true\n"
                + "                }\n"
                + "              ],\n"
                + "              \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "            }\n"
                + "          ],\n"
                + "          \"icon\": \"icon\",\n"
                + "          \"id\": \"id\",\n"
                + "          \"name\": \"name\",\n"
                + "          \"plans\": [\n"
                + "            {\n"
                + "              \"id\": \"id\",\n"
                + "              \"name\": \"name\"\n"
                + "            }\n"
                + "          ],\n"
                + "          \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "        }\n"
                + "      ],\n"
                + "      \"icon\": \"icon\",\n"
                + "      \"id\": \"id\",\n"
                + "      \"included_credit_grants\": [\n"
                + "        {\n"
                + "          \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "          \"credit_amount\": 1,\n"
                + "          \"credit_id\": \"credit_id\",\n"
                + "          \"credit_name\": \"credit_name\",\n"
                + "          \"id\": \"id\",\n"
                + "          \"plan_id\": \"plan_id\",\n"
                + "          \"plan_name\": \"plan_name\",\n"
                + "          \"reset_cadence\": \"reset_cadence\",\n"
                + "          \"reset_start\": \"reset_start\",\n"
                + "          \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "        }\n"
                + "      ],\n"
                + "      \"is_default\": true,\n"
                + "      \"is_free\": true,\n"
                + "      \"is_trialable\": true,\n"
                + "      \"monthly_price\": {\n"
                + "        \"currency\": \"currency\",\n"
                + "        \"external_price_id\": \"external_price_id\",\n"
                + "        \"id\": \"id\",\n"
                + "        \"interval\": \"interval\",\n"
                + "        \"price\": 1,\n"
                + "        \"scheme\": \"scheme\"\n"
                + "      },\n"
                + "      \"name\": \"name\",\n"
                + "      \"one_time_price\": {\n"
                + "        \"currency\": \"currency\",\n"
                + "        \"external_price_id\": \"external_price_id\",\n"
                + "        \"id\": \"id\",\n"
                + "        \"interval\": \"interval\",\n"
                + "        \"price\": 1,\n"
                + "        \"scheme\": \"scheme\"\n"
                + "      },\n"
                + "      \"plan_type\": \"plan_type\",\n"
                + "      \"trial_days\": 1,\n"
                + "      \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"yearly_price\": {\n"
                + "        \"currency\": \"currency\",\n"
                + "        \"external_price_id\": \"external_price_id\",\n"
                + "        \"id\": \"id\",\n"
                + "        \"interval\": \"interval\",\n"
                + "        \"price\": 1,\n"
                + "        \"scheme\": \"scheme\"\n"
                + "      }\n"
                + "    },\n"
                + "    \"show_credits\": true,\n"
                + "    \"show_period_toggle\": true,\n"
                + "    \"show_zero_price_as_free\": true,\n"
                + "    \"stripe_embed\": {\n"
                + "      \"account_id\": \"account_id\",\n"
                + "      \"publishable_key\": \"publishable_key\",\n"
                + "      \"schematic_publishable_key\": \"schematic_publishable_key\",\n"
                + "      \"setup_intent_client_secret\": \"setup_intent_client_secret\"\n"
                + "    },\n"
                + "    \"subscription\": {\n"
                + "      \"cancel_at\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"cancel_at_period_end\": true,\n"
                + "      \"currency\": \"currency\",\n"
                + "      \"customer_external_id\": \"customer_external_id\",\n"
                + "      \"discounts\": [\n"
                + "        {\n"
                + "          \"coupon_id\": \"coupon_id\",\n"
                + "          \"coupon_name\": \"coupon_name\",\n"
                + "          \"discount_external_id\": \"discount_external_id\",\n"
                + "          \"duration\": \"duration\",\n"
                + "          \"is_active\": true,\n"
                + "          \"started_at\": \"2024-01-15T09:30:00Z\",\n"
                + "          \"subscription_external_id\": \"subscription_external_id\"\n"
                + "        }\n"
                + "      ],\n"
                + "      \"expired_at\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"interval\": \"interval\",\n"
                + "      \"latest_invoice\": {\n"
                + "        \"amount_due\": 1,\n"
                + "        \"amount_paid\": 1,\n"
                + "        \"amount_remaining\": 1,\n"
                + "        \"collection_method\": \"collection_method\",\n"
                + "        \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"currency\": \"currency\",\n"
                + "        \"customer_external_id\": \"customer_external_id\",\n"
                + "        \"environment_id\": \"environment_id\",\n"
                + "        \"id\": \"id\",\n"
                + "        \"subtotal\": 1,\n"
                + "        \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "      },\n"
                + "      \"payment_method\": {\n"
                + "        \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"customer_external_id\": \"customer_external_id\",\n"
                + "        \"environment_id\": \"environment_id\",\n"
                + "        \"external_id\": \"external_id\",\n"
                + "        \"id\": \"id\",\n"
                + "        \"payment_method_type\": \"payment_method_type\",\n"
                + "        \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "      },\n"
                + "      \"products\": [\n"
                + "        {\n"
                + "          \"billing_scheme\": \"billing_scheme\",\n"
                + "          \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "          \"currency\": \"currency\",\n"
                + "          \"environment_id\": \"environment_id\",\n"
                + "          \"external_id\": \"external_id\",\n"
                + "          \"id\": \"id\",\n"
                + "          \"interval\": \"interval\",\n"
                + "          \"name\": \"name\",\n"
                + "          \"package_size\": 1,\n"
                + "          \"price\": 1,\n"
                + "          \"price_external_id\": \"price_external_id\",\n"
                + "          \"price_id\": \"price_id\",\n"
                + "          \"price_tier\": [\n"
                + "            {}\n"
                + "          ],\n"
                + "          \"quantity\": 1.1,\n"
                + "          \"subscription_id\": \"subscription_id\",\n"
                + "          \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "          \"usage_type\": \"usage_type\"\n"
                + "        }\n"
                + "      ],\n"
                + "      \"status\": \"status\",\n"
                + "      \"subscription_external_id\": \"subscription_external_id\",\n"
                + "      \"total_price\": 1,\n"
                + "      \"trial_end\": \"2024-01-15T09:30:00Z\"\n"
                + "    },\n"
                + "    \"trial_payment_method_required\": true,\n"
                + "    \"upcoming_invoice\": {\n"
                + "      \"amount_due\": 1,\n"
                + "      \"amount_paid\": 1,\n"
                + "      \"amount_remaining\": 1,\n"
                + "      \"collection_method\": \"collection_method\",\n"
                + "      \"company_id\": \"company_id\",\n"
                + "      \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"currency\": \"currency\",\n"
                + "      \"customer_external_id\": \"customer_external_id\",\n"
                + "      \"due_date\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"environment_id\": \"environment_id\",\n"
                + "      \"external_id\": \"external_id\",\n"
                + "      \"id\": \"id\",\n"
                + "      \"payment_method_external_id\": \"payment_method_external_id\",\n"
                + "      \"subscription_external_id\": \"subscription_external_id\",\n"
                + "      \"subtotal\": 1,\n"
                + "      \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"url\": \"url\"\n"
                + "    }\n"
                + "  },\n"
                + "  \"params\": {\n"
                + "    \"company_id\": \"company_id\",\n"
                + "    \"component_id\": \"component_id\"\n"
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
