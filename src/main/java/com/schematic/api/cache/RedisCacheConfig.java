package com.schematic.api.cache;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

/**
 * Configuration for connecting to Redis. Pass this to
 * {@link com.schematic.api.datastream.DatastreamOptions.Builder#redisCache(RedisCacheConfig)}
 * and the SDK will create and manage the Redis connection internally.
 */
public class RedisCacheConfig {

    private final List<String> endpoints;
    private final String username;
    private final String password;
    private final int database;
    private final boolean ssl;
    private final String keyPrefix;
    private final Duration connectTimeout;
    private final Duration readTimeout;
    private final int maxPoolSize;

    private RedisCacheConfig(Builder builder) {
        this.endpoints = builder.endpoints != null ? builder.endpoints : Collections.singletonList("localhost:6379");
        this.username = builder.username;
        this.password = builder.password;
        this.database = builder.database;
        this.ssl = builder.ssl;
        this.keyPrefix = builder.keyPrefix != null ? builder.keyPrefix : "schematic:";
        this.connectTimeout = builder.connectTimeout != null ? builder.connectTimeout : Duration.ofSeconds(5);
        this.readTimeout = builder.readTimeout != null ? builder.readTimeout : Duration.ofSeconds(3);
        this.maxPoolSize = builder.maxPoolSize > 0 ? builder.maxPoolSize : 8;
    }

    public static Builder builder() {
        return new Builder();
    }

    public List<String> getEndpoints() {
        return endpoints;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getDatabase() {
        return database;
    }

    public boolean isSsl() {
        return ssl;
    }

    public String getKeyPrefix() {
        return keyPrefix;
    }

    public Duration getConnectTimeout() {
        return connectTimeout;
    }

    public Duration getReadTimeout() {
        return readTimeout;
    }

    public int getMaxPoolSize() {
        return maxPoolSize;
    }

    public static class Builder {
        private List<String> endpoints;
        private String username;
        private String password;
        private int database;
        private boolean ssl;
        private String keyPrefix;
        private Duration connectTimeout;
        private Duration readTimeout;
        private int maxPoolSize;

        /**
         * Redis server endpoints in "host:port" format. Defaults to ["localhost:6379"].
         */
        public Builder endpoints(List<String> endpoints) {
            this.endpoints = endpoints;
            return this;
        }

        /**
         * Single Redis server endpoint. Convenience for single-node setups.
         */
        public Builder endpoint(String endpoint) {
            this.endpoints = Collections.singletonList(endpoint);
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder database(int database) {
            this.database = database;
            return this;
        }

        public Builder ssl(boolean ssl) {
            this.ssl = ssl;
            return this;
        }

        /**
         * Key prefix for all Redis cache entries. Defaults to "schematic:".
         */
        public Builder keyPrefix(String keyPrefix) {
            this.keyPrefix = keyPrefix;
            return this;
        }

        public Builder connectTimeout(Duration connectTimeout) {
            this.connectTimeout = connectTimeout;
            return this;
        }

        public Builder readTimeout(Duration readTimeout) {
            this.readTimeout = readTimeout;
            return this;
        }

        public Builder maxPoolSize(int maxPoolSize) {
            this.maxPoolSize = maxPoolSize;
            return this;
        }

        public RedisCacheConfig build() {
            return new RedisCacheConfig(this);
        }
    }
}
