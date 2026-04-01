package com.schematic.api.datastream;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.schematic.api.cache.CacheProvider;
import com.schematic.api.core.ObjectMappers;
import com.schematic.api.datastream.DataStreamMessages.Action;
import com.schematic.api.datastream.DataStreamMessages.DataStreamBaseReq;
import com.schematic.api.datastream.DataStreamMessages.DataStreamReq;
import com.schematic.api.datastream.DataStreamMessages.DataStreamResp;
import com.schematic.api.datastream.DataStreamMessages.EntityType;
import com.schematic.api.datastream.DataStreamMessages.MessageType;
import com.schematic.api.logger.SchematicLogger;
import com.schematic.api.types.RulesengineCheckFlagResult;
import com.schematic.api.types.RulesengineCompany;
import com.schematic.api.types.RulesengineFlag;
import com.schematic.api.types.RulesengineUser;
import java.io.Closeable;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * High-level DataStream client that manages WebSocket connections (or replicator mode),
 * caches entities (flags, companies, users), and provides flag checking.
 *
 * <p>Entities are cached as typed objects ({@link RulesengineFlag}, {@link RulesengineCompany},
 * {@link RulesengineUser}) matching the approach used by the Python and Go SDKs.
 */
public class DataStreamClient implements Closeable {

    // Cache key prefixes
    static final String FLAG_PREFIX = "flags:";
    static final String COMPANY_PREFIX = "company:";
    static final String COMPANY_KEY_PREFIX = "company_key:";
    static final String USER_PREFIX = "user:";
    static final String USER_KEY_PREFIX = "user_key:";

    private final DatastreamOptions options;
    private final String apiKey;
    private final String apiUrl;
    private final SchematicLogger logger;
    private final ObjectMapper objectMapper;
    private final RulesEngine rulesEngine;

    // Typed entity caches
    private final CacheProvider<RulesengineFlag> flagCache;
    private final CacheProvider<RulesengineCompany> companyCache;
    private final CacheProvider<RulesengineUser> userCache;

    // WebSocket client (direct mode only)
    private volatile DataStreamWebSocketClient wsClient;

    // Replicator mode state
    private final AtomicBoolean replicatorReady = new AtomicBoolean(false);
    private volatile ScheduledExecutorService healthCheckScheduler;
    private volatile ScheduledFuture<?> healthCheckTask;
    private final OkHttpClient httpClient;

    private final AtomicBoolean closed = new AtomicBoolean(false);

    public DataStreamClient(DatastreamOptions options, String apiKey, String apiUrl, SchematicLogger logger) {
        this(options, apiKey, apiUrl, logger, null);
    }

    public DataStreamClient(
            DatastreamOptions options, String apiKey, String apiUrl, SchematicLogger logger, RulesEngine rulesEngine) {
        this.options = options;
        this.apiKey = apiKey;
        this.apiUrl = apiUrl;
        this.logger = logger;
        this.objectMapper = ObjectMappers.JSON_MAPPER;
        this.rulesEngine = rulesEngine;
        this.flagCache = options.getFlagCacheProvider();
        this.companyCache = options.getCompanyCacheProvider();
        this.userCache = options.getUserCacheProvider();
        this.httpClient = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .build();
    }

    /**
     * Starts the DataStream client. In direct mode, connects via WebSocket.
     * In replicator mode, starts periodic health checks.
     */
    public void start() {
        if (closed.get()) {
            throw new IllegalStateException("DataStreamClient has been closed");
        }

        if (options.isReplicatorMode()) {
            startReplicatorMode();
        } else {
            startDirectMode();
        }
    }

    /**
     * Returns whether the datastream is connected and ready for flag checks.
     */
    public boolean isConnected() {
        if (options.isReplicatorMode()) {
            return replicatorReady.get();
        }
        return wsClient != null && wsClient.isReady();
    }

    /**
     * Returns whether this client is running in replicator mode.
     */
    public boolean isReplicatorMode() {
        return options.isReplicatorMode();
    }

