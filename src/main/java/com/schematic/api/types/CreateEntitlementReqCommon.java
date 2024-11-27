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

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonDeserialize(builder = CreateEntitlementReqCommon.Builder.class)
public final class CreateEntitlementReqCommon {
    private final String featureId;

    private final Optional<String> meteredMonthlyPriceId;

    private final Optional<String> meteredYearlyPriceId;

    private final Optional<CreateEntitlementReqCommonMetricPeriod> metricPeriod;

    private final Optional<CreateEntitlementReqCommonMetricPeriodMonthReset> metricPeriodMonthReset;

    private final Optional<Boolean> valueBool;

    private final Optional<Integer> valueNumeric;

    private final Optional<String> valueTraitId;

    private final CreateEntitlementReqCommonValueType valueType;

    private final Map<String, Object> additionalProperties;

    private CreateEntitlementReqCommon(
            String featureId,
            Optional<String> meteredMonthlyPriceId,
            Optional<String> meteredYearlyPriceId,
            Optional<CreateEntitlementReqCommonMetricPeriod> metricPeriod,
            Optional<CreateEntitlementReqCommonMetricPeriodMonthReset> metricPeriodMonthReset,
            Optional<Boolean> valueBool,
            Optional<Integer> valueNumeric,
            Optional<String> valueTraitId,
            CreateEntitlementReqCommonValueType valueType,
            Map<String, Object> additionalProperties) {
        this.featureId = featureId;
        this.meteredMonthlyPriceId = meteredMonthlyPriceId;
        this.meteredYearlyPriceId = meteredYearlyPriceId;
        this.metricPeriod = metricPeriod;
        this.metricPeriodMonthReset = metricPeriodMonthReset;
        this.valueBool = valueBool;
        this.valueNumeric = valueNumeric;
        this.valueTraitId = valueTraitId;
        this.valueType = valueType;
        this.additionalProperties = additionalProperties;
    }

    @JsonProperty("feature_id")
    public String getFeatureId() {
        return featureId;
    }

    @JsonProperty("metered_monthly_price_id")
    public Optional<String> getMeteredMonthlyPriceId() {
        return meteredMonthlyPriceId;
    }

    @JsonProperty("metered_yearly_price_id")
    public Optional<String> getMeteredYearlyPriceId() {
        return meteredYearlyPriceId;
    }

    @JsonProperty("metric_period")
    public Optional<CreateEntitlementReqCommonMetricPeriod> getMetricPeriod() {
        return metricPeriod;
    }

    @JsonProperty("metric_period_month_reset")
    public Optional<CreateEntitlementReqCommonMetricPeriodMonthReset> getMetricPeriodMonthReset() {
        return metricPeriodMonthReset;
    }

    @JsonProperty("value_bool")
    public Optional<Boolean> getValueBool() {
        return valueBool;
    }

    @JsonProperty("value_numeric")
    public Optional<Integer> getValueNumeric() {
        return valueNumeric;
    }

    @JsonProperty("value_trait_id")
    public Optional<String> getValueTraitId() {
        return valueTraitId;
    }

    @JsonProperty("value_type")
    public CreateEntitlementReqCommonValueType getValueType() {
        return valueType;
    }

