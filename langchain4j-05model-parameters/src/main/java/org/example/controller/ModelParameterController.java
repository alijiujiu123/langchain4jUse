package org.example.controller;

import dev.langchain4j.model.chat.ChatModel;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/modelParameter")
public class ModelParameterController {

    @Resource(name = "qwen")
    private ChatModel chatModelQwen;

    @Resource(name = "deepseek")
    private ChatModel chatModelDeepseek;

    /**
     * description  测试超时
     * date         2025/8/2 15:08
     * @author      geshishuai
     * @param       prompt
     * @return      java.lang.String
     */
    @GetMapping("/helloQwen")
    public String helloQwen(@RequestParam(value = "prompt", defaultValue = "你是谁") String prompt) {
        return chatModelQwen.chat(prompt);
    }

    /**
     * description  测试重试
     * date         2025/8/2 15:08
     * @author      geshishuai
     * @param       prompt
     * @return      java.lang.String
     */
    @RequestMapping("/helloDeepseek")
    public String helloDeepseek(@RequestParam(value = "prompt", defaultValue = "你是谁") String prompt) {
        return chatModelDeepseek.chat( prompt);
    }
}
