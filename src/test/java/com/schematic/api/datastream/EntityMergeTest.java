package com.schematic.api.datastream;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.schematic.api.core.ObjectMappers;
import com.schematic.api.types.RulesengineCompany;
import com.schematic.api.types.RulesengineEntitlementValueType;
import com.schematic.api.types.RulesengineFeatureEntitlement;
import com.schematic.api.types.RulesengineRule;
import com.schematic.api.types.RulesengineRuleRuleType;
import com.schematic.api.types.RulesengineTrait;
import com.schematic.api.types.RulesengineUser;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
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

    @Test
    void partialCompany_onlyTraits() {
        RulesengineCompany existing = RulesengineCompany.builder()
                .accountId("acc_1")
                .environmentId("env_1")
                .id("comp-1")
                .keys(Collections.singletonMap("org_id", "org-1"))
                .traits(Collections.singletonList(
                        RulesengineTrait.builder().value("old-trait").build()))
                .metrics(Collections.emptyList())
                .rules(Collections.emptyList())
                .billingProductIds(Collections.emptyList())
                .creditBalances(Collections.emptyMap())
                .planIds(Collections.emptyList())
                .planVersionIds(Collections.emptyList())
                .build();

        ObjectNode partial = objectMapper.createObjectNode();
        partial.put("id", "comp-1");
        ArrayNode newTraits = objectMapper.createArrayNode();
        ObjectNode trait = objectMapper.createObjectNode();
        trait.put("value", "new-trait");
        newTraits.add(trait);
        partial.set("traits", newTraits);

        RulesengineCompany merged = EntityMerge.partialCompany(existing, partial);

        assertEquals(1, merged.getTraits().size());
        assertEquals("new-trait", merged.getTraits().get(0).getValue());
        assertEquals("org-1", merged.getKeys().get("org_id"));
        assertEquals("acc_1", merged.getAccountId());
    }

    @Test
    void partialCompany_overwritesCreditBalance() {
        Map<String, Double> existingBalances = new HashMap<>();
        existingBalances.put("credit-1", 100.0);

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
        newBalances.put("credit-1", 50.0);
        partial.set("credit_balances", newBalances);

        RulesengineCompany merged = EntityMerge.partialCompany(existing, partial);

        assertEquals(50.0, merged.getCreditBalances().get("credit-1"));
    }

    @Test
    void partialCompany_emptyEntitlementsClearsExisting() {
        List<RulesengineFeatureEntitlement> existingEntitlements =
                Collections.singletonList(RulesengineFeatureEntitlement.builder()
                        .featureId("feat-1")
                        .featureKey("feat-key-1")
                        .valueType(RulesengineEntitlementValueType.BOOLEAN)
                        .build());

        RulesengineCompany existing = RulesengineCompany.builder()
                .accountId("acc_1")
                .environmentId("env_1")
                .id("comp-1")
                .keys(Collections.singletonMap("id", "comp-1"))
                .traits(Collections.emptyList())
                .metrics(Collections.emptyList())
                .rules(Collections.emptyList())
                .billingProductIds(Collections.emptyList())
                .creditBalances(Collections.emptyMap())
                .planIds(Collections.emptyList())
                .planVersionIds(Collections.emptyList())
                .entitlements(existingEntitlements)
                .build();

        ObjectNode partial = objectMapper.createObjectNode();
        partial.put("id", "comp-1");
        partial.set("entitlements", objectMapper.createArrayNode());

        RulesengineCompany merged = EntityMerge.partialCompany(existing, partial);

        assertTrue(merged.getEntitlements().isPresent(), "entitlements should be present");
        assertTrue(merged.getEntitlements().get().isEmpty(), "entitlements should be empty after clearing");
    }

    @Test
    void partialCompany_toleratesMissingId() {
        // Wire shape from the API: data is wrapped under the field name,
        // no id at the top level. Cache lookup happens at the handler level
        // using entityId from the envelope, not from the data payload.
        RulesengineCompany existing = buildCompany("comp-1", Collections.singletonMap("id", "comp-1"));

        ObjectNode partial = objectMapper.createObjectNode();
        partial.put("account_id", "acc_new");

        RulesengineCompany merged = EntityMerge.partialCompany(existing, partial);

        // Existing id is preserved; account_id is updated.
        assertEquals("comp-1", merged.getId());
        assertEquals("acc_new", merged.getAccountId());
    }

    @Test
    void partialCompany_doesNotMutateOriginal() {
        Map<String, String> existingKeys = new HashMap<>();
        existingKeys.put("org_id", "org-1");

        RulesengineCompany existing = buildCompany("comp-1", existingKeys);
        String originalAccountId = existing.getAccountId();

        ObjectNode partial = objectMapper.createObjectNode();
        partial.put("id", "comp-1");
        partial.put("account_id", "acc_new");
        ObjectNode newKeys = objectMapper.createObjectNode();
        newKeys.put("org_id", "org-updated");
        newKeys.put("new_key", "new-val");
        partial.set("keys", newKeys);

        EntityMerge.partialCompany(existing, partial);

        assertEquals(originalAccountId, existing.getAccountId());
        assertEquals("org-1", existing.getKeys().get("org_id"));
        assertNull(existing.getKeys().get("new_key"));
    }

    @Test
    void partialCompany_replacesRules() {
        RulesengineRule originalRule = RulesengineRule.builder()
                .accountId("acc_1")
                .environmentId("env_1")
                .id("rule-1")
                .name("Original Rule")
                .priority(1)
                .ruleType(RulesengineRuleRuleType.GLOBAL_OVERRIDE)
                .value(true)
                .build();

        RulesengineCompany existing = RulesengineCompany.builder()
                .accountId("acc_1")
                .environmentId("env_1")
                .id("comp-1")
                .keys(Collections.singletonMap("id", "comp-1"))
                .traits(Collections.emptyList())
                .metrics(Collections.emptyList())
                .rules(Collections.singletonList(originalRule))
                .billingProductIds(Collections.emptyList())
                .creditBalances(Collections.emptyMap())
                .planIds(Collections.emptyList())
                .planVersionIds(Collections.emptyList())
                .build();

        ObjectNode partial = objectMapper.createObjectNode();
        partial.put("id", "comp-1");
        ArrayNode newRules = objectMapper.createArrayNode();
        ObjectNode rule = objectMapper.createObjectNode();
        rule.put("account_id", "acc_1");
        rule.put("environment_id", "env_1");
        rule.put("id", "rule-2");
        rule.put("name", "Replacement Rule");
        rule.put("priority", 2);
        rule.put("rule_type", "global_override");
        rule.put("value", false);
        newRules.add(rule);
        partial.set("rules", newRules);

        RulesengineCompany merged = EntityMerge.partialCompany(existing, partial);

        assertEquals(1, merged.getRules().size());
        assertEquals("rule-2", merged.getRules().get(0).getId());
        assertEquals("Replacement Rule", merged.getRules().get(0).getName());
        assertEquals(1, existing.getRules().size());
        assertEquals("rule-1", existing.getRules().get(0).getId());
    }

    @Test
    void partialCompany_fullEntityPartialMessage() {
        RulesengineCompany existing = buildCompany("comp-1", Collections.singletonMap("id", "comp-1"));

        ObjectNode partial = objectMapper.createObjectNode();
        partial.put("id", "comp-1");
        partial.put("account_id", "acc_new");
        partial.put("environment_id", "env_new");

        ObjectNode newKeys = objectMapper.createObjectNode();
        newKeys.put("org_id", "org-new");
        partial.set("keys", newKeys);

        ArrayNode newTraits = objectMapper.createArrayNode();
        ObjectNode trait = objectMapper.createObjectNode();
        trait.put("value", "full-trait");
        newTraits.add(trait);
        partial.set("traits", newTraits);

        ObjectNode newBalances = objectMapper.createObjectNode();
        newBalances.put("credit-x", 999.0);
        partial.set("credit_balances", newBalances);

        ArrayNode newRules = objectMapper.createArrayNode();
        ObjectNode rule = objectMapper.createObjectNode();
        rule.put("account_id", "acc_new");
        rule.put("environment_id", "env_new");
        rule.put("id", "rule-full");
        rule.put("name", "Full Rule");
        rule.put("priority", 1);
        rule.put("rule_type", "global_override");
        rule.put("value", true);
        newRules.add(rule);
        partial.set("rules", newRules);

        RulesengineCompany merged = EntityMerge.partialCompany(existing, partial);

        assertEquals("acc_new", merged.getAccountId());
        assertEquals("env_new", merged.getEnvironmentId());
        assertEquals("org-new", merged.getKeys().get("org_id"));
        assertEquals(1, merged.getTraits().size());
        assertEquals("full-trait", merged.getTraits().get(0).getValue());
        assertEquals(999.0, merged.getCreditBalances().get("credit-x"));
        assertEquals(1, merged.getRules().size());
        assertEquals("rule-full", merged.getRules().get(0).getId());
    }

    // --- User partial merge tests (additional) ---

    @Test
    void partialUser_onlyTraits() {
        Map<String, String> existingKeys = new HashMap<>();
        existingKeys.put("email", "test@example.com");

        RulesengineUser existing = RulesengineUser.builder()
                .accountId("acc_1")
                .environmentId("env_1")
                .id("user-1")
                .keys(existingKeys)
                .traits(Collections.singletonList(
                        RulesengineTrait.builder().value("old-trait").build()))
                .rules(Collections.emptyList())
                .build();

        ObjectNode partial = objectMapper.createObjectNode();
        partial.put("id", "user-1");
        ArrayNode newTraits = objectMapper.createArrayNode();
        ObjectNode trait = objectMapper.createObjectNode();
        trait.put("value", "new-trait");
        newTraits.add(trait);
        partial.set("traits", newTraits);

        RulesengineUser merged = EntityMerge.partialUser(existing, partial);

        assertEquals(1, merged.getTraits().size());
        assertEquals("new-trait", merged.getTraits().get(0).getValue());
        assertEquals("test@example.com", merged.getKeys().get("email"));
    }

    @Test
    void partialUser_toleratesMissingId() {
        // Wire shape from the API: data is wrapped under the field name,
        // no id at the top level. Cache lookup happens at the handler level
        // using entityId from the envelope, not from the data payload.
        RulesengineUser existing = buildUser("user-1", Collections.singletonMap("id", "user-1"));

        ObjectNode partial = objectMapper.createObjectNode();
        partial.put("account_id", "acc_new");

        RulesengineUser merged = EntityMerge.partialUser(existing, partial);

        // Existing id is preserved; account_id is updated.
        assertEquals("user-1", merged.getId());
        assertEquals("acc_new", merged.getAccountId());
    }

    @Test
    void partialUser_doesNotMutateOriginal() {
        Map<String, String> existingKeys = new HashMap<>();
        existingKeys.put("email", "test@example.com");

        RulesengineUser existing = buildUser("user-1", existingKeys);
        String originalAccountId = existing.getAccountId();

        ObjectNode partial = objectMapper.createObjectNode();
        partial.put("id", "user-1");
        partial.put("account_id", "acc_new");
        ObjectNode newKeys = objectMapper.createObjectNode();
        newKeys.put("email", "updated@example.com");
        newKeys.put("new_key", "new-val");
        partial.set("keys", newKeys);

        EntityMerge.partialUser(existing, partial);

        assertEquals(originalAccountId, existing.getAccountId());
        assertEquals("test@example.com", existing.getKeys().get("email"));
        assertNull(existing.getKeys().get("new_key"));
    }

    @Test
    void partialUser_fullEntityPartialMessage() {
        RulesengineUser existing = buildUser("user-1", Collections.singletonMap("email", "old@example.com"));

        ObjectNode partial = objectMapper.createObjectNode();
        partial.put("id", "user-1");
        partial.put("account_id", "acc_new");
        partial.put("environment_id", "env_new");

        ObjectNode newKeys = objectMapper.createObjectNode();
        newKeys.put("email", "new@example.com");
        newKeys.put("user_id", "u-new");
        partial.set("keys", newKeys);

        ArrayNode newTraits = objectMapper.createArrayNode();
        ObjectNode trait = objectMapper.createObjectNode();
        trait.put("value", "full-trait");
        newTraits.add(trait);
        partial.set("traits", newTraits);

        ArrayNode newRules = objectMapper.createArrayNode();
        ObjectNode rule = objectMapper.createObjectNode();
        rule.put("account_id", "acc_new");
        rule.put("environment_id", "env_new");
        rule.put("id", "rule-u1");
        rule.put("name", "User Rule");
        rule.put("priority", 1);
        rule.put("rule_type", "global_override");
        rule.put("value", true);
        newRules.add(rule);
        partial.set("rules", newRules);

        RulesengineUser merged = EntityMerge.partialUser(existing, partial);

        assertEquals("acc_new", merged.getAccountId());
        assertEquals("env_new", merged.getEnvironmentId());
        assertEquals("new@example.com", merged.getKeys().get("email"));
        assertEquals("u-new", merged.getKeys().get("user_id"));
        assertEquals(1, merged.getTraits().size());
        assertEquals("full-trait", merged.getTraits().get(0).getValue());
        assertEquals(1, merged.getRules().size());
        assertEquals("rule-u1", merged.getRules().get(0).getId());
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
