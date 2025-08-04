package org.example.controller;

import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.chat.response.StreamingChatResponseHandler;
import dev.langchain4j.model.input.Prompt;
import dev.langchain4j.model.input.PromptTemplate;
import jakarta.annotation.Resource;
import org.example.entity.ProgramerPrompt;
import org.example.enums.LanguageEnum;
import org.example.service.ProgramerAssistant;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.Map;

@RestController
@RequestMapping("/api/chatPrompt")
public class ChatPromptController {

    @Resource
    private ProgramerAssistant programerAssistant;

    @Resource
    private StreamingChatModel streamingChatModel;

    @RequestMapping("/programerAssistant")
    public Flux<String> programerAssistant(@RequestParam(value = "question", defaultValue = "你是谁") String question) {
        // 回答控制在200个字以内，以html格式返回结果
        return programerAssistant.chat(question, 200, "html");
    }

    /**
     * description  编程专家助手（结构化提示词实现）
     * date         2025/8/4 10:39
     * @author      geshishuai
     * @param       question    问题
     * @param       language    希望回答的自然语言
     * @return      reactor.core.publisher.Flux<java.lang.String>
     */
    @GetMapping("/structPrompt")
    public Flux<String> structPrompt(
            @RequestParam(value = "question", defaultValue = "你是谁") String question,
            @RequestParam(value = "language", defaultValue = "1") Integer language) {
        // 创建ProgramerPrompt对象
        ProgramerPrompt programerPrompt = ProgramerPrompt.builder()
                .question(question)
                .length(400)
                .format("html")
                .language(LanguageEnum.getValueByCode(language))
                .build();
        return programerAssistant.chat(programerPrompt);
    }

    @GetMapping("/promptTmp")
    public Flux<String> promptTmp(@RequestParam(value = "role", defaultValue = "编程") String role,
                            @RequestParam(value = "question", defaultValue = "你是谁") String question) {
        // 1. 构造PromptTemplate模板
        PromptTemplate sysPromptTemplate = PromptTemplate.from("你是一个{{role}}助手，只回答{{role}}相关的问题。输出限制: 对于其他领域的问题禁止回答，直接返回'抱歉，我只能回答{{role}}相关的问题'。");
        // 2. 构造Prompt提示词
        Prompt sysPrompt = sysPromptTemplate.apply(Map.of("role", role));
        // 3. 提示词转换为SystemMessage
        SystemMessage systemMessage = sysPrompt.toSystemMessage();
        // 构建用户提示词
        PromptTemplate userPromptTemp = PromptTemplate.from("请回答以下问题: {{question}}, 以总-分-总的形式, 字数控制在300以内, 以html返回结果");
        Prompt userPrompt = userPromptTemp.apply(Map.of("question", question));
        UserMessage userMessage = userPrompt.toUserMessage();

        // 调用大模型
        return Flux.create(emitter -> {
            streamingChatModel.chat(Arrays.asList(systemMessage, userMessage), new StreamingChatResponseHandler() {
                @Override
                public void onPartialResponse(String partialResponse) {
                    emitter.next(partialResponse);
                }

                @Override
                public void onCompleteResponse(ChatResponse completeResponse) {
                    emitter.complete();
                }

                @Override
                public void onError(Throwable error) {
                    emitter.error( error);
                }
            });
        });

    }
}
