package org.example.config;

import cn.hutool.json.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.langchain4j.agent.tool.ToolSpecification;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.model.chat.request.json.JsonObjectSchema;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.tool.ToolExecutor;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.example.aiAssistant.InvoiceAssistant;
import org.example.aiAssistant.WeatherAssistant;
import org.example.entity.InvoiceData;
import org.example.entity.InvoiceInfo;
import org.example.modelTools.WeatherToolHandler;
import org.example.service.serviceImp.InvoiceService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Slf4j
@Configuration
public class LLMConfig {

    @Value("${spring.ai.alibaba.apiKey}")
    private String alibabaApiKey;
    @Value("${spring.ai.alibaba.baseUrl}")
    private String alibabaBaseUrl;

    @Value("${spring.ai.alibaba.qwenPlusModelName}")
    private String qwenLongModelName;

    @Resource
    private InvoiceService invoiceService;
    @Resource
    private WeatherToolHandler weatherToolHandler;

    @Bean
    public ChatModel chatModelQwenLong() {
        return OpenAiChatModel.builder()
                .apiKey(alibabaApiKey)
                .modelName(qwenLongModelName)
                .baseUrl(alibabaBaseUrl)
                .build();
    }

    @Bean
    public InvoiceAssistant invoiceAssistant(ChatModel chatModelQwenLong) {
        ToolSpecification toolSpecification = ToolSpecification.builder()
                .name("开具发票助手")
                .description("根据用户提交的开票信息，开具发票")
                .parameters(JsonObjectSchema.builder()
                        .addStringProperty("companyName", "公司名称")
                        .addStringProperty("dutyNumber", "税号序列")
                        .addStringProperty("amount", "开票金额，保留两位有效数字")
                        .build())
                .build();
        // 业务逻辑 ToolExecutor
        ToolExecutor toolExecutor = (toolExecutionRequest, memoryId) -> {
            System.out.println(toolExecutionRequest.id());
            System.out.println(toolExecutionRequest.name());
            String arguments1 = toolExecutionRequest.arguments();
            System.out.println("arguments1****》 " + arguments1);
            ObjectMapper mapper = new ObjectMapper();
            InvoiceInfo invoice = null;
            try {
                invoice = mapper.readValue(arguments1, InvoiceInfo.class);
            } catch (JsonProcessingException e) {
                log.error("JsonProcessingException: {}", e.getMessage());
            }
            InvoiceData invoiceData = invoiceService.createInvoice(invoice);
            log.info("invoiceData:{}", invoiceData);
            return invoiceData.toString();
        };

        return AiServices.builder(InvoiceAssistant.class)
                .chatModel(chatModelQwenLong)
                .tools(Map.of(toolSpecification, toolExecutor))
                .build();
    }

    /**
     * description  普通流式对话模型
     * date         2025/8/4 17:22
     * @return      dev.langchain4j.model.chat.StreamingChatModel
     */
    @Bean
    public StreamingChatModel chatModelQwen() {
        return OpenAiStreamingChatModel.builder()
                .apiKey(alibabaApiKey)
                .modelName(qwenLongModelName)
                .baseUrl(alibabaBaseUrl)
                .build();
    }

    @Bean
    public WeatherAssistant weatherAssistant(StreamingChatModel streamingChatModel) {

        return AiServices.builder(WeatherAssistant.class)
                .streamingChatModel(streamingChatModel)
                // 高阶ai工具
                .chatMemoryProvider(memoryId -> MessageWindowChatMemory.withMaxMessages(100))
                .tools(weatherToolHandler)
                .build();
    }

}
