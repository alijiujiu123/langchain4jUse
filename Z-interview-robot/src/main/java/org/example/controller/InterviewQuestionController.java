package org.example.controller;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.example.aiService.DistilledAssistant;
import org.example.entity.QuestionValidationResult;
import org.example.service.QuestionEmbedService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 面试题控制器
 * @version 1.0
 * @date 2025/8/5 18:50
 */
@Slf4j
@RestController
@RequestMapping("/api/interviewQuestion")
public class InterviewQuestionController {

    // 面试题蒸馏模型
    @Resource
    private DistilledAssistant distilledAssistant;

    // 面试题向量化存储服务
    @Resource
    private QuestionEmbedService questionEmbedService;

    @GetMapping("/add")
    public String add(@RequestParam Long userId, @RequestParam String question) {
        log.info("添加面试题到向量数据库, userId: {}, question:{}", userId, question);
        // 1. 面试题蒸馏构建数据
        QuestionValidationResult distilledQuestion = distilledAssistant.chat(question);
        if (!distilledQuestion.isValid()) {
            return "无效面试题, 理由: " + distilledQuestion.getReason();
        }
        String result = questionEmbedService.addQuestion(userId, distilledQuestion);
        return "添加面试题成功, 数据id: " + result;
    }
}
