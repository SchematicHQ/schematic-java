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
import org.jetbrains.annotations.NotNull;

@JsonInclude(JsonInclude.Include.NON_ABSENT)
@JsonDeserialize(builder = PlanGroupDetailResponseData.Builder.class)
public final class PlanGroupDetailResponseData {
    private final List<PlanGroupPlanDetailResponseData> addOns;

    private final Optional<PlanGroupPlanDetailResponseData> defaultPlan;

    private final Optional<String> defaultPlanId;

    private final String id;

    private final List<PlanGroupPlanDetailResponseData> plans;

    private final Optional<Integer> trialDays;

    private final Optional<Boolean> trialPaymentMethodRequired;

    private final Map<String, Object> additionalProperties;

    private PlanGroupDetailResponseData(
            List<PlanGroupPlanDetailResponseData> addOns,
            Optional<PlanGroupPlanDetailResponseData> defaultPlan,
            Optional<String> defaultPlanId,
            String id,
            List<PlanGroupPlanDetailResponseData> plans,
            Optional<Integer> trialDays,
            Optional<Boolean> trialPaymentMethodRequired,
            Map<String, Object> additionalProperties) {
        this.addOns = addOns;
        this.defaultPlan = defaultPlan;
        this.defaultPlanId = defaultPlanId;
        this.id = id;
        this.plans = plans;
        this.trialDays = trialDays;
        this.trialPaymentMethodRequired = trialPaymentMethodRequired;
        this.additionalProperties = additionalProperties;
    }

    @JsonProperty("add_ons")
    public List<PlanGroupPlanDetailResponseData> getAddOns() {
        return addOns;
    }

    @JsonProperty("default_plan")
    public Optional<PlanGroupPlanDetailResponseData> getDefaultPlan() {
        return defaultPlan;
    }

