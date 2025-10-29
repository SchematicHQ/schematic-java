package com.schematic.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.schematic.api.core.ObjectMappers;
import com.schematic.api.resources.companies.requests.CountCompaniesForAdvancedFilterRequest;
import com.schematic.api.resources.companies.requests.CountCompaniesRequest;
import com.schematic.api.resources.companies.requests.CountEntityKeyDefinitionsRequest;
import com.schematic.api.resources.companies.requests.CountEntityTraitDefinitionsRequest;
import com.schematic.api.resources.companies.requests.CountPlanTraitsRequest;
import com.schematic.api.resources.companies.requests.CountUsersRequest;
import com.schematic.api.resources.companies.requests.CreateEntityTraitDefinitionRequestBody;
import com.schematic.api.resources.companies.requests.CreatePlanTraitRequestBody;
import com.schematic.api.resources.companies.requests.GetActiveCompanySubscriptionRequest;
import com.schematic.api.resources.companies.requests.GetActiveDealsRequest;
import com.schematic.api.resources.companies.requests.GetEntityTraitValuesRequest;
import com.schematic.api.resources.companies.requests.GetOrCreateCompanyMembershipRequestBody;
import com.schematic.api.resources.companies.requests.ListCompaniesForAdvancedFilterRequest;
import com.schematic.api.resources.companies.requests.ListCompaniesRequest;
import com.schematic.api.resources.companies.requests.ListCompanyMembershipsRequest;
import com.schematic.api.resources.companies.requests.ListEntityKeyDefinitionsRequest;
import com.schematic.api.resources.companies.requests.ListEntityTraitDefinitionsRequest;
import com.schematic.api.resources.companies.requests.ListPlanChangesRequest;
import com.schematic.api.resources.companies.requests.ListPlanTraitsRequest;
import com.schematic.api.resources.companies.requests.ListUsersRequest;
import com.schematic.api.resources.companies.requests.LookupCompanyRequest;
import com.schematic.api.resources.companies.requests.LookupUserRequest;
import com.schematic.api.resources.companies.requests.UpdateEntityTraitDefinitionRequestBody;
import com.schematic.api.resources.companies.requests.UpdatePlanTraitBulkRequestBody;
import com.schematic.api.resources.companies.requests.UpdatePlanTraitRequestBody;
import com.schematic.api.resources.companies.types.CountCompaniesForAdvancedFilterRequestSortOrderDirection;
import com.schematic.api.resources.companies.types.CountCompaniesForAdvancedFilterResponse;
import com.schematic.api.resources.companies.types.CountCompaniesResponse;
import com.schematic.api.resources.companies.types.CountEntityKeyDefinitionsRequestEntityType;
import com.schematic.api.resources.companies.types.CountEntityKeyDefinitionsResponse;
import com.schematic.api.resources.companies.types.CountEntityTraitDefinitionsRequestEntityType;
import com.schematic.api.resources.companies.types.CountEntityTraitDefinitionsRequestTraitType;
import com.schematic.api.resources.companies.types.CountEntityTraitDefinitionsResponse;
import com.schematic.api.resources.companies.types.CountPlanTraitsResponse;
import com.schematic.api.resources.companies.types.CountUsersResponse;
import com.schematic.api.resources.companies.types.CreateCompanyResponse;
import com.schematic.api.resources.companies.types.CreateEntityTraitDefinitionRequestBodyEntityType;
import com.schematic.api.resources.companies.types.CreateEntityTraitDefinitionRequestBodyTraitType;
import com.schematic.api.resources.companies.types.CreatePlanTraitResponse;
import com.schematic.api.resources.companies.types.CreateUserResponse;
import com.schematic.api.resources.companies.types.DeleteCompanyByKeysResponse;
import com.schematic.api.resources.companies.types.DeleteCompanyMembershipResponse;
import com.schematic.api.resources.companies.types.DeleteCompanyResponse;
import com.schematic.api.resources.companies.types.DeletePlanTraitResponse;
import com.schematic.api.resources.companies.types.DeleteUserByKeysResponse;
import com.schematic.api.resources.companies.types.DeleteUserResponse;
import com.schematic.api.resources.companies.types.GetActiveCompanySubscriptionResponse;
import com.schematic.api.resources.companies.types.GetActiveDealsResponse;
import com.schematic.api.resources.companies.types.GetCompanyResponse;
import com.schematic.api.resources.companies.types.GetEntityTraitDefinitionResponse;
import com.schematic.api.resources.companies.types.GetEntityTraitValuesResponse;
import com.schematic.api.resources.companies.types.GetOrCreateCompanyMembershipResponse;
import com.schematic.api.resources.companies.types.GetOrCreateEntityTraitDefinitionResponse;
import com.schematic.api.resources.companies.types.GetPlanChangeResponse;
import com.schematic.api.resources.companies.types.GetPlanTraitResponse;
import com.schematic.api.resources.companies.types.GetUserResponse;
import com.schematic.api.resources.companies.types.ListCompaniesForAdvancedFilterRequestSortOrderDirection;
import com.schematic.api.resources.companies.types.ListCompaniesForAdvancedFilterResponse;
import com.schematic.api.resources.companies.types.ListCompaniesResponse;
import com.schematic.api.resources.companies.types.ListCompanyMembershipsResponse;
import com.schematic.api.resources.companies.types.ListEntityKeyDefinitionsRequestEntityType;
import com.schematic.api.resources.companies.types.ListEntityKeyDefinitionsResponse;
import com.schematic.api.resources.companies.types.ListEntityTraitDefinitionsRequestEntityType;
import com.schematic.api.resources.companies.types.ListEntityTraitDefinitionsRequestTraitType;
import com.schematic.api.resources.companies.types.ListEntityTraitDefinitionsResponse;
import com.schematic.api.resources.companies.types.ListPlanChangesResponse;
import com.schematic.api.resources.companies.types.ListPlanTraitsResponse;
import com.schematic.api.resources.companies.types.ListUsersResponse;
import com.schematic.api.resources.companies.types.LookupCompanyResponse;
import com.schematic.api.resources.companies.types.LookupUserResponse;
import com.schematic.api.resources.companies.types.UpdateEntityTraitDefinitionRequestBodyTraitType;
import com.schematic.api.resources.companies.types.UpdateEntityTraitDefinitionResponse;
import com.schematic.api.resources.companies.types.UpdatePlanTraitResponse;
import com.schematic.api.resources.companies.types.UpdatePlanTraitsBulkResponse;
import com.schematic.api.resources.companies.types.UpsertCompanyResponse;
import com.schematic.api.resources.companies.types.UpsertCompanyTraitResponse;
import com.schematic.api.resources.companies.types.UpsertUserResponse;
import com.schematic.api.resources.companies.types.UpsertUserTraitResponse;
import com.schematic.api.types.KeysRequestBody;
import com.schematic.api.types.UpdatePlanTraitTraitRequestBody;
import com.schematic.api.types.UpsertCompanyRequestBody;
import com.schematic.api.types.UpsertTraitRequestBody;
import com.schematic.api.types.UpsertUserRequestBody;
import java.util.Arrays;
import java.util.HashMap;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CompaniesWireTest {
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
    public void testListCompanies() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":[{\"add_ons\":[{\"id\":\"id\",\"name\":\"name\"}],\"billing_credit_balances\":{\"key\":1.1},\"billing_subscription\":{\"cancel_at_period_end\":true,\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"customer_external_id\":\"customer_external_id\",\"discounts\":[{\"coupon_id\":\"coupon_id\",\"coupon_name\":\"coupon_name\",\"discount_external_id\":\"discount_external_id\",\"duration\":\"duration\",\"is_active\":true,\"started_at\":\"2024-01-15T09:30:00Z\",\"subscription_external_id\":\"subscription_external_id\"}],\"id\":\"id\",\"interval\":\"interval\",\"period_end\":1,\"period_start\":1,\"products\":[{\"billing_scheme\":\"billing_scheme\",\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"id\":\"id\",\"interval\":\"interval\",\"name\":\"name\",\"package_size\":1,\"price\":1,\"price_external_id\":\"price_external_id\",\"price_id\":\"price_id\",\"price_tier\":[{}],\"quantity\":1.1,\"subscription_id\":\"subscription_id\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"usage_type\":\"usage_type\"}],\"status\":\"status\",\"subscription_external_id\":\"subscription_external_id\",\"total_price\":1},\"billing_subscriptions\":[{\"cancel_at_period_end\":true,\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"customer_external_id\":\"customer_external_id\",\"discounts\":[{\"coupon_id\":\"coupon_id\",\"coupon_name\":\"coupon_name\",\"discount_external_id\":\"discount_external_id\",\"duration\":\"duration\",\"is_active\":true,\"started_at\":\"2024-01-15T09:30:00Z\",\"subscription_external_id\":\"subscription_external_id\"}],\"id\":\"id\",\"interval\":\"interval\",\"period_end\":1,\"period_start\":1,\"products\":[{\"billing_scheme\":\"billing_scheme\",\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"id\":\"id\",\"interval\":\"interval\",\"name\":\"name\",\"package_size\":1,\"price\":1,\"price_external_id\":\"price_external_id\",\"price_id\":\"price_id\",\"price_tier\":[{}],\"quantity\":1.1,\"subscription_id\":\"subscription_id\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"usage_type\":\"usage_type\"}],\"status\":\"status\",\"subscription_external_id\":\"subscription_external_id\",\"total_price\":1}],\"created_at\":\"2024-01-15T09:30:00Z\",\"default_payment_method\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"customer_external_id\":\"customer_external_id\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"id\":\"id\",\"payment_method_type\":\"payment_method_type\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"entity_traits\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"definition_id\":\"definition_id\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"value\":\"value\"}],\"environment_id\":\"environment_id\",\"id\":\"id\",\"keys\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"definition_id\":\"definition_id\",\"entity_id\":\"entity_id\",\"entity_type\":\"entity_type\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"key\":\"key\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"value\":\"value\"}],\"last_seen_at\":\"2024-01-15T09:30:00Z\",\"logo_url\":\"logo_url\",\"metrics\":[{\"account_id\":\"account_id\",\"captured_at_max\":\"2024-01-15T09:30:00Z\",\"captured_at_min\":\"2024-01-15T09:30:00Z\",\"company_id\":\"company_id\",\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"event_subtype\":\"event_subtype\",\"month_reset\":\"month_reset\",\"period\":\"period\",\"value\":1}],\"name\":\"name\",\"payment_methods\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"customer_external_id\":\"customer_external_id\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"id\":\"id\",\"payment_method_type\":\"payment_method_type\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"plan\":{\"id\":\"id\",\"name\":\"name\"},\"plans\":[{\"id\":\"id\",\"name\":\"name\"}],\"rules\":[{\"account_id\":\"account_id\",\"condition_groups\":[{\"conditions\":[{\"account_id\":\"account_id\",\"condition_type\":\"condition_type\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"operator\":\"operator\",\"resource_ids\":[\"resource_ids\"],\"trait_value\":\"trait_value\"}]}],\"conditions\":[{\"account_id\":\"account_id\",\"condition_type\":\"condition_type\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"operator\":\"operator\",\"resource_ids\":[\"resource_ids\"],\"trait_value\":\"trait_value\"}],\"environment_id\":\"environment_id\",\"id\":\"id\",\"name\":\"name\",\"priority\":1,\"rule_type\":\"rule_type\",\"value\":true}],\"traits\":{\"key\":\"value\"},\"updated_at\":\"2024-01-15T09:30:00Z\",\"user_count\":1}],\"params\":{\"ids\":[\"ids\"],\"limit\":1,\"offset\":1,\"plan_id\":\"plan_id\",\"q\":\"q\",\"with_subscription\":true,\"without_feature_override_for\":\"without_feature_override_for\",\"without_plan\":true}}"));
        ListCompaniesResponse response = client.companies()
                .listCompanies(ListCompaniesRequest.builder()
                        .planId("plan_id")
                        .q("q")
                        .withoutFeatureOverrideFor("without_feature_override_for")
                        .withoutPlan(true)
                        .withSubscription(true)
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
                + "    }\n"
                + "  ],\n"
                + "  \"params\": {\n"
                + "    \"ids\": [\n"
                + "      \"ids\"\n"
                + "    ],\n"
                + "    \"limit\": 1,\n"
                + "    \"offset\": 1,\n"
                + "    \"plan_id\": \"plan_id\",\n"
                + "    \"q\": \"q\",\n"
                + "    \"with_subscription\": true,\n"
                + "    \"without_feature_override_for\": \"without_feature_override_for\",\n"
                + "    \"without_plan\": true\n"
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
    public void testUpsertCompany() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":{\"add_ons\":[{\"id\":\"id\",\"name\":\"name\"}],\"billing_credit_balances\":{\"key\":1.1},\"billing_subscription\":{\"cancel_at\":1,\"cancel_at_period_end\":true,\"company_id\":\"company_id\",\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"customer_external_id\":\"customer_external_id\",\"default_payment_method_id\":\"default_payment_method_id\",\"discounts\":[{\"coupon_id\":\"coupon_id\",\"coupon_name\":\"coupon_name\",\"discount_external_id\":\"discount_external_id\",\"duration\":\"duration\",\"is_active\":true,\"started_at\":\"2024-01-15T09:30:00Z\",\"subscription_external_id\":\"subscription_external_id\"}],\"expired_at\":\"2024-01-15T09:30:00Z\",\"id\":\"id\",\"interval\":\"interval\",\"latest_invoice\":{\"amount_due\":1,\"amount_paid\":1,\"amount_remaining\":1,\"collection_method\":\"collection_method\",\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"customer_external_id\":\"customer_external_id\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"subtotal\":1,\"updated_at\":\"2024-01-15T09:30:00Z\"},\"metadata\":{\"key\":\"value\"},\"payment_method\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"customer_external_id\":\"customer_external_id\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"id\":\"id\",\"payment_method_type\":\"payment_method_type\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"period_end\":1,\"period_start\":1,\"products\":[{\"billing_scheme\":\"billing_scheme\",\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"id\":\"id\",\"interval\":\"interval\",\"name\":\"name\",\"package_size\":1,\"price\":1,\"price_external_id\":\"price_external_id\",\"price_id\":\"price_id\",\"price_tier\":[{}],\"quantity\":1.1,\"subscription_id\":\"subscription_id\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"usage_type\":\"usage_type\"}],\"status\":\"status\",\"subscription_external_id\":\"subscription_external_id\",\"total_price\":1,\"trial_end\":1,\"trial_end_setting\":\"trial_end_setting\"},\"billing_subscriptions\":[{\"cancel_at_period_end\":true,\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"customer_external_id\":\"customer_external_id\",\"discounts\":[{\"coupon_id\":\"coupon_id\",\"coupon_name\":\"coupon_name\",\"discount_external_id\":\"discount_external_id\",\"duration\":\"duration\",\"is_active\":true,\"started_at\":\"2024-01-15T09:30:00Z\",\"subscription_external_id\":\"subscription_external_id\"}],\"id\":\"id\",\"interval\":\"interval\",\"period_end\":1,\"period_start\":1,\"products\":[{\"billing_scheme\":\"billing_scheme\",\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"id\":\"id\",\"interval\":\"interval\",\"name\":\"name\",\"package_size\":1,\"price\":1,\"price_external_id\":\"price_external_id\",\"price_id\":\"price_id\",\"price_tier\":[{}],\"quantity\":1.1,\"subscription_id\":\"subscription_id\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"usage_type\":\"usage_type\"}],\"status\":\"status\",\"subscription_external_id\":\"subscription_external_id\",\"total_price\":1}],\"created_at\":\"2024-01-15T09:30:00Z\",\"default_payment_method\":{\"account_last4\":\"account_last4\",\"account_name\":\"account_name\",\"bank_name\":\"bank_name\",\"billing_email\":\"billing_email\",\"billing_name\":\"billing_name\",\"card_brand\":\"card_brand\",\"card_exp_month\":1,\"card_exp_year\":1,\"card_last4\":\"card_last4\",\"company_id\":\"company_id\",\"created_at\":\"2024-01-15T09:30:00Z\",\"customer_external_id\":\"customer_external_id\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"id\":\"id\",\"payment_method_type\":\"payment_method_type\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"entity_traits\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"definition_id\":\"definition_id\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"value\":\"value\"}],\"environment_id\":\"environment_id\",\"id\":\"id\",\"keys\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"definition_id\":\"definition_id\",\"entity_id\":\"entity_id\",\"entity_type\":\"entity_type\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"key\":\"key\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"value\":\"value\"}],\"last_seen_at\":\"2024-01-15T09:30:00Z\",\"logo_url\":\"logo_url\",\"metrics\":[{\"account_id\":\"account_id\",\"captured_at_max\":\"2024-01-15T09:30:00Z\",\"captured_at_min\":\"2024-01-15T09:30:00Z\",\"company_id\":\"company_id\",\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"event_subtype\":\"event_subtype\",\"month_reset\":\"month_reset\",\"period\":\"period\",\"value\":1}],\"name\":\"name\",\"payment_methods\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"customer_external_id\":\"customer_external_id\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"id\":\"id\",\"payment_method_type\":\"payment_method_type\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"plan\":{\"added_on\":\"2024-01-15T09:30:00Z\",\"billing_product_external_id\":\"billing_product_external_id\",\"billing_product_id\":\"billing_product_id\",\"description\":\"description\",\"id\":\"id\",\"image_url\":\"image_url\",\"name\":\"name\",\"plan_period\":\"plan_period\",\"plan_price\":1},\"plans\":[{\"id\":\"id\",\"name\":\"name\"}],\"rules\":[{\"account_id\":\"account_id\",\"condition_groups\":[{\"conditions\":[{\"account_id\":\"account_id\",\"condition_type\":\"condition_type\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"operator\":\"operator\",\"resource_ids\":[\"resource_ids\"],\"trait_value\":\"trait_value\"}]}],\"conditions\":[{\"account_id\":\"account_id\",\"condition_type\":\"condition_type\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"operator\":\"operator\",\"resource_ids\":[\"resource_ids\"],\"trait_value\":\"trait_value\"}],\"environment_id\":\"environment_id\",\"id\":\"id\",\"name\":\"name\",\"priority\":1,\"rule_type\":\"rule_type\",\"value\":true}],\"traits\":{\"key\":\"value\"},\"updated_at\":\"2024-01-15T09:30:00Z\",\"user_count\":1},\"params\":{\"key\":\"value\"}}"));
        UpsertCompanyResponse response = client.companies()
                .upsertCompany(UpsertCompanyRequestBody.builder()
                        .keys(new HashMap<String, String>() {
                            {
                                put("key", "value");
                            }
                        })
                        .build());
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("POST", request.getMethod());
        // Validate request body
        String actualRequestBody = request.getBody().readUtf8();
        String expectedRequestBody = "" + "{\n" + "  \"keys\": {\n" + "    \"key\": \"value\"\n" + "  }\n" + "}";
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
    public void testGetCompany() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":{\"add_ons\":[{\"id\":\"id\",\"name\":\"name\"}],\"billing_credit_balances\":{\"key\":1.1},\"billing_subscription\":{\"cancel_at\":1,\"cancel_at_period_end\":true,\"company_id\":\"company_id\",\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"customer_external_id\":\"customer_external_id\",\"default_payment_method_id\":\"default_payment_method_id\",\"discounts\":[{\"coupon_id\":\"coupon_id\",\"coupon_name\":\"coupon_name\",\"discount_external_id\":\"discount_external_id\",\"duration\":\"duration\",\"is_active\":true,\"started_at\":\"2024-01-15T09:30:00Z\",\"subscription_external_id\":\"subscription_external_id\"}],\"expired_at\":\"2024-01-15T09:30:00Z\",\"id\":\"id\",\"interval\":\"interval\",\"latest_invoice\":{\"amount_due\":1,\"amount_paid\":1,\"amount_remaining\":1,\"collection_method\":\"collection_method\",\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"customer_external_id\":\"customer_external_id\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"subtotal\":1,\"updated_at\":\"2024-01-15T09:30:00Z\"},\"metadata\":{\"key\":\"value\"},\"payment_method\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"customer_external_id\":\"customer_external_id\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"id\":\"id\",\"payment_method_type\":\"payment_method_type\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"period_end\":1,\"period_start\":1,\"products\":[{\"billing_scheme\":\"billing_scheme\",\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"id\":\"id\",\"interval\":\"interval\",\"name\":\"name\",\"package_size\":1,\"price\":1,\"price_external_id\":\"price_external_id\",\"price_id\":\"price_id\",\"price_tier\":[{}],\"quantity\":1.1,\"subscription_id\":\"subscription_id\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"usage_type\":\"usage_type\"}],\"status\":\"status\",\"subscription_external_id\":\"subscription_external_id\",\"total_price\":1,\"trial_end\":1,\"trial_end_setting\":\"trial_end_setting\"},\"billing_subscriptions\":[{\"cancel_at_period_end\":true,\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"customer_external_id\":\"customer_external_id\",\"discounts\":[{\"coupon_id\":\"coupon_id\",\"coupon_name\":\"coupon_name\",\"discount_external_id\":\"discount_external_id\",\"duration\":\"duration\",\"is_active\":true,\"started_at\":\"2024-01-15T09:30:00Z\",\"subscription_external_id\":\"subscription_external_id\"}],\"id\":\"id\",\"interval\":\"interval\",\"period_end\":1,\"period_start\":1,\"products\":[{\"billing_scheme\":\"billing_scheme\",\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"id\":\"id\",\"interval\":\"interval\",\"name\":\"name\",\"package_size\":1,\"price\":1,\"price_external_id\":\"price_external_id\",\"price_id\":\"price_id\",\"price_tier\":[{}],\"quantity\":1.1,\"subscription_id\":\"subscription_id\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"usage_type\":\"usage_type\"}],\"status\":\"status\",\"subscription_external_id\":\"subscription_external_id\",\"total_price\":1}],\"created_at\":\"2024-01-15T09:30:00Z\",\"default_payment_method\":{\"account_last4\":\"account_last4\",\"account_name\":\"account_name\",\"bank_name\":\"bank_name\",\"billing_email\":\"billing_email\",\"billing_name\":\"billing_name\",\"card_brand\":\"card_brand\",\"card_exp_month\":1,\"card_exp_year\":1,\"card_last4\":\"card_last4\",\"company_id\":\"company_id\",\"created_at\":\"2024-01-15T09:30:00Z\",\"customer_external_id\":\"customer_external_id\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"id\":\"id\",\"payment_method_type\":\"payment_method_type\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"entity_traits\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"definition_id\":\"definition_id\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"value\":\"value\"}],\"environment_id\":\"environment_id\",\"id\":\"id\",\"keys\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"definition_id\":\"definition_id\",\"entity_id\":\"entity_id\",\"entity_type\":\"entity_type\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"key\":\"key\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"value\":\"value\"}],\"last_seen_at\":\"2024-01-15T09:30:00Z\",\"logo_url\":\"logo_url\",\"metrics\":[{\"account_id\":\"account_id\",\"captured_at_max\":\"2024-01-15T09:30:00Z\",\"captured_at_min\":\"2024-01-15T09:30:00Z\",\"company_id\":\"company_id\",\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"event_subtype\":\"event_subtype\",\"month_reset\":\"month_reset\",\"period\":\"period\",\"value\":1}],\"name\":\"name\",\"payment_methods\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"customer_external_id\":\"customer_external_id\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"id\":\"id\",\"payment_method_type\":\"payment_method_type\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"plan\":{\"added_on\":\"2024-01-15T09:30:00Z\",\"billing_product_external_id\":\"billing_product_external_id\",\"billing_product_id\":\"billing_product_id\",\"description\":\"description\",\"id\":\"id\",\"image_url\":\"image_url\",\"name\":\"name\",\"plan_period\":\"plan_period\",\"plan_price\":1},\"plans\":[{\"id\":\"id\",\"name\":\"name\"}],\"rules\":[{\"account_id\":\"account_id\",\"condition_groups\":[{\"conditions\":[{\"account_id\":\"account_id\",\"condition_type\":\"condition_type\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"operator\":\"operator\",\"resource_ids\":[\"resource_ids\"],\"trait_value\":\"trait_value\"}]}],\"conditions\":[{\"account_id\":\"account_id\",\"condition_type\":\"condition_type\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"operator\":\"operator\",\"resource_ids\":[\"resource_ids\"],\"trait_value\":\"trait_value\"}],\"environment_id\":\"environment_id\",\"id\":\"id\",\"name\":\"name\",\"priority\":1,\"rule_type\":\"rule_type\",\"value\":true}],\"traits\":{\"key\":\"value\"},\"updated_at\":\"2024-01-15T09:30:00Z\",\"user_count\":1},\"params\":{\"key\":\"value\"}}"));
        GetCompanyResponse response = client.companies().getCompany("company_id");
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("GET", request.getMethod());

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
    public void testDeleteCompany() throws Exception {
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody("{\"data\":{\"deleted\":true},\"params\":{\"key\":\"value\"}}"));
        DeleteCompanyResponse response = client.companies().deleteCompany("company_id");
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
    public void testCountCompanies() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":{\"count\":1},\"params\":{\"ids\":[\"ids\"],\"limit\":1,\"offset\":1,\"plan_id\":\"plan_id\",\"q\":\"q\",\"with_subscription\":true,\"without_feature_override_for\":\"without_feature_override_for\",\"without_plan\":true}}"));
        CountCompaniesResponse response = client.companies()
                .countCompanies(CountCompaniesRequest.builder()
                        .planId("plan_id")
                        .q("q")
                        .withoutFeatureOverrideFor("without_feature_override_for")
                        .withoutPlan(true)
                        .withSubscription(true)
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
                + "    \"plan_id\": \"plan_id\",\n"
                + "    \"q\": \"q\",\n"
                + "    \"with_subscription\": true,\n"
                + "    \"without_feature_override_for\": \"without_feature_override_for\",\n"
                + "    \"without_plan\": true\n"
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
    public void testCountCompaniesForAdvancedFilter() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":{\"count\":1},\"params\":{\"credit_type_ids\":[\"credit_type_ids\"],\"display_properties\":[\"display_properties\"],\"feature_ids\":[\"feature_ids\"],\"ids\":[\"ids\"],\"limit\":1,\"monetized_subscriptions\":true,\"offset\":1,\"plan_ids\":[\"plan_ids\"],\"q\":\"q\",\"sort_order_column\":\"sort_order_column\",\"sort_order_direction\":\"asc\",\"subscription_statuses\":[\"subscription_statuses\"],\"subscription_types\":[\"subscription_types\"],\"without_plan\":true,\"without_subscription\":true}}"));
        CountCompaniesForAdvancedFilterResponse response = client.companies()
                .countCompaniesForAdvancedFilter(CountCompaniesForAdvancedFilterRequest.builder()
                        .monetizedSubscriptions(true)
                        .q("q")
                        .withoutPlan(true)
                        .withoutSubscription(true)
                        .sortOrderColumn("sort_order_column")
                        .sortOrderDirection(CountCompaniesForAdvancedFilterRequestSortOrderDirection.ASC)
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
                + "    \"credit_type_ids\": [\n"
                + "      \"credit_type_ids\"\n"
                + "    ],\n"
                + "    \"display_properties\": [\n"
                + "      \"display_properties\"\n"
                + "    ],\n"
                + "    \"feature_ids\": [\n"
                + "      \"feature_ids\"\n"
                + "    ],\n"
                + "    \"ids\": [\n"
                + "      \"ids\"\n"
                + "    ],\n"
                + "    \"limit\": 1,\n"
                + "    \"monetized_subscriptions\": true,\n"
                + "    \"offset\": 1,\n"
                + "    \"plan_ids\": [\n"
                + "      \"plan_ids\"\n"
                + "    ],\n"
                + "    \"q\": \"q\",\n"
                + "    \"sort_order_column\": \"sort_order_column\",\n"
                + "    \"sort_order_direction\": \"asc\",\n"
                + "    \"subscription_statuses\": [\n"
                + "      \"subscription_statuses\"\n"
                + "    ],\n"
                + "    \"subscription_types\": [\n"
                + "      \"subscription_types\"\n"
                + "    ],\n"
                + "    \"without_plan\": true,\n"
                + "    \"without_subscription\": true\n"
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
    public void testCreateCompany() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":{\"add_ons\":[{\"id\":\"id\",\"name\":\"name\"}],\"billing_credit_balances\":{\"key\":1.1},\"billing_subscription\":{\"cancel_at\":1,\"cancel_at_period_end\":true,\"company_id\":\"company_id\",\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"customer_external_id\":\"customer_external_id\",\"default_payment_method_id\":\"default_payment_method_id\",\"discounts\":[{\"coupon_id\":\"coupon_id\",\"coupon_name\":\"coupon_name\",\"discount_external_id\":\"discount_external_id\",\"duration\":\"duration\",\"is_active\":true,\"started_at\":\"2024-01-15T09:30:00Z\",\"subscription_external_id\":\"subscription_external_id\"}],\"expired_at\":\"2024-01-15T09:30:00Z\",\"id\":\"id\",\"interval\":\"interval\",\"latest_invoice\":{\"amount_due\":1,\"amount_paid\":1,\"amount_remaining\":1,\"collection_method\":\"collection_method\",\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"customer_external_id\":\"customer_external_id\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"subtotal\":1,\"updated_at\":\"2024-01-15T09:30:00Z\"},\"metadata\":{\"key\":\"value\"},\"payment_method\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"customer_external_id\":\"customer_external_id\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"id\":\"id\",\"payment_method_type\":\"payment_method_type\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"period_end\":1,\"period_start\":1,\"products\":[{\"billing_scheme\":\"billing_scheme\",\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"id\":\"id\",\"interval\":\"interval\",\"name\":\"name\",\"package_size\":1,\"price\":1,\"price_external_id\":\"price_external_id\",\"price_id\":\"price_id\",\"price_tier\":[{}],\"quantity\":1.1,\"subscription_id\":\"subscription_id\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"usage_type\":\"usage_type\"}],\"status\":\"status\",\"subscription_external_id\":\"subscription_external_id\",\"total_price\":1,\"trial_end\":1,\"trial_end_setting\":\"trial_end_setting\"},\"billing_subscriptions\":[{\"cancel_at_period_end\":true,\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"customer_external_id\":\"customer_external_id\",\"discounts\":[{\"coupon_id\":\"coupon_id\",\"coupon_name\":\"coupon_name\",\"discount_external_id\":\"discount_external_id\",\"duration\":\"duration\",\"is_active\":true,\"started_at\":\"2024-01-15T09:30:00Z\",\"subscription_external_id\":\"subscription_external_id\"}],\"id\":\"id\",\"interval\":\"interval\",\"period_end\":1,\"period_start\":1,\"products\":[{\"billing_scheme\":\"billing_scheme\",\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"id\":\"id\",\"interval\":\"interval\",\"name\":\"name\",\"package_size\":1,\"price\":1,\"price_external_id\":\"price_external_id\",\"price_id\":\"price_id\",\"price_tier\":[{}],\"quantity\":1.1,\"subscription_id\":\"subscription_id\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"usage_type\":\"usage_type\"}],\"status\":\"status\",\"subscription_external_id\":\"subscription_external_id\",\"total_price\":1}],\"created_at\":\"2024-01-15T09:30:00Z\",\"default_payment_method\":{\"account_last4\":\"account_last4\",\"account_name\":\"account_name\",\"bank_name\":\"bank_name\",\"billing_email\":\"billing_email\",\"billing_name\":\"billing_name\",\"card_brand\":\"card_brand\",\"card_exp_month\":1,\"card_exp_year\":1,\"card_last4\":\"card_last4\",\"company_id\":\"company_id\",\"created_at\":\"2024-01-15T09:30:00Z\",\"customer_external_id\":\"customer_external_id\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"id\":\"id\",\"payment_method_type\":\"payment_method_type\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"entity_traits\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"definition_id\":\"definition_id\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"value\":\"value\"}],\"environment_id\":\"environment_id\",\"id\":\"id\",\"keys\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"definition_id\":\"definition_id\",\"entity_id\":\"entity_id\",\"entity_type\":\"entity_type\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"key\":\"key\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"value\":\"value\"}],\"last_seen_at\":\"2024-01-15T09:30:00Z\",\"logo_url\":\"logo_url\",\"metrics\":[{\"account_id\":\"account_id\",\"captured_at_max\":\"2024-01-15T09:30:00Z\",\"captured_at_min\":\"2024-01-15T09:30:00Z\",\"company_id\":\"company_id\",\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"event_subtype\":\"event_subtype\",\"month_reset\":\"month_reset\",\"period\":\"period\",\"value\":1}],\"name\":\"name\",\"payment_methods\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"customer_external_id\":\"customer_external_id\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"id\":\"id\",\"payment_method_type\":\"payment_method_type\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"plan\":{\"added_on\":\"2024-01-15T09:30:00Z\",\"billing_product_external_id\":\"billing_product_external_id\",\"billing_product_id\":\"billing_product_id\",\"description\":\"description\",\"id\":\"id\",\"image_url\":\"image_url\",\"name\":\"name\",\"plan_period\":\"plan_period\",\"plan_price\":1},\"plans\":[{\"id\":\"id\",\"name\":\"name\"}],\"rules\":[{\"account_id\":\"account_id\",\"condition_groups\":[{\"conditions\":[{\"account_id\":\"account_id\",\"condition_type\":\"condition_type\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"operator\":\"operator\",\"resource_ids\":[\"resource_ids\"],\"trait_value\":\"trait_value\"}]}],\"conditions\":[{\"account_id\":\"account_id\",\"condition_type\":\"condition_type\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"operator\":\"operator\",\"resource_ids\":[\"resource_ids\"],\"trait_value\":\"trait_value\"}],\"environment_id\":\"environment_id\",\"id\":\"id\",\"name\":\"name\",\"priority\":1,\"rule_type\":\"rule_type\",\"value\":true}],\"traits\":{\"key\":\"value\"},\"updated_at\":\"2024-01-15T09:30:00Z\",\"user_count\":1},\"params\":{\"key\":\"value\"}}"));
        CreateCompanyResponse response = client.companies()
                .createCompany(UpsertCompanyRequestBody.builder()
                        .keys(new HashMap<String, String>() {
                            {
                                put("key", "value");
                            }
                        })
                        .build());
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("POST", request.getMethod());
        // Validate request body
        String actualRequestBody = request.getBody().readUtf8();
        String expectedRequestBody = "" + "{\n" + "  \"keys\": {\n" + "    \"key\": \"value\"\n" + "  }\n" + "}";
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
    public void testDeleteCompanyByKeys() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"last_seen_at\":\"2024-01-15T09:30:00Z\",\"logo_url\":\"logo_url\",\"name\":\"name\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"params\":{\"key\":\"value\"}}"));
        DeleteCompanyByKeysResponse response = client.companies()
                .deleteCompanyByKeys(KeysRequestBody.builder()
                        .keys(new HashMap<String, String>() {
                            {
                                put("key", "value");
                            }
                        })
                        .build());
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("POST", request.getMethod());
        // Validate request body
        String actualRequestBody = request.getBody().readUtf8();
        String expectedRequestBody = "" + "{\n" + "  \"keys\": {\n" + "    \"key\": \"value\"\n" + "  }\n" + "}";
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
                + "    \"environment_id\": \"environment_id\",\n"
                + "    \"id\": \"id\",\n"
                + "    \"last_seen_at\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"logo_url\": \"logo_url\",\n"
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
    public void testListCompaniesForAdvancedFilter() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":[{\"add_ons\":[{\"id\":\"id\",\"name\":\"name\"}],\"billing_credit_balances\":{\"key\":1.1},\"billing_subscription\":{\"cancel_at_period_end\":true,\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"customer_external_id\":\"customer_external_id\",\"discounts\":[{\"coupon_id\":\"coupon_id\",\"coupon_name\":\"coupon_name\",\"discount_external_id\":\"discount_external_id\",\"duration\":\"duration\",\"is_active\":true,\"started_at\":\"2024-01-15T09:30:00Z\",\"subscription_external_id\":\"subscription_external_id\"}],\"id\":\"id\",\"interval\":\"interval\",\"period_end\":1,\"period_start\":1,\"products\":[{\"billing_scheme\":\"billing_scheme\",\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"id\":\"id\",\"interval\":\"interval\",\"name\":\"name\",\"package_size\":1,\"price\":1,\"price_external_id\":\"price_external_id\",\"price_id\":\"price_id\",\"price_tier\":[{}],\"quantity\":1.1,\"subscription_id\":\"subscription_id\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"usage_type\":\"usage_type\"}],\"status\":\"status\",\"subscription_external_id\":\"subscription_external_id\",\"total_price\":1},\"billing_subscriptions\":[{\"cancel_at_period_end\":true,\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"customer_external_id\":\"customer_external_id\",\"discounts\":[{\"coupon_id\":\"coupon_id\",\"coupon_name\":\"coupon_name\",\"discount_external_id\":\"discount_external_id\",\"duration\":\"duration\",\"is_active\":true,\"started_at\":\"2024-01-15T09:30:00Z\",\"subscription_external_id\":\"subscription_external_id\"}],\"id\":\"id\",\"interval\":\"interval\",\"period_end\":1,\"period_start\":1,\"products\":[{\"billing_scheme\":\"billing_scheme\",\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"id\":\"id\",\"interval\":\"interval\",\"name\":\"name\",\"package_size\":1,\"price\":1,\"price_external_id\":\"price_external_id\",\"price_id\":\"price_id\",\"price_tier\":[{}],\"quantity\":1.1,\"subscription_id\":\"subscription_id\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"usage_type\":\"usage_type\"}],\"status\":\"status\",\"subscription_external_id\":\"subscription_external_id\",\"total_price\":1}],\"created_at\":\"2024-01-15T09:30:00Z\",\"default_payment_method\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"customer_external_id\":\"customer_external_id\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"id\":\"id\",\"payment_method_type\":\"payment_method_type\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"entity_traits\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"definition_id\":\"definition_id\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"value\":\"value\"}],\"environment_id\":\"environment_id\",\"feature_usage\":[{\"entitlement_source\":\"entitlement_source\",\"entitlement_value_type\":\"entitlement_value_type\",\"feature_id\":\"feature_id\",\"feature_name\":\"feature_name\",\"feature_type\":\"feature_type\",\"hard_limit\":\"hard_limit\",\"has_access\":true,\"soft_limit\":\"soft_limit\",\"usage\":\"usage\"}],\"id\":\"id\",\"keys\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"definition_id\":\"definition_id\",\"entity_id\":\"entity_id\",\"entity_type\":\"entity_type\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"key\":\"key\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"value\":\"value\"}],\"last_seen_at\":\"2024-01-15T09:30:00Z\",\"logo_url\":\"logo_url\",\"metrics\":[{\"account_id\":\"account_id\",\"captured_at_max\":\"2024-01-15T09:30:00Z\",\"captured_at_min\":\"2024-01-15T09:30:00Z\",\"company_id\":\"company_id\",\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"event_subtype\":\"event_subtype\",\"month_reset\":\"month_reset\",\"period\":\"period\",\"value\":1}],\"name\":\"name\",\"payment_methods\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"customer_external_id\":\"customer_external_id\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"id\":\"id\",\"payment_method_type\":\"payment_method_type\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"plan\":{\"id\":\"id\",\"name\":\"name\"},\"plans\":[{\"id\":\"id\",\"name\":\"name\"}],\"rules\":[{\"account_id\":\"account_id\",\"condition_groups\":[{\"conditions\":[{\"account_id\":\"account_id\",\"condition_type\":\"condition_type\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"operator\":\"operator\",\"resource_ids\":[\"resource_ids\"],\"trait_value\":\"trait_value\"}]}],\"conditions\":[{\"account_id\":\"account_id\",\"condition_type\":\"condition_type\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"operator\":\"operator\",\"resource_ids\":[\"resource_ids\"],\"trait_value\":\"trait_value\"}],\"environment_id\":\"environment_id\",\"id\":\"id\",\"name\":\"name\",\"priority\":1,\"rule_type\":\"rule_type\",\"value\":true}],\"traits\":{\"key\":\"value\"},\"updated_at\":\"2024-01-15T09:30:00Z\",\"user_count\":1}],\"params\":{\"credit_type_ids\":[\"credit_type_ids\"],\"display_properties\":[\"display_properties\"],\"feature_ids\":[\"feature_ids\"],\"ids\":[\"ids\"],\"limit\":1,\"monetized_subscriptions\":true,\"offset\":1,\"plan_ids\":[\"plan_ids\"],\"q\":\"q\",\"sort_order_column\":\"sort_order_column\",\"sort_order_direction\":\"asc\",\"subscription_statuses\":[\"subscription_statuses\"],\"subscription_types\":[\"subscription_types\"],\"without_plan\":true,\"without_subscription\":true}}"));
        ListCompaniesForAdvancedFilterResponse response = client.companies()
                .listCompaniesForAdvancedFilter(ListCompaniesForAdvancedFilterRequest.builder()
                        .monetizedSubscriptions(true)
                        .q("q")
                        .withoutPlan(true)
                        .withoutSubscription(true)
                        .sortOrderColumn("sort_order_column")
                        .sortOrderDirection(ListCompaniesForAdvancedFilterRequestSortOrderDirection.ASC)
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
                + "      \"feature_usage\": [\n"
                + "        {\n"
                + "          \"entitlement_source\": \"entitlement_source\",\n"
                + "          \"entitlement_value_type\": \"entitlement_value_type\",\n"
                + "          \"feature_id\": \"feature_id\",\n"
                + "          \"feature_name\": \"feature_name\",\n"
                + "          \"feature_type\": \"feature_type\",\n"
                + "          \"hard_limit\": \"hard_limit\",\n"
                + "          \"has_access\": true,\n"
                + "          \"soft_limit\": \"soft_limit\",\n"
                + "          \"usage\": \"usage\"\n"
                + "        }\n"
                + "      ],\n"
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
                + "    }\n"
                + "  ],\n"
                + "  \"params\": {\n"
                + "    \"credit_type_ids\": [\n"
                + "      \"credit_type_ids\"\n"
                + "    ],\n"
                + "    \"display_properties\": [\n"
                + "      \"display_properties\"\n"
                + "    ],\n"
                + "    \"feature_ids\": [\n"
                + "      \"feature_ids\"\n"
                + "    ],\n"
                + "    \"ids\": [\n"
                + "      \"ids\"\n"
                + "    ],\n"
                + "    \"limit\": 1,\n"
                + "    \"monetized_subscriptions\": true,\n"
                + "    \"offset\": 1,\n"
                + "    \"plan_ids\": [\n"
                + "      \"plan_ids\"\n"
                + "    ],\n"
                + "    \"q\": \"q\",\n"
                + "    \"sort_order_column\": \"sort_order_column\",\n"
                + "    \"sort_order_direction\": \"asc\",\n"
                + "    \"subscription_statuses\": [\n"
                + "      \"subscription_statuses\"\n"
                + "    ],\n"
                + "    \"subscription_types\": [\n"
                + "      \"subscription_types\"\n"
                + "    ],\n"
                + "    \"without_plan\": true,\n"
                + "    \"without_subscription\": true\n"
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
    public void testLookupCompany() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":{\"add_ons\":[{\"added_on\":\"2024-01-15T09:30:00Z\",\"billing_product_external_id\":\"billing_product_external_id\",\"billing_product_id\":\"billing_product_id\",\"description\":\"description\",\"id\":\"id\",\"image_url\":\"image_url\",\"name\":\"name\",\"plan_period\":\"plan_period\",\"plan_price\":1},{\"added_on\":\"2024-01-15T09:30:00Z\",\"billing_product_external_id\":\"billing_product_external_id\",\"billing_product_id\":\"billing_product_id\",\"description\":\"description\",\"id\":\"id\",\"image_url\":\"image_url\",\"name\":\"name\",\"plan_period\":\"plan_period\",\"plan_price\":1}],\"billing_credit_balances\":{\"billing_credit_balances\":1.1},\"billing_subscription\":{\"cancel_at\":1,\"cancel_at_period_end\":true,\"company_id\":\"company_id\",\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"customer_external_id\":\"customer_external_id\",\"default_payment_method_id\":\"default_payment_method_id\",\"discounts\":[{\"amount_off\":1,\"coupon_id\":\"coupon_id\",\"coupon_name\":\"coupon_name\",\"currency\":\"currency\",\"customer_facing_code\":\"customer_facing_code\",\"discount_external_id\":\"discount_external_id\",\"duration\":\"duration\",\"duration_in_months\":1,\"ended_at\":\"2024-01-15T09:30:00Z\",\"is_active\":true,\"percent_off\":1.1,\"promo_code_external_id\":\"promo_code_external_id\",\"started_at\":\"2024-01-15T09:30:00Z\",\"subscription_external_id\":\"subscription_external_id\"},{\"amount_off\":1,\"coupon_id\":\"coupon_id\",\"coupon_name\":\"coupon_name\",\"currency\":\"currency\",\"customer_facing_code\":\"customer_facing_code\",\"discount_external_id\":\"discount_external_id\",\"duration\":\"duration\",\"duration_in_months\":1,\"ended_at\":\"2024-01-15T09:30:00Z\",\"is_active\":true,\"percent_off\":1.1,\"promo_code_external_id\":\"promo_code_external_id\",\"started_at\":\"2024-01-15T09:30:00Z\",\"subscription_external_id\":\"subscription_external_id\"}],\"expired_at\":\"2024-01-15T09:30:00Z\",\"id\":\"id\",\"interval\":\"interval\",\"latest_invoice\":{\"amount_due\":1,\"amount_paid\":1,\"amount_remaining\":1,\"collection_method\":\"collection_method\",\"company_id\":\"company_id\",\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"customer_external_id\":\"customer_external_id\",\"due_date\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"id\":\"id\",\"payment_method_external_id\":\"payment_method_external_id\",\"subscription_external_id\":\"subscription_external_id\",\"subtotal\":1,\"updated_at\":\"2024-01-15T09:30:00Z\",\"url\":\"url\"},\"metadata\":{\"metadata\":{\"key\":\"value\"}},\"payment_method\":{\"account_last4\":\"account_last4\",\"account_name\":\"account_name\",\"bank_name\":\"bank_name\",\"billing_email\":\"billing_email\",\"billing_name\":\"billing_name\",\"card_brand\":\"card_brand\",\"card_exp_month\":1,\"card_exp_year\":1,\"card_last4\":\"card_last4\",\"company_id\":\"company_id\",\"created_at\":\"2024-01-15T09:30:00Z\",\"customer_external_id\":\"customer_external_id\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"id\":\"id\",\"payment_method_type\":\"payment_method_type\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"period_end\":1,\"period_start\":1,\"products\":[{\"billing_scheme\":\"billing_scheme\",\"billing_threshold\":1,\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"id\":\"id\",\"interval\":\"interval\",\"meter_id\":\"meter_id\",\"name\":\"name\",\"package_size\":1,\"price\":1,\"price_decimal\":\"price_decimal\",\"price_external_id\":\"price_external_id\",\"price_id\":\"price_id\",\"price_tier\":[{\"flat_amount\":1,\"per_unit_price\":1,\"per_unit_price_decimal\":\"per_unit_price_decimal\",\"up_to\":1},{\"flat_amount\":1,\"per_unit_price\":1,\"per_unit_price_decimal\":\"per_unit_price_decimal\",\"up_to\":1}],\"quantity\":1.1,\"subscription_id\":\"subscription_id\",\"subscription_item_external_id\":\"subscription_item_external_id\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"usage_type\":\"usage_type\"},{\"billing_scheme\":\"billing_scheme\",\"billing_threshold\":1,\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"id\":\"id\",\"interval\":\"interval\",\"meter_id\":\"meter_id\",\"name\":\"name\",\"package_size\":1,\"price\":1,\"price_decimal\":\"price_decimal\",\"price_external_id\":\"price_external_id\",\"price_id\":\"price_id\",\"price_tier\":[{\"flat_amount\":1,\"per_unit_price\":1,\"per_unit_price_decimal\":\"per_unit_price_decimal\",\"up_to\":1},{\"flat_amount\":1,\"per_unit_price\":1,\"per_unit_price_decimal\":\"per_unit_price_decimal\",\"up_to\":1}],\"quantity\":1.1,\"subscription_id\":\"subscription_id\",\"subscription_item_external_id\":\"subscription_item_external_id\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"usage_type\":\"usage_type\"}],\"status\":\"status\",\"subscription_external_id\":\"subscription_external_id\",\"total_price\":1,\"trial_end\":1,\"trial_end_setting\":\"trial_end_setting\"},\"billing_subscriptions\":[{\"cancel_at\":1,\"cancel_at_period_end\":true,\"company_id\":\"company_id\",\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"customer_external_id\":\"customer_external_id\",\"default_payment_method_id\":\"default_payment_method_id\",\"discounts\":[{\"amount_off\":1,\"coupon_id\":\"coupon_id\",\"coupon_name\":\"coupon_name\",\"currency\":\"currency\",\"customer_facing_code\":\"customer_facing_code\",\"discount_external_id\":\"discount_external_id\",\"duration\":\"duration\",\"duration_in_months\":1,\"ended_at\":\"2024-01-15T09:30:00Z\",\"is_active\":true,\"percent_off\":1.1,\"promo_code_external_id\":\"promo_code_external_id\",\"started_at\":\"2024-01-15T09:30:00Z\",\"subscription_external_id\":\"subscription_external_id\"},{\"amount_off\":1,\"coupon_id\":\"coupon_id\",\"coupon_name\":\"coupon_name\",\"currency\":\"currency\",\"customer_facing_code\":\"customer_facing_code\",\"discount_external_id\":\"discount_external_id\",\"duration\":\"duration\",\"duration_in_months\":1,\"ended_at\":\"2024-01-15T09:30:00Z\",\"is_active\":true,\"percent_off\":1.1,\"promo_code_external_id\":\"promo_code_external_id\",\"started_at\":\"2024-01-15T09:30:00Z\",\"subscription_external_id\":\"subscription_external_id\"}],\"expired_at\":\"2024-01-15T09:30:00Z\",\"id\":\"id\",\"interval\":\"interval\",\"latest_invoice\":{\"amount_due\":1,\"amount_paid\":1,\"amount_remaining\":1,\"collection_method\":\"collection_method\",\"company_id\":\"company_id\",\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"customer_external_id\":\"customer_external_id\",\"due_date\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"id\":\"id\",\"payment_method_external_id\":\"payment_method_external_id\",\"subscription_external_id\":\"subscription_external_id\",\"subtotal\":1,\"updated_at\":\"2024-01-15T09:30:00Z\",\"url\":\"url\"},\"metadata\":{\"metadata\":{\"key\":\"value\"}},\"payment_method\":{\"account_last4\":\"account_last4\",\"account_name\":\"account_name\",\"bank_name\":\"bank_name\",\"billing_email\":\"billing_email\",\"billing_name\":\"billing_name\",\"card_brand\":\"card_brand\",\"card_exp_month\":1,\"card_exp_year\":1,\"card_last4\":\"card_last4\",\"company_id\":\"company_id\",\"created_at\":\"2024-01-15T09:30:00Z\",\"customer_external_id\":\"customer_external_id\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"id\":\"id\",\"payment_method_type\":\"payment_method_type\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"period_end\":1,\"period_start\":1,\"products\":[{\"billing_scheme\":\"billing_scheme\",\"billing_threshold\":1,\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"id\":\"id\",\"interval\":\"interval\",\"meter_id\":\"meter_id\",\"name\":\"name\",\"package_size\":1,\"price\":1,\"price_decimal\":\"price_decimal\",\"price_external_id\":\"price_external_id\",\"price_id\":\"price_id\",\"price_tier\":[{\"flat_amount\":1,\"per_unit_price\":1,\"per_unit_price_decimal\":\"per_unit_price_decimal\",\"up_to\":1},{\"flat_amount\":1,\"per_unit_price\":1,\"per_unit_price_decimal\":\"per_unit_price_decimal\",\"up_to\":1}],\"quantity\":1.1,\"subscription_id\":\"subscription_id\",\"subscription_item_external_id\":\"subscription_item_external_id\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"usage_type\":\"usage_type\"},{\"billing_scheme\":\"billing_scheme\",\"billing_threshold\":1,\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"id\":\"id\",\"interval\":\"interval\",\"meter_id\":\"meter_id\",\"name\":\"name\",\"package_size\":1,\"price\":1,\"price_decimal\":\"price_decimal\",\"price_external_id\":\"price_external_id\",\"price_id\":\"price_id\",\"price_tier\":[{\"flat_amount\":1,\"per_unit_price\":1,\"per_unit_price_decimal\":\"per_unit_price_decimal\",\"up_to\":1},{\"flat_amount\":1,\"per_unit_price\":1,\"per_unit_price_decimal\":\"per_unit_price_decimal\",\"up_to\":1}],\"quantity\":1.1,\"subscription_id\":\"subscription_id\",\"subscription_item_external_id\":\"subscription_item_external_id\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"usage_type\":\"usage_type\"}],\"status\":\"status\",\"subscription_external_id\":\"subscription_external_id\",\"total_price\":1,\"trial_end\":1,\"trial_end_setting\":\"trial_end_setting\"},{\"cancel_at\":1,\"cancel_at_period_end\":true,\"company_id\":\"company_id\",\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"customer_external_id\":\"customer_external_id\",\"default_payment_method_id\":\"default_payment_method_id\",\"discounts\":[{\"amount_off\":1,\"coupon_id\":\"coupon_id\",\"coupon_name\":\"coupon_name\",\"currency\":\"currency\",\"customer_facing_code\":\"customer_facing_code\",\"discount_external_id\":\"discount_external_id\",\"duration\":\"duration\",\"duration_in_months\":1,\"ended_at\":\"2024-01-15T09:30:00Z\",\"is_active\":true,\"percent_off\":1.1,\"promo_code_external_id\":\"promo_code_external_id\",\"started_at\":\"2024-01-15T09:30:00Z\",\"subscription_external_id\":\"subscription_external_id\"},{\"amount_off\":1,\"coupon_id\":\"coupon_id\",\"coupon_name\":\"coupon_name\",\"currency\":\"currency\",\"customer_facing_code\":\"customer_facing_code\",\"discount_external_id\":\"discount_external_id\",\"duration\":\"duration\",\"duration_in_months\":1,\"ended_at\":\"2024-01-15T09:30:00Z\",\"is_active\":true,\"percent_off\":1.1,\"promo_code_external_id\":\"promo_code_external_id\",\"started_at\":\"2024-01-15T09:30:00Z\",\"subscription_external_id\":\"subscription_external_id\"}],\"expired_at\":\"2024-01-15T09:30:00Z\",\"id\":\"id\",\"interval\":\"interval\",\"latest_invoice\":{\"amount_due\":1,\"amount_paid\":1,\"amount_remaining\":1,\"collection_method\":\"collection_method\",\"company_id\":\"company_id\",\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"customer_external_id\":\"customer_external_id\",\"due_date\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"id\":\"id\",\"payment_method_external_id\":\"payment_method_external_id\",\"subscription_external_id\":\"subscription_external_id\",\"subtotal\":1,\"updated_at\":\"2024-01-15T09:30:00Z\",\"url\":\"url\"},\"metadata\":{\"metadata\":{\"key\":\"value\"}},\"payment_method\":{\"account_last4\":\"account_last4\",\"account_name\":\"account_name\",\"bank_name\":\"bank_name\",\"billing_email\":\"billing_email\",\"billing_name\":\"billing_name\",\"card_brand\":\"card_brand\",\"card_exp_month\":1,\"card_exp_year\":1,\"card_last4\":\"card_last4\",\"company_id\":\"company_id\",\"created_at\":\"2024-01-15T09:30:00Z\",\"customer_external_id\":\"customer_external_id\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"id\":\"id\",\"payment_method_type\":\"payment_method_type\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"period_end\":1,\"period_start\":1,\"products\":[{\"billing_scheme\":\"billing_scheme\",\"billing_threshold\":1,\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"id\":\"id\",\"interval\":\"interval\",\"meter_id\":\"meter_id\",\"name\":\"name\",\"package_size\":1,\"price\":1,\"price_decimal\":\"price_decimal\",\"price_external_id\":\"price_external_id\",\"price_id\":\"price_id\",\"price_tier\":[{\"flat_amount\":1,\"per_unit_price\":1,\"per_unit_price_decimal\":\"per_unit_price_decimal\",\"up_to\":1},{\"flat_amount\":1,\"per_unit_price\":1,\"per_unit_price_decimal\":\"per_unit_price_decimal\",\"up_to\":1}],\"quantity\":1.1,\"subscription_id\":\"subscription_id\",\"subscription_item_external_id\":\"subscription_item_external_id\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"usage_type\":\"usage_type\"},{\"billing_scheme\":\"billing_scheme\",\"billing_threshold\":1,\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"id\":\"id\",\"interval\":\"interval\",\"meter_id\":\"meter_id\",\"name\":\"name\",\"package_size\":1,\"price\":1,\"price_decimal\":\"price_decimal\",\"price_external_id\":\"price_external_id\",\"price_id\":\"price_id\",\"price_tier\":[{\"flat_amount\":1,\"per_unit_price\":1,\"per_unit_price_decimal\":\"per_unit_price_decimal\",\"up_to\":1},{\"flat_amount\":1,\"per_unit_price\":1,\"per_unit_price_decimal\":\"per_unit_price_decimal\",\"up_to\":1}],\"quantity\":1.1,\"subscription_id\":\"subscription_id\",\"subscription_item_external_id\":\"subscription_item_external_id\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"usage_type\":\"usage_type\"}],\"status\":\"status\",\"subscription_external_id\":\"subscription_external_id\",\"total_price\":1,\"trial_end\":1,\"trial_end_setting\":\"trial_end_setting\"}],\"created_at\":\"2024-01-15T09:30:00Z\",\"default_payment_method\":{\"account_last4\":\"account_last4\",\"account_name\":\"account_name\",\"bank_name\":\"bank_name\",\"billing_email\":\"billing_email\",\"billing_name\":\"billing_name\",\"card_brand\":\"card_brand\",\"card_exp_month\":1,\"card_exp_year\":1,\"card_last4\":\"card_last4\",\"company_id\":\"company_id\",\"created_at\":\"2024-01-15T09:30:00Z\",\"customer_external_id\":\"customer_external_id\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"id\":\"id\",\"payment_method_type\":\"payment_method_type\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"entity_traits\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"definition\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"display_name\":\"display_name\",\"entity_type\":\"entity_type\",\"hierarchy\":[\"hierarchy\",\"hierarchy\"],\"id\":\"id\",\"trait_type\":\"trait_type\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"definition_id\":\"definition_id\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"value\":\"value\"},{\"created_at\":\"2024-01-15T09:30:00Z\",\"definition\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"display_name\":\"display_name\",\"entity_type\":\"entity_type\",\"hierarchy\":[\"hierarchy\",\"hierarchy\"],\"id\":\"id\",\"trait_type\":\"trait_type\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"definition_id\":\"definition_id\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"value\":\"value\"}],\"environment_id\":\"environment_id\",\"id\":\"id\",\"keys\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"definition\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"entity_type\":\"entity_type\",\"id\":\"id\",\"key\":\"key\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"definition_id\":\"definition_id\",\"entity_id\":\"entity_id\",\"entity_type\":\"entity_type\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"key\":\"key\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"value\":\"value\"},{\"created_at\":\"2024-01-15T09:30:00Z\",\"definition\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"entity_type\":\"entity_type\",\"id\":\"id\",\"key\":\"key\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"definition_id\":\"definition_id\",\"entity_id\":\"entity_id\",\"entity_type\":\"entity_type\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"key\":\"key\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"value\":\"value\"}],\"last_seen_at\":\"2024-01-15T09:30:00Z\",\"logo_url\":\"logo_url\",\"metrics\":[{\"account_id\":\"account_id\",\"captured_at_max\":\"2024-01-15T09:30:00Z\",\"captured_at_min\":\"2024-01-15T09:30:00Z\",\"company_id\":\"company_id\",\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"event_subtype\":\"event_subtype\",\"month_reset\":\"month_reset\",\"period\":\"period\",\"valid_until\":\"2024-01-15T09:30:00Z\",\"value\":1},{\"account_id\":\"account_id\",\"captured_at_max\":\"2024-01-15T09:30:00Z\",\"captured_at_min\":\"2024-01-15T09:30:00Z\",\"company_id\":\"company_id\",\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"event_subtype\":\"event_subtype\",\"month_reset\":\"month_reset\",\"period\":\"period\",\"valid_until\":\"2024-01-15T09:30:00Z\",\"value\":1}],\"name\":\"name\",\"payment_methods\":[{\"account_last4\":\"account_last4\",\"account_name\":\"account_name\",\"bank_name\":\"bank_name\",\"billing_email\":\"billing_email\",\"billing_name\":\"billing_name\",\"card_brand\":\"card_brand\",\"card_exp_month\":1,\"card_exp_year\":1,\"card_last4\":\"card_last4\",\"company_id\":\"company_id\",\"created_at\":\"2024-01-15T09:30:00Z\",\"customer_external_id\":\"customer_external_id\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"id\":\"id\",\"payment_method_type\":\"payment_method_type\",\"updated_at\":\"2024-01-15T09:30:00Z\"},{\"account_last4\":\"account_last4\",\"account_name\":\"account_name\",\"bank_name\":\"bank_name\",\"billing_email\":\"billing_email\",\"billing_name\":\"billing_name\",\"card_brand\":\"card_brand\",\"card_exp_month\":1,\"card_exp_year\":1,\"card_last4\":\"card_last4\",\"company_id\":\"company_id\",\"created_at\":\"2024-01-15T09:30:00Z\",\"customer_external_id\":\"customer_external_id\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"id\":\"id\",\"payment_method_type\":\"payment_method_type\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"plan\":{\"added_on\":\"2024-01-15T09:30:00Z\",\"billing_product_external_id\":\"billing_product_external_id\",\"billing_product_id\":\"billing_product_id\",\"description\":\"description\",\"id\":\"id\",\"image_url\":\"image_url\",\"name\":\"name\",\"plan_period\":\"plan_period\",\"plan_price\":1},\"plans\":[{\"description\":\"description\",\"id\":\"id\",\"image_url\":\"image_url\",\"name\":\"name\"},{\"description\":\"description\",\"id\":\"id\",\"image_url\":\"image_url\",\"name\":\"name\"}],\"rules\":[{\"account_id\":\"account_id\",\"condition_groups\":[{\"conditions\":[{\"account_id\":\"account_id\",\"comparison_trait_definition\":{\"comparable_type\":\"comparable_type\",\"entity_type\":\"entity_type\",\"id\":\"id\"},\"condition_type\":\"condition_type\",\"consumption_rate\":1.1,\"credit_id\":\"credit_id\",\"environment_id\":\"environment_id\",\"event_subtype\":\"event_subtype\",\"id\":\"id\",\"metric_period\":\"metric_period\",\"metric_period_month_reset\":\"metric_period_month_reset\",\"metric_value\":1,\"operator\":\"operator\",\"resource_ids\":[\"resource_ids\",\"resource_ids\"],\"trait_definition\":{\"comparable_type\":\"comparable_type\",\"entity_type\":\"entity_type\",\"id\":\"id\"},\"trait_value\":\"trait_value\"},{\"account_id\":\"account_id\",\"comparison_trait_definition\":{\"comparable_type\":\"comparable_type\",\"entity_type\":\"entity_type\",\"id\":\"id\"},\"condition_type\":\"condition_type\",\"consumption_rate\":1.1,\"credit_id\":\"credit_id\",\"environment_id\":\"environment_id\",\"event_subtype\":\"event_subtype\",\"id\":\"id\",\"metric_period\":\"metric_period\",\"metric_period_month_reset\":\"metric_period_month_reset\",\"metric_value\":1,\"operator\":\"operator\",\"resource_ids\":[\"resource_ids\",\"resource_ids\"],\"trait_definition\":{\"comparable_type\":\"comparable_type\",\"entity_type\":\"entity_type\",\"id\":\"id\"},\"trait_value\":\"trait_value\"}]},{\"conditions\":[{\"account_id\":\"account_id\",\"comparison_trait_definition\":{\"comparable_type\":\"comparable_type\",\"entity_type\":\"entity_type\",\"id\":\"id\"},\"condition_type\":\"condition_type\",\"consumption_rate\":1.1,\"credit_id\":\"credit_id\",\"environment_id\":\"environment_id\",\"event_subtype\":\"event_subtype\",\"id\":\"id\",\"metric_period\":\"metric_period\",\"metric_period_month_reset\":\"metric_period_month_reset\",\"metric_value\":1,\"operator\":\"operator\",\"resource_ids\":[\"resource_ids\",\"resource_ids\"],\"trait_definition\":{\"comparable_type\":\"comparable_type\",\"entity_type\":\"entity_type\",\"id\":\"id\"},\"trait_value\":\"trait_value\"},{\"account_id\":\"account_id\",\"comparison_trait_definition\":{\"comparable_type\":\"comparable_type\",\"entity_type\":\"entity_type\",\"id\":\"id\"},\"condition_type\":\"condition_type\",\"consumption_rate\":1.1,\"credit_id\":\"credit_id\",\"environment_id\":\"environment_id\",\"event_subtype\":\"event_subtype\",\"id\":\"id\",\"metric_period\":\"metric_period\",\"metric_period_month_reset\":\"metric_period_month_reset\",\"metric_value\":1,\"operator\":\"operator\",\"resource_ids\":[\"resource_ids\",\"resource_ids\"],\"trait_definition\":{\"comparable_type\":\"comparable_type\",\"entity_type\":\"entity_type\",\"id\":\"id\"},\"trait_value\":\"trait_value\"}]}],\"conditions\":[{\"account_id\":\"account_id\",\"comparison_trait_definition\":{\"comparable_type\":\"comparable_type\",\"entity_type\":\"entity_type\",\"id\":\"id\"},\"condition_type\":\"condition_type\",\"consumption_rate\":1.1,\"credit_id\":\"credit_id\",\"environment_id\":\"environment_id\",\"event_subtype\":\"event_subtype\",\"id\":\"id\",\"metric_period\":\"metric_period\",\"metric_period_month_reset\":\"metric_period_month_reset\",\"metric_value\":1,\"operator\":\"operator\",\"resource_ids\":[\"resource_ids\",\"resource_ids\"],\"trait_definition\":{\"comparable_type\":\"comparable_type\",\"entity_type\":\"entity_type\",\"id\":\"id\"},\"trait_value\":\"trait_value\"},{\"account_id\":\"account_id\",\"comparison_trait_definition\":{\"comparable_type\":\"comparable_type\",\"entity_type\":\"entity_type\",\"id\":\"id\"},\"condition_type\":\"condition_type\",\"consumption_rate\":1.1,\"credit_id\":\"credit_id\",\"environment_id\":\"environment_id\",\"event_subtype\":\"event_subtype\",\"id\":\"id\",\"metric_period\":\"metric_period\",\"metric_period_month_reset\":\"metric_period_month_reset\",\"metric_value\":1,\"operator\":\"operator\",\"resource_ids\":[\"resource_ids\",\"resource_ids\"],\"trait_definition\":{\"comparable_type\":\"comparable_type\",\"entity_type\":\"entity_type\",\"id\":\"id\"},\"trait_value\":\"trait_value\"}],\"environment_id\":\"environment_id\",\"flag_id\":\"flag_id\",\"id\":\"id\",\"name\":\"name\",\"priority\":1,\"rule_type\":\"rule_type\",\"value\":true},{\"account_id\":\"account_id\",\"condition_groups\":[{\"conditions\":[{\"account_id\":\"account_id\",\"comparison_trait_definition\":{\"comparable_type\":\"comparable_type\",\"entity_type\":\"entity_type\",\"id\":\"id\"},\"condition_type\":\"condition_type\",\"consumption_rate\":1.1,\"credit_id\":\"credit_id\",\"environment_id\":\"environment_id\",\"event_subtype\":\"event_subtype\",\"id\":\"id\",\"metric_period\":\"metric_period\",\"metric_period_month_reset\":\"metric_period_month_reset\",\"metric_value\":1,\"operator\":\"operator\",\"resource_ids\":[\"resource_ids\",\"resource_ids\"],\"trait_definition\":{\"comparable_type\":\"comparable_type\",\"entity_type\":\"entity_type\",\"id\":\"id\"},\"trait_value\":\"trait_value\"},{\"account_id\":\"account_id\",\"comparison_trait_definition\":{\"comparable_type\":\"comparable_type\",\"entity_type\":\"entity_type\",\"id\":\"id\"},\"condition_type\":\"condition_type\",\"consumption_rate\":1.1,\"credit_id\":\"credit_id\",\"environment_id\":\"environment_id\",\"event_subtype\":\"event_subtype\",\"id\":\"id\",\"metric_period\":\"metric_period\",\"metric_period_month_reset\":\"metric_period_month_reset\",\"metric_value\":1,\"operator\":\"operator\",\"resource_ids\":[\"resource_ids\",\"resource_ids\"],\"trait_definition\":{\"comparable_type\":\"comparable_type\",\"entity_type\":\"entity_type\",\"id\":\"id\"},\"trait_value\":\"trait_value\"}]},{\"conditions\":[{\"account_id\":\"account_id\",\"comparison_trait_definition\":{\"comparable_type\":\"comparable_type\",\"entity_type\":\"entity_type\",\"id\":\"id\"},\"condition_type\":\"condition_type\",\"consumption_rate\":1.1,\"credit_id\":\"credit_id\",\"environment_id\":\"environment_id\",\"event_subtype\":\"event_subtype\",\"id\":\"id\",\"metric_period\":\"metric_period\",\"metric_period_month_reset\":\"metric_period_month_reset\",\"metric_value\":1,\"operator\":\"operator\",\"resource_ids\":[\"resource_ids\",\"resource_ids\"],\"trait_definition\":{\"comparable_type\":\"comparable_type\",\"entity_type\":\"entity_type\",\"id\":\"id\"},\"trait_value\":\"trait_value\"},{\"account_id\":\"account_id\",\"comparison_trait_definition\":{\"comparable_type\":\"comparable_type\",\"entity_type\":\"entity_type\",\"id\":\"id\"},\"condition_type\":\"condition_type\",\"consumption_rate\":1.1,\"credit_id\":\"credit_id\",\"environment_id\":\"environment_id\",\"event_subtype\":\"event_subtype\",\"id\":\"id\",\"metric_period\":\"metric_period\",\"metric_period_month_reset\":\"metric_period_month_reset\",\"metric_value\":1,\"operator\":\"operator\",\"resource_ids\":[\"resource_ids\",\"resource_ids\"],\"trait_definition\":{\"comparable_type\":\"comparable_type\",\"entity_type\":\"entity_type\",\"id\":\"id\"},\"trait_value\":\"trait_value\"}]}],\"conditions\":[{\"account_id\":\"account_id\",\"comparison_trait_definition\":{\"comparable_type\":\"comparable_type\",\"entity_type\":\"entity_type\",\"id\":\"id\"},\"condition_type\":\"condition_type\",\"consumption_rate\":1.1,\"credit_id\":\"credit_id\",\"environment_id\":\"environment_id\",\"event_subtype\":\"event_subtype\",\"id\":\"id\",\"metric_period\":\"metric_period\",\"metric_period_month_reset\":\"metric_period_month_reset\",\"metric_value\":1,\"operator\":\"operator\",\"resource_ids\":[\"resource_ids\",\"resource_ids\"],\"trait_definition\":{\"comparable_type\":\"comparable_type\",\"entity_type\":\"entity_type\",\"id\":\"id\"},\"trait_value\":\"trait_value\"},{\"account_id\":\"account_id\",\"comparison_trait_definition\":{\"comparable_type\":\"comparable_type\",\"entity_type\":\"entity_type\",\"id\":\"id\"},\"condition_type\":\"condition_type\",\"consumption_rate\":1.1,\"credit_id\":\"credit_id\",\"environment_id\":\"environment_id\",\"event_subtype\":\"event_subtype\",\"id\":\"id\",\"metric_period\":\"metric_period\",\"metric_period_month_reset\":\"metric_period_month_reset\",\"metric_value\":1,\"operator\":\"operator\",\"resource_ids\":[\"resource_ids\",\"resource_ids\"],\"trait_definition\":{\"comparable_type\":\"comparable_type\",\"entity_type\":\"entity_type\",\"id\":\"id\"},\"trait_value\":\"trait_value\"}],\"environment_id\":\"environment_id\",\"flag_id\":\"flag_id\",\"id\":\"id\",\"name\":\"name\",\"priority\":1,\"rule_type\":\"rule_type\",\"value\":true}],\"traits\":{\"traits\":{\"key\":\"value\"}},\"updated_at\":\"2024-01-15T09:30:00Z\",\"user_count\":1},\"params\":{\"keys\":{\"keys\":\"keys\"}}}"));
        LookupCompanyResponse response = client.companies()
                .lookupCompany(LookupCompanyRequest.builder()
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
                + "    \"add_ons\": [\n"
                + "      {\n"
                + "        \"added_on\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"billing_product_external_id\": \"billing_product_external_id\",\n"
                + "        \"billing_product_id\": \"billing_product_id\",\n"
                + "        \"description\": \"description\",\n"
                + "        \"id\": \"id\",\n"
                + "        \"image_url\": \"image_url\",\n"
                + "        \"name\": \"name\",\n"
                + "        \"plan_period\": \"plan_period\",\n"
                + "        \"plan_price\": 1\n"
                + "      },\n"
                + "      {\n"
                + "        \"added_on\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"billing_product_external_id\": \"billing_product_external_id\",\n"
                + "        \"billing_product_id\": \"billing_product_id\",\n"
                + "        \"description\": \"description\",\n"
                + "        \"id\": \"id\",\n"
                + "        \"image_url\": \"image_url\",\n"
                + "        \"name\": \"name\",\n"
                + "        \"plan_period\": \"plan_period\",\n"
                + "        \"plan_price\": 1\n"
                + "      }\n"
                + "    ],\n"
                + "    \"billing_credit_balances\": {\n"
                + "      \"billing_credit_balances\": 1.1\n"
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
                + "          \"amount_off\": 1,\n"
                + "          \"coupon_id\": \"coupon_id\",\n"
                + "          \"coupon_name\": \"coupon_name\",\n"
                + "          \"currency\": \"currency\",\n"
                + "          \"customer_facing_code\": \"customer_facing_code\",\n"
                + "          \"discount_external_id\": \"discount_external_id\",\n"
                + "          \"duration\": \"duration\",\n"
                + "          \"duration_in_months\": 1,\n"
                + "          \"ended_at\": \"2024-01-15T09:30:00Z\",\n"
                + "          \"is_active\": true,\n"
                + "          \"percent_off\": 1.1,\n"
                + "          \"promo_code_external_id\": \"promo_code_external_id\",\n"
                + "          \"started_at\": \"2024-01-15T09:30:00Z\",\n"
                + "          \"subscription_external_id\": \"subscription_external_id\"\n"
                + "        },\n"
                + "        {\n"
                + "          \"amount_off\": 1,\n"
                + "          \"coupon_id\": \"coupon_id\",\n"
                + "          \"coupon_name\": \"coupon_name\",\n"
                + "          \"currency\": \"currency\",\n"
                + "          \"customer_facing_code\": \"customer_facing_code\",\n"
                + "          \"discount_external_id\": \"discount_external_id\",\n"
                + "          \"duration\": \"duration\",\n"
                + "          \"duration_in_months\": 1,\n"
                + "          \"ended_at\": \"2024-01-15T09:30:00Z\",\n"
                + "          \"is_active\": true,\n"
                + "          \"percent_off\": 1.1,\n"
                + "          \"promo_code_external_id\": \"promo_code_external_id\",\n"
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
                + "        \"company_id\": \"company_id\",\n"
                + "        \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"currency\": \"currency\",\n"
                + "        \"customer_external_id\": \"customer_external_id\",\n"
                + "        \"due_date\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"environment_id\": \"environment_id\",\n"
                + "        \"external_id\": \"external_id\",\n"
                + "        \"id\": \"id\",\n"
                + "        \"payment_method_external_id\": \"payment_method_external_id\",\n"
                + "        \"subscription_external_id\": \"subscription_external_id\",\n"
                + "        \"subtotal\": 1,\n"
                + "        \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"url\": \"url\"\n"
                + "      },\n"
                + "      \"metadata\": {\n"
                + "        \"metadata\": {\n"
                + "          \"key\": \"value\"\n"
                + "        }\n"
                + "      },\n"
                + "      \"payment_method\": {\n"
                + "        \"account_last4\": \"account_last4\",\n"
                + "        \"account_name\": \"account_name\",\n"
                + "        \"bank_name\": \"bank_name\",\n"
                + "        \"billing_email\": \"billing_email\",\n"
                + "        \"billing_name\": \"billing_name\",\n"
                + "        \"card_brand\": \"card_brand\",\n"
                + "        \"card_exp_month\": 1,\n"
                + "        \"card_exp_year\": 1,\n"
                + "        \"card_last4\": \"card_last4\",\n"
                + "        \"company_id\": \"company_id\",\n"
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
                + "          \"billing_threshold\": 1,\n"
                + "          \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "          \"currency\": \"currency\",\n"
                + "          \"environment_id\": \"environment_id\",\n"
                + "          \"external_id\": \"external_id\",\n"
                + "          \"id\": \"id\",\n"
                + "          \"interval\": \"interval\",\n"
                + "          \"meter_id\": \"meter_id\",\n"
                + "          \"name\": \"name\",\n"
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
                + "          \"quantity\": 1.1,\n"
                + "          \"subscription_id\": \"subscription_id\",\n"
                + "          \"subscription_item_external_id\": \"subscription_item_external_id\",\n"
                + "          \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "          \"usage_type\": \"usage_type\"\n"
                + "        },\n"
                + "        {\n"
                + "          \"billing_scheme\": \"billing_scheme\",\n"
                + "          \"billing_threshold\": 1,\n"
                + "          \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "          \"currency\": \"currency\",\n"
                + "          \"environment_id\": \"environment_id\",\n"
                + "          \"external_id\": \"external_id\",\n"
                + "          \"id\": \"id\",\n"
                + "          \"interval\": \"interval\",\n"
                + "          \"meter_id\": \"meter_id\",\n"
                + "          \"name\": \"name\",\n"
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
                + "          \"quantity\": 1.1,\n"
                + "          \"subscription_id\": \"subscription_id\",\n"
                + "          \"subscription_item_external_id\": \"subscription_item_external_id\",\n"
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
                + "        \"cancel_at\": 1,\n"
                + "        \"cancel_at_period_end\": true,\n"
                + "        \"company_id\": \"company_id\",\n"
                + "        \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"currency\": \"currency\",\n"
                + "        \"customer_external_id\": \"customer_external_id\",\n"
                + "        \"default_payment_method_id\": \"default_payment_method_id\",\n"
                + "        \"discounts\": [\n"
                + "          {\n"
                + "            \"amount_off\": 1,\n"
                + "            \"coupon_id\": \"coupon_id\",\n"
                + "            \"coupon_name\": \"coupon_name\",\n"
                + "            \"currency\": \"currency\",\n"
                + "            \"customer_facing_code\": \"customer_facing_code\",\n"
                + "            \"discount_external_id\": \"discount_external_id\",\n"
                + "            \"duration\": \"duration\",\n"
                + "            \"duration_in_months\": 1,\n"
                + "            \"ended_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"is_active\": true,\n"
                + "            \"percent_off\": 1.1,\n"
                + "            \"promo_code_external_id\": \"promo_code_external_id\",\n"
                + "            \"started_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"subscription_external_id\": \"subscription_external_id\"\n"
                + "          },\n"
                + "          {\n"
                + "            \"amount_off\": 1,\n"
                + "            \"coupon_id\": \"coupon_id\",\n"
                + "            \"coupon_name\": \"coupon_name\",\n"
                + "            \"currency\": \"currency\",\n"
                + "            \"customer_facing_code\": \"customer_facing_code\",\n"
                + "            \"discount_external_id\": \"discount_external_id\",\n"
                + "            \"duration\": \"duration\",\n"
                + "            \"duration_in_months\": 1,\n"
                + "            \"ended_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"is_active\": true,\n"
                + "            \"percent_off\": 1.1,\n"
                + "            \"promo_code_external_id\": \"promo_code_external_id\",\n"
                + "            \"started_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"subscription_external_id\": \"subscription_external_id\"\n"
                + "          }\n"
                + "        ],\n"
                + "        \"expired_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"id\": \"id\",\n"
                + "        \"interval\": \"interval\",\n"
                + "        \"latest_invoice\": {\n"
                + "          \"amount_due\": 1,\n"
                + "          \"amount_paid\": 1,\n"
                + "          \"amount_remaining\": 1,\n"
                + "          \"collection_method\": \"collection_method\",\n"
                + "          \"company_id\": \"company_id\",\n"
                + "          \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "          \"currency\": \"currency\",\n"
                + "          \"customer_external_id\": \"customer_external_id\",\n"
                + "          \"due_date\": \"2024-01-15T09:30:00Z\",\n"
                + "          \"environment_id\": \"environment_id\",\n"
                + "          \"external_id\": \"external_id\",\n"
                + "          \"id\": \"id\",\n"
                + "          \"payment_method_external_id\": \"payment_method_external_id\",\n"
                + "          \"subscription_external_id\": \"subscription_external_id\",\n"
                + "          \"subtotal\": 1,\n"
                + "          \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "          \"url\": \"url\"\n"
                + "        },\n"
                + "        \"metadata\": {\n"
                + "          \"metadata\": {\n"
                + "            \"key\": \"value\"\n"
                + "          }\n"
                + "        },\n"
                + "        \"payment_method\": {\n"
                + "          \"account_last4\": \"account_last4\",\n"
                + "          \"account_name\": \"account_name\",\n"
                + "          \"bank_name\": \"bank_name\",\n"
                + "          \"billing_email\": \"billing_email\",\n"
                + "          \"billing_name\": \"billing_name\",\n"
                + "          \"card_brand\": \"card_brand\",\n"
                + "          \"card_exp_month\": 1,\n"
                + "          \"card_exp_year\": 1,\n"
                + "          \"card_last4\": \"card_last4\",\n"
                + "          \"company_id\": \"company_id\",\n"
                + "          \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "          \"customer_external_id\": \"customer_external_id\",\n"
                + "          \"environment_id\": \"environment_id\",\n"
                + "          \"external_id\": \"external_id\",\n"
                + "          \"id\": \"id\",\n"
                + "          \"payment_method_type\": \"payment_method_type\",\n"
                + "          \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "        },\n"
                + "        \"period_end\": 1,\n"
                + "        \"period_start\": 1,\n"
                + "        \"products\": [\n"
                + "          {\n"
                + "            \"billing_scheme\": \"billing_scheme\",\n"
                + "            \"billing_threshold\": 1,\n"
                + "            \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"currency\": \"currency\",\n"
                + "            \"environment_id\": \"environment_id\",\n"
                + "            \"external_id\": \"external_id\",\n"
                + "            \"id\": \"id\",\n"
                + "            \"interval\": \"interval\",\n"
                + "            \"meter_id\": \"meter_id\",\n"
                + "            \"name\": \"name\",\n"
                + "            \"package_size\": 1,\n"
                + "            \"price\": 1,\n"
                + "            \"price_decimal\": \"price_decimal\",\n"
                + "            \"price_external_id\": \"price_external_id\",\n"
                + "            \"price_id\": \"price_id\",\n"
                + "            \"price_tier\": [\n"
                + "              {\n"
                + "                \"flat_amount\": 1,\n"
                + "                \"per_unit_price\": 1,\n"
                + "                \"per_unit_price_decimal\": \"per_unit_price_decimal\",\n"
                + "                \"up_to\": 1\n"
                + "              },\n"
                + "              {\n"
                + "                \"flat_amount\": 1,\n"
                + "                \"per_unit_price\": 1,\n"
                + "                \"per_unit_price_decimal\": \"per_unit_price_decimal\",\n"
                + "                \"up_to\": 1\n"
                + "              }\n"
                + "            ],\n"
                + "            \"quantity\": 1.1,\n"
                + "            \"subscription_id\": \"subscription_id\",\n"
                + "            \"subscription_item_external_id\": \"subscription_item_external_id\",\n"
                + "            \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"usage_type\": \"usage_type\"\n"
                + "          },\n"
                + "          {\n"
                + "            \"billing_scheme\": \"billing_scheme\",\n"
                + "            \"billing_threshold\": 1,\n"
                + "            \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"currency\": \"currency\",\n"
                + "            \"environment_id\": \"environment_id\",\n"
                + "            \"external_id\": \"external_id\",\n"
                + "            \"id\": \"id\",\n"
                + "            \"interval\": \"interval\",\n"
                + "            \"meter_id\": \"meter_id\",\n"
                + "            \"name\": \"name\",\n"
                + "            \"package_size\": 1,\n"
                + "            \"price\": 1,\n"
                + "            \"price_decimal\": \"price_decimal\",\n"
                + "            \"price_external_id\": \"price_external_id\",\n"
                + "            \"price_id\": \"price_id\",\n"
                + "            \"price_tier\": [\n"
                + "              {\n"
                + "                \"flat_amount\": 1,\n"
                + "                \"per_unit_price\": 1,\n"
                + "                \"per_unit_price_decimal\": \"per_unit_price_decimal\",\n"
                + "                \"up_to\": 1\n"
                + "              },\n"
                + "              {\n"
                + "                \"flat_amount\": 1,\n"
                + "                \"per_unit_price\": 1,\n"
                + "                \"per_unit_price_decimal\": \"per_unit_price_decimal\",\n"
                + "                \"up_to\": 1\n"
                + "              }\n"
                + "            ],\n"
                + "            \"quantity\": 1.1,\n"
                + "            \"subscription_id\": \"subscription_id\",\n"
                + "            \"subscription_item_external_id\": \"subscription_item_external_id\",\n"
                + "            \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"usage_type\": \"usage_type\"\n"
                + "          }\n"
                + "        ],\n"
                + "        \"status\": \"status\",\n"
                + "        \"subscription_external_id\": \"subscription_external_id\",\n"
                + "        \"total_price\": 1,\n"
                + "        \"trial_end\": 1,\n"
                + "        \"trial_end_setting\": \"trial_end_setting\"\n"
                + "      },\n"
                + "      {\n"
                + "        \"cancel_at\": 1,\n"
                + "        \"cancel_at_period_end\": true,\n"
                + "        \"company_id\": \"company_id\",\n"
                + "        \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"currency\": \"currency\",\n"
                + "        \"customer_external_id\": \"customer_external_id\",\n"
                + "        \"default_payment_method_id\": \"default_payment_method_id\",\n"
                + "        \"discounts\": [\n"
                + "          {\n"
                + "            \"amount_off\": 1,\n"
                + "            \"coupon_id\": \"coupon_id\",\n"
                + "            \"coupon_name\": \"coupon_name\",\n"
                + "            \"currency\": \"currency\",\n"
                + "            \"customer_facing_code\": \"customer_facing_code\",\n"
                + "            \"discount_external_id\": \"discount_external_id\",\n"
                + "            \"duration\": \"duration\",\n"
                + "            \"duration_in_months\": 1,\n"
                + "            \"ended_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"is_active\": true,\n"
                + "            \"percent_off\": 1.1,\n"
                + "            \"promo_code_external_id\": \"promo_code_external_id\",\n"
                + "            \"started_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"subscription_external_id\": \"subscription_external_id\"\n"
                + "          },\n"
                + "          {\n"
                + "            \"amount_off\": 1,\n"
                + "            \"coupon_id\": \"coupon_id\",\n"
                + "            \"coupon_name\": \"coupon_name\",\n"
                + "            \"currency\": \"currency\",\n"
                + "            \"customer_facing_code\": \"customer_facing_code\",\n"
                + "            \"discount_external_id\": \"discount_external_id\",\n"
                + "            \"duration\": \"duration\",\n"
                + "            \"duration_in_months\": 1,\n"
                + "            \"ended_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"is_active\": true,\n"
                + "            \"percent_off\": 1.1,\n"
                + "            \"promo_code_external_id\": \"promo_code_external_id\",\n"
                + "            \"started_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"subscription_external_id\": \"subscription_external_id\"\n"
                + "          }\n"
                + "        ],\n"
                + "        \"expired_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"id\": \"id\",\n"
                + "        \"interval\": \"interval\",\n"
                + "        \"latest_invoice\": {\n"
                + "          \"amount_due\": 1,\n"
                + "          \"amount_paid\": 1,\n"
                + "          \"amount_remaining\": 1,\n"
                + "          \"collection_method\": \"collection_method\",\n"
                + "          \"company_id\": \"company_id\",\n"
                + "          \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "          \"currency\": \"currency\",\n"
                + "          \"customer_external_id\": \"customer_external_id\",\n"
                + "          \"due_date\": \"2024-01-15T09:30:00Z\",\n"
                + "          \"environment_id\": \"environment_id\",\n"
                + "          \"external_id\": \"external_id\",\n"
                + "          \"id\": \"id\",\n"
                + "          \"payment_method_external_id\": \"payment_method_external_id\",\n"
                + "          \"subscription_external_id\": \"subscription_external_id\",\n"
                + "          \"subtotal\": 1,\n"
                + "          \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "          \"url\": \"url\"\n"
                + "        },\n"
                + "        \"metadata\": {\n"
                + "          \"metadata\": {\n"
                + "            \"key\": \"value\"\n"
                + "          }\n"
                + "        },\n"
                + "        \"payment_method\": {\n"
                + "          \"account_last4\": \"account_last4\",\n"
                + "          \"account_name\": \"account_name\",\n"
                + "          \"bank_name\": \"bank_name\",\n"
                + "          \"billing_email\": \"billing_email\",\n"
                + "          \"billing_name\": \"billing_name\",\n"
                + "          \"card_brand\": \"card_brand\",\n"
                + "          \"card_exp_month\": 1,\n"
                + "          \"card_exp_year\": 1,\n"
                + "          \"card_last4\": \"card_last4\",\n"
                + "          \"company_id\": \"company_id\",\n"
                + "          \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "          \"customer_external_id\": \"customer_external_id\",\n"
                + "          \"environment_id\": \"environment_id\",\n"
                + "          \"external_id\": \"external_id\",\n"
                + "          \"id\": \"id\",\n"
                + "          \"payment_method_type\": \"payment_method_type\",\n"
                + "          \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "        },\n"
                + "        \"period_end\": 1,\n"
                + "        \"period_start\": 1,\n"
                + "        \"products\": [\n"
                + "          {\n"
                + "            \"billing_scheme\": \"billing_scheme\",\n"
                + "            \"billing_threshold\": 1,\n"
                + "            \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"currency\": \"currency\",\n"
                + "            \"environment_id\": \"environment_id\",\n"
                + "            \"external_id\": \"external_id\",\n"
                + "            \"id\": \"id\",\n"
                + "            \"interval\": \"interval\",\n"
                + "            \"meter_id\": \"meter_id\",\n"
                + "            \"name\": \"name\",\n"
                + "            \"package_size\": 1,\n"
                + "            \"price\": 1,\n"
                + "            \"price_decimal\": \"price_decimal\",\n"
                + "            \"price_external_id\": \"price_external_id\",\n"
                + "            \"price_id\": \"price_id\",\n"
                + "            \"price_tier\": [\n"
                + "              {\n"
                + "                \"flat_amount\": 1,\n"
                + "                \"per_unit_price\": 1,\n"
                + "                \"per_unit_price_decimal\": \"per_unit_price_decimal\",\n"
                + "                \"up_to\": 1\n"
                + "              },\n"
                + "              {\n"
                + "                \"flat_amount\": 1,\n"
                + "                \"per_unit_price\": 1,\n"
                + "                \"per_unit_price_decimal\": \"per_unit_price_decimal\",\n"
                + "                \"up_to\": 1\n"
                + "              }\n"
                + "            ],\n"
                + "            \"quantity\": 1.1,\n"
                + "            \"subscription_id\": \"subscription_id\",\n"
                + "            \"subscription_item_external_id\": \"subscription_item_external_id\",\n"
                + "            \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"usage_type\": \"usage_type\"\n"
                + "          },\n"
                + "          {\n"
                + "            \"billing_scheme\": \"billing_scheme\",\n"
                + "            \"billing_threshold\": 1,\n"
                + "            \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"currency\": \"currency\",\n"
                + "            \"environment_id\": \"environment_id\",\n"
                + "            \"external_id\": \"external_id\",\n"
                + "            \"id\": \"id\",\n"
                + "            \"interval\": \"interval\",\n"
                + "            \"meter_id\": \"meter_id\",\n"
                + "            \"name\": \"name\",\n"
                + "            \"package_size\": 1,\n"
                + "            \"price\": 1,\n"
                + "            \"price_decimal\": \"price_decimal\",\n"
                + "            \"price_external_id\": \"price_external_id\",\n"
                + "            \"price_id\": \"price_id\",\n"
                + "            \"price_tier\": [\n"
                + "              {\n"
                + "                \"flat_amount\": 1,\n"
                + "                \"per_unit_price\": 1,\n"
                + "                \"per_unit_price_decimal\": \"per_unit_price_decimal\",\n"
                + "                \"up_to\": 1\n"
                + "              },\n"
                + "              {\n"
                + "                \"flat_amount\": 1,\n"
                + "                \"per_unit_price\": 1,\n"
                + "                \"per_unit_price_decimal\": \"per_unit_price_decimal\",\n"
                + "                \"up_to\": 1\n"
                + "              }\n"
                + "            ],\n"
                + "            \"quantity\": 1.1,\n"
                + "            \"subscription_id\": \"subscription_id\",\n"
                + "            \"subscription_item_external_id\": \"subscription_item_external_id\",\n"
                + "            \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"usage_type\": \"usage_type\"\n"
                + "          }\n"
                + "        ],\n"
                + "        \"status\": \"status\",\n"
                + "        \"subscription_external_id\": \"subscription_external_id\",\n"
                + "        \"total_price\": 1,\n"
                + "        \"trial_end\": 1,\n"
                + "        \"trial_end_setting\": \"trial_end_setting\"\n"
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
                + "        \"definition\": {\n"
                + "          \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "          \"display_name\": \"display_name\",\n"
                + "          \"entity_type\": \"entity_type\",\n"
                + "          \"hierarchy\": [\n"
                + "            \"hierarchy\",\n"
                + "            \"hierarchy\"\n"
                + "          ],\n"
                + "          \"id\": \"id\",\n"
                + "          \"trait_type\": \"trait_type\",\n"
                + "          \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "        },\n"
                + "        \"definition_id\": \"definition_id\",\n"
                + "        \"environment_id\": \"environment_id\",\n"
                + "        \"id\": \"id\",\n"
                + "        \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"value\": \"value\"\n"
                + "      },\n"
                + "      {\n"
                + "        \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"definition\": {\n"
                + "          \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "          \"display_name\": \"display_name\",\n"
                + "          \"entity_type\": \"entity_type\",\n"
                + "          \"hierarchy\": [\n"
                + "            \"hierarchy\",\n"
                + "            \"hierarchy\"\n"
                + "          ],\n"
                + "          \"id\": \"id\",\n"
                + "          \"trait_type\": \"trait_type\",\n"
                + "          \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "        },\n"
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
                + "        \"definition\": {\n"
                + "          \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "          \"entity_type\": \"entity_type\",\n"
                + "          \"id\": \"id\",\n"
                + "          \"key\": \"key\",\n"
                + "          \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "        },\n"
                + "        \"definition_id\": \"definition_id\",\n"
                + "        \"entity_id\": \"entity_id\",\n"
                + "        \"entity_type\": \"entity_type\",\n"
                + "        \"environment_id\": \"environment_id\",\n"
                + "        \"id\": \"id\",\n"
                + "        \"key\": \"key\",\n"
                + "        \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"value\": \"value\"\n"
                + "      },\n"
                + "      {\n"
                + "        \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"definition\": {\n"
                + "          \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "          \"entity_type\": \"entity_type\",\n"
                + "          \"id\": \"id\",\n"
                + "          \"key\": \"key\",\n"
                + "          \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "        },\n"
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
                + "        \"valid_until\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"value\": 1\n"
                + "      },\n"
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
                + "        \"valid_until\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"value\": 1\n"
                + "      }\n"
                + "    ],\n"
                + "    \"name\": \"name\",\n"
                + "    \"payment_methods\": [\n"
                + "      {\n"
                + "        \"account_last4\": \"account_last4\",\n"
                + "        \"account_name\": \"account_name\",\n"
                + "        \"bank_name\": \"bank_name\",\n"
                + "        \"billing_email\": \"billing_email\",\n"
                + "        \"billing_name\": \"billing_name\",\n"
                + "        \"card_brand\": \"card_brand\",\n"
                + "        \"card_exp_month\": 1,\n"
                + "        \"card_exp_year\": 1,\n"
                + "        \"card_last4\": \"card_last4\",\n"
                + "        \"company_id\": \"company_id\",\n"
                + "        \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"customer_external_id\": \"customer_external_id\",\n"
                + "        \"environment_id\": \"environment_id\",\n"
                + "        \"external_id\": \"external_id\",\n"
                + "        \"id\": \"id\",\n"
                + "        \"payment_method_type\": \"payment_method_type\",\n"
                + "        \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "      },\n"
                + "      {\n"
                + "        \"account_last4\": \"account_last4\",\n"
                + "        \"account_name\": \"account_name\",\n"
                + "        \"bank_name\": \"bank_name\",\n"
                + "        \"billing_email\": \"billing_email\",\n"
                + "        \"billing_name\": \"billing_name\",\n"
                + "        \"card_brand\": \"card_brand\",\n"
                + "        \"card_exp_month\": 1,\n"
                + "        \"card_exp_year\": 1,\n"
                + "        \"card_last4\": \"card_last4\",\n"
                + "        \"company_id\": \"company_id\",\n"
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
                + "        \"description\": \"description\",\n"
                + "        \"id\": \"id\",\n"
                + "        \"image_url\": \"image_url\",\n"
                + "        \"name\": \"name\"\n"
                + "      },\n"
                + "      {\n"
                + "        \"description\": \"description\",\n"
                + "        \"id\": \"id\",\n"
                + "        \"image_url\": \"image_url\",\n"
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
                + "                \"comparison_trait_definition\": {\n"
                + "                  \"comparable_type\": \"comparable_type\",\n"
                + "                  \"entity_type\": \"entity_type\",\n"
                + "                  \"id\": \"id\"\n"
                + "                },\n"
                + "                \"condition_type\": \"condition_type\",\n"
                + "                \"consumption_rate\": 1.1,\n"
                + "                \"credit_id\": \"credit_id\",\n"
                + "                \"environment_id\": \"environment_id\",\n"
                + "                \"event_subtype\": \"event_subtype\",\n"
                + "                \"id\": \"id\",\n"
                + "                \"metric_period\": \"metric_period\",\n"
                + "                \"metric_period_month_reset\": \"metric_period_month_reset\",\n"
                + "                \"metric_value\": 1,\n"
                + "                \"operator\": \"operator\",\n"
                + "                \"resource_ids\": [\n"
                + "                  \"resource_ids\",\n"
                + "                  \"resource_ids\"\n"
                + "                ],\n"
                + "                \"trait_definition\": {\n"
                + "                  \"comparable_type\": \"comparable_type\",\n"
                + "                  \"entity_type\": \"entity_type\",\n"
                + "                  \"id\": \"id\"\n"
                + "                },\n"
                + "                \"trait_value\": \"trait_value\"\n"
                + "              },\n"
                + "              {\n"
                + "                \"account_id\": \"account_id\",\n"
                + "                \"comparison_trait_definition\": {\n"
                + "                  \"comparable_type\": \"comparable_type\",\n"
                + "                  \"entity_type\": \"entity_type\",\n"
                + "                  \"id\": \"id\"\n"
                + "                },\n"
                + "                \"condition_type\": \"condition_type\",\n"
                + "                \"consumption_rate\": 1.1,\n"
                + "                \"credit_id\": \"credit_id\",\n"
                + "                \"environment_id\": \"environment_id\",\n"
                + "                \"event_subtype\": \"event_subtype\",\n"
                + "                \"id\": \"id\",\n"
                + "                \"metric_period\": \"metric_period\",\n"
                + "                \"metric_period_month_reset\": \"metric_period_month_reset\",\n"
                + "                \"metric_value\": 1,\n"
                + "                \"operator\": \"operator\",\n"
                + "                \"resource_ids\": [\n"
                + "                  \"resource_ids\",\n"
                + "                  \"resource_ids\"\n"
                + "                ],\n"
                + "                \"trait_definition\": {\n"
                + "                  \"comparable_type\": \"comparable_type\",\n"
                + "                  \"entity_type\": \"entity_type\",\n"
                + "                  \"id\": \"id\"\n"
                + "                },\n"
                + "                \"trait_value\": \"trait_value\"\n"
                + "              }\n"
                + "            ]\n"
                + "          },\n"
                + "          {\n"
                + "            \"conditions\": [\n"
                + "              {\n"
                + "                \"account_id\": \"account_id\",\n"
                + "                \"comparison_trait_definition\": {\n"
                + "                  \"comparable_type\": \"comparable_type\",\n"
                + "                  \"entity_type\": \"entity_type\",\n"
                + "                  \"id\": \"id\"\n"
                + "                },\n"
                + "                \"condition_type\": \"condition_type\",\n"
                + "                \"consumption_rate\": 1.1,\n"
                + "                \"credit_id\": \"credit_id\",\n"
                + "                \"environment_id\": \"environment_id\",\n"
                + "                \"event_subtype\": \"event_subtype\",\n"
                + "                \"id\": \"id\",\n"
                + "                \"metric_period\": \"metric_period\",\n"
                + "                \"metric_period_month_reset\": \"metric_period_month_reset\",\n"
                + "                \"metric_value\": 1,\n"
                + "                \"operator\": \"operator\",\n"
                + "                \"resource_ids\": [\n"
                + "                  \"resource_ids\",\n"
                + "                  \"resource_ids\"\n"
                + "                ],\n"
                + "                \"trait_definition\": {\n"
                + "                  \"comparable_type\": \"comparable_type\",\n"
                + "                  \"entity_type\": \"entity_type\",\n"
                + "                  \"id\": \"id\"\n"
                + "                },\n"
                + "                \"trait_value\": \"trait_value\"\n"
                + "              },\n"
                + "              {\n"
                + "                \"account_id\": \"account_id\",\n"
                + "                \"comparison_trait_definition\": {\n"
                + "                  \"comparable_type\": \"comparable_type\",\n"
                + "                  \"entity_type\": \"entity_type\",\n"
                + "                  \"id\": \"id\"\n"
                + "                },\n"
                + "                \"condition_type\": \"condition_type\",\n"
                + "                \"consumption_rate\": 1.1,\n"
                + "                \"credit_id\": \"credit_id\",\n"
                + "                \"environment_id\": \"environment_id\",\n"
                + "                \"event_subtype\": \"event_subtype\",\n"
                + "                \"id\": \"id\",\n"
                + "                \"metric_period\": \"metric_period\",\n"
                + "                \"metric_period_month_reset\": \"metric_period_month_reset\",\n"
                + "                \"metric_value\": 1,\n"
                + "                \"operator\": \"operator\",\n"
                + "                \"resource_ids\": [\n"
                + "                  \"resource_ids\",\n"
                + "                  \"resource_ids\"\n"
                + "                ],\n"
                + "                \"trait_definition\": {\n"
                + "                  \"comparable_type\": \"comparable_type\",\n"
                + "                  \"entity_type\": \"entity_type\",\n"
                + "                  \"id\": \"id\"\n"
                + "                },\n"
                + "                \"trait_value\": \"trait_value\"\n"
                + "              }\n"
                + "            ]\n"
                + "          }\n"
                + "        ],\n"
                + "        \"conditions\": [\n"
                + "          {\n"
                + "            \"account_id\": \"account_id\",\n"
                + "            \"comparison_trait_definition\": {\n"
                + "              \"comparable_type\": \"comparable_type\",\n"
                + "              \"entity_type\": \"entity_type\",\n"
                + "              \"id\": \"id\"\n"
                + "            },\n"
                + "            \"condition_type\": \"condition_type\",\n"
                + "            \"consumption_rate\": 1.1,\n"
                + "            \"credit_id\": \"credit_id\",\n"
                + "            \"environment_id\": \"environment_id\",\n"
                + "            \"event_subtype\": \"event_subtype\",\n"
                + "            \"id\": \"id\",\n"
                + "            \"metric_period\": \"metric_period\",\n"
                + "            \"metric_period_month_reset\": \"metric_period_month_reset\",\n"
                + "            \"metric_value\": 1,\n"
                + "            \"operator\": \"operator\",\n"
                + "            \"resource_ids\": [\n"
                + "              \"resource_ids\",\n"
                + "              \"resource_ids\"\n"
                + "            ],\n"
                + "            \"trait_definition\": {\n"
                + "              \"comparable_type\": \"comparable_type\",\n"
                + "              \"entity_type\": \"entity_type\",\n"
                + "              \"id\": \"id\"\n"
                + "            },\n"
                + "            \"trait_value\": \"trait_value\"\n"
                + "          },\n"
                + "          {\n"
                + "            \"account_id\": \"account_id\",\n"
                + "            \"comparison_trait_definition\": {\n"
                + "              \"comparable_type\": \"comparable_type\",\n"
                + "              \"entity_type\": \"entity_type\",\n"
                + "              \"id\": \"id\"\n"
                + "            },\n"
                + "            \"condition_type\": \"condition_type\",\n"
                + "            \"consumption_rate\": 1.1,\n"
                + "            \"credit_id\": \"credit_id\",\n"
                + "            \"environment_id\": \"environment_id\",\n"
                + "            \"event_subtype\": \"event_subtype\",\n"
                + "            \"id\": \"id\",\n"
                + "            \"metric_period\": \"metric_period\",\n"
                + "            \"metric_period_month_reset\": \"metric_period_month_reset\",\n"
                + "            \"metric_value\": 1,\n"
                + "            \"operator\": \"operator\",\n"
                + "            \"resource_ids\": [\n"
                + "              \"resource_ids\",\n"
                + "              \"resource_ids\"\n"
                + "            ],\n"
                + "            \"trait_definition\": {\n"
                + "              \"comparable_type\": \"comparable_type\",\n"
                + "              \"entity_type\": \"entity_type\",\n"
                + "              \"id\": \"id\"\n"
                + "            },\n"
                + "            \"trait_value\": \"trait_value\"\n"
                + "          }\n"
                + "        ],\n"
                + "        \"environment_id\": \"environment_id\",\n"
                + "        \"flag_id\": \"flag_id\",\n"
                + "        \"id\": \"id\",\n"
                + "        \"name\": \"name\",\n"
                + "        \"priority\": 1,\n"
                + "        \"rule_type\": \"rule_type\",\n"
                + "        \"value\": true\n"
                + "      },\n"
                + "      {\n"
                + "        \"account_id\": \"account_id\",\n"
                + "        \"condition_groups\": [\n"
                + "          {\n"
                + "            \"conditions\": [\n"
                + "              {\n"
                + "                \"account_id\": \"account_id\",\n"
                + "                \"comparison_trait_definition\": {\n"
                + "                  \"comparable_type\": \"comparable_type\",\n"
                + "                  \"entity_type\": \"entity_type\",\n"
                + "                  \"id\": \"id\"\n"
                + "                },\n"
                + "                \"condition_type\": \"condition_type\",\n"
                + "                \"consumption_rate\": 1.1,\n"
                + "                \"credit_id\": \"credit_id\",\n"
                + "                \"environment_id\": \"environment_id\",\n"
                + "                \"event_subtype\": \"event_subtype\",\n"
                + "                \"id\": \"id\",\n"
                + "                \"metric_period\": \"metric_period\",\n"
                + "                \"metric_period_month_reset\": \"metric_period_month_reset\",\n"
                + "                \"metric_value\": 1,\n"
                + "                \"operator\": \"operator\",\n"
                + "                \"resource_ids\": [\n"
                + "                  \"resource_ids\",\n"
                + "                  \"resource_ids\"\n"
                + "                ],\n"
                + "                \"trait_definition\": {\n"
                + "                  \"comparable_type\": \"comparable_type\",\n"
                + "                  \"entity_type\": \"entity_type\",\n"
                + "                  \"id\": \"id\"\n"
                + "                },\n"
                + "                \"trait_value\": \"trait_value\"\n"
                + "              },\n"
                + "              {\n"
                + "                \"account_id\": \"account_id\",\n"
                + "                \"comparison_trait_definition\": {\n"
                + "                  \"comparable_type\": \"comparable_type\",\n"
                + "                  \"entity_type\": \"entity_type\",\n"
                + "                  \"id\": \"id\"\n"
                + "                },\n"
                + "                \"condition_type\": \"condition_type\",\n"
                + "                \"consumption_rate\": 1.1,\n"
                + "                \"credit_id\": \"credit_id\",\n"
                + "                \"environment_id\": \"environment_id\",\n"
                + "                \"event_subtype\": \"event_subtype\",\n"
                + "                \"id\": \"id\",\n"
                + "                \"metric_period\": \"metric_period\",\n"
                + "                \"metric_period_month_reset\": \"metric_period_month_reset\",\n"
                + "                \"metric_value\": 1,\n"
                + "                \"operator\": \"operator\",\n"
                + "                \"resource_ids\": [\n"
                + "                  \"resource_ids\",\n"
                + "                  \"resource_ids\"\n"
                + "                ],\n"
                + "                \"trait_definition\": {\n"
                + "                  \"comparable_type\": \"comparable_type\",\n"
                + "                  \"entity_type\": \"entity_type\",\n"
                + "                  \"id\": \"id\"\n"
                + "                },\n"
                + "                \"trait_value\": \"trait_value\"\n"
                + "              }\n"
                + "            ]\n"
                + "          },\n"
                + "          {\n"
                + "            \"conditions\": [\n"
                + "              {\n"
                + "                \"account_id\": \"account_id\",\n"
                + "                \"comparison_trait_definition\": {\n"
                + "                  \"comparable_type\": \"comparable_type\",\n"
                + "                  \"entity_type\": \"entity_type\",\n"
                + "                  \"id\": \"id\"\n"
                + "                },\n"
                + "                \"condition_type\": \"condition_type\",\n"
                + "                \"consumption_rate\": 1.1,\n"
                + "                \"credit_id\": \"credit_id\",\n"
                + "                \"environment_id\": \"environment_id\",\n"
                + "                \"event_subtype\": \"event_subtype\",\n"
                + "                \"id\": \"id\",\n"
                + "                \"metric_period\": \"metric_period\",\n"
                + "                \"metric_period_month_reset\": \"metric_period_month_reset\",\n"
                + "                \"metric_value\": 1,\n"
                + "                \"operator\": \"operator\",\n"
                + "                \"resource_ids\": [\n"
                + "                  \"resource_ids\",\n"
                + "                  \"resource_ids\"\n"
                + "                ],\n"
                + "                \"trait_definition\": {\n"
                + "                  \"comparable_type\": \"comparable_type\",\n"
                + "                  \"entity_type\": \"entity_type\",\n"
                + "                  \"id\": \"id\"\n"
                + "                },\n"
                + "                \"trait_value\": \"trait_value\"\n"
                + "              },\n"
                + "              {\n"
                + "                \"account_id\": \"account_id\",\n"
                + "                \"comparison_trait_definition\": {\n"
                + "                  \"comparable_type\": \"comparable_type\",\n"
                + "                  \"entity_type\": \"entity_type\",\n"
                + "                  \"id\": \"id\"\n"
                + "                },\n"
                + "                \"condition_type\": \"condition_type\",\n"
                + "                \"consumption_rate\": 1.1,\n"
                + "                \"credit_id\": \"credit_id\",\n"
                + "                \"environment_id\": \"environment_id\",\n"
                + "                \"event_subtype\": \"event_subtype\",\n"
                + "                \"id\": \"id\",\n"
                + "                \"metric_period\": \"metric_period\",\n"
                + "                \"metric_period_month_reset\": \"metric_period_month_reset\",\n"
                + "                \"metric_value\": 1,\n"
                + "                \"operator\": \"operator\",\n"
                + "                \"resource_ids\": [\n"
                + "                  \"resource_ids\",\n"
                + "                  \"resource_ids\"\n"
                + "                ],\n"
                + "                \"trait_definition\": {\n"
                + "                  \"comparable_type\": \"comparable_type\",\n"
                + "                  \"entity_type\": \"entity_type\",\n"
                + "                  \"id\": \"id\"\n"
                + "                },\n"
                + "                \"trait_value\": \"trait_value\"\n"
                + "              }\n"
                + "            ]\n"
                + "          }\n"
                + "        ],\n"
                + "        \"conditions\": [\n"
                + "          {\n"
                + "            \"account_id\": \"account_id\",\n"
                + "            \"comparison_trait_definition\": {\n"
                + "              \"comparable_type\": \"comparable_type\",\n"
                + "              \"entity_type\": \"entity_type\",\n"
                + "              \"id\": \"id\"\n"
                + "            },\n"
                + "            \"condition_type\": \"condition_type\",\n"
                + "            \"consumption_rate\": 1.1,\n"
                + "            \"credit_id\": \"credit_id\",\n"
                + "            \"environment_id\": \"environment_id\",\n"
                + "            \"event_subtype\": \"event_subtype\",\n"
                + "            \"id\": \"id\",\n"
                + "            \"metric_period\": \"metric_period\",\n"
                + "            \"metric_period_month_reset\": \"metric_period_month_reset\",\n"
                + "            \"metric_value\": 1,\n"
                + "            \"operator\": \"operator\",\n"
                + "            \"resource_ids\": [\n"
                + "              \"resource_ids\",\n"
                + "              \"resource_ids\"\n"
                + "            ],\n"
                + "            \"trait_definition\": {\n"
                + "              \"comparable_type\": \"comparable_type\",\n"
                + "              \"entity_type\": \"entity_type\",\n"
                + "              \"id\": \"id\"\n"
                + "            },\n"
                + "            \"trait_value\": \"trait_value\"\n"
                + "          },\n"
                + "          {\n"
                + "            \"account_id\": \"account_id\",\n"
                + "            \"comparison_trait_definition\": {\n"
                + "              \"comparable_type\": \"comparable_type\",\n"
                + "              \"entity_type\": \"entity_type\",\n"
                + "              \"id\": \"id\"\n"
                + "            },\n"
                + "            \"condition_type\": \"condition_type\",\n"
                + "            \"consumption_rate\": 1.1,\n"
                + "            \"credit_id\": \"credit_id\",\n"
                + "            \"environment_id\": \"environment_id\",\n"
                + "            \"event_subtype\": \"event_subtype\",\n"
                + "            \"id\": \"id\",\n"
                + "            \"metric_period\": \"metric_period\",\n"
                + "            \"metric_period_month_reset\": \"metric_period_month_reset\",\n"
                + "            \"metric_value\": 1,\n"
                + "            \"operator\": \"operator\",\n"
                + "            \"resource_ids\": [\n"
                + "              \"resource_ids\",\n"
                + "              \"resource_ids\"\n"
                + "            ],\n"
                + "            \"trait_definition\": {\n"
                + "              \"comparable_type\": \"comparable_type\",\n"
                + "              \"entity_type\": \"entity_type\",\n"
                + "              \"id\": \"id\"\n"
                + "            },\n"
                + "            \"trait_value\": \"trait_value\"\n"
                + "          }\n"
                + "        ],\n"
                + "        \"environment_id\": \"environment_id\",\n"
                + "        \"flag_id\": \"flag_id\",\n"
                + "        \"id\": \"id\",\n"
                + "        \"name\": \"name\",\n"
                + "        \"priority\": 1,\n"
                + "        \"rule_type\": \"rule_type\",\n"
                + "        \"value\": true\n"
                + "      }\n"
                + "    ],\n"
                + "    \"traits\": {\n"
                + "      \"traits\": {\n"
                + "        \"key\": \"value\"\n"
                + "      }\n"
                + "    },\n"
                + "    \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"user_count\": 1\n"
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

    @Test
    public void testGetActiveDeals() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":[{\"deal_arr\":\"deal_arr\",\"deal_external_id\":\"deal_external_id\",\"deal_mrr\":\"deal_mrr\",\"deal_name\":\"deal_name\",\"line_items\":[{\"billing_frequency\":\"billing_frequency\",\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"description\":\"description\",\"id\":\"id\",\"name\":\"name\",\"price\":1.1,\"quantity\":1,\"updated_at\":\"2024-01-15T09:30:00Z\"}]}],\"params\":{\"company_id\":\"company_id\",\"deal_stage\":\"deal_stage\",\"limit\":1,\"offset\":1}}"));
        GetActiveDealsResponse response = client.companies()
                .getActiveDeals(GetActiveDealsRequest.builder()
                        .companyId("company_id")
                        .dealStage("deal_stage")
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
                + "      \"deal_arr\": \"deal_arr\",\n"
                + "      \"deal_external_id\": \"deal_external_id\",\n"
                + "      \"deal_mrr\": \"deal_mrr\",\n"
                + "      \"deal_name\": \"deal_name\",\n"
                + "      \"line_items\": [\n"
                + "        {\n"
                + "          \"billing_frequency\": \"billing_frequency\",\n"
                + "          \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "          \"currency\": \"currency\",\n"
                + "          \"description\": \"description\",\n"
                + "          \"id\": \"id\",\n"
                + "          \"name\": \"name\",\n"
                + "          \"price\": 1.1,\n"
                + "          \"quantity\": 1,\n"
                + "          \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "        }\n"
                + "      ]\n"
                + "    }\n"
                + "  ],\n"
                + "  \"params\": {\n"
                + "    \"company_id\": \"company_id\",\n"
                + "    \"deal_stage\": \"deal_stage\",\n"
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
    public void testListCompanyMemberships() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":[{\"company\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"name\":\"name\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"company_id\":\"company_id\",\"created_at\":\"2024-01-15T09:30:00Z\",\"id\":\"id\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"user_id\":\"user_id\"}],\"params\":{\"company_id\":\"company_id\",\"limit\":1,\"offset\":1,\"user_id\":\"user_id\"}}"));
        ListCompanyMembershipsResponse response = client.companies()
                .listCompanyMemberships(ListCompanyMembershipsRequest.builder()
                        .companyId("company_id")
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
                + "      \"company\": {\n"
                + "        \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"environment_id\": \"environment_id\",\n"
                + "        \"id\": \"id\",\n"
                + "        \"name\": \"name\",\n"
                + "        \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "      },\n"
                + "      \"company_id\": \"company_id\",\n"
                + "      \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"id\": \"id\",\n"
                + "      \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"user_id\": \"user_id\"\n"
                + "    }\n"
                + "  ],\n"
                + "  \"params\": {\n"
                + "    \"company_id\": \"company_id\",\n"
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
    public void testGetOrCreateCompanyMembership() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":{\"company\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"last_seen_at\":\"2024-01-15T09:30:00Z\",\"logo_url\":\"logo_url\",\"name\":\"name\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"company_id\":\"company_id\",\"created_at\":\"2024-01-15T09:30:00Z\",\"id\":\"id\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"user_id\":\"user_id\"},\"params\":{\"key\":\"value\"}}"));
        GetOrCreateCompanyMembershipResponse response = client.companies()
                .getOrCreateCompanyMembership(GetOrCreateCompanyMembershipRequestBody.builder()
                        .companyId("company_id")
                        .userId("user_id")
                        .build());
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("POST", request.getMethod());
        // Validate request body
        String actualRequestBody = request.getBody().readUtf8();
        String expectedRequestBody =
                "" + "{\n" + "  \"company_id\": \"company_id\",\n" + "  \"user_id\": \"user_id\"\n" + "}";
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
                + "      \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"environment_id\": \"environment_id\",\n"
                + "      \"id\": \"id\",\n"
                + "      \"last_seen_at\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"logo_url\": \"logo_url\",\n"
                + "      \"name\": \"name\",\n"
                + "      \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "    },\n"
                + "    \"company_id\": \"company_id\",\n"
                + "    \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"id\": \"id\",\n"
                + "    \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
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
    public void testDeleteCompanyMembership() throws Exception {
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody("{\"data\":{\"deleted\":true},\"params\":{\"key\":\"value\"}}"));
        DeleteCompanyMembershipResponse response = client.companies().deleteCompanyMembership("company_membership_id");
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
    public void testGetActiveCompanySubscription() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":[{\"cancel_at\":\"2024-01-15T09:30:00Z\",\"cancel_at_period_end\":true,\"currency\":\"currency\",\"customer_external_id\":\"customer_external_id\",\"discounts\":[{\"coupon_id\":\"coupon_id\",\"coupon_name\":\"coupon_name\",\"discount_external_id\":\"discount_external_id\",\"duration\":\"duration\",\"is_active\":true,\"started_at\":\"2024-01-15T09:30:00Z\",\"subscription_external_id\":\"subscription_external_id\"}],\"expired_at\":\"2024-01-15T09:30:00Z\",\"interval\":\"interval\",\"latest_invoice\":{\"amount_due\":1,\"amount_paid\":1,\"amount_remaining\":1,\"collection_method\":\"collection_method\",\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"customer_external_id\":\"customer_external_id\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"subtotal\":1,\"updated_at\":\"2024-01-15T09:30:00Z\"},\"payment_method\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"customer_external_id\":\"customer_external_id\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"id\":\"id\",\"payment_method_type\":\"payment_method_type\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"products\":[{\"billing_scheme\":\"billing_scheme\",\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"id\":\"id\",\"interval\":\"interval\",\"name\":\"name\",\"package_size\":1,\"price\":1,\"price_external_id\":\"price_external_id\",\"price_id\":\"price_id\",\"price_tier\":[{}],\"quantity\":1.1,\"subscription_id\":\"subscription_id\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"usage_type\":\"usage_type\"}],\"status\":\"status\",\"subscription_external_id\":\"subscription_external_id\",\"total_price\":1,\"trial_end\":\"2024-01-15T09:30:00Z\"}],\"params\":{\"company_id\":\"company_id\",\"company_ids\":[\"company_ids\"],\"limit\":1,\"offset\":1}}"));
        GetActiveCompanySubscriptionResponse response = client.companies()
                .getActiveCompanySubscription(GetActiveCompanySubscriptionRequest.builder()
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
                + "    }\n"
                + "  ],\n"
                + "  \"params\": {\n"
                + "    \"company_id\": \"company_id\",\n"
                + "    \"company_ids\": [\n"
                + "      \"company_ids\"\n"
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
    public void testUpsertCompanyTrait() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":{\"add_ons\":[{\"id\":\"id\",\"name\":\"name\"}],\"billing_credit_balances\":{\"key\":1.1},\"billing_subscription\":{\"cancel_at\":1,\"cancel_at_period_end\":true,\"company_id\":\"company_id\",\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"customer_external_id\":\"customer_external_id\",\"default_payment_method_id\":\"default_payment_method_id\",\"discounts\":[{\"coupon_id\":\"coupon_id\",\"coupon_name\":\"coupon_name\",\"discount_external_id\":\"discount_external_id\",\"duration\":\"duration\",\"is_active\":true,\"started_at\":\"2024-01-15T09:30:00Z\",\"subscription_external_id\":\"subscription_external_id\"}],\"expired_at\":\"2024-01-15T09:30:00Z\",\"id\":\"id\",\"interval\":\"interval\",\"latest_invoice\":{\"amount_due\":1,\"amount_paid\":1,\"amount_remaining\":1,\"collection_method\":\"collection_method\",\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"customer_external_id\":\"customer_external_id\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"subtotal\":1,\"updated_at\":\"2024-01-15T09:30:00Z\"},\"metadata\":{\"key\":\"value\"},\"payment_method\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"customer_external_id\":\"customer_external_id\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"id\":\"id\",\"payment_method_type\":\"payment_method_type\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"period_end\":1,\"period_start\":1,\"products\":[{\"billing_scheme\":\"billing_scheme\",\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"id\":\"id\",\"interval\":\"interval\",\"name\":\"name\",\"package_size\":1,\"price\":1,\"price_external_id\":\"price_external_id\",\"price_id\":\"price_id\",\"price_tier\":[{}],\"quantity\":1.1,\"subscription_id\":\"subscription_id\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"usage_type\":\"usage_type\"}],\"status\":\"status\",\"subscription_external_id\":\"subscription_external_id\",\"total_price\":1,\"trial_end\":1,\"trial_end_setting\":\"trial_end_setting\"},\"billing_subscriptions\":[{\"cancel_at_period_end\":true,\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"customer_external_id\":\"customer_external_id\",\"discounts\":[{\"coupon_id\":\"coupon_id\",\"coupon_name\":\"coupon_name\",\"discount_external_id\":\"discount_external_id\",\"duration\":\"duration\",\"is_active\":true,\"started_at\":\"2024-01-15T09:30:00Z\",\"subscription_external_id\":\"subscription_external_id\"}],\"id\":\"id\",\"interval\":\"interval\",\"period_end\":1,\"period_start\":1,\"products\":[{\"billing_scheme\":\"billing_scheme\",\"created_at\":\"2024-01-15T09:30:00Z\",\"currency\":\"currency\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"id\":\"id\",\"interval\":\"interval\",\"name\":\"name\",\"package_size\":1,\"price\":1,\"price_external_id\":\"price_external_id\",\"price_id\":\"price_id\",\"price_tier\":[{}],\"quantity\":1.1,\"subscription_id\":\"subscription_id\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"usage_type\":\"usage_type\"}],\"status\":\"status\",\"subscription_external_id\":\"subscription_external_id\",\"total_price\":1}],\"created_at\":\"2024-01-15T09:30:00Z\",\"default_payment_method\":{\"account_last4\":\"account_last4\",\"account_name\":\"account_name\",\"bank_name\":\"bank_name\",\"billing_email\":\"billing_email\",\"billing_name\":\"billing_name\",\"card_brand\":\"card_brand\",\"card_exp_month\":1,\"card_exp_year\":1,\"card_last4\":\"card_last4\",\"company_id\":\"company_id\",\"created_at\":\"2024-01-15T09:30:00Z\",\"customer_external_id\":\"customer_external_id\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"id\":\"id\",\"payment_method_type\":\"payment_method_type\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"entity_traits\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"definition_id\":\"definition_id\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"value\":\"value\"}],\"environment_id\":\"environment_id\",\"id\":\"id\",\"keys\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"definition_id\":\"definition_id\",\"entity_id\":\"entity_id\",\"entity_type\":\"entity_type\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"key\":\"key\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"value\":\"value\"}],\"last_seen_at\":\"2024-01-15T09:30:00Z\",\"logo_url\":\"logo_url\",\"metrics\":[{\"account_id\":\"account_id\",\"captured_at_max\":\"2024-01-15T09:30:00Z\",\"captured_at_min\":\"2024-01-15T09:30:00Z\",\"company_id\":\"company_id\",\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"event_subtype\":\"event_subtype\",\"month_reset\":\"month_reset\",\"period\":\"period\",\"value\":1}],\"name\":\"name\",\"payment_methods\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"customer_external_id\":\"customer_external_id\",\"environment_id\":\"environment_id\",\"external_id\":\"external_id\",\"id\":\"id\",\"payment_method_type\":\"payment_method_type\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"plan\":{\"added_on\":\"2024-01-15T09:30:00Z\",\"billing_product_external_id\":\"billing_product_external_id\",\"billing_product_id\":\"billing_product_id\",\"description\":\"description\",\"id\":\"id\",\"image_url\":\"image_url\",\"name\":\"name\",\"plan_period\":\"plan_period\",\"plan_price\":1},\"plans\":[{\"id\":\"id\",\"name\":\"name\"}],\"rules\":[{\"account_id\":\"account_id\",\"condition_groups\":[{\"conditions\":[{\"account_id\":\"account_id\",\"condition_type\":\"condition_type\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"operator\":\"operator\",\"resource_ids\":[\"resource_ids\"],\"trait_value\":\"trait_value\"}]}],\"conditions\":[{\"account_id\":\"account_id\",\"condition_type\":\"condition_type\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"operator\":\"operator\",\"resource_ids\":[\"resource_ids\"],\"trait_value\":\"trait_value\"}],\"environment_id\":\"environment_id\",\"id\":\"id\",\"name\":\"name\",\"priority\":1,\"rule_type\":\"rule_type\",\"value\":true}],\"traits\":{\"key\":\"value\"},\"updated_at\":\"2024-01-15T09:30:00Z\",\"user_count\":1},\"params\":{\"key\":\"value\"}}"));
        UpsertCompanyTraitResponse response = client.companies()
                .upsertCompanyTrait(UpsertTraitRequestBody.builder()
                        .keys(new HashMap<String, String>() {
                            {
                                put("key", "value");
                            }
                        })
                        .trait("trait")
                        .build());
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("POST", request.getMethod());
        // Validate request body
        String actualRequestBody = request.getBody().readUtf8();
        String expectedRequestBody = ""
                + "{\n"
                + "  \"keys\": {\n"
                + "    \"key\": \"value\"\n"
                + "  },\n"
                + "  \"trait\": \"trait\"\n"
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
    public void testListEntityKeyDefinitions() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"entity_type\":\"entity_type\",\"id\":\"id\",\"key\":\"key\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"params\":{\"entity_type\":\"company\",\"ids\":[\"ids\"],\"limit\":1,\"offset\":1,\"q\":\"q\"}}"));
        ListEntityKeyDefinitionsResponse response = client.companies()
                .listEntityKeyDefinitions(ListEntityKeyDefinitionsRequest.builder()
                        .entityType(ListEntityKeyDefinitionsRequestEntityType.COMPANY)
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
                + "      \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"entity_type\": \"entity_type\",\n"
                + "      \"id\": \"id\",\n"
                + "      \"key\": \"key\",\n"
                + "      \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "    }\n"
                + "  ],\n"
                + "  \"params\": {\n"
                + "    \"entity_type\": \"company\",\n"
                + "    \"ids\": [\n"
                + "      \"ids\"\n"
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
    public void testCountEntityKeyDefinitions() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":{\"count\":1},\"params\":{\"entity_type\":\"company\",\"ids\":[\"ids\"],\"limit\":1,\"offset\":1,\"q\":\"q\"}}"));
        CountEntityKeyDefinitionsResponse response = client.companies()
                .countEntityKeyDefinitions(CountEntityKeyDefinitionsRequest.builder()
                        .entityType(CountEntityKeyDefinitionsRequestEntityType.COMPANY)
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
                + "    \"entity_type\": \"company\",\n"
                + "    \"ids\": [\n"
                + "      \"ids\"\n"
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
    public void testListEntityTraitDefinitions() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"display_name\":\"display_name\",\"entity_type\":\"entity_type\",\"hierarchy\":[\"hierarchy\"],\"id\":\"id\",\"trait_type\":\"trait_type\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"params\":{\"entity_type\":\"company\",\"ids\":[\"ids\"],\"limit\":1,\"offset\":1,\"q\":\"q\",\"trait_type\":\"boolean\"}}"));
        ListEntityTraitDefinitionsResponse response = client.companies()
                .listEntityTraitDefinitions(ListEntityTraitDefinitionsRequest.builder()
                        .entityType(ListEntityTraitDefinitionsRequestEntityType.COMPANY)
                        .q("q")
                        .traitType(ListEntityTraitDefinitionsRequestTraitType.BOOLEAN)
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
                + "      \"display_name\": \"display_name\",\n"
                + "      \"entity_type\": \"entity_type\",\n"
                + "      \"hierarchy\": [\n"
                + "        \"hierarchy\"\n"
                + "      ],\n"
                + "      \"id\": \"id\",\n"
                + "      \"trait_type\": \"trait_type\",\n"
                + "      \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "    }\n"
                + "  ],\n"
                + "  \"params\": {\n"
                + "    \"entity_type\": \"company\",\n"
                + "    \"ids\": [\n"
                + "      \"ids\"\n"
                + "    ],\n"
                + "    \"limit\": 1,\n"
                + "    \"offset\": 1,\n"
                + "    \"q\": \"q\",\n"
                + "    \"trait_type\": \"boolean\"\n"
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
    public void testGetOrCreateEntityTraitDefinition() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"display_name\":\"display_name\",\"entity_type\":\"entity_type\",\"hierarchy\":[\"hierarchy\"],\"id\":\"id\",\"trait_type\":\"trait_type\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"params\":{\"key\":\"value\"}}"));
        GetOrCreateEntityTraitDefinitionResponse response = client.companies()
                .getOrCreateEntityTraitDefinition(CreateEntityTraitDefinitionRequestBody.builder()
                        .entityType(CreateEntityTraitDefinitionRequestBodyEntityType.COMPANY)
                        .hierarchy(Arrays.asList("hierarchy"))
                        .traitType(CreateEntityTraitDefinitionRequestBodyTraitType.BOOLEAN)
                        .build());
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("POST", request.getMethod());
        // Validate request body
        String actualRequestBody = request.getBody().readUtf8();
        String expectedRequestBody = ""
                + "{\n"
                + "  \"entity_type\": \"company\",\n"
                + "  \"hierarchy\": [\n"
                + "    \"hierarchy\"\n"
                + "  ],\n"
                + "  \"trait_type\": \"boolean\"\n"
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
                + "    \"display_name\": \"display_name\",\n"
                + "    \"entity_type\": \"entity_type\",\n"
                + "    \"hierarchy\": [\n"
                + "      \"hierarchy\"\n"
                + "    ],\n"
                + "    \"id\": \"id\",\n"
                + "    \"trait_type\": \"trait_type\",\n"
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
    public void testGetEntityTraitDefinition() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"display_name\":\"display_name\",\"entity_type\":\"entity_type\",\"hierarchy\":[\"hierarchy\"],\"id\":\"id\",\"trait_type\":\"trait_type\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"params\":{\"key\":\"value\"}}"));
        GetEntityTraitDefinitionResponse response =
                client.companies().getEntityTraitDefinition("entity_trait_definition_id");
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
                + "    \"display_name\": \"display_name\",\n"
                + "    \"entity_type\": \"entity_type\",\n"
                + "    \"hierarchy\": [\n"
                + "      \"hierarchy\"\n"
                + "    ],\n"
                + "    \"id\": \"id\",\n"
                + "    \"trait_type\": \"trait_type\",\n"
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
    public void testUpdateEntityTraitDefinition() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"display_name\":\"display_name\",\"entity_type\":\"entity_type\",\"hierarchy\":[\"hierarchy\"],\"id\":\"id\",\"trait_type\":\"trait_type\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"params\":{\"key\":\"value\"}}"));
        UpdateEntityTraitDefinitionResponse response = client.companies()
                .updateEntityTraitDefinition(
                        "entity_trait_definition_id",
                        UpdateEntityTraitDefinitionRequestBody.builder()
                                .traitType(UpdateEntityTraitDefinitionRequestBodyTraitType.BOOLEAN)
                                .build());
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("PUT", request.getMethod());
        // Validate request body
        String actualRequestBody = request.getBody().readUtf8();
        String expectedRequestBody = "" + "{\n" + "  \"trait_type\": \"boolean\"\n" + "}";
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
                + "    \"display_name\": \"display_name\",\n"
                + "    \"entity_type\": \"entity_type\",\n"
                + "    \"hierarchy\": [\n"
                + "      \"hierarchy\"\n"
                + "    ],\n"
                + "    \"id\": \"id\",\n"
                + "    \"trait_type\": \"trait_type\",\n"
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
    public void testCountEntityTraitDefinitions() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":{\"count\":1},\"params\":{\"entity_type\":\"company\",\"ids\":[\"ids\"],\"limit\":1,\"offset\":1,\"q\":\"q\",\"trait_type\":\"boolean\"}}"));
        CountEntityTraitDefinitionsResponse response = client.companies()
                .countEntityTraitDefinitions(CountEntityTraitDefinitionsRequest.builder()
                        .entityType(CountEntityTraitDefinitionsRequestEntityType.COMPANY)
                        .q("q")
                        .traitType(CountEntityTraitDefinitionsRequestTraitType.BOOLEAN)
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
                + "    \"entity_type\": \"company\",\n"
                + "    \"ids\": [\n"
                + "      \"ids\"\n"
                + "    ],\n"
                + "    \"limit\": 1,\n"
                + "    \"offset\": 1,\n"
                + "    \"q\": \"q\",\n"
                + "    \"trait_type\": \"boolean\"\n"
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
    public void testGetEntityTraitValues() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":[{\"definition_id\":\"definition_id\",\"value\":\"value\"}],\"params\":{\"definition_id\":\"definition_id\",\"limit\":1,\"offset\":1,\"q\":\"q\"}}"));
        GetEntityTraitValuesResponse response = client.companies()
                .getEntityTraitValues(GetEntityTraitValuesRequest.builder()
                        .definitionId("definition_id")
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
                + "      \"definition_id\": \"definition_id\",\n"
                + "      \"value\": \"value\"\n"
                + "    }\n"
                + "  ],\n"
                + "  \"params\": {\n"
                + "    \"definition_id\": \"definition_id\",\n"
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
    public void testListPlanChanges() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":[{\"action\":\"checkout\",\"actor_type\":\"app_user\",\"add_ons_added\":[{\"deleted\":true,\"description\":\"description\",\"icon\":\"icon\",\"id\":\"id\",\"name\":\"name\"}],\"add_ons_removed\":[{\"deleted\":true,\"description\":\"description\",\"icon\":\"icon\",\"id\":\"id\",\"name\":\"name\"}],\"api_key\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"id\":\"id\",\"name\":\"name\",\"scopes\":[\"scopes\"],\"updated_at\":\"2024-01-15T09:30:00Z\"},\"api_key_request\":{\"api_key_id\":\"api_key_id\",\"id\":\"id\",\"method\":\"method\",\"started_at\":\"2024-01-15T09:30:00Z\",\"url\":\"url\"},\"base_plan\":{\"deleted\":true,\"description\":\"description\",\"icon\":\"icon\",\"id\":\"id\",\"name\":\"name\"},\"base_plan_action\":\"fallback\",\"company\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"name\":\"name\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"company_id\":\"company_id\",\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"previous_base_plan\":{\"deleted\":true,\"description\":\"description\",\"icon\":\"icon\",\"id\":\"id\",\"name\":\"name\"},\"request_id\":\"request_id\",\"subscription_change_action\":\"downgrade\",\"traits_updated\":[{\"feature_id\":\"feature_id\",\"hierarchy\":[\"hierarchy\"],\"reason\":\"reason\",\"trait_id\":\"trait_id\",\"trait_name\":\"trait_name\",\"trait_type\":\"trait_type\",\"value\":\"value\"}],\"updated_at\":\"2024-01-15T09:30:00Z\",\"user_id\":\"user_id\",\"user_name\":\"user_name\"}],\"params\":{\"action\":\"action\",\"base_plan_action\":\"base_plan_action\",\"company_id\":\"company_id\",\"company_ids\":[\"company_ids\"],\"limit\":1,\"offset\":1,\"plan_ids\":[\"plan_ids\"]}}"));
        ListPlanChangesResponse response = client.companies()
                .listPlanChanges(ListPlanChangesRequest.builder()
                        .action("action")
                        .basePlanAction("base_plan_action")
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
                + "      \"action\": \"checkout\",\n"
                + "      \"actor_type\": \"app_user\",\n"
                + "      \"add_ons_added\": [\n"
                + "        {\n"
                + "          \"deleted\": true,\n"
                + "          \"description\": \"description\",\n"
                + "          \"icon\": \"icon\",\n"
                + "          \"id\": \"id\",\n"
                + "          \"name\": \"name\"\n"
                + "        }\n"
                + "      ],\n"
                + "      \"add_ons_removed\": [\n"
                + "        {\n"
                + "          \"deleted\": true,\n"
                + "          \"description\": \"description\",\n"
                + "          \"icon\": \"icon\",\n"
                + "          \"id\": \"id\",\n"
                + "          \"name\": \"name\"\n"
                + "        }\n"
                + "      ],\n"
                + "      \"api_key\": {\n"
                + "        \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"id\": \"id\",\n"
                + "        \"name\": \"name\",\n"
                + "        \"scopes\": [\n"
                + "          \"scopes\"\n"
                + "        ],\n"
                + "        \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "      },\n"
                + "      \"api_key_request\": {\n"
                + "        \"api_key_id\": \"api_key_id\",\n"
                + "        \"id\": \"id\",\n"
                + "        \"method\": \"method\",\n"
                + "        \"started_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"url\": \"url\"\n"
                + "      },\n"
                + "      \"base_plan\": {\n"
                + "        \"deleted\": true,\n"
                + "        \"description\": \"description\",\n"
                + "        \"icon\": \"icon\",\n"
                + "        \"id\": \"id\",\n"
                + "        \"name\": \"name\"\n"
                + "      },\n"
                + "      \"base_plan_action\": \"fallback\",\n"
                + "      \"company\": {\n"
                + "        \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"environment_id\": \"environment_id\",\n"
                + "        \"id\": \"id\",\n"
                + "        \"name\": \"name\",\n"
                + "        \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "      },\n"
                + "      \"company_id\": \"company_id\",\n"
                + "      \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"environment_id\": \"environment_id\",\n"
                + "      \"id\": \"id\",\n"
                + "      \"previous_base_plan\": {\n"
                + "        \"deleted\": true,\n"
                + "        \"description\": \"description\",\n"
                + "        \"icon\": \"icon\",\n"
                + "        \"id\": \"id\",\n"
                + "        \"name\": \"name\"\n"
                + "      },\n"
                + "      \"request_id\": \"request_id\",\n"
                + "      \"subscription_change_action\": \"downgrade\",\n"
                + "      \"traits_updated\": [\n"
                + "        {\n"
                + "          \"feature_id\": \"feature_id\",\n"
                + "          \"hierarchy\": [\n"
                + "            \"hierarchy\"\n"
                + "          ],\n"
                + "          \"reason\": \"reason\",\n"
                + "          \"trait_id\": \"trait_id\",\n"
                + "          \"trait_name\": \"trait_name\",\n"
                + "          \"trait_type\": \"trait_type\",\n"
                + "          \"value\": \"value\"\n"
                + "        }\n"
                + "      ],\n"
                + "      \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"user_id\": \"user_id\",\n"
                + "      \"user_name\": \"user_name\"\n"
                + "    }\n"
                + "  ],\n"
                + "  \"params\": {\n"
                + "    \"action\": \"action\",\n"
                + "    \"base_plan_action\": \"base_plan_action\",\n"
                + "    \"company_id\": \"company_id\",\n"
                + "    \"company_ids\": [\n"
                + "      \"company_ids\"\n"
                + "    ],\n"
                + "    \"limit\": 1,\n"
                + "    \"offset\": 1,\n"
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
    public void testGetPlanChange() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":{\"action\":\"checkout\",\"actor_type\":\"app_user\",\"add_ons_added\":[{\"deleted\":true,\"description\":\"description\",\"icon\":\"icon\",\"id\":\"id\",\"name\":\"name\"}],\"add_ons_removed\":[{\"deleted\":true,\"description\":\"description\",\"icon\":\"icon\",\"id\":\"id\",\"name\":\"name\"}],\"api_key\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"description\":\"description\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"last_used_at\":\"2024-01-15T09:30:00Z\",\"name\":\"name\",\"scopes\":[\"scopes\"],\"updated_at\":\"2024-01-15T09:30:00Z\"},\"api_key_request\":{\"api_key_id\":\"api_key_id\",\"ended_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"method\":\"method\",\"req_body\":\"req_body\",\"request_type\":\"request_type\",\"resource_id\":1,\"resource_id_string\":\"resource_id_string\",\"resource_name\":\"resource_name\",\"resource_type\":\"resource_type\",\"resp_code\":1,\"secondary_resource\":\"secondary_resource\",\"started_at\":\"2024-01-15T09:30:00Z\",\"url\":\"url\",\"user_name\":\"user_name\"},\"base_plan\":{\"deleted\":true,\"description\":\"description\",\"icon\":\"icon\",\"id\":\"id\",\"name\":\"name\"},\"base_plan_action\":\"fallback\",\"company\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"last_seen_at\":\"2024-01-15T09:30:00Z\",\"logo_url\":\"logo_url\",\"name\":\"name\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"company_id\":\"company_id\",\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"previous_base_plan\":{\"deleted\":true,\"description\":\"description\",\"icon\":\"icon\",\"id\":\"id\",\"name\":\"name\"},\"request_id\":\"request_id\",\"subscription_change_action\":\"downgrade\",\"traits_updated\":[{\"feature_id\":\"feature_id\",\"hierarchy\":[\"hierarchy\"],\"reason\":\"reason\",\"trait_id\":\"trait_id\",\"trait_name\":\"trait_name\",\"trait_type\":\"trait_type\",\"value\":\"value\"}],\"updated_at\":\"2024-01-15T09:30:00Z\",\"user_id\":\"user_id\",\"user_name\":\"user_name\"},\"params\":{\"key\":\"value\"}}"));
        GetPlanChangeResponse response = client.companies().getPlanChange("plan_change_id");
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("GET", request.getMethod());

        // Validate response body
        Assertions.assertNotNull(response, "Response should not be null");
        String actualResponseJson = objectMapper.writeValueAsString(response);
        String expectedResponseBody = ""
                + "{\n"
                + "  \"data\": {\n"
                + "    \"action\": \"checkout\",\n"
                + "    \"actor_type\": \"app_user\",\n"
                + "    \"add_ons_added\": [\n"
                + "      {\n"
                + "        \"deleted\": true,\n"
                + "        \"description\": \"description\",\n"
                + "        \"icon\": \"icon\",\n"
                + "        \"id\": \"id\",\n"
                + "        \"name\": \"name\"\n"
                + "      }\n"
                + "    ],\n"
                + "    \"add_ons_removed\": [\n"
                + "      {\n"
                + "        \"deleted\": true,\n"
                + "        \"description\": \"description\",\n"
                + "        \"icon\": \"icon\",\n"
                + "        \"id\": \"id\",\n"
                + "        \"name\": \"name\"\n"
                + "      }\n"
                + "    ],\n"
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
                + "    \"api_key_request\": {\n"
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
                + "    },\n"
                + "    \"base_plan\": {\n"
                + "      \"deleted\": true,\n"
                + "      \"description\": \"description\",\n"
                + "      \"icon\": \"icon\",\n"
                + "      \"id\": \"id\",\n"
                + "      \"name\": \"name\"\n"
                + "    },\n"
                + "    \"base_plan_action\": \"fallback\",\n"
                + "    \"company\": {\n"
                + "      \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"environment_id\": \"environment_id\",\n"
                + "      \"id\": \"id\",\n"
                + "      \"last_seen_at\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"logo_url\": \"logo_url\",\n"
                + "      \"name\": \"name\",\n"
                + "      \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "    },\n"
                + "    \"company_id\": \"company_id\",\n"
                + "    \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"environment_id\": \"environment_id\",\n"
                + "    \"id\": \"id\",\n"
                + "    \"previous_base_plan\": {\n"
                + "      \"deleted\": true,\n"
                + "      \"description\": \"description\",\n"
                + "      \"icon\": \"icon\",\n"
                + "      \"id\": \"id\",\n"
                + "      \"name\": \"name\"\n"
                + "    },\n"
                + "    \"request_id\": \"request_id\",\n"
                + "    \"subscription_change_action\": \"downgrade\",\n"
                + "    \"traits_updated\": [\n"
                + "      {\n"
                + "        \"feature_id\": \"feature_id\",\n"
                + "        \"hierarchy\": [\n"
                + "          \"hierarchy\"\n"
                + "        ],\n"
                + "        \"reason\": \"reason\",\n"
                + "        \"trait_id\": \"trait_id\",\n"
                + "        \"trait_name\": \"trait_name\",\n"
                + "        \"trait_type\": \"trait_type\",\n"
                + "        \"value\": \"value\"\n"
                + "      }\n"
                + "    ],\n"
                + "    \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
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
    public void testListPlanTraits() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":[{\"account_id\":\"account_id\",\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"plan_id\":\"plan_id\",\"plan_type\":\"plan_type\",\"trait_id\":\"trait_id\",\"trait_value\":\"trait_value\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"params\":{\"ids\":[\"ids\"],\"limit\":1,\"offset\":1,\"plan_id\":\"plan_id\",\"trait_id\":\"trait_id\",\"trait_ids\":[\"trait_ids\"]}}"));
        ListPlanTraitsResponse response = client.companies()
                .listPlanTraits(ListPlanTraitsRequest.builder()
                        .planId("plan_id")
                        .traitId("trait_id")
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
                + "      \"id\": \"id\",\n"
                + "      \"plan_id\": \"plan_id\",\n"
                + "      \"plan_type\": \"plan_type\",\n"
                + "      \"trait_id\": \"trait_id\",\n"
                + "      \"trait_value\": \"trait_value\",\n"
                + "      \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "    }\n"
                + "  ],\n"
                + "  \"params\": {\n"
                + "    \"ids\": [\n"
                + "      \"ids\"\n"
                + "    ],\n"
                + "    \"limit\": 1,\n"
                + "    \"offset\": 1,\n"
                + "    \"plan_id\": \"plan_id\",\n"
                + "    \"trait_id\": \"trait_id\",\n"
                + "    \"trait_ids\": [\n"
                + "      \"trait_ids\"\n"
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
    public void testCreatePlanTrait() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":{\"account_id\":\"account_id\",\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"plan_id\":\"plan_id\",\"plan_type\":\"plan_type\",\"trait_id\":\"trait_id\",\"trait_value\":\"trait_value\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"params\":{\"key\":\"value\"}}"));
        CreatePlanTraitResponse response = client.companies()
                .createPlanTrait(CreatePlanTraitRequestBody.builder()
                        .planId("plan_id")
                        .traitId("trait_id")
                        .traitValue("trait_value")
                        .build());
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("POST", request.getMethod());
        // Validate request body
        String actualRequestBody = request.getBody().readUtf8();
        String expectedRequestBody = ""
                + "{\n"
                + "  \"plan_id\": \"plan_id\",\n"
                + "  \"trait_id\": \"trait_id\",\n"
                + "  \"trait_value\": \"trait_value\"\n"
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
                + "    \"id\": \"id\",\n"
                + "    \"plan_id\": \"plan_id\",\n"
                + "    \"plan_type\": \"plan_type\",\n"
                + "    \"trait_id\": \"trait_id\",\n"
                + "    \"trait_value\": \"trait_value\",\n"
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
    public void testGetPlanTrait() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":{\"account_id\":\"account_id\",\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"plan_id\":\"plan_id\",\"plan_type\":\"plan_type\",\"trait_id\":\"trait_id\",\"trait_value\":\"trait_value\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"params\":{\"key\":\"value\"}}"));
        GetPlanTraitResponse response = client.companies().getPlanTrait("plan_trait_id");
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("GET", request.getMethod());

        // Validate response body
        Assertions.assertNotNull(response, "Response should not be null");
        String actualResponseJson = objectMapper.writeValueAsString(response);
        String expectedResponseBody = ""
                + "{\n"
                + "  \"data\": {\n"
                + "    \"account_id\": \"account_id\",\n"
                + "    \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"environment_id\": \"environment_id\",\n"
                + "    \"id\": \"id\",\n"
                + "    \"plan_id\": \"plan_id\",\n"
                + "    \"plan_type\": \"plan_type\",\n"
                + "    \"trait_id\": \"trait_id\",\n"
                + "    \"trait_value\": \"trait_value\",\n"
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
    public void testUpdatePlanTrait() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":{\"account_id\":\"account_id\",\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"plan_id\":\"plan_id\",\"plan_type\":\"plan_type\",\"trait_id\":\"trait_id\",\"trait_value\":\"trait_value\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"params\":{\"key\":\"value\"}}"));
        UpdatePlanTraitResponse response = client.companies()
                .updatePlanTrait(
                        "plan_trait_id",
                        UpdatePlanTraitRequestBody.builder()
                                .planId("plan_id")
                                .traitValue("trait_value")
                                .build());
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("PUT", request.getMethod());
        // Validate request body
        String actualRequestBody = request.getBody().readUtf8();
        String expectedRequestBody =
                "" + "{\n" + "  \"plan_id\": \"plan_id\",\n" + "  \"trait_value\": \"trait_value\"\n" + "}";
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
                + "    \"id\": \"id\",\n"
                + "    \"plan_id\": \"plan_id\",\n"
                + "    \"plan_type\": \"plan_type\",\n"
                + "    \"trait_id\": \"trait_id\",\n"
                + "    \"trait_value\": \"trait_value\",\n"
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
    public void testDeletePlanTrait() throws Exception {
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody("{\"data\":{\"deleted\":true},\"params\":{\"key\":\"value\"}}"));
        DeletePlanTraitResponse response = client.companies().deletePlanTrait("plan_trait_id");
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
    public void testUpdatePlanTraitsBulk() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":[{\"account_id\":\"account_id\",\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"plan_id\":\"plan_id\",\"plan_type\":\"plan_type\",\"trait_id\":\"trait_id\",\"trait_value\":\"trait_value\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"params\":{\"key\":\"value\"}}"));
        UpdatePlanTraitsBulkResponse response = client.companies()
                .updatePlanTraitsBulk(UpdatePlanTraitBulkRequestBody.builder()
                        .planId("plan_id")
                        .traits(Arrays.asList(UpdatePlanTraitTraitRequestBody.builder()
                                .traitId("trait_id")
                                .traitValue("trait_value")
                                .build()))
                        .build());
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("POST", request.getMethod());
        // Validate request body
        String actualRequestBody = request.getBody().readUtf8();
        String expectedRequestBody = ""
                + "{\n"
                + "  \"plan_id\": \"plan_id\",\n"
                + "  \"traits\": [\n"
                + "    {\n"
                + "      \"trait_id\": \"trait_id\",\n"
                + "      \"trait_value\": \"trait_value\"\n"
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
                + "  \"data\": [\n"
                + "    {\n"
                + "      \"account_id\": \"account_id\",\n"
                + "      \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"environment_id\": \"environment_id\",\n"
                + "      \"id\": \"id\",\n"
                + "      \"plan_id\": \"plan_id\",\n"
                + "      \"plan_type\": \"plan_type\",\n"
                + "      \"trait_id\": \"trait_id\",\n"
                + "      \"trait_value\": \"trait_value\",\n"
                + "      \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "    }\n"
                + "  ],\n"
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
    public void testCountPlanTraits() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":{\"count\":1},\"params\":{\"ids\":[\"ids\"],\"limit\":1,\"offset\":1,\"plan_id\":\"plan_id\",\"trait_id\":\"trait_id\",\"trait_ids\":[\"trait_ids\"]}}"));
        CountPlanTraitsResponse response = client.companies()
                .countPlanTraits(CountPlanTraitsRequest.builder()
                        .planId("plan_id")
                        .traitId("trait_id")
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
                + "    \"plan_id\": \"plan_id\",\n"
                + "    \"trait_id\": \"trait_id\",\n"
                + "    \"trait_ids\": [\n"
                + "      \"trait_ids\"\n"
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
    public void testUpsertUserTrait() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":{\"company_memberships\":[{\"company_id\":\"company_id\",\"created_at\":\"2024-01-15T09:30:00Z\",\"id\":\"id\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"user_id\":\"user_id\"}],\"created_at\":\"2024-01-15T09:30:00Z\",\"entity_traits\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"definition_id\":\"definition_id\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"value\":\"value\"}],\"environment_id\":\"environment_id\",\"id\":\"id\",\"keys\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"definition_id\":\"definition_id\",\"entity_id\":\"entity_id\",\"entity_type\":\"entity_type\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"key\":\"key\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"value\":\"value\"}],\"last_seen_at\":\"2024-01-15T09:30:00Z\",\"name\":\"name\",\"traits\":{\"key\":\"value\"},\"updated_at\":\"2024-01-15T09:30:00Z\"},\"params\":{\"key\":\"value\"}}"));
        UpsertUserTraitResponse response = client.companies()
                .upsertUserTrait(UpsertTraitRequestBody.builder()
                        .keys(new HashMap<String, String>() {
                            {
                                put("key", "value");
                            }
                        })
                        .trait("trait")
                        .build());
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("POST", request.getMethod());
        // Validate request body
        String actualRequestBody = request.getBody().readUtf8();
        String expectedRequestBody = ""
                + "{\n"
                + "  \"keys\": {\n"
                + "    \"key\": \"value\"\n"
                + "  },\n"
                + "  \"trait\": \"trait\"\n"
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
                + "    \"company_memberships\": [\n"
                + "      {\n"
                + "        \"company_id\": \"company_id\",\n"
                + "        \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"id\": \"id\",\n"
                + "        \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"user_id\": \"user_id\"\n"
                + "      }\n"
                + "    ],\n"
                + "    \"created_at\": \"2024-01-15T09:30:00Z\",\n"
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
                + "    \"name\": \"name\",\n"
                + "    \"traits\": {\n"
                + "      \"key\": \"value\"\n"
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
    public void testListUsers() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":[{\"company_memberships\":[{\"company_id\":\"company_id\",\"created_at\":\"2024-01-15T09:30:00Z\",\"id\":\"id\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"user_id\":\"user_id\"}],\"created_at\":\"2024-01-15T09:30:00Z\",\"entity_traits\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"definition_id\":\"definition_id\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"value\":\"value\"}],\"environment_id\":\"environment_id\",\"id\":\"id\",\"keys\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"definition_id\":\"definition_id\",\"entity_id\":\"entity_id\",\"entity_type\":\"entity_type\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"key\":\"key\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"value\":\"value\"}],\"last_seen_at\":\"2024-01-15T09:30:00Z\",\"name\":\"name\",\"traits\":{\"key\":\"value\"},\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"params\":{\"company_id\":\"company_id\",\"ids\":[\"ids\"],\"limit\":1,\"offset\":1,\"plan_id\":\"plan_id\",\"q\":\"q\"}}"));
        ListUsersResponse response = client.companies()
                .listUsers(ListUsersRequest.builder()
                        .companyId("company_id")
                        .planId("plan_id")
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
                + "      \"company_memberships\": [\n"
                + "        {\n"
                + "          \"company_id\": \"company_id\",\n"
                + "          \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "          \"id\": \"id\",\n"
                + "          \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "          \"user_id\": \"user_id\"\n"
                + "        }\n"
                + "      ],\n"
                + "      \"created_at\": \"2024-01-15T09:30:00Z\",\n"
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
                + "      \"name\": \"name\",\n"
                + "      \"traits\": {\n"
                + "        \"key\": \"value\"\n"
                + "      },\n"
                + "      \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "    }\n"
                + "  ],\n"
                + "  \"params\": {\n"
                + "    \"company_id\": \"company_id\",\n"
                + "    \"ids\": [\n"
                + "      \"ids\"\n"
                + "    ],\n"
                + "    \"limit\": 1,\n"
                + "    \"offset\": 1,\n"
                + "    \"plan_id\": \"plan_id\",\n"
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
    public void testUpsertUser() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":{\"company_memberships\":[{\"company_id\":\"company_id\",\"created_at\":\"2024-01-15T09:30:00Z\",\"id\":\"id\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"user_id\":\"user_id\"}],\"created_at\":\"2024-01-15T09:30:00Z\",\"entity_traits\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"definition_id\":\"definition_id\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"value\":\"value\"}],\"environment_id\":\"environment_id\",\"id\":\"id\",\"keys\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"definition_id\":\"definition_id\",\"entity_id\":\"entity_id\",\"entity_type\":\"entity_type\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"key\":\"key\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"value\":\"value\"}],\"last_seen_at\":\"2024-01-15T09:30:00Z\",\"name\":\"name\",\"traits\":{\"key\":\"value\"},\"updated_at\":\"2024-01-15T09:30:00Z\"},\"params\":{\"key\":\"value\"}}"));
        UpsertUserResponse response = client.companies()
                .upsertUser(UpsertUserRequestBody.builder()
                        .keys(new HashMap<String, String>() {
                            {
                                put("key", "value");
                            }
                        })
                        .build());
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("POST", request.getMethod());
        // Validate request body
        String actualRequestBody = request.getBody().readUtf8();
        String expectedRequestBody = "" + "{\n" + "  \"keys\": {\n" + "    \"key\": \"value\"\n" + "  }\n" + "}";
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
                + "    \"company_memberships\": [\n"
                + "      {\n"
                + "        \"company_id\": \"company_id\",\n"
                + "        \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"id\": \"id\",\n"
                + "        \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"user_id\": \"user_id\"\n"
                + "      }\n"
                + "    ],\n"
                + "    \"created_at\": \"2024-01-15T09:30:00Z\",\n"
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
                + "    \"name\": \"name\",\n"
                + "    \"traits\": {\n"
                + "      \"key\": \"value\"\n"
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
    public void testGetUser() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":{\"company_memberships\":[{\"company_id\":\"company_id\",\"created_at\":\"2024-01-15T09:30:00Z\",\"id\":\"id\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"user_id\":\"user_id\"}],\"created_at\":\"2024-01-15T09:30:00Z\",\"entity_traits\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"definition_id\":\"definition_id\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"value\":\"value\"}],\"environment_id\":\"environment_id\",\"id\":\"id\",\"keys\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"definition_id\":\"definition_id\",\"entity_id\":\"entity_id\",\"entity_type\":\"entity_type\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"key\":\"key\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"value\":\"value\"}],\"last_seen_at\":\"2024-01-15T09:30:00Z\",\"name\":\"name\",\"traits\":{\"key\":\"value\"},\"updated_at\":\"2024-01-15T09:30:00Z\"},\"params\":{\"key\":\"value\"}}"));
        GetUserResponse response = client.companies().getUser("user_id");
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("GET", request.getMethod());

        // Validate response body
        Assertions.assertNotNull(response, "Response should not be null");
        String actualResponseJson = objectMapper.writeValueAsString(response);
        String expectedResponseBody = ""
                + "{\n"
                + "  \"data\": {\n"
                + "    \"company_memberships\": [\n"
                + "      {\n"
                + "        \"company_id\": \"company_id\",\n"
                + "        \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"id\": \"id\",\n"
                + "        \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"user_id\": \"user_id\"\n"
                + "      }\n"
                + "    ],\n"
                + "    \"created_at\": \"2024-01-15T09:30:00Z\",\n"
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
                + "    \"name\": \"name\",\n"
                + "    \"traits\": {\n"
                + "      \"key\": \"value\"\n"
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
    public void testDeleteUser() throws Exception {
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody("{\"data\":{\"deleted\":true},\"params\":{\"key\":\"value\"}}"));
        DeleteUserResponse response = client.companies().deleteUser("user_id");
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
    public void testCountUsers() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":{\"count\":1},\"params\":{\"company_id\":\"company_id\",\"ids\":[\"ids\"],\"limit\":1,\"offset\":1,\"plan_id\":\"plan_id\",\"q\":\"q\"}}"));
        CountUsersResponse response = client.companies()
                .countUsers(CountUsersRequest.builder()
                        .companyId("company_id")
                        .planId("plan_id")
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
                + "    \"ids\": [\n"
                + "      \"ids\"\n"
                + "    ],\n"
                + "    \"limit\": 1,\n"
                + "    \"offset\": 1,\n"
                + "    \"plan_id\": \"plan_id\",\n"
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
    public void testCreateUser() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":{\"company_memberships\":[{\"company_id\":\"company_id\",\"created_at\":\"2024-01-15T09:30:00Z\",\"id\":\"id\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"user_id\":\"user_id\"}],\"created_at\":\"2024-01-15T09:30:00Z\",\"entity_traits\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"definition_id\":\"definition_id\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"value\":\"value\"}],\"environment_id\":\"environment_id\",\"id\":\"id\",\"keys\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"definition_id\":\"definition_id\",\"entity_id\":\"entity_id\",\"entity_type\":\"entity_type\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"key\":\"key\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"value\":\"value\"}],\"last_seen_at\":\"2024-01-15T09:30:00Z\",\"name\":\"name\",\"traits\":{\"key\":\"value\"},\"updated_at\":\"2024-01-15T09:30:00Z\"},\"params\":{\"key\":\"value\"}}"));
        CreateUserResponse response = client.companies()
                .createUser(UpsertUserRequestBody.builder()
                        .keys(new HashMap<String, String>() {
                            {
                                put("key", "value");
                            }
                        })
                        .build());
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("POST", request.getMethod());
        // Validate request body
        String actualRequestBody = request.getBody().readUtf8();
        String expectedRequestBody = "" + "{\n" + "  \"keys\": {\n" + "    \"key\": \"value\"\n" + "  }\n" + "}";
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
                + "    \"company_memberships\": [\n"
                + "      {\n"
                + "        \"company_id\": \"company_id\",\n"
                + "        \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"id\": \"id\",\n"
                + "        \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"user_id\": \"user_id\"\n"
                + "      }\n"
                + "    ],\n"
                + "    \"created_at\": \"2024-01-15T09:30:00Z\",\n"
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
                + "    \"name\": \"name\",\n"
                + "    \"traits\": {\n"
                + "      \"key\": \"value\"\n"
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
    public void testDeleteUserByKeys() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"last_seen_at\":\"2024-01-15T09:30:00Z\",\"name\":\"name\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"params\":{\"key\":\"value\"}}"));
        DeleteUserByKeysResponse response = client.companies()
                .deleteUserByKeys(KeysRequestBody.builder()
                        .keys(new HashMap<String, String>() {
                            {
                                put("key", "value");
                            }
                        })
                        .build());
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("POST", request.getMethod());
        // Validate request body
        String actualRequestBody = request.getBody().readUtf8();
        String expectedRequestBody = "" + "{\n" + "  \"keys\": {\n" + "    \"key\": \"value\"\n" + "  }\n" + "}";
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
                + "    \"environment_id\": \"environment_id\",\n"
                + "    \"id\": \"id\",\n"
                + "    \"last_seen_at\": \"2024-01-15T09:30:00Z\",\n"
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
    public void testLookupUser() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":{\"company_memberships\":[{\"company\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"last_seen_at\":\"2024-01-15T09:30:00Z\",\"logo_url\":\"logo_url\",\"name\":\"name\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"company_id\":\"company_id\",\"created_at\":\"2024-01-15T09:30:00Z\",\"id\":\"id\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"user_id\":\"user_id\"},{\"company\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"last_seen_at\":\"2024-01-15T09:30:00Z\",\"logo_url\":\"logo_url\",\"name\":\"name\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"company_id\":\"company_id\",\"created_at\":\"2024-01-15T09:30:00Z\",\"id\":\"id\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"user_id\":\"user_id\"}],\"created_at\":\"2024-01-15T09:30:00Z\",\"entity_traits\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"definition\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"display_name\":\"display_name\",\"entity_type\":\"entity_type\",\"hierarchy\":[\"hierarchy\",\"hierarchy\"],\"id\":\"id\",\"trait_type\":\"trait_type\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"definition_id\":\"definition_id\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"value\":\"value\"},{\"created_at\":\"2024-01-15T09:30:00Z\",\"definition\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"display_name\":\"display_name\",\"entity_type\":\"entity_type\",\"hierarchy\":[\"hierarchy\",\"hierarchy\"],\"id\":\"id\",\"trait_type\":\"trait_type\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"definition_id\":\"definition_id\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"value\":\"value\"}],\"environment_id\":\"environment_id\",\"id\":\"id\",\"keys\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"definition\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"entity_type\":\"entity_type\",\"id\":\"id\",\"key\":\"key\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"definition_id\":\"definition_id\",\"entity_id\":\"entity_id\",\"entity_type\":\"entity_type\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"key\":\"key\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"value\":\"value\"},{\"created_at\":\"2024-01-15T09:30:00Z\",\"definition\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"entity_type\":\"entity_type\",\"id\":\"id\",\"key\":\"key\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"definition_id\":\"definition_id\",\"entity_id\":\"entity_id\",\"entity_type\":\"entity_type\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"key\":\"key\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"value\":\"value\"}],\"last_seen_at\":\"2024-01-15T09:30:00Z\",\"name\":\"name\",\"traits\":{\"traits\":{\"key\":\"value\"}},\"updated_at\":\"2024-01-15T09:30:00Z\"},\"params\":{\"keys\":{\"keys\":\"keys\"}}}"));
        LookupUserResponse response = client.companies()
                .lookupUser(LookupUserRequest.builder()
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
                + "    \"company_memberships\": [\n"
                + "      {\n"
                + "        \"company\": {\n"
                + "          \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "          \"environment_id\": \"environment_id\",\n"
                + "          \"id\": \"id\",\n"
                + "          \"last_seen_at\": \"2024-01-15T09:30:00Z\",\n"
                + "          \"logo_url\": \"logo_url\",\n"
                + "          \"name\": \"name\",\n"
                + "          \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "        },\n"
                + "        \"company_id\": \"company_id\",\n"
                + "        \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"id\": \"id\",\n"
                + "        \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"user_id\": \"user_id\"\n"
                + "      },\n"
                + "      {\n"
                + "        \"company\": {\n"
                + "          \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "          \"environment_id\": \"environment_id\",\n"
                + "          \"id\": \"id\",\n"
                + "          \"last_seen_at\": \"2024-01-15T09:30:00Z\",\n"
                + "          \"logo_url\": \"logo_url\",\n"
                + "          \"name\": \"name\",\n"
                + "          \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "        },\n"
                + "        \"company_id\": \"company_id\",\n"
                + "        \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"id\": \"id\",\n"
                + "        \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"user_id\": \"user_id\"\n"
                + "      }\n"
                + "    ],\n"
                + "    \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"entity_traits\": [\n"
                + "      {\n"
                + "        \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"definition\": {\n"
                + "          \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "          \"display_name\": \"display_name\",\n"
                + "          \"entity_type\": \"entity_type\",\n"
                + "          \"hierarchy\": [\n"
                + "            \"hierarchy\",\n"
                + "            \"hierarchy\"\n"
                + "          ],\n"
                + "          \"id\": \"id\",\n"
                + "          \"trait_type\": \"trait_type\",\n"
                + "          \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "        },\n"
                + "        \"definition_id\": \"definition_id\",\n"
                + "        \"environment_id\": \"environment_id\",\n"
                + "        \"id\": \"id\",\n"
                + "        \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"value\": \"value\"\n"
                + "      },\n"
                + "      {\n"
                + "        \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"definition\": {\n"
                + "          \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "          \"display_name\": \"display_name\",\n"
                + "          \"entity_type\": \"entity_type\",\n"
                + "          \"hierarchy\": [\n"
                + "            \"hierarchy\",\n"
                + "            \"hierarchy\"\n"
                + "          ],\n"
                + "          \"id\": \"id\",\n"
                + "          \"trait_type\": \"trait_type\",\n"
                + "          \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "        },\n"
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
                + "        \"definition\": {\n"
                + "          \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "          \"entity_type\": \"entity_type\",\n"
                + "          \"id\": \"id\",\n"
                + "          \"key\": \"key\",\n"
                + "          \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "        },\n"
                + "        \"definition_id\": \"definition_id\",\n"
                + "        \"entity_id\": \"entity_id\",\n"
                + "        \"entity_type\": \"entity_type\",\n"
                + "        \"environment_id\": \"environment_id\",\n"
                + "        \"id\": \"id\",\n"
                + "        \"key\": \"key\",\n"
                + "        \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"value\": \"value\"\n"
                + "      },\n"
                + "      {\n"
                + "        \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"definition\": {\n"
                + "          \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "          \"entity_type\": \"entity_type\",\n"
                + "          \"id\": \"id\",\n"
                + "          \"key\": \"key\",\n"
                + "          \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "        },\n"
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
                + "    \"name\": \"name\",\n"
                + "    \"traits\": {\n"
                + "      \"traits\": {\n"
                + "        \"key\": \"value\"\n"
                + "      }\n"
                + "    },\n"
                + "    \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
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
