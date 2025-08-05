package org.example.config;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiEmbeddingModel;
import dev.langchain4j.service.AiServices;
import org.example.aiService.DistilledAssistant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 大模型配置类
 * @version 1.0
 * @date 2025/8/5 16:05
 */
@Configuration
public class LLMConfig {

    @Value("${spring.ai.alibaba.apiKey}")
    private String alibabaApiKey;
    @Value("${spring.ai.alibaba.baseUrl}")
    private String alibabaBaseUrl;

    // 向量模型
    @Value("${spring.ai.alibaba.qwenTextEmbeddingModelName}")
    private String qwenTextEmbeddingModel;
    @Value("${spring.ai.alibaba.qwenPlusModelName}")
    private String qwenPlusModelName;



    /**
     * description  向量大模型
     * date         2025/8/5 16:05
     * @author      geshishuai
     * @param
     * @return      dev.langchain4j.model.embedding.EmbeddingModel
     */
    @Bean
    public EmbeddingModel embeddingModel() {
        return OpenAiEmbeddingModel.builder()
                .apiKey(alibabaApiKey)
                .modelName(qwenTextEmbeddingModel)
                .baseUrl(alibabaBaseUrl)
                .build();
    }

    /**
     * 创建 qwen-plus 模型
     * @param
     * @return      dev.langchain4j.model.chat.ChatModel
     */
    @Bean
    public ChatModel chatModelQwenPlus() {
        return OpenAiChatModel.builder()
                .apiKey(alibabaApiKey)
                .modelName(qwenPlusModelName)
                .baseUrl(alibabaBaseUrl)
                .build();
    }

    /**
     * high-level创建面试题蒸馏助理
     * @param
     * @return      dev.langchain4j.model.chat.ChatModel
     */
    @Bean
    public DistilledAssistant distilledAssistant(ChatModel chatModelQwenPlus) {
        return AiServices.builder(DistilledAssistant.class)
                .chatModel(chatModelQwenPlus)
                .build();
    }
}
