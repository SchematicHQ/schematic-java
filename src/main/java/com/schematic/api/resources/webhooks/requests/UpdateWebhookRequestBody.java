/**
 * This file was auto-generated by Fern from our API Definition.
 */
package com.schematic.api.resources.webhooks.requests;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.schematic.api.core.ObjectMappers;
import com.schematic.api.resources.webhooks.types.UpdateWebhookRequestBodyRequestTypesItem;
import com.schematic.api.resources.webhooks.types.UpdateWebhookRequestBodyStatus;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@JsonInclude(JsonInclude.Include.NON_ABSENT)
@JsonDeserialize(builder = UpdateWebhookRequestBody.Builder.class)
public final class UpdateWebhookRequestBody {
    private final Optional<String> name;

    private final Optional<List<UpdateWebhookRequestBodyRequestTypesItem>> requestTypes;

    private final Optional<UpdateWebhookRequestBodyStatus> status;

    private final Optional<String> url;

    private final Map<String, Object> additionalProperties;

    private UpdateWebhookRequestBody(
            Optional<String> name,
            Optional<List<UpdateWebhookRequestBodyRequestTypesItem>> requestTypes,
            Optional<UpdateWebhookRequestBodyStatus> status,
            Optional<String> url,
            Map<String, Object> additionalProperties) {
        this.name = name;
        this.requestTypes = requestTypes;
        this.status = status;
        this.url = url;
        this.additionalProperties = additionalProperties;
    }

    @JsonProperty("name")
    public Optional<String> getName() {
        return name;
    }

    @JsonProperty("request_types")
    public Optional<List<UpdateWebhookRequestBodyRequestTypesItem>> getRequestTypes() {
        return requestTypes;
    }

    @JsonProperty("status")
    public Optional<UpdateWebhookRequestBodyStatus> getStatus() {
        return status;
    }

    @JsonProperty("url")
    public Optional<String> getUrl() {
        return url;
    }

    @java.lang.Override
    public boolean equals(Object other) {
        if (this == other) return true;
        return other instanceof UpdateWebhookRequestBody && equalTo((UpdateWebhookRequestBody) other);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    private boolean equalTo(UpdateWebhookRequestBody other) {
        return name.equals(other.name)
                && requestTypes.equals(other.requestTypes)
                && status.equals(other.status)
                && url.equals(other.url);
    }

    @java.lang.Override
    public int hashCode() {
        return Objects.hash(this.name, this.requestTypes, this.status, this.url);
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
        private Optional<String> name = Optional.empty();

        private Optional<List<UpdateWebhookRequestBodyRequestTypesItem>> requestTypes = Optional.empty();

        private Optional<UpdateWebhookRequestBodyStatus> status = Optional.empty();

        private Optional<String> url = Optional.empty();

        @JsonAnySetter
        private Map<String, Object> additionalProperties = new HashMap<>();

        private Builder() {}

        public Builder from(UpdateWebhookRequestBody other) {
            name(other.getName());
            requestTypes(other.getRequestTypes());
            status(other.getStatus());
            url(other.getUrl());
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

        @JsonSetter(value = "request_types", nulls = Nulls.SKIP)
        public Builder requestTypes(Optional<List<UpdateWebhookRequestBodyRequestTypesItem>> requestTypes) {
            this.requestTypes = requestTypes;
            return this;
        }

        public Builder requestTypes(List<UpdateWebhookRequestBodyRequestTypesItem> requestTypes) {
            this.requestTypes = Optional.ofNullable(requestTypes);
            return this;
        }

        @JsonSetter(value = "status", nulls = Nulls.SKIP)
        public Builder status(Optional<UpdateWebhookRequestBodyStatus> status) {
            this.status = status;
            return this;
        }

        public Builder status(UpdateWebhookRequestBodyStatus status) {
            this.status = Optional.ofNullable(status);
            return this;
        }

        @JsonSetter(value = "url", nulls = Nulls.SKIP)
        public Builder url(Optional<String> url) {
            this.url = url;
            return this;
        }

        public Builder url(String url) {
            this.url = Optional.ofNullable(url);
            return this;
        }

        public UpdateWebhookRequestBody build() {
            return new UpdateWebhookRequestBody(name, requestTypes, status, url, additionalProperties);
        }
    }
}
