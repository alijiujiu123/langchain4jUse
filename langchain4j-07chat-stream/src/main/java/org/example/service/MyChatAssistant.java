package org.example.service;

import reactor.core.publisher.Flux;

public interface MyChatAssistant {
    // 普通对话
    String chat(String prompt);
    // 流式响应
    Flux<String> chatFlux(String prompt);
}