    /**
     * Checks a flag using cached datastream data and the rules engine.
     */
    public RulesengineCheckFlagResult checkFlag(String flagKey, Map<String, String> company, Map<String, String> user) {
        // Step 1: Get flag from cache
        RulesengineFlag flag = flagCache.get(FLAG_PREFIX + flagKey);
        if (flag == null) {
            throw new DataStreamException("Flag not found in cache: " + flagKey);
        }

        // Step 2: Try to get company/user from cache
        boolean needsCompany = company != null && !company.isEmpty();
        boolean needsUser = user != null && !user.isEmpty();

        RulesengineCompany cachedCompany = null;
        RulesengineUser cachedUser = null;

        if (needsCompany) {
            cachedCompany = getCachedCompany(company);
            log("debug", "Company " + (cachedCompany != null ? "found in cache" : "not found in cache"));
        }
        if (needsUser) {
            cachedUser = getCachedUser(user);
            log("debug", "User " + (cachedUser != null ? "found in cache" : "not found in cache"));
        }

        // Step 3: Replicator mode - evaluate with whatever we have
        if (options.isReplicatorMode()) {
            return evaluateFlag(flag, cachedCompany, cachedUser);
        }

        // Step 4: Direct mode - if all needed data is cached, evaluate immediately
        if ((!needsCompany || cachedCompany != null) && (!needsUser || cachedUser != null)) {
            log("debug", "All required resources found in cache for flag " + flagKey);
            return evaluateFlag(flag, cachedCompany, cachedUser);
        }

        // Step 5: Direct mode - fetch missing entities via datastream
        if (!isConnected()) {
            throw new DataStreamException("Datastream not connected and required entities not in cache");
        }

        if (needsCompany && cachedCompany == null) {
            requestEntity(EntityType.COMPANY, company);
        }
        if (needsUser && cachedUser == null) {
            requestEntity(EntityType.USER, user);
        }

        return evaluateFlag(flag, cachedCompany, cachedUser);
    }

    /**
     * Evaluates a flag using the rules engine. Falls back to the flag's default value
     * if the rules engine is not available.
     */
    RulesengineCheckFlagResult evaluateFlag(RulesengineFlag flag, RulesengineCompany company, RulesengineUser user) {
        boolean defaultValue = flag.getDefaultValue();
        String flagKey = flag.getKey();
        String flagId = flag.getId();
        String companyId = company != null ? company.getId() : null;
        String userId = user != null ? user.getId() : null;

        if (rulesEngine != null && rulesEngine.isInitialized()) {
            try {
                RulesengineCheckFlagResult result = rulesEngine.checkFlag(flag, company, user);
                // The WASM engine returns a complete result — use it directly,
                // enriching with IDs from context if the engine didn't set them
                return RulesengineCheckFlagResult.builder()
                        .from(result)
                        .companyId(result.getCompanyId().orElse(companyId))
                        .userId(result.getUserId().orElse(userId))
                        .build();
            } catch (Exception e) {
                log("error", "Rules engine evaluation failed for flag " + flagKey + ": " + e.getMessage());
                return RulesengineCheckFlagResult.builder()
                        .flagKey(flagKey)
                        .reason("RULES_ENGINE_ERROR")
                        .value(defaultValue)
                        .flagId(flagId)
                        .companyId(companyId)
                        .userId(userId)
                        .err(e.getMessage())
                        .build();
            }
        }

        log("debug", "Rules engine not available, returning default for flag " + flagKey);
        return RulesengineCheckFlagResult.builder()
                .flagKey(flagKey)
                .reason("RULES_ENGINE_UNAVAILABLE")
                .value(defaultValue)
                .flagId(flagId)
                .companyId(companyId)
                .userId(userId)
                .build();
    }

    /**
     * Retrieves a cached flag definition by key.
     */
    public RulesengineFlag getCachedFlag(String flagKey) {
        return flagCache.get(FLAG_PREFIX + flagKey);
    }

    /**
     * Retrieves a cached company by its lookup keys.
     */
    public RulesengineCompany getCachedCompany(Map<String, String> keys) {
        if (keys == null || keys.isEmpty()) {
            return null;
        }
        // Look up by first key pair, matching the Node/Python SDK approach
        Map.Entry<String, String> first = keys.entrySet().iterator().next();
        String cacheKey = COMPANY_KEY_PREFIX + first.getKey() + ":" + first.getValue();
        return companyCache.get(cacheKey);
    }

    /**
     * Retrieves a cached user by its lookup keys.
     */
    public RulesengineUser getCachedUser(Map<String, String> keys) {
        if (keys == null || keys.isEmpty()) {
            return null;
        }
        Map.Entry<String, String> first = keys.entrySet().iterator().next();
        String cacheKey = USER_KEY_PREFIX + first.getKey() + ":" + first.getValue();
        return userCache.get(cacheKey);
    }

    /**
     * Sends a request to the datastream to fetch a specific entity.
     * Only works in direct (WebSocket) mode.
     */
    public void requestEntity(EntityType entityType, Map<String, String> keys) {
        if (options.isReplicatorMode()) {
            log("debug", "Cannot request entities in replicator mode");
            return;
        }
        if (wsClient == null || !wsClient.isReady()) {
            log("warn", "Cannot request entity: WebSocket not ready");
            return;
        }

        DataStreamReq req = new DataStreamReq(Action.START, entityType, keys);
        DataStreamBaseReq baseReq = new DataStreamBaseReq(req);
        wsClient.sendMessage(baseReq);
    }

