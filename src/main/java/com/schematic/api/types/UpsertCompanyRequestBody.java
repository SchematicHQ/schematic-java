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
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@JsonInclude(JsonInclude.Include.NON_ABSENT)
@JsonDeserialize(builder = UpsertCompanyRequestBody.Builder.class)
public final class UpsertCompanyRequestBody {
    private final Optional<String> id;

    private final Map<String, String> keys;

    private final Optional<OffsetDateTime> lastSeenAt;

    private final Optional<String> name;

    private final Optional<Map<String, JsonNode>> traits;

    private final Optional<Boolean> updateOnly;

    private final Map<String, Object> additionalProperties;

    private UpsertCompanyRequestBody(
            Optional<String> id,
            Map<String, String> keys,
            Optional<OffsetDateTime> lastSeenAt,
            Optional<String> name,
            Optional<Map<String, JsonNode>> traits,
            Optional<Boolean> updateOnly,
            Map<String, Object> additionalProperties) {
        this.id = id;
        this.keys = keys;
        this.lastSeenAt = lastSeenAt;
        this.name = name;
        this.traits = traits;
        this.updateOnly = updateOnly;
        this.additionalProperties = additionalProperties;
    }

    /**
     * @return If you know the Schematic ID, you can use that here instead of keys
     */
    @JsonProperty("id")
    public Optional<String> getId() {
        return id;
    }

    @JsonProperty("keys")
    public Map<String, String> getKeys() {
        return keys;
    }

    @JsonProperty("last_seen_at")
    public Optional<OffsetDateTime> getLastSeenAt() {
        return lastSeenAt;
    }

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

    @JsonProperty("update_only")
    public Optional<Boolean> getUpdateOnly() {
        return updateOnly;
    }

    @java.lang.Override
    public boolean equals(Object other) {
        if (this == other) return true;
        return other instanceof UpsertCompanyRequestBody && equalTo((UpsertCompanyRequestBody) other);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    private boolean equalTo(UpsertCompanyRequestBody other) {
        return id.equals(other.id)
                && keys.equals(other.keys)
                && lastSeenAt.equals(other.lastSeenAt)
                && name.equals(other.name)
                && traits.equals(other.traits)
                && updateOnly.equals(other.updateOnly);
    }

    @java.lang.Override
    public int hashCode() {
        return Objects.hash(this.id, this.keys, this.lastSeenAt, this.name, this.traits, this.updateOnly);
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
        private Optional<String> id = Optional.empty();

        private Map<String, String> keys = new LinkedHashMap<>();

        private Optional<OffsetDateTime> lastSeenAt = Optional.empty();

        private Optional<String> name = Optional.empty();

        private Optional<Map<String, JsonNode>> traits = Optional.empty();

        private Optional<Boolean> updateOnly = Optional.empty();

        @JsonAnySetter
        private Map<String, Object> additionalProperties = new HashMap<>();

        private Builder() {}

        public Builder from(UpsertCompanyRequestBody other) {
            id(other.getId());
            keys(other.getKeys());
            lastSeenAt(other.getLastSeenAt());
            name(other.getName());
            traits(other.getTraits());
            updateOnly(other.getUpdateOnly());
            return this;
        }

        @JsonSetter(value = "id", nulls = Nulls.SKIP)
        public Builder id(Optional<String> id) {
            this.id = id;
            return this;
        }

        public Builder id(String id) {
            this.id = Optional.ofNullable(id);
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

        @JsonSetter(value = "last_seen_at", nulls = Nulls.SKIP)
        public Builder lastSeenAt(Optional<OffsetDateTime> lastSeenAt) {
            this.lastSeenAt = lastSeenAt;
            return this;
        }

        public Builder lastSeenAt(OffsetDateTime lastSeenAt) {
            this.lastSeenAt = Optional.ofNullable(lastSeenAt);
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

        @JsonSetter(value = "update_only", nulls = Nulls.SKIP)
        public Builder updateOnly(Optional<Boolean> updateOnly) {
            this.updateOnly = updateOnly;
            return this;
        }

        public Builder updateOnly(Boolean updateOnly) {
            this.updateOnly = Optional.ofNullable(updateOnly);
            return this;
        }

        public UpsertCompanyRequestBody build() {
            return new UpsertCompanyRequestBody(id, keys, lastSeenAt, name, traits, updateOnly, additionalProperties);
        }
    }
}
