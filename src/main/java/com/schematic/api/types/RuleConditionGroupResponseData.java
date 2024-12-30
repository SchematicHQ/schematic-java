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
@JsonDeserialize(builder = RuleConditionGroupResponseData.Builder.class)
public final class RuleConditionGroupResponseData {
    private final OffsetDateTime createdAt;

    private final String environmentId;

    private final Optional<String> flagId;

    private final String id;

    private final Optional<String> planId;

    private final String ruleId;

    private final OffsetDateTime updatedAt;

    private final Map<String, Object> additionalProperties;

    private RuleConditionGroupResponseData(
            OffsetDateTime createdAt,
            String environmentId,
            Optional<String> flagId,
            String id,
            Optional<String> planId,
            String ruleId,
            OffsetDateTime updatedAt,
            Map<String, Object> additionalProperties) {
        this.createdAt = createdAt;
        this.environmentId = environmentId;
        this.flagId = flagId;
        this.id = id;
        this.planId = planId;
        this.ruleId = ruleId;
        this.updatedAt = updatedAt;
        this.additionalProperties = additionalProperties;
    }

    @JsonProperty("created_at")
    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    @JsonProperty("environment_id")
    public String getEnvironmentId() {
        return environmentId;
    }

    @JsonProperty("flag_id")
    public Optional<String> getFlagId() {
        return flagId;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("plan_id")
    public Optional<String> getPlanId() {
        return planId;
    }

    @JsonProperty("rule_id")
    public String getRuleId() {
        return ruleId;
    }

    @JsonProperty("updated_at")
    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    @java.lang.Override
    public boolean equals(Object other) {
        if (this == other) return true;
        return other instanceof RuleConditionGroupResponseData && equalTo((RuleConditionGroupResponseData) other);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    private boolean equalTo(RuleConditionGroupResponseData other) {
        return createdAt.equals(other.createdAt)
                && environmentId.equals(other.environmentId)
                && flagId.equals(other.flagId)
                && id.equals(other.id)
                && planId.equals(other.planId)
                && ruleId.equals(other.ruleId)
                && updatedAt.equals(other.updatedAt);
    }

    @java.lang.Override
    public int hashCode() {
        return Objects.hash(
                this.createdAt, this.environmentId, this.flagId, this.id, this.planId, this.ruleId, this.updatedAt);
    }

    @java.lang.Override
    public String toString() {
        return ObjectMappers.stringify(this);
    }

    public static CreatedAtStage builder() {
        return new Builder();
    }

    public interface CreatedAtStage {
        EnvironmentIdStage createdAt(OffsetDateTime createdAt);

        Builder from(RuleConditionGroupResponseData other);
    }

    public interface EnvironmentIdStage {
        IdStage environmentId(String environmentId);
    }

    public interface IdStage {
        RuleIdStage id(String id);
    }

    public interface RuleIdStage {
        UpdatedAtStage ruleId(String ruleId);
    }

    public interface UpdatedAtStage {
        _FinalStage updatedAt(OffsetDateTime updatedAt);
    }

    public interface _FinalStage {
        RuleConditionGroupResponseData build();

        _FinalStage flagId(Optional<String> flagId);

        _FinalStage flagId(String flagId);

        _FinalStage planId(Optional<String> planId);

        _FinalStage planId(String planId);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static final class Builder
            implements CreatedAtStage, EnvironmentIdStage, IdStage, RuleIdStage, UpdatedAtStage, _FinalStage {
        private OffsetDateTime createdAt;

        private String environmentId;

        private String id;

        private String ruleId;

        private OffsetDateTime updatedAt;

        private Optional<String> planId = Optional.empty();

        private Optional<String> flagId = Optional.empty();

        @JsonAnySetter
        private Map<String, Object> additionalProperties = new HashMap<>();

        private Builder() {}

        @java.lang.Override
        public Builder from(RuleConditionGroupResponseData other) {
            createdAt(other.getCreatedAt());
            environmentId(other.getEnvironmentId());
            flagId(other.getFlagId());
            id(other.getId());
            planId(other.getPlanId());
            ruleId(other.getRuleId());
            updatedAt(other.getUpdatedAt());
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
        public IdStage environmentId(String environmentId) {
            this.environmentId = environmentId;
            return this;
        }

        @java.lang.Override
        @JsonSetter("id")
        public RuleIdStage id(String id) {
            this.id = id;
            return this;
        }

        @java.lang.Override
        @JsonSetter("rule_id")
        public UpdatedAtStage ruleId(String ruleId) {
            this.ruleId = ruleId;
            return this;
        }

        @java.lang.Override
        @JsonSetter("updated_at")
        public _FinalStage updatedAt(OffsetDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        @java.lang.Override
        public _FinalStage planId(String planId) {
            this.planId = Optional.ofNullable(planId);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "plan_id", nulls = Nulls.SKIP)
        public _FinalStage planId(Optional<String> planId) {
            this.planId = planId;
            return this;
        }

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

        @java.lang.Override
        public RuleConditionGroupResponseData build() {
            return new RuleConditionGroupResponseData(
                    createdAt, environmentId, flagId, id, planId, ruleId, updatedAt, additionalProperties);
        }
    }
}
