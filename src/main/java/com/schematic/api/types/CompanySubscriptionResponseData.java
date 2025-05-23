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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;

@JsonInclude(JsonInclude.Include.NON_ABSENT)
@JsonDeserialize(builder = CompanySubscriptionResponseData.Builder.class)
public final class CompanySubscriptionResponseData {
    private final Optional<OffsetDateTime> cancelAt;

    private final boolean cancelAtPeriodEnd;

    private final String currency;

    private final String customerExternalId;

    private final List<BillingSubscriptionDiscountView> discounts;

    private final Optional<OffsetDateTime> expiredAt;

    private final String interval;

    private final Optional<InvoiceResponseData> latestInvoice;

    private final Optional<PaymentMethodResponseData> paymentMethod;

    private final List<BillingProductForSubscriptionResponseData> products;

    private final String status;

    private final String subscriptionExternalId;

    private final int totalPrice;

    private final Optional<OffsetDateTime> trialEnd;

    private final Map<String, Object> additionalProperties;

    private CompanySubscriptionResponseData(
            Optional<OffsetDateTime> cancelAt,
            boolean cancelAtPeriodEnd,
            String currency,
            String customerExternalId,
            List<BillingSubscriptionDiscountView> discounts,
            Optional<OffsetDateTime> expiredAt,
            String interval,
            Optional<InvoiceResponseData> latestInvoice,
            Optional<PaymentMethodResponseData> paymentMethod,
            List<BillingProductForSubscriptionResponseData> products,
            String status,
            String subscriptionExternalId,
            int totalPrice,
            Optional<OffsetDateTime> trialEnd,
            Map<String, Object> additionalProperties) {
        this.cancelAt = cancelAt;
        this.cancelAtPeriodEnd = cancelAtPeriodEnd;
        this.currency = currency;
        this.customerExternalId = customerExternalId;
        this.discounts = discounts;
        this.expiredAt = expiredAt;
        this.interval = interval;
        this.latestInvoice = latestInvoice;
        this.paymentMethod = paymentMethod;
        this.products = products;
        this.status = status;
        this.subscriptionExternalId = subscriptionExternalId;
        this.totalPrice = totalPrice;
        this.trialEnd = trialEnd;
        this.additionalProperties = additionalProperties;
    }

    @JsonProperty("cancel_at")
    public Optional<OffsetDateTime> getCancelAt() {
        return cancelAt;
    }

    @JsonProperty("cancel_at_period_end")
    public boolean getCancelAtPeriodEnd() {
        return cancelAtPeriodEnd;
    }

    @JsonProperty("currency")
    public String getCurrency() {
        return currency;
    }

    @JsonProperty("customer_external_id")
    public String getCustomerExternalId() {
        return customerExternalId;
    }

    @JsonProperty("discounts")
    public List<BillingSubscriptionDiscountView> getDiscounts() {
        return discounts;
    }

    @JsonProperty("expired_at")
    public Optional<OffsetDateTime> getExpiredAt() {
        return expiredAt;
    }

    @JsonProperty("interval")
    public String getInterval() {
        return interval;
    }

    @JsonProperty("latest_invoice")
    public Optional<InvoiceResponseData> getLatestInvoice() {
        return latestInvoice;
    }

    @JsonProperty("payment_method")
    public Optional<PaymentMethodResponseData> getPaymentMethod() {
        return paymentMethod;
    }

