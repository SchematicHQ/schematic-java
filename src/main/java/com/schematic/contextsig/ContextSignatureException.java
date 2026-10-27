package com.schematic.contextsig;

/**
 * Exception thrown when a context can't be signed.
 */
public class ContextSignatureException extends RuntimeException {

    /**
     * Constructs a new ContextSignatureException with the specified detail message.
     *
     * @param message the detail message
     */
    public ContextSignatureException(String message) {
        super(message);
    }

    /**
     * Constructs a new ContextSignatureException with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause the cause
     */
    public ContextSignatureException(String message, Throwable cause) {
        super(message, cause);
    }
}
