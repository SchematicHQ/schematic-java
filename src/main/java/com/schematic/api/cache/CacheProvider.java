package com.schematic.api.cache;

import java.time.Duration;
import java.util.List;

public interface CacheProvider<T> {
    T get(String key);

    void set(String key, T val, Duration ttlOverride);

    void set(String key, T val);

    /**
     * Removes a cached entry by key.
     */
    default void delete(String key) {
        // no-op by default for backwards compatibility
    }

    /**
     * Deletes all cached entries whose keys are not in the provided list.
     * Used to synchronize cache contents after receiving a full dataset from the server.
     */
    default void deleteMissing(List<String> keysToKeep) {
        // no-op by default for backwards compatibility
    }
}
