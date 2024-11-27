/**
 * This file was auto-generated by Fern from our API Definition.
 */
package com.schematic.api.resources.plans.requests;

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
@JsonDeserialize(builder = UpsertBillingProductRequestBody.Builder.class)
public final class UpsertBillingProductRequestBody {
    private final Optional<String> billingProductId;

    private final boolean isFreePlan;

    private final boolean isTrialable;

    private final Optional<String> monthlyPriceId;

    private final Optional<Integer> trialDays;

    private final Optional<String> yearlyPriceId;

    private final Map<String, Object> additionalProperties;

    private UpsertBillingProductRequestBody(
            Optional<String> billingProductId,
            boolean isFreePlan,
            boolean isTrialable,
            Optional<String> monthlyPriceId,
            Optional<Integer> trialDays,
            Optional<String> yearlyPriceId,
            Map<String, Object> additionalProperties) {
        this.billingProductId = billingProductId;
        this.isFreePlan = isFreePlan;
        this.isTrialable = isTrialable;
        this.monthlyPriceId = monthlyPriceId;
        this.trialDays = trialDays;
        this.yearlyPriceId = yearlyPriceId;
        this.additionalProperties = additionalProperties;
    }

    @JsonProperty("billing_product_id")
    public Optional<String> getBillingProductId() {
        return billingProductId;
    }

    @JsonProperty("is_free_plan")
    public boolean getIsFreePlan() {
        return isFreePlan;
    }

    @JsonProperty("is_trialable")
    public boolean getIsTrialable() {
        return isTrialable;
    }

    @JsonProperty("monthly_price_id")
    public Optional<String> getMonthlyPriceId() {
        return monthlyPriceId;
    }

    @JsonProperty("trial_days")
    public Optional<Integer> getTrialDays() {
        return trialDays;
    }

    @JsonProperty("yearly_price_id")
    public Optional<String> getYearlyPriceId() {
        return yearlyPriceId;
    }

    @java.lang.Override
    public boolean equals(Object other) {
        if (this == other) return true;
        return other instanceof UpsertBillingProductRequestBody && equalTo((UpsertBillingProductRequestBody) other);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    private boolean equalTo(UpsertBillingProductRequestBody other) {
        return billingProductId.equals(other.billingProductId)
                && isFreePlan == other.isFreePlan
                && isTrialable == other.isTrialable
                && monthlyPriceId.equals(other.monthlyPriceId)
                && trialDays.equals(other.trialDays)
                && yearlyPriceId.equals(other.yearlyPriceId);
    }

    @java.lang.Override
    public int hashCode() {
        return Objects.hash(
                this.billingProductId,
                this.isFreePlan,
                this.isTrialable,
                this.monthlyPriceId,
                this.trialDays,
                this.yearlyPriceId);
    }

    @java.lang.Override
    public String toString() {
        return ObjectMappers.stringify(this);
    }

    public static IsFreePlanStage builder() {
        return new Builder();
    }

    public interface IsFreePlanStage {
        IsTrialableStage isFreePlan(boolean isFreePlan);

        Builder from(UpsertBillingProductRequestBody other);
    }

    public interface IsTrialableStage {
        _FinalStage isTrialable(boolean isTrialable);
    }

    public interface _FinalStage {
        UpsertBillingProductRequestBody build();

        _FinalStage billingProductId(Optional<String> billingProductId);

        _FinalStage billingProductId(String billingProductId);

        _FinalStage monthlyPriceId(Optional<String> monthlyPriceId);

        _FinalStage monthlyPriceId(String monthlyPriceId);

        _FinalStage trialDays(Optional<Integer> trialDays);

        _FinalStage trialDays(Integer trialDays);

        _FinalStage yearlyPriceId(Optional<String> yearlyPriceId);

        _FinalStage yearlyPriceId(String yearlyPriceId);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static final class Builder implements IsFreePlanStage, IsTrialableStage, _FinalStage {
        private boolean isFreePlan;

        private boolean isTrialable;

        private Optional<String> yearlyPriceId = Optional.empty();

        private Optional<Integer> trialDays = Optional.empty();

        private Optional<String> monthlyPriceId = Optional.empty();

        private Optional<String> billingProductId = Optional.empty();

        @JsonAnySetter
        private Map<String, Object> additionalProperties = new HashMap<>();

        private Builder() {}

        @java.lang.Override
        public Builder from(UpsertBillingProductRequestBody other) {
            billingProductId(other.getBillingProductId());
            isFreePlan(other.getIsFreePlan());
            isTrialable(other.getIsTrialable());
            monthlyPriceId(other.getMonthlyPriceId());
            trialDays(other.getTrialDays());
            yearlyPriceId(other.getYearlyPriceId());
            return this;
        }

        @java.lang.Override
        @JsonSetter("is_free_plan")
        public IsTrialableStage isFreePlan(boolean isFreePlan) {
            this.isFreePlan = isFreePlan;
            return this;
        }

        @java.lang.Override
        @JsonSetter("is_trialable")
        public _FinalStage isTrialable(boolean isTrialable) {
            this.isTrialable = isTrialable;
            return this;
        }

        @java.lang.Override
        public _FinalStage yearlyPriceId(String yearlyPriceId) {
            this.yearlyPriceId = Optional.of(yearlyPriceId);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "yearly_price_id", nulls = Nulls.SKIP)
        public _FinalStage yearlyPriceId(Optional<String> yearlyPriceId) {
            this.yearlyPriceId = yearlyPriceId;
            return this;
        }

        @java.lang.Override
        public _FinalStage trialDays(Integer trialDays) {
            this.trialDays = Optional.of(trialDays);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "trial_days", nulls = Nulls.SKIP)
        public _FinalStage trialDays(Optional<Integer> trialDays) {
            this.trialDays = trialDays;
            return this;
        }

        @java.lang.Override
        public _FinalStage monthlyPriceId(String monthlyPriceId) {
            this.monthlyPriceId = Optional.of(monthlyPriceId);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "monthly_price_id", nulls = Nulls.SKIP)
        public _FinalStage monthlyPriceId(Optional<String> monthlyPriceId) {
            this.monthlyPriceId = monthlyPriceId;
            return this;
        }

        @java.lang.Override
        public _FinalStage billingProductId(String billingProductId) {
            this.billingProductId = Optional.of(billingProductId);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "billing_product_id", nulls = Nulls.SKIP)
        public _FinalStage billingProductId(Optional<String> billingProductId) {
            this.billingProductId = billingProductId;
            return this;
        }

        @java.lang.Override
        public UpsertBillingProductRequestBody build() {
            return new UpsertBillingProductRequestBody(
                    billingProductId,
                    isFreePlan,
                    isTrialable,
                    monthlyPriceId,
                    trialDays,
                    yearlyPriceId,
                    additionalProperties);
        }
    }
}
