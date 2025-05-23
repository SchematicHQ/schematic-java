/**
 * This file was auto-generated by Fern from our API Definition.
 */
package com.schematic.api.resources.components.requests;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.schematic.api.core.ObjectMappers;
import com.schematic.api.resources.components.types.UpdateComponentRequestBodyEntityType;
import com.schematic.api.resources.components.types.UpdateComponentRequestBodyState;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@JsonInclude(JsonInclude.Include.NON_ABSENT)
@JsonDeserialize(builder = UpdateComponentRequestBody.Builder.class)
public final class UpdateComponentRequestBody {
    private final Optional<Map<String, Double>> ast;

    private final Optional<UpdateComponentRequestBodyEntityType> entityType;

    private final Optional<String> name;

    private final Optional<UpdateComponentRequestBodyState> state;

    private final Map<String, Object> additionalProperties;

    private UpdateComponentRequestBody(
            Optional<Map<String, Double>> ast,
            Optional<UpdateComponentRequestBodyEntityType> entityType,
            Optional<String> name,
            Optional<UpdateComponentRequestBodyState> state,
            Map<String, Object> additionalProperties) {
        this.ast = ast;
        this.entityType = entityType;
        this.name = name;
        this.state = state;
        this.additionalProperties = additionalProperties;
    }

    @JsonProperty("ast")
    public Optional<Map<String, Double>> getAst() {
        return ast;
    }

    @JsonProperty("entity_type")
    public Optional<UpdateComponentRequestBodyEntityType> getEntityType() {
        return entityType;
    }

    @JsonProperty("name")
    public Optional<String> getName() {
        return name;
    }

    @JsonProperty("state")
    public Optional<UpdateComponentRequestBodyState> getState() {
        return state;
    }

    @java.lang.Override
    public boolean equals(Object other) {
        if (this == other) return true;
        return other instanceof UpdateComponentRequestBody && equalTo((UpdateComponentRequestBody) other);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    private boolean equalTo(UpdateComponentRequestBody other) {
        return ast.equals(other.ast)
                && entityType.equals(other.entityType)
                && name.equals(other.name)
                && state.equals(other.state);
    }

    @java.lang.Override
    public int hashCode() {
        return Objects.hash(this.ast, this.entityType, this.name, this.state);
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
        private Optional<Map<String, Double>> ast = Optional.empty();

        private Optional<UpdateComponentRequestBodyEntityType> entityType = Optional.empty();

        private Optional<String> name = Optional.empty();

        private Optional<UpdateComponentRequestBodyState> state = Optional.empty();

        @JsonAnySetter
        private Map<String, Object> additionalProperties = new HashMap<>();

        private Builder() {}

        public Builder from(UpdateComponentRequestBody other) {
            ast(other.getAst());
            entityType(other.getEntityType());
            name(other.getName());
            state(other.getState());
            return this;
        }

        @JsonSetter(value = "ast", nulls = Nulls.SKIP)
        public Builder ast(Optional<Map<String, Double>> ast) {
            this.ast = ast;
            return this;
        }

        public Builder ast(Map<String, Double> ast) {
            this.ast = Optional.ofNullable(ast);
            return this;
        }

        @JsonSetter(value = "entity_type", nulls = Nulls.SKIP)
        public Builder entityType(Optional<UpdateComponentRequestBodyEntityType> entityType) {
            this.entityType = entityType;
            return this;
        }

        public Builder entityType(UpdateComponentRequestBodyEntityType entityType) {
            this.entityType = Optional.ofNullable(entityType);
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

        @JsonSetter(value = "state", nulls = Nulls.SKIP)
        public Builder state(Optional<UpdateComponentRequestBodyState> state) {
            this.state = state;
            return this;
        }

        public Builder state(UpdateComponentRequestBodyState state) {
            this.state = Optional.ofNullable(state);
            return this;
        }

        public UpdateComponentRequestBody build() {
            return new UpdateComponentRequestBody(ast, entityType, name, state, additionalProperties);
        }
    }
}
