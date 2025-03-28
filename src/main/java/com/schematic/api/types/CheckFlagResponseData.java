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
@JsonDeserialize(builder = CheckFlagResponseData.Builder.class)
public final class CheckFlagResponseData {
    private final Optional<String> companyId;

    private final Optional<String> error;

    private final Optional<Integer> featureAllocation;

    private final Optional<Integer> featureUsage;

    private final Optional<String> featureUsagePeriod;

    private final Optional<OffsetDateTime> featureUsageResetAt;

    private final String flag;

    private final Optional<String> flagId;

    private final String reason;

    private final Optional<String> ruleId;

    private final Optional<String> ruleType;

    private final Optional<String> userId;

    private final boolean value;

    private final Map<String, Object> additionalProperties;

    private CheckFlagResponseData(
            Optional<String> companyId,
            Optional<String> error,
            Optional<Integer> featureAllocation,
            Optional<Integer> featureUsage,
            Optional<String> featureUsagePeriod,
            Optional<OffsetDateTime> featureUsageResetAt,
            String flag,
            Optional<String> flagId,
            String reason,
            Optional<String> ruleId,
            Optional<String> ruleType,
            Optional<String> userId,
            boolean value,
            Map<String, Object> additionalProperties) {
        this.companyId = companyId;
        this.error = error;
        this.featureAllocation = featureAllocation;
        this.featureUsage = featureUsage;
        this.featureUsagePeriod = featureUsagePeriod;
        this.featureUsageResetAt = featureUsageResetAt;
        this.flag = flag;
        this.flagId = flagId;
        this.reason = reason;
        this.ruleId = ruleId;
        this.ruleType = ruleType;
        this.userId = userId;
        this.value = value;
        this.additionalProperties = additionalProperties;
    }

    /**
     * @return If company keys were provided and matched a company, its ID
     */
    @JsonProperty("company_id")
    public Optional<String> getCompanyId() {
        return companyId;
    }

    /**
     * @return If an error occurred while checking the flag, the error message
     */
    @JsonProperty("error")
    public Optional<String> getError() {
        return error;
    }

    /**
     * @return If a numeric feature entitlement rule was matched, its allocation
     */
    @JsonProperty("feature_allocation")
    public Optional<Integer> getFeatureAllocation() {
        return featureAllocation;
    }

    /**
     * @return If a numeric feature entitlement rule was matched, the company's usage
     */
    @JsonProperty("feature_usage")
    public Optional<Integer> getFeatureUsage() {
        return featureUsage;
    }

    /**
     * @return For event-based feature entitlement rules, the period over which usage is tracked (current_month, current_day, current_week, all_time)
     */
    @JsonProperty("feature_usage_period")
    public Optional<String> getFeatureUsagePeriod() {
        return featureUsagePeriod;
    }

    /**
     * @return For event-based feature entitlement rules, when the usage period will reset
     */
    @JsonProperty("feature_usage_reset_at")
    public Optional<OffsetDateTime> getFeatureUsageResetAt() {
        return featureUsageResetAt;
    }

    /**
     * @return The key used to check the flag
     */
    @JsonProperty("flag")
    public String getFlag() {
        return flag;
    }

    /**
     * @return If a flag was found, its ID
     */
    @JsonProperty("flag_id")
    public Optional<String> getFlagId() {
        return flagId;
    }

    /**
     * @return A human-readable explanation of the result
     */
    @JsonProperty("reason")
    public String getReason() {
        return reason;
    }

    /**
     * @return If a rule was found, its ID
     */
    @JsonProperty("rule_id")
    public Optional<String> getRuleId() {
        return ruleId;
    }

    /**
     * @return If a rule was found, its type
     */
    @JsonProperty("rule_type")
    public Optional<String> getRuleType() {
        return ruleType;
    }

    /**
     * @return If user keys were provided and matched a user, its ID
     */
    @JsonProperty("user_id")
    public Optional<String> getUserId() {
        return userId;
    }

    /**
     * @return A boolean flag check result; for feature entitlements, this represents whether further consumption of the feature is permitted
     */
    @JsonProperty("value")
    public boolean getValue() {
        return value;
    }

