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
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.schematic.api.core.ObjectMappers;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;

@JsonInclude(JsonInclude.Include.NON_ABSENT)
@JsonDeserialize(builder = ApiError.Builder.class)
public final class ApiError {
    private final String error;

    private final Map<String, Object> additionalProperties;

    private ApiError(String error, Map<String, Object> additionalProperties) {
        this.error = error;
        this.additionalProperties = additionalProperties;
    }

    /**
     * @return Error message
     */
    @JsonProperty("error")
    public String getError() {
        return error;
    }

    @java.lang.Override
    public boolean equals(Object other) {
        if (this == other) return true;
        return other instanceof ApiError && equalTo((ApiError) other);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    private boolean equalTo(ApiError other) {
        return error.equals(other.error);
    }

    @java.lang.Override
    public int hashCode() {
        return Objects.hash(this.error);
    }

    @java.lang.Override
    public String toString() {
        return ObjectMappers.stringify(this);
    }

    public static ErrorStage builder() {
        return new Builder();
    }

    public interface ErrorStage {
        _FinalStage error(@NotNull String error);

        Builder from(ApiError other);
    }

    public interface _FinalStage {
        ApiError build();
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static final class Builder implements ErrorStage, _FinalStage {
        private String error;

        @JsonAnySetter
        private Map<String, Object> additionalProperties = new HashMap<>();

        private Builder() {}

        @java.lang.Override
        public Builder from(ApiError other) {
            error(other.getError());
            return this;
        }

        /**
         * <p>Error message</p>
         * @return Reference to {@code this} so that method calls can be chained together.
         */
        @java.lang.Override
        @JsonSetter("error")
        public _FinalStage error(@NotNull String error) {
            this.error = Objects.requireNonNull(error, "error must not be null");
            return this;
        }

        @java.lang.Override
        public ApiError build() {
            return new ApiError(error, additionalProperties);
        }
    }
}
