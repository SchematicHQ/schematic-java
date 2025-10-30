package com.schematic.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.schematic.api.core.ObjectMappers;
import com.schematic.api.resources.plans.requests.CountPlansRequest;
import com.schematic.api.resources.plans.requests.CreatePlanRequestBody;
import com.schematic.api.resources.plans.requests.ListPlanIssuesRequest;
import com.schematic.api.resources.plans.requests.ListPlansRequest;
import com.schematic.api.resources.plans.requests.UpdateCompanyPlansRequestBody;
import com.schematic.api.resources.plans.requests.UpdatePlanRequestBody;
import com.schematic.api.resources.plans.requests.UpsertBillingProductRequestBody;
import com.schematic.api.resources.plans.types.CountPlansRequestPlanType;
import com.schematic.api.resources.plans.types.CountPlansResponse;
import com.schematic.api.resources.plans.types.CreatePlanRequestBodyPlanType;
import com.schematic.api.resources.plans.types.CreatePlanResponse;
import com.schematic.api.resources.plans.types.DeletePlanResponse;
import com.schematic.api.resources.plans.types.GetPlanResponse;
import com.schematic.api.resources.plans.types.ListPlanIssuesResponse;
import com.schematic.api.resources.plans.types.ListPlansRequestPlanType;
import com.schematic.api.resources.plans.types.ListPlansResponse;
import com.schematic.api.resources.plans.types.UpdateCompanyPlansResponse;
import com.schematic.api.resources.plans.types.UpdatePlanResponse;
import com.schematic.api.resources.plans.types.UpsertBillingProductPlanResponse;
import com.schematic.api.resources.plans.types.UpsertBillingProductRequestBodyChargeType;
import java.util.Arrays;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PlansWireTest {
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
    public void testUpdateCompanyPlans() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":{\"add_ons\":[{\"id\":\"id\",\"name\":\"name\"}],\"billing_credit_balances\":{\"key\":1.1},\"billing_subscription\":{\"cancel_at\":1,\"cancel_at_period_end\":true,\"company_id\":\"company_id\",\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"customer_external_id\":\"customer_external_id\",\"default_payment_method_id\":\"default_payment_method_id\",\"discounts\":[{\"coupon_id\":\"coupon_id\",\"coupon_name\":\"coupon_name\",\"discount_external_id\":\"discount_external_id\",\"duration\":\"duration\",\"is_active\":true,\"started_at\":\"2024-01-15T09:30:00Z\",\"subscription_external_id\":\"subscription_external_id\"}],\"expired_at\":\"2024-01-15T09:30:00Z\",\"id\":\"id\",\"interval\":\"interval\",\"latest_invoice\":{\"amount_due\":1,\"amount_paid\":1,\"amount_remaining\":1,\"collection_method\":\"collection_method\",\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"customer_external_id\":\"customer_external_id\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"subtotal\":1,\"updated_at\":\"2024-01-15T09:30:00Z\"},\"metadata\":{\"key\":\"value\"},\"payment_method\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"customer_external_id\":\"customer_external_id\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"id\":\"id\",\"payment_method_type\":\"payment_method_type\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"period_end\":1,\"period_start\":1,\"products\":[{\"billing_scheme\":\"billing_scheme\",\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"id\":\"id\",\"interval\":\"interval\",\"name\":\"name\",\"package_size\":1,\"price\":1,\"price_external_id\":\"price_external_id\",\"price_id\":\"price_id\",\"price_tier\":[{}],\"quantity\":1.1,\"subscription_id\":\"subscription_id\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"usage_type\":\"usage_type\"}],\"status\":\"status\",\"subscription_external_id\":\"subscription_external_id\",\"total_price\":1,\"trial_end\":1,\"trial_end_setting\":\"trial_end_setting\"},\"billing_subscriptions\":[{\"cancel_at_period_end\":true,\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"customer_external_id\":\"customer_external_id\",\"discounts\":[{\"coupon_id\":\"coupon_id\",\"coupon_name\":\"coupon_name\",\"discount_external_id\":\"discount_external_id\",\"duration\":\"duration\",\"is_active\":true,\"started_at\":\"2024-01-15T09:30:00Z\",\"subscription_external_id\":\"subscription_external_id\"}],\"id\":\"id\",\"interval\":\"interval\",\"period_end\":1,\"period_start\":1,\"products\":[{\"billing_scheme\":\"billing_scheme\",\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"id\":\"id\",\"interval\":\"interval\",\"name\":\"name\",\"package_size\":1,\"price\":1,\"price_external_id\":\"price_external_id\",\"price_id\":\"price_id\",\"price_tier\":[{}],\"quantity\":1.1,\"subscription_id\":\"subscription_id\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"usage_type\":\"usage_type\"}],\"status\":\"status\",\"subscription_external_id\":\"subscription_external_id\",\"total_price\":1}],\"created_at\":\"2024-01-15T09:30:00Z\",\"default_payment_method\":{\"account_last4\":\"account_last4\",\"account_name\":\"account_name\",\"bank_name\":\"bank_name\",\"billing_email\":\"billing_email\",\"billing_name\":\"billing_name\",\"card_brand\":\"card_brand\",\"card_exp_month\":1,\"card_exp_year\":1,\"card_last4\":\"card_last4\",\"company_id\":\"company_id\",\"created_at\":\"2024-01-15T09:30:00Z\",\"customer_external_id\":\"customer_external_id\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"id\":\"id\",\"payment_method_type\":\"payment_method_type\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"entity_traits\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"definition_id\":\"definition_id\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"value\":\"value\"}],\"environment_id\":\"environment_id\",\"id\":\"id\",\"keys\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"definition_id\":\"definition_id\",\"entity_id\":\"entity_id\",\"entity_type\":\"entity_type\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"key\":\"key\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"value\":\"value\"}],\"last_seen_at\":\"2024-01-15T09:30:00Z\",\"logo_url\":\"logo_url\",\"metrics\":[{\"account_id\":\"account_id\",\"captured_at_max\":\"2024-01-15T09:30:00Z\",\"captured_at_min\":\"2024-01-15T09:30:00Z\",\"company_id\":\"company_id\",\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"event_subtype\":\"event_subtype\",\"month_reset\":\"month_reset\",\"period\":\"period\",\"value\":1}],\"name\":\"name\",\"payment_methods\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"customer_external_id\":\"customer_external_id\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"id\":\"id\",\"payment_method_type\":\"payment_method_type\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"plan\":{\"added_on\":\"2024-01-15T09:30:00Z\",\"billing_product_external_id\":\"billing_product_external_id\",\"billing_product_id\":\"billing_product_id\",\"description\":\"description\",\"id\":\"id\",\"image_url\":\"image_url\",\"name\":\"name\",\"plan_period\":\"plan_period\",\"plan_price\":1},\"plans\":[{\"id\":\"id\",\"name\":\"name\"}],\"rules\":[{\"account_id\":\"account_id\",\"condition_groups\":[{\"conditions\":[{\"account_id\":\"account_id\",\"condition_type\":\"condition_type\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"operator\":\"operator\",\"resource_ids\":[\"resource_ids\"],\"trait_value\":\"trait_value\"}]}],\"conditions\":[{\"account_id\":\"account_id\",\"condition_type\":\"condition_type\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"operator\":\"operator\",\"resource_ids\":[\"resource_ids\"],\"trait_value\":\"trait_value\"}],\"environment_id\":\"environment_id\",\"id\":\"id\",\"name\":\"name\",\"priority\":1,\"rule_type\":\"rule_type\",\"value\":true}],\"traits\":{\"key\":\"value\"},\"updated_at\":\"2024-01-15T09:30:00Z\",\"user_count\":1},\"params\":{\"key\":\"value\"}}"));
        UpdateCompanyPlansResponse response = client.plans()
                .updateCompanyPlans(
                        "company_plan_id",
                        UpdateCompanyPlansRequestBody.builder()
                                .addOnIds(Arrays.asList("add_on_ids"))
                                .build());
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("PUT", request.getMethod());
        // Validate request body
        String actualRequestBody = request.getBody().readUtf8();
        String expectedRequestBody = "" + "{\n" + "  \"add_on_ids\": [\n" + "    \"add_on_ids\"\n" + "  ]\n" + "}";
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
                + "    \"add_ons\": [\n"
                + "      {\n"
                + "        \"id\": \"id\",\n"
                + "        \"name\": \"name\"\n"
                + "      }\n"
                + "    ],\n"
                + "    \"billing_credit_balances\": {\n"
                + "      \"key\": 1.1\n"
                + "    },\n"
                + "    \"billing_subscription\": {\n"
                + "      \"cancel_at\": 1,\n"
                + "      \"cancel_at_period_end\": true,\n"
                + "      \"company_id\": \"company_id\",\n"
                + "      \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"currency\": \"currency\",\n"
                + "      \"customer_external_id\": \"customer_external_id\",\n"
                + "      \"default_payment_method_id\": \"default_payment_method_id\",\n"
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
                + "      \"id\": \"id\",\n"
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
                + "      \"metadata\": {\n"
                + "        \"key\": \"value\"\n"
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
                + "      \"period_end\": 1,\n"
                + "      \"period_start\": 1,\n"
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
                + "      \"trial_end\": 1,\n"
                + "      \"trial_end_setting\": \"trial_end_setting\"\n"
                + "    },\n"
                + "    \"billing_subscriptions\": [\n"
                + "      {\n"
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
                + "      }\n"
                + "    ],\n"
                + "    \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"default_payment_method\": {\n"
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
                + "    },\n"
                + "    \"entity_traits\": [\n"
                + "      {\n"
                + "        \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"definition_id\": \"definition_id\",\n"
                + "        \"environment_id\": \"environment_id\",\n"
                + "        \"id\": \"id\",\n"
                + "        \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"value\": \"value\"\n"
                + "      }\n"
                + "    ],\n"
                + "    \"environment_id\": \"environment_id\",\n"
                + "    \"id\": \"id\",\n"
                + "    \"keys\": [\n"
                + "      {\n"
                + "        \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"definition_id\": \"definition_id\",\n"
                + "        \"entity_id\": \"entity_id\",\n"
                + "        \"entity_type\": \"entity_type\",\n"
                + "        \"environment_id\": \"environment_id\",\n"
                + "        \"id\": \"id\",\n"
                + "        \"key\": \"key\",\n"
                + "        \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"value\": \"value\"\n"
                + "      }\n"
                + "    ],\n"
                + "    \"last_seen_at\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"logo_url\": \"logo_url\",\n"
                + "    \"metrics\": [\n"
                + "      {\n"
                + "        \"account_id\": \"account_id\",\n"
                + "        \"captured_at_max\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"captured_at_min\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"company_id\": \"company_id\",\n"
                + "        \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"environment_id\": \"environment_id\",\n"
                + "        \"event_subtype\": \"event_subtype\",\n"
                + "        \"month_reset\": \"month_reset\",\n"
                + "        \"period\": \"period\",\n"
                + "        \"value\": 1\n"
                + "      }\n"
                + "    ],\n"
                + "    \"name\": \"name\",\n"
                + "    \"payment_methods\": [\n"
                + "      {\n"
                + "        \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"customer_external_id\": \"customer_external_id\",\n"
                + "        \"environment_id\": \"environment_id\",\n"
                + "        \"external_id\": \"external_id\",\n"
                + "        \"id\": \"id\",\n"
                + "        \"payment_method_type\": \"payment_method_type\",\n"
                + "        \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "      }\n"
                + "    ],\n"
                + "    \"plan\": {\n"
                + "      \"added_on\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"billing_product_external_id\": \"billing_product_external_id\",\n"
                + "      \"billing_product_id\": \"billing_product_id\",\n"
                + "      \"description\": \"description\",\n"
                + "      \"id\": \"id\",\n"
                + "      \"image_url\": \"image_url\",\n"
                + "      \"name\": \"name\",\n"
                + "      \"plan_period\": \"plan_period\",\n"
                + "      \"plan_price\": 1\n"
                + "    },\n"
                + "    \"plans\": [\n"
                + "      {\n"
                + "        \"id\": \"id\",\n"
                + "        \"name\": \"name\"\n"
                + "      }\n"
                + "    ],\n"
                + "    \"rules\": [\n"
                + "      {\n"
                + "        \"account_id\": \"account_id\",\n"
                + "        \"condition_groups\": [\n"
                + "          {\n"
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
                + "            ]\n"
                + "          }\n"
                + "        ],\n"
                + "        \"conditions\": [\n"
                + "          {\n"
                + "            \"account_id\": \"account_id\",\n"
                + "            \"condition_type\": \"condition_type\",\n"
                + "            \"environment_id\": \"environment_id\",\n"
                + "            \"id\": \"id\",\n"
                + "            \"operator\": \"operator\",\n"
                + "            \"resource_ids\": [\n"
                + "              \"resource_ids\"\n"
                + "            ],\n"
                + "            \"trait_value\": \"trait_value\"\n"
                + "          }\n"
                + "        ],\n"
                + "        \"environment_id\": \"environment_id\",\n"
                + "        \"id\": \"id\",\n"
                + "        \"name\": \"name\",\n"
                + "        \"priority\": 1,\n"
                + "        \"rule_type\": \"rule_type\",\n"
                + "        \"value\": true\n"
                + "      }\n"
                + "    ],\n"
                + "    \"traits\": {\n"
                + "      \"key\": \"value\"\n"
                + "    },\n"
                + "    \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"user_count\": 1\n"
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
    public void testListPlans() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":[{\"billing_product\":{\"account_id\":\"account_id\",\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"is_active\":true,\"name\":\"name\",\"price\":1.1,\"prices\":[{\"currency\":\"currency\",\"external_price_id\":\"external_price_id\",\"id\":\"id\",\"interval\":\"interval\",\"price\":1,\"scheme\":\"scheme\"}],\"product_id\":\"product_id\",\"quantity\":1.1,\"subscription_count\":1,\"updated_at\":\"2024-01-15T09:30:00Z\"},\"charge_type\":\"charge_type\",\"company_count\":1,\"controlled_by\":\"controlled_by\",\"created_at\":\"2024-01-15T09:30:00Z\",\"description\":\"description\",\"features\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"description\":\"description\",\"feature_type\":\"feature_type\",\"flags\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"default_value\":true,\"description\":\"description\",\"flag_type\":\"flag_type\",\"id\":\"id\",\"key\":\"key\",\"name\":\"name\",\"rules\":[{\"condition_groups\":[{\"conditions\":[{\"condition_type\":\"condition_type\",\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"operator\":\"operator\",\"resource_ids\":[\"resource_ids\"],\"resources\":[{\"id\":\"id\",\"name\":\"name\"}],\"rule_id\":\"rule_id\",\"trait_value\":\"trait_value\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"rule_id\":\"rule_id\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"conditions\":[{\"condition_type\":\"condition_type\",\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"operator\":\"operator\",\"resource_ids\":[\"resource_ids\"],\"resources\":[{\"id\":\"id\",\"name\":\"name\"}],\"rule_id\":\"rule_id\",\"trait_value\":\"trait_value\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"name\":\"name\",\"priority\":1,\"rule_type\":\"rule_type\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"value\":true}],\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"icon\":\"icon\",\"id\":\"id\",\"name\":\"name\",\"plans\":[{\"id\":\"id\",\"name\":\"name\"}],\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"icon\":\"icon\",\"id\":\"id\",\"included_credit_grants\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"credit_amount\":1,\"credit_id\":\"credit_id\",\"credit_name\":\"credit_name\",\"id\":\"id\",\"plan_id\":\"plan_id\",\"plan_name\":\"plan_name\",\"reset_cadence\":\"reset_cadence\",\"reset_start\":\"reset_start\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"is_default\":true,\"is_free\":true,\"is_trialable\":true,\"monthly_price\":{\"currency\":\"currency\",\"external_price_id\":\"external_price_id\",\"id\":\"id\",\"interval\":\"interval\",\"price\":1,\"scheme\":\"scheme\"},\"name\":\"name\",\"one_time_price\":{\"currency\":\"currency\",\"external_price_id\":\"external_price_id\",\"id\":\"id\",\"interval\":\"interval\",\"price\":1,\"scheme\":\"scheme\"},\"plan_type\":\"plan_type\",\"trial_days\":1,\"updated_at\":\"2024-01-15T09:30:00Z\",\"yearly_price\":{\"currency\":\"currency\",\"external_price_id\":\"external_price_id\",\"id\":\"id\",\"interval\":\"interval\",\"price\":1,\"scheme\":\"scheme\"}}],\"params\":{\"company_id\":\"company_id\",\"for_fallback_plan\":true,\"for_initial_plan\":true,\"for_trial_expiry_plan\":true,\"has_product_id\":true,\"ids\":[\"ids\"],\"limit\":1,\"offset\":1,\"plan_type\":\"plan\",\"q\":\"q\",\"requires_payment_method\":true,\"without_entitlement_for\":\"without_entitlement_for\",\"without_paid_product_id\":true,\"without_product_id\":true}}"));
        ListPlansResponse response = client.plans()
                .listPlans(ListPlansRequest.builder()
                        .companyId("company_id")
                        .forFallbackPlan(true)
                        .forInitialPlan(true)
                        .forTrialExpiryPlan(true)
                        .hasProductId(true)
                        .planType(ListPlansRequestPlanType.PLAN)
                        .q("q")
                        .requiresPaymentMethod(true)
                        .withoutEntitlementFor("without_entitlement_for")
                        .withoutProductId(true)
                        .withoutPaidProductId(true)
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
                + "    }\n"
                + "  ],\n"
                + "  \"params\": {\n"
                + "    \"company_id\": \"company_id\",\n"
                + "    \"for_fallback_plan\": true,\n"
                + "    \"for_initial_plan\": true,\n"
                + "    \"for_trial_expiry_plan\": true,\n"
                + "    \"has_product_id\": true,\n"
                + "    \"ids\": [\n"
                + "      \"ids\"\n"
                + "    ],\n"
                + "    \"limit\": 1,\n"
                + "    \"offset\": 1,\n"
                + "    \"plan_type\": \"plan\",\n"
                + "    \"q\": \"q\",\n"
                + "    \"requires_payment_method\": true,\n"
                + "    \"without_entitlement_for\": \"without_entitlement_for\",\n"
                + "    \"without_paid_product_id\": true,\n"
                + "    \"without_product_id\": true\n"
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
    public void testCreatePlan() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":{\"billing_product\":{\"account_id\":\"account_id\",\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"is_active\":true,\"name\":\"name\",\"price\":1.1,\"price_decimal\":\"price_decimal\",\"prices\":[{\"currency\":\"currency\",\"external_price_id\":\"external_price_id\",\"id\":\"id\",\"interval\":\"interval\",\"price\":1,\"scheme\":\"scheme\"}],\"product_id\":\"product_id\",\"quantity\":1.1,\"subscription_count\":1,\"updated_at\":\"2024-01-15T09:30:00Z\"},\"charge_type\":\"charge_type\",\"company_count\":1,\"controlled_by\":\"controlled_by\",\"created_at\":\"2024-01-15T09:30:00Z\",\"description\":\"description\",\"features\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"description\":\"description\",\"feature_type\":\"feature_type\",\"flags\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"default_value\":true,\"description\":\"description\",\"flag_type\":\"flag_type\",\"id\":\"id\",\"key\":\"key\",\"name\":\"name\",\"rules\":[{\"condition_groups\":[{\"conditions\":[{\"condition_type\":\"condition_type\",\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"operator\":\"operator\",\"resource_ids\":[\"resource_ids\"],\"resources\":[{\"id\":\"id\",\"name\":\"name\"}],\"rule_id\":\"rule_id\",\"trait_value\":\"trait_value\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"rule_id\":\"rule_id\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"conditions\":[{\"condition_type\":\"condition_type\",\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"operator\":\"operator\",\"resource_ids\":[\"resource_ids\"],\"resources\":[{\"id\":\"id\",\"name\":\"name\"}],\"rule_id\":\"rule_id\",\"trait_value\":\"trait_value\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"name\":\"name\",\"priority\":1,\"rule_type\":\"rule_type\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"value\":true}],\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"icon\":\"icon\",\"id\":\"id\",\"name\":\"name\",\"plans\":[{\"id\":\"id\",\"name\":\"name\"}],\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"icon\":\"icon\",\"id\":\"id\",\"included_credit_grants\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"credit_amount\":1,\"credit_id\":\"credit_id\",\"credit_name\":\"credit_name\",\"id\":\"id\",\"plan_id\":\"plan_id\",\"plan_name\":\"plan_name\",\"reset_cadence\":\"reset_cadence\",\"reset_start\":\"reset_start\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"is_default\":true,\"is_free\":true,\"is_trialable\":true,\"monthly_price\":{\"currency\":\"currency\",\"external_price_id\":\"external_price_id\",\"id\":\"id\",\"interval\":\"interval\",\"price\":1,\"price_decimal\":\"price_decimal\",\"scheme\":\"scheme\"},\"name\":\"name\",\"one_time_price\":{\"currency\":\"currency\",\"external_price_id\":\"external_price_id\",\"id\":\"id\",\"interval\":\"interval\",\"price\":1,\"price_decimal\":\"price_decimal\",\"scheme\":\"scheme\"},\"plan_type\":\"plan_type\",\"trial_days\":1,\"updated_at\":\"2024-01-15T09:30:00Z\",\"yearly_price\":{\"currency\":\"currency\",\"external_price_id\":\"external_price_id\",\"id\":\"id\",\"interval\":\"interval\",\"price\":1,\"price_decimal\":\"price_decimal\",\"scheme\":\"scheme\"}},\"params\":{\"key\":\"value\"}}"));
        CreatePlanResponse response = client.plans()
                .createPlan(CreatePlanRequestBody.builder()
                        .description("description")
                        .name("name")
                        .planType(CreatePlanRequestBodyPlanType.PLAN)
                        .build());
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("POST", request.getMethod());
        // Validate request body
        String actualRequestBody = request.getBody().readUtf8();
        String expectedRequestBody = ""
                + "{\n"
                + "  \"description\": \"description\",\n"
                + "  \"name\": \"name\",\n"
                + "  \"plan_type\": \"plan\"\n"
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
                + "    \"billing_product\": {\n"
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
                + "    },\n"
                + "    \"charge_type\": \"charge_type\",\n"
                + "    \"company_count\": 1,\n"
                + "    \"controlled_by\": \"controlled_by\",\n"
                + "    \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"description\": \"description\",\n"
                + "    \"features\": [\n"
                + "      {\n"
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
                + "      }\n"
                + "    ],\n"
                + "    \"icon\": \"icon\",\n"
                + "    \"id\": \"id\",\n"
                + "    \"included_credit_grants\": [\n"
                + "      {\n"
                + "        \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"credit_amount\": 1,\n"
                + "        \"credit_id\": \"credit_id\",\n"
                + "        \"credit_name\": \"credit_name\",\n"
                + "        \"id\": \"id\",\n"
                + "        \"plan_id\": \"plan_id\",\n"
                + "        \"plan_name\": \"plan_name\",\n"
                + "        \"reset_cadence\": \"reset_cadence\",\n"
                + "        \"reset_start\": \"reset_start\",\n"
                + "        \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "      }\n"
                + "    ],\n"
                + "    \"is_default\": true,\n"
                + "    \"is_free\": true,\n"
                + "    \"is_trialable\": true,\n"
                + "    \"monthly_price\": {\n"
                + "      \"currency\": \"currency\",\n"
                + "      \"external_price_id\": \"external_price_id\",\n"
                + "      \"id\": \"id\",\n"
                + "      \"interval\": \"interval\",\n"
                + "      \"price\": 1,\n"
                + "      \"price_decimal\": \"price_decimal\",\n"
                + "      \"scheme\": \"scheme\"\n"
                + "    },\n"
                + "    \"name\": \"name\",\n"
                + "    \"one_time_price\": {\n"
                + "      \"currency\": \"currency\",\n"
                + "      \"external_price_id\": \"external_price_id\",\n"
                + "      \"id\": \"id\",\n"
                + "      \"interval\": \"interval\",\n"
                + "      \"price\": 1,\n"
                + "      \"price_decimal\": \"price_decimal\",\n"
                + "      \"scheme\": \"scheme\"\n"
                + "    },\n"
                + "    \"plan_type\": \"plan_type\",\n"
                + "    \"trial_days\": 1,\n"
                + "    \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"yearly_price\": {\n"
                + "      \"currency\": \"currency\",\n"
                + "      \"external_price_id\": \"external_price_id\",\n"
                + "      \"id\": \"id\",\n"
                + "      \"interval\": \"interval\",\n"
                + "      \"price\": 1,\n"
                + "      \"price_decimal\": \"price_decimal\",\n"
                + "      \"scheme\": \"scheme\"\n"
                + "    }\n"
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
    public void testGetPlan() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":{\"billing_product\":{\"account_id\":\"account_id\",\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"is_active\":true,\"name\":\"name\",\"price\":1.1,\"price_decimal\":\"price_decimal\",\"prices\":[{\"currency\":\"currency\",\"external_price_id\":\"external_price_id\",\"id\":\"id\",\"interval\":\"interval\",\"price\":1,\"scheme\":\"scheme\"}],\"product_id\":\"product_id\",\"quantity\":1.1,\"subscription_count\":1,\"updated_at\":\"2024-01-15T09:30:00Z\"},\"charge_type\":\"charge_type\",\"company_count\":1,\"controlled_by\":\"controlled_by\",\"created_at\":\"2024-01-15T09:30:00Z\",\"description\":\"description\",\"features\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"description\":\"description\",\"feature_type\":\"feature_type\",\"flags\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"default_value\":true,\"description\":\"description\",\"flag_type\":\"flag_type\",\"id\":\"id\",\"key\":\"key\",\"name\":\"name\",\"rules\":[{\"condition_groups\":[{\"conditions\":[{\"condition_type\":\"condition_type\",\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"operator\":\"operator\",\"resource_ids\":[\"resource_ids\"],\"resources\":[{\"id\":\"id\",\"name\":\"name\"}],\"rule_id\":\"rule_id\",\"trait_value\":\"trait_value\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"rule_id\":\"rule_id\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"conditions\":[{\"condition_type\":\"condition_type\",\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"operator\":\"operator\",\"resource_ids\":[\"resource_ids\"],\"resources\":[{\"id\":\"id\",\"name\":\"name\"}],\"rule_id\":\"rule_id\",\"trait_value\":\"trait_value\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"name\":\"name\",\"priority\":1,\"rule_type\":\"rule_type\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"value\":true}],\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"icon\":\"icon\",\"id\":\"id\",\"name\":\"name\",\"plans\":[{\"id\":\"id\",\"name\":\"name\"}],\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"icon\":\"icon\",\"id\":\"id\",\"included_credit_grants\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"credit_amount\":1,\"credit_id\":\"credit_id\",\"credit_name\":\"credit_name\",\"id\":\"id\",\"plan_id\":\"plan_id\",\"plan_name\":\"plan_name\",\"reset_cadence\":\"reset_cadence\",\"reset_start\":\"reset_start\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"is_default\":true,\"is_free\":true,\"is_trialable\":true,\"monthly_price\":{\"currency\":\"currency\",\"external_price_id\":\"external_price_id\",\"id\":\"id\",\"interval\":\"interval\",\"price\":1,\"price_decimal\":\"price_decimal\",\"scheme\":\"scheme\"},\"name\":\"name\",\"one_time_price\":{\"currency\":\"currency\",\"external_price_id\":\"external_price_id\",\"id\":\"id\",\"interval\":\"interval\",\"price\":1,\"price_decimal\":\"price_decimal\",\"scheme\":\"scheme\"},\"plan_type\":\"plan_type\",\"trial_days\":1,\"updated_at\":\"2024-01-15T09:30:00Z\",\"yearly_price\":{\"currency\":\"currency\",\"external_price_id\":\"external_price_id\",\"id\":\"id\",\"interval\":\"interval\",\"price\":1,\"price_decimal\":\"price_decimal\",\"scheme\":\"scheme\"}},\"params\":{\"key\":\"value\"}}"));
        GetPlanResponse response = client.plans().getPlan("plan_id");
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("GET", request.getMethod());

        // Validate response body
        Assertions.assertNotNull(response, "Response should not be null");
        String actualResponseJson = objectMapper.writeValueAsString(response);
        String expectedResponseBody = ""
                + "{\n"
                + "  \"data\": {\n"
                + "    \"billing_product\": {\n"
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
                + "    },\n"
                + "    \"charge_type\": \"charge_type\",\n"
                + "    \"company_count\": 1,\n"
                + "    \"controlled_by\": \"controlled_by\",\n"
                + "    \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"description\": \"description\",\n"
                + "    \"features\": [\n"
                + "      {\n"
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
                + "      }\n"
                + "    ],\n"
                + "    \"icon\": \"icon\",\n"
                + "    \"id\": \"id\",\n"
                + "    \"included_credit_grants\": [\n"
                + "      {\n"
                + "        \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"credit_amount\": 1,\n"
                + "        \"credit_id\": \"credit_id\",\n"
                + "        \"credit_name\": \"credit_name\",\n"
                + "        \"id\": \"id\",\n"
                + "        \"plan_id\": \"plan_id\",\n"
                + "        \"plan_name\": \"plan_name\",\n"
                + "        \"reset_cadence\": \"reset_cadence\",\n"
                + "        \"reset_start\": \"reset_start\",\n"
                + "        \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "      }\n"
                + "    ],\n"
                + "    \"is_default\": true,\n"
                + "    \"is_free\": true,\n"
                + "    \"is_trialable\": true,\n"
                + "    \"monthly_price\": {\n"
                + "      \"currency\": \"currency\",\n"
                + "      \"external_price_id\": \"external_price_id\",\n"
                + "      \"id\": \"id\",\n"
                + "      \"interval\": \"interval\",\n"
                + "      \"price\": 1,\n"
                + "      \"price_decimal\": \"price_decimal\",\n"
                + "      \"scheme\": \"scheme\"\n"
                + "    },\n"
                + "    \"name\": \"name\",\n"
                + "    \"one_time_price\": {\n"
                + "      \"currency\": \"currency\",\n"
                + "      \"external_price_id\": \"external_price_id\",\n"
                + "      \"id\": \"id\",\n"
                + "      \"interval\": \"interval\",\n"
                + "      \"price\": 1,\n"
                + "      \"price_decimal\": \"price_decimal\",\n"
                + "      \"scheme\": \"scheme\"\n"
                + "    },\n"
                + "    \"plan_type\": \"plan_type\",\n"
                + "    \"trial_days\": 1,\n"
                + "    \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"yearly_price\": {\n"
                + "      \"currency\": \"currency\",\n"
                + "      \"external_price_id\": \"external_price_id\",\n"
                + "      \"id\": \"id\",\n"
                + "      \"interval\": \"interval\",\n"
                + "      \"price\": 1,\n"
                + "      \"price_decimal\": \"price_decimal\",\n"
                + "      \"scheme\": \"scheme\"\n"
                + "    }\n"
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
    public void testUpdatePlan() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":{\"billing_product\":{\"account_id\":\"account_id\",\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"is_active\":true,\"name\":\"name\",\"price\":1.1,\"price_decimal\":\"price_decimal\",\"prices\":[{\"currency\":\"currency\",\"external_price_id\":\"external_price_id\",\"id\":\"id\",\"interval\":\"interval\",\"price\":1,\"scheme\":\"scheme\"}],\"product_id\":\"product_id\",\"quantity\":1.1,\"subscription_count\":1,\"updated_at\":\"2024-01-15T09:30:00Z\"},\"charge_type\":\"charge_type\",\"company_count\":1,\"controlled_by\":\"controlled_by\",\"created_at\":\"2024-01-15T09:30:00Z\",\"description\":\"description\",\"features\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"description\":\"description\",\"feature_type\":\"feature_type\",\"flags\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"default_value\":true,\"description\":\"description\",\"flag_type\":\"flag_type\",\"id\":\"id\",\"key\":\"key\",\"name\":\"name\",\"rules\":[{\"condition_groups\":[{\"conditions\":[{\"condition_type\":\"condition_type\",\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"operator\":\"operator\",\"resource_ids\":[\"resource_ids\"],\"resources\":[{\"id\":\"id\",\"name\":\"name\"}],\"rule_id\":\"rule_id\",\"trait_value\":\"trait_value\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"rule_id\":\"rule_id\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"conditions\":[{\"condition_type\":\"condition_type\",\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"operator\":\"operator\",\"resource_ids\":[\"resource_ids\"],\"resources\":[{\"id\":\"id\",\"name\":\"name\"}],\"rule_id\":\"rule_id\",\"trait_value\":\"trait_value\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"name\":\"name\",\"priority\":1,\"rule_type\":\"rule_type\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"value\":true}],\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"icon\":\"icon\",\"id\":\"id\",\"name\":\"name\",\"plans\":[{\"id\":\"id\",\"name\":\"name\"}],\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"icon\":\"icon\",\"id\":\"id\",\"included_credit_grants\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"credit_amount\":1,\"credit_id\":\"credit_id\",\"credit_name\":\"credit_name\",\"id\":\"id\",\"plan_id\":\"plan_id\",\"plan_name\":\"plan_name\",\"reset_cadence\":\"reset_cadence\",\"reset_start\":\"reset_start\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"is_default\":true,\"is_free\":true,\"is_trialable\":true,\"monthly_price\":{\"currency\":\"currency\",\"external_price_id\":\"external_price_id\",\"id\":\"id\",\"interval\":\"interval\",\"price\":1,\"price_decimal\":\"price_decimal\",\"scheme\":\"scheme\"},\"name\":\"name\",\"one_time_price\":{\"currency\":\"currency\",\"external_price_id\":\"external_price_id\",\"id\":\"id\",\"interval\":\"interval\",\"price\":1,\"price_decimal\":\"price_decimal\",\"scheme\":\"scheme\"},\"plan_type\":\"plan_type\",\"trial_days\":1,\"updated_at\":\"2024-01-15T09:30:00Z\",\"yearly_price\":{\"currency\":\"currency\",\"external_price_id\":\"external_price_id\",\"id\":\"id\",\"interval\":\"interval\",\"price\":1,\"price_decimal\":\"price_decimal\",\"scheme\":\"scheme\"}},\"params\":{\"key\":\"value\"}}"));
        UpdatePlanResponse response = client.plans()
                .updatePlan(
                        "plan_id", UpdatePlanRequestBody.builder().name("name").build());
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("PUT", request.getMethod());
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
                + "    \"billing_product\": {\n"
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
                + "    },\n"
                + "    \"charge_type\": \"charge_type\",\n"
                + "    \"company_count\": 1,\n"
                + "    \"controlled_by\": \"controlled_by\",\n"
                + "    \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"description\": \"description\",\n"
                + "    \"features\": [\n"
                + "      {\n"
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
                + "      }\n"
                + "    ],\n"
                + "    \"icon\": \"icon\",\n"
                + "    \"id\": \"id\",\n"
                + "    \"included_credit_grants\": [\n"
                + "      {\n"
                + "        \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"credit_amount\": 1,\n"
                + "        \"credit_id\": \"credit_id\",\n"
                + "        \"credit_name\": \"credit_name\",\n"
                + "        \"id\": \"id\",\n"
                + "        \"plan_id\": \"plan_id\",\n"
                + "        \"plan_name\": \"plan_name\",\n"
                + "        \"reset_cadence\": \"reset_cadence\",\n"
                + "        \"reset_start\": \"reset_start\",\n"
                + "        \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "      }\n"
                + "    ],\n"
                + "    \"is_default\": true,\n"
                + "    \"is_free\": true,\n"
                + "    \"is_trialable\": true,\n"
                + "    \"monthly_price\": {\n"
                + "      \"currency\": \"currency\",\n"
                + "      \"external_price_id\": \"external_price_id\",\n"
                + "      \"id\": \"id\",\n"
                + "      \"interval\": \"interval\",\n"
                + "      \"price\": 1,\n"
                + "      \"price_decimal\": \"price_decimal\",\n"
                + "      \"scheme\": \"scheme\"\n"
                + "    },\n"
                + "    \"name\": \"name\",\n"
                + "    \"one_time_price\": {\n"
                + "      \"currency\": \"currency\",\n"
                + "      \"external_price_id\": \"external_price_id\",\n"
                + "      \"id\": \"id\",\n"
                + "      \"interval\": \"interval\",\n"
                + "      \"price\": 1,\n"
                + "      \"price_decimal\": \"price_decimal\",\n"
                + "      \"scheme\": \"scheme\"\n"
                + "    },\n"
                + "    \"plan_type\": \"plan_type\",\n"
                + "    \"trial_days\": 1,\n"
                + "    \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"yearly_price\": {\n"
                + "      \"currency\": \"currency\",\n"
                + "      \"external_price_id\": \"external_price_id\",\n"
                + "      \"id\": \"id\",\n"
                + "      \"interval\": \"interval\",\n"
                + "      \"price\": 1,\n"
                + "      \"price_decimal\": \"price_decimal\",\n"
                + "      \"scheme\": \"scheme\"\n"
                + "    }\n"
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
    public void testDeletePlan() throws Exception {
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody("{\"data\":{\"deleted\":true},\"params\":{\"key\":\"value\"}}"));
        DeletePlanResponse response = client.plans().deletePlan("plan_id");
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
    public void testUpsertBillingProductPlan() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":{\"account_id\":\"account_id\",\"billing_product_id\":\"billing_product_id\",\"charge_type\":\"charge_type\",\"controlled_by\":\"controlled_by\",\"environment_id\":\"environment_id\",\"is_trialable\":true,\"monthly_price_id\":\"monthly_price_id\",\"one_time_price_id\":\"one_time_price_id\",\"plan_id\":\"plan_id\",\"trial_days\":1,\"yearly_price_id\":\"yearly_price_id\"},\"params\":{\"key\":\"value\"}}"));
        UpsertBillingProductPlanResponse response = client.plans()
                .upsertBillingProductPlan(
                        "plan_id",
                        UpsertBillingProductRequestBody.builder()
                                .chargeType(UpsertBillingProductRequestBodyChargeType.ONE_TIME)
                                .isTrialable(true)
                                .build());
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("PUT", request.getMethod());
        // Validate request body
        String actualRequestBody = request.getBody().readUtf8();
        String expectedRequestBody =
                "" + "{\n" + "  \"charge_type\": \"one_time\",\n" + "  \"is_trialable\": true\n" + "}";
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
                + "    \"billing_product_id\": \"billing_product_id\",\n"
                + "    \"charge_type\": \"charge_type\",\n"
                + "    \"controlled_by\": \"controlled_by\",\n"
                + "    \"environment_id\": \"environment_id\",\n"
                + "    \"is_trialable\": true,\n"
                + "    \"monthly_price_id\": \"monthly_price_id\",\n"
                + "    \"one_time_price_id\": \"one_time_price_id\",\n"
                + "    \"plan_id\": \"plan_id\",\n"
                + "    \"trial_days\": 1,\n"
                + "    \"yearly_price_id\": \"yearly_price_id\"\n"
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
    public void testCountPlans() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":{\"count\":1},\"params\":{\"company_id\":\"company_id\",\"for_fallback_plan\":true,\"for_initial_plan\":true,\"for_trial_expiry_plan\":true,\"has_product_id\":true,\"ids\":[\"ids\"],\"limit\":1,\"offset\":1,\"plan_type\":\"plan\",\"q\":\"q\",\"requires_payment_method\":true,\"without_entitlement_for\":\"without_entitlement_for\",\"without_paid_product_id\":true,\"without_product_id\":true}}"));
        CountPlansResponse response = client.plans()
                .countPlans(CountPlansRequest.builder()
                        .companyId("company_id")
                        .forFallbackPlan(true)
                        .forInitialPlan(true)
                        .forTrialExpiryPlan(true)
                        .hasProductId(true)
                        .planType(CountPlansRequestPlanType.PLAN)
                        .q("q")
                        .requiresPaymentMethod(true)
                        .withoutEntitlementFor("without_entitlement_for")
                        .withoutProductId(true)
                        .withoutPaidProductId(true)
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
                + "    \"for_fallback_plan\": true,\n"
                + "    \"for_initial_plan\": true,\n"
                + "    \"for_trial_expiry_plan\": true,\n"
                + "    \"has_product_id\": true,\n"
                + "    \"ids\": [\n"
                + "      \"ids\"\n"
                + "    ],\n"
                + "    \"limit\": 1,\n"
                + "    \"offset\": 1,\n"
                + "    \"plan_type\": \"plan\",\n"
                + "    \"q\": \"q\",\n"
                + "    \"requires_payment_method\": true,\n"
                + "    \"without_entitlement_for\": \"without_entitlement_for\",\n"
                + "    \"without_paid_product_id\": true,\n"
                + "    \"without_product_id\": true\n"
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
    public void testListPlanIssues() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":[{\"code\":\"code\",\"description\":\"description\",\"detail\":\"detail\",\"id\":\"id\"}],\"params\":{\"plan_id\":\"plan_id\"}}"));
        ListPlanIssuesResponse response = client.plans()
                .listPlanIssues(
                        ListPlanIssuesRequest.builder().planId("plan_id").build());
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
                + "      \"code\": \"code\",\n"
                + "      \"description\": \"description\",\n"
                + "      \"detail\": \"detail\",\n"
                + "      \"id\": \"id\"\n"
                + "    }\n"
                + "  ],\n"
                + "  \"params\": {\n"
                + "    \"plan_id\": \"plan_id\"\n"
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
