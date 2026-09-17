package com.schematic.api.credits;

import com.schematic.api.types.EventBodyFlagCheck;

/**
 * Reports a flag_check event for a check the credit flow resolved itself. The plain check paths
 * enqueue one per check, so without this a credit-gated check would be invisible to flag-check
 * analytics and to company last-seen. Fallback exits do not call it: the plain check they defer to
 * reports its own.
 */
public interface FlagCheckReporter {
    void report(EventBodyFlagCheck body);
}
