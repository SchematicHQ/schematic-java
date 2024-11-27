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

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonDeserialize(builder = ComponentHydrateResponseData.Builder.class)
public final class ComponentHydrateResponseData {
    private final List<CompanyPlanDetailResponseData> activeAddOns;

    private final List<CompanyPlanDetailResponseData> activePlans;

    private final List<UsageBasedEntitlementResponseData> activeUsageBasedEntitlements;

    private final Optional<ComponentCapabilities> capabilities;

    private final Optional<CompanyDetailResponseData> company;

    private final Optional<ComponentResponseData> component;

    private final Optional<FeatureUsageDetailResponseData> featureUsage;

    private final Optional<StripeEmbedInfo> stripeEmbed;

    private final Optional<CompanySubscriptionResponseData> subscription;

    private final Optional<InvoiceResponseData> upcomingInvoice;

    private final Map<String, Object> additionalProperties;

    private ComponentHydrateResponseData(
            List<CompanyPlanDetailResponseData> activeAddOns,
            List<CompanyPlanDetailResponseData> activePlans,
            List<UsageBasedEntitlementResponseData> activeUsageBasedEntitlements,
            Optional<ComponentCapabilities> capabilities,
            Optional<CompanyDetailResponseData> company,
            Optional<ComponentResponseData> component,
            Optional<FeatureUsageDetailResponseData> featureUsage,
            Optional<StripeEmbedInfo> stripeEmbed,
            Optional<CompanySubscriptionResponseData> subscription,
            Optional<InvoiceResponseData> upcomingInvoice,
            Map<String, Object> additionalProperties) {
        this.activeAddOns = activeAddOns;
        this.activePlans = activePlans;
        this.activeUsageBasedEntitlements = activeUsageBasedEntitlements;
        this.capabilities = capabilities;
        this.company = company;
        this.component = component;
        this.featureUsage = featureUsage;
        this.stripeEmbed = stripeEmbed;
        this.subscription = subscription;
        this.upcomingInvoice = upcomingInvoice;
        this.additionalProperties = additionalProperties;
    }

    @JsonProperty("active_add_ons")
    public List<CompanyPlanDetailResponseData> getActiveAddOns() {
        return activeAddOns;
    }

    @JsonProperty("active_plans")
    public List<CompanyPlanDetailResponseData> getActivePlans() {
        return activePlans;
    }

    @JsonProperty("active_usage_based_entitlements")
    public List<UsageBasedEntitlementResponseData> getActiveUsageBasedEntitlements() {
        return activeUsageBasedEntitlements;
    }

    @JsonProperty("capabilities")
    public Optional<ComponentCapabilities> getCapabilities() {
        return capabilities;
    }

    @JsonProperty("company")
    public Optional<CompanyDetailResponseData> getCompany() {
        return company;
    }

    @JsonProperty("component")
    public Optional<ComponentResponseData> getComponent() {
        return component;
    }

    @JsonProperty("feature_usage")
    public Optional<FeatureUsageDetailResponseData> getFeatureUsage() {
        return featureUsage;
    }

    @JsonProperty("stripe_embed")
    public Optional<StripeEmbedInfo> getStripeEmbed() {
        return stripeEmbed;
    }

    @JsonProperty("subscription")
    public Optional<CompanySubscriptionResponseData> getSubscription() {
        return subscription;
    }

    @JsonProperty("upcoming_invoice")
    public Optional<InvoiceResponseData> getUpcomingInvoice() {
        return upcomingInvoice;
    }

