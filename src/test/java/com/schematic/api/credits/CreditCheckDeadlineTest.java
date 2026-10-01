package com.schematic.api.credits;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

/** A check's timeout bounds the whole check, not each lease step in turn. */
class CreditCheckDeadlineTest {

    private static final Instant NOW = Instant.parse("2026-01-01T00:00:00Z");
    private static final Clock CLOCK = Clock.fixed(NOW, ZoneOffset.UTC);

    /** Allows every evaluation, and takes its time resolving the company. */
    private static final class SlowCompanyDataStream implements CreditCheckDataStream {
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
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
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

    @Test
    void theAcquireGetsWhatIsLeftOfTheChecksTimeout() {
        List<Duration> timeouts = Collections.synchronizedList(new ArrayList<>());
        LeaseWireClient wire = new LeaseWireClient() {
            @Override
            public LeaseGrant acquire(String companyId, String creditTypeId, double amount, Instant expiresAt) {
                return acquire(companyId, creditTypeId, amount, expiresAt, null);
            }

            @Override
            public LeaseGrant acquire(
                    String companyId, String creditTypeId, double amount, Instant expiresAt, Duration timeout) {
                timeouts.add(timeout);
                return new LeaseGrant("lse_1", companyId, creditTypeId, 1000, expiresAt);
            }

            @Override
            public LeaseGrant extend(String leaseId, double additionalAmount, Instant expiresAt) {
                throw new UnsupportedOperationException("no extend expected");
            }

            @Override
            public void release(String leaseId) {}
        };
        InMemoryLeaseStore leases = new InMemoryLeaseStore(CLOCK);
        InMemoryReservationStore holds = new InMemoryReservationStore(leases, CLOCK);
        CreditLeaseManager manager = new CreditLeaseManager(
                wire, leases, holds, CreditLeaseConfig.builder().build(), null, CLOCK);
        CreditCheck flow =
                new CreditCheck(new SlowCompanyDataStream(), leases, holds, manager, null, CLOCK, null, null);
        Duration perCheck = Duration.ofSeconds(2);

        CheckResult result = flow.check(
                new CheckRequest(
                        "inference",
                        Collections.singletonMap("id", "co_1"),
                        null,
                        10,
                        "inference_tokens",
                        false,
                        perCheck),
                () -> {
                    throw new AssertionError("no fallback expected");
                });
        manager.close();

        assertTrue(result.isAllowed());
        assertEquals(1, timeouts.size());
        // The company fetch spent part of the budget before the acquire went out.
        assertTrue(
                timeouts.get(0).compareTo(perCheck.minusMillis(300)) <= 0, "the acquire was handed " + timeouts.get(0));
    }
}
