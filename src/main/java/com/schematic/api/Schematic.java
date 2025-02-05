package com.schematic.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.schematic.api.cache.CacheProvider;
import com.schematic.api.cache.LocalCache;
import com.schematic.api.core.ClientOptions;
import com.schematic.api.core.Environment;
import com.schematic.api.core.ObjectMappers;
import com.schematic.api.logger.ConsoleLogger;
import com.schematic.api.logger.SchematicLogger;
import com.schematic.api.resources.features.types.CheckFlagResponse;
import com.schematic.api.types.CheckFlagRequestBody;
import com.schematic.api.types.CreateEventRequestBody;
import com.schematic.api.types.CreateEventRequestBodyEventType;
import com.schematic.api.types.EventBody;
import com.schematic.api.types.EventBodyIdentify;
import com.schematic.api.types.EventBodyIdentifyCompany;
import com.schematic.api.types.EventBodyTrack;
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
    private final List<CacheProvider<Boolean>> flagCheckCacheProviders;
    private final Map<String, Boolean> flagDefaults;
    private final SchematicLogger logger;
    private final String apiKey;
    private final Thread shutdownHook;
    private final boolean offline;

    private Schematic(Builder builder) {
        super(buildClientOptions(builder.apiKey, builder));

        this.apiKey = builder.apiKey;
        this.eventBufferInterval =
                builder.eventBufferInterval != null ? builder.eventBufferInterval : Duration.ofMillis(5000);
        this.logger = builder.logger != null ? builder.logger : new ConsoleLogger();
        this.flagDefaults = builder.flagDefaults != null ? builder.flagDefaults : new HashMap<>();
        this.offline = builder.offline;
        this.flagCheckCacheProviders =
                builder.cacheProviders != null ? builder.cacheProviders : Collections.singletonList(new LocalCache<>());

        this.eventBuffer = new EventBuffer(
                super.events(),
                this.logger,
                builder.eventBufferMaxSize,
                builder.eventBufferInterval != null ? builder.eventBufferInterval : Duration.ofMillis(5000));

        this.shutdownHook = new Thread(
                () -> {
                    try {
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
        private List<CacheProvider<Boolean>> cacheProviders;
        private boolean offline;
        private Duration eventBufferInterval;
        private int eventBufferMaxSize = 100;
        private String basePath;
        private Map<String, String> headers;

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

        public Builder cacheProviders(List<CacheProvider<Boolean>> cacheProviders) {
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

        public Schematic build() {
            if (apiKey == null) {
                throw new IllegalStateException("API key must be set");
            }
            return new Schematic(this);
        }
    }

    private static ClientOptions buildClientOptions(String apiKey, Builder builder) {
        String basePath = builder.basePath != null ? builder.basePath : "https://api.schematichq.com";
        return ClientOptions.builder()
                .environment(Environment.custom(basePath))
                .addHeader("X-Schematic-Api-Key", apiKey)
                .addHeader("Content-Type", "application/json")
                .build();
    }

    public List<CacheProvider<Boolean>> getFlagCheckCacheProviders() {
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

    public boolean checkFlag(String flagKey, Map<String, String> company, Map<String, String> user) {
        if (offline) {
            return getFlagDefault(flagKey);
        }

        try {
            String cacheKey = buildCacheKey(flagKey, company, user);

            // Check cache
            for (CacheProvider<Boolean> provider : flagCheckCacheProviders) {
                Boolean cachedValue = provider.get(cacheKey);
                if (cachedValue != null) {
                    return cachedValue;
                }
            }

            // Make API call
            CheckFlagRequestBody request =
                    CheckFlagRequestBody.builder().company(company).user(user).build();

            CheckFlagResponse response = features().checkFlag(flagKey, request);
            boolean value = response.getData().getValue();

            // Update cache
            for (CacheProvider<Boolean> provider : flagCheckCacheProviders) {
                provider.set(cacheKey, value);
            }

            return value;
        } catch (Exception e) {
            logger.error("Error checking flag: " + e.getMessage());
            return getFlagDefault(flagKey);
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
                    .eventType(CreateEventRequestBodyEventType.IDENTIFY)
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
        if (offline) return;

        try {
            EventBodyTrack body = EventBodyTrack.builder()
                    .event(eventName)
                    .company(company)
                    .user(user)
                    .traits(objectMapToJsonNode(traits))
                    .build();

            CreateEventRequestBody event = CreateEventRequestBody.builder()
                    .eventType(CreateEventRequestBodyEventType.TRACK)
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
