/**
 * This file was auto-generated by Fern from our API Definition.
 */
package com.schematic.api.resources.billing.requests;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.schematic.api.core.ObjectMappers;
import com.schematic.api.resources.billing.types.CreateBillingPriceRequestBodyUsageType;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;

@JsonInclude(JsonInclude.Include.NON_ABSENT)
@JsonDeserialize(builder = CreateBillingPriceRequestBody.Builder.class)
public final class CreateBillingPriceRequestBody {
    private final String currency;

    private final String interval;

    private final boolean isActive;

    private final Optional<String> meterId;

    private final int price;

    private final String priceExternalId;

    private final String productExternalId;

    private final CreateBillingPriceRequestBodyUsageType usageType;

    private final Map<String, Object> additionalProperties;

    private CreateBillingPriceRequestBody(
            String currency,
            String interval,
            boolean isActive,
            Optional<String> meterId,
            int price,
            String priceExternalId,
            String productExternalId,
            CreateBillingPriceRequestBodyUsageType usageType,
            Map<String, Object> additionalProperties) {
        this.currency = currency;
        this.interval = interval;
        this.isActive = isActive;
        this.meterId = meterId;
        this.price = price;
        this.priceExternalId = priceExternalId;
        this.productExternalId = productExternalId;
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

    @JsonProperty("is_active")
    public boolean getIsActive() {
        return isActive;
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

    @JsonProperty("usage_type")
    public CreateBillingPriceRequestBodyUsageType getUsageType() {
        return usageType;
    }

    @java.lang.Override
    public boolean equals(Object other) {
        if (this == other) return true;
        return other instanceof CreateBillingPriceRequestBody && equalTo((CreateBillingPriceRequestBody) other);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    private boolean equalTo(CreateBillingPriceRequestBody other) {
        return currency.equals(other.currency)
                && interval.equals(other.interval)
                && isActive == other.isActive
                && meterId.equals(other.meterId)
                && price == other.price
                && priceExternalId.equals(other.priceExternalId)
                && productExternalId.equals(other.productExternalId)
                && usageType.equals(other.usageType);
    }

    @java.lang.Override
    public int hashCode() {
        return Objects.hash(
                this.currency,
                this.interval,
                this.isActive,
                this.meterId,
                this.price,
                this.priceExternalId,
                this.productExternalId,
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
        IntervalStage currency(@NotNull String currency);

        Builder from(CreateBillingPriceRequestBody other);
    }

    public interface IntervalStage {
        IsActiveStage interval(@NotNull String interval);
    }

    public interface IsActiveStage {
        PriceStage isActive(boolean isActive);
    }

    public interface PriceStage {
        PriceExternalIdStage price(int price);
    }

    public interface PriceExternalIdStage {
        ProductExternalIdStage priceExternalId(@NotNull String priceExternalId);
    }

    public interface ProductExternalIdStage {
        UsageTypeStage productExternalId(@NotNull String productExternalId);
    }

    public interface UsageTypeStage {
        _FinalStage usageType(@NotNull CreateBillingPriceRequestBodyUsageType usageType);
    }

    public interface _FinalStage {
        CreateBillingPriceRequestBody build();

        _FinalStage meterId(Optional<String> meterId);

        _FinalStage meterId(String meterId);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static final class Builder
            implements CurrencyStage,
                    IntervalStage,
                    IsActiveStage,
                    PriceStage,
                    PriceExternalIdStage,
                    ProductExternalIdStage,
                    UsageTypeStage,
                    _FinalStage {
        private String currency;

        private String interval;

        private boolean isActive;

        private int price;

        private String priceExternalId;

        private String productExternalId;

        private CreateBillingPriceRequestBodyUsageType usageType;

        private Optional<String> meterId = Optional.empty();

        @JsonAnySetter
        private Map<String, Object> additionalProperties = new HashMap<>();

        private Builder() {}

        @java.lang.Override
        public Builder from(CreateBillingPriceRequestBody other) {
            currency(other.getCurrency());
            interval(other.getInterval());
            isActive(other.getIsActive());
            meterId(other.getMeterId());
            price(other.getPrice());
            priceExternalId(other.getPriceExternalId());
            productExternalId(other.getProductExternalId());
            usageType(other.getUsageType());
            return this;
        }

        @java.lang.Override
        @JsonSetter("currency")
        public IntervalStage currency(@NotNull String currency) {
            this.currency = Objects.requireNonNull(currency, "currency must not be null");
            return this;
        }

        @java.lang.Override
        @JsonSetter("interval")
        public IsActiveStage interval(@NotNull String interval) {
            this.interval = Objects.requireNonNull(interval, "interval must not be null");
            return this;
        }

        @java.lang.Override
        @JsonSetter("is_active")
        public PriceStage isActive(boolean isActive) {
            this.isActive = isActive;
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
        public ProductExternalIdStage priceExternalId(@NotNull String priceExternalId) {
            this.priceExternalId = Objects.requireNonNull(priceExternalId, "priceExternalId must not be null");
            return this;
        }

        @java.lang.Override
        @JsonSetter("product_external_id")
        public UsageTypeStage productExternalId(@NotNull String productExternalId) {
            this.productExternalId = Objects.requireNonNull(productExternalId, "productExternalId must not be null");
            return this;
        }

        @java.lang.Override
        @JsonSetter("usage_type")
        public _FinalStage usageType(@NotNull CreateBillingPriceRequestBodyUsageType usageType) {
            this.usageType = Objects.requireNonNull(usageType, "usageType must not be null");
            return this;
        }

        @java.lang.Override
        public _FinalStage meterId(String meterId) {
            this.meterId = Optional.ofNullable(meterId);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "meter_id", nulls = Nulls.SKIP)
        public _FinalStage meterId(Optional<String> meterId) {
            this.meterId = meterId;
            return this;
        }

        @java.lang.Override
        public CreateBillingPriceRequestBody build() {
            return new CreateBillingPriceRequestBody(
                    currency,
                    interval,
                    isActive,
                    meterId,
                    price,
                    priceExternalId,
                    productExternalId,
                    usageType,
                    additionalProperties);
        }
    }
}
