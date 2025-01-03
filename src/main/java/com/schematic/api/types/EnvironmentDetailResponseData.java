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
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;

@JsonInclude(JsonInclude.Include.NON_ABSENT)
@JsonDeserialize(builder = EnvironmentDetailResponseData.Builder.class)
public final class EnvironmentDetailResponseData {
    private final List<ApiKeyResponseData> apiKeys;

    private final OffsetDateTime createdAt;

    private final String environmentType;

    private final String id;

    private final String name;

    private final OffsetDateTime updatedAt;

    private final Map<String, Object> additionalProperties;

    private EnvironmentDetailResponseData(
            List<ApiKeyResponseData> apiKeys,
            OffsetDateTime createdAt,
            String environmentType,
            String id,
            String name,
            OffsetDateTime updatedAt,
            Map<String, Object> additionalProperties) {
        this.apiKeys = apiKeys;
        this.createdAt = createdAt;
        this.environmentType = environmentType;
        this.id = id;
        this.name = name;
        this.updatedAt = updatedAt;
        this.additionalProperties = additionalProperties;
    }

    @JsonProperty("api_keys")
    public List<ApiKeyResponseData> getApiKeys() {
        return apiKeys;
    }

    @JsonProperty("created_at")
    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    @JsonProperty("environment_type")
    public String getEnvironmentType() {
        return environmentType;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("updated_at")
    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    @java.lang.Override
    public boolean equals(Object other) {
        if (this == other) return true;
        return other instanceof EnvironmentDetailResponseData && equalTo((EnvironmentDetailResponseData) other);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    private boolean equalTo(EnvironmentDetailResponseData other) {
        return apiKeys.equals(other.apiKeys)
                && createdAt.equals(other.createdAt)
                && environmentType.equals(other.environmentType)
                && id.equals(other.id)
                && name.equals(other.name)
                && updatedAt.equals(other.updatedAt);
    }

    @java.lang.Override
    public int hashCode() {
        return Objects.hash(this.apiKeys, this.createdAt, this.environmentType, this.id, this.name, this.updatedAt);
    }

    @java.lang.Override
    public String toString() {
        return ObjectMappers.stringify(this);
    }

    public static CreatedAtStage builder() {
        return new Builder();
    }

    public interface CreatedAtStage {
        EnvironmentTypeStage createdAt(@NotNull OffsetDateTime createdAt);

        Builder from(EnvironmentDetailResponseData other);
    }

    public interface EnvironmentTypeStage {
        IdStage environmentType(@NotNull String environmentType);
    }

    public interface IdStage {
        NameStage id(@NotNull String id);
    }

    public interface NameStage {
        UpdatedAtStage name(@NotNull String name);
    }

    public interface UpdatedAtStage {
        _FinalStage updatedAt(@NotNull OffsetDateTime updatedAt);
    }

    public interface _FinalStage {
        EnvironmentDetailResponseData build();

        _FinalStage apiKeys(List<ApiKeyResponseData> apiKeys);

        _FinalStage addApiKeys(ApiKeyResponseData apiKeys);

        _FinalStage addAllApiKeys(List<ApiKeyResponseData> apiKeys);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static final class Builder
            implements CreatedAtStage, EnvironmentTypeStage, IdStage, NameStage, UpdatedAtStage, _FinalStage {
        private OffsetDateTime createdAt;

        private String environmentType;

        private String id;

        private String name;

        private OffsetDateTime updatedAt;

        private List<ApiKeyResponseData> apiKeys = new ArrayList<>();

        @JsonAnySetter
        private Map<String, Object> additionalProperties = new HashMap<>();

        private Builder() {}

        @java.lang.Override
        public Builder from(EnvironmentDetailResponseData other) {
            apiKeys(other.getApiKeys());
            createdAt(other.getCreatedAt());
            environmentType(other.getEnvironmentType());
            id(other.getId());
            name(other.getName());
            updatedAt(other.getUpdatedAt());
            return this;
        }

        @java.lang.Override
        @JsonSetter("created_at")
        public EnvironmentTypeStage createdAt(@NotNull OffsetDateTime createdAt) {
            this.createdAt = Objects.requireNonNull(createdAt, "createdAt must not be null");
            return this;
        }

        @java.lang.Override
        @JsonSetter("environment_type")
        public IdStage environmentType(@NotNull String environmentType) {
            this.environmentType = Objects.requireNonNull(environmentType, "environmentType must not be null");
            return this;
        }

        @java.lang.Override
        @JsonSetter("id")
        public NameStage id(@NotNull String id) {
            this.id = Objects.requireNonNull(id, "id must not be null");
            return this;
        }

        @java.lang.Override
        @JsonSetter("name")
        public UpdatedAtStage name(@NotNull String name) {
            this.name = Objects.requireNonNull(name, "name must not be null");
            return this;
        }

        @java.lang.Override
        @JsonSetter("updated_at")
        public _FinalStage updatedAt(@NotNull OffsetDateTime updatedAt) {
            this.updatedAt = Objects.requireNonNull(updatedAt, "updatedAt must not be null");
            return this;
        }

        @java.lang.Override
        public _FinalStage addAllApiKeys(List<ApiKeyResponseData> apiKeys) {
            this.apiKeys.addAll(apiKeys);
            return this;
        }

        @java.lang.Override
        public _FinalStage addApiKeys(ApiKeyResponseData apiKeys) {
            this.apiKeys.add(apiKeys);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "api_keys", nulls = Nulls.SKIP)
        public _FinalStage apiKeys(List<ApiKeyResponseData> apiKeys) {
            this.apiKeys.clear();
            this.apiKeys.addAll(apiKeys);
            return this;
        }

        @java.lang.Override
        public EnvironmentDetailResponseData build() {
            return new EnvironmentDetailResponseData(
                    apiKeys, createdAt, environmentType, id, name, updatedAt, additionalProperties);
        }
    }
}
