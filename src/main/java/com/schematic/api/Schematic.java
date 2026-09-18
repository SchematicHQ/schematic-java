package com.schematic.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.schematic.api.cache.CacheProvider;
import com.schematic.api.cache.LocalCache;
import com.schematic.api.core.ClientOptions;
import com.schematic.api.core.Environment;
import com.schematic.api.core.NoOpHttpClient;
import com.schematic.api.core.ObjectMappers;
import com.schematic.api.core.RequestOptions;
import com.schematic.api.credits.ApiLeaseWireClient;
import com.schematic.api.credits.CheckOptions;
import com.schematic.api.credits.CheckRequest;
import com.schematic.api.credits.CheckResult;
import com.schematic.api.credits.CreditAmounts;
import com.schematic.api.credits.CreditCheck;
import com.schematic.api.credits.CreditLeaseConfig;
import com.schematic.api.credits.CreditLeaseDefaults;
import com.schematic.api.credits.CreditLeaseManager;
import com.schematic.api.credits.CreditLeaseMode;
import com.schematic.api.credits.DataStreamCreditCheckSource;
import com.schematic.api.credits.InMemoryLeaseStore;
import com.schematic.api.credits.InMemoryReservationStore;
import com.schematic.api.credits.LeaseStore;
import com.schematic.api.credits.OnAcquireFailure;
import com.schematic.api.credits.PreflightOptions;
import com.schematic.api.credits.PrewarmCompanyResolver;
import com.schematic.api.credits.RedisLeaseStore;
import com.schematic.api.credits.RedisReservationStore;
import com.schematic.api.credits.Reservation;
import com.schematic.api.credits.ReservationSettlement;
import com.schematic.api.credits.ReservationStore;
import com.schematic.api.credits.ServerCreditCheck;
import com.schematic.api.datastream.CheckFlagOptions;
import com.schematic.api.datastream.DataStreamClient;
import com.schematic.api.datastream.DataStreamException;
import com.schematic.api.datastream.DatastreamOptions;
import com.schematic.api.datastream.WasmRulesEngine;
import com.schematic.api.logger.ConsoleLogger;
import com.schematic.api.logger.LogLevel;
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
import com.schematic.api.types.PreflightRequestBody;
import com.schematic.api.types.RulesengineCheckFlagResult;
import java.time.Clock;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import redis.clients.jedis.JedisPooled;

public final class Schematic extends BaseSchematic implements AutoCloseable {

    // Namespaces the settle key so a reservation id can never collide with a caller's own
    // idempotency key, and so a recovery emit and an accidental second settle collapse to one
    // billed event across pods and restarts.
    private static final String RESERVATION_TRACK_IDEMPOTENCY_PREFIX = "lease-reservation:";

    private final Duration eventBufferInterval;
    private final EventBuffer eventBuffer;
    private final List<CacheProvider<RulesengineCheckFlagResult>> flagCheckCacheProviders;
    private final Map<String, Boolean> flagDefaults;
    private final SchematicLogger logger;
    private final String apiKey;
    private final Thread shutdownHook;
    private final boolean offline;
    private final HttpEventSender eventSender;
    // Not final: a DataStream that fails to start leaves none, and auto mode reads this per check.
    private volatile DataStreamClient dataStreamClient;
    private final DatastreamOptions datastreamOptions;
    // Credit leases. Null throughout when the caller did not configure them, which is what every
    // credit-aware path checks before doing anything.
    private final CreditLeaseMode creditLeaseMode;
    private final LeaseStore leaseStore;
    private final ReservationStore reservations;
    private final CreditLeaseManager creditLeaseManager;
    private final CreditCheck creditCheck;
    private final boolean leaseBackendShared;
    private final Duration serverReservationTtl;
    private final Duration prewarmResolveTimeout;
    // Runs the prewarms identify kicks off, so the caller's identify does not wait on a lease
    // acquire. Null when leases are not configured.
    private final ExecutorService prewarms;
    private volatile boolean closing;

