/**
 * This file was auto-generated by Fern from our API Definition.
 */
package com.schematic.api.resources.checkout.requests;

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
@JsonDeserialize(builder = UpdateTrialEndRequestBody.Builder.class)
public final class UpdateTrialEndRequestBody {
    private final Optional<OffsetDateTime> trialEnd;

    private final Map<String, Object> additionalProperties;

    private UpdateTrialEndRequestBody(Optional<OffsetDateTime> trialEnd, Map<String, Object> additionalProperties) {
        this.trialEnd = trialEnd;
        this.additionalProperties = additionalProperties;
    }

    @JsonProperty("trial_end")
    public Optional<OffsetDateTime> getTrialEnd() {
        return trialEnd;
    }

    @java.lang.Override
    public boolean equals(Object other) {
        if (this == other) return true;
        return other instanceof UpdateTrialEndRequestBody && equalTo((UpdateTrialEndRequestBody) other);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    private boolean equalTo(UpdateTrialEndRequestBody other) {
        return trialEnd.equals(other.trialEnd);
    }

    @java.lang.Override
    public int hashCode() {
        return Objects.hash(this.trialEnd);
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
        private Optional<OffsetDateTime> trialEnd = Optional.empty();

        @JsonAnySetter
        private Map<String, Object> additionalProperties = new HashMap<>();

        private Builder() {}

        public Builder from(UpdateTrialEndRequestBody other) {
            trialEnd(other.getTrialEnd());
            return this;
        }

        @JsonSetter(value = "trial_end", nulls = Nulls.SKIP)
        public Builder trialEnd(Optional<OffsetDateTime> trialEnd) {
            this.trialEnd = trialEnd;
            return this;
        }

        public Builder trialEnd(OffsetDateTime trialEnd) {
            this.trialEnd = Optional.ofNullable(trialEnd);
            return this;
        }

        public UpdateTrialEndRequestBody build() {
            return new UpdateTrialEndRequestBody(trialEnd, additionalProperties);
        }
    }
}
