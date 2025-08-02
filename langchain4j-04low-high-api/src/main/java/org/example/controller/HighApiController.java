package org.example.controller;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.example.service.ChatAssistant;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/high")
public class HighApiController {

    @Resource
    private ChatAssistant chatAssistant;

    @GetMapping
    public String hello(@RequestParam(value = "prompt", defaultValue = "你是谁") String prompt)
    {
        log.info("prompt: {}", prompt);
        String result = chatAssistant.chat(prompt);
        log.info("result: {}", result);
        return result;
    }
}
