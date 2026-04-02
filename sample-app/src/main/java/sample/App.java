/*
 * Schematic Java Client - Datastream Test Server
 *
 * This example demonstrates how to use the Schematic Java client to check feature flags
 * with DataStream support, including replicator mode.
 *
 * Environment Variables:
 * - SCHEMATIC_API_KEY: Your Schematic API key (required)
 * - SCHEMATIC_API_URL: Schematic API base URL (default: https://api.schematichq.com)
 * - SERVER_PORT: Port to listen on (default: 8080)
 * - CACHE_TTL_MS: Cache TTL in milliseconds (default: 5000)
 * - REDIS_URL: Redis server endpoint in host:port format (default: localhost:6379)
 * - REDIS_PASSWORD: Redis password (optional)
 * - REPLICATOR_HEALTH_URL: Replicator health check URL (default: http://localhost:8090/ready)
 * - USE_REPLICATOR: Set to "true" to enable replicator mode (default: false)
 *
 * Usage:
 * 1. Set environment variables (only SCHEMATIC_API_KEY is required)
 * 2. Run: ./gradlew :sample-app:run
 * 3. Test endpoints:
 *    - GET / - Welcome message
 *    - GET /config - Show current configuration
 *    - GET /health - Health check with datastream status
 *    - GET /datastream-status - DataStream/replicator connection status
 *    - POST /checkflag - Check a feature flag
 *
 * Example checkflag request:
 *   curl -X POST http://localhost:8080/checkflag \
 *     -H "Content-Type: application/json" \
 *     -d '{"flag-key":"my-flag","company":{"id":"comp-123"},"user":{"id":"user-456"}}'
 *
 * Replicator mode example:
 *   export SCHEMATIC_API_KEY="your-key"
 *   export USE_REPLICATOR=true
 *   export REDIS_URL="localhost:6379"
 *   export REPLICATOR_HEALTH_URL="http://localhost:8090/ready"
 *   ./gradlew :sample-app:run
 */
package sample;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.schematic.api.Schematic;
import com.schematic.api.cache.RedisCacheConfig;
import com.schematic.api.datastream.DatastreamOptions;
import com.schematic.api.types.RulesengineCheckFlagResult;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.time.Duration;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

public final class App {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static Schematic schematic;
    private static String schematicApiUrl;
    private static int cacheTtlMs;
    private static int serverPort;
    private static boolean useReplicator;
    private static String replicatorHealthUrl;
    private static String redisUrl;

    public static void main(String[] args) throws Exception {
        String apiKey = System.getenv("SCHEMATIC_API_KEY");
        if (apiKey == null || apiKey.isEmpty()) {
            System.err.println("ERROR: SCHEMATIC_API_KEY environment variable is not set");
            System.exit(1);
        }

        schematicApiUrl = envOrDefault("SCHEMATIC_API_URL", "https://api.schematichq.com");
        serverPort = Integer.parseInt(envOrDefault("SERVER_PORT", "8080"));
        cacheTtlMs = Integer.parseInt(envOrDefault("CACHE_TTL_MS", "5000"));
        redisUrl = envOrDefault("REDIS_URL", "localhost:6379");
        useReplicator = "true".equalsIgnoreCase(envOrDefault("USE_REPLICATOR", "false"));
        replicatorHealthUrl = envOrDefault("REPLICATOR_HEALTH_URL", "http://localhost:8090/ready");

        // Configure Redis cache
        RedisCacheConfig.Builder redisBuilder = RedisCacheConfig.builder().endpoint(redisUrl);
        String redisPassword = System.getenv("REDIS_PASSWORD");
        if (redisPassword != null && !redisPassword.isEmpty()) {
            redisBuilder.password(redisPassword);
        }

        // Configure DataStream options with Redis
        DatastreamOptions.Builder datastreamBuilder = DatastreamOptions.builder()
                .cacheTTL(Duration.ofMillis(cacheTtlMs))
                .redisCache(redisBuilder.build());

        if (useReplicator) {
            datastreamBuilder.withReplicatorMode(replicatorHealthUrl);
        }

        DatastreamOptions datastreamOptions = datastreamBuilder.build();

        schematic = Schematic.builder()
                .apiKey(apiKey)
                .basePath(schematicApiUrl)
                .datastreamOptions(datastreamOptions)
                .build();

        HttpServer server = HttpServer.create(new InetSocketAddress(serverPort), 0);
        server.createContext("/", App::handleRoot);
        server.createContext("/config", App::handleConfig);
        server.createContext("/health", App::handleHealth);
        server.createContext("/datastream-status", App::handleDatastreamStatus);
        server.createContext("/checkflag", App::handleCheckFlag);
        server.setExecutor(null);
        server.start();

        System.out.println("Datastream Test Server started on port " + serverPort);
        System.out.println("Mode: " + (useReplicator ? "replicator" : "direct datastream"));
        System.out.println("Redis: " + redisUrl);
        System.out.println("Endpoints:");
        System.out.println("  GET  /                  - Welcome message");
        System.out.println("  GET  /config            - Show configuration");
        System.out.println("  GET  /health            - Health check");
        System.out.println("  GET  /datastream-status - DataStream connection status");
        System.out.println("  POST /checkflag         - Check a feature flag");
    }

    private static void handleRoot(HttpExchange exchange) throws IOException {
        if (!"GET".equals(exchange.getRequestMethod())) {
            sendMethodNotAllowed(exchange);
            return;
        }
        sendText(exchange, 200, "Welcome to the Schematic Datastream Test Server!");
    }

