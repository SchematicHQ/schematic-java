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
import org.jetbrains.annotations.NotNull;

@JsonInclude(JsonInclude.Include.NON_ABSENT)
@JsonDeserialize(builder = CountFeatureCompaniesRequest.Builder.class)
public final class CountFeatureCompaniesRequest {
    private final String featureId;

    private final Optional<String> q;

    private final Optional<Integer> limit;

    private final Optional<Integer> offset;

    private final Map<String, Object> additionalProperties;

    private CountFeatureCompaniesRequest(
            String featureId,
            Optional<String> q,
            Optional<Integer> limit,
            Optional<Integer> offset,
            Map<String, Object> additionalProperties) {
        this.featureId = featureId;
        this.q = q;
        this.limit = limit;
        this.offset = offset;
        this.additionalProperties = additionalProperties;
    }

    @JsonProperty("feature_id")
    public String getFeatureId() {
        return featureId;
    }

    @JsonProperty("q")
    public Optional<String> getQ() {
        return q;
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
        return other instanceof CountFeatureCompaniesRequest && equalTo((CountFeatureCompaniesRequest) other);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    private boolean equalTo(CountFeatureCompaniesRequest other) {
        return featureId.equals(other.featureId)
                && q.equals(other.q)
                && limit.equals(other.limit)
                && offset.equals(other.offset);
    }

    @java.lang.Override
    public int hashCode() {
        return Objects.hash(this.featureId, this.q, this.limit, this.offset);
    }

    @java.lang.Override
    public String toString() {
        return ObjectMappers.stringify(this);
    }

    public static FeatureIdStage builder() {
        return new Builder();
    }

    public interface FeatureIdStage {
        _FinalStage featureId(@NotNull String featureId);

        Builder from(CountFeatureCompaniesRequest other);
    }

    public interface _FinalStage {
        CountFeatureCompaniesRequest build();

        _FinalStage q(Optional<String> q);

        _FinalStage q(String q);

        _FinalStage limit(Optional<Integer> limit);

        _FinalStage limit(Integer limit);

        _FinalStage offset(Optional<Integer> offset);

        _FinalStage offset(Integer offset);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static final class Builder implements FeatureIdStage, _FinalStage {
        private String featureId;

        private Optional<Integer> offset = Optional.empty();

        private Optional<Integer> limit = Optional.empty();

        private Optional<String> q = Optional.empty();

        @JsonAnySetter
        private Map<String, Object> additionalProperties = new HashMap<>();

        private Builder() {}

        @java.lang.Override
        public Builder from(CountFeatureCompaniesRequest other) {
            featureId(other.getFeatureId());
            q(other.getQ());
            limit(other.getLimit());
            offset(other.getOffset());
            return this;
        }

        @java.lang.Override
        @JsonSetter("feature_id")
        public _FinalStage featureId(@NotNull String featureId) {
            this.featureId = Objects.requireNonNull(featureId, "featureId must not be null");
            return this;
        }

        /**
         * <p>Page offset (default 0)</p>
         * @return Reference to {@code this} so that method calls can be chained together.
         */
        @java.lang.Override
        public _FinalStage offset(Integer offset) {
            this.offset = Optional.ofNullable(offset);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "offset", nulls = Nulls.SKIP)
        public _FinalStage offset(Optional<Integer> offset) {
            this.offset = offset;
            return this;
        }

        /**
         * <p>Page limit (default 100)</p>
         * @return Reference to {@code this} so that method calls can be chained together.
         */
        @java.lang.Override
        public _FinalStage limit(Integer limit) {
            this.limit = Optional.ofNullable(limit);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "limit", nulls = Nulls.SKIP)
        public _FinalStage limit(Optional<Integer> limit) {
            this.limit = limit;
            return this;
        }

        @java.lang.Override
        public _FinalStage q(String q) {
            this.q = Optional.ofNullable(q);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "q", nulls = Nulls.SKIP)
        public _FinalStage q(Optional<String> q) {
            this.q = q;
            return this;
        }

        @java.lang.Override
        public CountFeatureCompaniesRequest build() {
            return new CountFeatureCompaniesRequest(featureId, q, limit, offset, additionalProperties);
        }
    }
}
