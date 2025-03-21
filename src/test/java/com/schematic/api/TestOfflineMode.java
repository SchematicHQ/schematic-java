package com.schematic.api;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class TestOfflineMode {

    @Test
    public void testOfflineModeDoesNotMakeRequests() {
        // Create a client in offline mode
        Schematic schematic = Schematic.builder()
                .apiKey("fake-api-key")
                .offline(true)
                .build();

        try {
            // Setup test data
            Map<String, String> company = new HashMap<>();
            company.put("id", "test-company");
            Map<String, String> user = new HashMap<>();
            user.put("id", "test-user");
            Map<String, Boolean> flagDefaults = new HashMap<>();
            flagDefaults.put("test-flag", true);

            // Test flag check - should return the default value
            boolean flagValue = schematic.checkFlag("test-flag", company, user);
            assertFalse(flagValue, "Flag check should return default value (false) when in offline mode");

            // Create a new client with flag defaults
            Schematic schematicWithDefaults = Schematic.builder()
                    .apiKey("fake-api-key")
                    .offline(true)
                    .flagDefaults(flagDefaults)
                    .build();

            // Test flag check with defaults - should return the configured default value
            flagValue = schematicWithDefaults.checkFlag("test-flag", company, user);
            assertTrue(flagValue, "Flag check should return configured default value (true) when in offline mode");

            // Test identify and track don't throw exceptions
            try {
                Map<String, String> keys = new HashMap<>();
                keys.put("id", "test-user");
                Map<String, Object> traits = new HashMap<>();
                traits.put("name", "Test User");

                schematic.identify(keys, null, "Test User", traits);
                schematic.track("test-event", company, user, traits);
            } catch (Exception e) {
                fail("Identify or track should not throw exceptions in offline mode: " + e.getMessage());
            }
        } finally {
            // Clean up resources
            schematic.close();
        }
    }
}
