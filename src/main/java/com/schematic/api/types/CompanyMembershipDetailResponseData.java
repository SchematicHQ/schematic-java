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

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonDeserialize(builder = CompanyMembershipDetailResponseData.Builder.class)
public final class CompanyMembershipDetailResponseData {
    private final Optional<CompanyResponseData> company;

    private final String companyId;

    private final OffsetDateTime createdAt;

    private final String id;

    private final OffsetDateTime updatedAt;

    private final String userId;

    private final Map<String, Object> additionalProperties;

    private CompanyMembershipDetailResponseData(
            Optional<CompanyResponseData> company,
            String companyId,
            OffsetDateTime createdAt,
            String id,
            OffsetDateTime updatedAt,
            String userId,
            Map<String, Object> additionalProperties) {
        this.company = company;
        this.companyId = companyId;
        this.createdAt = createdAt;
        this.id = id;
        this.updatedAt = updatedAt;
        this.userId = userId;
        this.additionalProperties = additionalProperties;
    }

    @JsonProperty("company")
    public Optional<CompanyResponseData> getCompany() {
        return company;
    }

    @JsonProperty("company_id")
    public String getCompanyId() {
        return companyId;
    }

    @JsonProperty("created_at")
    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("updated_at")
    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    @JsonProperty("user_id")
    public String getUserId() {
        return userId;
    }

    @java.lang.Override
    public boolean equals(Object other) {
        if (this == other) return true;
        return other instanceof CompanyMembershipDetailResponseData
                && equalTo((CompanyMembershipDetailResponseData) other);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    private boolean equalTo(CompanyMembershipDetailResponseData other) {
        return company.equals(other.company)
                && companyId.equals(other.companyId)
                && createdAt.equals(other.createdAt)
                && id.equals(other.id)
                && updatedAt.equals(other.updatedAt)
                && userId.equals(other.userId);
    }

    @java.lang.Override
    public int hashCode() {
        return Objects.hash(this.company, this.companyId, this.createdAt, this.id, this.updatedAt, this.userId);
    }

    @java.lang.Override
    public String toString() {
        return ObjectMappers.stringify(this);
    }

    public static CompanyIdStage builder() {
        return new Builder();
    }

    public interface CompanyIdStage {
        CreatedAtStage companyId(String companyId);

        Builder from(CompanyMembershipDetailResponseData other);
    }

    public interface CreatedAtStage {
        IdStage createdAt(OffsetDateTime createdAt);
    }

    public interface IdStage {
        UpdatedAtStage id(String id);
    }

    public interface UpdatedAtStage {
        UserIdStage updatedAt(OffsetDateTime updatedAt);
    }

    public interface UserIdStage {
        _FinalStage userId(String userId);
    }

    public interface _FinalStage {
        CompanyMembershipDetailResponseData build();

        _FinalStage company(Optional<CompanyResponseData> company);

        _FinalStage company(CompanyResponseData company);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static final class Builder
            implements CompanyIdStage, CreatedAtStage, IdStage, UpdatedAtStage, UserIdStage, _FinalStage {
        private String companyId;

        private OffsetDateTime createdAt;

        private String id;

        private OffsetDateTime updatedAt;

        private String userId;

        private Optional<CompanyResponseData> company = Optional.empty();

        @JsonAnySetter
        private Map<String, Object> additionalProperties = new HashMap<>();

        private Builder() {}

        @java.lang.Override
        public Builder from(CompanyMembershipDetailResponseData other) {
            company(other.getCompany());
            companyId(other.getCompanyId());
            createdAt(other.getCreatedAt());
            id(other.getId());
            updatedAt(other.getUpdatedAt());
            userId(other.getUserId());
            return this;
        }

        @java.lang.Override
        @JsonSetter("company_id")
        public CreatedAtStage companyId(String companyId) {
            this.companyId = companyId;
            return this;
        }

        @java.lang.Override
        @JsonSetter("created_at")
        public IdStage createdAt(OffsetDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        @java.lang.Override
        @JsonSetter("id")
        public UpdatedAtStage id(String id) {
            this.id = id;
            return this;
        }

        @java.lang.Override
        @JsonSetter("updated_at")
        public UserIdStage updatedAt(OffsetDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        @java.lang.Override
        @JsonSetter("user_id")
        public _FinalStage userId(String userId) {
            this.userId = userId;
            return this;
        }

        @java.lang.Override
        public _FinalStage company(CompanyResponseData company) {
            this.company = Optional.of(company);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "company", nulls = Nulls.SKIP)
        public _FinalStage company(Optional<CompanyResponseData> company) {
            this.company = company;
            return this;
        }

        @java.lang.Override
        public CompanyMembershipDetailResponseData build() {
            return new CompanyMembershipDetailResponseData(
                    company, companyId, createdAt, id, updatedAt, userId, additionalProperties);
        }
    }
}
