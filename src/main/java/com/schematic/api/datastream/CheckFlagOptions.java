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
    private final Double usage;
    private final String eventSubtype;
    private final Double eventQuantity;
    private final EventQuantities eventQuantities;

    private CheckFlagOptions(
            Map<String, Double> creditCost,
            Double usage,
            String eventSubtype,
            Double eventQuantity,
            EventQuantities eventQuantities) {
        this.creditCost = creditCost == null || creditCost.isEmpty()
                ? null
                : Collections.unmodifiableMap(new LinkedHashMap<>(creditCost));
        this.usage = usage;
        this.eventSubtype = eventSubtype;
        this.eventQuantity = eventQuantity;
        this.eventQuantities = eventQuantities;
    }

    /** Prices the action per credit type, bypassing the engine's own quantity times rate arithmetic. */
    public static CheckFlagOptions creditCost(Map<String, Double> creditCost) {
        return new CheckFlagOptions(creditCost, null, null, null, null);
    }

    /** A simulated quantity, unscoped. */
    public static CheckFlagOptions usage(double usage) {
        return new CheckFlagOptions(null, usage, null, null, null);
    }

    /** A simulated quantity scoped to the event subtype whose condition should answer it. */
    public static CheckFlagOptions eventUsage(String eventSubtype, double quantity) {
        return new CheckFlagOptions(null, null, eventSubtype, quantity, null);
    }

    /**
     * An event priced against credit-balance conditions whose event subtype matches, the way the
     * API burns it: {@code quantity} times the condition's consumption rate, plus each named
     * quantity times its rate in the condition's quantity rates. For an inference call, {@code
     * quantity} is the request count and {@code quantities} the token counts as the event reports
     * them (input tokens including the cached and cache-creation subsets). Keys without a rate cost
     * nothing.
     *
     * <p>A null or zero {@code quantity} means one. The engine reads these values as decimals, so
     * unlike {@link #eventUsage} they are not rounded. On credit-balance conditions this ranks below
     * {@link #creditCost} and above {@link #eventUsage} and {@link #usage}; negative values make the
     * engine return an error.
     *
     * @param quantities the named quantities, or null for none; copied
     */
    public static CheckFlagOptions eventQuantities(
            String eventSubtype, Double quantity, Map<String, Double> quantities) {
        return new CheckFlagOptions(null, null, null, null, new EventQuantities(eventSubtype, quantity, quantities));
    }

    public Map<String, Double> getCreditCost() {
        return creditCost;
    }

    public Double getUsage() {
        return usage;
    }

    public String getEventSubtype() {
        return eventSubtype;
    }

    public Double getEventQuantity() {
        return eventQuantity;
    }

    /** The event to price from the condition's quantity rates, or null when none was given. */
    public EventQuantities getEventQuantities() {
        return eventQuantities;
    }

    /** An event described by its base quantity and named quantities. */
    public static final class EventQuantities {
        private final String eventSubtype;
        private final Double quantity;
        private final Map<String, Double> quantities;

        private EventQuantities(String eventSubtype, Double quantity, Map<String, Double> quantities) {
            this.eventSubtype = eventSubtype;
            this.quantity = quantity;
            this.quantities = quantities == null || quantities.isEmpty()
                    ? null
                    : Collections.unmodifiableMap(new LinkedHashMap<>(quantities));
        }

        public String getEventSubtype() {
            return eventSubtype;
        }

        /** The base quantity, or null for one. */
        public Double getQuantity() {
            return quantity;
        }

        /** The named quantities, or null when there are none. */
        public Map<String, Double> getQuantities() {
            return quantities;
        }
    }
}
