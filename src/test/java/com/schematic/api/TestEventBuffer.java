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
        CreateEventRequestBody event = mock(CreateEventRequestBody.class);

        // Push events up to max size
        for (int i = 0; i < 5; i++) {
            eventBuffer.push(event);
        }

        // Push one more to trigger flush
        eventBuffer.push(event);

        verify(eventSender).sendBatch(any());
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
        doThrow(new IOException("Test error")).when(eventSender).sendBatch(any());
        CreateEventRequestBody event = mock(CreateEventRequestBody.class);
        eventBuffer.push(event);

        eventBuffer.flush();

        try {
            // Wait for all retries to complete
            // Initial delay + 2nd retry + 3rd retry = 100ms + 200ms + 400ms = 700ms
            Thread.sleep(800);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore the interrupt flag
            fail("Test was interrupted while waiting for retries");
        }

        verify(logger).error(contains("Failed to flush events"));
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
