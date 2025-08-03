package org.example.controller;

import jakarta.annotation.Resource;
import org.example.entity.ProgramerPrompt;
import org.example.service.ProgramerAssistant;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/chatPrompt")
public class ChatPromptController {

    @Resource
    private ProgramerAssistant programerAssistant;

    @RequestMapping("/programerAssistant")
    public Flux<String> programerAssistant(@RequestParam(value = "question", defaultValue = "你是谁") String question) {
        // 回答控制在200个字以内，以html格式返回结果
        return programerAssistant.chat(question, 200, "html");
    }

    @GetMapping("/structPrompt")
    public Flux<String> structPrompt(@RequestParam(value = "question", defaultValue = "你是谁") String question, @RequestParam(value = "language", defaultValue = "中文") String language) {
        // 创建ProgramerPrompt对象
        ProgramerPrompt programerPrompt = ProgramerPrompt.builder()
                .question(question)
                .length(400)
                .format("html")
                .language(language)
                .build();
        // 创建ProgramerPrompt对象
        return programerAssistant.chat(programerPrompt);
    }
}
