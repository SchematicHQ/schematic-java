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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@JsonInclude(JsonInclude.Include.NON_ABSENT)
@JsonDeserialize(builder = CheckoutDataResponseData.Builder.class)
public final class CheckoutDataResponseData {
    private final List<PlanDetailResponseData> activeAddOns;

    private final Optional<PlanDetailResponseData> activePlan;

    private final List<UsageBasedEntitlementResponseData> activeUsageBasedEntitlements;

    private final Optional<CompanyDetailResponseData> company;

    private final Optional<FeatureUsageDetailResponseData> featureUsage;

    private final Optional<PlanDetailResponseData> selectedPlan;

    private final List<UsageBasedEntitlementResponseData> selectedUsageBasedEntitlements;

    private final Optional<CompanySubscriptionResponseData> subscription;

    private final Map<String, Object> additionalProperties;

    private CheckoutDataResponseData(
            List<PlanDetailResponseData> activeAddOns,
            Optional<PlanDetailResponseData> activePlan,
            List<UsageBasedEntitlementResponseData> activeUsageBasedEntitlements,
            Optional<CompanyDetailResponseData> company,
            Optional<FeatureUsageDetailResponseData> featureUsage,
            Optional<PlanDetailResponseData> selectedPlan,
            List<UsageBasedEntitlementResponseData> selectedUsageBasedEntitlements,
            Optional<CompanySubscriptionResponseData> subscription,
            Map<String, Object> additionalProperties) {
        this.activeAddOns = activeAddOns;
        this.activePlan = activePlan;
        this.activeUsageBasedEntitlements = activeUsageBasedEntitlements;
        this.company = company;
        this.featureUsage = featureUsage;
        this.selectedPlan = selectedPlan;
        this.selectedUsageBasedEntitlements = selectedUsageBasedEntitlements;
        this.subscription = subscription;
        this.additionalProperties = additionalProperties;
    }

    @JsonProperty("active_add_ons")
    public List<PlanDetailResponseData> getActiveAddOns() {
        return activeAddOns;
    }

    @JsonProperty("active_plan")
    public Optional<PlanDetailResponseData> getActivePlan() {
        return activePlan;
    }

    @JsonProperty("active_usage_based_entitlements")
    public List<UsageBasedEntitlementResponseData> getActiveUsageBasedEntitlements() {
        return activeUsageBasedEntitlements;
    }

    @JsonProperty("company")
    public Optional<CompanyDetailResponseData> getCompany() {
        return company;
    }

    @JsonProperty("feature_usage")
    public Optional<FeatureUsageDetailResponseData> getFeatureUsage() {
        return featureUsage;
    }

    @JsonProperty("selected_plan")
    public Optional<PlanDetailResponseData> getSelectedPlan() {
        return selectedPlan;
    }

    @JsonProperty("selected_usage_based_entitlements")
    public List<UsageBasedEntitlementResponseData> getSelectedUsageBasedEntitlements() {
        return selectedUsageBasedEntitlements;
    }

    @JsonProperty("subscription")
    public Optional<CompanySubscriptionResponseData> getSubscription() {
        return subscription;
    }

