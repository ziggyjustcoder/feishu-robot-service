package com.glass.feishurobot.common.llm;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.glass.feishurobot.common.config.LlmConfig;
import com.glass.feishurobot.service.assistant.TripAi;
import com.glass.feishurobot.tools.TripMcpService;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;

/**
 * 扶摇大模型客户端配置
 * <p>
 * 构建 {@link OpenAiChatModel} Bean，并通过 {@link AiServices} 将 {@link TripMcpService}
 * 中用 {@code @Tool} 声明的方法注册为模型可调度的工具。
 * 这样在 {@link TripAi} 调用时，模型可自主决定调用哪些行程查询工具，
 * 并在必要时循环执行多次工具调用后给出最终回答。
 * </p>
 */
@Configuration
@EnableConfigurationProperties(LlmConfig.class)
public class FuyaoService {

    @Bean
    ChatModel fuyaoKimi(LlmConfig llmConfig) {
        return OpenAiChatModel.builder()
                .apiKey(llmConfig.getApiKey())
                .baseUrl(llmConfig.getUrl())
                .modelName(llmConfig.getModelName())
                .build();
    }

    /**
     * 将 TripMcpService 暴露为可被大模型调用的工具集，并绑定对话记忆。
     * AiServices 负责在每轮对话中：
     *  1. 把 @Tool 方法转换为 ToolSpecification 发给模型；
     *  2. 解析模型返回的 tool_calls 并反射执行对应方法；
     *  3. 把执行结果再喂回模型，直到模型生成最终文本回复。
     */
    @Bean
    TripAi tripAi(ChatModel fuyaoKimi, TripMcpService tripMcpService) {
        return AiServices.builder(TripAi.class)
                .chatModel(fuyaoKimi)
                .chatMemory(MessageWindowChatMemory.withMaxMessages(20))
                .tools(tripMcpService)
                .build();
    }

}
