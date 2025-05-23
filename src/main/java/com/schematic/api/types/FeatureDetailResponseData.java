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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;

@JsonInclude(JsonInclude.Include.NON_ABSENT)
@JsonDeserialize(builder = FeatureDetailResponseData.Builder.class)
public final class FeatureDetailResponseData {
    private final OffsetDateTime createdAt;

    private final String description;

    private final Optional<String> eventSubtype;

    private final Optional<EventSummaryResponseData> eventSummary;

    private final String featureType;

    private final List<FlagDetailResponseData> flags;

    private final String icon;

    private final String id;

    private final Optional<String> lifecyclePhase;

    private final Optional<String> maintainerId;

    private final String name;

    private final List<PreviewObject> plans;

    private final Optional<String> pluralName;

    private final Optional<String> singularName;

    private final Optional<EntityTraitDefinitionResponseData> trait;

    private final Optional<String> traitId;

    private final OffsetDateTime updatedAt;

    private final Map<String, Object> additionalProperties;

    private FeatureDetailResponseData(
            OffsetDateTime createdAt,
            String description,
            Optional<String> eventSubtype,
            Optional<EventSummaryResponseData> eventSummary,
            String featureType,
            List<FlagDetailResponseData> flags,
            String icon,
            String id,
            Optional<String> lifecyclePhase,
            Optional<String> maintainerId,
            String name,
            List<PreviewObject> plans,
            Optional<String> pluralName,
            Optional<String> singularName,
            Optional<EntityTraitDefinitionResponseData> trait,
            Optional<String> traitId,
            OffsetDateTime updatedAt,
            Map<String, Object> additionalProperties) {
        this.createdAt = createdAt;
        this.description = description;
        this.eventSubtype = eventSubtype;
        this.eventSummary = eventSummary;
        this.featureType = featureType;
        this.flags = flags;
        this.icon = icon;
        this.id = id;
        this.lifecyclePhase = lifecyclePhase;
        this.maintainerId = maintainerId;
        this.name = name;
        this.plans = plans;
        this.pluralName = pluralName;
        this.singularName = singularName;
        this.trait = trait;
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

    @JsonProperty("event_summary")
    public Optional<EventSummaryResponseData> getEventSummary() {
        return eventSummary;
    }

    @JsonProperty("feature_type")
    public String getFeatureType() {
        return featureType;
    }

    @JsonProperty("flags")
    public List<FlagDetailResponseData> getFlags() {
        return flags;
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

    @JsonProperty("plans")
    public List<PreviewObject> getPlans() {
        return plans;
    }

    @JsonProperty("plural_name")
    public Optional<String> getPluralName() {
        return pluralName;
    }

    @JsonProperty("singular_name")
    public Optional<String> getSingularName() {
        return singularName;
    }

    @JsonProperty("trait")
    public Optional<EntityTraitDefinitionResponseData> getTrait() {
        return trait;
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
        return other instanceof FeatureDetailResponseData && equalTo((FeatureDetailResponseData) other);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    private boolean equalTo(FeatureDetailResponseData other) {
        return createdAt.equals(other.createdAt)
                && description.equals(other.description)
                && eventSubtype.equals(other.eventSubtype)
                && eventSummary.equals(other.eventSummary)
                && featureType.equals(other.featureType)
                && flags.equals(other.flags)
                && icon.equals(other.icon)
                && id.equals(other.id)
                && lifecyclePhase.equals(other.lifecyclePhase)
                && maintainerId.equals(other.maintainerId)
                && name.equals(other.name)
                && plans.equals(other.plans)
                && pluralName.equals(other.pluralName)
                && singularName.equals(other.singularName)
                && trait.equals(other.trait)
                && traitId.equals(other.traitId)
                && updatedAt.equals(other.updatedAt);
    }

    @java.lang.Override
    public int hashCode() {
        return Objects.hash(
                this.createdAt,
                this.description,
                this.eventSubtype,
                this.eventSummary,
                this.featureType,
                this.flags,
                this.icon,
                this.id,
                this.lifecyclePhase,
                this.maintainerId,
                this.name,
                this.plans,
                this.pluralName,
                this.singularName,
                this.trait,
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

        Builder from(FeatureDetailResponseData other);
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
        FeatureDetailResponseData build();

        _FinalStage eventSubtype(Optional<String> eventSubtype);

        _FinalStage eventSubtype(String eventSubtype);

        _FinalStage eventSummary(Optional<EventSummaryResponseData> eventSummary);

        _FinalStage eventSummary(EventSummaryResponseData eventSummary);

        _FinalStage flags(List<FlagDetailResponseData> flags);

        _FinalStage addFlags(FlagDetailResponseData flags);

        _FinalStage addAllFlags(List<FlagDetailResponseData> flags);

        _FinalStage lifecyclePhase(Optional<String> lifecyclePhase);

        _FinalStage lifecyclePhase(String lifecyclePhase);

        _FinalStage maintainerId(Optional<String> maintainerId);

        _FinalStage maintainerId(String maintainerId);

        _FinalStage plans(List<PreviewObject> plans);

        _FinalStage addPlans(PreviewObject plans);

        _FinalStage addAllPlans(List<PreviewObject> plans);

        _FinalStage pluralName(Optional<String> pluralName);

        _FinalStage pluralName(String pluralName);

        _FinalStage singularName(Optional<String> singularName);

        _FinalStage singularName(String singularName);

        _FinalStage trait(Optional<EntityTraitDefinitionResponseData> trait);

        _FinalStage trait(EntityTraitDefinitionResponseData trait);

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

        private Optional<EntityTraitDefinitionResponseData> trait = Optional.empty();

        private Optional<String> singularName = Optional.empty();

        private Optional<String> pluralName = Optional.empty();

        private List<PreviewObject> plans = new ArrayList<>();

        private Optional<String> maintainerId = Optional.empty();

        private Optional<String> lifecyclePhase = Optional.empty();

        private List<FlagDetailResponseData> flags = new ArrayList<>();

        private Optional<EventSummaryResponseData> eventSummary = Optional.empty();

        private Optional<String> eventSubtype = Optional.empty();

        @JsonAnySetter
        private Map<String, Object> additionalProperties = new HashMap<>();

        private Builder() {}

        @java.lang.Override
        public Builder from(FeatureDetailResponseData other) {
            createdAt(other.getCreatedAt());
            description(other.getDescription());
            eventSubtype(other.getEventSubtype());
            eventSummary(other.getEventSummary());
            featureType(other.getFeatureType());
            flags(other.getFlags());
            icon(other.getIcon());
            id(other.getId());
            lifecyclePhase(other.getLifecyclePhase());
            maintainerId(other.getMaintainerId());
            name(other.getName());
            plans(other.getPlans());
            pluralName(other.getPluralName());
            singularName(other.getSingularName());
            trait(other.getTrait());
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
        public _FinalStage trait(EntityTraitDefinitionResponseData trait) {
            this.trait = Optional.ofNullable(trait);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "trait", nulls = Nulls.SKIP)
        public _FinalStage trait(Optional<EntityTraitDefinitionResponseData> trait) {
            this.trait = trait;
            return this;
        }

        @java.lang.Override
        public _FinalStage singularName(String singularName) {
            this.singularName = Optional.ofNullable(singularName);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "singular_name", nulls = Nulls.SKIP)
        public _FinalStage singularName(Optional<String> singularName) {
            this.singularName = singularName;
            return this;
        }

        @java.lang.Override
        public _FinalStage pluralName(String pluralName) {
            this.pluralName = Optional.ofNullable(pluralName);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "plural_name", nulls = Nulls.SKIP)
        public _FinalStage pluralName(Optional<String> pluralName) {
            this.pluralName = pluralName;
            return this;
        }

        @java.lang.Override
        public _FinalStage addAllPlans(List<PreviewObject> plans) {
            this.plans.addAll(plans);
            return this;
        }

        @java.lang.Override
        public _FinalStage addPlans(PreviewObject plans) {
            this.plans.add(plans);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "plans", nulls = Nulls.SKIP)
        public _FinalStage plans(List<PreviewObject> plans) {
            this.plans.clear();
            this.plans.addAll(plans);
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
        public _FinalStage addAllFlags(List<FlagDetailResponseData> flags) {
            this.flags.addAll(flags);
            return this;
        }

        @java.lang.Override
        public _FinalStage addFlags(FlagDetailResponseData flags) {
            this.flags.add(flags);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "flags", nulls = Nulls.SKIP)
        public _FinalStage flags(List<FlagDetailResponseData> flags) {
            this.flags.clear();
            this.flags.addAll(flags);
            return this;
        }

        @java.lang.Override
        public _FinalStage eventSummary(EventSummaryResponseData eventSummary) {
            this.eventSummary = Optional.ofNullable(eventSummary);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "event_summary", nulls = Nulls.SKIP)
        public _FinalStage eventSummary(Optional<EventSummaryResponseData> eventSummary) {
            this.eventSummary = eventSummary;
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
        public FeatureDetailResponseData build() {
            return new FeatureDetailResponseData(
                    createdAt,
                    description,
                    eventSubtype,
                    eventSummary,
                    featureType,
                    flags,
                    icon,
                    id,
                    lifecyclePhase,
                    maintainerId,
                    name,
                    plans,
                    pluralName,
                    singularName,
                    trait,
                    traitId,
                    updatedAt,
                    additionalProperties);
        }
    }
}
