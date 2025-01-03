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
import com.schematic.api.resources.webhooks.types.CreateWebhookRequestBodyRequestTypesItem;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;

@JsonInclude(JsonInclude.Include.NON_ABSENT)
@JsonDeserialize(builder = CreateWebhookRequestBody.Builder.class)
public final class CreateWebhookRequestBody {
    private final String name;

    private final List<CreateWebhookRequestBodyRequestTypesItem> requestTypes;

    private final String url;

    private final Map<String, Object> additionalProperties;

    private CreateWebhookRequestBody(
            String name,
            List<CreateWebhookRequestBodyRequestTypesItem> requestTypes,
            String url,
            Map<String, Object> additionalProperties) {
        this.name = name;
        this.requestTypes = requestTypes;
        this.url = url;
        this.additionalProperties = additionalProperties;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("request_types")
    public List<CreateWebhookRequestBodyRequestTypesItem> getRequestTypes() {
        return requestTypes;
    }

    @JsonProperty("url")
    public String getUrl() {
        return url;
    }

    @java.lang.Override
    public boolean equals(Object other) {
        if (this == other) return true;
        return other instanceof CreateWebhookRequestBody && equalTo((CreateWebhookRequestBody) other);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    private boolean equalTo(CreateWebhookRequestBody other) {
        return name.equals(other.name) && requestTypes.equals(other.requestTypes) && url.equals(other.url);
    }

    @java.lang.Override
    public int hashCode() {
        return Objects.hash(this.name, this.requestTypes, this.url);
    }

    @java.lang.Override
    public String toString() {
        return ObjectMappers.stringify(this);
    }

    public static NameStage builder() {
        return new Builder();
    }

    public interface NameStage {
        UrlStage name(@NotNull String name);

        Builder from(CreateWebhookRequestBody other);
    }

    public interface UrlStage {
        _FinalStage url(@NotNull String url);
    }

    public interface _FinalStage {
        CreateWebhookRequestBody build();

        _FinalStage requestTypes(List<CreateWebhookRequestBodyRequestTypesItem> requestTypes);

        _FinalStage addRequestTypes(CreateWebhookRequestBodyRequestTypesItem requestTypes);

        _FinalStage addAllRequestTypes(List<CreateWebhookRequestBodyRequestTypesItem> requestTypes);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static final class Builder implements NameStage, UrlStage, _FinalStage {
        private String name;

        private String url;

        private List<CreateWebhookRequestBodyRequestTypesItem> requestTypes = new ArrayList<>();

        @JsonAnySetter
        private Map<String, Object> additionalProperties = new HashMap<>();

        private Builder() {}

        @java.lang.Override
        public Builder from(CreateWebhookRequestBody other) {
            name(other.getName());
            requestTypes(other.getRequestTypes());
            url(other.getUrl());
            return this;
        }

        @java.lang.Override
        @JsonSetter("name")
        public UrlStage name(@NotNull String name) {
            this.name = Objects.requireNonNull(name, "name must not be null");
            return this;
        }

        @java.lang.Override
        @JsonSetter("url")
        public _FinalStage url(@NotNull String url) {
            this.url = Objects.requireNonNull(url, "url must not be null");
            return this;
        }

        @java.lang.Override
        public _FinalStage addAllRequestTypes(List<CreateWebhookRequestBodyRequestTypesItem> requestTypes) {
            this.requestTypes.addAll(requestTypes);
            return this;
        }

        @java.lang.Override
        public _FinalStage addRequestTypes(CreateWebhookRequestBodyRequestTypesItem requestTypes) {
            this.requestTypes.add(requestTypes);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "request_types", nulls = Nulls.SKIP)
        public _FinalStage requestTypes(List<CreateWebhookRequestBodyRequestTypesItem> requestTypes) {
            this.requestTypes.clear();
            this.requestTypes.addAll(requestTypes);
            return this;
        }

        @java.lang.Override
        public CreateWebhookRequestBody build() {
            return new CreateWebhookRequestBody(name, requestTypes, url, additionalProperties);
        }
    }
}
