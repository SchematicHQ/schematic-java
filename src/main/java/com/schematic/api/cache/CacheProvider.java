package com.schematic.api.cache;

import java.time.Duration;

public interface CacheProvider<T> {
    T get(String key);
    void set(String key, T val, Duration ttlOverride);
    void set(String key, T val);
}