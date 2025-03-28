package com.schematic.webhook;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Utilities for verifying the signatures of Schematic webhooks.
 * <p>
 * Schematic signs webhook payloads using HMAC-SHA256. This class provides methods
 * to verify these signatures.
 */
public class WebhookVerifier {

    /**
     * Header containing the webhook signature.
     */
    public static final String WEBHOOK_SIGNATURE_HEADER = "X-Schematic-Webhook-Signature";

    /**
     * Header containing the webhook timestamp.
     */
    public static final String WEBHOOK_TIMESTAMP_HEADER = "X-Schematic-Webhook-Timestamp";

    private static final String HMAC_SHA256 = "HmacSHA256";

    /**
     * Verifies the signature of a webhook request.
     *
     * @param body The request body as a string
     * @param headers Map of HTTP headers
     * @param secret The webhook secret
     * @throws WebhookSignatureException if the signature is invalid
     */
    public static void verifyWebhookSignature(String body, Map<String, String> headers, String secret)
            throws WebhookSignatureException {

        // Extract signature and timestamp headers
        String signature = headers.get(WEBHOOK_SIGNATURE_HEADER);
        String timestamp = headers.get(WEBHOOK_TIMESTAMP_HEADER);

        // Verify signature
        verifySignature(body, signature, timestamp, secret);
    }

    /**
     * Verifies the signature of a webhook payload.
     *
     * @param body The webhook payload
     * @param signature The signature header value
     * @param timestamp The timestamp header value
     * @param secret The webhook secret
     * @throws WebhookSignatureException if the signature is invalid
     */
    public static void verifySignature(String body, String signature, String timestamp, String secret)
            throws WebhookSignatureException {

        if (signature == null || signature.isEmpty()) {
            throw new WebhookSignatureException("Missing webhook signature");
        }

        if (timestamp == null || timestamp.isEmpty()) {
            throw new WebhookSignatureException("Missing webhook timestamp");
        }

        // Compute expected signature
        String expectedSignature = computeHexSignature(body, timestamp, secret);

        // Compare signatures using constant-time comparison
        if (!constantTimeEquals(hexToBytes(expectedSignature), hexToBytes(signature))) {
            throw new WebhookSignatureException("Invalid signature");
        }
    }

    /**
     * Computes the hex-encoded HMAC-SHA256 signature for a webhook payload.
     *
     * @param body The webhook payload
     * @param timestamp The timestamp
     * @param secret The webhook secret
     * @return The hex-encoded signature
     * @throws WebhookSignatureException if an error occurs during signature computation
     */
    public static String computeHexSignature(String body, String timestamp, String secret)
            throws WebhookSignatureException {

        byte[] signature = computeSignature(body, timestamp, secret);
        return bytesToHex(signature);
    }

    /**
     * Computes the HMAC-SHA256 signature for a webhook payload.
     *
     * @param body The webhook payload
     * @param timestamp The timestamp
     * @param secret The webhook secret
     * @return The signature bytes
     * @throws WebhookSignatureException if an error occurs during signature computation
     */
    public static byte[] computeSignature(String body, String timestamp, String secret)
            throws WebhookSignatureException {

        try {
            // Create message by concatenating body and timestamp
            String message = body + "+" + timestamp;
            byte[] messageBytes = message.getBytes(StandardCharsets.UTF_8);

            // Create HMAC-SHA256 instance
            SecretKeySpec keySpec = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), HMAC_SHA256);
            Mac mac = Mac.getInstance(HMAC_SHA256);
            mac.init(keySpec);

            // Compute and return signature
            return mac.doFinal(messageBytes);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new WebhookSignatureException("Error computing signature", e);
        }
    }

    /**
     * Converts a byte array to a hex string.
     *
     * @param bytes The byte array
     * @return The hex string
     */
    private static String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }

    /**
     * Converts a hex string to a byte array.
     *
     * @param hex The hex string
     * @return The byte array
     * @throws WebhookSignatureException if the hex string is invalid
     */
    private static byte[] hexToBytes(String hex) throws WebhookSignatureException {
        try {
            int len = hex.length();
            byte[] data = new byte[len / 2];
            for (int i = 0; i < len; i += 2) {
                data[i / 2] =
                        (byte) ((Character.digit(hex.charAt(i), 16) << 4) + Character.digit(hex.charAt(i + 1), 16));
            }
            return data;
        } catch (Exception e) {
            throw new WebhookSignatureException("Invalid signature format", e);
        }
    }

    /**
     * Compares two byte arrays in constant time to prevent timing attacks.
     *
     * @param a First byte array
     * @param b Second byte array
     * @return true if the arrays are equal, false otherwise
     */
    private static boolean constantTimeEquals(byte[] a, byte[] b) {
        if (a.length != b.length) {
            return false;
        }

        int result = 0;
        for (int i = 0; i < a.length; i++) {
            result |= a[i] ^ b[i];
        }
        return result == 0;
    }
}
