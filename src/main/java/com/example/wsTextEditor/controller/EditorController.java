package com.example.wsTextEditor.controller;

import com.example.wsTextEditor.model.Document;
import com.example.wsTextEditor.model.User;
import com.example.wsTextEditor.repository.DocumentRepository;
import com.example.wsTextEditor.repository.UserRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

@Controller
public class EditorController {

    private final DocumentRepository documentRepository;
    private final UserRepository userRepository;

    @Value("${y.websocket.browser.url}")
    private String yWebsocketUrl;

    public EditorController(DocumentRepository documentRepository, UserRepository userRepository) {
        this.documentRepository = documentRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String dashboard(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return "redirect:/login";
        }
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        List<Document> documents = documentRepository.findByOwner(user);
        model.addAttribute("documents", documents);
        return "dashboard";
    }

    @PostMapping("/documents/create")
    public String createDocument(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        Document doc = new Document();
        doc.setOwner(user);
        documentRepository.save(doc);
        return "redirect:/editor/" + doc.getUniqueId();
    }

    @GetMapping("/editor/{documentId}")
    public String editor(@PathVariable String documentId, Model model) {
        Document document = documentRepository.findByUniqueId(documentId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid document ID:" + documentId));
        model.addAttribute("document", document);
        model.addAttribute("ywsUrl", yWebsocketUrl);
        return "editor";
    }
}
