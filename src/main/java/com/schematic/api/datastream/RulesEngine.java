package com.schematic.api.datastream;

import com.schematic.api.types.RulesengineCheckFlagResult;
import com.schematic.api.types.RulesengineCompany;
import com.schematic.api.types.RulesengineFlag;
import com.schematic.api.types.RulesengineUser;

/**
 * Interface for local flag evaluation using a rules engine.
 *
 * <p>The WASM-based implementation evaluates flag rules locally using cached
 * flag definitions, company data, and user data from the DataStream.
 */
public interface RulesEngine {

    /**
     * Returns whether the rules engine has been initialized and is ready to evaluate flags.
     */
    boolean isInitialized();

    /**
     * Evaluates a flag against the provided company and user context.
     *
     * @param flag the flag definition from the datastream cache
     * @param company the company data (may be null)
     * @param user the user data (may be null)
     * @return the evaluation result
     * @throws Exception if evaluation fails
     */
    RulesengineCheckFlagResult checkFlag(RulesengineFlag flag, RulesengineCompany company, RulesengineUser user)
            throws Exception;
}
