package com.glass.feishurobot.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "lark.robot")
public class LarkRobotConfig {

    private String appid;
    private String appSecret;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    @Bean
    public com.lark.oapi.Client LarkReplyClient() {
        return new com.lark.oapi.Client.Builder(appid, appSecret).build();
    }
}
