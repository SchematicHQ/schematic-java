package com.schematic.api;

import static org.junit.jupiter.api.Assertions.*;

import com.schematic.api.cache.CacheProvider;
import com.schematic.api.cache.LocalCache;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
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

    @Test
    void testDeleteNotPresent() {
        LocalCache<String> cacheProvider = new LocalCache<>(10, Duration.ofMinutes(5));

        // Setting a key then overwriting with null effectively removes it
        // Getting a non-existent key after "delete" should return null without error
        assertNull(cacheProvider.get("never_existed"));

        cacheProvider.set("existing_key", "value");
        assertEquals("value", cacheProvider.get("existing_key"));

        // Overwrite with null to simulate deletion
        cacheProvider.set("existing_key", null);
        assertNull(cacheProvider.get("existing_key"));

        // Verify no side effects on other operations
        cacheProvider.set("another_key", "another_value");
        assertEquals("another_value", cacheProvider.get("another_key"));
    }

    @Test
    void testZeroMaxItems() {
        LocalCache<String> cacheProvider = new LocalCache<>(0, Duration.ofMinutes(5));

        cacheProvider.set("key1", "value1");
        assertNull(cacheProvider.get("key1"));

        cacheProvider.set("key2", "value2");
        assertNull(cacheProvider.get("key2"));

        // Multiple sets should all be no-ops
        for (int i = 0; i < 100; i++) {
            cacheProvider.set("key_" + i, "val_" + i);
        }
        for (int i = 0; i < 100; i++) {
            assertNull(cacheProvider.get("key_" + i));
        }
    }

    @Test
    void testCustomTTLOverrideShorterThanDefault() throws InterruptedException {
        Duration defaultTtl = Duration.ofSeconds(5);
        Duration shortTtl = Duration.ofMillis(50);
        LocalCache<String> cacheProvider = new LocalCache<>(100, defaultTtl);

        // Set one item with default TTL
        cacheProvider.set("default_ttl_key", "default_value");

        // Set another item with a very short TTL override
        cacheProvider.set("short_ttl_key", "short_value", shortTtl);

        // Both should be present initially
        assertEquals("default_value", cacheProvider.get("default_ttl_key"));
        assertEquals("short_value", cacheProvider.get("short_ttl_key"));

        // Wait for the short TTL to expire
        Thread.sleep(100);

        // Short TTL item should be expired, default TTL item should still exist
        assertNull(cacheProvider.get("short_ttl_key"));
        assertEquals("default_value", cacheProvider.get("default_ttl_key"));
    }

    @Test
    void testLRUEvictionOrder() {
        LocalCache<String> cacheProvider = new LocalCache<>(5, Duration.ofHours(1));

        // Fill cache to capacity
        cacheProvider.set("a", "val_a");
        cacheProvider.set("b", "val_b");
        cacheProvider.set("c", "val_c");
        cacheProvider.set("d", "val_d");
        cacheProvider.set("e", "val_e");

        // Access items in a specific order: a, c, e (making b and d least recently used)
        assertEquals("val_a", cacheProvider.get("a"));
        assertEquals("val_c", cacheProvider.get("c"));
        assertEquals("val_e", cacheProvider.get("e"));

        // Add a new item - should evict "b" (least recently used)
        cacheProvider.set("f", "val_f");
        assertNull(cacheProvider.get("b"));

        // All other items should still be present
        assertEquals("val_a", cacheProvider.get("a"));
        assertEquals("val_c", cacheProvider.get("c"));
        assertEquals("val_d", cacheProvider.get("d"));
        assertEquals("val_e", cacheProvider.get("e"));
        assertEquals("val_f", cacheProvider.get("f"));

        // Add another item - should evict "a" (now the least recently used after
        // the get() calls above moved other items to the front of the LRU list)
        cacheProvider.set("g", "val_g");
        assertNull(cacheProvider.get("a"));
    }

    @Test
    void testCacheWithDifferentValueTypes() {
        // Test with String values
        LocalCache<String> stringCache = new LocalCache<>(10, Duration.ofMinutes(5));
        stringCache.set("str_key", "hello world");
        assertEquals("hello world", stringCache.get("str_key"));

        // Test with Integer values
        LocalCache<Integer> intCache = new LocalCache<>(10, Duration.ofMinutes(5));
        intCache.set("int_key", 42);
        assertEquals(42, intCache.get("int_key"));
        intCache.set("int_negative", -100);
        assertEquals(-100, intCache.get("int_negative"));

        // Test with a complex object
        LocalCache<TestComplexObject> objectCache = new LocalCache<>(10, Duration.ofMinutes(5));
        TestComplexObject obj = new TestComplexObject("test-name", 99, Arrays.asList("tag1", "tag2"));
        objectCache.set("obj_key", obj);
        TestComplexObject retrieved = objectCache.get("obj_key");
        assertNotNull(retrieved);
        assertEquals("test-name", retrieved.name);
        assertEquals(99, retrieved.count);
        assertEquals(Arrays.asList("tag1", "tag2"), retrieved.tags);
    }

    @Test
    void testDeleteMissing_removesUnlistedKeys() {
        LocalCache<String> cacheProvider = new LocalCache<>(10, Duration.ofMinutes(5));

        cacheProvider.set("key1", "val1");
        cacheProvider.set("key2", "val2");
        cacheProvider.set("key3", "val3");

        cacheProvider.deleteMissing(Arrays.asList("key1"));

        assertEquals("val1", cacheProvider.get("key1"));
        assertNull(cacheProvider.get("key2"));
        assertNull(cacheProvider.get("key3"));
    }

    @Test
    void testDeleteMissing_withEmptyList() {
        LocalCache<String> cacheProvider = new LocalCache<>(10, Duration.ofMinutes(5));

        cacheProvider.set("key1", "val1");
        cacheProvider.set("key2", "val2");

        cacheProvider.deleteMissing(Collections.emptyList());

        assertNull(cacheProvider.get("key1"));
        assertNull(cacheProvider.get("key2"));
    }

    @Test
    void testDeleteMissing_withNullList() {
        LocalCache<String> cacheProvider = new LocalCache<>(10, Duration.ofMinutes(5));

        cacheProvider.set("key1", "val1");

        assertDoesNotThrow(() -> cacheProvider.deleteMissing(null));
        assertNull(cacheProvider.get("key1"));
    }

    @Test
    void testDeleteMissing_zeroCapacity() {
        LocalCache<String> cacheProvider = new LocalCache<>(0, Duration.ofMinutes(5));

        assertDoesNotThrow(() -> cacheProvider.deleteMissing(Arrays.asList("key1")));
    }

    /** Simple value object for testing complex types in the cache. */
    private static class TestComplexObject {
        final String name;
        final int count;
        final List<String> tags;

        TestComplexObject(String name, int count, List<String> tags) {
            this.name = name;
            this.count = count;
            this.tags = tags;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof TestComplexObject)) return false;
            TestComplexObject that = (TestComplexObject) o;
            return count == that.count && Objects.equals(name, that.name) && Objects.equals(tags, that.tags);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, count, tags);
        }
    }
}
