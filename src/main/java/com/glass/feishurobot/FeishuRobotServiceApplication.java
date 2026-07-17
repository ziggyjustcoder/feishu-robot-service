package com.glass.feishurobot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class FeishuRobotServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FeishuRobotServiceApplication.class, args);
    }

}
