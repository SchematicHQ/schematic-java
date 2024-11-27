/**
 * This file was auto-generated by Fern from our API Definition.
 */
package com.schematic.api.resources.components.types;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.schematic.api.core.ObjectMappers;
import com.schematic.api.types.ComponentResponseData;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonDeserialize(builder = UpdateComponentResponse.Builder.class)
public final class UpdateComponentResponse {
    private final ComponentResponseData data;

    private final Map<String, JsonNode> params;

    private final Map<String, Object> additionalProperties;

    private UpdateComponentResponse(
            ComponentResponseData data, Map<String, JsonNode> params, Map<String, Object> additionalProperties) {
        this.data = data;
        this.params = params;
        this.additionalProperties = additionalProperties;
    }

    @JsonProperty("data")
    public ComponentResponseData getData() {
        return data;
    }

    /**
     * @return Input parameters
     */
    @JsonProperty("params")
    public Map<String, JsonNode> getParams() {
        return params;
    }

    @java.lang.Override
    public boolean equals(Object other) {
        if (this == other) return true;
        return other instanceof UpdateComponentResponse && equalTo((UpdateComponentResponse) other);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    private boolean equalTo(UpdateComponentResponse other) {
        return data.equals(other.data) && params.equals(other.params);
    }

    @java.lang.Override
    public int hashCode() {
        return Objects.hash(this.data, this.params);
    }

    @java.lang.Override
    public String toString() {
        return ObjectMappers.stringify(this);
    }

    public static DataStage builder() {
        return new Builder();
    }

    public interface DataStage {
        _FinalStage data(ComponentResponseData data);

        Builder from(UpdateComponentResponse other);
    }

    public interface _FinalStage {
        UpdateComponentResponse build();

        _FinalStage params(Map<String, JsonNode> params);

        _FinalStage putAllParams(Map<String, JsonNode> params);

        _FinalStage params(String key, JsonNode value);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static final class Builder implements DataStage, _FinalStage {
        private ComponentResponseData data;

        private Map<String, JsonNode> params = new LinkedHashMap<>();

        @JsonAnySetter
        private Map<String, Object> additionalProperties = new HashMap<>();

        private Builder() {}

        @java.lang.Override
        public Builder from(UpdateComponentResponse other) {
            data(other.getData());
            params(other.getParams());
            return this;
        }

        @java.lang.Override
        @JsonSetter("data")
        public _FinalStage data(ComponentResponseData data) {
            this.data = data;
            return this;
        }

        /**
         * <p>Input parameters</p>
         * @return Reference to {@code this} so that method calls can be chained together.
         */
        @java.lang.Override
        public _FinalStage params(String key, JsonNode value) {
            this.params.put(key, value);
            return this;
        }

        /**
         * <p>Input parameters</p>
         * @return Reference to {@code this} so that method calls can be chained together.
         */
        @java.lang.Override
        public _FinalStage putAllParams(Map<String, JsonNode> params) {
            this.params.putAll(params);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "params", nulls = Nulls.SKIP)
        public _FinalStage params(Map<String, JsonNode> params) {
            this.params.clear();
            this.params.putAll(params);
            return this;
        }

        @java.lang.Override
        public UpdateComponentResponse build() {
            return new UpdateComponentResponse(data, params, additionalProperties);
        }
    }
}
