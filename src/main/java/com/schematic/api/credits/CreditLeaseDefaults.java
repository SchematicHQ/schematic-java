package com.schematic.api.credits;

import java.time.Duration;

/**
 * Defaults shared by {@link CreditLeaseConfig}, {@link CreditLeaseManager} and the check flows.
 *
 * <p>The figures match the other Schematic SDKs: the semantics are pinned by the
 * language-agnostic vectors in {@code conformance/}, and a fleet mixing SDKs shares one Redis.
 */
public final class CreditLeaseDefaults {

    /** Lease lifetime requested at acquire and extend: {@code expiresAt = now + duration}. */
    public static final Duration LEASE_DURATION = Duration.ofMinutes(5);

    /**
     * Reservation lifetime, and the sweep deadline. Size it above the longest expected gap
     * between a check and its settle: a settle arriving after the TTL still bills the server but
     * no longer re-debits the lease, so the local balance reads high until the lease rolls over.
     */
    public static final Duration RESERVATION_TTL = Duration.ofSeconds(60);

    /**
     * The furthest out the API will hold credits, so a larger server-mode reservation TTL would
     * fail every check.
     */
    public static final Duration MAX_RESERVATION_TTL = Duration.ofHours(1);

    /**
     * Held back from {@link #MAX_RESERVATION_TTL} because the API measures that hour against its
     * own clock while the SDK computes {@code expiresAt} against the caller's: a client running
     * ahead would otherwise be rejected at exactly the cap.
     */
    public static final Duration RESERVATION_TTL_SKEW_ALLOWANCE = Duration.ofSeconds(60);

    /** Credits requested per acquire, and the minimum extend tranche. */
    public static final double LEASE_SIZE = 10_000d;

    /** Remaining/granted ratio at or below which a background extend is kicked off. */
    public static final double LOW_WATER_MARK = 0.25d;

    /** Cadence of the expired-reservation sweep. */
    public static final Duration SWEEP_INTERVAL = Duration.ofSeconds(1);

    /**
     * How long a prewarm waits for a freshly identified company to surface in the datastream
     * cache before giving up. Long enough to cover the buffer flush, server ingest and datastream
     * push for a brand-new company; short enough that a misconfigured caller does not hang.
     */
    public static final Duration PREWARM_RESOLVE_TIMEOUT = Duration.ofSeconds(5);

    /** Gap between prewarm resolve attempts. */
    public static final Duration PREWARM_POLL_INTERVAL = Duration.ofMillis(100);

    /**
     * How long {@code close()} waits for in-flight lease work to land before giving up on it.
     * Bounded on purpose: a shutdown that hangs is worse than a hold the server expires at
     * {@link #LEASE_DURATION}.
     */
    public static final Duration SHUTDOWN_DRAIN_TIMEOUT = Duration.ofSeconds(5);

    /** Namespace for every lease and reservation key. */
    public static final String KEY_PREFIX = "schematic:";

    /**
     * The balance a fail-open evaluation substitutes for the metered credit: large enough that
     * the credit gate always passes, and still exact as a JSON number, so the engine reads back
     * what the SDK sent. This is Node's {@code Number.MAX_SAFE_INTEGER}, which the conformance
     * vectors name {@code max_safe_integer}.
     */
    public static final double FAIL_OPEN_BALANCE = 9007199254740991d;

    private CreditLeaseDefaults() {}
}