    @java.lang.Override
    public boolean equals(Object other) {
        if (this == other) return true;
        return other instanceof CreateEntitlementReqCommon && equalTo((CreateEntitlementReqCommon) other);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    private boolean equalTo(CreateEntitlementReqCommon other) {
        return featureId.equals(other.featureId)
                && meteredMonthlyPriceId.equals(other.meteredMonthlyPriceId)
                && meteredYearlyPriceId.equals(other.meteredYearlyPriceId)
                && metricPeriod.equals(other.metricPeriod)
                && metricPeriodMonthReset.equals(other.metricPeriodMonthReset)
                && valueBool.equals(other.valueBool)
                && valueNumeric.equals(other.valueNumeric)
                && valueTraitId.equals(other.valueTraitId)
                && valueType.equals(other.valueType);
    }

    @java.lang.Override
    public int hashCode() {
        return Objects.hash(
                this.featureId,
                this.meteredMonthlyPriceId,
                this.meteredYearlyPriceId,
                this.metricPeriod,
                this.metricPeriodMonthReset,
                this.valueBool,
                this.valueNumeric,
                this.valueTraitId,
                this.valueType);
    }

    @java.lang.Override
    public String toString() {
        return ObjectMappers.stringify(this);
    }

    public static FeatureIdStage builder() {
        return new Builder();
    }

    public interface FeatureIdStage {
        ValueTypeStage featureId(String featureId);

        Builder from(CreateEntitlementReqCommon other);
    }

    public interface ValueTypeStage {
        _FinalStage valueType(CreateEntitlementReqCommonValueType valueType);
    }

    public interface _FinalStage {
        CreateEntitlementReqCommon build();

        _FinalStage meteredMonthlyPriceId(Optional<String> meteredMonthlyPriceId);

        _FinalStage meteredMonthlyPriceId(String meteredMonthlyPriceId);

        _FinalStage meteredYearlyPriceId(Optional<String> meteredYearlyPriceId);

        _FinalStage meteredYearlyPriceId(String meteredYearlyPriceId);

        _FinalStage metricPeriod(Optional<CreateEntitlementReqCommonMetricPeriod> metricPeriod);

        _FinalStage metricPeriod(CreateEntitlementReqCommonMetricPeriod metricPeriod);

        _FinalStage metricPeriodMonthReset(
                Optional<CreateEntitlementReqCommonMetricPeriodMonthReset> metricPeriodMonthReset);

        _FinalStage metricPeriodMonthReset(CreateEntitlementReqCommonMetricPeriodMonthReset metricPeriodMonthReset);

        _FinalStage valueBool(Optional<Boolean> valueBool);

        _FinalStage valueBool(Boolean valueBool);

        _FinalStage valueNumeric(Optional<Integer> valueNumeric);

        _FinalStage valueNumeric(Integer valueNumeric);

        _FinalStage valueTraitId(Optional<String> valueTraitId);

        _FinalStage valueTraitId(String valueTraitId);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static final class Builder implements FeatureIdStage, ValueTypeStage, _FinalStage {
        private String featureId;

        private CreateEntitlementReqCommonValueType valueType;

        private Optional<String> valueTraitId = Optional.empty();

        private Optional<Integer> valueNumeric = Optional.empty();

        private Optional<Boolean> valueBool = Optional.empty();

        private Optional<CreateEntitlementReqCommonMetricPeriodMonthReset> metricPeriodMonthReset = Optional.empty();

        private Optional<CreateEntitlementReqCommonMetricPeriod> metricPeriod = Optional.empty();

        private Optional<String> meteredYearlyPriceId = Optional.empty();

        private Optional<String> meteredMonthlyPriceId = Optional.empty();

        @JsonAnySetter
        private Map<String, Object> additionalProperties = new HashMap<>();

        private Builder() {}

        @java.lang.Override
        public Builder from(CreateEntitlementReqCommon other) {
            featureId(other.getFeatureId());
            meteredMonthlyPriceId(other.getMeteredMonthlyPriceId());
            meteredYearlyPriceId(other.getMeteredYearlyPriceId());
            metricPeriod(other.getMetricPeriod());
            metricPeriodMonthReset(other.getMetricPeriodMonthReset());
            valueBool(other.getValueBool());
            valueNumeric(other.getValueNumeric());
            valueTraitId(other.getValueTraitId());
            valueType(other.getValueType());
            return this;
        }

        @java.lang.Override
        @JsonSetter("feature_id")
        public ValueTypeStage featureId(String featureId) {
            this.featureId = featureId;
            return this;
        }

        @java.lang.Override
        @JsonSetter("value_type")
        public _FinalStage valueType(CreateEntitlementReqCommonValueType valueType) {
            this.valueType = valueType;
            return this;
        }

        @java.lang.Override
        public _FinalStage valueTraitId(String valueTraitId) {
            this.valueTraitId = Optional.of(valueTraitId);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "value_trait_id", nulls = Nulls.SKIP)
        public _FinalStage valueTraitId(Optional<String> valueTraitId) {
            this.valueTraitId = valueTraitId;
            return this;
        }

        @java.lang.Override
        public _FinalStage valueNumeric(Integer valueNumeric) {
            this.valueNumeric = Optional.of(valueNumeric);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "value_numeric", nulls = Nulls.SKIP)
        public _FinalStage valueNumeric(Optional<Integer> valueNumeric) {
            this.valueNumeric = valueNumeric;
            return this;
        }

        @java.lang.Override
        public _FinalStage valueBool(Boolean valueBool) {
            this.valueBool = Optional.of(valueBool);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "value_bool", nulls = Nulls.SKIP)
        public _FinalStage valueBool(Optional<Boolean> valueBool) {
            this.valueBool = valueBool;
            return this;
        }

        @java.lang.Override
        public _FinalStage metricPeriodMonthReset(
                CreateEntitlementReqCommonMetricPeriodMonthReset metricPeriodMonthReset) {
            this.metricPeriodMonthReset = Optional.of(metricPeriodMonthReset);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "metric_period_month_reset", nulls = Nulls.SKIP)
        public _FinalStage metricPeriodMonthReset(
                Optional<CreateEntitlementReqCommonMetricPeriodMonthReset> metricPeriodMonthReset) {
            this.metricPeriodMonthReset = metricPeriodMonthReset;
            return this;
        }

        @java.lang.Override
        public _FinalStage metricPeriod(CreateEntitlementReqCommonMetricPeriod metricPeriod) {
            this.metricPeriod = Optional.of(metricPeriod);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "metric_period", nulls = Nulls.SKIP)
        public _FinalStage metricPeriod(Optional<CreateEntitlementReqCommonMetricPeriod> metricPeriod) {
            this.metricPeriod = metricPeriod;
            return this;
        }

        @java.lang.Override
        public _FinalStage meteredYearlyPriceId(String meteredYearlyPriceId) {
            this.meteredYearlyPriceId = Optional.of(meteredYearlyPriceId);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "metered_yearly_price_id", nulls = Nulls.SKIP)
        public _FinalStage meteredYearlyPriceId(Optional<String> meteredYearlyPriceId) {
            this.meteredYearlyPriceId = meteredYearlyPriceId;
            return this;
        }

        @java.lang.Override
        public _FinalStage meteredMonthlyPriceId(String meteredMonthlyPriceId) {
            this.meteredMonthlyPriceId = Optional.of(meteredMonthlyPriceId);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "metered_monthly_price_id", nulls = Nulls.SKIP)
        public _FinalStage meteredMonthlyPriceId(Optional<String> meteredMonthlyPriceId) {
            this.meteredMonthlyPriceId = meteredMonthlyPriceId;
            return this;
        }

        @java.lang.Override
        public CreateEntitlementReqCommon build() {
            return new CreateEntitlementReqCommon(
                    featureId,
                    meteredMonthlyPriceId,
                    meteredYearlyPriceId,
                    metricPeriod,
                    metricPeriodMonthReset,
                    valueBool,
                    valueNumeric,
                    valueTraitId,
                    valueType,
                    additionalProperties);
        }
    }
}
