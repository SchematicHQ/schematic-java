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
@JsonDeserialize(builder = RawEventBatchResponseData.Builder.class)
public final class RawEventBatchResponseData {
    private final List<RawEventResponseData> events;

    private final Map<String, Object> additionalProperties;

    private RawEventBatchResponseData(List<RawEventResponseData> events, Map<String, Object> additionalProperties) {
        this.events = events;
        this.additionalProperties = additionalProperties;
    }

    @JsonProperty("events")
    public List<RawEventResponseData> getEvents() {
        return events;
    }

    @java.lang.Override
    public boolean equals(Object other) {
        if (this == other) return true;
        return other instanceof RawEventBatchResponseData && equalTo((RawEventBatchResponseData) other);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    private boolean equalTo(RawEventBatchResponseData other) {
        return events.equals(other.events);
    }

    @java.lang.Override
    public int hashCode() {
        return Objects.hash(this.events);
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
        private List<RawEventResponseData> events = new ArrayList<>();

        @JsonAnySetter
        private Map<String, Object> additionalProperties = new HashMap<>();

        private Builder() {}

        public Builder from(RawEventBatchResponseData other) {
            events(other.getEvents());
            return this;
        }

        @JsonSetter(value = "events", nulls = Nulls.SKIP)
        public Builder events(List<RawEventResponseData> events) {
            this.events.clear();
            this.events.addAll(events);
            return this;
        }

        public Builder addEvents(RawEventResponseData events) {
            this.events.add(events);
            return this;
        }

        public Builder addAllEvents(List<RawEventResponseData> events) {
            this.events.addAll(events);
            return this;
        }

        public RawEventBatchResponseData build() {
            return new RawEventBatchResponseData(events, additionalProperties);
        }
    }
}
