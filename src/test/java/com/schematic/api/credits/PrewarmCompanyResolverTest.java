package com.schematic.api.credits;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.schematic.api.types.RulesengineCompany;
import java.time.Duration;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import org.junit.jupiter.api.Test;

class PrewarmCompanyResolverTest {

    private static final Map<String, String> KEYS = Collections.singletonMap("company_id", "acme");

    private static RulesengineCompany company() {
        return RulesengineCompany.builder()
                .accountId("acct")
                .environmentId("env")
                .id("co_1")
                .build();
    }

    private static final Function<Map<String, String>, RulesengineCompany> NOT_CACHED = keys -> null;
    private static final Function<RuntimeException, Void> IGNORE_ERRORS = error -> null;

    private static String resolve(
            Map<String, String> keys,
            Function<Map<String, String>, RulesengineCompany> cached,
            Function<Map<String, String>, RulesengineCompany> fetch,
            Duration timeout) {
        return PrewarmCompanyResolver.resolve(
                keys, cached, fetch, timeout, Duration.ofMillis(1), () -> false, IGNORE_ERRORS);
    }

    @Test
    void aZeroTimeoutStillAnswersFromTheCache() {
        AtomicInteger fetches = new AtomicInteger();

        String id = resolve(
                KEYS,
                keys -> company(),
                keys -> {
                    fetches.incrementAndGet();
                    return company();
                },
                Duration.ZERO);

        assertEquals("co_1", id);
        // Zero means cache-only, so nothing goes to the wire.
        assertEquals(0, fetches.get());
    }

    @Test
    void aZeroTimeoutGivesUpOnACacheMissWithoutFetching() {
        AtomicInteger fetches = new AtomicInteger();

        String id = resolve(
                KEYS,
                NOT_CACHED,
                keys -> {
                    fetches.incrementAndGet();
                    return company();
                },
                Duration.ZERO);

        assertNull(id);
        assertEquals(0, fetches.get());
    }

    @Test
    void anIdOnTheKeysNeedsNoResolution() {
        AtomicInteger reads = new AtomicInteger();

        String id = resolve(
                Collections.singletonMap("id", "co_9"),
                keys -> {
                    reads.incrementAndGet();
                    return company();
                },
                NOT_CACHED,
                Duration.ofSeconds(5));

        assertEquals("co_9", id);
        assertEquals(0, reads.get());
    }

    @Test
    void aCacheMissFetchesUntilTheCompanySurfaces() {
        AtomicInteger fetches = new AtomicInteger();

        String id = resolve(
                KEYS, NOT_CACHED, keys -> fetches.incrementAndGet() < 3 ? null : company(), Duration.ofSeconds(5));

        assertEquals("co_1", id);
        assertEquals(3, fetches.get());
    }

    @Test
    void aFetchThatNeverAnswersGivesUpAtTheTimeout() {
        String id = resolve(KEYS, NOT_CACHED, keys -> null, Duration.ofMillis(20));

        assertNull(id);
    }
}
