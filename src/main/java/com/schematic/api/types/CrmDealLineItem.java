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
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.schematic.api.core.ObjectMappers;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonDeserialize(builder = CrmDealLineItem.Builder.class)
public final class CrmDealLineItem {
    private final String billingFrequency;

    private final OffsetDateTime createdAt;

    private final String currency;

    private final String description;

    private final Optional<Map<String, JsonNode>> discountPercentage;

    private final String id;

    private final String name;

    private final double price;

    private final int quantity;

    private final Optional<Integer> termMonth;

    private final Optional<Map<String, JsonNode>> totalDiscount;

    private final OffsetDateTime updatedAt;

    private final Map<String, Object> additionalProperties;

    private CrmDealLineItem(
            String billingFrequency,
            OffsetDateTime createdAt,
            String currency,
            String description,
            Optional<Map<String, JsonNode>> discountPercentage,
            String id,
            String name,
            double price,
            int quantity,
            Optional<Integer> termMonth,
            Optional<Map<String, JsonNode>> totalDiscount,
            OffsetDateTime updatedAt,
            Map<String, Object> additionalProperties) {
        this.billingFrequency = billingFrequency;
        this.createdAt = createdAt;
        this.currency = currency;
        this.description = description;
        this.discountPercentage = discountPercentage;
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.termMonth = termMonth;
        this.totalDiscount = totalDiscount;
        this.updatedAt = updatedAt;
        this.additionalProperties = additionalProperties;
    }

    @JsonProperty("billing_frequency")
    public String getBillingFrequency() {
        return billingFrequency;
    }

