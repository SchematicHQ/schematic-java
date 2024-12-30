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
@JsonDeserialize(builder = CreateOrUpdateConditionRequestBody.Builder.class)
public final class CreateOrUpdateConditionRequestBody {
    private final Optional<String> comparisonTraitId;

    private final CreateOrUpdateConditionRequestBodyConditionType conditionType;

    private final Optional<String> eventSubtype;

    private final Optional<String> id;

    private final Optional<CreateOrUpdateConditionRequestBodyMetricPeriod> metricPeriod;

    private final Optional<CreateOrUpdateConditionRequestBodyMetricPeriodMonthReset> metricPeriodMonthReset;

    private final Optional<Integer> metricValue;

    private final CreateOrUpdateConditionRequestBodyOperator operator;

    private final List<String> resourceIds;

    private final Optional<String> traitId;

    private final Optional<String> traitValue;

    private final Map<String, Object> additionalProperties;

    private CreateOrUpdateConditionRequestBody(
            Optional<String> comparisonTraitId,
            CreateOrUpdateConditionRequestBodyConditionType conditionType,
            Optional<String> eventSubtype,
            Optional<String> id,
            Optional<CreateOrUpdateConditionRequestBodyMetricPeriod> metricPeriod,
            Optional<CreateOrUpdateConditionRequestBodyMetricPeriodMonthReset> metricPeriodMonthReset,
            Optional<Integer> metricValue,
            CreateOrUpdateConditionRequestBodyOperator operator,
            List<String> resourceIds,
            Optional<String> traitId,
            Optional<String> traitValue,
            Map<String, Object> additionalProperties) {
        this.comparisonTraitId = comparisonTraitId;
        this.conditionType = conditionType;
        this.eventSubtype = eventSubtype;
        this.id = id;
        this.metricPeriod = metricPeriod;
        this.metricPeriodMonthReset = metricPeriodMonthReset;
        this.metricValue = metricValue;
        this.operator = operator;
        this.resourceIds = resourceIds;
        this.traitId = traitId;
        this.traitValue = traitValue;
        this.additionalProperties = additionalProperties;
    }

    /**
     * @return Optionally provide a trait ID to compare a metric or trait value against instead of a value
     */
    @JsonProperty("comparison_trait_id")
    public Optional<String> getComparisonTraitId() {
        return comparisonTraitId;
    }

    @JsonProperty("condition_type")
    public CreateOrUpdateConditionRequestBodyConditionType getConditionType() {
        return conditionType;
    }

    /**
     * @return Name of track event type used to measure this condition
     */
    @JsonProperty("event_subtype")
    public Optional<String> getEventSubtype() {
        return eventSubtype;
    }

    @JsonProperty("id")
    public Optional<String> getId() {
        return id;
    }

    /**
     * @return Period of time over which to measure the track event metric
     */
    @JsonProperty("metric_period")
    public Optional<CreateOrUpdateConditionRequestBodyMetricPeriod> getMetricPeriod() {
        return metricPeriod;
    }

    /**
     * @return When metric_period=current_month, specify whether the month restarts based on the calendar month or the billing period
     */
    @JsonProperty("metric_period_month_reset")
    public Optional<CreateOrUpdateConditionRequestBodyMetricPeriodMonthReset> getMetricPeriodMonthReset() {
        return metricPeriodMonthReset;
    }

    /**
     * @return Value to compare the track event metric against
     */
    @JsonProperty("metric_value")
    public Optional<Integer> getMetricValue() {
        return metricValue;
    }

    @JsonProperty("operator")
    public CreateOrUpdateConditionRequestBodyOperator getOperator() {
        return operator;
    }

    /**
     * @return List of resource IDs (companies, users, or plans) targeted by this condition
     */
    @JsonProperty("resource_ids")
    public List<String> getResourceIds() {
        return resourceIds;
    }

    /**
     * @return ID of trait to use to measure this condition
     */
    @JsonProperty("trait_id")
    public Optional<String> getTraitId() {
        return traitId;
    }

    /**
     * @return Value to compare the trait value against
     */
    @JsonProperty("trait_value")
    public Optional<String> getTraitValue() {
        return traitValue;
    }

