package org.example.service;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import org.example.entity.ProgramerPrompt;
import reactor.core.publisher.Flux;

/**
 * 编程助手
 * @version 1.0
 * @date 2025/8/3 19:46
 */
public interface ProgramerAssistant {

    /**
     * 系统提示词与用户提示词结合
     * 系统提示词，优先与用于提示词
     */
    @SystemMessage("你是一个编程专家，只回答与编程技术相关的问题。" +
            "输出限制: 对于其他领域的问题禁止回答，直接返回'抱歉，我只能回答编程技术相关的问题'。")
    @UserMessage("请回答以下问题: {{question}}, 以总-分-总的形式, 字数控制在{{length}}以内, 以{{format}}返回结果")
    Flux<String> chat(@V("question") String question, @V("length") int length, @V("format") String format);

    /**
     * description  结构化提示词
     * date         2025/8/3 20:11
     * @param       programerPrompt 结构化提示词
     * @return      reactor.core.publisher.Flux<java.lang.String>
     */
    @SystemMessage("你是一个编程专家，使用用户指定的自然语言，只回答与编程技术相关的问题。" +
            "输出限制: 对于其他领域的问题禁止回答，直接返回'抱歉，我只能回答编程技术相关的问题'。")
    Flux<String> chat(ProgramerPrompt programerPrompt);
}
