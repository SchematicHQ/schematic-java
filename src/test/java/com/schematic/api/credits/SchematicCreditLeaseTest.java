package com.schematic.api.credits;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.schematic.api.IdentifyOptions;
import com.schematic.api.Schematic;
import com.schematic.api.logger.SchematicLogger;
import com.schematic.api.resources.features.FeaturesClient;
import com.schematic.api.types.CheckFlagRequestBody;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
    void clientModeWithoutDataStreamDegradesToAPlainCheckThatHonoursThePerCheckDefault() {
        SchematicLogger logger = mock(SchematicLogger.class);

        try (Schematic schematic = Schematic.builder()
                .apiKey("test_api_key")
                .logger(logger)
                .creditLeases(
                        CreditLeaseConfig.builder().mode(CreditLeaseMode.CLIENT).build())
                .build()) {
            FeaturesClient features = mock(FeaturesClient.class);
            Schematic spied = spy(schematic);
            when(spied.features()).thenReturn(features);
            when(features.checkFlag(anyString(), any(CheckFlagRequestBody.class)))
                    .thenThrow(new RuntimeException("connection refused"));

            CheckResult result = spied.check(
                    "test_flag",
                    Collections.singletonMap("id", "co_1"),
                    null,
                    CheckOptions.builder().usage(5).defaultValue(true).build());

            // The intended degradation, rather than a null pointer from a source that wraps
            // nothing and so sails past the guard meant to catch this.
            verify(logger).debug(contains("no DataStream, using a plain check"));
            verify(logger, never()).warn(contains("NullPointerException"));
            assertTrue(result.isAllowed());
        }
    }

    @Test
    void prewarmWithoutCreditTypesIsANoOp() {
        SchematicLogger logger = mock(SchematicLogger.class);

        try (Schematic schematic = Schematic.builder()
                .apiKey("test_api_key")
                .logger(logger)
                .creditLeases(
                        CreditLeaseConfig.builder().mode(CreditLeaseMode.CLIENT).build())
                .build()) {
            // Nothing named is nothing to warm, not something to throw at a caller who passed a
            // list their own configuration left empty.
            schematic.prewarm(Collections.singletonMap("id", "co_1"), null);
            schematic.prewarm(Collections.singletonMap("id", "co_1"), Collections.<String>emptyList());
            verify(logger, never()).error(anyString());
        }
    }

    @Test
    void identifyWithAPrewarmAfterCloseDropsItInsteadOfThrowing() {
        SchematicLogger logger = mock(SchematicLogger.class);
        Schematic schematic = Schematic.builder()
                .apiKey("test_api_key")
                .logger(logger)
                .creditLeases(
                        CreditLeaseConfig.builder().mode(CreditLeaseMode.CLIENT).build())
                .build();
        schematic.close();

        // The prewarm executor is shut down by now, and a caller identifying on a closed client
        // should not have to catch the shutdown race that queuing onto it loses.
        schematic.identify(
                Collections.singletonMap("id", "user_1"),
                null,
                null,
                null,
                IdentifyOptions.builder()
                        .prewarm(Collections.singletonList("ct_1"))
                        .build());

        verify(logger).debug(contains("skipping the prewarm"));
    }

    @Test
    void aPrewarmListIsCopiedOutOfTheCallersHands() {
        List<String> creditTypes = new ArrayList<>();
        creditTypes.add("ct_1");

        IdentifyOptions options = IdentifyOptions.builder().prewarm(creditTypes).build();
        // The prewarm runs in the background and reads this list after identify has returned, so a
        // caller reusing their own list must not get to change what gets warmed after the fact.
        creditTypes.clear();
        creditTypes.add("ct_2");

        assertEquals(Collections.singletonList("ct_1"), options.getPrewarm());
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
