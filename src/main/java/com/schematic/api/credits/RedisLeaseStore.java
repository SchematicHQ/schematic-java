package com.schematic.api.credits;

import java.time.Clock;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import redis.clients.jedis.JedisPooled;

/**
 * Keeps lease slots in Redis, one hash per (company, credit type), so every process pointed at
 * the same Redis gates against one balance.
 *
 * <p>The key layout, hash fields and Lua scripts are identical to the Node, Go and Python SDKs',
 * which is what lets a mixed fleet share one lease. Every script touches exactly one key, keeping
 * them safe under Redis Cluster, where a multi-key script spanning slots raises CROSSSLOT.
 */
public final class RedisLeaseStore implements LeaseStore {

    private static final String LEASE_KEY_NAMESPACE = "credit-lease:";
    // How long after the declared expiry the row survives before Redis evicts it. It gives the
    // sweeper a window to refund expired reservations before the lease state underneath them
    // disappears.
    private static final long LEASE_TTL_GRACE_MS = 60_000L;

    // Expiry is decided against the Redis server's clock, not the calling process's: with many
    // processes sharing one lease, local clock skew would let them disagree on whether the lease
    // is live. The snippet converts TIME to integer milliseconds (matching the stored expiresAt);
    // replicate_commands() comes first, so the non-deterministic TIME read is allowed alongside
    // writes on Redis 5 and 6.
    private static final String LEASE_NOW_MS = "\n"
            + "redis.replicate_commands()\n"
            + "local t = redis.call('TIME')\n"
            + "local now = (tonumber(t[1]) * 1000) + math.floor(tonumber(t[2]) / 1000)\n";

    /**
     * Atomic replace. Writes the lease hash only when the slot is empty or the existing lease has
     * expired. Returns 1 on write, 0 if a live lease already occupies the slot, even one with a
     * different leaseId, e.g. installed by a sibling that raced this acquire. An expired row with
     * the SAME leaseId is reconciled like an extend instead of rewritten, which would reset the
     * balance and erase debits whose reservations are still open.
     */
    private static final String REPLACE_SCRIPT = LEASE_NOW_MS
            + "\n"
            + "local existing_id = redis.call('HGET', KEYS[1], 'leaseId')\n"
            + "local existing_expiry = tonumber(redis.call('HGET', KEYS[1], 'expiresAt') or '0')\n"
            + "local new_id = ARGV[1]\n"
            + "local new_granted = ARGV[2]\n"
            + "local new_expiry = tonumber(ARGV[3])\n"
            + "local grace = tonumber(ARGV[4])\n"
            + "\n"
            + "if existing_id and existing_expiry > now then\n"
            + "    return 0\n"
            + "end\n"
            + "\n"
            + "if existing_id == new_id then\n"
            + "    local granted = tonumber(redis.call('HGET', KEYS[1], 'grantedAmount') or '0')\n"
            + "    local add = tonumber(new_granted) - granted\n"
            + "    if add > 0 then\n"
            + "        local remaining = tonumber(redis.call('HGET', KEYS[1], 'localRemainingCredits') or '0')\n"
            + "        redis.call('HSET', KEYS[1],\n"
            + "            'grantedAmount', new_granted,\n"
            + "            'localRemainingCredits', tostring(remaining + add))\n"
            + "    end\n"
            + "    if new_expiry > existing_expiry then\n"
            + "        redis.call('HSET', KEYS[1], 'expiresAt', ARGV[3])\n"
            + "        redis.call('PEXPIREAT', KEYS[1], new_expiry + grace)\n"
            + "    end\n"
            + "    return 0\n"
            + "end\n"
            + "\n"
            + "redis.call('DEL', KEYS[1])\n"
            + "redis.call('HSET', KEYS[1],\n"
            + "    'leaseId', new_id,\n"
            + "    'companyId', ARGV[5],\n"
            + "    'creditTypeId', ARGV[6],\n"
            + "    'grantedAmount', new_granted,\n"
            + "    'localRemainingCredits', new_granted,\n"
            + "    'expiresAt', ARGV[3])\n"
            + "redis.call('PEXPIREAT', KEYS[1], new_expiry + grace)\n"
            + "return 1\n";

