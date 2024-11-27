/**
 * This file was auto-generated by Fern from our API Definition.
 */
package com.schematic.api.resources.features.requests;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.schematic.api.core.ObjectMappers;
import com.schematic.api.types.CreateOrUpdateRuleRequestBody;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonDeserialize(builder = UpdateFlagRulesRequestBody.Builder.class)
public final class UpdateFlagRulesRequestBody {
    private final List<CreateOrUpdateRuleRequestBody> rules;

    private final Map<String, Object> additionalProperties;

    private UpdateFlagRulesRequestBody(
            List<CreateOrUpdateRuleRequestBody> rules, Map<String, Object> additionalProperties) {
        this.rules = rules;
        this.additionalProperties = additionalProperties;
    }

    @JsonProperty("rules")
    public List<CreateOrUpdateRuleRequestBody> getRules() {
        return rules;
    }

    @java.lang.Override
    public boolean equals(Object other) {
        if (this == other) return true;
        return other instanceof UpdateFlagRulesRequestBody && equalTo((UpdateFlagRulesRequestBody) other);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    private boolean equalTo(UpdateFlagRulesRequestBody other) {
        return rules.equals(other.rules);
    }

    @java.lang.Override
    public int hashCode() {
        return Objects.hash(this.rules);
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
        private List<CreateOrUpdateRuleRequestBody> rules = new ArrayList<>();

        @JsonAnySetter
        private Map<String, Object> additionalProperties = new HashMap<>();

        private Builder() {}

        public Builder from(UpdateFlagRulesRequestBody other) {
            rules(other.getRules());
            return this;
        }

        @JsonSetter(value = "rules", nulls = Nulls.SKIP)
        public Builder rules(List<CreateOrUpdateRuleRequestBody> rules) {
            this.rules.clear();
            this.rules.addAll(rules);
            return this;
        }

        public Builder addRules(CreateOrUpdateRuleRequestBody rules) {
            this.rules.add(rules);
            return this;
        }

        public Builder addAllRules(List<CreateOrUpdateRuleRequestBody> rules) {
            this.rules.addAll(rules);
            return this;
        }

        public UpdateFlagRulesRequestBody build() {
            return new UpdateFlagRulesRequestBody(rules, additionalProperties);
        }
    }
}
