package org.example.controller;

import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.chat.response.StreamingChatResponseHandler;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.example.service.MyChatAssistant;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@RestController
@RequestMapping("/streamingChat")
public class StreamingChatModelController {

    @Resource
    private MyChatAssistant myChatAssistant;

    @Resource
    private StreamingChatModel streamingChatModel;

    @GetMapping("/low")
    public Flux<String> lowLevel(@RequestParam(name = "prompt", defaultValue = "北京有什么好吃的") String prompt) {
        log.info("low.prompt: {}", prompt);
        AtomicInteger atomicInteger = new AtomicInteger();
        return Flux.create(emitter -> {
            streamingChatModel.chat(prompt, new StreamingChatResponseHandler() {
                @Override
                public void onPartialResponse(String partialResponse) {
                    log.info("第{}次输出, low.partialResponse: {}", atomicInteger.getAndIncrement(), partialResponse);
                    emitter.next(partialResponse);
                }

                @Override
                public void onCompleteResponse(ChatResponse completeResponse) {
                    log.info("流式响应输出完成,总次数: {}, low.completeResponse: {}", atomicInteger.get(), completeResponse);
                    emitter.complete();
                }

                @Override
                public void onError(Throwable error) {
                    emitter.error( error);
                }
            });
        });
    }

    @GetMapping("high")
    public Flux<String> high(@RequestParam(value = "prompt", defaultValue = "南京有什么好吃的") String prompt) {
        log.info("high.prompt: {}", prompt);
        return myChatAssistant.chatFlux( prompt);
    }
}
