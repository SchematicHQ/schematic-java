package com.schematic.api.datastream;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.schematic.api.datastream.DataStreamMessages.DataStreamResp;
import com.schematic.api.logger.SchematicLogger;
import java.io.Closeable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

/**
 * WebSocket client for the Schematic DataStream protocol.
 *
 * <p>Provides automatic reconnection with exponential backoff, ping/pong keep-alive,
 * and a message worker pool for processing incoming messages. Mirrors the Go
 * {@code schematic-datastream-ws} package.
 */
public class DataStreamWebSocketClient implements Closeable {

    /** Functional interface for handling incoming datastream messages. */
    public interface MessageHandler {
        void handle(DataStreamResp message) throws Exception;
    }

    /** Functional interface called after connection is established, before client is marked ready. */
    public interface ConnectionReadyHandler {
        void handle() throws Exception;
    }

    // Default constants (matching Go SDK)
    private static final long DEFAULT_PONG_WAIT_MS = 40_000;
    private static final long DEFAULT_PING_PERIOD_MS = 30_000;
    private static final long WRITE_WAIT_MS = 10_000;
    private static final int DEFAULT_MAX_RECONNECT_ATTEMPTS = 10;
    private static final long DEFAULT_MIN_RECONNECT_DELAY_MS = 1_000;
    private static final long DEFAULT_MAX_RECONNECT_DELAY_MS = 30_000;
    private static final int DEFAULT_MESSAGE_QUEUE_SIZE = 100;
    private static final int DEFAULT_MESSAGE_WORKERS = 1;

    // Configuration
    private final String url;
    private final String apiKey;
    private final MessageHandler messageHandler;
    private final ConnectionReadyHandler connectionReadyHandler;
    private final SchematicLogger logger;
    private final int maxReconnectAttempts;
    private final long minReconnectDelayMs;
    private final long maxReconnectDelayMs;
    private final long pingPeriodMs;
    private final long pongWaitMs;
    private final int messageQueueSize;
    private final int messageWorkers;

    // State
    private final AtomicBoolean connected = new AtomicBoolean(false);
    private final AtomicBoolean ready = new AtomicBoolean(false);
    private final AtomicBoolean closed = new AtomicBoolean(false);
    private final AtomicInteger reconnectAttempts = new AtomicInteger(0);

    // Infrastructure
    private final OkHttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final BlockingQueue<DataStreamResp> messageQueue;
    private final ExecutorService workerPool;
    private final ScheduledExecutorService scheduler;
    private volatile WebSocket webSocket;
    private volatile ScheduledFuture<?> pingTask;
    private volatile CountDownLatch connectLatch;

