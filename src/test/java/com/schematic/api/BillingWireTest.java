package com.schematic.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.schematic.api.core.ObjectMappers;
import com.schematic.api.resources.billing.requests.CountBillingProductsRequest;
import com.schematic.api.resources.billing.requests.CountCustomersRequest;
import com.schematic.api.resources.billing.requests.CreateBillingCustomerRequestBody;
import com.schematic.api.resources.billing.requests.CreateBillingPriceRequestBody;
import com.schematic.api.resources.billing.requests.CreateBillingProductRequestBody;
import com.schematic.api.resources.billing.requests.CreateBillingSubscriptionRequestBody;
import com.schematic.api.resources.billing.requests.CreateCouponRequestBody;
import com.schematic.api.resources.billing.requests.CreateInvoiceRequestBody;
import com.schematic.api.resources.billing.requests.CreateMeterRequestBody;
import com.schematic.api.resources.billing.requests.CreatePaymentMethodRequestBody;
import com.schematic.api.resources.billing.requests.ListBillingProductsRequest;
import com.schematic.api.resources.billing.requests.ListCouponsRequest;
import com.schematic.api.resources.billing.requests.ListCustomersWithSubscriptionsRequest;
import com.schematic.api.resources.billing.requests.ListInvoicesRequest;
import com.schematic.api.resources.billing.requests.ListMetersRequest;
import com.schematic.api.resources.billing.requests.ListPaymentMethodsRequest;
import com.schematic.api.resources.billing.requests.ListProductPricesRequest;
import com.schematic.api.resources.billing.requests.SearchBillingPricesRequest;
import com.schematic.api.resources.billing.types.CountBillingProductsRequestPriceUsageType;
import com.schematic.api.resources.billing.types.CountBillingProductsResponse;
import com.schematic.api.resources.billing.types.CountCustomersResponse;
import com.schematic.api.resources.billing.types.CreateBillingPriceRequestBodyBillingScheme;
import com.schematic.api.resources.billing.types.CreateBillingPriceRequestBodyUsageType;
import com.schematic.api.resources.billing.types.DeleteBillingProductResponse;
import com.schematic.api.resources.billing.types.DeleteProductPriceResponse;
import com.schematic.api.resources.billing.types.ListBillingProductsRequestPriceUsageType;
import com.schematic.api.resources.billing.types.ListBillingProductsResponse;
import com.schematic.api.resources.billing.types.ListCouponsResponse;
import com.schematic.api.resources.billing.types.ListCustomersWithSubscriptionsResponse;
import com.schematic.api.resources.billing.types.ListInvoicesResponse;
import com.schematic.api.resources.billing.types.ListMetersResponse;
import com.schematic.api.resources.billing.types.ListPaymentMethodsResponse;
import com.schematic.api.resources.billing.types.ListProductPricesRequestPriceUsageType;
import com.schematic.api.resources.billing.types.ListProductPricesResponse;
import com.schematic.api.resources.billing.types.SearchBillingPricesRequestTiersMode;
import com.schematic.api.resources.billing.types.SearchBillingPricesRequestUsageType;
import com.schematic.api.resources.billing.types.SearchBillingPricesResponse;
import com.schematic.api.resources.billing.types.UpsertBillingCouponResponse;
import com.schematic.api.resources.billing.types.UpsertBillingCustomerResponse;
import com.schematic.api.resources.billing.types.UpsertBillingMeterResponse;
import com.schematic.api.resources.billing.types.UpsertBillingPriceResponse;
import com.schematic.api.resources.billing.types.UpsertBillingProductResponse;
import com.schematic.api.resources.billing.types.UpsertBillingSubscriptionResponse;
import com.schematic.api.resources.billing.types.UpsertInvoiceResponse;
import com.schematic.api.resources.billing.types.UpsertPaymentMethodResponse;
import com.schematic.api.types.BillingProductPricing;
import com.schematic.api.types.BillingProductPricingUsageType;
import com.schematic.api.types.BillingSubscriptionDiscount;
import com.schematic.api.types.CreateBillingPriceTierRequestBody;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.HashMap;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BillingWireTest {
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
    public void testListCoupons() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":[{\"account_id\":\"account_id\",\"amount_off\":1,\"currency\":\"currency\",\"duration\":\"duration\",\"duration_in_months\":1,\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"id\":\"id\",\"is_active\":true,\"max_redemptions\":1,\"metadata\":{\"key\":\"value\"},\"name\":\"name\",\"percent_off\":1.1,\"times_redeemed\":1,\"valid_from\":\"2024-01-15T09:30:00Z\",\"valid_until\":\"2024-01-15T09:30:00Z\"}],\"params\":{\"is_active\":true,\"limit\":1,\"offset\":1,\"q\":\"q\"}}"));
        ListCouponsResponse response = client.billing()
                .listCoupons(ListCouponsRequest.builder()
                        .isActive(true)
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
                + "      \"account_id\": \"account_id\",\n"
                + "      \"amount_off\": 1,\n"
                + "      \"currency\": \"currency\",\n"
                + "      \"duration\": \"duration\",\n"
                + "      \"duration_in_months\": 1,\n"
                + "      \"environment_id\": \"environment_id\",\n"
                + "      \"external_id\": \"external_id\",\n"
                + "      \"id\": \"id\",\n"
                + "      \"is_active\": true,\n"
                + "      \"max_redemptions\": 1,\n"
                + "      \"metadata\": {\n"
                + "        \"key\": \"value\"\n"
                + "      },\n"
                + "      \"name\": \"name\",\n"
                + "      \"percent_off\": 1.1,\n"
                + "      \"times_redeemed\": 1,\n"
                + "      \"valid_from\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"valid_until\": \"2024-01-15T09:30:00Z\"\n"
                + "    }\n"
                + "  ],\n"
                + "  \"params\": {\n"
                + "    \"is_active\": true,\n"
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
    public void testUpsertBillingCoupon() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":{\"account_id\":\"account_id\",\"amount_off\":1,\"currency\":\"currency\",\"duration\":\"duration\",\"duration_in_months\":1,\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"id\":\"id\",\"is_active\":true,\"max_redemptions\":1,\"metadata\":{\"key\":\"value\"},\"name\":\"name\",\"percent_off\":1.1,\"times_redeemed\":1,\"valid_from\":\"2024-01-15T09:30:00Z\",\"valid_until\":\"2024-01-15T09:30:00Z\"},\"params\":{\"key\":\"value\"}}"));
        UpsertBillingCouponResponse response = client.billing()
                .upsertBillingCoupon(CreateCouponRequestBody.builder()
                        .amountOff(1)
                        .duration("duration")
                        .durationInMonths(1)
                        .externalId("external_id")
                        .maxRedemptions(1)
                        .name("name")
                        .percentOff(1.1)
                        .timesRedeemed(1)
                        .build());
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("POST", request.getMethod());
        // Validate request body
        String actualRequestBody = request.getBody().readUtf8();
        String expectedRequestBody = ""
                + "{\n"
                + "  \"amount_off\": 1,\n"
                + "  \"duration\": \"duration\",\n"
                + "  \"duration_in_months\": 1,\n"
                + "  \"external_id\": \"external_id\",\n"
                + "  \"max_redemptions\": 1,\n"
                + "  \"name\": \"name\",\n"
                + "  \"percent_off\": 1.1,\n"
                + "  \"times_redeemed\": 1\n"
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
                + "    \"account_id\": \"account_id\",\n"
                + "    \"amount_off\": 1,\n"
                + "    \"currency\": \"currency\",\n"
                + "    \"duration\": \"duration\",\n"
                + "    \"duration_in_months\": 1,\n"
                + "    \"environment_id\": \"environment_id\",\n"
                + "    \"external_id\": \"external_id\",\n"
                + "    \"id\": \"id\",\n"
                + "    \"is_active\": true,\n"
                + "    \"max_redemptions\": 1,\n"
                + "    \"metadata\": {\n"
                + "      \"key\": \"value\"\n"
                + "    },\n"
                + "    \"name\": \"name\",\n"
                + "    \"percent_off\": 1.1,\n"
                + "    \"times_redeemed\": 1,\n"
                + "    \"valid_from\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"valid_until\": \"2024-01-15T09:30:00Z\"\n"
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
    public void testUpsertBillingCustomer() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":{\"company_id\":\"company_id\",\"deleted_at\":\"2024-01-15T09:30:00Z\",\"email\":\"email\",\"external_id\":\"external_id\",\"failed_to_import\":true,\"id\":\"id\",\"name\":\"name\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"params\":{\"key\":\"value\"}}"));
        UpsertBillingCustomerResponse response = client.billing()
                .upsertBillingCustomer(CreateBillingCustomerRequestBody.builder()
                        .email("email")
                        .externalId("external_id")
                        .failedToImport(true)
                        .meta(new HashMap<String, String>() {
                            {
                                put("key", "value");
                            }
                        })
                        .name("name")
                        .build());
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("POST", request.getMethod());
        // Validate request body
        String actualRequestBody = request.getBody().readUtf8();
        String expectedRequestBody = ""
                + "{\n"
                + "  \"email\": \"email\",\n"
                + "  \"external_id\": \"external_id\",\n"
                + "  \"failed_to_import\": true,\n"
                + "  \"meta\": {\n"
                + "    \"key\": \"value\"\n"
                + "  },\n"
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
                + "    \"company_id\": \"company_id\",\n"
                + "    \"deleted_at\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"email\": \"email\",\n"
                + "    \"external_id\": \"external_id\",\n"
                + "    \"failed_to_import\": true,\n"
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
    public void testListCustomersWithSubscriptions() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":[{\"company_id\":\"company_id\",\"deleted_at\":\"2024-01-15T09:30:00Z\",\"email\":\"email\",\"external_id\":\"external_id\",\"failed_to_import\":true,\"id\":\"id\",\"name\":\"name\",\"subscriptions\":[{\"currency\":\"currency\",\"interval\":\"interval\",\"metered_usage\":true,\"per_unit_price\":1,\"total_price\":1}],\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"params\":{\"company_ids\":[\"company_ids\"],\"failed_to_import\":true,\"limit\":1,\"name\":\"name\",\"offset\":1,\"q\":\"q\"}}"));
        ListCustomersWithSubscriptionsResponse response = client.billing()
                .listCustomersWithSubscriptions(ListCustomersWithSubscriptionsRequest.builder()
                        .name("name")
                        .failedToImport(true)
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
                + "      \"company_id\": \"company_id\",\n"
                + "      \"deleted_at\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"email\": \"email\",\n"
                + "      \"external_id\": \"external_id\",\n"
                + "      \"failed_to_import\": true,\n"
                + "      \"id\": \"id\",\n"
                + "      \"name\": \"name\",\n"
                + "      \"subscriptions\": [\n"
                + "        {\n"
                + "          \"currency\": \"currency\",\n"
                + "          \"interval\": \"interval\",\n"
                + "          \"metered_usage\": true,\n"
                + "          \"per_unit_price\": 1,\n"
                + "          \"total_price\": 1\n"
                + "        }\n"
                + "      ],\n"
                + "      \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "    }\n"
                + "  ],\n"
                + "  \"params\": {\n"
                + "    \"company_ids\": [\n"
                + "      \"company_ids\"\n"
                + "    ],\n"
                + "    \"failed_to_import\": true,\n"
                + "    \"limit\": 1,\n"
                + "    \"name\": \"name\",\n"
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
    public void testCountCustomers() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":{\"count\":1},\"params\":{\"company_ids\":[\"company_ids\"],\"failed_to_import\":true,\"limit\":1,\"name\":\"name\",\"offset\":1,\"q\":\"q\"}}"));
        CountCustomersResponse response = client.billing()
                .countCustomers(CountCustomersRequest.builder()
                        .name("name")
                        .failedToImport(true)
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
                + "    \"company_ids\": [\n"
                + "      \"company_ids\"\n"
                + "    ],\n"
                + "    \"failed_to_import\": true,\n"
                + "    \"limit\": 1,\n"
                + "    \"name\": \"name\",\n"
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
    public void testListInvoices() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":[{\"amount_due\":1,\"amount_paid\":1,\"amount_remaining\":1,\"collection_method\":\"collection_method\",\"company_id\":\"company_id\",\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"customer_external_id\":\"customer_external_id\",\"due_date\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"id\":\"id\",\"payment_method_external_id\":\"payment_method_external_id\",\"subscription_external_id\":\"subscription_external_id\",\"subtotal\":1,\"updated_at\":\"2024-01-15T09:30:00Z\",\"url\":\"url\"}],\"params\":{\"company_id\":\"company_id\",\"customer_external_id\":\"customer_external_id\",\"limit\":1,\"offset\":1,\"subscription_external_id\":\"subscription_external_id\"}}"));
        ListInvoicesResponse response = client.billing()
                .listInvoices(ListInvoicesRequest.builder()
                        .customerExternalId("customer_external_id")
                        .subscriptionExternalId("subscription_external_id")
                        .companyId("company_id")
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
                + "  ],\n"
                + "  \"params\": {\n"
                + "    \"company_id\": \"company_id\",\n"
                + "    \"customer_external_id\": \"customer_external_id\",\n"
                + "    \"limit\": 1,\n"
                + "    \"offset\": 1,\n"
                + "    \"subscription_external_id\": \"subscription_external_id\"\n"
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
    public void testUpsertInvoice() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":{\"amount_due\":1,\"amount_paid\":1,\"amount_remaining\":1,\"collection_method\":\"collection_method\",\"company_id\":\"company_id\",\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"customer_external_id\":\"customer_external_id\",\"due_date\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"id\":\"id\",\"payment_method_external_id\":\"payment_method_external_id\",\"subscription_external_id\":\"subscription_external_id\",\"subtotal\":1,\"updated_at\":\"2024-01-15T09:30:00Z\",\"url\":\"url\"},\"params\":{\"key\":\"value\"}}"));
        UpsertInvoiceResponse response = client.billing()
                .upsertInvoice(CreateInvoiceRequestBody.builder()
                        .amountDue(1)
                        .amountPaid(1)
                        .amountRemaining(1)
                        .collectionMethod("collection_method")
                        .currency("currency")
                        .customerExternalId("customer_external_id")
                        .subtotal(1)
                        .build());
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("POST", request.getMethod());
        // Validate request body
        String actualRequestBody = request.getBody().readUtf8();
        String expectedRequestBody = ""
                + "{\n"
                + "  \"amount_due\": 1,\n"
                + "  \"amount_paid\": 1,\n"
                + "  \"amount_remaining\": 1,\n"
                + "  \"collection_method\": \"collection_method\",\n"
                + "  \"currency\": \"currency\",\n"
                + "  \"customer_external_id\": \"customer_external_id\",\n"
                + "  \"subtotal\": 1\n"
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
                + "    \"amount_due\": 1,\n"
                + "    \"amount_paid\": 1,\n"
                + "    \"amount_remaining\": 1,\n"
                + "    \"collection_method\": \"collection_method\",\n"
                + "    \"company_id\": \"company_id\",\n"
                + "    \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"currency\": \"currency\",\n"
                + "    \"customer_external_id\": \"customer_external_id\",\n"
                + "    \"due_date\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"environment_id\": \"environment_id\",\n"
                + "    \"external_id\": \"external_id\",\n"
                + "    \"id\": \"id\",\n"
                + "    \"payment_method_external_id\": \"payment_method_external_id\",\n"
                + "    \"subscription_external_id\": \"subscription_external_id\",\n"
                + "    \"subtotal\": 1,\n"
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
    public void testListMeters() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":[{\"dispaly_name\":\"dispaly_name\",\"event_name\":\"event_name\",\"event_payload_key\":\"event_payload_key\",\"external_price_id\":\"external_price_id\",\"id\":\"id\"}],\"params\":{\"display_name\":\"display_name\",\"limit\":1,\"offset\":1}}"));
        ListMetersResponse response = client.billing()
                .listMeters(ListMetersRequest.builder()
                        .displayName("display_name")
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
                + "      \"dispaly_name\": \"dispaly_name\",\n"
                + "      \"event_name\": \"event_name\",\n"
                + "      \"event_payload_key\": \"event_payload_key\",\n"
                + "      \"external_price_id\": \"external_price_id\",\n"
                + "      \"id\": \"id\"\n"
                + "    }\n"
                + "  ],\n"
                + "  \"params\": {\n"
                + "    \"display_name\": \"display_name\",\n"
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
    public void testUpsertBillingMeter() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":{\"dispaly_name\":\"dispaly_name\",\"event_name\":\"event_name\",\"event_payload_key\":\"event_payload_key\",\"external_price_id\":\"external_price_id\",\"id\":\"id\"},\"params\":{\"key\":\"value\"}}"));
        UpsertBillingMeterResponse response = client.billing()
                .upsertBillingMeter(CreateMeterRequestBody.builder()
                        .displayName("display_name")
                        .eventName("event_name")
                        .eventPayloadKey("event_payload_key")
                        .externalId("external_id")
                        .build());
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("POST", request.getMethod());
        // Validate request body
        String actualRequestBody = request.getBody().readUtf8();
        String expectedRequestBody = ""
                + "{\n"
                + "  \"display_name\": \"display_name\",\n"
                + "  \"event_name\": \"event_name\",\n"
                + "  \"event_payload_key\": \"event_payload_key\",\n"
                + "  \"external_id\": \"external_id\"\n"
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
                + "    \"dispaly_name\": \"dispaly_name\",\n"
                + "    \"event_name\": \"event_name\",\n"
                + "    \"event_payload_key\": \"event_payload_key\",\n"
                + "    \"external_price_id\": \"external_price_id\",\n"
                + "    \"id\": \"id\"\n"
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
    public void testListPaymentMethods() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":[{\"account_last4\":\"account_last4\",\"account_name\":\"account_name\",\"bank_name\":\"bank_name\",\"billing_email\":\"billing_email\",\"billing_name\":\"billing_name\",\"card_brand\":\"card_brand\",\"card_exp_month\":1,\"card_exp_year\":1,\"card_last4\":\"card_last4\",\"company_id\":\"company_id\",\"created_at\":\"2024-01-15T09:30:00Z\",\"customer_external_id\":\"customer_external_id\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"id\":\"id\",\"payment_method_type\":\"payment_method_type\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"params\":{\"company_id\":\"company_id\",\"customer_external_id\":\"customer_external_id\",\"limit\":1,\"offset\":1}}"));
        ListPaymentMethodsResponse response = client.billing()
                .listPaymentMethods(ListPaymentMethodsRequest.builder()
                        .customerExternalId("customer_external_id")
                        .companyId("company_id")
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
                + "      \"account_last4\": \"account_last4\",\n"
                + "      \"account_name\": \"account_name\",\n"
                + "      \"bank_name\": \"bank_name\",\n"
                + "      \"billing_email\": \"billing_email\",\n"
                + "      \"billing_name\": \"billing_name\",\n"
                + "      \"card_brand\": \"card_brand\",\n"
                + "      \"card_exp_month\": 1,\n"
                + "      \"card_exp_year\": 1,\n"
                + "      \"card_last4\": \"card_last4\",\n"
                + "      \"company_id\": \"company_id\",\n"
                + "      \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"customer_external_id\": \"customer_external_id\",\n"
                + "      \"environment_id\": \"environment_id\",\n"
                + "      \"external_id\": \"external_id\",\n"
                + "      \"id\": \"id\",\n"
                + "      \"payment_method_type\": \"payment_method_type\",\n"
                + "      \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "    }\n"
                + "  ],\n"
                + "  \"params\": {\n"
                + "    \"company_id\": \"company_id\",\n"
                + "    \"customer_external_id\": \"customer_external_id\",\n"
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
    public void testUpsertPaymentMethod() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":{\"account_last4\":\"account_last4\",\"account_name\":\"account_name\",\"bank_name\":\"bank_name\",\"billing_email\":\"billing_email\",\"billing_name\":\"billing_name\",\"card_brand\":\"card_brand\",\"card_exp_month\":1,\"card_exp_year\":1,\"card_last4\":\"card_last4\",\"company_id\":\"company_id\",\"created_at\":\"2024-01-15T09:30:00Z\",\"customer_external_id\":\"customer_external_id\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"id\":\"id\",\"payment_method_type\":\"payment_method_type\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"params\":{\"key\":\"value\"}}"));
        UpsertPaymentMethodResponse response = client.billing()
                .upsertPaymentMethod(CreatePaymentMethodRequestBody.builder()
                        .customerExternalId("customer_external_id")
                        .externalId("external_id")
                        .paymentMethodType("payment_method_type")
                        .build());
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("POST", request.getMethod());
        // Validate request body
        String actualRequestBody = request.getBody().readUtf8();
        String expectedRequestBody = ""
                + "{\n"
                + "  \"customer_external_id\": \"customer_external_id\",\n"
                + "  \"external_id\": \"external_id\",\n"
                + "  \"payment_method_type\": \"payment_method_type\"\n"
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
                + "    \"account_last4\": \"account_last4\",\n"
                + "    \"account_name\": \"account_name\",\n"
                + "    \"bank_name\": \"bank_name\",\n"
                + "    \"billing_email\": \"billing_email\",\n"
                + "    \"billing_name\": \"billing_name\",\n"
                + "    \"card_brand\": \"card_brand\",\n"
                + "    \"card_exp_month\": 1,\n"
                + "    \"card_exp_year\": 1,\n"
                + "    \"card_last4\": \"card_last4\",\n"
                + "    \"company_id\": \"company_id\",\n"
                + "    \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"customer_external_id\": \"customer_external_id\",\n"
                + "    \"environment_id\": \"environment_id\",\n"
                + "    \"external_id\": \"external_id\",\n"
                + "    \"id\": \"id\",\n"
                + "    \"payment_method_type\": \"payment_method_type\",\n"
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
    public void testSearchBillingPrices() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":[{\"billing_scheme\":\"billing_scheme\",\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"id\":\"id\",\"interval\":\"interval\",\"is_active\":true,\"meter_event_name\":\"meter_event_name\",\"meter_event_payload_key\":\"meter_event_payload_key\",\"meter_id\":\"meter_id\",\"package_size\":1,\"price\":1,\"price_decimal\":\"price_decimal\",\"price_external_id\":\"price_external_id\",\"price_id\":\"price_id\",\"price_tier\":[{}],\"product_external_id\":\"product_external_id\",\"product_id\":\"product_id\",\"product_name\":\"product_name\",\"tiers_mode\":\"tiers_mode\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"usage_type\":\"usage_type\"}],\"params\":{\"for_initial_plan\":true,\"for_trial_expiry_plan\":true,\"ids\":[\"ids\"],\"interval\":\"interval\",\"limit\":1,\"offset\":1,\"price\":1,\"product_id\":\"product_id\",\"q\":\"q\",\"requires_payment_method\":true,\"tiers_mode\":\"volume\",\"usage_type\":\"licensed\"}}"));
        SearchBillingPricesResponse response = client.billing()
                .searchBillingPrices(SearchBillingPricesRequest.builder()
                        .forInitialPlan(true)
                        .forTrialExpiryPlan(true)
                        .productId("product_id")
                        .interval("interval")
                        .price(1)
                        .q("q")
                        .requiresPaymentMethod(true)
                        .tiersMode(SearchBillingPricesRequestTiersMode.VOLUME)
                        .usageType(SearchBillingPricesRequestUsageType.LICENSED)
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
                + "      \"billing_scheme\": \"billing_scheme\",\n"
                + "      \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"currency\": \"currency\",\n"
                + "      \"id\": \"id\",\n"
                + "      \"interval\": \"interval\",\n"
                + "      \"is_active\": true,\n"
                + "      \"meter_event_name\": \"meter_event_name\",\n"
                + "      \"meter_event_payload_key\": \"meter_event_payload_key\",\n"
                + "      \"meter_id\": \"meter_id\",\n"
                + "      \"package_size\": 1,\n"
                + "      \"price\": 1,\n"
                + "      \"price_decimal\": \"price_decimal\",\n"
                + "      \"price_external_id\": \"price_external_id\",\n"
                + "      \"price_id\": \"price_id\",\n"
                + "      \"price_tier\": [\n"
                + "        {}\n"
                + "      ],\n"
                + "      \"product_external_id\": \"product_external_id\",\n"
                + "      \"product_id\": \"product_id\",\n"
                + "      \"product_name\": \"product_name\",\n"
                + "      \"tiers_mode\": \"tiers_mode\",\n"
                + "      \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"usage_type\": \"usage_type\"\n"
                + "    }\n"
                + "  ],\n"
                + "  \"params\": {\n"
                + "    \"for_initial_plan\": true,\n"
                + "    \"for_trial_expiry_plan\": true,\n"
                + "    \"ids\": [\n"
                + "      \"ids\"\n"
                + "    ],\n"
                + "    \"interval\": \"interval\",\n"
                + "    \"limit\": 1,\n"
                + "    \"offset\": 1,\n"
                + "    \"price\": 1,\n"
                + "    \"product_id\": \"product_id\",\n"
                + "    \"q\": \"q\",\n"
                + "    \"requires_payment_method\": true,\n"
                + "    \"tiers_mode\": \"volume\",\n"
                + "    \"usage_type\": \"licensed\"\n"
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
    public void testUpsertBillingPrice() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":{\"currency\":\"currency\",\"external_price_id\":\"external_price_id\",\"id\":\"id\",\"interval\":\"interval\",\"price\":1,\"price_decimal\":\"price_decimal\",\"scheme\":\"scheme\"},\"params\":{\"key\":\"value\"}}"));
        UpsertBillingPriceResponse response = client.billing()
                .upsertBillingPrice(CreateBillingPriceRequestBody.builder()
                        .billingScheme(CreateBillingPriceRequestBodyBillingScheme.PER_UNIT)
                        .currency("currency")
                        .externalAccountId("external_account_id")
                        .interval("interval")
                        .isActive(true)
                        .price(1)
                        .priceExternalId("price_external_id")
                        .priceTiers(Arrays.asList(CreateBillingPriceTierRequestBody.builder()
                                .priceExternalId("price_external_id")
                                .build()))
                        .productExternalId("product_external_id")
                        .usageType(CreateBillingPriceRequestBodyUsageType.LICENSED)
                        .build());
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("POST", request.getMethod());
        // Validate request body
        String actualRequestBody = request.getBody().readUtf8();
        String expectedRequestBody = ""
                + "{\n"
                + "  \"billing_scheme\": \"per_unit\",\n"
                + "  \"currency\": \"currency\",\n"
                + "  \"external_account_id\": \"external_account_id\",\n"
                + "  \"interval\": \"interval\",\n"
                + "  \"is_active\": true,\n"
                + "  \"price\": 1,\n"
                + "  \"price_external_id\": \"price_external_id\",\n"
                + "  \"price_tiers\": [\n"
                + "    {\n"
                + "      \"price_external_id\": \"price_external_id\"\n"
                + "    }\n"
                + "  ],\n"
                + "  \"product_external_id\": \"product_external_id\",\n"
                + "  \"usage_type\": \"licensed\"\n"
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
                + "    \"currency\": \"currency\",\n"
                + "    \"external_price_id\": \"external_price_id\",\n"
                + "    \"id\": \"id\",\n"
                + "    \"interval\": \"interval\",\n"
                + "    \"price\": 1,\n"
                + "    \"price_decimal\": \"price_decimal\",\n"
                + "    \"scheme\": \"scheme\"\n"
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
    public void testDeleteBillingProduct() throws Exception {
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody("{\"data\":{\"deleted\":true},\"params\":{\"key\":\"value\"}}"));
        DeleteBillingProductResponse response = client.billing().deleteBillingProduct("billing_id");
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
    public void testListProductPrices() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":[{\"currency\":\"currency\",\"external_price_id\":\"external_price_id\",\"id\":\"id\",\"interval\":\"interval\",\"price\":1,\"price_decimal\":\"price_decimal\",\"scheme\":\"scheme\"}],\"params\":{\"ids\":[\"ids\"],\"is_active\":true,\"limit\":1,\"name\":\"name\",\"offset\":1,\"price_usage_type\":\"licensed\",\"q\":\"q\",\"with_one_time_charges\":true,\"with_prices_only\":true,\"with_zero_price\":true,\"without_linked_to_plan\":true}}"));
        ListProductPricesResponse response = client.billing()
                .listProductPrices(ListProductPricesRequest.builder()
                        .name("name")
                        .q("q")
                        .priceUsageType(ListProductPricesRequestPriceUsageType.LICENSED)
                        .withoutLinkedToPlan(true)
                        .withOneTimeCharges(true)
                        .withZeroPrice(true)
                        .withPricesOnly(true)
                        .isActive(true)
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
                + "      \"currency\": \"currency\",\n"
                + "      \"external_price_id\": \"external_price_id\",\n"
                + "      \"id\": \"id\",\n"
                + "      \"interval\": \"interval\",\n"
                + "      \"price\": 1,\n"
                + "      \"price_decimal\": \"price_decimal\",\n"
                + "      \"scheme\": \"scheme\"\n"
                + "    }\n"
                + "  ],\n"
                + "  \"params\": {\n"
                + "    \"ids\": [\n"
                + "      \"ids\"\n"
                + "    ],\n"
                + "    \"is_active\": true,\n"
                + "    \"limit\": 1,\n"
                + "    \"name\": \"name\",\n"
                + "    \"offset\": 1,\n"
                + "    \"price_usage_type\": \"licensed\",\n"
                + "    \"q\": \"q\",\n"
                + "    \"with_one_time_charges\": true,\n"
                + "    \"with_prices_only\": true,\n"
                + "    \"with_zero_price\": true,\n"
                + "    \"without_linked_to_plan\": true\n"
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
    public void testDeleteProductPrice() throws Exception {
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody("{\"data\":{\"deleted\":true},\"params\":{\"key\":\"value\"}}"));
        DeleteProductPriceResponse response = client.billing().deleteProductPrice("billing_id");
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
    public void testUpsertBillingProduct() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":{\"account_id\":\"account_id\",\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"is_active\":true,\"name\":\"name\",\"price\":1.1,\"price_decimal\":\"price_decimal\",\"product_id\":\"product_id\",\"quantity\":1.1,\"updated_at\":\"2024-01-15T09:30:00Z\"},\"params\":{\"key\":\"value\"}}"));
        UpsertBillingProductResponse response = client.billing()
                .upsertBillingProduct(CreateBillingProductRequestBody.builder()
                        .externalId("external_id")
                        .name("name")
                        .price(1.1)
                        .build());
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("POST", request.getMethod());
        // Validate request body
        String actualRequestBody = request.getBody().readUtf8();
        String expectedRequestBody = ""
                + "{\n"
                + "  \"external_id\": \"external_id\",\n"
                + "  \"name\": \"name\",\n"
                + "  \"price\": 1.1\n"
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
                + "    \"account_id\": \"account_id\",\n"
                + "    \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"environment_id\": \"environment_id\",\n"
                + "    \"external_id\": \"external_id\",\n"
                + "    \"is_active\": true,\n"
                + "    \"name\": \"name\",\n"
                + "    \"price\": 1.1,\n"
                + "    \"price_decimal\": \"price_decimal\",\n"
                + "    \"product_id\": \"product_id\",\n"
                + "    \"quantity\": 1.1,\n"
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
    public void testListBillingProducts() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":[{\"account_id\":\"account_id\",\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"is_active\":true,\"name\":\"name\",\"price\":1.1,\"price_decimal\":\"price_decimal\",\"prices\":[{\"currency\":\"currency\",\"external_price_id\":\"external_price_id\",\"id\":\"id\",\"interval\":\"interval\",\"price\":1,\"scheme\":\"scheme\"}],\"product_id\":\"product_id\",\"quantity\":1.1,\"subscription_count\":1,\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"params\":{\"ids\":[\"ids\"],\"is_active\":true,\"limit\":1,\"name\":\"name\",\"offset\":1,\"price_usage_type\":\"licensed\",\"q\":\"q\",\"with_one_time_charges\":true,\"with_prices_only\":true,\"with_zero_price\":true,\"without_linked_to_plan\":true}}"));
        ListBillingProductsResponse response = client.billing()
                .listBillingProducts(ListBillingProductsRequest.builder()
                        .name("name")
                        .q("q")
                        .priceUsageType(ListBillingProductsRequestPriceUsageType.LICENSED)
                        .withoutLinkedToPlan(true)
                        .withOneTimeCharges(true)
                        .withZeroPrice(true)
                        .withPricesOnly(true)
                        .isActive(true)
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
                + "      \"account_id\": \"account_id\",\n"
                + "      \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"environment_id\": \"environment_id\",\n"
                + "      \"external_id\": \"external_id\",\n"
                + "      \"is_active\": true,\n"
                + "      \"name\": \"name\",\n"
                + "      \"price\": 1.1,\n"
                + "      \"price_decimal\": \"price_decimal\",\n"
                + "      \"prices\": [\n"
                + "        {\n"
                + "          \"currency\": \"currency\",\n"
                + "          \"external_price_id\": \"external_price_id\",\n"
                + "          \"id\": \"id\",\n"
                + "          \"interval\": \"interval\",\n"
                + "          \"price\": 1,\n"
                + "          \"scheme\": \"scheme\"\n"
                + "        }\n"
                + "      ],\n"
                + "      \"product_id\": \"product_id\",\n"
                + "      \"quantity\": 1.1,\n"
                + "      \"subscription_count\": 1,\n"
                + "      \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "    }\n"
                + "  ],\n"
                + "  \"params\": {\n"
                + "    \"ids\": [\n"
                + "      \"ids\"\n"
                + "    ],\n"
                + "    \"is_active\": true,\n"
                + "    \"limit\": 1,\n"
                + "    \"name\": \"name\",\n"
                + "    \"offset\": 1,\n"
                + "    \"price_usage_type\": \"licensed\",\n"
                + "    \"q\": \"q\",\n"
                + "    \"with_one_time_charges\": true,\n"
                + "    \"with_prices_only\": true,\n"
                + "    \"with_zero_price\": true,\n"
                + "    \"without_linked_to_plan\": true\n"
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
    public void testCountBillingProducts() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":{\"count\":1},\"params\":{\"ids\":[\"ids\"],\"is_active\":true,\"limit\":1,\"name\":\"name\",\"offset\":1,\"price_usage_type\":\"licensed\",\"q\":\"q\",\"with_one_time_charges\":true,\"with_prices_only\":true,\"with_zero_price\":true,\"without_linked_to_plan\":true}}"));
        CountBillingProductsResponse response = client.billing()
                .countBillingProducts(CountBillingProductsRequest.builder()
                        .name("name")
                        .q("q")
                        .priceUsageType(CountBillingProductsRequestPriceUsageType.LICENSED)
                        .withoutLinkedToPlan(true)
                        .withOneTimeCharges(true)
                        .withZeroPrice(true)
                        .withPricesOnly(true)
                        .isActive(true)
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
                + "    \"is_active\": true,\n"
                + "    \"limit\": 1,\n"
                + "    \"name\": \"name\",\n"
                + "    \"offset\": 1,\n"
                + "    \"price_usage_type\": \"licensed\",\n"
                + "    \"q\": \"q\",\n"
                + "    \"with_one_time_charges\": true,\n"
                + "    \"with_prices_only\": true,\n"
                + "    \"with_zero_price\": true,\n"
                + "    \"without_linked_to_plan\": true\n"
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
    public void testUpsertBillingSubscription() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":{\"cancel_at\":1,\"cancel_at_period_end\":true,\"company_id\":\"company_id\",\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"customer_external_id\":\"customer_external_id\",\"default_payment_method_id\":\"default_payment_method_id\",\"expired_at\":\"2024-01-15T09:30:00Z\",\"id\":\"id\",\"interval\":\"interval\",\"metadata\":{\"key\":\"value\"},\"period_end\":1,\"period_start\":1,\"status\":\"status\",\"subscription_external_id\":\"subscription_external_id\",\"total_price\":1,\"trial_end\":1,\"trial_end_setting\":\"trial_end_setting\"},\"params\":{\"key\":\"value\"}}"));
        UpsertBillingSubscriptionResponse response = client.billing()
                .upsertBillingSubscription(CreateBillingSubscriptionRequestBody.builder()
                        .cancelAtPeriodEnd(true)
                        .currency("currency")
                        .customerExternalId("customer_external_id")
                        .discounts(Arrays.asList(BillingSubscriptionDiscount.builder()
                                .couponExternalId("coupon_external_id")
                                .externalId("external_id")
                                .isActive(true)
                                .startedAt(OffsetDateTime.parse("2024-01-15T09:30:00Z"))
                                .build()))
                        .expiredAt(OffsetDateTime.parse("2024-01-15T09:30:00Z"))
                        .productExternalIds(Arrays.asList(BillingProductPricing.builder()
                                .currency("currency")
                                .interval("interval")
                                .price(1)
                                .priceExternalId("price_external_id")
                                .productExternalId("product_external_id")
                                .quantity(1)
                                .usageType(BillingProductPricingUsageType.LICENSED)
                                .build()))
                        .subscriptionExternalId("subscription_external_id")
                        .totalPrice(1)
                        .build());
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("POST", request.getMethod());
        // Validate request body
        String actualRequestBody = request.getBody().readUtf8();
        String expectedRequestBody = ""
                + "{\n"
                + "  \"cancel_at_period_end\": true,\n"
                + "  \"currency\": \"currency\",\n"
                + "  \"customer_external_id\": \"customer_external_id\",\n"
                + "  \"discounts\": [\n"
                + "    {\n"
                + "      \"coupon_external_id\": \"coupon_external_id\",\n"
                + "      \"external_id\": \"external_id\",\n"
                + "      \"is_active\": true,\n"
                + "      \"started_at\": \"2024-01-15T09:30:00Z\"\n"
                + "    }\n"
                + "  ],\n"
                + "  \"expired_at\": \"2024-01-15T09:30:00Z\",\n"
                + "  \"product_external_ids\": [\n"
                + "    {\n"
                + "      \"currency\": \"currency\",\n"
                + "      \"interval\": \"interval\",\n"
                + "      \"price\": 1,\n"
                + "      \"price_external_id\": \"price_external_id\",\n"
                + "      \"product_external_id\": \"product_external_id\",\n"
                + "      \"quantity\": 1,\n"
                + "      \"usage_type\": \"licensed\"\n"
                + "    }\n"
                + "  ],\n"
                + "  \"subscription_external_id\": \"subscription_external_id\",\n"
                + "  \"total_price\": 1\n"
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
                + "    \"cancel_at\": 1,\n"
                + "    \"cancel_at_period_end\": true,\n"
                + "    \"company_id\": \"company_id\",\n"
                + "    \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"currency\": \"currency\",\n"
                + "    \"customer_external_id\": \"customer_external_id\",\n"
                + "    \"default_payment_method_id\": \"default_payment_method_id\",\n"
                + "    \"expired_at\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"id\": \"id\",\n"
                + "    \"interval\": \"interval\",\n"
                + "    \"metadata\": {\n"
                + "      \"key\": \"value\"\n"
                + "    },\n"
                + "    \"period_end\": 1,\n"
                + "    \"period_start\": 1,\n"
                + "    \"status\": \"status\",\n"
                + "    \"subscription_external_id\": \"subscription_external_id\",\n"
                + "    \"total_price\": 1,\n"
                + "    \"trial_end\": 1,\n"
                + "    \"trial_end_setting\": \"trial_end_setting\"\n"
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
