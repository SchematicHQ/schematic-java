package com.schematic.api;

import static org.junit.jupiter.api.Assertions.*;

import com.schematic.api.cache.CacheProvider;
import com.schematic.api.cache.LocalCache;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.Test;

class LocalCacheTest {

    @Test
    void testSetAndGetBoolean() {
        CacheProvider<Boolean> cache = new LocalCache<>();
        String key = "test_key";
        cache.set(key, true);
        assertEquals(true, cache.get(key));
    }

    @Test
    void testSetAndGetString() {
        CacheProvider<String> cache = new LocalCache<>();
        String key = "test_key";
        cache.set(key, "test_string");
        assertEquals("test_string", cache.get(key));
    }

    @Test
    void testSetAndGetList() {
        CacheProvider<List<String>> cache = new LocalCache<>();
        String key = "test_key";
        List<String> value = Arrays.asList("test_string1", "test_string2");
        cache.set(key, value);
        assertEquals(value, cache.get(key));
    }

    @Test
    void testDefaultTTL() throws InterruptedException {
        LocalCache<Boolean> cacheProvider = new LocalCache<>(1, LocalCache.DEFAULT_CACHE_TTL);
        String key = "test_key";

        cacheProvider.set(key, true);
        assertEquals(true, cacheProvider.get(key));

        Thread.sleep(LocalCache.DEFAULT_CACHE_TTL.toMillis() + 5);
        assertNull(cacheProvider.get(key));
    }

    @Test
    void testDefaultCapacity() {
        LocalCache<Integer> cacheProvider = new LocalCache<>(LocalCache.DEFAULT_CACHE_CAPACITY, Duration.ofMinutes(10));
        String key = "test_key";

        cacheProvider.set(key, -1);
        for (int i = 1; i < LocalCache.DEFAULT_CACHE_CAPACITY; i++) {
            cacheProvider.set(String.valueOf(i), i);
            assertEquals(-1, cacheProvider.get(key));
        }

        cacheProvider.set("new_key", -2);
        assertNull(cacheProvider.get("1"));
        assertEquals(-1, cacheProvider.get(key));
        assertEquals(-2, cacheProvider.get("new_key"));
    }

    @Test
    void testNonExistentKeyReturnsNull() {
        LocalCache<Object> cacheProvider = new LocalCache<>(1, Duration.ofMinutes(1));
        assertNull(cacheProvider.get("non_existent_key"));
    }

    @Test
    void testConcurrentAccess() throws InterruptedException {
        int numberOfThreads = 10;
        int cacheCapacity = 30;
        LocalCache<Integer> cacheProvider = new LocalCache<>(cacheCapacity, Duration.ofHours(5));
        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);
        CountDownLatch startSignal = new CountDownLatch(1);
        CountDownLatch doneSignal = new CountDownLatch(numberOfThreads);

        for (int t = 0; t < numberOfThreads; t++) {
            final int threadNum = t;
            executor.execute(() -> {
                try {
                    startSignal.await();
                    int start = threadNum * cacheCapacity + 1;
                    int end = start + cacheCapacity - 1;
                    for (int i = start; i <= end; i++) {
                        cacheProvider.set(String.valueOf(i), i);
                    }
                } catch (InterruptedException ignored) {
                } finally {
                    doneSignal.countDown();
                }
            });
        }

        startSignal.countDown();
        doneSignal.await();
        executor.shutdown();

        List<Integer> cacheHits = new ArrayList<>();
        for (int i = 1; i <= numberOfThreads * cacheCapacity; i++) {
            Integer value = cacheProvider.get(String.valueOf(i));
            if (value != null && value == i) {
                cacheHits.add(i);
            }
        }

        assertEquals(cacheCapacity, cacheHits.size());
        assertNotEquals(cacheCapacity, cacheHits.get(cacheHits.size() - 1) - cacheHits.get(0) + 1);
    }

    @Test
    void testTTLOverride() throws InterruptedException {
        LocalCache<Integer> cacheProvider = new LocalCache<>(1000, Duration.ofHours(5));
        String key = "test_key";
        int expectedValue = 5;
        Duration ttlOverride = Duration.ofSeconds(3);

        cacheProvider.set(key, expectedValue, ttlOverride);
        assertEquals(expectedValue, cacheProvider.get(key));

        Thread.sleep(1000);
        assertEquals(expectedValue, cacheProvider.get(key));

        Thread.sleep(ttlOverride.toMillis() + 1);
        assertNull(cacheProvider.get(key));
    }

    @Test
    void testEvictionByLastAccessed() {
        LocalCache<Integer> cacheProvider = new LocalCache<>(10, Duration.ofHours(5));

        for (int i = 1; i <= 10; i++) {
            cacheProvider.set(String.valueOf(i), i);
        }

        for (int i = 1; i <= 10; i++) {
            assertEquals(i, cacheProvider.get(String.valueOf(i)));
        }

        for (int i = 1; i <= 10; i++) {
            cacheProvider.set(String.valueOf(i + 10), -1);
            assertNull(cacheProvider.get(String.valueOf(i)));
        }
    }
}
