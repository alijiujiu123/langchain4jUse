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
@StructuredPrompt("请使用{{language}}回答以下问题: {{question}}, 以总-分-总的形式, 字数控制在{{length}}以内, 以{{format}}返回结果")
public class ProgramerPrompt {
    private String question;
    private int length;
    private String format;
    private String language;
}
