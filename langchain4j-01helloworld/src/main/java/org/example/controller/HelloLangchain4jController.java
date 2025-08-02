package org.example.controller;

import dev.langchain4j.model.chat.ChatModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/hello")
public class HelloLangchain4jController {

    @Autowired
    private ChatModel chatModel;

    @RequestMapping("/langchain4j/helloWorld")
    public String helloWorld(@RequestParam(value = "question", defaultValue = "你是谁") String question) {
        log.info("question: {}", question);
        String result = chatModel.chat(question);
        log.info("result: {}", result);
        return result;
    }
}
