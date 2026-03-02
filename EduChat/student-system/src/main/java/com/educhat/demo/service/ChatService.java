package com.educhat.demo.service;

import com.educhat.demo.model.ChatMessage;
import com.educhat.demo.repository.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.HttpEntity;
import java.util.*;

@Service
public class ChatService {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Value("${google.ai.api.key}")
    private String apiKey;

    private final String API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent?key=";

    public ChatMessage processMessage(String userQuery) {
        return getAiResponse(userQuery);
    }

    public List<ChatMessage> getChatHistory() {
        return chatMessageRepository.findAll();
    }

    public ChatMessage getAiResponse(String userPrompt) {
        try {
            // 1. Save User Message to DB
            chatMessageRepository.save(new ChatMessage("user", userPrompt));

            RestTemplate restTemplate = new RestTemplate();
            String url = "https://api.groq.com/openai/v1/chat/completions";

            // 2. Prepare Headers (Groq needs the "Authorization" header)
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + apiKey); // apiKey = your Groq key

            // 3. Load History from DB (Context Window)
            List<ChatMessage> history = chatMessageRepository.findAll();
            List<Map<String, Object>> messages = new ArrayList<>();
            for (ChatMessage msg : history) {
                Map<String, Object> m = new HashMap<>();
                m.put("role", msg.getRole());
                m.put("content", msg.getContent());
                messages.add(m);
            }

            // 4. Prepare Body (OpenAI/Groq Standard)
            Map<String, Object> body = new HashMap<>();
            body.put("model", "llama-3.3-70b-versatile");
            body.put("messages", messages);

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

            // 5. Send Request
            Map<String, Object> response = restTemplate.postForObject(url, entity, Map.class);

            // 6. Dig into the response layers
            List choices = (List) response.get("choices");
            Map firstChoice = (Map) choices.get(0);
            Map respMessage = (Map) firstChoice.get("message");
            String aiContent = (String) respMessage.get("content");

            // 7. Save AI Response to DB
            ChatMessage aiResponse = new ChatMessage("assistant", aiContent);
            return chatMessageRepository.save(aiResponse);

        } catch (Exception e) {
            String errorMsg = "Groq Error: " + e.getMessage();
            ChatMessage errorResponse = new ChatMessage("assistant", errorMsg);
            return chatMessageRepository.save(errorResponse);
        }
    }

    public void clearChatHistory() {
        chatMessageRepository.deleteAll();
    }
}
