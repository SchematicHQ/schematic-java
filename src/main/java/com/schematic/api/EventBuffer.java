package com.schematic.api;

import com.schematic.api.logger.SchematicLogger;
import com.schematic.api.resources.events.EventsClient;
import com.schematic.api.resources.events.requests.CreateEventBatchRequestBody;
import com.schematic.api.types.CreateEventRequestBody;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Buffers and batches events before sending them to the Schematic API.
 * Provides thread-safe event handling with automatic retry capabilities
 * and resource management.
 */
public class EventBuffer implements AutoCloseable {
    private static final Duration DEFAULT_FLUSH_INTERVAL = Duration.ofMillis(5000);
    private static final int DEFAULT_MAX_BATCH_SIZE = 100;
    private static final int DEFAULT_MAX_QUEUE_SIZE = 10_000;
    private static final int MAX_RETRY_ATTEMPTS = 3;
    private static final Duration RETRY_INITIAL_DELAY = Duration.ofMillis(100);

    private final ConcurrentLinkedQueue<CreateEventRequestBody> events;
    private final int maxBatchSize;
    private final Duration flushInterval;
    private final EventsClient eventsClient;
    private final SchematicLogger logger;
    private final ScheduledExecutorService scheduler;
    private final AtomicInteger droppedEvents;
    private final AtomicInteger processedEvents;
    private final AtomicInteger failedEvents;
    private volatile boolean stopped;

    /**
     * Creates a new EventBuffer instance.
     *
     * @param eventsClient The client used to send events to the API
     * @param logger Logger instance for error reporting and monitoring
     * @param maxBatchSize Maximum number of events to include in a single batch
     * @param flushInterval How often to automatically flush the buffer
     */
    public EventBuffer(EventsClient eventsClient, SchematicLogger logger, int maxBatchSize, Duration flushInterval) {
        this.events = new ConcurrentLinkedQueue<>();
        this.maxBatchSize = maxBatchSize > 0 ? maxBatchSize : DEFAULT_MAX_BATCH_SIZE;
        this.flushInterval = flushInterval != null ? flushInterval : DEFAULT_FLUSH_INTERVAL;
        this.eventsClient = eventsClient;
        this.logger = logger;
        this.droppedEvents = new AtomicInteger(0);
        this.processedEvents = new AtomicInteger(0);
        this.failedEvents = new AtomicInteger(0);
        this.stopped = false;

        this.scheduler = Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r, "SchematicEventBuffer");
                thread.setDaemon(true);
                return thread;
            }
        });

        startPeriodicFlush();
    }

    /**
     * Adds an event to the buffer for processing.
     *
     * @param event The event to be processed
     */
    public void push(CreateEventRequestBody event) {
        if (event == null) {
            logger.warn("Attempted to push null event to buffer");
            return;
        }

        if (stopped) {
            logger.error("Event buffer is stopped, not accepting new events");
            return;
        }

        if (events.size() >= DEFAULT_MAX_QUEUE_SIZE) {
            droppedEvents.incrementAndGet();
            logger.warn("Event buffer queue size exceeded, dropping oldest event");
            events.poll();
        }

        events.offer(event);

        if (events.size() >= maxBatchSize) {
            flush();
        }
    }

    /**
     * Manually flushes the event buffer, sending all queued events to the API.
     */
    public void flush() {
        if (events.isEmpty()) {
            return;
        }

        List<CreateEventRequestBody> batch = new ArrayList<>();
        while (!events.isEmpty() && batch.size() < maxBatchSize) {
            CreateEventRequestBody event = events.poll();
            if (event != null) {
                batch.add(event);
            }
        }

        if (!batch.isEmpty()) {
            sendBatchWithRetry(batch, 0);
        }
    }

    private void sendBatchWithRetry(List<CreateEventRequestBody> batch, int retryCount) {
        try {
            CreateEventBatchRequestBody requestBody =
                    CreateEventBatchRequestBody.builder().events(batch).build();

            eventsClient.createEventBatch(requestBody);
            processedEvents.addAndGet(batch.size());

        } catch (Exception e) {
            if (retryCount < MAX_RETRY_ATTEMPTS) {
                long delayMillis = RETRY_INITIAL_DELAY.toMillis() * (1L << retryCount);
                logger.warn(
                        "Failed to send event batch, attempting retry %d of %d in %d ms",
                        retryCount + 1, MAX_RETRY_ATTEMPTS, delayMillis);

                scheduler.schedule(() -> sendBatchWithRetry(batch, retryCount + 1), delayMillis, TimeUnit.MILLISECONDS);
            } else {
                failedEvents.addAndGet(batch.size());
                logger.error("Failed to flush events: " + e.getMessage());
            }
        }
    }

    private void startPeriodicFlush() {
        scheduler.scheduleAtFixedRate(
                () -> {
                    try {
                        flush();
                    } catch (Exception e) {
                        logger.error("Error during periodic flush: %s", e.getMessage());
                    }
                },
                flushInterval.toMillis(),
                flushInterval.toMillis(),
                TimeUnit.MILLISECONDS);
    }

    /**
     * Stops the event buffer and releases all resources.
     * This method will attempt to flush any remaining events before shutting down.
     */
    @Override
    public void close() {
        if (!stopped) {
            stopped = true;
            try {
                // Attempt to flush remaining events
                flush();

                // Shutdown the scheduler
                scheduler.shutdown();
                if (!scheduler.awaitTermination(30, TimeUnit.SECONDS)) {
                    scheduler.shutdownNow();
                    if (!scheduler.awaitTermination(30, TimeUnit.SECONDS)) {
                        logger.error("EventBuffer thread pool did not terminate");
                    }
                }
            } catch (InterruptedException e) {
                scheduler.shutdownNow();
                Thread.currentThread().interrupt();
                logger.error("EventBuffer shutdown interrupted");
            }
        }
    }

    /**
     * Returns metrics about the event buffer's operation.
     *
     * @return A string containing current metrics
     */
    public String getMetrics() {
        return String.format(
                "EventBuffer Metrics - Processed: %d, Dropped: %d, Failed: %d, Current Queue Size: %d",
                processedEvents.get(), droppedEvents.get(), failedEvents.get(), events.size());
    }

    /**
     * @return The number of events currently queued in the buffer
     */
    public int getQueueSize() {
        return events.size();
    }

    /**
     * @return The number of events that have been successfully processed
     */
    public int getProcessedCount() {
        return processedEvents.get();
    }

    /**
     * @return The number of events that were dropped due to queue size limitations
     */
    public int getDroppedCount() {
        return droppedEvents.get();
    }

    /**
     * @return The number of events that failed to send after all retry attempts
     */
    public int getFailedCount() {
        return failedEvents.get();
    }
}
