/**
 * This file was auto-generated by Fern from our API Definition.
 */
package com.schematic.api.resources.companies.types;

import com.fasterxml.jackson.annotation.JsonValue;

public enum CountEntityTraitDefinitionsRequestEntityType {
    COMPANY("company"),

    USER("user");

    private final String value;

    CountEntityTraitDefinitionsRequestEntityType(String value) {
        this.value = value;
    }

    @JsonValue
    @java.lang.Override
    public String toString() {
        return this.value;
    }
}
