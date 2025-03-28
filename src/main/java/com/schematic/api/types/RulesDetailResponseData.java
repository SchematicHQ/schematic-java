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
@JsonDeserialize(builder = RulesDetailResponseData.Builder.class)
public final class RulesDetailResponseData {
    private final Optional<FlagResponseData> flag;

    private final List<RuleDetailResponseData> rules;

    private final Map<String, Object> additionalProperties;

    private RulesDetailResponseData(
            Optional<FlagResponseData> flag,
            List<RuleDetailResponseData> rules,
            Map<String, Object> additionalProperties) {
        this.flag = flag;
        this.rules = rules;
        this.additionalProperties = additionalProperties;
    }

    @JsonProperty("flag")
    public Optional<FlagResponseData> getFlag() {
        return flag;
    }

    @JsonProperty("rules")
    public List<RuleDetailResponseData> getRules() {
        return rules;
    }

    @java.lang.Override
    public boolean equals(Object other) {
        if (this == other) return true;
        return other instanceof RulesDetailResponseData && equalTo((RulesDetailResponseData) other);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    private boolean equalTo(RulesDetailResponseData other) {
        return flag.equals(other.flag) && rules.equals(other.rules);
    }

    @java.lang.Override
    public int hashCode() {
        return Objects.hash(this.flag, this.rules);
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
        private Optional<FlagResponseData> flag = Optional.empty();

        private List<RuleDetailResponseData> rules = new ArrayList<>();

        @JsonAnySetter
        private Map<String, Object> additionalProperties = new HashMap<>();

        private Builder() {}

        public Builder from(RulesDetailResponseData other) {
            flag(other.getFlag());
            rules(other.getRules());
            return this;
        }

        @JsonSetter(value = "flag", nulls = Nulls.SKIP)
        public Builder flag(Optional<FlagResponseData> flag) {
            this.flag = flag;
            return this;
        }

        public Builder flag(FlagResponseData flag) {
            this.flag = Optional.ofNullable(flag);
            return this;
        }

        @JsonSetter(value = "rules", nulls = Nulls.SKIP)
        public Builder rules(List<RuleDetailResponseData> rules) {
            this.rules.clear();
            this.rules.addAll(rules);
            return this;
        }

        public Builder addRules(RuleDetailResponseData rules) {
            this.rules.add(rules);
            return this;
        }

        public Builder addAllRules(List<RuleDetailResponseData> rules) {
            this.rules.addAll(rules);
            return this;
        }

        public RulesDetailResponseData build() {
            return new RulesDetailResponseData(flag, rules, additionalProperties);
        }
    }
}
