package org.example.entity;

import dev.langchain4j.model.input.structured.StructuredPrompt;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@StructuredPrompt("请回答以下问题: {{question}}, 以总-分-总的形式, 字数控制在{{length}}以内, 以{{format}}返回结果，并且使用{{language}}语言描述")
public class ProgramerPrompt {
    private String question;
    private int length;
    private String format;
    // TODO 为什么这个字段="英文",会导致"抱歉，我只能回答编程技术相关的问题"
    private String language;
}