    /**
     * Atomic check-and-decrement on localRemainingCredits. Returns the post-debit balance as a
     * string (a Lua number reply truncates to integer, which would corrupt fractional credit
     * costs) alongside the leaseId it came out of; nil if there is no lease, the lease has
     * expired, or there is insufficient remaining. The expiry guard compares against the Redis
     * server clock, so a reserve against an expired-but-not-yet-evicted row during the TTL grace
     * window is rejected.
     */
    private static final String TRY_RESERVE_SCRIPT = LEASE_NOW_MS
            + "\n"
            + "local raw = redis.call('HGET', KEYS[1], 'localRemainingCredits')\n"
            + "if not raw then return false end\n"
            + "local lease_id = redis.call('HGET', KEYS[1], 'leaseId')\n"
            + "if not lease_id then return false end\n"
            + "local expiry = tonumber(redis.call('HGET', KEYS[1], 'expiresAt') or '0')\n"
            + "if expiry <= now then return false end\n"
            + "local remaining = tonumber(raw)\n"
            + "local requested = tonumber(ARGV[1])\n"
            + "if remaining < requested then return false end\n"
            + "local new_remaining = remaining - requested\n"
            + "redis.call('HSET', KEYS[1], 'localRemainingCredits', tostring(new_remaining))\n"
            + "return { tostring(new_remaining), lease_id }\n";

    /**
     * Refund credits, clamped at grantedAmount. ARGV[2], when non-empty, pins the refund to a
     * specific leaseId: if the slot now holds a different lease, the refund is dropped, because
     * the expired lease's unspent remainder was already returned to the company balance
     * server-side, so crediting the successor would mint phantom credits.
     */
    private static final String REFUND_SCRIPT = "\n"
            + "local raw_remaining = redis.call('HGET', KEYS[1], 'localRemainingCredits')\n"
            + "if not raw_remaining then return 0 end\n"
            + "local required_lease = ARGV[2]\n"
            + "if required_lease and required_lease ~= '' then\n"
            + "    local current_lease = redis.call('HGET', KEYS[1], 'leaseId')\n"
            + "    if current_lease ~= required_lease then return 0 end\n"
            + "end\n"
            + "local remaining = tonumber(raw_remaining)\n"
            + "local granted = tonumber(redis.call('HGET', KEYS[1], 'grantedAmount') or '0')\n"
            + "local refund = tonumber(ARGV[1])\n"
            + "local new_balance = remaining + refund\n"
            + "if new_balance > granted then new_balance = granted end\n"
            + "redis.call('HSET', KEYS[1], 'localRemainingCredits', tostring(new_balance))\n"
            + "return 1\n";

    /**
     * Reconcile the lease to the server-authoritative grantedAmount total (ARGV[1]), crediting
     * the difference to localRemainingCredits. The delta is computed here, atomically against the
     * hash's current total, never by the caller from a pre-wire-call read: per-process
     * single-flight does not cover sibling processes, so two extending the same shared lease
     * concurrently would each apply a delta against the same stale read and mint phantom credits.
     * Expiry only ever moves forward. ARGV[4], when non-empty, pins the extend to a specific
     * leaseId, mirroring the pin on the refund script.
     */
    private static final String EXTEND_SCRIPT = "\n"
            + "local raw_granted = redis.call('HGET', KEYS[1], 'grantedAmount')\n"
            + "if not raw_granted then return 0 end\n"
            + "local required_lease = ARGV[4]\n"
            + "if required_lease and required_lease ~= '' then\n"
            + "    local current_lease = redis.call('HGET', KEYS[1], 'leaseId')\n"
            + "    if current_lease ~= required_lease then return 0 end\n"
            + "end\n"
            + "local granted = tonumber(raw_granted)\n"
            + "local target = tonumber(ARGV[1])\n"
            + "local add = target - granted\n"
            + "if add > 0 then\n"
            + "    local remaining = tonumber(redis.call('HGET', KEYS[1], 'localRemainingCredits') or '0')\n"
            + "    redis.call('HSET', KEYS[1],\n"
            + "        'grantedAmount', tostring(target),\n"
            + "        'localRemainingCredits', tostring(remaining + add))\n"
            + "end\n"
            + "local new_expiry = tonumber(ARGV[2])\n"
            + "local grace = tonumber(ARGV[3])\n"
            + "local current_expiry = tonumber(redis.call('HGET', KEYS[1], 'expiresAt') or '0')\n"
            + "if new_expiry > current_expiry then\n"
            + "    redis.call('HSET', KEYS[1], 'expiresAt', ARGV[2])\n"
            + "    redis.call('PEXPIREAT', KEYS[1], new_expiry + grace)\n"
            + "end\n"
            + "return 1\n";

    private final JedisPooled jedis;
    private final String keyPrefix;
    private final long defaultLeaseDurationMs;
    private final Clock clock;

    public RedisLeaseStore(JedisPooled jedis) {
        this(jedis, null, null, null);
    }

