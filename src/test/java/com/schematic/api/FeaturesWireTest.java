package com.schematic.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.schematic.api.core.ObjectMappers;
import com.schematic.api.resources.features.requests.CountFeaturesRequest;
import com.schematic.api.resources.features.requests.CountFlagsRequest;
import com.schematic.api.resources.features.requests.CreateFeatureRequestBody;
import com.schematic.api.resources.features.requests.ListFeaturesRequest;
import com.schematic.api.resources.features.requests.ListFlagsRequest;
import com.schematic.api.resources.features.requests.UpdateFeatureRequestBody;
import com.schematic.api.resources.features.requests.UpdateFlagRulesRequestBody;
import com.schematic.api.resources.features.types.CheckFlagResponse;
import com.schematic.api.resources.features.types.CheckFlagsResponse;
import com.schematic.api.resources.features.types.CountFeaturesResponse;
import com.schematic.api.resources.features.types.CountFlagsResponse;
import com.schematic.api.resources.features.types.CreateFeatureRequestBodyFeatureType;
import com.schematic.api.resources.features.types.CreateFeatureResponse;
import com.schematic.api.resources.features.types.CreateFlagResponse;
import com.schematic.api.resources.features.types.DeleteFeatureResponse;
import com.schematic.api.resources.features.types.DeleteFlagResponse;
import com.schematic.api.resources.features.types.GetFeatureResponse;
import com.schematic.api.resources.features.types.GetFlagResponse;
import com.schematic.api.resources.features.types.ListFeaturesResponse;
import com.schematic.api.resources.features.types.ListFlagsResponse;
import com.schematic.api.resources.features.types.UpdateFeatureResponse;
import com.schematic.api.resources.features.types.UpdateFlagResponse;
import com.schematic.api.resources.features.types.UpdateFlagRulesResponse;
import com.schematic.api.types.CheckFlagRequestBody;
import com.schematic.api.types.CreateFlagRequestBody;
import com.schematic.api.types.CreateOrUpdateConditionGroupRequestBody;
import com.schematic.api.types.CreateOrUpdateConditionRequestBody;
import com.schematic.api.types.CreateOrUpdateConditionRequestBodyConditionType;
import com.schematic.api.types.CreateOrUpdateConditionRequestBodyOperator;
import com.schematic.api.types.CreateOrUpdateRuleRequestBody;
import java.util.Arrays;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FeaturesWireTest {
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
    public void testListFeatures() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"description\":\"description\",\"event_subtype\":\"event_subtype\",\"event_summary\":{\"company_count\":1,\"environment_id\":\"environment_id\",\"event_count\":1,\"event_subtype\":\"event_subtype\",\"user_count\":1},\"feature_type\":\"feature_type\",\"flags\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"default_value\":true,\"description\":\"description\",\"flag_type\":\"flag_type\",\"id\":\"id\",\"key\":\"key\",\"name\":\"name\",\"rules\":[{\"condition_groups\":[{\"conditions\":[{\"condition_type\":\"condition_type\",\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"operator\":\"operator\",\"resource_ids\":[\"resource_ids\"],\"resources\":[{\"id\":\"id\",\"name\":\"name\"}],\"rule_id\":\"rule_id\",\"trait_value\":\"trait_value\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"rule_id\":\"rule_id\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"conditions\":[{\"condition_type\":\"condition_type\",\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"operator\":\"operator\",\"resource_ids\":[\"resource_ids\"],\"resources\":[{\"id\":\"id\",\"name\":\"name\"}],\"rule_id\":\"rule_id\",\"trait_value\":\"trait_value\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"name\":\"name\",\"priority\":1,\"rule_type\":\"rule_type\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"value\":true}],\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"icon\":\"icon\",\"id\":\"id\",\"lifecycle_phase\":\"lifecycle_phase\",\"maintainer_id\":\"maintainer_id\",\"name\":\"name\",\"plans\":[{\"id\":\"id\",\"name\":\"name\"}],\"plural_name\":\"plural_name\",\"singular_name\":\"singular_name\",\"trait\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"display_name\":\"display_name\",\"entity_type\":\"entity_type\",\"hierarchy\":[\"hierarchy\"],\"id\":\"id\",\"trait_type\":\"trait_type\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"trait_id\":\"trait_id\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"params\":{\"boolean_require_event\":true,\"feature_type\":[\"feature_type\"],\"ids\":[\"ids\"],\"limit\":1,\"offset\":1,\"q\":\"q\",\"without_company_override_for\":\"without_company_override_for\",\"without_plan_entitlement_for\":\"without_plan_entitlement_for\"}}"));
        ListFeaturesResponse response = client.features()
                .listFeatures(ListFeaturesRequest.builder()
                        .q("q")
                        .withoutCompanyOverrideFor("without_company_override_for")
                        .withoutPlanEntitlementFor("without_plan_entitlement_for")
                        .booleanRequireEvent(true)
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
                + "      \"event_subtype\": \"event_subtype\",\n"
                + "      \"event_summary\": {\n"
                + "        \"company_count\": 1,\n"
                + "        \"environment_id\": \"environment_id\",\n"
                + "        \"event_count\": 1,\n"
                + "        \"event_subtype\": \"event_subtype\",\n"
                + "        \"user_count\": 1\n"
                + "      },\n"
                + "      \"feature_type\": \"feature_type\",\n"
                + "      \"flags\": [\n"
                + "        {\n"
                + "          \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "          \"default_value\": true,\n"
                + "          \"description\": \"description\",\n"
                + "          \"flag_type\": \"flag_type\",\n"
                + "          \"id\": \"id\",\n"
                + "          \"key\": \"key\",\n"
                + "          \"name\": \"name\",\n"
                + "          \"rules\": [\n"
                + "            {\n"
                + "              \"condition_groups\": [\n"
                + "                {\n"
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
                + "                  \"rule_id\": \"rule_id\",\n"
                + "                  \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "                }\n"
                + "              ],\n"
                + "              \"conditions\": [\n"
                + "                {\n"
                + "                  \"condition_type\": \"condition_type\",\n"
                + "                  \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "                  \"environment_id\": \"environment_id\",\n"
                + "                  \"id\": \"id\",\n"
                + "                  \"operator\": \"operator\",\n"
                + "                  \"resource_ids\": [\n"
                + "                    \"resource_ids\"\n"
                + "                  ],\n"
                + "                  \"resources\": [\n"
                + "                    {\n"
                + "                      \"id\": \"id\",\n"
                + "                      \"name\": \"name\"\n"
                + "                    }\n"
                + "                  ],\n"
                + "                  \"rule_id\": \"rule_id\",\n"
                + "                  \"trait_value\": \"trait_value\",\n"
                + "                  \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "                }\n"
                + "              ],\n"
                + "              \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "              \"environment_id\": \"environment_id\",\n"
                + "              \"id\": \"id\",\n"
                + "              \"name\": \"name\",\n"
                + "              \"priority\": 1,\n"
                + "              \"rule_type\": \"rule_type\",\n"
                + "              \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "              \"value\": true\n"
                + "            }\n"
                + "          ],\n"
                + "          \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "        }\n"
                + "      ],\n"
                + "      \"icon\": \"icon\",\n"
                + "      \"id\": \"id\",\n"
                + "      \"lifecycle_phase\": \"lifecycle_phase\",\n"
                + "      \"maintainer_id\": \"maintainer_id\",\n"
                + "      \"name\": \"name\",\n"
                + "      \"plans\": [\n"
                + "        {\n"
                + "          \"id\": \"id\",\n"
                + "          \"name\": \"name\"\n"
                + "        }\n"
                + "      ],\n"
                + "      \"plural_name\": \"plural_name\",\n"
                + "      \"singular_name\": \"singular_name\",\n"
                + "      \"trait\": {\n"
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
                + "      \"trait_id\": \"trait_id\",\n"
                + "      \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "    }\n"
                + "  ],\n"
                + "  \"params\": {\n"
                + "    \"boolean_require_event\": true,\n"
                + "    \"feature_type\": [\n"
                + "      \"feature_type\"\n"
                + "    ],\n"
                + "    \"ids\": [\n"
                + "      \"ids\"\n"
                + "    ],\n"
                + "    \"limit\": 1,\n"
                + "    \"offset\": 1,\n"
                + "    \"q\": \"q\",\n"
                + "    \"without_company_override_for\": \"without_company_override_for\",\n"
                + "    \"without_plan_entitlement_for\": \"without_plan_entitlement_for\"\n"
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
    public void testCreateFeature() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"description\":\"description\",\"event_subtype\":\"event_subtype\",\"event_summary\":{\"company_count\":1,\"environment_id\":\"environment_id\",\"event_count\":1,\"event_subtype\":\"event_subtype\",\"last_seen_at\":\"2024-01-15T09:30:00Z\",\"user_count\":1},\"feature_type\":\"feature_type\",\"flags\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"default_value\":true,\"description\":\"description\",\"flag_type\":\"flag_type\",\"id\":\"id\",\"key\":\"key\",\"name\":\"name\",\"rules\":[{\"condition_groups\":[{\"conditions\":[{\"condition_type\":\"condition_type\",\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"operator\":\"operator\",\"resource_ids\":[\"resource_ids\"],\"resources\":[{\"id\":\"id\",\"name\":\"name\"}],\"rule_id\":\"rule_id\",\"trait_value\":\"trait_value\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"rule_id\":\"rule_id\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"conditions\":[{\"condition_type\":\"condition_type\",\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"operator\":\"operator\",\"resource_ids\":[\"resource_ids\"],\"resources\":[{\"id\":\"id\",\"name\":\"name\"}],\"rule_id\":\"rule_id\",\"trait_value\":\"trait_value\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"name\":\"name\",\"priority\":1,\"rule_type\":\"rule_type\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"value\":true}],\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"icon\":\"icon\",\"id\":\"id\",\"lifecycle_phase\":\"lifecycle_phase\",\"maintainer_id\":\"maintainer_id\",\"name\":\"name\",\"plans\":[{\"id\":\"id\",\"name\":\"name\"}],\"plural_name\":\"plural_name\",\"singular_name\":\"singular_name\",\"trait\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"display_name\":\"display_name\",\"entity_type\":\"entity_type\",\"hierarchy\":[\"hierarchy\"],\"id\":\"id\",\"trait_type\":\"trait_type\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"trait_id\":\"trait_id\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"params\":{\"key\":\"value\"}}"));
        CreateFeatureResponse response = client.features()
                .createFeature(CreateFeatureRequestBody.builder()
                        .description("description")
                        .featureType(CreateFeatureRequestBodyFeatureType.BOOLEAN)
                        .name("name")
                        .build());
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("POST", request.getMethod());
        // Validate request body
        String actualRequestBody = request.getBody().readUtf8();
        String expectedRequestBody = ""
                + "{\n"
                + "  \"description\": \"description\",\n"
                + "  \"feature_type\": \"boolean\",\n"
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
                + "    \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"description\": \"description\",\n"
                + "    \"event_subtype\": \"event_subtype\",\n"
                + "    \"event_summary\": {\n"
                + "      \"company_count\": 1,\n"
                + "      \"environment_id\": \"environment_id\",\n"
                + "      \"event_count\": 1,\n"
                + "      \"event_subtype\": \"event_subtype\",\n"
                + "      \"last_seen_at\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"user_count\": 1\n"
                + "    },\n"
                + "    \"feature_type\": \"feature_type\",\n"
                + "    \"flags\": [\n"
                + "      {\n"
                + "        \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"default_value\": true,\n"
                + "        \"description\": \"description\",\n"
                + "        \"flag_type\": \"flag_type\",\n"
                + "        \"id\": \"id\",\n"
                + "        \"key\": \"key\",\n"
                + "        \"name\": \"name\",\n"
                + "        \"rules\": [\n"
                + "          {\n"
                + "            \"condition_groups\": [\n"
                + "              {\n"
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
                + "                \"rule_id\": \"rule_id\",\n"
                + "                \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "              }\n"
                + "            ],\n"
                + "            \"conditions\": [\n"
                + "              {\n"
                + "                \"condition_type\": \"condition_type\",\n"
                + "                \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "                \"environment_id\": \"environment_id\",\n"
                + "                \"id\": \"id\",\n"
                + "                \"operator\": \"operator\",\n"
                + "                \"resource_ids\": [\n"
                + "                  \"resource_ids\"\n"
                + "                ],\n"
                + "                \"resources\": [\n"
                + "                  {\n"
                + "                    \"id\": \"id\",\n"
                + "                    \"name\": \"name\"\n"
                + "                  }\n"
                + "                ],\n"
                + "                \"rule_id\": \"rule_id\",\n"
                + "                \"trait_value\": \"trait_value\",\n"
                + "                \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "              }\n"
                + "            ],\n"
                + "            \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"environment_id\": \"environment_id\",\n"
                + "            \"id\": \"id\",\n"
                + "            \"name\": \"name\",\n"
                + "            \"priority\": 1,\n"
                + "            \"rule_type\": \"rule_type\",\n"
                + "            \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"value\": true\n"
                + "          }\n"
                + "        ],\n"
                + "        \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "      }\n"
                + "    ],\n"
                + "    \"icon\": \"icon\",\n"
                + "    \"id\": \"id\",\n"
                + "    \"lifecycle_phase\": \"lifecycle_phase\",\n"
                + "    \"maintainer_id\": \"maintainer_id\",\n"
                + "    \"name\": \"name\",\n"
                + "    \"plans\": [\n"
                + "      {\n"
                + "        \"id\": \"id\",\n"
                + "        \"name\": \"name\"\n"
                + "      }\n"
                + "    ],\n"
                + "    \"plural_name\": \"plural_name\",\n"
                + "    \"singular_name\": \"singular_name\",\n"
                + "    \"trait\": {\n"
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
                + "    \"trait_id\": \"trait_id\",\n"
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
    public void testGetFeature() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"description\":\"description\",\"event_subtype\":\"event_subtype\",\"event_summary\":{\"company_count\":1,\"environment_id\":\"environment_id\",\"event_count\":1,\"event_subtype\":\"event_subtype\",\"last_seen_at\":\"2024-01-15T09:30:00Z\",\"user_count\":1},\"feature_type\":\"feature_type\",\"flags\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"default_value\":true,\"description\":\"description\",\"flag_type\":\"flag_type\",\"id\":\"id\",\"key\":\"key\",\"name\":\"name\",\"rules\":[{\"condition_groups\":[{\"conditions\":[{\"condition_type\":\"condition_type\",\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"operator\":\"operator\",\"resource_ids\":[\"resource_ids\"],\"resources\":[{\"id\":\"id\",\"name\":\"name\"}],\"rule_id\":\"rule_id\",\"trait_value\":\"trait_value\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"rule_id\":\"rule_id\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"conditions\":[{\"condition_type\":\"condition_type\",\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"operator\":\"operator\",\"resource_ids\":[\"resource_ids\"],\"resources\":[{\"id\":\"id\",\"name\":\"name\"}],\"rule_id\":\"rule_id\",\"trait_value\":\"trait_value\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"name\":\"name\",\"priority\":1,\"rule_type\":\"rule_type\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"value\":true}],\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"icon\":\"icon\",\"id\":\"id\",\"lifecycle_phase\":\"lifecycle_phase\",\"maintainer_id\":\"maintainer_id\",\"name\":\"name\",\"plans\":[{\"id\":\"id\",\"name\":\"name\"}],\"plural_name\":\"plural_name\",\"singular_name\":\"singular_name\",\"trait\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"display_name\":\"display_name\",\"entity_type\":\"entity_type\",\"hierarchy\":[\"hierarchy\"],\"id\":\"id\",\"trait_type\":\"trait_type\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"trait_id\":\"trait_id\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"params\":{\"key\":\"value\"}}"));
        GetFeatureResponse response = client.features().getFeature("feature_id");
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
                + "    \"event_subtype\": \"event_subtype\",\n"
                + "    \"event_summary\": {\n"
                + "      \"company_count\": 1,\n"
                + "      \"environment_id\": \"environment_id\",\n"
                + "      \"event_count\": 1,\n"
                + "      \"event_subtype\": \"event_subtype\",\n"
                + "      \"last_seen_at\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"user_count\": 1\n"
                + "    },\n"
                + "    \"feature_type\": \"feature_type\",\n"
                + "    \"flags\": [\n"
                + "      {\n"
                + "        \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"default_value\": true,\n"
                + "        \"description\": \"description\",\n"
                + "        \"flag_type\": \"flag_type\",\n"
                + "        \"id\": \"id\",\n"
                + "        \"key\": \"key\",\n"
                + "        \"name\": \"name\",\n"
                + "        \"rules\": [\n"
                + "          {\n"
                + "            \"condition_groups\": [\n"
                + "              {\n"
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
                + "                \"rule_id\": \"rule_id\",\n"
                + "                \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "              }\n"
                + "            ],\n"
                + "            \"conditions\": [\n"
                + "              {\n"
                + "                \"condition_type\": \"condition_type\",\n"
                + "                \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "                \"environment_id\": \"environment_id\",\n"
                + "                \"id\": \"id\",\n"
                + "                \"operator\": \"operator\",\n"
                + "                \"resource_ids\": [\n"
                + "                  \"resource_ids\"\n"
                + "                ],\n"
                + "                \"resources\": [\n"
                + "                  {\n"
                + "                    \"id\": \"id\",\n"
                + "                    \"name\": \"name\"\n"
                + "                  }\n"
                + "                ],\n"
                + "                \"rule_id\": \"rule_id\",\n"
                + "                \"trait_value\": \"trait_value\",\n"
                + "                \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "              }\n"
                + "            ],\n"
                + "            \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"environment_id\": \"environment_id\",\n"
                + "            \"id\": \"id\",\n"
                + "            \"name\": \"name\",\n"
                + "            \"priority\": 1,\n"
                + "            \"rule_type\": \"rule_type\",\n"
                + "            \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"value\": true\n"
                + "          }\n"
                + "        ],\n"
                + "        \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "      }\n"
                + "    ],\n"
                + "    \"icon\": \"icon\",\n"
                + "    \"id\": \"id\",\n"
                + "    \"lifecycle_phase\": \"lifecycle_phase\",\n"
                + "    \"maintainer_id\": \"maintainer_id\",\n"
                + "    \"name\": \"name\",\n"
                + "    \"plans\": [\n"
                + "      {\n"
                + "        \"id\": \"id\",\n"
                + "        \"name\": \"name\"\n"
                + "      }\n"
                + "    ],\n"
                + "    \"plural_name\": \"plural_name\",\n"
                + "    \"singular_name\": \"singular_name\",\n"
                + "    \"trait\": {\n"
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
                + "    \"trait_id\": \"trait_id\",\n"
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
    public void testUpdateFeature() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"description\":\"description\",\"event_subtype\":\"event_subtype\",\"event_summary\":{\"company_count\":1,\"environment_id\":\"environment_id\",\"event_count\":1,\"event_subtype\":\"event_subtype\",\"last_seen_at\":\"2024-01-15T09:30:00Z\",\"user_count\":1},\"feature_type\":\"feature_type\",\"flags\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"default_value\":true,\"description\":\"description\",\"flag_type\":\"flag_type\",\"id\":\"id\",\"key\":\"key\",\"name\":\"name\",\"rules\":[{\"condition_groups\":[{\"conditions\":[{\"condition_type\":\"condition_type\",\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"operator\":\"operator\",\"resource_ids\":[\"resource_ids\"],\"resources\":[{\"id\":\"id\",\"name\":\"name\"}],\"rule_id\":\"rule_id\",\"trait_value\":\"trait_value\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"rule_id\":\"rule_id\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"conditions\":[{\"condition_type\":\"condition_type\",\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"operator\":\"operator\",\"resource_ids\":[\"resource_ids\"],\"resources\":[{\"id\":\"id\",\"name\":\"name\"}],\"rule_id\":\"rule_id\",\"trait_value\":\"trait_value\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"name\":\"name\",\"priority\":1,\"rule_type\":\"rule_type\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"value\":true}],\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"icon\":\"icon\",\"id\":\"id\",\"lifecycle_phase\":\"lifecycle_phase\",\"maintainer_id\":\"maintainer_id\",\"name\":\"name\",\"plans\":[{\"id\":\"id\",\"name\":\"name\"}],\"plural_name\":\"plural_name\",\"singular_name\":\"singular_name\",\"trait\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"display_name\":\"display_name\",\"entity_type\":\"entity_type\",\"hierarchy\":[\"hierarchy\"],\"id\":\"id\",\"trait_type\":\"trait_type\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"trait_id\":\"trait_id\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"params\":{\"key\":\"value\"}}"));
        UpdateFeatureResponse response = client.features()
                .updateFeature("feature_id", UpdateFeatureRequestBody.builder().build());
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
                + "    \"event_subtype\": \"event_subtype\",\n"
                + "    \"event_summary\": {\n"
                + "      \"company_count\": 1,\n"
                + "      \"environment_id\": \"environment_id\",\n"
                + "      \"event_count\": 1,\n"
                + "      \"event_subtype\": \"event_subtype\",\n"
                + "      \"last_seen_at\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"user_count\": 1\n"
                + "    },\n"
                + "    \"feature_type\": \"feature_type\",\n"
                + "    \"flags\": [\n"
                + "      {\n"
                + "        \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"default_value\": true,\n"
                + "        \"description\": \"description\",\n"
                + "        \"flag_type\": \"flag_type\",\n"
                + "        \"id\": \"id\",\n"
                + "        \"key\": \"key\",\n"
                + "        \"name\": \"name\",\n"
                + "        \"rules\": [\n"
                + "          {\n"
                + "            \"condition_groups\": [\n"
                + "              {\n"
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
                + "                \"rule_id\": \"rule_id\",\n"
                + "                \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "              }\n"
                + "            ],\n"
                + "            \"conditions\": [\n"
                + "              {\n"
                + "                \"condition_type\": \"condition_type\",\n"
                + "                \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "                \"environment_id\": \"environment_id\",\n"
                + "                \"id\": \"id\",\n"
                + "                \"operator\": \"operator\",\n"
                + "                \"resource_ids\": [\n"
                + "                  \"resource_ids\"\n"
                + "                ],\n"
                + "                \"resources\": [\n"
                + "                  {\n"
                + "                    \"id\": \"id\",\n"
                + "                    \"name\": \"name\"\n"
                + "                  }\n"
                + "                ],\n"
                + "                \"rule_id\": \"rule_id\",\n"
                + "                \"trait_value\": \"trait_value\",\n"
                + "                \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "              }\n"
                + "            ],\n"
                + "            \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"environment_id\": \"environment_id\",\n"
                + "            \"id\": \"id\",\n"
                + "            \"name\": \"name\",\n"
                + "            \"priority\": 1,\n"
                + "            \"rule_type\": \"rule_type\",\n"
                + "            \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"value\": true\n"
                + "          }\n"
                + "        ],\n"
                + "        \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "      }\n"
                + "    ],\n"
                + "    \"icon\": \"icon\",\n"
                + "    \"id\": \"id\",\n"
                + "    \"lifecycle_phase\": \"lifecycle_phase\",\n"
                + "    \"maintainer_id\": \"maintainer_id\",\n"
                + "    \"name\": \"name\",\n"
                + "    \"plans\": [\n"
                + "      {\n"
                + "        \"id\": \"id\",\n"
                + "        \"name\": \"name\"\n"
                + "      }\n"
                + "    ],\n"
                + "    \"plural_name\": \"plural_name\",\n"
                + "    \"singular_name\": \"singular_name\",\n"
                + "    \"trait\": {\n"
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
                + "    \"trait_id\": \"trait_id\",\n"
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
    public void testDeleteFeature() throws Exception {
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody("{\"data\":{\"deleted\":true},\"params\":{\"key\":\"value\"}}"));
        DeleteFeatureResponse response = client.features().deleteFeature("feature_id");
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
    public void testCountFeatures() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":{\"count\":1},\"params\":{\"boolean_require_event\":true,\"feature_type\":[\"feature_type\"],\"ids\":[\"ids\"],\"limit\":1,\"offset\":1,\"q\":\"q\",\"without_company_override_for\":\"without_company_override_for\",\"without_plan_entitlement_for\":\"without_plan_entitlement_for\"}}"));
        CountFeaturesResponse response = client.features()
                .countFeatures(CountFeaturesRequest.builder()
                        .q("q")
                        .withoutCompanyOverrideFor("without_company_override_for")
                        .withoutPlanEntitlementFor("without_plan_entitlement_for")
                        .booleanRequireEvent(true)
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
                + "    \"boolean_require_event\": true,\n"
                + "    \"feature_type\": [\n"
                + "      \"feature_type\"\n"
                + "    ],\n"
                + "    \"ids\": [\n"
                + "      \"ids\"\n"
                + "    ],\n"
                + "    \"limit\": 1,\n"
                + "    \"offset\": 1,\n"
                + "    \"q\": \"q\",\n"
                + "    \"without_company_override_for\": \"without_company_override_for\",\n"
                + "    \"without_plan_entitlement_for\": \"without_plan_entitlement_for\"\n"
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
    public void testListFlags() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":[{\"created_at\":\"2024-01-15T09:30:00Z\",\"default_value\":true,\"description\":\"description\",\"feature\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"description\":\"description\",\"feature_type\":\"feature_type\",\"icon\":\"icon\",\"id\":\"id\",\"name\":\"name\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"feature_id\":\"feature_id\",\"flag_type\":\"flag_type\",\"id\":\"id\",\"key\":\"key\",\"last_checked_at\":\"2024-01-15T09:30:00Z\",\"maintainer_id\":\"maintainer_id\",\"name\":\"name\",\"rules\":[{\"condition_groups\":[{\"conditions\":[{\"condition_type\":\"condition_type\",\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"operator\":\"operator\",\"resource_ids\":[\"resource_ids\"],\"resources\":[{\"id\":\"id\",\"name\":\"name\"}],\"rule_id\":\"rule_id\",\"trait_value\":\"trait_value\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"rule_id\":\"rule_id\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"conditions\":[{\"condition_type\":\"condition_type\",\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"operator\":\"operator\",\"resource_ids\":[\"resource_ids\"],\"resources\":[{\"id\":\"id\",\"name\":\"name\"}],\"rule_id\":\"rule_id\",\"trait_value\":\"trait_value\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"name\":\"name\",\"priority\":1,\"rule_type\":\"rule_type\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"value\":true}],\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"params\":{\"feature_id\":\"feature_id\",\"ids\":[\"ids\"],\"limit\":1,\"offset\":1,\"q\":\"q\"}}"));
        ListFlagsResponse response = client.features()
                .listFlags(ListFlagsRequest.builder()
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
                + "      \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"default_value\": true,\n"
                + "      \"description\": \"description\",\n"
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
                + "      \"flag_type\": \"flag_type\",\n"
                + "      \"id\": \"id\",\n"
                + "      \"key\": \"key\",\n"
                + "      \"last_checked_at\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"maintainer_id\": \"maintainer_id\",\n"
                + "      \"name\": \"name\",\n"
                + "      \"rules\": [\n"
                + "        {\n"
                + "          \"condition_groups\": [\n"
                + "            {\n"
                + "              \"conditions\": [\n"
                + "                {\n"
                + "                  \"condition_type\": \"condition_type\",\n"
                + "                  \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "                  \"environment_id\": \"environment_id\",\n"
                + "                  \"id\": \"id\",\n"
                + "                  \"operator\": \"operator\",\n"
                + "                  \"resource_ids\": [\n"
                + "                    \"resource_ids\"\n"
                + "                  ],\n"
                + "                  \"resources\": [\n"
                + "                    {\n"
                + "                      \"id\": \"id\",\n"
                + "                      \"name\": \"name\"\n"
                + "                    }\n"
                + "                  ],\n"
                + "                  \"rule_id\": \"rule_id\",\n"
                + "                  \"trait_value\": \"trait_value\",\n"
                + "                  \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "                }\n"
                + "              ],\n"
                + "              \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "              \"environment_id\": \"environment_id\",\n"
                + "              \"id\": \"id\",\n"
                + "              \"rule_id\": \"rule_id\",\n"
                + "              \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "            }\n"
                + "          ],\n"
                + "          \"conditions\": [\n"
                + "            {\n"
                + "              \"condition_type\": \"condition_type\",\n"
                + "              \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "              \"environment_id\": \"environment_id\",\n"
                + "              \"id\": \"id\",\n"
                + "              \"operator\": \"operator\",\n"
                + "              \"resource_ids\": [\n"
                + "                \"resource_ids\"\n"
                + "              ],\n"
                + "              \"resources\": [\n"
                + "                {\n"
                + "                  \"id\": \"id\",\n"
                + "                  \"name\": \"name\"\n"
                + "                }\n"
                + "              ],\n"
                + "              \"rule_id\": \"rule_id\",\n"
                + "              \"trait_value\": \"trait_value\",\n"
                + "              \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "            }\n"
                + "          ],\n"
                + "          \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "          \"environment_id\": \"environment_id\",\n"
                + "          \"id\": \"id\",\n"
                + "          \"name\": \"name\",\n"
                + "          \"priority\": 1,\n"
                + "          \"rule_type\": \"rule_type\",\n"
                + "          \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "          \"value\": true\n"
                + "        }\n"
                + "      ],\n"
                + "      \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "    }\n"
                + "  ],\n"
                + "  \"params\": {\n"
                + "    \"feature_id\": \"feature_id\",\n"
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
    public void testCreateFlag() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"default_value\":true,\"description\":\"description\",\"feature\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"description\":\"description\",\"event_subtype\":\"event_subtype\",\"feature_type\":\"feature_type\",\"icon\":\"icon\",\"id\":\"id\",\"lifecycle_phase\":\"lifecycle_phase\",\"maintainer_id\":\"maintainer_id\",\"name\":\"name\",\"plural_name\":\"plural_name\",\"singular_name\":\"singular_name\",\"trait_id\":\"trait_id\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"feature_id\":\"feature_id\",\"flag_type\":\"flag_type\",\"id\":\"id\",\"key\":\"key\",\"last_checked_at\":\"2024-01-15T09:30:00Z\",\"maintainer_id\":\"maintainer_id\",\"name\":\"name\",\"rules\":[{\"condition_groups\":[{\"conditions\":[{\"condition_type\":\"condition_type\",\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"operator\":\"operator\",\"resource_ids\":[\"resource_ids\"],\"resources\":[{\"id\":\"id\",\"name\":\"name\"}],\"rule_id\":\"rule_id\",\"trait_value\":\"trait_value\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"rule_id\":\"rule_id\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"conditions\":[{\"condition_type\":\"condition_type\",\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"operator\":\"operator\",\"resource_ids\":[\"resource_ids\"],\"resources\":[{\"id\":\"id\",\"name\":\"name\"}],\"rule_id\":\"rule_id\",\"trait_value\":\"trait_value\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"name\":\"name\",\"priority\":1,\"rule_type\":\"rule_type\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"value\":true}],\"updated_at\":\"2024-01-15T09:30:00Z\"},\"params\":{\"key\":\"value\"}}"));
        CreateFlagResponse response = client.features()
                .createFlag(CreateFlagRequestBody.builder()
                        .defaultValue(true)
                        .description("description")
                        .flagType("boolean")
                        .key("key")
                        .name("name")
                        .build());
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("POST", request.getMethod());
        // Validate request body
        String actualRequestBody = request.getBody().readUtf8();
        String expectedRequestBody = ""
                + "{\n"
                + "  \"default_value\": true,\n"
                + "  \"description\": \"description\",\n"
                + "  \"flag_type\": \"boolean\",\n"
                + "  \"key\": \"key\",\n"
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
                + "    \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"default_value\": true,\n"
                + "    \"description\": \"description\",\n"
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
                + "    \"flag_type\": \"flag_type\",\n"
                + "    \"id\": \"id\",\n"
                + "    \"key\": \"key\",\n"
                + "    \"last_checked_at\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"maintainer_id\": \"maintainer_id\",\n"
                + "    \"name\": \"name\",\n"
                + "    \"rules\": [\n"
                + "      {\n"
                + "        \"condition_groups\": [\n"
                + "          {\n"
                + "            \"conditions\": [\n"
                + "              {\n"
                + "                \"condition_type\": \"condition_type\",\n"
                + "                \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "                \"environment_id\": \"environment_id\",\n"
                + "                \"id\": \"id\",\n"
                + "                \"operator\": \"operator\",\n"
                + "                \"resource_ids\": [\n"
                + "                  \"resource_ids\"\n"
                + "                ],\n"
                + "                \"resources\": [\n"
                + "                  {\n"
                + "                    \"id\": \"id\",\n"
                + "                    \"name\": \"name\"\n"
                + "                  }\n"
                + "                ],\n"
                + "                \"rule_id\": \"rule_id\",\n"
                + "                \"trait_value\": \"trait_value\",\n"
                + "                \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "              }\n"
                + "            ],\n"
                + "            \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"environment_id\": \"environment_id\",\n"
                + "            \"id\": \"id\",\n"
                + "            \"rule_id\": \"rule_id\",\n"
                + "            \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "          }\n"
                + "        ],\n"
                + "        \"conditions\": [\n"
                + "          {\n"
                + "            \"condition_type\": \"condition_type\",\n"
                + "            \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"environment_id\": \"environment_id\",\n"
                + "            \"id\": \"id\",\n"
                + "            \"operator\": \"operator\",\n"
                + "            \"resource_ids\": [\n"
                + "              \"resource_ids\"\n"
                + "            ],\n"
                + "            \"resources\": [\n"
                + "              {\n"
                + "                \"id\": \"id\",\n"
                + "                \"name\": \"name\"\n"
                + "              }\n"
                + "            ],\n"
                + "            \"rule_id\": \"rule_id\",\n"
                + "            \"trait_value\": \"trait_value\",\n"
                + "            \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "          }\n"
                + "        ],\n"
                + "        \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"environment_id\": \"environment_id\",\n"
                + "        \"id\": \"id\",\n"
                + "        \"name\": \"name\",\n"
                + "        \"priority\": 1,\n"
                + "        \"rule_type\": \"rule_type\",\n"
                + "        \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"value\": true\n"
                + "      }\n"
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
    public void testGetFlag() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"default_value\":true,\"description\":\"description\",\"feature\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"description\":\"description\",\"event_subtype\":\"event_subtype\",\"feature_type\":\"feature_type\",\"icon\":\"icon\",\"id\":\"id\",\"lifecycle_phase\":\"lifecycle_phase\",\"maintainer_id\":\"maintainer_id\",\"name\":\"name\",\"plural_name\":\"plural_name\",\"singular_name\":\"singular_name\",\"trait_id\":\"trait_id\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"feature_id\":\"feature_id\",\"flag_type\":\"flag_type\",\"id\":\"id\",\"key\":\"key\",\"last_checked_at\":\"2024-01-15T09:30:00Z\",\"maintainer_id\":\"maintainer_id\",\"name\":\"name\",\"rules\":[{\"condition_groups\":[{\"conditions\":[{\"condition_type\":\"condition_type\",\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"operator\":\"operator\",\"resource_ids\":[\"resource_ids\"],\"resources\":[{\"id\":\"id\",\"name\":\"name\"}],\"rule_id\":\"rule_id\",\"trait_value\":\"trait_value\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"rule_id\":\"rule_id\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"conditions\":[{\"condition_type\":\"condition_type\",\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"operator\":\"operator\",\"resource_ids\":[\"resource_ids\"],\"resources\":[{\"id\":\"id\",\"name\":\"name\"}],\"rule_id\":\"rule_id\",\"trait_value\":\"trait_value\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"name\":\"name\",\"priority\":1,\"rule_type\":\"rule_type\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"value\":true}],\"updated_at\":\"2024-01-15T09:30:00Z\"},\"params\":{\"key\":\"value\"}}"));
        GetFlagResponse response = client.features().getFlag("flag_id");
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
                + "    \"default_value\": true,\n"
                + "    \"description\": \"description\",\n"
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
                + "    \"flag_type\": \"flag_type\",\n"
                + "    \"id\": \"id\",\n"
                + "    \"key\": \"key\",\n"
                + "    \"last_checked_at\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"maintainer_id\": \"maintainer_id\",\n"
                + "    \"name\": \"name\",\n"
                + "    \"rules\": [\n"
                + "      {\n"
                + "        \"condition_groups\": [\n"
                + "          {\n"
                + "            \"conditions\": [\n"
                + "              {\n"
                + "                \"condition_type\": \"condition_type\",\n"
                + "                \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "                \"environment_id\": \"environment_id\",\n"
                + "                \"id\": \"id\",\n"
                + "                \"operator\": \"operator\",\n"
                + "                \"resource_ids\": [\n"
                + "                  \"resource_ids\"\n"
                + "                ],\n"
                + "                \"resources\": [\n"
                + "                  {\n"
                + "                    \"id\": \"id\",\n"
                + "                    \"name\": \"name\"\n"
                + "                  }\n"
                + "                ],\n"
                + "                \"rule_id\": \"rule_id\",\n"
                + "                \"trait_value\": \"trait_value\",\n"
                + "                \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "              }\n"
                + "            ],\n"
                + "            \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"environment_id\": \"environment_id\",\n"
                + "            \"id\": \"id\",\n"
                + "            \"rule_id\": \"rule_id\",\n"
                + "            \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "          }\n"
                + "        ],\n"
                + "        \"conditions\": [\n"
                + "          {\n"
                + "            \"condition_type\": \"condition_type\",\n"
                + "            \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"environment_id\": \"environment_id\",\n"
                + "            \"id\": \"id\",\n"
                + "            \"operator\": \"operator\",\n"
                + "            \"resource_ids\": [\n"
                + "              \"resource_ids\"\n"
                + "            ],\n"
                + "            \"resources\": [\n"
                + "              {\n"
                + "                \"id\": \"id\",\n"
                + "                \"name\": \"name\"\n"
                + "              }\n"
                + "            ],\n"
                + "            \"rule_id\": \"rule_id\",\n"
                + "            \"trait_value\": \"trait_value\",\n"
                + "            \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "          }\n"
                + "        ],\n"
                + "        \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"environment_id\": \"environment_id\",\n"
                + "        \"id\": \"id\",\n"
                + "        \"name\": \"name\",\n"
                + "        \"priority\": 1,\n"
                + "        \"rule_type\": \"rule_type\",\n"
                + "        \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"value\": true\n"
                + "      }\n"
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
    public void testUpdateFlag() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"default_value\":true,\"description\":\"description\",\"feature\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"description\":\"description\",\"event_subtype\":\"event_subtype\",\"feature_type\":\"feature_type\",\"icon\":\"icon\",\"id\":\"id\",\"lifecycle_phase\":\"lifecycle_phase\",\"maintainer_id\":\"maintainer_id\",\"name\":\"name\",\"plural_name\":\"plural_name\",\"singular_name\":\"singular_name\",\"trait_id\":\"trait_id\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"feature_id\":\"feature_id\",\"flag_type\":\"flag_type\",\"id\":\"id\",\"key\":\"key\",\"last_checked_at\":\"2024-01-15T09:30:00Z\",\"maintainer_id\":\"maintainer_id\",\"name\":\"name\",\"rules\":[{\"condition_groups\":[{\"conditions\":[{\"condition_type\":\"condition_type\",\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"operator\":\"operator\",\"resource_ids\":[\"resource_ids\"],\"resources\":[{\"id\":\"id\",\"name\":\"name\"}],\"rule_id\":\"rule_id\",\"trait_value\":\"trait_value\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"rule_id\":\"rule_id\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"conditions\":[{\"condition_type\":\"condition_type\",\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"operator\":\"operator\",\"resource_ids\":[\"resource_ids\"],\"resources\":[{\"id\":\"id\",\"name\":\"name\"}],\"rule_id\":\"rule_id\",\"trait_value\":\"trait_value\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"name\":\"name\",\"priority\":1,\"rule_type\":\"rule_type\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"value\":true}],\"updated_at\":\"2024-01-15T09:30:00Z\"},\"params\":{\"key\":\"value\"}}"));
        UpdateFlagResponse response = client.features()
                .updateFlag(
                        "flag_id",
                        CreateFlagRequestBody.builder()
                                .defaultValue(true)
                                .description("description")
                                .flagType("boolean")
                                .key("key")
                                .name("name")
                                .build());
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("PUT", request.getMethod());
        // Validate request body
        String actualRequestBody = request.getBody().readUtf8();
        String expectedRequestBody = ""
                + "{\n"
                + "  \"default_value\": true,\n"
                + "  \"description\": \"description\",\n"
                + "  \"flag_type\": \"boolean\",\n"
                + "  \"key\": \"key\",\n"
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
                + "    \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"default_value\": true,\n"
                + "    \"description\": \"description\",\n"
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
                + "    \"flag_type\": \"flag_type\",\n"
                + "    \"id\": \"id\",\n"
                + "    \"key\": \"key\",\n"
                + "    \"last_checked_at\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"maintainer_id\": \"maintainer_id\",\n"
                + "    \"name\": \"name\",\n"
                + "    \"rules\": [\n"
                + "      {\n"
                + "        \"condition_groups\": [\n"
                + "          {\n"
                + "            \"conditions\": [\n"
                + "              {\n"
                + "                \"condition_type\": \"condition_type\",\n"
                + "                \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "                \"environment_id\": \"environment_id\",\n"
                + "                \"id\": \"id\",\n"
                + "                \"operator\": \"operator\",\n"
                + "                \"resource_ids\": [\n"
                + "                  \"resource_ids\"\n"
                + "                ],\n"
                + "                \"resources\": [\n"
                + "                  {\n"
                + "                    \"id\": \"id\",\n"
                + "                    \"name\": \"name\"\n"
                + "                  }\n"
                + "                ],\n"
                + "                \"rule_id\": \"rule_id\",\n"
                + "                \"trait_value\": \"trait_value\",\n"
                + "                \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "              }\n"
                + "            ],\n"
                + "            \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"environment_id\": \"environment_id\",\n"
                + "            \"id\": \"id\",\n"
                + "            \"rule_id\": \"rule_id\",\n"
                + "            \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "          }\n"
                + "        ],\n"
                + "        \"conditions\": [\n"
                + "          {\n"
                + "            \"condition_type\": \"condition_type\",\n"
                + "            \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"environment_id\": \"environment_id\",\n"
                + "            \"id\": \"id\",\n"
                + "            \"operator\": \"operator\",\n"
                + "            \"resource_ids\": [\n"
                + "              \"resource_ids\"\n"
                + "            ],\n"
                + "            \"resources\": [\n"
                + "              {\n"
                + "                \"id\": \"id\",\n"
                + "                \"name\": \"name\"\n"
                + "              }\n"
                + "            ],\n"
                + "            \"rule_id\": \"rule_id\",\n"
                + "            \"trait_value\": \"trait_value\",\n"
                + "            \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "          }\n"
                + "        ],\n"
                + "        \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"environment_id\": \"environment_id\",\n"
                + "        \"id\": \"id\",\n"
                + "        \"name\": \"name\",\n"
                + "        \"priority\": 1,\n"
                + "        \"rule_type\": \"rule_type\",\n"
                + "        \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"value\": true\n"
                + "      }\n"
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
    public void testDeleteFlag() throws Exception {
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody("{\"data\":{\"deleted\":true},\"params\":{\"key\":\"value\"}}"));
        DeleteFlagResponse response = client.features().deleteFlag("flag_id");
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
    public void testUpdateFlagRules() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":{\"flag\":{\"created_at\":\"2024-01-15T09:30:00Z\",\"default_value\":true,\"description\":\"description\",\"feature_id\":\"feature_id\",\"flag_type\":\"flag_type\",\"id\":\"id\",\"key\":\"key\",\"maintainer_id\":\"maintainer_id\",\"name\":\"name\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"rules\":[{\"condition_groups\":[{\"conditions\":[{\"condition_type\":\"condition_type\",\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"operator\":\"operator\",\"resource_ids\":[\"resource_ids\"],\"resources\":[{\"id\":\"id\",\"name\":\"name\"}],\"rule_id\":\"rule_id\",\"trait_value\":\"trait_value\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"rule_id\":\"rule_id\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"conditions\":[{\"condition_type\":\"condition_type\",\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"operator\":\"operator\",\"resource_ids\":[\"resource_ids\"],\"resources\":[{\"id\":\"id\",\"name\":\"name\"}],\"rule_id\":\"rule_id\",\"trait_value\":\"trait_value\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"created_at\":\"2024-01-15T09:30:00Z\",\"environment_id\":\"environment_id\",\"id\":\"id\",\"name\":\"name\",\"priority\":1,\"rule_type\":\"rule_type\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"value\":true}]},\"params\":{\"key\":\"value\"}}"));
        UpdateFlagRulesResponse response = client.features()
                .updateFlagRules(
                        "flag_id",
                        UpdateFlagRulesRequestBody.builder()
                                .rules(Arrays.asList(CreateOrUpdateRuleRequestBody.builder()
                                        .conditionGroups(Arrays.asList(CreateOrUpdateConditionGroupRequestBody.builder()
                                                .conditions(Arrays.asList(CreateOrUpdateConditionRequestBody.builder()
                                                        .conditionType(
                                                                CreateOrUpdateConditionRequestBodyConditionType.COMPANY)
                                                        .operator(CreateOrUpdateConditionRequestBodyOperator.EQ)
                                                        .resourceIds(Arrays.asList("resource_ids"))
                                                        .build()))
                                                .build()))
                                        .conditions(Arrays.asList(CreateOrUpdateConditionRequestBody.builder()
                                                .conditionType(CreateOrUpdateConditionRequestBodyConditionType.COMPANY)
                                                .operator(CreateOrUpdateConditionRequestBodyOperator.EQ)
                                                .resourceIds(Arrays.asList("resource_ids"))
                                                .build()))
                                        .name("name")
                                        .priority(1)
                                        .value(true)
                                        .build()))
                                .build());
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("PUT", request.getMethod());
        // Validate request body
        String actualRequestBody = request.getBody().readUtf8();
        String expectedRequestBody = ""
                + "{\n"
                + "  \"rules\": [\n"
                + "    {\n"
                + "      \"condition_groups\": [\n"
                + "        {\n"
                + "          \"conditions\": [\n"
                + "            {\n"
                + "              \"condition_type\": \"company\",\n"
                + "              \"operator\": \"eq\",\n"
                + "              \"resource_ids\": [\n"
                + "                \"resource_ids\"\n"
                + "              ]\n"
                + "            }\n"
                + "          ]\n"
                + "        }\n"
                + "      ],\n"
                + "      \"conditions\": [\n"
                + "        {\n"
                + "          \"condition_type\": \"company\",\n"
                + "          \"operator\": \"eq\",\n"
                + "          \"resource_ids\": [\n"
                + "            \"resource_ids\"\n"
                + "          ]\n"
                + "        }\n"
                + "      ],\n"
                + "      \"name\": \"name\",\n"
                + "      \"priority\": 1,\n"
                + "      \"value\": true\n"
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
                + "    \"flag\": {\n"
                + "      \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"default_value\": true,\n"
                + "      \"description\": \"description\",\n"
                + "      \"feature_id\": \"feature_id\",\n"
                + "      \"flag_type\": \"flag_type\",\n"
                + "      \"id\": \"id\",\n"
                + "      \"key\": \"key\",\n"
                + "      \"maintainer_id\": \"maintainer_id\",\n"
                + "      \"name\": \"name\",\n"
                + "      \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "    },\n"
                + "    \"rules\": [\n"
                + "      {\n"
                + "        \"condition_groups\": [\n"
                + "          {\n"
                + "            \"conditions\": [\n"
                + "              {\n"
                + "                \"condition_type\": \"condition_type\",\n"
                + "                \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "                \"environment_id\": \"environment_id\",\n"
                + "                \"id\": \"id\",\n"
                + "                \"operator\": \"operator\",\n"
                + "                \"resource_ids\": [\n"
                + "                  \"resource_ids\"\n"
                + "                ],\n"
                + "                \"resources\": [\n"
                + "                  {\n"
                + "                    \"id\": \"id\",\n"
                + "                    \"name\": \"name\"\n"
                + "                  }\n"
                + "                ],\n"
                + "                \"rule_id\": \"rule_id\",\n"
                + "                \"trait_value\": \"trait_value\",\n"
                + "                \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "              }\n"
                + "            ],\n"
                + "            \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"environment_id\": \"environment_id\",\n"
                + "            \"id\": \"id\",\n"
                + "            \"rule_id\": \"rule_id\",\n"
                + "            \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "          }\n"
                + "        ],\n"
                + "        \"conditions\": [\n"
                + "          {\n"
                + "            \"condition_type\": \"condition_type\",\n"
                + "            \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "            \"environment_id\": \"environment_id\",\n"
                + "            \"id\": \"id\",\n"
                + "            \"operator\": \"operator\",\n"
                + "            \"resource_ids\": [\n"
                + "              \"resource_ids\"\n"
                + "            ],\n"
                + "            \"resources\": [\n"
                + "              {\n"
                + "                \"id\": \"id\",\n"
                + "                \"name\": \"name\"\n"
                + "              }\n"
                + "            ],\n"
                + "            \"rule_id\": \"rule_id\",\n"
                + "            \"trait_value\": \"trait_value\",\n"
                + "            \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "          }\n"
                + "        ],\n"
                + "        \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"environment_id\": \"environment_id\",\n"
                + "        \"id\": \"id\",\n"
                + "        \"name\": \"name\",\n"
                + "        \"priority\": 1,\n"
                + "        \"rule_type\": \"rule_type\",\n"
                + "        \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "        \"value\": true\n"
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
    public void testCheckFlag() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":{\"company_id\":\"company_id\",\"error\":\"error\",\"feature_allocation\":1,\"feature_usage\":1,\"feature_usage_event\":\"feature_usage_event\",\"feature_usage_period\":\"feature_usage_period\",\"feature_usage_reset_at\":\"2024-01-15T09:30:00Z\",\"flag\":\"flag\",\"flag_id\":\"flag_id\",\"reason\":\"reason\",\"rule_id\":\"rule_id\",\"rule_type\":\"rule_type\",\"user_id\":\"user_id\",\"value\":true},\"params\":{\"key\":\"value\"}}"));
        CheckFlagResponse response = client.features()
                .checkFlag("key", CheckFlagRequestBody.builder().build());
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("POST", request.getMethod());
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
                + "    \"error\": \"error\",\n"
                + "    \"feature_allocation\": 1,\n"
                + "    \"feature_usage\": 1,\n"
                + "    \"feature_usage_event\": \"feature_usage_event\",\n"
                + "    \"feature_usage_period\": \"feature_usage_period\",\n"
                + "    \"feature_usage_reset_at\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"flag\": \"flag\",\n"
                + "    \"flag_id\": \"flag_id\",\n"
                + "    \"reason\": \"reason\",\n"
                + "    \"rule_id\": \"rule_id\",\n"
                + "    \"rule_type\": \"rule_type\",\n"
                + "    \"user_id\": \"user_id\",\n"
                + "    \"value\": true\n"
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
    public void testCheckFlags() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":{\"flags\":[{\"flag\":\"flag\",\"reason\":\"reason\",\"value\":true}]},\"params\":{\"key\":\"value\"}}"));
        CheckFlagsResponse response =
                client.features().checkFlags(CheckFlagRequestBody.builder().build());
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("POST", request.getMethod());
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
                + "    \"flags\": [\n"
                + "      {\n"
                + "        \"flag\": \"flag\",\n"
                + "        \"reason\": \"reason\",\n"
                + "        \"value\": true\n"
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
    public void testCountFlags() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"data\":{\"count\":1},\"params\":{\"feature_id\":\"feature_id\",\"ids\":[\"ids\"],\"limit\":1,\"offset\":1,\"q\":\"q\"}}"));
        CountFlagsResponse response = client.features()
                .countFlags(CountFlagsRequest.builder()
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
}
