package com.schematic.api.credits;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import com.schematic.api.datastream.WasmRulesEngine;
import com.schematic.api.types.ComparableOperator;
import com.schematic.api.types.RulesengineCheckFlagResult;
import com.schematic.api.types.RulesengineCompany;
import com.schematic.api.types.RulesengineCondition;
import com.schematic.api.types.RulesengineConditionType;
import com.schematic.api.types.RulesengineEntitlementValueType;
import com.schematic.api.types.RulesengineFeatureEntitlement;
import com.schematic.api.types.RulesengineFlag;
import com.schematic.api.types.RulesengineRule;
import com.schematic.api.types.RulesengineRuleType;
import com.schematic.api.types.RulesengineUser;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * The flow's other tests script the rules engine, so the contract that matters most is never
 * exercised against the real thing: reading the matched credit entitlement off the probe,
 * substituting the lease balance into the company, and letting the engine's credit cost gate
 * decide. These run the bundled WebAssembly end to end, so a drift in the option envelope or the
 * entity shape fails here rather than mis-gating in production.
 */
class WasmCreditGateTest {

    private static final String FLAG_KEY = "infer";
    private static final String CREDIT_ID = "ct_1";
    private static final String SUBTYPE = "inference_tokens";
    private static final Instant NOW = Instant.parse("2026-01-01T00:00:00Z");

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

    private static RulesengineCondition creditCondition() {
        return RulesengineCondition.builder()
                .accountId("acct")
                .conditionType(RulesengineConditionType.CREDIT)
                .environmentId("env")
                .id("cond_credit")
                .operator(ComparableOperator.LT)
                .traitValue("")
                .creditId(CREDIT_ID)
                .consumptionRate(1.0)
                .eventSubtype(SUBTYPE)
                .build();
    }

    /** A membership condition, so the engine can deny for a reason that is not the balance. */
    private static RulesengineCondition companyCondition(String companyId) {
        return RulesengineCondition.builder()
                .accountId("acct")
                .conditionType(RulesengineConditionType.COMPANY)
                .environmentId("env")
                .id("cond_company")
                .operator(ComparableOperator.EQ)
                .traitValue("")
                .resourceIds(Collections.singletonList(companyId))
                .build();
    }

    private static RulesengineFlag creditFlag(RulesengineCondition... extra) {
        List<RulesengineCondition> conditions = new ArrayList<>();
        conditions.add(creditCondition());
        conditions.addAll(Arrays.asList(extra));
        return RulesengineFlag.builder()
                .accountId("acct")
                .defaultValue(false)
                .environmentId("env")
                .id("flag_infer")
                .key(FLAG_KEY)
                .rules(Collections.singletonList(RulesengineRule.builder()
                        .accountId("acct")
                        .environmentId("env")
                        .id("rule_credit")
                        .name("Credit")
                        .priority(100)
                        .ruleType(RulesengineRuleType.PLAN_ENTITLEMENT)
                        .value(true)
                        .conditions(conditions)
                        .build()))
                .build();
    }

    private static RulesengineCompany company(double balance) {
        return RulesengineCompany.builder()
                .accountId("acct")
                .environmentId("env")
                .id("co_1")
                .creditBalances(Collections.singletonMap(CREDIT_ID, balance))
                .entitlements(Collections.singletonList(RulesengineFeatureEntitlement.builder()
                        .featureId("feat_infer")
                        .featureKey(FLAG_KEY)
                        .valueType(RulesengineEntitlementValueType.CREDIT)
                        .creditId(CREDIT_ID)
                        .consumptionRate(1.0)
                        .eventSubtype(SUBTYPE)
                        .creditTotal(balance)
                        .creditRemaining(balance)
                        .build()))
                .build();
    }

    /** Serves fixed fixtures and the real engine. */
    private static final class EngineDataStream implements CreditCheckDataStream {
        private final RulesengineFlag flag;
        private final RulesengineCompany company;

        EngineDataStream(RulesengineFlag flag, RulesengineCompany company) {
            this.flag = flag;
            this.company = company;
        }

        @Override
        public RulesengineFlag getFlag(String flagKey) {
            return flag;
        }

        @Override
        public RulesengineCompany getCompany(Map<String, String> keys) {
            return company;
        }

        @Override
        public RulesengineUser getUser(Map<String, String> keys) {
            return null;
        }

        @Override
        public RulesengineCheckFlagResult evaluateFlag(
                RulesengineFlag flag, RulesengineCompany company, RulesengineUser user, PreflightOptions preflight)
                throws Exception {
            return engine.checkFlag(flag, company, user, DataStreamCreditCheckSource.toEngineOptions(preflight));
        }
    }

