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
import com.schematic.api.types.EventBodyTrack;
import com.schematic.api.types.RulesengineCheckFlagResult;
import com.schematic.api.types.RulesengineCompany;
import com.schematic.api.types.RulesengineCompanyMetric;
import com.schematic.api.types.RulesengineFlag;
import com.schematic.api.types.RulesengineUser;
import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * High-level DataStream client that manages WebSocket connections (or replicator mode),
 * caches entities (flags, companies, users), and provides flag checking.
 *
 * <p>Entities are cached as typed objects ({@link RulesengineFlag}, {@link RulesengineCompany},
 * {@link RulesengineUser}).
 */
public class DataStreamClient implements Closeable {

    // Cache key prefixes. Must match the replicator and other SDKs: both
    // ID-based and (key, value)-based lookups share the same resource prefix
    // (`company:` / `user:`) and are disambiguated by the trailing segments.
    static final String FLAG_PREFIX = "flags:";
    static final String COMPANY_PREFIX = "company:";
    static final String USER_PREFIX = "user:";

    // Timeout for waiting on entity responses from WebSocket
    private static final long RESOURCE_TIMEOUT_MS = 2_000;

    private final DatastreamOptions options;
    private final String apiKey;
    private final String apiUrl;
    private final String sdkVersion;
    private final SchematicLogger logger;
    private final ObjectMapper objectMapper;
    private final RulesEngine rulesEngine;

    // Typed entity caches
    private final CacheProvider<RulesengineFlag> flagCache;
    private final CacheProvider<RulesengineCompany> companyCache;
    private final CacheProvider<RulesengineUser> userCache;
    // Key-based lookup caches: map `{resource}:{version}:{key}:{value}` -> entity ID.
    // The entity object itself lives in the corresponding ID cache above.
    private final CacheProvider<String> companyKeyCache;
    private final CacheProvider<String> userKeyCache;

    // Pending entity requests: cache key -> list of futures waiting for that entity.
    private final ConcurrentHashMap<String, List<CompletableFuture<RulesengineCompany>>> pendingCompanyRequests =
            new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, List<CompletableFuture<RulesengineUser>>> pendingUserRequests =
            new ConcurrentHashMap<>();

    // WebSocket client (direct mode only)
    private volatile DataStreamWebSocketClient wsClient;

    // Replicator mode state
    private final AtomicBoolean replicatorReady = new AtomicBoolean(false);
    private volatile String replicatorCacheVersion;
    private volatile ScheduledExecutorService healthCheckScheduler;
    private volatile ScheduledFuture<?> healthCheckTask;
    private final OkHttpClient httpClient;

    private final AtomicBoolean closed = new AtomicBoolean(false);

    public DataStreamClient(DatastreamOptions options, String apiKey, String apiUrl, SchematicLogger logger) {
        this(options, apiKey, apiUrl, logger, null, null);
    }

    public DataStreamClient(
            DatastreamOptions options, String apiKey, String apiUrl, SchematicLogger logger, RulesEngine rulesEngine) {
        this(options, apiKey, apiUrl, logger, rulesEngine, null);
    }

