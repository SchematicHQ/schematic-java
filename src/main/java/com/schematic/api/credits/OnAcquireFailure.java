package com.schematic.api.credits;

/**
 * What a check does when it cannot gate: the wire call failed, the store is unreachable, or the
 * lease is exhausted.
 */
public enum OnAcquireFailure {
    /**
     * Err on the side of assuming the credits are there. The rules engine still evaluates the
     * flag, with the credit balance substituted to an effectively unlimited value, so plan
     * targeting, overrides and every non-credit condition still apply and only the credit gate is
     * bypassed. No reservation is issued. In server mode there is no local engine to re-run, so
     * the check returns the caller's default value instead.
     */
    FAIL_OPEN,
    /** Deny, so the caller blocks the action. The default. */
    FAIL_CLOSED
}