    @JsonProperty("created_at")
    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    @JsonProperty("currency")
    public String getCurrency() {
        return currency;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("discount_percentage")
    public Optional<Map<String, JsonNode>> getDiscountPercentage() {
        return discountPercentage;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("price")
    public double getPrice() {
        return price;
    }

    @JsonProperty("quantity")
    public int getQuantity() {
        return quantity;
    }

    @JsonProperty("term_month")
    public Optional<Integer> getTermMonth() {
        return termMonth;
    }

    @JsonProperty("total_discount")
    public Optional<Map<String, JsonNode>> getTotalDiscount() {
        return totalDiscount;
    }

    @JsonProperty("updated_at")
    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    @java.lang.Override
    public boolean equals(Object other) {
        if (this == other) return true;
        return other instanceof CrmDealLineItem && equalTo((CrmDealLineItem) other);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    private boolean equalTo(CrmDealLineItem other) {
        return billingFrequency.equals(other.billingFrequency)
                && createdAt.equals(other.createdAt)
                && currency.equals(other.currency)
                && description.equals(other.description)
                && discountPercentage.equals(other.discountPercentage)
                && id.equals(other.id)
                && name.equals(other.name)
                && price == other.price
                && quantity == other.quantity
                && termMonth.equals(other.termMonth)
                && totalDiscount.equals(other.totalDiscount)
                && updatedAt.equals(other.updatedAt);
    }

    @java.lang.Override
    public int hashCode() {
        return Objects.hash(
                this.billingFrequency,
                this.createdAt,
                this.currency,
                this.description,
                this.discountPercentage,
                this.id,
                this.name,
                this.price,
                this.quantity,
                this.termMonth,
                this.totalDiscount,
                this.updatedAt);
    }

    @java.lang.Override
    public String toString() {
        return ObjectMappers.stringify(this);
    }

    public static BillingFrequencyStage builder() {
        return new Builder();
    }

    public interface BillingFrequencyStage {
        CreatedAtStage billingFrequency(String billingFrequency);

        Builder from(CrmDealLineItem other);
    }

    public interface CreatedAtStage {
        CurrencyStage createdAt(OffsetDateTime createdAt);
    }

    public interface CurrencyStage {
        DescriptionStage currency(String currency);
    }

    public interface DescriptionStage {
        IdStage description(String description);
    }

    public interface IdStage {
        NameStage id(String id);
    }

    public interface NameStage {
        PriceStage name(String name);
    }

    public interface PriceStage {
        QuantityStage price(double price);
    }

    public interface QuantityStage {
        UpdatedAtStage quantity(int quantity);
    }

    public interface UpdatedAtStage {
        _FinalStage updatedAt(OffsetDateTime updatedAt);
    }

    public interface _FinalStage {
        CrmDealLineItem build();

        _FinalStage discountPercentage(Optional<Map<String, JsonNode>> discountPercentage);

        _FinalStage discountPercentage(Map<String, JsonNode> discountPercentage);

        _FinalStage termMonth(Optional<Integer> termMonth);

        _FinalStage termMonth(Integer termMonth);

        _FinalStage totalDiscount(Optional<Map<String, JsonNode>> totalDiscount);

        _FinalStage totalDiscount(Map<String, JsonNode> totalDiscount);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static final class Builder
            implements BillingFrequencyStage,
                    CreatedAtStage,
                    CurrencyStage,
                    DescriptionStage,
                    IdStage,
                    NameStage,
                    PriceStage,
                    QuantityStage,
                    UpdatedAtStage,
                    _FinalStage {
        private String billingFrequency;

        private OffsetDateTime createdAt;

        private String currency;

        private String description;

        private String id;

        private String name;

        private double price;

        private int quantity;

        private OffsetDateTime updatedAt;

        private Optional<Map<String, JsonNode>> totalDiscount = Optional.empty();

        private Optional<Integer> termMonth = Optional.empty();

        private Optional<Map<String, JsonNode>> discountPercentage = Optional.empty();

        @JsonAnySetter
        private Map<String, Object> additionalProperties = new HashMap<>();

        private Builder() {}

        @java.lang.Override
        public Builder from(CrmDealLineItem other) {
            billingFrequency(other.getBillingFrequency());
            createdAt(other.getCreatedAt());
            currency(other.getCurrency());
            description(other.getDescription());
            discountPercentage(other.getDiscountPercentage());
            id(other.getId());
            name(other.getName());
            price(other.getPrice());
            quantity(other.getQuantity());
            termMonth(other.getTermMonth());
            totalDiscount(other.getTotalDiscount());
            updatedAt(other.getUpdatedAt());
            return this;
        }

        @java.lang.Override
        @JsonSetter("billing_frequency")
        public CreatedAtStage billingFrequency(String billingFrequency) {
            this.billingFrequency = billingFrequency;
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
        public DescriptionStage currency(String currency) {
            this.currency = currency;
            return this;
        }

        @java.lang.Override
        @JsonSetter("description")
        public IdStage description(String description) {
            this.description = description;
            return this;
        }

        @java.lang.Override
        @JsonSetter("id")
        public NameStage id(String id) {
            this.id = id;
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
        public QuantityStage price(double price) {
            this.price = price;
            return this;
        }

        @java.lang.Override
        @JsonSetter("quantity")
        public UpdatedAtStage quantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        @java.lang.Override
        @JsonSetter("updated_at")
        public _FinalStage updatedAt(OffsetDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        @java.lang.Override
        public _FinalStage totalDiscount(Map<String, JsonNode> totalDiscount) {
            this.totalDiscount = Optional.of(totalDiscount);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "total_discount", nulls = Nulls.SKIP)
        public _FinalStage totalDiscount(Optional<Map<String, JsonNode>> totalDiscount) {
            this.totalDiscount = totalDiscount;
            return this;
        }

        @java.lang.Override
        public _FinalStage termMonth(Integer termMonth) {
            this.termMonth = Optional.of(termMonth);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "term_month", nulls = Nulls.SKIP)
        public _FinalStage termMonth(Optional<Integer> termMonth) {
            this.termMonth = termMonth;
            return this;
        }

        @java.lang.Override
        public _FinalStage discountPercentage(Map<String, JsonNode> discountPercentage) {
            this.discountPercentage = Optional.of(discountPercentage);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "discount_percentage", nulls = Nulls.SKIP)
        public _FinalStage discountPercentage(Optional<Map<String, JsonNode>> discountPercentage) {
            this.discountPercentage = discountPercentage;
            return this;
        }

        @java.lang.Override
        public CrmDealLineItem build() {
            return new CrmDealLineItem(
                    billingFrequency,
                    createdAt,
                    currency,
                    description,
                    discountPercentage,
                    id,
                    name,
                    price,
                    quantity,
                    termMonth,
                    totalDiscount,
                    updatedAt,
                    additionalProperties);
        }
    }
}
