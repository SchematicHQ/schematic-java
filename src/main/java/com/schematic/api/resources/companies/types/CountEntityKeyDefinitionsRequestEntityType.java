/**
 * This file was auto-generated by Fern from our API Definition.
 */
package com.schematic.api.resources.companies.types;

import com.fasterxml.jackson.annotation.JsonValue;

public enum CountEntityKeyDefinitionsRequestEntityType {
    COMPANY("company"),

    USER("user");

    private final String value;

    CountEntityKeyDefinitionsRequestEntityType(String value) {
        this.value = value;
    }

    @JsonValue
    @java.lang.Override
    public String toString() {
        return this.value;
    }
}
