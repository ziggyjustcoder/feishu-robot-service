package com.glass.feishurobot.service.assistant;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;

/**
 * 行程 AI 对话接口
 * <p>
 * 由 {@code AiServices} 在运行时生成代理实现，自动完成：
 * 工具调度、对话记忆维护、多轮工具循环。
 * </p>
 */
public interface TripAi {

    @SystemMessage("你是一位专业的车辆行程售后咨询助手。" +
            "你可以根据用户的问题调用行程查询工具获取真实数据后再作答，" +
            "回答要准确、简洁、基于工具返回的数据。")
    String chat(@UserMessage String userInput);

}
