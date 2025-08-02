package org.example.controller;

import dev.langchain4j.data.message.ImageContent;
import dev.langchain4j.data.message.TextContent;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Base64;

@RestController
@Slf4j
@RequestMapping("/imageModel")
public class ImageModelController {

    // vl-max 多模态模型
    @Autowired
    private ChatModel chatModel;

    @Value("classpath:images/mi.jpg")
    private Resource miResource;

    @Value("classpath:images/haier.png")
    private Resource haierResource;

    @GetMapping("/readImageContent")
    public String readImageContent() throws IOException {
        // 1. 图片转码
        byte[] contentAsByteArray = haierResource.getContentAsByteArray();
        String base64 = Base64.getEncoder().encodeToString(contentAsByteArray);
        // 2. 提示词指定
        UserMessage userMessage = UserMessage.from(
                TextContent.from("从下面图中分析一下股价走势和成交量以及资金趋势"),
                ImageContent.from(base64, "image/png")
        );
        // 3. API调用
        // 请求内容包括文本提示和图片，模型会根据提示词和图片内容进行识别，并返回分析结果
        ChatResponse chatResponse = chatModel.chat(userMessage);
        // 4. 解析输出
        String result = chatResponse.aiMessage().text();
        log.info("result:{}", result);
        return result;
    }
}
