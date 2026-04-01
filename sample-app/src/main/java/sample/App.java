/*
 * Schematic Java Client - Datastream Test Server
 *
 * This example demonstrates how to use the Schematic Java client to check feature flags.
 * It is modeled after the C# DatastreamTestServer example and will be extended
 * to support replicator/datastream mode once that functionality is available in the Java SDK.
 *
 * Environment Variables:
 * - SCHEMATIC_API_KEY: Your Schematic API key (required)
 * - SCHEMATIC_API_URL: Schematic API base URL (default: https://api.schematichq.com)
 * - SERVER_PORT: Port to listen on (default: 8080)
 * - CACHE_TTL_MS: Cache TTL in milliseconds (default: 5000)
 *
 * Usage:
 * 1. Set environment variables (only SCHEMATIC_API_KEY is required)
 * 2. Run: ./gradlew :sample-app:run
 * 3. Test endpoints:
 *    - GET / - Welcome message
 *    - GET /config - Show current configuration
 *    - GET /health - Check service health
 *    - POST /checkflag - Check feature flags
 *
 * Example checkflag request:
 *   curl -X POST http://localhost:8080/checkflag \
 *     -H "Content-Type: application/json" \
 *     -d '{"flag-key":"my-flag","company":{"id":"comp-123"},"user":{"id":"user-456"}}'
 */
package sample;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.schematic.api.Schematic;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

public final class App {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static Schematic schematic;
    private static String schematicApiUrl;
    private static int cacheTtlMs;
    private static int serverPort;

    public static void main(String[] args) throws Exception {
        String apiKey = System.getenv("SCHEMATIC_API_KEY");
        if (apiKey == null || apiKey.isEmpty()) {
            System.err.println("ERROR: SCHEMATIC_API_KEY environment variable is not set");
            System.exit(1);
        }

        schematicApiUrl = envOrDefault("SCHEMATIC_API_URL", "https://api.schematichq.com");
        serverPort = Integer.parseInt(envOrDefault("SERVER_PORT", "8080"));
        cacheTtlMs = Integer.parseInt(envOrDefault("CACHE_TTL_MS", "5000"));

        Schematic.Builder builder = Schematic.builder().apiKey(apiKey).basePath(schematicApiUrl);

        // TODO: Once datastream/replicator mode is available in the Java SDK, configure it here:
        // - Redis cache provider
        // - Replicator health URL
        // - DatastreamOptions

        schematic = builder.build();

        HttpServer server = HttpServer.create(new InetSocketAddress(serverPort), 0);
        server.createContext("/", App::handleRoot);
        server.createContext("/config", App::handleConfig);
        server.createContext("/health", App::handleHealth);
        server.createContext("/checkflag", App::handleCheckFlag);
        server.setExecutor(null);
        server.start();

        System.out.println("Datastream Test Server started on port " + serverPort);
        System.out.println("Endpoints:");
        System.out.println("  GET  /          - Welcome message");
        System.out.println("  GET  /config    - Show configuration");
        System.out.println("  GET  /health    - Health check");
        System.out.println("  POST /checkflag - Check a feature flag");
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

        Map<String, Object> endpoints = new LinkedHashMap<>();
        endpoints.put("health", "/health");
        endpoints.put("config", "/config");
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

        Map<String, Object> config = new LinkedHashMap<>();
        config.put("schematicApiUrl", schematicApiUrl);
        config.put("cacheTtlMs", cacheTtlMs);
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
            boolean result = schematic.checkFlag(flagKey, company, user);
            double durationMs = (System.nanoTime() - startTime) / 1_000_000.0;

            Map<String, Object> response = new LinkedHashMap<>();
            response.put("flagKey", flagKey);
            response.put("result", result);
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
