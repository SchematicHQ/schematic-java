package com.schematic.api.datastream;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.schematic.api.core.ObjectMappers;
import com.schematic.api.types.RulesengineCompany;
import com.schematic.api.types.RulesengineUser;
import java.util.HashMap;
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
 *
 * <p>Partials don't carry refreshed entitlements, so when {@code credit_balances}
 * or {@code metrics} change in another part of the company we sync the derived
 * fields on existing entitlements here to match server behavior:
 * <ul>
 *   <li>{@code credit_remaining} ← {@code credit_balances[credit_id]}</li>
 *   <li>{@code usage} ← metric value matching {@code (event_name, metric_period, month_reset)}</li>
 * </ul>
 * Both are skipped when the partial also sends {@code entitlements} wholesale.
 */
final class EntityMerge {

    private static final ObjectMapper MAPPER = ObjectMappers.JSON_MAPPER;

    /** Usage default for a matched metric that carries no value. */
    private static final IntNode ZERO = IntNode.valueOf(0);

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

        JsonNode updatedBalances = null;
        boolean metricsUpdated = false;

        Iterator<Map.Entry<String, JsonNode>> fields = partial.fields();
        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> field = fields.next();
            String key = field.getKey();
            JsonNode value = field.getValue();

            switch (key) {
                case "keys":
                    // Additive merge: overlay partial keys onto existing
                    mergeObject(base, key, value);
                    break;
                case "credit_balances":
                    mergeObject(base, key, value);
                    updatedBalances = value;
                    break;
                case "metrics":
                    // Upsert: match by (event_subtype, period, month_reset)
                    upsertMetrics(base, value);
                    metricsUpdated = true;
                    break;
                default:
                    // Replace
                    base.set(key, value);
                    break;
            }
        }

        // Partials don't carry refreshed entitlements, so re-derive credit_remaining
        // and usage from the merged credit_balances/metrics. Skipped when the partial
        // sent entitlements wholesale — we trust those.
        if ((updatedBalances != null || metricsUpdated) && !partial.has("entitlements")) {
            syncEntitlements(base, updatedBalances, metricsUpdated);
        }

        return MAPPER.convertValue(base, RulesengineCompany.class);
    }

    /**
     * Re-derives entitlement fields that a partial leaves stale:
     * {@code credit_remaining} from the merged credit balances and {@code usage}
     * from the merged metrics. Existing entitlements are rebuilt in place; the
     * matching mirrors the server's effective-entitlement lookup.
     *
     * @param base            the company JSON tree being built (with metrics/balances already merged)
     * @param updatedBalances the partial's {@code credit_balances} node, or {@code null} if unchanged
     * @param metricsUpdated  whether the partial updated {@code metrics}
     */
    private static void syncEntitlements(ObjectNode base, JsonNode updatedBalances, boolean metricsUpdated) {
        JsonNode entitlements = base.get("entitlements");
        if (entitlements == null || !entitlements.isArray() || entitlements.isEmpty()) {
            return;
        }

        // Index merged metric values by (event_subtype, period, month_reset).
        Map<String, JsonNode> metricsLookup = new HashMap<>();
        if (metricsUpdated) {
            JsonNode metrics = base.get("metrics");
            if (metrics != null && metrics.isArray()) {
                for (JsonNode metric : metrics) {
                    if (metric == null || !metric.isObject()) {
                        continue;
                    }
                    String key = metricKey(
                            textOrEmpty(metric, "event_subtype"),
                            textOrEmpty(metric, "period"),
                            textOrEmpty(metric, "month_reset"));
                    // Mirror Python/Ruby: a metric with no value counts as 0.
                    JsonNode value = metric.get("value");
                    metricsLookup.put(key, (value == null || value.isNull()) ? ZERO : value);
                }
            }
        }

        boolean balancesUsable = updatedBalances != null && updatedBalances.isObject();

        ArrayNode result = MAPPER.createArrayNode();
        for (JsonNode entNode : entitlements) {
            if (entNode == null || !entNode.isObject()) {
                result.add(entNode);
                continue;
            }
            ObjectNode ent = ((ObjectNode) entNode).deepCopy();

            if (balancesUsable) {
                String creditId = textOrEmpty(ent, "credit_id");
                if (!creditId.isEmpty() && updatedBalances.has(creditId)) {
                    ent.set("credit_remaining", updatedBalances.get(creditId));
                }
            }

            if (metricsUpdated) {
                String eventName = textOrEmpty(ent, "event_name");
                if (!eventName.isEmpty()) {
                    // Server defaults when the entitlement omits these.
                    String period = textOrDefault(ent, "metric_period", "all_time");
                    String monthReset = textOrDefault(ent, "month_reset", "first_of_month");
                    // A matched key always sets usage; absent keys leave it unchanged.
                    JsonNode matched = metricsLookup.get(metricKey(eventName, period, monthReset));
                    if (matched != null) {
                        ent.set("usage", matched);
                    }
                }
            }

            result.add(ent);
        }

        base.set("entitlements", result);
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
        ArrayNode result = MAPPER.createArrayNode();
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

    /** Composite key for matching a metric to an entitlement. */
    private static String metricKey(String eventSubtype, String period, String monthReset) {
        return eventSubtype + '\0' + period + '\0' + monthReset;
    }

    /** Returns the field's text value, or empty string if absent or null. */
    private static String textOrEmpty(JsonNode node, String field) {
        JsonNode value = node.get(field);
        return (value == null || value.isNull()) ? "" : value.asText("");
    }

    /** Returns the field's text value, or {@code dflt} if absent, null, or empty. */
    private static String textOrDefault(JsonNode node, String field, String dflt) {
        String value = textOrEmpty(node, field);
        return value.isEmpty() ? dflt : value;
    }
}
