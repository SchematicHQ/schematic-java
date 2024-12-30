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

@JsonInclude(JsonInclude.Include.NON_ABSENT)
@JsonDeserialize(builder = CompanyEventPeriodMetricsResponseData.Builder.class)
public final class CompanyEventPeriodMetricsResponseData {
    private final String accountId;

    private final OffsetDateTime capturedAtMax;

    private final OffsetDateTime capturedAtMin;

    private final String companyId;

    private final OffsetDateTime createdAt;

    private final String environmentId;

    private final String eventSubtype;

    private final String monthReset;

    private final String period;

    private final Optional<OffsetDateTime> validUntil;

    private final int value;

    private final Map<String, Object> additionalProperties;

    private CompanyEventPeriodMetricsResponseData(
            String accountId,
            OffsetDateTime capturedAtMax,
            OffsetDateTime capturedAtMin,
            String companyId,
            OffsetDateTime createdAt,
            String environmentId,
            String eventSubtype,
            String monthReset,
            String period,
            Optional<OffsetDateTime> validUntil,
            int value,
            Map<String, Object> additionalProperties) {
        this.accountId = accountId;
        this.capturedAtMax = capturedAtMax;
        this.capturedAtMin = capturedAtMin;
        this.companyId = companyId;
        this.createdAt = createdAt;
        this.environmentId = environmentId;
        this.eventSubtype = eventSubtype;
        this.monthReset = monthReset;
        this.period = period;
        this.validUntil = validUntil;
        this.value = value;
        this.additionalProperties = additionalProperties;
    }

    @JsonProperty("account_id")
    public String getAccountId() {
        return accountId;
    }

    @JsonProperty("captured_at_max")
    public OffsetDateTime getCapturedAtMax() {
        return capturedAtMax;
    }

