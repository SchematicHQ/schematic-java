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
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.schematic.api.core.ObjectMappers;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;

@JsonInclude(JsonInclude.Include.NON_ABSENT)
@JsonDeserialize(builder = EntityTraitValue.Builder.class)
public final class EntityTraitValue {
    private final String definitionId;

    private final String value;

    private final Map<String, Object> additionalProperties;

    private EntityTraitValue(String definitionId, String value, Map<String, Object> additionalProperties) {
        this.definitionId = definitionId;
        this.value = value;
        this.additionalProperties = additionalProperties;
    }

    @JsonProperty("definition_id")
    public String getDefinitionId() {
        return definitionId;
    }

    @JsonProperty("value")
    public String getValue() {
        return value;
    }

    @java.lang.Override
    public boolean equals(Object other) {
        if (this == other) return true;
        return other instanceof EntityTraitValue && equalTo((EntityTraitValue) other);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    private boolean equalTo(EntityTraitValue other) {
        return definitionId.equals(other.definitionId) && value.equals(other.value);
    }

    @java.lang.Override
    public int hashCode() {
        return Objects.hash(this.definitionId, this.value);
    }

    @java.lang.Override
    public String toString() {
        return ObjectMappers.stringify(this);
    }

    public static DefinitionIdStage builder() {
        return new Builder();
    }

    public interface DefinitionIdStage {
        ValueStage definitionId(@NotNull String definitionId);

        Builder from(EntityTraitValue other);
    }

    public interface ValueStage {
        _FinalStage value(@NotNull String value);
    }

    public interface _FinalStage {
        EntityTraitValue build();
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static final class Builder implements DefinitionIdStage, ValueStage, _FinalStage {
        private String definitionId;

        private String value;

        @JsonAnySetter
        private Map<String, Object> additionalProperties = new HashMap<>();

        private Builder() {}

        @java.lang.Override
        public Builder from(EntityTraitValue other) {
            definitionId(other.getDefinitionId());
            value(other.getValue());
            return this;
        }

        @java.lang.Override
        @JsonSetter("definition_id")
        public ValueStage definitionId(@NotNull String definitionId) {
            this.definitionId = Objects.requireNonNull(definitionId, "definitionId must not be null");
            return this;
        }

        @java.lang.Override
        @JsonSetter("value")
        public _FinalStage value(@NotNull String value) {
            this.value = Objects.requireNonNull(value, "value must not be null");
            return this;
        }

        @java.lang.Override
        public EntityTraitValue build() {
            return new EntityTraitValue(definitionId, value, additionalProperties);
        }
    }
}
