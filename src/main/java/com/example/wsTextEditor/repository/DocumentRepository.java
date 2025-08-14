package com.example.wsTextEditor.repository;

import com.example.wsTextEditor.model.Document;
import com.example.wsTextEditor.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    Optional<Document> findByUniqueId(String uniqueId);
    List<Document> findByOwner(User owner);
} 