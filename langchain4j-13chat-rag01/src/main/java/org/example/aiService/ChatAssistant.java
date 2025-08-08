package org.example.aiService;

import reactor.core.publisher.Flux;

/**
 * 对话助理接口
 * 带有RAG增强搜索 功能
 * @version 1.0
 * @date 2025/8/7 15:49
 */
public interface ChatAssistant {
    Flux<String> chat(String  prompt);
}
