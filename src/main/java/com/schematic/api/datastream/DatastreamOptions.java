package com.schematic.api.datastream;

import com.schematic.api.cache.CacheProvider;
import com.schematic.api.cache.LocalCache;
import com.schematic.api.types.RulesengineCompany;
import com.schematic.api.types.RulesengineFlag;
import com.schematic.api.types.RulesengineUser;
import java.time.Duration;

/**
 * Configuration options for the Schematic DataStream client.
 *
 * <p>DataStream provides real-time flag and entity data via persistent WebSocket connections
 * or via an external replicator service that populates a shared cache.
 */
public class DatastreamOptions {

    private static final Duration DEFAULT_CACHE_TTL = Duration.ofHours(24);
    private static final String DEFAULT_REPLICATOR_HEALTH_URL = "http://localhost:8090/ready";
    private static final Duration DEFAULT_REPLICATOR_HEALTH_CHECK_INTERVAL = Duration.ofSeconds(30);

    private final Duration cacheTTL;
    private final boolean replicatorMode;
    private final String replicatorHealthUrl;
    private final Duration replicatorHealthCheckInterval;
    private final CacheProvider<RulesengineFlag> flagCacheProvider;
    private final CacheProvider<RulesengineCompany> companyCacheProvider;
    private final CacheProvider<RulesengineUser> userCacheProvider;

    private DatastreamOptions(Builder builder) {
        this.cacheTTL = builder.cacheTTL != null ? builder.cacheTTL : DEFAULT_CACHE_TTL;
        this.replicatorMode = builder.replicatorMode;
        this.replicatorHealthUrl =
                builder.replicatorHealthUrl != null ? builder.replicatorHealthUrl : DEFAULT_REPLICATOR_HEALTH_URL;
        this.replicatorHealthCheckInterval = builder.replicatorHealthCheckInterval != null
                ? builder.replicatorHealthCheckInterval
                : DEFAULT_REPLICATOR_HEALTH_CHECK_INTERVAL;
        this.flagCacheProvider =
                builder.flagCacheProvider != null ? builder.flagCacheProvider : new LocalCache<>(10_000, this.cacheTTL);
        this.companyCacheProvider = builder.companyCacheProvider != null
                ? builder.companyCacheProvider
                : new LocalCache<>(10_000, this.cacheTTL);
        this.userCacheProvider =
                builder.userCacheProvider != null ? builder.userCacheProvider : new LocalCache<>(10_000, this.cacheTTL);
    }

    public static Builder builder() {
        return new Builder();
    }

    public Duration getCacheTTL() {
        return cacheTTL;
    }

    public boolean isReplicatorMode() {
        return replicatorMode;
    }

    public String getReplicatorHealthUrl() {
        return replicatorHealthUrl;
    }

    public Duration getReplicatorHealthCheckInterval() {
        return replicatorHealthCheckInterval;
    }

    public CacheProvider<RulesengineFlag> getFlagCacheProvider() {
        return flagCacheProvider;
    }

    public CacheProvider<RulesengineCompany> getCompanyCacheProvider() {
        return companyCacheProvider;
    }

    public CacheProvider<RulesengineUser> getUserCacheProvider() {
        return userCacheProvider;
    }

    public static class Builder {
        private Duration cacheTTL;
        private boolean replicatorMode;
        private String replicatorHealthUrl;
        private Duration replicatorHealthCheckInterval;
        private CacheProvider<RulesengineFlag> flagCacheProvider;
        private CacheProvider<RulesengineCompany> companyCacheProvider;
        private CacheProvider<RulesengineUser> userCacheProvider;

        public Builder cacheTTL(Duration cacheTTL) {
            this.cacheTTL = cacheTTL;
            return this;
        }

        public Builder replicatorMode(boolean replicatorMode) {
            this.replicatorMode = replicatorMode;
            return this;
        }

        public Builder replicatorHealthUrl(String replicatorHealthUrl) {
            this.replicatorHealthUrl = replicatorHealthUrl;
            return this;
        }

        public Builder replicatorHealthCheckInterval(Duration interval) {
            this.replicatorHealthCheckInterval = interval;
            return this;
        }

        public Builder flagCacheProvider(CacheProvider<RulesengineFlag> flagCacheProvider) {
            this.flagCacheProvider = flagCacheProvider;
            return this;
        }

        public Builder companyCacheProvider(CacheProvider<RulesengineCompany> companyCacheProvider) {
            this.companyCacheProvider = companyCacheProvider;
            return this;
        }

        public Builder userCacheProvider(CacheProvider<RulesengineUser> userCacheProvider) {
            this.userCacheProvider = userCacheProvider;
            return this;
        }

        /**
         * Convenience method to enable replicator mode with a health check URL.
         */
        public Builder withReplicatorMode(String healthUrl) {
            this.replicatorMode = true;
            this.replicatorHealthUrl = healthUrl;
            return this;
        }

        public DatastreamOptions build() {
            return new DatastreamOptions(this);
        }
    }
}
