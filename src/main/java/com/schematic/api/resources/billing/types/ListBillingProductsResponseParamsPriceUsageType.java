/**
 * This file was auto-generated by Fern from our API Definition.
 */
package com.schematic.api.resources.billing.types;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ListBillingProductsResponseParamsPriceUsageType {
    LICENSED("licensed"),

    METERED("metered");

    private final String value;

    ListBillingProductsResponseParamsPriceUsageType(String value) {
        this.value = value;
    }

    @JsonValue
    @java.lang.Override
    public String toString() {
        return this.value;
    }
}
