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
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonDeserialize(builder = UpsertTraitRequestBody.Builder.class)
public final class UpsertTraitRequestBody {
    private final Optional<Integer> incr;

    private final Map<String, String> keys;

    private final Optional<String> set;

    private final String trait;

    private final Optional<Boolean> updateOnly;

    private final Map<String, Object> additionalProperties;

    private UpsertTraitRequestBody(
            Optional<Integer> incr,
            Map<String, String> keys,
            Optional<String> set,
            String trait,
            Optional<Boolean> updateOnly,
            Map<String, Object> additionalProperties) {
        this.incr = incr;
        this.keys = keys;
        this.set = set;
        this.trait = trait;
        this.updateOnly = updateOnly;
        this.additionalProperties = additionalProperties;
    }

    /**
     * @return Amount to increment the trait by (positive or negative)
     */
    @JsonProperty("incr")
    public Optional<Integer> getIncr() {
        return incr;
    }

    /**
     * @return Key/value pairs too identify a company or user
     */
    @JsonProperty("keys")
    public Map<String, String> getKeys() {
        return keys;
    }

    /**
     * @return Value to set the trait to
     */
    @JsonProperty("set")
    public Optional<String> getSet() {
        return set;
    }

    /**
     * @return Name of the trait to update
     */
    @JsonProperty("trait")
    public String getTrait() {
        return trait;
    }

    /**
     * @return Unless this is set, the company or user will be created if it does not already exist
     */
    @JsonProperty("update_only")
    public Optional<Boolean> getUpdateOnly() {
        return updateOnly;
    }

    @java.lang.Override
    public boolean equals(Object other) {
        if (this == other) return true;
        return other instanceof UpsertTraitRequestBody && equalTo((UpsertTraitRequestBody) other);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    private boolean equalTo(UpsertTraitRequestBody other) {
        return incr.equals(other.incr)
                && keys.equals(other.keys)
                && set.equals(other.set)
                && trait.equals(other.trait)
                && updateOnly.equals(other.updateOnly);
    }

    @java.lang.Override
    public int hashCode() {
        return Objects.hash(this.incr, this.keys, this.set, this.trait, this.updateOnly);
    }

    @java.lang.Override
    public String toString() {
        return ObjectMappers.stringify(this);
    }

    public static TraitStage builder() {
        return new Builder();
    }

    public interface TraitStage {
        _FinalStage trait(String trait);

        Builder from(UpsertTraitRequestBody other);
    }

    public interface _FinalStage {
        UpsertTraitRequestBody build();

        _FinalStage incr(Optional<Integer> incr);

        _FinalStage incr(Integer incr);

        _FinalStage keys(Map<String, String> keys);

        _FinalStage putAllKeys(Map<String, String> keys);

        _FinalStage keys(String key, String value);

        _FinalStage set(Optional<String> set);

        _FinalStage set(String set);

        _FinalStage updateOnly(Optional<Boolean> updateOnly);

        _FinalStage updateOnly(Boolean updateOnly);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static final class Builder implements TraitStage, _FinalStage {
        private String trait;

        private Optional<Boolean> updateOnly = Optional.empty();

        private Optional<String> set = Optional.empty();

        private Map<String, String> keys = new LinkedHashMap<>();

        private Optional<Integer> incr = Optional.empty();

        @JsonAnySetter
        private Map<String, Object> additionalProperties = new HashMap<>();

        private Builder() {}

        @java.lang.Override
        public Builder from(UpsertTraitRequestBody other) {
            incr(other.getIncr());
            keys(other.getKeys());
            set(other.getSet());
            trait(other.getTrait());
            updateOnly(other.getUpdateOnly());
            return this;
        }

        /**
         * <p>Name of the trait to update</p>
         * @return Reference to {@code this} so that method calls can be chained together.
         */
        @java.lang.Override
        @JsonSetter("trait")
        public _FinalStage trait(String trait) {
            this.trait = trait;
            return this;
        }

        /**
         * <p>Unless this is set, the company or user will be created if it does not already exist</p>
         * @return Reference to {@code this} so that method calls can be chained together.
         */
        @java.lang.Override
        public _FinalStage updateOnly(Boolean updateOnly) {
            this.updateOnly = Optional.of(updateOnly);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "update_only", nulls = Nulls.SKIP)
        public _FinalStage updateOnly(Optional<Boolean> updateOnly) {
            this.updateOnly = updateOnly;
            return this;
        }

        /**
         * <p>Value to set the trait to</p>
         * @return Reference to {@code this} so that method calls can be chained together.
         */
        @java.lang.Override
        public _FinalStage set(String set) {
            this.set = Optional.of(set);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "set", nulls = Nulls.SKIP)
        public _FinalStage set(Optional<String> set) {
            this.set = set;
            return this;
        }

        /**
         * <p>Key/value pairs too identify a company or user</p>
         * @return Reference to {@code this} so that method calls can be chained together.
         */
        @java.lang.Override
        public _FinalStage keys(String key, String value) {
            this.keys.put(key, value);
            return this;
        }

        /**
         * <p>Key/value pairs too identify a company or user</p>
         * @return Reference to {@code this} so that method calls can be chained together.
         */
        @java.lang.Override
        public _FinalStage putAllKeys(Map<String, String> keys) {
            this.keys.putAll(keys);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "keys", nulls = Nulls.SKIP)
        public _FinalStage keys(Map<String, String> keys) {
            this.keys.clear();
            this.keys.putAll(keys);
            return this;
        }

        /**
         * <p>Amount to increment the trait by (positive or negative)</p>
         * @return Reference to {@code this} so that method calls can be chained together.
         */
        @java.lang.Override
        public _FinalStage incr(Integer incr) {
            this.incr = Optional.of(incr);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "incr", nulls = Nulls.SKIP)
        public _FinalStage incr(Optional<Integer> incr) {
            this.incr = incr;
            return this;
        }

        @java.lang.Override
        public UpsertTraitRequestBody build() {
            return new UpsertTraitRequestBody(incr, keys, set, trait, updateOnly, additionalProperties);
        }
    }
}
