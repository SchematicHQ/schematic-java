package com.schematic.api.cache;

import java.time.Duration;
import java.util.List;

public interface CacheProvider<T> {
    T get(String key);

    void set(String key, T val, Duration ttlOverride);

    void set(String key, T val);

    /**
     * Writes a value but keeps the key's current expiry instead of applying this cache's TTL.
     *
     * <p>Used when the SDK rewrites an entry that another writer (the replicator) owns, so the
     * rewrite does not change how long the entry lives. A key with no expiry keeps none. Providers
     * that cannot read an entry's expiry fall back to {@link #set(String, Object)}.
     */
    default void setKeepTTL(String key, T val) {
        set(key, val);
    }

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

    /**
     * Deletes cached entries whose keys are not in {@code keysToKeep}, scoped to the
     * namespace described by {@code scanPattern} (e.g. {@code "flags:*"}).
     *
     * <p>The scope is required for backends like Redis where the cache is one slice of a
     * shared keyspace — without it, a full-snapshot sync would wipe sibling caches.
     * Backends that own their own keyspace (e.g. in-process caches) can ignore
     * {@code scanPattern} and reuse the one-arg logic.
     */
    default void deleteMissing(List<String> keysToKeep, String scanPattern) {
        deleteMissing(keysToKeep);
    }
}
