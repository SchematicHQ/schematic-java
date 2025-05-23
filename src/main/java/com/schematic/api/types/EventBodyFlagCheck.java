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
import org.jetbrains.annotations.NotNull;

@JsonInclude(JsonInclude.Include.NON_ABSENT)
@JsonDeserialize(builder = EventBodyFlagCheck.Builder.class)
public final class EventBodyFlagCheck {
    private final Optional<String> companyId;

    private final Optional<String> error;

    private final Optional<String> flagId;

    private final String flagKey;

    private final String reason;

    private final Optional<Map<String, String>> reqCompany;

    private final Optional<Map<String, String>> reqUser;

    private final Optional<String> ruleId;

    private final Optional<String> userId;

    private final boolean value;

    private final Map<String, Object> additionalProperties;

    private EventBodyFlagCheck(
            Optional<String> companyId,
            Optional<String> error,
            Optional<String> flagId,
            String flagKey,
            String reason,
            Optional<Map<String, String>> reqCompany,
            Optional<Map<String, String>> reqUser,
            Optional<String> ruleId,
            Optional<String> userId,
            boolean value,
            Map<String, Object> additionalProperties) {
        this.companyId = companyId;
        this.error = error;
        this.flagId = flagId;
        this.flagKey = flagKey;
        this.reason = reason;
        this.reqCompany = reqCompany;
        this.reqUser = reqUser;
        this.ruleId = ruleId;
        this.userId = userId;
        this.value = value;
        this.additionalProperties = additionalProperties;
    }

    /**
     * @return Schematic company ID (starting with 'comp_') of the company evaluated, if any
     */
    @JsonProperty("company_id")
    public Optional<String> getCompanyId() {
        return companyId;
    }

    /**
     * @return Report an error that occurred during the flag check
     */
    @JsonProperty("error")
    public Optional<String> getError() {
        return error;
    }

    /**
     * @return Schematic flag ID (starting with 'flag_') for the flag matching the key, if any
     */
    @JsonProperty("flag_id")
    public Optional<String> getFlagId() {
        return flagId;
    }

    /**
     * @return The key of the flag being checked
     */
    @JsonProperty("flag_key")
    public String getFlagKey() {
        return flagKey;
    }

    /**
     * @return The reason why the value was returned
     */
    @JsonProperty("reason")
    public String getReason() {
        return reason;
    }

    /**
     * @return Key-value pairs used to to identify company for which the flag was checked
     */
    @JsonProperty("req_company")
    public Optional<Map<String, String>> getReqCompany() {
        return reqCompany;
    }

    /**
     * @return Key-value pairs used to to identify user for which the flag was checked
     */
    @JsonProperty("req_user")
    public Optional<Map<String, String>> getReqUser() {
        return reqUser;
    }

    /**
     * @return Schematic rule ID (starting with 'rule_') of the rule that matched for the flag, if any
     */
    @JsonProperty("rule_id")
    public Optional<String> getRuleId() {
        return ruleId;
    }

    /**
     * @return Schematic user ID (starting with 'user_') of the user evaluated, if any
     */
    @JsonProperty("user_id")
    public Optional<String> getUserId() {
        return userId;
    }

    /**
     * @return The value of the flag for the given company and/or user
     */
    @JsonProperty("value")
    public boolean getValue() {
        return value;
    }

