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
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;

@JsonInclude(JsonInclude.Include.NON_ABSENT)
@JsonDeserialize(builder = EntityKeyResponseData.Builder.class)
public final class EntityKeyResponseData {
    private final OffsetDateTime createdAt;

    private final String definitionId;

    private final String entityId;

    private final String entityType;

    private final String environmentId;

    private final String id;

    private final String key;

    private final OffsetDateTime updatedAt;

    private final String value;

    private final Map<String, Object> additionalProperties;

    private EntityKeyResponseData(
            OffsetDateTime createdAt,
            String definitionId,
            String entityId,
            String entityType,
            String environmentId,
            String id,
            String key,
            OffsetDateTime updatedAt,
            String value,
            Map<String, Object> additionalProperties) {
        this.createdAt = createdAt;
        this.definitionId = definitionId;
        this.entityId = entityId;
        this.entityType = entityType;
        this.environmentId = environmentId;
        this.id = id;
        this.key = key;
        this.updatedAt = updatedAt;
        this.value = value;
        this.additionalProperties = additionalProperties;
    }

    @JsonProperty("created_at")
    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    @JsonProperty("definition_id")
    public String getDefinitionId() {
        return definitionId;
    }

    @JsonProperty("entity_id")
    public String getEntityId() {
        return entityId;
    }

    @JsonProperty("entity_type")
    public String getEntityType() {
        return entityType;
    }

    @JsonProperty("environment_id")
    public String getEnvironmentId() {
        return environmentId;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("key")
    public String getKey() {
        return key;
    }

    @JsonProperty("updated_at")
    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    @JsonProperty("value")
    public String getValue() {
        return value;
    }

    @java.lang.Override
    public boolean equals(Object other) {
        if (this == other) return true;
        return other instanceof EntityKeyResponseData && equalTo((EntityKeyResponseData) other);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    private boolean equalTo(EntityKeyResponseData other) {
        return createdAt.equals(other.createdAt)
                && definitionId.equals(other.definitionId)
                && entityId.equals(other.entityId)
                && entityType.equals(other.entityType)
                && environmentId.equals(other.environmentId)
                && id.equals(other.id)
                && key.equals(other.key)
                && updatedAt.equals(other.updatedAt)
                && value.equals(other.value);
    }

    @java.lang.Override
    public int hashCode() {
        return Objects.hash(
                this.createdAt,
                this.definitionId,
                this.entityId,
                this.entityType,
                this.environmentId,
                this.id,
                this.key,
                this.updatedAt,
                this.value);
    }

    @java.lang.Override
    public String toString() {
        return ObjectMappers.stringify(this);
    }

    public static CreatedAtStage builder() {
        return new Builder();
    }

    public interface CreatedAtStage {
        DefinitionIdStage createdAt(@NotNull OffsetDateTime createdAt);

        Builder from(EntityKeyResponseData other);
    }

    public interface DefinitionIdStage {
        EntityIdStage definitionId(@NotNull String definitionId);
    }

    public interface EntityIdStage {
        EntityTypeStage entityId(@NotNull String entityId);
    }

    public interface EntityTypeStage {
        EnvironmentIdStage entityType(@NotNull String entityType);
    }

    public interface EnvironmentIdStage {
        IdStage environmentId(@NotNull String environmentId);
    }

    public interface IdStage {
        KeyStage id(@NotNull String id);
    }

    public interface KeyStage {
        UpdatedAtStage key(@NotNull String key);
    }

    public interface UpdatedAtStage {
        ValueStage updatedAt(@NotNull OffsetDateTime updatedAt);
    }

    public interface ValueStage {
        _FinalStage value(@NotNull String value);
    }

    public interface _FinalStage {
        EntityKeyResponseData build();
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static final class Builder
            implements CreatedAtStage,
                    DefinitionIdStage,
                    EntityIdStage,
                    EntityTypeStage,
                    EnvironmentIdStage,
                    IdStage,
                    KeyStage,
                    UpdatedAtStage,
                    ValueStage,
                    _FinalStage {
        private OffsetDateTime createdAt;

        private String definitionId;

        private String entityId;

        private String entityType;

        private String environmentId;

        private String id;

        private String key;

        private OffsetDateTime updatedAt;

        private String value;

        @JsonAnySetter
        private Map<String, Object> additionalProperties = new HashMap<>();

        private Builder() {}

        @java.lang.Override
        public Builder from(EntityKeyResponseData other) {
            createdAt(other.getCreatedAt());
            definitionId(other.getDefinitionId());
            entityId(other.getEntityId());
            entityType(other.getEntityType());
            environmentId(other.getEnvironmentId());
            id(other.getId());
            key(other.getKey());
            updatedAt(other.getUpdatedAt());
            value(other.getValue());
            return this;
        }

        @java.lang.Override
        @JsonSetter("created_at")
        public DefinitionIdStage createdAt(@NotNull OffsetDateTime createdAt) {
            this.createdAt = Objects.requireNonNull(createdAt, "createdAt must not be null");
            return this;
        }

        @java.lang.Override
        @JsonSetter("definition_id")
        public EntityIdStage definitionId(@NotNull String definitionId) {
            this.definitionId = Objects.requireNonNull(definitionId, "definitionId must not be null");
            return this;
        }

        @java.lang.Override
        @JsonSetter("entity_id")
        public EntityTypeStage entityId(@NotNull String entityId) {
            this.entityId = Objects.requireNonNull(entityId, "entityId must not be null");
            return this;
        }

        @java.lang.Override
        @JsonSetter("entity_type")
        public EnvironmentIdStage entityType(@NotNull String entityType) {
            this.entityType = Objects.requireNonNull(entityType, "entityType must not be null");
            return this;
        }

        @java.lang.Override
        @JsonSetter("environment_id")
        public IdStage environmentId(@NotNull String environmentId) {
            this.environmentId = Objects.requireNonNull(environmentId, "environmentId must not be null");
            return this;
        }

        @java.lang.Override
        @JsonSetter("id")
        public KeyStage id(@NotNull String id) {
            this.id = Objects.requireNonNull(id, "id must not be null");
            return this;
        }

        @java.lang.Override
        @JsonSetter("key")
        public UpdatedAtStage key(@NotNull String key) {
            this.key = Objects.requireNonNull(key, "key must not be null");
            return this;
        }

        @java.lang.Override
        @JsonSetter("updated_at")
        public ValueStage updatedAt(@NotNull OffsetDateTime updatedAt) {
            this.updatedAt = Objects.requireNonNull(updatedAt, "updatedAt must not be null");
            return this;
        }

        @java.lang.Override
        @JsonSetter("value")
        public _FinalStage value(@NotNull String value) {
            this.value = Objects.requireNonNull(value, "value must not be null");
            return this;
        }

        @java.lang.Override
        public EntityKeyResponseData build() {
            return new EntityKeyResponseData(
                    createdAt,
                    definitionId,
                    entityId,
                    entityType,
                    environmentId,
                    id,
                    key,
                    updatedAt,
                    value,
                    additionalProperties);
        }
    }
}
