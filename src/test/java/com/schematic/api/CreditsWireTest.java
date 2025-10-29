package com.schematic.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.schematic.api.core.ObjectMappers;
import com.schematic.api.resources.credits.requests.CountBillingCreditsGrantsRequest;
import com.schematic.api.resources.credits.requests.CountBillingCreditsRequest;
import com.schematic.api.resources.credits.requests.CountBillingPlanCreditGrantsRequest;
import com.schematic.api.resources.credits.requests.CountCreditBundlesRequest;
import com.schematic.api.resources.credits.requests.CountCreditLedgerRequest;
import com.schematic.api.resources.credits.requests.CreateBillingCreditRequestBody;
import com.schematic.api.resources.credits.requests.CreateBillingPlanCreditGrantRequestBody;
import com.schematic.api.resources.credits.requests.CreateCompanyCreditGrant;
import com.schematic.api.resources.credits.requests.CreateCreditBundleRequestBody;
import com.schematic.api.resources.credits.requests.GetEnrichedCreditLedgerRequest;
import com.schematic.api.resources.credits.requests.ListBillingCreditsRequest;
import com.schematic.api.resources.credits.requests.ListBillingPlanCreditGrantsRequest;
import com.schematic.api.resources.credits.requests.ListCompanyGrantsRequest;
import com.schematic.api.resources.credits.requests.ListCreditBundlesRequest;
import com.schematic.api.resources.credits.requests.ListGrantsForCreditRequest;
import com.schematic.api.resources.credits.requests.UpdateBillingCreditRequestBody;
import com.schematic.api.resources.credits.requests.UpdateCreditBundleDetailsRequestBody;
import com.schematic.api.resources.credits.requests.ZeroOutGrantRequestBody;
import com.schematic.api.resources.credits.types.CountBillingCreditsGrantsResponse;
import com.schematic.api.resources.credits.types.CountBillingCreditsResponse;
import com.schematic.api.resources.credits.types.CountBillingPlanCreditGrantsResponse;
import com.schematic.api.resources.credits.types.CountCreditBundlesRequestStatus;
import com.schematic.api.resources.credits.types.CountCreditBundlesResponse;
import com.schematic.api.resources.credits.types.CountCreditLedgerRequestPeriod;
import com.schematic.api.resources.credits.types.CountCreditLedgerResponse;
import com.schematic.api.resources.credits.types.CreateBillingCreditResponse;
import com.schematic.api.resources.credits.types.CreateBillingPlanCreditGrantRequestBodyResetCadence;
import com.schematic.api.resources.credits.types.CreateBillingPlanCreditGrantRequestBodyResetStart;
import com.schematic.api.resources.credits.types.CreateBillingPlanCreditGrantResponse;
import com.schematic.api.resources.credits.types.CreateCreditBundleResponse;
import com.schematic.api.resources.credits.types.DeleteBillingPlanCreditGrantResponse;
import com.schematic.api.resources.credits.types.DeleteCreditBundleResponse;
import com.schematic.api.resources.credits.types.GetCreditBundleResponse;
import com.schematic.api.resources.credits.types.GetEnrichedCreditLedgerRequestPeriod;
import com.schematic.api.resources.credits.types.GetEnrichedCreditLedgerResponse;
import com.schematic.api.resources.credits.types.GetSingleBillingCreditResponse;
import com.schematic.api.resources.credits.types.GrantBillingCreditsToCompanyResponse;
import com.schematic.api.resources.credits.types.ListBillingCreditsResponse;
import com.schematic.api.resources.credits.types.ListBillingPlanCreditGrantsResponse;
import com.schematic.api.resources.credits.types.ListCompanyGrantsRequestDir;
import com.schematic.api.resources.credits.types.ListCompanyGrantsRequestOrder;
import com.schematic.api.resources.credits.types.ListCompanyGrantsResponse;
import com.schematic.api.resources.credits.types.ListCreditBundlesRequestStatus;
import com.schematic.api.resources.credits.types.ListCreditBundlesResponse;
import com.schematic.api.resources.credits.types.ListGrantsForCreditResponse;
import com.schematic.api.resources.credits.types.SoftDeleteBillingCreditResponse;
import com.schematic.api.resources.credits.types.UpdateBillingCreditResponse;
import com.schematic.api.resources.credits.types.UpdateCreditBundleDetailsResponse;
import com.schematic.api.resources.credits.types.ZeroOutGrantResponse;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CreditsWireTest {
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
    public void testListBillingCredits() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":[{\"burn_strategy\":\"burn_strategy\",\"created_at\":\"2024-01-15T09:30:00Z\",\"default_expiry_unit\":\"default_expiry_unit\",\"default_expiry_unit_count\":1,\"default_rollover_policy\":\"default_rollover_policy\",\"description\":\"description\",\"icon\":\"icon\",\"id\":\"id\",\"name\":\"name\",\"plural_name\":\"plural_name\",\"price\":{\"currency\":\"currency\",\"external_price_id\":\"external_price_id\",\"id\":\"id\",\"interval\":\"interval\",\"price\":1,\"scheme\":\"scheme\"},\"product\":{\"account_id\":\"account_id\",\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"is_active\":true,\"name\":\"name\",\"price\":1.1,\"product_id\":\"product_id\",\"quantity\":1.1,\"updated_at\":\"2024-01-15T09:30:00Z\"},\"singular_name\":\"singular_name\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"params\":{\"ids\":[\"ids\"],\"limit\":1,\"name\":\"name\",\"offset\":1}}"));
        ListBillingCreditsResponse response = client.credits()
                .listBillingCredits(ListBillingCreditsRequest.builder()
                        .name("name")
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
                + "      \"burn_strategy\": \"burn_strategy\",\n"
                + "      \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"default_expiry_unit\": \"default_expiry_unit\",\n"
                + "      \"default_expiry_unit_count\": 1,\n"
                + "      \"default_rollover_policy\": \"default_rollover_policy\",\n"
                + "      \"description\": \"description\",\n"
                + "      \"icon\": \"icon\",\n"
                + "      \"id\": \"id\",\n"
                + "      \"name\": \"name\",\n"
                + "      \"plural_name\": \"plural_name\",\n"
                + "      \"price\": {\n"
                + "        \"currency\": \"currency\",\n"
                + "        \"external_price_id\": \"external_price_id\",\n"
                + "        \"id\": \"id\",\n"
                + "        \"interval\": \"interval\",\n"
                + "        \"price\": 1,\n"
                + "        \"scheme\": \"scheme\"\n"
                + "      },\n"
                + "      \"product\": {\n"
                + "        \"account_id\": \"account_id\",\n"
                + "        \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"environment_id\": \"environment_id\",\n"
                + "        \"external_id\": \"external_id\",\n"
                + "        \"is_active\": true,\n"
                + "        \"name\": \"name\",\n"
                + "        \"price\": 1.1,\n"
                + "        \"product_id\": \"product_id\",\n"
                + "        \"quantity\": 1.1,\n"
                + "        \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "      },\n"
                + "      \"singular_name\": \"singular_name\",\n"
                + "      \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "    }\n"
                + "  ],\n"
                + "  \"params\": {\n"
                + "    \"ids\": [\n"
                + "      \"ids\"\n"
                + "    ],\n"
                + "    \"limit\": 1,\n"
                + "    \"name\": \"name\",\n"
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
    public void testCreateBillingCredit() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":{\"burn_strategy\":\"burn_strategy\",\"created_at\":\"2024-01-15T09:30:00Z\",\"default_expiry_unit\":\"default_expiry_unit\",\"default_expiry_unit_count\":1,\"default_rollover_policy\":\"default_rollover_policy\",\"description\":\"description\",\"icon\":\"icon\",\"id\":\"id\",\"name\":\"name\",\"plural_name\":\"plural_name\",\"price\":{\"currency\":\"currency\",\"external_price_id\":\"external_price_id\",\"id\":\"id\",\"interval\":\"interval\",\"price\":1,\"price_decimal\":\"price_decimal\",\"scheme\":\"scheme\"},\"product\":{\"account_id\":\"account_id\",\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"is_active\":true,\"name\":\"name\",\"price\":1.1,\"price_decimal\":\"price_decimal\",\"product_id\":\"product_id\",\"quantity\":1.1,\"updated_at\":\"2024-01-15T09:30:00Z\"},\"singular_name\":\"singular_name\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"params\":{\"key\":\"value\"}}"));
        CreateBillingCreditResponse response = client.credits()
                .createBillingCredit(CreateBillingCreditRequestBody.builder()
                        .currency("currency")
                        .description("description")
                        .name("name")
                        .build());
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("POST", request.getMethod());
        // Validate request body
        String actualRequestBody = request.getBody().readUtf8();
        String expectedRequestBody = ""
                + "{\n"
                + "  \"currency\": \"currency\",\n"
                + "  \"description\": \"description\",\n"
                + "  \"name\": \"name\"\n"
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
                + "    \"burn_strategy\": \"burn_strategy\",\n"
                + "    \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"default_expiry_unit\": \"default_expiry_unit\",\n"
                + "    \"default_expiry_unit_count\": 1,\n"
                + "    \"default_rollover_policy\": \"default_rollover_policy\",\n"
                + "    \"description\": \"description\",\n"
                + "    \"icon\": \"icon\",\n"
                + "    \"id\": \"id\",\n"
                + "    \"name\": \"name\",\n"
                + "    \"plural_name\": \"plural_name\",\n"
                + "    \"price\": {\n"
                + "      \"currency\": \"currency\",\n"
                + "      \"external_price_id\": \"external_price_id\",\n"
                + "      \"id\": \"id\",\n"
                + "      \"interval\": \"interval\",\n"
                + "      \"price\": 1,\n"
                + "      \"price_decimal\": \"price_decimal\",\n"
                + "      \"scheme\": \"scheme\"\n"
                + "    },\n"
                + "    \"product\": {\n"
                + "      \"account_id\": \"account_id\",\n"
                + "      \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"environment_id\": \"environment_id\",\n"
                + "      \"external_id\": \"external_id\",\n"
                + "      \"is_active\": true,\n"
                + "      \"name\": \"name\",\n"
                + "      \"price\": 1.1,\n"
                + "      \"price_decimal\": \"price_decimal\",\n"
                + "      \"product_id\": \"product_id\",\n"
                + "      \"quantity\": 1.1,\n"
                + "      \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "    },\n"
                + "    \"singular_name\": \"singular_name\",\n"
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
    public void testGetSingleBillingCredit() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":{\"burn_strategy\":\"burn_strategy\",\"created_at\":\"2024-01-15T09:30:00Z\",\"default_expiry_unit\":\"default_expiry_unit\",\"default_expiry_unit_count\":1,\"default_rollover_policy\":\"default_rollover_policy\",\"description\":\"description\",\"icon\":\"icon\",\"id\":\"id\",\"name\":\"name\",\"plural_name\":\"plural_name\",\"price\":{\"currency\":\"currency\",\"external_price_id\":\"external_price_id\",\"id\":\"id\",\"interval\":\"interval\",\"price\":1,\"price_decimal\":\"price_decimal\",\"scheme\":\"scheme\"},\"product\":{\"account_id\":\"account_id\",\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"is_active\":true,\"name\":\"name\",\"price\":1.1,\"price_decimal\":\"price_decimal\",\"product_id\":\"product_id\",\"quantity\":1.1,\"updated_at\":\"2024-01-15T09:30:00Z\"},\"singular_name\":\"singular_name\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"params\":{\"key\":\"value\"}}"));
        GetSingleBillingCreditResponse response = client.credits().getSingleBillingCredit("credit_id");
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("GET", request.getMethod());

        // Validate response body
        Assertions.assertNotNull(response, "Response should not be null");
        String actualResponseJson = objectMapper.writeValueAsString(response);
        String expectedResponseBody = ""
                + "{\n"
                + "  \"data\": {\n"
                + "    \"burn_strategy\": \"burn_strategy\",\n"
                + "    \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"default_expiry_unit\": \"default_expiry_unit\",\n"
                + "    \"default_expiry_unit_count\": 1,\n"
                + "    \"default_rollover_policy\": \"default_rollover_policy\",\n"
                + "    \"description\": \"description\",\n"
                + "    \"icon\": \"icon\",\n"
                + "    \"id\": \"id\",\n"
                + "    \"name\": \"name\",\n"
                + "    \"plural_name\": \"plural_name\",\n"
                + "    \"price\": {\n"
                + "      \"currency\": \"currency\",\n"
                + "      \"external_price_id\": \"external_price_id\",\n"
                + "      \"id\": \"id\",\n"
                + "      \"interval\": \"interval\",\n"
                + "      \"price\": 1,\n"
                + "      \"price_decimal\": \"price_decimal\",\n"
                + "      \"scheme\": \"scheme\"\n"
                + "    },\n"
                + "    \"product\": {\n"
                + "      \"account_id\": \"account_id\",\n"
                + "      \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"environment_id\": \"environment_id\",\n"
                + "      \"external_id\": \"external_id\",\n"
                + "      \"is_active\": true,\n"
                + "      \"name\": \"name\",\n"
                + "      \"price\": 1.1,\n"
                + "      \"price_decimal\": \"price_decimal\",\n"
                + "      \"product_id\": \"product_id\",\n"
                + "      \"quantity\": 1.1,\n"
                + "      \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "    },\n"
                + "    \"singular_name\": \"singular_name\",\n"
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
    public void testUpdateBillingCredit() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":{\"burn_strategy\":\"burn_strategy\",\"created_at\":\"2024-01-15T09:30:00Z\",\"default_expiry_unit\":\"default_expiry_unit\",\"default_expiry_unit_count\":1,\"default_rollover_policy\":\"default_rollover_policy\",\"description\":\"description\",\"icon\":\"icon\",\"id\":\"id\",\"name\":\"name\",\"plural_name\":\"plural_name\",\"price\":{\"currency\":\"currency\",\"external_price_id\":\"external_price_id\",\"id\":\"id\",\"interval\":\"interval\",\"price\":1,\"price_decimal\":\"price_decimal\",\"scheme\":\"scheme\"},\"product\":{\"account_id\":\"account_id\",\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"is_active\":true,\"name\":\"name\",\"price\":1.1,\"price_decimal\":\"price_decimal\",\"product_id\":\"product_id\",\"quantity\":1.1,\"updated_at\":\"2024-01-15T09:30:00Z\"},\"singular_name\":\"singular_name\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"params\":{\"key\":\"value\"}}"));
        UpdateBillingCreditResponse response = client.credits()
                .updateBillingCredit(
                        "credit_id",
                        UpdateBillingCreditRequestBody.builder()
                                .description("description")
                                .name("name")
                                .build());
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("PUT", request.getMethod());
        // Validate request body
        String actualRequestBody = request.getBody().readUtf8();
        String expectedRequestBody =
                "" + "{\n" + "  \"description\": \"description\",\n" + "  \"name\": \"name\"\n" + "}";
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
                + "    \"burn_strategy\": \"burn_strategy\",\n"
                + "    \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"default_expiry_unit\": \"default_expiry_unit\",\n"
                + "    \"default_expiry_unit_count\": 1,\n"
                + "    \"default_rollover_policy\": \"default_rollover_policy\",\n"
                + "    \"description\": \"description\",\n"
                + "    \"icon\": \"icon\",\n"
                + "    \"id\": \"id\",\n"
                + "    \"name\": \"name\",\n"
                + "    \"plural_name\": \"plural_name\",\n"
                + "    \"price\": {\n"
                + "      \"currency\": \"currency\",\n"
                + "      \"external_price_id\": \"external_price_id\",\n"
                + "      \"id\": \"id\",\n"
                + "      \"interval\": \"interval\",\n"
                + "      \"price\": 1,\n"
                + "      \"price_decimal\": \"price_decimal\",\n"
                + "      \"scheme\": \"scheme\"\n"
                + "    },\n"
                + "    \"product\": {\n"
                + "      \"account_id\": \"account_id\",\n"
                + "      \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"environment_id\": \"environment_id\",\n"
                + "      \"external_id\": \"external_id\",\n"
                + "      \"is_active\": true,\n"
                + "      \"name\": \"name\",\n"
                + "      \"price\": 1.1,\n"
                + "      \"price_decimal\": \"price_decimal\",\n"
                + "      \"product_id\": \"product_id\",\n"
                + "      \"quantity\": 1.1,\n"
                + "      \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "    },\n"
                + "    \"singular_name\": \"singular_name\",\n"
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
    public void testSoftDeleteBillingCredit() throws Exception {
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody("{\"data\":{\"deleted\":true},\"params\":{\"key\":\"value\"}}"));
        SoftDeleteBillingCreditResponse response = client.credits().softDeleteBillingCredit("credit_id");
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
    public void testListCreditBundles() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":[{\"billing_invoice_id\":\"billing_invoice_id\",\"bundle_type\":\"bundle_type\",\"created_at\":\"2024-01-15T09:30:00Z\",\"credit_description\":\"credit_description\",\"credit_icon\":\"credit_icon\",\"credit_id\":\"credit_id\",\"credit_name\":\"credit_name\",\"expiry_type\":\"expiry_type\",\"expiry_unit\":\"expiry_unit\",\"expiry_unit_count\":1,\"has_grants\":true,\"id\":\"id\",\"name\":\"name\",\"plural_name\":\"plural_name\",\"price\":{\"currency\":\"currency\",\"external_price_id\":\"external_price_id\",\"id\":\"id\",\"interval\":\"interval\",\"price\":1,\"scheme\":\"scheme\"},\"quantity\":1,\"singular_name\":\"singular_name\",\"status\":\"status\",\"unit_price\":{\"currency\":\"currency\",\"external_price_id\":\"external_price_id\",\"id\":\"id\",\"interval\":\"interval\",\"price\":1,\"scheme\":\"scheme\"},\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"params\":{\"bundle_type\":\"fixed\",\"credit_id\":\"credit_id\",\"ids\":[\"ids\"],\"limit\":1,\"offset\":1,\"status\":\"active\"}}"));
        ListCreditBundlesResponse response = client.credits()
                .listCreditBundles(ListCreditBundlesRequest.builder()
                        .creditId("credit_id")
                        .status(ListCreditBundlesRequestStatus.ACTIVE)
                        .bundleType("fixed")
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
                + "      \"billing_invoice_id\": \"billing_invoice_id\",\n"
                + "      \"bundle_type\": \"bundle_type\",\n"
                + "      \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"credit_description\": \"credit_description\",\n"
                + "      \"credit_icon\": \"credit_icon\",\n"
                + "      \"credit_id\": \"credit_id\",\n"
                + "      \"credit_name\": \"credit_name\",\n"
                + "      \"expiry_type\": \"expiry_type\",\n"
                + "      \"expiry_unit\": \"expiry_unit\",\n"
                + "      \"expiry_unit_count\": 1,\n"
                + "      \"has_grants\": true,\n"
                + "      \"id\": \"id\",\n"
                + "      \"name\": \"name\",\n"
                + "      \"plural_name\": \"plural_name\",\n"
                + "      \"price\": {\n"
                + "        \"currency\": \"currency\",\n"
                + "        \"external_price_id\": \"external_price_id\",\n"
                + "        \"id\": \"id\",\n"
                + "        \"interval\": \"interval\",\n"
                + "        \"price\": 1,\n"
                + "        \"scheme\": \"scheme\"\n"
                + "      },\n"
                + "      \"quantity\": 1,\n"
                + "      \"singular_name\": \"singular_name\",\n"
                + "      \"status\": \"status\",\n"
                + "      \"unit_price\": {\n"
                + "        \"currency\": \"currency\",\n"
                + "        \"external_price_id\": \"external_price_id\",\n"
                + "        \"id\": \"id\",\n"
                + "        \"interval\": \"interval\",\n"
                + "        \"price\": 1,\n"
                + "        \"scheme\": \"scheme\"\n"
                + "      },\n"
                + "      \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "    }\n"
                + "  ],\n"
                + "  \"params\": {\n"
                + "    \"bundle_type\": \"fixed\",\n"
                + "    \"credit_id\": \"credit_id\",\n"
                + "    \"ids\": [\n"
                + "      \"ids\"\n"
                + "    ],\n"
                + "    \"limit\": 1,\n"
                + "    \"offset\": 1,\n"
                + "    \"status\": \"active\"\n"
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
    public void testCreateCreditBundle() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":{\"billing_invoice_id\":\"billing_invoice_id\",\"bundle_type\":\"bundle_type\",\"created_at\":\"2024-01-15T09:30:00Z\",\"credit_description\":\"credit_description\",\"credit_icon\":\"credit_icon\",\"credit_id\":\"credit_id\",\"credit_name\":\"credit_name\",\"expiry_type\":\"expiry_type\",\"expiry_unit\":\"expiry_unit\",\"expiry_unit_count\":1,\"has_grants\":true,\"id\":\"id\",\"name\":\"name\",\"plural_name\":\"plural_name\",\"price\":{\"currency\":\"currency\",\"external_price_id\":\"external_price_id\",\"id\":\"id\",\"interval\":\"interval\",\"price\":1,\"price_decimal\":\"price_decimal\",\"scheme\":\"scheme\"},\"quantity\":1,\"singular_name\":\"singular_name\",\"status\":\"status\",\"unit_price\":{\"currency\":\"currency\",\"external_price_id\":\"external_price_id\",\"id\":\"id\",\"interval\":\"interval\",\"price\":1,\"price_decimal\":\"price_decimal\",\"scheme\":\"scheme\"},\"updated_at\":\"2024-01-15T09:30:00Z\"},\"params\":{\"key\":\"value\"}}"));
        CreateCreditBundleResponse response = client.credits()
                .createCreditBundle(CreateCreditBundleRequestBody.builder()
                        .bundleName("bundle_name")
                        .creditId("credit_id")
                        .currency("currency")
                        .pricePerUnit(1)
                        .build());
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("POST", request.getMethod());
        // Validate request body
        String actualRequestBody = request.getBody().readUtf8();
        String expectedRequestBody = ""
                + "{\n"
                + "  \"bundle_name\": \"bundle_name\",\n"
                + "  \"credit_id\": \"credit_id\",\n"
                + "  \"currency\": \"currency\",\n"
                + "  \"price_per_unit\": 1\n"
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
                + "    \"billing_invoice_id\": \"billing_invoice_id\",\n"
                + "    \"bundle_type\": \"bundle_type\",\n"
                + "    \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"credit_description\": \"credit_description\",\n"
                + "    \"credit_icon\": \"credit_icon\",\n"
                + "    \"credit_id\": \"credit_id\",\n"
                + "    \"credit_name\": \"credit_name\",\n"
                + "    \"expiry_type\": \"expiry_type\",\n"
                + "    \"expiry_unit\": \"expiry_unit\",\n"
                + "    \"expiry_unit_count\": 1,\n"
                + "    \"has_grants\": true,\n"
                + "    \"id\": \"id\",\n"
                + "    \"name\": \"name\",\n"
                + "    \"plural_name\": \"plural_name\",\n"
                + "    \"price\": {\n"
                + "      \"currency\": \"currency\",\n"
                + "      \"external_price_id\": \"external_price_id\",\n"
                + "      \"id\": \"id\",\n"
                + "      \"interval\": \"interval\",\n"
                + "      \"price\": 1,\n"
                + "      \"price_decimal\": \"price_decimal\",\n"
                + "      \"scheme\": \"scheme\"\n"
                + "    },\n"
                + "    \"quantity\": 1,\n"
                + "    \"singular_name\": \"singular_name\",\n"
                + "    \"status\": \"status\",\n"
                + "    \"unit_price\": {\n"
                + "      \"currency\": \"currency\",\n"
                + "      \"external_price_id\": \"external_price_id\",\n"
                + "      \"id\": \"id\",\n"
                + "      \"interval\": \"interval\",\n"
                + "      \"price\": 1,\n"
                + "      \"price_decimal\": \"price_decimal\",\n"
                + "      \"scheme\": \"scheme\"\n"
                + "    },\n"
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
    public void testGetCreditBundle() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":{\"billing_invoice_id\":\"billing_invoice_id\",\"bundle_type\":\"bundle_type\",\"created_at\":\"2024-01-15T09:30:00Z\",\"credit_description\":\"credit_description\",\"credit_icon\":\"credit_icon\",\"credit_id\":\"credit_id\",\"credit_name\":\"credit_name\",\"expiry_type\":\"expiry_type\",\"expiry_unit\":\"expiry_unit\",\"expiry_unit_count\":1,\"has_grants\":true,\"id\":\"id\",\"name\":\"name\",\"plural_name\":\"plural_name\",\"price\":{\"currency\":\"currency\",\"external_price_id\":\"external_price_id\",\"id\":\"id\",\"interval\":\"interval\",\"price\":1,\"price_decimal\":\"price_decimal\",\"scheme\":\"scheme\"},\"quantity\":1,\"singular_name\":\"singular_name\",\"status\":\"status\",\"unit_price\":{\"currency\":\"currency\",\"external_price_id\":\"external_price_id\",\"id\":\"id\",\"interval\":\"interval\",\"price\":1,\"price_decimal\":\"price_decimal\",\"scheme\":\"scheme\"},\"updated_at\":\"2024-01-15T09:30:00Z\"},\"params\":{\"key\":\"value\"}}"));
        GetCreditBundleResponse response = client.credits().getCreditBundle("bundle_id");
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("GET", request.getMethod());

        // Validate response body
        Assertions.assertNotNull(response, "Response should not be null");
        String actualResponseJson = objectMapper.writeValueAsString(response);
        String expectedResponseBody = ""
                + "{\n"
                + "  \"data\": {\n"
                + "    \"billing_invoice_id\": \"billing_invoice_id\",\n"
                + "    \"bundle_type\": \"bundle_type\",\n"
                + "    \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"credit_description\": \"credit_description\",\n"
                + "    \"credit_icon\": \"credit_icon\",\n"
                + "    \"credit_id\": \"credit_id\",\n"
                + "    \"credit_name\": \"credit_name\",\n"
                + "    \"expiry_type\": \"expiry_type\",\n"
                + "    \"expiry_unit\": \"expiry_unit\",\n"
                + "    \"expiry_unit_count\": 1,\n"
                + "    \"has_grants\": true,\n"
                + "    \"id\": \"id\",\n"
                + "    \"name\": \"name\",\n"
                + "    \"plural_name\": \"plural_name\",\n"
                + "    \"price\": {\n"
                + "      \"currency\": \"currency\",\n"
                + "      \"external_price_id\": \"external_price_id\",\n"
                + "      \"id\": \"id\",\n"
                + "      \"interval\": \"interval\",\n"
                + "      \"price\": 1,\n"
                + "      \"price_decimal\": \"price_decimal\",\n"
                + "      \"scheme\": \"scheme\"\n"
                + "    },\n"
                + "    \"quantity\": 1,\n"
                + "    \"singular_name\": \"singular_name\",\n"
                + "    \"status\": \"status\",\n"
                + "    \"unit_price\": {\n"
                + "      \"currency\": \"currency\",\n"
                + "      \"external_price_id\": \"external_price_id\",\n"
                + "      \"id\": \"id\",\n"
                + "      \"interval\": \"interval\",\n"
                + "      \"price\": 1,\n"
                + "      \"price_decimal\": \"price_decimal\",\n"
                + "      \"scheme\": \"scheme\"\n"
                + "    },\n"
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
    public void testUpdateCreditBundleDetails() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":{\"billing_invoice_id\":\"billing_invoice_id\",\"bundle_type\":\"bundle_type\",\"created_at\":\"2024-01-15T09:30:00Z\",\"credit_description\":\"credit_description\",\"credit_icon\":\"credit_icon\",\"credit_id\":\"credit_id\",\"credit_name\":\"credit_name\",\"expiry_type\":\"expiry_type\",\"expiry_unit\":\"expiry_unit\",\"expiry_unit_count\":1,\"has_grants\":true,\"id\":\"id\",\"name\":\"name\",\"plural_name\":\"plural_name\",\"price\":{\"currency\":\"currency\",\"external_price_id\":\"external_price_id\",\"id\":\"id\",\"interval\":\"interval\",\"price\":1,\"price_decimal\":\"price_decimal\",\"scheme\":\"scheme\"},\"quantity\":1,\"singular_name\":\"singular_name\",\"status\":\"status\",\"unit_price\":{\"currency\":\"currency\",\"external_price_id\":\"external_price_id\",\"id\":\"id\",\"interval\":\"interval\",\"price\":1,\"price_decimal\":\"price_decimal\",\"scheme\":\"scheme\"},\"updated_at\":\"2024-01-15T09:30:00Z\"},\"params\":{\"key\":\"value\"}}"));
        UpdateCreditBundleDetailsResponse response = client.credits()
                .updateCreditBundleDetails(
                        "bundle_id",
                        UpdateCreditBundleDetailsRequestBody.builder()
                                .bundleName("bundle_name")
                                .pricePerUnit(1)
                                .build());
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("PUT", request.getMethod());
        // Validate request body
        String actualRequestBody = request.getBody().readUtf8();
        String expectedRequestBody =
                "" + "{\n" + "  \"bundle_name\": \"bundle_name\",\n" + "  \"price_per_unit\": 1\n" + "}";
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
                + "    \"billing_invoice_id\": \"billing_invoice_id\",\n"
                + "    \"bundle_type\": \"bundle_type\",\n"
                + "    \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"credit_description\": \"credit_description\",\n"
                + "    \"credit_icon\": \"credit_icon\",\n"
                + "    \"credit_id\": \"credit_id\",\n"
                + "    \"credit_name\": \"credit_name\",\n"
                + "    \"expiry_type\": \"expiry_type\",\n"
                + "    \"expiry_unit\": \"expiry_unit\",\n"
                + "    \"expiry_unit_count\": 1,\n"
                + "    \"has_grants\": true,\n"
                + "    \"id\": \"id\",\n"
                + "    \"name\": \"name\",\n"
                + "    \"plural_name\": \"plural_name\",\n"
                + "    \"price\": {\n"
                + "      \"currency\": \"currency\",\n"
                + "      \"external_price_id\": \"external_price_id\",\n"
                + "      \"id\": \"id\",\n"
                + "      \"interval\": \"interval\",\n"
                + "      \"price\": 1,\n"
                + "      \"price_decimal\": \"price_decimal\",\n"
                + "      \"scheme\": \"scheme\"\n"
                + "    },\n"
                + "    \"quantity\": 1,\n"
                + "    \"singular_name\": \"singular_name\",\n"
                + "    \"status\": \"status\",\n"
                + "    \"unit_price\": {\n"
                + "      \"currency\": \"currency\",\n"
                + "      \"external_price_id\": \"external_price_id\",\n"
                + "      \"id\": \"id\",\n"
                + "      \"interval\": \"interval\",\n"
                + "      \"price\": 1,\n"
                + "      \"price_decimal\": \"price_decimal\",\n"
                + "      \"scheme\": \"scheme\"\n"
                + "    },\n"
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
    public void testDeleteCreditBundle() throws Exception {
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody("{\"data\":{\"deleted\":true},\"params\":{\"key\":\"value\"}}"));
        DeleteCreditBundleResponse response = client.credits().deleteCreditBundle("bundle_id");
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
    public void testCountCreditBundles() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":{\"count\":1},\"params\":{\"bundle_type\":\"fixed\",\"credit_id\":\"credit_id\",\"ids\":[\"ids\"],\"limit\":1,\"offset\":1,\"status\":\"active\"}}"));
        CountCreditBundlesResponse response = client.credits()
                .countCreditBundles(CountCreditBundlesRequest.builder()
                        .creditId("credit_id")
                        .status(CountCreditBundlesRequestStatus.ACTIVE)
                        .bundleType("fixed")
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
                + "    \"bundle_type\": \"fixed\",\n"
                + "    \"credit_id\": \"credit_id\",\n"
                + "    \"ids\": [\n"
                + "      \"ids\"\n"
                + "    ],\n"
                + "    \"limit\": 1,\n"
                + "    \"offset\": 1,\n"
                + "    \"status\": \"active\"\n"
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
    public void testCountBillingCredits() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":{\"count\":1},\"params\":{\"ids\":[\"ids\"],\"limit\":1,\"name\":\"name\",\"offset\":1}}"));
        CountBillingCreditsResponse response = client.credits()
                .countBillingCredits(CountBillingCreditsRequest.builder()
                        .name("name")
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
                + "    \"name\": \"name\",\n"
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
    public void testZeroOutGrant() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":{\"company_id\":\"company_id\",\"company_name\":\"company_name\",\"created_at\":\"2024-01-15T09:30:00Z\",\"credit_icon\":\"credit_icon\",\"credit_id\":\"credit_id\",\"credit_name\":\"credit_name\",\"expires_at\":\"2024-01-15T09:30:00Z\",\"grant_reason\":\"grant_reason\",\"id\":\"id\",\"plan_id\":\"plan_id\",\"plan_name\":\"plan_name\",\"price\":{\"currency\":\"currency\",\"external_price_id\":\"external_price_id\",\"id\":\"id\",\"interval\":\"interval\",\"price\":1,\"price_decimal\":\"price_decimal\",\"scheme\":\"scheme\"},\"quantity\":1,\"quantity_remaining\":1.1,\"quantity_used\":1.1,\"source_label\":\"source_label\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"valid_from\":\"2024-01-15T09:30:00Z\",\"zeroed_out_date\":\"2024-01-15T09:30:00Z\",\"zeroed_out_reason\":\"zeroed_out_reason\"},\"params\":{\"key\":\"value\"}}"));
        ZeroOutGrantResponse response = client.credits()
                .zeroOutGrant("grant_id", ZeroOutGrantRequestBody.builder().build());
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
                + "    \"company_id\": \"company_id\",\n"
                + "    \"company_name\": \"company_name\",\n"
                + "    \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"credit_icon\": \"credit_icon\",\n"
                + "    \"credit_id\": \"credit_id\",\n"
                + "    \"credit_name\": \"credit_name\",\n"
                + "    \"expires_at\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"grant_reason\": \"grant_reason\",\n"
                + "    \"id\": \"id\",\n"
                + "    \"plan_id\": \"plan_id\",\n"
                + "    \"plan_name\": \"plan_name\",\n"
                + "    \"price\": {\n"
                + "      \"currency\": \"currency\",\n"
                + "      \"external_price_id\": \"external_price_id\",\n"
                + "      \"id\": \"id\",\n"
                + "      \"interval\": \"interval\",\n"
                + "      \"price\": 1,\n"
                + "      \"price_decimal\": \"price_decimal\",\n"
                + "      \"scheme\": \"scheme\"\n"
                + "    },\n"
                + "    \"quantity\": 1,\n"
                + "    \"quantity_remaining\": 1.1,\n"
                + "    \"quantity_used\": 1.1,\n"
                + "    \"source_label\": \"source_label\",\n"
                + "    \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"valid_from\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"zeroed_out_date\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"zeroed_out_reason\": \"zeroed_out_reason\"\n"
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
    public void testGrantBillingCreditsToCompany() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":{\"company_id\":\"company_id\",\"company_name\":\"company_name\",\"created_at\":\"2024-01-15T09:30:00Z\",\"credit_icon\":\"credit_icon\",\"credit_id\":\"credit_id\",\"credit_name\":\"credit_name\",\"expires_at\":\"2024-01-15T09:30:00Z\",\"grant_reason\":\"grant_reason\",\"id\":\"id\",\"plan_id\":\"plan_id\",\"plan_name\":\"plan_name\",\"price\":{\"currency\":\"currency\",\"external_price_id\":\"external_price_id\",\"id\":\"id\",\"interval\":\"interval\",\"price\":1,\"price_decimal\":\"price_decimal\",\"scheme\":\"scheme\"},\"quantity\":1,\"quantity_remaining\":1.1,\"quantity_used\":1.1,\"source_label\":\"source_label\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"valid_from\":\"2024-01-15T09:30:00Z\",\"zeroed_out_date\":\"2024-01-15T09:30:00Z\",\"zeroed_out_reason\":\"zeroed_out_reason\"},\"params\":{\"key\":\"value\"}}"));
        GrantBillingCreditsToCompanyResponse response = client.credits()
                .grantBillingCreditsToCompany(CreateCompanyCreditGrant.builder()
                        .companyId("company_id")
                        .creditId("credit_id")
                        .quantity(1)
                        .reason("reason")
                        .build());
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("POST", request.getMethod());
        // Validate request body
        String actualRequestBody = request.getBody().readUtf8();
        String expectedRequestBody = ""
                + "{\n"
                + "  \"company_id\": \"company_id\",\n"
                + "  \"credit_id\": \"credit_id\",\n"
                + "  \"quantity\": 1,\n"
                + "  \"reason\": \"reason\"\n"
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
                + "    \"company_id\": \"company_id\",\n"
                + "    \"company_name\": \"company_name\",\n"
                + "    \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"credit_icon\": \"credit_icon\",\n"
                + "    \"credit_id\": \"credit_id\",\n"
                + "    \"credit_name\": \"credit_name\",\n"
                + "    \"expires_at\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"grant_reason\": \"grant_reason\",\n"
                + "    \"id\": \"id\",\n"
                + "    \"plan_id\": \"plan_id\",\n"
                + "    \"plan_name\": \"plan_name\",\n"
                + "    \"price\": {\n"
                + "      \"currency\": \"currency\",\n"
                + "      \"external_price_id\": \"external_price_id\",\n"
                + "      \"id\": \"id\",\n"
                + "      \"interval\": \"interval\",\n"
                + "      \"price\": 1,\n"
                + "      \"price_decimal\": \"price_decimal\",\n"
                + "      \"scheme\": \"scheme\"\n"
                + "    },\n"
                + "    \"quantity\": 1,\n"
                + "    \"quantity_remaining\": 1.1,\n"
                + "    \"quantity_used\": 1.1,\n"
                + "    \"source_label\": \"source_label\",\n"
                + "    \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"valid_from\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"zeroed_out_date\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"zeroed_out_reason\": \"zeroed_out_reason\"\n"
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
    public void testListCompanyGrants() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":[{\"company_id\":\"company_id\",\"company_name\":\"company_name\",\"created_at\":\"2024-01-15T09:30:00Z\",\"credit_icon\":\"credit_icon\",\"credit_id\":\"credit_id\",\"credit_name\":\"credit_name\",\"expires_at\":\"2024-01-15T09:30:00Z\",\"grant_reason\":\"grant_reason\",\"id\":\"id\",\"plan_id\":\"plan_id\",\"plan_name\":\"plan_name\",\"price\":{\"currency\":\"currency\",\"external_price_id\":\"external_price_id\",\"id\":\"id\",\"interval\":\"interval\",\"price\":1,\"scheme\":\"scheme\"},\"quantity\":1,\"quantity_remaining\":1.1,\"quantity_used\":1.1,\"source_label\":\"source_label\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"valid_from\":\"2024-01-15T09:30:00Z\",\"zeroed_out_date\":\"2024-01-15T09:30:00Z\",\"zeroed_out_reason\":\"zeroed_out_reason\"}],\"params\":{\"company_id\":\"company_id\",\"dir\":\"asc\",\"limit\":1,\"offset\":1,\"order\":\"created_at\"}}"));
        ListCompanyGrantsResponse response = client.credits()
                .listCompanyGrants(ListCompanyGrantsRequest.builder()
                        .companyId("company_id")
                        .order(ListCompanyGrantsRequestOrder.CREATED_AT)
                        .dir(ListCompanyGrantsRequestDir.ASC)
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
                + "      \"company_id\": \"company_id\",\n"
                + "      \"company_name\": \"company_name\",\n"
                + "      \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"credit_icon\": \"credit_icon\",\n"
                + "      \"credit_id\": \"credit_id\",\n"
                + "      \"credit_name\": \"credit_name\",\n"
                + "      \"expires_at\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"grant_reason\": \"grant_reason\",\n"
                + "      \"id\": \"id\",\n"
                + "      \"plan_id\": \"plan_id\",\n"
                + "      \"plan_name\": \"plan_name\",\n"
                + "      \"price\": {\n"
                + "        \"currency\": \"currency\",\n"
                + "        \"external_price_id\": \"external_price_id\",\n"
                + "        \"id\": \"id\",\n"
                + "        \"interval\": \"interval\",\n"
                + "        \"price\": 1,\n"
                + "        \"scheme\": \"scheme\"\n"
                + "      },\n"
                + "      \"quantity\": 1,\n"
                + "      \"quantity_remaining\": 1.1,\n"
                + "      \"quantity_used\": 1.1,\n"
                + "      \"source_label\": \"source_label\",\n"
                + "      \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"valid_from\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"zeroed_out_date\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"zeroed_out_reason\": \"zeroed_out_reason\"\n"
                + "    }\n"
                + "  ],\n"
                + "  \"params\": {\n"
                + "    \"company_id\": \"company_id\",\n"
                + "    \"dir\": \"asc\",\n"
                + "    \"limit\": 1,\n"
                + "    \"offset\": 1,\n"
                + "    \"order\": \"created_at\"\n"
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
    public void testCountBillingCreditsGrants() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":{\"count\":1},\"params\":{\"credit_id\":\"credit_id\",\"ids\":[\"ids\"],\"limit\":1,\"offset\":1}}"));
        CountBillingCreditsGrantsResponse response = client.credits()
                .countBillingCreditsGrants(CountBillingCreditsGrantsRequest.builder()
                        .creditId("credit_id")
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
                + "    \"credit_id\": \"credit_id\",\n"
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
    public void testListGrantsForCredit() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":[{\"company_id\":\"company_id\",\"company_name\":\"company_name\",\"created_at\":\"2024-01-15T09:30:00Z\",\"credit_icon\":\"credit_icon\",\"credit_id\":\"credit_id\",\"credit_name\":\"credit_name\",\"expires_at\":\"2024-01-15T09:30:00Z\",\"grant_reason\":\"grant_reason\",\"id\":\"id\",\"plan_id\":\"plan_id\",\"plan_name\":\"plan_name\",\"price\":{\"currency\":\"currency\",\"external_price_id\":\"external_price_id\",\"id\":\"id\",\"interval\":\"interval\",\"price\":1,\"scheme\":\"scheme\"},\"quantity\":1,\"quantity_remaining\":1.1,\"quantity_used\":1.1,\"source_label\":\"source_label\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"valid_from\":\"2024-01-15T09:30:00Z\",\"zeroed_out_date\":\"2024-01-15T09:30:00Z\",\"zeroed_out_reason\":\"zeroed_out_reason\"}],\"params\":{\"credit_id\":\"credit_id\",\"ids\":[\"ids\"],\"limit\":1,\"offset\":1}}"));
        ListGrantsForCreditResponse response = client.credits()
                .listGrantsForCredit(ListGrantsForCreditRequest.builder()
                        .creditId("credit_id")
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
                + "      \"company_id\": \"company_id\",\n"
                + "      \"company_name\": \"company_name\",\n"
                + "      \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"credit_icon\": \"credit_icon\",\n"
                + "      \"credit_id\": \"credit_id\",\n"
                + "      \"credit_name\": \"credit_name\",\n"
                + "      \"expires_at\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"grant_reason\": \"grant_reason\",\n"
                + "      \"id\": \"id\",\n"
                + "      \"plan_id\": \"plan_id\",\n"
                + "      \"plan_name\": \"plan_name\",\n"
                + "      \"price\": {\n"
                + "        \"currency\": \"currency\",\n"
                + "        \"external_price_id\": \"external_price_id\",\n"
                + "        \"id\": \"id\",\n"
                + "        \"interval\": \"interval\",\n"
                + "        \"price\": 1,\n"
                + "        \"scheme\": \"scheme\"\n"
                + "      },\n"
                + "      \"quantity\": 1,\n"
                + "      \"quantity_remaining\": 1.1,\n"
                + "      \"quantity_used\": 1.1,\n"
                + "      \"source_label\": \"source_label\",\n"
                + "      \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"valid_from\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"zeroed_out_date\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"zeroed_out_reason\": \"zeroed_out_reason\"\n"
                + "    }\n"
                + "  ],\n"
                + "  \"params\": {\n"
                + "    \"credit_id\": \"credit_id\",\n"
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
    public void testGetEnrichedCreditLedger() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":[{\"billing_credit_id\":\"billing_credit_id\",\"company\":{\"id\":\"id\",\"name\":\"name\"},\"company_id\":\"company_id\",\"credit\":{\"id\":\"id\",\"name\":\"name\"},\"expired_grant_count\":1,\"feature\":{\"id\":\"id\",\"name\":\"name\"},\"feature_id\":\"feature_id\",\"first_transaction_at\":\"2024-01-15T09:30:00Z\",\"free_grant_count\":1,\"grant_count\":1,\"last_transaction_at\":\"2024-01-15T09:30:00Z\",\"manually_zeroed_count\":1,\"net_change\":1.1,\"plan_grant_count\":1,\"purchased_grant_count\":1,\"time_bucket\":\"2024-01-15T09:30:00Z\",\"total_consumed\":1.1,\"total_granted\":1.1,\"transaction_count\":1,\"usage_count\":1,\"zeroed_out_count\":1}],\"params\":{\"billing_credit_id\":\"billing_credit_id\",\"company_id\":\"company_id\",\"end_time\":\"end_time\",\"feature_id\":\"feature_id\",\"limit\":1,\"offset\":1,\"period\":\"daily\",\"start_time\":\"start_time\"}}"));
        GetEnrichedCreditLedgerResponse response = client.credits()
                .getEnrichedCreditLedger(GetEnrichedCreditLedgerRequest.builder()
                        .companyId("company_id")
                        .period(GetEnrichedCreditLedgerRequestPeriod.DAILY)
                        .billingCreditId("billing_credit_id")
                        .featureId("feature_id")
                        .startTime("start_time")
                        .endTime("end_time")
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
                + "      \"billing_credit_id\": \"billing_credit_id\",\n"
                + "      \"company\": {\n"
                + "        \"id\": \"id\",\n"
                + "        \"name\": \"name\"\n"
                + "      },\n"
                + "      \"company_id\": \"company_id\",\n"
                + "      \"credit\": {\n"
                + "        \"id\": \"id\",\n"
                + "        \"name\": \"name\"\n"
                + "      },\n"
                + "      \"expired_grant_count\": 1,\n"
                + "      \"feature\": {\n"
                + "        \"id\": \"id\",\n"
                + "        \"name\": \"name\"\n"
                + "      },\n"
                + "      \"feature_id\": \"feature_id\",\n"
                + "      \"first_transaction_at\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"free_grant_count\": 1,\n"
                + "      \"grant_count\": 1,\n"
                + "      \"last_transaction_at\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"manually_zeroed_count\": 1,\n"
                + "      \"net_change\": 1.1,\n"
                + "      \"plan_grant_count\": 1,\n"
                + "      \"purchased_grant_count\": 1,\n"
                + "      \"time_bucket\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"total_consumed\": 1.1,\n"
                + "      \"total_granted\": 1.1,\n"
                + "      \"transaction_count\": 1,\n"
                + "      \"usage_count\": 1,\n"
                + "      \"zeroed_out_count\": 1\n"
                + "    }\n"
                + "  ],\n"
                + "  \"params\": {\n"
                + "    \"billing_credit_id\": \"billing_credit_id\",\n"
                + "    \"company_id\": \"company_id\",\n"
                + "    \"end_time\": \"end_time\",\n"
                + "    \"feature_id\": \"feature_id\",\n"
                + "    \"limit\": 1,\n"
                + "    \"offset\": 1,\n"
                + "    \"period\": \"daily\",\n"
                + "    \"start_time\": \"start_time\"\n"
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
    public void testCountCreditLedger() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":{\"count\":1},\"params\":{\"billing_credit_id\":\"billing_credit_id\",\"company_id\":\"company_id\",\"end_time\":\"end_time\",\"feature_id\":\"feature_id\",\"limit\":1,\"offset\":1,\"period\":\"daily\",\"start_time\":\"start_time\"}}"));
        CountCreditLedgerResponse response = client.credits()
                .countCreditLedger(CountCreditLedgerRequest.builder()
                        .companyId("company_id")
                        .period(CountCreditLedgerRequestPeriod.DAILY)
                        .billingCreditId("billing_credit_id")
                        .featureId("feature_id")
                        .startTime("start_time")
                        .endTime("end_time")
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
                + "    \"billing_credit_id\": \"billing_credit_id\",\n"
                + "    \"company_id\": \"company_id\",\n"
                + "    \"end_time\": \"end_time\",\n"
                + "    \"feature_id\": \"feature_id\",\n"
                + "    \"limit\": 1,\n"
                + "    \"offset\": 1,\n"
                + "    \"period\": \"daily\",\n"
                + "    \"start_time\": \"start_time\"\n"
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
    public void testListBillingPlanCreditGrants() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"credit_amount\":1,\"credit_id\":\"credit_id\",\"credit_name\":\"credit_name\",\"credit_plural_name\":\"credit_plural_name\",\"credit_singular_name\":\"credit_singular_name\",\"expiry_type\":\"expiry_type\",\"expiry_unit\":\"expiry_unit\",\"expiry_unit_count\":1,\"id\":\"id\",\"plan_id\":\"plan_id\",\"plan_name\":\"plan_name\",\"reset_cadence\":\"reset_cadence\",\"reset_start\":\"reset_start\",\"reset_type\":\"reset_type\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"params\":{\"credit_id\":\"credit_id\",\"ids\":[\"ids\"],\"limit\":1,\"offset\":1,\"plan_id\":\"plan_id\",\"plan_ids\":[\"plan_ids\"]}}"));
        ListBillingPlanCreditGrantsResponse response = client.credits()
                .listBillingPlanCreditGrants(ListBillingPlanCreditGrantsRequest.builder()
                        .creditId("credit_id")
                        .planId("plan_id")
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
                + "      \"credit_amount\": 1,\n"
                + "      \"credit_id\": \"credit_id\",\n"
                + "      \"credit_name\": \"credit_name\",\n"
                + "      \"credit_plural_name\": \"credit_plural_name\",\n"
                + "      \"credit_singular_name\": \"credit_singular_name\",\n"
                + "      \"expiry_type\": \"expiry_type\",\n"
                + "      \"expiry_unit\": \"expiry_unit\",\n"
                + "      \"expiry_unit_count\": 1,\n"
                + "      \"id\": \"id\",\n"
                + "      \"plan_id\": \"plan_id\",\n"
                + "      \"plan_name\": \"plan_name\",\n"
                + "      \"reset_cadence\": \"reset_cadence\",\n"
                + "      \"reset_start\": \"reset_start\",\n"
                + "      \"reset_type\": \"reset_type\",\n"
                + "      \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "    }\n"
                + "  ],\n"
                + "  \"params\": {\n"
                + "    \"credit_id\": \"credit_id\",\n"
                + "    \"ids\": [\n"
                + "      \"ids\"\n"
                + "    ],\n"
                + "    \"limit\": 1,\n"
                + "    \"offset\": 1,\n"
                + "    \"plan_id\": \"plan_id\",\n"
                + "    \"plan_ids\": [\n"
                + "      \"plan_ids\"\n"
                + "    ]\n"
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
    public void testCreateBillingPlanCreditGrant() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"credit_amount\":1,\"credit_id\":\"credit_id\",\"credit_name\":\"credit_name\",\"credit_plural_name\":\"credit_plural_name\",\"credit_singular_name\":\"credit_singular_name\",\"expiry_type\":\"expiry_type\",\"expiry_unit\":\"expiry_unit\",\"expiry_unit_count\":1,\"id\":\"id\",\"plan_id\":\"plan_id\",\"plan_name\":\"plan_name\",\"reset_cadence\":\"reset_cadence\",\"reset_start\":\"reset_start\",\"reset_type\":\"reset_type\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"params\":{\"key\":\"value\"}}"));
        CreateBillingPlanCreditGrantResponse response = client.credits()
                .createBillingPlanCreditGrant(CreateBillingPlanCreditGrantRequestBody.builder()
                        .creditAmount(1)
                        .creditId("credit_id")
                        .planId("plan_id")
                        .resetCadence(CreateBillingPlanCreditGrantRequestBodyResetCadence.MONTHLY)
                        .resetStart(CreateBillingPlanCreditGrantRequestBodyResetStart.BILLING_PERIOD)
                        .build());
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("POST", request.getMethod());
        // Validate request body
        String actualRequestBody = request.getBody().readUtf8();
        String expectedRequestBody = ""
                + "{\n"
                + "  \"credit_amount\": 1,\n"
                + "  \"credit_id\": \"credit_id\",\n"
                + "  \"plan_id\": \"plan_id\",\n"
                + "  \"reset_cadence\": \"monthly\",\n"
                + "  \"reset_start\": \"billing_period\"\n"
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
                + "    \"credit_amount\": 1,\n"
                + "    \"credit_id\": \"credit_id\",\n"
                + "    \"credit_name\": \"credit_name\",\n"
                + "    \"credit_plural_name\": \"credit_plural_name\",\n"
                + "    \"credit_singular_name\": \"credit_singular_name\",\n"
                + "    \"expiry_type\": \"expiry_type\",\n"
                + "    \"expiry_unit\": \"expiry_unit\",\n"
                + "    \"expiry_unit_count\": 1,\n"
                + "    \"id\": \"id\",\n"
                + "    \"plan_id\": \"plan_id\",\n"
                + "    \"plan_name\": \"plan_name\",\n"
                + "    \"reset_cadence\": \"reset_cadence\",\n"
                + "    \"reset_start\": \"reset_start\",\n"
                + "    \"reset_type\": \"reset_type\",\n"
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
    public void testDeleteBillingPlanCreditGrant() throws Exception {
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody("{\"data\":{\"deleted\":true},\"params\":{\"key\":\"value\"}}"));
        DeleteBillingPlanCreditGrantResponse response = client.credits().deleteBillingPlanCreditGrant("plan_grant_id");
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
    public void testCountBillingPlanCreditGrants() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":{\"count\":1},\"params\":{\"credit_id\":\"credit_id\",\"ids\":[\"ids\"],\"limit\":1,\"offset\":1,\"plan_id\":\"plan_id\",\"plan_ids\":[\"plan_ids\"]}}"));
        CountBillingPlanCreditGrantsResponse response = client.credits()
                .countBillingPlanCreditGrants(CountBillingPlanCreditGrantsRequest.builder()
                        .creditId("credit_id")
                        .planId("plan_id")
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
                + "    \"credit_id\": \"credit_id\",\n"
                + "    \"ids\": [\n"
                + "      \"ids\"\n"
                + "    ],\n"
                + "    \"limit\": 1,\n"
                + "    \"offset\": 1,\n"
                + "    \"plan_id\": \"plan_id\",\n"
                + "    \"plan_ids\": [\n"
                + "      \"plan_ids\"\n"
                + "    ]\n"
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