    private DataStreamWebSocketClient(Builder builder) {
        if (builder.url == null || builder.url.isEmpty()) {
            throw new IllegalArgumentException("URL is required");
        }
        if (builder.apiKey == null || builder.apiKey.isEmpty()) {
            throw new IllegalArgumentException("API key is required");
        }
        if (builder.messageHandler == null) {
            throw new IllegalArgumentException("MessageHandler is required");
        }

        this.url = convertApiUrlToWebSocketUrl(builder.url);
        this.apiKey = builder.apiKey;
        this.messageHandler = builder.messageHandler;
        this.connectionReadyHandler = builder.connectionReadyHandler;
        this.logger = builder.logger;
        this.maxReconnectAttempts =
                builder.maxReconnectAttempts > 0 ? builder.maxReconnectAttempts : DEFAULT_MAX_RECONNECT_ATTEMPTS;
        this.minReconnectDelayMs =
                builder.minReconnectDelayMs > 0 ? builder.minReconnectDelayMs : DEFAULT_MIN_RECONNECT_DELAY_MS;
        this.maxReconnectDelayMs =
                builder.maxReconnectDelayMs > 0 ? builder.maxReconnectDelayMs : DEFAULT_MAX_RECONNECT_DELAY_MS;
        this.pingPeriodMs = builder.pingPeriodMs > 0 ? builder.pingPeriodMs : DEFAULT_PING_PERIOD_MS;
        this.pongWaitMs = builder.pongWaitMs > 0 ? builder.pongWaitMs : DEFAULT_PONG_WAIT_MS;
        this.messageQueueSize = builder.messageQueueSize > 0 ? builder.messageQueueSize : DEFAULT_MESSAGE_QUEUE_SIZE;
        this.messageWorkers = builder.messageWorkers > 0 ? builder.messageWorkers : DEFAULT_MESSAGE_WORKERS;

        this.objectMapper = new ObjectMapper();
        this.messageQueue = new LinkedBlockingQueue<>(this.messageQueueSize);

        // OkHttp client with ping interval for keep-alive
        this.httpClient = new OkHttpClient.Builder()
                .pingInterval(this.pingPeriodMs, TimeUnit.MILLISECONDS)
                .readTimeout(this.pongWaitMs, TimeUnit.MILLISECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .build();

        this.workerPool = Executors.newFixedThreadPool(this.messageWorkers, r -> {
            Thread t = new Thread(r, "schematic-ds-worker");
            t.setDaemon(true);
            return t;
        });

        this.scheduler = Executors.newSingleThreadScheduledExecutor(r -> {
            Thread t = new Thread(r, "schematic-ds-scheduler");
            t.setDaemon(true);
            return t;
        });

        // Start message workers
        for (int i = 0; i < this.messageWorkers; i++) {
            workerPool.submit(this::messageWorkerLoop);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    /** Starts the WebSocket connection asynchronously. */
    public void start() {
        if (closed.get()) {
            throw new IllegalStateException("Client has been closed");
        }
        log("info", "Starting WebSocket client");
        connectLatch = new CountDownLatch(1);
        scheduler.submit(this::connectAndRead);
    }

    /**
     * Blocks until the client is connected and ready, or the timeout elapses.
     *
     * @return true if connected and ready, false if timed out
     */
    public boolean awaitReady(long timeout, TimeUnit unit) throws InterruptedException {
        if (connectLatch == null) {
            return ready.get();
        }
        return connectLatch.await(timeout, unit);
    }

    /** Returns whether the WebSocket is currently connected. */
    public boolean isConnected() {
        return connected.get();
    }

    /** Returns whether the client is ready (connected + initialized). */
    public boolean isReady() {
        return ready.get() && connected.get();
    }

    /**
     * Sends a message through the WebSocket connection.
     *
     * @param message the message object to serialize and send
     * @return true if the message was enqueued for sending
     */
    public boolean sendMessage(Object message) {
        if (!isConnected() || webSocket == null) {
            log("warn", "Cannot send message: WebSocket not connected");
            return false;
        }

        try {
            String json = objectMapper.writeValueAsString(message);
            log("debug", "Sending WebSocket message: " + json);
            return webSocket.send(json);
        } catch (JsonProcessingException e) {
            log("error", "Failed to serialize message: " + e.getMessage());
            return false;
        }
    }

    /** Gracefully closes the WebSocket connection and shuts down workers. */
    @Override
    public void close() {
        if (!closed.compareAndSet(false, true)) {
            return; // Already closed
        }

        log("info", "Closing WebSocket client");

        // Cancel ping task
        if (pingTask != null) {
            pingTask.cancel(false);
        }

        // Close WebSocket
        setConnected(false);
        setReady(false);
        if (webSocket != null) {
            try {
                webSocket.close(1000, "Client closing");
            } catch (Exception e) {
                log("debug", "Error closing WebSocket: " + e.getMessage());
            }
        }

        // Shutdown workers
        workerPool.shutdownNow();
        scheduler.shutdownNow();
        httpClient.dispatcher().executorService().shutdownNow();
        httpClient.connectionPool().evictAll();

        // Release connect latch if anyone is waiting
        if (connectLatch != null) {
            connectLatch.countDown();
        }

        log("info", "WebSocket client closed");
    }

    // --- Connection lifecycle ---

    private void connectAndRead() {
        while (!closed.get()) {
            try {
                doConnect();
                // If doConnect returns without exception and we're connected,
                // reset attempts and wait for disconnection
                if (connected.get()) {
                    reconnectAttempts.set(0);
                    return; // WebSocket listener handles the rest
                }
            } catch (Exception e) {
                log("error", "Failed to connect to WebSocket: " + e.getMessage());
            }

            int attempts = reconnectAttempts.incrementAndGet();
            if (attempts >= maxReconnectAttempts) {
                log("error", "Max reconnection attempts reached (" + maxReconnectAttempts + ")");
                if (connectLatch != null) {
                    connectLatch.countDown();
                }
                return;
            }

            long delay = calculateBackoffDelay(attempts);
            log(
                    "info",
                    String.format(
                            "Retrying WebSocket connection in %dms (attempt %d/%d)",
                            delay, attempts, maxReconnectAttempts));

            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }

    private void doConnect() {
        Request request = new Request.Builder()
                .url(url)
                .header("X-Schematic-Api-Key", apiKey)
                .build();

        log("debug", "Connecting to WebSocket: " + url);
        httpClient.newWebSocket(request, new SchematicWebSocketListener());
    }

    private void handleConnected() {
        log("info", "Connected to WebSocket");
        setConnected(true);
        reconnectAttempts.set(0);

        // Call connection ready handler if provided
        if (connectionReadyHandler != null) {
            try {
                log("debug", "Calling connection ready handler");
                connectionReadyHandler.handle();
                log("debug", "Connection ready handler completed");
            } catch (Exception e) {
                log("error", "Connection ready handler failed: " + e.getMessage());
                setConnected(false);
                setReady(false);
                if (webSocket != null) {
                    webSocket.close(1000, "Ready handler failed");
                }
                return;
            }
        }

        setReady(true);
        log("info", "DataStream client is ready");

        // Release anyone waiting on awaitReady
        if (connectLatch != null) {
            connectLatch.countDown();
        }
    }

    private void handleDisconnected(int code, String reason) {
        setConnected(false);
        setReady(false);

        if (closed.get()) {
            return;
        }

        // Normal/going-away closure or non-retriable (4001 unauthorized) - don't reconnect
        if (code == 1000 || code == 1001 || code == 4001) {
            log("info", String.format("WebSocket closed (code=%d, reason=%s), not reconnecting", code, reason));
            return;
        }

        // All other closures - attempt reconnect
        log("info", String.format("WebSocket disconnected (code=%d, reason=%s), scheduling reconnect", code, reason));
        scheduleReconnect();
    }

    private void handleFailure(Throwable t) {
        setConnected(false);
        setReady(false);

        if (closed.get()) {
            return;
        }

        log("error", "WebSocket failure: " + t.getMessage());
        scheduleReconnect();
    }

    private void scheduleReconnect() {
        if (closed.get()) {
            return;
        }
        scheduler.submit(this::connectAndRead);
    }

    // --- Message processing ---

    private void messageWorkerLoop() {
        while (!closed.get() && !Thread.currentThread().isInterrupted()) {
            try {
                DataStreamResp message = messageQueue.poll(1, TimeUnit.SECONDS);
                if (message == null) {
                    continue;
                }

                try {
                    messageHandler.handle(message);
                    log("debug", "Message processed successfully");
                } catch (Exception e) {
                    log("error", "Message handler error: " + e.getMessage());
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }

    // --- Utilities ---

    long calculateBackoffDelay(int attempt) {
        long jitter = (long) (Math.random() * minReconnectDelayMs);
        long delay = (long) (Math.pow(2, attempt - 1) * minReconnectDelayMs) + jitter;
        if (delay > maxReconnectDelayMs) {
            delay = maxReconnectDelayMs + jitter;
        }
        return delay;
    }

    static String convertApiUrlToWebSocketUrl(String apiUrl) {
        // If already a WebSocket URL, just ensure /datastream path
        if (apiUrl.startsWith("ws://") || apiUrl.startsWith("wss://")) {
            if (!apiUrl.endsWith("/datastream")) {
                return apiUrl + "/datastream";
            }
            return apiUrl;
        }

        try {
            URL parsed = new URL(apiUrl);
            String protocol = parsed.getProtocol();
            if (!"http".equals(protocol) && !"https".equals(protocol)) {
                throw new IllegalArgumentException(
                        "Unsupported scheme: " + protocol + " (must be http or https)");
            }
            String scheme = "https".equals(protocol) ? "wss" : "ws";
            String host = parsed.getHost();
            int port = parsed.getPort();

            // Replace 'api' subdomain with 'datastream'
            String[] hostParts = host.split("\\.");
            if (hostParts.length > 1 && "api".equals(hostParts[0])) {
                hostParts[0] = "datastream";
                host = String.join(".", hostParts);
            }

            StringBuilder sb = new StringBuilder();
            sb.append(scheme).append("://").append(host);
            if (port != -1 && port != 80 && port != 443) {
                sb.append(":").append(port);
            }
            sb.append("/datastream");
            return sb.toString();
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Invalid API URL: " + apiUrl, e);
        }
    }

    private void setConnected(boolean value) {
        connected.set(value);
        if (!value) {
            ready.set(false);
        }
    }

    private void setReady(boolean value) {
        ready.set(value);
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

    // --- WebSocket listener ---

    private class SchematicWebSocketListener extends WebSocketListener {

        @Override
        public void onOpen(WebSocket ws, Response response) {
            webSocket = ws;
            handleConnected();
        }

        @Override
        public void onMessage(WebSocket ws, String text) {
            log("debug", "Received WebSocket message: " + text);
            try {
                DataStreamResp message = objectMapper.readValue(text, DataStreamResp.class);

                String entityId = message.getEntityId() != null ? message.getEntityId() : "null";
                log(
                        "debug",
                        String.format(
                                "Parsed message - EntityType: %s, MessageType: %s, EntityID: %s",
                                message.getEntityType(), message.getMessageType(), entityId));

                if (!messageQueue.offer(message)) {
                    log("error", "Message queue full, dropping message");
                }
            } catch (JsonProcessingException e) {
                log("error", "Failed to parse datastream message: " + e.getMessage());
            }
        }

        @Override
        public void onClosing(WebSocket ws, int code, String reason) {
            log("debug", String.format("WebSocket closing (code=%d, reason=%s)", code, reason));
            ws.close(code, reason);
        }

        @Override
        public void onClosed(WebSocket ws, int code, String reason) {
            log("debug", String.format("WebSocket closed (code=%d, reason=%s)", code, reason));
            handleDisconnected(code, reason);
        }

        @Override
        public void onFailure(WebSocket ws, Throwable t, Response response) {
            String status = response != null ? String.valueOf(response.code()) : "no response";
            log("error", String.format("WebSocket failure: %s (response: %s)", t.getMessage(), status));
            handleFailure(t);
        }
    }

    // --- Builder ---

    public static class Builder {
        private String url;
        private String apiKey;
        private MessageHandler messageHandler;
        private ConnectionReadyHandler connectionReadyHandler;
        private SchematicLogger logger;
        private int maxReconnectAttempts;
        private long minReconnectDelayMs;
        private long maxReconnectDelayMs;
        private long pingPeriodMs;
        private long pongWaitMs;
        private int messageWorkers;
        private int messageQueueSize;

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder apiKey(String apiKey) {
            this.apiKey = apiKey;
            return this;
        }

        public Builder messageHandler(MessageHandler messageHandler) {
            this.messageHandler = messageHandler;
            return this;
        }

        public Builder connectionReadyHandler(ConnectionReadyHandler connectionReadyHandler) {
            this.connectionReadyHandler = connectionReadyHandler;
            return this;
        }

        public Builder logger(SchematicLogger logger) {
            this.logger = logger;
            return this;
        }

        public Builder maxReconnectAttempts(int maxReconnectAttempts) {
            this.maxReconnectAttempts = maxReconnectAttempts;
            return this;
        }

        public Builder minReconnectDelayMs(long minReconnectDelayMs) {
            this.minReconnectDelayMs = minReconnectDelayMs;
            return this;
        }

        public Builder maxReconnectDelayMs(long maxReconnectDelayMs) {
            this.maxReconnectDelayMs = maxReconnectDelayMs;
            return this;
        }

        public Builder pingPeriodMs(long pingPeriodMs) {
            this.pingPeriodMs = pingPeriodMs;
            return this;
        }

        public Builder pongWaitMs(long pongWaitMs) {
            this.pongWaitMs = pongWaitMs;
            return this;
        }

        public Builder messageWorkers(int messageWorkers) {
            this.messageWorkers = messageWorkers;
            return this;
        }

        public Builder messageQueueSize(int messageQueueSize) {
            this.messageQueueSize = messageQueueSize;
            return this;
        }

        public DataStreamWebSocketClient build() {
            return new DataStreamWebSocketClient(this);
        }
    }
}
