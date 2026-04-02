package com.schematic.api.datastream;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.schematic.api.core.ObjectMappers;
import com.schematic.api.types.RulesengineCompany;
import com.schematic.api.types.RulesengineUser;
import java.util.Iterator;
import java.util.Map;

/**
 * Merge helpers for partial datastream updates.
 *
 * <p>Merge strategy:
 * <ul>
 *   <li>Company: additive merge for {@code keys} and {@code credit_balances},
 *       upsert for {@code metrics}, replace for all other fields</li>
 *   <li>User: additive merge for {@code keys}, replace for all other fields</li>
 * </ul>
 */
final class EntityMerge {

    private static final ObjectMapper MAPPER = ObjectMappers.JSON_MAPPER;

    private EntityMerge() {}

    /**
     * Merges a partial company update into an existing company.
     * Only fields present in the partial are applied.
     *
     * @param existing the current cached company
     * @param partial  the partial update as raw JSON from the datastream
     * @return a new merged company
     */
    static RulesengineCompany partialCompany(RulesengineCompany existing, JsonNode partial) {
        // Serialize existing to a mutable JSON tree
        ObjectNode base = (ObjectNode) MAPPER.valueToTree(existing);

        Iterator<Map.Entry<String, JsonNode>> fields = partial.fields();
        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> field = fields.next();
            String key = field.getKey();
            JsonNode value = field.getValue();

            switch (key) {
                case "keys":
                case "credit_balances":
                    // Additive merge: overlay partial keys onto existing
                    mergeObject(base, key, value);
                    break;
                case "metrics":
                    // Upsert: match by (event_subtype, period, month_reset)
                    upsertMetrics(base, value);
                    break;
                default:
                    // Replace
                    base.set(key, value);
                    break;
            }
        }

        return MAPPER.convertValue(base, RulesengineCompany.class);
    }

    /**
     * Merges a partial user update into an existing user.
     * Only fields present in the partial are applied.
     *
     * @param existing the current cached user
     * @param partial  the partial update as raw JSON from the datastream
     * @return a new merged user
     */
    static RulesengineUser partialUser(RulesengineUser existing, JsonNode partial) {
        ObjectNode base = (ObjectNode) MAPPER.valueToTree(existing);

        Iterator<Map.Entry<String, JsonNode>> fields = partial.fields();
        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> field = fields.next();
            String key = field.getKey();
            JsonNode value = field.getValue();

            if ("keys".equals(key)) {
                // Additive merge
                mergeObject(base, key, value);
            } else {
                // Replace
                base.set(key, value);
            }
        }

        return MAPPER.convertValue(base, RulesengineUser.class);
    }

    /**
     * Additively merges an object field: existing keys are preserved,
     * partial keys are added or updated.
     */
    private static void mergeObject(ObjectNode base, String fieldName, JsonNode partial) {
        if (!partial.isObject()) {
            base.set(fieldName, partial);
            return;
        }
        JsonNode existing = base.get(fieldName);
        if (existing == null || !existing.isObject()) {
            base.set(fieldName, partial);
            return;
        }

        ObjectNode merged = ((ObjectNode) existing).deepCopy();
        Iterator<Map.Entry<String, JsonNode>> fields = partial.fields();
        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> field = fields.next();
            merged.set(field.getKey(), field.getValue());
        }
        base.set(fieldName, merged);
    }

    /**
     * Upserts metrics by matching on (event_subtype, period, month_reset).
     * Existing metrics with matching keys are replaced; new metrics are appended.
     */
    private static void upsertMetrics(ObjectNode base, JsonNode partialMetrics) {
        if (!partialMetrics.isArray()) {
            return;
        }
        JsonNode existingMetrics = base.get("metrics");
        if (existingMetrics == null || !existingMetrics.isArray()) {
            base.set("metrics", partialMetrics);
            return;
        }

        // Build mutable list from existing
        com.fasterxml.jackson.databind.node.ArrayNode result = MAPPER.createArrayNode();
        // Copy existing metrics, replacing any that match a partial metric
        for (JsonNode existing : existingMetrics) {
            boolean replaced = false;
            for (JsonNode partial : partialMetrics) {
                if (metricsMatch(existing, partial)) {
                    result.add(partial);
                    replaced = true;
                    break;
                }
            }
            if (!replaced) {
                result.add(existing);
            }
        }
        // Append any partial metrics that didn't match an existing one
        for (JsonNode partial : partialMetrics) {
            boolean found = false;
            for (JsonNode existing : existingMetrics) {
                if (metricsMatch(existing, partial)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                result.add(partial);
            }
        }

        base.set("metrics", result);
    }

    /**
     * Matches metrics by the composite key (event_subtype, period, month_reset).
     */
    private static boolean metricsMatch(JsonNode a, JsonNode b) {
        return textEquals(a, b, "event_subtype") && textEquals(a, b, "period") && textEquals(a, b, "month_reset");
    }

    private static boolean textEquals(JsonNode a, JsonNode b, String field) {
        String aVal = a.has(field) ? a.get(field).asText("") : "";
        String bVal = b.has(field) ? b.get(field).asText("") : "";
        return aVal.equals(bVal);
    }
}
