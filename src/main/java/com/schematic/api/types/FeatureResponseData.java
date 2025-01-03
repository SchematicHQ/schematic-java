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
@JsonDeserialize(builder = FeatureResponseData.Builder.class)
public final class FeatureResponseData {
    private final OffsetDateTime createdAt;

    private final String description;

    private final Optional<String> eventSubtype;

    private final String featureType;

    private final String icon;

    private final String id;

    private final Optional<String> lifecyclePhase;

    private final Optional<String> maintainerId;

    private final String name;

    private final Optional<String> traitId;

    private final OffsetDateTime updatedAt;

    private final Map<String, Object> additionalProperties;

    private FeatureResponseData(
            OffsetDateTime createdAt,
            String description,
            Optional<String> eventSubtype,
            String featureType,
            String icon,
            String id,
            Optional<String> lifecyclePhase,
            Optional<String> maintainerId,
            String name,
            Optional<String> traitId,
            OffsetDateTime updatedAt,
            Map<String, Object> additionalProperties) {
        this.createdAt = createdAt;
        this.description = description;
        this.eventSubtype = eventSubtype;
        this.featureType = featureType;
        this.icon = icon;
        this.id = id;
        this.lifecyclePhase = lifecyclePhase;
        this.maintainerId = maintainerId;
        this.name = name;
        this.traitId = traitId;
        this.updatedAt = updatedAt;
        this.additionalProperties = additionalProperties;
    }

