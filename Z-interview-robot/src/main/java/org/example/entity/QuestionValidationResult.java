package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 面试题蒸馏后的数据结构
 * @version 1.0
 * @date 2025/8/5 19:53
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionValidationResult {
    /**
     * 是否有效面试题
     */
    private boolean isValid;
    /**
     * 无效时给出的原因
     */
    private String reason;
    /**
     *  interviewer 输出的题干
     */
    private String title;
    /**
     *  interviewer 输出的答案
     */
    private String answer;
    /**
     *  面试题分值
     */
    private Score score;
    /**
     *  面试题元数据
     */
    private Metadata metadata;
}
