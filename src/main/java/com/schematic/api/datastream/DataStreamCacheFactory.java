package com.schematic.api.datastream;

import com.schematic.api.cache.CacheProvider;
import com.schematic.api.cache.LocalCache;
import com.schematic.api.cache.RedisCacheConfig;
import com.schematic.api.cache.RedisCacheProvider;
import com.schematic.api.types.RulesengineCompany;
import com.schematic.api.types.RulesengineFlag;
import com.schematic.api.types.RulesengineUser;
import java.time.Duration;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.Connection;
import redis.clients.jedis.DefaultJedisClientConfig;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisPooled;

/**
 * Factory for creating cache providers used by the DataStream client.
 * Handles Redis client creation and cache provider selection logic,
 * keeping DatastreamOptions as a pure configuration holder.
 */
final class DataStreamCacheFactory {

    private static final int DEFAULT_CACHE_SIZE = 1_000;
    private static final Duration MAX_CACHE_TTL = Duration.ofDays(30);

    private DataStreamCacheFactory() {}

    static CacheProvider<RulesengineFlag> buildFlagCache(
            DatastreamOptions options, JedisPooled redisClient, String keyPrefix) {
        if (options.getFlagCacheProvider() != null) {
            return options.getFlagCacheProvider();
        }
        // Flag cache uses the greater of maxCacheTTL or configured TTL
        Duration flagTTL = options.getCacheTTL().compareTo(MAX_CACHE_TTL) > 0 ? options.getCacheTTL() : MAX_CACHE_TTL;
        if (redisClient != null) {
            return new RedisCacheProvider<>(redisClient, flagTTL, keyPrefix, RulesengineFlag.class);
        }
        return new LocalCache<>(DEFAULT_CACHE_SIZE, flagTTL);
    }

    static CacheProvider<RulesengineCompany> buildCompanyCache(
            DatastreamOptions options, JedisPooled redisClient, String keyPrefix) {
        if (options.getCompanyCacheProvider() != null) {
            return options.getCompanyCacheProvider();
        }
        if (redisClient != null) {
            return new RedisCacheProvider<>(redisClient, options.getCacheTTL(), keyPrefix, RulesengineCompany.class);
        }
        return new LocalCache<>(DEFAULT_CACHE_SIZE, options.getCacheTTL());
    }

    static CacheProvider<RulesengineUser> buildUserCache(
            DatastreamOptions options, JedisPooled redisClient, String keyPrefix) {
        if (options.getUserCacheProvider() != null) {
            return options.getUserCacheProvider();
        }
        if (redisClient != null) {
            return new RedisCacheProvider<>(redisClient, options.getCacheTTL(), keyPrefix, RulesengineUser.class);
        }
        return new LocalCache<>(DEFAULT_CACHE_SIZE, options.getCacheTTL());
    }

    static JedisPooled buildRedisClient(RedisCacheConfig config) {
        if (config == null) {
            return null;
        }

        String endpoint = config.getEndpoints().get(0);

        // Strip redis:// or rediss:// URI scheme if present
        String hostPort = endpoint;
        if (hostPort.startsWith("redis://")) {
            hostPort = hostPort.substring("redis://".length());
        } else if (hostPort.startsWith("rediss://")) {
            hostPort = hostPort.substring("rediss://".length());
        }

        // Strip trailing path/slash (e.g. "localhost:6379/0" -> "localhost:6379")
        int slashIdx = hostPort.indexOf('/');
        if (slashIdx >= 0) {
            hostPort = hostPort.substring(0, slashIdx);
        }

        String[] parts = hostPort.split(":");
        String host = parts[0];
        int port = parts.length > 1 ? Integer.parseInt(parts[1]) : 6379;

        DefaultJedisClientConfig.Builder clientConfig = DefaultJedisClientConfig.builder()
                .connectionTimeoutMillis((int) config.getConnectTimeout().toMillis())
                .socketTimeoutMillis((int) config.getReadTimeout().toMillis())
                .database(config.getDatabase())
                .ssl(config.isSsl());

        if (config.getUsername() != null) {
            clientConfig.user(config.getUsername());
        }
        if (config.getPassword() != null) {
            clientConfig.password(config.getPassword());
        }

        GenericObjectPoolConfig<Connection> poolConfig = new GenericObjectPoolConfig<>();
        poolConfig.setMaxTotal(config.getMaxPoolSize());

        return new JedisPooled(poolConfig, new HostAndPort(host, port), clientConfig.build());
    }
}
