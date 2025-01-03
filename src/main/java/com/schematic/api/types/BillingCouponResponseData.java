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
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;

@JsonInclude(JsonInclude.Include.NON_ABSENT)
@JsonDeserialize(builder = BillingCouponResponseData.Builder.class)
public final class BillingCouponResponseData {
    private final String accountId;

    private final Optional<Integer> amountOff;

    private final Optional<String> currency;

    private final Optional<String> duration;

    private final Optional<Integer> durationInMonths;

    private final String environmentId;

    private final String externalId;

    private final String id;

    private final boolean isActive;

    private final Optional<Integer> maxRedemptions;

    private final Map<String, JsonNode> metadata;

    private final String name;

    private final Optional<Integer> percentOff;

    private final int timesRedeemed;

    private final Optional<OffsetDateTime> validFrom;

    private final Optional<OffsetDateTime> validUntil;

    private final Map<String, Object> additionalProperties;

    private BillingCouponResponseData(
            String accountId,
            Optional<Integer> amountOff,
            Optional<String> currency,
            Optional<String> duration,
            Optional<Integer> durationInMonths,
            String environmentId,
            String externalId,
            String id,
            boolean isActive,
            Optional<Integer> maxRedemptions,
            Map<String, JsonNode> metadata,
            String name,
            Optional<Integer> percentOff,
            int timesRedeemed,
            Optional<OffsetDateTime> validFrom,
            Optional<OffsetDateTime> validUntil,
            Map<String, Object> additionalProperties) {
        this.accountId = accountId;
        this.amountOff = amountOff;
        this.currency = currency;
        this.duration = duration;
        this.durationInMonths = durationInMonths;
        this.environmentId = environmentId;
        this.externalId = externalId;
        this.id = id;
        this.isActive = isActive;
        this.maxRedemptions = maxRedemptions;
        this.metadata = metadata;
        this.name = name;
        this.percentOff = percentOff;
        this.timesRedeemed = timesRedeemed;
        this.validFrom = validFrom;
        this.validUntil = validUntil;
        this.additionalProperties = additionalProperties;
    }

    @JsonProperty("account_id")
    public String getAccountId() {
        return accountId;
    }

    @JsonProperty("amount_off")
    public Optional<Integer> getAmountOff() {
        return amountOff;
    }

    @JsonProperty("currency")
    public Optional<String> getCurrency() {
        return currency;
    }

    @JsonProperty("duration")
    public Optional<String> getDuration() {
        return duration;
    }

    @JsonProperty("duration_in_months")
    public Optional<Integer> getDurationInMonths() {
        return durationInMonths;
    }

    @JsonProperty("environment_id")
    public String getEnvironmentId() {
        return environmentId;
    }

