package com.glass.feishurobot.service.lark;

import com.google.gson.JsonParser;
import com.lark.oapi.Client;
import com.lark.oapi.core.utils.Jsons;
import com.lark.oapi.service.im.v1.model.ReplyMessageReq;
import com.lark.oapi.service.im.v1.model.ReplyMessageReqBody;
import com.lark.oapi.service.im.v1.model.ReplyMessageResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@Component
public class MsgReplyUtil {
    private final Logger log = LoggerFactory.getLogger(MsgReplyUtil.class);

    private final Client larkReplyClient;

    public MsgReplyUtil(Client larkReplyClient) {
        this.larkReplyClient = larkReplyClient;
    }

    public void replyMsg(String msgId, String content) throws Exception {
        Map<String, String> text = Map.of("text", content);
        // 创建请求对象
        ReplyMessageReq req = ReplyMessageReq.newBuilder()
                .messageId(msgId)
                .replyMessageReqBody(ReplyMessageReqBody.newBuilder()
                        .content(Jsons.DEFAULT.toJson(text))
                        .msgType("text")
                        .replyInThread(true)
                        //.uuid("选填，每次调用前请更换，如a0d69e20-1dd1-458b-k525-dfeca4015204")
                        .build())
                .build();

        // 发起请求
        ReplyMessageResp resp = larkReplyClient.im().v1().message().reply(req);

        // 处理服务端错误
        if (!resp.success()) {
            log.error("code:{},msg:{},reqId:{}, resp:{}", resp.getCode(), resp.getMsg(), resp.getRequestId(), Jsons.createGSON(true, false).toJson(JsonParser.parseString(new String(resp.getRawResponse().getBody(), StandardCharsets.UTF_8))));
        }
    }

}
