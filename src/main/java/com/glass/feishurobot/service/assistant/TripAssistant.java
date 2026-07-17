package com.glass.feishurobot.service.assistant;

import org.springframework.stereotype.Service;
import dev.langchain4j.model.openai.OpenAiChatModel;

/**
 * 行程AI小助手 TripAssistant
 */
@Service
public class TripAssistant {

    private OpenAiChatModel fuyaoKimi;

    public TripAssistant(OpenAiChatModel fuyaoKimi) {
        this.fuyaoKimi = fuyaoKimi;
    }

    public String handle(String userInput) {
        // 这里可以调用 fuyaoKimi 模型进行处理
        // 例如，使用模型生成响应
        String response = fuyaoKimi.chat(userInput);
        return response;
    }


}
