# LangChain4j 学习项目集

这是一个全面的 LangChain4j 学习项目集合，包含了从基础到高级的各种示例和实战项目。

## 🎯 项目简介

本项目旨在通过实践案例帮助开发者掌握 LangChain4j 的各种功能和应用场景，涵盖了 LLM 集成、向量数据库、函数调用、RAG 等核心概念。

## 📚 项目模块

### 基础模块
- **01helloworld** - LangChain4j 入门示例，基础聊天功能
- **02multi-model-together** - 多模型集成与协同工作
- **03boot-integration** - Spring Boot 框架集成
- **04low-high-api** - 高低级API使用对比
- **05model-parameters** - 模型参数配置与调优

### 进阶模块
- **06chat-image** - 图像聊天模型集成
- **07chat-stream** - 流式聊天响应
- **08chat-memory** - 对话记忆管理
- **09chat-prompt** - 提示词模板与工程
- **10chat-persistence** - 聊天持久化存储

### 高级模块
- **11chat-functioncalling** - 函数调用与外部工具集成
- **12chat-embedding** - 文本嵌入与向量化
- **13chat-rag01** - RAG检索增强生成
- **14chat-mcp** - MCP模型控制协议集成

### 实战项目
- **Z-interview-robot** - 智能面试机器人，支持问题生成和评分

## 🛠️ 技术栈

- **语言**: Java 21
- **框架**: Spring Boot 3.5.0
- **AI框架**: LangChain4j 1.0.1, Spring AI 1.0.0
- **数据库**: Qdrant (向量数据库), Redis (内存存储)
- **模型支持**: OpenAI, 阿里云百炼, 通义千问等

## 🚀 快速开始

### 环境要求
- JDK 21+
- Maven 3.6+
- Qdrant 向量数据库
- Redis 服务器

### 安装运行

1. 克隆项目
```bash
git clone <repository-url>
cd langchain4jUse
```

2. 编译项目
```bash
mvn clean compile
```

3. 运行特定模块
```bash
cd langchain4j-01helloworld
mvn spring-boot:run
```

4. 访问应用
打开浏览器访问 `http://localhost:8080`

## ⚙️ 配置说明

### API密钥配置
在每个模块的 `src/main/resources/application.yml` 中配置：

```yaml
openai:
  api-key: your-openai-api-key
  model-name: gpt-3.5-turbo

alibaba:
  dashscope:
    api-key: your-dashscope-api-key
```

### 数据库配置
```yaml
qdrant:
  host: localhost
  port: 6333
  collection-name: my-collection

redis:
  host: localhost
  port: 6379
```

## 📖 学习路径

1. **初学者**: 从 `01helloworld` 开始，了解基础概念
2. **进阶学习**: 依次学习各模块，掌握不同功能
3. **实战应用**: 研究 `Z-interview-robot` 项目，了解实际应用

## 🎨 功能特性

- ✅ 多模型支持 (OpenAI, 阿里云百炼等)
- ✅ 流式聊天响应
- ✅ 对话记忆管理
- ✅ 函数调用能力
- ✅ 向量检索与RAG
- ✅ Spring Boot 集成
- ✅ RESTful API 接口

## 🔧 开发指南

### 添加新模块
1. 在父pom.xml中添加模块
2. 创建新的模块目录
3. 配置Spring Boot和LangChain4j依赖
4. 实现业务逻辑

### 代码结构
每个模块遵循标准Spring Boot结构：
```
src/main/java/org/example/
├── config/          # 配置类
├── controller/      # REST控制器
├── service/         # 业务服务
├── entity/          # 数据实体
└── *App.java       # 启动类
```

## 🤝 贡献指南

1. Fork 本项目
2. 创建特性分支
3. 提交更改
4. 推送到分支
5. 创建Pull Request

## 📄 许可证

本项目采用 MIT 许可证 - 查看 [LICENSE](LICENSE) 文件了解详情

## 🙏 致谢

- [LangChain4j](https://github.com/langchain4j/langchain4j) - 优秀的Java LLM框架
- [Spring AI](https://spring.io/projects/spring-ai) - Spring生态的AI支持
- [Qdrant](https://qdrant.tech/) - 高性能向量数据库

## 📞 联系方式

如有问题或建议，请通过以下方式联系：
- 提交 Issue
- 发送邮件
- 参与讨论

---

⭐ 如果这个项目对你有帮助，请给它一个星标！