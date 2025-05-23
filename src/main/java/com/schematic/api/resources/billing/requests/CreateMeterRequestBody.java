/**
 * This file was auto-generated by Fern from our API Definition.
 */
package com.schematic.api.resources.billing.requests;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.schematic.api.core.ObjectMappers;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;

@JsonInclude(JsonInclude.Include.NON_ABSENT)
@JsonDeserialize(builder = CreateMeterRequestBody.Builder.class)
public final class CreateMeterRequestBody {
    private final String displayName;

    private final String eventName;

    private final String eventPayloadKey;

    private final String externalId;

    private final Map<String, Object> additionalProperties;

    private CreateMeterRequestBody(
            String displayName,
            String eventName,
            String eventPayloadKey,
            String externalId,
            Map<String, Object> additionalProperties) {
        this.displayName = displayName;
        this.eventName = eventName;
        this.eventPayloadKey = eventPayloadKey;
        this.externalId = externalId;
        this.additionalProperties = additionalProperties;
    }

    @JsonProperty("display_name")
    public String getDisplayName() {
        return displayName;
    }

    @JsonProperty("event_name")
    public String getEventName() {
        return eventName;
    }

    @JsonProperty("event_payload_key")
    public String getEventPayloadKey() {
        return eventPayloadKey;
    }

    @JsonProperty("external_id")
    public String getExternalId() {
        return externalId;
    }

    @java.lang.Override
    public boolean equals(Object other) {
        if (this == other) return true;
        return other instanceof CreateMeterRequestBody && equalTo((CreateMeterRequestBody) other);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    private boolean equalTo(CreateMeterRequestBody other) {
        return displayName.equals(other.displayName)
                && eventName.equals(other.eventName)
                && eventPayloadKey.equals(other.eventPayloadKey)
                && externalId.equals(other.externalId);
    }

    @java.lang.Override
    public int hashCode() {
        return Objects.hash(this.displayName, this.eventName, this.eventPayloadKey, this.externalId);
    }

    @java.lang.Override
    public String toString() {
        return ObjectMappers.stringify(this);
    }

    public static DisplayNameStage builder() {
        return new Builder();
    }

    public interface DisplayNameStage {
        EventNameStage displayName(@NotNull String displayName);

        Builder from(CreateMeterRequestBody other);
    }

    public interface EventNameStage {
        EventPayloadKeyStage eventName(@NotNull String eventName);
    }

    public interface EventPayloadKeyStage {
        ExternalIdStage eventPayloadKey(@NotNull String eventPayloadKey);
    }

    public interface ExternalIdStage {
        _FinalStage externalId(@NotNull String externalId);
    }

    public interface _FinalStage {
        CreateMeterRequestBody build();
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static final class Builder
            implements DisplayNameStage, EventNameStage, EventPayloadKeyStage, ExternalIdStage, _FinalStage {
        private String displayName;

        private String eventName;

        private String eventPayloadKey;

        private String externalId;

        @JsonAnySetter
        private Map<String, Object> additionalProperties = new HashMap<>();

        private Builder() {}

        @java.lang.Override
        public Builder from(CreateMeterRequestBody other) {
            displayName(other.getDisplayName());
            eventName(other.getEventName());
            eventPayloadKey(other.getEventPayloadKey());
            externalId(other.getExternalId());
            return this;
        }

        @java.lang.Override
        @JsonSetter("display_name")
        public EventNameStage displayName(@NotNull String displayName) {
            this.displayName = Objects.requireNonNull(displayName, "displayName must not be null");
            return this;
        }

        @java.lang.Override
        @JsonSetter("event_name")
        public EventPayloadKeyStage eventName(@NotNull String eventName) {
            this.eventName = Objects.requireNonNull(eventName, "eventName must not be null");
            return this;
        }

        @java.lang.Override
        @JsonSetter("event_payload_key")
        public ExternalIdStage eventPayloadKey(@NotNull String eventPayloadKey) {
            this.eventPayloadKey = Objects.requireNonNull(eventPayloadKey, "eventPayloadKey must not be null");
            return this;
        }

        @java.lang.Override
        @JsonSetter("external_id")
        public _FinalStage externalId(@NotNull String externalId) {
            this.externalId = Objects.requireNonNull(externalId, "externalId must not be null");
            return this;
        }

        @java.lang.Override
        public CreateMeterRequestBody build() {
            return new CreateMeterRequestBody(
                    displayName, eventName, eventPayloadKey, externalId, additionalProperties);
        }
    }
}