    private static void handleConfig(HttpExchange exchange) throws IOException {
        if (!"GET".equals(exchange.getRequestMethod())) {
            sendMethodNotAllowed(exchange);
            return;
        }

        Map<String, Object> config = new LinkedHashMap<>();
        config.put("schematicApiUrl", schematicApiUrl);
        config.put("cacheTtlMs", cacheTtlMs);
        config.put("hasApiKey", true);
        config.put("redisUrl", redisUrl);
        config.put("replicatorMode", schematic.isReplicatorMode());
        if (useReplicator) {
            config.put("replicatorHealthUrl", replicatorHealthUrl);
        }

        Map<String, Object> endpoints = new LinkedHashMap<>();
        endpoints.put("health", "/health");
        endpoints.put("config", "/config");
        endpoints.put("datastreamStatus", "/datastream-status");
        endpoints.put("checkFlag", "/checkflag (POST)");

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("configuration", config);
        response.put("endpoints", endpoints);
        response.put("timestamp", Instant.now().toString());

        sendJson(exchange, 200, response);
    }

    private static void handleHealth(HttpExchange exchange) throws IOException {
        if (!"GET".equals(exchange.getRequestMethod())) {
            sendMethodNotAllowed(exchange);
            return;
        }

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", "healthy");
        response.put("replicatorMode", schematic.isReplicatorMode());
        response.put("datastreamConnected", schematic.isDatastreamConnected());

        Map<String, Object> config = new LinkedHashMap<>();
        config.put("schematicApiUrl", schematicApiUrl);
        config.put("cacheTtlMs", cacheTtlMs);
        response.put("configuration", config);
        response.put("timestamp", Instant.now().toString());

        sendJson(exchange, 200, response);
    }

    private static void handleDatastreamStatus(HttpExchange exchange) throws IOException {
        if (!"GET".equals(exchange.getRequestMethod())) {
            sendMethodNotAllowed(exchange);
            return;
        }

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("replicatorMode", schematic.isReplicatorMode());
        response.put("datastreamConnected", schematic.isDatastreamConnected());

        Map<String, Object> config = new LinkedHashMap<>();
        config.put("schematicApiUrl", schematicApiUrl);
        if (useReplicator) {
            config.put("replicatorHealthUrl", replicatorHealthUrl);
        }
        response.put("configuration", config);
        response.put("timestamp", Instant.now().toString());

        sendJson(exchange, 200, response);
    }

    @SuppressWarnings("unchecked")
    private static void handleCheckFlag(HttpExchange exchange) throws IOException {
        if (!"POST".equals(exchange.getRequestMethod())) {
            sendMethodNotAllowed(exchange);
            return;
        }

        try {
            InputStream body = exchange.getRequestBody();
            Map<String, Object> requestBody = MAPPER.readValue(body, Map.class);

            String flagKey = (String) requestBody.get("flag-key");
            if (flagKey == null || flagKey.isEmpty()) {
                Map<String, Object> error = new LinkedHashMap<>();
                error.put("error", "flag-key is required");
                sendJson(exchange, 400, error);
                return;
            }

            Map<String, String> company = toStringMap(requestBody.get("company"));
            Map<String, String> user = toStringMap(requestBody.get("user"));

            long startTime = System.nanoTime();
            RulesengineCheckFlagResult result = schematic.checkFlagWithEntitlement(flagKey, company, user);
            double durationMs = (System.nanoTime() - startTime) / 1_000_000.0;

            Map<String, Object> response = new LinkedHashMap<>();
            response.put("flagKey", result.getFlagKey());
            response.put("value", result.getValue());
            response.put("reason", result.getReason());
            result.getFlagId().ifPresent(v -> response.put("flagId", v));
            result.getCompanyId().ifPresent(v -> response.put("companyId", v));
            result.getUserId().ifPresent(v -> response.put("userId", v));
            result.getRuleId().ifPresent(v -> response.put("ruleId", v));
            result.getErr().ifPresent(v -> response.put("error", v));
            response.put("replicatorMode", schematic.isReplicatorMode());
            response.put("datastreamConnected", schematic.isDatastreamConnected());
            response.put("durationMs", durationMs);
            response.put("timestamp", Instant.now().toString());

            sendJson(exchange, 200, response);
        } catch (Exception e) {
            Map<String, Object> error = new LinkedHashMap<>();
            error.put("error", "Flag Check Failed");
            error.put("detail", e.getMessage());
            sendJson(exchange, 500, error);
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

    private static void sendText(HttpExchange exchange, int status, String text) throws IOException {
        byte[] bytes = text.getBytes("UTF-8");
        exchange.getResponseHeaders().set("Content-Type", "text/plain; charset=utf-8");
        exchange.sendResponseHeaders(status, bytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }

    private static void sendJson(HttpExchange exchange, int status, Object obj) throws IOException {
        byte[] bytes = MAPPER.writerWithDefaultPrettyPrinter().writeValueAsBytes(obj);
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(status, bytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }

    private static void sendMethodNotAllowed(HttpExchange exchange) throws IOException {
        sendText(exchange, 405, "Method Not Allowed");
    }

    private static String envOrDefault(String key, String defaultValue) {
        String value = System.getenv(key);
        return (value != null && !value.isEmpty()) ? value : defaultValue;
    }
}