    @java.lang.Override
    public boolean equals(Object other) {
        if (this == other) return true;
        return other instanceof EventBodyFlagCheck && equalTo((EventBodyFlagCheck) other);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    private boolean equalTo(EventBodyFlagCheck other) {
        return companyId.equals(other.companyId)
                && error.equals(other.error)
                && flagId.equals(other.flagId)
                && flagKey.equals(other.flagKey)
                && reason.equals(other.reason)
                && reqCompany.equals(other.reqCompany)
                && reqUser.equals(other.reqUser)
                && ruleId.equals(other.ruleId)
                && userId.equals(other.userId)
                && value == other.value;
    }

    @java.lang.Override
    public int hashCode() {
        return Objects.hash(
                this.companyId,
                this.error,
                this.flagId,
                this.flagKey,
                this.reason,
                this.reqCompany,
                this.reqUser,
                this.ruleId,
                this.userId,
                this.value);
    }

    @java.lang.Override
    public String toString() {
        return ObjectMappers.stringify(this);
    }

    public static FlagKeyStage builder() {
        return new Builder();
    }

    public interface FlagKeyStage {
        ReasonStage flagKey(@NotNull String flagKey);

        Builder from(EventBodyFlagCheck other);
    }

    public interface ReasonStage {
        ValueStage reason(@NotNull String reason);
    }

    public interface ValueStage {
        _FinalStage value(boolean value);
    }

    public interface _FinalStage {
        EventBodyFlagCheck build();

        _FinalStage companyId(Optional<String> companyId);

        _FinalStage companyId(String companyId);

        _FinalStage error(Optional<String> error);

        _FinalStage error(String error);

        _FinalStage flagId(Optional<String> flagId);

        _FinalStage flagId(String flagId);

        _FinalStage reqCompany(Optional<Map<String, String>> reqCompany);

        _FinalStage reqCompany(Map<String, String> reqCompany);

        _FinalStage reqUser(Optional<Map<String, String>> reqUser);

        _FinalStage reqUser(Map<String, String> reqUser);

        _FinalStage ruleId(Optional<String> ruleId);

        _FinalStage ruleId(String ruleId);

        _FinalStage userId(Optional<String> userId);

        _FinalStage userId(String userId);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static final class Builder implements FlagKeyStage, ReasonStage, ValueStage, _FinalStage {
        private String flagKey;

        private String reason;

        private boolean value;

        private Optional<String> userId = Optional.empty();

        private Optional<String> ruleId = Optional.empty();

        private Optional<Map<String, String>> reqUser = Optional.empty();

        private Optional<Map<String, String>> reqCompany = Optional.empty();

        private Optional<String> flagId = Optional.empty();

        private Optional<String> error = Optional.empty();

        private Optional<String> companyId = Optional.empty();

        @JsonAnySetter
        private Map<String, Object> additionalProperties = new HashMap<>();

        private Builder() {}

        @java.lang.Override
        public Builder from(EventBodyFlagCheck other) {
            companyId(other.getCompanyId());
            error(other.getError());
            flagId(other.getFlagId());
            flagKey(other.getFlagKey());
            reason(other.getReason());
            reqCompany(other.getReqCompany());
            reqUser(other.getReqUser());
            ruleId(other.getRuleId());
            userId(other.getUserId());
            value(other.getValue());
            return this;
        }

        /**
         * <p>The key of the flag being checked</p>
         * @return Reference to {@code this} so that method calls can be chained together.
         */
        @java.lang.Override
        @JsonSetter("flag_key")
        public ReasonStage flagKey(@NotNull String flagKey) {
            this.flagKey = Objects.requireNonNull(flagKey, "flagKey must not be null");
            return this;
        }

        /**
         * <p>The reason why the value was returned</p>
         * @return Reference to {@code this} so that method calls can be chained together.
         */
        @java.lang.Override
        @JsonSetter("reason")
        public ValueStage reason(@NotNull String reason) {
            this.reason = Objects.requireNonNull(reason, "reason must not be null");
            return this;
        }

        /**
         * <p>The value of the flag for the given company and/or user</p>
         * @return Reference to {@code this} so that method calls can be chained together.
         */
        @java.lang.Override
        @JsonSetter("value")
        public _FinalStage value(boolean value) {
            this.value = value;
            return this;
        }

        /**
         * <p>Schematic user ID (starting with 'user_') of the user evaluated, if any</p>
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
         * <p>Schematic rule ID (starting with 'rule_') of the rule that matched for the flag, if any</p>
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
         * <p>Key-value pairs used to to identify user for which the flag was checked</p>
         * @return Reference to {@code this} so that method calls can be chained together.
         */
        @java.lang.Override
        public _FinalStage reqUser(Map<String, String> reqUser) {
            this.reqUser = Optional.ofNullable(reqUser);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "req_user", nulls = Nulls.SKIP)
        public _FinalStage reqUser(Optional<Map<String, String>> reqUser) {
            this.reqUser = reqUser;
            return this;
        }

        /**
         * <p>Key-value pairs used to to identify company for which the flag was checked</p>
         * @return Reference to {@code this} so that method calls can be chained together.
         */
        @java.lang.Override
        public _FinalStage reqCompany(Map<String, String> reqCompany) {
            this.reqCompany = Optional.ofNullable(reqCompany);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "req_company", nulls = Nulls.SKIP)
        public _FinalStage reqCompany(Optional<Map<String, String>> reqCompany) {
            this.reqCompany = reqCompany;
            return this;
        }

        /**
         * <p>Schematic flag ID (starting with 'flag_') for the flag matching the key, if any</p>
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
         * <p>Report an error that occurred during the flag check</p>
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
         * <p>Schematic company ID (starting with 'comp_') of the company evaluated, if any</p>
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
        public EventBodyFlagCheck build() {
            return new EventBodyFlagCheck(
                    companyId,
                    error,
                    flagId,
                    flagKey,
                    reason,
                    reqCompany,
                    reqUser,
                    ruleId,
                    userId,
                    value,
                    additionalProperties);
        }
    }
}