    @JsonProperty("captured_at_min")
    public OffsetDateTime getCapturedAtMin() {
        return capturedAtMin;
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

    @JsonProperty("event_subtype")
    public String getEventSubtype() {
        return eventSubtype;
    }

    @JsonProperty("month_reset")
    public String getMonthReset() {
        return monthReset;
    }

    @JsonProperty("period")
    public String getPeriod() {
        return period;
    }

    @JsonProperty("valid_until")
    public Optional<OffsetDateTime> getValidUntil() {
        return validUntil;
    }

    @JsonProperty("value")
    public int getValue() {
        return value;
    }

    @java.lang.Override
    public boolean equals(Object other) {
        if (this == other) return true;
        return other instanceof CompanyEventPeriodMetricsResponseData
                && equalTo((CompanyEventPeriodMetricsResponseData) other);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    private boolean equalTo(CompanyEventPeriodMetricsResponseData other) {
        return accountId.equals(other.accountId)
                && capturedAtMax.equals(other.capturedAtMax)
                && capturedAtMin.equals(other.capturedAtMin)
                && companyId.equals(other.companyId)
                && createdAt.equals(other.createdAt)
                && environmentId.equals(other.environmentId)
                && eventSubtype.equals(other.eventSubtype)
                && monthReset.equals(other.monthReset)
                && period.equals(other.period)
                && validUntil.equals(other.validUntil)
                && value == other.value;
    }

    @java.lang.Override
    public int hashCode() {
        return Objects.hash(
                this.accountId,
                this.capturedAtMax,
                this.capturedAtMin,
                this.companyId,
                this.createdAt,
                this.environmentId,
                this.eventSubtype,
                this.monthReset,
                this.period,
                this.validUntil,
                this.value);
    }

    @java.lang.Override
    public String toString() {
        return ObjectMappers.stringify(this);
    }

    public static AccountIdStage builder() {
        return new Builder();
    }

    public interface AccountIdStage {
        CapturedAtMaxStage accountId(String accountId);

        Builder from(CompanyEventPeriodMetricsResponseData other);
    }

    public interface CapturedAtMaxStage {
        CapturedAtMinStage capturedAtMax(OffsetDateTime capturedAtMax);
    }

    public interface CapturedAtMinStage {
        CompanyIdStage capturedAtMin(OffsetDateTime capturedAtMin);
    }

    public interface CompanyIdStage {
        CreatedAtStage companyId(String companyId);
    }

    public interface CreatedAtStage {
        EnvironmentIdStage createdAt(OffsetDateTime createdAt);
    }

    public interface EnvironmentIdStage {
        EventSubtypeStage environmentId(String environmentId);
    }

    public interface EventSubtypeStage {
        MonthResetStage eventSubtype(String eventSubtype);
    }

    public interface MonthResetStage {
        PeriodStage monthReset(String monthReset);
    }

    public interface PeriodStage {
        ValueStage period(String period);
    }

    public interface ValueStage {
        _FinalStage value(int value);
    }

    public interface _FinalStage {
        CompanyEventPeriodMetricsResponseData build();

        _FinalStage validUntil(Optional<OffsetDateTime> validUntil);

        _FinalStage validUntil(OffsetDateTime validUntil);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static final class Builder
            implements AccountIdStage,
                    CapturedAtMaxStage,
                    CapturedAtMinStage,
                    CompanyIdStage,
                    CreatedAtStage,
                    EnvironmentIdStage,
                    EventSubtypeStage,
                    MonthResetStage,
                    PeriodStage,
                    ValueStage,
                    _FinalStage {
        private String accountId;

        private OffsetDateTime capturedAtMax;

        private OffsetDateTime capturedAtMin;

        private String companyId;

        private OffsetDateTime createdAt;

        private String environmentId;

        private String eventSubtype;

        private String monthReset;

        private String period;

        private int value;

        private Optional<OffsetDateTime> validUntil = Optional.empty();

        @JsonAnySetter
        private Map<String, Object> additionalProperties = new HashMap<>();

        private Builder() {}

        @java.lang.Override
        public Builder from(CompanyEventPeriodMetricsResponseData other) {
            accountId(other.getAccountId());
            capturedAtMax(other.getCapturedAtMax());
            capturedAtMin(other.getCapturedAtMin());
            companyId(other.getCompanyId());
            createdAt(other.getCreatedAt());
            environmentId(other.getEnvironmentId());
            eventSubtype(other.getEventSubtype());
            monthReset(other.getMonthReset());
            period(other.getPeriod());
            validUntil(other.getValidUntil());
            value(other.getValue());
            return this;
        }

        @java.lang.Override
        @JsonSetter("account_id")
        public CapturedAtMaxStage accountId(String accountId) {
            this.accountId = accountId;
            return this;
        }

        @java.lang.Override
        @JsonSetter("captured_at_max")
        public CapturedAtMinStage capturedAtMax(OffsetDateTime capturedAtMax) {
            this.capturedAtMax = capturedAtMax;
            return this;
        }

        @java.lang.Override
        @JsonSetter("captured_at_min")
        public CompanyIdStage capturedAtMin(OffsetDateTime capturedAtMin) {
            this.capturedAtMin = capturedAtMin;
            return this;
        }

        @java.lang.Override
        @JsonSetter("company_id")
        public CreatedAtStage companyId(String companyId) {
            this.companyId = companyId;
            return this;
        }

        @java.lang.Override
        @JsonSetter("created_at")
        public EnvironmentIdStage createdAt(OffsetDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        @java.lang.Override
        @JsonSetter("environment_id")
        public EventSubtypeStage environmentId(String environmentId) {
            this.environmentId = environmentId;
            return this;
        }

        @java.lang.Override
        @JsonSetter("event_subtype")
        public MonthResetStage eventSubtype(String eventSubtype) {
            this.eventSubtype = eventSubtype;
            return this;
        }

        @java.lang.Override
        @JsonSetter("month_reset")
        public PeriodStage monthReset(String monthReset) {
            this.monthReset = monthReset;
            return this;
        }

        @java.lang.Override
        @JsonSetter("period")
        public ValueStage period(String period) {
            this.period = period;
            return this;
        }

        @java.lang.Override
        @JsonSetter("value")
        public _FinalStage value(int value) {
            this.value = value;
            return this;
        }

        @java.lang.Override
        public _FinalStage validUntil(OffsetDateTime validUntil) {
            this.validUntil = Optional.ofNullable(validUntil);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "valid_until", nulls = Nulls.SKIP)
        public _FinalStage validUntil(Optional<OffsetDateTime> validUntil) {
            this.validUntil = validUntil;
            return this;
        }

        @java.lang.Override
        public CompanyEventPeriodMetricsResponseData build() {
            return new CompanyEventPeriodMetricsResponseData(
                    accountId,
                    capturedAtMax,
                    capturedAtMin,
                    companyId,
                    createdAt,
                    environmentId,
                    eventSubtype,
                    monthReset,
                    period,
                    validUntil,
                    value,
                    additionalProperties);
        }
    }
}
