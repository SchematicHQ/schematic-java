/*
 * Schematic Java SDK - E2E Test App
 *
 * HTTP server implementing the shared E2E test app contract.
 * The SDK client is initialized lazily via POST /configure.
 *
 * Usage:
 *   ./gradlew :sample-app:run
 *
 * Endpoints:
 *   GET  /health          - Returns {"status":"waiting"} or {"status":"configured"}
 *   POST /configure       - Initialize SDK client with config
 *   POST /check-flag      - Check a feature flag
 *   POST /identify        - Submit identify event
 *   POST /track           - Submit track event
 *   POST /set-flag-default - Set a flag default value
 */
package sample;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.schematic.api.Schematic;
import com.schematic.api.cache.LocalCache;
import com.schematic.api.cache.RedisCacheConfig;
import com.schematic.api.datastream.DatastreamOptions;
import com.schematic.api.types.CheckFlagRequestBody;
import com.schematic.api.types.EventBodyIdentify;
import com.schematic.api.types.EventBodyIdentifyCompany;
import com.schematic.api.types.EventBodyTrack;
import com.schematic.api.types.RulesengineCheckFlagResult;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetSocketAddress;
import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public final class App {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final int CACHE_TTL_MS = 2000;

    private static volatile Schematic client;
    private static volatile Map<String, Object> currentConfig;

    public static void main(String[] args) throws Exception {
        int port = Integer.parseInt(System.getenv().getOrDefault("PORT", "8080"));

        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/health", App::handleHealth);
        server.createContext("/configure", App::handleConfigure);
        server.createContext("/check-flag", App::handleCheckFlag);
        server.createContext("/identify", App::handleIdentify);
        server.createContext("/track", App::handleTrack);
        server.createContext("/set-flag-default", App::handleSetFlagDefault);
        server.setExecutor(null);
        server.start();

        System.out.println("E2E test app listening on port " + port);
    }

    private static void handleHealth(HttpExchange exchange) throws IOException {
        if (!"GET".equals(exchange.getRequestMethod())) {
            sendJson(exchange, 405, Map.of("error", "Method Not Allowed"));
            return;
        }

        Map<String, Object> response = new LinkedHashMap<>();
        if (client == null) {
            response.put("status", "waiting");
        } else {
            response.put("status", "configured");
            response.put("config", currentConfig);
            response.put("cacheTtlMs", CACHE_TTL_MS);
        }
        sendJson(exchange, 200, response);
    }

    @SuppressWarnings("unchecked")
    private static void handleConfigure(HttpExchange exchange) throws IOException {
        if (!"POST".equals(exchange.getRequestMethod())) {
            sendJson(exchange, 405, Map.of("error", "Method Not Allowed"));
            return;
        }

        try {
            Map<String, Object> config = MAPPER.readValue(exchange.getRequestBody(), Map.class);
            logRequest("/configure", config);
            currentConfig = config;

            String apiKey = (String) config.get("apiKey");
            String baseUrl = (String) config.get("baseUrl");
            String eventCaptureBaseUrl = (String) config.get("eventCaptureBaseUrl");
            boolean offline = Boolean.TRUE.equals(config.get("offline"));
            boolean noCache = Boolean.TRUE.equals(config.get("noCache"));
            boolean useDataStream = Boolean.TRUE.equals(config.get("useDataStream"));
            String redisUrl = (String) config.get("redisUrl");
            String replicatorUrl = (String) config.get("replicatorUrl");

            // Parse flag defaults
            Map<String, Boolean> flagDefaults = new HashMap<>();
            Object flagDefaultsRaw = config.get("flagDefaults");
            if (flagDefaultsRaw instanceof Map) {
                for (Map.Entry<String, Object> entry : ((Map<String, Object>) flagDefaultsRaw).entrySet()) {
                    flagDefaults.put(entry.getKey(), Boolean.TRUE.equals(entry.getValue()));
                }
            }

            // Close existing client if reconfiguring
            if (client != null) {
                try {
                    client.close();
                } catch (Exception e) {
                    // ignore
                }
            }

            Schematic.Builder builder = Schematic.builder().apiKey(apiKey);

            if (baseUrl != null) {
                builder.basePath(baseUrl);
            }
            if (eventCaptureBaseUrl != null) {
                builder.eventCaptureBaseUrl(eventCaptureBaseUrl);
            }
            if (offline) {
                builder.offline(true);
            }
            if (!flagDefaults.isEmpty()) {
                builder.flagDefaults(flagDefaults);
            }

            // Cache configuration
            if (noCache) {
                builder.cacheProviders(Collections.emptyList());
            } else if (redisUrl != null && !useDataStream) {
                // Redis for flag check cache only (no datastream)
                // Note: flag check cache uses RedisCacheConfig through DatastreamOptions
                // For non-datastream Redis, we use LocalCache with short TTL
                builder.cacheProviders(
                        Collections.singletonList(new LocalCache<>(1000, Duration.ofMillis(CACHE_TTL_MS))));
            } else {
                builder.cacheProviders(
                        Collections.singletonList(new LocalCache<>(1000, Duration.ofMillis(CACHE_TTL_MS))));
            }

            // DataStream configuration
            if (useDataStream) {
                DatastreamOptions.Builder dsBuilder =
                        DatastreamOptions.builder().cacheTTL(Duration.ofMillis(CACHE_TTL_MS));

                if (redisUrl != null) {
                    dsBuilder.redisCache(
                            RedisCacheConfig.builder().endpoint(redisUrl).build());
                }

                if (replicatorUrl != null) {
                    dsBuilder.withReplicatorMode(replicatorUrl);
                }

                builder.datastreamOptions(dsBuilder.build());
            }

            client = builder.build();

            sendJson(exchange, 200, Map.of("success", true));
        } catch (Exception e) {
            sendJson(exchange, 500, errorDetail(e));
        }
    }

    @SuppressWarnings("unchecked")
    private static void handleCheckFlag(HttpExchange exchange) throws IOException {
        if (!"POST".equals(exchange.getRequestMethod())) {
            sendJson(exchange, 405, Map.of("error", "Method Not Allowed"));
            return;
        }
        if (client == null) {
            sendJson(exchange, 503, Map.of("error", "Not configured"));
            return;
        }

        try {
            Map<String, Object> body = MAPPER.readValue(exchange.getRequestBody(), Map.class);
            logRequest("/check-flag", body);
            String flagKey = (String) body.get("flagKey");
            Map<String, String> company = toStringMap(body.get("company"));
            Map<String, String> user = toStringMap(body.get("user"));

            boolean value = client.checkFlag(flagKey, company, user);

            sendJson(exchange, 200, Map.of("value", value));
        } catch (Exception e) {
            Map<String, Object> resp = new LinkedHashMap<>();
            resp.put("value", false);
            resp.putAll(errorDetail(e));
            sendJson(exchange, 200, resp);
        }
    }

    @SuppressWarnings("unchecked")
    private static void handleIdentify(HttpExchange exchange) throws IOException {
        if (!"POST".equals(exchange.getRequestMethod())) {
            sendJson(exchange, 405, Map.of("error", "Method Not Allowed"));
            return;
        }
        if (client == null) {
            sendJson(exchange, 503, Map.of("error", "Not configured"));
            return;
        }

        try {
            Map<String, Object> body = MAPPER.readValue(exchange.getRequestBody(), Map.class);
            logRequest("/identify", body);
            Map<String, String> keys = toStringMap(body.get("keys"));
            Map<String, String> companyKeys = toStringMap(body.get("company"));

            // Build company if provided
            EventBodyIdentifyCompany company = null;
            if (companyKeys != null && !companyKeys.isEmpty()) {
                company = EventBodyIdentifyCompany.builder().keys(companyKeys).build();
            }

            // User keys default to the top-level keys
            Map<String, String> userKeys = keys;
            Map<String, String> userFromBody = toStringMap(body.get("user"));
            if (userFromBody != null && !userFromBody.isEmpty()) {
                userKeys = userFromBody;
            }

            client.identify(userKeys != null ? userKeys : Collections.emptyMap(), company, null, null);

            sendJson(exchange, 200, Map.of("success", true));
        } catch (Exception e) {
            Map<String, Object> resp = new LinkedHashMap<>();
            resp.put("success", false);
            resp.putAll(errorDetail(e));
            sendJson(exchange, 200, resp);
        }
    }

    @SuppressWarnings("unchecked")
    private static void handleTrack(HttpExchange exchange) throws IOException {
        if (!"POST".equals(exchange.getRequestMethod())) {
            sendJson(exchange, 405, Map.of("error", "Method Not Allowed"));
            return;
        }
        if (client == null) {
            sendJson(exchange, 503, Map.of("error", "Not configured"));
            return;
        }

        try {
            Map<String, Object> body = MAPPER.readValue(exchange.getRequestBody(), Map.class);
            logRequest("/track", body);
            String event = (String) body.get("event");
            Map<String, String> company = toStringMap(body.get("company"));
            Map<String, String> user = toStringMap(body.get("user"));
            Long quantity = body.containsKey("quantity") ? ((Number) body.get("quantity")).longValue() : null;

            if (quantity != null) {
                client.track(event, company, user, null, quantity);
            } else {
                client.track(event, company, user, null);
            }

            sendJson(exchange, 200, Map.of("success", true));
        } catch (Exception e) {
            Map<String, Object> resp = new LinkedHashMap<>();
            resp.put("success", false);
            resp.putAll(errorDetail(e));
            sendJson(exchange, 200, resp);
        }
    }

    @SuppressWarnings("unchecked")
    private static void handleSetFlagDefault(HttpExchange exchange) throws IOException {
        if (!"POST".equals(exchange.getRequestMethod())) {
            sendJson(exchange, 405, Map.of("error", "Method Not Allowed"));
            return;
        }
        if (client == null) {
            sendJson(exchange, 503, Map.of("error", "Not configured"));
            return;
        }

        try {
            Map<String, Object> body = MAPPER.readValue(exchange.getRequestBody(), Map.class);
            logRequest("/set-flag-default", body);
            String flagKey = (String) body.get("flagKey");
            boolean value = Boolean.TRUE.equals(body.get("value"));

            client.setFlagDefault(flagKey, value);

            sendJson(exchange, 200, Map.of("success", true));
        } catch (Exception e) {
            Map<String, Object> resp = new LinkedHashMap<>();
            resp.put("success", false);
            resp.putAll(errorDetail(e));
            sendJson(exchange, 200, resp);
        }
    }

    @SuppressWarnings("unchecked")
    private static Map<String, String> toStringMap(Object obj) {
        if (obj == null) {
            return null;
        }
        Map<String, Object> raw = (Map<String, Object>) obj;
        Map<String, String> result = new LinkedHashMap<>();
        for (Map.Entry<String, Object> entry : raw.entrySet()) {
            result.put(entry.getKey(), String.valueOf(entry.getValue()));
        }
        return result;
    }

    private static void logRequest(String endpoint, Map<String, Object> body) {
        try {
            System.out.println("[" + endpoint + "] " + MAPPER.writeValueAsString(body));
        } catch (Exception e) {
            System.out.println("[" + endpoint + "] (failed to serialize body)");
        }
    }

    private static Map<String, Object> errorDetail(Exception e) {
        Map<String, Object> detail = new LinkedHashMap<>();
        detail.put("type", e.getClass().getName());
        detail.put("message", e.getMessage());
        if (e.getCause() != null) {
            detail.put("cause", e.getCause().getClass().getName() + ": " + e.getCause().getMessage());
        }
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        detail.put("stackTrace", sw.toString());
        // Also print to stderr for test runner visibility
        System.err.println("[ERROR] " + e.getClass().getName() + ": " + e.getMessage());
        e.printStackTrace(System.err);
        return detail;
    }

    private static void sendJson(HttpExchange exchange, int status, Object obj) throws IOException {
        byte[] bytes = MAPPER.writeValueAsBytes(obj);
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(status, bytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }
}
