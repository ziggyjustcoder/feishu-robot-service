package com.glass.feishurobot.common.llm;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import dev.langchain4j.model.openai.OpenAiChatModel;

@Service
public class FuyaoService {

    @Bean OpenAiChatModel fuyaoKimi() {
        return OpenAiChatModel.builder()
                .apiKey("c7b3283f71b94c99915282d576a622fb")
                .baseUrl("https://fuyao-ai-gateway.xiaopeng.link/v1") // 关键：指向你的本地服务
                .modelName("fuyao-kimi")    // 填你列表里的模型名
                .build();
    }

}
