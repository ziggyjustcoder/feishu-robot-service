package com.glass.feishurobot.service.lark;

import com.glass.feishurobot.common.config.LarkRobotConfig;
import com.lark.oapi.event.EventDispatcher;
import com.lark.oapi.ws.Client;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class MsgReceiveUtil {
    private final LarkRobotConfig config;
    private final CustomReceiveHandler customReceiveHandler;

    public MsgReceiveUtil(LarkRobotConfig config,
                          CustomReceiveHandler customReceiveHandler) {
        this.config = config;
        this.customReceiveHandler = customReceiveHandler;
    }

    @Bean
    public Client larkReciveClient() {
        EventDispatcher eventHandler = EventDispatcher.newBuilder("", "")
                .onP2MessageReceiveV1(customReceiveHandler)
                .build();
        Client client = new Client.Builder(config.getAppid(), config.getAppSecret())
                .eventHandler(eventHandler)
                .build();
        client.start();
        return client;
    }
}
