/**
 * This file was auto-generated by Fern from our API Definition.
 */
package com.schematic.api.resources.entitlements.types;

import com.fasterxml.jackson.annotation.JsonValue;

public enum UpdatePlanEntitlementRequestBodyValueType {
    BOOLEAN("boolean"),

    NUMERIC("numeric"),

    TRAIT("trait"),

    UNLIMITED("unlimited");

    private final String value;

    UpdatePlanEntitlementRequestBodyValueType(String value) {
        this.value = value;
    }

    @JsonValue
    @java.lang.Override
    public String toString() {
        return this.value;
    }
}
