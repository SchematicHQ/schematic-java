package com.schematic.api.credits;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.Test;

/** What the per-process store keeps, and what it lets go of. */
class InMemoryLeaseStoreTest {

    private static final Instant NOW = Instant.parse("2026-01-01T00:00:00Z");
    private static final Clock CLOCK = Clock.fixed(NOW, ZoneOffset.UTC);

    @Test
    void dropRetiresTheSlotLockAlongWithItsState() throws Exception {
        InMemoryLeaseStore store = new InMemoryLeaseStore(CLOCK);
        store.replace(new LeaseGrant("lse_1", "co_1", "ct_1", 1000, NOW.plusSeconds(300)));
        store.tryReserve("co_1", "ct_1", 100);
        assertEquals(1, lockCount(store));

        store.drop("co_1", "ct_1");

        // A long-lived process leases against many companies, and a lock left behind for each one
        // is a slot's worth of memory nothing will ever read again.
        assertEquals(0, lockCount(store));
        assertTrue(store.list().isEmpty());
    }

    @Test
    void oneSlotIsStillExclusiveWhileItsLockIsBeingRetired() throws Exception {
        InMemoryLeaseStore store = new InMemoryLeaseStore(CLOCK);
        int rounds = 2000;
        // Each lease grants exactly what one reserve takes, so a lease id can be charged once and
        // only once. Seeing the same id twice means two threads debited it under different locks.
        List<String> charged = Collections.synchronizedList(new ArrayList<>());
        List<Throwable> failures = Collections.synchronizedList(new ArrayList<>());

        Thread writer = new Thread(() -> {
            for (int i = 0; i < rounds; i++) {
                store.drop("co_1", "ct_1");
                store.replace(new LeaseGrant("lse_" + i, "co_1", "ct_1", 100, NOW.plusSeconds(300)));
            }
        });
        List<Thread> readers = new ArrayList<>();
        for (int t = 0; t < 3; t++) {
            readers.add(new Thread(() -> {
                for (int i = 0; i < rounds; i++) {
                    ReserveResult result = store.tryReserve("co_1", "ct_1", 100);
                    if (result != null) {
                        charged.add(result.getLeaseId());
                    }
                }
            }));
        }

        List<Thread> all = new ArrayList<>(readers);
        all.add(writer);
        for (Thread thread : all) {
            thread.setUncaughtExceptionHandler((ignored, error) -> failures.add(error));
            thread.start();
        }
        for (Thread thread : all) {
            thread.join(30_000);
        }

        assertEquals(Collections.emptyList(), failures);
        Set<String> distinct = new HashSet<>(charged);
        assertEquals(
                charged.size(),
                distinct.size(),
                "a lease was charged twice: " + charged.size() + " debits over " + distinct.size() + " leases");
    }

    private static int lockCount(InMemoryLeaseStore store) throws Exception {
        Field field = InMemoryLeaseStore.class.getDeclaredField("locks");
        field.setAccessible(true);
        return ((Map<?, ?>) field.get(store)).size();
    }
}
