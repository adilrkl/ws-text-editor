package com.example.wsTextEditor.crdt;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;

public class CRDTCharacter implements Comparable<CRDTCharacter> {
    private String id;
    private char content;
    private long timestamp;
    private String siteId;
    private String afterId;
    private boolean deleted;

    // No-arg constructor for Jackson
    public CRDTCharacter() {}

    // Main constructor used by the application logic
    public CRDTCharacter(char content, long timestamp, String siteId, String afterId) {
        this.content = content;
        this.timestamp = timestamp;
        this.siteId = siteId;
        this.afterId = afterId;
        this.id = timestamp + "_" + siteId;
        this.deleted = false;
    }

    // Copy constructor
    public CRDTCharacter(CRDTCharacter other) {
        this.id = other.id;
        this.content = other.content;
        this.timestamp = other.timestamp;
        this.siteId = other.siteId;
        this.afterId = other.afterId;
        this.deleted = other.deleted;
    }

    // Getters
    public String getId() { return id; }
    public char getContent() { return content; }
    public long getTimestamp() { return timestamp; }
    public String getSiteId() { return siteId; }
    public String getAfterId() {
        return afterId;
    }

    public void setAfterId(String afterId) {
        this.afterId = afterId;
    }

    public boolean isDeleted() { return deleted; }

    // Setters for Jackson deserialization
    @JsonCreator
    public static CRDTCharacter fromJson(@JsonProperty("id") String id,
                                         @JsonProperty("content") char content,
                                         @JsonProperty("timestamp") long timestamp,
                                         @JsonProperty("siteId") String siteId,
                                         @JsonProperty("afterId") String afterId,
                                         @JsonProperty("deleted") boolean deleted) {
        CRDTCharacter character = new CRDTCharacter();
        character.id = id;
        character.content = content;
        character.timestamp = timestamp;
        character.siteId = siteId;
        character.afterId = afterId;
        character.deleted = deleted;
        return character;
    }

    public void delete() {
        this.deleted = true;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public int compareTo(CRDTCharacter other) {
        // The `afterId` defines the primary order. Characters are sorted relative to their predecessor.
        // This comparison is a fallback for characters inserted at the same position (i.e., with the same `afterId`).
        // We sort by timestamp to ensure a deterministic order.
        if (this.timestamp != other.timestamp) {
            return Long.compare(this.timestamp, other.timestamp);
        }
        // If timestamps are identical (highly unlikely but possible), use siteId as a tie-breaker.
        return this.siteId.compareTo(other.siteId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        CRDTCharacter that = (CRDTCharacter) obj;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "CRDTCharacter{" +
                "id='" + id + '\'' +
                ", content=" + content +
                ", afterId='" + afterId + '\'' +
                ", deleted=" + deleted +
                '}';
    }
}
