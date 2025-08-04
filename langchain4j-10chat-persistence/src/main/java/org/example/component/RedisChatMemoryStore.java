package org.example.component;

import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.ChatMessageDeserializer;
import dev.langchain4j.data.message.ChatMessageSerializer;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class RedisChatMemoryStore implements ChatMemoryStore {
    // redis key 前缀
    public static final String CHAT_MEMORY_PREFIX = "CHAT_MEMORY:";

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 根据memoryId，获取持久化会话消息
     *
     * @param memoryId 会话ID
     * @return 会话消息
     */
    @Override
    public List<ChatMessage> getMessages(Object memoryId) {
        log.info("getMessages memoryId: {}", memoryId);
        String message = redisTemplate.opsForValue().get(CHAT_MEMORY_PREFIX + memoryId);
        log.info("getMessages message: {}", message);
        return ChatMessageDeserializer.messagesFromJson(message);
    }

    /**
     * 更新持久化会话消息
     *
     * @param memoryId 会话ID
     * @param messages 会话消息
     */
    @Override
    public void updateMessages(Object memoryId, List<ChatMessage> messages) {
        log.info("updateMessages memoryId: {}, messages: {}", memoryId, messages);
        redisTemplate.opsForValue().set(CHAT_MEMORY_PREFIX + memoryId, ChatMessageSerializer.messagesToJson(messages));
    }

    /**
     * 删除持久化会话消息
     *
     * @param memoryId 会话ID
     */
    @Override
    public void deleteMessages(Object memoryId) {
        log.info("deleteMessages memoryId: {}", memoryId);
        redisTemplate.delete(CHAT_MEMORY_PREFIX + memoryId);
    }
}
