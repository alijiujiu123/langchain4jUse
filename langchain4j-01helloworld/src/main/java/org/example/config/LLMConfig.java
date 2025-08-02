package org.example.config;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 创建LLM配置类
 * @author geshishuai
 * @version 1.0
 * @date 2025/8/1 12:58
 */
@Configuration
public class LLMConfig {

    @Value("${spring.ai.alibaba.qwen.apiKey}")
    private String apiKey;
    @Value("${spring.ai.alibaba.qwen.modelName}")
    private String modelName;
    @Value("${spring.ai.alibaba.qwen.baseUrl}")
    private String baseUrl;

    @Bean
    public ChatModel chatModelQwen() {
        return OpenAiChatModel.builder()
                .apiKey(apiKey)
                .modelName(modelName)
                .baseUrl(baseUrl)
                .build();
    }
}