    @java.lang.Override
    public boolean equals(Object other) {
        if (this == other) return true;
        return other instanceof ComponentHydrateResponseData && equalTo((ComponentHydrateResponseData) other);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    private boolean equalTo(ComponentHydrateResponseData other) {
        return activeAddOns.equals(other.activeAddOns)
                && activePlans.equals(other.activePlans)
                && activeUsageBasedEntitlements.equals(other.activeUsageBasedEntitlements)
                && capabilities.equals(other.capabilities)
                && company.equals(other.company)
                && component.equals(other.component)
                && featureUsage.equals(other.featureUsage)
                && stripeEmbed.equals(other.stripeEmbed)
                && subscription.equals(other.subscription)
                && upcomingInvoice.equals(other.upcomingInvoice);
    }

    @java.lang.Override
    public int hashCode() {
        return Objects.hash(
                this.activeAddOns,
                this.activePlans,
                this.activeUsageBasedEntitlements,
                this.capabilities,
                this.company,
                this.component,
                this.featureUsage,
                this.stripeEmbed,
                this.subscription,
                this.upcomingInvoice);
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
        private List<CompanyPlanDetailResponseData> activeAddOns = new ArrayList<>();

        private List<CompanyPlanDetailResponseData> activePlans = new ArrayList<>();

        private List<UsageBasedEntitlementResponseData> activeUsageBasedEntitlements = new ArrayList<>();

        private Optional<ComponentCapabilities> capabilities = Optional.empty();

        private Optional<CompanyDetailResponseData> company = Optional.empty();

        private Optional<ComponentResponseData> component = Optional.empty();

        private Optional<FeatureUsageDetailResponseData> featureUsage = Optional.empty();

        private Optional<StripeEmbedInfo> stripeEmbed = Optional.empty();

        private Optional<CompanySubscriptionResponseData> subscription = Optional.empty();

        private Optional<InvoiceResponseData> upcomingInvoice = Optional.empty();

        @JsonAnySetter
        private Map<String, Object> additionalProperties = new HashMap<>();

        private Builder() {}

        public Builder from(ComponentHydrateResponseData other) {
            activeAddOns(other.getActiveAddOns());
            activePlans(other.getActivePlans());
            activeUsageBasedEntitlements(other.getActiveUsageBasedEntitlements());
            capabilities(other.getCapabilities());
            company(other.getCompany());
            component(other.getComponent());
            featureUsage(other.getFeatureUsage());
            stripeEmbed(other.getStripeEmbed());
            subscription(other.getSubscription());
            upcomingInvoice(other.getUpcomingInvoice());
            return this;
        }

        @JsonSetter(value = "active_add_ons", nulls = Nulls.SKIP)
        public Builder activeAddOns(List<CompanyPlanDetailResponseData> activeAddOns) {
            this.activeAddOns.clear();
            this.activeAddOns.addAll(activeAddOns);
            return this;
        }

        public Builder addActiveAddOns(CompanyPlanDetailResponseData activeAddOns) {
            this.activeAddOns.add(activeAddOns);
            return this;
        }

        public Builder addAllActiveAddOns(List<CompanyPlanDetailResponseData> activeAddOns) {
            this.activeAddOns.addAll(activeAddOns);
            return this;
        }

        @JsonSetter(value = "active_plans", nulls = Nulls.SKIP)
        public Builder activePlans(List<CompanyPlanDetailResponseData> activePlans) {
            this.activePlans.clear();
            this.activePlans.addAll(activePlans);
            return this;
        }

        public Builder addActivePlans(CompanyPlanDetailResponseData activePlans) {
            this.activePlans.add(activePlans);
            return this;
        }

        public Builder addAllActivePlans(List<CompanyPlanDetailResponseData> activePlans) {
            this.activePlans.addAll(activePlans);
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

        @JsonSetter(value = "capabilities", nulls = Nulls.SKIP)
        public Builder capabilities(Optional<ComponentCapabilities> capabilities) {
            this.capabilities = capabilities;
            return this;
        }

        public Builder capabilities(ComponentCapabilities capabilities) {
            this.capabilities = Optional.of(capabilities);
            return this;
        }

        @JsonSetter(value = "company", nulls = Nulls.SKIP)
        public Builder company(Optional<CompanyDetailResponseData> company) {
            this.company = company;
            return this;
        }

        public Builder company(CompanyDetailResponseData company) {
            this.company = Optional.of(company);
            return this;
        }

        @JsonSetter(value = "component", nulls = Nulls.SKIP)
        public Builder component(Optional<ComponentResponseData> component) {
            this.component = component;
            return this;
        }

        public Builder component(ComponentResponseData component) {
            this.component = Optional.of(component);
            return this;
        }

        @JsonSetter(value = "feature_usage", nulls = Nulls.SKIP)
        public Builder featureUsage(Optional<FeatureUsageDetailResponseData> featureUsage) {
            this.featureUsage = featureUsage;
            return this;
        }

        public Builder featureUsage(FeatureUsageDetailResponseData featureUsage) {
            this.featureUsage = Optional.of(featureUsage);
            return this;
        }

        @JsonSetter(value = "stripe_embed", nulls = Nulls.SKIP)
        public Builder stripeEmbed(Optional<StripeEmbedInfo> stripeEmbed) {
            this.stripeEmbed = stripeEmbed;
            return this;
        }

        public Builder stripeEmbed(StripeEmbedInfo stripeEmbed) {
            this.stripeEmbed = Optional.of(stripeEmbed);
            return this;
        }

        @JsonSetter(value = "subscription", nulls = Nulls.SKIP)
        public Builder subscription(Optional<CompanySubscriptionResponseData> subscription) {
            this.subscription = subscription;
            return this;
        }

        public Builder subscription(CompanySubscriptionResponseData subscription) {
            this.subscription = Optional.of(subscription);
            return this;
        }

        @JsonSetter(value = "upcoming_invoice", nulls = Nulls.SKIP)
        public Builder upcomingInvoice(Optional<InvoiceResponseData> upcomingInvoice) {
            this.upcomingInvoice = upcomingInvoice;
            return this;
        }

        public Builder upcomingInvoice(InvoiceResponseData upcomingInvoice) {
            this.upcomingInvoice = Optional.of(upcomingInvoice);
            return this;
        }

        public ComponentHydrateResponseData build() {
            return new ComponentHydrateResponseData(
                    activeAddOns,
                    activePlans,
                    activeUsageBasedEntitlements,
                    capabilities,
                    company,
                    component,
                    featureUsage,
                    stripeEmbed,
                    subscription,
                    upcomingInvoice,
                    additionalProperties);
        }
    }
}
