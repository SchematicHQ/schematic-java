package com.schematic.api.cache;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

class RedisCacheConfigTest {

    @Test
    void defaults() {
        RedisCacheConfig config = RedisCacheConfig.builder().build();

        assertEquals("localhost:6379", config.getEndpoints().get(0));
        assertEquals("schematic:", config.getKeyPrefix());
        assertEquals(0, config.getDatabase());
        assertFalse(config.isSsl());
        assertNull(config.getUsername());
        assertNull(config.getPassword());
        assertEquals(Duration.ofSeconds(5), config.getConnectTimeout());
        assertEquals(Duration.ofSeconds(3), config.getReadTimeout());
        assertEquals(8, config.getMaxPoolSize());
    }

    @Test
    void singleEndpoint() {
        RedisCacheConfig config =
                RedisCacheConfig.builder().endpoint("redis.example.com:6380").build();

        assertEquals(1, config.getEndpoints().size());
        assertEquals("redis.example.com:6380", config.getEndpoints().get(0));
    }

    @Test
    void multipleEndpoints() {
        RedisCacheConfig config = RedisCacheConfig.builder()
                .endpoints(Arrays.asList("node1:6379", "node2:6379"))
                .build();

        assertEquals(2, config.getEndpoints().size());
    }

    @Test
    void authConfig() {
        RedisCacheConfig config = RedisCacheConfig.builder()
                .username("myuser")
                .password("mypass")
                .database(2)
                .ssl(true)
                .build();

        assertEquals("myuser", config.getUsername());
        assertEquals("mypass", config.getPassword());
        assertEquals(2, config.getDatabase());
        assertTrue(config.isSsl());
    }

    @Test
    void customKeyPrefix() {
        RedisCacheConfig config = RedisCacheConfig.builder().keyPrefix("myapp:").build();

        assertEquals("myapp:", config.getKeyPrefix());
    }

    @Test
    void customTimeouts() {
        RedisCacheConfig config = RedisCacheConfig.builder()
                .connectTimeout(Duration.ofSeconds(10))
                .readTimeout(Duration.ofSeconds(8))
                .maxPoolSize(16)
                .build();

        assertEquals(Duration.ofSeconds(10), config.getConnectTimeout());
        assertEquals(Duration.ofSeconds(8), config.getReadTimeout());
        assertEquals(16, config.getMaxPoolSize());
    }
}
