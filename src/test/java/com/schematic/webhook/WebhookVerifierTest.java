package com.schematic.webhook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class WebhookVerifierTest {

    private static final String WEBHOOK_SECRET = "test_secret";
    private static final String PAYLOAD = "{\"event\":\"test_event\"}";
    private static final String TIMESTAMP = "1234567890";

    private String validSignature;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        validSignature = WebhookVerifier.computeHexSignature(PAYLOAD, TIMESTAMP, WEBHOOK_SECRET);
    }

    @Test
    void testComputeHexSignature() {
        String signature = WebhookVerifier.computeHexSignature(PAYLOAD, TIMESTAMP, WEBHOOK_SECRET);
        assertNotNull(signature);
        assertFalse(signature.isEmpty());
    }

    @Test
    void testVerifySignature_ValidSignature() {
        // Should not throw an exception
        WebhookVerifier.verifySignature(PAYLOAD, validSignature, TIMESTAMP, WEBHOOK_SECRET);
    }

    @Test
    void testVerifySignature_InvalidSignature() {
        // Invalid signature
        assertThrows(WebhookSignatureException.class, () -> 
                WebhookVerifier.verifySignature(PAYLOAD, "invalid", TIMESTAMP, WEBHOOK_SECRET));
    }

    @Test
    void testVerifySignature_MissingSignature() {
        // Missing signature
        assertThrows(WebhookSignatureException.class, () -> 
                WebhookVerifier.verifySignature(PAYLOAD, null, TIMESTAMP, WEBHOOK_SECRET));
        
        assertThrows(WebhookSignatureException.class, () -> 
                WebhookVerifier.verifySignature(PAYLOAD, "", TIMESTAMP, WEBHOOK_SECRET));
    }

    @Test
    void testVerifySignature_MissingTimestamp() {
        // Missing timestamp
        assertThrows(WebhookSignatureException.class, () -> 
                WebhookVerifier.verifySignature(PAYLOAD, validSignature, null, WEBHOOK_SECRET));
        
        assertThrows(WebhookSignatureException.class, () -> 
                WebhookVerifier.verifySignature(PAYLOAD, validSignature, "", WEBHOOK_SECRET));
    }

    @Test
    void testVerifySignature_TamperedPayload() {
        // Tampered payload
        String tamperedPayload = "{\"event\":\"tampered_event\"}";
        assertThrows(WebhookSignatureException.class, () -> 
                WebhookVerifier.verifySignature(tamperedPayload, validSignature, TIMESTAMP, WEBHOOK_SECRET));
    }

    @Test
    void testVerifyWebhookSignature_ValidSignature() {
        // Setup headers
        Map<String, String> headers = new HashMap<>();
        headers.put(WebhookVerifier.WEBHOOK_SIGNATURE_HEADER, validSignature);
        headers.put(WebhookVerifier.WEBHOOK_TIMESTAMP_HEADER, TIMESTAMP);
        
        // Should not throw an exception
        WebhookVerifier.verifyWebhookSignature(PAYLOAD, headers, WEBHOOK_SECRET);
    }

    @Test
    void testVerifyWebhookSignature_InvalidSignature() {
        // Setup headers with invalid signature
        Map<String, String> headers = new HashMap<>();
        headers.put(WebhookVerifier.WEBHOOK_SIGNATURE_HEADER, "invalid");
        headers.put(WebhookVerifier.WEBHOOK_TIMESTAMP_HEADER, TIMESTAMP);
        
        // Should throw an exception
        assertThrows(WebhookSignatureException.class, () -> 
                WebhookVerifier.verifyWebhookSignature(PAYLOAD, headers, WEBHOOK_SECRET));
    }
}