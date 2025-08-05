package org.example.aiAssistant;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.UserMessage;
import reactor.core.publisher.Flux;

/**
 * 天气助理
 * 挂载天气工具
 * @version 1.0
 * @date 2025/8/4 17:47
 */
public interface WeatherAssistant {
    Flux<String> chat(@MemoryId Long memoryId, @UserMessage String prompt);
}
