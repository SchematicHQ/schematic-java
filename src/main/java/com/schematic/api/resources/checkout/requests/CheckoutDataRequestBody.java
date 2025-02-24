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
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;

@JsonInclude(JsonInclude.Include.NON_ABSENT)
@JsonDeserialize(builder = CheckoutDataRequestBody.Builder.class)
public final class CheckoutDataRequestBody {
    private final String companyId;

    private final Optional<String> selectedPlanId;

    private final Map<String, Object> additionalProperties;

    private CheckoutDataRequestBody(
            String companyId, Optional<String> selectedPlanId, Map<String, Object> additionalProperties) {
        this.companyId = companyId;
        this.selectedPlanId = selectedPlanId;
        this.additionalProperties = additionalProperties;
    }

    @JsonProperty("company_id")
    public String getCompanyId() {
        return companyId;
    }

    @JsonProperty("selected_plan_id")
    public Optional<String> getSelectedPlanId() {
        return selectedPlanId;
    }

    @java.lang.Override
    public boolean equals(Object other) {
        if (this == other) return true;
        return other instanceof CheckoutDataRequestBody && equalTo((CheckoutDataRequestBody) other);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    private boolean equalTo(CheckoutDataRequestBody other) {
        return companyId.equals(other.companyId) && selectedPlanId.equals(other.selectedPlanId);
    }

    @java.lang.Override
    public int hashCode() {
        return Objects.hash(this.companyId, this.selectedPlanId);
    }

    @java.lang.Override
    public String toString() {
        return ObjectMappers.stringify(this);
    }

    public static CompanyIdStage builder() {
        return new Builder();
    }

    public interface CompanyIdStage {
        _FinalStage companyId(@NotNull String companyId);

        Builder from(CheckoutDataRequestBody other);
    }

    public interface _FinalStage {
        CheckoutDataRequestBody build();

        _FinalStage selectedPlanId(Optional<String> selectedPlanId);

        _FinalStage selectedPlanId(String selectedPlanId);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static final class Builder implements CompanyIdStage, _FinalStage {
        private String companyId;

        private Optional<String> selectedPlanId = Optional.empty();

        @JsonAnySetter
        private Map<String, Object> additionalProperties = new HashMap<>();

        private Builder() {}

        @java.lang.Override
        public Builder from(CheckoutDataRequestBody other) {
            companyId(other.getCompanyId());
            selectedPlanId(other.getSelectedPlanId());
            return this;
        }

        @java.lang.Override
        @JsonSetter("company_id")
        public _FinalStage companyId(@NotNull String companyId) {
            this.companyId = Objects.requireNonNull(companyId, "companyId must not be null");
            return this;
        }

        @java.lang.Override
        public _FinalStage selectedPlanId(String selectedPlanId) {
            this.selectedPlanId = Optional.ofNullable(selectedPlanId);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "selected_plan_id", nulls = Nulls.SKIP)
        public _FinalStage selectedPlanId(Optional<String> selectedPlanId) {
            this.selectedPlanId = selectedPlanId;
            return this;
        }

        @java.lang.Override
        public CheckoutDataRequestBody build() {
            return new CheckoutDataRequestBody(companyId, selectedPlanId, additionalProperties);
        }
    }
}
