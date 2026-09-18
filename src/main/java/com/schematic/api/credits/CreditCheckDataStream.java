package com.schematic.api.credits;

import com.schematic.api.types.RulesengineCheckFlagResult;
import com.schematic.api.types.RulesengineCompany;
import com.schematic.api.types.RulesengineFlag;
import com.schematic.api.types.RulesengineUser;
import java.util.Map;

/**
 * The slice of the DataStream client a credit-gated check touches. Narrow on purpose: it keeps
 * this package off the wider DataStream surface and lets the conformance runner drive the flow
 * without a socket or a WASM runtime.
 */
public interface CreditCheckDataStream {

    /** Reads a flag from the local cache, or returns null when it is not there. */
    RulesengineFlag getFlag(String flagKey);

    /** Resolves company keys, cache first, then over the wire. Null when it cannot be resolved. */
    RulesengineCompany getCompany(Map<String, String> keys);

    /** Resolves user keys, cache first, then over the wire. Null when it cannot be resolved. */
    RulesengineUser getUser(Map<String, String> keys);

    /** Runs the rules engine. A null {@code preflight} means no preflight. */
    RulesengineCheckFlagResult evaluateFlag(
            RulesengineFlag flag, RulesengineCompany company, RulesengineUser user, PreflightOptions preflight)
            throws Exception;
}
