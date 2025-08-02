package org.example.controller;

import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.output.TokenUsage;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 底层chatModel可以手动灵活实现个性化需求
 * @author geshishuai
 * @version 1.0
 * @date 2025/8/1 19:01
 */
@Slf4j
@RestController
@RequestMapping("/api/low")
public class LowApiController {

    @Resource(name = "deepseek")
    private ChatModel chatModelDeepseek;

    /**
     * 低阶模型交互，并返回token使用量
     * @param prompt 提示器
     * @return 模型返回结果
     */
    @GetMapping("/helloWithTokenUseage")
    public String helloWithTokenUseage(@RequestParam(value = "prompt", defaultValue = "你是谁") String prompt)
    {
        log.info("prompt:{}", prompt);
        ChatResponse chatResponse = chatModelDeepseek.chat(UserMessage.from(prompt));
        String result = chatResponse.aiMessage().text();
        log.info("result:{}", result);
        // token 使用量
        TokenUsage tokenUsage = chatResponse.tokenUsage();
        log.info("本次调用消耗的token:{}", tokenUsage);
        result = result + "\r\n本次调用消耗的token: " + tokenUsage;
        return result;
    }

}
