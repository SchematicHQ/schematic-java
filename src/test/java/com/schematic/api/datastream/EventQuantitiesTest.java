package com.schematic.api.datastream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.schematic.api.core.ObjectMappers;
import com.schematic.api.types.RulesengineCheckFlagResult;
import com.schematic.api.types.RulesengineCompany;
import com.schematic.api.types.RulesengineFlag;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * The event_quantities preflight and the quantity_rates it prices from, run through the bundled
 * WebAssembly engine. Flags and companies are read from the snake_case payloads DataStream sends,
 * through the SDK's object mapper, so a quantity_rates the generated models would drop fails here.
 */
class EventQuantitiesTest {

    private static final ObjectMapper MAPPER = ObjectMappers.JSON_MAPPER;
    private static final String SUBTYPE = "chat";
    private static final String CREDIT_ID = "credit-abc";
    private static final Map<String, Double> RATES = rates("input_tokens", 0.001, "output_tokens", 0.01);
    // 1 request x 0.5 + (1000 - 400 cached) x 0.001 + 100 x 0.01 = 2.1. The cached tokens are
    // unrated, so they cost nothing but still come out of input.
    private static final Map<String, Double> QUANTITIES = quantities();
    private static final CheckFlagOptions CALL = CheckFlagOptions.eventQuantities(SUBTYPE, null, QUANTITIES);

    private static WasmRulesEngine engine;

    @BeforeAll
    static void loadEngine() {
        WasmRulesEngine candidate = new WasmRulesEngine(null);
        try {
            candidate.initialize();
        } catch (RuntimeException e) {
            // The binary is fetched by scripts/download-wasm.sh, which needs a token.
            candidate = null;
        }
        engine = candidate;
        assumeTrue(engine != null, "the rules engine WASM binary is not present");
    }

    private static Map<String, Double> rates(String k1, double v1, String k2, double v2) {
        Map<String, Double> m = new LinkedHashMap<>();
        m.put(k1, v1);
        m.put(k2, v2);
        return m;
    }

    private static Map<String, Double> quantities() {
        Map<String, Double> m = new LinkedHashMap<>();
        m.put("input_tokens", 1000.0);
        m.put("cached_input_tokens", 400.0);
        m.put("output_tokens", 100.0);
        return m;
    }

    /**
     * A single credit-balance rule priced like an inference entitlement: requests at the
     * consumption rate, tokens at their own rates.
     */
    private static RulesengineFlag inferenceFlagWithRates(double consumptionRate, JsonNode quantityRates)
            throws Exception {
        ObjectNode condition = MAPPER.createObjectNode()
                .put("id", "cond-1")
                .put("account_id", "acct")
                .put("environment_id", "env")
                .put("condition_type", "credit")
                .put("operator", "lt")
                .put("trait_value", "")
                .put("credit_id", CREDIT_ID)
                .put("consumption_rate", consumptionRate)
                .put("event_subtype", SUBTYPE);
        condition.putArray("resource_ids");
        if (quantityRates != null && !quantityRates.isNull()) {
            condition.set("quantity_rates", quantityRates);
        }
        ObjectNode rule = MAPPER.createObjectNode()
                .put("id", "rule-1")
                .put("account_id", "acct")
                .put("environment_id", "env")
                .put("name", "Credits")
                .put("rule_type", "plan_entitlement")
                .put("priority", 0)
                .put("value", true);
        rule.putArray("conditions").add(condition);
        rule.putArray("condition_groups");
        ObjectNode flag = MAPPER.createObjectNode()
                .put("id", "flag-1")
                .put("account_id", "acct")
                .put("environment_id", "env")
                .put("key", "chat")
                .put("default_value", false);
        flag.putArray("rules").add(rule);
        return MAPPER.treeToValue(flag, RulesengineFlag.class);
    }

    private static RulesengineFlag inferenceFlag(double consumptionRate, Map<String, Double> quantityRates)
            throws Exception {
        return inferenceFlagWithRates(
                consumptionRate, quantityRates == null ? null : MAPPER.valueToTree(quantityRates));
    }

    private static ObjectNode companyNode(double balance) {
        ObjectNode company = MAPPER.createObjectNode()
                .put("id", "co")
                .put("account_id", "acct")
                .put("environment_id", "env");
        company.putObject("keys").put("id", "co");
        company.putObject("credit_balances").put(CREDIT_ID, balance);
        company.putArray("billing_product_ids");
        company.putArray("crm_product_ids");
        company.putArray("plan_ids");
        company.putArray("plan_version_ids");
        company.putArray("metrics");
        company.putArray("traits");
        company.putArray("rules");
        return company;
    }

    private static RulesengineCompany company(double balance) throws Exception {
        return MAPPER.treeToValue(companyNode(balance), RulesengineCompany.class);
    }

    private static RulesengineCheckFlagResult check(double balance, CheckFlagOptions options) throws Exception {
        return engine.checkFlag(inferenceFlag(0.5, RATES), company(balance), null, options);
    }

    @Test
    void passesWhenTheBalanceCoversTheCall() throws Exception {
        RulesengineCheckFlagResult result = check(2.1, CALL);

        assertEquals(Optional.of("rule-1"), result.getRuleId());
        assertTrue(result.getValue());
    }

    @Test
    void refusesWhenTheBalanceFallsShort() throws Exception {
        // Covers the request and the legacy single unit, not the tokens.
        RulesengineCheckFlagResult result = check(2.0, CALL);

        assertEquals(Optional.empty(), result.getRuleId());
        assertFalse(result.getValue());
    }

