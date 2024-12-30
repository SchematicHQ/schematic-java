/**
 * This file was auto-generated by Fern from our API Definition.
 */
package com.schematic.api.resources.entitlements.requests;

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
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_ABSENT)
@JsonDeserialize(builder = GetFeatureUsageByCompanyRequest.Builder.class)
public final class GetFeatureUsageByCompanyRequest {
    private final Map<String, JsonNode> keys;

    private final Map<String, Object> additionalProperties;

    private GetFeatureUsageByCompanyRequest(Map<String, JsonNode> keys, Map<String, Object> additionalProperties) {
        this.keys = keys;
        this.additionalProperties = additionalProperties;
    }

    /**
     * @return Key/value pairs
     */
    @JsonProperty("keys")
    public Map<String, JsonNode> getKeys() {
        return keys;
    }

    @java.lang.Override
    public boolean equals(Object other) {
        if (this == other) return true;
        return other instanceof GetFeatureUsageByCompanyRequest && equalTo((GetFeatureUsageByCompanyRequest) other);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    private boolean equalTo(GetFeatureUsageByCompanyRequest other) {
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
        private Map<String, JsonNode> keys = new LinkedHashMap<>();

        @JsonAnySetter
        private Map<String, Object> additionalProperties = new HashMap<>();

        private Builder() {}

        public Builder from(GetFeatureUsageByCompanyRequest other) {
            keys(other.getKeys());
            return this;
        }

        @JsonSetter(value = "keys", nulls = Nulls.SKIP)
        public Builder keys(Map<String, JsonNode> keys) {
            this.keys.clear();
            this.keys.putAll(keys);
            return this;
        }

        public Builder putAllKeys(Map<String, JsonNode> keys) {
            this.keys.putAll(keys);
            return this;
        }

        public Builder keys(String key, JsonNode value) {
            this.keys.put(key, value);
            return this;
        }

        public GetFeatureUsageByCompanyRequest build() {
            return new GetFeatureUsageByCompanyRequest(keys, additionalProperties);
        }
    }
}
