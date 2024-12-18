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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonDeserialize(builder = CompanyCrmDealsResponseData.Builder.class)
public final class CompanyCrmDealsResponseData {
    private final String dealArr;

    private final String dealExternalId;

    private final String dealMrr;

    private final Optional<String> dealName;

    private final List<CrmDealLineItem> lineItems;

    private final Map<String, Object> additionalProperties;

    private CompanyCrmDealsResponseData(
            String dealArr,
            String dealExternalId,
            String dealMrr,
            Optional<String> dealName,
            List<CrmDealLineItem> lineItems,
            Map<String, Object> additionalProperties) {
        this.dealArr = dealArr;
        this.dealExternalId = dealExternalId;
        this.dealMrr = dealMrr;
        this.dealName = dealName;
        this.lineItems = lineItems;
        this.additionalProperties = additionalProperties;
    }

    @JsonProperty("deal_arr")
    public String getDealArr() {
        return dealArr;
    }

    @JsonProperty("deal_external_id")
    public String getDealExternalId() {
        return dealExternalId;
    }

    @JsonProperty("deal_mrr")
    public String getDealMrr() {
        return dealMrr;
    }

    @JsonProperty("deal_name")
    public Optional<String> getDealName() {
        return dealName;
    }

    @JsonProperty("line_items")
    public List<CrmDealLineItem> getLineItems() {
        return lineItems;
    }

    @java.lang.Override
    public boolean equals(Object other) {
        if (this == other) return true;
        return other instanceof CompanyCrmDealsResponseData && equalTo((CompanyCrmDealsResponseData) other);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    private boolean equalTo(CompanyCrmDealsResponseData other) {
        return dealArr.equals(other.dealArr)
                && dealExternalId.equals(other.dealExternalId)
                && dealMrr.equals(other.dealMrr)
                && dealName.equals(other.dealName)
                && lineItems.equals(other.lineItems);
    }

    @java.lang.Override
    public int hashCode() {
        return Objects.hash(this.dealArr, this.dealExternalId, this.dealMrr, this.dealName, this.lineItems);
    }

    @java.lang.Override
    public String toString() {
        return ObjectMappers.stringify(this);
    }

    public static DealArrStage builder() {
        return new Builder();
    }

    public interface DealArrStage {
        DealExternalIdStage dealArr(String dealArr);

        Builder from(CompanyCrmDealsResponseData other);
    }

    public interface DealExternalIdStage {
        DealMrrStage dealExternalId(String dealExternalId);
    }

    public interface DealMrrStage {
        _FinalStage dealMrr(String dealMrr);
    }

    public interface _FinalStage {
        CompanyCrmDealsResponseData build();

        _FinalStage dealName(Optional<String> dealName);

        _FinalStage dealName(String dealName);

        _FinalStage lineItems(List<CrmDealLineItem> lineItems);

        _FinalStage addLineItems(CrmDealLineItem lineItems);

        _FinalStage addAllLineItems(List<CrmDealLineItem> lineItems);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static final class Builder implements DealArrStage, DealExternalIdStage, DealMrrStage, _FinalStage {
        private String dealArr;

        private String dealExternalId;

        private String dealMrr;

        private List<CrmDealLineItem> lineItems = new ArrayList<>();

        private Optional<String> dealName = Optional.empty();

        @JsonAnySetter
        private Map<String, Object> additionalProperties = new HashMap<>();

        private Builder() {}

        @java.lang.Override
        public Builder from(CompanyCrmDealsResponseData other) {
            dealArr(other.getDealArr());
            dealExternalId(other.getDealExternalId());
            dealMrr(other.getDealMrr());
            dealName(other.getDealName());
            lineItems(other.getLineItems());
            return this;
        }

        @java.lang.Override
        @JsonSetter("deal_arr")
        public DealExternalIdStage dealArr(String dealArr) {
            this.dealArr = dealArr;
            return this;
        }

        @java.lang.Override
        @JsonSetter("deal_external_id")
        public DealMrrStage dealExternalId(String dealExternalId) {
            this.dealExternalId = dealExternalId;
            return this;
        }

        @java.lang.Override
        @JsonSetter("deal_mrr")
        public _FinalStage dealMrr(String dealMrr) {
            this.dealMrr = dealMrr;
            return this;
        }

        @java.lang.Override
        public _FinalStage addAllLineItems(List<CrmDealLineItem> lineItems) {
            this.lineItems.addAll(lineItems);
            return this;
        }

        @java.lang.Override
        public _FinalStage addLineItems(CrmDealLineItem lineItems) {
            this.lineItems.add(lineItems);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "line_items", nulls = Nulls.SKIP)
        public _FinalStage lineItems(List<CrmDealLineItem> lineItems) {
            this.lineItems.clear();
            this.lineItems.addAll(lineItems);
            return this;
        }

        @java.lang.Override
        public _FinalStage dealName(String dealName) {
            this.dealName = Optional.of(dealName);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "deal_name", nulls = Nulls.SKIP)
        public _FinalStage dealName(Optional<String> dealName) {
            this.dealName = dealName;
            return this;
        }

        @java.lang.Override
        public CompanyCrmDealsResponseData build() {
            return new CompanyCrmDealsResponseData(
                    dealArr, dealExternalId, dealMrr, dealName, lineItems, additionalProperties);
        }
    }
}
