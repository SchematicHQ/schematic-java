package com.schematic.api.credits;

import com.fasterxml.jackson.databind.JsonNode;
import com.schematic.api.core.ObjectMappers;
import java.time.Clock;
import java.time.Instant;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import redis.clients.jedis.AbstractTransaction;
import redis.clients.jedis.JedisPooled;

/**
 * Keeps the reservation table in Redis and refunds through the lease store it is handed.
 *
 * <p>Each hold is a hash, indexed by expiry in a sorted set so the sweeper can pop expired
 * entries in O(log n), and by (company, credit type) so a balance display can sum a tenant's open
 * holds. Every mutation is a single-key operation, or single-key Lua, or a transaction over one
 * key, so the store is correct on standalone and clustered Redis alike: the unspent-slice refund
 * is delegated to the lease store rather than reaching across to the lease hash inside a
 * multi-key script.
 */
public final class RedisReservationStore implements ReservationStore {

    private static final String RES_KEY_NAMESPACE = "credit-reservation:";
    // Sorted set scoring open reservations by expiresAt so the sweeper can pop expired entries in
    // O(log n). Members encode the full (company, credit, id) tuple.
    private static final String RES_INDEX_KEY = "credit-reservations:byExpiry";
    // Per-(company, credit) index of open holds, one hash of reservationId -> creditsReserved, so
    // reservedCredits reads a tenant's holds with one HGETALL. The hash is also the source of
    // truth for that sum: a field exists exactly while its reservation is open and unrefunded.
    private static final String RES_BYCREDIT_NAMESPACE = "credit-reservations:byCredit:";
    // Buffer past expiresAt before Redis evicts the row, so the sweeper has a window to refund.
    private static final long RES_TTL_GRACE_MS = 30_000L;
    // Page size for the sweeper's ZRANGEBYSCORE. Without a limit, a backlog of expired holds
    // (after a Redis outage or a long pause) would come back as one giant reply on every
    // process's next tick; paging bounds the reply while the per-member ZREM keeps offset 0
    // advancing through the backlog.
    private static final int SWEEP_BATCH_SIZE = 256;
    // Bounds one sweep's work, and guards against an endless loop if ZREM persistently fails.
    // Anything left over is picked up on the next tick.
    private static final int MAX_SWEEP_BATCHES = 16;
    // Absent from Schematic ids and from the UUID reservation id.
    private static final String MEMBER_DELIMITER = "|";

    /**
     * Atomic claim: read the reservation hash and delete it in one step, returning its fields (or
     * nil if it was already gone). Touches a single key. The atomic read-then-delete is what
     * makes consume exactly-once: of two racing callers (a normal track and a sweeper, say) only
     * one gets the fields back and proceeds to refund. The refund to the lease hash is a separate
     * single-key op; a crash in the gap leaves the unspent slice held on the lease until the
     * lease itself expires, never double-refunded.
     */
    private static final String CLAIM_SCRIPT = "\n"
            + "local raw = redis.call('HGETALL', KEYS[1])\n"
            + "if #raw == 0 then return nil end\n"
            + "redis.call('DEL', KEYS[1])\n"
            + "return raw\n";

    private final JedisPooled jedis;
    private final ReservationRefunder leases;
    private final String keyPrefix;
    private final Clock clock;

    public RedisReservationStore(JedisPooled jedis, ReservationRefunder leases) {
        this(jedis, leases, null, null);
    }

    /**
     * @param keyPrefix namespace for reservation keys; pass the same prefix as the lease store
     * @param clock decides the sweep cutoff
     */
    public RedisReservationStore(JedisPooled jedis, ReservationRefunder leases, String keyPrefix, Clock clock) {
        this.jedis = jedis;
        this.leases = leases;
        this.keyPrefix = keyPrefix != null ? keyPrefix : CreditLeaseDefaults.KEY_PREFIX;
        this.clock = clock != null ? clock : Clock.systemUTC();
    }

