package com.schematic.api.cache;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class LocalCache<T> implements CacheProvider<T>, AutoCloseable {
    public static final int DEFAULT_CACHE_CAPACITY = 1000;
    public static final Duration DEFAULT_CACHE_TTL = Duration.ofMillis(5000); // 5000 milliseconds
    private static final Duration CLEANUP_INTERVAL = Duration.ofSeconds(30);

    // Shared scheduler for background cleanup across all LocalCache instances
    private static final ScheduledExecutorService SHARED_CLEANUP_SCHEDULER =
            Executors.newSingleThreadScheduledExecutor(r -> {
                Thread t = new Thread(r, "schematic-cache-cleanup");
                t.setDaemon(true);
                return t;
            });

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

        // Schedule background cleanup on the shared thread
        SHARED_CLEANUP_SCHEDULER.scheduleAtFixedRate(
                this::removeExpiredItems,
                CLEANUP_INTERVAL.getSeconds(),
                CLEANUP_INTERVAL.getSeconds(),
                TimeUnit.SECONDS);
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

    @Override
    public void deleteMissing(List<String> keysToKeep) {
        if (maxItems == 0) {
            return;
        }

        Set<String> keepSet = keysToKeep != null ? new HashSet<>(keysToKeep) : new HashSet<>();

        List<String> keysToDelete;
        lock.lock();
        try {
            keysToDelete = new ArrayList<>();
            for (String key : cache.keySet()) {
                if (!keepSet.contains(key)) {
                    keysToDelete.add(key);
                }
            }
        } finally {
            lock.unlock();
        }

        for (String key : keysToDelete) {
            remove(key);
        }
    }

    @Override
    public void delete(String key) {
        remove(key);
    }

    @Override
    public void close() {
        cache.clear();
        lock.lock();
        try {
            lruList.clear();
        } finally {
            lock.unlock();
        }
    }

    private void removeExpiredItems() {
        Instant now = Instant.now();
        List<String> expired = new ArrayList<>();

        for (ConcurrentHashMap.Entry<String, CachedItem<T>> entry : cache.entrySet()) {
            if (now.isAfter(entry.getValue().getExpiration())) {
                expired.add(entry.getKey());
            }
        }

        for (String key : expired) {
            remove(key);
        }
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
