/**
 * This file was auto-generated by Fern from our API Definition.
 */
package com.schematic.api.resources.companies.requests;

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
@JsonDeserialize(builder = GetOrCreateCompanyMembershipRequestBody.Builder.class)
public final class GetOrCreateCompanyMembershipRequestBody {
    private final String companyId;

    private final String userId;

    private final Map<String, Object> additionalProperties;

    private GetOrCreateCompanyMembershipRequestBody(
            String companyId, String userId, Map<String, Object> additionalProperties) {
        this.companyId = companyId;
        this.userId = userId;
        this.additionalProperties = additionalProperties;
    }

    @JsonProperty("company_id")
    public String getCompanyId() {
        return companyId;
    }

    @JsonProperty("user_id")
    public String getUserId() {
        return userId;
    }

    @java.lang.Override
    public boolean equals(Object other) {
        if (this == other) return true;
        return other instanceof GetOrCreateCompanyMembershipRequestBody
                && equalTo((GetOrCreateCompanyMembershipRequestBody) other);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    private boolean equalTo(GetOrCreateCompanyMembershipRequestBody other) {
        return companyId.equals(other.companyId) && userId.equals(other.userId);
    }

    @java.lang.Override
    public int hashCode() {
        return Objects.hash(this.companyId, this.userId);
    }

    @java.lang.Override
    public String toString() {
        return ObjectMappers.stringify(this);
    }

    public static CompanyIdStage builder() {
        return new Builder();
    }

    public interface CompanyIdStage {
        UserIdStage companyId(@NotNull String companyId);

        Builder from(GetOrCreateCompanyMembershipRequestBody other);
    }

    public interface UserIdStage {
        _FinalStage userId(@NotNull String userId);
    }

    public interface _FinalStage {
        GetOrCreateCompanyMembershipRequestBody build();
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static final class Builder implements CompanyIdStage, UserIdStage, _FinalStage {
        private String companyId;

        private String userId;

        @JsonAnySetter
        private Map<String, Object> additionalProperties = new HashMap<>();

        private Builder() {}

        @java.lang.Override
        public Builder from(GetOrCreateCompanyMembershipRequestBody other) {
            companyId(other.getCompanyId());
            userId(other.getUserId());
            return this;
        }

        @java.lang.Override
        @JsonSetter("company_id")
        public UserIdStage companyId(@NotNull String companyId) {
            this.companyId = Objects.requireNonNull(companyId, "companyId must not be null");
            return this;
        }

        @java.lang.Override
        @JsonSetter("user_id")
        public _FinalStage userId(@NotNull String userId) {
            this.userId = Objects.requireNonNull(userId, "userId must not be null");
            return this;
        }

        @java.lang.Override
        public GetOrCreateCompanyMembershipRequestBody build() {
            return new GetOrCreateCompanyMembershipRequestBody(companyId, userId, additionalProperties);
        }
    }
}
