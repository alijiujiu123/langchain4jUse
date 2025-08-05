package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 面试题的分数
 * @version 1.0
 * @date 2025/8/5 19:55
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Score {
    /**
     * 总分
     */
    private int total;
    /**
     * 平均分
     * 预留字段
     */
    private Double average; // 预留字段
    /**
     * 知识点
     */
    private List<KnowledgePoint> knowledgePoints;
}
