package com.schematic.api.datastream;

/**
 * Exception thrown when a DataStream operation fails.
 */
public class DataStreamException extends RuntimeException {

    public DataStreamException(String message) {
        super(message);
    }

    public DataStreamException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Thrown when the keys submitted for an entity lookup match multiple
     * distinct entities in the cache — an invalid state that should surface as
     * a flag check with reason "key conflict" rather than an arbitrary match.
     */
    public static class KeyConflict extends DataStreamException {
        public KeyConflict(String message) {
            super(message);
        }
    }
}
