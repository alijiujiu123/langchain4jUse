package org.example.config;

import dev.langchain4j.mcp.McpToolProvider;
import dev.langchain4j.mcp.client.DefaultMcpClient;
import dev.langchain4j.mcp.client.McpClient;
import dev.langchain4j.mcp.client.transport.McpTransport;
import dev.langchain4j.mcp.client.transport.http.HttpMcpTransport;
import dev.langchain4j.mcp.client.transport.stdio.StdioMcpTransport;
import dev.langchain4j.service.tool.ToolProvider;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

/**
 * MCP Client 配置
 * @version 1.0
 * @date 2025/8/10 19:33
 */
@Configuration
public class MCPClientConfig {

    @Value("${mcp.AMAP}")
    private String amapApiKey;
    @Value("${mcp.GIT_ACCESS_KEY}")
    private String gitAccessKey;
    @Value("${mcp.atlassianEnvPath}")
    private String atlassianEnvPath;

    /**
     * description  构建高德地图McpTransport
     * date         2025/8/10 19:55
     * @param
     * @return      dev.langchain4j.mcp.client.transport.McpTransport
     */
    @Bean
    public McpTransport ampMcpTransport() {
        return new StdioMcpTransport.Builder()
                .command(List.of("npm", "exec", "@amap/amap-maps-mcp-server"))
                .environment(Map.of("AMAP_MAPS_API_KEY", amapApiKey))
                .build();
//        return new HttpMcpTransport.Builder()
//                .sseUrl("https://mcp.amap.com/sse?key="+amapApiKey)
//                .logRequests( true)
//                .logResponses( true)
//                .build();
    }

    /**
     * description  构建McpClient
     * date         2025/8/10 19:55
     * @return      dev.langchain4j.mcp.client.McpClient
     */
    @Bean
    public McpClient amapMcpClient(McpTransport ampMcpTransport) {
        return new DefaultMcpClient.Builder()
                .transport(ampMcpTransport)
                .build();
    }

    /**
     * description  docker 运行GitMcpClient
     * date         2025/9/1 15:29
     * @param
     * @return      dev.langchain4j.mcp.client.transport.McpTransport
     */
    @Bean
    public McpTransport dockerGitMcpTransport() {
        return new StdioMcpTransport.Builder()
                .command(List.of("/usr/local/bin/docker", "run", "-e", gitAccessKey, "-i", "mcp/github"))
                .build();
    }

    @Bean
    public McpClient dockerGitMcpClient(McpTransport dockerGitMcpTransport) {
        return new DefaultMcpClient.Builder()
                .transport(dockerGitMcpTransport)
                .build();
    }


    /**
     * description  docker 运行AtlassianMcpClient
     * date         2025/9/1 15:29
     * @param
     * @return      dev.langchain4j.mcp.client.transport.McpTransport
     */
//    @Bean
//    public McpTransport dockerAtlassianMcpTransport() {
//        return new StdioMcpTransport.Builder()
//                .command(List.of("/usr/local/bin/docker", "run", "--rm", "-it",
//                        "--env-file", atlassianEnvPath,
//                        "ghcr.io/sooperset/mcp-atlassian:latest"))
//                .build();
//    }
//
//    @Bean
//    public McpClient dockerAtlassianMcpClient(McpTransport dockerAtlassianMcpTransport) {
//        return new DefaultMcpClient.Builder()
//                .transport(dockerAtlassianMcpTransport)
//                .build();
//    }
    @Bean
    public McpTransport dockerAtlassianMcpTransport() {
        return new HttpMcpTransport.Builder()
                .sseUrl("http://localhost:9000/sse")
                .logRequests(true) // if you want to see the traffic in the log
                .logResponses(true)
                .build();
    }

    @Bean
    public McpClient dockerAtlassianMcpClient(McpTransport dockerAtlassianMcpTransport) {
        return new DefaultMcpClient.Builder()
                .transport(dockerAtlassianMcpTransport)
                .build();
    }

    @Bean
    public ToolProvider toolProvider(McpClient amapMcpClient, McpClient dockerGitMcpClient, McpClient dockerAtlassianMcpClient) {
        return McpToolProvider.builder()
                .mcpClients(List.of(amapMcpClient, dockerGitMcpClient, dockerAtlassianMcpClient))
                .build();
    }
}
