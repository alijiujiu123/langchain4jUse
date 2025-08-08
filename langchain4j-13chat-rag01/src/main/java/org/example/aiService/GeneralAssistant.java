package org.example.aiService;

import reactor.core.publisher.Flux;

/**
 * 普通聊天
 * 没有RAG
 * @author geshishuai
 * @version 1.0
 * @date 2025/8/7 16:38
 */
public interface GeneralAssistant {
    Flux<String> chat(String prompt);
}