    @Override
    public void add(Reservation reservation) {
        long expiresMs = reservation.getExpiresAt().toEpochMilli();
        String hashKey = hashKey(reservation.getId());
        Map<String, String> hash = new LinkedHashMap<>();
        hash.put("id", reservation.getId());
        hash.put("leaseId", reservation.getLeaseId());
        hash.put("companyId", reservation.getCompanyId());
        hash.put("creditTypeId", reservation.getCreditTypeId());
        hash.put("eventSubtype", reservation.getEventSubtype());
        hash.put("quantityReserved", CreditAmounts.format(reservation.getQuantityReserved()));
        hash.put("creditsReserved", CreditAmounts.format(reservation.getCreditsReserved()));
        hash.put("consumptionRate", CreditAmounts.format(reservation.getConsumptionRate()));
        hash.put("expiresAt", Long.toString(expiresMs));
        hash.put("evalCtx", encodeEvalCtx(reservation));
        // The hash and its expiry go out as one MULTI/EXEC. Written separately, a crash in the gap
        // leaves a reservation row with no TTL: once the sweeper drops its index entry, nothing
        // points at the row and nothing reaps it, so it sits in Redis for good. Both commands
        // touch the one key, so this is Cluster-safe.
        try (AbstractTransaction txn = jedis.multi()) {
            txn.hset(hashKey, hash);
            txn.pexpireAt(hashKey, expiresMs + RES_TTL_GRACE_MS);
            txn.exec();
        }
        // The two indexes (expiry zset for the sweeper, per-tenant hash for reservedCredits) only
        // depend on the hash existing, so they stay outside the transaction, where their keys are
        // free to hash to other Cluster slots. A partial failure here at worst leaves an
        // un-indexed reservation that the TTL reaps (its slice reclaimed when the lease expires),
        // never a double-spend.
        jedis.zadd(
                indexKey(),
                (double) expiresMs,
                encodeMember(reservation.getCompanyId(), reservation.getCreditTypeId(), reservation.getId()));
        jedis.hset(
                byCreditKey(reservation.getCompanyId(), reservation.getCreditTypeId()),
                reservation.getId(),
                CreditAmounts.format(reservation.getCreditsReserved()));
    }

    @Override
    public Reservation get(String id) {
        Map<String, String> raw = jedis.hgetAll(hashKey(id));
        if (raw == null || raw.get("id") == null) {
            return null;
        }
        return decode(raw);
    }

    @Override
    public Double consume(String id, double creditsConsumed) {
        // Atomically claim (read and delete) the reservation hash. Only one caller wins; a
        // duplicate or racing consume gets nil and reports nothing claimed.
        Object claimed = jedis.eval(CLAIM_SCRIPT, Collections.singletonList(hashKey(id)), Collections.emptyList());
        Map<String, String> raw = decodeFlat(claimed);
        if (raw == null || raw.get("id") == null) {
            return null;
        }

        String companyId = raw.get("companyId");
        String creditTypeId = raw.get("creditTypeId");
        double reserved = CreditAmounts.parse(raw.get("creditsReserved"), 0);

        // Index cleanup, single-key ops. The per-tenant hash loses the slice BEFORE the refund
        // below, so the lease (local remaining plus this hash) never transiently double-counts
        // it. Both are best-effort: a failed cleanup must not abort the settle. The per-tenant
        // field goes first, since the expiry index is what the sweeper would reach a surviving
        // field through: dropping that first and then failing here would inflate reservedCredits
        // forever.
        ignoringFailures(() -> jedis.hdel(byCreditKey(companyId, creditTypeId), id));
        ignoringFailures(() -> jedis.zrem(indexKey(), encodeMember(companyId, creditTypeId, id)));

        double consumed = CreditAmounts.clampConsumption(creditsConsumed, reserved);
        double refund = reserved - consumed;
        if (refund > 0) {
            // Delegated to the lease store, which owns the lease hash, so this cross-key write
            // stays out of a single Lua script. Pinned to the reservation's lease so a hold
            // carved out of an expired lease cannot inflate a successor's balance.
            leases.refund(companyId, creditTypeId, refund, raw.get("leaseId"));
        }
        return consumed;
    }

    @Override
    public double reservedCredits(String companyId, String creditTypeId) {
        Map<String, String> byCredit;
        try {
            byCredit = jedis.hgetAll(byCreditKey(companyId, creditTypeId));
        } catch (RuntimeException e) {
            // A display-path read, not a gate. A Redis blip here reads as nothing reserved rather
            // than as an exception thrown at a caller asking what the balance looks like.
            return 0;
        }
        if (byCredit == null) {
            return 0;
        }
        double total = 0;
        for (String value : byCredit.values()) {
            total += CreditAmounts.parse(value, 0);
        }
        return total;
    }

    @Override
    public int sweepExpired() {
        long cutoff = clock.instant().toEpochMilli();
        int swept = 0;
        // Page through expired members (encoded company|credit|id, scored by expiresAt) rather
        // than fetching them all at once. Each processed member is removed below, so re-reading
        // at offset 0 advances through the backlog.
        for (int batch = 0; batch < MAX_SWEEP_BATCHES; batch++) {
            List<String> expired = jedis.zrangeByScore(indexKey(), 0, (double) cutoff, 0, SWEEP_BATCH_SIZE);
            if (expired == null || expired.isEmpty()) {
                return swept;
            }
            for (String member : expired) {
                String[] parts = decodeMember(member);
                if (parts == null) {
                    // Nothing but add writes members, so this is belt-and-braces: drop it rather
                    // than let it wedge the sweeper.
                    ignoringFailures(() -> jedis.zrem(indexKey(), member));
                    continue;
                }
                Double consumed = consume(parts[2], 0);
                // Always drop the member just read. On the success path consume already removed
                // it, so this is idempotent; it also covers the hash-evicted path below.
                ignoringFailures(() -> jedis.zrem(indexKey(), member));
                if (consumed != null) {
                    swept++;
                    continue;
                }
                // No reservation hash: either a racing track consumed it (and reconciled the
                // byCredit field, making this a no-op) or the hash TTL-evicted before the sweeper
                // reached it, orphaning the field. Reconcile so reservedCredits stops summing an
                // evicted hold. Deliberately no refund: without the hash, exactly-once cannot be
                // arbitrated across racing sweepers, so the slice waits for the lease to expire
                // server-side.
                ignoringFailures(() -> jedis.hdel(byCreditKey(parts[0], parts[1]), parts[2]));
            }
            if (expired.size() < SWEEP_BATCH_SIZE) {
                return swept;
            }
        }
        return swept;
    }