    @Override
    public void close() {
        if (!closed.compareAndSet(false, true)) {
            return;
        }

        log("info", "Closing DataStream client");

        if (healthCheckTask != null) {
            healthCheckTask.cancel(false);
        }
        if (healthCheckScheduler != null) {
            healthCheckScheduler.shutdownNow();
        }
        if (wsClient != null) {
            wsClient.close();
        }

        httpClient.dispatcher().executorService().shutdownNow();
        httpClient.connectionPool().evictAll();

        log("info", "DataStream client closed");
    }

    // --- Direct WebSocket mode ---

    private void startDirectMode() {
        log("info", "Starting DataStream client in direct mode");

        wsClient = DataStreamWebSocketClient.builder()
                .url(apiUrl)
                .apiKey(apiKey)
                .messageHandler(this::handleMessage)
                .connectionReadyHandler(this::onConnectionReady)
                .logger(logger)
                .build();

        wsClient.start();
    }

    private void onConnectionReady() {
        log("info", "DataStream connection established, requesting flags");
        DataStreamReq req = new DataStreamReq(Action.START, EntityType.FLAGS, null);
        DataStreamBaseReq baseReq = new DataStreamBaseReq(req);
        wsClient.sendMessage(baseReq);
    }

    // --- Replicator mode ---

    private void startReplicatorMode() {
        log("info", "Starting DataStream client in replicator mode");
        log("info", "Replicator health URL: " + options.getReplicatorHealthUrl());

        healthCheckScheduler = Executors.newSingleThreadScheduledExecutor(r -> {
            Thread t = new Thread(r, "schematic-replicator-health");
            t.setDaemon(true);
            return t;
        });

        long intervalMs = options.getReplicatorHealthCheckInterval().toMillis();
        healthCheckTask = healthCheckScheduler.scheduleAtFixedRate(
                this::checkReplicatorHealth, 0, intervalMs, TimeUnit.MILLISECONDS);
    }

    void checkReplicatorHealth() {
        try {
            Request request = new Request.Builder()
                    .url(options.getReplicatorHealthUrl())
                    .get()
                    .build();

            try (Response response = httpClient.newCall(request).execute()) {
                if (response.isSuccessful() && response.body() != null) {
                    JsonNode body = objectMapper.readTree(response.body().string());
                    boolean ready = body.has("ready") && body.get("ready").asBoolean(false);
                    boolean wasReady = replicatorReady.getAndSet(ready);

                    if (ready && !wasReady) {
                        log("info", "Replicator is now ready");
                        if (body.has("cache_version")) {
                            log(
                                    "info",
                                    "Replicator cache version: "
                                            + body.get("cache_version").asText());
                        }
                    } else if (!ready && wasReady) {
                        log("warn", "Replicator is no longer ready");
                    }
                } else {
                    boolean wasReady = replicatorReady.getAndSet(false);
                    if (wasReady) {
                        log("warn", "Replicator health check failed with status: " + response.code());
                    }
                }
            }
        } catch (IOException e) {
            boolean wasReady = replicatorReady.getAndSet(false);
            if (wasReady) {
                log("warn", "Replicator health check failed: " + e.getMessage());
            }
            log("debug", "Replicator health check error: " + e.getMessage());
        }
    }

    // --- Message handling ---

    void handleMessage(DataStreamResp message) {
        EntityType entityType = message.getEntityTypeEnum();
        MessageType messageType = message.getMessageTypeEnum();

        if (entityType == null) {
            log("warn", "Received message with unknown entity type: " + message.getEntityType());
            return;
        }

        if (messageType == MessageType.ERROR) {
            handleErrorMessage(message);
            return;
        }

        switch (entityType) {
            case FLAG:
            case FLAGS:
                handleFlagMessage(message, messageType);
                break;
            case COMPANY:
            case COMPANIES:
                handleCompanyMessage(message, messageType);
                break;
            case USER:
            case USERS:
                handleUserMessage(message, messageType);
                break;
            default:
                log("debug", "Unhandled entity type: " + entityType);
        }
    }

    private void handleFlagMessage(DataStreamResp message, MessageType messageType) {
        JsonNode data = message.getData();
        if (data == null) {
            return;
        }

        if (messageType == MessageType.FULL) {
            if (data.isArray()) {
                for (JsonNode flagData : data) {
                    cacheFlag(flagData);
                }
            } else {
                cacheFlag(data);
            }
        } else if (messageType == MessageType.DELETE) {
            String flagKey = data.has("key") ? data.get("key").asText() : null;
            if (flagKey != null) {
                flagCache.set(FLAG_PREFIX + flagKey, null);
            }
        }
    }

