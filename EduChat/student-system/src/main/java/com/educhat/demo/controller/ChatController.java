package com.educhat.demo.controller;

import com.educhat.demo.model.ChatMessage;
import com.educhat.demo.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin // simple CORS for development
public class ChatController {

    @Autowired
    private ChatService chatService;

    @PostMapping("/chat")
    public ChatMessage chat(@RequestBody Map<String, String> payload) {
        String query = payload.get("query");
        return chatService.processMessage(query);
    }

    @GetMapping("/history")
    public List<ChatMessage> getHistory() {
        return chatService.getChatHistory();
    }

    @PostMapping("/clear")
    public String clearChat() {
        chatService.clearChatHistory();
        return "Chat history cleared!";
    }
}
