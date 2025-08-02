package org.example.config;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LLMConfig {

    @Value("${spring.ai.alibaba.qwen.apiKey}")
    private String qwenApiKey;
    @Value("${spring.ai.alibaba.qwen.modelName}")
    private String qwenModelName;
    @Value("${spring.ai.alibaba.qwen.baseUrl}")
    private String qwenBaseUrl;

    @Value("${spring.ai.deepseek.apiKey}")
    private String deepseekApiKey;
    @Value("${spring.ai.deepseek.modelName}")
    private String deepseekModelName;
    @Value("${spring.ai.deepseek.baseUrl}")
    private String deepseekBaseUrl;

    @Bean("qwen")
    public ChatModel chatModelQwen() {
        return OpenAiChatModel.builder()
                .apiKey(qwenApiKey)
                .modelName(qwenModelName)
                .baseUrl(qwenBaseUrl)
                .build();
    }

    @Bean("deepseek")
    public ChatModel chatModelDeepseek() {
        return OpenAiChatModel.builder()
                .apiKey(deepseekApiKey)
                .modelName(deepseekModelName)
                .baseUrl(deepseekBaseUrl)
                .build();
    }
}
