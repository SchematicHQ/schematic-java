/**
 * This file was auto-generated by Fern from our API Definition.
 */
package com.schematic.api.resources.webhooks.requests;

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
@JsonDeserialize(builder = CountWebhookEventsRequest.Builder.class)
public final class CountWebhookEventsRequest {
    private final Optional<String> ids;

    private final Optional<String> q;

    private final Optional<String> webhookId;

    private final Optional<Integer> limit;

    private final Optional<Integer> offset;

    private final Map<String, Object> additionalProperties;

    private CountWebhookEventsRequest(
            Optional<String> ids,
            Optional<String> q,
            Optional<String> webhookId,
            Optional<Integer> limit,
            Optional<Integer> offset,
            Map<String, Object> additionalProperties) {
        this.ids = ids;
        this.q = q;
        this.webhookId = webhookId;
        this.limit = limit;
        this.offset = offset;
        this.additionalProperties = additionalProperties;
    }

    @JsonProperty("ids")
    public Optional<String> getIds() {
        return ids;
    }

    @JsonProperty("q")
    public Optional<String> getQ() {
        return q;
    }

    @JsonProperty("webhook_id")
    public Optional<String> getWebhookId() {
        return webhookId;
    }

    /**
     * @return Page limit (default 100)
     */
    @JsonProperty("limit")
    public Optional<Integer> getLimit() {
        return limit;
    }

    /**
     * @return Page offset (default 0)
     */
    @JsonProperty("offset")
    public Optional<Integer> getOffset() {
        return offset;
    }

    @java.lang.Override
    public boolean equals(Object other) {
        if (this == other) return true;
        return other instanceof CountWebhookEventsRequest && equalTo((CountWebhookEventsRequest) other);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    private boolean equalTo(CountWebhookEventsRequest other) {
        return ids.equals(other.ids)
                && q.equals(other.q)
                && webhookId.equals(other.webhookId)
                && limit.equals(other.limit)
                && offset.equals(other.offset);
    }

    @java.lang.Override
    public int hashCode() {
        return Objects.hash(this.ids, this.q, this.webhookId, this.limit, this.offset);
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
        private Optional<String> ids = Optional.empty();

        private Optional<String> q = Optional.empty();

        private Optional<String> webhookId = Optional.empty();

        private Optional<Integer> limit = Optional.empty();

        private Optional<Integer> offset = Optional.empty();

        @JsonAnySetter
        private Map<String, Object> additionalProperties = new HashMap<>();

        private Builder() {}

        public Builder from(CountWebhookEventsRequest other) {
            ids(other.getIds());
            q(other.getQ());
            webhookId(other.getWebhookId());
            limit(other.getLimit());
            offset(other.getOffset());
            return this;
        }

        @JsonSetter(value = "ids", nulls = Nulls.SKIP)
        public Builder ids(Optional<String> ids) {
            this.ids = ids;
            return this;
        }

        public Builder ids(String ids) {
            this.ids = Optional.ofNullable(ids);
            return this;
        }

        @JsonSetter(value = "q", nulls = Nulls.SKIP)
        public Builder q(Optional<String> q) {
            this.q = q;
            return this;
        }

        public Builder q(String q) {
            this.q = Optional.ofNullable(q);
            return this;
        }

        @JsonSetter(value = "webhook_id", nulls = Nulls.SKIP)
        public Builder webhookId(Optional<String> webhookId) {
            this.webhookId = webhookId;
            return this;
        }

        public Builder webhookId(String webhookId) {
            this.webhookId = Optional.ofNullable(webhookId);
            return this;
        }

        @JsonSetter(value = "limit", nulls = Nulls.SKIP)
        public Builder limit(Optional<Integer> limit) {
            this.limit = limit;
            return this;
        }

        public Builder limit(Integer limit) {
            this.limit = Optional.ofNullable(limit);
            return this;
        }

        @JsonSetter(value = "offset", nulls = Nulls.SKIP)
        public Builder offset(Optional<Integer> offset) {
            this.offset = offset;
            return this;
        }

        public Builder offset(Integer offset) {
            this.offset = Optional.ofNullable(offset);
            return this;
        }

        public CountWebhookEventsRequest build() {
            return new CountWebhookEventsRequest(ids, q, webhookId, limit, offset, additionalProperties);
        }
    }
}