    public DataStreamClient(
            DatastreamOptions options,
            String apiKey,
            String apiUrl,
            SchematicLogger logger,
            RulesEngine rulesEngine,
            String sdkVersion) {
        this.options = options;
        this.apiKey = apiKey;
        this.apiUrl = apiUrl;
        this.sdkVersion = sdkVersion;
        this.logger = logger;
        this.objectMapper = ObjectMappers.JSON_MAPPER;
        this.rulesEngine = rulesEngine;

        // Build cache providers via factory: custom > Redis > local
        redis.clients.jedis.JedisPooled redisClient =
                DataStreamCacheFactory.buildRedisClient(options.getRedisCacheConfig());
        String keyPrefix = options.getRedisCacheConfig() != null
                ? options.getRedisCacheConfig().getKeyPrefix()
                : "schematic:";
        this.flagCache = DataStreamCacheFactory.buildFlagCache(options, redisClient, keyPrefix);
        this.companyCache = DataStreamCacheFactory.buildCompanyCache(options, redisClient, keyPrefix);
        this.userCache = DataStreamCacheFactory.buildUserCache(options, redisClient, keyPrefix);
        this.companyKeyCache = DataStreamCacheFactory.buildKeyLookupCache(options, redisClient, keyPrefix);
        this.userKeyCache = DataStreamCacheFactory.buildKeyLookupCache(options, redisClient, keyPrefix);

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
        RulesengineFlag flag = flagCache.get(flagCacheKey(flagKey));
        if (flag == null) {
            throw new DataStreamException("Flag not found in cache: " + flagKey);
        }

        // Step 2: Try to get company/user from cache
        boolean needsCompany = company != null && !company.isEmpty();
        boolean needsUser = user != null && !user.isEmpty();

        RulesengineCompany cachedCompany = null;
        RulesengineUser cachedUser = null;

        try {
            if (needsCompany) {
                cachedCompany = getCachedCompany(company);
                log("debug", "Company " + (cachedCompany != null ? "found in cache" : "not found in cache"));
            }
            if (needsUser) {
                cachedUser = getCachedUser(user);
                log("debug", "User " + (cachedUser != null ? "found in cache" : "not found in cache"));
            }
        } catch (DataStreamException.KeyConflict e) {
            log("warn", "Key conflict for flag " + flagKey + ": " + e.getMessage());
            return RulesengineCheckFlagResult.builder()
                    .flagKey(flagKey)
                    .reason("key conflict")
                    .value(flag.getDefaultValue())
                    .flagId(flag.getId())
                    .err(e.getMessage())
                    .build();
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

        // Step 5: Direct mode - fetch missing entities via datastream and wait for response
        if (!isConnected()) {
            throw new DataStreamException("Datastream not connected and required entities not in cache");
        }

        if (needsCompany && cachedCompany == null) {
            cachedCompany = getCompany(company);
        }
        if (needsUser && cachedUser == null) {
            cachedUser = getUser(user);
        }

        return evaluateFlag(flag, cachedCompany, cachedUser);
    }

    /**
     * Fetches a company via the datastream WebSocket, waiting for the response with a timeout.
     * Deduplicates concurrent requests for the same entity.
     */
    private RulesengineCompany getCompany(Map<String, String> keys) {
        // Check cache first
        RulesengineCompany cached = getCachedCompany(keys);
        if (cached != null) {
            return cached;
        }

        CompletableFuture<RulesengineCompany> future = new CompletableFuture<>();
        boolean shouldSendRequest = registerPendingCompanyRequest(keys, future);

        if (shouldSendRequest) {
            requestEntity(EntityType.COMPANY, keys);
        }

        try {
            return future.get(RESOURCE_TIMEOUT_MS, TimeUnit.MILLISECONDS);
        } catch (TimeoutException e) {
            log("warn", "Timeout waiting for company data");
        } catch (Exception e) {
            log("warn", "Error waiting for company data: " + e.getMessage());
        } finally {
            cleanupPendingCompanyRequests(keys, future);
        }

        return null;
    }

    /**
     * Fetches a user via the datastream WebSocket, waiting for the response with a timeout.
     * Deduplicates concurrent requests for the same entity.
     */
    private RulesengineUser getUser(Map<String, String> keys) {
        // Check cache first
        RulesengineUser cached = getCachedUser(keys);
        if (cached != null) {
            return cached;
        }

        CompletableFuture<RulesengineUser> future = new CompletableFuture<>();
        boolean shouldSendRequest = registerPendingUserRequest(keys, future);

        if (shouldSendRequest) {
            requestEntity(EntityType.USER, keys);
        }

        try {
            return future.get(RESOURCE_TIMEOUT_MS, TimeUnit.MILLISECONDS);
        } catch (TimeoutException e) {
            log("warn", "Timeout waiting for user data");
        } catch (Exception e) {
            log("warn", "Error waiting for user data: " + e.getMessage());
        } finally {
            cleanupPendingUserRequests(keys, future);
        }

        return null;
    }

    /**
     * Registers a future for a pending company request. Returns true if this is the
     * first request for this entity (meaning the caller should send the WebSocket message).
     */
    private boolean registerPendingCompanyRequest(
            Map<String, String> keys, CompletableFuture<RulesengineCompany> future) {
        boolean shouldSend = true;
        for (Map.Entry<String, String> entry : keys.entrySet()) {
            String cacheKey = companyCacheKey(entry.getKey(), entry.getValue());
            synchronized (pendingCompanyRequests) {
                List<CompletableFuture<RulesengineCompany>> existing = pendingCompanyRequests.get(cacheKey);
                if (existing != null) {
                    // Another thread already requested this entity
                    existing.add(future);
                    shouldSend = false;
                } else {
                    List<CompletableFuture<RulesengineCompany>> futures = new ArrayList<>();
                    futures.add(future);
                    pendingCompanyRequests.put(cacheKey, futures);
                }
            }
        }
        return shouldSend;
    }

    /**
     * Registers a future for a pending user request. Returns true if this is the
     * first request for this entity (meaning the caller should send the WebSocket message).
     */
    private boolean registerPendingUserRequest(Map<String, String> keys, CompletableFuture<RulesengineUser> future) {
        boolean shouldSend = true;
        for (Map.Entry<String, String> entry : keys.entrySet()) {
            String cacheKey = userCacheKey(entry.getKey(), entry.getValue());
            synchronized (pendingUserRequests) {
                List<CompletableFuture<RulesengineUser>> existing = pendingUserRequests.get(cacheKey);
                if (existing != null) {
                    existing.add(future);
                    shouldSend = false;
                } else {
                    List<CompletableFuture<RulesengineUser>> futures = new ArrayList<>();
                    futures.add(future);
                    pendingUserRequests.put(cacheKey, futures);
                }
            }
        }
        return shouldSend;
    }

    /**
     * Notifies all pending futures waiting for a company with the given keys.
     */
    private void notifyPendingCompanyRequests(Map<String, String> keys, RulesengineCompany company) {
        synchronized (pendingCompanyRequests) {
            for (Map.Entry<String, String> entry : keys.entrySet()) {
                String cacheKey = companyCacheKey(entry.getKey(), entry.getValue());
                List<CompletableFuture<RulesengineCompany>> futures = pendingCompanyRequests.remove(cacheKey);
                if (futures != null) {
                    for (CompletableFuture<RulesengineCompany> future : futures) {
                        future.complete(company);
                    }
                }
            }
        }
    }

    /**
     * Notifies all pending futures waiting for a user with the given keys.
     */
    private void notifyPendingUserRequests(Map<String, String> keys, RulesengineUser user) {
        synchronized (pendingUserRequests) {
            for (Map.Entry<String, String> entry : keys.entrySet()) {
                String cacheKey = userCacheKey(entry.getKey(), entry.getValue());
                List<CompletableFuture<RulesengineUser>> futures = pendingUserRequests.remove(cacheKey);
                if (futures != null) {
                    for (CompletableFuture<RulesengineUser> future : futures) {
                        future.complete(user);
                    }
                }
            }
        }
    }

    private void cleanupPendingCompanyRequests(Map<String, String> keys, CompletableFuture<RulesengineCompany> future) {
        synchronized (pendingCompanyRequests) {
            for (Map.Entry<String, String> entry : keys.entrySet()) {
                String cacheKey = companyCacheKey(entry.getKey(), entry.getValue());
                List<CompletableFuture<RulesengineCompany>> futures = pendingCompanyRequests.get(cacheKey);
                if (futures != null) {
                    futures.remove(future);
                    if (futures.isEmpty()) {
                        pendingCompanyRequests.remove(cacheKey);
                    }
                }
            }
        }
    }

    private void cleanupPendingUserRequests(Map<String, String> keys, CompletableFuture<RulesengineUser> future) {
        synchronized (pendingUserRequests) {
            for (Map.Entry<String, String> entry : keys.entrySet()) {
                String cacheKey = userCacheKey(entry.getKey(), entry.getValue());
                List<CompletableFuture<RulesengineUser>> futures = pendingUserRequests.get(cacheKey);
                if (futures != null) {
                    futures.remove(future);
                    if (futures.isEmpty()) {
                        pendingUserRequests.remove(cacheKey);
                    }
                }
            }
        }
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
     * Updates cached company metrics locally when a track event is received.
     * Increments metric values matching the event name by the event quantity.
     */
    public void updateCompanyMetrics(EventBodyTrack event) {
        if (event == null) {
            return;
        }

        Map<String, String> keys = event.getCompany().orElse(null);
        if (keys == null || keys.isEmpty()) {
            return;
        }

        RulesengineCompany company = getCachedCompany(keys);
        if (company == null) {
            return;
        }

        String eventName = event.getEvent();
        int quantity = event.getQuantity().orElse(1);

        List<RulesengineCompanyMetric> updatedMetrics = new ArrayList<>();
        for (RulesengineCompanyMetric metric : company.getMetrics()) {
            if (eventName.equals(metric.getEventSubtype())) {
                updatedMetrics.add(RulesengineCompanyMetric.builder()
                        .from(metric)
                        .value(metric.getValue() + quantity)
                        .build());
            } else {
                updatedMetrics.add(metric);
            }
        }

        RulesengineCompany updated = RulesengineCompany.builder()
                .from(company)
                .metrics(updatedMetrics)
                .build();

        cacheCompanyObject(updated);
    }

    /**
     * Retrieves a cached flag definition by key.
     */
    public RulesengineFlag getCachedFlag(String flagKey) {
        return flagCache.get(flagCacheKey(flagKey));
    }

    /**
     * Retrieves a cached company by its lookup keys.
     */
    public RulesengineCompany getCachedCompany(Map<String, String> keys) {
        if (keys == null || keys.isEmpty()) {
            return null;
        }
        String matchedId = null;
        for (Map.Entry<String, String> entry : keys.entrySet()) {
            String id = companyKeyCache.get(companyCacheKey(entry.getKey(), entry.getValue()));
            if (id == null) {
                continue;
            }
            if (matchedId == null) {
                matchedId = id;
            } else if (!matchedId.equals(id)) {
                throw new DataStreamException.KeyConflict(
                        "Company keys match multiple entities: " + matchedId + " and " + id);
            }
        }
        if (matchedId == null) {
            return null;
        }
        return companyCache.get(companyIdCacheKey(matchedId));
    }

    /**
     * Retrieves a cached user by its lookup keys.
     */
    public RulesengineUser getCachedUser(Map<String, String> keys) {
        if (keys == null || keys.isEmpty()) {
            return null;
        }
        String matchedId = null;
        for (Map.Entry<String, String> entry : keys.entrySet()) {
            String id = userKeyCache.get(userCacheKey(entry.getKey(), entry.getValue()));
            if (id == null) {
                continue;
            }
            if (matchedId == null) {
                matchedId = id;
            } else if (!matchedId.equals(id)) {
                throw new DataStreamException.KeyConflict(
                        "User keys match multiple entities: " + matchedId + " and " + id);
            }
        }
        if (matchedId == null) {
            return null;
        }
        return userCache.get(userIdCacheKey(matchedId));
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
                .sdkVersion(sdkVersion)
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

                    String newCacheVersion = null;
                    if (body.has("cache_version")) {
                        newCacheVersion = body.get("cache_version").asText();
                    } else if (body.has("cacheVersion")) {
                        newCacheVersion = body.get("cacheVersion").asText();
                    }
                    if (newCacheVersion != null && !newCacheVersion.equals(replicatorCacheVersion)) {
                        String oldVersion = replicatorCacheVersion;
                        replicatorCacheVersion = newCacheVersion;
                        log(
                                "info",
                                "Replicator cache version changed from "
                                        + (oldVersion == null ? "(null)" : oldVersion) + " to "
                                        + newCacheVersion);
                    }

                    if (ready && !wasReady) {
                        log("info", "Replicator is now ready");
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
                List<String> cacheKeys = new ArrayList<>();
                for (JsonNode flagData : data) {
                    cacheFlag(flagData);
                    String key = flagData.has("key") ? flagData.get("key").asText() : null;
                    if (key != null) {
                        cacheKeys.add(flagCacheKey(key));
                    }
                }
                flagCache.deleteMissing(cacheKeys, FLAG_PREFIX + "*");
            } else {
                cacheFlag(data);
            }
        } else if (messageType == MessageType.DELETE) {
            String flagKey = data.has("key") ? data.get("key").asText() : null;
            if (flagKey != null) {
                flagCache.delete(flagCacheKey(flagKey));
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
                RulesengineCompany existing = companyCache.get(companyIdCacheKey(entityId));
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
                // Clean up key-based cache entries before removing by ID
                RulesengineCompany existing = companyCache.get(companyIdCacheKey(entityId));
                if (existing != null) {
                    for (Map.Entry<String, String> entry : existing.getKeys().entrySet()) {
                        companyKeyCache.delete(companyCacheKey(entry.getKey(), entry.getValue()));
                    }
                }
                companyCache.delete(companyIdCacheKey(entityId));
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
                RulesengineUser existing = userCache.get(userIdCacheKey(entityId));
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
                // Clean up key-based cache entries before removing by ID
                RulesengineUser existing = userCache.get(userIdCacheKey(entityId));
                if (existing != null) {
                    for (Map.Entry<String, String> entry : existing.getKeys().entrySet()) {
                        userKeyCache.delete(userCacheKey(entry.getKey(), entry.getValue()));
                    }
                }
                userCache.delete(userIdCacheKey(entityId));
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
            flagCache.set(flagCacheKey(flag.getKey()), flag);
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
        companyCache.set(companyIdCacheKey(company.getId()), company);
        for (Map.Entry<String, String> entry : company.getKeys().entrySet()) {
            companyKeyCache.set(companyCacheKey(entry.getKey(), entry.getValue()), company.getId());
        }
        // Notify any pending requests waiting for this company
        notifyPendingCompanyRequests(company.getKeys(), company);
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
        userCache.set(userIdCacheKey(user.getId()), user);
        for (Map.Entry<String, String> entry : user.getKeys().entrySet()) {
            userKeyCache.set(userCacheKey(entry.getKey(), entry.getValue()), user.getId());
        }
        // Notify any pending requests waiting for this user
        notifyPendingUserRequests(user.getKeys(), user);
    }

    /**
     * Returns the version key used to namespace cache entries. Prefers the
     * replicator cache version (when in replicator mode and available),
     * otherwise falls back to the rules engine version key, or "1" if neither
     * is available.
     */
    private String versionKey() {
        String replicatorVersion = replicatorCacheVersion;
        if (options.isReplicatorMode() && replicatorVersion != null) {
            return replicatorVersion;
        }
        if (rulesEngine != null) {
            try {
                if (rulesEngine.isInitialized()) {
                    String v = rulesEngine.getVersionKey();
                    if (v != null) {
                        return v;
                    }
                }
            } catch (Exception e) {
                log("warn", "Failed to get rules engine version key: " + e.getMessage());
            }
        }
        return "1";
    }

    private String flagCacheKey(String flagKey) {
        return FLAG_PREFIX + versionKey() + ":" + flagKey.toLowerCase();
    }

    private String companyIdCacheKey(String id) {
        return COMPANY_PREFIX + versionKey() + ":" + id;
    }

    private String userIdCacheKey(String id) {
        return USER_PREFIX + versionKey() + ":" + id;
    }

    private String companyCacheKey(String key, String value) {
        return COMPANY_PREFIX + versionKey() + ":" + key.toLowerCase() + ":" + value.toLowerCase();
    }

    private String userCacheKey(String key, String value) {
        return USER_PREFIX + versionKey() + ":" + key.toLowerCase() + ":" + value.toLowerCase();
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
