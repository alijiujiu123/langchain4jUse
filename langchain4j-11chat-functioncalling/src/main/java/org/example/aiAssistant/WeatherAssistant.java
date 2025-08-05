package org.example.aiAssistant;

import reactor.core.publisher.Flux;

/**
 * 天气助理
 * 挂载天气工具
 * @version 1.0
 * @date 2025/8/4 17:47
 */
public interface WeatherAssistant {
    Flux<String> chat(String prompt);
}