    @Test
    void ignoredForAnotherSubtype() throws Exception {
        CheckFlagOptions other =
                CheckFlagOptions.eventQuantities("other", null, Collections.singletonMap("input_tokens", 1e6));

        assertEquals(Optional.of("rule-1"), check(1.0, other).getRuleId());
    }

    @Test
    void quantityMultipliesTheBaseNotTheQuantities() throws Exception {
        // 3 x 0.5 + 600 x 0.001 + 100 x 0.01 = 3.1.
        CheckFlagOptions options = CheckFlagOptions.eventQuantities(SUBTYPE, 3.0, QUANTITIES);

        assertEquals(Optional.of("rule-1"), check(3.1, options).getRuleId());
        assertEquals(Optional.empty(), check(3.0, options).getRuleId());
    }

    @Test
    void fractionalQuantitiesPassThroughUnrounded() throws Exception {
        // 0.5 + 0.5 x 0.01 = 0.505; rounding the half token up would ask 0.51.
        CheckFlagOptions options =
                CheckFlagOptions.eventQuantities(SUBTYPE, null, Collections.singletonMap("output_tokens", 0.5));

        assertEquals(Optional.of("rule-1"), check(0.505, options).getRuleId());
    }

    @Test
    void rejectsNegativeValues() throws Exception {
        for (CheckFlagOptions options : new CheckFlagOptions[] {
            CheckFlagOptions.eventQuantities(SUBTYPE, -1.0, null),
            CheckFlagOptions.eventQuantities(SUBTYPE, null, Collections.singletonMap("input_tokens", -1.0))
        }) {
            RulesengineCheckFlagResult result = check(100, options);

            assertFalse(result.getValue());
            assertTrue(result.getErr().isPresent());
        }
    }

    @Test
    void theOptionKeepsItsOwnCopyOfQuantities() throws Exception {
        Map<String, Double> quantities = new HashMap<>();
        quantities.put("output_tokens", 100.0);
        CheckFlagOptions options = CheckFlagOptions.eventQuantities(SUBTYPE, null, quantities);
        quantities.put("output_tokens", 1e6);

        // 0.5 + 100 x 0.01 = 1.5.
        assertEquals(Optional.of("rule-1"), check(1.5, options).getRuleId());
    }

    @Test
    void aCompanyEntitlementsQuantityRatesReachTheResult() throws Exception {
        ObjectNode company = companyNode(0);
        ObjectNode entitlement = company.putArray("entitlements")
                .addObject()
                .put("feature_id", "feat-1")
                .put("feature_key", "chat")
                .put("value_type", "credit");
        entitlement.set("quantity_rates", MAPPER.valueToTree(RATES));

        RulesengineCheckFlagResult result =
                engine.checkFlag(inferenceFlag(0.5, null), MAPPER.treeToValue(company, RulesengineCompany.class), null);

        assertTrue(result.getEntitlement().isPresent());
        assertEquals(
                MAPPER.valueToTree(RATES),
                MAPPER.valueToTree(
                        result.getEntitlement().get().getAdditionalProperties().get("quantity_rates")));
    }

    /**
     * quantity_cost.json is copied verbatim from schematic-api's
     * api/lib/rulesengine/testdata/quantity_cost.json. The API's burn and the engine both price
     * every case there; running them through the WebAssembly engine here pins that this SDK's wire
     * shape for quantity_rates and event_quantities reaches that pricing intact.
     */
    @Test
    void sharedQuantityCostFixture() throws Exception {
        JsonNode fixture;
        try (InputStream in = getClass().getClassLoader().getResourceAsStream("quantity_cost.json")) {
            fixture = MAPPER.readTree(in);
        }
        int ran = 0;
        for (JsonNode tc : fixture.get("cases")) {
            Double quantity = tc.hasNonNull("quantity") ? tc.get("quantity").asDouble() : null;
            Map<String, Double> quantities = new LinkedHashMap<>();
            boolean negative = quantity != null && quantity < 0;
            if (tc.hasNonNull("quantities")) {
                Iterator<Map.Entry<String, JsonNode>> fields =
                        tc.get("quantities").fields();
                while (fields.hasNext()) {
                    Map.Entry<String, JsonNode> field = fields.next();
                    quantities.put(field.getKey(), field.getValue().asDouble());
                    negative |= field.getValue().asDouble() < 0;
                }
            }
            if (negative) {
                // The preflight rejects negative quantities before pricing.
                continue;
            }
            ran++;

            String name = tc.get("name").asText();
            RulesengineFlag flag =
                    inferenceFlagWithRates(tc.get("consumption_rate").asDouble(), tc.get("quantity_rates"));
            CheckFlagOptions options = CheckFlagOptions.eventQuantities(SUBTYPE, quantity, quantities);
            double cost = tc.get("expected_cost").asDouble();

            // A cost priced to zero gates on balance > 0, so the smallest positive balance passes
            // and zero does not.
            double covers = cost * (1 + 1e-9) + 1e-9;
            double shortOf = cost == 0 ? 0 : cost * (1 - 1e-6);

            assertEquals(
                    Optional.of("rule-1"),
                    engine.checkFlag(flag, company(covers), null, options).getRuleId(),
                    name + ": balance " + covers + " should cover cost " + cost);
            assertEquals(
                    Optional.empty(),
                    engine.checkFlag(flag, company(shortOf), null, options).getRuleId(),
                    name + ": balance " + shortOf + " should not cover cost " + cost);
        }
        assertTrue(ran > 0);
    }
}
