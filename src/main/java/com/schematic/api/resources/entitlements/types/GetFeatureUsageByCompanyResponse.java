/**
 * This file was auto-generated by Fern from our API Definition.
 */
package com.schematic.api.resources.entitlements.types;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.schematic.api.core.ObjectMappers;
import com.schematic.api.types.FeatureUsageDetailResponseData;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;

@JsonInclude(JsonInclude.Include.NON_ABSENT)
@JsonDeserialize(builder = GetFeatureUsageByCompanyResponse.Builder.class)
public final class GetFeatureUsageByCompanyResponse {
    private final FeatureUsageDetailResponseData data;

    private final GetFeatureUsageByCompanyParams params;

    private final Map<String, Object> additionalProperties;

    private GetFeatureUsageByCompanyResponse(
            FeatureUsageDetailResponseData data,
            GetFeatureUsageByCompanyParams params,
            Map<String, Object> additionalProperties) {
        this.data = data;
        this.params = params;
        this.additionalProperties = additionalProperties;
    }

    @JsonProperty("data")
    public FeatureUsageDetailResponseData getData() {
        return data;
    }

    /**
     * @return Input parameters
     */
    @JsonProperty("params")
    public GetFeatureUsageByCompanyParams getParams() {
        return params;
    }

    @java.lang.Override
    public boolean equals(Object other) {
        if (this == other) return true;
        return other instanceof GetFeatureUsageByCompanyResponse && equalTo((GetFeatureUsageByCompanyResponse) other);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    private boolean equalTo(GetFeatureUsageByCompanyResponse other) {
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
        ParamsStage data(@NotNull FeatureUsageDetailResponseData data);

        Builder from(GetFeatureUsageByCompanyResponse other);
    }

    public interface ParamsStage {
        _FinalStage params(@NotNull GetFeatureUsageByCompanyParams params);
    }

    public interface _FinalStage {
        GetFeatureUsageByCompanyResponse build();
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static final class Builder implements DataStage, ParamsStage, _FinalStage {
        private FeatureUsageDetailResponseData data;

        private GetFeatureUsageByCompanyParams params;

        @JsonAnySetter
        private Map<String, Object> additionalProperties = new HashMap<>();

        private Builder() {}

        @java.lang.Override
        public Builder from(GetFeatureUsageByCompanyResponse other) {
            data(other.getData());
            params(other.getParams());
            return this;
        }

        @java.lang.Override
        @JsonSetter("data")
        public ParamsStage data(@NotNull FeatureUsageDetailResponseData data) {
            this.data = Objects.requireNonNull(data, "data must not be null");
            return this;
        }

        /**
         * <p>Input parameters</p>
         * @return Reference to {@code this} so that method calls can be chained together.
         */
        @java.lang.Override
        @JsonSetter("params")
        public _FinalStage params(@NotNull GetFeatureUsageByCompanyParams params) {
            this.params = Objects.requireNonNull(params, "params must not be null");
            return this;
        }

        @java.lang.Override
        public GetFeatureUsageByCompanyResponse build() {
            return new GetFeatureUsageByCompanyResponse(data, params, additionalProperties);
        }
    }
}
