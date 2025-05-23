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
import com.schematic.api.types.CompanyMembershipDetailResponseData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;

@JsonInclude(JsonInclude.Include.NON_ABSENT)
@JsonDeserialize(builder = ListCompanyMembershipsResponse.Builder.class)
public final class ListCompanyMembershipsResponse {
    private final List<CompanyMembershipDetailResponseData> data;

    private final ListCompanyMembershipsParams params;

    private final Map<String, Object> additionalProperties;

    private ListCompanyMembershipsResponse(
            List<CompanyMembershipDetailResponseData> data,
            ListCompanyMembershipsParams params,
            Map<String, Object> additionalProperties) {
        this.data = data;
        this.params = params;
        this.additionalProperties = additionalProperties;
    }

    /**
     * @return The returned resources
     */
    @JsonProperty("data")
    public List<CompanyMembershipDetailResponseData> getData() {
        return data;
    }

    /**
     * @return Input parameters
     */
    @JsonProperty("params")
    public ListCompanyMembershipsParams getParams() {
        return params;
    }

    @java.lang.Override
    public boolean equals(Object other) {
        if (this == other) return true;
        return other instanceof ListCompanyMembershipsResponse && equalTo((ListCompanyMembershipsResponse) other);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    private boolean equalTo(ListCompanyMembershipsResponse other) {
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
        _FinalStage params(@NotNull ListCompanyMembershipsParams params);

        Builder from(ListCompanyMembershipsResponse other);
    }

    public interface _FinalStage {
        ListCompanyMembershipsResponse build();

        _FinalStage data(List<CompanyMembershipDetailResponseData> data);

        _FinalStage addData(CompanyMembershipDetailResponseData data);

        _FinalStage addAllData(List<CompanyMembershipDetailResponseData> data);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static final class Builder implements ParamsStage, _FinalStage {
        private ListCompanyMembershipsParams params;

        private List<CompanyMembershipDetailResponseData> data = new ArrayList<>();

        @JsonAnySetter
        private Map<String, Object> additionalProperties = new HashMap<>();

        private Builder() {}

        @java.lang.Override
        public Builder from(ListCompanyMembershipsResponse other) {
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
        public _FinalStage params(@NotNull ListCompanyMembershipsParams params) {
            this.params = Objects.requireNonNull(params, "params must not be null");
            return this;
        }

        /**
         * <p>The returned resources</p>
         * @return Reference to {@code this} so that method calls can be chained together.
         */
        @java.lang.Override
        public _FinalStage addAllData(List<CompanyMembershipDetailResponseData> data) {
            this.data.addAll(data);
            return this;
        }

        /**
         * <p>The returned resources</p>
         * @return Reference to {@code this} so that method calls can be chained together.
         */
        @java.lang.Override
        public _FinalStage addData(CompanyMembershipDetailResponseData data) {
            this.data.add(data);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "data", nulls = Nulls.SKIP)
        public _FinalStage data(List<CompanyMembershipDetailResponseData> data) {
            this.data.clear();
            this.data.addAll(data);
            return this;
        }

        @java.lang.Override
        public ListCompanyMembershipsResponse build() {
            return new ListCompanyMembershipsResponse(data, params, additionalProperties);
        }
    }
}
