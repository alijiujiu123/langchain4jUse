package org.example.config;

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingStore;
import org.example.aiService.ChatAssistant;
import org.example.aiService.GeneralAssistant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 大模型配置
 * @version 1.0
 * @date 2025/8/7 15:44
 */
@Configuration
public class LLMConfig {

    @Value("${spring.ai.alibaba.apiKey}")
    private String alibabaApiKey;
    @Value("${spring.ai.alibaba.baseUrl}")
    private String alibabaBaseUrl;

    @Value("${spring.ai.alibaba.qwenPlusModelName}")
    private String qwenPlusModelName;

    /**
     * description  流式对话模型
     * date         2025/8/7 15:42
     * @return      dev.langchain4j.model.chat.StreamingChatModel
     */
    @Bean
    public StreamingChatModel qwenPlusModel() {
        return OpenAiStreamingChatModel.builder()
                .apiKey(alibabaApiKey)
                .modelName(qwenPlusModelName)
                .baseUrl(alibabaBaseUrl)
                .build();
    }

    /**
     * description  创建带有contentRetriever的聊天助手
     * high-level
     * date         2025/8/7 15:53
     * @return      org.example.aiService.ChatAssistant
     */
    @Bean
    public ChatAssistant chatAssistant(StreamingChatModel qwenPlusModel, EmbeddingStore<TextSegment> embeddingStore) {
        return AiServices.builder(ChatAssistant.class)
                .streamingChatModel(qwenPlusModel)
                .chatMemory(MessageWindowChatMemory.withMaxMessages(50))
                .contentRetriever(EmbeddingStoreContentRetriever.from(embeddingStore))
                .build();
    }

    /**
     * description  普通模型问答
     * date         2025/8/7 16:40
     * @author      geshishuai
     * @param       qwenPlusModel
     * @return      org.example.aiService.GeneralAssistant
     */
    @Bean
    public GeneralAssistant generalAssistant(StreamingChatModel qwenPlusModel) {
        return AiServices.builder(GeneralAssistant.class)
                .streamingChatModel(qwenPlusModel)
                .chatMemory(MessageWindowChatMemory.withMaxMessages(50))
                .build();
    }


}