    @java.lang.Override
    public boolean equals(Object other) {
        if (this == other) return true;
        return other instanceof CheckoutDataResponseData && equalTo((CheckoutDataResponseData) other);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    private boolean equalTo(CheckoutDataResponseData other) {
        return activeAddOns.equals(other.activeAddOns)
                && activePlan.equals(other.activePlan)
                && activeUsageBasedEntitlements.equals(other.activeUsageBasedEntitlements)
                && company.equals(other.company)
                && featureUsage.equals(other.featureUsage)
                && selectedPlan.equals(other.selectedPlan)
                && selectedUsageBasedEntitlements.equals(other.selectedUsageBasedEntitlements)
                && subscription.equals(other.subscription);
    }

    @java.lang.Override
    public int hashCode() {
        return Objects.hash(
                this.activeAddOns,
                this.activePlan,
                this.activeUsageBasedEntitlements,
                this.company,
                this.featureUsage,
                this.selectedPlan,
                this.selectedUsageBasedEntitlements,
                this.subscription);
    }

    @java.lang.Override
    public String toString() {
        return ObjectMappers.stringify(this);
    }

    public static Builder builder() {
        return new Builder();
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static final class Builder {
        private List<PlanDetailResponseData> activeAddOns = new ArrayList<>();

        private Optional<PlanDetailResponseData> activePlan = Optional.empty();

        private List<UsageBasedEntitlementResponseData> activeUsageBasedEntitlements = new ArrayList<>();

        private Optional<CompanyDetailResponseData> company = Optional.empty();

        private Optional<FeatureUsageDetailResponseData> featureUsage = Optional.empty();

        private Optional<PlanDetailResponseData> selectedPlan = Optional.empty();

        private List<UsageBasedEntitlementResponseData> selectedUsageBasedEntitlements = new ArrayList<>();

        private Optional<CompanySubscriptionResponseData> subscription = Optional.empty();

        @JsonAnySetter
        private Map<String, Object> additionalProperties = new HashMap<>();

        private Builder() {}

        public Builder from(CheckoutDataResponseData other) {
            activeAddOns(other.getActiveAddOns());
            activePlan(other.getActivePlan());
            activeUsageBasedEntitlements(other.getActiveUsageBasedEntitlements());
            company(other.getCompany());
            featureUsage(other.getFeatureUsage());
            selectedPlan(other.getSelectedPlan());
            selectedUsageBasedEntitlements(other.getSelectedUsageBasedEntitlements());
            subscription(other.getSubscription());
            return this;
        }

        @JsonSetter(value = "active_add_ons", nulls = Nulls.SKIP)
        public Builder activeAddOns(List<PlanDetailResponseData> activeAddOns) {
            this.activeAddOns.clear();
            this.activeAddOns.addAll(activeAddOns);
            return this;
        }

        public Builder addActiveAddOns(PlanDetailResponseData activeAddOns) {
            this.activeAddOns.add(activeAddOns);
            return this;
        }

        public Builder addAllActiveAddOns(List<PlanDetailResponseData> activeAddOns) {
            this.activeAddOns.addAll(activeAddOns);
            return this;
        }

        @JsonSetter(value = "active_plan", nulls = Nulls.SKIP)
        public Builder activePlan(Optional<PlanDetailResponseData> activePlan) {
            this.activePlan = activePlan;
            return this;
        }

        public Builder activePlan(PlanDetailResponseData activePlan) {
            this.activePlan = Optional.ofNullable(activePlan);
            return this;
        }

        @JsonSetter(value = "active_usage_based_entitlements", nulls = Nulls.SKIP)
        public Builder activeUsageBasedEntitlements(
                List<UsageBasedEntitlementResponseData> activeUsageBasedEntitlements) {
            this.activeUsageBasedEntitlements.clear();
            this.activeUsageBasedEntitlements.addAll(activeUsageBasedEntitlements);
            return this;
        }

        public Builder addActiveUsageBasedEntitlements(UsageBasedEntitlementResponseData activeUsageBasedEntitlements) {
            this.activeUsageBasedEntitlements.add(activeUsageBasedEntitlements);
            return this;
        }

        public Builder addAllActiveUsageBasedEntitlements(
                List<UsageBasedEntitlementResponseData> activeUsageBasedEntitlements) {
            this.activeUsageBasedEntitlements.addAll(activeUsageBasedEntitlements);
            return this;
        }

        @JsonSetter(value = "company", nulls = Nulls.SKIP)
        public Builder company(Optional<CompanyDetailResponseData> company) {
            this.company = company;
            return this;
        }

        public Builder company(CompanyDetailResponseData company) {
            this.company = Optional.ofNullable(company);
            return this;
        }

        @JsonSetter(value = "feature_usage", nulls = Nulls.SKIP)
        public Builder featureUsage(Optional<FeatureUsageDetailResponseData> featureUsage) {
            this.featureUsage = featureUsage;
            return this;
        }

        public Builder featureUsage(FeatureUsageDetailResponseData featureUsage) {
            this.featureUsage = Optional.ofNullable(featureUsage);
            return this;
        }

        @JsonSetter(value = "selected_plan", nulls = Nulls.SKIP)
        public Builder selectedPlan(Optional<PlanDetailResponseData> selectedPlan) {
            this.selectedPlan = selectedPlan;
            return this;
        }

        public Builder selectedPlan(PlanDetailResponseData selectedPlan) {
            this.selectedPlan = Optional.ofNullable(selectedPlan);
            return this;
        }

        @JsonSetter(value = "selected_usage_based_entitlements", nulls = Nulls.SKIP)
        public Builder selectedUsageBasedEntitlements(
                List<UsageBasedEntitlementResponseData> selectedUsageBasedEntitlements) {
            this.selectedUsageBasedEntitlements.clear();
            this.selectedUsageBasedEntitlements.addAll(selectedUsageBasedEntitlements);
            return this;
        }

        public Builder addSelectedUsageBasedEntitlements(
                UsageBasedEntitlementResponseData selectedUsageBasedEntitlements) {
            this.selectedUsageBasedEntitlements.add(selectedUsageBasedEntitlements);
            return this;
        }

        public Builder addAllSelectedUsageBasedEntitlements(
                List<UsageBasedEntitlementResponseData> selectedUsageBasedEntitlements) {
            this.selectedUsageBasedEntitlements.addAll(selectedUsageBasedEntitlements);
            return this;
        }

        @JsonSetter(value = "subscription", nulls = Nulls.SKIP)
        public Builder subscription(Optional<CompanySubscriptionResponseData> subscription) {
            this.subscription = subscription;
            return this;
        }

        public Builder subscription(CompanySubscriptionResponseData subscription) {
            this.subscription = Optional.ofNullable(subscription);
            return this;
        }

        public CheckoutDataResponseData build() {
            return new CheckoutDataResponseData(
                    activeAddOns,
                    activePlan,
                    activeUsageBasedEntitlements,
                    company,
                    featureUsage,
                    selectedPlan,
                    selectedUsageBasedEntitlements,
                    subscription,
                    additionalProperties);
        }
    }
}
