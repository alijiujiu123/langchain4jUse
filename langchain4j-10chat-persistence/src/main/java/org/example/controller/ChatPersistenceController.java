package org.example.controller;

import jakarta.annotation.Resource;
import org.example.service.ChatPersistenceAssistant;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/chatPersistence")
public class ChatPersistenceController {

    @Resource
    private ChatPersistenceAssistant chatPersistenceAssistant;

    @GetMapping("/redisChat")
    public Flux<String> redisChat(
            @RequestParam(value = "userId") Long userId,
            @RequestParam(value = "prompt", defaultValue = "我是谁") String prompt) {
        return chatPersistenceAssistant.chatFlux(userId, prompt);
    }
}
