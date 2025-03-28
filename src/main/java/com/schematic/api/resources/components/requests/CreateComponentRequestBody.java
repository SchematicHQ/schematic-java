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
import com.schematic.api.resources.components.types.CreateComponentRequestBodyEntityType;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;

@JsonInclude(JsonInclude.Include.NON_ABSENT)
@JsonDeserialize(builder = CreateComponentRequestBody.Builder.class)
public final class CreateComponentRequestBody {
    private final Optional<Map<String, Double>> ast;

    private final CreateComponentRequestBodyEntityType entityType;

    private final String name;

    private final Map<String, Object> additionalProperties;

    private CreateComponentRequestBody(
            Optional<Map<String, Double>> ast,
            CreateComponentRequestBodyEntityType entityType,
            String name,
            Map<String, Object> additionalProperties) {
        this.ast = ast;
        this.entityType = entityType;
        this.name = name;
        this.additionalProperties = additionalProperties;
    }

    @JsonProperty("ast")
    public Optional<Map<String, Double>> getAst() {
        return ast;
    }

    @JsonProperty("entity_type")
    public CreateComponentRequestBodyEntityType getEntityType() {
        return entityType;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @java.lang.Override
    public boolean equals(Object other) {
        if (this == other) return true;
        return other instanceof CreateComponentRequestBody && equalTo((CreateComponentRequestBody) other);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    private boolean equalTo(CreateComponentRequestBody other) {
        return ast.equals(other.ast) && entityType.equals(other.entityType) && name.equals(other.name);
    }

    @java.lang.Override
    public int hashCode() {
        return Objects.hash(this.ast, this.entityType, this.name);
    }

    @java.lang.Override
    public String toString() {
        return ObjectMappers.stringify(this);
    }

    public static EntityTypeStage builder() {
        return new Builder();
    }

    public interface EntityTypeStage {
        NameStage entityType(@NotNull CreateComponentRequestBodyEntityType entityType);

        Builder from(CreateComponentRequestBody other);
    }

    public interface NameStage {
        _FinalStage name(@NotNull String name);
    }

    public interface _FinalStage {
        CreateComponentRequestBody build();

        _FinalStage ast(Optional<Map<String, Double>> ast);

        _FinalStage ast(Map<String, Double> ast);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static final class Builder implements EntityTypeStage, NameStage, _FinalStage {
        private CreateComponentRequestBodyEntityType entityType;

        private String name;

        private Optional<Map<String, Double>> ast = Optional.empty();

        @JsonAnySetter
        private Map<String, Object> additionalProperties = new HashMap<>();

        private Builder() {}

        @java.lang.Override
        public Builder from(CreateComponentRequestBody other) {
            ast(other.getAst());
            entityType(other.getEntityType());
            name(other.getName());
            return this;
        }

        @java.lang.Override
        @JsonSetter("entity_type")
        public NameStage entityType(@NotNull CreateComponentRequestBodyEntityType entityType) {
            this.entityType = Objects.requireNonNull(entityType, "entityType must not be null");
            return this;
        }

        @java.lang.Override
        @JsonSetter("name")
        public _FinalStage name(@NotNull String name) {
            this.name = Objects.requireNonNull(name, "name must not be null");
            return this;
        }

        @java.lang.Override
        public _FinalStage ast(Map<String, Double> ast) {
            this.ast = Optional.ofNullable(ast);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "ast", nulls = Nulls.SKIP)
        public _FinalStage ast(Optional<Map<String, Double>> ast) {
            this.ast = ast;
            return this;
        }

        @java.lang.Override
        public CreateComponentRequestBody build() {
            return new CreateComponentRequestBody(ast, entityType, name, additionalProperties);
        }
    }
}
