/**
 * This file was auto-generated by Fern from our API Definition.
 */
package com.schematic.api.resources.plans.requests;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.schematic.api.core.ObjectMappers;
import com.schematic.api.types.CreateOrUpdateConditionGroupRequestBody;
import com.schematic.api.types.CreateOrUpdateConditionRequestBody;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_ABSENT)
@JsonDeserialize(builder = UpdateAudienceRequestBody.Builder.class)
public final class UpdateAudienceRequestBody {
    private final List<CreateOrUpdateConditionGroupRequestBody> conditionGroups;

    private final List<CreateOrUpdateConditionRequestBody> conditions;

    private final Map<String, Object> additionalProperties;

    private UpdateAudienceRequestBody(
            List<CreateOrUpdateConditionGroupRequestBody> conditionGroups,
            List<CreateOrUpdateConditionRequestBody> conditions,
            Map<String, Object> additionalProperties) {
        this.conditionGroups = conditionGroups;
        this.conditions = conditions;
        this.additionalProperties = additionalProperties;
    }

    @JsonProperty("condition_groups")
    public List<CreateOrUpdateConditionGroupRequestBody> getConditionGroups() {
        return conditionGroups;
    }

    @JsonProperty("conditions")
    public List<CreateOrUpdateConditionRequestBody> getConditions() {
        return conditions;
    }

    @java.lang.Override
    public boolean equals(Object other) {
        if (this == other) return true;
        return other instanceof UpdateAudienceRequestBody && equalTo((UpdateAudienceRequestBody) other);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    private boolean equalTo(UpdateAudienceRequestBody other) {
        return conditionGroups.equals(other.conditionGroups) && conditions.equals(other.conditions);
    }

    @java.lang.Override
    public int hashCode() {
        return Objects.hash(this.conditionGroups, this.conditions);
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
        private List<CreateOrUpdateConditionGroupRequestBody> conditionGroups = new ArrayList<>();

        private List<CreateOrUpdateConditionRequestBody> conditions = new ArrayList<>();

        @JsonAnySetter
        private Map<String, Object> additionalProperties = new HashMap<>();

        private Builder() {}

        public Builder from(UpdateAudienceRequestBody other) {
            conditionGroups(other.getConditionGroups());
            conditions(other.getConditions());
            return this;
        }

        @JsonSetter(value = "condition_groups", nulls = Nulls.SKIP)
        public Builder conditionGroups(List<CreateOrUpdateConditionGroupRequestBody> conditionGroups) {
            this.conditionGroups.clear();
            this.conditionGroups.addAll(conditionGroups);
            return this;
        }

        public Builder addConditionGroups(CreateOrUpdateConditionGroupRequestBody conditionGroups) {
            this.conditionGroups.add(conditionGroups);
            return this;
        }

        public Builder addAllConditionGroups(List<CreateOrUpdateConditionGroupRequestBody> conditionGroups) {
            this.conditionGroups.addAll(conditionGroups);
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

        public UpdateAudienceRequestBody build() {
            return new UpdateAudienceRequestBody(conditionGroups, conditions, additionalProperties);
        }
    }
}
