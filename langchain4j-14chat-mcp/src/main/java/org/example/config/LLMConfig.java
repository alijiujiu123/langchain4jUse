package org.example.config;

import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.tool.ToolProvider;
import jakarta.annotation.Resource;
import org.example.service.MCPService;
import org.example.service.WithoutMcpService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 大模型配置类
 * @version 1.0
 * @date 2025/8/10 19:33
 */
@Configuration
public class LLMConfig {

    @Resource
    private StreamingChatModel streamingChatModel;
    @Resource
    private ToolProvider toolProvider;

    @Bean
    public MCPService mcpService() {
        return AiServices.builder(MCPService.class)
                .streamingChatModel(streamingChatModel)
                .chatMemoryProvider(memoryId -> MessageWindowChatMemory.withMaxMessages(5))
                .toolProvider(toolProvider)
                .build();
    }

    @Bean
    public WithoutMcpService withoutMcp() {
        return AiServices.builder(WithoutMcpService.class)
                .streamingChatModel(streamingChatModel)
                .build();
    }
}
