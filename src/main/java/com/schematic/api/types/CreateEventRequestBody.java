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

@JsonInclude(JsonInclude.Include.NON_ABSENT)
@JsonDeserialize(builder = CreateEventRequestBody.Builder.class)
public final class CreateEventRequestBody {
    private final Optional<EventBody> body;

    private final CreateEventRequestBodyEventType eventType;

    private final Optional<OffsetDateTime> sentAt;

    private final Map<String, Object> additionalProperties;

    private CreateEventRequestBody(
            Optional<EventBody> body,
            CreateEventRequestBodyEventType eventType,
            Optional<OffsetDateTime> sentAt,
            Map<String, Object> additionalProperties) {
        this.body = body;
        this.eventType = eventType;
        this.sentAt = sentAt;
        this.additionalProperties = additionalProperties;
    }

    @JsonProperty("body")
    public Optional<EventBody> getBody() {
        return body;
    }

    /**
     * @return Either 'identify' or 'track'
     */
    @JsonProperty("event_type")
    public CreateEventRequestBodyEventType getEventType() {
        return eventType;
    }

    /**
     * @return Optionally provide a timestamp at which the event was sent to Schematic
     */
    @JsonProperty("sent_at")
    public Optional<OffsetDateTime> getSentAt() {
        return sentAt;
    }

    @java.lang.Override
    public boolean equals(Object other) {
        if (this == other) return true;
        return other instanceof CreateEventRequestBody && equalTo((CreateEventRequestBody) other);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    private boolean equalTo(CreateEventRequestBody other) {
        return body.equals(other.body) && eventType.equals(other.eventType) && sentAt.equals(other.sentAt);
    }

    @java.lang.Override
    public int hashCode() {
        return Objects.hash(this.body, this.eventType, this.sentAt);
    }

    @java.lang.Override
    public String toString() {
        return ObjectMappers.stringify(this);
    }

    public static EventTypeStage builder() {
        return new Builder();
    }

    public interface EventTypeStage {
        _FinalStage eventType(CreateEventRequestBodyEventType eventType);

        Builder from(CreateEventRequestBody other);
    }

    public interface _FinalStage {
        CreateEventRequestBody build();

        _FinalStage body(Optional<EventBody> body);

        _FinalStage body(EventBody body);

        _FinalStage sentAt(Optional<OffsetDateTime> sentAt);

        _FinalStage sentAt(OffsetDateTime sentAt);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static final class Builder implements EventTypeStage, _FinalStage {
        private CreateEventRequestBodyEventType eventType;

        private Optional<OffsetDateTime> sentAt = Optional.empty();

        private Optional<EventBody> body = Optional.empty();

        @JsonAnySetter
        private Map<String, Object> additionalProperties = new HashMap<>();

        private Builder() {}

        @java.lang.Override
        public Builder from(CreateEventRequestBody other) {
            body(other.getBody());
            eventType(other.getEventType());
            sentAt(other.getSentAt());
            return this;
        }

        /**
         * <p>Either 'identify' or 'track'</p>
         * @return Reference to {@code this} so that method calls can be chained together.
         */
        @java.lang.Override
        @JsonSetter("event_type")
        public _FinalStage eventType(CreateEventRequestBodyEventType eventType) {
            this.eventType = eventType;
            return this;
        }

        /**
         * <p>Optionally provide a timestamp at which the event was sent to Schematic</p>
         * @return Reference to {@code this} so that method calls can be chained together.
         */
        @java.lang.Override
        public _FinalStage sentAt(OffsetDateTime sentAt) {
            this.sentAt = Optional.ofNullable(sentAt);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "sent_at", nulls = Nulls.SKIP)
        public _FinalStage sentAt(Optional<OffsetDateTime> sentAt) {
            this.sentAt = sentAt;
            return this;
        }

        @java.lang.Override
        public _FinalStage body(EventBody body) {
            this.body = Optional.ofNullable(body);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "body", nulls = Nulls.SKIP)
        public _FinalStage body(Optional<EventBody> body) {
            this.body = body;
            return this;
        }

        @java.lang.Override
        public CreateEventRequestBody build() {
            return new CreateEventRequestBody(body, eventType, sentAt, additionalProperties);
        }
    }
}
