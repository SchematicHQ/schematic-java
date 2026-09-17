package com.schematic.api.credits;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import com.schematic.api.Schematic;
import com.schematic.api.logger.SchematicLogger;
import java.time.Duration;
import java.util.Collections;
import org.junit.jupiter.api.Test;

/** The client-side wiring of credit leases: what each configured mode builds, and what it says. */
class SchematicCreditLeaseTest {

    @Test
    void serverModeWarnsAboutTheOptionsItIgnores() {
        SchematicLogger logger = mock(SchematicLogger.class);

        try (Schematic schematic = Schematic.builder()
                .apiKey("test_api_key")
                .logger(logger)
                .creditLeases(CreditLeaseConfig.builder()
                        .mode(CreditLeaseMode.SERVER)
                        .defaultLeaseSize(500)
                        .lowWaterMark(0.5)
                        .build())
                .build()) {
            verify(logger).warn(contains("will be ignored"));
            // No local plumbing exists to warm.
            schematic.prewarm(Collections.singletonMap("id", "co_1"), Collections.singletonList("ct_1"));
            verify(logger, never()).error(anyString());
        }
    }

    @Test
    void clientModeWithoutDataStreamWarnsThatChecksAreNotGated() {
        SchematicLogger logger = mock(SchematicLogger.class);

        try (Schematic schematic = Schematic.builder()
                .apiKey("test_api_key")
                .logger(logger)
                .creditLeases(CreditLeaseConfig.builder()
                        .mode(CreditLeaseMode.CLIENT)
                        .defaultReservationTtl(Duration.ofSeconds(30))
                        .build())
                .build()) {
            verify(logger).warn(contains("no credit gating"));
            // No shared backend either, which is its own warning.
            verify(logger, atLeastOnce()).warn(contains("shared Redis backend"));
            assertFalse(schematic.isOffline());
        }
    }

    @Test
    void aTtlLongerThanTheApiWillHoldIsClampedForServerMode() {
        SchematicLogger logger = mock(SchematicLogger.class);

        try (Schematic schematic = Schematic.builder()
                .apiKey("test_api_key")
                .logger(logger)
                .creditLeases(CreditLeaseConfig.builder()
                        .mode(CreditLeaseMode.SERVER)
                        .defaultReservationTtl(Duration.ofHours(2))
                        .build())
                .build()) {
            verify(logger).warn(contains("longer than the API will hold"));
        }
    }
}
