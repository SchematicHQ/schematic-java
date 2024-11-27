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

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonDeserialize(builder = PreviewObject.Builder.class)
public final class PreviewObject {
    private final Optional<String> description;

    private final String id;

    private final Optional<String> imageUrl;

    private final String name;

    private final Map<String, Object> additionalProperties;

    private PreviewObject(
            Optional<String> description,
            String id,
            Optional<String> imageUrl,
            String name,
            Map<String, Object> additionalProperties) {
        this.description = description;
        this.id = id;
        this.imageUrl = imageUrl;
        this.name = name;
        this.additionalProperties = additionalProperties;
    }

    @JsonProperty("description")
    public Optional<String> getDescription() {
        return description;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("image_url")
    public Optional<String> getImageUrl() {
        return imageUrl;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @java.lang.Override
    public boolean equals(Object other) {
        if (this == other) return true;
        return other instanceof PreviewObject && equalTo((PreviewObject) other);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    private boolean equalTo(PreviewObject other) {
        return description.equals(other.description)
                && id.equals(other.id)
                && imageUrl.equals(other.imageUrl)
                && name.equals(other.name);
    }

    @java.lang.Override
    public int hashCode() {
        return Objects.hash(this.description, this.id, this.imageUrl, this.name);
    }

    @java.lang.Override
    public String toString() {
        return ObjectMappers.stringify(this);
    }

    public static IdStage builder() {
        return new Builder();
    }

    public interface IdStage {
        NameStage id(String id);

        Builder from(PreviewObject other);
    }

    public interface NameStage {
        _FinalStage name(String name);
    }

    public interface _FinalStage {
        PreviewObject build();

        _FinalStage description(Optional<String> description);

        _FinalStage description(String description);

        _FinalStage imageUrl(Optional<String> imageUrl);

        _FinalStage imageUrl(String imageUrl);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static final class Builder implements IdStage, NameStage, _FinalStage {
        private String id;

        private String name;

        private Optional<String> imageUrl = Optional.empty();

        private Optional<String> description = Optional.empty();

        @JsonAnySetter
        private Map<String, Object> additionalProperties = new HashMap<>();

        private Builder() {}

        @java.lang.Override
        public Builder from(PreviewObject other) {
            description(other.getDescription());
            id(other.getId());
            imageUrl(other.getImageUrl());
            name(other.getName());
            return this;
        }

        @java.lang.Override
        @JsonSetter("id")
        public NameStage id(String id) {
            this.id = id;
            return this;
        }

        @java.lang.Override
        @JsonSetter("name")
        public _FinalStage name(String name) {
            this.name = name;
            return this;
        }

        @java.lang.Override
        public _FinalStage imageUrl(String imageUrl) {
            this.imageUrl = Optional.of(imageUrl);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "image_url", nulls = Nulls.SKIP)
        public _FinalStage imageUrl(Optional<String> imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        @java.lang.Override
        public _FinalStage description(String description) {
            this.description = Optional.of(description);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "description", nulls = Nulls.SKIP)
        public _FinalStage description(Optional<String> description) {
            this.description = description;
            return this;
        }

        @java.lang.Override
        public PreviewObject build() {
            return new PreviewObject(description, id, imageUrl, name, additionalProperties);
        }
    }
}
