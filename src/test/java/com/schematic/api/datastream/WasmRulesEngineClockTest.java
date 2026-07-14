package com.schematic.api.datastream;

import static org.junit.jupiter.api.Assertions.*;

import com.schematic.api.core.ObjectMappers;
import com.schematic.api.types.RulesengineCheckFlagResult;
import com.schematic.api.types.RulesengineCompany;
import com.schematic.api.types.RulesengineFlag;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Regression for SCHY-471.
 *
 * <p>A company-override entitlement rule whose metric condition uses a
 * calendar/billing metric period drives the engine into the metric-period-reset
 * code path, which needs a wall clock. On the raw {@code wasm32-unknown-unknown}
 * build that path used to trap ({@code wasm unreachable}) under the Chicory
 * runtime; the flag then wrongly fell back to its default value for a company
 * that is legitimately entitled. The host now injects the current time via
 * {@code setCurrentTimeMillis} so this path evaluates cleanly.
 */
class WasmRulesEngineClockTest {

    private static WasmRulesEngine engine;

    @BeforeAll
    static void setUp() {
        engine = new WasmRulesEngine(null);
        engine.initialize();
    }

    private static final String FLAG_JSON = "{"
            + "\"id\":\"flag1\",\"key\":\"mcp-access\",\"account_id\":\"acc_1\","
            + "\"environment_id\":\"env_1\",\"default_value\":false,\"rules\":[]}";

    // Usage 40 < allocation 100, so the company override grants the feature.
    private static final String COMPANY_JSON = "{"
            + "\"id\":\"co_entitled\",\"account_id\":\"acc_1\",\"environment_id\":\"env_1\","
            + "\"keys\":{\"id\":\"co_entitled\"},"
            + "\"metrics\":[{\"account_id\":\"acc_1\",\"environment_id\":\"env_1\",\"company_id\":\"co_entitled\","
            + "\"event_subtype\":\"api-calls\",\"period\":\"current_month\",\"month_reset\":\"billing_cycle\","
            + "\"value\":40,\"created_at\":\"2023-01-01T00:00:00Z\"}],"
            + "\"rules\":[{\"id\":\"rule_override\",\"flag_id\":\"flag1\",\"account_id\":\"acc_1\","
            + "\"environment_id\":\"env_1\",\"name\":\"Company Override\",\"rule_type\":\"company_override\","
            + "\"priority\":0,\"value\":true,\"condition_groups\":[],\"conditions\":["
            + "{\"id\":\"cond_company\",\"account_id\":\"acc_1\",\"environment_id\":\"env_1\","
            + "\"condition_type\":\"company\",\"operator\":\"eq\",\"resource_ids\":[\"co_entitled\"],\"trait_value\":\"\"},"
            + "{\"id\":\"cond_metric\",\"account_id\":\"acc_1\",\"environment_id\":\"env_1\","
            + "\"condition_type\":\"metric\",\"operator\":\"lt\",\"event_subtype\":\"api-calls\",\"metric_value\":100,"
            + "\"metric_period\":\"current_month\",\"metric_period_month_reset\":\"billing_cycle\",\"trait_value\":\"100\"}"
            + "]}]}";

    private static RulesengineFlag flag() throws Exception {
        return ObjectMappers.JSON_MAPPER.readValue(FLAG_JSON, RulesengineFlag.class);
    }

    private static RulesengineCompany entitledCompany() throws Exception {
        return ObjectMappers.JSON_MAPPER.readValue(COMPANY_JSON, RulesengineCompany.class);
    }

    @Test
    void billingMetricOverride_evaluatesWithoutTrapping() throws Exception {
        // Must not throw (the bug surfaced as a Chicory trap from checkFlagCombined).
        RulesengineCheckFlagResult result = engine.checkFlag(flag(), entitledCompany(), null);

        // The override grants the feature; a fallback to default_value would be false.
        assertTrue(result.getValue(), "company override should grant the flag, not fall back to default false");
        assertTrue(
                result.getReason().toLowerCase().contains("override"),
                "expected an override match reason, got: " + result.getReason());
    }

    @Test
    void billingMetricOverride_populatesResetAt() throws Exception {
        RulesengineCheckFlagResult result = engine.checkFlag(flag(), entitledCompany(), null);

        // setCurrentTimeMillis lets the engine compute the next reset boundary.
        assertTrue(
                result.getFeatureUsageResetAt().isPresent(),
                "reset-at should be computed from the injected host time");
    }
}
