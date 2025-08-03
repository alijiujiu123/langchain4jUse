package org.example.config;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;
import dev.langchain4j.service.AiServices;
import org.example.service.ProgramerAssistant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LLMConfig {

    @Value("${spring.ai.alibaba.apiKey}")
    private String alibabaApiKey;
    @Value("${spring.ai.alibaba.baseUrl}")
    private String alibabaBaseUrl;

    @Value("${spring.ai.alibaba.qwenLongModelName}")
    private String qwenLongModelName;

    @Bean
    public StreamingChatModel chatModelQwenLong() {
        return OpenAiStreamingChatModel.builder()
                .apiKey(alibabaApiKey)
                .modelName(qwenLongModelName)
                .baseUrl(alibabaBaseUrl)
                .build();
    }

    /**
     * 创建一个自定义的编程专家模型
     * @return
     */
    @Bean
    public ProgramerAssistant programerAssistant(StreamingChatModel streamingChatModel) {
        return AiServices.create(ProgramerAssistant.class, streamingChatModel);
    }
}
