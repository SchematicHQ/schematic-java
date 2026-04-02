package com.schematic.api.datastream;

import static org.junit.jupiter.api.Assertions.*;

import com.schematic.api.logger.SchematicLogger;
import com.schematic.api.types.RulesengineCheckFlagResult;
import com.schematic.api.types.RulesengineCompany;
import com.schematic.api.types.RulesengineFlag;
import com.schematic.api.types.RulesengineRule;
import com.schematic.api.types.RulesengineRuleRuleType;
import com.schematic.api.types.RulesengineTrait;
import com.schematic.api.types.RulesengineUser;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
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

    @Test
    void checkFlag_standardRule() throws Exception {
        List<RulesengineRule> rules = new ArrayList<>();
        rules.add(RulesengineRule.builder()
                .accountId("acc_1")
                .environmentId("env_1")
                .id("rule_standard")
                .name("standard")
                .priority(1)
                .ruleType(RulesengineRuleRuleType.STANDARD)
                .value(true)
                .conditions(Collections.emptyList())
                .conditionGroups(Collections.emptyList())
                .build());

        RulesengineFlag flag = buildFlag("standard-flag", false, rules);

        RulesengineCheckFlagResult result = engine.checkFlag(flag, null, null);

        assertNotNull(result);
        assertEquals("standard-flag", result.getFlagKey());
    }

    @Test
    void checkFlag_companyWithPlanIds() throws Exception {
        List<RulesengineRule> rules = new ArrayList<>();
        rules.add(buildRule("rule1", "global_override", true, 1));
        RulesengineFlag flag = buildFlag("plan-flag", false, rules);

        RulesengineCompany company = RulesengineCompany.builder()
                .accountId("acc_1")
                .environmentId("env_1")
                .id("comp-plan")
                .keys(Collections.singletonMap("id", "comp-plan"))
                .planIds(Arrays.asList("plan_1", "plan_2"))
                .traits(Collections.emptyList())
                .metrics(Collections.emptyList())
                .rules(Collections.emptyList())
                .billingProductIds(Collections.emptyList())
                .creditBalances(Collections.emptyMap())
                .planVersionIds(Collections.emptyList())
                .build();

        RulesengineCheckFlagResult result = engine.checkFlag(flag, company, null);

        assertNotNull(result);
        assertTrue(result.getValue());
    }

    @Test
    void checkFlag_companyWithTraits() throws Exception {
        List<RulesengineRule> rules = new ArrayList<>();
        rules.add(buildRule("rule1", "global_override", true, 1));
        RulesengineFlag flag = buildFlag("trait-flag", false, rules);

        List<RulesengineTrait> traits = new ArrayList<>();
        traits.add(RulesengineTrait.builder().value("premium").build());

        RulesengineCompany company = RulesengineCompany.builder()
                .accountId("acc_1")
                .environmentId("env_1")
                .id("comp-traits")
                .keys(Collections.singletonMap("id", "comp-traits"))
                .traits(traits)
                .metrics(Collections.emptyList())
                .rules(Collections.emptyList())
                .billingProductIds(Collections.emptyList())
                .creditBalances(Collections.emptyMap())
                .planIds(Collections.emptyList())
                .planVersionIds(Collections.emptyList())
                .build();

        RulesengineCheckFlagResult result = engine.checkFlag(flag, company, null);

        assertNotNull(result);
        assertTrue(result.getValue());
    }

    @Test
    void checkFlag_companyWithCreditBalances() throws Exception {
        List<RulesengineRule> rules = new ArrayList<>();
        rules.add(buildRule("rule1", "global_override", true, 1));
        RulesengineFlag flag = buildFlag("credit-flag", false, rules);

        Map<String, Double> creditBalances = new HashMap<>();
        creditBalances.put("credits", 100.0);
        creditBalances.put("bonus_credits", 50.5);

        RulesengineCompany company = RulesengineCompany.builder()
                .accountId("acc_1")
                .environmentId("env_1")
                .id("comp-credits")
                .keys(Collections.singletonMap("id", "comp-credits"))
                .creditBalances(creditBalances)
                .traits(Collections.emptyList())
                .metrics(Collections.emptyList())
                .rules(Collections.emptyList())
                .billingProductIds(Collections.emptyList())
                .planIds(Collections.emptyList())
                .planVersionIds(Collections.emptyList())
                .build();

        RulesengineCheckFlagResult result = engine.checkFlag(flag, company, null);

        assertNotNull(result);
        assertTrue(result.getValue());
    }

    @Test
    void checkFlag_companyWithMultipleKeys() throws Exception {
        List<RulesengineRule> rules = new ArrayList<>();
        rules.add(buildRule("rule1", "global_override", true, 1));
        RulesengineFlag flag = buildFlag("multikey-flag", false, rules);

        Map<String, String> keys = new HashMap<>();
        keys.put("id", "comp-multi");
        keys.put("slug", "comp-multi-slug");
        keys.put("external_id", "ext-123");

        RulesengineCompany company = RulesengineCompany.builder()
                .accountId("acc_1")
                .environmentId("env_1")
                .id("comp-multi")
                .keys(keys)
                .traits(Collections.emptyList())
                .metrics(Collections.emptyList())
                .rules(Collections.emptyList())
                .billingProductIds(Collections.emptyList())
                .creditBalances(Collections.emptyMap())
                .planIds(Collections.emptyList())
                .planVersionIds(Collections.emptyList())
                .build();

        RulesengineCheckFlagResult result = engine.checkFlag(flag, company, null);

        assertNotNull(result);
        assertTrue(result.getValue());
    }

    @Test
    void checkFlag_userWithTraits() throws Exception {
        List<RulesengineRule> rules = new ArrayList<>();
        rules.add(buildRule("rule1", "global_override", true, 1));
        RulesengineFlag flag = buildFlag("user-trait-flag", false, rules);

        List<RulesengineTrait> traits = new ArrayList<>();
        traits.add(RulesengineTrait.builder().value("admin").build());
        traits.add(RulesengineTrait.builder().value("active").build());

        RulesengineUser user = RulesengineUser.builder()
                .accountId("acc_1")
                .environmentId("env_1")
                .id("user-traits")
                .keys(Collections.singletonMap("id", "user-traits"))
                .traits(traits)
                .rules(Collections.emptyList())
                .build();

        RulesengineCheckFlagResult result = engine.checkFlag(flag, null, user);

        assertNotNull(result);
        assertTrue(result.getValue());
    }

    @Test
    void checkFlag_consistentResultsForIdenticalInputs() throws Exception {
        List<RulesengineRule> rules = new ArrayList<>();
        rules.add(buildRule("rule1", "global_override", true, 1));
        RulesengineFlag flag = buildFlag("determinism-flag", false, rules);
        RulesengineCompany company = buildCompany("comp-det");
        RulesengineUser user = buildUser("user-det");

        RulesengineCheckFlagResult result1 = engine.checkFlag(flag, company, user);
        RulesengineCheckFlagResult result2 = engine.checkFlag(flag, company, user);
        RulesengineCheckFlagResult result3 = engine.checkFlag(flag, company, user);

        assertNotNull(result1);
        assertNotNull(result2);
        assertNotNull(result3);
        assertEquals(result1.getValue(), result2.getValue());
        assertEquals(result2.getValue(), result3.getValue());
        assertEquals(result1.getFlagKey(), result2.getFlagKey());
        assertEquals(result2.getFlagKey(), result3.getFlagKey());
    }

    @Test
    void checkFlag_concurrentOperations() throws Exception {
        int threadCount = 10;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch doneLatch = new CountDownLatch(threadCount);
        AtomicInteger errorCount = new AtomicInteger(0);

        List<RulesengineRule> rules = new ArrayList<>();
        rules.add(buildRule("rule1", "global_override", true, 1));
        RulesengineFlag flag = buildFlag("concurrent-flag", false, rules);
        RulesengineCompany company = buildCompany("comp-conc");
        RulesengineUser user = buildUser("user-conc");

        for (int i = 0; i < threadCount; i++) {
            executor.execute(() -> {
                try {
                    startLatch.await();
                    RulesengineCheckFlagResult result = engine.checkFlag(flag, company, user);
                    assertNotNull(result);
                    assertTrue(result.getValue());
                } catch (Exception e) {
                    errorCount.incrementAndGet();
                } finally {
                    doneLatch.countDown();
                }
            });
        }

        startLatch.countDown();
        doneLatch.await();
        executor.shutdown();

        assertEquals(0, errorCount.get(), "Concurrent checkFlag calls should complete without errors");
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
