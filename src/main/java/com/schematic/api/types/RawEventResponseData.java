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
@JsonDeserialize(builder = RawEventResponseData.Builder.class)
public final class RawEventResponseData {
    private final OffsetDateTime capturedAt;

    private final Optional<String> eventId;

    private final String remoteAddr;

    private final String remoteIp;

    private final String userAgent;

    private final Map<String, Object> additionalProperties;

    private RawEventResponseData(
            OffsetDateTime capturedAt,
            Optional<String> eventId,
            String remoteAddr,
            String remoteIp,
            String userAgent,
            Map<String, Object> additionalProperties) {
        this.capturedAt = capturedAt;
        this.eventId = eventId;
        this.remoteAddr = remoteAddr;
        this.remoteIp = remoteIp;
        this.userAgent = userAgent;
        this.additionalProperties = additionalProperties;
    }

    @JsonProperty("captured_at")
    public OffsetDateTime getCapturedAt() {
        return capturedAt;
    }

    @JsonProperty("event_id")
    public Optional<String> getEventId() {
        return eventId;
    }

    @JsonProperty("remote_addr")
    public String getRemoteAddr() {
        return remoteAddr;
    }

    @JsonProperty("remote_ip")
    public String getRemoteIp() {
        return remoteIp;
    }

    @JsonProperty("user_agent")
    public String getUserAgent() {
        return userAgent;
    }

    @java.lang.Override
    public boolean equals(Object other) {
        if (this == other) return true;
        return other instanceof RawEventResponseData && equalTo((RawEventResponseData) other);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    private boolean equalTo(RawEventResponseData other) {
        return capturedAt.equals(other.capturedAt)
                && eventId.equals(other.eventId)
                && remoteAddr.equals(other.remoteAddr)
                && remoteIp.equals(other.remoteIp)
                && userAgent.equals(other.userAgent);
    }

    @java.lang.Override
    public int hashCode() {
        return Objects.hash(this.capturedAt, this.eventId, this.remoteAddr, this.remoteIp, this.userAgent);
    }

    @java.lang.Override
    public String toString() {
        return ObjectMappers.stringify(this);
    }

    public static CapturedAtStage builder() {
        return new Builder();
    }

    public interface CapturedAtStage {
        RemoteAddrStage capturedAt(@NotNull OffsetDateTime capturedAt);

        Builder from(RawEventResponseData other);
    }

    public interface RemoteAddrStage {
        RemoteIpStage remoteAddr(@NotNull String remoteAddr);
    }

    public interface RemoteIpStage {
        UserAgentStage remoteIp(@NotNull String remoteIp);
    }

    public interface UserAgentStage {
        _FinalStage userAgent(@NotNull String userAgent);
    }

    public interface _FinalStage {
        RawEventResponseData build();

        _FinalStage eventId(Optional<String> eventId);

        _FinalStage eventId(String eventId);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static final class Builder
            implements CapturedAtStage, RemoteAddrStage, RemoteIpStage, UserAgentStage, _FinalStage {
        private OffsetDateTime capturedAt;

        private String remoteAddr;

        private String remoteIp;

        private String userAgent;

        private Optional<String> eventId = Optional.empty();

        @JsonAnySetter
        private Map<String, Object> additionalProperties = new HashMap<>();

        private Builder() {}

        @java.lang.Override
        public Builder from(RawEventResponseData other) {
            capturedAt(other.getCapturedAt());
            eventId(other.getEventId());
            remoteAddr(other.getRemoteAddr());
            remoteIp(other.getRemoteIp());
            userAgent(other.getUserAgent());
            return this;
        }

        @java.lang.Override
        @JsonSetter("captured_at")
        public RemoteAddrStage capturedAt(@NotNull OffsetDateTime capturedAt) {
            this.capturedAt = Objects.requireNonNull(capturedAt, "capturedAt must not be null");
            return this;
        }

        @java.lang.Override
        @JsonSetter("remote_addr")
        public RemoteIpStage remoteAddr(@NotNull String remoteAddr) {
            this.remoteAddr = Objects.requireNonNull(remoteAddr, "remoteAddr must not be null");
            return this;
        }

        @java.lang.Override
        @JsonSetter("remote_ip")
        public UserAgentStage remoteIp(@NotNull String remoteIp) {
            this.remoteIp = Objects.requireNonNull(remoteIp, "remoteIp must not be null");
            return this;
        }

        @java.lang.Override
        @JsonSetter("user_agent")
        public _FinalStage userAgent(@NotNull String userAgent) {
            this.userAgent = Objects.requireNonNull(userAgent, "userAgent must not be null");
            return this;
        }

        @java.lang.Override
        public _FinalStage eventId(String eventId) {
            this.eventId = Optional.ofNullable(eventId);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "event_id", nulls = Nulls.SKIP)
        public _FinalStage eventId(Optional<String> eventId) {
            this.eventId = eventId;
            return this;
        }

        @java.lang.Override
        public RawEventResponseData build() {
            return new RawEventResponseData(capturedAt, eventId, remoteAddr, remoteIp, userAgent, additionalProperties);
        }
    }
}