    @JsonProperty("default_plan_id")
    public Optional<String> getDefaultPlanId() {
        return defaultPlanId;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("plans")
    public List<PlanGroupPlanDetailResponseData> getPlans() {
        return plans;
    }

    @JsonProperty("trial_days")
    public Optional<Integer> getTrialDays() {
        return trialDays;
    }

    @JsonProperty("trial_payment_method_required")
    public Optional<Boolean> getTrialPaymentMethodRequired() {
        return trialPaymentMethodRequired;
    }

    @java.lang.Override
    public boolean equals(Object other) {
        if (this == other) return true;
        return other instanceof PlanGroupDetailResponseData && equalTo((PlanGroupDetailResponseData) other);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    private boolean equalTo(PlanGroupDetailResponseData other) {
        return addOns.equals(other.addOns)
                && defaultPlan.equals(other.defaultPlan)
                && defaultPlanId.equals(other.defaultPlanId)
                && id.equals(other.id)
                && plans.equals(other.plans)
                && trialDays.equals(other.trialDays)
                && trialPaymentMethodRequired.equals(other.trialPaymentMethodRequired);
    }

    @java.lang.Override
    public int hashCode() {
        return Objects.hash(
                this.addOns,
                this.defaultPlan,
                this.defaultPlanId,
                this.id,
                this.plans,
                this.trialDays,
                this.trialPaymentMethodRequired);
    }

    @java.lang.Override
    public String toString() {
        return ObjectMappers.stringify(this);
    }

    public static IdStage builder() {
        return new Builder();
    }

    public interface IdStage {
        _FinalStage id(@NotNull String id);

        Builder from(PlanGroupDetailResponseData other);
    }

    public interface _FinalStage {
        PlanGroupDetailResponseData build();

        _FinalStage addOns(List<PlanGroupPlanDetailResponseData> addOns);

        _FinalStage addAddOns(PlanGroupPlanDetailResponseData addOns);

        _FinalStage addAllAddOns(List<PlanGroupPlanDetailResponseData> addOns);

        _FinalStage defaultPlan(Optional<PlanGroupPlanDetailResponseData> defaultPlan);

        _FinalStage defaultPlan(PlanGroupPlanDetailResponseData defaultPlan);

        _FinalStage defaultPlanId(Optional<String> defaultPlanId);

        _FinalStage defaultPlanId(String defaultPlanId);

        _FinalStage plans(List<PlanGroupPlanDetailResponseData> plans);

        _FinalStage addPlans(PlanGroupPlanDetailResponseData plans);

        _FinalStage addAllPlans(List<PlanGroupPlanDetailResponseData> plans);

        _FinalStage trialDays(Optional<Integer> trialDays);

        _FinalStage trialDays(Integer trialDays);

        _FinalStage trialPaymentMethodRequired(Optional<Boolean> trialPaymentMethodRequired);

        _FinalStage trialPaymentMethodRequired(Boolean trialPaymentMethodRequired);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static final class Builder implements IdStage, _FinalStage {
        private String id;

        private Optional<Boolean> trialPaymentMethodRequired = Optional.empty();

        private Optional<Integer> trialDays = Optional.empty();

        private List<PlanGroupPlanDetailResponseData> plans = new ArrayList<>();

        private Optional<String> defaultPlanId = Optional.empty();

        private Optional<PlanGroupPlanDetailResponseData> defaultPlan = Optional.empty();

        private List<PlanGroupPlanDetailResponseData> addOns = new ArrayList<>();

        @JsonAnySetter
        private Map<String, Object> additionalProperties = new HashMap<>();

        private Builder() {}

        @java.lang.Override
        public Builder from(PlanGroupDetailResponseData other) {
            addOns(other.getAddOns());
            defaultPlan(other.getDefaultPlan());
            defaultPlanId(other.getDefaultPlanId());
            id(other.getId());
            plans(other.getPlans());
            trialDays(other.getTrialDays());
            trialPaymentMethodRequired(other.getTrialPaymentMethodRequired());
            return this;
        }

        @java.lang.Override
        @JsonSetter("id")
        public _FinalStage id(@NotNull String id) {
            this.id = Objects.requireNonNull(id, "id must not be null");
            return this;
        }

        @java.lang.Override
        public _FinalStage trialPaymentMethodRequired(Boolean trialPaymentMethodRequired) {
            this.trialPaymentMethodRequired = Optional.ofNullable(trialPaymentMethodRequired);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "trial_payment_method_required", nulls = Nulls.SKIP)
        public _FinalStage trialPaymentMethodRequired(Optional<Boolean> trialPaymentMethodRequired) {
            this.trialPaymentMethodRequired = trialPaymentMethodRequired;
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
        public _FinalStage addAllPlans(List<PlanGroupPlanDetailResponseData> plans) {
            this.plans.addAll(plans);
            return this;
        }

        @java.lang.Override
        public _FinalStage addPlans(PlanGroupPlanDetailResponseData plans) {
            this.plans.add(plans);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "plans", nulls = Nulls.SKIP)
        public _FinalStage plans(List<PlanGroupPlanDetailResponseData> plans) {
            this.plans.clear();
            this.plans.addAll(plans);
            return this;
        }

        @java.lang.Override
        public _FinalStage defaultPlanId(String defaultPlanId) {
            this.defaultPlanId = Optional.ofNullable(defaultPlanId);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "default_plan_id", nulls = Nulls.SKIP)
        public _FinalStage defaultPlanId(Optional<String> defaultPlanId) {
            this.defaultPlanId = defaultPlanId;
            return this;
        }

        @java.lang.Override
        public _FinalStage defaultPlan(PlanGroupPlanDetailResponseData defaultPlan) {
            this.defaultPlan = Optional.ofNullable(defaultPlan);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "default_plan", nulls = Nulls.SKIP)
        public _FinalStage defaultPlan(Optional<PlanGroupPlanDetailResponseData> defaultPlan) {
            this.defaultPlan = defaultPlan;
            return this;
        }

        @java.lang.Override
        public _FinalStage addAllAddOns(List<PlanGroupPlanDetailResponseData> addOns) {
            this.addOns.addAll(addOns);
            return this;
        }

        @java.lang.Override
        public _FinalStage addAddOns(PlanGroupPlanDetailResponseData addOns) {
            this.addOns.add(addOns);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "add_ons", nulls = Nulls.SKIP)
        public _FinalStage addOns(List<PlanGroupPlanDetailResponseData> addOns) {
            this.addOns.clear();
            this.addOns.addAll(addOns);
            return this;
        }

        @java.lang.Override
        public PlanGroupDetailResponseData build() {
            return new PlanGroupDetailResponseData(
                    addOns,
                    defaultPlan,
                    defaultPlanId,
                    id,
                    plans,
                    trialDays,
                    trialPaymentMethodRequired,
                    additionalProperties);
        }
    }
}