    private Schematic(Builder builder) {
        super(buildClientOptions(builder.apiKey, builder));

        this.apiKey = builder.apiKey;
        this.eventBufferInterval =
                builder.eventBufferInterval != null ? builder.eventBufferInterval : Duration.ofMillis(5000);
        // A consumer-provided logger is used as-is (its own level governs); logLevel only
        // configures the default ConsoleLogger, which otherwise defaults to WARN.
        this.logger = builder.logger != null ? builder.logger : new ConsoleLogger(builder.logLevel);
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

            DataStreamClient started = new DataStreamClient(
                    this.datastreamOptions, this.apiKey, basePath, this.logger, rulesEngine, resolveSdkVersion());
            try {
                started.start();
            } catch (RuntimeException e) {
                // Nothing here can serve a local evaluation, so the client is dropped rather than
                // kept as a handle every later path has to re-test. Checks fall to the API, and
                // auto mode resolves to server-side gating on the strength of this field.
                this.logger.error("DataStream failed to start, falling back to API checks: " + e.getMessage());
                try {
                    started.close();
                } catch (Exception closing) {
                    this.logger.debug("DataStream close after a failed start: " + closing);
                }
                started = null;
            }
            this.dataStreamClient = started;
        } else {
            this.dataStreamClient = null;
        }

        // Credit leases and reservations, if the caller opted in.
        CreditLeaseConfig creditLeases = builder.creditLeases;
        if (creditLeases != null && this.offline) {
            this.logger.warn("creditLeases is configured but the client is offline, so checks return flag defaults "
                    + "with no credit gating");
        }
        CreditLeaseMode mode = null;
        Duration serverTtl = CreditLeaseDefaults.RESERVATION_TTL;
        Duration prewarmTimeout = CreditLeaseDefaults.PREWARM_RESOLVE_TIMEOUT;
        LeaseStore leases = null;
        ReservationStore holds = null;
        CreditLeaseManager manager = null;
        CreditCheck check = null;
        boolean sharedBackend = false;
        if (creditLeases != null && !this.offline) {
            mode = creditLeases.getMode() != null ? creditLeases.getMode() : CreditLeaseMode.AUTO;
            Duration configuredTtl = creditLeases.getDefaultReservationTtl() != null
                    ? creditLeases.getDefaultReservationTtl()
                    : CreditLeaseDefaults.RESERVATION_TTL;
            // The API refuses a hold expiring more than an hour after its own clock, and this TTL
            // is applied to the caller's, so clamp a step below the cap to leave room for skew.
            // Only server mode sends the value to the API: in client mode it sizes the local
            // sweep, so clamping it there would shorten holds for no reason.
            Duration maxTtl =
                    CreditLeaseDefaults.MAX_RESERVATION_TTL.minus(CreditLeaseDefaults.RESERVATION_TTL_SKEW_ALLOWANCE);
            serverTtl = mode == CreditLeaseMode.CLIENT || configuredTtl.compareTo(maxTtl) <= 0 ? configuredTtl : maxTtl;
            if (mode != CreditLeaseMode.CLIENT && configuredTtl.compareTo(maxTtl) > 0) {
                this.logger.warn("creditLeases.defaultReservationTtl of " + configuredTtl.toMillis()
                        + "ms is longer than the API will hold credits for; server-mode holds are clamped to "
                        + maxTtl.toMillis() + "ms");
            }
            if (creditLeases.getPrewarmResolveTimeout() != null) {
                prewarmTimeout = creditLeases.getPrewarmResolveTimeout();
            }

            // Server mode holds credits over the API, so none of the local plumbing is built and
            // the options that only steer it would silently do nothing. Say so once, at startup.
            if (mode == CreditLeaseMode.SERVER || (mode == CreditLeaseMode.AUTO && this.dataStreamClient == null)) {
                String clientOnly = clientOnlyOptions(creditLeases);
                if (!clientOnly.isEmpty()) {
                    this.logger.warn("creditLeases resolves to server mode, so " + clientOnly
                            + " will be ignored: those options only apply to client mode");
                }
            }
            // Auto with no DataStream is the server-mode default, not a misconfiguration.
            // Client without DataStream is the degraded path, where every check falls back to a
            // plain flag check with the usage ignored, so it still warns.
            if (mode == CreditLeaseMode.AUTO && this.dataStreamClient == null) {
                this.logger.info("creditLeases is configured and DataStream is not enabled, so credit reservations "
                        + "run in server mode, one check-and-reserve call per check");
            }
            if (mode == CreditLeaseMode.CLIENT && this.dataStreamClient == null) {
                this.logger.warn("creditLeases is configured but DataStream is not enabled, so check() falls back to "
                        + "plain flag checks with no credit gating");
            }
        }
        boolean usesLeases =
                mode == CreditLeaseMode.CLIENT || (mode == CreditLeaseMode.AUTO && this.dataStreamClient != null);
        if (creditLeases != null && !this.offline && usesLeases) {
            // Lease and hold state belongs in a shared cache so gating holds across horizontally
            // scaled pods. An explicit client wins; otherwise reuse the one the DataStream caches
            // are already configured with, so an existing Redis setup backs leases with no second
            // client to wire up.
            JedisPooled redisClient = inheritFromDataStream(
                    creditLeases.getRedisClient(),
                    this.dataStreamClient == null ? null : this.dataStreamClient.getRedisClient());
            String keyPrefix = inheritFromDataStream(
                    creditLeases.getRedisKeyPrefix(),
                    this.dataStreamClient == null ? null : this.dataStreamClient.getRedisKeyPrefix());
            if (redisClient != null) {
                sharedBackend = true;
                leases = new RedisLeaseStore(redisClient, keyPrefix, creditLeases.getDefaultLeaseDuration(), null);
                holds = new RedisReservationStore(redisClient, leases, keyPrefix, null);
            } else {
                // Without a shared backend each pod gates against its own leases, which defeats
                // the cross-pod protection that is the point of leasing, so warn rather than
                // degrade silently.
                this.logger.warn("creditLeases is enabled without a shared Redis backend, so lease and reservation "
                        + "state is per-process; configure a Redis client so leases gate across SDK instances");
                leases = new InMemoryLeaseStore(null);
                holds = new InMemoryReservationStore(leases, null);
            }
            manager = new CreditLeaseManager(
                    new ApiLeaseWireClient(credits()), leases, holds, creditLeases, this.logger, Clock.systemUTC());
            manager.startSweep();
            check = new CreditCheck(
                    // Null rather than a source wrapping nothing: CreditCheck degrades to a plain
                    // check on a null source, and a wrapper would sail past that guard and fail
                    // on the first cached-flag read instead.
                    this.dataStreamClient == null ? null : new DataStreamCreditCheckSource(this.dataStreamClient),
                    leases,
                    holds,
                    manager,
                    this.logger,
                    Clock.systemUTC(),
                    body -> eventBuffer.push(CreateEventRequestBody.builder()
                            .eventType(EventType.FLAG_CHECK)
                            .body(EventBody.of(body))
                            .sentAt(OffsetDateTime.now())
                            .build()),
                    null);
        }
        this.creditLeaseMode = mode;
        this.leaseStore = leases;
        this.reservations = holds;
        this.creditLeaseManager = manager;
        this.creditCheck = check;
        this.leaseBackendShared = sharedBackend;
        this.serverReservationTtl = serverTtl;
        this.prewarmResolveTimeout = prewarmTimeout;
        this.prewarms = manager == null
                ? null
                : Executors.newSingleThreadExecutor(runnable -> {
                    Thread thread = new Thread(runnable, "SchematicCreditLeasePrewarm");
                    // Daemon, so a prewarm in flight can never hold a shutting-down process open.
                    thread.setDaemon(true);
                    return thread;
                });

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
        private LogLevel logLevel;
        private Map<String, Boolean> flagDefaults;
        private List<CacheProvider<RulesengineCheckFlagResult>> cacheProviders;
        private boolean offline;
        private Duration eventBufferInterval;
        private int eventBufferMaxSize = 100;
        private String basePath;
        private Map<String, String> headers;
        private DatastreamOptions datastreamOptions;
        private String eventCaptureBaseUrl;
        private CreditLeaseConfig creditLeases;

