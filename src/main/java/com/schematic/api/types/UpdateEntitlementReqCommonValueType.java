/**
 * This file was auto-generated by Fern from our API Definition.
 */
package com.schematic.api.types;

import com.fasterxml.jackson.annotation.JsonValue;

public enum UpdateEntitlementReqCommonValueType {
    BOOLEAN("boolean"),

    NUMERIC("numeric"),

    TRAIT("trait"),

    UNLIMITED("unlimited");

    private final String value;

    UpdateEntitlementReqCommonValueType(String value) {
        this.value = value;
    }

    @JsonValue
    @java.lang.Override
    public String toString() {
        return this.value;
    }
}
