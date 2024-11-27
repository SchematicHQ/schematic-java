/**
 * This file was auto-generated by Fern from our API Definition.
 */
package com.schematic.api.resources.plans.requests;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.schematic.api.core.ObjectMappers;
import com.schematic.api.resources.plans.types.CountPlansRequestPlanType;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonDeserialize(builder = CountPlansRequest.Builder.class)
public final class CountPlansRequest {
    private final Optional<String> companyId;

    private final Optional<Boolean> hasProductId;

    private final Optional<String> ids;

    private final Optional<CountPlansRequestPlanType> planType;

    private final Optional<String> q;

    private final Optional<String> withoutEntitlementFor;

    private final Optional<Boolean> withoutProductId;

    private final Optional<Integer> limit;

    private final Optional<Integer> offset;

    private final Map<String, Object> additionalProperties;

    private CountPlansRequest(
            Optional<String> companyId,
            Optional<Boolean> hasProductId,
            Optional<String> ids,
            Optional<CountPlansRequestPlanType> planType,
            Optional<String> q,
            Optional<String> withoutEntitlementFor,
            Optional<Boolean> withoutProductId,
            Optional<Integer> limit,
            Optional<Integer> offset,
            Map<String, Object> additionalProperties) {
        this.companyId = companyId;
        this.hasProductId = hasProductId;
        this.ids = ids;
        this.planType = planType;
        this.q = q;
        this.withoutEntitlementFor = withoutEntitlementFor;
        this.withoutProductId = withoutProductId;
        this.limit = limit;
        this.offset = offset;
        this.additionalProperties = additionalProperties;
    }

    @JsonProperty("company_id")
    public Optional<String> getCompanyId() {
        return companyId;
    }

    /**
     * @return Filter out plans that do not have a billing product ID
     */
    @JsonProperty("has_product_id")
    public Optional<Boolean> getHasProductId() {
        return hasProductId;
    }

    @JsonProperty("ids")
    public Optional<String> getIds() {
        return ids;
    }

    /**
     * @return Filter by plan type
     */
    @JsonProperty("plan_type")
    public Optional<CountPlansRequestPlanType> getPlanType() {
        return planType;
    }

    @JsonProperty("q")
    public Optional<String> getQ() {
        return q;
    }

    /**
     * @return Filter out plans that already have a plan entitlement for the specified feature ID
     */
    @JsonProperty("without_entitlement_for")
    public Optional<String> getWithoutEntitlementFor() {
        return withoutEntitlementFor;
    }

    /**
     * @return Filter out plans that have a billing product ID
     */
    @JsonProperty("without_product_id")
    public Optional<Boolean> getWithoutProductId() {
        return withoutProductId;
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

    @java.lang.Override
    public boolean equals(Object other) {
        if (this == other) return true;
        return other instanceof CountPlansRequest && equalTo((CountPlansRequest) other);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    private boolean equalTo(CountPlansRequest other) {
        return companyId.equals(other.companyId)
                && hasProductId.equals(other.hasProductId)
                && ids.equals(other.ids)
                && planType.equals(other.planType)
                && q.equals(other.q)
                && withoutEntitlementFor.equals(other.withoutEntitlementFor)
                && withoutProductId.equals(other.withoutProductId)
                && limit.equals(other.limit)
                && offset.equals(other.offset);
    }

    @java.lang.Override
    public int hashCode() {
        return Objects.hash(
                this.companyId,
                this.hasProductId,
                this.ids,
                this.planType,
                this.q,
                this.withoutEntitlementFor,
                this.withoutProductId,
                this.limit,
                this.offset);
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

        private Optional<Boolean> hasProductId = Optional.empty();

        private Optional<String> ids = Optional.empty();

        private Optional<CountPlansRequestPlanType> planType = Optional.empty();

        private Optional<String> q = Optional.empty();

        private Optional<String> withoutEntitlementFor = Optional.empty();

        private Optional<Boolean> withoutProductId = Optional.empty();

        private Optional<Integer> limit = Optional.empty();

        private Optional<Integer> offset = Optional.empty();

        @JsonAnySetter
        private Map<String, Object> additionalProperties = new HashMap<>();

        private Builder() {}

        public Builder from(CountPlansRequest other) {
            companyId(other.getCompanyId());
            hasProductId(other.getHasProductId());
            ids(other.getIds());
            planType(other.getPlanType());
            q(other.getQ());
            withoutEntitlementFor(other.getWithoutEntitlementFor());
            withoutProductId(other.getWithoutProductId());
            limit(other.getLimit());
            offset(other.getOffset());
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

        @JsonSetter(value = "has_product_id", nulls = Nulls.SKIP)
        public Builder hasProductId(Optional<Boolean> hasProductId) {
            this.hasProductId = hasProductId;
            return this;
        }

        public Builder hasProductId(Boolean hasProductId) {
            this.hasProductId = Optional.of(hasProductId);
            return this;
        }

        @JsonSetter(value = "ids", nulls = Nulls.SKIP)
        public Builder ids(Optional<String> ids) {
            this.ids = ids;
            return this;
        }

        public Builder ids(String ids) {
            this.ids = Optional.of(ids);
            return this;
        }

        @JsonSetter(value = "plan_type", nulls = Nulls.SKIP)
        public Builder planType(Optional<CountPlansRequestPlanType> planType) {
            this.planType = planType;
            return this;
        }

        public Builder planType(CountPlansRequestPlanType planType) {
            this.planType = Optional.of(planType);
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

        @JsonSetter(value = "without_entitlement_for", nulls = Nulls.SKIP)
        public Builder withoutEntitlementFor(Optional<String> withoutEntitlementFor) {
            this.withoutEntitlementFor = withoutEntitlementFor;
            return this;
        }

        public Builder withoutEntitlementFor(String withoutEntitlementFor) {
            this.withoutEntitlementFor = Optional.of(withoutEntitlementFor);
            return this;
        }

        @JsonSetter(value = "without_product_id", nulls = Nulls.SKIP)
        public Builder withoutProductId(Optional<Boolean> withoutProductId) {
            this.withoutProductId = withoutProductId;
            return this;
        }

        public Builder withoutProductId(Boolean withoutProductId) {
            this.withoutProductId = Optional.of(withoutProductId);
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

        public CountPlansRequest build() {
            return new CountPlansRequest(
                    companyId,
                    hasProductId,
                    ids,
                    planType,
                    q,
                    withoutEntitlementFor,
                    withoutProductId,
                    limit,
                    offset,
                    additionalProperties);
        }
    }
}