        public Builder apiKey(String apiKey) {
            this.apiKey = apiKey;
            return this;
        }

        public Builder logger(SchematicLogger logger) {
            this.logger = logger;
            return this;
        }

        /**
         * Sets the level for the default {@link ConsoleLogger} (defaults to {@link LogLevel#WARN}).
         * Ignored when a custom {@link #logger(SchematicLogger)} is provided — that logger's own level
         * configuration is the source of truth.
         */
        public Builder logLevel(LogLevel logLevel) {
            this.logLevel = logLevel;
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

        /**
         * Enables credit holds on {@link Schematic#check} and
         * {@link Schematic#trackWithReservation}. Omit it to leave the client credit-unaware.
         */
        public Builder creditLeases(CreditLeaseConfig creditLeases) {
            this.creditLeases = creditLeases;
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
        return defaultFlagResult(flagKey, reason, err, null);
    }

    /**
     * The result a check falls back to when it cannot get an answer. {@code perCheckDefault} is
     * the caller's own default for this one check, which outranks the client-wide one; null means
     * the caller did not name one.
     */
    private RulesengineCheckFlagResult defaultFlagResult(
            String flagKey, String reason, String err, Boolean perCheckDefault) {
        return RulesengineCheckFlagResult.builder()
                .flagKey(flagKey)
                .reason(reason)
                .value(perCheckDefault != null ? perCheckDefault : getFlagDefault(flagKey))
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
        return tryDatastreamCheckFlag(flagKey, company, user, null);
    }

    private RulesengineCheckFlagResult tryDatastreamCheckFlag(
            String flagKey, Map<String, String> company, Map<String, String> user, CheckFlagOptions preflight) {
        if (dataStreamClient == null || !dataStreamClient.isConnected()) {
            return null;
        }
        try {
            return dataStreamClient.checkFlag(flagKey, company, user, preflight);
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
     * Checks a flag via the Schematic API, using the flag check result cache. A preflighted check
     * skips that cache in both directions, since it asks a different question than the plain
     * check the cache is keyed for.
     */
    private RulesengineCheckFlagResult checkFlagViaApi(
            String flagKey, Map<String, String> company, Map<String, String> user) {
        return checkFlagViaApi(flagKey, company, user, null, null, null);
    }

    /**
     * The REST flag check. {@code perCheckDefault} is what a failure resolves to, so a caller that
     * named a default on this one check gets it rather than the client-wide one.
     */
    private RulesengineCheckFlagResult checkFlagViaApi(
            String flagKey,
            Map<String, String> company,
            Map<String, String> user,
            Duration timeout,
            PreflightOptions preflight,
            Boolean perCheckDefault) {
        try {
            // Null once a preflight that the API would ignore, such as a zero usage, has been
            // dropped: such a check is a plain one and keeps the cache.
            PreflightRequestBody preflightBody = preflight != null ? preflight.toRequestBody() : null;
            // The cache is keyed by flag, company and user, and a preflighted check asks a
            // different question than a plain one: whether the action about to run would be
            // allowed. So a preflighted verdict is neither answered from the cache nor written
            // back to it.
            if (preflightBody == null) {
                RulesengineCheckFlagResult cached = getCachedFlag(flagKey, company, user);
                if (cached != null) {
                    return cached;
                }
            }

            CheckFlagRequestBody.Builder request =
                    CheckFlagRequestBody.builder().company(company).user(user);
            if (preflightBody != null) {
                request.preflight(preflightBody);
            }
            CheckFlagResponse response = timeout == null
                    ? features().checkFlag(flagKey, request.build())
                    : features()
                            .checkFlag(
                                    flagKey,
                                    request.build(),
                                    RequestOptions.builder()
                                            .timeout(CreditAmounts.millisAsInt(timeout), TimeUnit.MILLISECONDS)
                                            .build());
            RulesengineCheckFlagResult result = toRulesengineResult(response.getData());

            if (preflightBody == null) {
                cacheFlag(flagKey, result, company, user);
            }
            return result;
        } catch (Exception e) {
            logger.error("Error checking flag via API: " + e.getMessage());
            return defaultFlagResult(flagKey, "flag default", e.getMessage(), perCheckDefault);
        }
    }

    /**
     * Which reservation mode a {@code check()} with usage resolves to right now. Null means no
     * credit gating at all: leases are not configured, or the client is offline.
     *
     * <p>Auto resolves per check rather than once at startup: a DataStream that failed to start
     * leaves no client behind, and the checks that follow gate server-side instead of silently
     * dropping to a plain, ungated flag check. A DataStream that is merely disconnected stays in
     * client mode and degrades through the plain check, which has its own story for that.
     */
    private CreditLeaseMode effectiveLeaseMode() {
        if (creditLeaseMode == null || offline) {
            return null;
        }
        if (creditLeaseMode != CreditLeaseMode.AUTO) {
            return creditLeaseMode;
        }
        boolean clientPlumbingReady = creditCheck != null && leaseStore != null && reservations != null;
        return dataStreamClient != null && clientPlumbingReady ? CreditLeaseMode.CLIENT : CreditLeaseMode.SERVER;
    }

    /**
     * Credit-aware feature check. With credit leases configured and a usage on the options, this
     * gates the check against the company's credit balance and hands back a hold on success: pass
     * it to {@link #trackWithReservation} when the work completes.
     *
     * <p>In client mode the hold is carved out of a local lease and the flag is evaluated locally;
     * in server mode it is one check-and-reserve call that evaluates the flag and takes the hold
     * server-side.
     *
     * <p>Without credit leases, or without a usage, this is a plain flag check that issues no
     * hold. The caller's preflight still reaches whichever path answers it, local or the API, so
     * the check gates on the post-call balance, just without a hold.
     */
    public CheckResult check(
            String flagKey, Map<String, String> company, Map<String, String> user, CheckOptions options) {
        CheckOptions opts = options != null ? options : CheckOptions.builder().build();
        Callable<CheckResult> fallback = () -> plainCheck(flagKey, company, user, opts);
        CreditLeaseMode mode = effectiveLeaseMode();
        if (opts.getUsage() == null || mode == null) {
            return plainCheck(flagKey, company, user, opts);
        }

        CheckRequest request = new CheckRequest(
                flagKey,
                company,
                user,
                opts.getUsage(),
                opts.getEventSubtype(),
                opts.getOnAcquireFailure() == OnAcquireFailure.FAIL_OPEN,
                opts.getTimeout());
        if (mode == CreditLeaseMode.SERVER) {
            ServerCreditCheck serverCheck =
                    new ServerCreditCheck(features(), credits(), logger, serverReservationTtl, Clock.systemUTC());
            return serverCheck.check(request, opts.getTimeout(), () -> checkDefault(flagKey, opts), fallback);
        }
        // Client mode without the local plumbing keeps the old behavior: a plain, ungated check.
        if (creditCheck == null) {
            return plainCheck(flagKey, company, user, opts);
        }
        return creditCheck.check(request, fallback);
    }

    /**
     * Settles a hold issued by {@link #check}. In client mode it refunds the unspent slice to the
     * lease and emits a track event carrying the lease id; in server mode the event carries the
     * reservation id and the server settles the hold when it processes the event.
     *
     * <p>When the work outlived the hold's TTL and the sweeper already returned it, the usage
     * still has to be billed, so the event goes out anyway. A deterministic idempotency key
     * derived from the reservation id keeps that recovery emit, and an accidental second settle,
     * from billing twice.
     */
    public void trackWithReservation(Reservation reservation, double actualQuantity) {
        trackWithReservation(reservation, actualQuantity, null);
    }

    /** Settles a hold, attaching traits to the event it emits. */
    public void trackWithReservation(Reservation reservation, double actualQuantity, Map<String, Object> traits) {
        if (offline) {
            return;
        }
        // check() allows without a hold in several ordinary cases: the feature is not
        // credit-metered, the check failed open, the usage was zero, or leases are not configured.
        // Callers pass the result's reservation straight through, so take the null and say how to
        // bill the usage instead of throwing on a settle with nothing to settle.
        if (reservation == null) {
            logger.error("trackWithReservation was called without a reservation: the check allowed without taking a "
                    + "hold, so there is nothing to settle. Report the usage with track() instead.");
            return;
        }
        // A non-finite quantity must reach neither the store, where the clamp would claim the hold
        // with no refund of the unspent slice, nor the billing event. Skipping the settle leaves
        // the hold to its TTL, where the sweeper refunds all of it, so no credits are lost and
        // nothing bogus is billed.
        if (!CreditAmounts.isValidQuantity(actualQuantity)) {
            logger.error("trackWithReservation: invalid actualQuantity " + actualQuantity + " for reservation "
                    + reservation.getId() + "; skipping the settle, the hold is refunded at its TTL");
            return;
        }

        EventBodyTrack track;
        if (reservation.getMode() == CreditLeaseMode.SERVER || reservations == null) {
            // Server mode holds the credits server-side, so there is nothing local to consume. A
            // client-mode handle with no store still has to carry its lease id and its key, since
            // dropping either would double-debit the grant or double-bill the usage.
            track = ReservationSettlement.buildTrackEvent(reservation, actualQuantity);
        } else {
            try {
                ReservationSettlement.SettleOutcome outcome =
                        ReservationSettlement.settle(reservations, reservation, actualQuantity);
                track = outcome.getTrack();
                if (!outcome.isSettledLocally()) {
                    logger.debug("trackWithReservation: reservation " + reservation.getId() + " was not settled "
                            + "locally (expired, already settled, or the store was unreachable); emitting the track "
                            + "keyed for server-side dedupe");
                }
            } catch (RuntimeException e) {
                // The local settle failed, likely an unreachable Redis. The usage still has to be
                // billed, and the un-settled hold is reclaimed by the sweeper or at lease expiry.
                logger.warn("trackWithReservation: failed to settle reservation " + reservation.getId() + " locally ("
                        + e + "); emitting the track anyway");
                track = ReservationSettlement.buildTrackEvent(reservation, actualQuantity);
            }
        }

        try {
            eventBuffer.push(buildReservationSettleEvent(track, objectMapToJsonNode(traits), reservation.getId()));
            updateCompanyMetrics(track);
        } catch (Exception e) {
            logger.error("Error sending track event: " + e.getMessage());
        }
    }

    /**
     * Folds a track event into the cached company's metrics, so a local evaluation right after it
     * gates on the usage just recorded instead of waiting for the stream to push the new figure
     * back. A settle is a track, and reads the same way.
     */
    private void updateCompanyMetrics(EventBodyTrack body) {
        Map<String, String> company = body.getCompany().orElse(null);
        if (company == null || company.isEmpty() || dataStreamClient == null || !dataStreamClient.isConnected()) {
            return;
        }
        try {
            dataStreamClient.updateCompanyMetrics(body);
        } catch (Exception e) {
            logger.error("Failed to update company metrics: " + e.getMessage());
        }
    }

    /**
     * Warms a credit lease for each named credit type, so the first {@link #check} against it does
     * not pay the acquire round trip. Failures are logged, never thrown.
     *
     * <p>When the company keys carry no id, this actively fetches the company over the DataStream,
     * which both resolves the id and warms the cache so the first check hits the lease path.
     */
    public void prewarm(Map<String, String> company, List<String> creditTypeIds) {
        if (creditLeaseManager == null || leaseStore == null) {
            logger.debug(
                    effectiveLeaseMode() == CreditLeaseMode.SERVER
                            ? "prewarm is a no-op in server mode, since there is no local lease to warm"
                            : "prewarm was called but credit leases are not configured");
            return;
        }
        if (company == null || company.isEmpty()) {
            logger.debug("prewarm needs company keys");
            return;
        }
        if (creditTypeIds == null || creditTypeIds.isEmpty()) {
            logger.debug("prewarm was given no credit types");
            return;
        }
        if (closing) {
            // close() only waits out the prewarms it spawned; a caller invoking prewarm directly
            // would otherwise install a lease after the release has already listed the store.
            logger.debug("prewarm: the client is closing, skipping the acquire");
            return;
        }
        String companyId = resolveCompanyIdWithWait(company);
        if (companyId == null) {
            logger.debug("prewarm: the company did not resolve within " + prewarmResolveTimeout.toMillis()
                    + "ms (the first check will acquire)");
            return;
        }
        for (String creditTypeId : creditTypeIds) {
            try {
                creditLeaseManager.acquireIfNeeded(companyId, creditTypeId);
            } catch (RuntimeException e) {
                logger.warn("prewarm: failed to acquire a lease for " + creditTypeId + ": " + e);
            }
        }
    }

    /**
     * Resolves the company id, actively fetching over the DataStream when only secondary keys were
     * given, which warms the cache as a side effect. Null when the company never surfaced within
     * the prewarm resolve timeout.
     */
    private String resolveCompanyIdWithWait(Map<String, String> company) {
        if (dataStreamClient == null) {
            return company.get("id");
        }
        return PrewarmCompanyResolver.resolve(
                company,
                dataStreamClient::getCachedCompany,
                dataStreamClient::getCompany,
                prewarmResolveTimeout,
                CreditLeaseDefaults.PREWARM_POLL_INTERVAL,
                () -> closing,
                error -> {
                    logger.debug("prewarm: the DataStream company fetch failed (" + error + ")");
                    return null;
                });
    }

    /** The plain flag check a credit-aware check defers to, with the caller's preflight threaded through. */
    private CheckResult plainCheck(
            String flagKey, Map<String, String> company, Map<String, String> user, CheckOptions options) {
        PreflightOptions preflight = PreflightOptions.fromUsage(options.getUsage(), options.getEventSubtype());
        RulesengineCheckFlagResult result;
        if (offline) {
            boolean value = checkDefault(flagKey, options);
            result = RulesengineCheckFlagResult.builder()
                    .flagKey(flagKey)
                    .reason("flag default")
                    .value(value)
                    .build();
        } else {
            RulesengineCheckFlagResult dsResult = tryDatastreamCheckFlag(
                    flagKey, company, user, DataStreamCreditCheckSource.toEngineOptions(preflight));
            if (dsResult != null) {
                enqueueFlagCheckEvent(flagKey, dsResult, company, user);
                result = dsResult;
            } else {
                // The API answers a preflight too, so the caller's usage gates the REST path the
                // same way it gates a local evaluation. The caller's timeout applies, since this
                // is the call it is waiting on.
                result = checkFlagViaApi(
                        flagKey, company, user, options.getTimeout(), preflight, options.getDefaultValue());
            }
        }
        return new CheckResult(
                result.getValue(),
                result.getValue(),
                result.getReason(),
                result.getFlagKey(),
                result.getFlagId().orElse(null),
                result.getEntitlement().orElse(null),
                null,
                result.getErr().orElse(null));
    }

    /**
     * Builds the event that settles a hold. Package-private for unit-testing the mapping. The key
     * is derived from the reservation id, so a recovery emit and an accidental second settle
     * collapse to one billed event server-side.
     */
    static CreateEventRequestBody buildReservationSettleEvent(
            EventBodyTrack track, Map<String, JsonNode> traits, String reservationId) {
        EventBodyTrack body =
                EventBodyTrack.builder().from(track).traits(traits).build();
        return buildTrackEvent(
                EventBody.of(body),
                TrackOptions.builder()
                        .idempotencyKey(RESERVATION_TRACK_IDEMPOTENCY_PREFIX + reservationId)
                        .build());
    }

    /** The caller's default for this flag: the per-check one when set, otherwise the client's. */
    private boolean checkDefault(String flagKey, CheckOptions options) {
        return options.getDefaultValue() != null ? options.getDefaultValue() : getFlagDefault(flagKey);
    }

    public void identify(
            Map<String, String> keys, EventBodyIdentifyCompany company, String name, Map<String, Object> traits) {
        identify(keys, company, name, traits, null);
    }

    public void identify(
            Map<String, String> keys,
            EventBodyIdentifyCompany company,
            String name,
            Map<String, Object> traits,
            IdentifyOptions options) {
        if (offline) return;

        try {
            EventBodyIdentify body = EventBodyIdentify.builder()
                    .keys(keys)
                    .company(company)
                    .name(name)
                    .traits(objectMapToJsonNode(traits))
                    .build();

            eventBuffer.push(buildIdentifyEvent(EventBody.of(body), options));
        } catch (Exception e) {
            logger.error("Error sending identify event: " + e.getMessage());
        }

        List<String> creditTypeIds = options != null ? options.getPrewarm() : null;
        if (creditTypeIds != null && !creditTypeIds.isEmpty() && prewarms != null) {
            Map<String, String> companyKeys = company != null ? company.getKeys() : null;
            // Flush first so the server processes the identify promptly: without it the company
            // can sit in the buffer for a full flush interval while the prewarm waits on us.
            try {
                prewarms.execute(() -> {
                    try {
                        eventBuffer.flush();
                    } catch (RuntimeException e) {
                        logger.debug("identify: the flush before the prewarm failed: " + e);
                    }
                    try {
                        prewarm(companyKeys, creditTypeIds);
                    } catch (RuntimeException e) {
                        logger.warn("identify: the prewarm failed: " + e);
                    }
                });
            } catch (RejectedExecutionException e) {
                // identify still recorded the company; only the warm-up is dropped, and a
                // caller identifying after close() should not be handed a shutdown race to
                // catch.
                logger.debug("identify: the client is closed, skipping the prewarm");
            }
        }
    }

    public void track(
            String eventName, Map<String, String> company, Map<String, String> user, Map<String, Object> traits) {
        track(eventName, company, user, traits, 1L, null);
    }

    public void track(
            String eventName,
            Map<String, String> company,
            Map<String, String> user,
            Map<String, Object> traits,
            Long quantity) {
        track(eventName, company, user, traits, quantity, null);
    }

    public void track(
            String eventName,
            Map<String, String> company,
            Map<String, String> user,
            Map<String, Object> traits,
            TrackOptions options) {
        track(eventName, company, user, traits, 1L, options);
    }

    public void track(
            String eventName,
            Map<String, String> company,
            Map<String, String> user,
            Map<String, Object> traits,
            Long quantity,
            TrackOptions options) {
        if (offline) return;

        try {
            EventBodyTrack body = EventBodyTrack.builder()
                    .event(eventName)
                    .company(company)
                    .user(user)
                    .traits(objectMapToJsonNode(traits))
                    .quantity(quantity)
                    .build();

            eventBuffer.push(buildTrackEvent(EventBody.of(body), options));
            updateCompanyMetrics(body);
        } catch (Exception e) {
            logger.error("Error sending track event: " + e.getMessage());
        }
    }

    /**
     * Builds the identify event pushed to the buffer. Package-private for unit-testing the
     * option-to-event mapping. {@code sent_at} is stamped with the local clock; a null option
     * field passes through to {@code Optional.empty()} and is omitted from the wire.
     */
    static CreateEventRequestBody buildIdentifyEvent(EventBody body, IdentifyOptions options) {
        CreateEventRequestBody._FinalStage event = CreateEventRequestBody.builder()
                .eventType(EventType.IDENTIFY)
                .body(body)
                .sentAt(OffsetDateTime.now());
        if (options != null) {
            event.idempotencyKey(options.getIdempotencyKey());
        }
        return event.build();
    }

    /**
     * Builds the track event pushed to the buffer. Package-private for unit-testing the
     * option-to-event mapping. An explicit {@code sentAt} option overrides the local-clock default
     * (required when {@code trustedClientClock} is set); other null option fields pass through to
     * {@code Optional.empty()} and are omitted from the wire.
     */
    static CreateEventRequestBody buildTrackEvent(EventBody body, TrackOptions options) {
        CreateEventRequestBody._FinalStage event = CreateEventRequestBody.builder()
                .eventType(EventType.TRACK)
                .body(body)
                .sentAt(options != null && options.getSentAt() != null ? options.getSentAt() : OffsetDateTime.now());
        if (options != null) {
            event.idempotencyKey(options.getIdempotencyKey())
                    .trustedClientClock(options.getTrustedClientClock())
                    .backfill(options.getBackfill());
        }
        return event.build();
    }

    @Override
    public void close() {
        closing = true;
        try {
            // Remove shutdown hook if we're closing explicitly
            try {
                Runtime.getRuntime().removeShutdownHook(this.shutdownHook);
            } catch (IllegalStateException e) {
                // Shutdown is already in progress, hook will run automatically
            }

            if (creditLeaseManager != null) {
                // Refuse new lease work first, so the waits below are waiting on work that is
                // already unwinding rather than on work still starting. Both steps run for a
                // shared backend too: the work must not outlive the client, even where there is
                // nothing to release.
                creditLeaseManager.stop();
                // One budget across both waits, not each timeout in turn: a caller closing a
                // client wants a bounded shutdown, not the sum of every wait inside it.
                long deadline = System.nanoTime() + CreditLeaseDefaults.SHUTDOWN_DRAIN_TIMEOUT.toNanos();
                if (prewarms != null) {
                    prewarms.shutdown();
                    try {
                        if (!prewarms.awaitTermination(remaining(deadline).toMillis(), TimeUnit.MILLISECONDS)) {
                            logger.warn("Timed out waiting for in-flight prewarms on close");
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                creditLeaseManager.drain(remaining(deadline));
                // Never release leases held in a shared backend: a sibling process is still
                // drawing on them.
                if (!leaseBackendShared) {
                    creditLeaseManager.releaseAllLocalLeases(remaining(deadline));
                }
                creditLeaseManager.close(remaining(deadline));
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

    /**
     * Resolves one lease Redis setting against the DataStream cache's. The client and the key
     * prefix resolve independently: a lease client of its own does not cost a caller the
     * DataStream prefix, which would split the key layout of a mixed fleet sharing those leases.
     */
    static <T> T inheritFromDataStream(T configured, T fromDataStream) {
        return configured != null ? configured : fromDataStream;
    }

    /**
     * Names the configured options that only steer client-mode plumbing, so server mode can say
     * once that it is ignoring them.
     */
    private static String clientOnlyOptions(CreditLeaseConfig config) {
        List<String> names = new ArrayList<>();
        if (config.getDefaultLeaseDuration() != null) {
            names.add("defaultLeaseDuration");
        }
        if (config.getDefaultLeaseSize() != null) {
            names.add("defaultLeaseSize");
        }
        if (config.getLowWaterMark() != null) {
            names.add("lowWaterMark");
        }
        if (config.getSweepInterval() != null) {
            names.add("sweepInterval");
        }
        if (config.getRedisClient() != null) {
            names.add("redisClient");
        }
        if (config.getRedisKeyPrefix() != null) {
            names.add("redisKeyPrefix");
        }
        if (config.getPrewarmResolveTimeout() != null) {
            names.add("prewarmResolveTimeout");
        }
        if (config.getOverrides() != null && !config.getOverrides().isEmpty()) {
            names.add("overrides");
        }
        return String.join(", ", names);
    }

    private static Duration remaining(long deadlineNanos) {
        long left = deadlineNanos - System.nanoTime();
        return left <= 0 ? Duration.ZERO : Duration.ofNanos(left);
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

    private static Map<String, JsonNode> objectMapToJsonNode(Map<String, Object> map) {
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
