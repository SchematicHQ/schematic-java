/**
 * This file was auto-generated by Fern from our API Definition.
 */
package com.schematic.api.resources.components.types;

import com.fasterxml.jackson.annotation.JsonValue;

public enum UpdateComponentRequestBodyEntityType {
    ENTITLEMENT("entitlement"),

    BILLING("billing");

    private final String value;

    UpdateComponentRequestBodyEntityType(String value) {
        this.value = value;
    }

    @JsonValue
    @java.lang.Override
    public String toString() {
        return this.value;
    }
}