    @java.lang.Override
    public boolean equals(Object other) {
        if (this == other) return true;
        return other instanceof CheckFlagResponseData && equalTo((CheckFlagResponseData) other);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    private boolean equalTo(CheckFlagResponseData other) {
        return companyId.equals(other.companyId)
                && error.equals(other.error)
                && featureAllocation.equals(other.featureAllocation)
                && featureUsage.equals(other.featureUsage)
                && featureUsagePeriod.equals(other.featureUsagePeriod)
                && featureUsageResetAt.equals(other.featureUsageResetAt)
                && flag.equals(other.flag)
                && flagId.equals(other.flagId)
                && reason.equals(other.reason)
                && ruleId.equals(other.ruleId)
                && ruleType.equals(other.ruleType)
                && userId.equals(other.userId)
                && value == other.value;
    }

    @java.lang.Override
    public int hashCode() {
        return Objects.hash(
                this.companyId,
                this.error,
                this.featureAllocation,
                this.featureUsage,
                this.featureUsagePeriod,
                this.featureUsageResetAt,
                this.flag,
                this.flagId,
                this.reason,
                this.ruleId,
                this.ruleType,
                this.userId,
                this.value);
    }

    @java.lang.Override
    public String toString() {
        return ObjectMappers.stringify(this);
    }

    public static FlagStage builder() {
        return new Builder();
    }

    public interface FlagStage {
        ReasonStage flag(@NotNull String flag);

        Builder from(CheckFlagResponseData other);
    }

    public interface ReasonStage {
        ValueStage reason(@NotNull String reason);
    }

    public interface ValueStage {
        _FinalStage value(boolean value);
    }

    public interface _FinalStage {
        CheckFlagResponseData build();

        _FinalStage companyId(Optional<String> companyId);

        _FinalStage companyId(String companyId);

        _FinalStage error(Optional<String> error);

        _FinalStage error(String error);

        _FinalStage featureAllocation(Optional<Integer> featureAllocation);

        _FinalStage featureAllocation(Integer featureAllocation);

        _FinalStage featureUsage(Optional<Integer> featureUsage);

        _FinalStage featureUsage(Integer featureUsage);

        _FinalStage featureUsagePeriod(Optional<String> featureUsagePeriod);

        _FinalStage featureUsagePeriod(String featureUsagePeriod);

        _FinalStage featureUsageResetAt(Optional<OffsetDateTime> featureUsageResetAt);

        _FinalStage featureUsageResetAt(OffsetDateTime featureUsageResetAt);

        _FinalStage flagId(Optional<String> flagId);

        _FinalStage flagId(String flagId);

        _FinalStage ruleId(Optional<String> ruleId);

        _FinalStage ruleId(String ruleId);

        _FinalStage ruleType(Optional<String> ruleType);

        _FinalStage ruleType(String ruleType);

        _FinalStage userId(Optional<String> userId);

        _FinalStage userId(String userId);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static final class Builder implements FlagStage, ReasonStage, ValueStage, _FinalStage {
        private String flag;

        private String reason;

        private boolean value;

        private Optional<String> userId = Optional.empty();

        private Optional<String> ruleType = Optional.empty();

        private Optional<String> ruleId = Optional.empty();

        private Optional<String> flagId = Optional.empty();

        private Optional<OffsetDateTime> featureUsageResetAt = Optional.empty();

        private Optional<String> featureUsagePeriod = Optional.empty();

        private Optional<Integer> featureUsage = Optional.empty();

        private Optional<Integer> featureAllocation = Optional.empty();

        private Optional<String> error = Optional.empty();

        private Optional<String> companyId = Optional.empty();

        @JsonAnySetter
        private Map<String, Object> additionalProperties = new HashMap<>();

        private Builder() {}

        @java.lang.Override
        public Builder from(CheckFlagResponseData other) {
            companyId(other.getCompanyId());
            error(other.getError());
            featureAllocation(other.getFeatureAllocation());
            featureUsage(other.getFeatureUsage());
            featureUsagePeriod(other.getFeatureUsagePeriod());
            featureUsageResetAt(other.getFeatureUsageResetAt());
            flag(other.getFlag());
            flagId(other.getFlagId());
            reason(other.getReason());
            ruleId(other.getRuleId());
            ruleType(other.getRuleType());
            userId(other.getUserId());
            value(other.getValue());
            return this;
        }

        /**
         * <p>The key used to check the flag</p>
         * @return Reference to {@code this} so that method calls can be chained together.
         */
        @java.lang.Override
        @JsonSetter("flag")
        public ReasonStage flag(@NotNull String flag) {
            this.flag = Objects.requireNonNull(flag, "flag must not be null");
            return this;
        }

        /**
         * <p>A human-readable explanation of the result</p>
         * @return Reference to {@code this} so that method calls can be chained together.
         */
        @java.lang.Override
        @JsonSetter("reason")
        public ValueStage reason(@NotNull String reason) {
            this.reason = Objects.requireNonNull(reason, "reason must not be null");
            return this;
        }

        /**
         * <p>A boolean flag check result; for feature entitlements, this represents whether further consumption of the feature is permitted</p>
         * @return Reference to {@code this} so that method calls can be chained together.
         */
        @java.lang.Override
        @JsonSetter("value")
        public _FinalStage value(boolean value) {
            this.value = value;
            return this;
        }

        /**
         * <p>If user keys were provided and matched a user, its ID</p>
         * @return Reference to {@code this} so that method calls can be chained together.
         */
        @java.lang.Override
        public _FinalStage userId(String userId) {
            this.userId = Optional.ofNullable(userId);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "user_id", nulls = Nulls.SKIP)
        public _FinalStage userId(Optional<String> userId) {
            this.userId = userId;
            return this;
        }

        /**
         * <p>If a rule was found, its type</p>
         * @return Reference to {@code this} so that method calls can be chained together.
         */
        @java.lang.Override
        public _FinalStage ruleType(String ruleType) {
            this.ruleType = Optional.ofNullable(ruleType);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "rule_type", nulls = Nulls.SKIP)
        public _FinalStage ruleType(Optional<String> ruleType) {
            this.ruleType = ruleType;
            return this;
        }

        /**
         * <p>If a rule was found, its ID</p>
         * @return Reference to {@code this} so that method calls can be chained together.
         */
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

        /**
         * <p>If a flag was found, its ID</p>
         * @return Reference to {@code this} so that method calls can be chained together.
         */
        @java.lang.Override
        public _FinalStage flagId(String flagId) {
            this.flagId = Optional.ofNullable(flagId);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "flag_id", nulls = Nulls.SKIP)
        public _FinalStage flagId(Optional<String> flagId) {
            this.flagId = flagId;
            return this;
        }

        /**
         * <p>For event-based feature entitlement rules, when the usage period will reset</p>
         * @return Reference to {@code this} so that method calls can be chained together.
         */
        @java.lang.Override
        public _FinalStage featureUsageResetAt(OffsetDateTime featureUsageResetAt) {
            this.featureUsageResetAt = Optional.ofNullable(featureUsageResetAt);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "feature_usage_reset_at", nulls = Nulls.SKIP)
        public _FinalStage featureUsageResetAt(Optional<OffsetDateTime> featureUsageResetAt) {
            this.featureUsageResetAt = featureUsageResetAt;
            return this;
        }

        /**
         * <p>For event-based feature entitlement rules, the period over which usage is tracked (current_month, current_day, current_week, all_time)</p>
         * @return Reference to {@code this} so that method calls can be chained together.
         */
        @java.lang.Override
        public _FinalStage featureUsagePeriod(String featureUsagePeriod) {
            this.featureUsagePeriod = Optional.ofNullable(featureUsagePeriod);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "feature_usage_period", nulls = Nulls.SKIP)
        public _FinalStage featureUsagePeriod(Optional<String> featureUsagePeriod) {
            this.featureUsagePeriod = featureUsagePeriod;
            return this;
        }

        /**
         * <p>If a numeric feature entitlement rule was matched, the company's usage</p>
         * @return Reference to {@code this} so that method calls can be chained together.
         */
        @java.lang.Override
        public _FinalStage featureUsage(Integer featureUsage) {
            this.featureUsage = Optional.ofNullable(featureUsage);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "feature_usage", nulls = Nulls.SKIP)
        public _FinalStage featureUsage(Optional<Integer> featureUsage) {
            this.featureUsage = featureUsage;
            return this;
        }

        /**
         * <p>If a numeric feature entitlement rule was matched, its allocation</p>
         * @return Reference to {@code this} so that method calls can be chained together.
         */
        @java.lang.Override
        public _FinalStage featureAllocation(Integer featureAllocation) {
            this.featureAllocation = Optional.ofNullable(featureAllocation);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "feature_allocation", nulls = Nulls.SKIP)
        public _FinalStage featureAllocation(Optional<Integer> featureAllocation) {
            this.featureAllocation = featureAllocation;
            return this;
        }

        /**
         * <p>If an error occurred while checking the flag, the error message</p>
         * @return Reference to {@code this} so that method calls can be chained together.
         */
        @java.lang.Override
        public _FinalStage error(String error) {
            this.error = Optional.ofNullable(error);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "error", nulls = Nulls.SKIP)
        public _FinalStage error(Optional<String> error) {
            this.error = error;
            return this;
        }

        /**
         * <p>If company keys were provided and matched a company, its ID</p>
         * @return Reference to {@code this} so that method calls can be chained together.
         */
        @java.lang.Override
        public _FinalStage companyId(String companyId) {
            this.companyId = Optional.ofNullable(companyId);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "company_id", nulls = Nulls.SKIP)
        public _FinalStage companyId(Optional<String> companyId) {
            this.companyId = companyId;
            return this;
        }

        @java.lang.Override
        public CheckFlagResponseData build() {
            return new CheckFlagResponseData(
                    companyId,
                    error,
                    featureAllocation,
                    featureUsage,
                    featureUsagePeriod,
                    featureUsageResetAt,
                    flag,
                    flagId,
                    reason,
                    ruleId,
                    ruleType,
                    userId,
                    value,
                    additionalProperties);
        }
    }
}
