package org.example.controller;

import dev.langchain4j.model.chat.ChatModel;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * langchain4j 使用：low-level, 直接使用ChatModel进行交互
 * @author geshishuai
 * @version 1.0
 * @date 2025/8/1 17:35
 */
@Slf4j
@RestController
@RequestMapping("/api/population")
public class PopulationIntegrationController {

    @Resource
    private ChatModel chatModel;

    @GetMapping("/hello")
    public String hello(@RequestParam(value = "prompt", defaultValue = "你是谁") String prompt) {
        log.info("prompt: {}", prompt);
        String result = chatModel.chat("你是谁");
        log.info("result: {}", result);
        return result;
    }
}
