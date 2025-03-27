package com.schematic.webhook;

/**
 * Exception thrown when webhook signature verification fails.
 */
public class WebhookSignatureException extends RuntimeException {
    
    /**
     * Constructs a new WebhookSignatureException with the specified detail message.
     *
     * @param message the detail message
     */
    public WebhookSignatureException(String message) {
        super(message);
    }
    
    /**
     * Constructs a new WebhookSignatureException with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause the cause
     */
    public WebhookSignatureException(String message, Throwable cause) {
        super(message, cause);
    }
}