package com.schematic.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.schematic.api.core.ObjectMappers;
import com.schematic.api.resources.entitlements.requests.CountCompanyOverridesRequest;
import com.schematic.api.resources.entitlements.requests.CountFeatureCompaniesRequest;
import com.schematic.api.resources.entitlements.requests.CountFeatureUsageRequest;
import com.schematic.api.resources.entitlements.requests.CountFeatureUsersRequest;
import com.schematic.api.resources.entitlements.requests.CountPlanEntitlementsRequest;
import com.schematic.api.resources.entitlements.requests.CreateCompanyOverrideRequestBody;
import com.schematic.api.resources.entitlements.requests.CreatePlanEntitlementRequestBody;
import com.schematic.api.resources.entitlements.requests.GetFeatureUsageByCompanyRequest;
import com.schematic.api.resources.entitlements.requests.ListCompanyOverridesRequest;
import com.schematic.api.resources.entitlements.requests.ListFeatureCompaniesRequest;
import com.schematic.api.resources.entitlements.requests.ListFeatureUsageRequest;
import com.schematic.api.resources.entitlements.requests.ListFeatureUsersRequest;
import com.schematic.api.resources.entitlements.requests.ListPlanEntitlementsRequest;
import com.schematic.api.resources.entitlements.requests.UpdateCompanyOverrideRequestBody;
import com.schematic.api.resources.entitlements.requests.UpdatePlanEntitlementRequestBody;
import com.schematic.api.resources.entitlements.types.CountCompanyOverridesResponse;
import com.schematic.api.resources.entitlements.types.CountFeatureCompaniesResponse;
import com.schematic.api.resources.entitlements.types.CountFeatureUsageResponse;
import com.schematic.api.resources.entitlements.types.CountFeatureUsersResponse;
import com.schematic.api.resources.entitlements.types.CountPlanEntitlementsResponse;
import com.schematic.api.resources.entitlements.types.CreateCompanyOverrideRequestBodyValueType;
import com.schematic.api.resources.entitlements.types.CreateCompanyOverrideResponse;
import com.schematic.api.resources.entitlements.types.CreatePlanEntitlementRequestBodyValueType;
import com.schematic.api.resources.entitlements.types.CreatePlanEntitlementResponse;
import com.schematic.api.resources.entitlements.types.DeleteCompanyOverrideResponse;
import com.schematic.api.resources.entitlements.types.DeletePlanEntitlementResponse;
import com.schematic.api.resources.entitlements.types.GetCompanyOverrideResponse;
import com.schematic.api.resources.entitlements.types.GetFeatureUsageByCompanyResponse;
import com.schematic.api.resources.entitlements.types.GetPlanEntitlementResponse;
import com.schematic.api.resources.entitlements.types.ListCompanyOverridesResponse;
import com.schematic.api.resources.entitlements.types.ListFeatureCompaniesResponse;
import com.schematic.api.resources.entitlements.types.ListFeatureUsageResponse;
import com.schematic.api.resources.entitlements.types.ListFeatureUsersResponse;
import com.schematic.api.resources.entitlements.types.ListPlanEntitlementsResponse;
import com.schematic.api.resources.entitlements.types.UpdateCompanyOverrideRequestBodyValueType;
import com.schematic.api.resources.entitlements.types.UpdateCompanyOverrideResponse;
import com.schematic.api.resources.entitlements.types.UpdatePlanEntitlementRequestBodyValueType;
import com.schematic.api.resources.entitlements.types.UpdatePlanEntitlementResponse;
import java.util.HashMap;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EntitlementsWireTest {
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
    public void testListCompanyOverrides() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":[{\"company\":{\"add_ons\":[{\"id\":\"id\",\"name\":\"name\"}],\"billing_subscriptions\":[{\"cancel_at_period_end\":true,\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"customer_external_id\":\"customer_external_id\",\"discounts\":[{\"coupon_id\":\"coupon_id\",\"coupon_name\":\"coupon_name\",\"discount_external_id\":\"discount_external_id\",\"duration\":\"duration\",\"is_active\":true,\"started_at\":\"2024-01-15T09:30:00Z\",\"subscription_external_id\":\"subscription_external_id\"}],\"id\":\"id\",\"interval\":\"interval\",\"period_end\":1,\"period_start\":1,\"products\":[{\"billing_scheme\":\"billing_scheme\",\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"id\":\"id\",\"interval\":\"interval\",\"name\":\"name\",\"package_size\":1,\"price\":1,\"price_external_id\":\"price_external_id\",\"price_id\":\"price_id\",\"price_tier\":[{}],\"quantity\":1.1,\"subscription_id\":\"subscription_id\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"usage_type\":\"usage_type\"}],\"status\":\"status\",\"subscription_external_id\":\"subscription_external_id\",\"total_price\":1}],\"created_at\":\"2024-01-15T09:30:00Z\",\"entity_traits\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"definition_id\":\"definition_id\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"value\":\"value\"}],\"environment_id\":\"environment_id\",\"id\":\"id\",\"keys\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"definition_id\":\"definition_id\",\"entity_id\":\"entity_id\",\"entity_type\":\"entity_type\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"key\":\"key\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"value\":\"value\"}],\"metrics\":[{\"account_id\":\"account_id\",\"captured_at_max\":\"2024-01-15T09:30:00Z\",\"captured_at_min\":\"2024-01-15T09:30:00Z\",\"company_id\":\"company_id\",\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"event_subtype\":\"event_subtype\",\"month_reset\":\"month_reset\",\"period\":\"period\",\"value\":1}],\"name\":\"name\",\"payment_methods\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"customer_external_id\":\"customer_external_id\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"id\":\"id\",\"payment_method_type\":\"payment_method_type\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"plans\":[{\"id\":\"id\",\"name\":\"name\"}],\"rules\":[{\"account_id\":\"account_id\",\"condition_groups\":[{\"conditions\":[{\"account_id\":\"account_id\",\"condition_type\":\"condition_type\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"operator\":\"operator\",\"resource_ids\":[\"resource_ids\"],\"trait_value\":\"trait_value\"}]}],\"conditions\":[{\"account_id\":\"account_id\",\"condition_type\":\"condition_type\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"operator\":\"operator\",\"resource_ids\":[\"resource_ids\"],\"trait_value\":\"trait_value\"}],\"environment_id\":\"environment_id\",\"id\":\"id\",\"name\":\"name\",\"priority\":1,\"rule_type\":\"rule_type\",\"value\":true}],\"updated_at\":\"2024-01-15T09:30:00Z\",\"user_count\":1},\"company_id\":\"company_id\",\"consumption_rate\":1.1,\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"expiration_date\":\"2024-01-15T09:30:00Z\",\"feature\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"description\":\"description\",\"feature_type\":\"feature_type\",\"icon\":\"icon\",\"id\":\"id\",\"name\":\"name\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"feature_id\":\"feature_id\",\"id\":\"id\",\"metric_period\":\"metric_period\",\"metric_period_month_reset\":\"metric_period_month_reset\",\"notes\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"external_user_id\":\"external_user_id\",\"external_user_name\":\"external_user_name\",\"id\":\"id\",\"note\":\"note\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"rule_id\":\"rule_id\",\"rule_id_usage_exceeded\":\"rule_id_usage_exceeded\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"value_bool\":true,\"value_numeric\":1,\"value_trait\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"display_name\":\"display_name\",\"entity_type\":\"entity_type\",\"hierarchy\":[\"hierarchy\"],\"id\":\"id\",\"trait_type\":\"trait_type\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"value_trait_id\":\"value_trait_id\",\"value_type\":\"value_type\"}],\"params\":{\"company_id\":\"company_id\",\"company_ids\":[\"company_ids\"],\"feature_id\":\"feature_id\",\"feature_ids\":[\"feature_ids\"],\"ids\":[\"ids\"],\"limit\":1,\"offset\":1,\"q\":\"q\",\"without_expired\":true}}"));
        ListCompanyOverridesResponse response = client.entitlements()
                .listCompanyOverrides(ListCompanyOverridesRequest.builder()
                        .companyId("company_id")
                        .featureId("feature_id")
                        .withoutExpired(true)
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
                + "      \"company\": {\n"
                + "        \"add_ons\": [\n"
                + "          {\n"
                + "            \"id\": \"id\",\n"
                + "            \"name\": \"name\"\n"
                + "          }\n"
                + "        ],\n"
                + "        \"billing_subscriptions\": [\n"
                + "          {\n"
                + "            \"cancel_at_period_end\": true,\n"
                + "            \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"currency\": \"currency\",\n"
                + "            \"customer_external_id\": \"customer_external_id\",\n"
                + "            \"discounts\": [\n"
                + "              {\n"
                + "                \"coupon_id\": \"coupon_id\",\n"
                + "                \"coupon_name\": \"coupon_name\",\n"
                + "                \"discount_external_id\": \"discount_external_id\",\n"
                + "                \"duration\": \"duration\",\n"
                + "                \"is_active\": true,\n"
                + "                \"started_at\": \"2024-01-15T09:30:00Z\",\n"
                + "                \"subscription_external_id\": \"subscription_external_id\"\n"
                + "              }\n"
                + "            ],\n"
                + "            \"id\": \"id\",\n"
                + "            \"interval\": \"interval\",\n"
                + "            \"period_end\": 1,\n"
                + "            \"period_start\": 1,\n"
                + "            \"products\": [\n"
                + "              {\n"
                + "                \"billing_scheme\": \"billing_scheme\",\n"
                + "                \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "                \"currency\": \"currency\",\n"
                + "                \"environment_id\": \"environment_id\",\n"
                + "                \"external_id\": \"external_id\",\n"
                + "                \"id\": \"id\",\n"
                + "                \"interval\": \"interval\",\n"
                + "                \"name\": \"name\",\n"
                + "                \"package_size\": 1,\n"
                + "                \"price\": 1,\n"
                + "                \"price_external_id\": \"price_external_id\",\n"
                + "                \"price_id\": \"price_id\",\n"
                + "                \"price_tier\": [\n"
                + "                  {}\n"
                + "                ],\n"
                + "                \"quantity\": 1.1,\n"
                + "                \"subscription_id\": \"subscription_id\",\n"
                + "                \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "                \"usage_type\": \"usage_type\"\n"
                + "              }\n"
                + "            ],\n"
                + "            \"status\": \"status\",\n"
                + "            \"subscription_external_id\": \"subscription_external_id\",\n"
                + "            \"total_price\": 1\n"
                + "          }\n"
                + "        ],\n"
                + "        \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"entity_traits\": [\n"
                + "          {\n"
                + "            \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"definition_id\": \"definition_id\",\n"
                + "            \"environment_id\": \"environment_id\",\n"
                + "            \"id\": \"id\",\n"
                + "            \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"value\": \"value\"\n"
                + "          }\n"
                + "        ],\n"
                + "        \"environment_id\": \"environment_id\",\n"
                + "        \"id\": \"id\",\n"
                + "        \"keys\": [\n"
                + "          {\n"
                + "            \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"definition_id\": \"definition_id\",\n"
                + "            \"entity_id\": \"entity_id\",\n"
                + "            \"entity_type\": \"entity_type\",\n"
                + "            \"environment_id\": \"environment_id\",\n"
                + "            \"id\": \"id\",\n"
                + "            \"key\": \"key\",\n"
                + "            \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"value\": \"value\"\n"
                + "          }\n"
                + "        ],\n"
                + "        \"metrics\": [\n"
                + "          {\n"
                + "            \"account_id\": \"account_id\",\n"
                + "            \"captured_at_max\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"captured_at_min\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"company_id\": \"company_id\",\n"
                + "            \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"environment_id\": \"environment_id\",\n"
                + "            \"event_subtype\": \"event_subtype\",\n"
                + "            \"month_reset\": \"month_reset\",\n"
                + "            \"period\": \"period\",\n"
                + "            \"value\": 1\n"
                + "          }\n"
                + "        ],\n"
                + "        \"name\": \"name\",\n"
                + "        \"payment_methods\": [\n"
                + "          {\n"
                + "            \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"customer_external_id\": \"customer_external_id\",\n"
                + "            \"environment_id\": \"environment_id\",\n"
                + "            \"external_id\": \"external_id\",\n"
                + "            \"id\": \"id\",\n"
                + "            \"payment_method_type\": \"payment_method_type\",\n"
                + "            \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "          }\n"
                + "        ],\n"
                + "        \"plans\": [\n"
                + "          {\n"
                + "            \"id\": \"id\",\n"
                + "            \"name\": \"name\"\n"
                + "          }\n"
                + "        ],\n"
                + "        \"rules\": [\n"
                + "          {\n"
                + "            \"account_id\": \"account_id\",\n"
                + "            \"condition_groups\": [\n"
                + "              {\n"
                + "                \"conditions\": [\n"
                + "                  {\n"
                + "                    \"account_id\": \"account_id\",\n"
                + "                    \"condition_type\": \"condition_type\",\n"
                + "                    \"environment_id\": \"environment_id\",\n"
                + "                    \"id\": \"id\",\n"
                + "                    \"operator\": \"operator\",\n"
                + "                    \"resource_ids\": [\n"
                + "                      \"resource_ids\"\n"
                + "                    ],\n"
                + "                    \"trait_value\": \"trait_value\"\n"
                + "                  }\n"
                + "                ]\n"
                + "              }\n"
                + "            ],\n"
                + "            \"conditions\": [\n"
                + "              {\n"
                + "                \"account_id\": \"account_id\",\n"
                + "                \"condition_type\": \"condition_type\",\n"
                + "                \"environment_id\": \"environment_id\",\n"
                + "                \"id\": \"id\",\n"
                + "                \"operator\": \"operator\",\n"
                + "                \"resource_ids\": [\n"
                + "                  \"resource_ids\"\n"
                + "                ],\n"
                + "                \"trait_value\": \"trait_value\"\n"
                + "              }\n"
                + "            ],\n"
                + "            \"environment_id\": \"environment_id\",\n"
                + "            \"id\": \"id\",\n"
                + "            \"name\": \"name\",\n"
                + "            \"priority\": 1,\n"
                + "            \"rule_type\": \"rule_type\",\n"
                + "            \"value\": true\n"
                + "          }\n"
                + "        ],\n"
                + "        \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"user_count\": 1\n"
                + "      },\n"
                + "      \"company_id\": \"company_id\",\n"
                + "      \"consumption_rate\": 1.1,\n"
                + "      \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"environment_id\": \"environment_id\",\n"
                + "      \"expiration_date\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"feature\": {\n"
                + "        \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"description\": \"description\",\n"
                + "        \"feature_type\": \"feature_type\",\n"
                + "        \"icon\": \"icon\",\n"
                + "        \"id\": \"id\",\n"
                + "        \"name\": \"name\",\n"
                + "        \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "      },\n"
                + "      \"feature_id\": \"feature_id\",\n"
                + "      \"id\": \"id\",\n"
                + "      \"metric_period\": \"metric_period\",\n"
                + "      \"metric_period_month_reset\": \"metric_period_month_reset\",\n"
                + "      \"notes\": [\n"
                + "        {\n"
                + "          \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "          \"external_user_id\": \"external_user_id\",\n"
                + "          \"external_user_name\": \"external_user_name\",\n"
                + "          \"id\": \"id\",\n"
                + "          \"note\": \"note\",\n"
                + "          \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "        }\n"
                + "      ],\n"
                + "      \"rule_id\": \"rule_id\",\n"
                + "      \"rule_id_usage_exceeded\": \"rule_id_usage_exceeded\",\n"
                + "      \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"value_bool\": true,\n"
                + "      \"value_numeric\": 1,\n"
                + "      \"value_trait\": {\n"
                + "        \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"display_name\": \"display_name\",\n"
                + "        \"entity_type\": \"entity_type\",\n"
                + "        \"hierarchy\": [\n"
                + "          \"hierarchy\"\n"
                + "        ],\n"
                + "        \"id\": \"id\",\n"
                + "        \"trait_type\": \"trait_type\",\n"
                + "        \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "      },\n"
                + "      \"value_trait_id\": \"value_trait_id\",\n"
                + "      \"value_type\": \"value_type\"\n"
                + "    }\n"
                + "  ],\n"
                + "  \"params\": {\n"
                + "    \"company_id\": \"company_id\",\n"
                + "    \"company_ids\": [\n"
                + "      \"company_ids\"\n"
                + "    ],\n"
                + "    \"feature_id\": \"feature_id\",\n"
                + "    \"feature_ids\": [\n"
                + "      \"feature_ids\"\n"
                + "    ],\n"
                + "    \"ids\": [\n"
                + "      \"ids\"\n"
                + "    ],\n"
                + "    \"limit\": 1,\n"
                + "    \"offset\": 1,\n"
                + "    \"q\": \"q\",\n"
                + "    \"without_expired\": true\n"
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
    public void testCreateCompanyOverride() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":{\"company\":{\"add_ons\":[{\"id\":\"id\",\"name\":\"name\"}],\"billing_credit_balances\":{\"key\":1.1},\"billing_subscription\":{\"cancel_at_period_end\":true,\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"customer_external_id\":\"customer_external_id\",\"discounts\":[{\"coupon_id\":\"coupon_id\",\"coupon_name\":\"coupon_name\",\"discount_external_id\":\"discount_external_id\",\"duration\":\"duration\",\"is_active\":true,\"started_at\":\"2024-01-15T09:30:00Z\",\"subscription_external_id\":\"subscription_external_id\"}],\"id\":\"id\",\"interval\":\"interval\",\"period_end\":1,\"period_start\":1,\"products\":[{\"billing_scheme\":\"billing_scheme\",\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"id\":\"id\",\"interval\":\"interval\",\"name\":\"name\",\"package_size\":1,\"price\":1,\"price_external_id\":\"price_external_id\",\"price_id\":\"price_id\",\"price_tier\":[{}],\"quantity\":1.1,\"subscription_id\":\"subscription_id\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"usage_type\":\"usage_type\"}],\"status\":\"status\",\"subscription_external_id\":\"subscription_external_id\",\"total_price\":1},\"billing_subscriptions\":[{\"cancel_at_period_end\":true,\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"customer_external_id\":\"customer_external_id\",\"discounts\":[{\"coupon_id\":\"coupon_id\",\"coupon_name\":\"coupon_name\",\"discount_external_id\":\"discount_external_id\",\"duration\":\"duration\",\"is_active\":true,\"started_at\":\"2024-01-15T09:30:00Z\",\"subscription_external_id\":\"subscription_external_id\"}],\"id\":\"id\",\"interval\":\"interval\",\"period_end\":1,\"period_start\":1,\"products\":[{\"billing_scheme\":\"billing_scheme\",\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"id\":\"id\",\"interval\":\"interval\",\"name\":\"name\",\"package_size\":1,\"price\":1,\"price_external_id\":\"price_external_id\",\"price_id\":\"price_id\",\"price_tier\":[{}],\"quantity\":1.1,\"subscription_id\":\"subscription_id\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"usage_type\":\"usage_type\"}],\"status\":\"status\",\"subscription_external_id\":\"subscription_external_id\",\"total_price\":1}],\"created_at\":\"2024-01-15T09:30:00Z\",\"default_payment_method\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"customer_external_id\":\"customer_external_id\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"id\":\"id\",\"payment_method_type\":\"payment_method_type\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"entity_traits\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"definition_id\":\"definition_id\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"value\":\"value\"}],\"environment_id\":\"environment_id\",\"id\":\"id\",\"keys\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"definition_id\":\"definition_id\",\"entity_id\":\"entity_id\",\"entity_type\":\"entity_type\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"key\":\"key\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"value\":\"value\"}],\"last_seen_at\":\"2024-01-15T09:30:00Z\",\"logo_url\":\"logo_url\",\"metrics\":[{\"account_id\":\"account_id\",\"captured_at_max\":\"2024-01-15T09:30:00Z\",\"captured_at_min\":\"2024-01-15T09:30:00Z\",\"company_id\":\"company_id\",\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"event_subtype\":\"event_subtype\",\"month_reset\":\"month_reset\",\"period\":\"period\",\"value\":1}],\"name\":\"name\",\"payment_methods\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"customer_external_id\":\"customer_external_id\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"id\":\"id\",\"payment_method_type\":\"payment_method_type\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"plan\":{\"id\":\"id\",\"name\":\"name\"},\"plans\":[{\"id\":\"id\",\"name\":\"name\"}],\"rules\":[{\"account_id\":\"account_id\",\"condition_groups\":[{\"conditions\":[{\"account_id\":\"account_id\",\"condition_type\":\"condition_type\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"operator\":\"operator\",\"resource_ids\":[\"resource_ids\"],\"trait_value\":\"trait_value\"}]}],\"conditions\":[{\"account_id\":\"account_id\",\"condition_type\":\"condition_type\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"operator\":\"operator\",\"resource_ids\":[\"resource_ids\"],\"trait_value\":\"trait_value\"}],\"environment_id\":\"environment_id\",\"id\":\"id\",\"name\":\"name\",\"priority\":1,\"rule_type\":\"rule_type\",\"value\":true}],\"traits\":{\"key\":\"value\"},\"updated_at\":\"2024-01-15T09:30:00Z\",\"user_count\":1},\"company_id\":\"company_id\",\"consumption_rate\":1.1,\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"expiration_date\":\"2024-01-15T09:30:00Z\",\"feature\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"description\":\"description\",\"event_subtype\":\"event_subtype\",\"feature_type\":\"feature_type\",\"icon\":\"icon\",\"id\":\"id\",\"lifecycle_phase\":\"lifecycle_phase\",\"maintainer_id\":\"maintainer_id\",\"name\":\"name\",\"plural_name\":\"plural_name\",\"singular_name\":\"singular_name\",\"trait_id\":\"trait_id\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"feature_id\":\"feature_id\",\"id\":\"id\",\"metric_period\":\"metric_period\",\"metric_period_month_reset\":\"metric_period_month_reset\",\"notes\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"external_user_id\":\"external_user_id\",\"external_user_name\":\"external_user_name\",\"id\":\"id\",\"note\":\"note\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"rule_id\":\"rule_id\",\"rule_id_usage_exceeded\":\"rule_id_usage_exceeded\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"value_bool\":true,\"value_numeric\":1,\"value_trait\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"display_name\":\"display_name\",\"entity_type\":\"entity_type\",\"hierarchy\":[\"hierarchy\"],\"id\":\"id\",\"trait_type\":\"trait_type\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"value_trait_id\":\"value_trait_id\",\"value_type\":\"value_type\"},\"params\":{\"key\":\"value\"}}"));
        CreateCompanyOverrideResponse response = client.entitlements()
                .createCompanyOverride(CreateCompanyOverrideRequestBody.builder()
                        .companyId("company_id")
                        .featureId("feature_id")
                        .valueType(CreateCompanyOverrideRequestBodyValueType.BOOLEAN)
                        .build());
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("POST", request.getMethod());
        // Validate request body
        String actualRequestBody = request.getBody().readUtf8();
        String expectedRequestBody = ""
                + "{\n"
                + "  \"company_id\": \"company_id\",\n"
                + "  \"feature_id\": \"feature_id\",\n"
                + "  \"value_type\": \"boolean\"\n"
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
                + "    \"company_id\": \"company_id\",\n"
                + "    \"consumption_rate\": 1.1,\n"
                + "    \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"environment_id\": \"environment_id\",\n"
                + "    \"expiration_date\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"feature\": {\n"
                + "      \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"description\": \"description\",\n"
                + "      \"event_subtype\": \"event_subtype\",\n"
                + "      \"feature_type\": \"feature_type\",\n"
                + "      \"icon\": \"icon\",\n"
                + "      \"id\": \"id\",\n"
                + "      \"lifecycle_phase\": \"lifecycle_phase\",\n"
                + "      \"maintainer_id\": \"maintainer_id\",\n"
                + "      \"name\": \"name\",\n"
                + "      \"plural_name\": \"plural_name\",\n"
                + "      \"singular_name\": \"singular_name\",\n"
                + "      \"trait_id\": \"trait_id\",\n"
                + "      \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "    },\n"
                + "    \"feature_id\": \"feature_id\",\n"
                + "    \"id\": \"id\",\n"
                + "    \"metric_period\": \"metric_period\",\n"
                + "    \"metric_period_month_reset\": \"metric_period_month_reset\",\n"
                + "    \"notes\": [\n"
                + "      {\n"
                + "        \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"external_user_id\": \"external_user_id\",\n"
                + "        \"external_user_name\": \"external_user_name\",\n"
                + "        \"id\": \"id\",\n"
                + "        \"note\": \"note\",\n"
                + "        \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "      }\n"
                + "    ],\n"
                + "    \"rule_id\": \"rule_id\",\n"
                + "    \"rule_id_usage_exceeded\": \"rule_id_usage_exceeded\",\n"
                + "    \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"value_bool\": true,\n"
                + "    \"value_numeric\": 1,\n"
                + "    \"value_trait\": {\n"
                + "      \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"display_name\": \"display_name\",\n"
                + "      \"entity_type\": \"entity_type\",\n"
                + "      \"hierarchy\": [\n"
                + "        \"hierarchy\"\n"
                + "      ],\n"
                + "      \"id\": \"id\",\n"
                + "      \"trait_type\": \"trait_type\",\n"
                + "      \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "    },\n"
                + "    \"value_trait_id\": \"value_trait_id\",\n"
                + "    \"value_type\": \"value_type\"\n"
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
    public void testGetCompanyOverride() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":{\"company\":{\"add_ons\":[{\"id\":\"id\",\"name\":\"name\"}],\"billing_credit_balances\":{\"key\":1.1},\"billing_subscription\":{\"cancel_at_period_end\":true,\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"customer_external_id\":\"customer_external_id\",\"discounts\":[{\"coupon_id\":\"coupon_id\",\"coupon_name\":\"coupon_name\",\"discount_external_id\":\"discount_external_id\",\"duration\":\"duration\",\"is_active\":true,\"started_at\":\"2024-01-15T09:30:00Z\",\"subscription_external_id\":\"subscription_external_id\"}],\"id\":\"id\",\"interval\":\"interval\",\"period_end\":1,\"period_start\":1,\"products\":[{\"billing_scheme\":\"billing_scheme\",\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"id\":\"id\",\"interval\":\"interval\",\"name\":\"name\",\"package_size\":1,\"price\":1,\"price_external_id\":\"price_external_id\",\"price_id\":\"price_id\",\"price_tier\":[{}],\"quantity\":1.1,\"subscription_id\":\"subscription_id\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"usage_type\":\"usage_type\"}],\"status\":\"status\",\"subscription_external_id\":\"subscription_external_id\",\"total_price\":1},\"billing_subscriptions\":[{\"cancel_at_period_end\":true,\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"customer_external_id\":\"customer_external_id\",\"discounts\":[{\"coupon_id\":\"coupon_id\",\"coupon_name\":\"coupon_name\",\"discount_external_id\":\"discount_external_id\",\"duration\":\"duration\",\"is_active\":true,\"started_at\":\"2024-01-15T09:30:00Z\",\"subscription_external_id\":\"subscription_external_id\"}],\"id\":\"id\",\"interval\":\"interval\",\"period_end\":1,\"period_start\":1,\"products\":[{\"billing_scheme\":\"billing_scheme\",\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"id\":\"id\",\"interval\":\"interval\",\"name\":\"name\",\"package_size\":1,\"price\":1,\"price_external_id\":\"price_external_id\",\"price_id\":\"price_id\",\"price_tier\":[{}],\"quantity\":1.1,\"subscription_id\":\"subscription_id\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"usage_type\":\"usage_type\"}],\"status\":\"status\",\"subscription_external_id\":\"subscription_external_id\",\"total_price\":1}],\"created_at\":\"2024-01-15T09:30:00Z\",\"default_payment_method\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"customer_external_id\":\"customer_external_id\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"id\":\"id\",\"payment_method_type\":\"payment_method_type\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"entity_traits\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"definition_id\":\"definition_id\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"value\":\"value\"}],\"environment_id\":\"environment_id\",\"id\":\"id\",\"keys\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"definition_id\":\"definition_id\",\"entity_id\":\"entity_id\",\"entity_type\":\"entity_type\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"key\":\"key\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"value\":\"value\"}],\"last_seen_at\":\"2024-01-15T09:30:00Z\",\"logo_url\":\"logo_url\",\"metrics\":[{\"account_id\":\"account_id\",\"captured_at_max\":\"2024-01-15T09:30:00Z\",\"captured_at_min\":\"2024-01-15T09:30:00Z\",\"company_id\":\"company_id\",\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"event_subtype\":\"event_subtype\",\"month_reset\":\"month_reset\",\"period\":\"period\",\"value\":1}],\"name\":\"name\",\"payment_methods\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"customer_external_id\":\"customer_external_id\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"id\":\"id\",\"payment_method_type\":\"payment_method_type\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"plan\":{\"id\":\"id\",\"name\":\"name\"},\"plans\":[{\"id\":\"id\",\"name\":\"name\"}],\"rules\":[{\"account_id\":\"account_id\",\"condition_groups\":[{\"conditions\":[{\"account_id\":\"account_id\",\"condition_type\":\"condition_type\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"operator\":\"operator\",\"resource_ids\":[\"resource_ids\"],\"trait_value\":\"trait_value\"}]}],\"conditions\":[{\"account_id\":\"account_id\",\"condition_type\":\"condition_type\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"operator\":\"operator\",\"resource_ids\":[\"resource_ids\"],\"trait_value\":\"trait_value\"}],\"environment_id\":\"environment_id\",\"id\":\"id\",\"name\":\"name\",\"priority\":1,\"rule_type\":\"rule_type\",\"value\":true}],\"traits\":{\"key\":\"value\"},\"updated_at\":\"2024-01-15T09:30:00Z\",\"user_count\":1},\"company_id\":\"company_id\",\"consumption_rate\":1.1,\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"expiration_date\":\"2024-01-15T09:30:00Z\",\"feature\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"description\":\"description\",\"event_subtype\":\"event_subtype\",\"feature_type\":\"feature_type\",\"icon\":\"icon\",\"id\":\"id\",\"lifecycle_phase\":\"lifecycle_phase\",\"maintainer_id\":\"maintainer_id\",\"name\":\"name\",\"plural_name\":\"plural_name\",\"singular_name\":\"singular_name\",\"trait_id\":\"trait_id\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"feature_id\":\"feature_id\",\"id\":\"id\",\"metric_period\":\"metric_period\",\"metric_period_month_reset\":\"metric_period_month_reset\",\"notes\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"external_user_id\":\"external_user_id\",\"external_user_name\":\"external_user_name\",\"id\":\"id\",\"note\":\"note\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"rule_id\":\"rule_id\",\"rule_id_usage_exceeded\":\"rule_id_usage_exceeded\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"value_bool\":true,\"value_numeric\":1,\"value_trait\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"display_name\":\"display_name\",\"entity_type\":\"entity_type\",\"hierarchy\":[\"hierarchy\"],\"id\":\"id\",\"trait_type\":\"trait_type\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"value_trait_id\":\"value_trait_id\",\"value_type\":\"value_type\"},\"params\":{\"key\":\"value\"}}"));
        GetCompanyOverrideResponse response = client.entitlements().getCompanyOverride("company_override_id");
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("GET", request.getMethod());

        // Validate response body
        Assertions.assertNotNull(response, "Response should not be null");
        String actualResponseJson = objectMapper.writeValueAsString(response);
        String expectedResponseBody = ""
                + "{\n"
                + "  \"data\": {\n"
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
                + "    \"company_id\": \"company_id\",\n"
                + "    \"consumption_rate\": 1.1,\n"
                + "    \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"environment_id\": \"environment_id\",\n"
                + "    \"expiration_date\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"feature\": {\n"
                + "      \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"description\": \"description\",\n"
                + "      \"event_subtype\": \"event_subtype\",\n"
                + "      \"feature_type\": \"feature_type\",\n"
                + "      \"icon\": \"icon\",\n"
                + "      \"id\": \"id\",\n"
                + "      \"lifecycle_phase\": \"lifecycle_phase\",\n"
                + "      \"maintainer_id\": \"maintainer_id\",\n"
                + "      \"name\": \"name\",\n"
                + "      \"plural_name\": \"plural_name\",\n"
                + "      \"singular_name\": \"singular_name\",\n"
                + "      \"trait_id\": \"trait_id\",\n"
                + "      \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "    },\n"
                + "    \"feature_id\": \"feature_id\",\n"
                + "    \"id\": \"id\",\n"
                + "    \"metric_period\": \"metric_period\",\n"
                + "    \"metric_period_month_reset\": \"metric_period_month_reset\",\n"
                + "    \"notes\": [\n"
                + "      {\n"
                + "        \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"external_user_id\": \"external_user_id\",\n"
                + "        \"external_user_name\": \"external_user_name\",\n"
                + "        \"id\": \"id\",\n"
                + "        \"note\": \"note\",\n"
                + "        \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "      }\n"
                + "    ],\n"
                + "    \"rule_id\": \"rule_id\",\n"
                + "    \"rule_id_usage_exceeded\": \"rule_id_usage_exceeded\",\n"
                + "    \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"value_bool\": true,\n"
                + "    \"value_numeric\": 1,\n"
                + "    \"value_trait\": {\n"
                + "      \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"display_name\": \"display_name\",\n"
                + "      \"entity_type\": \"entity_type\",\n"
                + "      \"hierarchy\": [\n"
                + "        \"hierarchy\"\n"
                + "      ],\n"
                + "      \"id\": \"id\",\n"
                + "      \"trait_type\": \"trait_type\",\n"
                + "      \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "    },\n"
                + "    \"value_trait_id\": \"value_trait_id\",\n"
                + "    \"value_type\": \"value_type\"\n"
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
    public void testUpdateCompanyOverride() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":{\"company\":{\"add_ons\":[{\"id\":\"id\",\"name\":\"name\"}],\"billing_credit_balances\":{\"key\":1.1},\"billing_subscription\":{\"cancel_at_period_end\":true,\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"customer_external_id\":\"customer_external_id\",\"discounts\":[{\"coupon_id\":\"coupon_id\",\"coupon_name\":\"coupon_name\",\"discount_external_id\":\"discount_external_id\",\"duration\":\"duration\",\"is_active\":true,\"started_at\":\"2024-01-15T09:30:00Z\",\"subscription_external_id\":\"subscription_external_id\"}],\"id\":\"id\",\"interval\":\"interval\",\"period_end\":1,\"period_start\":1,\"products\":[{\"billing_scheme\":\"billing_scheme\",\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"id\":\"id\",\"interval\":\"interval\",\"name\":\"name\",\"package_size\":1,\"price\":1,\"price_external_id\":\"price_external_id\",\"price_id\":\"price_id\",\"price_tier\":[{}],\"quantity\":1.1,\"subscription_id\":\"subscription_id\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"usage_type\":\"usage_type\"}],\"status\":\"status\",\"subscription_external_id\":\"subscription_external_id\",\"total_price\":1},\"billing_subscriptions\":[{\"cancel_at_period_end\":true,\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"customer_external_id\":\"customer_external_id\",\"discounts\":[{\"coupon_id\":\"coupon_id\",\"coupon_name\":\"coupon_name\",\"discount_external_id\":\"discount_external_id\",\"duration\":\"duration\",\"is_active\":true,\"started_at\":\"2024-01-15T09:30:00Z\",\"subscription_external_id\":\"subscription_external_id\"}],\"id\":\"id\",\"interval\":\"interval\",\"period_end\":1,\"period_start\":1,\"products\":[{\"billing_scheme\":\"billing_scheme\",\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"id\":\"id\",\"interval\":\"interval\",\"name\":\"name\",\"package_size\":1,\"price\":1,\"price_external_id\":\"price_external_id\",\"price_id\":\"price_id\",\"price_tier\":[{}],\"quantity\":1.1,\"subscription_id\":\"subscription_id\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"usage_type\":\"usage_type\"}],\"status\":\"status\",\"subscription_external_id\":\"subscription_external_id\",\"total_price\":1}],\"created_at\":\"2024-01-15T09:30:00Z\",\"default_payment_method\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"customer_external_id\":\"customer_external_id\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"id\":\"id\",\"payment_method_type\":\"payment_method_type\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"entity_traits\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"definition_id\":\"definition_id\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"value\":\"value\"}],\"environment_id\":\"environment_id\",\"id\":\"id\",\"keys\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"definition_id\":\"definition_id\",\"entity_id\":\"entity_id\",\"entity_type\":\"entity_type\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"key\":\"key\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"value\":\"value\"}],\"last_seen_at\":\"2024-01-15T09:30:00Z\",\"logo_url\":\"logo_url\",\"metrics\":[{\"account_id\":\"account_id\",\"captured_at_max\":\"2024-01-15T09:30:00Z\",\"captured_at_min\":\"2024-01-15T09:30:00Z\",\"company_id\":\"company_id\",\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"event_subtype\":\"event_subtype\",\"month_reset\":\"month_reset\",\"period\":\"period\",\"value\":1}],\"name\":\"name\",\"payment_methods\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"customer_external_id\":\"customer_external_id\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"id\":\"id\",\"payment_method_type\":\"payment_method_type\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"plan\":{\"id\":\"id\",\"name\":\"name\"},\"plans\":[{\"id\":\"id\",\"name\":\"name\"}],\"rules\":[{\"account_id\":\"account_id\",\"condition_groups\":[{\"conditions\":[{\"account_id\":\"account_id\",\"condition_type\":\"condition_type\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"operator\":\"operator\",\"resource_ids\":[\"resource_ids\"],\"trait_value\":\"trait_value\"}]}],\"conditions\":[{\"account_id\":\"account_id\",\"condition_type\":\"condition_type\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"operator\":\"operator\",\"resource_ids\":[\"resource_ids\"],\"trait_value\":\"trait_value\"}],\"environment_id\":\"environment_id\",\"id\":\"id\",\"name\":\"name\",\"priority\":1,\"rule_type\":\"rule_type\",\"value\":true}],\"traits\":{\"key\":\"value\"},\"updated_at\":\"2024-01-15T09:30:00Z\",\"user_count\":1},\"company_id\":\"company_id\",\"consumption_rate\":1.1,\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"expiration_date\":\"2024-01-15T09:30:00Z\",\"feature\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"description\":\"description\",\"event_subtype\":\"event_subtype\",\"feature_type\":\"feature_type\",\"icon\":\"icon\",\"id\":\"id\",\"lifecycle_phase\":\"lifecycle_phase\",\"maintainer_id\":\"maintainer_id\",\"name\":\"name\",\"plural_name\":\"plural_name\",\"singular_name\":\"singular_name\",\"trait_id\":\"trait_id\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"feature_id\":\"feature_id\",\"id\":\"id\",\"metric_period\":\"metric_period\",\"metric_period_month_reset\":\"metric_period_month_reset\",\"notes\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"external_user_id\":\"external_user_id\",\"external_user_name\":\"external_user_name\",\"id\":\"id\",\"note\":\"note\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"rule_id\":\"rule_id\",\"rule_id_usage_exceeded\":\"rule_id_usage_exceeded\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"value_bool\":true,\"value_numeric\":1,\"value_trait\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"display_name\":\"display_name\",\"entity_type\":\"entity_type\",\"hierarchy\":[\"hierarchy\"],\"id\":\"id\",\"trait_type\":\"trait_type\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"value_trait_id\":\"value_trait_id\",\"value_type\":\"value_type\"},\"params\":{\"key\":\"value\"}}"));
        UpdateCompanyOverrideResponse response = client.entitlements()
                .updateCompanyOverride(
                        "company_override_id",
                        UpdateCompanyOverrideRequestBody.builder()
                                .valueType(UpdateCompanyOverrideRequestBodyValueType.BOOLEAN)
                                .build());
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("PUT", request.getMethod());
        // Validate request body
        String actualRequestBody = request.getBody().readUtf8();
        String expectedRequestBody = "" + "{\n" + "  \"value_type\": \"boolean\"\n" + "}";
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
                + "    \"company_id\": \"company_id\",\n"
                + "    \"consumption_rate\": 1.1,\n"
                + "    \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"environment_id\": \"environment_id\",\n"
                + "    \"expiration_date\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"feature\": {\n"
                + "      \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"description\": \"description\",\n"
                + "      \"event_subtype\": \"event_subtype\",\n"
                + "      \"feature_type\": \"feature_type\",\n"
                + "      \"icon\": \"icon\",\n"
                + "      \"id\": \"id\",\n"
                + "      \"lifecycle_phase\": \"lifecycle_phase\",\n"
                + "      \"maintainer_id\": \"maintainer_id\",\n"
                + "      \"name\": \"name\",\n"
                + "      \"plural_name\": \"plural_name\",\n"
                + "      \"singular_name\": \"singular_name\",\n"
                + "      \"trait_id\": \"trait_id\",\n"
                + "      \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "    },\n"
                + "    \"feature_id\": \"feature_id\",\n"
                + "    \"id\": \"id\",\n"
                + "    \"metric_period\": \"metric_period\",\n"
                + "    \"metric_period_month_reset\": \"metric_period_month_reset\",\n"
                + "    \"notes\": [\n"
                + "      {\n"
                + "        \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"external_user_id\": \"external_user_id\",\n"
                + "        \"external_user_name\": \"external_user_name\",\n"
                + "        \"id\": \"id\",\n"
                + "        \"note\": \"note\",\n"
                + "        \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "      }\n"
                + "    ],\n"
                + "    \"rule_id\": \"rule_id\",\n"
                + "    \"rule_id_usage_exceeded\": \"rule_id_usage_exceeded\",\n"
                + "    \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"value_bool\": true,\n"
                + "    \"value_numeric\": 1,\n"
                + "    \"value_trait\": {\n"
                + "      \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"display_name\": \"display_name\",\n"
                + "      \"entity_type\": \"entity_type\",\n"
                + "      \"hierarchy\": [\n"
                + "        \"hierarchy\"\n"
                + "      ],\n"
                + "      \"id\": \"id\",\n"
                + "      \"trait_type\": \"trait_type\",\n"
                + "      \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "    },\n"
                + "    \"value_trait_id\": \"value_trait_id\",\n"
                + "    \"value_type\": \"value_type\"\n"
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
    public void testDeleteCompanyOverride() throws Exception {
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody("{\"data\":{\"deleted\":true},\"params\":{\"key\":\"value\"}}"));
        DeleteCompanyOverrideResponse response = client.entitlements().deleteCompanyOverride("company_override_id");
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
    public void testCountCompanyOverrides() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":{\"count\":1},\"params\":{\"company_id\":\"company_id\",\"company_ids\":[\"company_ids\"],\"feature_id\":\"feature_id\",\"feature_ids\":[\"feature_ids\"],\"ids\":[\"ids\"],\"limit\":1,\"offset\":1,\"q\":\"q\",\"without_expired\":true}}"));
        CountCompanyOverridesResponse response = client.entitlements()
                .countCompanyOverrides(CountCompanyOverridesRequest.builder()
                        .companyId("company_id")
                        .featureId("feature_id")
                        .withoutExpired(true)
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
                + "    \"company_id\": \"company_id\",\n"
                + "    \"company_ids\": [\n"
                + "      \"company_ids\"\n"
                + "    ],\n"
                + "    \"feature_id\": \"feature_id\",\n"
                + "    \"feature_ids\": [\n"
                + "      \"feature_ids\"\n"
                + "    ],\n"
                + "    \"ids\": [\n"
                + "      \"ids\"\n"
                + "    ],\n"
                + "    \"limit\": 1,\n"
                + "    \"offset\": 1,\n"
                + "    \"q\": \"q\",\n"
                + "    \"without_expired\": true\n"
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
    public void testListFeatureCompanies() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":[{\"access\":true,\"allocation\":1,\"allocation_type\":\"boolean\",\"company\":{\"add_ons\":[{\"id\":\"id\",\"name\":\"name\"}],\"billing_subscriptions\":[{\"cancel_at_period_end\":true,\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"customer_external_id\":\"customer_external_id\",\"discounts\":[{\"coupon_id\":\"coupon_id\",\"coupon_name\":\"coupon_name\",\"discount_external_id\":\"discount_external_id\",\"duration\":\"duration\",\"is_active\":true,\"started_at\":\"2024-01-15T09:30:00Z\",\"subscription_external_id\":\"subscription_external_id\"}],\"id\":\"id\",\"interval\":\"interval\",\"period_end\":1,\"period_start\":1,\"products\":[{\"billing_scheme\":\"billing_scheme\",\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"id\":\"id\",\"interval\":\"interval\",\"name\":\"name\",\"package_size\":1,\"price\":1,\"price_external_id\":\"price_external_id\",\"price_id\":\"price_id\",\"price_tier\":[{}],\"quantity\":1.1,\"subscription_id\":\"subscription_id\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"usage_type\":\"usage_type\"}],\"status\":\"status\",\"subscription_external_id\":\"subscription_external_id\",\"total_price\":1}],\"created_at\":\"2024-01-15T09:30:00Z\",\"entity_traits\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"definition_id\":\"definition_id\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"value\":\"value\"}],\"environment_id\":\"environment_id\",\"id\":\"id\",\"keys\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"definition_id\":\"definition_id\",\"entity_id\":\"entity_id\",\"entity_type\":\"entity_type\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"key\":\"key\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"value\":\"value\"}],\"metrics\":[{\"account_id\":\"account_id\",\"captured_at_max\":\"2024-01-15T09:30:00Z\",\"captured_at_min\":\"2024-01-15T09:30:00Z\",\"company_id\":\"company_id\",\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"event_subtype\":\"event_subtype\",\"month_reset\":\"month_reset\",\"period\":\"period\",\"value\":1}],\"name\":\"name\",\"payment_methods\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"customer_external_id\":\"customer_external_id\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"id\":\"id\",\"payment_method_type\":\"payment_method_type\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"plans\":[{\"id\":\"id\",\"name\":\"name\"}],\"rules\":[{\"account_id\":\"account_id\",\"condition_groups\":[{\"conditions\":[{\"account_id\":\"account_id\",\"condition_type\":\"condition_type\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"operator\":\"operator\",\"resource_ids\":[\"resource_ids\"],\"trait_value\":\"trait_value\"}]}],\"conditions\":[{\"account_id\":\"account_id\",\"condition_type\":\"condition_type\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"operator\":\"operator\",\"resource_ids\":[\"resource_ids\"],\"trait_value\":\"trait_value\"}],\"environment_id\":\"environment_id\",\"id\":\"id\",\"name\":\"name\",\"priority\":1,\"rule_type\":\"rule_type\",\"value\":true}],\"updated_at\":\"2024-01-15T09:30:00Z\",\"user_count\":1},\"company_override\":{\"company_id\":\"company_id\",\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"feature_id\":\"feature_id\",\"id\":\"id\",\"notes\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"external_user_id\":\"external_user_id\",\"external_user_name\":\"external_user_name\",\"id\":\"id\",\"note\":\"note\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"updated_at\":\"2024-01-15T09:30:00Z\",\"value_type\":\"value_type\"},\"credit_consumption_rate\":1.1,\"credit_grant_counts\":{\"key\":1.1},\"credit_grant_details\":[{\"grant_reason\":\"free\",\"quantity\":1.1}],\"credit_grant_reason\":\"free\",\"credit_remaining\":1.1,\"credit_total\":1.1,\"credit_type_icon\":\"credit_type_icon\",\"credit_used\":1.1,\"effective_limit\":1,\"effective_price\":1.1,\"entitlement_expiration_date\":\"2024-01-15T09:30:00Z\",\"entitlement_id\":\"entitlement_id\",\"entitlement_source\":\"entitlement_source\",\"entitlement_type\":\"entitlement_type\",\"feature\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"description\":\"description\",\"feature_type\":\"feature_type\",\"flags\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"default_value\":true,\"description\":\"description\",\"flag_type\":\"flag_type\",\"id\":\"id\",\"key\":\"key\",\"name\":\"name\",\"rules\":[{\"condition_groups\":[{\"conditions\":[{\"condition_type\":\"condition_type\",\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"operator\":\"operator\",\"resource_ids\":[\"resource_ids\"],\"resources\":[{\"id\":\"id\",\"name\":\"name\"}],\"rule_id\":\"rule_id\",\"trait_value\":\"trait_value\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"rule_id\":\"rule_id\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"conditions\":[{\"condition_type\":\"condition_type\",\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"operator\":\"operator\",\"resource_ids\":[\"resource_ids\"],\"resources\":[{\"id\":\"id\",\"name\":\"name\"}],\"rule_id\":\"rule_id\",\"trait_value\":\"trait_value\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"name\":\"name\",\"priority\":1,\"rule_type\":\"rule_type\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"value\":true}],\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"icon\":\"icon\",\"id\":\"id\",\"name\":\"name\",\"plans\":[{\"id\":\"id\",\"name\":\"name\"}],\"updated_at\":\"2024-01-15T09:30:00Z\"},\"has_valid_allocation\":true,\"is_unlimited\":true,\"metric_reset_at\":\"2024-01-15T09:30:00Z\",\"month_reset\":\"month_reset\",\"monthly_usage_based_price\":{\"billing_scheme\":\"billing_scheme\",\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"id\":\"id\",\"interval\":\"interval\",\"is_active\":true,\"package_size\":1,\"price\":1,\"price_external_id\":\"price_external_id\",\"price_id\":\"price_id\",\"price_tier\":[{}],\"product_external_id\":\"product_external_id\",\"product_id\":\"product_id\",\"product_name\":\"product_name\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"usage_type\":\"usage_type\"},\"overuse\":1,\"percent_used\":1.1,\"period\":\"period\",\"plan\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"description\":\"description\",\"icon\":\"icon\",\"id\":\"id\",\"name\":\"name\",\"plan_type\":\"plan_type\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"plan_entitlement\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"feature_id\":\"feature_id\",\"id\":\"id\",\"plan_id\":\"plan_id\",\"rule_id\":\"rule_id\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"value_type\":\"value_type\"},\"price_behavior\":\"price_behavior\",\"soft_limit\":1,\"usage\":1,\"yearly_usage_based_price\":{\"billing_scheme\":\"billing_scheme\",\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"id\":\"id\",\"interval\":\"interval\",\"is_active\":true,\"package_size\":1,\"price\":1,\"price_external_id\":\"price_external_id\",\"price_id\":\"price_id\",\"price_tier\":[{}],\"product_external_id\":\"product_external_id\",\"product_id\":\"product_id\",\"product_name\":\"product_name\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"usage_type\":\"usage_type\"}}],\"params\":{\"feature_id\":\"feature_id\",\"limit\":1,\"offset\":1,\"q\":\"q\"}}"));
        ListFeatureCompaniesResponse response = client.entitlements()
                .listFeatureCompanies(ListFeatureCompaniesRequest.builder()
                        .featureId("feature_id")
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
                + "      \"access\": true,\n"
                + "      \"allocation\": 1,\n"
                + "      \"allocation_type\": \"boolean\",\n"
                + "      \"company\": {\n"
                + "        \"add_ons\": [\n"
                + "          {\n"
                + "            \"id\": \"id\",\n"
                + "            \"name\": \"name\"\n"
                + "          }\n"
                + "        ],\n"
                + "        \"billing_subscriptions\": [\n"
                + "          {\n"
                + "            \"cancel_at_period_end\": true,\n"
                + "            \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"currency\": \"currency\",\n"
                + "            \"customer_external_id\": \"customer_external_id\",\n"
                + "            \"discounts\": [\n"
                + "              {\n"
                + "                \"coupon_id\": \"coupon_id\",\n"
                + "                \"coupon_name\": \"coupon_name\",\n"
                + "                \"discount_external_id\": \"discount_external_id\",\n"
                + "                \"duration\": \"duration\",\n"
                + "                \"is_active\": true,\n"
                + "                \"started_at\": \"2024-01-15T09:30:00Z\",\n"
                + "                \"subscription_external_id\": \"subscription_external_id\"\n"
                + "              }\n"
                + "            ],\n"
                + "            \"id\": \"id\",\n"
                + "            \"interval\": \"interval\",\n"
                + "            \"period_end\": 1,\n"
                + "            \"period_start\": 1,\n"
                + "            \"products\": [\n"
                + "              {\n"
                + "                \"billing_scheme\": \"billing_scheme\",\n"
                + "                \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "                \"currency\": \"currency\",\n"
                + "                \"environment_id\": \"environment_id\",\n"
                + "                \"external_id\": \"external_id\",\n"
                + "                \"id\": \"id\",\n"
                + "                \"interval\": \"interval\",\n"
                + "                \"name\": \"name\",\n"
                + "                \"package_size\": 1,\n"
                + "                \"price\": 1,\n"
                + "                \"price_external_id\": \"price_external_id\",\n"
                + "                \"price_id\": \"price_id\",\n"
                + "                \"price_tier\": [\n"
                + "                  {}\n"
                + "                ],\n"
                + "                \"quantity\": 1.1,\n"
                + "                \"subscription_id\": \"subscription_id\",\n"
                + "                \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "                \"usage_type\": \"usage_type\"\n"
                + "              }\n"
                + "            ],\n"
                + "            \"status\": \"status\",\n"
                + "            \"subscription_external_id\": \"subscription_external_id\",\n"
                + "            \"total_price\": 1\n"
                + "          }\n"
                + "        ],\n"
                + "        \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"entity_traits\": [\n"
                + "          {\n"
                + "            \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"definition_id\": \"definition_id\",\n"
                + "            \"environment_id\": \"environment_id\",\n"
                + "            \"id\": \"id\",\n"
                + "            \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"value\": \"value\"\n"
                + "          }\n"
                + "        ],\n"
                + "        \"environment_id\": \"environment_id\",\n"
                + "        \"id\": \"id\",\n"
                + "        \"keys\": [\n"
                + "          {\n"
                + "            \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"definition_id\": \"definition_id\",\n"
                + "            \"entity_id\": \"entity_id\",\n"
                + "            \"entity_type\": \"entity_type\",\n"
                + "            \"environment_id\": \"environment_id\",\n"
                + "            \"id\": \"id\",\n"
                + "            \"key\": \"key\",\n"
                + "            \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"value\": \"value\"\n"
                + "          }\n"
                + "        ],\n"
                + "        \"metrics\": [\n"
                + "          {\n"
                + "            \"account_id\": \"account_id\",\n"
                + "            \"captured_at_max\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"captured_at_min\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"company_id\": \"company_id\",\n"
                + "            \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"environment_id\": \"environment_id\",\n"
                + "            \"event_subtype\": \"event_subtype\",\n"
                + "            \"month_reset\": \"month_reset\",\n"
                + "            \"period\": \"period\",\n"
                + "            \"value\": 1\n"
                + "          }\n"
                + "        ],\n"
                + "        \"name\": \"name\",\n"
                + "        \"payment_methods\": [\n"
                + "          {\n"
                + "            \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"customer_external_id\": \"customer_external_id\",\n"
                + "            \"environment_id\": \"environment_id\",\n"
                + "            \"external_id\": \"external_id\",\n"
                + "            \"id\": \"id\",\n"
                + "            \"payment_method_type\": \"payment_method_type\",\n"
                + "            \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "          }\n"
                + "        ],\n"
                + "        \"plans\": [\n"
                + "          {\n"
                + "            \"id\": \"id\",\n"
                + "            \"name\": \"name\"\n"
                + "          }\n"
                + "        ],\n"
                + "        \"rules\": [\n"
                + "          {\n"
                + "            \"account_id\": \"account_id\",\n"
                + "            \"condition_groups\": [\n"
                + "              {\n"
                + "                \"conditions\": [\n"
                + "                  {\n"
                + "                    \"account_id\": \"account_id\",\n"
                + "                    \"condition_type\": \"condition_type\",\n"
                + "                    \"environment_id\": \"environment_id\",\n"
                + "                    \"id\": \"id\",\n"
                + "                    \"operator\": \"operator\",\n"
                + "                    \"resource_ids\": [\n"
                + "                      \"resource_ids\"\n"
                + "                    ],\n"
                + "                    \"trait_value\": \"trait_value\"\n"
                + "                  }\n"
                + "                ]\n"
                + "              }\n"
                + "            ],\n"
                + "            \"conditions\": [\n"
                + "              {\n"
                + "                \"account_id\": \"account_id\",\n"
                + "                \"condition_type\": \"condition_type\",\n"
                + "                \"environment_id\": \"environment_id\",\n"
                + "                \"id\": \"id\",\n"
                + "                \"operator\": \"operator\",\n"
                + "                \"resource_ids\": [\n"
                + "                  \"resource_ids\"\n"
                + "                ],\n"
                + "                \"trait_value\": \"trait_value\"\n"
                + "              }\n"
                + "            ],\n"
                + "            \"environment_id\": \"environment_id\",\n"
                + "            \"id\": \"id\",\n"
                + "            \"name\": \"name\",\n"
                + "            \"priority\": 1,\n"
                + "            \"rule_type\": \"rule_type\",\n"
                + "            \"value\": true\n"
                + "          }\n"
                + "        ],\n"
                + "        \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"user_count\": 1\n"
                + "      },\n"
                + "      \"company_override\": {\n"
                + "        \"company_id\": \"company_id\",\n"
                + "        \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"environment_id\": \"environment_id\",\n"
                + "        \"feature_id\": \"feature_id\",\n"
                + "        \"id\": \"id\",\n"
                + "        \"notes\": [\n"
                + "          {\n"
                + "            \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"external_user_id\": \"external_user_id\",\n"
                + "            \"external_user_name\": \"external_user_name\",\n"
                + "            \"id\": \"id\",\n"
                + "            \"note\": \"note\",\n"
                + "            \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "          }\n"
                + "        ],\n"
                + "        \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"value_type\": \"value_type\"\n"
                + "      },\n"
                + "      \"credit_consumption_rate\": 1.1,\n"
                + "      \"credit_grant_counts\": {\n"
                + "        \"key\": 1.1\n"
                + "      },\n"
                + "      \"credit_grant_details\": [\n"
                + "        {\n"
                + "          \"grant_reason\": \"free\",\n"
                + "          \"quantity\": 1.1\n"
                + "        }\n"
                + "      ],\n"
                + "      \"credit_grant_reason\": \"free\",\n"
                + "      \"credit_remaining\": 1.1,\n"
                + "      \"credit_total\": 1.1,\n"
                + "      \"credit_type_icon\": \"credit_type_icon\",\n"
                + "      \"credit_used\": 1.1,\n"
                + "      \"effective_limit\": 1,\n"
                + "      \"effective_price\": 1.1,\n"
                + "      \"entitlement_expiration_date\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"entitlement_id\": \"entitlement_id\",\n"
                + "      \"entitlement_source\": \"entitlement_source\",\n"
                + "      \"entitlement_type\": \"entitlement_type\",\n"
                + "      \"feature\": {\n"
                + "        \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"description\": \"description\",\n"
                + "        \"feature_type\": \"feature_type\",\n"
                + "        \"flags\": [\n"
                + "          {\n"
                + "            \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"default_value\": true,\n"
                + "            \"description\": \"description\",\n"
                + "            \"flag_type\": \"flag_type\",\n"
                + "            \"id\": \"id\",\n"
                + "            \"key\": \"key\",\n"
                + "            \"name\": \"name\",\n"
                + "            \"rules\": [\n"
                + "              {\n"
                + "                \"condition_groups\": [\n"
                + "                  {\n"
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
                + "                    \"rule_id\": \"rule_id\",\n"
                + "                    \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "                  }\n"
                + "                ],\n"
                + "                \"conditions\": [\n"
                + "                  {\n"
                + "                    \"condition_type\": \"condition_type\",\n"
                + "                    \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "                    \"environment_id\": \"environment_id\",\n"
                + "                    \"id\": \"id\",\n"
                + "                    \"operator\": \"operator\",\n"
                + "                    \"resource_ids\": [\n"
                + "                      \"resource_ids\"\n"
                + "                    ],\n"
                + "                    \"resources\": [\n"
                + "                      {\n"
                + "                        \"id\": \"id\",\n"
                + "                        \"name\": \"name\"\n"
                + "                      }\n"
                + "                    ],\n"
                + "                    \"rule_id\": \"rule_id\",\n"
                + "                    \"trait_value\": \"trait_value\",\n"
                + "                    \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "                  }\n"
                + "                ],\n"
                + "                \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "                \"environment_id\": \"environment_id\",\n"
                + "                \"id\": \"id\",\n"
                + "                \"name\": \"name\",\n"
                + "                \"priority\": 1,\n"
                + "                \"rule_type\": \"rule_type\",\n"
                + "                \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "                \"value\": true\n"
                + "              }\n"
                + "            ],\n"
                + "            \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "          }\n"
                + "        ],\n"
                + "        \"icon\": \"icon\",\n"
                + "        \"id\": \"id\",\n"
                + "        \"name\": \"name\",\n"
                + "        \"plans\": [\n"
                + "          {\n"
                + "            \"id\": \"id\",\n"
                + "            \"name\": \"name\"\n"
                + "          }\n"
                + "        ],\n"
                + "        \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "      },\n"
                + "      \"has_valid_allocation\": true,\n"
                + "      \"is_unlimited\": true,\n"
                + "      \"metric_reset_at\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"month_reset\": \"month_reset\",\n"
                + "      \"monthly_usage_based_price\": {\n"
                + "        \"billing_scheme\": \"billing_scheme\",\n"
                + "        \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"currency\": \"currency\",\n"
                + "        \"id\": \"id\",\n"
                + "        \"interval\": \"interval\",\n"
                + "        \"is_active\": true,\n"
                + "        \"package_size\": 1,\n"
                + "        \"price\": 1,\n"
                + "        \"price_external_id\": \"price_external_id\",\n"
                + "        \"price_id\": \"price_id\",\n"
                + "        \"price_tier\": [\n"
                + "          {}\n"
                + "        ],\n"
                + "        \"product_external_id\": \"product_external_id\",\n"
                + "        \"product_id\": \"product_id\",\n"
                + "        \"product_name\": \"product_name\",\n"
                + "        \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"usage_type\": \"usage_type\"\n"
                + "      },\n"
                + "      \"overuse\": 1,\n"
                + "      \"percent_used\": 1.1,\n"
                + "      \"period\": \"period\",\n"
                + "      \"plan\": {\n"
                + "        \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"description\": \"description\",\n"
                + "        \"icon\": \"icon\",\n"
                + "        \"id\": \"id\",\n"
                + "        \"name\": \"name\",\n"
                + "        \"plan_type\": \"plan_type\",\n"
                + "        \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "      },\n"
                + "      \"plan_entitlement\": {\n"
                + "        \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"environment_id\": \"environment_id\",\n"
                + "        \"feature_id\": \"feature_id\",\n"
                + "        \"id\": \"id\",\n"
                + "        \"plan_id\": \"plan_id\",\n"
                + "        \"rule_id\": \"rule_id\",\n"
                + "        \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"value_type\": \"value_type\"\n"
                + "      },\n"
                + "      \"price_behavior\": \"price_behavior\",\n"
                + "      \"soft_limit\": 1,\n"
                + "      \"usage\": 1,\n"
                + "      \"yearly_usage_based_price\": {\n"
                + "        \"billing_scheme\": \"billing_scheme\",\n"
                + "        \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"currency\": \"currency\",\n"
                + "        \"id\": \"id\",\n"
                + "        \"interval\": \"interval\",\n"
                + "        \"is_active\": true,\n"
                + "        \"package_size\": 1,\n"
                + "        \"price\": 1,\n"
                + "        \"price_external_id\": \"price_external_id\",\n"
                + "        \"price_id\": \"price_id\",\n"
                + "        \"price_tier\": [\n"
                + "          {}\n"
                + "        ],\n"
                + "        \"product_external_id\": \"product_external_id\",\n"
                + "        \"product_id\": \"product_id\",\n"
                + "        \"product_name\": \"product_name\",\n"
                + "        \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"usage_type\": \"usage_type\"\n"
                + "      }\n"
                + "    }\n"
                + "  ],\n"
                + "  \"params\": {\n"
                + "    \"feature_id\": \"feature_id\",\n"
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
    public void testCountFeatureCompanies() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":{\"count\":1},\"params\":{\"feature_id\":\"feature_id\",\"limit\":1,\"offset\":1,\"q\":\"q\"}}"));
        CountFeatureCompaniesResponse response = client.entitlements()
                .countFeatureCompanies(CountFeatureCompaniesRequest.builder()
                        .featureId("feature_id")
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
                + "    \"feature_id\": \"feature_id\",\n"
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
    public void testListFeatureUsage() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":[{\"access\":true,\"allocation\":1,\"allocation_type\":\"boolean\",\"company_override\":{\"company_id\":\"company_id\",\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"feature_id\":\"feature_id\",\"id\":\"id\",\"notes\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"external_user_id\":\"external_user_id\",\"external_user_name\":\"external_user_name\",\"id\":\"id\",\"note\":\"note\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"updated_at\":\"2024-01-15T09:30:00Z\",\"value_type\":\"value_type\"},\"credit_consumption_rate\":1.1,\"credit_grant_counts\":{\"key\":1.1},\"credit_grant_details\":[{\"grant_reason\":\"free\",\"quantity\":1.1}],\"credit_grant_reason\":\"free\",\"credit_remaining\":1.1,\"credit_total\":1.1,\"credit_type_icon\":\"credit_type_icon\",\"credit_used\":1.1,\"effective_limit\":1,\"effective_price\":1.1,\"entitlement_expiration_date\":\"2024-01-15T09:30:00Z\",\"entitlement_id\":\"entitlement_id\",\"entitlement_source\":\"entitlement_source\",\"entitlement_type\":\"entitlement_type\",\"feature\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"description\":\"description\",\"feature_type\":\"feature_type\",\"flags\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"default_value\":true,\"description\":\"description\",\"flag_type\":\"flag_type\",\"id\":\"id\",\"key\":\"key\",\"name\":\"name\",\"rules\":[{\"condition_groups\":[{\"conditions\":[{\"condition_type\":\"condition_type\",\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"operator\":\"operator\",\"resource_ids\":[\"resource_ids\"],\"resources\":[{\"id\":\"id\",\"name\":\"name\"}],\"rule_id\":\"rule_id\",\"trait_value\":\"trait_value\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"rule_id\":\"rule_id\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"conditions\":[{\"condition_type\":\"condition_type\",\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"operator\":\"operator\",\"resource_ids\":[\"resource_ids\"],\"resources\":[{\"id\":\"id\",\"name\":\"name\"}],\"rule_id\":\"rule_id\",\"trait_value\":\"trait_value\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"name\":\"name\",\"priority\":1,\"rule_type\":\"rule_type\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"value\":true}],\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"icon\":\"icon\",\"id\":\"id\",\"name\":\"name\",\"plans\":[{\"id\":\"id\",\"name\":\"name\"}],\"updated_at\":\"2024-01-15T09:30:00Z\"},\"has_valid_allocation\":true,\"is_unlimited\":true,\"metric_reset_at\":\"2024-01-15T09:30:00Z\",\"month_reset\":\"month_reset\",\"monthly_usage_based_price\":{\"billing_scheme\":\"billing_scheme\",\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"id\":\"id\",\"interval\":\"interval\",\"is_active\":true,\"package_size\":1,\"price\":1,\"price_external_id\":\"price_external_id\",\"price_id\":\"price_id\",\"price_tier\":[{}],\"product_external_id\":\"product_external_id\",\"product_id\":\"product_id\",\"product_name\":\"product_name\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"usage_type\":\"usage_type\"},\"overuse\":1,\"percent_used\":1.1,\"period\":\"period\",\"plan\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"description\":\"description\",\"icon\":\"icon\",\"id\":\"id\",\"name\":\"name\",\"plan_type\":\"plan_type\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"plan_entitlement\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"feature_id\":\"feature_id\",\"id\":\"id\",\"plan_id\":\"plan_id\",\"rule_id\":\"rule_id\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"value_type\":\"value_type\"},\"price_behavior\":\"price_behavior\",\"soft_limit\":1,\"usage\":1,\"yearly_usage_based_price\":{\"billing_scheme\":\"billing_scheme\",\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"id\":\"id\",\"interval\":\"interval\",\"is_active\":true,\"package_size\":1,\"price\":1,\"price_external_id\":\"price_external_id\",\"price_id\":\"price_id\",\"price_tier\":[{}],\"product_external_id\":\"product_external_id\",\"product_id\":\"product_id\",\"product_name\":\"product_name\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"usage_type\":\"usage_type\"}}],\"params\":{\"company_id\":\"company_id\",\"company_keys\":{\"key\":\"value\"},\"feature_ids\":[\"feature_ids\"],\"limit\":1,\"offset\":1,\"q\":\"q\",\"without_negative_entitlements\":true}}"));
        ListFeatureUsageResponse response = client.entitlements()
                .listFeatureUsage(ListFeatureUsageRequest.builder()
                        .companyId("company_id")
                        .q("q")
                        .withoutNegativeEntitlements(true)
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
                + "      \"access\": true,\n"
                + "      \"allocation\": 1,\n"
                + "      \"allocation_type\": \"boolean\",\n"
                + "      \"company_override\": {\n"
                + "        \"company_id\": \"company_id\",\n"
                + "        \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"environment_id\": \"environment_id\",\n"
                + "        \"feature_id\": \"feature_id\",\n"
                + "        \"id\": \"id\",\n"
                + "        \"notes\": [\n"
                + "          {\n"
                + "            \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"external_user_id\": \"external_user_id\",\n"
                + "            \"external_user_name\": \"external_user_name\",\n"
                + "            \"id\": \"id\",\n"
                + "            \"note\": \"note\",\n"
                + "            \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "          }\n"
                + "        ],\n"
                + "        \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"value_type\": \"value_type\"\n"
                + "      },\n"
                + "      \"credit_consumption_rate\": 1.1,\n"
                + "      \"credit_grant_counts\": {\n"
                + "        \"key\": 1.1\n"
                + "      },\n"
                + "      \"credit_grant_details\": [\n"
                + "        {\n"
                + "          \"grant_reason\": \"free\",\n"
                + "          \"quantity\": 1.1\n"
                + "        }\n"
                + "      ],\n"
                + "      \"credit_grant_reason\": \"free\",\n"
                + "      \"credit_remaining\": 1.1,\n"
                + "      \"credit_total\": 1.1,\n"
                + "      \"credit_type_icon\": \"credit_type_icon\",\n"
                + "      \"credit_used\": 1.1,\n"
                + "      \"effective_limit\": 1,\n"
                + "      \"effective_price\": 1.1,\n"
                + "      \"entitlement_expiration_date\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"entitlement_id\": \"entitlement_id\",\n"
                + "      \"entitlement_source\": \"entitlement_source\",\n"
                + "      \"entitlement_type\": \"entitlement_type\",\n"
                + "      \"feature\": {\n"
                + "        \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"description\": \"description\",\n"
                + "        \"feature_type\": \"feature_type\",\n"
                + "        \"flags\": [\n"
                + "          {\n"
                + "            \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"default_value\": true,\n"
                + "            \"description\": \"description\",\n"
                + "            \"flag_type\": \"flag_type\",\n"
                + "            \"id\": \"id\",\n"
                + "            \"key\": \"key\",\n"
                + "            \"name\": \"name\",\n"
                + "            \"rules\": [\n"
                + "              {\n"
                + "                \"condition_groups\": [\n"
                + "                  {\n"
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
                + "                    \"rule_id\": \"rule_id\",\n"
                + "                    \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "                  }\n"
                + "                ],\n"
                + "                \"conditions\": [\n"
                + "                  {\n"
                + "                    \"condition_type\": \"condition_type\",\n"
                + "                    \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "                    \"environment_id\": \"environment_id\",\n"
                + "                    \"id\": \"id\",\n"
                + "                    \"operator\": \"operator\",\n"
                + "                    \"resource_ids\": [\n"
                + "                      \"resource_ids\"\n"
                + "                    ],\n"
                + "                    \"resources\": [\n"
                + "                      {\n"
                + "                        \"id\": \"id\",\n"
                + "                        \"name\": \"name\"\n"
                + "                      }\n"
                + "                    ],\n"
                + "                    \"rule_id\": \"rule_id\",\n"
                + "                    \"trait_value\": \"trait_value\",\n"
                + "                    \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "                  }\n"
                + "                ],\n"
                + "                \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "                \"environment_id\": \"environment_id\",\n"
                + "                \"id\": \"id\",\n"
                + "                \"name\": \"name\",\n"
                + "                \"priority\": 1,\n"
                + "                \"rule_type\": \"rule_type\",\n"
                + "                \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "                \"value\": true\n"
                + "              }\n"
                + "            ],\n"
                + "            \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "          }\n"
                + "        ],\n"
                + "        \"icon\": \"icon\",\n"
                + "        \"id\": \"id\",\n"
                + "        \"name\": \"name\",\n"
                + "        \"plans\": [\n"
                + "          {\n"
                + "            \"id\": \"id\",\n"
                + "            \"name\": \"name\"\n"
                + "          }\n"
                + "        ],\n"
                + "        \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "      },\n"
                + "      \"has_valid_allocation\": true,\n"
                + "      \"is_unlimited\": true,\n"
                + "      \"metric_reset_at\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"month_reset\": \"month_reset\",\n"
                + "      \"monthly_usage_based_price\": {\n"
                + "        \"billing_scheme\": \"billing_scheme\",\n"
                + "        \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"currency\": \"currency\",\n"
                + "        \"id\": \"id\",\n"
                + "        \"interval\": \"interval\",\n"
                + "        \"is_active\": true,\n"
                + "        \"package_size\": 1,\n"
                + "        \"price\": 1,\n"
                + "        \"price_external_id\": \"price_external_id\",\n"
                + "        \"price_id\": \"price_id\",\n"
                + "        \"price_tier\": [\n"
                + "          {}\n"
                + "        ],\n"
                + "        \"product_external_id\": \"product_external_id\",\n"
                + "        \"product_id\": \"product_id\",\n"
                + "        \"product_name\": \"product_name\",\n"
                + "        \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"usage_type\": \"usage_type\"\n"
                + "      },\n"
                + "      \"overuse\": 1,\n"
                + "      \"percent_used\": 1.1,\n"
                + "      \"period\": \"period\",\n"
                + "      \"plan\": {\n"
                + "        \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"description\": \"description\",\n"
                + "        \"icon\": \"icon\",\n"
                + "        \"id\": \"id\",\n"
                + "        \"name\": \"name\",\n"
                + "        \"plan_type\": \"plan_type\",\n"
                + "        \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "      },\n"
                + "      \"plan_entitlement\": {\n"
                + "        \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"environment_id\": \"environment_id\",\n"
                + "        \"feature_id\": \"feature_id\",\n"
                + "        \"id\": \"id\",\n"
                + "        \"plan_id\": \"plan_id\",\n"
                + "        \"rule_id\": \"rule_id\",\n"
                + "        \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"value_type\": \"value_type\"\n"
                + "      },\n"
                + "      \"price_behavior\": \"price_behavior\",\n"
                + "      \"soft_limit\": 1,\n"
                + "      \"usage\": 1,\n"
                + "      \"yearly_usage_based_price\": {\n"
                + "        \"billing_scheme\": \"billing_scheme\",\n"
                + "        \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"currency\": \"currency\",\n"
                + "        \"id\": \"id\",\n"
                + "        \"interval\": \"interval\",\n"
                + "        \"is_active\": true,\n"
                + "        \"package_size\": 1,\n"
                + "        \"price\": 1,\n"
                + "        \"price_external_id\": \"price_external_id\",\n"
                + "        \"price_id\": \"price_id\",\n"
                + "        \"price_tier\": [\n"
                + "          {}\n"
                + "        ],\n"
                + "        \"product_external_id\": \"product_external_id\",\n"
                + "        \"product_id\": \"product_id\",\n"
                + "        \"product_name\": \"product_name\",\n"
                + "        \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"usage_type\": \"usage_type\"\n"
                + "      }\n"
                + "    }\n"
                + "  ],\n"
                + "  \"params\": {\n"
                + "    \"company_id\": \"company_id\",\n"
                + "    \"company_keys\": {\n"
                + "      \"key\": \"value\"\n"
                + "    },\n"
                + "    \"feature_ids\": [\n"
                + "      \"feature_ids\"\n"
                + "    ],\n"
                + "    \"limit\": 1,\n"
                + "    \"offset\": 1,\n"
                + "    \"q\": \"q\",\n"
                + "    \"without_negative_entitlements\": true\n"
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
    public void testCountFeatureUsage() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":{\"count\":1},\"params\":{\"company_id\":\"company_id\",\"company_keys\":{\"key\":\"value\"},\"feature_ids\":[\"feature_ids\"],\"limit\":1,\"offset\":1,\"q\":\"q\",\"without_negative_entitlements\":true}}"));
        CountFeatureUsageResponse response = client.entitlements()
                .countFeatureUsage(CountFeatureUsageRequest.builder()
                        .companyId("company_id")
                        .q("q")
                        .withoutNegativeEntitlements(true)
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
                + "    \"company_id\": \"company_id\",\n"
                + "    \"company_keys\": {\n"
                + "      \"key\": \"value\"\n"
                + "    },\n"
                + "    \"feature_ids\": [\n"
                + "      \"feature_ids\"\n"
                + "    ],\n"
                + "    \"limit\": 1,\n"
                + "    \"offset\": 1,\n"
                + "    \"q\": \"q\",\n"
                + "    \"without_negative_entitlements\": true\n"
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
    public void testListFeatureUsers() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":[{\"access\":true,\"allocation\":1,\"allocation_type\":\"boolean\",\"company\":{\"add_ons\":[{\"id\":\"id\",\"name\":\"name\"}],\"billing_subscriptions\":[{\"cancel_at_period_end\":true,\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"customer_external_id\":\"customer_external_id\",\"discounts\":[{\"coupon_id\":\"coupon_id\",\"coupon_name\":\"coupon_name\",\"discount_external_id\":\"discount_external_id\",\"duration\":\"duration\",\"is_active\":true,\"started_at\":\"2024-01-15T09:30:00Z\",\"subscription_external_id\":\"subscription_external_id\"}],\"id\":\"id\",\"interval\":\"interval\",\"period_end\":1,\"period_start\":1,\"products\":[{\"billing_scheme\":\"billing_scheme\",\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"id\":\"id\",\"interval\":\"interval\",\"name\":\"name\",\"package_size\":1,\"price\":1,\"price_external_id\":\"price_external_id\",\"price_id\":\"price_id\",\"price_tier\":[{}],\"quantity\":1.1,\"subscription_id\":\"subscription_id\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"usage_type\":\"usage_type\"}],\"status\":\"status\",\"subscription_external_id\":\"subscription_external_id\",\"total_price\":1}],\"created_at\":\"2024-01-15T09:30:00Z\",\"entity_traits\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"definition_id\":\"definition_id\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"value\":\"value\"}],\"environment_id\":\"environment_id\",\"id\":\"id\",\"keys\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"definition_id\":\"definition_id\",\"entity_id\":\"entity_id\",\"entity_type\":\"entity_type\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"key\":\"key\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"value\":\"value\"}],\"metrics\":[{\"account_id\":\"account_id\",\"captured_at_max\":\"2024-01-15T09:30:00Z\",\"captured_at_min\":\"2024-01-15T09:30:00Z\",\"company_id\":\"company_id\",\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"event_subtype\":\"event_subtype\",\"month_reset\":\"month_reset\",\"period\":\"period\",\"value\":1}],\"name\":\"name\",\"payment_methods\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"customer_external_id\":\"customer_external_id\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"id\":\"id\",\"payment_method_type\":\"payment_method_type\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"plans\":[{\"id\":\"id\",\"name\":\"name\"}],\"rules\":[{\"account_id\":\"account_id\",\"condition_groups\":[{\"conditions\":[{\"account_id\":\"account_id\",\"condition_type\":\"condition_type\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"operator\":\"operator\",\"resource_ids\":[\"resource_ids\"],\"trait_value\":\"trait_value\"}]}],\"conditions\":[{\"account_id\":\"account_id\",\"condition_type\":\"condition_type\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"operator\":\"operator\",\"resource_ids\":[\"resource_ids\"],\"trait_value\":\"trait_value\"}],\"environment_id\":\"environment_id\",\"id\":\"id\",\"name\":\"name\",\"priority\":1,\"rule_type\":\"rule_type\",\"value\":true}],\"updated_at\":\"2024-01-15T09:30:00Z\",\"user_count\":1},\"entitlement_id\":\"entitlement_id\",\"entitlement_type\":\"entitlement_type\",\"feature\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"description\":\"description\",\"feature_type\":\"feature_type\",\"flags\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"default_value\":true,\"description\":\"description\",\"flag_type\":\"flag_type\",\"id\":\"id\",\"key\":\"key\",\"name\":\"name\",\"rules\":[{\"condition_groups\":[{\"conditions\":[{\"condition_type\":\"condition_type\",\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"operator\":\"operator\",\"resource_ids\":[\"resource_ids\"],\"resources\":[{\"id\":\"id\",\"name\":\"name\"}],\"rule_id\":\"rule_id\",\"trait_value\":\"trait_value\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"rule_id\":\"rule_id\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"conditions\":[{\"condition_type\":\"condition_type\",\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"operator\":\"operator\",\"resource_ids\":[\"resource_ids\"],\"resources\":[{\"id\":\"id\",\"name\":\"name\"}],\"rule_id\":\"rule_id\",\"trait_value\":\"trait_value\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"name\":\"name\",\"priority\":1,\"rule_type\":\"rule_type\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"value\":true}],\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"icon\":\"icon\",\"id\":\"id\",\"name\":\"name\",\"plans\":[{\"id\":\"id\",\"name\":\"name\"}],\"updated_at\":\"2024-01-15T09:30:00Z\"},\"metric_reset_at\":\"2024-01-15T09:30:00Z\",\"month_reset\":\"month_reset\",\"period\":\"period\",\"plan\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"description\":\"description\",\"icon\":\"icon\",\"id\":\"id\",\"name\":\"name\",\"plan_type\":\"plan_type\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"usage\":1,\"user\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"name\":\"name\",\"updated_at\":\"2024-01-15T09:30:00Z\"}}],\"params\":{\"feature_id\":\"feature_id\",\"limit\":1,\"offset\":1,\"q\":\"q\"}}"));
        ListFeatureUsersResponse response = client.entitlements()
                .listFeatureUsers(ListFeatureUsersRequest.builder()
                        .featureId("feature_id")
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
                + "      \"access\": true,\n"
                + "      \"allocation\": 1,\n"
                + "      \"allocation_type\": \"boolean\",\n"
                + "      \"company\": {\n"
                + "        \"add_ons\": [\n"
                + "          {\n"
                + "            \"id\": \"id\",\n"
                + "            \"name\": \"name\"\n"
                + "          }\n"
                + "        ],\n"
                + "        \"billing_subscriptions\": [\n"
                + "          {\n"
                + "            \"cancel_at_period_end\": true,\n"
                + "            \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"currency\": \"currency\",\n"
                + "            \"customer_external_id\": \"customer_external_id\",\n"
                + "            \"discounts\": [\n"
                + "              {\n"
                + "                \"coupon_id\": \"coupon_id\",\n"
                + "                \"coupon_name\": \"coupon_name\",\n"
                + "                \"discount_external_id\": \"discount_external_id\",\n"
                + "                \"duration\": \"duration\",\n"
                + "                \"is_active\": true,\n"
                + "                \"started_at\": \"2024-01-15T09:30:00Z\",\n"
                + "                \"subscription_external_id\": \"subscription_external_id\"\n"
                + "              }\n"
                + "            ],\n"
                + "            \"id\": \"id\",\n"
                + "            \"interval\": \"interval\",\n"
                + "            \"period_end\": 1,\n"
                + "            \"period_start\": 1,\n"
                + "            \"products\": [\n"
                + "              {\n"
                + "                \"billing_scheme\": \"billing_scheme\",\n"
                + "                \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "                \"currency\": \"currency\",\n"
                + "                \"environment_id\": \"environment_id\",\n"
                + "                \"external_id\": \"external_id\",\n"
                + "                \"id\": \"id\",\n"
                + "                \"interval\": \"interval\",\n"
                + "                \"name\": \"name\",\n"
                + "                \"package_size\": 1,\n"
                + "                \"price\": 1,\n"
                + "                \"price_external_id\": \"price_external_id\",\n"
                + "                \"price_id\": \"price_id\",\n"
                + "                \"price_tier\": [\n"
                + "                  {}\n"
                + "                ],\n"
                + "                \"quantity\": 1.1,\n"
                + "                \"subscription_id\": \"subscription_id\",\n"
                + "                \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "                \"usage_type\": \"usage_type\"\n"
                + "              }\n"
                + "            ],\n"
                + "            \"status\": \"status\",\n"
                + "            \"subscription_external_id\": \"subscription_external_id\",\n"
                + "            \"total_price\": 1\n"
                + "          }\n"
                + "        ],\n"
                + "        \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"entity_traits\": [\n"
                + "          {\n"
                + "            \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"definition_id\": \"definition_id\",\n"
                + "            \"environment_id\": \"environment_id\",\n"
                + "            \"id\": \"id\",\n"
                + "            \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"value\": \"value\"\n"
                + "          }\n"
                + "        ],\n"
                + "        \"environment_id\": \"environment_id\",\n"
                + "        \"id\": \"id\",\n"
                + "        \"keys\": [\n"
                + "          {\n"
                + "            \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"definition_id\": \"definition_id\",\n"
                + "            \"entity_id\": \"entity_id\",\n"
                + "            \"entity_type\": \"entity_type\",\n"
                + "            \"environment_id\": \"environment_id\",\n"
                + "            \"id\": \"id\",\n"
                + "            \"key\": \"key\",\n"
                + "            \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"value\": \"value\"\n"
                + "          }\n"
                + "        ],\n"
                + "        \"metrics\": [\n"
                + "          {\n"
                + "            \"account_id\": \"account_id\",\n"
                + "            \"captured_at_max\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"captured_at_min\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"company_id\": \"company_id\",\n"
                + "            \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"environment_id\": \"environment_id\",\n"
                + "            \"event_subtype\": \"event_subtype\",\n"
                + "            \"month_reset\": \"month_reset\",\n"
                + "            \"period\": \"period\",\n"
                + "            \"value\": 1\n"
                + "          }\n"
                + "        ],\n"
                + "        \"name\": \"name\",\n"
                + "        \"payment_methods\": [\n"
                + "          {\n"
                + "            \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"customer_external_id\": \"customer_external_id\",\n"
                + "            \"environment_id\": \"environment_id\",\n"
                + "            \"external_id\": \"external_id\",\n"
                + "            \"id\": \"id\",\n"
                + "            \"payment_method_type\": \"payment_method_type\",\n"
                + "            \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "          }\n"
                + "        ],\n"
                + "        \"plans\": [\n"
                + "          {\n"
                + "            \"id\": \"id\",\n"
                + "            \"name\": \"name\"\n"
                + "          }\n"
                + "        ],\n"
                + "        \"rules\": [\n"
                + "          {\n"
                + "            \"account_id\": \"account_id\",\n"
                + "            \"condition_groups\": [\n"
                + "              {\n"
                + "                \"conditions\": [\n"
                + "                  {\n"
                + "                    \"account_id\": \"account_id\",\n"
                + "                    \"condition_type\": \"condition_type\",\n"
                + "                    \"environment_id\": \"environment_id\",\n"
                + "                    \"id\": \"id\",\n"
                + "                    \"operator\": \"operator\",\n"
                + "                    \"resource_ids\": [\n"
                + "                      \"resource_ids\"\n"
                + "                    ],\n"
                + "                    \"trait_value\": \"trait_value\"\n"
                + "                  }\n"
                + "                ]\n"
                + "              }\n"
                + "            ],\n"
                + "            \"conditions\": [\n"
                + "              {\n"
                + "                \"account_id\": \"account_id\",\n"
                + "                \"condition_type\": \"condition_type\",\n"
                + "                \"environment_id\": \"environment_id\",\n"
                + "                \"id\": \"id\",\n"
                + "                \"operator\": \"operator\",\n"
                + "                \"resource_ids\": [\n"
                + "                  \"resource_ids\"\n"
                + "                ],\n"
                + "                \"trait_value\": \"trait_value\"\n"
                + "              }\n"
                + "            ],\n"
                + "            \"environment_id\": \"environment_id\",\n"
                + "            \"id\": \"id\",\n"
                + "            \"name\": \"name\",\n"
                + "            \"priority\": 1,\n"
                + "            \"rule_type\": \"rule_type\",\n"
                + "            \"value\": true\n"
                + "          }\n"
                + "        ],\n"
                + "        \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"user_count\": 1\n"
                + "      },\n"
                + "      \"entitlement_id\": \"entitlement_id\",\n"
                + "      \"entitlement_type\": \"entitlement_type\",\n"
                + "      \"feature\": {\n"
                + "        \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"description\": \"description\",\n"
                + "        \"feature_type\": \"feature_type\",\n"
                + "        \"flags\": [\n"
                + "          {\n"
                + "            \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"default_value\": true,\n"
                + "            \"description\": \"description\",\n"
                + "            \"flag_type\": \"flag_type\",\n"
                + "            \"id\": \"id\",\n"
                + "            \"key\": \"key\",\n"
                + "            \"name\": \"name\",\n"
                + "            \"rules\": [\n"
                + "              {\n"
                + "                \"condition_groups\": [\n"
                + "                  {\n"
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
                + "                    \"rule_id\": \"rule_id\",\n"
                + "                    \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "                  }\n"
                + "                ],\n"
                + "                \"conditions\": [\n"
                + "                  {\n"
                + "                    \"condition_type\": \"condition_type\",\n"
                + "                    \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "                    \"environment_id\": \"environment_id\",\n"
                + "                    \"id\": \"id\",\n"
                + "                    \"operator\": \"operator\",\n"
                + "                    \"resource_ids\": [\n"
                + "                      \"resource_ids\"\n"
                + "                    ],\n"
                + "                    \"resources\": [\n"
                + "                      {\n"
                + "                        \"id\": \"id\",\n"
                + "                        \"name\": \"name\"\n"
                + "                      }\n"
                + "                    ],\n"
                + "                    \"rule_id\": \"rule_id\",\n"
                + "                    \"trait_value\": \"trait_value\",\n"
                + "                    \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "                  }\n"
                + "                ],\n"
                + "                \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "                \"environment_id\": \"environment_id\",\n"
                + "                \"id\": \"id\",\n"
                + "                \"name\": \"name\",\n"
                + "                \"priority\": 1,\n"
                + "                \"rule_type\": \"rule_type\",\n"
                + "                \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "                \"value\": true\n"
                + "              }\n"
                + "            ],\n"
                + "            \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "          }\n"
                + "        ],\n"
                + "        \"icon\": \"icon\",\n"
                + "        \"id\": \"id\",\n"
                + "        \"name\": \"name\",\n"
                + "        \"plans\": [\n"
                + "          {\n"
                + "            \"id\": \"id\",\n"
                + "            \"name\": \"name\"\n"
                + "          }\n"
                + "        ],\n"
                + "        \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "      },\n"
                + "      \"metric_reset_at\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"month_reset\": \"month_reset\",\n"
                + "      \"period\": \"period\",\n"
                + "      \"plan\": {\n"
                + "        \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"description\": \"description\",\n"
                + "        \"icon\": \"icon\",\n"
                + "        \"id\": \"id\",\n"
                + "        \"name\": \"name\",\n"
                + "        \"plan_type\": \"plan_type\",\n"
                + "        \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "      },\n"
                + "      \"usage\": 1,\n"
                + "      \"user\": {\n"
                + "        \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"environment_id\": \"environment_id\",\n"
                + "        \"id\": \"id\",\n"
                + "        \"name\": \"name\",\n"
                + "        \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "      }\n"
                + "    }\n"
                + "  ],\n"
                + "  \"params\": {\n"
                + "    \"feature_id\": \"feature_id\",\n"
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
    public void testCountFeatureUsers() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":{\"count\":1},\"params\":{\"feature_id\":\"feature_id\",\"limit\":1,\"offset\":1,\"q\":\"q\"}}"));
        CountFeatureUsersResponse response = client.entitlements()
                .countFeatureUsers(CountFeatureUsersRequest.builder()
                        .featureId("feature_id")
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
                + "    \"feature_id\": \"feature_id\",\n"
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
    public void testListPlanEntitlements() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":[{\"billing_threshold\":1,\"consumption_rate\":1.1,\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"feature\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"description\":\"description\",\"feature_type\":\"feature_type\",\"icon\":\"icon\",\"id\":\"id\",\"name\":\"name\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"feature_id\":\"feature_id\",\"id\":\"id\",\"metered_monthly_price\":{\"billing_scheme\":\"billing_scheme\",\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"id\":\"id\",\"interval\":\"interval\",\"is_active\":true,\"package_size\":1,\"price\":1,\"price_external_id\":\"price_external_id\",\"price_id\":\"price_id\",\"price_tier\":[{}],\"product_external_id\":\"product_external_id\",\"product_id\":\"product_id\",\"product_name\":\"product_name\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"usage_type\":\"usage_type\"},\"metered_yearly_price\":{\"billing_scheme\":\"billing_scheme\",\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"id\":\"id\",\"interval\":\"interval\",\"is_active\":true,\"package_size\":1,\"price\":1,\"price_external_id\":\"price_external_id\",\"price_id\":\"price_id\",\"price_tier\":[{}],\"product_external_id\":\"product_external_id\",\"product_id\":\"product_id\",\"product_name\":\"product_name\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"usage_type\":\"usage_type\"},\"metric_period\":\"metric_period\",\"metric_period_month_reset\":\"metric_period_month_reset\",\"plan\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"description\":\"description\",\"icon\":\"icon\",\"id\":\"id\",\"name\":\"name\",\"plan_type\":\"plan_type\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"plan_id\":\"plan_id\",\"price_behavior\":\"price_behavior\",\"rule_id\":\"rule_id\",\"rule_id_usage_exceeded\":\"rule_id_usage_exceeded\",\"soft_limit\":1,\"updated_at\":\"2024-01-15T09:30:00Z\",\"usage_based_product\":{\"account_id\":\"account_id\",\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"is_active\":true,\"name\":\"name\",\"price\":1.1,\"product_id\":\"product_id\",\"quantity\":1.1,\"updated_at\":\"2024-01-15T09:30:00Z\"},\"value_bool\":true,\"value_credit\":{\"burn_strategy\":\"burn_strategy\",\"created_at\":\"2024-01-15T09:30:00Z\",\"default_expiry_unit\":\"default_expiry_unit\",\"default_rollover_policy\":\"default_rollover_policy\",\"description\":\"description\",\"id\":\"id\",\"name\":\"name\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"value_numeric\":1,\"value_trait\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"display_name\":\"display_name\",\"entity_type\":\"entity_type\",\"hierarchy\":[\"hierarchy\"],\"id\":\"id\",\"trait_type\":\"trait_type\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"value_trait_id\":\"value_trait_id\",\"value_type\":\"value_type\"}],\"params\":{\"feature_id\":\"feature_id\",\"feature_ids\":[\"feature_ids\"],\"ids\":[\"ids\"],\"limit\":1,\"offset\":1,\"plan_id\":\"plan_id\",\"plan_ids\":[\"plan_ids\"],\"q\":\"q\",\"with_metered_products\":true}}"));
        ListPlanEntitlementsResponse response = client.entitlements()
                .listPlanEntitlements(ListPlanEntitlementsRequest.builder()
                        .featureId("feature_id")
                        .planId("plan_id")
                        .q("q")
                        .withMeteredProducts(true)
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
                + "      \"billing_threshold\": 1,\n"
                + "      \"consumption_rate\": 1.1,\n"
                + "      \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"environment_id\": \"environment_id\",\n"
                + "      \"feature\": {\n"
                + "        \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"description\": \"description\",\n"
                + "        \"feature_type\": \"feature_type\",\n"
                + "        \"icon\": \"icon\",\n"
                + "        \"id\": \"id\",\n"
                + "        \"name\": \"name\",\n"
                + "        \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "      },\n"
                + "      \"feature_id\": \"feature_id\",\n"
                + "      \"id\": \"id\",\n"
                + "      \"metered_monthly_price\": {\n"
                + "        \"billing_scheme\": \"billing_scheme\",\n"
                + "        \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"currency\": \"currency\",\n"
                + "        \"id\": \"id\",\n"
                + "        \"interval\": \"interval\",\n"
                + "        \"is_active\": true,\n"
                + "        \"package_size\": 1,\n"
                + "        \"price\": 1,\n"
                + "        \"price_external_id\": \"price_external_id\",\n"
                + "        \"price_id\": \"price_id\",\n"
                + "        \"price_tier\": [\n"
                + "          {}\n"
                + "        ],\n"
                + "        \"product_external_id\": \"product_external_id\",\n"
                + "        \"product_id\": \"product_id\",\n"
                + "        \"product_name\": \"product_name\",\n"
                + "        \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"usage_type\": \"usage_type\"\n"
                + "      },\n"
                + "      \"metered_yearly_price\": {\n"
                + "        \"billing_scheme\": \"billing_scheme\",\n"
                + "        \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"currency\": \"currency\",\n"
                + "        \"id\": \"id\",\n"
                + "        \"interval\": \"interval\",\n"
                + "        \"is_active\": true,\n"
                + "        \"package_size\": 1,\n"
                + "        \"price\": 1,\n"
                + "        \"price_external_id\": \"price_external_id\",\n"
                + "        \"price_id\": \"price_id\",\n"
                + "        \"price_tier\": [\n"
                + "          {}\n"
                + "        ],\n"
                + "        \"product_external_id\": \"product_external_id\",\n"
                + "        \"product_id\": \"product_id\",\n"
                + "        \"product_name\": \"product_name\",\n"
                + "        \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"usage_type\": \"usage_type\"\n"
                + "      },\n"
                + "      \"metric_period\": \"metric_period\",\n"
                + "      \"metric_period_month_reset\": \"metric_period_month_reset\",\n"
                + "      \"plan\": {\n"
                + "        \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"description\": \"description\",\n"
                + "        \"icon\": \"icon\",\n"
                + "        \"id\": \"id\",\n"
                + "        \"name\": \"name\",\n"
                + "        \"plan_type\": \"plan_type\",\n"
                + "        \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "      },\n"
                + "      \"plan_id\": \"plan_id\",\n"
                + "      \"price_behavior\": \"price_behavior\",\n"
                + "      \"rule_id\": \"rule_id\",\n"
                + "      \"rule_id_usage_exceeded\": \"rule_id_usage_exceeded\",\n"
                + "      \"soft_limit\": 1,\n"
                + "      \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"usage_based_product\": {\n"
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
                + "      \"value_bool\": true,\n"
                + "      \"value_credit\": {\n"
                + "        \"burn_strategy\": \"burn_strategy\",\n"
                + "        \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"default_expiry_unit\": \"default_expiry_unit\",\n"
                + "        \"default_rollover_policy\": \"default_rollover_policy\",\n"
                + "        \"description\": \"description\",\n"
                + "        \"id\": \"id\",\n"
                + "        \"name\": \"name\",\n"
                + "        \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "      },\n"
                + "      \"value_numeric\": 1,\n"
                + "      \"value_trait\": {\n"
                + "        \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"display_name\": \"display_name\",\n"
                + "        \"entity_type\": \"entity_type\",\n"
                + "        \"hierarchy\": [\n"
                + "          \"hierarchy\"\n"
                + "        ],\n"
                + "        \"id\": \"id\",\n"
                + "        \"trait_type\": \"trait_type\",\n"
                + "        \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "      },\n"
                + "      \"value_trait_id\": \"value_trait_id\",\n"
                + "      \"value_type\": \"value_type\"\n"
                + "    }\n"
                + "  ],\n"
                + "  \"params\": {\n"
                + "    \"feature_id\": \"feature_id\",\n"
                + "    \"feature_ids\": [\n"
                + "      \"feature_ids\"\n"
                + "    ],\n"
                + "    \"ids\": [\n"
                + "      \"ids\"\n"
                + "    ],\n"
                + "    \"limit\": 1,\n"
                + "    \"offset\": 1,\n"
                + "    \"plan_id\": \"plan_id\",\n"
                + "    \"plan_ids\": [\n"
                + "      \"plan_ids\"\n"
                + "    ],\n"
                + "    \"q\": \"q\",\n"
                + "    \"with_metered_products\": true\n"
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
    public void testCreatePlanEntitlement() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":{\"billing_threshold\":1,\"consumption_rate\":1.1,\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"feature\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"description\":\"description\",\"event_subtype\":\"event_subtype\",\"feature_type\":\"feature_type\",\"icon\":\"icon\",\"id\":\"id\",\"lifecycle_phase\":\"lifecycle_phase\",\"maintainer_id\":\"maintainer_id\",\"name\":\"name\",\"plural_name\":\"plural_name\",\"singular_name\":\"singular_name\",\"trait_id\":\"trait_id\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"feature_id\":\"feature_id\",\"id\":\"id\",\"metered_monthly_price\":{\"billing_scheme\":\"billing_scheme\",\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"id\":\"id\",\"interval\":\"interval\",\"is_active\":true,\"meter_event_name\":\"meter_event_name\",\"meter_event_payload_key\":\"meter_event_payload_key\",\"meter_id\":\"meter_id\",\"package_size\":1,\"price\":1,\"price_decimal\":\"price_decimal\",\"price_external_id\":\"price_external_id\",\"price_id\":\"price_id\",\"price_tier\":[{}],\"product_external_id\":\"product_external_id\",\"product_id\":\"product_id\",\"product_name\":\"product_name\",\"tiers_mode\":\"tiers_mode\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"usage_type\":\"usage_type\"},\"metered_yearly_price\":{\"billing_scheme\":\"billing_scheme\",\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"id\":\"id\",\"interval\":\"interval\",\"is_active\":true,\"meter_event_name\":\"meter_event_name\",\"meter_event_payload_key\":\"meter_event_payload_key\",\"meter_id\":\"meter_id\",\"package_size\":1,\"price\":1,\"price_decimal\":\"price_decimal\",\"price_external_id\":\"price_external_id\",\"price_id\":\"price_id\",\"price_tier\":[{}],\"product_external_id\":\"product_external_id\",\"product_id\":\"product_id\",\"product_name\":\"product_name\",\"tiers_mode\":\"tiers_mode\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"usage_type\":\"usage_type\"},\"metric_period\":\"metric_period\",\"metric_period_month_reset\":\"metric_period_month_reset\",\"plan\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"description\":\"description\",\"icon\":\"icon\",\"id\":\"id\",\"name\":\"name\",\"plan_type\":\"plan_type\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"plan_id\":\"plan_id\",\"price_behavior\":\"price_behavior\",\"rule_id\":\"rule_id\",\"rule_id_usage_exceeded\":\"rule_id_usage_exceeded\",\"soft_limit\":1,\"updated_at\":\"2024-01-15T09:30:00Z\",\"usage_based_product\":{\"account_id\":\"account_id\",\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"is_active\":true,\"name\":\"name\",\"price\":1.1,\"price_decimal\":\"price_decimal\",\"product_id\":\"product_id\",\"quantity\":1.1,\"updated_at\":\"2024-01-15T09:30:00Z\"},\"value_bool\":true,\"value_credit\":{\"burn_strategy\":\"burn_strategy\",\"created_at\":\"2024-01-15T09:30:00Z\",\"default_expiry_unit\":\"default_expiry_unit\",\"default_expiry_unit_count\":1,\"default_rollover_policy\":\"default_rollover_policy\",\"description\":\"description\",\"icon\":\"icon\",\"id\":\"id\",\"name\":\"name\",\"plural_name\":\"plural_name\",\"price\":{\"currency\":\"currency\",\"external_price_id\":\"external_price_id\",\"id\":\"id\",\"interval\":\"interval\",\"price\":1,\"scheme\":\"scheme\"},\"product\":{\"account_id\":\"account_id\",\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"is_active\":true,\"name\":\"name\",\"price\":1.1,\"product_id\":\"product_id\",\"quantity\":1.1,\"updated_at\":\"2024-01-15T09:30:00Z\"},\"singular_name\":\"singular_name\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"value_numeric\":1,\"value_trait\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"display_name\":\"display_name\",\"entity_type\":\"entity_type\",\"hierarchy\":[\"hierarchy\"],\"id\":\"id\",\"trait_type\":\"trait_type\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"value_trait_id\":\"value_trait_id\",\"value_type\":\"value_type\"},\"params\":{\"key\":\"value\"}}"));
        CreatePlanEntitlementResponse response = client.entitlements()
                .createPlanEntitlement(CreatePlanEntitlementRequestBody.builder()
                        .featureId("feature_id")
                        .planId("plan_id")
                        .valueType(CreatePlanEntitlementRequestBodyValueType.BOOLEAN)
                        .build());
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("POST", request.getMethod());
        // Validate request body
        String actualRequestBody = request.getBody().readUtf8();
        String expectedRequestBody = ""
                + "{\n"
                + "  \"feature_id\": \"feature_id\",\n"
                + "  \"plan_id\": \"plan_id\",\n"
                + "  \"value_type\": \"boolean\"\n"
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
                + "    \"billing_threshold\": 1,\n"
                + "    \"consumption_rate\": 1.1,\n"
                + "    \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"environment_id\": \"environment_id\",\n"
                + "    \"feature\": {\n"
                + "      \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"description\": \"description\",\n"
                + "      \"event_subtype\": \"event_subtype\",\n"
                + "      \"feature_type\": \"feature_type\",\n"
                + "      \"icon\": \"icon\",\n"
                + "      \"id\": \"id\",\n"
                + "      \"lifecycle_phase\": \"lifecycle_phase\",\n"
                + "      \"maintainer_id\": \"maintainer_id\",\n"
                + "      \"name\": \"name\",\n"
                + "      \"plural_name\": \"plural_name\",\n"
                + "      \"singular_name\": \"singular_name\",\n"
                + "      \"trait_id\": \"trait_id\",\n"
                + "      \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "    },\n"
                + "    \"feature_id\": \"feature_id\",\n"
                + "    \"id\": \"id\",\n"
                + "    \"metered_monthly_price\": {\n"
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
                + "    },\n"
                + "    \"metered_yearly_price\": {\n"
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
                + "    },\n"
                + "    \"metric_period\": \"metric_period\",\n"
                + "    \"metric_period_month_reset\": \"metric_period_month_reset\",\n"
                + "    \"plan\": {\n"
                + "      \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"description\": \"description\",\n"
                + "      \"icon\": \"icon\",\n"
                + "      \"id\": \"id\",\n"
                + "      \"name\": \"name\",\n"
                + "      \"plan_type\": \"plan_type\",\n"
                + "      \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "    },\n"
                + "    \"plan_id\": \"plan_id\",\n"
                + "    \"price_behavior\": \"price_behavior\",\n"
                + "    \"rule_id\": \"rule_id\",\n"
                + "    \"rule_id_usage_exceeded\": \"rule_id_usage_exceeded\",\n"
                + "    \"soft_limit\": 1,\n"
                + "    \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"usage_based_product\": {\n"
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
                + "    \"value_bool\": true,\n"
                + "    \"value_credit\": {\n"
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
                + "    },\n"
                + "    \"value_numeric\": 1,\n"
                + "    \"value_trait\": {\n"
                + "      \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"display_name\": \"display_name\",\n"
                + "      \"entity_type\": \"entity_type\",\n"
                + "      \"hierarchy\": [\n"
                + "        \"hierarchy\"\n"
                + "      ],\n"
                + "      \"id\": \"id\",\n"
                + "      \"trait_type\": \"trait_type\",\n"
                + "      \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "    },\n"
                + "    \"value_trait_id\": \"value_trait_id\",\n"
                + "    \"value_type\": \"value_type\"\n"
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
    public void testGetPlanEntitlement() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":{\"billing_threshold\":1,\"consumption_rate\":1.1,\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"feature\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"description\":\"description\",\"event_subtype\":\"event_subtype\",\"feature_type\":\"feature_type\",\"icon\":\"icon\",\"id\":\"id\",\"lifecycle_phase\":\"lifecycle_phase\",\"maintainer_id\":\"maintainer_id\",\"name\":\"name\",\"plural_name\":\"plural_name\",\"singular_name\":\"singular_name\",\"trait_id\":\"trait_id\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"feature_id\":\"feature_id\",\"id\":\"id\",\"metered_monthly_price\":{\"billing_scheme\":\"billing_scheme\",\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"id\":\"id\",\"interval\":\"interval\",\"is_active\":true,\"meter_event_name\":\"meter_event_name\",\"meter_event_payload_key\":\"meter_event_payload_key\",\"meter_id\":\"meter_id\",\"package_size\":1,\"price\":1,\"price_decimal\":\"price_decimal\",\"price_external_id\":\"price_external_id\",\"price_id\":\"price_id\",\"price_tier\":[{}],\"product_external_id\":\"product_external_id\",\"product_id\":\"product_id\",\"product_name\":\"product_name\",\"tiers_mode\":\"tiers_mode\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"usage_type\":\"usage_type\"},\"metered_yearly_price\":{\"billing_scheme\":\"billing_scheme\",\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"id\":\"id\",\"interval\":\"interval\",\"is_active\":true,\"meter_event_name\":\"meter_event_name\",\"meter_event_payload_key\":\"meter_event_payload_key\",\"meter_id\":\"meter_id\",\"package_size\":1,\"price\":1,\"price_decimal\":\"price_decimal\",\"price_external_id\":\"price_external_id\",\"price_id\":\"price_id\",\"price_tier\":[{}],\"product_external_id\":\"product_external_id\",\"product_id\":\"product_id\",\"product_name\":\"product_name\",\"tiers_mode\":\"tiers_mode\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"usage_type\":\"usage_type\"},\"metric_period\":\"metric_period\",\"metric_period_month_reset\":\"metric_period_month_reset\",\"plan\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"description\":\"description\",\"icon\":\"icon\",\"id\":\"id\",\"name\":\"name\",\"plan_type\":\"plan_type\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"plan_id\":\"plan_id\",\"price_behavior\":\"price_behavior\",\"rule_id\":\"rule_id\",\"rule_id_usage_exceeded\":\"rule_id_usage_exceeded\",\"soft_limit\":1,\"updated_at\":\"2024-01-15T09:30:00Z\",\"usage_based_product\":{\"account_id\":\"account_id\",\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"is_active\":true,\"name\":\"name\",\"price\":1.1,\"price_decimal\":\"price_decimal\",\"product_id\":\"product_id\",\"quantity\":1.1,\"updated_at\":\"2024-01-15T09:30:00Z\"},\"value_bool\":true,\"value_credit\":{\"burn_strategy\":\"burn_strategy\",\"created_at\":\"2024-01-15T09:30:00Z\",\"default_expiry_unit\":\"default_expiry_unit\",\"default_expiry_unit_count\":1,\"default_rollover_policy\":\"default_rollover_policy\",\"description\":\"description\",\"icon\":\"icon\",\"id\":\"id\",\"name\":\"name\",\"plural_name\":\"plural_name\",\"price\":{\"currency\":\"currency\",\"external_price_id\":\"external_price_id\",\"id\":\"id\",\"interval\":\"interval\",\"price\":1,\"scheme\":\"scheme\"},\"product\":{\"account_id\":\"account_id\",\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"is_active\":true,\"name\":\"name\",\"price\":1.1,\"product_id\":\"product_id\",\"quantity\":1.1,\"updated_at\":\"2024-01-15T09:30:00Z\"},\"singular_name\":\"singular_name\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"value_numeric\":1,\"value_trait\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"display_name\":\"display_name\",\"entity_type\":\"entity_type\",\"hierarchy\":[\"hierarchy\"],\"id\":\"id\",\"trait_type\":\"trait_type\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"value_trait_id\":\"value_trait_id\",\"value_type\":\"value_type\"},\"params\":{\"key\":\"value\"}}"));
        GetPlanEntitlementResponse response = client.entitlements().getPlanEntitlement("plan_entitlement_id");
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("GET", request.getMethod());

        // Validate response body
        Assertions.assertNotNull(response, "Response should not be null");
        String actualResponseJson = objectMapper.writeValueAsString(response);
        String expectedResponseBody = ""
                + "{\n"
                + "  \"data\": {\n"
                + "    \"billing_threshold\": 1,\n"
                + "    \"consumption_rate\": 1.1,\n"
                + "    \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"environment_id\": \"environment_id\",\n"
                + "    \"feature\": {\n"
                + "      \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"description\": \"description\",\n"
                + "      \"event_subtype\": \"event_subtype\",\n"
                + "      \"feature_type\": \"feature_type\",\n"
                + "      \"icon\": \"icon\",\n"
                + "      \"id\": \"id\",\n"
                + "      \"lifecycle_phase\": \"lifecycle_phase\",\n"
                + "      \"maintainer_id\": \"maintainer_id\",\n"
                + "      \"name\": \"name\",\n"
                + "      \"plural_name\": \"plural_name\",\n"
                + "      \"singular_name\": \"singular_name\",\n"
                + "      \"trait_id\": \"trait_id\",\n"
                + "      \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "    },\n"
                + "    \"feature_id\": \"feature_id\",\n"
                + "    \"id\": \"id\",\n"
                + "    \"metered_monthly_price\": {\n"
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
                + "    },\n"
                + "    \"metered_yearly_price\": {\n"
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
                + "    },\n"
                + "    \"metric_period\": \"metric_period\",\n"
                + "    \"metric_period_month_reset\": \"metric_period_month_reset\",\n"
                + "    \"plan\": {\n"
                + "      \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"description\": \"description\",\n"
                + "      \"icon\": \"icon\",\n"
                + "      \"id\": \"id\",\n"
                + "      \"name\": \"name\",\n"
                + "      \"plan_type\": \"plan_type\",\n"
                + "      \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "    },\n"
                + "    \"plan_id\": \"plan_id\",\n"
                + "    \"price_behavior\": \"price_behavior\",\n"
                + "    \"rule_id\": \"rule_id\",\n"
                + "    \"rule_id_usage_exceeded\": \"rule_id_usage_exceeded\",\n"
                + "    \"soft_limit\": 1,\n"
                + "    \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"usage_based_product\": {\n"
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
                + "    \"value_bool\": true,\n"
                + "    \"value_credit\": {\n"
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
                + "    },\n"
                + "    \"value_numeric\": 1,\n"
                + "    \"value_trait\": {\n"
                + "      \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"display_name\": \"display_name\",\n"
                + "      \"entity_type\": \"entity_type\",\n"
                + "      \"hierarchy\": [\n"
                + "        \"hierarchy\"\n"
                + "      ],\n"
                + "      \"id\": \"id\",\n"
                + "      \"trait_type\": \"trait_type\",\n"
                + "      \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "    },\n"
                + "    \"value_trait_id\": \"value_trait_id\",\n"
                + "    \"value_type\": \"value_type\"\n"
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
    public void testUpdatePlanEntitlement() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":{\"billing_threshold\":1,\"consumption_rate\":1.1,\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"feature\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"description\":\"description\",\"event_subtype\":\"event_subtype\",\"feature_type\":\"feature_type\",\"icon\":\"icon\",\"id\":\"id\",\"lifecycle_phase\":\"lifecycle_phase\",\"maintainer_id\":\"maintainer_id\",\"name\":\"name\",\"plural_name\":\"plural_name\",\"singular_name\":\"singular_name\",\"trait_id\":\"trait_id\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"feature_id\":\"feature_id\",\"id\":\"id\",\"metered_monthly_price\":{\"billing_scheme\":\"billing_scheme\",\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"id\":\"id\",\"interval\":\"interval\",\"is_active\":true,\"meter_event_name\":\"meter_event_name\",\"meter_event_payload_key\":\"meter_event_payload_key\",\"meter_id\":\"meter_id\",\"package_size\":1,\"price\":1,\"price_decimal\":\"price_decimal\",\"price_external_id\":\"price_external_id\",\"price_id\":\"price_id\",\"price_tier\":[{}],\"product_external_id\":\"product_external_id\",\"product_id\":\"product_id\",\"product_name\":\"product_name\",\"tiers_mode\":\"tiers_mode\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"usage_type\":\"usage_type\"},\"metered_yearly_price\":{\"billing_scheme\":\"billing_scheme\",\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"id\":\"id\",\"interval\":\"interval\",\"is_active\":true,\"meter_event_name\":\"meter_event_name\",\"meter_event_payload_key\":\"meter_event_payload_key\",\"meter_id\":\"meter_id\",\"package_size\":1,\"price\":1,\"price_decimal\":\"price_decimal\",\"price_external_id\":\"price_external_id\",\"price_id\":\"price_id\",\"price_tier\":[{}],\"product_external_id\":\"product_external_id\",\"product_id\":\"product_id\",\"product_name\":\"product_name\",\"tiers_mode\":\"tiers_mode\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"usage_type\":\"usage_type\"},\"metric_period\":\"metric_period\",\"metric_period_month_reset\":\"metric_period_month_reset\",\"plan\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"description\":\"description\",\"icon\":\"icon\",\"id\":\"id\",\"name\":\"name\",\"plan_type\":\"plan_type\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"plan_id\":\"plan_id\",\"price_behavior\":\"price_behavior\",\"rule_id\":\"rule_id\",\"rule_id_usage_exceeded\":\"rule_id_usage_exceeded\",\"soft_limit\":1,\"updated_at\":\"2024-01-15T09:30:00Z\",\"usage_based_product\":{\"account_id\":\"account_id\",\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"is_active\":true,\"name\":\"name\",\"price\":1.1,\"price_decimal\":\"price_decimal\",\"product_id\":\"product_id\",\"quantity\":1.1,\"updated_at\":\"2024-01-15T09:30:00Z\"},\"value_bool\":true,\"value_credit\":{\"burn_strategy\":\"burn_strategy\",\"created_at\":\"2024-01-15T09:30:00Z\",\"default_expiry_unit\":\"default_expiry_unit\",\"default_expiry_unit_count\":1,\"default_rollover_policy\":\"default_rollover_policy\",\"description\":\"description\",\"icon\":\"icon\",\"id\":\"id\",\"name\":\"name\",\"plural_name\":\"plural_name\",\"price\":{\"currency\":\"currency\",\"external_price_id\":\"external_price_id\",\"id\":\"id\",\"interval\":\"interval\",\"price\":1,\"scheme\":\"scheme\"},\"product\":{\"account_id\":\"account_id\",\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"is_active\":true,\"name\":\"name\",\"price\":1.1,\"product_id\":\"product_id\",\"quantity\":1.1,\"updated_at\":\"2024-01-15T09:30:00Z\"},\"singular_name\":\"singular_name\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"value_numeric\":1,\"value_trait\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"display_name\":\"display_name\",\"entity_type\":\"entity_type\",\"hierarchy\":[\"hierarchy\"],\"id\":\"id\",\"trait_type\":\"trait_type\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"value_trait_id\":\"value_trait_id\",\"value_type\":\"value_type\"},\"params\":{\"key\":\"value\"}}"));
        UpdatePlanEntitlementResponse response = client.entitlements()
                .updatePlanEntitlement(
                        "plan_entitlement_id",
                        UpdatePlanEntitlementRequestBody.builder()
                                .valueType(UpdatePlanEntitlementRequestBodyValueType.BOOLEAN)
                                .build());
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("PUT", request.getMethod());
        // Validate request body
        String actualRequestBody = request.getBody().readUtf8();
        String expectedRequestBody = "" + "{\n" + "  \"value_type\": \"boolean\"\n" + "}";
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
                + "    \"billing_threshold\": 1,\n"
                + "    \"consumption_rate\": 1.1,\n"
                + "    \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"environment_id\": \"environment_id\",\n"
                + "    \"feature\": {\n"
                + "      \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"description\": \"description\",\n"
                + "      \"event_subtype\": \"event_subtype\",\n"
                + "      \"feature_type\": \"feature_type\",\n"
                + "      \"icon\": \"icon\",\n"
                + "      \"id\": \"id\",\n"
                + "      \"lifecycle_phase\": \"lifecycle_phase\",\n"
                + "      \"maintainer_id\": \"maintainer_id\",\n"
                + "      \"name\": \"name\",\n"
                + "      \"plural_name\": \"plural_name\",\n"
                + "      \"singular_name\": \"singular_name\",\n"
                + "      \"trait_id\": \"trait_id\",\n"
                + "      \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "    },\n"
                + "    \"feature_id\": \"feature_id\",\n"
                + "    \"id\": \"id\",\n"
                + "    \"metered_monthly_price\": {\n"
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
                + "    },\n"
                + "    \"metered_yearly_price\": {\n"
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
                + "    },\n"
                + "    \"metric_period\": \"metric_period\",\n"
                + "    \"metric_period_month_reset\": \"metric_period_month_reset\",\n"
                + "    \"plan\": {\n"
                + "      \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"description\": \"description\",\n"
                + "      \"icon\": \"icon\",\n"
                + "      \"id\": \"id\",\n"
                + "      \"name\": \"name\",\n"
                + "      \"plan_type\": \"plan_type\",\n"
                + "      \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "    },\n"
                + "    \"plan_id\": \"plan_id\",\n"
                + "    \"price_behavior\": \"price_behavior\",\n"
                + "    \"rule_id\": \"rule_id\",\n"
                + "    \"rule_id_usage_exceeded\": \"rule_id_usage_exceeded\",\n"
                + "    \"soft_limit\": 1,\n"
                + "    \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"usage_based_product\": {\n"
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
                + "    \"value_bool\": true,\n"
                + "    \"value_credit\": {\n"
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
                + "    },\n"
                + "    \"value_numeric\": 1,\n"
                + "    \"value_trait\": {\n"
                + "      \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"display_name\": \"display_name\",\n"
                + "      \"entity_type\": \"entity_type\",\n"
                + "      \"hierarchy\": [\n"
                + "        \"hierarchy\"\n"
                + "      ],\n"
                + "      \"id\": \"id\",\n"
                + "      \"trait_type\": \"trait_type\",\n"
                + "      \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "    },\n"
                + "    \"value_trait_id\": \"value_trait_id\",\n"
                + "    \"value_type\": \"value_type\"\n"
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
    public void testDeletePlanEntitlement() throws Exception {
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody("{\"data\":{\"deleted\":true},\"params\":{\"key\":\"value\"}}"));
        DeletePlanEntitlementResponse response = client.entitlements().deletePlanEntitlement("plan_entitlement_id");
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
    public void testCountPlanEntitlements() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":{\"count\":1},\"params\":{\"feature_id\":\"feature_id\",\"feature_ids\":[\"feature_ids\"],\"ids\":[\"ids\"],\"limit\":1,\"offset\":1,\"plan_id\":\"plan_id\",\"plan_ids\":[\"plan_ids\"],\"q\":\"q\",\"with_metered_products\":true}}"));
        CountPlanEntitlementsResponse response = client.entitlements()
                .countPlanEntitlements(CountPlanEntitlementsRequest.builder()
                        .featureId("feature_id")
                        .planId("plan_id")
                        .q("q")
                        .withMeteredProducts(true)
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
                + "    \"feature_id\": \"feature_id\",\n"
                + "    \"feature_ids\": [\n"
                + "      \"feature_ids\"\n"
                + "    ],\n"
                + "    \"ids\": [\n"
                + "      \"ids\"\n"
                + "    ],\n"
                + "    \"limit\": 1,\n"
                + "    \"offset\": 1,\n"
                + "    \"plan_id\": \"plan_id\",\n"
                + "    \"plan_ids\": [\n"
                + "      \"plan_ids\"\n"
                + "    ],\n"
                + "    \"q\": \"q\",\n"
                + "    \"with_metered_products\": true\n"
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
    public void testGetFeatureUsageByCompany() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":{\"features\":[{\"access\":true,\"allocation\":1,\"allocation_type\":\"boolean\",\"company_override\":{\"company\":{\"add_ons\":[{\"id\":\"id\",\"name\":\"name\"},{\"id\":\"id\",\"name\":\"name\"}],\"billing_credit_balances\":{\"billing_credit_balances\":1.1},\"billing_subscription\":{\"cancel_at_period_end\":true,\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"customer_external_id\":\"customer_external_id\",\"discounts\":[],\"id\":\"id\",\"interval\":\"interval\",\"period_end\":1,\"period_start\":1,\"products\":[],\"status\":\"status\",\"subscription_external_id\":\"subscription_external_id\",\"total_price\":1},\"billing_subscriptions\":[{\"cancel_at_period_end\":true,\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"customer_external_id\":\"customer_external_id\",\"discounts\":[],\"id\":\"id\",\"interval\":\"interval\",\"period_end\":1,\"period_start\":1,\"products\":[],\"status\":\"status\",\"subscription_external_id\":\"subscription_external_id\",\"total_price\":1},{\"cancel_at_period_end\":true,\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"customer_external_id\":\"customer_external_id\",\"discounts\":[],\"id\":\"id\",\"interval\":\"interval\",\"period_end\":1,\"period_start\":1,\"products\":[],\"status\":\"status\",\"subscription_external_id\":\"subscription_external_id\",\"total_price\":1}],\"created_at\":\"2024-01-15T09:30:00Z\",\"default_payment_method\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"customer_external_id\":\"customer_external_id\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"id\":\"id\",\"payment_method_type\":\"payment_method_type\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"entity_traits\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"definition_id\":\"definition_id\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"value\":\"value\"},{\"created_at\":\"2024-01-15T09:30:00Z\",\"definition_id\":\"definition_id\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"value\":\"value\"}],\"environment_id\":\"environment_id\",\"id\":\"id\",\"keys\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"definition_id\":\"definition_id\",\"entity_id\":\"entity_id\",\"entity_type\":\"entity_type\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"key\":\"key\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"value\":\"value\"},{\"created_at\":\"2024-01-15T09:30:00Z\",\"definition_id\":\"definition_id\",\"entity_id\":\"entity_id\",\"entity_type\":\"entity_type\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"key\":\"key\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"value\":\"value\"}],\"last_seen_at\":\"2024-01-15T09:30:00Z\",\"logo_url\":\"logo_url\",\"metrics\":[{\"account_id\":\"account_id\",\"captured_at_max\":\"2024-01-15T09:30:00Z\",\"captured_at_min\":\"2024-01-15T09:30:00Z\",\"company_id\":\"company_id\",\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"event_subtype\":\"event_subtype\",\"month_reset\":\"month_reset\",\"period\":\"period\",\"value\":1},{\"account_id\":\"account_id\",\"captured_at_max\":\"2024-01-15T09:30:00Z\",\"captured_at_min\":\"2024-01-15T09:30:00Z\",\"company_id\":\"company_id\",\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"event_subtype\":\"event_subtype\",\"month_reset\":\"month_reset\",\"period\":\"period\",\"value\":1}],\"name\":\"name\",\"payment_methods\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"customer_external_id\":\"customer_external_id\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"id\":\"id\",\"payment_method_type\":\"payment_method_type\",\"updated_at\":\"2024-01-15T09:30:00Z\"},{\"created_at\":\"2024-01-15T09:30:00Z\",\"customer_external_id\":\"customer_external_id\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"id\":\"id\",\"payment_method_type\":\"payment_method_type\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"plan\":{\"id\":\"id\",\"name\":\"name\"},\"plans\":[{\"id\":\"id\",\"name\":\"name\"},{\"id\":\"id\",\"name\":\"name\"}],\"rules\":[{\"account_id\":\"account_id\",\"condition_groups\":[],\"conditions\":[],\"environment_id\":\"environment_id\",\"id\":\"id\",\"name\":\"name\",\"priority\":1,\"rule_type\":\"rule_type\",\"value\":true},{\"account_id\":\"account_id\",\"condition_groups\":[],\"conditions\":[],\"environment_id\":\"environment_id\",\"id\":\"id\",\"name\":\"name\",\"priority\":1,\"rule_type\":\"rule_type\",\"value\":true}],\"traits\":{\"traits\":{\"key\":\"value\"}},\"updated_at\":\"2024-01-15T09:30:00Z\",\"user_count\":1},\"company_id\":\"company_id\",\"consumption_rate\":1.1,\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"expiration_date\":\"2024-01-15T09:30:00Z\",\"feature\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"description\":\"description\",\"event_subtype\":\"event_subtype\",\"feature_type\":\"feature_type\",\"icon\":\"icon\",\"id\":\"id\",\"lifecycle_phase\":\"lifecycle_phase\",\"maintainer_id\":\"maintainer_id\",\"name\":\"name\",\"plural_name\":\"plural_name\",\"singular_name\":\"singular_name\",\"trait_id\":\"trait_id\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"feature_id\":\"feature_id\",\"id\":\"id\",\"metric_period\":\"metric_period\",\"metric_period_month_reset\":\"metric_period_month_reset\",\"notes\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"external_user_id\":\"external_user_id\",\"external_user_name\":\"external_user_name\",\"id\":\"id\",\"note\":\"note\",\"updated_at\":\"2024-01-15T09:30:00Z\"},{\"created_at\":\"2024-01-15T09:30:00Z\",\"external_user_id\":\"external_user_id\",\"external_user_name\":\"external_user_name\",\"id\":\"id\",\"note\":\"note\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"rule_id\":\"rule_id\",\"rule_id_usage_exceeded\":\"rule_id_usage_exceeded\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"value_bool\":true,\"value_numeric\":1,\"value_trait\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"display_name\":\"display_name\",\"entity_type\":\"entity_type\",\"hierarchy\":[\"hierarchy\",\"hierarchy\"],\"id\":\"id\",\"trait_type\":\"trait_type\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"value_trait_id\":\"value_trait_id\",\"value_type\":\"value_type\"},\"credit_consumption_rate\":1.1,\"credit_grant_counts\":{\"credit_grant_counts\":1.1},\"credit_grant_details\":[{\"credit_type_icon\":\"credit_type_icon\",\"expires_at\":\"2024-01-15T09:30:00Z\",\"grant_reason\":\"free\",\"quantity\":1.1},{\"credit_type_icon\":\"credit_type_icon\",\"expires_at\":\"2024-01-15T09:30:00Z\",\"grant_reason\":\"free\",\"quantity\":1.1}],\"credit_grant_reason\":\"free\",\"credit_remaining\":1.1,\"credit_total\":1.1,\"credit_type_icon\":\"credit_type_icon\",\"credit_used\":1.1,\"effective_limit\":1,\"effective_price\":1.1,\"entitlement_expiration_date\":\"2024-01-15T09:30:00Z\",\"entitlement_id\":\"entitlement_id\",\"entitlement_source\":\"entitlement_source\",\"entitlement_type\":\"entitlement_type\",\"feature\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"description\":\"description\",\"event_subtype\":\"event_subtype\",\"event_summary\":{\"company_count\":1,\"environment_id\":\"environment_id\",\"event_count\":1,\"event_subtype\":\"event_subtype\",\"last_seen_at\":\"2024-01-15T09:30:00Z\",\"user_count\":1},\"feature_type\":\"feature_type\",\"flags\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"default_value\":true,\"description\":\"description\",\"feature\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"description\":\"description\",\"feature_type\":\"feature_type\",\"icon\":\"icon\",\"id\":\"id\",\"name\":\"name\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"feature_id\":\"feature_id\",\"flag_type\":\"flag_type\",\"id\":\"id\",\"key\":\"key\",\"last_checked_at\":\"2024-01-15T09:30:00Z\",\"maintainer_id\":\"maintainer_id\",\"name\":\"name\",\"rules\":[{\"condition_groups\":[],\"conditions\":[],\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"name\":\"name\",\"priority\":1,\"rule_type\":\"rule_type\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"value\":true},{\"condition_groups\":[],\"conditions\":[],\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"name\":\"name\",\"priority\":1,\"rule_type\":\"rule_type\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"value\":true}],\"updated_at\":\"2024-01-15T09:30:00Z\"},{\"created_at\":\"2024-01-15T09:30:00Z\",\"default_value\":true,\"description\":\"description\",\"feature\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"description\":\"description\",\"feature_type\":\"feature_type\",\"icon\":\"icon\",\"id\":\"id\",\"name\":\"name\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"feature_id\":\"feature_id\",\"flag_type\":\"flag_type\",\"id\":\"id\",\"key\":\"key\",\"last_checked_at\":\"2024-01-15T09:30:00Z\",\"maintainer_id\":\"maintainer_id\",\"name\":\"name\",\"rules\":[{\"condition_groups\":[],\"conditions\":[],\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"name\":\"name\",\"priority\":1,\"rule_type\":\"rule_type\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"value\":true},{\"condition_groups\":[],\"conditions\":[],\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"name\":\"name\",\"priority\":1,\"rule_type\":\"rule_type\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"value\":true}],\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"icon\":\"icon\",\"id\":\"id\",\"lifecycle_phase\":\"lifecycle_phase\",\"maintainer_id\":\"maintainer_id\",\"name\":\"name\",\"plans\":[{\"description\":\"description\",\"id\":\"id\",\"image_url\":\"image_url\",\"name\":\"name\"},{\"description\":\"description\",\"id\":\"id\",\"image_url\":\"image_url\",\"name\":\"name\"}],\"plural_name\":\"plural_name\",\"singular_name\":\"singular_name\",\"trait\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"display_name\":\"display_name\",\"entity_type\":\"entity_type\",\"hierarchy\":[\"hierarchy\",\"hierarchy\"],\"id\":\"id\",\"trait_type\":\"trait_type\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"trait_id\":\"trait_id\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"has_valid_allocation\":true,\"is_unlimited\":true,\"metric_reset_at\":\"2024-01-15T09:30:00Z\",\"month_reset\":\"month_reset\",\"monthly_usage_based_price\":{\"billing_scheme\":\"billing_scheme\",\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"id\":\"id\",\"interval\":\"interval\",\"is_active\":true,\"meter_event_name\":\"meter_event_name\",\"meter_event_payload_key\":\"meter_event_payload_key\",\"meter_id\":\"meter_id\",\"package_size\":1,\"price\":1,\"price_decimal\":\"price_decimal\",\"price_external_id\":\"price_external_id\",\"price_id\":\"price_id\",\"price_tier\":[{\"flat_amount\":1,\"per_unit_price\":1,\"per_unit_price_decimal\":\"per_unit_price_decimal\",\"up_to\":1},{\"flat_amount\":1,\"per_unit_price\":1,\"per_unit_price_decimal\":\"per_unit_price_decimal\",\"up_to\":1}],\"product_external_id\":\"product_external_id\",\"product_id\":\"product_id\",\"product_name\":\"product_name\",\"tiers_mode\":\"tiers_mode\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"usage_type\":\"usage_type\"},\"overuse\":1,\"percent_used\":1.1,\"period\":\"period\",\"plan\":{\"audience_type\":\"audience_type\",\"created_at\":\"2024-01-15T09:30:00Z\",\"description\":\"description\",\"icon\":\"icon\",\"id\":\"id\",\"name\":\"name\",\"plan_type\":\"plan_type\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"plan_entitlement\":{\"billing_threshold\":1,\"consumption_rate\":1.1,\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"feature\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"description\":\"description\",\"event_subtype\":\"event_subtype\",\"feature_type\":\"feature_type\",\"icon\":\"icon\",\"id\":\"id\",\"lifecycle_phase\":\"lifecycle_phase\",\"maintainer_id\":\"maintainer_id\",\"name\":\"name\",\"plural_name\":\"plural_name\",\"singular_name\":\"singular_name\",\"trait_id\":\"trait_id\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"feature_id\":\"feature_id\",\"id\":\"id\",\"metered_monthly_price\":{\"billing_scheme\":\"billing_scheme\",\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"id\":\"id\",\"interval\":\"interval\",\"is_active\":true,\"meter_event_name\":\"meter_event_name\",\"meter_event_payload_key\":\"meter_event_payload_key\",\"meter_id\":\"meter_id\",\"package_size\":1,\"price\":1,\"price_decimal\":\"price_decimal\",\"price_external_id\":\"price_external_id\",\"price_id\":\"price_id\",\"price_tier\":[{},{}],\"product_external_id\":\"product_external_id\",\"product_id\":\"product_id\",\"product_name\":\"product_name\",\"tiers_mode\":\"tiers_mode\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"usage_type\":\"usage_type\"},\"metered_yearly_price\":{\"billing_scheme\":\"billing_scheme\",\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"id\":\"id\",\"interval\":\"interval\",\"is_active\":true,\"meter_event_name\":\"meter_event_name\",\"meter_event_payload_key\":\"meter_event_payload_key\",\"meter_id\":\"meter_id\",\"package_size\":1,\"price\":1,\"price_decimal\":\"price_decimal\",\"price_external_id\":\"price_external_id\",\"price_id\":\"price_id\",\"price_tier\":[{},{}],\"product_external_id\":\"product_external_id\",\"product_id\":\"product_id\",\"product_name\":\"product_name\",\"tiers_mode\":\"tiers_mode\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"usage_type\":\"usage_type\"},\"metric_period\":\"metric_period\",\"metric_period_month_reset\":\"metric_period_month_reset\",\"plan\":{\"audience_type\":\"audience_type\",\"created_at\":\"2024-01-15T09:30:00Z\",\"description\":\"description\",\"icon\":\"icon\",\"id\":\"id\",\"name\":\"name\",\"plan_type\":\"plan_type\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"plan_id\":\"plan_id\",\"price_behavior\":\"price_behavior\",\"rule_id\":\"rule_id\",\"rule_id_usage_exceeded\":\"rule_id_usage_exceeded\",\"soft_limit\":1,\"updated_at\":\"2024-01-15T09:30:00Z\",\"usage_based_product\":{\"account_id\":\"account_id\",\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"is_active\":true,\"name\":\"name\",\"price\":1.1,\"price_decimal\":\"price_decimal\",\"product_id\":\"product_id\",\"quantity\":1.1,\"updated_at\":\"2024-01-15T09:30:00Z\"},\"value_bool\":true,\"value_credit\":{\"burn_strategy\":\"burn_strategy\",\"created_at\":\"2024-01-15T09:30:00Z\",\"default_expiry_unit\":\"default_expiry_unit\",\"default_expiry_unit_count\":1,\"default_rollover_policy\":\"default_rollover_policy\",\"description\":\"description\",\"icon\":\"icon\",\"id\":\"id\",\"name\":\"name\",\"plural_name\":\"plural_name\",\"price\":{\"currency\":\"currency\",\"external_price_id\":\"external_price_id\",\"id\":\"id\",\"interval\":\"interval\",\"price\":1,\"scheme\":\"scheme\"},\"product\":{\"account_id\":\"account_id\",\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"is_active\":true,\"name\":\"name\",\"price\":1.1,\"product_id\":\"product_id\",\"quantity\":1.1,\"updated_at\":\"2024-01-15T09:30:00Z\"},\"singular_name\":\"singular_name\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"value_numeric\":1,\"value_trait\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"display_name\":\"display_name\",\"entity_type\":\"entity_type\",\"hierarchy\":[\"hierarchy\",\"hierarchy\"],\"id\":\"id\",\"trait_type\":\"trait_type\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"value_trait_id\":\"value_trait_id\",\"value_type\":\"value_type\"},\"price_behavior\":\"price_behavior\",\"soft_limit\":1,\"usage\":1,\"yearly_usage_based_price\":{\"billing_scheme\":\"billing_scheme\",\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"id\":\"id\",\"interval\":\"interval\",\"is_active\":true,\"meter_event_name\":\"meter_event_name\",\"meter_event_payload_key\":\"meter_event_payload_key\",\"meter_id\":\"meter_id\",\"package_size\":1,\"price\":1,\"price_decimal\":\"price_decimal\",\"price_external_id\":\"price_external_id\",\"price_id\":\"price_id\",\"price_tier\":[{\"flat_amount\":1,\"per_unit_price\":1,\"per_unit_price_decimal\":\"per_unit_price_decimal\",\"up_to\":1},{\"flat_amount\":1,\"per_unit_price\":1,\"per_unit_price_decimal\":\"per_unit_price_decimal\",\"up_to\":1}],\"product_external_id\":\"product_external_id\",\"product_id\":\"product_id\",\"product_name\":\"product_name\",\"tiers_mode\":\"tiers_mode\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"usage_type\":\"usage_type\"}},{\"access\":true,\"allocation\":1,\"allocation_type\":\"boolean\",\"company_override\":{\"company\":{\"add_ons\":[{\"id\":\"id\",\"name\":\"name\"},{\"id\":\"id\",\"name\":\"name\"}],\"billing_credit_balances\":{\"billing_credit_balances\":1.1},\"billing_subscription\":{\"cancel_at_period_end\":true,\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"customer_external_id\":\"customer_external_id\",\"discounts\":[],\"id\":\"id\",\"interval\":\"interval\",\"period_end\":1,\"period_start\":1,\"products\":[],\"status\":\"status\",\"subscription_external_id\":\"subscription_external_id\",\"total_price\":1},\"billing_subscriptions\":[{\"cancel_at_period_end\":true,\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"customer_external_id\":\"customer_external_id\",\"discounts\":[],\"id\":\"id\",\"interval\":\"interval\",\"period_end\":1,\"period_start\":1,\"products\":[],\"status\":\"status\",\"subscription_external_id\":\"subscription_external_id\",\"total_price\":1},{\"cancel_at_period_end\":true,\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"customer_external_id\":\"customer_external_id\",\"discounts\":[],\"id\":\"id\",\"interval\":\"interval\",\"period_end\":1,\"period_start\":1,\"products\":[],\"status\":\"status\",\"subscription_external_id\":\"subscription_external_id\",\"total_price\":1}],\"created_at\":\"2024-01-15T09:30:00Z\",\"default_payment_method\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"customer_external_id\":\"customer_external_id\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"id\":\"id\",\"payment_method_type\":\"payment_method_type\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"entity_traits\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"definition_id\":\"definition_id\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"value\":\"value\"},{\"created_at\":\"2024-01-15T09:30:00Z\",\"definition_id\":\"definition_id\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"value\":\"value\"}],\"environment_id\":\"environment_id\",\"id\":\"id\",\"keys\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"definition_id\":\"definition_id\",\"entity_id\":\"entity_id\",\"entity_type\":\"entity_type\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"key\":\"key\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"value\":\"value\"},{\"created_at\":\"2024-01-15T09:30:00Z\",\"definition_id\":\"definition_id\",\"entity_id\":\"entity_id\",\"entity_type\":\"entity_type\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"key\":\"key\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"value\":\"value\"}],\"last_seen_at\":\"2024-01-15T09:30:00Z\",\"logo_url\":\"logo_url\",\"metrics\":[{\"account_id\":\"account_id\",\"captured_at_max\":\"2024-01-15T09:30:00Z\",\"captured_at_min\":\"2024-01-15T09:30:00Z\",\"company_id\":\"company_id\",\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"event_subtype\":\"event_subtype\",\"month_reset\":\"month_reset\",\"period\":\"period\",\"value\":1},{\"account_id\":\"account_id\",\"captured_at_max\":\"2024-01-15T09:30:00Z\",\"captured_at_min\":\"2024-01-15T09:30:00Z\",\"company_id\":\"company_id\",\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"event_subtype\":\"event_subtype\",\"month_reset\":\"month_reset\",\"period\":\"period\",\"value\":1}],\"name\":\"name\",\"payment_methods\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"customer_external_id\":\"customer_external_id\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"id\":\"id\",\"payment_method_type\":\"payment_method_type\",\"updated_at\":\"2024-01-15T09:30:00Z\"},{\"created_at\":\"2024-01-15T09:30:00Z\",\"customer_external_id\":\"customer_external_id\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"id\":\"id\",\"payment_method_type\":\"payment_method_type\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"plan\":{\"id\":\"id\",\"name\":\"name\"},\"plans\":[{\"id\":\"id\",\"name\":\"name\"},{\"id\":\"id\",\"name\":\"name\"}],\"rules\":[{\"account_id\":\"account_id\",\"condition_groups\":[],\"conditions\":[],\"environment_id\":\"environment_id\",\"id\":\"id\",\"name\":\"name\",\"priority\":1,\"rule_type\":\"rule_type\",\"value\":true},{\"account_id\":\"account_id\",\"condition_groups\":[],\"conditions\":[],\"environment_id\":\"environment_id\",\"id\":\"id\",\"name\":\"name\",\"priority\":1,\"rule_type\":\"rule_type\",\"value\":true}],\"traits\":{\"traits\":{\"key\":\"value\"}},\"updated_at\":\"2024-01-15T09:30:00Z\",\"user_count\":1},\"company_id\":\"company_id\",\"consumption_rate\":1.1,\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"expiration_date\":\"2024-01-15T09:30:00Z\",\"feature\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"description\":\"description\",\"event_subtype\":\"event_subtype\",\"feature_type\":\"feature_type\",\"icon\":\"icon\",\"id\":\"id\",\"lifecycle_phase\":\"lifecycle_phase\",\"maintainer_id\":\"maintainer_id\",\"name\":\"name\",\"plural_name\":\"plural_name\",\"singular_name\":\"singular_name\",\"trait_id\":\"trait_id\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"feature_id\":\"feature_id\",\"id\":\"id\",\"metric_period\":\"metric_period\",\"metric_period_month_reset\":\"metric_period_month_reset\",\"notes\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"external_user_id\":\"external_user_id\",\"external_user_name\":\"external_user_name\",\"id\":\"id\",\"note\":\"note\",\"updated_at\":\"2024-01-15T09:30:00Z\"},{\"created_at\":\"2024-01-15T09:30:00Z\",\"external_user_id\":\"external_user_id\",\"external_user_name\":\"external_user_name\",\"id\":\"id\",\"note\":\"note\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"rule_id\":\"rule_id\",\"rule_id_usage_exceeded\":\"rule_id_usage_exceeded\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"value_bool\":true,\"value_numeric\":1,\"value_trait\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"display_name\":\"display_name\",\"entity_type\":\"entity_type\",\"hierarchy\":[\"hierarchy\",\"hierarchy\"],\"id\":\"id\",\"trait_type\":\"trait_type\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"value_trait_id\":\"value_trait_id\",\"value_type\":\"value_type\"},\"credit_consumption_rate\":1.1,\"credit_grant_counts\":{\"credit_grant_counts\":1.1},\"credit_grant_details\":[{\"credit_type_icon\":\"credit_type_icon\",\"expires_at\":\"2024-01-15T09:30:00Z\",\"grant_reason\":\"free\",\"quantity\":1.1},{\"credit_type_icon\":\"credit_type_icon\",\"expires_at\":\"2024-01-15T09:30:00Z\",\"grant_reason\":\"free\",\"quantity\":1.1}],\"credit_grant_reason\":\"free\",\"credit_remaining\":1.1,\"credit_total\":1.1,\"credit_type_icon\":\"credit_type_icon\",\"credit_used\":1.1,\"effective_limit\":1,\"effective_price\":1.1,\"entitlement_expiration_date\":\"2024-01-15T09:30:00Z\",\"entitlement_id\":\"entitlement_id\",\"entitlement_source\":\"entitlement_source\",\"entitlement_type\":\"entitlement_type\",\"feature\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"description\":\"description\",\"event_subtype\":\"event_subtype\",\"event_summary\":{\"company_count\":1,\"environment_id\":\"environment_id\",\"event_count\":1,\"event_subtype\":\"event_subtype\",\"last_seen_at\":\"2024-01-15T09:30:00Z\",\"user_count\":1},\"feature_type\":\"feature_type\",\"flags\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"default_value\":true,\"description\":\"description\",\"feature\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"description\":\"description\",\"feature_type\":\"feature_type\",\"icon\":\"icon\",\"id\":\"id\",\"name\":\"name\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"feature_id\":\"feature_id\",\"flag_type\":\"flag_type\",\"id\":\"id\",\"key\":\"key\",\"last_checked_at\":\"2024-01-15T09:30:00Z\",\"maintainer_id\":\"maintainer_id\",\"name\":\"name\",\"rules\":[{\"condition_groups\":[],\"conditions\":[],\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"name\":\"name\",\"priority\":1,\"rule_type\":\"rule_type\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"value\":true},{\"condition_groups\":[],\"conditions\":[],\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"name\":\"name\",\"priority\":1,\"rule_type\":\"rule_type\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"value\":true}],\"updated_at\":\"2024-01-15T09:30:00Z\"},{\"created_at\":\"2024-01-15T09:30:00Z\",\"default_value\":true,\"description\":\"description\",\"feature\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"description\":\"description\",\"feature_type\":\"feature_type\",\"icon\":\"icon\",\"id\":\"id\",\"name\":\"name\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"feature_id\":\"feature_id\",\"flag_type\":\"flag_type\",\"id\":\"id\",\"key\":\"key\",\"last_checked_at\":\"2024-01-15T09:30:00Z\",\"maintainer_id\":\"maintainer_id\",\"name\":\"name\",\"rules\":[{\"condition_groups\":[],\"conditions\":[],\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"name\":\"name\",\"priority\":1,\"rule_type\":\"rule_type\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"value\":true},{\"condition_groups\":[],\"conditions\":[],\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"name\":\"name\",\"priority\":1,\"rule_type\":\"rule_type\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"value\":true}],\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"icon\":\"icon\",\"id\":\"id\",\"lifecycle_phase\":\"lifecycle_phase\",\"maintainer_id\":\"maintainer_id\",\"name\":\"name\",\"plans\":[{\"description\":\"description\",\"id\":\"id\",\"image_url\":\"image_url\",\"name\":\"name\"},{\"description\":\"description\",\"id\":\"id\",\"image_url\":\"image_url\",\"name\":\"name\"}],\"plural_name\":\"plural_name\",\"singular_name\":\"singular_name\",\"trait\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"display_name\":\"display_name\",\"entity_type\":\"entity_type\",\"hierarchy\":[\"hierarchy\",\"hierarchy\"],\"id\":\"id\",\"trait_type\":\"trait_type\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"trait_id\":\"trait_id\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"has_valid_allocation\":true,\"is_unlimited\":true,\"metric_reset_at\":\"2024-01-15T09:30:00Z\",\"month_reset\":\"month_reset\",\"monthly_usage_based_price\":{\"billing_scheme\":\"billing_scheme\",\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"id\":\"id\",\"interval\":\"interval\",\"is_active\":true,\"meter_event_name\":\"meter_event_name\",\"meter_event_payload_key\":\"meter_event_payload_key\",\"meter_id\":\"meter_id\",\"package_size\":1,\"price\":1,\"price_decimal\":\"price_decimal\",\"price_external_id\":\"price_external_id\",\"price_id\":\"price_id\",\"price_tier\":[{\"flat_amount\":1,\"per_unit_price\":1,\"per_unit_price_decimal\":\"per_unit_price_decimal\",\"up_to\":1},{\"flat_amount\":1,\"per_unit_price\":1,\"per_unit_price_decimal\":\"per_unit_price_decimal\",\"up_to\":1}],\"product_external_id\":\"product_external_id\",\"product_id\":\"product_id\",\"product_name\":\"product_name\",\"tiers_mode\":\"tiers_mode\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"usage_type\":\"usage_type\"},\"overuse\":1,\"percent_used\":1.1,\"period\":\"period\",\"plan\":{\"audience_type\":\"audience_type\",\"created_at\":\"2024-01-15T09:30:00Z\",\"description\":\"description\",\"icon\":\"icon\",\"id\":\"id\",\"name\":\"name\",\"plan_type\":\"plan_type\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"plan_entitlement\":{\"billing_threshold\":1,\"consumption_rate\":1.1,\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"feature\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"description\":\"description\",\"event_subtype\":\"event_subtype\",\"feature_type\":\"feature_type\",\"icon\":\"icon\",\"id\":\"id\",\"lifecycle_phase\":\"lifecycle_phase\",\"maintainer_id\":\"maintainer_id\",\"name\":\"name\",\"plural_name\":\"plural_name\",\"singular_name\":\"singular_name\",\"trait_id\":\"trait_id\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"feature_id\":\"feature_id\",\"id\":\"id\",\"metered_monthly_price\":{\"billing_scheme\":\"billing_scheme\",\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"id\":\"id\",\"interval\":\"interval\",\"is_active\":true,\"meter_event_name\":\"meter_event_name\",\"meter_event_payload_key\":\"meter_event_payload_key\",\"meter_id\":\"meter_id\",\"package_size\":1,\"price\":1,\"price_decimal\":\"price_decimal\",\"price_external_id\":\"price_external_id\",\"price_id\":\"price_id\",\"price_tier\":[{},{}],\"product_external_id\":\"product_external_id\",\"product_id\":\"product_id\",\"product_name\":\"product_name\",\"tiers_mode\":\"tiers_mode\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"usage_type\":\"usage_type\"},\"metered_yearly_price\":{\"billing_scheme\":\"billing_scheme\",\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"id\":\"id\",\"interval\":\"interval\",\"is_active\":true,\"meter_event_name\":\"meter_event_name\",\"meter_event_payload_key\":\"meter_event_payload_key\",\"meter_id\":\"meter_id\",\"package_size\":1,\"price\":1,\"price_decimal\":\"price_decimal\",\"price_external_id\":\"price_external_id\",\"price_id\":\"price_id\",\"price_tier\":[{},{}],\"product_external_id\":\"product_external_id\",\"product_id\":\"product_id\",\"product_name\":\"product_name\",\"tiers_mode\":\"tiers_mode\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"usage_type\":\"usage_type\"},\"metric_period\":\"metric_period\",\"metric_period_month_reset\":\"metric_period_month_reset\",\"plan\":{\"audience_type\":\"audience_type\",\"created_at\":\"2024-01-15T09:30:00Z\",\"description\":\"description\",\"icon\":\"icon\",\"id\":\"id\",\"name\":\"name\",\"plan_type\":\"plan_type\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"plan_id\":\"plan_id\",\"price_behavior\":\"price_behavior\",\"rule_id\":\"rule_id\",\"rule_id_usage_exceeded\":\"rule_id_usage_exceeded\",\"soft_limit\":1,\"updated_at\":\"2024-01-15T09:30:00Z\",\"usage_based_product\":{\"account_id\":\"account_id\",\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"is_active\":true,\"name\":\"name\",\"price\":1.1,\"price_decimal\":\"price_decimal\",\"product_id\":\"product_id\",\"quantity\":1.1,\"updated_at\":\"2024-01-15T09:30:00Z\"},\"value_bool\":true,\"value_credit\":{\"burn_strategy\":\"burn_strategy\",\"created_at\":\"2024-01-15T09:30:00Z\",\"default_expiry_unit\":\"default_expiry_unit\",\"default_expiry_unit_count\":1,\"default_rollover_policy\":\"default_rollover_policy\",\"description\":\"description\",\"icon\":\"icon\",\"id\":\"id\",\"name\":\"name\",\"plural_name\":\"plural_name\",\"price\":{\"currency\":\"currency\",\"external_price_id\":\"external_price_id\",\"id\":\"id\",\"interval\":\"interval\",\"price\":1,\"scheme\":\"scheme\"},\"product\":{\"account_id\":\"account_id\",\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"is_active\":true,\"name\":\"name\",\"price\":1.1,\"product_id\":\"product_id\",\"quantity\":1.1,\"updated_at\":\"2024-01-15T09:30:00Z\"},\"singular_name\":\"singular_name\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"value_numeric\":1,\"value_trait\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"display_name\":\"display_name\",\"entity_type\":\"entity_type\",\"hierarchy\":[\"hierarchy\",\"hierarchy\"],\"id\":\"id\",\"trait_type\":\"trait_type\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"value_trait_id\":\"value_trait_id\",\"value_type\":\"value_type\"},\"price_behavior\":\"price_behavior\",\"soft_limit\":1,\"usage\":1,\"yearly_usage_based_price\":{\"billing_scheme\":\"billing_scheme\",\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"id\":\"id\",\"interval\":\"interval\",\"is_active\":true,\"meter_event_name\":\"meter_event_name\",\"meter_event_payload_key\":\"meter_event_payload_key\",\"meter_id\":\"meter_id\",\"package_size\":1,\"price\":1,\"price_decimal\":\"price_decimal\",\"price_external_id\":\"price_external_id\",\"price_id\":\"price_id\",\"price_tier\":[{\"flat_amount\":1,\"per_unit_price\":1,\"per_unit_price_decimal\":\"per_unit_price_decimal\",\"up_to\":1},{\"flat_amount\":1,\"per_unit_price\":1,\"per_unit_price_decimal\":\"per_unit_price_decimal\",\"up_to\":1}],\"product_external_id\":\"product_external_id\",\"product_id\":\"product_id\",\"product_name\":\"product_name\",\"tiers_mode\":\"tiers_mode\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"usage_type\":\"usage_type\"}}]},\"params\":{\"keys\":{\"keys\":\"keys\"}}}"));
        GetFeatureUsageByCompanyResponse response = client.entitlements()
                .getFeatureUsageByCompany(GetFeatureUsageByCompanyRequest.builder()
                        .keys(new HashMap<String, String>() {
                            {
                                put("keys", "keys");
                            }
                        })
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
                + "    \"features\": [\n"
                + "      {\n"
                + "        \"access\": true,\n"
                + "        \"allocation\": 1,\n"
                + "        \"allocation_type\": \"boolean\",\n"
                + "        \"company_override\": {\n"
                + "          \"company\": {\n"
                + "            \"add_ons\": [\n"
                + "              {\n"
                + "                \"id\": \"id\",\n"
                + "                \"name\": \"name\"\n"
                + "              },\n"
                + "              {\n"
                + "                \"id\": \"id\",\n"
                + "                \"name\": \"name\"\n"
                + "              }\n"
                + "            ],\n"
                + "            \"billing_credit_balances\": {\n"
                + "              \"billing_credit_balances\": 1.1\n"
                + "            },\n"
                + "            \"billing_subscription\": {\n"
                + "              \"cancel_at_period_end\": true,\n"
                + "              \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "              \"currency\": \"currency\",\n"
                + "              \"customer_external_id\": \"customer_external_id\",\n"
                + "              \"discounts\": [],\n"
                + "              \"id\": \"id\",\n"
                + "              \"interval\": \"interval\",\n"
                + "              \"period_end\": 1,\n"
                + "              \"period_start\": 1,\n"
                + "              \"products\": [],\n"
                + "              \"status\": \"status\",\n"
                + "              \"subscription_external_id\": \"subscription_external_id\",\n"
                + "              \"total_price\": 1\n"
                + "            },\n"
                + "            \"billing_subscriptions\": [\n"
                + "              {\n"
                + "                \"cancel_at_period_end\": true,\n"
                + "                \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "                \"currency\": \"currency\",\n"
                + "                \"customer_external_id\": \"customer_external_id\",\n"
                + "                \"discounts\": [],\n"
                + "                \"id\": \"id\",\n"
                + "                \"interval\": \"interval\",\n"
                + "                \"period_end\": 1,\n"
                + "                \"period_start\": 1,\n"
                + "                \"products\": [],\n"
                + "                \"status\": \"status\",\n"
                + "                \"subscription_external_id\": \"subscription_external_id\",\n"
                + "                \"total_price\": 1\n"
                + "              },\n"
                + "              {\n"
                + "                \"cancel_at_period_end\": true,\n"
                + "                \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "                \"currency\": \"currency\",\n"
                + "                \"customer_external_id\": \"customer_external_id\",\n"
                + "                \"discounts\": [],\n"
                + "                \"id\": \"id\",\n"
                + "                \"interval\": \"interval\",\n"
                + "                \"period_end\": 1,\n"
                + "                \"period_start\": 1,\n"
                + "                \"products\": [],\n"
                + "                \"status\": \"status\",\n"
                + "                \"subscription_external_id\": \"subscription_external_id\",\n"
                + "                \"total_price\": 1\n"
                + "              }\n"
                + "            ],\n"
                + "            \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"default_payment_method\": {\n"
                + "              \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "              \"customer_external_id\": \"customer_external_id\",\n"
                + "              \"environment_id\": \"environment_id\",\n"
                + "              \"external_id\": \"external_id\",\n"
                + "              \"id\": \"id\",\n"
                + "              \"payment_method_type\": \"payment_method_type\",\n"
                + "              \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "            },\n"
                + "            \"entity_traits\": [\n"
                + "              {\n"
                + "                \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "                \"definition_id\": \"definition_id\",\n"
                + "                \"environment_id\": \"environment_id\",\n"
                + "                \"id\": \"id\",\n"
                + "                \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "                \"value\": \"value\"\n"
                + "              },\n"
                + "              {\n"
                + "                \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "                \"definition_id\": \"definition_id\",\n"
                + "                \"environment_id\": \"environment_id\",\n"
                + "                \"id\": \"id\",\n"
                + "                \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "                \"value\": \"value\"\n"
                + "              }\n"
                + "            ],\n"
                + "            \"environment_id\": \"environment_id\",\n"
                + "            \"id\": \"id\",\n"
                + "            \"keys\": [\n"
                + "              {\n"
                + "                \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "                \"definition_id\": \"definition_id\",\n"
                + "                \"entity_id\": \"entity_id\",\n"
                + "                \"entity_type\": \"entity_type\",\n"
                + "                \"environment_id\": \"environment_id\",\n"
                + "                \"id\": \"id\",\n"
                + "                \"key\": \"key\",\n"
                + "                \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "                \"value\": \"value\"\n"
                + "              },\n"
                + "              {\n"
                + "                \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "                \"definition_id\": \"definition_id\",\n"
                + "                \"entity_id\": \"entity_id\",\n"
                + "                \"entity_type\": \"entity_type\",\n"
                + "                \"environment_id\": \"environment_id\",\n"
                + "                \"id\": \"id\",\n"
                + "                \"key\": \"key\",\n"
                + "                \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "                \"value\": \"value\"\n"
                + "              }\n"
                + "            ],\n"
                + "            \"last_seen_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"logo_url\": \"logo_url\",\n"
                + "            \"metrics\": [\n"
                + "              {\n"
                + "                \"account_id\": \"account_id\",\n"
                + "                \"captured_at_max\": \"2024-01-15T09:30:00Z\",\n"
                + "                \"captured_at_min\": \"2024-01-15T09:30:00Z\",\n"
                + "                \"company_id\": \"company_id\",\n"
                + "                \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "                \"environment_id\": \"environment_id\",\n"
                + "                \"event_subtype\": \"event_subtype\",\n"
                + "                \"month_reset\": \"month_reset\",\n"
                + "                \"period\": \"period\",\n"
                + "                \"value\": 1\n"
                + "              },\n"
                + "              {\n"
                + "                \"account_id\": \"account_id\",\n"
                + "                \"captured_at_max\": \"2024-01-15T09:30:00Z\",\n"
                + "                \"captured_at_min\": \"2024-01-15T09:30:00Z\",\n"
                + "                \"company_id\": \"company_id\",\n"
                + "                \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "                \"environment_id\": \"environment_id\",\n"
                + "                \"event_subtype\": \"event_subtype\",\n"
                + "                \"month_reset\": \"month_reset\",\n"
                + "                \"period\": \"period\",\n"
                + "                \"value\": 1\n"
                + "              }\n"
                + "            ],\n"
                + "            \"name\": \"name\",\n"
                + "            \"payment_methods\": [\n"
                + "              {\n"
                + "                \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "                \"customer_external_id\": \"customer_external_id\",\n"
                + "                \"environment_id\": \"environment_id\",\n"
                + "                \"external_id\": \"external_id\",\n"
                + "                \"id\": \"id\",\n"
                + "                \"payment_method_type\": \"payment_method_type\",\n"
                + "                \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "              },\n"
                + "              {\n"
                + "                \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "                \"customer_external_id\": \"customer_external_id\",\n"
                + "                \"environment_id\": \"environment_id\",\n"
                + "                \"external_id\": \"external_id\",\n"
                + "                \"id\": \"id\",\n"
                + "                \"payment_method_type\": \"payment_method_type\",\n"
                + "                \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "              }\n"
                + "            ],\n"
                + "            \"plan\": {\n"
                + "              \"id\": \"id\",\n"
                + "              \"name\": \"name\"\n"
                + "            },\n"
                + "            \"plans\": [\n"
                + "              {\n"
                + "                \"id\": \"id\",\n"
                + "                \"name\": \"name\"\n"
                + "              },\n"
                + "              {\n"
                + "                \"id\": \"id\",\n"
                + "                \"name\": \"name\"\n"
                + "              }\n"
                + "            ],\n"
                + "            \"rules\": [\n"
                + "              {\n"
                + "                \"account_id\": \"account_id\",\n"
                + "                \"condition_groups\": [],\n"
                + "                \"conditions\": [],\n"
                + "                \"environment_id\": \"environment_id\",\n"
                + "                \"id\": \"id\",\n"
                + "                \"name\": \"name\",\n"
                + "                \"priority\": 1,\n"
                + "                \"rule_type\": \"rule_type\",\n"
                + "                \"value\": true\n"
                + "              },\n"
                + "              {\n"
                + "                \"account_id\": \"account_id\",\n"
                + "                \"condition_groups\": [],\n"
                + "                \"conditions\": [],\n"
                + "                \"environment_id\": \"environment_id\",\n"
                + "                \"id\": \"id\",\n"
                + "                \"name\": \"name\",\n"
                + "                \"priority\": 1,\n"
                + "                \"rule_type\": \"rule_type\",\n"
                + "                \"value\": true\n"
                + "              }\n"
                + "            ],\n"
                + "            \"traits\": {\n"
                + "              \"traits\": {\n"
                + "                \"key\": \"value\"\n"
                + "              }\n"
                + "            },\n"
                + "            \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"user_count\": 1\n"
                + "          },\n"
                + "          \"company_id\": \"company_id\",\n"
                + "          \"consumption_rate\": 1.1,\n"
                + "          \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "          \"environment_id\": \"environment_id\",\n"
                + "          \"expiration_date\": \"2024-01-15T09:30:00Z\",\n"
                + "          \"feature\": {\n"
                + "            \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"description\": \"description\",\n"
                + "            \"event_subtype\": \"event_subtype\",\n"
                + "            \"feature_type\": \"feature_type\",\n"
                + "            \"icon\": \"icon\",\n"
                + "            \"id\": \"id\",\n"
                + "            \"lifecycle_phase\": \"lifecycle_phase\",\n"
                + "            \"maintainer_id\": \"maintainer_id\",\n"
                + "            \"name\": \"name\",\n"
                + "            \"plural_name\": \"plural_name\",\n"
                + "            \"singular_name\": \"singular_name\",\n"
                + "            \"trait_id\": \"trait_id\",\n"
                + "            \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "          },\n"
                + "          \"feature_id\": \"feature_id\",\n"
                + "          \"id\": \"id\",\n"
                + "          \"metric_period\": \"metric_period\",\n"
                + "          \"metric_period_month_reset\": \"metric_period_month_reset\",\n"
                + "          \"notes\": [\n"
                + "            {\n"
                + "              \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "              \"external_user_id\": \"external_user_id\",\n"
                + "              \"external_user_name\": \"external_user_name\",\n"
                + "              \"id\": \"id\",\n"
                + "              \"note\": \"note\",\n"
                + "              \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "            },\n"
                + "            {\n"
                + "              \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "              \"external_user_id\": \"external_user_id\",\n"
                + "              \"external_user_name\": \"external_user_name\",\n"
                + "              \"id\": \"id\",\n"
                + "              \"note\": \"note\",\n"
                + "              \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "            }\n"
                + "          ],\n"
                + "          \"rule_id\": \"rule_id\",\n"
                + "          \"rule_id_usage_exceeded\": \"rule_id_usage_exceeded\",\n"
                + "          \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "          \"value_bool\": true,\n"
                + "          \"value_numeric\": 1,\n"
                + "          \"value_trait\": {\n"
                + "            \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"display_name\": \"display_name\",\n"
                + "            \"entity_type\": \"entity_type\",\n"
                + "            \"hierarchy\": [\n"
                + "              \"hierarchy\",\n"
                + "              \"hierarchy\"\n"
                + "            ],\n"
                + "            \"id\": \"id\",\n"
                + "            \"trait_type\": \"trait_type\",\n"
                + "            \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "          },\n"
                + "          \"value_trait_id\": \"value_trait_id\",\n"
                + "          \"value_type\": \"value_type\"\n"
                + "        },\n"
                + "        \"credit_consumption_rate\": 1.1,\n"
                + "        \"credit_grant_counts\": {\n"
                + "          \"credit_grant_counts\": 1.1\n"
                + "        },\n"
                + "        \"credit_grant_details\": [\n"
                + "          {\n"
                + "            \"credit_type_icon\": \"credit_type_icon\",\n"
                + "            \"expires_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"grant_reason\": \"free\",\n"
                + "            \"quantity\": 1.1\n"
                + "          },\n"
                + "          {\n"
                + "            \"credit_type_icon\": \"credit_type_icon\",\n"
                + "            \"expires_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"grant_reason\": \"free\",\n"
                + "            \"quantity\": 1.1\n"
                + "          }\n"
                + "        ],\n"
                + "        \"credit_grant_reason\": \"free\",\n"
                + "        \"credit_remaining\": 1.1,\n"
                + "        \"credit_total\": 1.1,\n"
                + "        \"credit_type_icon\": \"credit_type_icon\",\n"
                + "        \"credit_used\": 1.1,\n"
                + "        \"effective_limit\": 1,\n"
                + "        \"effective_price\": 1.1,\n"
                + "        \"entitlement_expiration_date\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"entitlement_id\": \"entitlement_id\",\n"
                + "        \"entitlement_source\": \"entitlement_source\",\n"
                + "        \"entitlement_type\": \"entitlement_type\",\n"
                + "        \"feature\": {\n"
                + "          \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "          \"description\": \"description\",\n"
                + "          \"event_subtype\": \"event_subtype\",\n"
                + "          \"event_summary\": {\n"
                + "            \"company_count\": 1,\n"
                + "            \"environment_id\": \"environment_id\",\n"
                + "            \"event_count\": 1,\n"
                + "            \"event_subtype\": \"event_subtype\",\n"
                + "            \"last_seen_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"user_count\": 1\n"
                + "          },\n"
                + "          \"feature_type\": \"feature_type\",\n"
                + "          \"flags\": [\n"
                + "            {\n"
                + "              \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "              \"default_value\": true,\n"
                + "              \"description\": \"description\",\n"
                + "              \"feature\": {\n"
                + "                \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "                \"description\": \"description\",\n"
                + "                \"feature_type\": \"feature_type\",\n"
                + "                \"icon\": \"icon\",\n"
                + "                \"id\": \"id\",\n"
                + "                \"name\": \"name\",\n"
                + "                \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "              },\n"
                + "              \"feature_id\": \"feature_id\",\n"
                + "              \"flag_type\": \"flag_type\",\n"
                + "              \"id\": \"id\",\n"
                + "              \"key\": \"key\",\n"
                + "              \"last_checked_at\": \"2024-01-15T09:30:00Z\",\n"
                + "              \"maintainer_id\": \"maintainer_id\",\n"
                + "              \"name\": \"name\",\n"
                + "              \"rules\": [\n"
                + "                {\n"
                + "                  \"condition_groups\": [],\n"
                + "                  \"conditions\": [],\n"
                + "                  \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "                  \"environment_id\": \"environment_id\",\n"
                + "                  \"id\": \"id\",\n"
                + "                  \"name\": \"name\",\n"
                + "                  \"priority\": 1,\n"
                + "                  \"rule_type\": \"rule_type\",\n"
                + "                  \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "                  \"value\": true\n"
                + "                },\n"
                + "                {\n"
                + "                  \"condition_groups\": [],\n"
                + "                  \"conditions\": [],\n"
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
                + "            },\n"
                + "            {\n"
                + "              \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "              \"default_value\": true,\n"
                + "              \"description\": \"description\",\n"
                + "              \"feature\": {\n"
                + "                \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "                \"description\": \"description\",\n"
                + "                \"feature_type\": \"feature_type\",\n"
                + "                \"icon\": \"icon\",\n"
                + "                \"id\": \"id\",\n"
                + "                \"name\": \"name\",\n"
                + "                \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "              },\n"
                + "              \"feature_id\": \"feature_id\",\n"
                + "              \"flag_type\": \"flag_type\",\n"
                + "              \"id\": \"id\",\n"
                + "              \"key\": \"key\",\n"
                + "              \"last_checked_at\": \"2024-01-15T09:30:00Z\",\n"
                + "              \"maintainer_id\": \"maintainer_id\",\n"
                + "              \"name\": \"name\",\n"
                + "              \"rules\": [\n"
                + "                {\n"
                + "                  \"condition_groups\": [],\n"
                + "                  \"conditions\": [],\n"
                + "                  \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "                  \"environment_id\": \"environment_id\",\n"
                + "                  \"id\": \"id\",\n"
                + "                  \"name\": \"name\",\n"
                + "                  \"priority\": 1,\n"
                + "                  \"rule_type\": \"rule_type\",\n"
                + "                  \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "                  \"value\": true\n"
                + "                },\n"
                + "                {\n"
                + "                  \"condition_groups\": [],\n"
                + "                  \"conditions\": [],\n"
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
                + "          \"lifecycle_phase\": \"lifecycle_phase\",\n"
                + "          \"maintainer_id\": \"maintainer_id\",\n"
                + "          \"name\": \"name\",\n"
                + "          \"plans\": [\n"
                + "            {\n"
                + "              \"description\": \"description\",\n"
                + "              \"id\": \"id\",\n"
                + "              \"image_url\": \"image_url\",\n"
                + "              \"name\": \"name\"\n"
                + "            },\n"
                + "            {\n"
                + "              \"description\": \"description\",\n"
                + "              \"id\": \"id\",\n"
                + "              \"image_url\": \"image_url\",\n"
                + "              \"name\": \"name\"\n"
                + "            }\n"
                + "          ],\n"
                + "          \"plural_name\": \"plural_name\",\n"
                + "          \"singular_name\": \"singular_name\",\n"
                + "          \"trait\": {\n"
                + "            \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"display_name\": \"display_name\",\n"
                + "            \"entity_type\": \"entity_type\",\n"
                + "            \"hierarchy\": [\n"
                + "              \"hierarchy\",\n"
                + "              \"hierarchy\"\n"
                + "            ],\n"
                + "            \"id\": \"id\",\n"
                + "            \"trait_type\": \"trait_type\",\n"
                + "            \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "          },\n"
                + "          \"trait_id\": \"trait_id\",\n"
                + "          \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "        },\n"
                + "        \"has_valid_allocation\": true,\n"
                + "        \"is_unlimited\": true,\n"
                + "        \"metric_reset_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"month_reset\": \"month_reset\",\n"
                + "        \"monthly_usage_based_price\": {\n"
                + "          \"billing_scheme\": \"billing_scheme\",\n"
                + "          \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "          \"currency\": \"currency\",\n"
                + "          \"id\": \"id\",\n"
                + "          \"interval\": \"interval\",\n"
                + "          \"is_active\": true,\n"
                + "          \"meter_event_name\": \"meter_event_name\",\n"
                + "          \"meter_event_payload_key\": \"meter_event_payload_key\",\n"
                + "          \"meter_id\": \"meter_id\",\n"
                + "          \"package_size\": 1,\n"
                + "          \"price\": 1,\n"
                + "          \"price_decimal\": \"price_decimal\",\n"
                + "          \"price_external_id\": \"price_external_id\",\n"
                + "          \"price_id\": \"price_id\",\n"
                + "          \"price_tier\": [\n"
                + "            {\n"
                + "              \"flat_amount\": 1,\n"
                + "              \"per_unit_price\": 1,\n"
                + "              \"per_unit_price_decimal\": \"per_unit_price_decimal\",\n"
                + "              \"up_to\": 1\n"
                + "            },\n"
                + "            {\n"
                + "              \"flat_amount\": 1,\n"
                + "              \"per_unit_price\": 1,\n"
                + "              \"per_unit_price_decimal\": \"per_unit_price_decimal\",\n"
                + "              \"up_to\": 1\n"
                + "            }\n"
                + "          ],\n"
                + "          \"product_external_id\": \"product_external_id\",\n"
                + "          \"product_id\": \"product_id\",\n"
                + "          \"product_name\": \"product_name\",\n"
                + "          \"tiers_mode\": \"tiers_mode\",\n"
                + "          \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "          \"usage_type\": \"usage_type\"\n"
                + "        },\n"
                + "        \"overuse\": 1,\n"
                + "        \"percent_used\": 1.1,\n"
                + "        \"period\": \"period\",\n"
                + "        \"plan\": {\n"
                + "          \"audience_type\": \"audience_type\",\n"
                + "          \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "          \"description\": \"description\",\n"
                + "          \"icon\": \"icon\",\n"
                + "          \"id\": \"id\",\n"
                + "          \"name\": \"name\",\n"
                + "          \"plan_type\": \"plan_type\",\n"
                + "          \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "        },\n"
                + "        \"plan_entitlement\": {\n"
                + "          \"billing_threshold\": 1,\n"
                + "          \"consumption_rate\": 1.1,\n"
                + "          \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "          \"environment_id\": \"environment_id\",\n"
                + "          \"feature\": {\n"
                + "            \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"description\": \"description\",\n"
                + "            \"event_subtype\": \"event_subtype\",\n"
                + "            \"feature_type\": \"feature_type\",\n"
                + "            \"icon\": \"icon\",\n"
                + "            \"id\": \"id\",\n"
                + "            \"lifecycle_phase\": \"lifecycle_phase\",\n"
                + "            \"maintainer_id\": \"maintainer_id\",\n"
                + "            \"name\": \"name\",\n"
                + "            \"plural_name\": \"plural_name\",\n"
                + "            \"singular_name\": \"singular_name\",\n"
                + "            \"trait_id\": \"trait_id\",\n"
                + "            \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "          },\n"
                + "          \"feature_id\": \"feature_id\",\n"
                + "          \"id\": \"id\",\n"
                + "          \"metered_monthly_price\": {\n"
                + "            \"billing_scheme\": \"billing_scheme\",\n"
                + "            \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"currency\": \"currency\",\n"
                + "            \"id\": \"id\",\n"
                + "            \"interval\": \"interval\",\n"
                + "            \"is_active\": true,\n"
                + "            \"meter_event_name\": \"meter_event_name\",\n"
                + "            \"meter_event_payload_key\": \"meter_event_payload_key\",\n"
                + "            \"meter_id\": \"meter_id\",\n"
                + "            \"package_size\": 1,\n"
                + "            \"price\": 1,\n"
                + "            \"price_decimal\": \"price_decimal\",\n"
                + "            \"price_external_id\": \"price_external_id\",\n"
                + "            \"price_id\": \"price_id\",\n"
                + "            \"price_tier\": [\n"
                + "              {},\n"
                + "              {}\n"
                + "            ],\n"
                + "            \"product_external_id\": \"product_external_id\",\n"
                + "            \"product_id\": \"product_id\",\n"
                + "            \"product_name\": \"product_name\",\n"
                + "            \"tiers_mode\": \"tiers_mode\",\n"
                + "            \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"usage_type\": \"usage_type\"\n"
                + "          },\n"
                + "          \"metered_yearly_price\": {\n"
                + "            \"billing_scheme\": \"billing_scheme\",\n"
                + "            \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"currency\": \"currency\",\n"
                + "            \"id\": \"id\",\n"
                + "            \"interval\": \"interval\",\n"
                + "            \"is_active\": true,\n"
                + "            \"meter_event_name\": \"meter_event_name\",\n"
                + "            \"meter_event_payload_key\": \"meter_event_payload_key\",\n"
                + "            \"meter_id\": \"meter_id\",\n"
                + "            \"package_size\": 1,\n"
                + "            \"price\": 1,\n"
                + "            \"price_decimal\": \"price_decimal\",\n"
                + "            \"price_external_id\": \"price_external_id\",\n"
                + "            \"price_id\": \"price_id\",\n"
                + "            \"price_tier\": [\n"
                + "              {},\n"
                + "              {}\n"
                + "            ],\n"
                + "            \"product_external_id\": \"product_external_id\",\n"
                + "            \"product_id\": \"product_id\",\n"
                + "            \"product_name\": \"product_name\",\n"
                + "            \"tiers_mode\": \"tiers_mode\",\n"
                + "            \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"usage_type\": \"usage_type\"\n"
                + "          },\n"
                + "          \"metric_period\": \"metric_period\",\n"
                + "          \"metric_period_month_reset\": \"metric_period_month_reset\",\n"
                + "          \"plan\": {\n"
                + "            \"audience_type\": \"audience_type\",\n"
                + "            \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"description\": \"description\",\n"
                + "            \"icon\": \"icon\",\n"
                + "            \"id\": \"id\",\n"
                + "            \"name\": \"name\",\n"
                + "            \"plan_type\": \"plan_type\",\n"
                + "            \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "          },\n"
                + "          \"plan_id\": \"plan_id\",\n"
                + "          \"price_behavior\": \"price_behavior\",\n"
                + "          \"rule_id\": \"rule_id\",\n"
                + "          \"rule_id_usage_exceeded\": \"rule_id_usage_exceeded\",\n"
                + "          \"soft_limit\": 1,\n"
                + "          \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "          \"usage_based_product\": {\n"
                + "            \"account_id\": \"account_id\",\n"
                + "            \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"currency\": \"currency\",\n"
                + "            \"environment_id\": \"environment_id\",\n"
                + "            \"external_id\": \"external_id\",\n"
                + "            \"is_active\": true,\n"
                + "            \"name\": \"name\",\n"
                + "            \"price\": 1.1,\n"
                + "            \"price_decimal\": \"price_decimal\",\n"
                + "            \"product_id\": \"product_id\",\n"
                + "            \"quantity\": 1.1,\n"
                + "            \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "          },\n"
                + "          \"value_bool\": true,\n"
                + "          \"value_credit\": {\n"
                + "            \"burn_strategy\": \"burn_strategy\",\n"
                + "            \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"default_expiry_unit\": \"default_expiry_unit\",\n"
                + "            \"default_expiry_unit_count\": 1,\n"
                + "            \"default_rollover_policy\": \"default_rollover_policy\",\n"
                + "            \"description\": \"description\",\n"
                + "            \"icon\": \"icon\",\n"
                + "            \"id\": \"id\",\n"
                + "            \"name\": \"name\",\n"
                + "            \"plural_name\": \"plural_name\",\n"
                + "            \"price\": {\n"
                + "              \"currency\": \"currency\",\n"
                + "              \"external_price_id\": \"external_price_id\",\n"
                + "              \"id\": \"id\",\n"
                + "              \"interval\": \"interval\",\n"
                + "              \"price\": 1,\n"
                + "              \"scheme\": \"scheme\"\n"
                + "            },\n"
                + "            \"product\": {\n"
                + "              \"account_id\": \"account_id\",\n"
                + "              \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "              \"environment_id\": \"environment_id\",\n"
                + "              \"external_id\": \"external_id\",\n"
                + "              \"is_active\": true,\n"
                + "              \"name\": \"name\",\n"
                + "              \"price\": 1.1,\n"
                + "              \"product_id\": \"product_id\",\n"
                + "              \"quantity\": 1.1,\n"
                + "              \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "            },\n"
                + "            \"singular_name\": \"singular_name\",\n"
                + "            \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "          },\n"
                + "          \"value_numeric\": 1,\n"
                + "          \"value_trait\": {\n"
                + "            \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"display_name\": \"display_name\",\n"
                + "            \"entity_type\": \"entity_type\",\n"
                + "            \"hierarchy\": [\n"
                + "              \"hierarchy\",\n"
                + "              \"hierarchy\"\n"
                + "            ],\n"
                + "            \"id\": \"id\",\n"
                + "            \"trait_type\": \"trait_type\",\n"
                + "            \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "          },\n"
                + "          \"value_trait_id\": \"value_trait_id\",\n"
                + "          \"value_type\": \"value_type\"\n"
                + "        },\n"
                + "        \"price_behavior\": \"price_behavior\",\n"
                + "        \"soft_limit\": 1,\n"
                + "        \"usage\": 1,\n"
                + "        \"yearly_usage_based_price\": {\n"
                + "          \"billing_scheme\": \"billing_scheme\",\n"
                + "          \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "          \"currency\": \"currency\",\n"
                + "          \"id\": \"id\",\n"
                + "          \"interval\": \"interval\",\n"
                + "          \"is_active\": true,\n"
                + "          \"meter_event_name\": \"meter_event_name\",\n"
                + "          \"meter_event_payload_key\": \"meter_event_payload_key\",\n"
                + "          \"meter_id\": \"meter_id\",\n"
                + "          \"package_size\": 1,\n"
                + "          \"price\": 1,\n"
                + "          \"price_decimal\": \"price_decimal\",\n"
                + "          \"price_external_id\": \"price_external_id\",\n"
                + "          \"price_id\": \"price_id\",\n"
                + "          \"price_tier\": [\n"
                + "            {\n"
                + "              \"flat_amount\": 1,\n"
                + "              \"per_unit_price\": 1,\n"
                + "              \"per_unit_price_decimal\": \"per_unit_price_decimal\",\n"
                + "              \"up_to\": 1\n"
                + "            },\n"
                + "            {\n"
                + "              \"flat_amount\": 1,\n"
                + "              \"per_unit_price\": 1,\n"
                + "              \"per_unit_price_decimal\": \"per_unit_price_decimal\",\n"
                + "              \"up_to\": 1\n"
                + "            }\n"
                + "          ],\n"
                + "          \"product_external_id\": \"product_external_id\",\n"
                + "          \"product_id\": \"product_id\",\n"
                + "          \"product_name\": \"product_name\",\n"
                + "          \"tiers_mode\": \"tiers_mode\",\n"
                + "          \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "          \"usage_type\": \"usage_type\"\n"
                + "        }\n"
                + "      },\n"
                + "      {\n"
                + "        \"access\": true,\n"
                + "        \"allocation\": 1,\n"
                + "        \"allocation_type\": \"boolean\",\n"
                + "        \"company_override\": {\n"
                + "          \"company\": {\n"
                + "            \"add_ons\": [\n"
                + "              {\n"
                + "                \"id\": \"id\",\n"
                + "                \"name\": \"name\"\n"
                + "              },\n"
                + "              {\n"
                + "                \"id\": \"id\",\n"
                + "                \"name\": \"name\"\n"
                + "              }\n"
                + "            ],\n"
                + "            \"billing_credit_balances\": {\n"
                + "              \"billing_credit_balances\": 1.1\n"
                + "            },\n"
                + "            \"billing_subscription\": {\n"
                + "              \"cancel_at_period_end\": true,\n"
                + "              \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "              \"currency\": \"currency\",\n"
                + "              \"customer_external_id\": \"customer_external_id\",\n"
                + "              \"discounts\": [],\n"
                + "              \"id\": \"id\",\n"
                + "              \"interval\": \"interval\",\n"
                + "              \"period_end\": 1,\n"
                + "              \"period_start\": 1,\n"
                + "              \"products\": [],\n"
                + "              \"status\": \"status\",\n"
                + "              \"subscription_external_id\": \"subscription_external_id\",\n"
                + "              \"total_price\": 1\n"
                + "            },\n"
                + "            \"billing_subscriptions\": [\n"
                + "              {\n"
                + "                \"cancel_at_period_end\": true,\n"
                + "                \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "                \"currency\": \"currency\",\n"
                + "                \"customer_external_id\": \"customer_external_id\",\n"
                + "                \"discounts\": [],\n"
                + "                \"id\": \"id\",\n"
                + "                \"interval\": \"interval\",\n"
                + "                \"period_end\": 1,\n"
                + "                \"period_start\": 1,\n"
                + "                \"products\": [],\n"
                + "                \"status\": \"status\",\n"
                + "                \"subscription_external_id\": \"subscription_external_id\",\n"
                + "                \"total_price\": 1\n"
                + "              },\n"
                + "              {\n"
                + "                \"cancel_at_period_end\": true,\n"
                + "                \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "                \"currency\": \"currency\",\n"
                + "                \"customer_external_id\": \"customer_external_id\",\n"
                + "                \"discounts\": [],\n"
                + "                \"id\": \"id\",\n"
                + "                \"interval\": \"interval\",\n"
                + "                \"period_end\": 1,\n"
                + "                \"period_start\": 1,\n"
                + "                \"products\": [],\n"
                + "                \"status\": \"status\",\n"
                + "                \"subscription_external_id\": \"subscription_external_id\",\n"
                + "                \"total_price\": 1\n"
                + "              }\n"
                + "            ],\n"
                + "            \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"default_payment_method\": {\n"
                + "              \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "              \"customer_external_id\": \"customer_external_id\",\n"
                + "              \"environment_id\": \"environment_id\",\n"
                + "              \"external_id\": \"external_id\",\n"
                + "              \"id\": \"id\",\n"
                + "              \"payment_method_type\": \"payment_method_type\",\n"
                + "              \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "            },\n"
                + "            \"entity_traits\": [\n"
                + "              {\n"
                + "                \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "                \"definition_id\": \"definition_id\",\n"
                + "                \"environment_id\": \"environment_id\",\n"
                + "                \"id\": \"id\",\n"
                + "                \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "                \"value\": \"value\"\n"
                + "              },\n"
                + "              {\n"
                + "                \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "                \"definition_id\": \"definition_id\",\n"
                + "                \"environment_id\": \"environment_id\",\n"
                + "                \"id\": \"id\",\n"
                + "                \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "                \"value\": \"value\"\n"
                + "              }\n"
                + "            ],\n"
                + "            \"environment_id\": \"environment_id\",\n"
                + "            \"id\": \"id\",\n"
                + "            \"keys\": [\n"
                + "              {\n"
                + "                \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "                \"definition_id\": \"definition_id\",\n"
                + "                \"entity_id\": \"entity_id\",\n"
                + "                \"entity_type\": \"entity_type\",\n"
                + "                \"environment_id\": \"environment_id\",\n"
                + "                \"id\": \"id\",\n"
                + "                \"key\": \"key\",\n"
                + "                \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "                \"value\": \"value\"\n"
                + "              },\n"
                + "              {\n"
                + "                \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "                \"definition_id\": \"definition_id\",\n"
                + "                \"entity_id\": \"entity_id\",\n"
                + "                \"entity_type\": \"entity_type\",\n"
                + "                \"environment_id\": \"environment_id\",\n"
                + "                \"id\": \"id\",\n"
                + "                \"key\": \"key\",\n"
                + "                \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "                \"value\": \"value\"\n"
                + "              }\n"
                + "            ],\n"
                + "            \"last_seen_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"logo_url\": \"logo_url\",\n"
                + "            \"metrics\": [\n"
                + "              {\n"
                + "                \"account_id\": \"account_id\",\n"
                + "                \"captured_at_max\": \"2024-01-15T09:30:00Z\",\n"
                + "                \"captured_at_min\": \"2024-01-15T09:30:00Z\",\n"
                + "                \"company_id\": \"company_id\",\n"
                + "                \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "                \"environment_id\": \"environment_id\",\n"
                + "                \"event_subtype\": \"event_subtype\",\n"
                + "                \"month_reset\": \"month_reset\",\n"
                + "                \"period\": \"period\",\n"
                + "                \"value\": 1\n"
                + "              },\n"
                + "              {\n"
                + "                \"account_id\": \"account_id\",\n"
                + "                \"captured_at_max\": \"2024-01-15T09:30:00Z\",\n"
                + "                \"captured_at_min\": \"2024-01-15T09:30:00Z\",\n"
                + "                \"company_id\": \"company_id\",\n"
                + "                \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "                \"environment_id\": \"environment_id\",\n"
                + "                \"event_subtype\": \"event_subtype\",\n"
                + "                \"month_reset\": \"month_reset\",\n"
                + "                \"period\": \"period\",\n"
                + "                \"value\": 1\n"
                + "              }\n"
                + "            ],\n"
                + "            \"name\": \"name\",\n"
                + "            \"payment_methods\": [\n"
                + "              {\n"
                + "                \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "                \"customer_external_id\": \"customer_external_id\",\n"
                + "                \"environment_id\": \"environment_id\",\n"
                + "                \"external_id\": \"external_id\",\n"
                + "                \"id\": \"id\",\n"
                + "                \"payment_method_type\": \"payment_method_type\",\n"
                + "                \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "              },\n"
                + "              {\n"
                + "                \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "                \"customer_external_id\": \"customer_external_id\",\n"
                + "                \"environment_id\": \"environment_id\",\n"
                + "                \"external_id\": \"external_id\",\n"
                + "                \"id\": \"id\",\n"
                + "                \"payment_method_type\": \"payment_method_type\",\n"
                + "                \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "              }\n"
                + "            ],\n"
                + "            \"plan\": {\n"
                + "              \"id\": \"id\",\n"
                + "              \"name\": \"name\"\n"
                + "            },\n"
                + "            \"plans\": [\n"
                + "              {\n"
                + "                \"id\": \"id\",\n"
                + "                \"name\": \"name\"\n"
                + "              },\n"
                + "              {\n"
                + "                \"id\": \"id\",\n"
                + "                \"name\": \"name\"\n"
                + "              }\n"
                + "            ],\n"
                + "            \"rules\": [\n"
                + "              {\n"
                + "                \"account_id\": \"account_id\",\n"
                + "                \"condition_groups\": [],\n"
                + "                \"conditions\": [],\n"
                + "                \"environment_id\": \"environment_id\",\n"
                + "                \"id\": \"id\",\n"
                + "                \"name\": \"name\",\n"
                + "                \"priority\": 1,\n"
                + "                \"rule_type\": \"rule_type\",\n"
                + "                \"value\": true\n"
                + "              },\n"
                + "              {\n"
                + "                \"account_id\": \"account_id\",\n"
                + "                \"condition_groups\": [],\n"
                + "                \"conditions\": [],\n"
                + "                \"environment_id\": \"environment_id\",\n"
                + "                \"id\": \"id\",\n"
                + "                \"name\": \"name\",\n"
                + "                \"priority\": 1,\n"
                + "                \"rule_type\": \"rule_type\",\n"
                + "                \"value\": true\n"
                + "              }\n"
                + "            ],\n"
                + "            \"traits\": {\n"
                + "              \"traits\": {\n"
                + "                \"key\": \"value\"\n"
                + "              }\n"
                + "            },\n"
                + "            \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"user_count\": 1\n"
                + "          },\n"
                + "          \"company_id\": \"company_id\",\n"
                + "          \"consumption_rate\": 1.1,\n"
                + "          \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "          \"environment_id\": \"environment_id\",\n"
                + "          \"expiration_date\": \"2024-01-15T09:30:00Z\",\n"
                + "          \"feature\": {\n"
                + "            \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"description\": \"description\",\n"
                + "            \"event_subtype\": \"event_subtype\",\n"
                + "            \"feature_type\": \"feature_type\",\n"
                + "            \"icon\": \"icon\",\n"
                + "            \"id\": \"id\",\n"
                + "            \"lifecycle_phase\": \"lifecycle_phase\",\n"
                + "            \"maintainer_id\": \"maintainer_id\",\n"
                + "            \"name\": \"name\",\n"
                + "            \"plural_name\": \"plural_name\",\n"
                + "            \"singular_name\": \"singular_name\",\n"
                + "            \"trait_id\": \"trait_id\",\n"
                + "            \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "          },\n"
                + "          \"feature_id\": \"feature_id\",\n"
                + "          \"id\": \"id\",\n"
                + "          \"metric_period\": \"metric_period\",\n"
                + "          \"metric_period_month_reset\": \"metric_period_month_reset\",\n"
                + "          \"notes\": [\n"
                + "            {\n"
                + "              \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "              \"external_user_id\": \"external_user_id\",\n"
                + "              \"external_user_name\": \"external_user_name\",\n"
                + "              \"id\": \"id\",\n"
                + "              \"note\": \"note\",\n"
                + "              \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "            },\n"
                + "            {\n"
                + "              \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "              \"external_user_id\": \"external_user_id\",\n"
                + "              \"external_user_name\": \"external_user_name\",\n"
                + "              \"id\": \"id\",\n"
                + "              \"note\": \"note\",\n"
                + "              \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "            }\n"
                + "          ],\n"
                + "          \"rule_id\": \"rule_id\",\n"
                + "          \"rule_id_usage_exceeded\": \"rule_id_usage_exceeded\",\n"
                + "          \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "          \"value_bool\": true,\n"
                + "          \"value_numeric\": 1,\n"
                + "          \"value_trait\": {\n"
                + "            \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"display_name\": \"display_name\",\n"
                + "            \"entity_type\": \"entity_type\",\n"
                + "            \"hierarchy\": [\n"
                + "              \"hierarchy\",\n"
                + "              \"hierarchy\"\n"
                + "            ],\n"
                + "            \"id\": \"id\",\n"
                + "            \"trait_type\": \"trait_type\",\n"
                + "            \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "          },\n"
                + "          \"value_trait_id\": \"value_trait_id\",\n"
                + "          \"value_type\": \"value_type\"\n"
                + "        },\n"
                + "        \"credit_consumption_rate\": 1.1,\n"
                + "        \"credit_grant_counts\": {\n"
                + "          \"credit_grant_counts\": 1.1\n"
                + "        },\n"
                + "        \"credit_grant_details\": [\n"
                + "          {\n"
                + "            \"credit_type_icon\": \"credit_type_icon\",\n"
                + "            \"expires_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"grant_reason\": \"free\",\n"
                + "            \"quantity\": 1.1\n"
                + "          },\n"
                + "          {\n"
                + "            \"credit_type_icon\": \"credit_type_icon\",\n"
                + "            \"expires_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"grant_reason\": \"free\",\n"
                + "            \"quantity\": 1.1\n"
                + "          }\n"
                + "        ],\n"
                + "        \"credit_grant_reason\": \"free\",\n"
                + "        \"credit_remaining\": 1.1,\n"
                + "        \"credit_total\": 1.1,\n"
                + "        \"credit_type_icon\": \"credit_type_icon\",\n"
                + "        \"credit_used\": 1.1,\n"
                + "        \"effective_limit\": 1,\n"
                + "        \"effective_price\": 1.1,\n"
                + "        \"entitlement_expiration_date\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"entitlement_id\": \"entitlement_id\",\n"
                + "        \"entitlement_source\": \"entitlement_source\",\n"
                + "        \"entitlement_type\": \"entitlement_type\",\n"
                + "        \"feature\": {\n"
                + "          \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "          \"description\": \"description\",\n"
                + "          \"event_subtype\": \"event_subtype\",\n"
                + "          \"event_summary\": {\n"
                + "            \"company_count\": 1,\n"
                + "            \"environment_id\": \"environment_id\",\n"
                + "            \"event_count\": 1,\n"
                + "            \"event_subtype\": \"event_subtype\",\n"
                + "            \"last_seen_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"user_count\": 1\n"
                + "          },\n"
                + "          \"feature_type\": \"feature_type\",\n"
                + "          \"flags\": [\n"
                + "            {\n"
                + "              \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "              \"default_value\": true,\n"
                + "              \"description\": \"description\",\n"
                + "              \"feature\": {\n"
                + "                \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "                \"description\": \"description\",\n"
                + "                \"feature_type\": \"feature_type\",\n"
                + "                \"icon\": \"icon\",\n"
                + "                \"id\": \"id\",\n"
                + "                \"name\": \"name\",\n"
                + "                \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "              },\n"
                + "              \"feature_id\": \"feature_id\",\n"
                + "              \"flag_type\": \"flag_type\",\n"
                + "              \"id\": \"id\",\n"
                + "              \"key\": \"key\",\n"
                + "              \"last_checked_at\": \"2024-01-15T09:30:00Z\",\n"
                + "              \"maintainer_id\": \"maintainer_id\",\n"
                + "              \"name\": \"name\",\n"
                + "              \"rules\": [\n"
                + "                {\n"
                + "                  \"condition_groups\": [],\n"
                + "                  \"conditions\": [],\n"
                + "                  \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "                  \"environment_id\": \"environment_id\",\n"
                + "                  \"id\": \"id\",\n"
                + "                  \"name\": \"name\",\n"
                + "                  \"priority\": 1,\n"
                + "                  \"rule_type\": \"rule_type\",\n"
                + "                  \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "                  \"value\": true\n"
                + "                },\n"
                + "                {\n"
                + "                  \"condition_groups\": [],\n"
                + "                  \"conditions\": [],\n"
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
                + "            },\n"
                + "            {\n"
                + "              \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "              \"default_value\": true,\n"
                + "              \"description\": \"description\",\n"
                + "              \"feature\": {\n"
                + "                \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "                \"description\": \"description\",\n"
                + "                \"feature_type\": \"feature_type\",\n"
                + "                \"icon\": \"icon\",\n"
                + "                \"id\": \"id\",\n"
                + "                \"name\": \"name\",\n"
                + "                \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "              },\n"
                + "              \"feature_id\": \"feature_id\",\n"
                + "              \"flag_type\": \"flag_type\",\n"
                + "              \"id\": \"id\",\n"
                + "              \"key\": \"key\",\n"
                + "              \"last_checked_at\": \"2024-01-15T09:30:00Z\",\n"
                + "              \"maintainer_id\": \"maintainer_id\",\n"
                + "              \"name\": \"name\",\n"
                + "              \"rules\": [\n"
                + "                {\n"
                + "                  \"condition_groups\": [],\n"
                + "                  \"conditions\": [],\n"
                + "                  \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "                  \"environment_id\": \"environment_id\",\n"
                + "                  \"id\": \"id\",\n"
                + "                  \"name\": \"name\",\n"
                + "                  \"priority\": 1,\n"
                + "                  \"rule_type\": \"rule_type\",\n"
                + "                  \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "                  \"value\": true\n"
                + "                },\n"
                + "                {\n"
                + "                  \"condition_groups\": [],\n"
                + "                  \"conditions\": [],\n"
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
                + "          \"lifecycle_phase\": \"lifecycle_phase\",\n"
                + "          \"maintainer_id\": \"maintainer_id\",\n"
                + "          \"name\": \"name\",\n"
                + "          \"plans\": [\n"
                + "            {\n"
                + "              \"description\": \"description\",\n"
                + "              \"id\": \"id\",\n"
                + "              \"image_url\": \"image_url\",\n"
                + "              \"name\": \"name\"\n"
                + "            },\n"
                + "            {\n"
                + "              \"description\": \"description\",\n"
                + "              \"id\": \"id\",\n"
                + "              \"image_url\": \"image_url\",\n"
                + "              \"name\": \"name\"\n"
                + "            }\n"
                + "          ],\n"
                + "          \"plural_name\": \"plural_name\",\n"
                + "          \"singular_name\": \"singular_name\",\n"
                + "          \"trait\": {\n"
                + "            \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"display_name\": \"display_name\",\n"
                + "            \"entity_type\": \"entity_type\",\n"
                + "            \"hierarchy\": [\n"
                + "              \"hierarchy\",\n"
                + "              \"hierarchy\"\n"
                + "            ],\n"
                + "            \"id\": \"id\",\n"
                + "            \"trait_type\": \"trait_type\",\n"
                + "            \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "          },\n"
                + "          \"trait_id\": \"trait_id\",\n"
                + "          \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "        },\n"
                + "        \"has_valid_allocation\": true,\n"
                + "        \"is_unlimited\": true,\n"
                + "        \"metric_reset_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"month_reset\": \"month_reset\",\n"
                + "        \"monthly_usage_based_price\": {\n"
                + "          \"billing_scheme\": \"billing_scheme\",\n"
                + "          \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "          \"currency\": \"currency\",\n"
                + "          \"id\": \"id\",\n"
                + "          \"interval\": \"interval\",\n"
                + "          \"is_active\": true,\n"
                + "          \"meter_event_name\": \"meter_event_name\",\n"
                + "          \"meter_event_payload_key\": \"meter_event_payload_key\",\n"
                + "          \"meter_id\": \"meter_id\",\n"
                + "          \"package_size\": 1,\n"
                + "          \"price\": 1,\n"
                + "          \"price_decimal\": \"price_decimal\",\n"
                + "          \"price_external_id\": \"price_external_id\",\n"
                + "          \"price_id\": \"price_id\",\n"
                + "          \"price_tier\": [\n"
                + "            {\n"
                + "              \"flat_amount\": 1,\n"
                + "              \"per_unit_price\": 1,\n"
                + "              \"per_unit_price_decimal\": \"per_unit_price_decimal\",\n"
                + "              \"up_to\": 1\n"
                + "            },\n"
                + "            {\n"
                + "              \"flat_amount\": 1,\n"
                + "              \"per_unit_price\": 1,\n"
                + "              \"per_unit_price_decimal\": \"per_unit_price_decimal\",\n"
                + "              \"up_to\": 1\n"
                + "            }\n"
                + "          ],\n"
                + "          \"product_external_id\": \"product_external_id\",\n"
                + "          \"product_id\": \"product_id\",\n"
                + "          \"product_name\": \"product_name\",\n"
                + "          \"tiers_mode\": \"tiers_mode\",\n"
                + "          \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "          \"usage_type\": \"usage_type\"\n"
                + "        },\n"
                + "        \"overuse\": 1,\n"
                + "        \"percent_used\": 1.1,\n"
                + "        \"period\": \"period\",\n"
                + "        \"plan\": {\n"
                + "          \"audience_type\": \"audience_type\",\n"
                + "          \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "          \"description\": \"description\",\n"
                + "          \"icon\": \"icon\",\n"
                + "          \"id\": \"id\",\n"
                + "          \"name\": \"name\",\n"
                + "          \"plan_type\": \"plan_type\",\n"
                + "          \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "        },\n"
                + "        \"plan_entitlement\": {\n"
                + "          \"billing_threshold\": 1,\n"
                + "          \"consumption_rate\": 1.1,\n"
                + "          \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "          \"environment_id\": \"environment_id\",\n"
                + "          \"feature\": {\n"
                + "            \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"description\": \"description\",\n"
                + "            \"event_subtype\": \"event_subtype\",\n"
                + "            \"feature_type\": \"feature_type\",\n"
                + "            \"icon\": \"icon\",\n"
                + "            \"id\": \"id\",\n"
                + "            \"lifecycle_phase\": \"lifecycle_phase\",\n"
                + "            \"maintainer_id\": \"maintainer_id\",\n"
                + "            \"name\": \"name\",\n"
                + "            \"plural_name\": \"plural_name\",\n"
                + "            \"singular_name\": \"singular_name\",\n"
                + "            \"trait_id\": \"trait_id\",\n"
                + "            \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "          },\n"
                + "          \"feature_id\": \"feature_id\",\n"
                + "          \"id\": \"id\",\n"
                + "          \"metered_monthly_price\": {\n"
                + "            \"billing_scheme\": \"billing_scheme\",\n"
                + "            \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"currency\": \"currency\",\n"
                + "            \"id\": \"id\",\n"
                + "            \"interval\": \"interval\",\n"
                + "            \"is_active\": true,\n"
                + "            \"meter_event_name\": \"meter_event_name\",\n"
                + "            \"meter_event_payload_key\": \"meter_event_payload_key\",\n"
                + "            \"meter_id\": \"meter_id\",\n"
                + "            \"package_size\": 1,\n"
                + "            \"price\": 1,\n"
                + "            \"price_decimal\": \"price_decimal\",\n"
                + "            \"price_external_id\": \"price_external_id\",\n"
                + "            \"price_id\": \"price_id\",\n"
                + "            \"price_tier\": [\n"
                + "              {},\n"
                + "              {}\n"
                + "            ],\n"
                + "            \"product_external_id\": \"product_external_id\",\n"
                + "            \"product_id\": \"product_id\",\n"
                + "            \"product_name\": \"product_name\",\n"
                + "            \"tiers_mode\": \"tiers_mode\",\n"
                + "            \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"usage_type\": \"usage_type\"\n"
                + "          },\n"
                + "          \"metered_yearly_price\": {\n"
                + "            \"billing_scheme\": \"billing_scheme\",\n"
                + "            \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"currency\": \"currency\",\n"
                + "            \"id\": \"id\",\n"
                + "            \"interval\": \"interval\",\n"
                + "            \"is_active\": true,\n"
                + "            \"meter_event_name\": \"meter_event_name\",\n"
                + "            \"meter_event_payload_key\": \"meter_event_payload_key\",\n"
                + "            \"meter_id\": \"meter_id\",\n"
                + "            \"package_size\": 1,\n"
                + "            \"price\": 1,\n"
                + "            \"price_decimal\": \"price_decimal\",\n"
                + "            \"price_external_id\": \"price_external_id\",\n"
                + "            \"price_id\": \"price_id\",\n"
                + "            \"price_tier\": [\n"
                + "              {},\n"
                + "              {}\n"
                + "            ],\n"
                + "            \"product_external_id\": \"product_external_id\",\n"
                + "            \"product_id\": \"product_id\",\n"
                + "            \"product_name\": \"product_name\",\n"
                + "            \"tiers_mode\": \"tiers_mode\",\n"
                + "            \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"usage_type\": \"usage_type\"\n"
                + "          },\n"
                + "          \"metric_period\": \"metric_period\",\n"
                + "          \"metric_period_month_reset\": \"metric_period_month_reset\",\n"
                + "          \"plan\": {\n"
                + "            \"audience_type\": \"audience_type\",\n"
                + "            \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"description\": \"description\",\n"
                + "            \"icon\": \"icon\",\n"
                + "            \"id\": \"id\",\n"
                + "            \"name\": \"name\",\n"
                + "            \"plan_type\": \"plan_type\",\n"
                + "            \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "          },\n"
                + "          \"plan_id\": \"plan_id\",\n"
                + "          \"price_behavior\": \"price_behavior\",\n"
                + "          \"rule_id\": \"rule_id\",\n"
                + "          \"rule_id_usage_exceeded\": \"rule_id_usage_exceeded\",\n"
                + "          \"soft_limit\": 1,\n"
                + "          \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "          \"usage_based_product\": {\n"
                + "            \"account_id\": \"account_id\",\n"
                + "            \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"currency\": \"currency\",\n"
                + "            \"environment_id\": \"environment_id\",\n"
                + "            \"external_id\": \"external_id\",\n"
                + "            \"is_active\": true,\n"
                + "            \"name\": \"name\",\n"
                + "            \"price\": 1.1,\n"
                + "            \"price_decimal\": \"price_decimal\",\n"
                + "            \"product_id\": \"product_id\",\n"
                + "            \"quantity\": 1.1,\n"
                + "            \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "          },\n"
                + "          \"value_bool\": true,\n"
                + "          \"value_credit\": {\n"
                + "            \"burn_strategy\": \"burn_strategy\",\n"
                + "            \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"default_expiry_unit\": \"default_expiry_unit\",\n"
                + "            \"default_expiry_unit_count\": 1,\n"
                + "            \"default_rollover_policy\": \"default_rollover_policy\",\n"
                + "            \"description\": \"description\",\n"
                + "            \"icon\": \"icon\",\n"
                + "            \"id\": \"id\",\n"
                + "            \"name\": \"name\",\n"
                + "            \"plural_name\": \"plural_name\",\n"
                + "            \"price\": {\n"
                + "              \"currency\": \"currency\",\n"
                + "              \"external_price_id\": \"external_price_id\",\n"
                + "              \"id\": \"id\",\n"
                + "              \"interval\": \"interval\",\n"
                + "              \"price\": 1,\n"
                + "              \"scheme\": \"scheme\"\n"
                + "            },\n"
                + "            \"product\": {\n"
                + "              \"account_id\": \"account_id\",\n"
                + "              \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "              \"environment_id\": \"environment_id\",\n"
                + "              \"external_id\": \"external_id\",\n"
                + "              \"is_active\": true,\n"
                + "              \"name\": \"name\",\n"
                + "              \"price\": 1.1,\n"
                + "              \"product_id\": \"product_id\",\n"
                + "              \"quantity\": 1.1,\n"
                + "              \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "            },\n"
                + "            \"singular_name\": \"singular_name\",\n"
                + "            \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "          },\n"
                + "          \"value_numeric\": 1,\n"
                + "          \"value_trait\": {\n"
                + "            \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"display_name\": \"display_name\",\n"
                + "            \"entity_type\": \"entity_type\",\n"
                + "            \"hierarchy\": [\n"
                + "              \"hierarchy\",\n"
                + "              \"hierarchy\"\n"
                + "            ],\n"
                + "            \"id\": \"id\",\n"
                + "            \"trait_type\": \"trait_type\",\n"
                + "            \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "          },\n"
                + "          \"value_trait_id\": \"value_trait_id\",\n"
                + "          \"value_type\": \"value_type\"\n"
                + "        },\n"
                + "        \"price_behavior\": \"price_behavior\",\n"
                + "        \"soft_limit\": 1,\n"
                + "        \"usage\": 1,\n"
                + "        \"yearly_usage_based_price\": {\n"
                + "          \"billing_scheme\": \"billing_scheme\",\n"
                + "          \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "          \"currency\": \"currency\",\n"
                + "          \"id\": \"id\",\n"
                + "          \"interval\": \"interval\",\n"
                + "          \"is_active\": true,\n"
                + "          \"meter_event_name\": \"meter_event_name\",\n"
                + "          \"meter_event_payload_key\": \"meter_event_payload_key\",\n"
                + "          \"meter_id\": \"meter_id\",\n"
                + "          \"package_size\": 1,\n"
                + "          \"price\": 1,\n"
                + "          \"price_decimal\": \"price_decimal\",\n"
                + "          \"price_external_id\": \"price_external_id\",\n"
                + "          \"price_id\": \"price_id\",\n"
                + "          \"price_tier\": [\n"
                + "            {\n"
                + "              \"flat_amount\": 1,\n"
                + "              \"per_unit_price\": 1,\n"
                + "              \"per_unit_price_decimal\": \"per_unit_price_decimal\",\n"
                + "              \"up_to\": 1\n"
                + "            },\n"
                + "            {\n"
                + "              \"flat_amount\": 1,\n"
                + "              \"per_unit_price\": 1,\n"
                + "              \"per_unit_price_decimal\": \"per_unit_price_decimal\",\n"
                + "              \"up_to\": 1\n"
                + "            }\n"
                + "          ],\n"
                + "          \"product_external_id\": \"product_external_id\",\n"
                + "          \"product_id\": \"product_id\",\n"
                + "          \"product_name\": \"product_name\",\n"
                + "          \"tiers_mode\": \"tiers_mode\",\n"
                + "          \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "          \"usage_type\": \"usage_type\"\n"
                + "        }\n"
                + "      }\n"
                + "    ]\n"
                + "  },\n"
                + "  \"params\": {\n"
                + "    \"keys\": {\n"
                + "      \"keys\": \"keys\"\n"
                + "    }\n"
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