    @JsonProperty("created_at")
    public OffsetDateTime getCreatedAt() {
        return createdAt;
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
    public String getFeatureType() {
        return featureType;
    }

    @JsonProperty("icon")
    public String getIcon() {
        return icon;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
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

    @JsonProperty("updated_at")
    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    @java.lang.Override
    public boolean equals(Object other) {
        if (this == other) return true;
        return other instanceof FeatureResponseData && equalTo((FeatureResponseData) other);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    private boolean equalTo(FeatureResponseData other) {
        return createdAt.equals(other.createdAt)
                && description.equals(other.description)
                && eventSubtype.equals(other.eventSubtype)
                && featureType.equals(other.featureType)
                && icon.equals(other.icon)
                && id.equals(other.id)
                && lifecyclePhase.equals(other.lifecyclePhase)
                && maintainerId.equals(other.maintainerId)
                && name.equals(other.name)
                && traitId.equals(other.traitId)
                && updatedAt.equals(other.updatedAt);
    }

    @java.lang.Override
    public int hashCode() {
        return Objects.hash(
                this.createdAt,
                this.description,
                this.eventSubtype,
                this.featureType,
                this.icon,
                this.id,
                this.lifecyclePhase,
                this.maintainerId,
                this.name,
                this.traitId,
                this.updatedAt);
    }

    @java.lang.Override
    public String toString() {
        return ObjectMappers.stringify(this);
    }

    public static CreatedAtStage builder() {
        return new Builder();
    }

    public interface CreatedAtStage {
        DescriptionStage createdAt(@NotNull OffsetDateTime createdAt);

        Builder from(FeatureResponseData other);
    }

    public interface DescriptionStage {
        FeatureTypeStage description(@NotNull String description);
    }

    public interface FeatureTypeStage {
        IconStage featureType(@NotNull String featureType);
    }

    public interface IconStage {
        IdStage icon(@NotNull String icon);
    }

    public interface IdStage {
        NameStage id(@NotNull String id);
    }

    public interface NameStage {
        UpdatedAtStage name(@NotNull String name);
    }

    public interface UpdatedAtStage {
        _FinalStage updatedAt(@NotNull OffsetDateTime updatedAt);
    }

    public interface _FinalStage {
        FeatureResponseData build();

        _FinalStage eventSubtype(Optional<String> eventSubtype);

        _FinalStage eventSubtype(String eventSubtype);

        _FinalStage lifecyclePhase(Optional<String> lifecyclePhase);

        _FinalStage lifecyclePhase(String lifecyclePhase);

        _FinalStage maintainerId(Optional<String> maintainerId);

        _FinalStage maintainerId(String maintainerId);

        _FinalStage traitId(Optional<String> traitId);

        _FinalStage traitId(String traitId);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static final class Builder
            implements CreatedAtStage,
                    DescriptionStage,
                    FeatureTypeStage,
                    IconStage,
                    IdStage,
                    NameStage,
                    UpdatedAtStage,
                    _FinalStage {
        private OffsetDateTime createdAt;

        private String description;

        private String featureType;

        private String icon;

        private String id;

        private String name;

        private OffsetDateTime updatedAt;

        private Optional<String> traitId = Optional.empty();

        private Optional<String> maintainerId = Optional.empty();

        private Optional<String> lifecyclePhase = Optional.empty();

        private Optional<String> eventSubtype = Optional.empty();

        @JsonAnySetter
        private Map<String, Object> additionalProperties = new HashMap<>();

        private Builder() {}

        @java.lang.Override
        public Builder from(FeatureResponseData other) {
            createdAt(other.getCreatedAt());
            description(other.getDescription());
            eventSubtype(other.getEventSubtype());
            featureType(other.getFeatureType());
            icon(other.getIcon());
            id(other.getId());
            lifecyclePhase(other.getLifecyclePhase());
            maintainerId(other.getMaintainerId());
            name(other.getName());
            traitId(other.getTraitId());
            updatedAt(other.getUpdatedAt());
            return this;
        }

        @java.lang.Override
        @JsonSetter("created_at")
        public DescriptionStage createdAt(@NotNull OffsetDateTime createdAt) {
            this.createdAt = Objects.requireNonNull(createdAt, "createdAt must not be null");
            return this;
        }

        @java.lang.Override
        @JsonSetter("description")
        public FeatureTypeStage description(@NotNull String description) {
            this.description = Objects.requireNonNull(description, "description must not be null");
            return this;
        }

        @java.lang.Override
        @JsonSetter("feature_type")
        public IconStage featureType(@NotNull String featureType) {
            this.featureType = Objects.requireNonNull(featureType, "featureType must not be null");
            return this;
        }

        @java.lang.Override
        @JsonSetter("icon")
        public IdStage icon(@NotNull String icon) {
            this.icon = Objects.requireNonNull(icon, "icon must not be null");
            return this;
        }

        @java.lang.Override
        @JsonSetter("id")
        public NameStage id(@NotNull String id) {
            this.id = Objects.requireNonNull(id, "id must not be null");
            return this;
        }

        @java.lang.Override
        @JsonSetter("name")
        public UpdatedAtStage name(@NotNull String name) {
            this.name = Objects.requireNonNull(name, "name must not be null");
            return this;
        }

        @java.lang.Override
        @JsonSetter("updated_at")
        public _FinalStage updatedAt(@NotNull OffsetDateTime updatedAt) {
            this.updatedAt = Objects.requireNonNull(updatedAt, "updatedAt must not be null");
            return this;
        }

        @java.lang.Override
        public _FinalStage traitId(String traitId) {
            this.traitId = Optional.ofNullable(traitId);
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
            this.maintainerId = Optional.ofNullable(maintainerId);
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
            this.lifecyclePhase = Optional.ofNullable(lifecyclePhase);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "lifecycle_phase", nulls = Nulls.SKIP)
        public _FinalStage lifecyclePhase(Optional<String> lifecyclePhase) {
            this.lifecyclePhase = lifecyclePhase;
            return this;
        }

        @java.lang.Override
        public _FinalStage eventSubtype(String eventSubtype) {
            this.eventSubtype = Optional.ofNullable(eventSubtype);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "event_subtype", nulls = Nulls.SKIP)
        public _FinalStage eventSubtype(Optional<String> eventSubtype) {
            this.eventSubtype = eventSubtype;
            return this;
        }

        @java.lang.Override
        public FeatureResponseData build() {
            return new FeatureResponseData(
                    createdAt,
                    description,
                    eventSubtype,
                    featureType,
                    icon,
                    id,
                    lifecyclePhase,
                    maintainerId,
                    name,
                    traitId,
                    updatedAt,
                    additionalProperties);
        }
    }
}
