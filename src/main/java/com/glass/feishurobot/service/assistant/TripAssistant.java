package com.glass.feishurobot.service.assistant;

import org.springframework.stereotype.Service;

/**
 * 行程 AI 小助手
 * <p>
 * 通过扶摇大模型（fuyao-kimi）对外提供行程问答能力。
 * 底层委托给 {@link TripAi}（由 AiServices 生成的代理），
 * 自动拥有 {@code TripMcpService} 暴露的行程查询工具与多轮对话记忆。
 * </p>
 */
@Service
public class TripAssistant {

    private final TripAi tripAi;

    public TripAssistant(TripAi tripAi) {
        this.tripAi = tripAi;
    }

    public String handle(String userInput) {
        return tripAi.chat(userInput);
    }

}
