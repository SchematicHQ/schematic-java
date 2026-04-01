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
}
