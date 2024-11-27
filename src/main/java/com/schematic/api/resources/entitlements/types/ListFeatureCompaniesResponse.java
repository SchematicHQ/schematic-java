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
import com.fasterxml.jackson.annotation.Nulls;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.schematic.api.core.ObjectMappers;
import com.schematic.api.types.FeatureCompanyResponseData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonDeserialize(builder = ListFeatureCompaniesResponse.Builder.class)
public final class ListFeatureCompaniesResponse {
    private final List<FeatureCompanyResponseData> data;

    private final ListFeatureCompaniesParams params;

    private final Map<String, Object> additionalProperties;

    private ListFeatureCompaniesResponse(
            List<FeatureCompanyResponseData> data,
            ListFeatureCompaniesParams params,
            Map<String, Object> additionalProperties) {
        this.data = data;
        this.params = params;
        this.additionalProperties = additionalProperties;
    }

    /**
     * @return The returned resources
     */
    @JsonProperty("data")
    public List<FeatureCompanyResponseData> getData() {
        return data;
    }

    /**
     * @return Input parameters
     */
    @JsonProperty("params")
    public ListFeatureCompaniesParams getParams() {
        return params;
    }

    @java.lang.Override
    public boolean equals(Object other) {
        if (this == other) return true;
        return other instanceof ListFeatureCompaniesResponse && equalTo((ListFeatureCompaniesResponse) other);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    private boolean equalTo(ListFeatureCompaniesResponse other) {
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

    public static ParamsStage builder() {
        return new Builder();
    }

    public interface ParamsStage {
        _FinalStage params(ListFeatureCompaniesParams params);

        Builder from(ListFeatureCompaniesResponse other);
    }

    public interface _FinalStage {
        ListFeatureCompaniesResponse build();

        _FinalStage data(List<FeatureCompanyResponseData> data);

        _FinalStage addData(FeatureCompanyResponseData data);

        _FinalStage addAllData(List<FeatureCompanyResponseData> data);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static final class Builder implements ParamsStage, _FinalStage {
        private ListFeatureCompaniesParams params;

        private List<FeatureCompanyResponseData> data = new ArrayList<>();

        @JsonAnySetter
        private Map<String, Object> additionalProperties = new HashMap<>();

        private Builder() {}

        @java.lang.Override
        public Builder from(ListFeatureCompaniesResponse other) {
            data(other.getData());
            params(other.getParams());
            return this;
        }

        /**
         * <p>Input parameters</p>
         * @return Reference to {@code this} so that method calls can be chained together.
         */
        @java.lang.Override
        @JsonSetter("params")
        public _FinalStage params(ListFeatureCompaniesParams params) {
            this.params = params;
            return this;
        }

        /**
         * <p>The returned resources</p>
         * @return Reference to {@code this} so that method calls can be chained together.
         */
        @java.lang.Override
        public _FinalStage addAllData(List<FeatureCompanyResponseData> data) {
            this.data.addAll(data);
            return this;
        }

        /**
         * <p>The returned resources</p>
         * @return Reference to {@code this} so that method calls can be chained together.
         */
        @java.lang.Override
        public _FinalStage addData(FeatureCompanyResponseData data) {
            this.data.add(data);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "data", nulls = Nulls.SKIP)
        public _FinalStage data(List<FeatureCompanyResponseData> data) {
            this.data.clear();
            this.data.addAll(data);
            return this;
        }

        @java.lang.Override
        public ListFeatureCompaniesResponse build() {
            return new ListFeatureCompaniesResponse(data, params, additionalProperties);
        }
    }
}
