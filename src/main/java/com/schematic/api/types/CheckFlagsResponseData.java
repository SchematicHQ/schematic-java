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

@JsonInclude(JsonInclude.Include.NON_ABSENT)
@JsonDeserialize(builder = CheckFlagsResponseData.Builder.class)
public final class CheckFlagsResponseData {
    private final List<CheckFlagResponseData> flags;

    private final Map<String, Object> additionalProperties;

    private CheckFlagsResponseData(List<CheckFlagResponseData> flags, Map<String, Object> additionalProperties) {
        this.flags = flags;
        this.additionalProperties = additionalProperties;
    }

    @JsonProperty("flags")
    public List<CheckFlagResponseData> getFlags() {
        return flags;
    }

    @java.lang.Override
    public boolean equals(Object other) {
        if (this == other) return true;
        return other instanceof CheckFlagsResponseData && equalTo((CheckFlagsResponseData) other);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    private boolean equalTo(CheckFlagsResponseData other) {
        return flags.equals(other.flags);
    }

    @java.lang.Override
    public int hashCode() {
        return Objects.hash(this.flags);
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
        private List<CheckFlagResponseData> flags = new ArrayList<>();

        @JsonAnySetter
        private Map<String, Object> additionalProperties = new HashMap<>();

        private Builder() {}

        public Builder from(CheckFlagsResponseData other) {
            flags(other.getFlags());
            return this;
        }

        @JsonSetter(value = "flags", nulls = Nulls.SKIP)
        public Builder flags(List<CheckFlagResponseData> flags) {
            this.flags.clear();
            this.flags.addAll(flags);
            return this;
        }

        public Builder addFlags(CheckFlagResponseData flags) {
            this.flags.add(flags);
            return this;
        }

        public Builder addAllFlags(List<CheckFlagResponseData> flags) {
            this.flags.addAll(flags);
            return this;
        }

        public CheckFlagsResponseData build() {
            return new CheckFlagsResponseData(flags, additionalProperties);
        }
    }
}
