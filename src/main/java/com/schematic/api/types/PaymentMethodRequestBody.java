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
import org.jetbrains.annotations.NotNull;

@JsonInclude(JsonInclude.Include.NON_ABSENT)
@JsonDeserialize(builder = PaymentMethodRequestBody.Builder.class)
public final class PaymentMethodRequestBody {
    private final Optional<String> accountLast4;

    private final Optional<String> accountName;

    private final Optional<String> bankName;

    private final Optional<String> billingEmail;

    private final Optional<String> billingName;

    private final Optional<String> cardBrand;

    private final Optional<Integer> cardExpMonth;

    private final Optional<Integer> cardExpYear;

    private final Optional<String> cardLast4;

    private final String customerExternalId;

    private final String paymentMethodType;

    private final Optional<String> subscriptionExternalId;

    private final Map<String, Object> additionalProperties;

    private PaymentMethodRequestBody(
            Optional<String> accountLast4,
            Optional<String> accountName,
            Optional<String> bankName,
            Optional<String> billingEmail,
            Optional<String> billingName,
            Optional<String> cardBrand,
            Optional<Integer> cardExpMonth,
            Optional<Integer> cardExpYear,
            Optional<String> cardLast4,
            String customerExternalId,
            String paymentMethodType,
            Optional<String> subscriptionExternalId,
            Map<String, Object> additionalProperties) {
        this.accountLast4 = accountLast4;
        this.accountName = accountName;
        this.bankName = bankName;
        this.billingEmail = billingEmail;
        this.billingName = billingName;
        this.cardBrand = cardBrand;
        this.cardExpMonth = cardExpMonth;
        this.cardExpYear = cardExpYear;
        this.cardLast4 = cardLast4;
        this.customerExternalId = customerExternalId;
        this.paymentMethodType = paymentMethodType;
        this.subscriptionExternalId = subscriptionExternalId;
        this.additionalProperties = additionalProperties;
    }

    @JsonProperty("account_last4")
    public Optional<String> getAccountLast4() {
        return accountLast4;
    }

    @JsonProperty("account_name")
    public Optional<String> getAccountName() {
        return accountName;
    }

    @JsonProperty("bank_name")
    public Optional<String> getBankName() {
        return bankName;
    }

    @JsonProperty("billing_email")
    public Optional<String> getBillingEmail() {
        return billingEmail;
    }

    @JsonProperty("billing_name")
    public Optional<String> getBillingName() {
        return billingName;
    }

    @JsonProperty("card_brand")
    public Optional<String> getCardBrand() {
        return cardBrand;
    }

    @JsonProperty("card_exp_month")
    public Optional<Integer> getCardExpMonth() {
        return cardExpMonth;
    }

    @JsonProperty("card_exp_year")
    public Optional<Integer> getCardExpYear() {
        return cardExpYear;
    }

    @JsonProperty("card_last4")
    public Optional<String> getCardLast4() {
        return cardLast4;
    }

    @JsonProperty("customer_external_id")
    public String getCustomerExternalId() {
        return customerExternalId;
    }

    @JsonProperty("payment_method_type")
    public String getPaymentMethodType() {
        return paymentMethodType;
    }

    @JsonProperty("subscription_external_id")
    public Optional<String> getSubscriptionExternalId() {
        return subscriptionExternalId;
    }

