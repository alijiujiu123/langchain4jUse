package org.example.service;

/**
 * chatAssistant接口
 * 具体执行逻辑由langchain4j通过反射实现
 * @author geshishuai
 * @version 1.0
 * @date 2025/8/1 18:57
 */
public interface ChatAssistant {
    String chat(String prompt);
}
