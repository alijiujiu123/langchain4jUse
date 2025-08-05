package org.example.config;

import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.openai.OpenAiEmbeddingModel;
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

    @Value("${spring.ai.alibaba.qwenTextEmbeddingModelName}")
    private String qwenTextEmbeddingModel;

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
}
