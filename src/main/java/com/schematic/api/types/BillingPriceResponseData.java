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
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.schematic.api.core.ObjectMappers;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_ABSENT)
@JsonDeserialize(builder = BillingPriceResponseData.Builder.class)
public final class BillingPriceResponseData {
    private final String currency;

    private final String externalPriceId;

    private final String id;

    private final String interval;

    private final int price;

    private final Map<String, Object> additionalProperties;

    private BillingPriceResponseData(
            String currency,
            String externalPriceId,
            String id,
            String interval,
            int price,
            Map<String, Object> additionalProperties) {
        this.currency = currency;
        this.externalPriceId = externalPriceId;
        this.id = id;
        this.interval = interval;
        this.price = price;
        this.additionalProperties = additionalProperties;
    }

    @JsonProperty("currency")
    public String getCurrency() {
        return currency;
    }

    @JsonProperty("external_price_id")
    public String getExternalPriceId() {
        return externalPriceId;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("interval")
    public String getInterval() {
        return interval;
    }

    @JsonProperty("price")
    public int getPrice() {
        return price;
    }

    @java.lang.Override
    public boolean equals(Object other) {
        if (this == other) return true;
        return other instanceof BillingPriceResponseData && equalTo((BillingPriceResponseData) other);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    private boolean equalTo(BillingPriceResponseData other) {
        return currency.equals(other.currency)
                && externalPriceId.equals(other.externalPriceId)
                && id.equals(other.id)
                && interval.equals(other.interval)
                && price == other.price;
    }

    @java.lang.Override
    public int hashCode() {
        return Objects.hash(this.currency, this.externalPriceId, this.id, this.interval, this.price);
    }

    @java.lang.Override
    public String toString() {
        return ObjectMappers.stringify(this);
    }

    public static CurrencyStage builder() {
        return new Builder();
    }

    public interface CurrencyStage {
        ExternalPriceIdStage currency(String currency);

        Builder from(BillingPriceResponseData other);
    }

    public interface ExternalPriceIdStage {
        IdStage externalPriceId(String externalPriceId);
    }

    public interface IdStage {
        IntervalStage id(String id);
    }

    public interface IntervalStage {
        PriceStage interval(String interval);
    }

    public interface PriceStage {
        _FinalStage price(int price);
    }

    public interface _FinalStage {
        BillingPriceResponseData build();
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static final class Builder
            implements CurrencyStage, ExternalPriceIdStage, IdStage, IntervalStage, PriceStage, _FinalStage {
        private String currency;

        private String externalPriceId;

        private String id;

        private String interval;

        private int price;

        @JsonAnySetter
        private Map<String, Object> additionalProperties = new HashMap<>();

        private Builder() {}

        @java.lang.Override
        public Builder from(BillingPriceResponseData other) {
            currency(other.getCurrency());
            externalPriceId(other.getExternalPriceId());
            id(other.getId());
            interval(other.getInterval());
            price(other.getPrice());
            return this;
        }

        @java.lang.Override
        @JsonSetter("currency")
        public ExternalPriceIdStage currency(String currency) {
            this.currency = currency;
            return this;
        }

        @java.lang.Override
        @JsonSetter("external_price_id")
        public IdStage externalPriceId(String externalPriceId) {
            this.externalPriceId = externalPriceId;
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
        public PriceStage interval(String interval) {
            this.interval = interval;
            return this;
        }

        @java.lang.Override
        @JsonSetter("price")
        public _FinalStage price(int price) {
            this.price = price;
            return this;
        }

        @java.lang.Override
        public BillingPriceResponseData build() {
            return new BillingPriceResponseData(currency, externalPriceId, id, interval, price, additionalProperties);
        }
    }
}
