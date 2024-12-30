/**
 * This file was auto-generated by Fern from our API Definition.
 */
package com.schematic.api.types;

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
import java.util.Optional;

@JsonInclude(JsonInclude.Include.NON_ABSENT)
@JsonDeserialize(builder = EventBodyIdentify.Builder.class)
public final class EventBodyIdentify {
    private final Optional<EventBodyIdentifyCompany> company;

    private final Map<String, String> keys;

    private final Optional<String> name;

    private final Optional<Map<String, JsonNode>> traits;

    private final Map<String, Object> additionalProperties;

    private EventBodyIdentify(
            Optional<EventBodyIdentifyCompany> company,
            Map<String, String> keys,
            Optional<String> name,
            Optional<Map<String, JsonNode>> traits,
            Map<String, Object> additionalProperties) {
        this.company = company;
        this.keys = keys;
        this.name = name;
        this.traits = traits;
        this.additionalProperties = additionalProperties;
    }

    /**
     * @return Information about the company associated with the user; required only if it is a new user
     */
    @JsonProperty("company")
    public Optional<EventBodyIdentifyCompany> getCompany() {
        return company;
    }

    /**
     * @return Key-value pairs to identify the user
     */
    @JsonProperty("keys")
    public Map<String, String> getKeys() {
        return keys;
    }

    /**
     * @return The display name of the user being identified; required only if it is a new user
     */
    @JsonProperty("name")
    public Optional<String> getName() {
        return name;
    }

    /**
     * @return A map of trait names to trait values
     */
    @JsonProperty("traits")
    public Optional<Map<String, JsonNode>> getTraits() {
        return traits;
    }

    @java.lang.Override
    public boolean equals(Object other) {
        if (this == other) return true;
        return other instanceof EventBodyIdentify && equalTo((EventBodyIdentify) other);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    private boolean equalTo(EventBodyIdentify other) {
        return company.equals(other.company)
                && keys.equals(other.keys)
                && name.equals(other.name)
                && traits.equals(other.traits);
    }

    @java.lang.Override
    public int hashCode() {
        return Objects.hash(this.company, this.keys, this.name, this.traits);
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
        private Optional<EventBodyIdentifyCompany> company = Optional.empty();

        private Map<String, String> keys = new LinkedHashMap<>();

        private Optional<String> name = Optional.empty();

        private Optional<Map<String, JsonNode>> traits = Optional.empty();

        @JsonAnySetter
        private Map<String, Object> additionalProperties = new HashMap<>();

        private Builder() {}

        public Builder from(EventBodyIdentify other) {
            company(other.getCompany());
            keys(other.getKeys());
            name(other.getName());
            traits(other.getTraits());
            return this;
        }

        @JsonSetter(value = "company", nulls = Nulls.SKIP)
        public Builder company(Optional<EventBodyIdentifyCompany> company) {
            this.company = company;
            return this;
        }

        public Builder company(EventBodyIdentifyCompany company) {
            this.company = Optional.ofNullable(company);
            return this;
        }

        @JsonSetter(value = "keys", nulls = Nulls.SKIP)
        public Builder keys(Map<String, String> keys) {
            this.keys.clear();
            this.keys.putAll(keys);
            return this;
        }

        public Builder putAllKeys(Map<String, String> keys) {
            this.keys.putAll(keys);
            return this;
        }

        public Builder keys(String key, String value) {
            this.keys.put(key, value);
            return this;
        }

        @JsonSetter(value = "name", nulls = Nulls.SKIP)
        public Builder name(Optional<String> name) {
            this.name = name;
            return this;
        }

        public Builder name(String name) {
            this.name = Optional.ofNullable(name);
            return this;
        }

        @JsonSetter(value = "traits", nulls = Nulls.SKIP)
        public Builder traits(Optional<Map<String, JsonNode>> traits) {
            this.traits = traits;
            return this;
        }

        public Builder traits(Map<String, JsonNode> traits) {
            this.traits = Optional.ofNullable(traits);
            return this;
        }

        public EventBodyIdentify build() {
            return new EventBodyIdentify(company, keys, name, traits, additionalProperties);
        }
    }
}
