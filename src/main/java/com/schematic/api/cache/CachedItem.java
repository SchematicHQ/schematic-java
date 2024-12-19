package com.schematic.api.cache;

import java.time.Instant;

class CachedItem<T> {
    private T value;
    private Instant expiration;
    private String key;

    public CachedItem(T value, Instant expiration, String key) {
        this.value = value;
        this.expiration = expiration;
        this.key = key;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public Instant getExpiration() {
        return expiration;
    }

    public void setExpiration(Instant expiration) {
        this.expiration = expiration;
    }

    public String getKey() {
        return key;
    }
}