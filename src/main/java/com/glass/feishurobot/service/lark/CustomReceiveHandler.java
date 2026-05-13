package com.glass.feishurobot.service.lark;

import com.glass.feishurobot.common.llm.task.TempTaskProcessor;
import com.glass.feishurobot.common.tools.AsyncExecutorUtil;
import com.lark.oapi.service.im.ImService;
import com.lark.oapi.service.im.v1.model.P2MessageReceiveV1;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.Semaphore;

@Component
public class CustomReceiveHandler extends ImService.P2MessageReceiveV1Handler {
    @Value("${task.concurren.limit}")
    private Integer taskConcurrentLimit;
    private static final Deque<P2MessageReceiveV1> workDeque = new ConcurrentLinkedDeque<>();
    // 2. 信号量：控制最大并发数为 3
    private final Semaphore semaphore;
    private final TempTaskProcessor tempTaskProcessor;
    private final MsgReplyUtil msgReplyUtil;

    public CustomReceiveHandler(TempTaskProcessor tempTaskProcessor, MsgReplyUtil msgReplyUtil) {
        // 启动后台调度线程（使用虚拟线程）
        Thread.startVirtualThread(this::scheduleLoop);
        this.tempTaskProcessor = tempTaskProcessor;
        this.msgReplyUtil = msgReplyUtil;
        semaphore = new Semaphore(taskConcurrentLimit);
    }

    @Override
    public void handle(P2MessageReceiveV1 event) throws Exception {
        workDeque.addLast(event);
    }

    private void scheduleLoop() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                // 如果没有信号量，这里会阻塞等待
                semaphore.acquire();
                // 从队列拿任务（如果队列为空，这里也会阻塞）
                P2MessageReceiveV1 msg = workDeque.poll();
                AsyncExecutorUtil.getInstance().executeAsync(() -> {
                    // 异步执行任务
                    if (msg != null) {
                        String handle = tempTaskProcessor.handle(msg.getEvent().getMessage().getContent());
                        try {
                            msgReplyUtil.replyMsg(msg.getEvent().getMessage().getMessageId(), handle);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }

                });
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            } finally {
                semaphore.release();
            }
        }
    }
}
