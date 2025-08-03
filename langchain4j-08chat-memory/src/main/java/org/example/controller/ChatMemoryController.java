package org.example.controller;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.example.service.ChatAssistant;
import org.example.service.ChatMemoryAssistant;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/chatMemory")
public class ChatMemoryController {

    // 普通对话模型，不带记忆缓存
    @Resource
    private ChatAssistant chatAssistant;
    // 带记忆缓存的模型，淘汰策略：(滑动窗口)根据message次数进行淘汰，maxMessages=5
    @Resource(name = "messageWindowChatMemoryAssistant")
    private ChatMemoryAssistant messageWindowChatMemoryAssistant;
    // 带记忆缓存的模型，淘汰策略：(滑动窗口)根据token数量进行淘汰, maxTokens=1000
    @Resource(name = "tokenWindowChatMemoryAssistant")
    private ChatMemoryAssistant tokenWindowChatMemoryAssistant;

    /**
     * 普通对话模型调用
     * @param prompt 提示词
     * @return
     */
    @GetMapping("/chatAssistant")
    public String chatAssistant(@RequestParam(value = "prompt", defaultValue = "我是谁") String prompt) {
        log.info("chatAssistant.prompt: {}", prompt);
        return chatAssistant.chat( prompt);
    }

    /**
     * 带记忆的对话模型调用(根据消息次数进行记忆缓存淘汰)
     * @param memoryId 记忆id
     * @param prompt 提示词
     * @return
     */
    @GetMapping("/messageWindow")
    public String messageWindow(
            @RequestParam(value = "prompt", defaultValue = "我是谁") String prompt,
            @RequestParam(value = "memoryId", defaultValue = "1") Long memoryId
    ) {
        log.info("messageWindow.prompt: {}， memoryId: {}", prompt, memoryId);
        String result = messageWindowChatMemoryAssistant.chatWithChatMemory(memoryId, prompt);
        log.info("messageWindow.result: {}", result);
        return result;
    }

    @GetMapping("/tokenWindow")
    public String tokenWindow(
            @RequestParam(value = "prompt", defaultValue = "我是谁") String prompt,
            @RequestParam(value = "memoryId", defaultValue = "1") Long memoryId
    ) {
        log.info("tokenWindow.prompt:{}, memoryId: {}", prompt, memoryId);
        String result = tokenWindowChatMemoryAssistant.chatWithChatMemory(memoryId, prompt);
        log.info("tokenWindow.result: {}", result);
        return result;
    }


}
