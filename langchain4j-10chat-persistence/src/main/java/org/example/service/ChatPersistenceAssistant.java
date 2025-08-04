package org.example.service;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.UserMessage;
import reactor.core.publisher.Flux;

public interface ChatPersistenceAssistant {

    Flux<String> chatFlux(@MemoryId Long memoryId, @UserMessage String prompt);
}
