package com.schematic.api;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.*;

import com.schematic.api.logger.SchematicLogger;
import com.schematic.api.types.CreateEventRequestBody;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EventBufferTest {
    @Mock
    private HttpEventSender eventSender;

    @Mock
    private SchematicLogger logger;

    private EventBuffer eventBuffer;

    @BeforeEach
    void setUp() {
        eventBuffer = new EventBuffer(eventSender, logger, 5, Duration.ofMillis(100));
    }

    @AfterEach
    void tearDown() {
        eventBuffer.close();
    }

    @Test
    void pushEvent_ShouldAddToBuffer() throws IOException {
        CreateEventRequestBody event = mock(CreateEventRequestBody.class);
        eventBuffer.push(event);

        // Force a flush to verify the event was buffered
        eventBuffer.flush();
        ArgumentCaptor<List<CreateEventRequestBody>> captor = ArgumentCaptor.forClass(List.class);
        verify(eventSender).sendBatch(captor.capture());
        assertEquals(1, captor.getValue().size());
    }

    @Test
    void pushEvents_ExceedingMaxSize_ShouldTriggerFlush() throws IOException {
        // Use a long flush interval so the periodic flush can't race with the size-triggered
        // flush and add an extra sendBatch call on a slow runner.
        EventBuffer sizeOnlyBuffer = new EventBuffer(eventSender, logger, 5, Duration.ofHours(1));
        try {
            CreateEventRequestBody event = mock(CreateEventRequestBody.class);

            // Pushing up to max size triggers a flush on the 5th push
            for (int i = 0; i < 5; i++) {
                sizeOnlyBuffer.push(event);
            }

            // One more stays buffered until the next flush
            sizeOnlyBuffer.push(event);

            ArgumentCaptor<List<CreateEventRequestBody>> captor = ArgumentCaptor.forClass(List.class);
            verify(eventSender).sendBatch(captor.capture());
            assertEquals(5, captor.getValue().size());
            assertEquals(1, sizeOnlyBuffer.getQueueSize());
        } finally {
            sizeOnlyBuffer.close();
        }
    }

    @Test
    void flush_ShouldSendEvents() throws IOException {
        CreateEventRequestBody event = mock(CreateEventRequestBody.class);
        eventBuffer.push(event);
        eventBuffer.push(event);

        eventBuffer.flush();

        ArgumentCaptor<List<CreateEventRequestBody>> captor = ArgumentCaptor.forClass(List.class);
        verify(eventSender).sendBatch(captor.capture());
        assertEquals(2, captor.getValue().size());
    }

    @Test
    void periodicFlush_ShouldTrigger() throws Exception {
        CreateEventRequestBody event = mock(CreateEventRequestBody.class);
        eventBuffer.push(event);

        // Wait for periodic flush
        Thread.sleep(150);

        verify(eventSender, atLeastOnce()).sendBatch(any());
    }

    @Test
    void stop_ShouldFlushRemainingEvents() throws IOException {
        CreateEventRequestBody event = mock(CreateEventRequestBody.class);
        eventBuffer.push(event);

        eventBuffer.close();

        verify(eventSender).sendBatch(any());
    }

    @Test
    void pushAfterStop_ShouldLogError() throws IOException {
        eventBuffer.close();
        CreateEventRequestBody event = mock(CreateEventRequestBody.class);

        eventBuffer.push(event);

        verify(logger).error(anyString());
        verify(eventSender, never()).sendBatch(any());
    }

    @Test
    void flushWithError_ShouldLogError() throws IOException {
        // Use a tiny retry delay so the three async retries finish in milliseconds rather
        // than the production ~7s, keeping the test fast and deterministic.
        EventBuffer fastRetryBuffer =
                new EventBuffer(eventSender, logger, 5, Duration.ofMillis(100), Duration.ofMillis(1));
        try {
            doThrow(new IOException("Test error")).when(eventSender).sendBatch(any());
            CreateEventRequestBody event = mock(CreateEventRequestBody.class);
            fastRetryBuffer.push(event);

            fastRetryBuffer.flush();

            verify(logger, timeout(2_000)).error(contains("Failed to flush events"));
        } finally {
            fastRetryBuffer.close();
        }
    }

    @Test
    void concurrentPushes_ShouldHandleCorrectly() throws Exception {
        int threadCount = 10;
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch doneLatch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            new Thread(() -> {
                        try {
                            startLatch.await();
                            for (int j = 0; j < 10; j++) {
                                eventBuffer.push(mock(CreateEventRequestBody.class));
                            }
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        } finally {
                            doneLatch.countDown();
                        }
                    })
                    .start();
        }

        startLatch.countDown();
        assertTrue(doneLatch.await(5, TimeUnit.SECONDS));

        // Allow time for flush operations
        Thread.sleep(200);

        // Verify events were processed
        ArgumentCaptor<List<CreateEventRequestBody>> captor = ArgumentCaptor.forClass(List.class);
        verify(eventSender, atLeastOnce()).sendBatch(captor.capture());

        int totalEvents = captor.getAllValues().stream().mapToInt(List::size).sum();
        assertEquals(100, totalEvents);
    }
}
