package com.example.wsTextEditor.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String uniqueId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User owner;

    @PrePersist
    public void prePersist() {
        if (uniqueId == null) {
            uniqueId = UUID.randomUUID().toString();
        }
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
} 