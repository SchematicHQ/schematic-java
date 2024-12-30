/**
 * This file was auto-generated by Fern from our API Definition.
 */
package com.schematic.api.resources.companies.types;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.schematic.api.core.ObjectMappers;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@JsonInclude(JsonInclude.Include.NON_ABSENT)
@JsonDeserialize(builder = LookupCompanyParams.Builder.class)
public final class LookupCompanyParams {
    private final Optional<Map<String, JsonNode>> keys;

    private final Map<String, Object> additionalProperties;

    private LookupCompanyParams(Optional<Map<String, JsonNode>> keys, Map<String, Object> additionalProperties) {
        this.keys = keys;
        this.additionalProperties = additionalProperties;
    }

    @JsonProperty("keys")
    public Optional<Map<String, JsonNode>> getKeys() {
        return keys;
    }

    @java.lang.Override
    public boolean equals(Object other) {
        if (this == other) return true;
        return other instanceof LookupCompanyParams && equalTo((LookupCompanyParams) other);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    private boolean equalTo(LookupCompanyParams other) {
        return keys.equals(other.keys);
    }

    @java.lang.Override
    public int hashCode() {
        return Objects.hash(this.keys);
    }

    @java.lang.Override
    public String toString() {
        return ObjectMappers.stringify(this);
    }

    public static Builder builder() {
        return new Builder();
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static final class Builder {
        private Optional<Map<String, JsonNode>> keys = Optional.empty();

        @JsonAnySetter
        private Map<String, Object> additionalProperties = new HashMap<>();

        private Builder() {}

        public Builder from(LookupCompanyParams other) {
            keys(other.getKeys());
            return this;
        }

        @JsonSetter(value = "keys", nulls = Nulls.SKIP)
        public Builder keys(Optional<Map<String, JsonNode>> keys) {
            this.keys = keys;
            return this;
        }

        public Builder keys(Map<String, JsonNode> keys) {
            this.keys = Optional.ofNullable(keys);
            return this;
        }

        public LookupCompanyParams build() {
            return new LookupCompanyParams(keys, additionalProperties);
        }
    }
}
