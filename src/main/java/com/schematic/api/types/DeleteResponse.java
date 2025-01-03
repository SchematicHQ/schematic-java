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
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@JsonInclude(JsonInclude.Include.NON_ABSENT)
@JsonDeserialize(builder = DeleteResponse.Builder.class)
public final class DeleteResponse {
    private final Optional<Boolean> deleted;

    private final Map<String, Object> additionalProperties;

    private DeleteResponse(Optional<Boolean> deleted, Map<String, Object> additionalProperties) {
        this.deleted = deleted;
        this.additionalProperties = additionalProperties;
    }

    /**
     * @return Whether the delete was successful
     */
    @JsonProperty("deleted")
    public Optional<Boolean> getDeleted() {
        return deleted;
    }

    @java.lang.Override
    public boolean equals(Object other) {
        if (this == other) return true;
        return other instanceof DeleteResponse && equalTo((DeleteResponse) other);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    private boolean equalTo(DeleteResponse other) {
        return deleted.equals(other.deleted);
    }

    @java.lang.Override
    public int hashCode() {
        return Objects.hash(this.deleted);
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
        private Optional<Boolean> deleted = Optional.empty();

        @JsonAnySetter
        private Map<String, Object> additionalProperties = new HashMap<>();

        private Builder() {}

        public Builder from(DeleteResponse other) {
            deleted(other.getDeleted());
            return this;
        }

        @JsonSetter(value = "deleted", nulls = Nulls.SKIP)
        public Builder deleted(Optional<Boolean> deleted) {
            this.deleted = deleted;
            return this;
        }

        public Builder deleted(Boolean deleted) {
            this.deleted = Optional.ofNullable(deleted);
            return this;
        }

        public DeleteResponse build() {
            return new DeleteResponse(deleted, additionalProperties);
        }
    }
}
