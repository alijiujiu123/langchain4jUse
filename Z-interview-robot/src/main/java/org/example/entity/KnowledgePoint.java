package org.example.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 面试题知识点
 * @version 1.0
 * @date 2025/8/5 19:56
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class KnowledgePoint {
    /**
     * 知识点描述
     */
    private String description;
    /**
     * 知识点名称
     */
    private String name;
    /**
     * 知识点得分
     */
    private int score;
}
