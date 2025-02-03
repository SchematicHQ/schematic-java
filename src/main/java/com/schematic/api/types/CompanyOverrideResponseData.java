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
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;

@JsonInclude(JsonInclude.Include.NON_ABSENT)
@JsonDeserialize(builder = CompanyOverrideResponseData.Builder.class)
public final class CompanyOverrideResponseData {
    private final Optional<CompanyDetailResponseData> company;

    private final String companyId;

    private final OffsetDateTime createdAt;

    private final String environmentId;

    private final Optional<OffsetDateTime> expirationDate;

    private final Optional<FeatureResponseData> feature;

    private final String featureId;

    private final String id;

    private final Optional<String> metricPeriod;

    private final Optional<String> metricPeriodMonthReset;

    private final Optional<String> ruleId;

    private final Optional<String> ruleIdUsageExceeded;

    private final OffsetDateTime updatedAt;

    private final Optional<Boolean> valueBool;

    private final Optional<Integer> valueNumeric;

    private final Optional<EntityTraitDefinitionResponseData> valueTrait;

    private final Optional<String> valueTraitId;

    private final String valueType;

    private final Map<String, Object> additionalProperties;

    private CompanyOverrideResponseData(
            Optional<CompanyDetailResponseData> company,
            String companyId,
            OffsetDateTime createdAt,
            String environmentId,
            Optional<OffsetDateTime> expirationDate,
            Optional<FeatureResponseData> feature,
            String featureId,
            String id,
            Optional<String> metricPeriod,
            Optional<String> metricPeriodMonthReset,
            Optional<String> ruleId,
            Optional<String> ruleIdUsageExceeded,
            OffsetDateTime updatedAt,
            Optional<Boolean> valueBool,
            Optional<Integer> valueNumeric,
            Optional<EntityTraitDefinitionResponseData> valueTrait,
            Optional<String> valueTraitId,
            String valueType,
            Map<String, Object> additionalProperties) {
        this.company = company;
        this.companyId = companyId;
        this.createdAt = createdAt;
        this.environmentId = environmentId;
        this.expirationDate = expirationDate;
        this.feature = feature;
        this.featureId = featureId;
        this.id = id;
        this.metricPeriod = metricPeriod;
        this.metricPeriodMonthReset = metricPeriodMonthReset;
        this.ruleId = ruleId;
        this.ruleIdUsageExceeded = ruleIdUsageExceeded;
        this.updatedAt = updatedAt;
        this.valueBool = valueBool;
        this.valueNumeric = valueNumeric;
        this.valueTrait = valueTrait;
        this.valueTraitId = valueTraitId;
        this.valueType = valueType;
        this.additionalProperties = additionalProperties;
    }

    @JsonProperty("company")
    public Optional<CompanyDetailResponseData> getCompany() {
        return company;
    }

    @JsonProperty("company_id")
    public String getCompanyId() {
        return companyId;
    }

    @JsonProperty("created_at")
    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    @JsonProperty("environment_id")
    public String getEnvironmentId() {
        return environmentId;
    }

    @JsonProperty("expiration_date")
    public Optional<OffsetDateTime> getExpirationDate() {
        return expirationDate;
    }

    @JsonProperty("feature")
    public Optional<FeatureResponseData> getFeature() {
        return feature;
    }

