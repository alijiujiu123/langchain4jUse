package org.example.config;

import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.memory.chat.TokenWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiTokenCountEstimator;
import dev.langchain4j.service.AiServices;
import org.example.service.ChatAssistant;
import org.example.service.ChatMemoryAssistant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 带有记忆缓存的对话大模型
 * 记忆缓存是langchain4j实现，与语言模型无关
 * 如果没有持久化缓存，重启服务会丢失对话记忆缓存
 * @version 1.0
 * @date 2025/8/3 18:04
 */
@Configuration
public class LLMConfig {

    @Value("${spring.ai.alibaba.apiKey}")
    private String alibabaApiKey;
    @Value("${spring.ai.alibaba.baseUrl}")
    private String alibabaBaseUrl;

    @Value("${spring.ai.alibaba.qwenLongModelName}")
    private String qwenLongModelName;

    /**
     * 创建Qwen-Long模型: 普通对话模型，没有记忆缓存
     * low-level
     * @return
     */
    @Bean
    public ChatModel chatModelQwenLong() {
        OpenAiChatModel openAiChatModel = OpenAiChatModel.builder()
                .apiKey(alibabaApiKey)
                .modelName(qwenLongModelName)
                .baseUrl(alibabaBaseUrl)
                .build();
        return openAiChatModel;
    }

    /**
     * 创建Qwen-Long模型: 普通聊天模型，没有记忆缓存
     * high-level
     * @return
     */
    @Bean
    public ChatAssistant chatAssistant(ChatModel chatModelQwenLong) {
        return AiServices.create(ChatAssistant.class, chatModelQwenLong);
    }

    /**
     * 创建Qwen-Long模型: 聊天模型，有记忆缓存
     * high-level
     * eviction policy: (滑动窗口)记忆缓存根据message次数进行淘汰
     * @return
     */
    @Bean("messageWindowChatMemoryAssistant")
    public ChatMemoryAssistant messageWindowChatMemoryAssistant(ChatModel chatModelQwenLong) {
        return AiServices.builder(ChatMemoryAssistant.class)
                .chatModel(chatModelQwenLong)
                // 记忆缓存淘汰策略: 根据message次数进行淘汰
                .chatMemoryProvider(memoryId -> MessageWindowChatMemory.withMaxMessages(5))
                .build();
    }

    /**
     * 基于token的缓存(滑动窗口)
     * @param chatModelQwenLong low-level对话模型
     * @return
     */
    @Bean("tokenWindowChatMemoryAssistant")
    public ChatMemoryAssistant tokenWindowChatMemoryAssistant(ChatModel chatModelQwenLong) {
        OpenAiTokenCountEstimator openAiTokenCountEstimator = new OpenAiTokenCountEstimator("gpt-4");
        return AiServices.builder(ChatMemoryAssistant.class)
                .chatModel(chatModelQwenLong())
                // 记忆缓存淘汰策略: 根据token数量进行淘汰
                .chatMemoryProvider(memoryId -> TokenWindowChatMemory.withMaxTokens(1000, openAiTokenCountEstimator))
                .build();
    }
}
