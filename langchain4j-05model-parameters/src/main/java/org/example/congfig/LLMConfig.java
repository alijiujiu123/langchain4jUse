package org.example.congfig;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import jakarta.annotation.Resource;
import org.example.listener.MyChatModelListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.List;

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

    @Resource
    private MyChatModelListener myChatModelListener;

    @Bean("qwen")
    public ChatModel chatModelQwen() {
        return OpenAiChatModel.builder()
                .apiKey(qwenApiKey)
                .modelName(qwenModelName)
                .baseUrl(qwenBaseUrl)
                .logRequests(true) // 请求日志开关，日志级别设置为DEBUG才有效
                .logResponses(true) // 响应日志开关，日志级别设置为DEBUG才有效
                .timeout(Duration.ofSeconds(2)) // 请求超时时间，向大模型发送请求时，如在指定时间内未得到响应，该请求将被中断，并报request time out
                .listeners(List.of(myChatModelListener))
                .build();
    }

    @Bean("deepseek")
    public ChatModel chatModelDeepseek() {
        return OpenAiChatModel.builder()
                .apiKey(deepseekApiKey) //故意写错，测试重试次数
                .modelName(deepseekModelName)
                .baseUrl(deepseekBaseUrl)
                .logRequests(true) // 请求日志开关，日志级别设置为DEBUG才有效
                .logResponses(true) // 响应日志开关，日志级别设置为DEBUG才有效
                .maxRetries(2) // 重试次数
                .listeners(List.of(myChatModelListener))
                .build();
    }
}
