package org.example.service;

import org.example.entity.QuestionValidationResult;

/**
 * 面试题数据存储服务（向量数据库）
 * @version 1.0
 * @date 2025/8/5 20:26
 */
public interface QuestionEmbedService {
    /**
     * description  TODO
     * date         2025/8/5 20:27
     * @author      geshishuai
     * @param       userId 用户id
     * @param       validationQuestion  合法问题
     * @return      数据id
     * The auto-generated ID associated with the added embedding.
     */
    String addQuestion(Long userId, QuestionValidationResult validationQuestion);
}
