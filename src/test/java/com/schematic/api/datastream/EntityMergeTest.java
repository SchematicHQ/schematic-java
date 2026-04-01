package com.schematic.api.datastream;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.schematic.api.core.ObjectMappers;
import com.schematic.api.types.RulesengineCompany;
import com.schematic.api.types.RulesengineUser;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class EntityMergeTest {

    private final ObjectMapper objectMapper = ObjectMappers.JSON_MAPPER;

    // --- Company partial merge tests ---

    @Test
    void partialCompany_mergesKeysAdditively() {
        Map<String, String> existingKeys = new HashMap<>();
        existingKeys.put("customer_id", "cust-1");
        existingKeys.put("org_id", "org-1");

        RulesengineCompany existing = buildCompany("comp-1", existingKeys);

        ObjectNode partial = objectMapper.createObjectNode();
        partial.put("id", "comp-1");
        ObjectNode newKeys = objectMapper.createObjectNode();
        newKeys.put("customer_id", "cust-updated");
        newKeys.put("external_id", "ext-1");
        partial.set("keys", newKeys);

        RulesengineCompany merged = EntityMerge.partialCompany(existing, partial);

        // Original key preserved, updated key changed, new key added
        assertEquals("cust-updated", merged.getKeys().get("customer_id"));
        assertEquals("org-1", merged.getKeys().get("org_id"));
        assertEquals("ext-1", merged.getKeys().get("external_id"));
    }

    @Test
    void partialCompany_mergesCreditBalancesAdditively() {
        Map<String, Double> existingBalances = new HashMap<>();
        existingBalances.put("credits_a", 100.0);
        existingBalances.put("credits_b", 50.0);

        RulesengineCompany existing = RulesengineCompany.builder()
                .accountId("acc_1")
                .environmentId("env_1")
                .id("comp-1")
                .keys(Collections.singletonMap("id", "comp-1"))
                .traits(Collections.emptyList())
                .metrics(Collections.emptyList())
                .rules(Collections.emptyList())
                .billingProductIds(Collections.emptyList())
                .creditBalances(existingBalances)
                .planIds(Collections.emptyList())
                .planVersionIds(Collections.emptyList())
                .build();

        ObjectNode partial = objectMapper.createObjectNode();
        partial.put("id", "comp-1");
        ObjectNode newBalances = objectMapper.createObjectNode();
        newBalances.put("credits_a", 75.0);
        newBalances.put("credits_c", 200.0);
        partial.set("credit_balances", newBalances);

        RulesengineCompany merged = EntityMerge.partialCompany(existing, partial);

        assertEquals(75.0, merged.getCreditBalances().get("credits_a"));
        assertEquals(50.0, merged.getCreditBalances().get("credits_b"));
        assertEquals(200.0, merged.getCreditBalances().get("credits_c"));
    }

    @Test
    void partialCompany_replacesOtherFields() {
        RulesengineCompany existing = buildCompany("comp-1", Collections.singletonMap("id", "comp-1"));

        ObjectNode partial = objectMapper.createObjectNode();
        partial.put("id", "comp-1");
        partial.put("account_id", "acc_new");

        RulesengineCompany merged = EntityMerge.partialCompany(existing, partial);

        assertEquals("acc_new", merged.getAccountId());
        // Untouched fields preserved
        assertEquals("env_1", merged.getEnvironmentId());
    }

    @Test
    void partialCompany_upsertsMetrics() {
        RulesengineCompany existing = buildCompany("comp-1", Collections.singletonMap("id", "comp-1"));

        // Add initial metrics via a full update
        ObjectNode fullData = (ObjectNode) objectMapper.valueToTree(existing);
        ArrayNode existingMetrics = objectMapper.createArrayNode();
        ObjectNode metric1 = objectMapper.createObjectNode();
        metric1.put("event_subtype", "api_calls");
        metric1.put("period", "monthly");
        metric1.put("month_reset", "first");
        metric1.put("value", 10);
        existingMetrics.add(metric1);
        fullData.set("metrics", existingMetrics);
        existing = objectMapper.convertValue(fullData, RulesengineCompany.class);

        // Partial update with matching metric (should replace) and new metric (should append)
        ObjectNode partial = objectMapper.createObjectNode();
        partial.put("id", "comp-1");
        ArrayNode partialMetrics = objectMapper.createArrayNode();
        ObjectNode updatedMetric = objectMapper.createObjectNode();
        updatedMetric.put("event_subtype", "api_calls");
        updatedMetric.put("period", "monthly");
        updatedMetric.put("month_reset", "first");
        updatedMetric.put("value", 25);
        partialMetrics.add(updatedMetric);
        ObjectNode newMetric = objectMapper.createObjectNode();
        newMetric.put("event_subtype", "page_views");
        newMetric.put("period", "daily");
        newMetric.put("month_reset", "");
        newMetric.put("value", 5);
        partialMetrics.add(newMetric);
        partial.set("metrics", partialMetrics);

        RulesengineCompany merged = EntityMerge.partialCompany(existing, partial);

        assertEquals(2, merged.getMetrics().size());
    }

    // --- User partial merge tests ---

    @Test
    void partialUser_mergesKeysAdditively() {
        Map<String, String> existingKeys = new HashMap<>();
        existingKeys.put("email", "old@example.com");
        existingKeys.put("user_id", "u-1");

        RulesengineUser existing = buildUser("user-1", existingKeys);

        ObjectNode partial = objectMapper.createObjectNode();
        partial.put("id", "user-1");
        ObjectNode newKeys = objectMapper.createObjectNode();
        newKeys.put("email", "new@example.com");
        newKeys.put("external_id", "ext-1");
        partial.set("keys", newKeys);

        RulesengineUser merged = EntityMerge.partialUser(existing, partial);

        assertEquals("new@example.com", merged.getKeys().get("email"));
        assertEquals("u-1", merged.getKeys().get("user_id"));
        assertEquals("ext-1", merged.getKeys().get("external_id"));
    }

    @Test
    void partialUser_replacesOtherFields() {
        RulesengineUser existing = buildUser("user-1", Collections.singletonMap("id", "user-1"));

        ObjectNode partial = objectMapper.createObjectNode();
        partial.put("id", "user-1");
        partial.put("account_id", "acc_new");

        RulesengineUser merged = EntityMerge.partialUser(existing, partial);

        assertEquals("acc_new", merged.getAccountId());
        assertEquals("env_1", merged.getEnvironmentId());
    }

    @Test
    void partialUser_onlyAppliesPresentFields() {
        Map<String, String> existingKeys = new HashMap<>();
        existingKeys.put("email", "test@example.com");

        RulesengineUser existing = buildUser("user-1", existingKeys);

        // Partial with only account_id — keys should be untouched
        ObjectNode partial = objectMapper.createObjectNode();
        partial.put("id", "user-1");
        partial.put("account_id", "acc_2");

        RulesengineUser merged = EntityMerge.partialUser(existing, partial);

        assertEquals("acc_2", merged.getAccountId());
        assertEquals("test@example.com", merged.getKeys().get("email"));
    }

    // --- Helpers ---

    private RulesengineCompany buildCompany(String id, Map<String, String> keys) {
        return RulesengineCompany.builder()
                .accountId("acc_1")
                .environmentId("env_1")
                .id(id)
                .keys(keys)
                .traits(Collections.emptyList())
                .metrics(Collections.emptyList())
                .rules(Collections.emptyList())
                .billingProductIds(Collections.emptyList())
                .creditBalances(Collections.emptyMap())
                .planIds(Collections.emptyList())
                .planVersionIds(Collections.emptyList())
                .build();
    }

    private RulesengineUser buildUser(String id, Map<String, String> keys) {
        return RulesengineUser.builder()
                .accountId("acc_1")
                .environmentId("env_1")
                .id(id)
                .keys(keys)
                .traits(Collections.emptyList())
                .rules(Collections.emptyList())
                .build();
    }
}
