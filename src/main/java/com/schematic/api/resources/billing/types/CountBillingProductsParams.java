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
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@JsonInclude(JsonInclude.Include.NON_ABSENT)
@JsonDeserialize(builder = CountBillingProductsParams.Builder.class)
public final class CountBillingProductsParams {
    private final Optional<List<String>> ids;

    private final Optional<Integer> limit;

    private final Optional<String> name;

    private final Optional<Integer> offset;

    private final Optional<CountBillingProductsResponseParamsPriceUsageType> priceUsageType;

    private final Optional<String> q;

    private final Optional<Boolean> withPricesOnly;

    private final Optional<Boolean> withZeroPrice;

    private final Optional<Boolean> withoutLinkedToPlan;

    private final Map<String, Object> additionalProperties;

    private CountBillingProductsParams(
            Optional<List<String>> ids,
            Optional<Integer> limit,
            Optional<String> name,
            Optional<Integer> offset,
            Optional<CountBillingProductsResponseParamsPriceUsageType> priceUsageType,
            Optional<String> q,
            Optional<Boolean> withPricesOnly,
            Optional<Boolean> withZeroPrice,
            Optional<Boolean> withoutLinkedToPlan,
            Map<String, Object> additionalProperties) {
        this.ids = ids;
        this.limit = limit;
        this.name = name;
        this.offset = offset;
        this.priceUsageType = priceUsageType;
        this.q = q;
        this.withPricesOnly = withPricesOnly;
        this.withZeroPrice = withZeroPrice;
        this.withoutLinkedToPlan = withoutLinkedToPlan;
        this.additionalProperties = additionalProperties;
    }

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

    @JsonProperty("price_usage_type")
    public Optional<CountBillingProductsResponseParamsPriceUsageType> getPriceUsageType() {
        return priceUsageType;
    }

    @JsonProperty("q")
    public Optional<String> getQ() {
        return q;
    }

    /**
     * @return Filter products that have prices
     */
    @JsonProperty("with_prices_only")
    public Optional<Boolean> getWithPricesOnly() {
        return withPricesOnly;
    }

    /**
     * @return Filter products that have zero price for free subscription type
     */
    @JsonProperty("with_zero_price")
    public Optional<Boolean> getWithZeroPrice() {
        return withZeroPrice;
    }

    /**
     * @return Filter products that are not linked to any plan
     */
    @JsonProperty("without_linked_to_plan")
    public Optional<Boolean> getWithoutLinkedToPlan() {
        return withoutLinkedToPlan;
    }

    @java.lang.Override
    public boolean equals(Object other) {
        if (this == other) return true;
        return other instanceof CountBillingProductsParams && equalTo((CountBillingProductsParams) other);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    private boolean equalTo(CountBillingProductsParams other) {
        return ids.equals(other.ids)
                && limit.equals(other.limit)
                && name.equals(other.name)
                && offset.equals(other.offset)
                && priceUsageType.equals(other.priceUsageType)
                && q.equals(other.q)
                && withPricesOnly.equals(other.withPricesOnly)
                && withZeroPrice.equals(other.withZeroPrice)
                && withoutLinkedToPlan.equals(other.withoutLinkedToPlan);
    }

    @java.lang.Override
    public int hashCode() {
        return Objects.hash(
                this.ids,
                this.limit,
                this.name,
                this.offset,
                this.priceUsageType,
                this.q,
                this.withPricesOnly,
                this.withZeroPrice,
                this.withoutLinkedToPlan);
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

        private Optional<String> name = Optional.empty();

        private Optional<Integer> offset = Optional.empty();

        private Optional<CountBillingProductsResponseParamsPriceUsageType> priceUsageType = Optional.empty();

        private Optional<String> q = Optional.empty();

        private Optional<Boolean> withPricesOnly = Optional.empty();

        private Optional<Boolean> withZeroPrice = Optional.empty();

        private Optional<Boolean> withoutLinkedToPlan = Optional.empty();

        @JsonAnySetter
        private Map<String, Object> additionalProperties = new HashMap<>();

        private Builder() {}

        public Builder from(CountBillingProductsParams other) {
            ids(other.getIds());
            limit(other.getLimit());
            name(other.getName());
            offset(other.getOffset());
            priceUsageType(other.getPriceUsageType());
            q(other.getQ());
            withPricesOnly(other.getWithPricesOnly());
            withZeroPrice(other.getWithZeroPrice());
            withoutLinkedToPlan(other.getWithoutLinkedToPlan());
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

        @JsonSetter(value = "price_usage_type", nulls = Nulls.SKIP)
        public Builder priceUsageType(Optional<CountBillingProductsResponseParamsPriceUsageType> priceUsageType) {
            this.priceUsageType = priceUsageType;
            return this;
        }

        public Builder priceUsageType(CountBillingProductsResponseParamsPriceUsageType priceUsageType) {
            this.priceUsageType = Optional.ofNullable(priceUsageType);
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

        @JsonSetter(value = "with_prices_only", nulls = Nulls.SKIP)
        public Builder withPricesOnly(Optional<Boolean> withPricesOnly) {
            this.withPricesOnly = withPricesOnly;
            return this;
        }

        public Builder withPricesOnly(Boolean withPricesOnly) {
            this.withPricesOnly = Optional.ofNullable(withPricesOnly);
            return this;
        }

        @JsonSetter(value = "with_zero_price", nulls = Nulls.SKIP)
        public Builder withZeroPrice(Optional<Boolean> withZeroPrice) {
            this.withZeroPrice = withZeroPrice;
            return this;
        }

        public Builder withZeroPrice(Boolean withZeroPrice) {
            this.withZeroPrice = Optional.ofNullable(withZeroPrice);
            return this;
        }

        @JsonSetter(value = "without_linked_to_plan", nulls = Nulls.SKIP)
        public Builder withoutLinkedToPlan(Optional<Boolean> withoutLinkedToPlan) {
            this.withoutLinkedToPlan = withoutLinkedToPlan;
            return this;
        }

        public Builder withoutLinkedToPlan(Boolean withoutLinkedToPlan) {
            this.withoutLinkedToPlan = Optional.ofNullable(withoutLinkedToPlan);
            return this;
        }

        public CountBillingProductsParams build() {
            return new CountBillingProductsParams(
                    ids,
                    limit,
                    name,
                    offset,
                    priceUsageType,
                    q,
                    withPricesOnly,
                    withZeroPrice,
                    withoutLinkedToPlan,
                    additionalProperties);
        }
    }
}
