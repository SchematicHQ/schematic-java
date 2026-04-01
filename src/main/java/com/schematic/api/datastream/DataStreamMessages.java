package com.schematic.api.datastream;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.Map;

/**
 * Message types for the DataStream WebSocket protocol.
 */
public final class DataStreamMessages {

    private DataStreamMessages() {}

    /** Actions that can be sent to the datastream server. */
    public enum Action {
        @JsonProperty("start")
        START,
        @JsonProperty("stop")
        STOP
    }

    /** Entity types used in datastream communication. */
    public enum EntityType {
        @JsonProperty("rulesengine.Company")
        COMPANY("rulesengine.Company"),
        @JsonProperty("rulesengine.Companies")
        COMPANIES("rulesengine.Companies"),
        @JsonProperty("rulesengine.Flag")
        FLAG("rulesengine.Flag"),
        @JsonProperty("rulesengine.Flags")
        FLAGS("rulesengine.Flags"),
        @JsonProperty("rulesengine.User")
        USER("rulesengine.User"),
        @JsonProperty("rulesengine.Users")
        USERS("rulesengine.Users");

        private final String value;

        EntityType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public static EntityType fromString(String value) {
            for (EntityType type : values()) {
                if (type.value.equals(value)) {
                    return type;
                }
            }
            return null;
        }
    }

    /** Message types received from the datastream server. */
    public enum MessageType {
        @JsonProperty("full")
        FULL("full"),
        @JsonProperty("partial")
        PARTIAL("partial"),
        @JsonProperty("delete")
        DELETE("delete"),
        @JsonProperty("error")
        ERROR("error"),
        @JsonProperty("unknown")
        UNKNOWN("unknown");

        private final String value;

        MessageType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public static MessageType fromString(String value) {
            for (MessageType type : values()) {
                if (type.value.equals(value)) {
                    return type;
                }
            }
            return UNKNOWN;
        }
    }

    /** Request message sent to the datastream server. */
    public static class DataStreamReq {
        @JsonProperty("action")
        private final Action action;

        @JsonProperty("entity_type")
        private final String entityType;

        @JsonProperty("keys")
        private final Map<String, String> keys;

        public DataStreamReq(Action action, EntityType entityType, Map<String, String> keys) {
            this.action = action;
            this.entityType = entityType.getValue();
            this.keys = keys;
        }

        public Action getAction() {
            return action;
        }

        public String getEntityType() {
            return entityType;
        }

        public Map<String, String> getKeys() {
            return keys;
        }
    }

    /** Wrapper for request messages (matches Go's DataStreamBaseReq). */
    public static class DataStreamBaseReq {
        @JsonProperty("data")
        private final DataStreamReq data;

        public DataStreamBaseReq(DataStreamReq data) {
            this.data = data;
        }

        public DataStreamReq getData() {
            return data;
        }
    }

    /** Response message received from the datastream server. */
    public static class DataStreamResp {
        @JsonProperty("data")
        private JsonNode data;

        @JsonProperty("entity_id")
        private String entityId;

        @JsonProperty("entity_type")
        private String entityType;

        @JsonProperty("message_type")
        private String messageType;

        public DataStreamResp() {}

        public JsonNode getData() {
            return data;
        }

        public String getEntityId() {
            return entityId;
        }

        public String getEntityType() {
            return entityType;
        }

        public EntityType getEntityTypeEnum() {
            return EntityType.fromString(entityType);
        }

        public MessageType getMessageTypeEnum() {
            return MessageType.fromString(messageType);
        }

        public String getMessageType() {
            return messageType;
        }
    }

    /** Error message from the datastream server. */
    public static class DataStreamError {
        @JsonProperty("error")
        private String error;

        @JsonProperty("keys")
        private Map<String, String> keys;

        @JsonProperty("entity_type")
        private String entityType;

        public DataStreamError() {}

        public String getError() {
            return error;
        }

        public Map<String, String> getKeys() {
            return keys;
        }

        public String getEntityType() {
            return entityType;
        }
    }
}
