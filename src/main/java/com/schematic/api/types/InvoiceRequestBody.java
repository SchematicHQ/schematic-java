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

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonDeserialize(builder = InvoiceRequestBody.Builder.class)
public final class InvoiceRequestBody {
    private final int amountDue;

    private final int amountPaid;

    private final int amountRemaining;

    private final String collectionMethod;

    private final String currency;

    private final String customerExternalId;

    private final Optional<OffsetDateTime> dueDate;

    private final Optional<String> paymentMethodExternalId;

    private final Optional<String> subscriptionExternalId;

    private final int subtotal;

    private final Optional<String> url;

    private final Map<String, Object> additionalProperties;

    private InvoiceRequestBody(
            int amountDue,
            int amountPaid,
            int amountRemaining,
            String collectionMethod,
            String currency,
            String customerExternalId,
            Optional<OffsetDateTime> dueDate,
            Optional<String> paymentMethodExternalId,
            Optional<String> subscriptionExternalId,
            int subtotal,
            Optional<String> url,
            Map<String, Object> additionalProperties) {
        this.amountDue = amountDue;
        this.amountPaid = amountPaid;
        this.amountRemaining = amountRemaining;
        this.collectionMethod = collectionMethod;
        this.currency = currency;
        this.customerExternalId = customerExternalId;
        this.dueDate = dueDate;
        this.paymentMethodExternalId = paymentMethodExternalId;
        this.subscriptionExternalId = subscriptionExternalId;
        this.subtotal = subtotal;
        this.url = url;
        this.additionalProperties = additionalProperties;
    }

    @JsonProperty("amount_due")
    public int getAmountDue() {
        return amountDue;
    }

    @JsonProperty("amount_paid")
    public int getAmountPaid() {
        return amountPaid;
    }

    @JsonProperty("amount_remaining")
    public int getAmountRemaining() {
        return amountRemaining;
    }

    @JsonProperty("collection_method")
    public String getCollectionMethod() {
        return collectionMethod;
    }

    @JsonProperty("currency")
    public String getCurrency() {
        return currency;
    }

    @JsonProperty("customer_external_id")
    public String getCustomerExternalId() {
        return customerExternalId;
    }

    @JsonProperty("due_date")
    public Optional<OffsetDateTime> getDueDate() {
        return dueDate;
    }

    @JsonProperty("payment_method_external_id")
    public Optional<String> getPaymentMethodExternalId() {
        return paymentMethodExternalId;
    }

    @JsonProperty("subscription_external_id")
    public Optional<String> getSubscriptionExternalId() {
        return subscriptionExternalId;
    }

    @JsonProperty("subtotal")
    public int getSubtotal() {
        return subtotal;
    }

    @JsonProperty("url")
    public Optional<String> getUrl() {
        return url;
    }

