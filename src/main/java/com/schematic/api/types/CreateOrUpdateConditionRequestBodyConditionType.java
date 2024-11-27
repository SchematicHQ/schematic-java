/**
 * This file was auto-generated by Fern from our API Definition.
 */
package com.schematic.api.types;

import com.fasterxml.jackson.annotation.JsonValue;

public enum CreateOrUpdateConditionRequestBodyConditionType {
    COMPANY("company"),

    METRIC("metric"),

    TRAIT("trait"),

    USER("user"),

    PLAN("plan"),

    BILLING_PRODUCT("billing_product"),

    CRM_PRODUCT("crm_product"),

    BASE_PLAN("base_plan");

    private final String value;

    CreateOrUpdateConditionRequestBodyConditionType(String value) {
        this.value = value;
    }

    @JsonValue
    @java.lang.Override
    public String toString() {
        return this.value;
    }
}
