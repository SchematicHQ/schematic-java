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
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.Callable;
import org.junit.jupiter.api.Test;

/** A hold is pinned to the lease the debit landed on, never to the one the acquire handed back. */
class CreditCheckReservePinningTest {

    private static final Instant NOW = Instant.parse("2026-01-01T00:00:00Z");
    private static final Clock CLOCK = Clock.fixed(NOW, ZoneOffset.UTC);

    /** Answers both evaluations allow, and meters the credit the entitlement names. */
    private static final class AllowingDataStream implements CreditCheckDataStream {
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

    /** Rewrites what the debit reports, so the flow can be held to what it pins. */
    private static final class RewritingLeaseStore implements LeaseStore {
        private final LeaseStore delegate;
        private final String debitedLeaseId;

        RewritingLeaseStore(LeaseStore delegate, String debitedLeaseId) {
            this.delegate = delegate;
            this.debitedLeaseId = debitedLeaseId;
        }

        @Override
        public LeaseState get(String companyId, String creditTypeId) {
            return delegate.get(companyId, creditTypeId);
        }

        @Override
        public boolean replace(LeaseGrant grant) {
            return delegate.replace(grant);
        }

        @Override
        public ReserveResult tryReserve(String companyId, String creditTypeId, double credits) {
            ReserveResult result = delegate.tryReserve(companyId, creditTypeId, credits);
            return result == null ? null : new ReserveResult(result.getBalance(), debitedLeaseId);
        }

        @Override
        public void refund(String companyId, String creditTypeId, double credits, String pinLeaseId) {
            delegate.refund(companyId, creditTypeId, credits, pinLeaseId);
        }

        @Override
        public void extend(
                String companyId, String creditTypeId, double grantedTotal, Instant newExpiresAt, String pinLeaseId) {
            delegate.extend(companyId, creditTypeId, grantedTotal, newExpiresAt, pinLeaseId);
        }

        @Override
        public void drop(String companyId, String creditTypeId) {
            delegate.drop(companyId, creditTypeId);
        }
    }

    private static final class Fixture {
        final InMemoryLeaseStore backing = new InMemoryLeaseStore(CLOCK);
        final LeaseStore leases;
        final InMemoryReservationStore holds;
        final CreditLeaseManager manager;
        final CreditCheck flow;
        boolean fellBack;

        Fixture(String debitedLeaseId) {
            leases = new RewritingLeaseStore(backing, debitedLeaseId);
            holds = new InMemoryReservationStore(leases, CLOCK);
            LeaseWireClient wire = new LeaseWireClient() {
                @Override
                public LeaseGrant acquire(
                        String companyId, String creditTypeId, double requestedAmount, Instant expiresAt) {
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
            flow = new CreditCheck(new AllowingDataStream(), leases, holds, manager, null, CLOCK, null, null);
            backing.replace(new LeaseGrant("lse_acquired", "co_1", "ct_1", 1000, NOW.plusSeconds(300)));
        }

        CheckResult run() {
            Callable<CheckResult> fallback = () -> {
                fellBack = true;
                return new CheckResult(true, true, "fallback", "inference", null, null, null, null);
            };
            CheckResult result = flow.check(
                    new CheckRequest(
                            "inference", Collections.singletonMap("id", "co_1"), null, 10, "inference_tokens", false),
                    fallback);
            manager.drain(Duration.ofSeconds(5));
            manager.close();
            return result;
        }
    }

    @Test
    void theHoldNamesTheLeaseTheDebitLandedOn() {
        // The slot took on a different lease between the acquire and the debit, which the atomic
        // reserve reports. Pinning the acquired lease instead would send this hold's refunds, and
        // its billing, to a lease that never held the credits.
        Fixture fixture = new Fixture("lse_debited");

        CheckResult result = fixture.run();

        assertFalse(fixture.fellBack);
        assertTrue(result.isAllowed());
        assertNotNull(result.getReservation());
        assertEquals("lse_debited", result.getReservation().getLeaseId());
    }

    @Test
    void aDebitThatNamesNoLeaseIsHandedBackRatherThanPinnedToTheAcquiredOne() {
        Fixture fixture = new Fixture(null);

        CheckResult result = fixture.run();

        assertFalse(result.isAllowed());
        assertNull(result.getReservation());
        assertEquals("lease_store_error", result.getReason());
        assertEquals(0, fixture.holds.count());
        // The debit went back to the lease rather than sitting there until it expires.
        assertEquals(1000.0, fixture.backing.get("co_1", "ct_1").getLocalRemainingCredits());
    }
}
