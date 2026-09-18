package com.schematic.api.credits;

import java.math.BigDecimal;
import java.time.Duration;

/** Credit-amount helpers shared by the stores and the check flows. */
public final class CreditAmounts {

    /**
     * Whether a caller-supplied quantity can size a credit hold. NaN is the dangerous case: it
     * slips through every numeric comparison, and a NaN balance would approve every later reserve
     * on a possibly shared lease.
     */
    public static boolean isValidQuantity(double value) {
        return !Double.isNaN(value) && !Double.isInfinite(value) && value >= 0;
    }

    /**
     * Formats an amount for a Redis hash field. Plain decimal, shortest exact form, so a fleet of
     * SDKs reading the same hash sees the same figures and no fractional rate is truncated.
     */
    public static String format(double value) {
        if (value == Math.rint(value) && !Double.isInfinite(value) && Math.abs(value) < 1e15) {
            return Long.toString((long) value);
        }
        return BigDecimal.valueOf(value).stripTrailingZeros().toPlainString();
    }

    /** Parses an amount written by {@link #format}, or by another SDK's equivalent. */
    public static double parse(String raw, double fallback) {
        if (raw == null || raw.isEmpty()) {
            return fallback;
        }
        try {
            return Double.parseDouble(raw);
        } catch (NumberFormatException e) {
            return fallback;
        }
    }

    /** Keeps local bookkeeping from ever debiting a lease past the hold it took. */
    public static double clampConsumption(double creditsConsumed, double creditsReserved) {
        if (Double.isNaN(creditsConsumed) || creditsConsumed < 0) {
            return 0;
        }
        return Math.min(creditsConsumed, creditsReserved);
    }

    /**
     * A duration as the milliseconds an int-typed request option takes, clamped at both ends.
     * Casting alone wraps anything past the int range into a negative, which the transport reads
     * as an instant timeout: the opposite of the long wait the caller asked for. A caller passing
     * a negative or sub-millisecond duration lands on the same value, so the floor keeps it a
     * timeout the transport can express rather than one that fails every call before it is sent.
     */
    public static int millisAsInt(Duration timeout) {
        long millis = timeout.toMillis();
        if (millis > Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        }
        return millis < 1 ? 1 : (int) millis;
    }

    private CreditAmounts() {}
}
