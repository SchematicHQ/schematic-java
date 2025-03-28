package com.schematic.webhook.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.schematic.webhook.WebhookSignatureException;
import com.schematic.webhook.WebhookVerifier;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * A simple HTTP server for testing Schematic webhooks.
 * This server listens for webhook requests, verifies their signatures, and logs the payloads.
 */
public class WebhookTestServer {

    private final int port;
    private final String secret;
    private final HttpServer server;
    private final ObjectMapper objectMapper;

    /**
     * Creates a new WebhookTestServer.
     *
     * @param port The port to listen on
     * @param secret The webhook secret for signature verification
     * @throws IOException if the server cannot be created
     */
    public WebhookTestServer(int port, String secret) throws IOException {
        this.port = port;
        this.secret = secret;
        this.objectMapper = new ObjectMapper();
        this.server = HttpServer.create(new InetSocketAddress(port), 0);

        server.createContext("/webhook", new WebhookHandler());
        server.setExecutor(null); // Use default executor
    }

    /**
     * Starts the server.
     */
    public void start() {
        server.start();
        System.out.println("Webhook test server started on port " + port);
        System.out.println("Ready to receive webhooks at http://localhost:" + port + "/webhook");
        if (secret != null && !secret.isEmpty()) {
            System.out.println("Using webhook secret: " + secret);
        } else {
            System.out.println("WARNING: No webhook secret provided. Signature verification will be skipped.");
        }
        System.out.println("Press Ctrl+C to stop");
    }

    /**
     * Stops the server.
     */
    public void stop() {
        server.stop(0);
        System.out.println("Server stopped");
    }

    /**
     * Handler for webhook requests.
     */
    private class WebhookHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if (!exchange.getRequestMethod().equals("POST")) {
                sendResponse(exchange, 405, "Method not allowed");
                return;
            }

            // Read request body
            String body = new BufferedReader(new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8))
                    .lines()
                    .collect(Collectors.joining("\n"));

            // Log headers
            System.out.println("\nHeaders:");
            exchange.getRequestHeaders()
                    .forEach((key, values) -> System.out.println("  " + key + ": " + String.join(", ", values)));

            // Verify signature if secret is provided
            if (secret != null && !secret.isEmpty()) {
                String signature = exchange.getRequestHeaders().getFirst(WebhookVerifier.WEBHOOK_SIGNATURE_HEADER);
                String timestamp = exchange.getRequestHeaders().getFirst(WebhookVerifier.WEBHOOK_TIMESTAMP_HEADER);

                try {
                    WebhookVerifier.verifySignature(body, signature, timestamp, secret);
                    System.out.println("✅ Signature verification successful!");
                } catch (WebhookSignatureException e) {
                    System.out.println("❌ Signature verification failed: " + e.getMessage());

                    Map<String, String> error = new HashMap<>();
                    error.put("error", e.getMessage());

                    sendResponse(exchange, 401, objectMapper.writeValueAsString(error));
                    return;
                }
            } else {
                System.out.println("⚠️ No webhook secret provided, skipping signature verification");
            }

            // Log payload
            try {
                Object json = objectMapper.readValue(body, Object.class);
                System.out.println("\nPayload:");
                System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(json));
            } catch (Exception e) {
                System.out.println("\nRaw body (not JSON):");
                System.out.println(body);
            }

            // Send success response
            Map<String, String> response = new HashMap<>();
            response.put("status", "success");

            sendResponse(exchange, 200, objectMapper.writeValueAsString(response));
        }

        private void sendResponse(HttpExchange exchange, int statusCode, String body) throws IOException {
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(statusCode, body.length());

            try (OutputStream os = exchange.getResponseBody()) {
                os.write(body.getBytes(StandardCharsets.UTF_8));
            }
        }
    }

    /**
     * Main method to run the server.
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        int port = 8080;
        String secret = System.getenv("SCHEMATIC_WEBHOOK_SECRET");

        // Parse command line arguments
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("--port") && i + 1 < args.length) {
                try {
                    port = Integer.parseInt(args[i + 1]);
                    i++;
                } catch (NumberFormatException e) {
                    System.err.println("Invalid port number: " + args[i + 1]);
                    System.exit(1);
                }
            } else if (args[i].equals("--secret") && i + 1 < args.length) {
                secret = args[i + 1];
                i++;
            } else if (args[i].equals("--help")) {
                printUsage();
                System.exit(0);
            }
        }

        try {
            WebhookTestServer server = new WebhookTestServer(port, secret);
            server.start();

            // Add shutdown hook to stop server gracefully
            Runtime.getRuntime().addShutdownHook(new Thread(server::stop));
        } catch (IOException e) {
            System.err.println("Error starting server: " + e.getMessage());
            System.exit(1);
        }
    }

    private static void printUsage() {
        System.out.println("Webhook Test Server for Schematic");
        System.out.println();
        System.out.println("Usage: java -cp <classpath> com.schematic.webhook.server.WebhookTestServer [options]");
        System.out.println();
        System.out.println("Options:");
        System.out.println("  --port PORT     Port to listen on (default: 8080)");
        System.out.println("  --secret SECRET Webhook secret (default: reads from SCHEMATIC_WEBHOOK_SECRET env var)");
        System.out.println("  --help          Print this help message");
        System.out.println();
        System.out.println("Examples:");
        System.out.println(
                "  java -cp <classpath> com.schematic.webhook.server.WebhookTestServer --port 8080 --secret my_webhook_secret");
        System.out.println();
        System.out.println("  # Or using environment variables:");
        System.out.println("  export SCHEMATIC_WEBHOOK_SECRET=my_webhook_secret");
        System.out.println("  java -cp <classpath> com.schematic.webhook.server.WebhookTestServer");
        System.out.println();
        System.out.println("Notes:");
        System.out.println("  For testing with Schematic, you can use a tool like ngrok to expose");
        System.out.println("  your local server to the internet.");
    }
}
