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

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonDeserialize(builder = CreateOrUpdateConditionGroupRequestBody.Builder.class)
public final class CreateOrUpdateConditionGroupRequestBody {
    private final List<CreateOrUpdateConditionRequestBody> conditions;

    private final Optional<String> flagId;

    private final Optional<String> id;

    private final Optional<String> planId;

    private final Map<String, Object> additionalProperties;

    private CreateOrUpdateConditionGroupRequestBody(
            List<CreateOrUpdateConditionRequestBody> conditions,
            Optional<String> flagId,
            Optional<String> id,
            Optional<String> planId,
            Map<String, Object> additionalProperties) {
        this.conditions = conditions;
        this.flagId = flagId;
        this.id = id;
        this.planId = planId;
        this.additionalProperties = additionalProperties;
    }

    @JsonProperty("conditions")
    public List<CreateOrUpdateConditionRequestBody> getConditions() {
        return conditions;
    }

    @JsonProperty("flag_id")
    public Optional<String> getFlagId() {
        return flagId;
    }

    @JsonProperty("id")
    public Optional<String> getId() {
        return id;
    }

    @JsonProperty("plan_id")
    public Optional<String> getPlanId() {
        return planId;
    }

    @java.lang.Override
    public boolean equals(Object other) {
        if (this == other) return true;
        return other instanceof CreateOrUpdateConditionGroupRequestBody
                && equalTo((CreateOrUpdateConditionGroupRequestBody) other);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    private boolean equalTo(CreateOrUpdateConditionGroupRequestBody other) {
        return conditions.equals(other.conditions)
                && flagId.equals(other.flagId)
                && id.equals(other.id)
                && planId.equals(other.planId);
    }

    @java.lang.Override
    public int hashCode() {
        return Objects.hash(this.conditions, this.flagId, this.id, this.planId);
    }

    @java.lang.Override
    public String toString() {
        return ObjectMappers.stringify(this);
    }

    public static Builder builder() {
        return new Builder();
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static final class Builder {
        private List<CreateOrUpdateConditionRequestBody> conditions = new ArrayList<>();

        private Optional<String> flagId = Optional.empty();

        private Optional<String> id = Optional.empty();

        private Optional<String> planId = Optional.empty();

        @JsonAnySetter
        private Map<String, Object> additionalProperties = new HashMap<>();

        private Builder() {}

        public Builder from(CreateOrUpdateConditionGroupRequestBody other) {
            conditions(other.getConditions());
            flagId(other.getFlagId());
            id(other.getId());
            planId(other.getPlanId());
            return this;
        }

        @JsonSetter(value = "conditions", nulls = Nulls.SKIP)
        public Builder conditions(List<CreateOrUpdateConditionRequestBody> conditions) {
            this.conditions.clear();
            this.conditions.addAll(conditions);
            return this;
        }

        public Builder addConditions(CreateOrUpdateConditionRequestBody conditions) {
            this.conditions.add(conditions);
            return this;
        }

        public Builder addAllConditions(List<CreateOrUpdateConditionRequestBody> conditions) {
            this.conditions.addAll(conditions);
            return this;
        }

        @JsonSetter(value = "flag_id", nulls = Nulls.SKIP)
        public Builder flagId(Optional<String> flagId) {
            this.flagId = flagId;
            return this;
        }

        public Builder flagId(String flagId) {
            this.flagId = Optional.of(flagId);
            return this;
        }

        @JsonSetter(value = "id", nulls = Nulls.SKIP)
        public Builder id(Optional<String> id) {
            this.id = id;
            return this;
        }

        public Builder id(String id) {
            this.id = Optional.of(id);
            return this;
        }

        @JsonSetter(value = "plan_id", nulls = Nulls.SKIP)
        public Builder planId(Optional<String> planId) {
            this.planId = planId;
            return this;
        }

        public Builder planId(String planId) {
            this.planId = Optional.of(planId);
            return this;
        }

        public CreateOrUpdateConditionGroupRequestBody build() {
            return new CreateOrUpdateConditionGroupRequestBody(conditions, flagId, id, planId, additionalProperties);
        }
    }
}
