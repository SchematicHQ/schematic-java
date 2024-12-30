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

@JsonInclude(JsonInclude.Include.NON_ABSENT)
@JsonDeserialize(builder = CompanyPlanDetailResponseData.Builder.class)
public final class CompanyPlanDetailResponseData {
    private final Optional<String> audienceType;

    private final Optional<BillingProductDetailResponseData> billingProduct;

    private final boolean companyCanTrial;

    private final int companyCount;

    private final OffsetDateTime createdAt;

    private final boolean current;

    private final String description;

    private final List<PlanEntitlementResponseData> entitlements;

    private final List<FeatureDetailResponseData> features;

    private final String icon;

    private final String id;

    private final boolean isDefault;

    private final boolean isFree;

    private final boolean isTrialable;

    private final Optional<BillingPriceResponseData> monthlyPrice;

    private final String name;

    private final String planType;

    private final Optional<Integer> trialDays;

    private final OffsetDateTime updatedAt;

    private final boolean valid;

    private final Optional<BillingPriceResponseData> yearlyPrice;

    private final Map<String, Object> additionalProperties;

    private CompanyPlanDetailResponseData(
            Optional<String> audienceType,
            Optional<BillingProductDetailResponseData> billingProduct,
            boolean companyCanTrial,
            int companyCount,
            OffsetDateTime createdAt,
            boolean current,
            String description,
            List<PlanEntitlementResponseData> entitlements,
            List<FeatureDetailResponseData> features,
            String icon,
            String id,
            boolean isDefault,
            boolean isFree,
            boolean isTrialable,
            Optional<BillingPriceResponseData> monthlyPrice,
            String name,
            String planType,
            Optional<Integer> trialDays,
            OffsetDateTime updatedAt,
            boolean valid,
            Optional<BillingPriceResponseData> yearlyPrice,
            Map<String, Object> additionalProperties) {
        this.audienceType = audienceType;
        this.billingProduct = billingProduct;
        this.companyCanTrial = companyCanTrial;
        this.companyCount = companyCount;
        this.createdAt = createdAt;
        this.current = current;
        this.description = description;
        this.entitlements = entitlements;
        this.features = features;
        this.icon = icon;
        this.id = id;
        this.isDefault = isDefault;
        this.isFree = isFree;
        this.isTrialable = isTrialable;
        this.monthlyPrice = monthlyPrice;
        this.name = name;
        this.planType = planType;
        this.trialDays = trialDays;
        this.updatedAt = updatedAt;
        this.valid = valid;
        this.yearlyPrice = yearlyPrice;
        this.additionalProperties = additionalProperties;
    }

    @JsonProperty("audience_type")
    public Optional<String> getAudienceType() {
        return audienceType;
    }

    @JsonProperty("billing_product")
    public Optional<BillingProductDetailResponseData> getBillingProduct() {
        return billingProduct;
    }

    @JsonProperty("company_can_trial")
    public boolean getCompanyCanTrial() {
        return companyCanTrial;
    }

    @JsonProperty("company_count")
    public int getCompanyCount() {
        return companyCount;
    }

