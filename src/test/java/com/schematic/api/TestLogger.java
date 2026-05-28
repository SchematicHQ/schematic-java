package com.schematic.api;

import static org.junit.jupiter.api.Assertions.*;

import com.schematic.api.logger.ConsoleLogger;
import com.schematic.api.logger.LogLevel;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LoggerTest {
    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;

    @BeforeEach
    void setUp() {
        outputStream = new ByteArrayOutputStream();
        originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    private String output() {
        return outputStream.toString().trim();
    }

    // --- Emission + formatting (verbose logger so every level is allowed) ---

    @Test
    void testErrorLogsMessage() {
        new ConsoleLogger(LogLevel.DEBUG).error("This is an error message");
        assertTrue(output().contains("[ERROR]"));
        assertTrue(output().contains("This is an error message"));
    }

    @Test
    void testWarnLogsMessage() {
        new ConsoleLogger(LogLevel.DEBUG).warn("This is a warning message");
        assertTrue(output().contains("[WARN]"));
        assertTrue(output().contains("This is a warning message"));
    }

    @Test
    void testInfoLogsMessage() {
        new ConsoleLogger(LogLevel.DEBUG).info("This is an info message");
        assertTrue(output().contains("[INFO]"));
        assertTrue(output().contains("This is an info message"));
    }

    @Test
    void testDebugLogsMessage() {
        new ConsoleLogger(LogLevel.DEBUG).debug("This is a debug message");
        assertTrue(output().contains("[DEBUG]"));
        assertTrue(output().contains("This is a debug message"));
    }

    @Test
    void testFormatsMessageWithArgs() {
        new ConsoleLogger(LogLevel.DEBUG).debug("Debug %s", "123");
        assertTrue(output().contains("[DEBUG]"));
        assertTrue(output().contains("Debug 123"));
    }

    @Test
    void testArglessMessageWithPercentDoesNotThrow() {
        // A literal '%' with no args must not trigger a format exception.
        assertDoesNotThrow(() -> new ConsoleLogger(LogLevel.DEBUG).warn("100% complete"));
        assertTrue(output().contains("100% complete"));
    }

    // --- Level filtering (the conformance requirement) ---

    @Test
    void testDefaultLevelIsWarnAndSuppressesInfoAndDebug() {
        ConsoleLogger logger = new ConsoleLogger(); // default WARN
        logger.debug("dbg");
        logger.info("inf");
        logger.warn("wrn");
        logger.error("err");

        String out = output();
        assertFalse(out.contains("[DEBUG]"), "debug should be suppressed at WARN");
        assertFalse(out.contains("[INFO]"), "info should be suppressed at WARN");
        assertTrue(out.contains("[WARN]"));
        assertTrue(out.contains("[ERROR]"));
    }

    @Test
    void testNullLevelDefaultsToWarn() {
        ConsoleLogger logger = new ConsoleLogger(null);
        logger.info("inf");
        logger.warn("wrn");

        String out = output();
        assertFalse(out.contains("[INFO]"));
        assertTrue(out.contains("[WARN]"));
    }

    @Test
    void testDebugLevelEmitsEveryLevel() {
        ConsoleLogger logger = new ConsoleLogger(LogLevel.DEBUG);
        logger.debug("dbg");
        logger.info("inf");
        logger.warn("wrn");
        logger.error("err");

        String out = output();
        assertTrue(out.contains("[DEBUG]"));
        assertTrue(out.contains("[INFO]"));
        assertTrue(out.contains("[WARN]"));
        assertTrue(out.contains("[ERROR]"));
    }

    @Test
    void testErrorLevelSuppressesEverythingBelowError() {
        ConsoleLogger logger = new ConsoleLogger(LogLevel.ERROR);
        logger.debug("dbg");
        logger.info("inf");
        logger.warn("wrn");
        logger.error("err");

        String out = output();
        assertFalse(out.contains("[DEBUG]"));
        assertFalse(out.contains("[INFO]"));
        assertFalse(out.contains("[WARN]"));
        assertTrue(out.contains("[ERROR]"));
    }
}
