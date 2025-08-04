package org.example.config;

import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;
import dev.langchain4j.service.AiServices;
import jakarta.annotation.Resource;
import org.example.component.RedisChatMemoryStore;
import org.example.service.ChatPersistenceAssistant;
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

    @Resource
    private RedisChatMemoryStore redisChatMemoryStore;

    /**
     * description  普通流式对话模型
     * date         2025/8/4 11:44
     * @return      dev.langchain4j.model.chat.ChatModel
     */
    @Bean
    public StreamingChatModel streamingChatModel() {
        return OpenAiStreamingChatModel.builder()
                .apiKey(alibabaApiKey)
                .modelName(qwenLongModelName)
                .baseUrl(alibabaBaseUrl)
                .build();
    }

    @Bean
    public ChatPersistenceAssistant chatPersistenceAssistant(StreamingChatModel streamingChatModel) {
        // 创建ChatMemoryProvider
        ChatMemoryProvider chatMemoryProvider = memoryId ->
                MessageWindowChatMemory.builder()
                        .id(memoryId)
                        .maxMessages(5)
                        .chatMemoryStore(redisChatMemoryStore)
                        .build();
        return AiServices.builder(ChatPersistenceAssistant.class)
                .streamingChatModel(streamingChatModel)
                .chatMemoryProvider(chatMemoryProvider)
                .build();
    }
}
