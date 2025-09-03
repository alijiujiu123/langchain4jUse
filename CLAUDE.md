# LangChain4j 学习项目工作流程

## 📁 项目结构
- **langchain4j-01helloworld** - LangChain4j 基础入门示例
- **langchain4j-02multi-model-together** - 多模型集成示例
- **langchain4j-03boot-integration** - Spring Boot 集成
- **langchain4j-04low-high-api** - 高低级API使用
- **langchain4j-05model-parameters** - 模型参数配置
- **langchain4j-06chat-image** - 图像聊天模型
- **langchain4j-07chat-stream** - 流式聊天
- **langchain4j-08chat-memory** - 聊天记忆管理
- **langchain4j-09chat-prompt** - 提示词模板
- **langchain4j-10chat-persistence** - 聊天持久化
- **langchain4j-11chat-functioncalling** - 函数调用
- **langchain4j-12chat-embedding** - 嵌入向量
- **langchain4j-13chat-rag01** - RAG检索增强生成
- **langchain4j-14chat-mcp** - MCP集成
- **Z-interview-robot** - 面试机器人项目

## 🛠️ 技术栈
- Java 21
- Spring Boot 3.5.0
- LangChain4j 1.0.1
- Spring AI 1.0.0
- Qdrant 向量数据库
- Redis 内存存储

## 🚀 常用命令
- `mvn clean compile` - 编译整个项目
- `mvn spring-boot:run` - 运行Spring Boot应用
- `mvn clean install` - 安装依赖并编译
- `mvn test` - 运行测试

## 📋 模块运行
```bash
# 运行特定模块
cd langchain4j-01helloworld
mvn spring-boot:run

# 编译特定模块
cd langchain4j-02multi-model-together
mvn clean compile
```

## ⚠️ 注意事项
- 确保所有文件使用UTF-8编码
- 检查pom.xml依赖配置
- 配置API密钥和环境变量
- 需要Java 21或更高版本
- 需要配置Qdrant和Redis服务

## 🔑 环境配置
在 `application.yml` 中配置:
- OpenAI API密钥
- 阿里云百炼平台密钥
- Qdrant连接信息
- Redis连接信息

## 📚 学习资源
- 查看每个模块的README了解具体功能
- 参考 langchain4j-01helloworld/LearnLangchain4j.md 学习基础