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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonDeserialize(builder = ListCompanyOverridesParams.Builder.class)
public final class ListCompanyOverridesParams {
    private final Optional<String> companyId;

    private final Optional<List<String>> companyIds;

    private final Optional<String> featureId;

    private final Optional<List<String>> featureIds;

    private final Optional<List<String>> ids;

    private final Optional<Integer> limit;

    private final Optional<Integer> offset;

    private final Optional<String> q;

    private final Map<String, Object> additionalProperties;

    private ListCompanyOverridesParams(
            Optional<String> companyId,
            Optional<List<String>> companyIds,
            Optional<String> featureId,
            Optional<List<String>> featureIds,
            Optional<List<String>> ids,
            Optional<Integer> limit,
            Optional<Integer> offset,
            Optional<String> q,
            Map<String, Object> additionalProperties) {
        this.companyId = companyId;
        this.companyIds = companyIds;
        this.featureId = featureId;
        this.featureIds = featureIds;
        this.ids = ids;
        this.limit = limit;
        this.offset = offset;
        this.q = q;
        this.additionalProperties = additionalProperties;
    }

    /**
     * @return Filter company overrides by a single company ID (starting with comp_)
     */
    @JsonProperty("company_id")
    public Optional<String> getCompanyId() {
        return companyId;
    }

    /**
     * @return Filter company overrides by multiple company IDs (starting with comp_)
     */
    @JsonProperty("company_ids")
    public Optional<List<String>> getCompanyIds() {
        return companyIds;
    }

    /**
     * @return Filter company overrides by a single feature ID (starting with feat_)
     */
    @JsonProperty("feature_id")
    public Optional<String> getFeatureId() {
        return featureId;
    }

    /**
     * @return Filter company overrides by multiple feature IDs (starting with feat_)
     */
    @JsonProperty("feature_ids")
    public Optional<List<String>> getFeatureIds() {
        return featureIds;
    }

    /**
     * @return Filter company overrides by multiple company override IDs (starting with cmov_)
     */
    @JsonProperty("ids")
    public Optional<List<String>> getIds() {
        return ids;
    }

    /**
     * @return Page limit (default 100)
     */
    @JsonProperty("limit")
    public Optional<Integer> getLimit() {
        return limit;
    }

    /**
     * @return Page offset (default 0)
     */
    @JsonProperty("offset")
    public Optional<Integer> getOffset() {
        return offset;
    }

    /**
     * @return Search for company overrides by feature or company name
     */
    @JsonProperty("q")
    public Optional<String> getQ() {
        return q;
    }

    @java.lang.Override
    public boolean equals(Object other) {
        if (this == other) return true;
        return other instanceof ListCompanyOverridesParams && equalTo((ListCompanyOverridesParams) other);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    private boolean equalTo(ListCompanyOverridesParams other) {
        return companyId.equals(other.companyId)
                && companyIds.equals(other.companyIds)
                && featureId.equals(other.featureId)
                && featureIds.equals(other.featureIds)
                && ids.equals(other.ids)
                && limit.equals(other.limit)
                && offset.equals(other.offset)
                && q.equals(other.q);
    }

    @java.lang.Override
    public int hashCode() {
        return Objects.hash(
                this.companyId,
                this.companyIds,
                this.featureId,
                this.featureIds,
                this.ids,
                this.limit,
                this.offset,
                this.q);
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
        private Optional<String> companyId = Optional.empty();

        private Optional<List<String>> companyIds = Optional.empty();

        private Optional<String> featureId = Optional.empty();

        private Optional<List<String>> featureIds = Optional.empty();

        private Optional<List<String>> ids = Optional.empty();

        private Optional<Integer> limit = Optional.empty();

        private Optional<Integer> offset = Optional.empty();

        private Optional<String> q = Optional.empty();

        @JsonAnySetter
        private Map<String, Object> additionalProperties = new HashMap<>();

        private Builder() {}

        public Builder from(ListCompanyOverridesParams other) {
            companyId(other.getCompanyId());
            companyIds(other.getCompanyIds());
            featureId(other.getFeatureId());
            featureIds(other.getFeatureIds());
            ids(other.getIds());
            limit(other.getLimit());
            offset(other.getOffset());
            q(other.getQ());
            return this;
        }

        @JsonSetter(value = "company_id", nulls = Nulls.SKIP)
        public Builder companyId(Optional<String> companyId) {
            this.companyId = companyId;
            return this;
        }

        public Builder companyId(String companyId) {
            this.companyId = Optional.of(companyId);
            return this;
        }

        @JsonSetter(value = "company_ids", nulls = Nulls.SKIP)
        public Builder companyIds(Optional<List<String>> companyIds) {
            this.companyIds = companyIds;
            return this;
        }

        public Builder companyIds(List<String> companyIds) {
            this.companyIds = Optional.of(companyIds);
            return this;
        }

        @JsonSetter(value = "feature_id", nulls = Nulls.SKIP)
        public Builder featureId(Optional<String> featureId) {
            this.featureId = featureId;
            return this;
        }

        public Builder featureId(String featureId) {
            this.featureId = Optional.of(featureId);
            return this;
        }

        @JsonSetter(value = "feature_ids", nulls = Nulls.SKIP)
        public Builder featureIds(Optional<List<String>> featureIds) {
            this.featureIds = featureIds;
            return this;
        }

        public Builder featureIds(List<String> featureIds) {
            this.featureIds = Optional.of(featureIds);
            return this;
        }

        @JsonSetter(value = "ids", nulls = Nulls.SKIP)
        public Builder ids(Optional<List<String>> ids) {
            this.ids = ids;
            return this;
        }

        public Builder ids(List<String> ids) {
            this.ids = Optional.of(ids);
            return this;
        }

        @JsonSetter(value = "limit", nulls = Nulls.SKIP)
        public Builder limit(Optional<Integer> limit) {
            this.limit = limit;
            return this;
        }

        public Builder limit(Integer limit) {
            this.limit = Optional.of(limit);
            return this;
        }

        @JsonSetter(value = "offset", nulls = Nulls.SKIP)
        public Builder offset(Optional<Integer> offset) {
            this.offset = offset;
            return this;
        }

        public Builder offset(Integer offset) {
            this.offset = Optional.of(offset);
            return this;
        }

        @JsonSetter(value = "q", nulls = Nulls.SKIP)
        public Builder q(Optional<String> q) {
            this.q = q;
            return this;
        }

        public Builder q(String q) {
            this.q = Optional.of(q);
            return this;
        }

        public ListCompanyOverridesParams build() {
            return new ListCompanyOverridesParams(
                    companyId, companyIds, featureId, featureIds, ids, limit, offset, q, additionalProperties);
        }
    }
}
