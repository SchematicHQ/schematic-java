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
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@JsonInclude(JsonInclude.Include.NON_ABSENT)
@JsonDeserialize(builder = UpdatePlanRequestBody.Builder.class)
public final class UpdatePlanRequestBody {
    private final Optional<String> description;

    private final Optional<String> icon;

    private final String name;

    private final Map<String, Object> additionalProperties;

    private UpdatePlanRequestBody(
            Optional<String> description,
            Optional<String> icon,
            String name,
            Map<String, Object> additionalProperties) {
        this.description = description;
        this.icon = icon;
        this.name = name;
        this.additionalProperties = additionalProperties;
    }

    @JsonProperty("description")
    public Optional<String> getDescription() {
        return description;
    }

    @JsonProperty("icon")
    public Optional<String> getIcon() {
        return icon;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @java.lang.Override
    public boolean equals(Object other) {
        if (this == other) return true;
        return other instanceof UpdatePlanRequestBody && equalTo((UpdatePlanRequestBody) other);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    private boolean equalTo(UpdatePlanRequestBody other) {
        return description.equals(other.description) && icon.equals(other.icon) && name.equals(other.name);
    }

    @java.lang.Override
    public int hashCode() {
        return Objects.hash(this.description, this.icon, this.name);
    }

    @java.lang.Override
    public String toString() {
        return ObjectMappers.stringify(this);
    }

    public static NameStage builder() {
        return new Builder();
    }

    public interface NameStage {
        _FinalStage name(String name);

        Builder from(UpdatePlanRequestBody other);
    }

    public interface _FinalStage {
        UpdatePlanRequestBody build();

        _FinalStage description(Optional<String> description);

        _FinalStage description(String description);

        _FinalStage icon(Optional<String> icon);

        _FinalStage icon(String icon);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static final class Builder implements NameStage, _FinalStage {
        private String name;

        private Optional<String> icon = Optional.empty();

        private Optional<String> description = Optional.empty();

        @JsonAnySetter
        private Map<String, Object> additionalProperties = new HashMap<>();

        private Builder() {}

        @java.lang.Override
        public Builder from(UpdatePlanRequestBody other) {
            description(other.getDescription());
            icon(other.getIcon());
            name(other.getName());
            return this;
        }

        @java.lang.Override
        @JsonSetter("name")
        public _FinalStage name(String name) {
            this.name = name;
            return this;
        }

        @java.lang.Override
        public _FinalStage icon(String icon) {
            this.icon = Optional.ofNullable(icon);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "icon", nulls = Nulls.SKIP)
        public _FinalStage icon(Optional<String> icon) {
            this.icon = icon;
            return this;
        }

        @java.lang.Override
        public _FinalStage description(String description) {
            this.description = Optional.ofNullable(description);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "description", nulls = Nulls.SKIP)
        public _FinalStage description(Optional<String> description) {
            this.description = description;
            return this;
        }

        @java.lang.Override
        public UpdatePlanRequestBody build() {
            return new UpdatePlanRequestBody(description, icon, name, additionalProperties);
        }
    }
}
