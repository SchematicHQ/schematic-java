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
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@JsonInclude(JsonInclude.Include.NON_ABSENT)
@JsonDeserialize(builder = BillingProductForSubscriptionResponseData.Builder.class)
public final class BillingProductForSubscriptionResponseData {
    private final OffsetDateTime createdAt;

    private final String currency;

    private final String environmentId;

    private final String externalId;

    private final String id;

    private final String interval;

    private final Optional<String> meterId;

    private final String name;

    private final int price;

    private final String priceExternalId;

    private final String priceId;

    private final double quantity;

    private final String subscriptionId;

    private final OffsetDateTime updatedAt;

    private final String usageType;

    private final Map<String, Object> additionalProperties;

    private BillingProductForSubscriptionResponseData(
            OffsetDateTime createdAt,
            String currency,
            String environmentId,
            String externalId,
            String id,
            String interval,
            Optional<String> meterId,
            String name,
            int price,
            String priceExternalId,
            String priceId,
            double quantity,
            String subscriptionId,
            OffsetDateTime updatedAt,
            String usageType,
            Map<String, Object> additionalProperties) {
        this.createdAt = createdAt;
        this.currency = currency;
        this.environmentId = environmentId;
        this.externalId = externalId;
        this.id = id;
        this.interval = interval;
        this.meterId = meterId;
        this.name = name;
        this.price = price;
        this.priceExternalId = priceExternalId;
        this.priceId = priceId;
        this.quantity = quantity;
        this.subscriptionId = subscriptionId;
        this.updatedAt = updatedAt;
        this.usageType = usageType;
        this.additionalProperties = additionalProperties;
    }

    @JsonProperty("created_at")
    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    @JsonProperty("currency")
    public String getCurrency() {
        return currency;
    }

    @JsonProperty("environment_id")
    public String getEnvironmentId() {
        return environmentId;
    }