    private static final class Fixture {
        final InMemoryLeaseStore leases = new InMemoryLeaseStore(Clock.fixed(NOW, ZoneOffset.UTC));
        final InMemoryReservationStore holds = new InMemoryReservationStore(leases, Clock.fixed(NOW, ZoneOffset.UTC));
        final CreditLeaseManager manager;
        final CreditCheck flow;
        boolean fellBack;
        int acquires;

        Fixture(RulesengineFlag flag, RulesengineCompany company) {
            LeaseWireClient wire = new LeaseWireClient() {
                @Override
                public LeaseGrant acquire(
                        String companyId, String creditTypeId, double requestedAmount, Instant expiresAt) {
                    acquires++;
                    return new LeaseGrant("lse_wire", companyId, creditTypeId, requestedAmount, expiresAt);
                }

                @Override
                public LeaseGrant extend(String leaseId, double additionalAmount, Instant expiresAt) {
                    throw new UnsupportedOperationException("no extend expected");
                }

                @Override
                public void release(String leaseId) {}
            };
            manager = new CreditLeaseManager(
                    wire, leases, holds, CreditLeaseConfig.builder().build(), null, Clock.fixed(NOW, ZoneOffset.UTC));
            flow = new CreditCheck(
                    new EngineDataStream(flag, company),
                    leases,
                    holds,
                    manager,
                    null,
                    Clock.fixed(NOW, ZoneOffset.UTC),
                    null,
                    null);
        }

        void installLease(double granted) {
            leases.replace(new LeaseGrant("lse_1", "co_1", CREDIT_ID, granted, NOW.plusSeconds(300)));
        }

        CheckResult run(double usage) {
            Callable<CheckResult> fallback = () -> {
                fellBack = true;
                return new CheckResult(true, true, "fallback", FLAG_KEY, null, null, null, null);
            };
            CheckResult result = flow.check(
                    new CheckRequest(FLAG_KEY, Collections.singletonMap("id", "co_1"), null, usage, SUBTYPE, false),
                    fallback);
            manager.drain(java.time.Duration.ofSeconds(5));
            manager.close();
            return result;
        }
    }

    @Test
    void substitutesTheLeaseBalanceAndIssuesAHold() {
        Fixture fixture = new Fixture(creditFlag(), company(100));
        fixture.installLease(10000);

        CheckResult result = fixture.run(50);

        assertFalse(fixture.fellBack);
        assertTrue(result.isAllowed());
        assertNotNull(result.getReservation());
        assertEquals(CREDIT_ID, result.getReservation().getCreditTypeId());
        assertEquals(50.0, result.getReservation().getCreditsReserved());
        // The hold stays debited from the lease's local view.
        assertEquals(9950.0, fixture.leases.get("co_1", CREDIT_ID).getLocalRemainingCredits());
        assertEquals(1, fixture.holds.count());
    }

    @Test
    void cancelsTheHoldWhenTheRuleDeniesForANonCreditReason() {
        // The balance is plentiful, but the membership condition excludes this company, so the
        // engine denies and the hold taken before the gate has to go back.
        Fixture fixture = new Fixture(creditFlag(companyCondition("co_other")), company(10000));
        fixture.installLease(10000);

        CheckResult result = fixture.run(50);

        assertFalse(fixture.fellBack);
        assertFalse(result.isAllowed());
        assertNull(result.getReservation());
        assertEquals(10000.0, fixture.leases.get("co_1", CREDIT_ID).getLocalRemainingCredits());
        assertEquals(0, fixture.holds.count());
    }

    @Test
    void gatesExactlyAtTheBalanceBoundary() throws Exception {
        // The contract the flow leans on: the preflight the SDK builds reaches the engine as the
        // event-scoped usage it gates the credit condition with.
        RulesengineFlag flag = creditFlag();
        RulesengineCompany company = company(100);

        RulesengineCheckFlagResult under = engine.checkFlag(
                flag,
                company,
                null,
                DataStreamCreditCheckSource.toEngineOptions(PreflightOptions.fromUsage(50.0, SUBTYPE)));
        RulesengineCheckFlagResult over = engine.checkFlag(
                flag,
                company,
                null,
                DataStreamCreditCheckSource.toEngineOptions(PreflightOptions.fromUsage(150.0, SUBTYPE)));

        assertTrue(under.getValue());
        assertFalse(over.getValue());
    }
}