    @Override
    public int count() {
        try {
            return (int) jedis.zcard(indexKey());
        } catch (RuntimeException e) {
            return 0;
        }
    }

    private String hashKey(String id) {
        return keyPrefix + RES_KEY_NAMESPACE + id;
    }

    private String indexKey() {
        return keyPrefix + RES_INDEX_KEY;
    }

    private String byCreditKey(String companyId, String creditTypeId) {
        return keyPrefix + RES_BYCREDIT_NAMESPACE + companyId + ":" + creditTypeId;
    }

    /**
     * Packs the whole tuple into an expiry-index member. The sweeper needs company and credit to
     * clean the per-tenant hash even after the reservation hash has TTL-evicted, at which point
     * the claim returns nil and cannot report them; otherwise the orphaned field would inflate
     * reservedCredits forever.
     */
    private static String encodeMember(String companyId, String creditTypeId, String id) {
        return companyId + MEMBER_DELIMITER + creditTypeId + MEMBER_DELIMITER + id;
    }

    private static String[] decodeMember(String member) {
        String[] parts = member.split("\\|", -1);
        return parts.length == 3 ? parts : null;
    }

    private static String encodeEvalCtx(Reservation reservation) {
        Map<String, Map<String, String>> ctx = new LinkedHashMap<>();
        if (reservation.getCompany() != null) {
            ctx.put("company", reservation.getCompany());
        }
        if (reservation.getUser() != null) {
            ctx.put("user", reservation.getUser());
        }
        try {
            return ObjectMappers.JSON_MAPPER.writeValueAsString(ctx);
        } catch (Exception e) {
            return "{}";
        }
    }

    /** Decodes the flat [field, value, ...] reply the claim script returns. */
    private static Map<String, String> decodeFlat(Object raw) {
        if (!(raw instanceof List)) {
            return null;
        }
        List<?> flat = (List<?>) raw;
        if (flat.isEmpty()) {
            return null;
        }
        Map<String, String> out = new LinkedHashMap<>();
        for (int i = 0; i + 1 < flat.size(); i += 2) {
            out.put(String.valueOf(flat.get(i)), String.valueOf(flat.get(i + 1)));
        }
        return out;
    }

    private static Reservation decode(Map<String, String> raw) {
        // Read field by field rather than binding the whole object to a map of string maps. The
        // SDKs write the check's request body here, which carries more than company and user, so
        // one sibling adding a field would otherwise fail the whole bind and silently drop the
        // entity keys a recovered hold needs to bill its usage.
        JsonNode ctx = null;
        String encoded = raw.get("evalCtx");
        if (encoded != null && !encoded.isEmpty()) {
            try {
                ctx = ObjectMappers.JSON_MAPPER.readTree(encoded);
            } catch (Exception e) {
                ctx = null;
            }
        }
        return new Reservation(
                raw.get("id"),
                raw.get("leaseId"),
                CreditLeaseMode.CLIENT,
                raw.get("companyId"),
                raw.get("creditTypeId"),
                raw.get("eventSubtype"),
                CreditAmounts.parse(raw.get("quantityReserved"), 0),
                CreditAmounts.parse(raw.get("creditsReserved"), 0),
                CreditAmounts.parse(raw.get("consumptionRate"), 0),
                Instant.ofEpochMilli((long) CreditAmounts.parse(raw.get("expiresAt"), 0)),
                entityKeys(ctx, "company"),
                entityKeys(ctx, "user"));
    }

    /** One entity's keys out of a decoded eval context, null when it carries none. */
    private static Map<String, String> entityKeys(JsonNode ctx, String field) {
        if (ctx == null) {
            return null;
        }
        JsonNode node = ctx.get(field);
        if (node == null || !node.isObject()) {
            return null;
        }
        Map<String, String> keys = new LinkedHashMap<>();
        Iterator<Map.Entry<String, JsonNode>> fields = node.fields();
        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> entry = fields.next();
            if (entry.getValue().isTextual()) {
                keys.put(entry.getKey(), entry.getValue().asText());
            }
        }
        return keys;
    }

    private static void ignoringFailures(Runnable step) {
        try {
            step.run();
        } catch (RuntimeException e) {
            // Index bookkeeping only: a failure here is reconciled by the next sweep, and must
            // not abort the settle that is already claimed.
        }
    }
}
