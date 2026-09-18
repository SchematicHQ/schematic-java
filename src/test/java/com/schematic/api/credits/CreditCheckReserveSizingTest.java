package com.schematic.api.credits;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.schematic.api.types.RulesengineCheckFlagResult;
import com.schematic.api.types.RulesengineCompany;
import com.schematic.api.types.RulesengineEntitlementValueType;
import com.schematic.api.types.RulesengineFeatureEntitlement;
import com.schematic.api.types.RulesengineFlag;
import com.schematic.api.types.RulesengineUser;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.Callable;
import org.junit.jupiter.api.Test;

/** How large a hold a check takes, and what it leaves behind when it cannot take one. */
class CreditCheckReserveSizingTest {

    private static final Instant NOW = Instant.parse("2026-01-01T00:00:00Z");
    private static final Clock CLOCK = Clock.fixed(NOW, ZoneOffset.UTC);

    /** Allows, and meters the credit at ten credits a unit. */
    private static class AllowingDataStream implements CreditCheckDataStream {
        @Override
        public RulesengineFlag getFlag(String flagKey) {
            return RulesengineFlag.builder()
                    .accountId("acct")
                    .defaultValue(false)
                    .environmentId("env")
                    .id("flag_1")
                    .key(flagKey)
                    .build();
        }

        @Override
        public RulesengineCompany getCompany(Map<String, String> keys) {
            return RulesengineCompany.builder()
                    .accountId("acct")
                    .environmentId("env")
                    .id("co_1")
                    .creditBalances(Collections.singletonMap("ct_1", 5000.0))
                    .build();
        }

        @Override
        public RulesengineUser getUser(Map<String, String> keys) {
            return null;
        }

        @Override
        public RulesengineCheckFlagResult evaluateFlag(
                RulesengineFlag flag, RulesengineCompany company, RulesengineUser user, PreflightOptions preflight) {
            return RulesengineCheckFlagResult.builder()
                    .flagKey("inference")
                    .reason("ok")
                    .value(true)
                    .flagId("flag_1")
                    .entitlement(RulesengineFeatureEntitlement.builder()
                            .featureId("feat_1")
                            .featureKey("inference")
                            .valueType(RulesengineEntitlementValueType.CREDIT)
                            .creditId("ct_1")
                            .consumptionRate(10.0)
                            .eventSubtype("inference_tokens")
                            .build())
                    .build();
        }
    }

    /** A clock nothing can read, standing in for anything the reservation prep can throw on. */
    private static final class BrokenClock extends Clock {
        @Override
        public ZoneId getZone() {
            return ZoneOffset.UTC;
        }

        @Override
        public Clock withZone(ZoneId zone) {
            return this;
        }

        @Override
        public Instant instant() {
            throw new IllegalStateException("no clock");
        }
    }

    /** Allows the probe, then throws when the gate asks. */
    private static final class ThrowingGateDataStream extends AllowingDataStream {
        private int evaluations;

        @Override
        public RulesengineCheckFlagResult evaluateFlag(
                RulesengineFlag flag, RulesengineCompany company, RulesengineUser user, PreflightOptions preflight) {
            if (++evaluations > 1) {
                throw new IllegalStateException("the engine blew up");
            }
            return super.evaluateFlag(flag, company, user, preflight);
        }
    }

    private static final class Fixture {
        final InMemoryLeaseStore leases = new InMemoryLeaseStore(CLOCK);
        final InMemoryReservationStore holds = new InMemoryReservationStore(leases, CLOCK);
        final CreditLeaseManager manager;
        final CreditCheck flow;
        boolean fellBack;

        Fixture(Clock flowClock) {
            this(flowClock, new AllowingDataStream());
        }

        Fixture(Clock flowClock, CreditCheckDataStream source) {
            LeaseWireClient wire = new LeaseWireClient() {
                @Override
                public LeaseGrant acquire(String companyId, String creditTypeId, double amount, Instant expiresAt) {
                    throw new UnsupportedOperationException("the slot already holds a lease");
                }

                @Override
                public LeaseGrant extend(String leaseId, double additionalAmount, Instant expiresAt) {
                    throw new UnsupportedOperationException("no extend expected");
                }

                @Override
                public void release(String leaseId) {}
            };
            manager = new CreditLeaseManager(
                    wire, leases, holds, CreditLeaseConfig.builder().build(), null, CLOCK);
            flow = new CreditCheck(source, leases, holds, manager, null, flowClock, null, null);
            leases.replace(new LeaseGrant("lse_1", "co_1", "ct_1", 1000, NOW.plusSeconds(300)));
        }

        CheckResult run(double usage) {
            Callable<CheckResult> fallback = () -> {
                fellBack = true;
                return new CheckResult(true, true, "fallback", "inference", null, null, null, null);
            };
            CheckResult result = flow.check(
                    new CheckRequest(
                            "inference",
                            Collections.singletonMap("id", "co_1"),
                            null,
                            usage,
                            "inference_tokens",
                            false),
                    fallback);
            manager.drain(Duration.ofSeconds(5));
            manager.close();
            return result;
        }
    }

    @Test
    void aFractionalUsageHoldsExactlyUsageTimesTheRate() {
        Fixture fixture = new Fixture(CLOCK);

        CheckResult result = fixture.run(0.5);

        assertFalse(fixture.fellBack);
        assertTrue(result.isAllowed());
        assertNotNull(result.getReservation());
        // The ledger figure is the raw product, which is what every SDK sharing this lease
        // computes for the same check. The settling event rounds its own quantity up, because
        // that field is an integer, but the hold does not follow it.
        assertEquals(5.0, result.getReservation().getCreditsReserved());
        assertEquals(0.5, result.getReservation().getQuantityReserved());
        assertEquals(995.0, fixture.leases.get("co_1", "ct_1").getLocalRemainingCredits());
    }

    @Test
    void anEngineThrowAtTheGateCancelsTheHoldItTookFirst() {
        Fixture fixture = new Fixture(CLOCK, new ThrowingGateDataStream());

        CheckResult result = fixture.run(10);

        // A throw is not a verdict, so the check resolves through its fail-closed contract, and
        // the credits debited before the gate go back rather than sitting on the lease.
        assertFalse(fixture.fellBack);
        assertFalse(result.isAllowed());
        assertNull(result.getReservation());
        assertTrue(result.getReason().startsWith("wasm_error"));
        assertEquals(0, fixture.holds.count());
        assertEquals(1000.0, fixture.leases.get("co_1", "ct_1").getLocalRemainingCredits());
    }

    @Test
    void aFailureWhilePreparingTheHoldLeavesNoDebitBehind() {
        Fixture fixture = new Fixture(new BrokenClock());

        CheckResult result = fixture.run(10);

        assertFalse(result.isAllowed());
        assertNull(result.getReservation());
        assertEquals("lease_store_error", result.getReason());
        assertEquals(0, fixture.holds.count());
        // Everything the hold needs is resolved before the debit, so a throw here cannot strand
        // credits on a lease with no reservation naming them.
        assertEquals(1000.0, fixture.leases.get("co_1", "ct_1").getLocalRemainingCredits());
    }
}
