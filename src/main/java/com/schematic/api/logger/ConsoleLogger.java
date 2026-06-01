package com.schematic.api.logger;

/**
 * Default {@link SchematicLogger} implementation that writes to standard out, filtering messages by
 * a configured {@link LogLevel}.
 *
 * <p>Defaults to {@link LogLevel#WARN}: {@code debug} and {@code info} are suppressed unless a more
 * verbose level is requested, so production consumers aren't flooded with diagnostics they never
 * asked for. Raise the level (e.g. {@link LogLevel#DEBUG}) for development.
 */
public class ConsoleLogger implements SchematicLogger {

    private final LogLevel level;

    /** Creates a logger at the default {@link LogLevel#WARN} level. */
    public ConsoleLogger() {
        this(LogLevel.WARN);
    }

    /** Creates a logger that emits messages at {@code level} or more severe. Null defaults to WARN. */
    public ConsoleLogger(LogLevel level) {
        this.level = level != null ? level : LogLevel.WARN;
    }

    @Override
    public void error(String message, Object... args) {
        log(LogLevel.ERROR, message, args);
    }

    @Override
    public void warn(String message, Object... args) {
        log(LogLevel.WARN, message, args);
    }

    @Override
    public void info(String message, Object... args) {
        log(LogLevel.INFO, message, args);
    }

    @Override
    public void debug(String message, Object... args) {
        log(LogLevel.DEBUG, message, args);
    }

    private void log(LogLevel messageLevel, String message, Object... args) {
        if (!level.allows(messageLevel)) {
            return;
        }
        // Only run through String.format when args are supplied, so a literal '%' in an
        // arg-less message doesn't blow up with a format exception.
        String formatted = (args == null || args.length == 0) ? message : String.format(message, args);
        System.out.println("[" + messageLevel.name() + "] " + formatted);
    }
}
