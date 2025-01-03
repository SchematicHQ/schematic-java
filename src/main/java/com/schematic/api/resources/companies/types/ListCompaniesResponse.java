/**
 * This file was auto-generated by Fern from our API Definition.
 */
package com.schematic.api.resources.companies.types;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.schematic.api.core.ObjectMappers;
import com.schematic.api.types.CompanyDetailResponseData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;

@JsonInclude(JsonInclude.Include.NON_ABSENT)
@JsonDeserialize(builder = ListCompaniesResponse.Builder.class)
public final class ListCompaniesResponse {
    private final List<CompanyDetailResponseData> data;

    private final ListCompaniesParams params;

    private final Map<String, Object> additionalProperties;

    private ListCompaniesResponse(
            List<CompanyDetailResponseData> data,
            ListCompaniesParams params,
            Map<String, Object> additionalProperties) {
        this.data = data;
        this.params = params;
        this.additionalProperties = additionalProperties;
    }

    /**
     * @return The returned resources
     */
    @JsonProperty("data")
    public List<CompanyDetailResponseData> getData() {
        return data;
    }

    /**
     * @return Input parameters
     */
    @JsonProperty("params")
    public ListCompaniesParams getParams() {
        return params;
    }

    @java.lang.Override
    public boolean equals(Object other) {
        if (this == other) return true;
        return other instanceof ListCompaniesResponse && equalTo((ListCompaniesResponse) other);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    private boolean equalTo(ListCompaniesResponse other) {
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
        _FinalStage params(@NotNull ListCompaniesParams params);

        Builder from(ListCompaniesResponse other);
    }

    public interface _FinalStage {
        ListCompaniesResponse build();

        _FinalStage data(List<CompanyDetailResponseData> data);

        _FinalStage addData(CompanyDetailResponseData data);

        _FinalStage addAllData(List<CompanyDetailResponseData> data);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static final class Builder implements ParamsStage, _FinalStage {
        private ListCompaniesParams params;

        private List<CompanyDetailResponseData> data = new ArrayList<>();

        @JsonAnySetter
        private Map<String, Object> additionalProperties = new HashMap<>();

        private Builder() {}

        @java.lang.Override
        public Builder from(ListCompaniesResponse other) {
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
        public _FinalStage params(@NotNull ListCompaniesParams params) {
            this.params = Objects.requireNonNull(params, "params must not be null");
            return this;
        }

        /**
         * <p>The returned resources</p>
         * @return Reference to {@code this} so that method calls can be chained together.
         */
        @java.lang.Override
        public _FinalStage addAllData(List<CompanyDetailResponseData> data) {
            this.data.addAll(data);
            return this;
        }

        /**
         * <p>The returned resources</p>
         * @return Reference to {@code this} so that method calls can be chained together.
         */
        @java.lang.Override
        public _FinalStage addData(CompanyDetailResponseData data) {
            this.data.add(data);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "data", nulls = Nulls.SKIP)
        public _FinalStage data(List<CompanyDetailResponseData> data) {
            this.data.clear();
            this.data.addAll(data);
            return this;
        }

        @java.lang.Override
        public ListCompaniesResponse build() {
            return new ListCompaniesResponse(data, params, additionalProperties);
        }
    }
}