    @JsonProperty("created_at")
    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    @JsonProperty("current")
    public boolean getCurrent() {
        return current;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("entitlements")
    public List<PlanEntitlementResponseData> getEntitlements() {
        return entitlements;
    }

    @JsonProperty("features")
    public List<FeatureDetailResponseData> getFeatures() {
        return features;
    }

    @JsonProperty("icon")
    public String getIcon() {
        return icon;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("is_default")
    public boolean getIsDefault() {
        return isDefault;
    }

    @JsonProperty("is_free")
    public boolean getIsFree() {
        return isFree;
    }

    @JsonProperty("is_trialable")
    public boolean getIsTrialable() {
        return isTrialable;
    }

    @JsonProperty("monthly_price")
    public Optional<BillingPriceResponseData> getMonthlyPrice() {
        return monthlyPrice;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("plan_type")
    public String getPlanType() {
        return planType;
    }

    @JsonProperty("trial_days")
    public Optional<Integer> getTrialDays() {
        return trialDays;
    }

    @JsonProperty("updated_at")
    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    @JsonProperty("valid")
    public boolean getValid() {
        return valid;
    }

    @JsonProperty("yearly_price")
    public Optional<BillingPriceResponseData> getYearlyPrice() {
        return yearlyPrice;
    }

    @java.lang.Override
    public boolean equals(Object other) {
        if (this == other) return true;
        return other instanceof CompanyPlanDetailResponseData && equalTo((CompanyPlanDetailResponseData) other);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    private boolean equalTo(CompanyPlanDetailResponseData other) {
        return audienceType.equals(other.audienceType)
                && billingProduct.equals(other.billingProduct)
                && companyCanTrial == other.companyCanTrial
                && companyCount == other.companyCount
                && createdAt.equals(other.createdAt)
                && current == other.current
                && description.equals(other.description)
                && entitlements.equals(other.entitlements)
                && features.equals(other.features)
                && icon.equals(other.icon)
                && id.equals(other.id)
                && isDefault == other.isDefault
                && isFree == other.isFree
                && isTrialable == other.isTrialable
                && monthlyPrice.equals(other.monthlyPrice)
                && name.equals(other.name)
                && planType.equals(other.planType)
                && trialDays.equals(other.trialDays)
                && updatedAt.equals(other.updatedAt)
                && valid == other.valid
                && yearlyPrice.equals(other.yearlyPrice);
    }

    @java.lang.Override
    public int hashCode() {
        return Objects.hash(
                this.audienceType,
                this.billingProduct,
                this.companyCanTrial,
                this.companyCount,
                this.createdAt,
                this.current,
                this.description,
                this.entitlements,
                this.features,
                this.icon,
                this.id,
                this.isDefault,
                this.isFree,
                this.isTrialable,
                this.monthlyPrice,
                this.name,
                this.planType,
                this.trialDays,
                this.updatedAt,
                this.valid,
                this.yearlyPrice);
    }

    @java.lang.Override
    public String toString() {
        return ObjectMappers.stringify(this);
    }

    public static CompanyCanTrialStage builder() {
        return new Builder();
    }

    public interface CompanyCanTrialStage {
        CompanyCountStage companyCanTrial(boolean companyCanTrial);

        Builder from(CompanyPlanDetailResponseData other);
    }

    public interface CompanyCountStage {
        CreatedAtStage companyCount(int companyCount);
    }

    public interface CreatedAtStage {
        CurrentStage createdAt(OffsetDateTime createdAt);
    }

    public interface CurrentStage {
        DescriptionStage current(boolean current);
    }

    public interface DescriptionStage {
        IconStage description(String description);
    }

    public interface IconStage {
        IdStage icon(String icon);
    }

    public interface IdStage {
        IsDefaultStage id(String id);
    }

    public interface IsDefaultStage {
        IsFreeStage isDefault(boolean isDefault);
    }

    public interface IsFreeStage {
        IsTrialableStage isFree(boolean isFree);
    }

    public interface IsTrialableStage {
        NameStage isTrialable(boolean isTrialable);
    }

    public interface NameStage {
        PlanTypeStage name(String name);
    }

    public interface PlanTypeStage {
        UpdatedAtStage planType(String planType);
    }

    public interface UpdatedAtStage {
        ValidStage updatedAt(OffsetDateTime updatedAt);
    }

    public interface ValidStage {
        _FinalStage valid(boolean valid);
    }

    public interface _FinalStage {
        CompanyPlanDetailResponseData build();

        _FinalStage audienceType(Optional<String> audienceType);

        _FinalStage audienceType(String audienceType);

        _FinalStage billingProduct(Optional<BillingProductDetailResponseData> billingProduct);

        _FinalStage billingProduct(BillingProductDetailResponseData billingProduct);

        _FinalStage entitlements(List<PlanEntitlementResponseData> entitlements);

        _FinalStage addEntitlements(PlanEntitlementResponseData entitlements);

        _FinalStage addAllEntitlements(List<PlanEntitlementResponseData> entitlements);

        _FinalStage features(List<FeatureDetailResponseData> features);

        _FinalStage addFeatures(FeatureDetailResponseData features);

        _FinalStage addAllFeatures(List<FeatureDetailResponseData> features);

        _FinalStage monthlyPrice(Optional<BillingPriceResponseData> monthlyPrice);

        _FinalStage monthlyPrice(BillingPriceResponseData monthlyPrice);

        _FinalStage trialDays(Optional<Integer> trialDays);

        _FinalStage trialDays(Integer trialDays);

        _FinalStage yearlyPrice(Optional<BillingPriceResponseData> yearlyPrice);

        _FinalStage yearlyPrice(BillingPriceResponseData yearlyPrice);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static final class Builder
            implements CompanyCanTrialStage,
                    CompanyCountStage,
                    CreatedAtStage,
                    CurrentStage,
                    DescriptionStage,
                    IconStage,
                    IdStage,
                    IsDefaultStage,
                    IsFreeStage,
                    IsTrialableStage,
                    NameStage,
                    PlanTypeStage,
                    UpdatedAtStage,
                    ValidStage,
                    _FinalStage {
        private boolean companyCanTrial;

        private int companyCount;

        private OffsetDateTime createdAt;

        private boolean current;

        private String description;

        private String icon;

        private String id;

        private boolean isDefault;

        private boolean isFree;

        private boolean isTrialable;

        private String name;

        private String planType;

        private OffsetDateTime updatedAt;

        private boolean valid;

        private Optional<BillingPriceResponseData> yearlyPrice = Optional.empty();

        private Optional<Integer> trialDays = Optional.empty();

        private Optional<BillingPriceResponseData> monthlyPrice = Optional.empty();

        private List<FeatureDetailResponseData> features = new ArrayList<>();

        private List<PlanEntitlementResponseData> entitlements = new ArrayList<>();

        private Optional<BillingProductDetailResponseData> billingProduct = Optional.empty();

        private Optional<String> audienceType = Optional.empty();

        @JsonAnySetter
        private Map<String, Object> additionalProperties = new HashMap<>();

        private Builder() {}

        @java.lang.Override
        public Builder from(CompanyPlanDetailResponseData other) {
            audienceType(other.getAudienceType());
            billingProduct(other.getBillingProduct());
            companyCanTrial(other.getCompanyCanTrial());
            companyCount(other.getCompanyCount());
            createdAt(other.getCreatedAt());
            current(other.getCurrent());
            description(other.getDescription());
            entitlements(other.getEntitlements());
            features(other.getFeatures());
            icon(other.getIcon());
            id(other.getId());
            isDefault(other.getIsDefault());
            isFree(other.getIsFree());
            isTrialable(other.getIsTrialable());
            monthlyPrice(other.getMonthlyPrice());
            name(other.getName());
            planType(other.getPlanType());
            trialDays(other.getTrialDays());
            updatedAt(other.getUpdatedAt());
            valid(other.getValid());
            yearlyPrice(other.getYearlyPrice());
            return this;
        }

        @java.lang.Override
        @JsonSetter("company_can_trial")
        public CompanyCountStage companyCanTrial(boolean companyCanTrial) {
            this.companyCanTrial = companyCanTrial;
            return this;
        }

        @java.lang.Override
        @JsonSetter("company_count")
        public CreatedAtStage companyCount(int companyCount) {
            this.companyCount = companyCount;
            return this;
        }

        @java.lang.Override
        @JsonSetter("created_at")
        public CurrentStage createdAt(OffsetDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        @java.lang.Override
        @JsonSetter("current")
        public DescriptionStage current(boolean current) {
            this.current = current;
            return this;
        }

        @java.lang.Override
        @JsonSetter("description")
        public IconStage description(String description) {
            this.description = description;
            return this;
        }

        @java.lang.Override
        @JsonSetter("icon")
        public IdStage icon(String icon) {
            this.icon = icon;
            return this;
        }

        @java.lang.Override
        @JsonSetter("id")
        public IsDefaultStage id(String id) {
            this.id = id;
            return this;
        }

        @java.lang.Override
        @JsonSetter("is_default")
        public IsFreeStage isDefault(boolean isDefault) {
            this.isDefault = isDefault;
            return this;
        }

        @java.lang.Override
        @JsonSetter("is_free")
        public IsTrialableStage isFree(boolean isFree) {
            this.isFree = isFree;
            return this;
        }

        @java.lang.Override
        @JsonSetter("is_trialable")
        public NameStage isTrialable(boolean isTrialable) {
            this.isTrialable = isTrialable;
            return this;
        }

        @java.lang.Override
        @JsonSetter("name")
        public PlanTypeStage name(String name) {
            this.name = name;
            return this;
        }

        @java.lang.Override
        @JsonSetter("plan_type")
        public UpdatedAtStage planType(String planType) {
            this.planType = planType;
            return this;
        }

        @java.lang.Override
        @JsonSetter("updated_at")
        public ValidStage updatedAt(OffsetDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        @java.lang.Override
        @JsonSetter("valid")
        public _FinalStage valid(boolean valid) {
            this.valid = valid;
            return this;
        }

        @java.lang.Override
        public _FinalStage yearlyPrice(BillingPriceResponseData yearlyPrice) {
            this.yearlyPrice = Optional.ofNullable(yearlyPrice);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "yearly_price", nulls = Nulls.SKIP)
        public _FinalStage yearlyPrice(Optional<BillingPriceResponseData> yearlyPrice) {
            this.yearlyPrice = yearlyPrice;
            return this;
        }

        @java.lang.Override
        public _FinalStage trialDays(Integer trialDays) {
            this.trialDays = Optional.ofNullable(trialDays);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "trial_days", nulls = Nulls.SKIP)
        public _FinalStage trialDays(Optional<Integer> trialDays) {
            this.trialDays = trialDays;
            return this;
        }

        @java.lang.Override
        public _FinalStage monthlyPrice(BillingPriceResponseData monthlyPrice) {
            this.monthlyPrice = Optional.ofNullable(monthlyPrice);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "monthly_price", nulls = Nulls.SKIP)
        public _FinalStage monthlyPrice(Optional<BillingPriceResponseData> monthlyPrice) {
            this.monthlyPrice = monthlyPrice;
            return this;
        }

        @java.lang.Override
        public _FinalStage addAllFeatures(List<FeatureDetailResponseData> features) {
            this.features.addAll(features);
            return this;
        }

        @java.lang.Override
        public _FinalStage addFeatures(FeatureDetailResponseData features) {
            this.features.add(features);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "features", nulls = Nulls.SKIP)
        public _FinalStage features(List<FeatureDetailResponseData> features) {
            this.features.clear();
            this.features.addAll(features);
            return this;
        }

        @java.lang.Override
        public _FinalStage addAllEntitlements(List<PlanEntitlementResponseData> entitlements) {
            this.entitlements.addAll(entitlements);
            return this;
        }

        @java.lang.Override
        public _FinalStage addEntitlements(PlanEntitlementResponseData entitlements) {
            this.entitlements.add(entitlements);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "entitlements", nulls = Nulls.SKIP)
        public _FinalStage entitlements(List<PlanEntitlementResponseData> entitlements) {
            this.entitlements.clear();
            this.entitlements.addAll(entitlements);
            return this;
        }

        @java.lang.Override
        public _FinalStage billingProduct(BillingProductDetailResponseData billingProduct) {
            this.billingProduct = Optional.ofNullable(billingProduct);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "billing_product", nulls = Nulls.SKIP)
        public _FinalStage billingProduct(Optional<BillingProductDetailResponseData> billingProduct) {
            this.billingProduct = billingProduct;
            return this;
        }

        @java.lang.Override
        public _FinalStage audienceType(String audienceType) {
            this.audienceType = Optional.ofNullable(audienceType);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "audience_type", nulls = Nulls.SKIP)
        public _FinalStage audienceType(Optional<String> audienceType) {
            this.audienceType = audienceType;
            return this;
        }

        @java.lang.Override
        public CompanyPlanDetailResponseData build() {
            return new CompanyPlanDetailResponseData(
                    audienceType,
                    billingProduct,
                    companyCanTrial,
                    companyCount,
                    createdAt,
                    current,
                    description,
                    entitlements,
                    features,
                    icon,
                    id,
                    isDefault,
                    isFree,
                    isTrialable,
                    monthlyPrice,
                    name,
                    planType,
                    trialDays,
                    updatedAt,
                    valid,
                    yearlyPrice,
                    additionalProperties);
        }
    }
}
