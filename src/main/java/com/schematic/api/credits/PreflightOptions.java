package com.schematic.api.credits;

import com.schematic.api.types.PreflightEventUsageRequestBody;
import com.schematic.api.types.PreflightRequestBody;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The preflight a credit-gated check asks an evaluation to answer: what this call is about to
 * cost, before it has been recorded.
 *
 * <p>Plain data rather than the engine's own option type, so the flow can be driven and asserted
 * on without a WASM runtime, and so the same value can be sent to the API and to the local engine.
 */
public final class PreflightOptions {

    /** A simulated quantity scoped to one event subtype. */
    public static final class EventUsage {
        private final String eventSubtype;
        private final double quantity;

        public EventUsage(String eventSubtype, double quantity) {
            this.eventSubtype = eventSubtype;
            this.quantity = quantity;
        }

        public String getEventSubtype() {
            return eventSubtype;
        }

        public double getQuantity() {
            return quantity;
        }
    }

    private final Map<String, Double> creditCost;
    private final Double usage;
    private final EventUsage eventUsage;

    private PreflightOptions(Map<String, Double> creditCost, Double usage, EventUsage eventUsage) {
        this.creditCost = creditCost == null || creditCost.isEmpty()
                ? null
                : Collections.unmodifiableMap(new LinkedHashMap<>(creditCost));
        this.usage = usage;
        this.eventUsage = eventUsage;
    }

    /**
     * The preflight a caller's usage implies. With a subtype the quantity goes out scoped to it so
     * the engine matches that subtype's condition; without one it goes out as the generic knob.
     * Returns null when there is no usage to declare.
     */
    public static PreflightOptions fromUsage(Double usage, String eventSubtype) {
        if (usage == null || !CreditAmounts.isValidQuantity(usage)) {
            return null;
        }
        // Kept as the caller gave it. The engine evaluates the real quantity, fraction and all;
        // only the wire body rounds, and only because its field is an integer.
        if (eventSubtype != null && !eventSubtype.isEmpty()) {
            return new PreflightOptions(null, null, new EventUsage(eventSubtype, usage));
        }
        return new PreflightOptions(null, usage, null);
    }

    /** Prices one credit type directly, bypassing the engine's own quantity times rate arithmetic. */
    public static PreflightOptions forCreditCost(String creditTypeId, double cost) {
        return new PreflightOptions(Collections.singletonMap(creditTypeId, cost), null, null);
    }

    /**
     * Casts a usage onto the integer the request body carries. A preflight asks an upper-bound
     * question, so a fraction rounds up: the check must not pass on less usage than the operation
     * is about to record. Only the wire needs this; a local evaluation reads the raw quantity.
     */
    public static long preflightQuantity(double usage) {
        return (long) Math.ceil(usage);
    }

    public Map<String, Double> getCreditCost() {
        return creditCost;
    }

    public Double getUsage() {
        return usage;
    }

    public EventUsage getEventUsage() {
        return eventUsage;
    }

    /**
     * The same preflight as the API's request body, for the paths that gate server-side. Null
     * when nothing in it would change the answer, so the caller can send a plain request.
     *
     * <p>A zero usage and a zero event quantity are dropped: the API documents them as having no
     * effect, and a request that carries one is still a preflighted request, which costs it the
     * flag check cache for nothing. A zero credit cost stays, because that one says something,
     * namely that this call is free rather than unpriced.
     */
    public PreflightRequestBody toRequestBody() {
        boolean hasUsage = usage != null && usage != 0;
        boolean hasEventUsage = eventUsage != null && eventUsage.getQuantity() != 0;
        if (creditCost == null && !hasUsage && !hasEventUsage) {
            return null;
        }
        PreflightRequestBody.Builder builder = PreflightRequestBody.builder();
        if (creditCost != null) {
            builder.creditCost(creditCost);
        }
        // The wire fields are integers, so the rounding happens here and nowhere else.
        if (hasUsage) {
            builder.usage(preflightQuantity(usage));
        }
        if (hasEventUsage) {
            builder.eventUsage(PreflightEventUsageRequestBody.builder()
                    .eventSubtype(eventUsage.getEventSubtype())
                    .quantity(preflightQuantity(eventUsage.getQuantity()))
                    .build());
        }
        return builder.build();
    }
}
