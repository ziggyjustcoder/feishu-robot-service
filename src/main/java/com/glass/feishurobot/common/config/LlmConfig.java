package com.glass.feishurobot.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import lombok.Data;

@Data
@ConfigurationProperties(prefix = "llm")
public class LlmConfig {

    private String apiKey;
    private String url;
    private String modelName;

}
