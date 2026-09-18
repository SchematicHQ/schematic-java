package com.schematic.api.credits;

/**
 * Where a credit hold lives for a {@code check()} that passes usage.
 */
public enum CreditLeaseMode {
    /**
     * Local leases over DataStream: the SDK draws a tranche of credits up front and carves
     * reservations out of it locally. Requires DataStream, and a shared Redis backend to gate
     * across processes.
     */
    CLIENT,
    /**
     * One check-and-reserve API call per check: the server evaluates the flag and takes the hold
     * in the same round trip. No DataStream, no Redis, no local stores.
     */
    SERVER,
    /** Client mode when DataStream is enabled, server mode otherwise. The default. */
    AUTO
}
