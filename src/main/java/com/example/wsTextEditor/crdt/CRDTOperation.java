package com.example.wsTextEditor.crdt;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CRDTOperation {
    public enum Type {
        INSERT, DELETE, UPDATE
    }

    private Type type;
    private CRDTCharacter character;
    private String sessionId;
    private String siteId;
    private long timestamp;

    public CRDTOperation() {}

    @JsonCreator
    public CRDTOperation(@JsonProperty("type") Type type,
                         @JsonProperty("character") CRDTCharacter character,
                         @JsonProperty("sessionId") String sessionId,
                         @JsonProperty("siteId") String siteId,
                         @JsonProperty("timestamp") long timestamp) {
        this.type = type;
        this.character = character;
        this.sessionId = sessionId;
        this.siteId = siteId;
        this.timestamp = timestamp;
    }

    // Getters
    public Type getType() { return type; }
    public CRDTCharacter getCharacter() { return character; }
    public String getSessionId() { return sessionId; }
    public String getSiteId() { return siteId; }
    public long getTimestamp() { return timestamp; }

    // Setters
    public void setType(Type type) { this.type = type; }
    public void setCharacter(CRDTCharacter character) { this.character = character; }
    public void setSessionId(String sessionId) { this.sessionId = sessionId; }
    public void setSiteId(String siteId) { this.siteId = siteId; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }

    @Override
    public String toString() {
        return "CRDTOperation{" +
                "type=" + type +
                ", character=" + character +
                ", sessionId='" + sessionId + '\'' +
                ", siteId='" + siteId + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
