package com.schematic.api.logger;

public class ConsoleLogger implements SchematicLogger {
    @Override
    public void error(String message, Object... args) {
        System.out.println("[ERROR] " + String.format(message, args));
    }

    @Override
    public void warn(String message, Object... args) {
        System.out.println("[WARN] " + String.format(message, args));
    }

    @Override
    public void info(String message, Object... args) {
        System.out.println("[INFO] " + String.format(message, args));
    }

    @Override
    public void debug(String message, Object... args) {
        System.out.println("[DEBUG] " + String.format(message, args));
    }
}
