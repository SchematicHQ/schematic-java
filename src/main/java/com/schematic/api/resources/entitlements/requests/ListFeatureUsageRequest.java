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

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonDeserialize(builder = ListFeatureUsageRequest.Builder.class)
public final class ListFeatureUsageRequest {
    private final Optional<String> companyId;

    private final Optional<Map<String, String>> companyKeys;

    private final Optional<String> featureIds;

    private final Optional<String> q;

    private final Optional<Boolean> withoutNegativeEntitlements;

    private final Optional<Integer> limit;

    private final Optional<Integer> offset;

    private final Map<String, Object> additionalProperties;

    private ListFeatureUsageRequest(
            Optional<String> companyId,
            Optional<Map<String, String>> companyKeys,
            Optional<String> featureIds,
            Optional<String> q,
            Optional<Boolean> withoutNegativeEntitlements,
            Optional<Integer> limit,
            Optional<Integer> offset,
            Map<String, Object> additionalProperties) {
        this.companyId = companyId;
        this.companyKeys = companyKeys;
        this.featureIds = featureIds;
        this.q = q;
        this.withoutNegativeEntitlements = withoutNegativeEntitlements;
        this.limit = limit;
        this.offset = offset;
        this.additionalProperties = additionalProperties;
    }

    @JsonProperty("company_id")
    public Optional<String> getCompanyId() {
        return companyId;
    }

    @JsonProperty("company_keys")
    public Optional<Map<String, String>> getCompanyKeys() {
        return companyKeys;
    }

    @JsonProperty("feature_ids")
    public Optional<String> getFeatureIds() {
        return featureIds;
    }

    @JsonProperty("q")
    public Optional<String> getQ() {
        return q;
    }

    @JsonProperty("without_negative_entitlements")
    public Optional<Boolean> getWithoutNegativeEntitlements() {
        return withoutNegativeEntitlements;
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
        return other instanceof ListFeatureUsageRequest && equalTo((ListFeatureUsageRequest) other);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    private boolean equalTo(ListFeatureUsageRequest other) {
        return companyId.equals(other.companyId)
                && companyKeys.equals(other.companyKeys)
                && featureIds.equals(other.featureIds)
                && q.equals(other.q)
                && withoutNegativeEntitlements.equals(other.withoutNegativeEntitlements)
                && limit.equals(other.limit)
                && offset.equals(other.offset);
    }

    @java.lang.Override
    public int hashCode() {
        return Objects.hash(
                this.companyId,
                this.companyKeys,
                this.featureIds,
                this.q,
                this.withoutNegativeEntitlements,
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

        private Optional<Map<String, String>> companyKeys = Optional.empty();

        private Optional<String> featureIds = Optional.empty();

        private Optional<String> q = Optional.empty();

        private Optional<Boolean> withoutNegativeEntitlements = Optional.empty();

        private Optional<Integer> limit = Optional.empty();

        private Optional<Integer> offset = Optional.empty();

        @JsonAnySetter
        private Map<String, Object> additionalProperties = new HashMap<>();

        private Builder() {}

        public Builder from(ListFeatureUsageRequest other) {
            companyId(other.getCompanyId());
            companyKeys(other.getCompanyKeys());
            featureIds(other.getFeatureIds());
            q(other.getQ());
            withoutNegativeEntitlements(other.getWithoutNegativeEntitlements());
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

        @JsonSetter(value = "company_keys", nulls = Nulls.SKIP)
        public Builder companyKeys(Optional<Map<String, String>> companyKeys) {
            this.companyKeys = companyKeys;
            return this;
        }

        public Builder companyKeys(Map<String, String> companyKeys) {
            this.companyKeys = Optional.of(companyKeys);
            return this;
        }

        @JsonSetter(value = "feature_ids", nulls = Nulls.SKIP)
        public Builder featureIds(Optional<String> featureIds) {
            this.featureIds = featureIds;
            return this;
        }

        public Builder featureIds(String featureIds) {
            this.featureIds = Optional.of(featureIds);
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

        @JsonSetter(value = "without_negative_entitlements", nulls = Nulls.SKIP)
        public Builder withoutNegativeEntitlements(Optional<Boolean> withoutNegativeEntitlements) {
            this.withoutNegativeEntitlements = withoutNegativeEntitlements;
            return this;
        }

        public Builder withoutNegativeEntitlements(Boolean withoutNegativeEntitlements) {
            this.withoutNegativeEntitlements = Optional.of(withoutNegativeEntitlements);
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

        public ListFeatureUsageRequest build() {
            return new ListFeatureUsageRequest(
                    companyId,
                    companyKeys,
                    featureIds,
                    q,
                    withoutNegativeEntitlements,
                    limit,
                    offset,
                    additionalProperties);
        }
    }
}