    @JsonProperty("products")
    public List<BillingProductForSubscriptionResponseData> getProducts() {
        return products;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("subscription_external_id")
    public String getSubscriptionExternalId() {
        return subscriptionExternalId;
    }

    @JsonProperty("total_price")
    public int getTotalPrice() {
        return totalPrice;
    }

    @JsonProperty("trial_end")
    public Optional<OffsetDateTime> getTrialEnd() {
        return trialEnd;
    }

    @java.lang.Override
    public boolean equals(Object other) {
        if (this == other) return true;
        return other instanceof CompanySubscriptionResponseData && equalTo((CompanySubscriptionResponseData) other);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    private boolean equalTo(CompanySubscriptionResponseData other) {
        return cancelAt.equals(other.cancelAt)
                && cancelAtPeriodEnd == other.cancelAtPeriodEnd
                && currency.equals(other.currency)
                && customerExternalId.equals(other.customerExternalId)
                && discounts.equals(other.discounts)
                && expiredAt.equals(other.expiredAt)
                && interval.equals(other.interval)
                && latestInvoice.equals(other.latestInvoice)
                && paymentMethod.equals(other.paymentMethod)
                && products.equals(other.products)
                && status.equals(other.status)
                && subscriptionExternalId.equals(other.subscriptionExternalId)
                && totalPrice == other.totalPrice
                && trialEnd.equals(other.trialEnd);
    }

    @java.lang.Override
    public int hashCode() {
        return Objects.hash(
                this.cancelAt,
                this.cancelAtPeriodEnd,
                this.currency,
                this.customerExternalId,
                this.discounts,
                this.expiredAt,
                this.interval,
                this.latestInvoice,
                this.paymentMethod,
                this.products,
                this.status,
                this.subscriptionExternalId,
                this.totalPrice,
                this.trialEnd);
    }

    @java.lang.Override
    public String toString() {
        return ObjectMappers.stringify(this);
    }

    public static CancelAtPeriodEndStage builder() {
        return new Builder();
    }

    public interface CancelAtPeriodEndStage {
        CurrencyStage cancelAtPeriodEnd(boolean cancelAtPeriodEnd);

        Builder from(CompanySubscriptionResponseData other);
    }

    public interface CurrencyStage {
        CustomerExternalIdStage currency(@NotNull String currency);
    }

    public interface CustomerExternalIdStage {
        IntervalStage customerExternalId(@NotNull String customerExternalId);
    }

    public interface IntervalStage {
        StatusStage interval(@NotNull String interval);
    }

    public interface StatusStage {
        SubscriptionExternalIdStage status(@NotNull String status);
    }

    public interface SubscriptionExternalIdStage {
        TotalPriceStage subscriptionExternalId(@NotNull String subscriptionExternalId);
    }

    public interface TotalPriceStage {
        _FinalStage totalPrice(int totalPrice);
    }

    public interface _FinalStage {
        CompanySubscriptionResponseData build();

        _FinalStage cancelAt(Optional<OffsetDateTime> cancelAt);

        _FinalStage cancelAt(OffsetDateTime cancelAt);

        _FinalStage discounts(List<BillingSubscriptionDiscountView> discounts);

        _FinalStage addDiscounts(BillingSubscriptionDiscountView discounts);

        _FinalStage addAllDiscounts(List<BillingSubscriptionDiscountView> discounts);

        _FinalStage expiredAt(Optional<OffsetDateTime> expiredAt);

        _FinalStage expiredAt(OffsetDateTime expiredAt);

        _FinalStage latestInvoice(Optional<InvoiceResponseData> latestInvoice);

        _FinalStage latestInvoice(InvoiceResponseData latestInvoice);

        _FinalStage paymentMethod(Optional<PaymentMethodResponseData> paymentMethod);

        _FinalStage paymentMethod(PaymentMethodResponseData paymentMethod);

        _FinalStage products(List<BillingProductForSubscriptionResponseData> products);

        _FinalStage addProducts(BillingProductForSubscriptionResponseData products);

        _FinalStage addAllProducts(List<BillingProductForSubscriptionResponseData> products);

        _FinalStage trialEnd(Optional<OffsetDateTime> trialEnd);

        _FinalStage trialEnd(OffsetDateTime trialEnd);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static final class Builder
            implements CancelAtPeriodEndStage,
                    CurrencyStage,
                    CustomerExternalIdStage,
                    IntervalStage,
                    StatusStage,
                    SubscriptionExternalIdStage,
                    TotalPriceStage,
                    _FinalStage {
        private boolean cancelAtPeriodEnd;

        private String currency;

        private String customerExternalId;

        private String interval;

        private String status;

        private String subscriptionExternalId;

        private int totalPrice;

        private Optional<OffsetDateTime> trialEnd = Optional.empty();

        private List<BillingProductForSubscriptionResponseData> products = new ArrayList<>();

        private Optional<PaymentMethodResponseData> paymentMethod = Optional.empty();

        private Optional<InvoiceResponseData> latestInvoice = Optional.empty();

        private Optional<OffsetDateTime> expiredAt = Optional.empty();

        private List<BillingSubscriptionDiscountView> discounts = new ArrayList<>();

        private Optional<OffsetDateTime> cancelAt = Optional.empty();

        @JsonAnySetter
        private Map<String, Object> additionalProperties = new HashMap<>();

        private Builder() {}

        @java.lang.Override
        public Builder from(CompanySubscriptionResponseData other) {
            cancelAt(other.getCancelAt());
            cancelAtPeriodEnd(other.getCancelAtPeriodEnd());
            currency(other.getCurrency());
            customerExternalId(other.getCustomerExternalId());
            discounts(other.getDiscounts());
            expiredAt(other.getExpiredAt());
            interval(other.getInterval());
            latestInvoice(other.getLatestInvoice());
            paymentMethod(other.getPaymentMethod());
            products(other.getProducts());
            status(other.getStatus());
            subscriptionExternalId(other.getSubscriptionExternalId());
            totalPrice(other.getTotalPrice());
            trialEnd(other.getTrialEnd());
            return this;
        }

        @java.lang.Override
        @JsonSetter("cancel_at_period_end")
        public CurrencyStage cancelAtPeriodEnd(boolean cancelAtPeriodEnd) {
            this.cancelAtPeriodEnd = cancelAtPeriodEnd;
            return this;
        }

        @java.lang.Override
        @JsonSetter("currency")
        public CustomerExternalIdStage currency(@NotNull String currency) {
            this.currency = Objects.requireNonNull(currency, "currency must not be null");
            return this;
        }

        @java.lang.Override
        @JsonSetter("customer_external_id")
        public IntervalStage customerExternalId(@NotNull String customerExternalId) {
            this.customerExternalId = Objects.requireNonNull(customerExternalId, "customerExternalId must not be null");
            return this;
        }

        @java.lang.Override
        @JsonSetter("interval")
        public StatusStage interval(@NotNull String interval) {
            this.interval = Objects.requireNonNull(interval, "interval must not be null");
            return this;
        }

        @java.lang.Override
        @JsonSetter("status")
        public SubscriptionExternalIdStage status(@NotNull String status) {
            this.status = Objects.requireNonNull(status, "status must not be null");
            return this;
        }

        @java.lang.Override
        @JsonSetter("subscription_external_id")
        public TotalPriceStage subscriptionExternalId(@NotNull String subscriptionExternalId) {
            this.subscriptionExternalId =
                    Objects.requireNonNull(subscriptionExternalId, "subscriptionExternalId must not be null");
            return this;
        }

        @java.lang.Override
        @JsonSetter("total_price")
        public _FinalStage totalPrice(int totalPrice) {
            this.totalPrice = totalPrice;
            return this;
        }

        @java.lang.Override
        public _FinalStage trialEnd(OffsetDateTime trialEnd) {
            this.trialEnd = Optional.ofNullable(trialEnd);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "trial_end", nulls = Nulls.SKIP)
        public _FinalStage trialEnd(Optional<OffsetDateTime> trialEnd) {
            this.trialEnd = trialEnd;
            return this;
        }

        @java.lang.Override
        public _FinalStage addAllProducts(List<BillingProductForSubscriptionResponseData> products) {
            this.products.addAll(products);
            return this;
        }

        @java.lang.Override
        public _FinalStage addProducts(BillingProductForSubscriptionResponseData products) {
            this.products.add(products);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "products", nulls = Nulls.SKIP)
        public _FinalStage products(List<BillingProductForSubscriptionResponseData> products) {
            this.products.clear();
            this.products.addAll(products);
            return this;
        }

        @java.lang.Override
        public _FinalStage paymentMethod(PaymentMethodResponseData paymentMethod) {
            this.paymentMethod = Optional.ofNullable(paymentMethod);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "payment_method", nulls = Nulls.SKIP)
        public _FinalStage paymentMethod(Optional<PaymentMethodResponseData> paymentMethod) {
            this.paymentMethod = paymentMethod;
            return this;
        }

        @java.lang.Override
        public _FinalStage latestInvoice(InvoiceResponseData latestInvoice) {
            this.latestInvoice = Optional.ofNullable(latestInvoice);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "latest_invoice", nulls = Nulls.SKIP)
        public _FinalStage latestInvoice(Optional<InvoiceResponseData> latestInvoice) {
            this.latestInvoice = latestInvoice;
            return this;
        }

        @java.lang.Override
        public _FinalStage expiredAt(OffsetDateTime expiredAt) {
            this.expiredAt = Optional.ofNullable(expiredAt);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "expired_at", nulls = Nulls.SKIP)
        public _FinalStage expiredAt(Optional<OffsetDateTime> expiredAt) {
            this.expiredAt = expiredAt;
            return this;
        }

        @java.lang.Override
        public _FinalStage addAllDiscounts(List<BillingSubscriptionDiscountView> discounts) {
            this.discounts.addAll(discounts);
            return this;
        }

        @java.lang.Override
        public _FinalStage addDiscounts(BillingSubscriptionDiscountView discounts) {
            this.discounts.add(discounts);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "discounts", nulls = Nulls.SKIP)
        public _FinalStage discounts(List<BillingSubscriptionDiscountView> discounts) {
            this.discounts.clear();
            this.discounts.addAll(discounts);
            return this;
        }

        @java.lang.Override
        public _FinalStage cancelAt(OffsetDateTime cancelAt) {
            this.cancelAt = Optional.ofNullable(cancelAt);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "cancel_at", nulls = Nulls.SKIP)
        public _FinalStage cancelAt(Optional<OffsetDateTime> cancelAt) {
            this.cancelAt = cancelAt;
            return this;
        }

        @java.lang.Override
        public CompanySubscriptionResponseData build() {
            return new CompanySubscriptionResponseData(
                    cancelAt,
                    cancelAtPeriodEnd,
                    currency,
                    customerExternalId,
                    discounts,
                    expiredAt,
                    interval,
                    latestInvoice,
                    paymentMethod,
                    products,
                    status,
                    subscriptionExternalId,
                    totalPrice,
                    trialEnd,
                    additionalProperties);
        }
    }
}
