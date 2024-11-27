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
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonDeserialize(builder = ApiKeyRequestListResponseData.Builder.class)
public final class ApiKeyRequestListResponseData {
    private final String apiKeyId;

    private final Optional<OffsetDateTime> endedAt;

    private final Optional<String> environmentId;

    private final String id;

    private final String method;

    private final Optional<String> reqBody;

    private final Optional<String> requestType;

    private final Optional<Integer> resourceId;

    private final Optional<String> resourceIdString;

    private final Optional<String> resourceName;

    private final Optional<String> resourceType;

    private final Optional<Integer> respCode;

    private final Optional<String> secondaryResource;

    private final OffsetDateTime startedAt;

    private final String url;

    private final Optional<String> userName;

    private final Map<String, Object> additionalProperties;

    private ApiKeyRequestListResponseData(
            String apiKeyId,
            Optional<OffsetDateTime> endedAt,
            Optional<String> environmentId,
            String id,
            String method,
            Optional<String> reqBody,
            Optional<String> requestType,
            Optional<Integer> resourceId,
            Optional<String> resourceIdString,
            Optional<String> resourceName,
            Optional<String> resourceType,
            Optional<Integer> respCode,
            Optional<String> secondaryResource,
            OffsetDateTime startedAt,
            String url,
            Optional<String> userName,
            Map<String, Object> additionalProperties) {
        this.apiKeyId = apiKeyId;
        this.endedAt = endedAt;
        this.environmentId = environmentId;
        this.id = id;
        this.method = method;
        this.reqBody = reqBody;
        this.requestType = requestType;
        this.resourceId = resourceId;
        this.resourceIdString = resourceIdString;
        this.resourceName = resourceName;
        this.resourceType = resourceType;
        this.respCode = respCode;
        this.secondaryResource = secondaryResource;
        this.startedAt = startedAt;
        this.url = url;
        this.userName = userName;
        this.additionalProperties = additionalProperties;
    }

    @JsonProperty("api_key_id")
    public String getApiKeyId() {
        return apiKeyId;
    }

    @JsonProperty("ended_at")
    public Optional<OffsetDateTime> getEndedAt() {
        return endedAt;
    }