    @java.lang.Override
    public boolean equals(Object other) {
        if (this == other) return true;
        return other instanceof CreateOrUpdateConditionRequestBody
                && equalTo((CreateOrUpdateConditionRequestBody) other);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    private boolean equalTo(CreateOrUpdateConditionRequestBody other) {
        return comparisonTraitId.equals(other.comparisonTraitId)
                && conditionType.equals(other.conditionType)
                && eventSubtype.equals(other.eventSubtype)
                && id.equals(other.id)
                && metricPeriod.equals(other.metricPeriod)
                && metricPeriodMonthReset.equals(other.metricPeriodMonthReset)
                && metricValue.equals(other.metricValue)
                && operator.equals(other.operator)
                && resourceIds.equals(other.resourceIds)
                && traitId.equals(other.traitId)
                && traitValue.equals(other.traitValue);
    }

    @java.lang.Override
    public int hashCode() {
        return Objects.hash(
                this.comparisonTraitId,
                this.conditionType,
                this.eventSubtype,
                this.id,
                this.metricPeriod,
                this.metricPeriodMonthReset,
                this.metricValue,
                this.operator,
                this.resourceIds,
                this.traitId,
                this.traitValue);
    }

    @java.lang.Override
    public String toString() {
        return ObjectMappers.stringify(this);
    }

    public static ConditionTypeStage builder() {
        return new Builder();
    }

    public interface ConditionTypeStage {
        OperatorStage conditionType(CreateOrUpdateConditionRequestBodyConditionType conditionType);

        Builder from(CreateOrUpdateConditionRequestBody other);
    }

    public interface OperatorStage {
        _FinalStage operator(CreateOrUpdateConditionRequestBodyOperator operator);
    }

    public interface _FinalStage {
        CreateOrUpdateConditionRequestBody build();

        _FinalStage comparisonTraitId(Optional<String> comparisonTraitId);

        _FinalStage comparisonTraitId(String comparisonTraitId);

        _FinalStage eventSubtype(Optional<String> eventSubtype);

        _FinalStage eventSubtype(String eventSubtype);

        _FinalStage id(Optional<String> id);

        _FinalStage id(String id);

        _FinalStage metricPeriod(Optional<CreateOrUpdateConditionRequestBodyMetricPeriod> metricPeriod);

        _FinalStage metricPeriod(CreateOrUpdateConditionRequestBodyMetricPeriod metricPeriod);

        _FinalStage metricPeriodMonthReset(
                Optional<CreateOrUpdateConditionRequestBodyMetricPeriodMonthReset> metricPeriodMonthReset);

        _FinalStage metricPeriodMonthReset(
                CreateOrUpdateConditionRequestBodyMetricPeriodMonthReset metricPeriodMonthReset);

        _FinalStage metricValue(Optional<Integer> metricValue);

        _FinalStage metricValue(Integer metricValue);

        _FinalStage resourceIds(List<String> resourceIds);

        _FinalStage addResourceIds(String resourceIds);

        _FinalStage addAllResourceIds(List<String> resourceIds);

        _FinalStage traitId(Optional<String> traitId);

        _FinalStage traitId(String traitId);

        _FinalStage traitValue(Optional<String> traitValue);

        _FinalStage traitValue(String traitValue);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static final class Builder implements ConditionTypeStage, OperatorStage, _FinalStage {
        private CreateOrUpdateConditionRequestBodyConditionType conditionType;

        private CreateOrUpdateConditionRequestBodyOperator operator;

        private Optional<String> traitValue = Optional.empty();

        private Optional<String> traitId = Optional.empty();

        private List<String> resourceIds = new ArrayList<>();

        private Optional<Integer> metricValue = Optional.empty();

        private Optional<CreateOrUpdateConditionRequestBodyMetricPeriodMonthReset> metricPeriodMonthReset =
                Optional.empty();

        private Optional<CreateOrUpdateConditionRequestBodyMetricPeriod> metricPeriod = Optional.empty();

        private Optional<String> id = Optional.empty();

        private Optional<String> eventSubtype = Optional.empty();

        private Optional<String> comparisonTraitId = Optional.empty();

        @JsonAnySetter
        private Map<String, Object> additionalProperties = new HashMap<>();

        private Builder() {}

        @java.lang.Override
        public Builder from(CreateOrUpdateConditionRequestBody other) {
            comparisonTraitId(other.getComparisonTraitId());
            conditionType(other.getConditionType());
            eventSubtype(other.getEventSubtype());
            id(other.getId());
            metricPeriod(other.getMetricPeriod());
            metricPeriodMonthReset(other.getMetricPeriodMonthReset());
            metricValue(other.getMetricValue());
            operator(other.getOperator());
            resourceIds(other.getResourceIds());
            traitId(other.getTraitId());
            traitValue(other.getTraitValue());
            return this;
        }

        @java.lang.Override
        @JsonSetter("condition_type")
        public OperatorStage conditionType(CreateOrUpdateConditionRequestBodyConditionType conditionType) {
            this.conditionType = conditionType;
            return this;
        }

        @java.lang.Override
        @JsonSetter("operator")
        public _FinalStage operator(CreateOrUpdateConditionRequestBodyOperator operator) {
            this.operator = operator;
            return this;
        }

        /**
         * <p>Value to compare the trait value against</p>
         * @return Reference to {@code this} so that method calls can be chained together.
         */
        @java.lang.Override
        public _FinalStage traitValue(String traitValue) {
            this.traitValue = Optional.ofNullable(traitValue);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "trait_value", nulls = Nulls.SKIP)
        public _FinalStage traitValue(Optional<String> traitValue) {
            this.traitValue = traitValue;
            return this;
        }

        /**
         * <p>ID of trait to use to measure this condition</p>
         * @return Reference to {@code this} so that method calls can be chained together.
         */
        @java.lang.Override
        public _FinalStage traitId(String traitId) {
            this.traitId = Optional.ofNullable(traitId);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "trait_id", nulls = Nulls.SKIP)
        public _FinalStage traitId(Optional<String> traitId) {
            this.traitId = traitId;
            return this;
        }

        /**
         * <p>List of resource IDs (companies, users, or plans) targeted by this condition</p>
         * @return Reference to {@code this} so that method calls can be chained together.
         */
        @java.lang.Override
        public _FinalStage addAllResourceIds(List<String> resourceIds) {
            this.resourceIds.addAll(resourceIds);
            return this;
        }

        /**
         * <p>List of resource IDs (companies, users, or plans) targeted by this condition</p>
         * @return Reference to {@code this} so that method calls can be chained together.
         */
        @java.lang.Override
        public _FinalStage addResourceIds(String resourceIds) {
            this.resourceIds.add(resourceIds);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "resource_ids", nulls = Nulls.SKIP)
        public _FinalStage resourceIds(List<String> resourceIds) {
            this.resourceIds.clear();
            this.resourceIds.addAll(resourceIds);
            return this;
        }

        /**
         * <p>Value to compare the track event metric against</p>
         * @return Reference to {@code this} so that method calls can be chained together.
         */
        @java.lang.Override
        public _FinalStage metricValue(Integer metricValue) {
            this.metricValue = Optional.ofNullable(metricValue);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "metric_value", nulls = Nulls.SKIP)
        public _FinalStage metricValue(Optional<Integer> metricValue) {
            this.metricValue = metricValue;
            return this;
        }

        /**
         * <p>When metric_period=current_month, specify whether the month restarts based on the calendar month or the billing period</p>
         * @return Reference to {@code this} so that method calls can be chained together.
         */
        @java.lang.Override
        public _FinalStage metricPeriodMonthReset(
                CreateOrUpdateConditionRequestBodyMetricPeriodMonthReset metricPeriodMonthReset) {
            this.metricPeriodMonthReset = Optional.ofNullable(metricPeriodMonthReset);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "metric_period_month_reset", nulls = Nulls.SKIP)
        public _FinalStage metricPeriodMonthReset(
                Optional<CreateOrUpdateConditionRequestBodyMetricPeriodMonthReset> metricPeriodMonthReset) {
            this.metricPeriodMonthReset = metricPeriodMonthReset;
            return this;
        }

        /**
         * <p>Period of time over which to measure the track event metric</p>
         * @return Reference to {@code this} so that method calls can be chained together.
         */
        @java.lang.Override
        public _FinalStage metricPeriod(CreateOrUpdateConditionRequestBodyMetricPeriod metricPeriod) {
            this.metricPeriod = Optional.ofNullable(metricPeriod);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "metric_period", nulls = Nulls.SKIP)
        public _FinalStage metricPeriod(Optional<CreateOrUpdateConditionRequestBodyMetricPeriod> metricPeriod) {
            this.metricPeriod = metricPeriod;
            return this;
        }

        @java.lang.Override
        public _FinalStage id(String id) {
            this.id = Optional.ofNullable(id);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "id", nulls = Nulls.SKIP)
        public _FinalStage id(Optional<String> id) {
            this.id = id;
            return this;
        }

        /**
         * <p>Name of track event type used to measure this condition</p>
         * @return Reference to {@code this} so that method calls can be chained together.
         */
        @java.lang.Override
        public _FinalStage eventSubtype(String eventSubtype) {
            this.eventSubtype = Optional.ofNullable(eventSubtype);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "event_subtype", nulls = Nulls.SKIP)
        public _FinalStage eventSubtype(Optional<String> eventSubtype) {
            this.eventSubtype = eventSubtype;
            return this;
        }

        /**
         * <p>Optionally provide a trait ID to compare a metric or trait value against instead of a value</p>
         * @return Reference to {@code this} so that method calls can be chained together.
         */
        @java.lang.Override
        public _FinalStage comparisonTraitId(String comparisonTraitId) {
            this.comparisonTraitId = Optional.ofNullable(comparisonTraitId);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "comparison_trait_id", nulls = Nulls.SKIP)
        public _FinalStage comparisonTraitId(Optional<String> comparisonTraitId) {
            this.comparisonTraitId = comparisonTraitId;
            return this;
        }

        @java.lang.Override
        public CreateOrUpdateConditionRequestBody build() {
            return new CreateOrUpdateConditionRequestBody(
                    comparisonTraitId,
                    conditionType,
                    eventSubtype,
                    id,
                    metricPeriod,
                    metricPeriodMonthReset,
                    metricValue,
                    operator,
                    resourceIds,
                    traitId,
                    traitValue,
                    additionalProperties);
        }
    }
}
