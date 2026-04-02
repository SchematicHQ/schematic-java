package com.schematic.api.cache;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.schematic.api.core.ObjectMappers;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import redis.clients.jedis.JedisPooled;
import redis.clients.jedis.params.ScanParams;
import redis.clients.jedis.resps.ScanResult;

/**
 * Redis-backed cache provider using Jedis.
 *
 * <p>Values are serialized as JSON strings. Requires a pre-configured {@link JedisPooled}
 * instance, which handles connection pooling internally.
 */
public class RedisCacheProvider<T> implements CacheProvider<T> {

    private static final int SCAN_BATCH_SIZE = 100;
    private static final int DELETE_BATCH_SIZE = 100;

    private final JedisPooled jedis;
    private final long defaultTTLSeconds;
    private final String keyPrefix;
    private final ObjectMapper objectMapper;
    private final JavaType valueType;

    /**
     * Creates a Redis cache provider.
     *
     * @param jedis a pre-configured JedisPooled instance
     * @param ttl default time-to-live for cached entries
     * @param keyPrefix prefix for all Redis keys (e.g. "schematic:")
     * @param valueClass the class of cached values (needed for JSON deserialization)
     */
    public RedisCacheProvider(JedisPooled jedis, Duration ttl, String keyPrefix, Class<T> valueClass) {
        this.jedis = jedis;
        this.defaultTTLSeconds = ttl != null ? ttl.getSeconds() : 0;
        this.keyPrefix = keyPrefix != null ? keyPrefix : "schematic:";
        this.objectMapper = ObjectMappers.JSON_MAPPER;
        this.valueType = objectMapper.constructType(valueClass);
    }

    @Override
    public T get(String key) {
        String fullKey = getFullKey(key);
        String value = jedis.get(fullKey);
        if (value == null) {
            return null;
        }
        try {
            return objectMapper.readValue(value, valueType);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    @Override
    public void set(String key, T val, Duration ttlOverride) {
        String fullKey = getFullKey(key);
        if (val == null) {
            jedis.del(fullKey);
            return;
        }

        String json;
        try {
            json = objectMapper.writeValueAsString(val);
        } catch (JsonProcessingException e) {
            return;
        }

        long ttl = ttlOverride != null ? ttlOverride.getSeconds() : defaultTTLSeconds;
        if (ttl > 0) {
            jedis.setex(fullKey, ttl, json);
        } else {
            jedis.set(fullKey, json);
        }
    }

    @Override
    public void set(String key, T val) {
        set(key, val, null);
    }

    @Override
    public void delete(String key) {
        jedis.del(getFullKey(key));
    }

    @Override
    public void deleteMissing(List<String> keysToKeep) {
        if (keysToKeep == null || keysToKeep.isEmpty()) {
            return;
        }

        // Build set of full keys to keep for O(1) lookup
        Set<String> keepSet = new HashSet<>();
        String scanPrefix = null;
        for (String key : keysToKeep) {
            String fullKey = getFullKey(key);
            keepSet.add(fullKey);
            // Extract prefix from first key for scanning
            if (scanPrefix == null) {
                int colonIdx = fullKey.indexOf(':');
                if (colonIdx >= 0) {
                    // Use the key prefix + the cache key prefix (e.g. "schematic:flags:")
                    int secondColon = fullKey.indexOf(':', colonIdx + 1);
                    if (secondColon >= 0) {
                        scanPrefix = fullKey.substring(0, secondColon + 1);
                    } else {
                        scanPrefix = fullKey.substring(0, colonIdx + 1);
                    }
                }
            }
        }

        if (scanPrefix == null) {
            return;
        }

        // SCAN for all keys matching the prefix
        ScanParams scanParams = new ScanParams().match(scanPrefix + "*").count(SCAN_BATCH_SIZE);
        String cursor = ScanParams.SCAN_POINTER_START;
        List<String> keysToDelete = new ArrayList<>();

        do {
            ScanResult<String> result = jedis.scan(cursor, scanParams);
            for (String existingKey : result.getResult()) {
                if (!keepSet.contains(existingKey)) {
                    keysToDelete.add(existingKey);
                }
            }
            cursor = result.getCursor();
        } while (!cursor.equals(ScanParams.SCAN_POINTER_START));

        // Delete in batches
        for (int i = 0; i < keysToDelete.size(); i += DELETE_BATCH_SIZE) {
            int end = Math.min(i + DELETE_BATCH_SIZE, keysToDelete.size());
            List<String> batch = keysToDelete.subList(i, end);
            jedis.del(batch.toArray(new String[0]));
        }
    }

    private String getFullKey(String key) {
        return keyPrefix + key;
    }
}
