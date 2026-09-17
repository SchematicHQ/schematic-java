package com.schematic.api.credits;

import com.schematic.api.datastream.CheckFlagOptions;
import com.schematic.api.datastream.DataStreamClient;
import com.schematic.api.types.RulesengineCheckFlagResult;
import com.schematic.api.types.RulesengineCompany;
import com.schematic.api.types.RulesengineFlag;
import com.schematic.api.types.RulesengineUser;
import java.util.Map;

/**
 * Serves a credit-gated check from the DataStream client. The translation from the flow's
 * preflight to the engine's own options lives here, so the flow stays free of the engine's
 * surface.
 */
public final class DataStreamCreditCheckSource implements CreditCheckDataStream {

    private final DataStreamClient dataStream;

    public DataStreamCreditCheckSource(DataStreamClient dataStream) {
        this.dataStream = dataStream;
    }

    @Override
    public RulesengineFlag getFlag(String flagKey) {
        return dataStream.getCachedFlag(flagKey);
    }

    @Override
    public RulesengineCompany getCompany(Map<String, String> keys) {
        return dataStream.getCompany(keys);
    }

    @Override
    public RulesengineUser getUser(Map<String, String> keys) {
        return dataStream.getUser(keys);
    }

    @Override
    public RulesengineCheckFlagResult evaluateFlag(
            RulesengineFlag flag, RulesengineCompany company, RulesengineUser user, PreflightOptions preflight)
            throws Exception {
        return dataStream.evaluateFlagWithOptions(flag, company, user, toEngineOptions(preflight));
    }

    /** Translates the flow's preflight into the engine's options. */
    public static CheckFlagOptions toEngineOptions(PreflightOptions preflight) {
        if (preflight == null) {
            return null;
        }
        if (preflight.getCreditCost() != null) {
            return CheckFlagOptions.creditCost(preflight.getCreditCost());
        }
        if (preflight.getEventUsage() != null) {
            return CheckFlagOptions.eventUsage(
                    preflight.getEventUsage().getEventSubtype(),
                    preflight.getEventUsage().getQuantity());
        }
        if (preflight.getUsage() != null) {
            return CheckFlagOptions.usage(preflight.getUsage());
        }
        return null;
    }
}
