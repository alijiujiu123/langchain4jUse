package org.example.service.imp;

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.QuestionValidationResult;
import org.example.service.QuestionEmbedService;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class QuestionEmbedServiceImp implements QuestionEmbedService {

    // 向量化模型
    @Resource
    private EmbeddingModel embeddingModel;
    // 向量数据存储
    @Resource
    private EmbeddingStore<TextSegment> embeddingStore;
    @Override
    public String addQuestion(Long userId, QuestionValidationResult validationQuestion) {
        log.info("添加面试题: userId:{}, validationQuestion: {}", userId, validationQuestion);
        // 2. 向量化

//        TextSegment.from()
//        embeddingModel.embed()
        // 3. 存储
        return "";
    }
}
