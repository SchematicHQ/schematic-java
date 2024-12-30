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
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.schematic.api.core.ObjectMappers;
import com.schematic.api.types.CountResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_ABSENT)
@JsonDeserialize(builder = CountComponentsResponse.Builder.class)
public final class CountComponentsResponse {
    private final CountResponse data;

    private final CountComponentsParams params;

    private final Map<String, Object> additionalProperties;

    private CountComponentsResponse(
            CountResponse data, CountComponentsParams params, Map<String, Object> additionalProperties) {
        this.data = data;
        this.params = params;
        this.additionalProperties = additionalProperties;
    }

    @JsonProperty("data")
    public CountResponse getData() {
        return data;
    }

    /**
     * @return Input parameters
     */
    @JsonProperty("params")
    public CountComponentsParams getParams() {
        return params;
    }

    @java.lang.Override
    public boolean equals(Object other) {
        if (this == other) return true;
        return other instanceof CountComponentsResponse && equalTo((CountComponentsResponse) other);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    private boolean equalTo(CountComponentsResponse other) {
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
        ParamsStage data(CountResponse data);

        Builder from(CountComponentsResponse other);
    }

    public interface ParamsStage {
        _FinalStage params(CountComponentsParams params);
    }

    public interface _FinalStage {
        CountComponentsResponse build();
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static final class Builder implements DataStage, ParamsStage, _FinalStage {
        private CountResponse data;

        private CountComponentsParams params;

        @JsonAnySetter
        private Map<String, Object> additionalProperties = new HashMap<>();

        private Builder() {}

        @java.lang.Override
        public Builder from(CountComponentsResponse other) {
            data(other.getData());
            params(other.getParams());
            return this;
        }

        @java.lang.Override
        @JsonSetter("data")
        public ParamsStage data(CountResponse data) {
            this.data = data;
            return this;
        }

        /**
         * <p>Input parameters</p>
         * @return Reference to {@code this} so that method calls can be chained together.
         */
        @java.lang.Override
        @JsonSetter("params")
        public _FinalStage params(CountComponentsParams params) {
            this.params = params;
            return this;
        }

        @java.lang.Override
        public CountComponentsResponse build() {
            return new CountComponentsResponse(data, params, additionalProperties);
        }
    }
}
