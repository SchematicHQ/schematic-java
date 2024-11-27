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

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonDeserialize(builder = IssueTemporaryAccessTokenResponseData.Builder.class)
public final class IssueTemporaryAccessTokenResponseData {
    private final String apiKeyId;

    private final OffsetDateTime createdAt;

    private final String environmentId;

    private final OffsetDateTime expiredAt;

    private final String id;

    private final String resourceType;

    private final String token;

    private final OffsetDateTime updatedAt;

    private final Map<String, Object> additionalProperties;

    private IssueTemporaryAccessTokenResponseData(
            String apiKeyId,
            OffsetDateTime createdAt,
            String environmentId,
            OffsetDateTime expiredAt,
            String id,
            String resourceType,
            String token,
            OffsetDateTime updatedAt,
            Map<String, Object> additionalProperties) {
        this.apiKeyId = apiKeyId;
        this.createdAt = createdAt;
        this.environmentId = environmentId;
        this.expiredAt = expiredAt;
        this.id = id;
        this.resourceType = resourceType;
        this.token = token;
        this.updatedAt = updatedAt;
        this.additionalProperties = additionalProperties;
    }

    @JsonProperty("api_key_id")
    public String getApiKeyId() {
        return apiKeyId;
    }

    @JsonProperty("created_at")
    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    @JsonProperty("environment_id")
    public String getEnvironmentId() {
        return environmentId;
    }

    @JsonProperty("expired_at")
    public OffsetDateTime getExpiredAt() {
        return expiredAt;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("resource_type")
    public String getResourceType() {
        return resourceType;
    }

    @JsonProperty("token")
    public String getToken() {
        return token;
    }

    @JsonProperty("updated_at")
    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    @java.lang.Override
    public boolean equals(Object other) {
        if (this == other) return true;
        return other instanceof IssueTemporaryAccessTokenResponseData
                && equalTo((IssueTemporaryAccessTokenResponseData) other);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    private boolean equalTo(IssueTemporaryAccessTokenResponseData other) {
        return apiKeyId.equals(other.apiKeyId)
                && createdAt.equals(other.createdAt)
                && environmentId.equals(other.environmentId)
                && expiredAt.equals(other.expiredAt)
                && id.equals(other.id)
                && resourceType.equals(other.resourceType)
                && token.equals(other.token)
                && updatedAt.equals(other.updatedAt);
    }

    @java.lang.Override
    public int hashCode() {
        return Objects.hash(
                this.apiKeyId,
                this.createdAt,
                this.environmentId,
                this.expiredAt,
                this.id,
                this.resourceType,
                this.token,
                this.updatedAt);
    }

    @java.lang.Override
    public String toString() {
        return ObjectMappers.stringify(this);
    }

    public static ApiKeyIdStage builder() {
        return new Builder();
    }

    public interface ApiKeyIdStage {
        CreatedAtStage apiKeyId(String apiKeyId);

        Builder from(IssueTemporaryAccessTokenResponseData other);
    }

    public interface CreatedAtStage {
        EnvironmentIdStage createdAt(OffsetDateTime createdAt);
    }

    public interface EnvironmentIdStage {
        ExpiredAtStage environmentId(String environmentId);
    }

    public interface ExpiredAtStage {
        IdStage expiredAt(OffsetDateTime expiredAt);
    }

    public interface IdStage {
        ResourceTypeStage id(String id);
    }

    public interface ResourceTypeStage {
        TokenStage resourceType(String resourceType);
    }

    public interface TokenStage {
        UpdatedAtStage token(String token);
    }

    public interface UpdatedAtStage {
        _FinalStage updatedAt(OffsetDateTime updatedAt);
    }

    public interface _FinalStage {
        IssueTemporaryAccessTokenResponseData build();
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static final class Builder
            implements ApiKeyIdStage,
                    CreatedAtStage,
                    EnvironmentIdStage,
                    ExpiredAtStage,
                    IdStage,
                    ResourceTypeStage,
                    TokenStage,
                    UpdatedAtStage,
                    _FinalStage {
        private String apiKeyId;

        private OffsetDateTime createdAt;

        private String environmentId;

        private OffsetDateTime expiredAt;

        private String id;

        private String resourceType;

        private String token;

        private OffsetDateTime updatedAt;

        @JsonAnySetter
        private Map<String, Object> additionalProperties = new HashMap<>();

        private Builder() {}

        @java.lang.Override
        public Builder from(IssueTemporaryAccessTokenResponseData other) {
            apiKeyId(other.getApiKeyId());
            createdAt(other.getCreatedAt());
            environmentId(other.getEnvironmentId());
            expiredAt(other.getExpiredAt());
            id(other.getId());
            resourceType(other.getResourceType());
            token(other.getToken());
            updatedAt(other.getUpdatedAt());
            return this;
        }

        @java.lang.Override
        @JsonSetter("api_key_id")
        public CreatedAtStage apiKeyId(String apiKeyId) {
            this.apiKeyId = apiKeyId;
            return this;
        }

        @java.lang.Override
        @JsonSetter("created_at")
        public EnvironmentIdStage createdAt(OffsetDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        @java.lang.Override
        @JsonSetter("environment_id")
        public ExpiredAtStage environmentId(String environmentId) {
            this.environmentId = environmentId;
            return this;
        }

        @java.lang.Override
        @JsonSetter("expired_at")
        public IdStage expiredAt(OffsetDateTime expiredAt) {
            this.expiredAt = expiredAt;
            return this;
        }

        @java.lang.Override
        @JsonSetter("id")
        public ResourceTypeStage id(String id) {
            this.id = id;
            return this;
        }

        @java.lang.Override
        @JsonSetter("resource_type")
        public TokenStage resourceType(String resourceType) {
            this.resourceType = resourceType;
            return this;
        }

        @java.lang.Override
        @JsonSetter("token")
        public UpdatedAtStage token(String token) {
            this.token = token;
            return this;
        }

        @java.lang.Override
        @JsonSetter("updated_at")
        public _FinalStage updatedAt(OffsetDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        @java.lang.Override
        public IssueTemporaryAccessTokenResponseData build() {
            return new IssueTemporaryAccessTokenResponseData(
                    apiKeyId,
                    createdAt,
                    environmentId,
                    expiredAt,
                    id,
                    resourceType,
                    token,
                    updatedAt,
                    additionalProperties);
        }
    }
}
