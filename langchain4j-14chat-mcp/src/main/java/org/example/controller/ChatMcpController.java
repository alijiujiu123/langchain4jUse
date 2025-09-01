package org.example.controller;

import dev.langchain4j.model.chat.StreamingChatModel;
import jakarta.annotation.Resource;
import org.example.service.MCPService;
import org.example.service.WithoutMcpService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/chatMcp")
public class ChatMcpController {

    @Resource
    private MCPService mcpService;
    @Resource
    private WithoutMcpService withoutMcpService;

    @GetMapping("/withMcp")
    public Flux<String> chat(@RequestParam Long memoryId, @RequestParam String prompt) {
        return mcpService.chat(memoryId, prompt);
    }

    @GetMapping("/withoutMcp")
    public Flux<String> chatWithoutMcp(String prompt) {
        return withoutMcpService.chat( prompt);
    }
}
