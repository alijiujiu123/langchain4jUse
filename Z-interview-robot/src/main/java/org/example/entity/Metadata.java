package org.example.entity;

import lombok.Data;

import java.util.List;

/**
 * 向量数据库的标签数据
 * @version 1.0
 * @date 2025/8/5 19:57
 */
@Data
public class Metadata {
    /**
     * 技术点（如集合、线程、JVM 等）
     */
    private List<String> topics;
    /**
     * 难度（初级 / 中级 / 高级）
     */
    private String difficulty;
    /**
     * 类型（选择题 / 简答题 / 编程题 / 设计题）
     */
    private String type;
    /**
     * 编程语言（如 Java、Python 等）
     */
    private String language;
}
