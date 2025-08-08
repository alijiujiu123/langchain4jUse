package org.example.controller;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.parser.apache.tika.ApacheTikaDocumentParser;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.IngestionResult;
import io.qdrant.client.QdrantClient;
import io.qdrant.client.grpc.Collections;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.example.aiService.ChatAssistant;
import org.example.aiService.GeneralAssistant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;

import java.io.IOException;

/**
 * RAG检索控制器
 * @version 1.0
 * @date 2025/8/7 16:08
 */
@Slf4j
@RestController
@RequestMapping("/rag")
public class RAGController {

    @Resource
    private EmbeddingStore<TextSegment> embeddingStore;
    @Resource
    private ChatAssistant chatAssistant;
    @Resource
    private GeneralAssistant generalAssistant;

    @Resource
    private QdrantClient qdrantClient;
    @Value("${vector.qdrant.collectionName}")
    private String collectionName;

    /**
     * description  创建qdrant collection
     * date         2025/8/7 16:20
     * @return      java.lang.String
     */
    @GetMapping("/addCollection")
    public String addCollection() {
        log.info("创建collection");
        Collections.VectorParams vectorParams = Collections.VectorParams.newBuilder()
                .setSize(384)
                .setDistance(Collections.Distance.Cosine)
                .build();
        qdrantClient.createCollectionAsync(collectionName, vectorParams);
        return collectionName+" 创建成功";
    }

    /**
     * description  添加文件到向量数据库
     * date         2025/8/7 16:08
     * @param       file
     * @return      java.lang.String
     */
    @PostMapping("/addFile")
    public String addFile(@RequestParam MultipartFile file) throws IOException {
        // 1. 文件流生成文档
        Document document = new ApacheTikaDocumentParser().parse(file.getInputStream());
        // 2. 文档存储向量数据库
        IngestionResult ingest = EmbeddingStoreIngestor.ingest(document, embeddingStore);
        log.info("向量数据库存储成功，向量数量：{}", ingest);
        return "success";
    }

    @GetMapping("/chat")
    public Flux<String> chat(@RequestParam(value = "prompt", defaultValue = "你是谁") String prompt) {
        return chatAssistant.chat(prompt);
    }

    @GetMapping("/chatGeneral")
    public Flux<String> chatGeneral(@RequestParam(value = "prompt", defaultValue = "你是谁") String prompt) {
        return generalAssistant.chat(prompt);
    }
}
