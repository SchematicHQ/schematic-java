package com.schematic.api.datastream;

import static org.junit.jupiter.api.Assertions.*;

import com.schematic.api.cache.CacheProvider;
import com.schematic.api.cache.LocalCache;
import com.schematic.api.cache.RedisCacheConfig;
import com.schematic.api.types.RulesengineCompany;
import com.schematic.api.types.RulesengineFlag;
import com.schematic.api.types.RulesengineUser;
import java.time.Duration;
import org.junit.jupiter.api.Test;

class DataStreamCacheFactoryTest {

    @Test
    void buildsLocalCacheWhenNoRedisConfig() {
        DatastreamOptions options = DatastreamOptions.builder().build();

        CacheProvider<RulesengineFlag> flagCache = DataStreamCacheFactory.buildFlagCache(options, null, "schematic:");
        CacheProvider<RulesengineCompany> companyCache =
                DataStreamCacheFactory.buildCompanyCache(options, null, "schematic:");
        CacheProvider<RulesengineUser> userCache = DataStreamCacheFactory.buildUserCache(options, null, "schematic:");

        assertNotNull(flagCache);
        assertNotNull(companyCache);
        assertNotNull(userCache);
        assertTrue(flagCache instanceof LocalCache);
        assertTrue(companyCache instanceof LocalCache);
        assertTrue(userCache instanceof LocalCache);
    }

    @Test
    void respectsCustomFlagCacheProvider() {
        LocalCache<RulesengineFlag> customCache = new LocalCache<>(500, Duration.ofMinutes(1));
        DatastreamOptions options =
                DatastreamOptions.builder().flagCacheProvider(customCache).build();

        CacheProvider<RulesengineFlag> flagCache = DataStreamCacheFactory.buildFlagCache(options, null, "schematic:");

        assertSame(customCache, flagCache);
    }

    @Test
    void respectsCustomCompanyCacheProvider() {
        LocalCache<RulesengineCompany> customCache = new LocalCache<>(500, Duration.ofMinutes(1));
        DatastreamOptions options =
                DatastreamOptions.builder().companyCacheProvider(customCache).build();

        CacheProvider<RulesengineCompany> companyCache =
                DataStreamCacheFactory.buildCompanyCache(options, null, "schematic:");

        assertSame(customCache, companyCache);
    }

    @Test
    void respectsCustomUserCacheProvider() {
        LocalCache<RulesengineUser> customCache = new LocalCache<>(500, Duration.ofMinutes(1));
        DatastreamOptions options =
                DatastreamOptions.builder().userCacheProvider(customCache).build();

        CacheProvider<RulesengineUser> userCache = DataStreamCacheFactory.buildUserCache(options, null, "schematic:");

        assertSame(customCache, userCache);
    }

    @Test
    void buildRedisClient_returnsNullForNullConfig() {
        assertNull(DataStreamCacheFactory.buildRedisClient(null));
    }

    @Test
    void redisCacheConfig_storedInOptions() {
        RedisCacheConfig redisConfig =
                RedisCacheConfig.builder().endpoint("redis:6379").build();
        DatastreamOptions options =
                DatastreamOptions.builder().redisCache(redisConfig).build();

        assertNotNull(options.getRedisCacheConfig());
        assertEquals("redis:6379", options.getRedisCacheConfig().getEndpoints().get(0));
    }
}