    @java.lang.Override
    public boolean equals(Object other) {
        if (this == other) return true;
        return other instanceof PaymentMethodRequestBody && equalTo((PaymentMethodRequestBody) other);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    private boolean equalTo(PaymentMethodRequestBody other) {
        return accountLast4.equals(other.accountLast4)
                && accountName.equals(other.accountName)
                && bankName.equals(other.bankName)
                && billingEmail.equals(other.billingEmail)
                && billingName.equals(other.billingName)
                && cardBrand.equals(other.cardBrand)
                && cardExpMonth.equals(other.cardExpMonth)
                && cardExpYear.equals(other.cardExpYear)
                && cardLast4.equals(other.cardLast4)
                && customerExternalId.equals(other.customerExternalId)
                && paymentMethodType.equals(other.paymentMethodType)
                && subscriptionExternalId.equals(other.subscriptionExternalId);
    }

    @java.lang.Override
    public int hashCode() {
        return Objects.hash(
                this.accountLast4,
                this.accountName,
                this.bankName,
                this.billingEmail,
                this.billingName,
                this.cardBrand,
                this.cardExpMonth,
                this.cardExpYear,
                this.cardLast4,
                this.customerExternalId,
                this.paymentMethodType,
                this.subscriptionExternalId);
    }

    @java.lang.Override
    public String toString() {
        return ObjectMappers.stringify(this);
    }

    public static CustomerExternalIdStage builder() {
        return new Builder();
    }

    public interface CustomerExternalIdStage {
        PaymentMethodTypeStage customerExternalId(@NotNull String customerExternalId);

        Builder from(PaymentMethodRequestBody other);
    }

    public interface PaymentMethodTypeStage {
        _FinalStage paymentMethodType(@NotNull String paymentMethodType);
    }

    public interface _FinalStage {
        PaymentMethodRequestBody build();

        _FinalStage accountLast4(Optional<String> accountLast4);

        _FinalStage accountLast4(String accountLast4);

        _FinalStage accountName(Optional<String> accountName);

        _FinalStage accountName(String accountName);

        _FinalStage bankName(Optional<String> bankName);

        _FinalStage bankName(String bankName);

        _FinalStage billingEmail(Optional<String> billingEmail);

        _FinalStage billingEmail(String billingEmail);

        _FinalStage billingName(Optional<String> billingName);

        _FinalStage billingName(String billingName);

        _FinalStage cardBrand(Optional<String> cardBrand);

        _FinalStage cardBrand(String cardBrand);

        _FinalStage cardExpMonth(Optional<Integer> cardExpMonth);

        _FinalStage cardExpMonth(Integer cardExpMonth);

        _FinalStage cardExpYear(Optional<Integer> cardExpYear);

        _FinalStage cardExpYear(Integer cardExpYear);

        _FinalStage cardLast4(Optional<String> cardLast4);

        _FinalStage cardLast4(String cardLast4);

        _FinalStage subscriptionExternalId(Optional<String> subscriptionExternalId);

        _FinalStage subscriptionExternalId(String subscriptionExternalId);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static final class Builder implements CustomerExternalIdStage, PaymentMethodTypeStage, _FinalStage {
        private String customerExternalId;

        private String paymentMethodType;

        private Optional<String> subscriptionExternalId = Optional.empty();

        private Optional<String> cardLast4 = Optional.empty();

        private Optional<Integer> cardExpYear = Optional.empty();

        private Optional<Integer> cardExpMonth = Optional.empty();

        private Optional<String> cardBrand = Optional.empty();

        private Optional<String> billingName = Optional.empty();

        private Optional<String> billingEmail = Optional.empty();

        private Optional<String> bankName = Optional.empty();

        private Optional<String> accountName = Optional.empty();

        private Optional<String> accountLast4 = Optional.empty();

        @JsonAnySetter
        private Map<String, Object> additionalProperties = new HashMap<>();

        private Builder() {}

        @java.lang.Override
        public Builder from(PaymentMethodRequestBody other) {
            accountLast4(other.getAccountLast4());
            accountName(other.getAccountName());
            bankName(other.getBankName());
            billingEmail(other.getBillingEmail());
            billingName(other.getBillingName());
            cardBrand(other.getCardBrand());
            cardExpMonth(other.getCardExpMonth());
            cardExpYear(other.getCardExpYear());
            cardLast4(other.getCardLast4());
            customerExternalId(other.getCustomerExternalId());
            paymentMethodType(other.getPaymentMethodType());
            subscriptionExternalId(other.getSubscriptionExternalId());
            return this;
        }

        @java.lang.Override
        @JsonSetter("customer_external_id")
        public PaymentMethodTypeStage customerExternalId(@NotNull String customerExternalId) {
            this.customerExternalId = Objects.requireNonNull(customerExternalId, "customerExternalId must not be null");
            return this;
        }

        @java.lang.Override
        @JsonSetter("payment_method_type")
        public _FinalStage paymentMethodType(@NotNull String paymentMethodType) {
            this.paymentMethodType = Objects.requireNonNull(paymentMethodType, "paymentMethodType must not be null");
            return this;
        }

        @java.lang.Override
        public _FinalStage subscriptionExternalId(String subscriptionExternalId) {
            this.subscriptionExternalId = Optional.ofNullable(subscriptionExternalId);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "subscription_external_id", nulls = Nulls.SKIP)
        public _FinalStage subscriptionExternalId(Optional<String> subscriptionExternalId) {
            this.subscriptionExternalId = subscriptionExternalId;
            return this;
        }

        @java.lang.Override
        public _FinalStage cardLast4(String cardLast4) {
            this.cardLast4 = Optional.ofNullable(cardLast4);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "card_last4", nulls = Nulls.SKIP)
        public _FinalStage cardLast4(Optional<String> cardLast4) {
            this.cardLast4 = cardLast4;
            return this;
        }

        @java.lang.Override
        public _FinalStage cardExpYear(Integer cardExpYear) {
            this.cardExpYear = Optional.ofNullable(cardExpYear);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "card_exp_year", nulls = Nulls.SKIP)
        public _FinalStage cardExpYear(Optional<Integer> cardExpYear) {
            this.cardExpYear = cardExpYear;
            return this;
        }

        @java.lang.Override
        public _FinalStage cardExpMonth(Integer cardExpMonth) {
            this.cardExpMonth = Optional.ofNullable(cardExpMonth);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "card_exp_month", nulls = Nulls.SKIP)
        public _FinalStage cardExpMonth(Optional<Integer> cardExpMonth) {
            this.cardExpMonth = cardExpMonth;
            return this;
        }

        @java.lang.Override
        public _FinalStage cardBrand(String cardBrand) {
            this.cardBrand = Optional.ofNullable(cardBrand);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "card_brand", nulls = Nulls.SKIP)
        public _FinalStage cardBrand(Optional<String> cardBrand) {
            this.cardBrand = cardBrand;
            return this;
        }

        @java.lang.Override
        public _FinalStage billingName(String billingName) {
            this.billingName = Optional.ofNullable(billingName);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "billing_name", nulls = Nulls.SKIP)
        public _FinalStage billingName(Optional<String> billingName) {
            this.billingName = billingName;
            return this;
        }

        @java.lang.Override
        public _FinalStage billingEmail(String billingEmail) {
            this.billingEmail = Optional.ofNullable(billingEmail);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "billing_email", nulls = Nulls.SKIP)
        public _FinalStage billingEmail(Optional<String> billingEmail) {
            this.billingEmail = billingEmail;
            return this;
        }

        @java.lang.Override
        public _FinalStage bankName(String bankName) {
            this.bankName = Optional.ofNullable(bankName);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "bank_name", nulls = Nulls.SKIP)
        public _FinalStage bankName(Optional<String> bankName) {
            this.bankName = bankName;
            return this;
        }

        @java.lang.Override
        public _FinalStage accountName(String accountName) {
            this.accountName = Optional.ofNullable(accountName);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "account_name", nulls = Nulls.SKIP)
        public _FinalStage accountName(Optional<String> accountName) {
            this.accountName = accountName;
            return this;
        }

        @java.lang.Override
        public _FinalStage accountLast4(String accountLast4) {
            this.accountLast4 = Optional.ofNullable(accountLast4);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "account_last4", nulls = Nulls.SKIP)
        public _FinalStage accountLast4(Optional<String> accountLast4) {
            this.accountLast4 = accountLast4;
            return this;
        }

        @java.lang.Override
        public PaymentMethodRequestBody build() {
            return new PaymentMethodRequestBody(
                    accountLast4,
                    accountName,
                    bankName,
                    billingEmail,
                    billingName,
                    cardBrand,
                    cardExpMonth,
                    cardExpYear,
                    cardLast4,
                    customerExternalId,
                    paymentMethodType,
                    subscriptionExternalId,
                    additionalProperties);
        }
    }
}
