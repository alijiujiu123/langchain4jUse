package org.example.controller;

import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.output.Response;
import dev.langchain4j.store.embedding.EmbeddingSearchRequest;
import dev.langchain4j.store.embedding.EmbeddingSearchResult;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.filter.comparison.IsEqualTo;
import io.qdrant.client.QdrantClient;
import io.qdrant.client.grpc.Collections;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("/api/embedding")
public class EmbeddingController {

    // 向量大模型: 将文本转换为向量
    @Resource
    private EmbeddingModel embeddingModel;
    // 向量数据库: 存储向量
    @Resource
    private EmbeddingStore<TextSegment> embeddingStore;
    @Autowired
    private QdrantClient qdrantClient;

    @Value("${vector.qdrant.collectionName}")
    private String collectionName;

    /**
     * 将文本转换为向量
     * @param text
     * @return
     */
    @GetMapping("/embedText")
    public String embedText(@RequestParam String text) {
        log.info("文本向量化, text : {}", text);
        Response<Embedding> embed = embeddingModel.embed(text);
        log.info("embed: {}", embed);
        log.info("embed.content: {}", embed.content());
        log.info("embed.vector: {}", embed.content().vectorAsList());
        return embed.content().toString();
    }

    /**
     * description  创建qdrant collection
     * date         2025/8/5 16:42
     * @param
     * @return      java.lang.String
     */
    @GetMapping("/createCollection")
    public String createCollection() {
        log.info("创建collection");
        Collections.VectorParams vectorParams = Collections.VectorParams.newBuilder()
                .setSize(1024)
                .setDistance(Collections.Distance.Cosine)
                .build();
        qdrantClient.createCollectionAsync(collectionName, vectorParams);
        return collectionName+" 创建成功";
    }

    /**
     * description  文本添加向量数据库
     * date         2025/8/5 17:02
     * @param       userId  用户id
     * @param       text    向量化文本
     * @return      java.lang.String
     */
    @GetMapping("/add")
    public String add(@RequestParam Long userId, @RequestParam String text) {
        log.info("添加文本到向量数据库, text:{}", text);
        // 1. 构建数据
        // 创建文本片段
        TextSegment textSegment = TextSegment.from(text);
        // 添加元数据
        textSegment.metadata().put("userId", userId);
        // 2. 向量化
        Embedding embedding = embeddingModel.embed(textSegment).content();
        // 3. 添加到向量数据库: 向量化数据, 元数据
        String result = embeddingStore.add(embedding, textSegment);
        return result;
    }

    /**
     * description  查询向量数据库
     * date         2025/8/5 18:35
     * @param       userId 用户id
     * @param       searchPrompt    搜索提示词
     * @return      java.lang.String
     */
    @GetMapping("/vector/query")
    public String query(@RequestParam(value = "userId", required = false) Long userId,@RequestParam String searchPrompt) {
        // 1. 查询条件向量化数据
        Embedding searchEmbed = embeddingModel.embed(searchPrompt).content();
        // 2. 构建向量化查询条件
        EmbeddingSearchRequest.EmbeddingSearchRequestBuilder embeddingSearchRequestBuilder = EmbeddingSearchRequest.builder()
                .queryEmbedding(searchEmbed)
                .maxResults(1);
        if (userId != null) {
            IsEqualTo isEqualTo = new IsEqualTo("userId", userId);
            embeddingSearchRequestBuilder.filter(isEqualTo);
        }

        // 3. 数据库搜索向量
        EmbeddingSearchResult<TextSegment> searchResult = embeddingStore.search(embeddingSearchRequestBuilder.build());

        if (!searchResult.matches().isEmpty()) {
            return searchResult.matches().get(0).embedded().text();
        }else {
            // todo 问题 这里永远都不会走到，因为只要qdrant中有数据，就会有结果，即使相关度很低
            return "未找到内容";
        }
    }
}
