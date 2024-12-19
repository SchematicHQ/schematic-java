package com.schematic.api.cache;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;
import java.util.LinkedList;
import java.util.concurrent.locks.ReentrantLock;

public class LocalCache<T> implements CacheProvider<T> {
    public static final int DEFAULT_CACHE_CAPACITY = 1000;
    public static final Duration DEFAULT_CACHE_TTL = Duration.ofMillis(5000); // 5000 milliseconds

    private final ConcurrentHashMap<String, CachedItem<T>> cache;
    private final LinkedList<String> lruList;
    private final ReentrantLock lock;
    private final int maxItems;
    private final Duration ttl;

    public LocalCache() {
        this(DEFAULT_CACHE_CAPACITY, DEFAULT_CACHE_TTL);
    }

    public LocalCache(int maxItems, Duration ttl) {
        this.cache = new ConcurrentHashMap<>();
        this.lruList = new LinkedList<>();
        this.lock = new ReentrantLock();
        this.maxItems = maxItems;
        this.ttl = ttl;
    }

    @Override
    public T get(String key) {
        if (maxItems == 0) {
            return null;
        }

        CachedItem<T> item = cache.get(key);
        if (item == null) {
            return null;
        }

        if (Instant.now().isAfter(item.getExpiration())) {
            remove(key);
            return null;
        }

        lock.lock();
        try {
            lruList.remove(item.getKey());
            lruList.addFirst(item.getKey());
        } finally {
            lock.unlock();
        }

        return item.getValue();
    }

    @Override
    public void set(String key, T val, Duration ttlOverride) {
        if (maxItems == 0) {
            return;
        }

        Duration effectiveTtl = ttlOverride != null ? ttlOverride : ttl;
        Instant expiration = Instant.now().plus(effectiveTtl);

        lock.lock();
        try {
            CachedItem<T> existingItem = cache.get(key);
            if (existingItem != null) {
                existingItem.setValue(val);
                existingItem.setExpiration(expiration);
                lruList.remove(existingItem.getKey());
                lruList.addFirst(existingItem.getKey());
            } else {
                if (cache.size() >= maxItems) {
                    String lruKey = lruList.getLast();
                    remove(lruKey);
                }

                CachedItem<T> newItem = new CachedItem<>(val, expiration, key);
                lruList.addFirst(key);
                cache.put(key, newItem);
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void set(String key, T val) {
        set(key, val, null);
    }

    private void remove(String key) {
        CachedItem<T> removedItem = cache.remove(key);
        if (removedItem != null) {
            lock.lock();
            try {
                lruList.remove(removedItem.getKey());
            } finally {
                lock.unlock();
            }
        }
    }
}