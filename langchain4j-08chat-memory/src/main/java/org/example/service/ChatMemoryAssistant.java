package org.example.service;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.UserMessage;

/**
 * 带有聊天记忆缓存的聊天模型
 * @version 1.0
 * @date 2025/8/3 16:52
 */
public interface ChatMemoryAssistant {

    String chatWithChatMemory(@MemoryId Long memoryId, @UserMessage String prompt);
}
