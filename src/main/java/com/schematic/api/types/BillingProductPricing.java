/**
 * This file was auto-generated by Fern from our API Definition.
 */
package com.schematic.api.types;

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
@JsonDeserialize(builder = BillingProductPricing.Builder.class)
public final class BillingProductPricing {
    private final String currency;

    private final String interval;

    private final Optional<String> meterId;

    private final int price;

    private final String priceExternalId;

    private final String productExternalId;

    private final int quantity;

    private final String usageType;

    private final Map<String, Object> additionalProperties;

    private BillingProductPricing(
            String currency,
            String interval,
            Optional<String> meterId,
            int price,
            String priceExternalId,
            String productExternalId,
            int quantity,
            String usageType,
            Map<String, Object> additionalProperties) {
        this.currency = currency;
        this.interval = interval;
        this.meterId = meterId;
        this.price = price;
        this.priceExternalId = priceExternalId;
        this.productExternalId = productExternalId;
        this.quantity = quantity;
        this.usageType = usageType;
        this.additionalProperties = additionalProperties;
    }

    @JsonProperty("currency")
    public String getCurrency() {
        return currency;
    }

    @JsonProperty("interval")
    public String getInterval() {
        return interval;
    }

    @JsonProperty("meter_id")
    public Optional<String> getMeterId() {
        return meterId;
    }

    @JsonProperty("price")
    public int getPrice() {
        return price;
    }

    @JsonProperty("price_external_id")
    public String getPriceExternalId() {
        return priceExternalId;
    }

    @JsonProperty("product_external_id")
    public String getProductExternalId() {
        return productExternalId;
    }

    @JsonProperty("quantity")
    public int getQuantity() {
        return quantity;
    }

    @JsonProperty("usage_type")
    public String getUsageType() {
        return usageType;
    }

    @java.lang.Override
    public boolean equals(Object other) {
        if (this == other) return true;
        return other instanceof BillingProductPricing && equalTo((BillingProductPricing) other);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    private boolean equalTo(BillingProductPricing other) {
        return currency.equals(other.currency)
                && interval.equals(other.interval)
                && meterId.equals(other.meterId)
                && price == other.price
                && priceExternalId.equals(other.priceExternalId)
                && productExternalId.equals(other.productExternalId)
                && quantity == other.quantity
                && usageType.equals(other.usageType);
    }

    @java.lang.Override
    public int hashCode() {
        return Objects.hash(
                this.currency,
                this.interval,
                this.meterId,
                this.price,
                this.priceExternalId,
                this.productExternalId,
                this.quantity,
                this.usageType);
    }

    @java.lang.Override
    public String toString() {
        return ObjectMappers.stringify(this);
    }

    public static CurrencyStage builder() {
        return new Builder();
    }

    public interface CurrencyStage {
        IntervalStage currency(String currency);

        Builder from(BillingProductPricing other);
    }

    public interface IntervalStage {
        PriceStage interval(String interval);
    }

    public interface PriceStage {
        PriceExternalIdStage price(int price);
    }

    public interface PriceExternalIdStage {
        ProductExternalIdStage priceExternalId(String priceExternalId);
    }

    public interface ProductExternalIdStage {
        QuantityStage productExternalId(String productExternalId);
    }

    public interface QuantityStage {
        UsageTypeStage quantity(int quantity);
    }

    public interface UsageTypeStage {
        _FinalStage usageType(String usageType);
    }

    public interface _FinalStage {
        BillingProductPricing build();

        _FinalStage meterId(Optional<String> meterId);

        _FinalStage meterId(String meterId);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static final class Builder
            implements CurrencyStage,
                    IntervalStage,
                    PriceStage,
                    PriceExternalIdStage,
                    ProductExternalIdStage,
                    QuantityStage,
                    UsageTypeStage,
                    _FinalStage {
        private String currency;

        private String interval;

        private int price;

        private String priceExternalId;

        private String productExternalId;

        private int quantity;

        private String usageType;

        private Optional<String> meterId = Optional.empty();

        @JsonAnySetter
        private Map<String, Object> additionalProperties = new HashMap<>();

        private Builder() {}

        @java.lang.Override
        public Builder from(BillingProductPricing other) {
            currency(other.getCurrency());
            interval(other.getInterval());
            meterId(other.getMeterId());
            price(other.getPrice());
            priceExternalId(other.getPriceExternalId());
            productExternalId(other.getProductExternalId());
            quantity(other.getQuantity());
            usageType(other.getUsageType());
            return this;
        }

        @java.lang.Override
        @JsonSetter("currency")
        public IntervalStage currency(String currency) {
            this.currency = currency;
            return this;
        }

        @java.lang.Override
        @JsonSetter("interval")
        public PriceStage interval(String interval) {
            this.interval = interval;
            return this;
        }

        @java.lang.Override
        @JsonSetter("price")
        public PriceExternalIdStage price(int price) {
            this.price = price;
            return this;
        }

        @java.lang.Override
        @JsonSetter("price_external_id")
        public ProductExternalIdStage priceExternalId(String priceExternalId) {
            this.priceExternalId = priceExternalId;
            return this;
        }

        @java.lang.Override
        @JsonSetter("product_external_id")
        public QuantityStage productExternalId(String productExternalId) {
            this.productExternalId = productExternalId;
            return this;
        }

        @java.lang.Override
        @JsonSetter("quantity")
        public UsageTypeStage quantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        @java.lang.Override
        @JsonSetter("usage_type")
        public _FinalStage usageType(String usageType) {
            this.usageType = usageType;
            return this;
        }

        @java.lang.Override
        public _FinalStage meterId(String meterId) {
            this.meterId = Optional.of(meterId);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "meter_id", nulls = Nulls.SKIP)
        public _FinalStage meterId(Optional<String> meterId) {
            this.meterId = meterId;
            return this;
        }

        @java.lang.Override
        public BillingProductPricing build() {
            return new BillingProductPricing(
                    currency,
                    interval,
                    meterId,
                    price,
                    priceExternalId,
                    productExternalId,
                    quantity,
                    usageType,
                    additionalProperties);
        }
    }
}
