package com.schematic.api.datastream;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The preflight a local evaluation answers: what the call being gated is about to cost, before it
 * has been recorded. Serialized into the {@code options} envelope the rules engine reads.
 */
public final class CheckFlagOptions {

    private final Map<String, Double> creditCost;
    private final Long usage;
    private final String eventSubtype;
    private final Long eventQuantity;

    private CheckFlagOptions(Map<String, Double> creditCost, Long usage, String eventSubtype, Long eventQuantity) {
        this.creditCost = creditCost == null || creditCost.isEmpty()
                ? null
                : Collections.unmodifiableMap(new LinkedHashMap<>(creditCost));
        this.usage = usage;
        this.eventSubtype = eventSubtype;
        this.eventQuantity = eventQuantity;
    }

    /** Prices the action per credit type, bypassing the engine's own quantity times rate arithmetic. */
    public static CheckFlagOptions creditCost(Map<String, Double> creditCost) {
        return new CheckFlagOptions(creditCost, null, null, null);
    }

    /** A simulated quantity, unscoped. */
    public static CheckFlagOptions usage(long usage) {
        return new CheckFlagOptions(null, usage, null, null);
    }

    /** A simulated quantity scoped to the event subtype whose condition should answer it. */
    public static CheckFlagOptions eventUsage(String eventSubtype, long quantity) {
        return new CheckFlagOptions(null, null, eventSubtype, quantity);
    }

    public Map<String, Double> getCreditCost() {
        return creditCost;
    }

    public Long getUsage() {
        return usage;
    }

    public String getEventSubtype() {
        return eventSubtype;
    }

    public Long getEventQuantity() {
        return eventQuantity;
    }
}
