package org.example.listener;

import dev.langchain4j.model.chat.listener.ChatModelListener;
import dev.langchain4j.model.chat.listener.ChatModelRequestContext;
import dev.langchain4j.model.chat.listener.ChatModelResponseContext;
import dev.langchain4j.model.output.TokenUsage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TokenUseageListeren implements ChatModelListener {

    @Override
    public void onRequest(ChatModelRequestContext request) {
        // 记录请求开始时间
        long l = System.currentTimeMillis();
        request.attributes().put("startTime", l);
        // 格式化请求开始时间
        log.info("大模型调用，开始请求时间:{}", l);
    }

    @Override
    public void onResponse(ChatModelResponseContext response) {
        TokenUsage tokenUsage = response.chatResponse().tokenUsage();
        log.info("本次调用消耗的token:{}", tokenUsage);
        // 打印持续时间
        long timeCost = System.currentTimeMillis() - (Long) response.attributes().get("startTime");
        log.info("本次调用耗时:{}", timeCost);
    }
}