    @JsonProperty("external_id")
    public String getExternalId() {
        return externalId;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("interval")
    public String getInterval() {
        return interval;
    }

    @JsonProperty("meter_id")
    public Optional<String> getMeterId() {
        return meterId;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("price")
    public int getPrice() {
        return price;
    }

    @JsonProperty("price_external_id")
    public String getPriceExternalId() {
        return priceExternalId;
    }

    @JsonProperty("price_id")
    public String getPriceId() {
        return priceId;
    }

    @JsonProperty("quantity")
    public double getQuantity() {
        return quantity;
    }

    @JsonProperty("subscription_id")
    public String getSubscriptionId() {
        return subscriptionId;
    }

    @JsonProperty("updated_at")
    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    @JsonProperty("usage_type")
    public String getUsageType() {
        return usageType;
    }

    @java.lang.Override
    public boolean equals(Object other) {
        if (this == other) return true;
        return other instanceof BillingProductForSubscriptionResponseData
                && equalTo((BillingProductForSubscriptionResponseData) other);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    private boolean equalTo(BillingProductForSubscriptionResponseData other) {
        return createdAt.equals(other.createdAt)
                && currency.equals(other.currency)
                && environmentId.equals(other.environmentId)
                && externalId.equals(other.externalId)
                && id.equals(other.id)
                && interval.equals(other.interval)
                && meterId.equals(other.meterId)
                && name.equals(other.name)
                && price == other.price
                && priceExternalId.equals(other.priceExternalId)
                && priceId.equals(other.priceId)
                && quantity == other.quantity
                && subscriptionId.equals(other.subscriptionId)
                && updatedAt.equals(other.updatedAt)
                && usageType.equals(other.usageType);
    }

    @java.lang.Override
    public int hashCode() {
        return Objects.hash(
                this.createdAt,
                this.currency,
                this.environmentId,
                this.externalId,
                this.id,
                this.interval,
                this.meterId,
                this.name,
                this.price,
                this.priceExternalId,
                this.priceId,
                this.quantity,
                this.subscriptionId,
                this.updatedAt,
                this.usageType);
    }

    @java.lang.Override
    public String toString() {
        return ObjectMappers.stringify(this);
    }

    public static CreatedAtStage builder() {
        return new Builder();
    }

    public interface CreatedAtStage {
        CurrencyStage createdAt(OffsetDateTime createdAt);

        Builder from(BillingProductForSubscriptionResponseData other);
    }

    public interface CurrencyStage {
        EnvironmentIdStage currency(String currency);
    }

    public interface EnvironmentIdStage {
        ExternalIdStage environmentId(String environmentId);
    }

    public interface ExternalIdStage {
        IdStage externalId(String externalId);
    }

    public interface IdStage {
        IntervalStage id(String id);
    }

    public interface IntervalStage {
        NameStage interval(String interval);
    }

    public interface NameStage {
        PriceStage name(String name);
    }

    public interface PriceStage {
        PriceExternalIdStage price(int price);
    }

    public interface PriceExternalIdStage {
        PriceIdStage priceExternalId(String priceExternalId);
    }

    public interface PriceIdStage {
        QuantityStage priceId(String priceId);
    }

    public interface QuantityStage {
        SubscriptionIdStage quantity(double quantity);
    }

    public interface SubscriptionIdStage {
        UpdatedAtStage subscriptionId(String subscriptionId);
    }

    public interface UpdatedAtStage {
        UsageTypeStage updatedAt(OffsetDateTime updatedAt);
    }

    public interface UsageTypeStage {
        _FinalStage usageType(String usageType);
    }

    public interface _FinalStage {
        BillingProductForSubscriptionResponseData build();

        _FinalStage meterId(Optional<String> meterId);

        _FinalStage meterId(String meterId);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static final class Builder
            implements CreatedAtStage,
                    CurrencyStage,
                    EnvironmentIdStage,
                    ExternalIdStage,
                    IdStage,
                    IntervalStage,
                    NameStage,
                    PriceStage,
                    PriceExternalIdStage,
                    PriceIdStage,
                    QuantityStage,
                    SubscriptionIdStage,
                    UpdatedAtStage,
                    UsageTypeStage,
                    _FinalStage {
        private OffsetDateTime createdAt;

        private String currency;

        private String environmentId;

        private String externalId;

        private String id;

        private String interval;

        private String name;

        private int price;

        private String priceExternalId;

        private String priceId;

        private double quantity;

        private String subscriptionId;

        private OffsetDateTime updatedAt;

        private String usageType;

        private Optional<String> meterId = Optional.empty();

        @JsonAnySetter
        private Map<String, Object> additionalProperties = new HashMap<>();

        private Builder() {}

        @java.lang.Override
        public Builder from(BillingProductForSubscriptionResponseData other) {
            createdAt(other.getCreatedAt());
            currency(other.getCurrency());
            environmentId(other.getEnvironmentId());
            externalId(other.getExternalId());
            id(other.getId());
            interval(other.getInterval());
            meterId(other.getMeterId());
            name(other.getName());
            price(other.getPrice());
            priceExternalId(other.getPriceExternalId());
            priceId(other.getPriceId());
            quantity(other.getQuantity());
            subscriptionId(other.getSubscriptionId());
            updatedAt(other.getUpdatedAt());
            usageType(other.getUsageType());
            return this;
        }

        @java.lang.Override
        @JsonSetter("created_at")
        public CurrencyStage createdAt(OffsetDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        @java.lang.Override
        @JsonSetter("currency")
        public EnvironmentIdStage currency(String currency) {
            this.currency = currency;
            return this;
        }

        @java.lang.Override
        @JsonSetter("environment_id")
        public ExternalIdStage environmentId(String environmentId) {
            this.environmentId = environmentId;
            return this;
        }

        @java.lang.Override
        @JsonSetter("external_id")
        public IdStage externalId(String externalId) {
            this.externalId = externalId;
            return this;
        }

        @java.lang.Override
        @JsonSetter("id")
        public IntervalStage id(String id) {
            this.id = id;
            return this;
        }

        @java.lang.Override
        @JsonSetter("interval")
        public NameStage interval(String interval) {
            this.interval = interval;
            return this;
        }

        @java.lang.Override
        @JsonSetter("name")
        public PriceStage name(String name) {
            this.name = name;
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
        public PriceIdStage priceExternalId(String priceExternalId) {
            this.priceExternalId = priceExternalId;
            return this;
        }

        @java.lang.Override
        @JsonSetter("price_id")
        public QuantityStage priceId(String priceId) {
            this.priceId = priceId;
            return this;
        }

        @java.lang.Override
        @JsonSetter("quantity")
        public SubscriptionIdStage quantity(double quantity) {
            this.quantity = quantity;
            return this;
        }

        @java.lang.Override
        @JsonSetter("subscription_id")
        public UpdatedAtStage subscriptionId(String subscriptionId) {
            this.subscriptionId = subscriptionId;
            return this;
        }

        @java.lang.Override
        @JsonSetter("updated_at")
        public UsageTypeStage updatedAt(OffsetDateTime updatedAt) {
            this.updatedAt = updatedAt;
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
        public BillingProductForSubscriptionResponseData build() {
            return new BillingProductForSubscriptionResponseData(
                    createdAt,
                    currency,
                    environmentId,
                    externalId,
                    id,
                    interval,
                    meterId,
                    name,
                    price,
                    priceExternalId,
                    priceId,
                    quantity,
                    subscriptionId,
                    updatedAt,
                    usageType,
                    additionalProperties);
        }
    }
}
