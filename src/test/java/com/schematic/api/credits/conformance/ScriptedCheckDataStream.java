package com.schematic.api.credits.conformance;

import com.schematic.api.credits.CreditCheckDataStream;
import com.schematic.api.credits.PreflightOptions;
import com.schematic.api.types.RulesengineCheckFlagResult;
import com.schematic.api.types.RulesengineCompany;
import com.schematic.api.types.RulesengineEntitlementValueType;
import com.schematic.api.types.RulesengineFeatureEntitlement;
import com.schematic.api.types.RulesengineFlag;
import com.schematic.api.types.RulesengineUser;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Map;

/**
 * Serves one flag and one company from "cache" and answers each evaluation with the next scripted
 * result, in call order. The engine is an oracle here, as conformance/SPEC.md says: the vectors pin
 * the orchestration around it, not the engine, which is shared across the SDKs and has its own
 * tests.
 */
final class ScriptedCheckDataStream implements CreditCheckDataStream {

    /** One evaluation the flow asked for, kept so a vector can assert on what it was given. */
    static final class EngineCall {
        final Map<String, Double> creditBalances;
        final PreflightOptions preflight;

        EngineCall(Map<String, Double> creditBalances, PreflightOptions preflight) {
            this.creditBalances = creditBalances;
            this.preflight = preflight;
        }
    }

    /** One scripted engine answer. */
    static final class Result {
        final boolean value;
        final String reason;
        final RulesengineFeatureEntitlement entitlement;

        Result(boolean value, String reason, RulesengineFeatureEntitlement entitlement) {
            this.value = value;
            this.reason = reason;
            this.entitlement = entitlement;
        }
    }

    private final String flagKey;
    private final RulesengineFlag flag;
    private final RulesengineCompany company;
    private final Deque<Result> results = new ArrayDeque<>();
    final List<EngineCall> calls = new ArrayList<>();

    ScriptedCheckDataStream(String flagKey, RulesengineCompany company, List<Result> results) {
        this.flagKey = flagKey;
        this.flag = RulesengineFlag.builder()
                .accountId("acct_1")
                .defaultValue(false)
                .environmentId("env_1")
                .id("flag_1")
                .key(flagKey)
                .build();
        this.company = company;
        this.results.addAll(results);
    }

    static RulesengineCompany company(String id, Map<String, Double> creditBalances) {
        return RulesengineCompany.builder()
                .accountId("acct_1")
                .environmentId("env_1")
                .id(id)
                .creditBalances(creditBalances)
                .build();
    }

    static RulesengineFeatureEntitlement entitlement(
            String valueType, String creditId, Double consumptionRate, String eventSubtype) {
        RulesengineFeatureEntitlement._FinalStage builder = RulesengineFeatureEntitlement.builder()
                .featureId("feat_1")
                .featureKey("feature")
                .valueType(RulesengineEntitlementValueType.valueOf(valueType));
        if (creditId != null) {
            builder.creditId(creditId);
        }
        if (consumptionRate != null) {
            builder.consumptionRate(consumptionRate);
        }
        if (eventSubtype != null) {
            builder.eventSubtype(eventSubtype);
        }
        return builder.build();
    }

    @Override
    public RulesengineFlag getFlag(String key) {
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
            RulesengineFlag flag, RulesengineCompany company, RulesengineUser user, PreflightOptions preflight) {
        calls.add(new EngineCall(company == null ? null : company.getCreditBalances(), preflight));
        Result scripted = results.poll();
        if (scripted == null) {
            throw new IllegalStateException("unscripted engine call in a check op for flag " + flagKey);
        }
        RulesengineCheckFlagResult._FinalStage result = RulesengineCheckFlagResult.builder()
                .flagKey(flagKey)
                .reason(scripted.reason)
                .value(scripted.value)
                .flagId("flag_1");
        if (scripted.entitlement != null) {
            result.entitlement(scripted.entitlement);
        }
        return result.build();
    }
}
