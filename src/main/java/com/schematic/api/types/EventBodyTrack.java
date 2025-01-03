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
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.schematic.api.core.ObjectMappers;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;

@JsonInclude(JsonInclude.Include.NON_ABSENT)
@JsonDeserialize(builder = EventBodyTrack.Builder.class)
public final class EventBodyTrack {
    private final Optional<Map<String, String>> company;

    private final String event;

    private final Optional<Map<String, JsonNode>> traits;

    private final Optional<Map<String, String>> user;

    private final Map<String, Object> additionalProperties;

    private EventBodyTrack(
            Optional<Map<String, String>> company,
            String event,
            Optional<Map<String, JsonNode>> traits,
            Optional<Map<String, String>> user,
            Map<String, Object> additionalProperties) {
        this.company = company;
        this.event = event;
        this.traits = traits;
        this.user = user;
        this.additionalProperties = additionalProperties;
    }

    /**
     * @return Key-value pairs to identify company associated with track event
     */
    @JsonProperty("company")
    public Optional<Map<String, String>> getCompany() {
        return company;
    }

    /**
     * @return The name of the type of track event
     */
    @JsonProperty("event")
    public String getEvent() {
        return event;
    }

    /**
     * @return A map of trait names to trait values
     */
    @JsonProperty("traits")
    public Optional<Map<String, JsonNode>> getTraits() {
        return traits;
    }

    /**
     * @return Key-value pairs to identify user associated with track event
     */
    @JsonProperty("user")
    public Optional<Map<String, String>> getUser() {
        return user;
    }

    @java.lang.Override
    public boolean equals(Object other) {
        if (this == other) return true;
        return other instanceof EventBodyTrack && equalTo((EventBodyTrack) other);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    private boolean equalTo(EventBodyTrack other) {
        return company.equals(other.company)
                && event.equals(other.event)
                && traits.equals(other.traits)
                && user.equals(other.user);
    }

    @java.lang.Override
    public int hashCode() {
        return Objects.hash(this.company, this.event, this.traits, this.user);
    }

    @java.lang.Override
    public String toString() {
        return ObjectMappers.stringify(this);
    }

    public static EventStage builder() {
        return new Builder();
    }

    public interface EventStage {
        _FinalStage event(@NotNull String event);

        Builder from(EventBodyTrack other);
    }

    public interface _FinalStage {
        EventBodyTrack build();

        _FinalStage company(Optional<Map<String, String>> company);

        _FinalStage company(Map<String, String> company);

        _FinalStage traits(Optional<Map<String, JsonNode>> traits);

        _FinalStage traits(Map<String, JsonNode> traits);

        _FinalStage user(Optional<Map<String, String>> user);

        _FinalStage user(Map<String, String> user);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static final class Builder implements EventStage, _FinalStage {
        private String event;

        private Optional<Map<String, String>> user = Optional.empty();

        private Optional<Map<String, JsonNode>> traits = Optional.empty();

        private Optional<Map<String, String>> company = Optional.empty();

        @JsonAnySetter
        private Map<String, Object> additionalProperties = new HashMap<>();

        private Builder() {}

        @java.lang.Override
        public Builder from(EventBodyTrack other) {
            company(other.getCompany());
            event(other.getEvent());
            traits(other.getTraits());
            user(other.getUser());
            return this;
        }

        /**
         * <p>The name of the type of track event</p>
         * @return Reference to {@code this} so that method calls can be chained together.
         */
        @java.lang.Override
        @JsonSetter("event")
        public _FinalStage event(@NotNull String event) {
            this.event = Objects.requireNonNull(event, "event must not be null");
            return this;
        }

        /**
         * <p>Key-value pairs to identify user associated with track event</p>
         * @return Reference to {@code this} so that method calls can be chained together.
         */
        @java.lang.Override
        public _FinalStage user(Map<String, String> user) {
            this.user = Optional.ofNullable(user);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "user", nulls = Nulls.SKIP)
        public _FinalStage user(Optional<Map<String, String>> user) {
            this.user = user;
            return this;
        }

        /**
         * <p>A map of trait names to trait values</p>
         * @return Reference to {@code this} so that method calls can be chained together.
         */
        @java.lang.Override
        public _FinalStage traits(Map<String, JsonNode> traits) {
            this.traits = Optional.ofNullable(traits);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "traits", nulls = Nulls.SKIP)
        public _FinalStage traits(Optional<Map<String, JsonNode>> traits) {
            this.traits = traits;
            return this;
        }

        /**
         * <p>Key-value pairs to identify company associated with track event</p>
         * @return Reference to {@code this} so that method calls can be chained together.
         */
        @java.lang.Override
        public _FinalStage company(Map<String, String> company) {
            this.company = Optional.ofNullable(company);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "company", nulls = Nulls.SKIP)
        public _FinalStage company(Optional<Map<String, String>> company) {
            this.company = company;
            return this;
        }

        @java.lang.Override
        public EventBodyTrack build() {
            return new EventBodyTrack(company, event, traits, user, additionalProperties);
        }
    }
}
