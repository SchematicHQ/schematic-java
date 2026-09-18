package com.schematic.api.credits;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class CreditLeaseConfigValidationTest {

    @Test
    void aNaNLeaseSizeIsRejected() {
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> CreditLeaseConfig.builder().defaultLeaseSize(Double.NaN).build());
        assertTrue(thrown.getMessage().contains("defaultLeaseSize"));
    }

    @Test
    void anInfiniteLeaseSizeIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> CreditLeaseConfig.builder()
                .defaultLeaseSize(Double.POSITIVE_INFINITY)
                .build());
    }

    @Test
    void aZeroLeaseSizeIsRejected() {
        assertThrows(
                IllegalArgumentException.class,
                () -> CreditLeaseConfig.builder().defaultLeaseSize(0).build());
    }

    @Test
    void aNegativeLeaseSizeIsRejected() {
        assertThrows(
                IllegalArgumentException.class,
                () -> CreditLeaseConfig.builder().defaultLeaseSize(-1).build());
    }

    @Test
    void aNaNLowWaterMarkIsRejected() {
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> CreditLeaseConfig.builder().lowWaterMark(Double.NaN).build());
        assertTrue(thrown.getMessage().contains("lowWaterMark"));
    }

    @Test
    void aLowWaterMarkOfZeroIsRejected() {
        assertThrows(
                IllegalArgumentException.class,
                () -> CreditLeaseConfig.builder().lowWaterMark(0).build());
    }

    @Test
    void aLowWaterMarkOfOneIsRejected() {
        assertThrows(
                IllegalArgumentException.class,
                () -> CreditLeaseConfig.builder().lowWaterMark(1).build());
    }

    @Test
    void aNegativeLowWaterMarkIsRejected() {
        assertThrows(
                IllegalArgumentException.class,
                () -> CreditLeaseConfig.builder().lowWaterMark(-0.5).build());
    }

    @Test
    void aWaterMarkInsideTheOpenUnitIntervalIsAccepted() {
        CreditLeaseConfig config =
                CreditLeaseConfig.builder().lowWaterMark(0.99).build();
        assertEquals(0.99, config.getLowWaterMark(), 0);
    }

    @Test
    void aZeroLeaseDurationIsRejected() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> CreditLeaseConfig.builder()
                .defaultLeaseDuration(Duration.ZERO)
                .build());
        assertTrue(thrown.getMessage().contains("defaultLeaseDuration"));
    }

    @Test
    void aNegativeLeaseDurationIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> CreditLeaseConfig.builder()
                .defaultLeaseDuration(Duration.ofSeconds(-1))
                .build());
    }

    @Test
    void aZeroReservationTtlIsRejected() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> CreditLeaseConfig.builder()
                .defaultReservationTtl(Duration.ZERO)
                .build());
        assertTrue(thrown.getMessage().contains("defaultReservationTtl"));
    }

    @Test
    void aNegativeReservationTtlIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> CreditLeaseConfig.builder()
                .defaultReservationTtl(Duration.ofMinutes(-5))
                .build());
    }

    @Test
    void aZeroSweepIntervalIsRejected() {
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> CreditLeaseConfig.builder().sweepInterval(Duration.ZERO).build());
        assertTrue(thrown.getMessage().contains("sweepInterval"));
    }

    @Test
    void aNegativeSweepIntervalIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> CreditLeaseConfig.builder()
                .sweepInterval(Duration.ofMillis(-1))
                .build());
    }

    @Test
    void aFullyValidConfigBuilds() {
        CreditLeaseConfig config = CreditLeaseConfig.builder()
                .defaultLeaseSize(500)
                .lowWaterMark(0.4)
                .defaultLeaseDuration(Duration.ofMinutes(2))
                .defaultReservationTtl(Duration.ofSeconds(30))
                .sweepInterval(Duration.ofSeconds(2))
                .override(
                        "tokens",
                        CreditLeaseOverride.builder()
                                .defaultLeaseSize(50)
                                .lowWaterMark(0.1)
                                .defaultLeaseDuration(Duration.ofMinutes(1))
                                .defaultReservationTtl(Duration.ofSeconds(15))
                                .build())
                .build();

        assertEquals(500, config.getDefaultLeaseSize(), 0);
        assertEquals(Duration.ofSeconds(2), config.getSweepInterval());
        assertEquals(1, config.getOverrides().size());
    }

    @Test
    void aConfigThatSetsNothingBuilds() {
        CreditLeaseConfig config = CreditLeaseConfig.builder().build();

        assertNotNull(config);
        assertEquals(CreditLeaseMode.AUTO, config.getMode());
    }

    @Test
    void anOverrideWithANaNLeaseSizeNamesTheCreditType() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> CreditLeaseConfig.builder()
                .override(
                        "tokens",
                        CreditLeaseOverride.builder()
                                .defaultLeaseSize(Double.NaN)
                                .build())
                .build());
        assertTrue(thrown.getMessage().contains("defaultLeaseSize"));
        assertTrue(thrown.getMessage().contains("for credit type tokens"));
    }

    @Test
    void anOverrideWithANegativeLeaseSizeIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> CreditLeaseConfig.builder()
                .override(
                        "tokens",
                        CreditLeaseOverride.builder().defaultLeaseSize(-5).build())
                .build());
    }

    @Test
    void anOverrideWithAWaterMarkOfOneNamesTheCreditType() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> CreditLeaseConfig.builder()
                .override(
                        "tokens", CreditLeaseOverride.builder().lowWaterMark(1).build())
                .build());
        assertTrue(thrown.getMessage().contains("lowWaterMark"));
        assertTrue(thrown.getMessage().contains("for credit type tokens"));
    }

    @Test
    void anOverrideWithAZeroLeaseDurationNamesTheCreditType() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> CreditLeaseConfig.builder()
                .override(
                        "tokens",
                        CreditLeaseOverride.builder()
                                .defaultLeaseDuration(Duration.ZERO)
                                .build())
                .build());
        assertTrue(thrown.getMessage().contains("defaultLeaseDuration"));
        assertTrue(thrown.getMessage().contains("for credit type tokens"));
    }

    @Test
    void anOverrideWithANegativeReservationTtlNamesTheCreditType() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> CreditLeaseConfig.builder()
                .override(
                        "tokens",
                        CreditLeaseOverride.builder()
                                .defaultReservationTtl(Duration.ofSeconds(-1))
                                .build())
                .build());
        assertTrue(thrown.getMessage().contains("defaultReservationTtl"));
        assertTrue(thrown.getMessage().contains("for credit type tokens"));
    }

    @Test
    void anOverrideThatSetsNothingBuilds() {
        CreditLeaseConfig config = CreditLeaseConfig.builder()
                .override("tokens", CreditLeaseOverride.builder().build())
                .build();

        assertEquals(1, config.getOverrides().size());
    }

    @Nested
    class Resolution {

        private CreditLeaseConfig config() {
            return CreditLeaseConfig.builder()
                    .defaultLeaseSize(1000)
                    .lowWaterMark(0.5)
                    .defaultLeaseDuration(Duration.ofMinutes(10))
                    .defaultReservationTtl(Duration.ofSeconds(90))
                    .override(
                            "tokens",
                            CreditLeaseOverride.builder()
                                    .defaultLeaseSize(25)
                                    .defaultLeaseDuration(Duration.ofMinutes(1))
                                    .build())
                    .build();
        }

        @Test
        void anOverriddenCreditTypeTakesTheKnobsItSetsAndTheConfigDefaultsItDoesNot() {
            ResolvedLeaseConfig resolved = config().resolve("tokens");

            assertEquals(25, resolved.getLeaseSize(), 0);
            assertEquals(Duration.ofMinutes(1), resolved.getLeaseDuration());
            assertEquals(0.5, resolved.getLowWaterMark(), 0);
            assertEquals(Duration.ofSeconds(90), resolved.getReservationTtl());
        }

        @Test
        void aCreditTypeWithoutAnOverrideTakesEveryConfigDefault() {
            ResolvedLeaseConfig resolved = config().resolve("seats");

            assertEquals(1000, resolved.getLeaseSize(), 0);
            assertEquals(Duration.ofMinutes(10), resolved.getLeaseDuration());
            assertEquals(0.5, resolved.getLowWaterMark(), 0);
            assertEquals(Duration.ofSeconds(90), resolved.getReservationTtl());
        }

        @Test
        void aConfigThatSetsNothingResolvesToTheLibraryDefaults() {
            ResolvedLeaseConfig resolved = CreditLeaseConfig.builder().build().resolve("tokens");

            assertEquals(CreditLeaseDefaults.LEASE_SIZE, resolved.getLeaseSize(), 0);
            assertEquals(CreditLeaseDefaults.LEASE_DURATION, resolved.getLeaseDuration());
            assertEquals(CreditLeaseDefaults.LOW_WATER_MARK, resolved.getLowWaterMark(), 0);
            assertEquals(CreditLeaseDefaults.RESERVATION_TTL, resolved.getReservationTtl());
        }
    }
}
