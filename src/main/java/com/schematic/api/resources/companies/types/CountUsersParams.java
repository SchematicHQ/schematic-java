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

@JsonInclude(JsonInclude.Include.NON_ABSENT)
@JsonDeserialize(builder = CountUsersParams.Builder.class)
public final class CountUsersParams {
    private final Optional<String> companyId;

    private final Optional<List<String>> ids;

    private final Optional<Integer> limit;

    private final Optional<Integer> offset;

    private final Optional<String> planId;

    private final Optional<String> q;

    private final Map<String, Object> additionalProperties;

    private CountUsersParams(
            Optional<String> companyId,
            Optional<List<String>> ids,
            Optional<Integer> limit,
            Optional<Integer> offset,
            Optional<String> planId,
            Optional<String> q,
            Map<String, Object> additionalProperties) {
        this.companyId = companyId;
        this.ids = ids;
        this.limit = limit;
        this.offset = offset;
        this.planId = planId;
        this.q = q;
        this.additionalProperties = additionalProperties;
    }

    /**
     * @return Filter users by company ID (starts with comp_)
     */
    @JsonProperty("company_id")
    public Optional<String> getCompanyId() {
        return companyId;
    }

    /**
     * @return Filter users by multiple user IDs (starts with user_)
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
     * @return Filter users by plan ID (starts with plan_)
     */
    @JsonProperty("plan_id")
    public Optional<String> getPlanId() {
        return planId;
    }

    /**
     * @return Search for users by name, keys or string traits
     */
    @JsonProperty("q")
    public Optional<String> getQ() {
        return q;
    }

    @java.lang.Override
    public boolean equals(Object other) {
        if (this == other) return true;
        return other instanceof CountUsersParams && equalTo((CountUsersParams) other);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    private boolean equalTo(CountUsersParams other) {
        return companyId.equals(other.companyId)
                && ids.equals(other.ids)
                && limit.equals(other.limit)
                && offset.equals(other.offset)
                && planId.equals(other.planId)
                && q.equals(other.q);
    }

    @java.lang.Override
    public int hashCode() {
        return Objects.hash(this.companyId, this.ids, this.limit, this.offset, this.planId, this.q);
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

        private Optional<List<String>> ids = Optional.empty();

        private Optional<Integer> limit = Optional.empty();

        private Optional<Integer> offset = Optional.empty();

        private Optional<String> planId = Optional.empty();

        private Optional<String> q = Optional.empty();

        @JsonAnySetter
        private Map<String, Object> additionalProperties = new HashMap<>();

        private Builder() {}

        public Builder from(CountUsersParams other) {
            companyId(other.getCompanyId());
            ids(other.getIds());
            limit(other.getLimit());
            offset(other.getOffset());
            planId(other.getPlanId());
            q(other.getQ());
            return this;
        }

        @JsonSetter(value = "company_id", nulls = Nulls.SKIP)
        public Builder companyId(Optional<String> companyId) {
            this.companyId = companyId;
            return this;
        }

        public Builder companyId(String companyId) {
            this.companyId = Optional.ofNullable(companyId);
            return this;
        }

        @JsonSetter(value = "ids", nulls = Nulls.SKIP)
        public Builder ids(Optional<List<String>> ids) {
            this.ids = ids;
            return this;
        }

        public Builder ids(List<String> ids) {
            this.ids = Optional.ofNullable(ids);
            return this;
        }

        @JsonSetter(value = "limit", nulls = Nulls.SKIP)
        public Builder limit(Optional<Integer> limit) {
            this.limit = limit;
            return this;
        }

        public Builder limit(Integer limit) {
            this.limit = Optional.ofNullable(limit);
            return this;
        }

        @JsonSetter(value = "offset", nulls = Nulls.SKIP)
        public Builder offset(Optional<Integer> offset) {
            this.offset = offset;
            return this;
        }

        public Builder offset(Integer offset) {
            this.offset = Optional.ofNullable(offset);
            return this;
        }

        @JsonSetter(value = "plan_id", nulls = Nulls.SKIP)
        public Builder planId(Optional<String> planId) {
            this.planId = planId;
            return this;
        }

        public Builder planId(String planId) {
            this.planId = Optional.ofNullable(planId);
            return this;
        }

        @JsonSetter(value = "q", nulls = Nulls.SKIP)
        public Builder q(Optional<String> q) {
            this.q = q;
            return this;
        }

        public Builder q(String q) {
            this.q = Optional.ofNullable(q);
            return this;
        }

        public CountUsersParams build() {
            return new CountUsersParams(companyId, ids, limit, offset, planId, q, additionalProperties);
        }
    }
}
