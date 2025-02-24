/**
 * This file was auto-generated by Fern from our API Definition.
 */
package com.schematic.api.resources.crm.requests;

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
@JsonDeserialize(builder = ListCrmProductsRequest.Builder.class)
public final class ListCrmProductsRequest {
    private final Optional<String> ids;

    private final Optional<String> name;

    private final Optional<Integer> limit;

    private final Optional<Integer> offset;

    private final Map<String, Object> additionalProperties;

    private ListCrmProductsRequest(
            Optional<String> ids,
            Optional<String> name,
            Optional<Integer> limit,
            Optional<Integer> offset,
            Map<String, Object> additionalProperties) {
        this.ids = ids;
        this.name = name;
        this.limit = limit;
        this.offset = offset;
        this.additionalProperties = additionalProperties;
    }

    @JsonProperty("ids")
    public Optional<String> getIds() {
        return ids;
    }

    @JsonProperty("name")
    public Optional<String> getName() {
        return name;
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
        return other instanceof ListCrmProductsRequest && equalTo((ListCrmProductsRequest) other);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    private boolean equalTo(ListCrmProductsRequest other) {
        return ids.equals(other.ids)
                && name.equals(other.name)
                && limit.equals(other.limit)
                && offset.equals(other.offset);
    }

    @java.lang.Override
    public int hashCode() {
        return Objects.hash(this.ids, this.name, this.limit, this.offset);
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
        private Optional<String> ids = Optional.empty();

        private Optional<String> name = Optional.empty();

        private Optional<Integer> limit = Optional.empty();

        private Optional<Integer> offset = Optional.empty();

        @JsonAnySetter
        private Map<String, Object> additionalProperties = new HashMap<>();

        private Builder() {}

        public Builder from(ListCrmProductsRequest other) {
            ids(other.getIds());
            name(other.getName());
            limit(other.getLimit());
            offset(other.getOffset());
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

        @JsonSetter(value = "name", nulls = Nulls.SKIP)
        public Builder name(Optional<String> name) {
            this.name = name;
            return this;
        }

        public Builder name(String name) {
            this.name = Optional.ofNullable(name);
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

        public ListCrmProductsRequest build() {
            return new ListCrmProductsRequest(ids, name, limit, offset, additionalProperties);
        }
    }
}
