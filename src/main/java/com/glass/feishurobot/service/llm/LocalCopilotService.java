package com.glass.feishurobot.service.llm;

import dev.langchain4j.model.openai.OpenAiChatModel;
import org.springframework.stereotype.Service;

@Service
public class LocalCopilotService {

    public LocalCopilotService() {
        OpenAiChatModel model = OpenAiChatModel.builder()
                .apiKey("dummy")
                .baseUrl("http://localhost:4141/") // 关键：指向你的本地服务
                .modelName("gpt-5-mini")    // 填你列表里的模型名
                .build();
    }

    public static void main(String[] args) {
        OpenAiChatModel model = OpenAiChatModel.builder()
                .apiKey("dummy")
                .baseUrl("http://localhost:4141/") // 关键：指向你的本地服务
                .modelName("gpt-5-mini")    // 填你列表里的模型名
                .build();

        String generate = model.chat("你好，请自我介绍一下。");
        System.out.println(generate);
    }
}