    /**
     * @param jedis a pre-configured client, shared with the datastream cache when one is set up
     * @param keyPrefix namespace for lease keys; defaults to {@code schematic:}
     * @param defaultLeaseDuration fallback expiry for an {@link #extend} that supplies none. The
     *     lease manager always supplies one, so this only matters for a direct caller.
     * @param clock reads the current time for the expiry a bare extend derives. Lease liveness
     *     itself is decided by the Redis server's clock inside the scripts.
     */
    public RedisLeaseStore(JedisPooled jedis, String keyPrefix, java.time.Duration defaultLeaseDuration, Clock clock) {
        this.jedis = jedis;
        this.keyPrefix = keyPrefix != null ? keyPrefix : CreditLeaseDefaults.KEY_PREFIX;
        this.defaultLeaseDurationMs =
                (defaultLeaseDuration != null ? defaultLeaseDuration : CreditLeaseDefaults.LEASE_DURATION).toMillis();
        this.clock = clock != null ? clock : Clock.systemUTC();
    }

    /** Public so the reservation store can target the same lease hash for refunds. */
    public String hashKey(String companyId, String creditTypeId) {
        return keyPrefix + LEASE_KEY_NAMESPACE + LeaseStore.leaseKey(companyId, creditTypeId);
    }

    @Override
    public LeaseState get(String companyId, String creditTypeId) {
        Map<String, String> raw = jedis.hgetAll(hashKey(companyId, creditTypeId));
        if (raw == null || raw.get("leaseId") == null) {
            return null;
        }
        return new LeaseState(
                raw.get("leaseId"),
                raw.get("companyId"),
                raw.get("creditTypeId"),
                CreditAmounts.parse(raw.get("grantedAmount"), 0),
                CreditAmounts.parse(raw.get("localRemainingCredits"), 0),
                Instant.ofEpochMilli((long) CreditAmounts.parse(raw.get("expiresAt"), 0)));
    }

    @Override
    public boolean replace(LeaseGrant grant) {
        // No client clock here: the script reads now from the Redis server via TIME, so every
        // process agrees on expiry.
        Object result = jedis.eval(
                REPLACE_SCRIPT,
                Collections.singletonList(hashKey(grant.getCompanyId(), grant.getCreditTypeId())),
                Arrays.asList(
                        grant.getLeaseId(),
                        CreditAmounts.format(grant.getGrantedAmount()),
                        Long.toString(grant.getExpiresAt().toEpochMilli()),
                        Long.toString(LEASE_TTL_GRACE_MS),
                        grant.getCompanyId(),
                        grant.getCreditTypeId()));
        return result instanceof Long && (Long) result == 1L;
    }

    @Override
    public ReserveResult tryReserve(String companyId, String creditTypeId, double credits) {
        // Reject a non-finite or negative debit before it reaches the script: "NaN" parses back
        // to nan in Lua, slips through the comparison, and would poison the shared balance.
        if (!CreditAmounts.isValidQuantity(credits)) {
            return null;
        }
        Object result = jedis.eval(
                TRY_RESERVE_SCRIPT,
                Collections.singletonList(hashKey(companyId, creditTypeId)),
                // Only the requested amount: now comes from the Redis server clock.
                Collections.singletonList(CreditAmounts.format(credits)));
        // A nil reply (could not reserve) arrives as null; success is a two-element reply of
        // [post-debit balance, charged leaseId], both strings.
        if (!(result instanceof List)) {
            return null;
        }
        List<?> reply = (List<?>) result;
        if (reply.size() < 2) {
            return null;
        }
        return new ReserveResult(CreditAmounts.parse(String.valueOf(reply.get(0)), 0), String.valueOf(reply.get(1)));
    }

    @Override
    public void refund(String companyId, String creditTypeId, double credits, String pinLeaseId) {
        if (credits <= 0) {
            return;
        }
        jedis.eval(
                REFUND_SCRIPT,
                Collections.singletonList(hashKey(companyId, creditTypeId)),
                // An empty string disables the lease pin: Lua has no nil ARGV.
                Arrays.asList(CreditAmounts.format(credits), pinLeaseId != null ? pinLeaseId : ""));
    }

    @Override
    public void extend(
            String companyId, String creditTypeId, double grantedTotal, Instant newExpiresAt, String pinLeaseId) {
        long expiry = newExpiresAt != null
                ? newExpiresAt.toEpochMilli()
                : clock.instant().toEpochMilli() + defaultLeaseDurationMs;
        jedis.eval(
                EXTEND_SCRIPT,
                Collections.singletonList(hashKey(companyId, creditTypeId)),
                // grantedTotal is the server-authoritative TOTAL; the script computes the credit
                // delta atomically against the stored total.
                Arrays.asList(
                        CreditAmounts.format(grantedTotal),
                        Long.toString(expiry),
                        Long.toString(LEASE_TTL_GRACE_MS),
                        pinLeaseId != null ? pinLeaseId : ""));
    }

    @Override
    public void drop(String companyId, String creditTypeId) {
        // A plain single-key delete: there is no secondary index to keep in sync.
        jedis.del(hashKey(companyId, creditTypeId));
    }
}
