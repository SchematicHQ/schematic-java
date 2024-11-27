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
import com.schematic.api.resources.features.types.CreateFeatureRequestBodyFeatureType;
import com.schematic.api.types.CreateOrUpdateFlagRequestBody;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonDeserialize(builder = CreateFeatureRequestBody.Builder.class)
public final class CreateFeatureRequestBody {
    private final String description;

    private final Optional<String> eventSubtype;

    private final CreateFeatureRequestBodyFeatureType featureType;

    private final Optional<CreateOrUpdateFlagRequestBody> flag;

    private final Optional<String> icon;

    private final Optional<String> lifecyclePhase;

    private final Optional<String> maintainerId;

    private final String name;

    private final Optional<String> traitId;

    private final Map<String, Object> additionalProperties;

    private CreateFeatureRequestBody(
            String description,
            Optional<String> eventSubtype,
            CreateFeatureRequestBodyFeatureType featureType,
            Optional<CreateOrUpdateFlagRequestBody> flag,
            Optional<String> icon,
            Optional<String> lifecyclePhase,
            Optional<String> maintainerId,
            String name,
            Optional<String> traitId,
            Map<String, Object> additionalProperties) {
        this.description = description;
        this.eventSubtype = eventSubtype;
        this.featureType = featureType;
        this.flag = flag;
        this.icon = icon;
        this.lifecyclePhase = lifecyclePhase;
        this.maintainerId = maintainerId;
        this.name = name;
        this.traitId = traitId;
        this.additionalProperties = additionalProperties;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("event_subtype")
    public Optional<String> getEventSubtype() {
        return eventSubtype;
    }

    @JsonProperty("feature_type")
    public CreateFeatureRequestBodyFeatureType getFeatureType() {
        return featureType;
    }

    @JsonProperty("flag")
    public Optional<CreateOrUpdateFlagRequestBody> getFlag() {
        return flag;
    }

    @JsonProperty("icon")
    public Optional<String> getIcon() {
        return icon;
    }

    @JsonProperty("lifecycle_phase")
    public Optional<String> getLifecyclePhase() {
        return lifecyclePhase;
    }

    @JsonProperty("maintainer_id")
    public Optional<String> getMaintainerId() {
        return maintainerId;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("trait_id")
    public Optional<String> getTraitId() {
        return traitId;
    }

    @java.lang.Override
    public boolean equals(Object other) {
        if (this == other) return true;
        return other instanceof CreateFeatureRequestBody && equalTo((CreateFeatureRequestBody) other);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    private boolean equalTo(CreateFeatureRequestBody other) {
        return description.equals(other.description)
                && eventSubtype.equals(other.eventSubtype)
                && featureType.equals(other.featureType)
                && flag.equals(other.flag)
                && icon.equals(other.icon)
                && lifecyclePhase.equals(other.lifecyclePhase)
                && maintainerId.equals(other.maintainerId)
                && name.equals(other.name)
                && traitId.equals(other.traitId);
    }

    @java.lang.Override
    public int hashCode() {
        return Objects.hash(
                this.description,
                this.eventSubtype,
                this.featureType,
                this.flag,
                this.icon,
                this.lifecyclePhase,
                this.maintainerId,
                this.name,
                this.traitId);
    }

    @java.lang.Override
    public String toString() {
        return ObjectMappers.stringify(this);
    }

    public static DescriptionStage builder() {
        return new Builder();
    }

    public interface DescriptionStage {
        FeatureTypeStage description(String description);

        Builder from(CreateFeatureRequestBody other);
    }

    public interface FeatureTypeStage {
        NameStage featureType(CreateFeatureRequestBodyFeatureType featureType);
    }

    public interface NameStage {
        _FinalStage name(String name);
    }

    public interface _FinalStage {
        CreateFeatureRequestBody build();

        _FinalStage eventSubtype(Optional<String> eventSubtype);

        _FinalStage eventSubtype(String eventSubtype);

        _FinalStage flag(Optional<CreateOrUpdateFlagRequestBody> flag);

        _FinalStage flag(CreateOrUpdateFlagRequestBody flag);

        _FinalStage icon(Optional<String> icon);

        _FinalStage icon(String icon);

        _FinalStage lifecyclePhase(Optional<String> lifecyclePhase);

        _FinalStage lifecyclePhase(String lifecyclePhase);

        _FinalStage maintainerId(Optional<String> maintainerId);

        _FinalStage maintainerId(String maintainerId);

        _FinalStage traitId(Optional<String> traitId);

        _FinalStage traitId(String traitId);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static final class Builder implements DescriptionStage, FeatureTypeStage, NameStage, _FinalStage {
        private String description;

        private CreateFeatureRequestBodyFeatureType featureType;

        private String name;

        private Optional<String> traitId = Optional.empty();

        private Optional<String> maintainerId = Optional.empty();

        private Optional<String> lifecyclePhase = Optional.empty();

        private Optional<String> icon = Optional.empty();

        private Optional<CreateOrUpdateFlagRequestBody> flag = Optional.empty();

        private Optional<String> eventSubtype = Optional.empty();

        @JsonAnySetter
        private Map<String, Object> additionalProperties = new HashMap<>();

        private Builder() {}

        @java.lang.Override
        public Builder from(CreateFeatureRequestBody other) {
            description(other.getDescription());
            eventSubtype(other.getEventSubtype());
            featureType(other.getFeatureType());
            flag(other.getFlag());
            icon(other.getIcon());
            lifecyclePhase(other.getLifecyclePhase());
            maintainerId(other.getMaintainerId());
            name(other.getName());
            traitId(other.getTraitId());
            return this;
        }

        @java.lang.Override
        @JsonSetter("description")
        public FeatureTypeStage description(String description) {
            this.description = description;
            return this;
        }

        @java.lang.Override
        @JsonSetter("feature_type")
        public NameStage featureType(CreateFeatureRequestBodyFeatureType featureType) {
            this.featureType = featureType;
            return this;
        }

        @java.lang.Override
        @JsonSetter("name")
        public _FinalStage name(String name) {
            this.name = name;
            return this;
        }

        @java.lang.Override
        public _FinalStage traitId(String traitId) {
            this.traitId = Optional.of(traitId);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "trait_id", nulls = Nulls.SKIP)
        public _FinalStage traitId(Optional<String> traitId) {
            this.traitId = traitId;
            return this;
        }

        @java.lang.Override
        public _FinalStage maintainerId(String maintainerId) {
            this.maintainerId = Optional.of(maintainerId);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "maintainer_id", nulls = Nulls.SKIP)
        public _FinalStage maintainerId(Optional<String> maintainerId) {
            this.maintainerId = maintainerId;
            return this;
        }

        @java.lang.Override
        public _FinalStage lifecyclePhase(String lifecyclePhase) {
            this.lifecyclePhase = Optional.of(lifecyclePhase);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "lifecycle_phase", nulls = Nulls.SKIP)
        public _FinalStage lifecyclePhase(Optional<String> lifecyclePhase) {
            this.lifecyclePhase = lifecyclePhase;
            return this;
        }

        @java.lang.Override
        public _FinalStage icon(String icon) {
            this.icon = Optional.of(icon);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "icon", nulls = Nulls.SKIP)
        public _FinalStage icon(Optional<String> icon) {
            this.icon = icon;
            return this;
        }

        @java.lang.Override
        public _FinalStage flag(CreateOrUpdateFlagRequestBody flag) {
            this.flag = Optional.of(flag);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "flag", nulls = Nulls.SKIP)
        public _FinalStage flag(Optional<CreateOrUpdateFlagRequestBody> flag) {
            this.flag = flag;
            return this;
        }

        @java.lang.Override
        public _FinalStage eventSubtype(String eventSubtype) {
            this.eventSubtype = Optional.of(eventSubtype);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "event_subtype", nulls = Nulls.SKIP)
        public _FinalStage eventSubtype(Optional<String> eventSubtype) {
            this.eventSubtype = eventSubtype;
            return this;
        }

        @java.lang.Override
        public CreateFeatureRequestBody build() {
            return new CreateFeatureRequestBody(
                    description,
                    eventSubtype,
                    featureType,
                    flag,
                    icon,
                    lifecyclePhase,
                    maintainerId,
                    name,
                    traitId,
                    additionalProperties);
        }
    }
}
