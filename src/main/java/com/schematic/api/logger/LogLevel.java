package com.schematic.api.logger;

/**
 * Severity levels for {@link SchematicLogger}, ordered from most to least verbose.
 *
 * <p>A logger configured at a given level emits messages at that level and every more severe level,
 * suppressing the rest. For example {@link #WARN} emits {@code warn} and {@code error} but drops
 * {@code info} and {@code debug}.
 */
public enum LogLevel {
    DEBUG(0),
    INFO(1),
    WARN(2),
    ERROR(3);

    private final int severity;

    LogLevel(int severity) {
        this.severity = severity;
    }

    /** Whether a message logged at {@code messageLevel} should be emitted by a logger at this level. */
    boolean allows(LogLevel messageLevel) {
        return messageLevel.severity >= this.severity;
    }
}