    @java.lang.Override
    public boolean equals(Object other) {
        if (this == other) return true;
        return other instanceof InvoiceRequestBody && equalTo((InvoiceRequestBody) other);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    private boolean equalTo(InvoiceRequestBody other) {
        return amountDue == other.amountDue
                && amountPaid == other.amountPaid
                && amountRemaining == other.amountRemaining
                && collectionMethod.equals(other.collectionMethod)
                && currency.equals(other.currency)
                && customerExternalId.equals(other.customerExternalId)
                && dueDate.equals(other.dueDate)
                && paymentMethodExternalId.equals(other.paymentMethodExternalId)
                && subscriptionExternalId.equals(other.subscriptionExternalId)
                && subtotal == other.subtotal
                && url.equals(other.url);
    }

    @java.lang.Override
    public int hashCode() {
        return Objects.hash(
                this.amountDue,
                this.amountPaid,
                this.amountRemaining,
                this.collectionMethod,
                this.currency,
                this.customerExternalId,
                this.dueDate,
                this.paymentMethodExternalId,
                this.subscriptionExternalId,
                this.subtotal,
                this.url);
    }

    @java.lang.Override
    public String toString() {
        return ObjectMappers.stringify(this);
    }

    public static AmountDueStage builder() {
        return new Builder();
    }

    public interface AmountDueStage {
        AmountPaidStage amountDue(int amountDue);

        Builder from(InvoiceRequestBody other);
    }

    public interface AmountPaidStage {
        AmountRemainingStage amountPaid(int amountPaid);
    }

    public interface AmountRemainingStage {
        CollectionMethodStage amountRemaining(int amountRemaining);
    }

    public interface CollectionMethodStage {
        CurrencyStage collectionMethod(String collectionMethod);
    }

    public interface CurrencyStage {
        CustomerExternalIdStage currency(String currency);
    }

    public interface CustomerExternalIdStage {
        SubtotalStage customerExternalId(String customerExternalId);
    }

    public interface SubtotalStage {
        _FinalStage subtotal(int subtotal);
    }

    public interface _FinalStage {
        InvoiceRequestBody build();

        _FinalStage dueDate(Optional<OffsetDateTime> dueDate);

        _FinalStage dueDate(OffsetDateTime dueDate);

        _FinalStage paymentMethodExternalId(Optional<String> paymentMethodExternalId);

        _FinalStage paymentMethodExternalId(String paymentMethodExternalId);

        _FinalStage subscriptionExternalId(Optional<String> subscriptionExternalId);

        _FinalStage subscriptionExternalId(String subscriptionExternalId);

        _FinalStage url(Optional<String> url);

        _FinalStage url(String url);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static final class Builder
            implements AmountDueStage,
                    AmountPaidStage,
                    AmountRemainingStage,
                    CollectionMethodStage,
                    CurrencyStage,
                    CustomerExternalIdStage,
                    SubtotalStage,
                    _FinalStage {
        private int amountDue;

        private int amountPaid;

        private int amountRemaining;

        private String collectionMethod;

        private String currency;

        private String customerExternalId;

        private int subtotal;

        private Optional<String> url = Optional.empty();

        private Optional<String> subscriptionExternalId = Optional.empty();

        private Optional<String> paymentMethodExternalId = Optional.empty();

        private Optional<OffsetDateTime> dueDate = Optional.empty();

        @JsonAnySetter
        private Map<String, Object> additionalProperties = new HashMap<>();

        private Builder() {}

        @java.lang.Override
        public Builder from(InvoiceRequestBody other) {
            amountDue(other.getAmountDue());
            amountPaid(other.getAmountPaid());
            amountRemaining(other.getAmountRemaining());
            collectionMethod(other.getCollectionMethod());
            currency(other.getCurrency());
            customerExternalId(other.getCustomerExternalId());
            dueDate(other.getDueDate());
            paymentMethodExternalId(other.getPaymentMethodExternalId());
            subscriptionExternalId(other.getSubscriptionExternalId());
            subtotal(other.getSubtotal());
            url(other.getUrl());
            return this;
        }

        @java.lang.Override
        @JsonSetter("amount_due")
        public AmountPaidStage amountDue(int amountDue) {
            this.amountDue = amountDue;
            return this;
        }

        @java.lang.Override
        @JsonSetter("amount_paid")
        public AmountRemainingStage amountPaid(int amountPaid) {
            this.amountPaid = amountPaid;
            return this;
        }

        @java.lang.Override
        @JsonSetter("amount_remaining")
        public CollectionMethodStage amountRemaining(int amountRemaining) {
            this.amountRemaining = amountRemaining;
            return this;
        }

        @java.lang.Override
        @JsonSetter("collection_method")
        public CurrencyStage collectionMethod(String collectionMethod) {
            this.collectionMethod = collectionMethod;
            return this;
        }

        @java.lang.Override
        @JsonSetter("currency")
        public CustomerExternalIdStage currency(String currency) {
            this.currency = currency;
            return this;
        }

        @java.lang.Override
        @JsonSetter("customer_external_id")
        public SubtotalStage customerExternalId(String customerExternalId) {
            this.customerExternalId = customerExternalId;
            return this;
        }

        @java.lang.Override
        @JsonSetter("subtotal")
        public _FinalStage subtotal(int subtotal) {
            this.subtotal = subtotal;
            return this;
        }

        @java.lang.Override
        public _FinalStage url(String url) {
            this.url = Optional.of(url);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "url", nulls = Nulls.SKIP)
        public _FinalStage url(Optional<String> url) {
            this.url = url;
            return this;
        }

        @java.lang.Override
        public _FinalStage subscriptionExternalId(String subscriptionExternalId) {
            this.subscriptionExternalId = Optional.of(subscriptionExternalId);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "subscription_external_id", nulls = Nulls.SKIP)
        public _FinalStage subscriptionExternalId(Optional<String> subscriptionExternalId) {
            this.subscriptionExternalId = subscriptionExternalId;
            return this;
        }

        @java.lang.Override
        public _FinalStage paymentMethodExternalId(String paymentMethodExternalId) {
            this.paymentMethodExternalId = Optional.of(paymentMethodExternalId);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "payment_method_external_id", nulls = Nulls.SKIP)
        public _FinalStage paymentMethodExternalId(Optional<String> paymentMethodExternalId) {
            this.paymentMethodExternalId = paymentMethodExternalId;
            return this;
        }

        @java.lang.Override
        public _FinalStage dueDate(OffsetDateTime dueDate) {
            this.dueDate = Optional.of(dueDate);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "due_date", nulls = Nulls.SKIP)
        public _FinalStage dueDate(Optional<OffsetDateTime> dueDate) {
            this.dueDate = dueDate;
            return this;
        }

        @java.lang.Override
        public InvoiceRequestBody build() {
            return new InvoiceRequestBody(
                    amountDue,
                    amountPaid,
                    amountRemaining,
                    collectionMethod,
                    currency,
                    customerExternalId,
                    dueDate,
                    paymentMethodExternalId,
                    subscriptionExternalId,
                    subtotal,
                    url,
                    additionalProperties);
        }
    }
}
