package com.schematic.api.credits;

import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;

/**
 * Keeps lease slots in this process only, so it gates a single process. Swap in
 * {@link RedisLeaseStore} to gate across several; both implement {@link LeaseStore}.
 */
public final class InMemoryLeaseStore implements LeaseStore, LeaseLister {

    private final Clock clock;
    // Concurrent so list() can snapshot it without taking every slot lock; the compound
    // read-modify-write below still runs under the slot's lock.
    private final ConcurrentHashMap<String, LeaseState> leases = new ConcurrentHashMap<>();
    // One lock per slot, so a reserve on one company never waits on another's.
    private final ConcurrentHashMap<String, ReentrantLock> locks = new ConcurrentHashMap<>();

    public InMemoryLeaseStore() {
        this(Clock.systemUTC());
    }

    public InMemoryLeaseStore(Clock clock) {
        this.clock = clock != null ? clock : Clock.systemUTC();
    }

    @Override
    public LeaseState get(String companyId, String creditTypeId) {
        String key = LeaseStore.leaseKey(companyId, creditTypeId);
        return withLock(key, () -> leases.get(key));
    }

    @Override
    public boolean replace(LeaseGrant grant) {
        String key = LeaseStore.leaseKey(grant.getCompanyId(), grant.getCreditTypeId());
        return withLock(key, () -> {
            LeaseState existing = leases.get(key);
            if (existing != null && existing.isLiveAt(now())) {
                // A live lease already holds this slot: preserve its already-debited balance
                // rather than clobbering it.
                return false;
            }
            if (existing != null && existing.getLeaseId().equals(grant.getLeaseId())) {
                // The same lease coming back over its own expired row: a stale acquire response
                // for a lease the idempotent server also handed a racing sibling, which may since
                // have extended it. Rewriting would reset the balance to the full grant and erase
                // debits whose reservations are still open, so reconcile like an extend instead.
                leases.put(key, reconcile(existing, grant.getGrantedAmount(), grant.getExpiresAt()));
                return false;
            }
            leases.put(
                    key,
                    new LeaseState(
                            grant.getLeaseId(),
                            grant.getCompanyId(),
                            grant.getCreditTypeId(),
                            grant.getGrantedAmount(),
                            grant.getGrantedAmount(),
                            grant.getExpiresAt()));
            return true;
        });
    }

    @Override
    public ReserveResult tryReserve(String companyId, String creditTypeId, double credits) {
        // Reject a non-finite or negative debit outright: NaN passes every comparison below, and
        // a NaN balance would approve every later reserve.
        if (!CreditAmounts.isValidQuantity(credits)) {
            return null;
        }
        String key = LeaseStore.leaseKey(companyId, creditTypeId);
        return withLock(key, () -> {
            LeaseState entry = leases.get(key);
            if (entry == null) {
                return null;
            }
            // Never reserve against an expired lease: the server treats it as released and has
            // refunded the grant, so the local balance is stale.
            if (!entry.isLiveAt(now())) {
                return null;
            }
            if (entry.getLocalRemainingCredits() < credits) {
                return null;
            }
            double balance = entry.getLocalRemainingCredits() - credits;
            leases.put(key, withBalance(entry, balance));
            // The lease id is read under the same lock as the debit: the caller pins its hold to
            // it, so a read after the lock could name a lease that replaced this one in between.
            return new ReserveResult(balance, entry.getLeaseId());
        });
    }

    @Override
    public void refund(String companyId, String creditTypeId, double credits, String pinLeaseId) {
        if (credits <= 0) {
            return;
        }
        String key = LeaseStore.leaseKey(companyId, creditTypeId);
        withLock(key, () -> {
            LeaseState entry = leases.get(key);
            if (entry == null) {
                return null;
            }
            if (pinLeaseId != null
                    && !pinLeaseId.isEmpty()
                    && !entry.getLeaseId().equals(pinLeaseId)) {
                return null;
            }
            double balance = Math.min(entry.getLocalRemainingCredits() + credits, entry.getGrantedAmount());
            leases.put(key, withBalance(entry, balance));
            return null;
        });
    }

    @Override
    public void extend(
            String companyId, String creditTypeId, double grantedTotal, Instant newExpiresAt, String pinLeaseId) {
        String key = LeaseStore.leaseKey(companyId, creditTypeId);
        withLock(key, () -> {
            LeaseState entry = leases.get(key);
            if (entry == null) {
                return null;
            }
            if (pinLeaseId != null
                    && !pinLeaseId.isEmpty()
                    && !entry.getLeaseId().equals(pinLeaseId)) {
                return null;
            }
            leases.put(key, reconcile(entry, grantedTotal, newExpiresAt));
            return null;
        });
    }

    @Override
    public void drop(String companyId, String creditTypeId) {
        String key = LeaseStore.leaseKey(companyId, creditTypeId);
        while (true) {
            ReentrantLock lock = lockFor(key);
            lock.lock();
            try {
                if (locks.get(key) != lock) {
                    continue;
                }
                leases.remove(key);
                // Retire the lock with the state it guarded, so neither map grows with every
                // company this process has ever leased against. Retiring it last, and while
                // holding it, is what lets a waiter notice and retake the replacement.
                locks.remove(key, lock);
                return;
            } finally {
                lock.unlock();
            }
        }
    }

    @Override
    public List<LeaseState> list() {
        return new ArrayList<>(leases.values());
    }

    /**
     * Reconciles an entry to a server-authoritative total: the delta is credited to the balance,
     * a total already applied is a no-op, and the expiry only ever moves forward, so an
     * out-of-order apply cannot shorten a lease a concurrent extend already pushed out.
     */
    private static LeaseState reconcile(LeaseState entry, double grantedTotal, Instant newExpiresAt) {
        double granted = entry.getGrantedAmount();
        double balance = entry.getLocalRemainingCredits();
        double add = grantedTotal - granted;
        if (add > 0) {
            granted = grantedTotal;
            balance += add;
        }
        Instant expiresAt = entry.getExpiresAt();
        if (newExpiresAt != null && newExpiresAt.isAfter(expiresAt)) {
            expiresAt = newExpiresAt;
        }
        return new LeaseState(
                entry.getLeaseId(), entry.getCompanyId(), entry.getCreditTypeId(), granted, balance, expiresAt);
    }

    private static LeaseState withBalance(LeaseState entry, double balance) {
        return new LeaseState(
                entry.getLeaseId(),
                entry.getCompanyId(),
                entry.getCreditTypeId(),
                entry.getGrantedAmount(),
                balance,
                entry.getExpiresAt());
    }

    /**
     * Runs {@code body} under the slot's lock, and only ever under the lock currently registered
     * for that key.
     *
     * <p>{@link #drop} retires a lock along with the state it guarded. A thread that was already
     * waiting on that lock wakes up holding a retired one, while a newcomer serialises on the
     * replacement, so it re-checks and retakes the replacement instead of mutating the slot
     * behind the newcomer's back. A lock can only be retired by a drop, so the retry terminates.
     */
    private <T> T withLock(String key, Supplier<T> body) {
        while (true) {
            ReentrantLock lock = lockFor(key);
            lock.lock();
            try {
                if (locks.get(key) != lock) {
                    continue;
                }
                return body.get();
            } finally {
                lock.unlock();
            }
        }
    }

    private ReentrantLock lockFor(String key) {
        ReentrantLock lock = locks.get(key);
        if (lock != null) {
            return lock;
        }
        ReentrantLock created = new ReentrantLock();
        ReentrantLock raced = locks.putIfAbsent(key, created);
        return raced != null ? raced : created;
    }

    private Instant now() {
        return clock.instant();
    }
}
