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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonDeserialize(builder = CountCompaniesParams.Builder.class)
public final class CountCompaniesParams {
    private final Optional<List<String>> ids;

    private final Optional<Integer> limit;

    private final Optional<Integer> offset;

    private final Optional<String> planId;

    private final Optional<String> q;

    private final Optional<String> withoutFeatureOverrideFor;

    private final Optional<Boolean> withoutPlan;

    private final Map<String, Object> additionalProperties;

    private CountCompaniesParams(
            Optional<List<String>> ids,
            Optional<Integer> limit,
            Optional<Integer> offset,
            Optional<String> planId,
            Optional<String> q,
            Optional<String> withoutFeatureOverrideFor,
            Optional<Boolean> withoutPlan,
            Map<String, Object> additionalProperties) {
        this.ids = ids;
        this.limit = limit;
        this.offset = offset;
        this.planId = planId;
        this.q = q;
        this.withoutFeatureOverrideFor = withoutFeatureOverrideFor;
        this.withoutPlan = withoutPlan;
        this.additionalProperties = additionalProperties;
    }

    /**
     * @return Filter companies by multiple company IDs (starts with comp_)
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
     * @return Filter companies by plan ID (starts with plan_)
     */
    @JsonProperty("plan_id")
    public Optional<String> getPlanId() {
        return planId;
    }

    /**
     * @return Search for companies by name, keys or string traits
     */
    @JsonProperty("q")
    public Optional<String> getQ() {
        return q;
    }

    /**
     * @return Filter out companies that already have a company override for the specified feature ID
     */
    @JsonProperty("without_feature_override_for")
    public Optional<String> getWithoutFeatureOverrideFor() {
        return withoutFeatureOverrideFor;
    }

    /**
     * @return Filter out companies that have a plan
     */
    @JsonProperty("without_plan")
    public Optional<Boolean> getWithoutPlan() {
        return withoutPlan;
    }

    @java.lang.Override
    public boolean equals(Object other) {
        if (this == other) return true;
        return other instanceof CountCompaniesParams && equalTo((CountCompaniesParams) other);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    private boolean equalTo(CountCompaniesParams other) {
        return ids.equals(other.ids)
                && limit.equals(other.limit)
                && offset.equals(other.offset)
                && planId.equals(other.planId)
                && q.equals(other.q)
                && withoutFeatureOverrideFor.equals(other.withoutFeatureOverrideFor)
                && withoutPlan.equals(other.withoutPlan);
    }

    @java.lang.Override
    public int hashCode() {
        return Objects.hash(
                this.ids,
                this.limit,
                this.offset,
                this.planId,
                this.q,
                this.withoutFeatureOverrideFor,
                this.withoutPlan);
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
        private Optional<List<String>> ids = Optional.empty();

        private Optional<Integer> limit = Optional.empty();

        private Optional<Integer> offset = Optional.empty();

        private Optional<String> planId = Optional.empty();

        private Optional<String> q = Optional.empty();

        private Optional<String> withoutFeatureOverrideFor = Optional.empty();

        private Optional<Boolean> withoutPlan = Optional.empty();

        @JsonAnySetter
        private Map<String, Object> additionalProperties = new HashMap<>();

        private Builder() {}

        public Builder from(CountCompaniesParams other) {
            ids(other.getIds());
            limit(other.getLimit());
            offset(other.getOffset());
            planId(other.getPlanId());
            q(other.getQ());
            withoutFeatureOverrideFor(other.getWithoutFeatureOverrideFor());
            withoutPlan(other.getWithoutPlan());
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

        @JsonSetter(value = "plan_id", nulls = Nulls.SKIP)
        public Builder planId(Optional<String> planId) {
            this.planId = planId;
            return this;
        }

        public Builder planId(String planId) {
            this.planId = Optional.of(planId);
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

        @JsonSetter(value = "without_feature_override_for", nulls = Nulls.SKIP)
        public Builder withoutFeatureOverrideFor(Optional<String> withoutFeatureOverrideFor) {
            this.withoutFeatureOverrideFor = withoutFeatureOverrideFor;
            return this;
        }

        public Builder withoutFeatureOverrideFor(String withoutFeatureOverrideFor) {
            this.withoutFeatureOverrideFor = Optional.of(withoutFeatureOverrideFor);
            return this;
        }

        @JsonSetter(value = "without_plan", nulls = Nulls.SKIP)
        public Builder withoutPlan(Optional<Boolean> withoutPlan) {
            this.withoutPlan = withoutPlan;
            return this;
        }

        public Builder withoutPlan(Boolean withoutPlan) {
            this.withoutPlan = Optional.of(withoutPlan);
            return this;
        }

        public CountCompaniesParams build() {
            return new CountCompaniesParams(
                    ids, limit, offset, planId, q, withoutFeatureOverrideFor, withoutPlan, additionalProperties);
        }
    }
}
