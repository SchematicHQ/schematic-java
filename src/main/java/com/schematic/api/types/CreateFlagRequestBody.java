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
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.schematic.api.core.ObjectMappers;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@JsonInclude(JsonInclude.Include.NON_ABSENT)
@JsonDeserialize(builder = CreateFlagRequestBody.Builder.class)
public final class CreateFlagRequestBody {
    private final boolean defaultValue;

    private final String description;

    private final Optional<String> featureId;

    private final String flagType;

    private final String key;

    private final Optional<String> maintainerId;

    private final String name;

    private final Map<String, Object> additionalProperties;

    private CreateFlagRequestBody(
            boolean defaultValue,
            String description,
            Optional<String> featureId,
            String flagType,
            String key,
            Optional<String> maintainerId,
            String name,
            Map<String, Object> additionalProperties) {
        this.defaultValue = defaultValue;
        this.description = description;
        this.featureId = featureId;
        this.flagType = flagType;
        this.key = key;
        this.maintainerId = maintainerId;
        this.name = name;
        this.additionalProperties = additionalProperties;
    }

    @JsonProperty("default_value")
    public boolean getDefaultValue() {
        return defaultValue;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("feature_id")
    public Optional<String> getFeatureId() {
        return featureId;
    }

    @JsonProperty("flag_type")
    public String getFlagType() {
        return flagType;
    }

    @JsonProperty("key")
    public String getKey() {
        return key;
    }

    @JsonProperty("maintainer_id")
    public Optional<String> getMaintainerId() {
        return maintainerId;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @java.lang.Override
    public boolean equals(Object other) {
        if (this == other) return true;
        return other instanceof CreateFlagRequestBody && equalTo((CreateFlagRequestBody) other);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    private boolean equalTo(CreateFlagRequestBody other) {
        return defaultValue == other.defaultValue
                && description.equals(other.description)
                && featureId.equals(other.featureId)
                && flagType.equals(other.flagType)
                && key.equals(other.key)
                && maintainerId.equals(other.maintainerId)
                && name.equals(other.name);
    }

    @java.lang.Override
    public int hashCode() {
        return Objects.hash(
                this.defaultValue,
                this.description,
                this.featureId,
                this.flagType,
                this.key,
                this.maintainerId,
                this.name);
    }

    @java.lang.Override
    public String toString() {
        return ObjectMappers.stringify(this);
    }

    public static DefaultValueStage builder() {
        return new Builder();
    }

    public interface DefaultValueStage {
        DescriptionStage defaultValue(boolean defaultValue);

        Builder from(CreateFlagRequestBody other);
    }

    public interface DescriptionStage {
        FlagTypeStage description(String description);
    }

    public interface FlagTypeStage {
        KeyStage flagType(String flagType);
    }

    public interface KeyStage {
        NameStage key(String key);
    }

    public interface NameStage {
        _FinalStage name(String name);
    }

    public interface _FinalStage {
        CreateFlagRequestBody build();

        _FinalStage featureId(Optional<String> featureId);

        _FinalStage featureId(String featureId);

        _FinalStage maintainerId(Optional<String> maintainerId);

        _FinalStage maintainerId(String maintainerId);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static final class Builder
            implements DefaultValueStage, DescriptionStage, FlagTypeStage, KeyStage, NameStage, _FinalStage {
        private boolean defaultValue;

        private String description;

        private String flagType;

        private String key;

        private String name;

        private Optional<String> maintainerId = Optional.empty();

        private Optional<String> featureId = Optional.empty();

        @JsonAnySetter
        private Map<String, Object> additionalProperties = new HashMap<>();

        private Builder() {}

        @java.lang.Override
        public Builder from(CreateFlagRequestBody other) {
            defaultValue(other.getDefaultValue());
            description(other.getDescription());
            featureId(other.getFeatureId());
            flagType(other.getFlagType());
            key(other.getKey());
            maintainerId(other.getMaintainerId());
            name(other.getName());
            return this;
        }

        @java.lang.Override
        @JsonSetter("default_value")
        public DescriptionStage defaultValue(boolean defaultValue) {
            this.defaultValue = defaultValue;
            return this;
        }

        @java.lang.Override
        @JsonSetter("description")
        public FlagTypeStage description(String description) {
            this.description = description;
            return this;
        }

        @java.lang.Override
        @JsonSetter("flag_type")
        public KeyStage flagType(String flagType) {
            this.flagType = flagType;
            return this;
        }

        @java.lang.Override
        @JsonSetter("key")
        public NameStage key(String key) {
            this.key = key;
            return this;
        }

        @java.lang.Override
        @JsonSetter("name")
        public _FinalStage name(String name) {
            this.name = name;
            return this;
        }

        @java.lang.Override
        public _FinalStage maintainerId(String maintainerId) {
            this.maintainerId = Optional.ofNullable(maintainerId);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "maintainer_id", nulls = Nulls.SKIP)
        public _FinalStage maintainerId(Optional<String> maintainerId) {
            this.maintainerId = maintainerId;
            return this;
        }

        @java.lang.Override
        public _FinalStage featureId(String featureId) {
            this.featureId = Optional.ofNullable(featureId);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "feature_id", nulls = Nulls.SKIP)
        public _FinalStage featureId(Optional<String> featureId) {
            this.featureId = featureId;
            return this;
        }

        @java.lang.Override
        public CreateFlagRequestBody build() {
            return new CreateFlagRequestBody(
                    defaultValue, description, featureId, flagType, key, maintainerId, name, additionalProperties);
        }
    }
}
