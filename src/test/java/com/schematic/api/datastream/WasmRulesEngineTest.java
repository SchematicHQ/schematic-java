package com.schematic.api.datastream;

import static org.junit.jupiter.api.Assertions.*;

import com.schematic.api.logger.SchematicLogger;
import com.schematic.api.types.RulesengineCheckFlagResult;
import com.schematic.api.types.RulesengineCompany;
import com.schematic.api.types.RulesengineFlag;
import com.schematic.api.types.RulesengineRule;
import com.schematic.api.types.RulesengineRuleRuleType;
import com.schematic.api.types.RulesengineUser;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class WasmRulesEngineTest {

    @Mock
    private static SchematicLogger logger;

    private static WasmRulesEngine engine;

    @BeforeAll
    static void setUp() {
        engine = new WasmRulesEngine(logger);
        engine.initialize();
    }

    @Test
    void initialize_isIdempotent() {
        engine.initialize();
        assertTrue(engine.isInitialized());
    }

    @Test
    void getVersionKey_returnsNonNull() {
        String versionKey = engine.getVersionKey();
        assertNotNull(versionKey);
        assertFalse(versionKey.isEmpty());
    }

    @Test
    void checkFlag_globalOverrideTrue() throws Exception {
        List<RulesengineRule> rules = new ArrayList<>();
        rules.add(buildRule("rule1", "global_override", true, 1));

        RulesengineFlag flag = buildFlag("test-flag", false, rules);

        RulesengineCheckFlagResult result = engine.checkFlag(flag, null, null);

        assertNotNull(result);
        assertEquals("test-flag", result.getFlagKey());
        assertTrue(result.getValue());
    }

    @Test
    void checkFlag_defaultValueFalse_noRules() throws Exception {
        RulesengineFlag flag = buildFlag("default-flag", false, Collections.emptyList());

        RulesengineCheckFlagResult result = engine.checkFlag(flag, null, null);

        assertNotNull(result);
        assertEquals("default-flag", result.getFlagKey());
        assertFalse(result.getValue());
    }

    @Test
    void checkFlag_defaultValueTrue_noRules() throws Exception {
        RulesengineFlag flag = buildFlag("true-flag", true, Collections.emptyList());

        RulesengineCheckFlagResult result = engine.checkFlag(flag, null, null);

        assertNotNull(result);
        assertEquals("true-flag", result.getFlagKey());
        assertTrue(result.getValue());
    }

    @Test
    void checkFlag_withCompanyContext() throws Exception {
        List<RulesengineRule> rules = new ArrayList<>();
        rules.add(buildRule("rule1", "global_override", true, 1));
        RulesengineFlag flag = buildFlag("company-flag", false, rules);

        RulesengineCompany company = buildCompany("comp-1");

        RulesengineCheckFlagResult result = engine.checkFlag(flag, company, null);

        assertNotNull(result);
        assertTrue(result.getValue());
    }

    @Test
    void checkFlag_withUserContext() throws Exception {
        List<RulesengineRule> rules = new ArrayList<>();
        rules.add(buildRule("rule1", "global_override", true, 1));
        RulesengineFlag flag = buildFlag("user-flag", false, rules);

        RulesengineUser user = buildUser("user-1");

        RulesengineCheckFlagResult result = engine.checkFlag(flag, null, user);

        assertNotNull(result);
        assertTrue(result.getValue());
    }

    @Test
    void checkFlag_withCompanyAndUserContext() throws Exception {
        List<RulesengineRule> rules = new ArrayList<>();
        rules.add(buildRule("rule1", "global_override", true, 1));
        RulesengineFlag flag = buildFlag("full-flag", false, rules);

        RulesengineCompany company = buildCompany("comp-1");
        RulesengineUser user = buildUser("user-1");

        RulesengineCheckFlagResult result = engine.checkFlag(flag, company, user);

        assertNotNull(result);
        assertTrue(result.getValue());
    }

    @Test
    void camelToSnake_convertsCorrectly() {
        assertEquals("flag_key", WasmRulesEngine.camelToSnake("flagKey"));
        assertEquals("company_id", WasmRulesEngine.camelToSnake("companyId"));
        assertEquals("feature_usage_reset_at", WasmRulesEngine.camelToSnake("featureUsageResetAt"));
        assertEquals("value", WasmRulesEngine.camelToSnake("value"));
        assertEquals("id", WasmRulesEngine.camelToSnake("id"));
    }

    // --- Helpers using generated types ---

    private RulesengineFlag buildFlag(String key, boolean defaultValue, List<RulesengineRule> rules) {
        return RulesengineFlag.builder()
                .accountId("acc_1")
                .defaultValue(defaultValue)
                .environmentId("env_1")
                .id("flag_" + key)
                .key(key)
                .rules(rules)
                .build();
    }

    private RulesengineRule buildRule(String id, String ruleType, boolean value, int priority) {
        return RulesengineRule.builder()
                .accountId("acc_1")
                .environmentId("env_1")
                .id(id)
                .name(ruleType)
                .priority(priority)
                .ruleType(RulesengineRuleRuleType.GLOBAL_OVERRIDE)
                .value(value)
                .conditions(Collections.emptyList())
                .conditionGroups(Collections.emptyList())
                .build();
    }

    private RulesengineCompany buildCompany(String id) {
        return RulesengineCompany.builder()
                .accountId("acc_1")
                .environmentId("env_1")
                .id(id)
                .keys(Collections.singletonMap("id", id))
                .traits(Collections.emptyList())
                .metrics(Collections.emptyList())
                .rules(Collections.emptyList())
                .billingProductIds(Collections.emptyList())
                .creditBalances(Collections.emptyMap())
                .planIds(Collections.emptyList())
                .planVersionIds(Collections.emptyList())
                .build();
    }

    private RulesengineUser buildUser(String id) {
        return RulesengineUser.builder()
                .accountId("acc_1")
                .environmentId("env_1")
                .id(id)
                .keys(Collections.singletonMap("id", id))
                .traits(Collections.emptyList())
                .rules(Collections.emptyList())
                .build();
    }
}
