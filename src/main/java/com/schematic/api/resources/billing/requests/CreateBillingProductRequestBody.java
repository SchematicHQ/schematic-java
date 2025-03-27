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
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.schematic.api.core.ObjectMappers;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;

@JsonInclude(JsonInclude.Include.NON_ABSENT)
@JsonDeserialize(builder = CreateBillingProductRequestBody.Builder.class)
public final class CreateBillingProductRequestBody {
    private final boolean active;

    private final String currency;

    private final String externalId;

    private final String name;

    private final double price;

    private final int quantity;

    private final Map<String, Object> additionalProperties;

    private CreateBillingProductRequestBody(
            boolean active,
            String currency,
            String externalId,
            String name,
            double price,
            int quantity,
            Map<String, Object> additionalProperties) {
        this.active = active;
        this.currency = currency;
        this.externalId = externalId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.additionalProperties = additionalProperties;
    }

    @JsonProperty("active")
    public boolean getActive() {
        return active;
    }

    @JsonProperty("currency")
    public String getCurrency() {
        return currency;
    }

    @JsonProperty("external_id")
    public String getExternalId() {
        return externalId;
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

    @java.lang.Override
    public boolean equals(Object other) {
        if (this == other) return true;
        return other instanceof CreateBillingProductRequestBody && equalTo((CreateBillingProductRequestBody) other);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    private boolean equalTo(CreateBillingProductRequestBody other) {
        return active == other.active
                && currency.equals(other.currency)
                && externalId.equals(other.externalId)
                && name.equals(other.name)
                && price == other.price
                && quantity == other.quantity;
    }

    @java.lang.Override
    public int hashCode() {
        return Objects.hash(this.active, this.currency, this.externalId, this.name, this.price, this.quantity);
    }

    @java.lang.Override
    public String toString() {
        return ObjectMappers.stringify(this);
    }

    public static ActiveStage builder() {
        return new Builder();
    }

    public interface ActiveStage {
        CurrencyStage active(boolean active);

        Builder from(CreateBillingProductRequestBody other);
    }

    public interface CurrencyStage {
        ExternalIdStage currency(@NotNull String currency);
    }

    public interface ExternalIdStage {
        NameStage externalId(@NotNull String externalId);
    }

    public interface NameStage {
        PriceStage name(@NotNull String name);
    }

    public interface PriceStage {
        QuantityStage price(double price);
    }

    public interface QuantityStage {
        _FinalStage quantity(int quantity);
    }

    public interface _FinalStage {
        CreateBillingProductRequestBody build();
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static final class Builder
            implements ActiveStage, CurrencyStage, ExternalIdStage, NameStage, PriceStage, QuantityStage, _FinalStage {
        private boolean active;

        private String currency;

        private String externalId;

        private String name;

        private double price;

        private int quantity;

        @JsonAnySetter
        private Map<String, Object> additionalProperties = new HashMap<>();

        private Builder() {}

        @java.lang.Override
        public Builder from(CreateBillingProductRequestBody other) {
            active(other.getActive());
            currency(other.getCurrency());
            externalId(other.getExternalId());
            name(other.getName());
            price(other.getPrice());
            quantity(other.getQuantity());
            return this;
        }

        @java.lang.Override
        @JsonSetter("active")
        public CurrencyStage active(boolean active) {
            this.active = active;
            return this;
        }

        @java.lang.Override
        @JsonSetter("currency")
        public ExternalIdStage currency(@NotNull String currency) {
            this.currency = Objects.requireNonNull(currency, "currency must not be null");
            return this;
        }

        @java.lang.Override
        @JsonSetter("external_id")
        public NameStage externalId(@NotNull String externalId) {
            this.externalId = Objects.requireNonNull(externalId, "externalId must not be null");
            return this;
        }

        @java.lang.Override
        @JsonSetter("name")
        public PriceStage name(@NotNull String name) {
            this.name = Objects.requireNonNull(name, "name must not be null");
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
        public _FinalStage quantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        @java.lang.Override
        public CreateBillingProductRequestBody build() {
            return new CreateBillingProductRequestBody(
                    active, currency, externalId, name, price, quantity, additionalProperties);
        }
    }
}
