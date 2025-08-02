package org.example.config;

import dev.langchain4j.community.model.dashscope.WanxImageModel;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import org.example.listener.TokenUseageListeren;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class LLMConfig {

    @Value("${spring.ai.alibaba.apiKey}")
    private String alibabaApiKey;
    @Value("${spring.ai.alibaba.baseUrl}")
    private String alibabaBaseUrl;

    // 多模态大模型：图片和文字的结合输入，适用于视觉-语言任务
    @Value("${spring.ai.alibaba.qwenVlMaxModeName}")
    private String vlMaxModelName;
    // 通义万象：图片生成
    @Value("${spring.ai.alibaba.wanxModelName}")
    private String wanxModelName;

    @Autowired
    private TokenUseageListeren tokenUseageListeren;

    @Bean
    public ChatModel chatModelQwen() {
        return OpenAiChatModel.builder()
                .apiKey(alibabaApiKey)
                .modelName(vlMaxModelName)
                .baseUrl(alibabaBaseUrl)
                .listeners(List.of(tokenUseageListeren))
                .build();
    }

    @Bean
    public WanxImageModel wanxImageModel() {
        return WanxImageModel.builder()
                .apiKey(alibabaApiKey)
                .modelName(wanxModelName)
                // 这里配置baseUrl会报错
//                .baseUrl(alibabaBaseUrl)
                .build();
    }
}
