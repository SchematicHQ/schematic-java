/**
 * This file was auto-generated by Fern from our API Definition.
 */
package com.schematic.api.types;

import com.fasterxml.jackson.annotation.JsonValue;

public enum CreateOrUpdateConditionRequestBodyMetricPeriodMonthReset {
    FIRST_OF_MONTH("first_of_month"),

    BILLING_CYCLE("billing_cycle");

    private final String value;

    CreateOrUpdateConditionRequestBodyMetricPeriodMonthReset(String value) {
        this.value = value;
    }

    @JsonValue
    @java.lang.Override
    public String toString() {
        return this.value;
    }
}
