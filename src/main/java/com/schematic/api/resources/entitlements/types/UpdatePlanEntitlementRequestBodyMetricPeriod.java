/**
 * This file was auto-generated by Fern from our API Definition.
 */
package com.schematic.api.resources.entitlements.types;

import com.fasterxml.jackson.annotation.JsonValue;

public enum UpdatePlanEntitlementRequestBodyMetricPeriod {
    ALL_TIME("all_time"),

    BILLING("billing"),

    CURRENT_MONTH("current_month"),

    CURRENT_WEEK("current_week"),

    CURRENT_DAY("current_day");

    private final String value;

    UpdatePlanEntitlementRequestBodyMetricPeriod(String value) {
        this.value = value;
    }

    @JsonValue
    @java.lang.Override
    public String toString() {
        return this.value;
    }
}
