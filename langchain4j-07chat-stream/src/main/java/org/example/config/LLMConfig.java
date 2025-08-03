package org.example.config;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;
import dev.langchain4j.service.AiServices;
import org.example.service.MyChatAssistant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LLMConfig {

    @Value("${spring.ai.alibaba.apiKey}")
    private String alibabaApiKey;
    @Value("${spring.ai.alibaba.baseUrl}")
    private String alibabaBaseUrl;

    @Value("${spring.ai.alibaba.qwenModelName}")
    private String qwenModelName;

    /**
     * 阿里云的qwen模型-普通对话模型
     * low-level
     * @return
     */
//    @Bean("qwen")
//    public ChatModel chatModelQwen() {
//        return OpenAiChatModel.builder()
//                .apiKey(alibabaApiKey)
//                .baseUrl(alibabaBaseUrl)
//                .modelName(qwenModelName)
//                .build();
//    }

    /**
     * 阿里云的qwen模型- 流式响应模型
     * low-level
     * @return
     */
    @Bean
    public StreamingChatModel streamingChatModelQwen() {
        return OpenAiStreamingChatModel.builder()
                .apiKey(alibabaApiKey)
                .baseUrl(alibabaBaseUrl)
                .modelName(qwenModelName)
                .build();
    }

    /**
     * 自定义aiservice 流式响应输出
     * high-level
     * @return
     */
    @Bean
    public MyChatAssistant myCharAssistant(StreamingChatModel streamingChatModel) {
        return AiServices.create(MyChatAssistant.class, streamingChatModel);
    }
}
