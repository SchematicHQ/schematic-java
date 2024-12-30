/**
 * This file was auto-generated by Fern from our API Definition.
 */
package com.schematic.api.resources.billing.types;

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
@JsonDeserialize(builder = CountCustomersParams.Builder.class)
public final class CountCustomersParams {
    private final Optional<Boolean> failedToImport;

    private final Optional<Integer> limit;

    private final Optional<String> name;

    private final Optional<Integer> offset;

    private final Optional<String> q;

    private final Map<String, Object> additionalProperties;

    private CountCustomersParams(
            Optional<Boolean> failedToImport,
            Optional<Integer> limit,
            Optional<String> name,
            Optional<Integer> offset,
            Optional<String> q,
            Map<String, Object> additionalProperties) {
        this.failedToImport = failedToImport;
        this.limit = limit;
        this.name = name;
        this.offset = offset;
        this.q = q;
        this.additionalProperties = additionalProperties;
    }

    @JsonProperty("failed_to_import")
    public Optional<Boolean> getFailedToImport() {
        return failedToImport;
    }

    /**
     * @return Page limit (default 100)
     */
    @JsonProperty("limit")
    public Optional<Integer> getLimit() {
        return limit;
    }

    @JsonProperty("name")
    public Optional<String> getName() {
        return name;
    }

    /**
     * @return Page offset (default 0)
     */
    @JsonProperty("offset")
    public Optional<Integer> getOffset() {
        return offset;
    }

    @JsonProperty("q")
    public Optional<String> getQ() {
        return q;
    }

    @java.lang.Override
    public boolean equals(Object other) {
        if (this == other) return true;
        return other instanceof CountCustomersParams && equalTo((CountCustomersParams) other);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    private boolean equalTo(CountCustomersParams other) {
        return failedToImport.equals(other.failedToImport)
                && limit.equals(other.limit)
                && name.equals(other.name)
                && offset.equals(other.offset)
                && q.equals(other.q);
    }

    @java.lang.Override
    public int hashCode() {
        return Objects.hash(this.failedToImport, this.limit, this.name, this.offset, this.q);
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
        private Optional<Boolean> failedToImport = Optional.empty();

        private Optional<Integer> limit = Optional.empty();

        private Optional<String> name = Optional.empty();

        private Optional<Integer> offset = Optional.empty();

        private Optional<String> q = Optional.empty();

        @JsonAnySetter
        private Map<String, Object> additionalProperties = new HashMap<>();

        private Builder() {}

        public Builder from(CountCustomersParams other) {
            failedToImport(other.getFailedToImport());
            limit(other.getLimit());
            name(other.getName());
            offset(other.getOffset());
            q(other.getQ());
            return this;
        }

        @JsonSetter(value = "failed_to_import", nulls = Nulls.SKIP)
        public Builder failedToImport(Optional<Boolean> failedToImport) {
            this.failedToImport = failedToImport;
            return this;
        }

        public Builder failedToImport(Boolean failedToImport) {
            this.failedToImport = Optional.ofNullable(failedToImport);
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

        @JsonSetter(value = "name", nulls = Nulls.SKIP)
        public Builder name(Optional<String> name) {
            this.name = name;
            return this;
        }

        public Builder name(String name) {
            this.name = Optional.ofNullable(name);
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

        @JsonSetter(value = "q", nulls = Nulls.SKIP)
        public Builder q(Optional<String> q) {
            this.q = q;
            return this;
        }

        public Builder q(String q) {
            this.q = Optional.ofNullable(q);
            return this;
        }

        public CountCustomersParams build() {
            return new CountCustomersParams(failedToImport, limit, name, offset, q, additionalProperties);
        }
    }
}