    @JsonProperty("feature_id")
    public String getFeatureId() {
        return featureId;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("metric_period")
    public Optional<String> getMetricPeriod() {
        return metricPeriod;
    }

    @JsonProperty("metric_period_month_reset")
    public Optional<String> getMetricPeriodMonthReset() {
        return metricPeriodMonthReset;
    }

    @JsonProperty("rule_id")
    public Optional<String> getRuleId() {
        return ruleId;
    }

    @JsonProperty("rule_id_usage_exceeded")
    public Optional<String> getRuleIdUsageExceeded() {
        return ruleIdUsageExceeded;
    }

    @JsonProperty("updated_at")
    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    @JsonProperty("value_bool")
    public Optional<Boolean> getValueBool() {
        return valueBool;
    }

    @JsonProperty("value_numeric")
    public Optional<Integer> getValueNumeric() {
        return valueNumeric;
    }

    @JsonProperty("value_trait")
    public Optional<EntityTraitDefinitionResponseData> getValueTrait() {
        return valueTrait;
    }

    @JsonProperty("value_trait_id")
    public Optional<String> getValueTraitId() {
        return valueTraitId;
    }

    @JsonProperty("value_type")
    public String getValueType() {
        return valueType;
    }

    @java.lang.Override
    public boolean equals(Object other) {
        if (this == other) return true;
        return other instanceof CompanyOverrideResponseData && equalTo((CompanyOverrideResponseData) other);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    private boolean equalTo(CompanyOverrideResponseData other) {
        return company.equals(other.company)
                && companyId.equals(other.companyId)
                && createdAt.equals(other.createdAt)
                && environmentId.equals(other.environmentId)
                && expirationDate.equals(other.expirationDate)
                && feature.equals(other.feature)
                && featureId.equals(other.featureId)
                && id.equals(other.id)
                && metricPeriod.equals(other.metricPeriod)
                && metricPeriodMonthReset.equals(other.metricPeriodMonthReset)
                && ruleId.equals(other.ruleId)
                && ruleIdUsageExceeded.equals(other.ruleIdUsageExceeded)
                && updatedAt.equals(other.updatedAt)
                && valueBool.equals(other.valueBool)
                && valueNumeric.equals(other.valueNumeric)
                && valueTrait.equals(other.valueTrait)
                && valueTraitId.equals(other.valueTraitId)
                && valueType.equals(other.valueType);
    }

    @java.lang.Override
    public int hashCode() {
        return Objects.hash(
                this.company,
                this.companyId,
                this.createdAt,
                this.environmentId,
                this.expirationDate,
                this.feature,
                this.featureId,
                this.id,
                this.metricPeriod,
                this.metricPeriodMonthReset,
                this.ruleId,
                this.ruleIdUsageExceeded,
                this.updatedAt,
                this.valueBool,
                this.valueNumeric,
                this.valueTrait,
                this.valueTraitId,
                this.valueType);
    }

    @java.lang.Override
    public String toString() {
        return ObjectMappers.stringify(this);
    }

    public static CompanyIdStage builder() {
        return new Builder();
    }

    public interface CompanyIdStage {
        CreatedAtStage companyId(@NotNull String companyId);

        Builder from(CompanyOverrideResponseData other);
    }

    public interface CreatedAtStage {
        EnvironmentIdStage createdAt(@NotNull OffsetDateTime createdAt);
    }

    public interface EnvironmentIdStage {
        FeatureIdStage environmentId(@NotNull String environmentId);
    }

    public interface FeatureIdStage {
        IdStage featureId(@NotNull String featureId);
    }

    public interface IdStage {
        UpdatedAtStage id(@NotNull String id);
    }

    public interface UpdatedAtStage {
        ValueTypeStage updatedAt(@NotNull OffsetDateTime updatedAt);
    }

    public interface ValueTypeStage {
        _FinalStage valueType(@NotNull String valueType);
    }

    public interface _FinalStage {
        CompanyOverrideResponseData build();

        _FinalStage company(Optional<CompanyDetailResponseData> company);

        _FinalStage company(CompanyDetailResponseData company);

        _FinalStage expirationDate(Optional<OffsetDateTime> expirationDate);

        _FinalStage expirationDate(OffsetDateTime expirationDate);

        _FinalStage feature(Optional<FeatureResponseData> feature);

        _FinalStage feature(FeatureResponseData feature);

        _FinalStage metricPeriod(Optional<String> metricPeriod);

        _FinalStage metricPeriod(String metricPeriod);

        _FinalStage metricPeriodMonthReset(Optional<String> metricPeriodMonthReset);

        _FinalStage metricPeriodMonthReset(String metricPeriodMonthReset);

        _FinalStage ruleId(Optional<String> ruleId);

        _FinalStage ruleId(String ruleId);

        _FinalStage ruleIdUsageExceeded(Optional<String> ruleIdUsageExceeded);

        _FinalStage ruleIdUsageExceeded(String ruleIdUsageExceeded);

        _FinalStage valueBool(Optional<Boolean> valueBool);

        _FinalStage valueBool(Boolean valueBool);

        _FinalStage valueNumeric(Optional<Integer> valueNumeric);

        _FinalStage valueNumeric(Integer valueNumeric);

        _FinalStage valueTrait(Optional<EntityTraitDefinitionResponseData> valueTrait);

        _FinalStage valueTrait(EntityTraitDefinitionResponseData valueTrait);

        _FinalStage valueTraitId(Optional<String> valueTraitId);

        _FinalStage valueTraitId(String valueTraitId);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static final class Builder
            implements CompanyIdStage,
                    CreatedAtStage,
                    EnvironmentIdStage,
                    FeatureIdStage,
                    IdStage,
                    UpdatedAtStage,
                    ValueTypeStage,
                    _FinalStage {
        private String companyId;

        private OffsetDateTime createdAt;

        private String environmentId;

        private String featureId;

        private String id;

        private OffsetDateTime updatedAt;

        private String valueType;

        private Optional<String> valueTraitId = Optional.empty();

        private Optional<EntityTraitDefinitionResponseData> valueTrait = Optional.empty();

        private Optional<Integer> valueNumeric = Optional.empty();

        private Optional<Boolean> valueBool = Optional.empty();

        private Optional<String> ruleIdUsageExceeded = Optional.empty();

        private Optional<String> ruleId = Optional.empty();

        private Optional<String> metricPeriodMonthReset = Optional.empty();

        private Optional<String> metricPeriod = Optional.empty();

        private Optional<FeatureResponseData> feature = Optional.empty();

        private Optional<OffsetDateTime> expirationDate = Optional.empty();

        private Optional<CompanyDetailResponseData> company = Optional.empty();

        @JsonAnySetter
        private Map<String, Object> additionalProperties = new HashMap<>();

        private Builder() {}

        @java.lang.Override
        public Builder from(CompanyOverrideResponseData other) {
            company(other.getCompany());
            companyId(other.getCompanyId());
            createdAt(other.getCreatedAt());
            environmentId(other.getEnvironmentId());
            expirationDate(other.getExpirationDate());
            feature(other.getFeature());
            featureId(other.getFeatureId());
            id(other.getId());
            metricPeriod(other.getMetricPeriod());
            metricPeriodMonthReset(other.getMetricPeriodMonthReset());
            ruleId(other.getRuleId());
            ruleIdUsageExceeded(other.getRuleIdUsageExceeded());
            updatedAt(other.getUpdatedAt());
            valueBool(other.getValueBool());
            valueNumeric(other.getValueNumeric());
            valueTrait(other.getValueTrait());
            valueTraitId(other.getValueTraitId());
            valueType(other.getValueType());
            return this;
        }

        @java.lang.Override
        @JsonSetter("company_id")
        public CreatedAtStage companyId(@NotNull String companyId) {
            this.companyId = Objects.requireNonNull(companyId, "companyId must not be null");
            return this;
        }

        @java.lang.Override
        @JsonSetter("created_at")
        public EnvironmentIdStage createdAt(@NotNull OffsetDateTime createdAt) {
            this.createdAt = Objects.requireNonNull(createdAt, "createdAt must not be null");
            return this;
        }

        @java.lang.Override
        @JsonSetter("environment_id")
        public FeatureIdStage environmentId(@NotNull String environmentId) {
            this.environmentId = Objects.requireNonNull(environmentId, "environmentId must not be null");
            return this;
        }

        @java.lang.Override
        @JsonSetter("feature_id")
        public IdStage featureId(@NotNull String featureId) {
            this.featureId = Objects.requireNonNull(featureId, "featureId must not be null");
            return this;
        }

        @java.lang.Override
        @JsonSetter("id")
        public UpdatedAtStage id(@NotNull String id) {
            this.id = Objects.requireNonNull(id, "id must not be null");
            return this;
        }

        @java.lang.Override
        @JsonSetter("updated_at")
        public ValueTypeStage updatedAt(@NotNull OffsetDateTime updatedAt) {
            this.updatedAt = Objects.requireNonNull(updatedAt, "updatedAt must not be null");
            return this;
        }

        @java.lang.Override
        @JsonSetter("value_type")
        public _FinalStage valueType(@NotNull String valueType) {
            this.valueType = Objects.requireNonNull(valueType, "valueType must not be null");
            return this;
        }

        @java.lang.Override
        public _FinalStage valueTraitId(String valueTraitId) {
            this.valueTraitId = Optional.ofNullable(valueTraitId);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "value_trait_id", nulls = Nulls.SKIP)
        public _FinalStage valueTraitId(Optional<String> valueTraitId) {
            this.valueTraitId = valueTraitId;
            return this;
        }

        @java.lang.Override
        public _FinalStage valueTrait(EntityTraitDefinitionResponseData valueTrait) {
            this.valueTrait = Optional.ofNullable(valueTrait);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "value_trait", nulls = Nulls.SKIP)
        public _FinalStage valueTrait(Optional<EntityTraitDefinitionResponseData> valueTrait) {
            this.valueTrait = valueTrait;
            return this;
        }

        @java.lang.Override
        public _FinalStage valueNumeric(Integer valueNumeric) {
            this.valueNumeric = Optional.ofNullable(valueNumeric);
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
            this.valueBool = Optional.ofNullable(valueBool);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "value_bool", nulls = Nulls.SKIP)
        public _FinalStage valueBool(Optional<Boolean> valueBool) {
            this.valueBool = valueBool;
            return this;
        }

        @java.lang.Override
        public _FinalStage ruleIdUsageExceeded(String ruleIdUsageExceeded) {
            this.ruleIdUsageExceeded = Optional.ofNullable(ruleIdUsageExceeded);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "rule_id_usage_exceeded", nulls = Nulls.SKIP)
        public _FinalStage ruleIdUsageExceeded(Optional<String> ruleIdUsageExceeded) {
            this.ruleIdUsageExceeded = ruleIdUsageExceeded;
            return this;
        }

        @java.lang.Override
        public _FinalStage ruleId(String ruleId) {
            this.ruleId = Optional.ofNullable(ruleId);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "rule_id", nulls = Nulls.SKIP)
        public _FinalStage ruleId(Optional<String> ruleId) {
            this.ruleId = ruleId;
            return this;
        }

        @java.lang.Override
        public _FinalStage metricPeriodMonthReset(String metricPeriodMonthReset) {
            this.metricPeriodMonthReset = Optional.ofNullable(metricPeriodMonthReset);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "metric_period_month_reset", nulls = Nulls.SKIP)
        public _FinalStage metricPeriodMonthReset(Optional<String> metricPeriodMonthReset) {
            this.metricPeriodMonthReset = metricPeriodMonthReset;
            return this;
        }

        @java.lang.Override
        public _FinalStage metricPeriod(String metricPeriod) {
            this.metricPeriod = Optional.ofNullable(metricPeriod);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "metric_period", nulls = Nulls.SKIP)
        public _FinalStage metricPeriod(Optional<String> metricPeriod) {
            this.metricPeriod = metricPeriod;
            return this;
        }

        @java.lang.Override
        public _FinalStage feature(FeatureResponseData feature) {
            this.feature = Optional.ofNullable(feature);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "feature", nulls = Nulls.SKIP)
        public _FinalStage feature(Optional<FeatureResponseData> feature) {
            this.feature = feature;
            return this;
        }

        @java.lang.Override
        public _FinalStage expirationDate(OffsetDateTime expirationDate) {
            this.expirationDate = Optional.ofNullable(expirationDate);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "expiration_date", nulls = Nulls.SKIP)
        public _FinalStage expirationDate(Optional<OffsetDateTime> expirationDate) {
            this.expirationDate = expirationDate;
            return this;
        }

        @java.lang.Override
        public _FinalStage company(CompanyDetailResponseData company) {
            this.company = Optional.ofNullable(company);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "company", nulls = Nulls.SKIP)
        public _FinalStage company(Optional<CompanyDetailResponseData> company) {
            this.company = company;
            return this;
        }

        @java.lang.Override
        public CompanyOverrideResponseData build() {
            return new CompanyOverrideResponseData(
                    company,
                    companyId,
                    createdAt,
                    environmentId,
                    expirationDate,
                    feature,
                    featureId,
                    id,
                    metricPeriod,
                    metricPeriodMonthReset,
                    ruleId,
                    ruleIdUsageExceeded,
                    updatedAt,
                    valueBool,
                    valueNumeric,
                    valueTrait,
                    valueTraitId,
                    valueType,
                    additionalProperties);
        }
    }
}