    private void handleCompanyMessage(DataStreamResp message, MessageType messageType) {
        JsonNode data = message.getData();
        if (data == null) {
            return;
        }

        if (messageType == MessageType.FULL) {
            if (data.isArray()) {
                for (JsonNode companyData : data) {
                    cacheCompany(companyData);
                }
            } else {
                cacheCompany(data);
            }
        } else if (messageType == MessageType.PARTIAL) {
            String entityId = message.getEntityId();
            if (entityId != null) {
                RulesengineCompany existing = companyCache.get(COMPANY_PREFIX + entityId);
                if (existing != null) {
                    try {
                        RulesengineCompany merged = EntityMerge.partialCompany(existing, data);
                        cacheCompanyObject(merged);
                    } catch (Exception e) {
                        log("warn", "Failed to merge partial company update: " + e.getMessage());
                    }
                } else {
                    // No existing company — try to parse as full
                    cacheCompany(data);
                }
            }
        } else if (messageType == MessageType.DELETE) {
            String entityId = message.getEntityId();
            if (entityId != null) {
                companyCache.set(COMPANY_PREFIX + entityId, null);
            }
        }
    }

    private void handleUserMessage(DataStreamResp message, MessageType messageType) {
        JsonNode data = message.getData();
        if (data == null) {
            return;
        }

        if (messageType == MessageType.FULL) {
            if (data.isArray()) {
                for (JsonNode userData : data) {
                    cacheUser(userData);
                }
            } else {
                cacheUser(data);
            }
        } else if (messageType == MessageType.PARTIAL) {
            String entityId = message.getEntityId();
            if (entityId != null) {
                RulesengineUser existing = userCache.get(USER_PREFIX + entityId);
                if (existing != null) {
                    try {
                        RulesengineUser merged = EntityMerge.partialUser(existing, data);
                        cacheUserObject(merged);
                    } catch (Exception e) {
                        log("warn", "Failed to merge partial user update: " + e.getMessage());
                    }
                } else {
                    cacheUser(data);
                }
            }
        } else if (messageType == MessageType.DELETE) {
            String entityId = message.getEntityId();
            if (entityId != null) {
                userCache.set(USER_PREFIX + entityId, null);
            }
        }
    }

    private void handleErrorMessage(DataStreamResp message) {
        JsonNode data = message.getData();
        if (data != null) {
            log("error", "DataStream error for entity " + message.getEntityType() + ": " + data.toString());
        } else {
            log("error", "DataStream error for entity " + message.getEntityType());
        }
    }

    // --- Cache helpers: parse JSON once into typed objects ---

    private void cacheFlag(JsonNode data) {
        try {
            RulesengineFlag flag = objectMapper.treeToValue(data, RulesengineFlag.class);
            log("debug", "Caching flag: " + flag.getKey());
            flagCache.set(FLAG_PREFIX + flag.getKey(), flag);
        } catch (Exception e) {
            log("warn", "Failed to parse flag from datastream: " + e.getMessage());
        }
    }

    private void cacheCompany(JsonNode data) {
        try {
            RulesengineCompany company = objectMapper.treeToValue(data, RulesengineCompany.class);
            cacheCompanyObject(company);
        } catch (Exception e) {
            log("warn", "Failed to parse company from datastream: " + e.getMessage());
        }
    }

    private void cacheCompanyObject(RulesengineCompany company) {
        companyCache.set(COMPANY_PREFIX + company.getId(), company);
        for (Map.Entry<String, String> entry : company.getKeys().entrySet()) {
            String cacheKey = COMPANY_KEY_PREFIX + entry.getKey() + ":" + entry.getValue();
            companyCache.set(cacheKey, company);
        }
    }

    private void cacheUser(JsonNode data) {
        try {
            RulesengineUser user = objectMapper.treeToValue(data, RulesengineUser.class);
            cacheUserObject(user);
        } catch (Exception e) {
            log("warn", "Failed to parse user from datastream: " + e.getMessage());
        }
    }

    private void cacheUserObject(RulesengineUser user) {
        userCache.set(USER_PREFIX + user.getId(), user);
        for (Map.Entry<String, String> entry : user.getKeys().entrySet()) {
            String cacheKey = USER_KEY_PREFIX + entry.getKey() + ":" + entry.getValue();
            userCache.set(cacheKey, user);
        }
    }

    private void log(String level, String message) {
        if (logger == null) {
            return;
        }
        switch (level) {
            case "debug":
                logger.debug(message);
                break;
            case "info":
                logger.info(message);
                break;
            case "warn":
                logger.warn(message);
                break;
            case "error":
                logger.error(message);
                break;
            default:
                logger.debug(message);
                break;
        }
    }
}
