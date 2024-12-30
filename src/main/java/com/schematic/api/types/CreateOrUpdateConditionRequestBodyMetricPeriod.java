/**
 * This file was auto-generated by Fern from our API Definition.
 */
package com.schematic.api.types;

import com.fasterxml.jackson.annotation.JsonValue;

public enum CreateOrUpdateConditionRequestBodyMetricPeriod {
    ALL_TIME("all_time"),

    CURRENT_MONTH("current_month"),

    CURRENT_WEEK("current_week"),

    CURRENT_DAY("current_day");

    private final String value;

    CreateOrUpdateConditionRequestBodyMetricPeriod(String value) {
        this.value = value;
    }

    @JsonValue
    @java.lang.Override
    public String toString() {
        return this.value;
    }
}
