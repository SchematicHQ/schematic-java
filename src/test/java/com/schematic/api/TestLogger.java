package com.schematic.api;

import com.schematic.api.logger.ConsoleLogger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.*;

class LoggerTest {
    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;
    private ConsoleLogger logger;

    @BeforeEach
    void setUp() {
        outputStream = new ByteArrayOutputStream();
        originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
        logger = new ConsoleLogger();
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void testErrorLogsMessage() {
        String message = "This is an error message";
        logger.error(message);

        String output = outputStream.toString().trim();
        assertTrue(output.contains("[ERROR]"));
        assertTrue(output.contains(message));
    }

    @Test
    void testWarnLogsMessage() {
        String message = "This is a warning message";
        logger.warn(message);

        String output = outputStream.toString().trim();
        assertTrue(output.contains("[WARN]"));
        assertTrue(output.contains(message));
    }

    @Test
    void testInfoLogsMessage() {
        String message = "This is an info message";
        logger.info(message);

        String output = outputStream.toString().trim();
        assertTrue(output.contains("[INFO]"));
        assertTrue(output.contains(message));
    }

    @Test
    void testDebugLogsMessage() {
        String message = "This is a debug message";
        logger.debug(message);

        String output = outputStream.toString().trim();
        assertTrue(output.contains("[DEBUG]"));
        assertTrue(output.contains(message));
    }

    @Test
    void testErrorFormatsMessageWithArgs() {
        logger.error("Error %s", "123");

        String output = outputStream.toString().trim();
        assertTrue(output.contains("[ERROR]"));
        assertTrue(output.contains("Error 123"));
    }

    @Test
    void testWarnFormatsMessageWithArgs() {
        logger.warn("Warning %s", "123");

        String output = outputStream.toString().trim();
        assertTrue(output.contains("[WARN]"));
        assertTrue(output.contains("Warning 123"));
    }

    @Test
    void testInfoFormatsMessageWithArgs() {
        logger.info("Info %s", "123");

        String output = outputStream.toString().trim();
        assertTrue(output.contains("[INFO]"));
        assertTrue(output.contains("Info 123"));
    }

    @Test
    void testDebugFormatsMessageWithArgs() {
        logger.debug("Debug %s", "123");

        String output = outputStream.toString().trim();
        assertTrue(output.contains("[DEBUG]"));
        assertTrue(output.contains("Debug 123"));
    }
}
