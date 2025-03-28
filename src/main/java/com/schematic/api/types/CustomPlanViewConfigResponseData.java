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
@JsonDeserialize(builder = CustomPlanViewConfigResponseData.Builder.class)
public final class CustomPlanViewConfigResponseData {
    private final String ctaText;

    private final String ctaWebSite;

    private final String priceText;

    private final Map<String, Object> additionalProperties;

    private CustomPlanViewConfigResponseData(
            String ctaText, String ctaWebSite, String priceText, Map<String, Object> additionalProperties) {
        this.ctaText = ctaText;
        this.ctaWebSite = ctaWebSite;
        this.priceText = priceText;
        this.additionalProperties = additionalProperties;
    }

    @JsonProperty("cta_text")
    public String getCtaText() {
        return ctaText;
    }

    @JsonProperty("cta_web_site")
    public String getCtaWebSite() {
        return ctaWebSite;
    }

    @JsonProperty("price_text")
    public String getPriceText() {
        return priceText;
    }

    @java.lang.Override
    public boolean equals(Object other) {
        if (this == other) return true;
        return other instanceof CustomPlanViewConfigResponseData && equalTo((CustomPlanViewConfigResponseData) other);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    private boolean equalTo(CustomPlanViewConfigResponseData other) {
        return ctaText.equals(other.ctaText)
                && ctaWebSite.equals(other.ctaWebSite)
                && priceText.equals(other.priceText);
    }

    @java.lang.Override
    public int hashCode() {
        return Objects.hash(this.ctaText, this.ctaWebSite, this.priceText);
    }

    @java.lang.Override
    public String toString() {
        return ObjectMappers.stringify(this);
    }

    public static CtaTextStage builder() {
        return new Builder();
    }

    public interface CtaTextStage {
        CtaWebSiteStage ctaText(@NotNull String ctaText);

        Builder from(CustomPlanViewConfigResponseData other);
    }

    public interface CtaWebSiteStage {
        PriceTextStage ctaWebSite(@NotNull String ctaWebSite);
    }

    public interface PriceTextStage {
        _FinalStage priceText(@NotNull String priceText);
    }

    public interface _FinalStage {
        CustomPlanViewConfigResponseData build();
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static final class Builder implements CtaTextStage, CtaWebSiteStage, PriceTextStage, _FinalStage {
        private String ctaText;

        private String ctaWebSite;

        private String priceText;

        @JsonAnySetter
        private Map<String, Object> additionalProperties = new HashMap<>();

        private Builder() {}

        @java.lang.Override
        public Builder from(CustomPlanViewConfigResponseData other) {
            ctaText(other.getCtaText());
            ctaWebSite(other.getCtaWebSite());
            priceText(other.getPriceText());
            return this;
        }

        @java.lang.Override
        @JsonSetter("cta_text")
        public CtaWebSiteStage ctaText(@NotNull String ctaText) {
            this.ctaText = Objects.requireNonNull(ctaText, "ctaText must not be null");
            return this;
        }

        @java.lang.Override
        @JsonSetter("cta_web_site")
        public PriceTextStage ctaWebSite(@NotNull String ctaWebSite) {
            this.ctaWebSite = Objects.requireNonNull(ctaWebSite, "ctaWebSite must not be null");
            return this;
        }

        @java.lang.Override
        @JsonSetter("price_text")
        public _FinalStage priceText(@NotNull String priceText) {
            this.priceText = Objects.requireNonNull(priceText, "priceText must not be null");
            return this;
        }

        @java.lang.Override
        public CustomPlanViewConfigResponseData build() {
            return new CustomPlanViewConfigResponseData(ctaText, ctaWebSite, priceText, additionalProperties);
        }
    }
}
