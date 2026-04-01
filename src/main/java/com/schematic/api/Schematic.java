package com.schematic.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.schematic.api.cache.CacheProvider;
import com.schematic.api.cache.LocalCache;
import com.schematic.api.core.ClientOptions;
import com.schematic.api.core.Environment;
import com.schematic.api.core.NoOpHttpClient;
import com.schematic.api.core.ObjectMappers;
import com.schematic.api.datastream.DataStreamClient;
import com.schematic.api.datastream.DatastreamOptions;
import com.schematic.api.datastream.WasmRulesEngine;
import com.schematic.api.logger.ConsoleLogger;
import com.schematic.api.logger.SchematicLogger;
import com.schematic.api.resources.features.types.CheckFlagResponse;
import com.schematic.api.types.CheckFlagRequestBody;
import com.schematic.api.types.CheckFlagResponseData;
import com.schematic.api.types.CreateEventRequestBody;
import com.schematic.api.types.EventBody;
import com.schematic.api.types.EventBodyIdentify;
import com.schematic.api.types.EventBodyIdentifyCompany;
import com.schematic.api.types.EventBodyTrack;
import com.schematic.api.types.EventType;
import com.schematic.api.types.RulesengineCheckFlagResult;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class Schematic extends BaseSchematic implements AutoCloseable {
    private final Duration eventBufferInterval;
    private final EventBuffer eventBuffer;
    private final List<CacheProvider<RulesengineCheckFlagResult>> flagCheckCacheProviders;
    private final Map<String, Boolean> flagDefaults;
    private final SchematicLogger logger;
    private final String apiKey;
    private final Thread shutdownHook;
    private final boolean offline;
    private final DataStreamClient dataStreamClient;
    private final DatastreamOptions datastreamOptions;

    private Schematic(Builder builder) {
        super(buildClientOptions(builder.apiKey, builder));

        this.apiKey = builder.apiKey;
        this.eventBufferInterval =
                builder.eventBufferInterval != null ? builder.eventBufferInterval : Duration.ofMillis(5000);
        this.logger = builder.logger != null ? builder.logger : new ConsoleLogger();
        this.flagDefaults = builder.flagDefaults != null ? builder.flagDefaults : new HashMap<>();
        this.offline = builder.offline;
        this.flagCheckCacheProviders = builder.cacheProviders != null
                ? builder.cacheProviders
                : Collections.singletonList(new LocalCache<RulesengineCheckFlagResult>());
        this.datastreamOptions = builder.datastreamOptions;

        this.eventBuffer = new EventBuffer(
                super.events(),
                this.logger,
                builder.eventBufferMaxSize,
                builder.eventBufferInterval != null ? builder.eventBufferInterval : Duration.ofMillis(5000));

        // Initialize DataStream client if options are provided
        if (this.datastreamOptions != null && !this.offline) {
            String basePath = builder.basePath != null ? builder.basePath : "https://api.schematichq.com";

            // Initialize WASM rules engine for local flag evaluation
            WasmRulesEngine rulesEngine = null;
            try {
                rulesEngine = new WasmRulesEngine(this.logger);
                rulesEngine.initialize();
            } catch (Exception e) {
                this.logger.warn(
                        "WASM rules engine not available, flag checks will fall back to API: " + e.getMessage());
                rulesEngine = null;
            }

            this.dataStreamClient =
                    new DataStreamClient(this.datastreamOptions, this.apiKey, basePath, this.logger, rulesEngine);
            this.dataStreamClient.start();
        } else {
            this.dataStreamClient = null;
        }

        this.shutdownHook = new Thread(
                () -> {
                    try {
                        if (this.dataStreamClient != null) {
                            this.dataStreamClient.close();
                        }
                        this.eventBuffer.close();
                    } catch (Exception e) {
                        logger.error("Error during Schematic shutdown: " + e.getMessage());
                    }
                },
                "SchematicShutdownHook");

        Runtime.getRuntime().addShutdownHook(this.shutdownHook);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String apiKey;
        private SchematicLogger logger;
        private Map<String, Boolean> flagDefaults;
        private List<CacheProvider<RulesengineCheckFlagResult>> cacheProviders;
        private boolean offline;
        private Duration eventBufferInterval;
        private int eventBufferMaxSize = 100;
        private String basePath;
        private Map<String, String> headers;
        private DatastreamOptions datastreamOptions;

        public Builder apiKey(String apiKey) {
            this.apiKey = apiKey;
            return this;
        }

        public Builder logger(SchematicLogger logger) {
            this.logger = logger;
            return this;
        }

        public Builder flagDefaults(Map<String, Boolean> flagDefaults) {
            this.flagDefaults = flagDefaults;
            return this;
        }

        public Builder cacheProviders(List<CacheProvider<RulesengineCheckFlagResult>> cacheProviders) {
            this.cacheProviders = cacheProviders;
            return this;
        }

        public Builder offline(boolean offline) {
            this.offline = offline;
            return this;
        }

        public Builder eventBufferInterval(Duration eventBufferInterval) {
            this.eventBufferInterval = eventBufferInterval;
            return this;
        }

        public Builder eventBufferMaxSize(int eventBufferMaxSize) {
            this.eventBufferMaxSize = eventBufferMaxSize;
            return this;
        }

        public Builder basePath(String basePath) {
            this.basePath = basePath;
            return this;
        }

        public Builder headers(Map<String, String> headers) {
            this.headers = headers;
            return this;
        }

        public Builder datastreamOptions(DatastreamOptions datastreamOptions) {
            this.datastreamOptions = datastreamOptions;
            return this;
        }

        public Schematic build() {
            if (apiKey == null) {
                throw new IllegalStateException("API key must be set");
            }
            return new Schematic(this);
        }
    }

    private static ClientOptions buildClientOptions(String apiKey, Builder builder) {
        String basePath = builder.basePath != null ? builder.basePath : "https://api.schematichq.com";
        ClientOptions.Builder clientOptionsBuilder = ClientOptions.builder()
                .environment(Environment.custom(basePath))
                .addHeader("X-Schematic-Api-Key", apiKey)
                .addHeader("Content-Type", "application/json");

        if (builder.offline) {
            clientOptionsBuilder.httpClient(new NoOpHttpClient());
        }

        return clientOptionsBuilder.build();
    }

    public List<CacheProvider<RulesengineCheckFlagResult>> getFlagCheckCacheProviders() {
        return flagCheckCacheProviders;
    }

    public String getApiKey() {
        return this.apiKey;
    }

    public Duration getEventBufferInterval() {
        return this.eventBufferInterval;
    }

    public boolean isOffline() {
        return this.offline;
    }

    /**
     * Returns the DataStream client, or null if datastream is not configured.
     */
    public DataStreamClient getDataStreamClient() {
        return this.dataStreamClient;
    }

    /**
     * Returns whether the client is operating in replicator mode.
     */
    public boolean isReplicatorMode() {
        return this.dataStreamClient != null && this.dataStreamClient.isReplicatorMode();
    }

    /**
     * Returns whether the datastream connection is active and ready.
     */
    public boolean isDatastreamConnected() {
        return this.dataStreamClient != null && this.dataStreamClient.isConnected();
    }

    /**
     * Checks a feature flag, returning a boolean value.
     *
     * <p>If datastream is configured and connected, evaluates the flag locally using cached
     * data and the rules engine. Falls back to the API if datastream is unavailable or
     * evaluation fails.
     */
    public boolean checkFlag(String flagKey, Map<String, String> company, Map<String, String> user) {
        return checkFlagWithEntitlement(flagKey, company, user).getValue();
    }

    /**
     * Checks a feature flag, returning the full evaluation result including metadata
     * such as the evaluation reason, rule ID, and entitlement information.
     *
     * <p>Priority order:
     * <ol>
     *   <li>DataStream evaluation (if configured and connected)</li>
     *   <li>API call with result caching (fallback)</li>
     *   <li>Flag default value (if all else fails)</li>
     * </ol>
     */
    public RulesengineCheckFlagResult checkFlagWithEntitlement(
            String flagKey, Map<String, String> company, Map<String, String> user) {
        if (offline) {
            return RulesengineCheckFlagResult.builder()
                    .flagKey(flagKey)
                    .reason("flag default")
                    .value(getFlagDefault(flagKey))
                    .build();
        }

        // Try datastream first if available
        if (dataStreamClient != null && dataStreamClient.isConnected()) {
            try {
                return dataStreamClient.checkFlag(flagKey, company, user);
            } catch (Exception e) {
                logger.debug(
                        "Datastream flag check failed for " + flagKey + ", falling back to API: " + e.getMessage());
            }
        }

        // Fall back to API
        return checkFlagViaApi(flagKey, company, user);
    }

    /**
     * Checks a flag via the Schematic API, using the flag check result cache.
     */
    private RulesengineCheckFlagResult checkFlagViaApi(
            String flagKey, Map<String, String> company, Map<String, String> user) {
        try {
            String cacheKey = buildCacheKey(flagKey, company, user);

            // Check flag check result cache
            for (CacheProvider<RulesengineCheckFlagResult> provider : flagCheckCacheProviders) {
                RulesengineCheckFlagResult cached = provider.get(cacheKey);
                if (cached != null) {
                    return cached;
                }
            }

            // Make API call
            CheckFlagRequestBody request =
                    CheckFlagRequestBody.builder().company(company).user(user).build();

            CheckFlagResponse response = features().checkFlag(flagKey, request);
            CheckFlagResponseData data = response.getData();

            RulesengineCheckFlagResult result = RulesengineCheckFlagResult.builder()
                    .flagKey(flagKey)
                    .reason(data.getReason())
                    .value(data.getValue())
                    .flagId(data.getFlagId().orElse(null))
                    .companyId(data.getCompanyId().orElse(null))
                    .userId(data.getUserId().orElse(null))
                    .ruleId(data.getRuleId().orElse(null))
                    .build();

            // Update flag check result cache
            for (CacheProvider<RulesengineCheckFlagResult> provider : flagCheckCacheProviders) {
                provider.set(cacheKey, result);
            }

            return result;
        } catch (Exception e) {
            logger.error("Error checking flag via API: " + e.getMessage());
            return RulesengineCheckFlagResult.builder()
                    .flagKey(flagKey)
                    .reason("flag default")
                    .value(getFlagDefault(flagKey))
                    .err(e.getMessage())
                    .build();
        }
    }

    public void identify(
            Map<String, String> keys, EventBodyIdentifyCompany company, String name, Map<String, Object> traits) {
        if (offline) return;

        try {
            EventBodyIdentify body = EventBodyIdentify.builder()
                    .keys(keys)
                    .company(company)
                    .name(name)
                    .traits(objectMapToJsonNode(traits))
                    .build();

            CreateEventRequestBody event = CreateEventRequestBody.builder()
                    .eventType(EventType.IDENTIFY)
                    .body(EventBody.of(body))
                    .sentAt(OffsetDateTime.now())
                    .build();

            eventBuffer.push(event);
        } catch (Exception e) {
            logger.error("Error sending identify event: " + e.getMessage());
        }
    }

    public void track(
            String eventName, Map<String, String> company, Map<String, String> user, Map<String, Object> traits) {
        track(eventName, company, user, traits, 1);
    }

    public void track(
            String eventName,
            Map<String, String> company,
            Map<String, String> user,
            Map<String, Object> traits,
            Integer quantity) {
        if (offline) return;

        try {
            EventBodyTrack body = EventBodyTrack.builder()
                    .event(eventName)
                    .company(company)
                    .user(user)
                    .traits(objectMapToJsonNode(traits))
                    .quantity(quantity)
                    .build();

            CreateEventRequestBody event = CreateEventRequestBody.builder()
                    .eventType(EventType.TRACK)
                    .body(EventBody.of(body))
                    .sentAt(OffsetDateTime.now())
                    .build();

            eventBuffer.push(event);
        } catch (Exception e) {
            logger.error("Error sending track event: " + e.getMessage());
        }
    }

    @Override
    public void close() {
        try {
            // Remove shutdown hook if we're closing explicitly
            try {
                Runtime.getRuntime().removeShutdownHook(this.shutdownHook);
            } catch (IllegalStateException e) {
                // Shutdown is already in progress, hook will run automatically
            }

            if (dataStreamClient != null) {
                dataStreamClient.close();
            }
            eventBuffer.close();
        } catch (Exception e) {
            logger.error("Error closing Schematic client: " + e.getMessage());
        }
    }

    private boolean getFlagDefault(String flagKey) {
        return flagDefaults.getOrDefault(flagKey, false);
    }

    private String buildCacheKey(String flagKey, Map<String, String> company, Map<String, String> user) {
        StringBuilder key = new StringBuilder(flagKey);

        if (company != null && !company.isEmpty()) {
            key.append(":c-").append(serializeMap(company));
        }

        if (user != null && !user.isEmpty()) {
            key.append(":u-").append(serializeMap(user));
        }

        return key.toString();
    }

    private Map<String, JsonNode> objectMapToJsonNode(Map<String, Object> map) {
        Map<String, JsonNode> result = new HashMap<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            result.put(entry.getKey(), ObjectMappers.JSON_MAPPER.valueToTree(entry.getValue()));
        }
        return result;
    }

    private String serializeMap(Map<String, String> map) {
        return map.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(e -> e.getKey() + "=" + e.getValue())
                .collect(Collectors.joining(";"));
    }
}
