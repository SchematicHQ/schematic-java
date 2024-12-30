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
@JsonDeserialize(builder = ChangeSubscriptionRequestBody.Builder.class)
public final class ChangeSubscriptionRequestBody {
    private final List<UpdateAddOnRequestBody> addOnIds;

    private final String newPlanId;

    private final String newPriceId;

    private final List<UpdatePayInAdvanceRequestBody> payInAdvance;

    private final Optional<String> paymentMethodId;

    private final Optional<String> promoCode;

    private final Map<String, Object> additionalProperties;

    private ChangeSubscriptionRequestBody(
            List<UpdateAddOnRequestBody> addOnIds,
            String newPlanId,
            String newPriceId,
            List<UpdatePayInAdvanceRequestBody> payInAdvance,
            Optional<String> paymentMethodId,
            Optional<String> promoCode,
            Map<String, Object> additionalProperties) {
        this.addOnIds = addOnIds;
        this.newPlanId = newPlanId;
        this.newPriceId = newPriceId;
        this.payInAdvance = payInAdvance;
        this.paymentMethodId = paymentMethodId;
        this.promoCode = promoCode;
        this.additionalProperties = additionalProperties;
    }

    @JsonProperty("add_on_ids")
    public List<UpdateAddOnRequestBody> getAddOnIds() {
        return addOnIds;
    }

    @JsonProperty("new_plan_id")
    public String getNewPlanId() {
        return newPlanId;
    }

    @JsonProperty("new_price_id")
    public String getNewPriceId() {
        return newPriceId;
    }

    @JsonProperty("pay_in_advance")
    public List<UpdatePayInAdvanceRequestBody> getPayInAdvance() {
        return payInAdvance;
    }

    @JsonProperty("payment_method_id")
    public Optional<String> getPaymentMethodId() {
        return paymentMethodId;
    }

    @JsonProperty("promo_code")
    public Optional<String> getPromoCode() {
        return promoCode;
    }

    @java.lang.Override
    public boolean equals(Object other) {
        if (this == other) return true;
        return other instanceof ChangeSubscriptionRequestBody && equalTo((ChangeSubscriptionRequestBody) other);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    private boolean equalTo(ChangeSubscriptionRequestBody other) {
        return addOnIds.equals(other.addOnIds)
                && newPlanId.equals(other.newPlanId)
                && newPriceId.equals(other.newPriceId)
                && payInAdvance.equals(other.payInAdvance)
                && paymentMethodId.equals(other.paymentMethodId)
                && promoCode.equals(other.promoCode);
    }

    @java.lang.Override
    public int hashCode() {
        return Objects.hash(
                this.addOnIds,
                this.newPlanId,
                this.newPriceId,
                this.payInAdvance,
                this.paymentMethodId,
                this.promoCode);
    }

    @java.lang.Override
    public String toString() {
        return ObjectMappers.stringify(this);
    }

    public static NewPlanIdStage builder() {
        return new Builder();
    }

    public interface NewPlanIdStage {
        NewPriceIdStage newPlanId(String newPlanId);

        Builder from(ChangeSubscriptionRequestBody other);
    }

    public interface NewPriceIdStage {
        _FinalStage newPriceId(String newPriceId);
    }

    public interface _FinalStage {
        ChangeSubscriptionRequestBody build();

        _FinalStage addOnIds(List<UpdateAddOnRequestBody> addOnIds);

        _FinalStage addAddOnIds(UpdateAddOnRequestBody addOnIds);

        _FinalStage addAllAddOnIds(List<UpdateAddOnRequestBody> addOnIds);

        _FinalStage payInAdvance(List<UpdatePayInAdvanceRequestBody> payInAdvance);

        _FinalStage addPayInAdvance(UpdatePayInAdvanceRequestBody payInAdvance);

        _FinalStage addAllPayInAdvance(List<UpdatePayInAdvanceRequestBody> payInAdvance);

        _FinalStage paymentMethodId(Optional<String> paymentMethodId);

        _FinalStage paymentMethodId(String paymentMethodId);

        _FinalStage promoCode(Optional<String> promoCode);

        _FinalStage promoCode(String promoCode);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static final class Builder implements NewPlanIdStage, NewPriceIdStage, _FinalStage {
        private String newPlanId;

        private String newPriceId;

        private Optional<String> promoCode = Optional.empty();

        private Optional<String> paymentMethodId = Optional.empty();

        private List<UpdatePayInAdvanceRequestBody> payInAdvance = new ArrayList<>();

        private List<UpdateAddOnRequestBody> addOnIds = new ArrayList<>();

        @JsonAnySetter
        private Map<String, Object> additionalProperties = new HashMap<>();

        private Builder() {}

        @java.lang.Override
        public Builder from(ChangeSubscriptionRequestBody other) {
            addOnIds(other.getAddOnIds());
            newPlanId(other.getNewPlanId());
            newPriceId(other.getNewPriceId());
            payInAdvance(other.getPayInAdvance());
            paymentMethodId(other.getPaymentMethodId());
            promoCode(other.getPromoCode());
            return this;
        }

        @java.lang.Override
        @JsonSetter("new_plan_id")
        public NewPriceIdStage newPlanId(String newPlanId) {
            this.newPlanId = newPlanId;
            return this;
        }

        @java.lang.Override
        @JsonSetter("new_price_id")
        public _FinalStage newPriceId(String newPriceId) {
            this.newPriceId = newPriceId;
            return this;
        }

        @java.lang.Override
        public _FinalStage promoCode(String promoCode) {
            this.promoCode = Optional.ofNullable(promoCode);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "promo_code", nulls = Nulls.SKIP)
        public _FinalStage promoCode(Optional<String> promoCode) {
            this.promoCode = promoCode;
            return this;
        }

        @java.lang.Override
        public _FinalStage paymentMethodId(String paymentMethodId) {
            this.paymentMethodId = Optional.ofNullable(paymentMethodId);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "payment_method_id", nulls = Nulls.SKIP)
        public _FinalStage paymentMethodId(Optional<String> paymentMethodId) {
            this.paymentMethodId = paymentMethodId;
            return this;
        }

        @java.lang.Override
        public _FinalStage addAllPayInAdvance(List<UpdatePayInAdvanceRequestBody> payInAdvance) {
            this.payInAdvance.addAll(payInAdvance);
            return this;
        }

        @java.lang.Override
        public _FinalStage addPayInAdvance(UpdatePayInAdvanceRequestBody payInAdvance) {
            this.payInAdvance.add(payInAdvance);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "pay_in_advance", nulls = Nulls.SKIP)
        public _FinalStage payInAdvance(List<UpdatePayInAdvanceRequestBody> payInAdvance) {
            this.payInAdvance.clear();
            this.payInAdvance.addAll(payInAdvance);
            return this;
        }

        @java.lang.Override
        public _FinalStage addAllAddOnIds(List<UpdateAddOnRequestBody> addOnIds) {
            this.addOnIds.addAll(addOnIds);
            return this;
        }

        @java.lang.Override
        public _FinalStage addAddOnIds(UpdateAddOnRequestBody addOnIds) {
            this.addOnIds.add(addOnIds);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "add_on_ids", nulls = Nulls.SKIP)
        public _FinalStage addOnIds(List<UpdateAddOnRequestBody> addOnIds) {
            this.addOnIds.clear();
            this.addOnIds.addAll(addOnIds);
            return this;
        }

        @java.lang.Override
        public ChangeSubscriptionRequestBody build() {
            return new ChangeSubscriptionRequestBody(
                    addOnIds, newPlanId, newPriceId, payInAdvance, paymentMethodId, promoCode, additionalProperties);
        }
    }
}
