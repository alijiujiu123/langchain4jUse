package org.example.controller;

import dev.langchain4j.model.chat.ChatModel;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/multiModel")
public class MultiModelController {

//    @Autowired
//    @Qualifier("qwen")
    @Resource(name = "qwen") // JSR-250 标准注解，效果与上边等同
    private ChatModel qwen;

    @Resource(name = "deepseek")
    private ChatModel deepseek;

    @GetMapping("/qwen")
    public String qwenCall(@RequestParam (value = "prompt", defaultValue = "你是谁") String prompt) {
        log.info("qwen.prompt: {}", prompt);
        String result = qwen.chat(prompt);
        log.info("qwen.result: {}", result);
        return result;
    }

    @GetMapping("/deepseek")
    public String deepseekCall(@RequestParam (value = "prompt", defaultValue = "你是谁") String prompt) {
        log.info("deepseek.prompt: {}", prompt);
        String result = deepseek.chat(prompt);
        log.info("deepseek.result: {}", result);
        return result;
    }
}
