package org.example.controller;

import cn.hutool.core.date.DateUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.example.aiAssistant.InvoiceAssistant;
import org.example.aiAssistant.WeatherAssistant;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@Slf4j
@RestController
@RequestMapping("/api/chatFunctioncalling")
public class ChatFunctioncallingController {

    @Resource
    private InvoiceAssistant invoiceAssistant;
    @Resource
    private WeatherAssistant weatherAssistant;


    /**
     * 开票助理
     * @version 1.0
     * @date 2025/8/4 17:50
     */
    @GetMapping("/invoice")
    public String invoice(@RequestParam (value = "prompt", defaultValue = "你是谁") String prompt) {
        log.info("invoice.prompt: {}", prompt);
        String result = invoiceAssistant.chat(prompt);
        log.info("invoice.result: {}", result);
        return "success : "+ DateUtil.now() + "\t"+result;

    }

    @GetMapping("/weather")
    public Flux<String> weather(
            @RequestParam(value = "memoryId", defaultValue = "1") Long memoryId,
            @RequestParam (value = "prompt", defaultValue = "你是谁") String prompt) {
        log.info("weather.prompt: {}", prompt);
        return weatherAssistant.chat(memoryId, prompt);
    }
}
