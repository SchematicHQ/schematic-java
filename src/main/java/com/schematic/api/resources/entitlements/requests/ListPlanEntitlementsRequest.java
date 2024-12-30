/**
 * This file was auto-generated by Fern from our API Definition.
 */
package com.schematic.api.resources.entitlements.requests;

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
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@JsonInclude(JsonInclude.Include.NON_ABSENT)
@JsonDeserialize(builder = ListPlanEntitlementsRequest.Builder.class)
public final class ListPlanEntitlementsRequest {
    private final Optional<String> featureId;

    private final Optional<String> featureIds;

    private final Optional<String> ids;

    private final Optional<String> planId;

    private final Optional<String> planIds;

    private final Optional<String> q;

    private final Optional<Boolean> withMeteredProducts;

    private final Optional<Integer> limit;

    private final Optional<Integer> offset;

    private final Map<String, Object> additionalProperties;

    private ListPlanEntitlementsRequest(
            Optional<String> featureId,
            Optional<String> featureIds,
            Optional<String> ids,
            Optional<String> planId,
            Optional<String> planIds,
            Optional<String> q,
            Optional<Boolean> withMeteredProducts,
            Optional<Integer> limit,
            Optional<Integer> offset,
            Map<String, Object> additionalProperties) {
        this.featureId = featureId;
        this.featureIds = featureIds;
        this.ids = ids;
        this.planId = planId;
        this.planIds = planIds;
        this.q = q;
        this.withMeteredProducts = withMeteredProducts;
        this.limit = limit;
        this.offset = offset;
        this.additionalProperties = additionalProperties;
    }

    /**
     * @return Filter plan entitlements by a single feature ID (starting with feat_)
     */
    @JsonProperty("feature_id")
    public Optional<String> getFeatureId() {
        return featureId;
    }

    /**
     * @return Filter plan entitlements by multiple feature IDs (starting with feat_)
     */
    @JsonProperty("feature_ids")
    public Optional<String> getFeatureIds() {
        return featureIds;
    }

    /**
     * @return Filter plan entitlements by multiple plan entitlement IDs (starting with pltl_)
     */
    @JsonProperty("ids")
    public Optional<String> getIds() {
        return ids;
    }

    /**
     * @return Filter plan entitlements by a single plan ID (starting with plan_)
     */
    @JsonProperty("plan_id")
    public Optional<String> getPlanId() {
        return planId;
    }

    /**
     * @return Filter plan entitlements by multiple plan IDs (starting with plan_)
     */
    @JsonProperty("plan_ids")
    public Optional<String> getPlanIds() {
        return planIds;
    }

    /**
     * @return Search for plan entitlements by feature or company name
     */
    @JsonProperty("q")
    public Optional<String> getQ() {
        return q;
    }

    /**
     * @return Filter plan entitlements only with metered products
     */
    @JsonProperty("with_metered_products")
    public Optional<Boolean> getWithMeteredProducts() {
        return withMeteredProducts;
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
        return other instanceof ListPlanEntitlementsRequest && equalTo((ListPlanEntitlementsRequest) other);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    private boolean equalTo(ListPlanEntitlementsRequest other) {
        return featureId.equals(other.featureId)
                && featureIds.equals(other.featureIds)
                && ids.equals(other.ids)
                && planId.equals(other.planId)
                && planIds.equals(other.planIds)
                && q.equals(other.q)
                && withMeteredProducts.equals(other.withMeteredProducts)
                && limit.equals(other.limit)
                && offset.equals(other.offset);
    }

    @java.lang.Override
    public int hashCode() {
        return Objects.hash(
                this.featureId,
                this.featureIds,
                this.ids,
                this.planId,
                this.planIds,
                this.q,
                this.withMeteredProducts,
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
        private Optional<String> featureId = Optional.empty();

        private Optional<String> featureIds = Optional.empty();

        private Optional<String> ids = Optional.empty();

        private Optional<String> planId = Optional.empty();

        private Optional<String> planIds = Optional.empty();

        private Optional<String> q = Optional.empty();

        private Optional<Boolean> withMeteredProducts = Optional.empty();

        private Optional<Integer> limit = Optional.empty();

        private Optional<Integer> offset = Optional.empty();

        @JsonAnySetter
        private Map<String, Object> additionalProperties = new HashMap<>();

        private Builder() {}

        public Builder from(ListPlanEntitlementsRequest other) {
            featureId(other.getFeatureId());
            featureIds(other.getFeatureIds());
            ids(other.getIds());
            planId(other.getPlanId());
            planIds(other.getPlanIds());
            q(other.getQ());
            withMeteredProducts(other.getWithMeteredProducts());
            limit(other.getLimit());
            offset(other.getOffset());
            return this;
        }

        @JsonSetter(value = "feature_id", nulls = Nulls.SKIP)
        public Builder featureId(Optional<String> featureId) {
            this.featureId = featureId;
            return this;
        }

        public Builder featureId(String featureId) {
            this.featureId = Optional.ofNullable(featureId);
            return this;
        }

        @JsonSetter(value = "feature_ids", nulls = Nulls.SKIP)
        public Builder featureIds(Optional<String> featureIds) {
            this.featureIds = featureIds;
            return this;
        }

        public Builder featureIds(String featureIds) {
            this.featureIds = Optional.ofNullable(featureIds);
            return this;
        }

        @JsonSetter(value = "ids", nulls = Nulls.SKIP)
        public Builder ids(Optional<String> ids) {
            this.ids = ids;
            return this;
        }

        public Builder ids(String ids) {
            this.ids = Optional.ofNullable(ids);
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

        @JsonSetter(value = "plan_ids", nulls = Nulls.SKIP)
        public Builder planIds(Optional<String> planIds) {
            this.planIds = planIds;
            return this;
        }

        public Builder planIds(String planIds) {
            this.planIds = Optional.ofNullable(planIds);
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

        @JsonSetter(value = "with_metered_products", nulls = Nulls.SKIP)
        public Builder withMeteredProducts(Optional<Boolean> withMeteredProducts) {
            this.withMeteredProducts = withMeteredProducts;
            return this;
        }

        public Builder withMeteredProducts(Boolean withMeteredProducts) {
            this.withMeteredProducts = Optional.ofNullable(withMeteredProducts);
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

        public ListPlanEntitlementsRequest build() {
            return new ListPlanEntitlementsRequest(
                    featureId,
                    featureIds,
                    ids,
                    planId,
                    planIds,
                    q,
                    withMeteredProducts,
                    limit,
                    offset,
                    additionalProperties);
        }
    }
}
