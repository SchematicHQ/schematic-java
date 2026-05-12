package com.schematic.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.schematic.api.cache.CacheProvider;
import com.schematic.api.cache.LocalCache;
import com.schematic.api.core.ClientOptions;
import com.schematic.api.core.Environment;
import com.schematic.api.core.NoOpHttpClient;
import com.schematic.api.core.ObjectMappers;
import com.schematic.api.datastream.DataStreamClient;
import com.schematic.api.datastream.DataStreamException;
import com.schematic.api.datastream.DatastreamOptions;
import com.schematic.api.datastream.WasmRulesEngine;
import com.schematic.api.logger.ConsoleLogger;
import com.schematic.api.logger.SchematicLogger;
import com.schematic.api.resources.features.types.CheckFlagResponse;
import com.schematic.api.resources.features.types.CheckFlagsResponse;
import com.schematic.api.types.CheckFlagRequestBody;
import com.schematic.api.types.CheckFlagResponseData;
import com.schematic.api.types.CreateEventRequestBody;
import com.schematic.api.types.EventBody;
import com.schematic.api.types.EventBodyFlagCheck;
import com.schematic.api.types.EventBodyIdentify;
import com.schematic.api.types.EventBodyIdentifyCompany;
import com.schematic.api.types.EventBodyTrack;
import com.schematic.api.types.EventType;
import com.schematic.api.types.RulesengineCheckFlagResult;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.ArrayList;
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
    private final HttpEventSender eventSender;
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

        this.eventSender = new HttpEventSender(null, this.apiKey, builder.eventCaptureBaseUrl, this.logger);
        this.eventBuffer = new EventBuffer(
                eventSender,
                this.logger,
                builder.eventBufferMaxSize,
                builder.eventBufferInterval != null ? builder.eventBufferInterval : Duration.ofMillis(5000));

        // Initialize DataStream client if options are provided
        if (this.datastreamOptions != null && !this.offline) {
            requireJava11ForDatastream();
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

            this.dataStreamClient = new DataStreamClient(
                    this.datastreamOptions, this.apiKey, basePath, this.logger, rulesEngine, resolveSdkVersion());
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
                        this.eventSender.close();
                    } catch (Exception e) {
                        logger.error("Error during Schematic shutdown: " + e.getMessage());
                    }
                },
                "SchematicShutdownHook");

        Runtime.getRuntime().addShutdownHook(this.shutdownHook);
    }

    // The SDK version is published by Fern into ClientOptions's X-Fern-SDK-Version
    // header on every regen — same source of truth as build.gradle's `version`.
    // Reading from there means no parallel constant for us to hand-maintain.
    // Resolved once at construction and passed through to the WebSocket client.
    private String resolveSdkVersion() {
        try {
            String version = this.clientOptions.headers(null).get("X-Fern-SDK-Version");
            if (version != null && !version.isEmpty()) {
                return version;
            }
        } catch (Exception e) {
            logger.debug("Failed to resolve SDK version: " + e.getMessage());
        }
        return null;
    }

    // Datastream depends on the WASM runtime (Chicory), which requires Java 11+.
    // The check lives here so it fires before any datastream class is resolved —
    // otherwise Java 8 users hit a cryptic UnsupportedClassVersionError on Chicory.
    private static void requireJava11ForDatastream() {
        String version = System.getProperty("java.specification.version", "");
        int major;
        try {
            major = version.startsWith("1.") ? Integer.parseInt(version.substring(2)) : Integer.parseInt(version);
        } catch (NumberFormatException e) {
            return;
        }
        if (major < 11) {
            throw new DataStreamException(
                    "Schematic datastream requires Java 11 or later (detected Java "
                            + version
                            + "). Core SDK features (flag checks, events, webhooks) are compatible with Java 8+, but datastream and local flag evaluation require Java 11+.");
        }
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
        private String eventCaptureBaseUrl;

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

        public Builder eventCaptureBaseUrl(String eventCaptureBaseUrl) {
            this.eventCaptureBaseUrl = eventCaptureBaseUrl;
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
     * Sets a default value for a specific flag at runtime.
     */
    public void setFlagDefault(String flagKey, boolean value) {
        flagDefaults.put(flagKey, value);
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
            return defaultFlagResult(flagKey, "flag default", null);
        }

        RulesengineCheckFlagResult dsResult = tryDatastreamCheckFlag(flagKey, company, user);
        if (dsResult != null) {
            enqueueFlagCheckEvent(flagKey, dsResult, company, user);
            return dsResult;
        }

        return checkFlagViaApi(flagKey, company, user);
    }

    private RulesengineCheckFlagResult defaultFlagResult(String flagKey, String reason, String err) {
        return RulesengineCheckFlagResult.builder()
                .flagKey(flagKey)
                .reason(reason)
                .value(getFlagDefault(flagKey))
                .err(err)
                .build();
    }

    /**
     * Attempts to evaluate a flag via the datastream client. Returns the result on
     * success, or {@code null} if datastream is not configured/connected or evaluation
     * failed. Callers are responsible for emitting a {@code flag_check} event when
     * appropriate — single-flag checks do, bulk checks do not.
     */
    private RulesengineCheckFlagResult tryDatastreamCheckFlag(
            String flagKey, Map<String, String> company, Map<String, String> user) {
        if (dataStreamClient == null || !dataStreamClient.isConnected()) {
            return null;
        }
        try {
            return dataStreamClient.checkFlag(flagKey, company, user);
        } catch (Exception e) {
            logger.debug("Datastream flag check failed for " + flagKey + ", falling back to API: " + e.getMessage());
            return null;
        }
    }

    private RulesengineCheckFlagResult getCachedFlag(
            String flagKey, Map<String, String> company, Map<String, String> user) {
        String cacheKey = buildCacheKey(flagKey, company, user);
        for (CacheProvider<RulesengineCheckFlagResult> provider : flagCheckCacheProviders) {
            RulesengineCheckFlagResult cached = provider.get(cacheKey);
            if (cached != null) {
                return cached;
            }
        }
        return null;
    }

    private void cacheFlag(
            String flagKey, RulesengineCheckFlagResult result, Map<String, String> company, Map<String, String> user) {
        String cacheKey = buildCacheKey(flagKey, company, user);
        for (CacheProvider<RulesengineCheckFlagResult> provider : flagCheckCacheProviders) {
            provider.set(cacheKey, result);
        }
    }

    private void enqueueFlagCheckEvent(
            String flagKey, RulesengineCheckFlagResult result, Map<String, String> company, Map<String, String> user) {
        try {
            EventBodyFlagCheck flagCheckBody = EventBodyFlagCheck.builder()
                    .flagKey(flagKey)
                    .reason(result.getReason())
                    .value(result.getValue())
                    .companyId(result.getCompanyId().orElse(null))
                    .userId(result.getUserId().orElse(null))
                    .flagId(result.getFlagId().orElse(null))
                    .ruleId(result.getRuleId().orElse(null))
                    .reqCompany(company)
                    .reqUser(user)
                    .error(result.getErr().orElse(null))
                    .build();

            CreateEventRequestBody event = CreateEventRequestBody.builder()
                    .eventType(EventType.FLAG_CHECK)
                    .body(EventBody.of(flagCheckBody))
                    .sentAt(OffsetDateTime.now())
                    .build();

            eventBuffer.push(event);
        } catch (Exception e) {
            logger.error("Failed to enqueue flag_check event: " + e.getMessage());
        }
    }

    /**
     * Checks multiple feature flags, returning the full evaluation results in the
     * same order as the requested keys.
     *
     * <p>Evaluation order:
     * <ol>
     *   <li>Offline mode → return flag defaults for the requested keys</li>
     *   <li>DataStream / replicator (if configured and connected) → evaluate each key
     *       locally; falls back to the API if any key fails</li>
     *   <li>Otherwise → look up each requested key in the result cache; if any are
     *       missing, issue a single bulk {@code features.checkFlags} API call to fetch
     *       fresh values, refresh the cache, and merge the results</li>
     * </ol>
     *
     * <p>If {@code flagKeys} is null or empty, the bulk API is called once to discover
     * all flags available for the given context.
     */
    public List<RulesengineCheckFlagResult> checkFlags(
            List<String> flagKeys, Map<String, String> company, Map<String, String> user) {
        // 1. Offline → return flag defaults for the requested keys. If no keys were
        // provided, fall back to every key in the configured flag defaults map.
        if (offline) {
            Iterable<String> keysToReturn = (flagKeys == null || flagKeys.isEmpty()) ? flagDefaults.keySet() : flagKeys;
            List<RulesengineCheckFlagResult> results = new ArrayList<>();
            for (String key : keysToReturn) {
                if (key == null) continue;
                results.add(defaultFlagResult(key, "Offline mode - using default value", null));
            }
            return results;
        }

        // 2. DataStream/replicator path: evaluate each key; on any failure fall back to API.
        if (dataStreamClient != null && dataStreamClient.isConnected() && flagKeys != null && !flagKeys.isEmpty()) {
            List<RulesengineCheckFlagResult> dsResults = new ArrayList<>(flagKeys.size());
            boolean dsOk = true;
            for (String key : flagKeys) {
                if (key == null) continue;
                RulesengineCheckFlagResult result = tryDatastreamCheckFlag(key, company, user);
                if (result == null) {
                    dsOk = false;
                    break;
                }
                dsResults.add(result);
            }
            if (dsOk) {
                return dsResults;
            }
        }

        // 3. Cache + bulk API path.
        try {
            CheckFlagRequestBody request =
                    CheckFlagRequestBody.builder().company(company).user(user).build();

            // No keys → discover all flags for the context via the bulk API.
            if (flagKeys == null || flagKeys.isEmpty()) {
                CheckFlagsResponse response = features().checkFlags(request);
                List<CheckFlagResponseData> flags = response.getData().getFlags();
                List<RulesengineCheckFlagResult> all = new ArrayList<>(flags.size());
                for (CheckFlagResponseData f : flags) {
                    all.add(toRulesengineResult(f));
                }
                return all;
            }

            // Look up each key in the cache; track which are missing.
            Map<String, RulesengineCheckFlagResult> cachedResults = new HashMap<>();
            boolean anyMissing = false;
            for (String key : flagKeys) {
                if (key == null) continue;
                RulesengineCheckFlagResult hit = getCachedFlag(key, company, user);
                if (hit != null) {
                    cachedResults.put(key, hit);
                } else {
                    anyMissing = true;
                }
            }

            // All cached → return without an API call.
            if (!anyMissing) {
                List<RulesengineCheckFlagResult> results = new ArrayList<>(flagKeys.size());
                for (String key : flagKeys) {
                    if (key == null) continue;
                    results.add(cachedResults.get(key));
                }
                return results;
            }

            // Cache miss → one bulk API call; refresh cache for everything returned.
            Map<String, RulesengineCheckFlagResult> apiResults = new HashMap<>();
            CheckFlagsResponse response = features().checkFlags(request);
            for (CheckFlagResponseData f : response.getData().getFlags()) {
                RulesengineCheckFlagResult result = toRulesengineResult(f);
                apiResults.put(f.getFlag(), result);
                cacheFlag(f.getFlag(), result, company, user);
            }

            // Build results in requested key order. Prefer fresh API values, fall back
            // to the configured flag default for any keys missing from the response.
            List<RulesengineCheckFlagResult> results = new ArrayList<>(flagKeys.size());
            for (String key : flagKeys) {
                if (key == null) continue;
                RulesengineCheckFlagResult fresh = apiResults.get(key);
                if (fresh != null) {
                    results.add(fresh);
                } else {
                    results.add(defaultFlagResult(key, "Flag not found - using default value", null));
                }
            }
            return results;
        } catch (Exception e) {
            logger.error("Error checking flags via API: " + e.getMessage());
            List<RulesengineCheckFlagResult> fallback = new ArrayList<>();
            if (flagKeys != null) {
                for (String key : flagKeys) {
                    if (key == null) continue;
                    fallback.add(defaultFlagResult(
                            key, "Error occurred - using default value: " + e.getMessage(), e.getMessage()));
                }
            }
            return fallback;
        }
    }

    private RulesengineCheckFlagResult toRulesengineResult(CheckFlagResponseData data) {
        return RulesengineCheckFlagResult.builder()
                .flagKey(data.getFlag())
                .reason(data.getReason())
                .value(data.getValue())
                .flagId(data.getFlagId().orElse(null))
                .companyId(data.getCompanyId().orElse(null))
                .userId(data.getUserId().orElse(null))
                .ruleId(data.getRuleId().orElse(null))
                .build();
    }

    /**
     * Checks a flag via the Schematic API, using the flag check result cache.
     */
    private RulesengineCheckFlagResult checkFlagViaApi(
            String flagKey, Map<String, String> company, Map<String, String> user) {
        try {
            RulesengineCheckFlagResult cached = getCachedFlag(flagKey, company, user);
            if (cached != null) {
                return cached;
            }

            CheckFlagRequestBody request =
                    CheckFlagRequestBody.builder().company(company).user(user).build();
            CheckFlagResponse response = features().checkFlag(flagKey, request);
            RulesengineCheckFlagResult result = toRulesengineResult(response.getData());

            cacheFlag(flagKey, result, company, user);
            return result;
        } catch (Exception e) {
            logger.error("Error checking flag via API: " + e.getMessage());
            return defaultFlagResult(flagKey, "flag default", e.getMessage());
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

            // Update cached company metrics if datastream is active
            if (company != null && !company.isEmpty() && dataStreamClient != null && dataStreamClient.isConnected()) {
                try {
                    dataStreamClient.updateCompanyMetrics(body);
                } catch (Exception e2) {
                    logger.error("Failed to update company metrics: " + e2.getMessage());
                }
            }
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
            eventSender.close();
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
        if (map == null) {
            return null;
        }
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
