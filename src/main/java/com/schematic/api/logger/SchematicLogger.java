package com.schematic.api.logger;

public interface SchematicLogger {
    void error(String message, Object... args);
    void warn(String message, Object... args);
    void info(String message, Object... args);
    void debug(String message, Object... args);
}