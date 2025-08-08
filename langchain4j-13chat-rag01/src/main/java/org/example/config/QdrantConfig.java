package org.example.config;

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.qdrant.QdrantEmbeddingStore;
import io.qdrant.client.QdrantClient;
import io.qdrant.client.QdrantGrpcClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 向量数据库配置
 * @version 1.0
 * @date 2025/8/7 15:43
 */
@Configuration
public class QdrantConfig {

    @Value("${vector.qdrant.host}")
    private String host;
    @Value("${vector.qdrant.port}")
    private int port;
    @Value("${vector.qdrant.collectionName}")
    private String collectionName;

    /**
     * description  创建Qdrant客户端
     * date         2025/8/7 15:46
     * @param
     * @return      io.qdrant.client.QdrantClient
     */
    @Bean
    public QdrantClient qdrantClient() {
        QdrantGrpcClient qdrantGrpcClient = QdrantGrpcClient.newBuilder(host, port, false).build();
        return new QdrantClient(qdrantGrpcClient);
    }

    /**
     * description  创建向量化存储实例
     * date         2025/8/7 15:46
     * @param
     * @return      void
     */
    @Bean
    public EmbeddingStore<TextSegment> embeddingStore(QdrantClient qdrantClient) {
        return QdrantEmbeddingStore.builder()
                .client(qdrantClient)
                .collectionName(collectionName)
                .build();
    }
}
