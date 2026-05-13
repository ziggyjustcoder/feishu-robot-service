package com.glass.feishurobot.common.llm.task;

import org.springframework.stereotype.Component;

@Component
public class TempTaskProcessor {

    public String handle(String prompt) {
        return "服务器繁忙，稍后再试";
    }


}
