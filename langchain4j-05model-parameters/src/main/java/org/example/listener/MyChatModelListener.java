package org.example.listener;

import cn.hutool.core.util.IdUtil;
import dev.langchain4j.model.chat.listener.ChatModelErrorContext;
import dev.langchain4j.model.chat.listener.ChatModelListener;
import dev.langchain4j.model.chat.listener.ChatModelRequestContext;
import dev.langchain4j.model.chat.listener.ChatModelResponseContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MyChatModelListener implements ChatModelListener {

    @Override
    public void onRequest(ChatModelRequestContext requestContext) {
        String uuid = IdUtil.simpleUUID();
        requestContext.attributes().put("TraceId", uuid);
        log.info("[TraceId:{}] onRequest: {}", uuid, requestContext.chatRequest());
    }

    @Override
    public void onResponse(ChatModelResponseContext responseContext) {
        Object uuid = responseContext.attributes().get("TraceId");
        log.info("[TraceId:{}] onResponse: {}", uuid, responseContext.chatResponse());
    }

    @Override
    public void onError(ChatModelErrorContext errorContext) {
        Object uuid = errorContext.attributes().get("TraceId");
        log.error("[TraceId:{}] errorContext: {}", uuid, errorContext);
    }
}
