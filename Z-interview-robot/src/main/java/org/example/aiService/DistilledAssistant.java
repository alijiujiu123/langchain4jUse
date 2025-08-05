package org.example.aiService;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import org.example.entity.QuestionValidationResult;

public interface DistilledAssistant {
    @SystemMessage("你是一个面试经验丰富的 编程技术专家和人工智能助手。你的任务是对输入的面试题进行内容过滤、知识点提取、回答优化与标签结构化。" +
            "所有的返回数据只允许最终要求的json格式。")
    @UserMessage("""
            【输入说明】原始题目内容如下：
            {{question}}
            
            【任务要求】
            你的任务包括：
            一、面试题蒸馏：
            1. 过滤内容不佳或非编程技术类的题目，若不相关，标记 `isValid=false`，并返回 reason 字段解释，其余字段可置空。
            2. 精炼题干与回答，确保表达简洁但不丢失原始信息。
            3. 拆分出涉及的多个编程相关知识点，并对每个知识点提供补充说明（如原始回答不完善）。
            4. 根据知识点的重要程度（满分100）为每个知识点打分。
            5. 计算该题目的总分（所有知识点得分之和）。
            6. 返回平均分字段（为预留字段，可设为 null）。
            
            二、结构化标签提取：
            1. 根据内容与知识点自动识别并填充以下元数据：
               - 技术点（如集合、线程、JVM 等）
               - 难度（初级 / 中级 / 高级）
               - 类型（选择题 / 简答题 / 编程题 / 设计题）
               - 编程语言（如 Java、Python 等）
            
            三、最终返回格式（请严格按照 JSON 格式输出）如下：
            
            ```json
            {
              "isValid": true,
              "title": "原始题干精炼内容",
              "answer": "精炼后的答案",
              "score": {
                "total": 85,
                "average": null,
                "knowledgePoints": [
                  {
                    "name": "JVM 内存模型",
                    "description": "描述该知识点的简要定义与应用",
                    "score": 50
                  },
                  {
                    "name": "GC 算法",
                    "description": "补充说明 GC 类型与适用场景",
                    "score": 35
                  }
                ]
              },
              "metadata": {
                "topics": ["JVM", "GC"],
                "difficulty": "中级",
                "type": "简答题",
                "language": "Java"
              }
            }
            """)
    QuestionValidationResult chat(@V("question") String question);
}