    @JsonProperty("external_id")
    public String getExternalId() {
        return externalId;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("is_active")
    public boolean getIsActive() {
        return isActive;
    }

    @JsonProperty("max_redemptions")
    public Optional<Integer> getMaxRedemptions() {
        return maxRedemptions;
    }

    @JsonProperty("metadata")
    public Map<String, JsonNode> getMetadata() {
        return metadata;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("percent_off")
    public Optional<Integer> getPercentOff() {
        return percentOff;
    }

    @JsonProperty("times_redeemed")
    public int getTimesRedeemed() {
        return timesRedeemed;
    }

    @JsonProperty("valid_from")
    public Optional<OffsetDateTime> getValidFrom() {
        return validFrom;
    }

    @JsonProperty("valid_until")
    public Optional<OffsetDateTime> getValidUntil() {
        return validUntil;
    }

    @java.lang.Override
    public boolean equals(Object other) {
        if (this == other) return true;
        return other instanceof BillingCouponResponseData && equalTo((BillingCouponResponseData) other);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    private boolean equalTo(BillingCouponResponseData other) {
        return accountId.equals(other.accountId)
                && amountOff.equals(other.amountOff)
                && currency.equals(other.currency)
                && duration.equals(other.duration)
                && durationInMonths.equals(other.durationInMonths)
                && environmentId.equals(other.environmentId)
                && externalId.equals(other.externalId)
                && id.equals(other.id)
                && isActive == other.isActive
                && maxRedemptions.equals(other.maxRedemptions)
                && metadata.equals(other.metadata)
                && name.equals(other.name)
                && percentOff.equals(other.percentOff)
                && timesRedeemed == other.timesRedeemed
                && validFrom.equals(other.validFrom)
                && validUntil.equals(other.validUntil);
    }

    @java.lang.Override
    public int hashCode() {
        return Objects.hash(
                this.accountId,
                this.amountOff,
                this.currency,
                this.duration,
                this.durationInMonths,
                this.environmentId,
                this.externalId,
                this.id,
                this.isActive,
                this.maxRedemptions,
                this.metadata,
                this.name,
                this.percentOff,
                this.timesRedeemed,
                this.validFrom,
                this.validUntil);
    }

    @java.lang.Override
    public String toString() {
        return ObjectMappers.stringify(this);
    }

    public static AccountIdStage builder() {
        return new Builder();
    }

    public interface AccountIdStage {
        EnvironmentIdStage accountId(@NotNull String accountId);

        Builder from(BillingCouponResponseData other);
    }

    public interface EnvironmentIdStage {
        ExternalIdStage environmentId(@NotNull String environmentId);
    }

    public interface ExternalIdStage {
        IdStage externalId(@NotNull String externalId);
    }

    public interface IdStage {
        IsActiveStage id(@NotNull String id);
    }

    public interface IsActiveStage {
        NameStage isActive(boolean isActive);
    }

    public interface NameStage {
        TimesRedeemedStage name(@NotNull String name);
    }

    public interface TimesRedeemedStage {
        _FinalStage timesRedeemed(int timesRedeemed);
    }

    public interface _FinalStage {
        BillingCouponResponseData build();

        _FinalStage amountOff(Optional<Integer> amountOff);

        _FinalStage amountOff(Integer amountOff);

        _FinalStage currency(Optional<String> currency);

        _FinalStage currency(String currency);

        _FinalStage duration(Optional<String> duration);

        _FinalStage duration(String duration);

        _FinalStage durationInMonths(Optional<Integer> durationInMonths);

        _FinalStage durationInMonths(Integer durationInMonths);

        _FinalStage maxRedemptions(Optional<Integer> maxRedemptions);

        _FinalStage maxRedemptions(Integer maxRedemptions);

        _FinalStage metadata(Map<String, JsonNode> metadata);

        _FinalStage putAllMetadata(Map<String, JsonNode> metadata);

        _FinalStage metadata(String key, JsonNode value);

        _FinalStage percentOff(Optional<Integer> percentOff);

        _FinalStage percentOff(Integer percentOff);

        _FinalStage validFrom(Optional<OffsetDateTime> validFrom);

        _FinalStage validFrom(OffsetDateTime validFrom);

        _FinalStage validUntil(Optional<OffsetDateTime> validUntil);

        _FinalStage validUntil(OffsetDateTime validUntil);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static final class Builder
            implements AccountIdStage,
                    EnvironmentIdStage,
                    ExternalIdStage,
                    IdStage,
                    IsActiveStage,
                    NameStage,
                    TimesRedeemedStage,
                    _FinalStage {
        private String accountId;

        private String environmentId;

        private String externalId;

        private String id;

        private boolean isActive;

        private String name;

        private int timesRedeemed;

        private Optional<OffsetDateTime> validUntil = Optional.empty();

        private Optional<OffsetDateTime> validFrom = Optional.empty();

        private Optional<Integer> percentOff = Optional.empty();

        private Map<String, JsonNode> metadata = new LinkedHashMap<>();

        private Optional<Integer> maxRedemptions = Optional.empty();

        private Optional<Integer> durationInMonths = Optional.empty();

        private Optional<String> duration = Optional.empty();

        private Optional<String> currency = Optional.empty();

        private Optional<Integer> amountOff = Optional.empty();

        @JsonAnySetter
        private Map<String, Object> additionalProperties = new HashMap<>();

        private Builder() {}

        @java.lang.Override
        public Builder from(BillingCouponResponseData other) {
            accountId(other.getAccountId());
            amountOff(other.getAmountOff());
            currency(other.getCurrency());
            duration(other.getDuration());
            durationInMonths(other.getDurationInMonths());
            environmentId(other.getEnvironmentId());
            externalId(other.getExternalId());
            id(other.getId());
            isActive(other.getIsActive());
            maxRedemptions(other.getMaxRedemptions());
            metadata(other.getMetadata());
            name(other.getName());
            percentOff(other.getPercentOff());
            timesRedeemed(other.getTimesRedeemed());
            validFrom(other.getValidFrom());
            validUntil(other.getValidUntil());
            return this;
        }

        @java.lang.Override
        @JsonSetter("account_id")
        public EnvironmentIdStage accountId(@NotNull String accountId) {
            this.accountId = Objects.requireNonNull(accountId, "accountId must not be null");
            return this;
        }

        @java.lang.Override
        @JsonSetter("environment_id")
        public ExternalIdStage environmentId(@NotNull String environmentId) {
            this.environmentId = Objects.requireNonNull(environmentId, "environmentId must not be null");
            return this;
        }

        @java.lang.Override
        @JsonSetter("external_id")
        public IdStage externalId(@NotNull String externalId) {
            this.externalId = Objects.requireNonNull(externalId, "externalId must not be null");
            return this;
        }

        @java.lang.Override
        @JsonSetter("id")
        public IsActiveStage id(@NotNull String id) {
            this.id = Objects.requireNonNull(id, "id must not be null");
            return this;
        }

        @java.lang.Override
        @JsonSetter("is_active")
        public NameStage isActive(boolean isActive) {
            this.isActive = isActive;
            return this;
        }

        @java.lang.Override
        @JsonSetter("name")
        public TimesRedeemedStage name(@NotNull String name) {
            this.name = Objects.requireNonNull(name, "name must not be null");
            return this;
        }

        @java.lang.Override
        @JsonSetter("times_redeemed")
        public _FinalStage timesRedeemed(int timesRedeemed) {
            this.timesRedeemed = timesRedeemed;
            return this;
        }

        @java.lang.Override
        public _FinalStage validUntil(OffsetDateTime validUntil) {
            this.validUntil = Optional.ofNullable(validUntil);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "valid_until", nulls = Nulls.SKIP)
        public _FinalStage validUntil(Optional<OffsetDateTime> validUntil) {
            this.validUntil = validUntil;
            return this;
        }

        @java.lang.Override
        public _FinalStage validFrom(OffsetDateTime validFrom) {
            this.validFrom = Optional.ofNullable(validFrom);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "valid_from", nulls = Nulls.SKIP)
        public _FinalStage validFrom(Optional<OffsetDateTime> validFrom) {
            this.validFrom = validFrom;
            return this;
        }

        @java.lang.Override
        public _FinalStage percentOff(Integer percentOff) {
            this.percentOff = Optional.ofNullable(percentOff);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "percent_off", nulls = Nulls.SKIP)
        public _FinalStage percentOff(Optional<Integer> percentOff) {
            this.percentOff = percentOff;
            return this;
        }

        @java.lang.Override
        public _FinalStage metadata(String key, JsonNode value) {
            this.metadata.put(key, value);
            return this;
        }

        @java.lang.Override
        public _FinalStage putAllMetadata(Map<String, JsonNode> metadata) {
            this.metadata.putAll(metadata);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "metadata", nulls = Nulls.SKIP)
        public _FinalStage metadata(Map<String, JsonNode> metadata) {
            this.metadata.clear();
            this.metadata.putAll(metadata);
            return this;
        }

        @java.lang.Override
        public _FinalStage maxRedemptions(Integer maxRedemptions) {
            this.maxRedemptions = Optional.ofNullable(maxRedemptions);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "max_redemptions", nulls = Nulls.SKIP)
        public _FinalStage maxRedemptions(Optional<Integer> maxRedemptions) {
            this.maxRedemptions = maxRedemptions;
            return this;
        }

        @java.lang.Override
        public _FinalStage durationInMonths(Integer durationInMonths) {
            this.durationInMonths = Optional.ofNullable(durationInMonths);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "duration_in_months", nulls = Nulls.SKIP)
        public _FinalStage durationInMonths(Optional<Integer> durationInMonths) {
            this.durationInMonths = durationInMonths;
            return this;
        }

        @java.lang.Override
        public _FinalStage duration(String duration) {
            this.duration = Optional.ofNullable(duration);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "duration", nulls = Nulls.SKIP)
        public _FinalStage duration(Optional<String> duration) {
            this.duration = duration;
            return this;
        }

        @java.lang.Override
        public _FinalStage currency(String currency) {
            this.currency = Optional.ofNullable(currency);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "currency", nulls = Nulls.SKIP)
        public _FinalStage currency(Optional<String> currency) {
            this.currency = currency;
            return this;
        }

        @java.lang.Override
        public _FinalStage amountOff(Integer amountOff) {
            this.amountOff = Optional.ofNullable(amountOff);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "amount_off", nulls = Nulls.SKIP)
        public _FinalStage amountOff(Optional<Integer> amountOff) {
            this.amountOff = amountOff;
            return this;
        }

        @java.lang.Override
        public BillingCouponResponseData build() {
            return new BillingCouponResponseData(
                    accountId,
                    amountOff,
                    currency,
                    duration,
                    durationInMonths,
                    environmentId,
                    externalId,
                    id,
                    isActive,
                    maxRedemptions,
                    metadata,
                    name,
                    percentOff,
                    timesRedeemed,
                    validFrom,
                    validUntil,
                    additionalProperties);
        }
    }
}
