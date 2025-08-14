package com.example.wsTextEditor.controller;

import com.example.wsTextEditor.crdt.CRDTDocument;
import com.example.wsTextEditor.crdt.CRDTOperation;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Controller
public class TextEditorWebSocketController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    private final Map<String, CRDTDocument> documents = new ConcurrentHashMap<>();

    @MessageMapping("/crdt-operation")
    public void handleCRDTOperation(CRDTOperation operation, SimpMessageHeaderAccessor headerAccessor) {
        try {
            String sessionId = headerAccessor.getSessionId();
            if (sessionId == null) {
                System.err.println("Error: Session ID is null.");
                return;
            }

            String siteId = operation.getCharacter().getSiteId();
            CRDTDocument document = documents.computeIfAbsent(sessionId, k -> new CRDTDocument(siteId));

            List<CRDTOperation> processedOperations = document.applyOperation(operation);

            for (CRDTOperation op : processedOperations) {
                op.setSessionId(sessionId);
                System.out.println("Broadcasting CRDT Operation: " + op);
                messagingTemplate.convertAndSend("/topic/crdt-changes", op);
            }
            System.out.println("Document state: " + document.getText());

        } catch (Exception e) {
            System.err.println("Error processing CRDT operation: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @MessageMapping("/request-document")
    @SendTo("/topic/document-state")
    public Map<String, Object> requestDocument(Map<String, String> payload) {
        String sessionId = payload.get("sessionId");
        String siteId = payload.get("siteId");
        CRDTDocument document = documents.computeIfAbsent(sessionId, k -> new CRDTDocument(siteId));

        System.out.println("Sending initial document state for session: " + sessionId);
        return Map.of(
                "sessionId", sessionId,
                "characters", document.getCharacters()
        );
    }

    @MessageMapping("/mouse")
    @SendTo("/topic/mouse")
    public Map<String, Object> mousePosition(Map<String, Object> data) {
        return data;
    }
}