    @JsonProperty("environment_id")
    public Optional<String> getEnvironmentId() {
        return environmentId;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("method")
    public String getMethod() {
        return method;
    }

    @JsonProperty("req_body")
    public Optional<String> getReqBody() {
        return reqBody;
    }

    @JsonProperty("request_type")
    public Optional<String> getRequestType() {
        return requestType;
    }

    @JsonProperty("resource_id")
    public Optional<Integer> getResourceId() {
        return resourceId;
    }

    @JsonProperty("resource_id_string")
    public Optional<String> getResourceIdString() {
        return resourceIdString;
    }

    @JsonProperty("resource_name")
    public Optional<String> getResourceName() {
        return resourceName;
    }

    @JsonProperty("resource_type")
    public Optional<String> getResourceType() {
        return resourceType;
    }

    @JsonProperty("resp_code")
    public Optional<Integer> getRespCode() {
        return respCode;
    }

    @JsonProperty("secondary_resource")
    public Optional<String> getSecondaryResource() {
        return secondaryResource;
    }

    @JsonProperty("started_at")
    public OffsetDateTime getStartedAt() {
        return startedAt;
    }

    @JsonProperty("url")
    public String getUrl() {
        return url;
    }

    @JsonProperty("user_name")
    public Optional<String> getUserName() {
        return userName;
    }

    @java.lang.Override
    public boolean equals(Object other) {
        if (this == other) return true;
        return other instanceof ApiKeyRequestListResponseData && equalTo((ApiKeyRequestListResponseData) other);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    private boolean equalTo(ApiKeyRequestListResponseData other) {
        return apiKeyId.equals(other.apiKeyId)
                && endedAt.equals(other.endedAt)
                && environmentId.equals(other.environmentId)
                && id.equals(other.id)
                && method.equals(other.method)
                && reqBody.equals(other.reqBody)
                && requestType.equals(other.requestType)
                && resourceId.equals(other.resourceId)
                && resourceIdString.equals(other.resourceIdString)
                && resourceName.equals(other.resourceName)
                && resourceType.equals(other.resourceType)
                && respCode.equals(other.respCode)
                && secondaryResource.equals(other.secondaryResource)
                && startedAt.equals(other.startedAt)
                && url.equals(other.url)
                && userName.equals(other.userName);
    }

    @java.lang.Override
    public int hashCode() {
        return Objects.hash(
                this.apiKeyId,
                this.endedAt,
                this.environmentId,
                this.id,
                this.method,
                this.reqBody,
                this.requestType,
                this.resourceId,
                this.resourceIdString,
                this.resourceName,
                this.resourceType,
                this.respCode,
                this.secondaryResource,
                this.startedAt,
                this.url,
                this.userName);
    }

    @java.lang.Override
    public String toString() {
        return ObjectMappers.stringify(this);
    }

    public static ApiKeyIdStage builder() {
        return new Builder();
    }

    public interface ApiKeyIdStage {
        IdStage apiKeyId(String apiKeyId);

        Builder from(ApiKeyRequestListResponseData other);
    }

    public interface IdStage {
        MethodStage id(String id);
    }

    public interface MethodStage {
        StartedAtStage method(String method);
    }

    public interface StartedAtStage {
        UrlStage startedAt(OffsetDateTime startedAt);
    }

    public interface UrlStage {
        _FinalStage url(String url);
    }

    public interface _FinalStage {
        ApiKeyRequestListResponseData build();

        _FinalStage endedAt(Optional<OffsetDateTime> endedAt);

        _FinalStage endedAt(OffsetDateTime endedAt);

        _FinalStage environmentId(Optional<String> environmentId);

        _FinalStage environmentId(String environmentId);

        _FinalStage reqBody(Optional<String> reqBody);

        _FinalStage reqBody(String reqBody);

        _FinalStage requestType(Optional<String> requestType);

        _FinalStage requestType(String requestType);

        _FinalStage resourceId(Optional<Integer> resourceId);

        _FinalStage resourceId(Integer resourceId);

        _FinalStage resourceIdString(Optional<String> resourceIdString);

        _FinalStage resourceIdString(String resourceIdString);

        _FinalStage resourceName(Optional<String> resourceName);

        _FinalStage resourceName(String resourceName);

        _FinalStage resourceType(Optional<String> resourceType);

        _FinalStage resourceType(String resourceType);

        _FinalStage respCode(Optional<Integer> respCode);

        _FinalStage respCode(Integer respCode);

        _FinalStage secondaryResource(Optional<String> secondaryResource);

        _FinalStage secondaryResource(String secondaryResource);

        _FinalStage userName(Optional<String> userName);

        _FinalStage userName(String userName);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static final class Builder
            implements ApiKeyIdStage, IdStage, MethodStage, StartedAtStage, UrlStage, _FinalStage {
        private String apiKeyId;

        private String id;

        private String method;

        private OffsetDateTime startedAt;

        private String url;

        private Optional<String> userName = Optional.empty();

        private Optional<String> secondaryResource = Optional.empty();

        private Optional<Integer> respCode = Optional.empty();

        private Optional<String> resourceType = Optional.empty();

        private Optional<String> resourceName = Optional.empty();

        private Optional<String> resourceIdString = Optional.empty();

        private Optional<Integer> resourceId = Optional.empty();

        private Optional<String> requestType = Optional.empty();

        private Optional<String> reqBody = Optional.empty();

        private Optional<String> environmentId = Optional.empty();

        private Optional<OffsetDateTime> endedAt = Optional.empty();

        @JsonAnySetter
        private Map<String, Object> additionalProperties = new HashMap<>();

        private Builder() {}

        @java.lang.Override
        public Builder from(ApiKeyRequestListResponseData other) {
            apiKeyId(other.getApiKeyId());
            endedAt(other.getEndedAt());
            environmentId(other.getEnvironmentId());
            id(other.getId());
            method(other.getMethod());
            reqBody(other.getReqBody());
            requestType(other.getRequestType());
            resourceId(other.getResourceId());
            resourceIdString(other.getResourceIdString());
            resourceName(other.getResourceName());
            resourceType(other.getResourceType());
            respCode(other.getRespCode());
            secondaryResource(other.getSecondaryResource());
            startedAt(other.getStartedAt());
            url(other.getUrl());
            userName(other.getUserName());
            return this;
        }

        @java.lang.Override
        @JsonSetter("api_key_id")
        public IdStage apiKeyId(String apiKeyId) {
            this.apiKeyId = apiKeyId;
            return this;
        }

        @java.lang.Override
        @JsonSetter("id")
        public MethodStage id(String id) {
            this.id = id;
            return this;
        }

        @java.lang.Override
        @JsonSetter("method")
        public StartedAtStage method(String method) {
            this.method = method;
            return this;
        }

        @java.lang.Override
        @JsonSetter("started_at")
        public UrlStage startedAt(OffsetDateTime startedAt) {
            this.startedAt = startedAt;
            return this;
        }

        @java.lang.Override
        @JsonSetter("url")
        public _FinalStage url(String url) {
            this.url = url;
            return this;
        }

        @java.lang.Override
        public _FinalStage userName(String userName) {
            this.userName = Optional.of(userName);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "user_name", nulls = Nulls.SKIP)
        public _FinalStage userName(Optional<String> userName) {
            this.userName = userName;
            return this;
        }

        @java.lang.Override
        public _FinalStage secondaryResource(String secondaryResource) {
            this.secondaryResource = Optional.of(secondaryResource);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "secondary_resource", nulls = Nulls.SKIP)
        public _FinalStage secondaryResource(Optional<String> secondaryResource) {
            this.secondaryResource = secondaryResource;
            return this;
        }

        @java.lang.Override
        public _FinalStage respCode(Integer respCode) {
            this.respCode = Optional.of(respCode);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "resp_code", nulls = Nulls.SKIP)
        public _FinalStage respCode(Optional<Integer> respCode) {
            this.respCode = respCode;
            return this;
        }

        @java.lang.Override
        public _FinalStage resourceType(String resourceType) {
            this.resourceType = Optional.of(resourceType);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "resource_type", nulls = Nulls.SKIP)
        public _FinalStage resourceType(Optional<String> resourceType) {
            this.resourceType = resourceType;
            return this;
        }

        @java.lang.Override
        public _FinalStage resourceName(String resourceName) {
            this.resourceName = Optional.of(resourceName);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "resource_name", nulls = Nulls.SKIP)
        public _FinalStage resourceName(Optional<String> resourceName) {
            this.resourceName = resourceName;
            return this;
        }

        @java.lang.Override
        public _FinalStage resourceIdString(String resourceIdString) {
            this.resourceIdString = Optional.of(resourceIdString);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "resource_id_string", nulls = Nulls.SKIP)
        public _FinalStage resourceIdString(Optional<String> resourceIdString) {
            this.resourceIdString = resourceIdString;
            return this;
        }

        @java.lang.Override
        public _FinalStage resourceId(Integer resourceId) {
            this.resourceId = Optional.of(resourceId);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "resource_id", nulls = Nulls.SKIP)
        public _FinalStage resourceId(Optional<Integer> resourceId) {
            this.resourceId = resourceId;
            return this;
        }

        @java.lang.Override
        public _FinalStage requestType(String requestType) {
            this.requestType = Optional.of(requestType);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "request_type", nulls = Nulls.SKIP)
        public _FinalStage requestType(Optional<String> requestType) {
            this.requestType = requestType;
            return this;
        }

        @java.lang.Override
        public _FinalStage reqBody(String reqBody) {
            this.reqBody = Optional.of(reqBody);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "req_body", nulls = Nulls.SKIP)
        public _FinalStage reqBody(Optional<String> reqBody) {
            this.reqBody = reqBody;
            return this;
        }

        @java.lang.Override
        public _FinalStage environmentId(String environmentId) {
            this.environmentId = Optional.of(environmentId);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "environment_id", nulls = Nulls.SKIP)
        public _FinalStage environmentId(Optional<String> environmentId) {
            this.environmentId = environmentId;
            return this;
        }

        @java.lang.Override
        public _FinalStage endedAt(OffsetDateTime endedAt) {
            this.endedAt = Optional.of(endedAt);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "ended_at", nulls = Nulls.SKIP)
        public _FinalStage endedAt(Optional<OffsetDateTime> endedAt) {
            this.endedAt = endedAt;
            return this;
        }

        @java.lang.Override
        public ApiKeyRequestListResponseData build() {
            return new ApiKeyRequestListResponseData(
                    apiKeyId,
                    endedAt,
                    environmentId,
                    id,
                    method,
                    reqBody,
                    requestType,
                    resourceId,
                    resourceIdString,
                    resourceName,
                    resourceType,
                    respCode,
                    secondaryResource,
                    startedAt,
                    url,
                    userName,
                    additionalProperties);
        }
    }
}
