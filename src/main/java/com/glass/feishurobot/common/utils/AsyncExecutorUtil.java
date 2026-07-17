package com.glass.feishurobot.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.*;
import java.util.function.Supplier;

/**
 * 异步执行工具类
 *
 * <p>
 * 提供线程池管理和异步任务执行能力，主要用于处理耗时的外部服务调用。
 * </p>
 */
@Component
public class AsyncExecutorUtil implements Closeable {
    private final Logger log = LoggerFactory.getLogger(AsyncExecutorUtil.class);

    private static final AsyncExecutorUtil INSTANCE = new AsyncExecutorUtil();

    /**
     * 线程池配置（针对2核机器优化）
     * IO密集型任务：外部服务调用
     * 核心线程数 = CPU核心数 * 2 = 4
     * 最大线程数 = CPU核心数 * 4 = 8
     */
    private static final int CORE_POOL_SIZE = 4;
    private static final int MAXIMUM_POOL_SIZE = 8;
    private static final long KEEP_ALIVE_TIME = 60L;
    private static final int QUEUE_CAPACITY = 500;
    private static final String THREAD_NAME_PREFIX = "async-executor-";

    private final ThreadPoolExecutor executor;

    private AsyncExecutorUtil() {
        this.executor = new ThreadPoolExecutor(
                CORE_POOL_SIZE,
                MAXIMUM_POOL_SIZE,
                KEEP_ALIVE_TIME,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(QUEUE_CAPACITY),
                new ThreadFactory() {
                    private int counter = 0;

                    @Override
                    public Thread newThread(Runnable r) {
                        // thread.setDaemon(true);
                        return new Thread(r, THREAD_NAME_PREFIX + (++counter));
                    }
                },
                new ThreadPoolExecutor.CallerRunsPolicy()
        );

        log.info("AsyncExecutorUtil initialized with corePoolSize={}, maxPoolSize={}, queueCapacity={}",
                CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, QUEUE_CAPACITY);
    }

    /**
     * 获取单例实例
     */
    public static AsyncExecutorUtil getInstance() {
        return INSTANCE;
    }

    /**
     * 异步执行任务
     *
     * @param supplier 任务执行逻辑
     * @param <T>      返回值类型
     * @return CompletableFuture 包装的异步结果
     */
    public <T> CompletableFuture<T> executeAsync(Supplier<T> supplier) {
        return CompletableFuture.supplyAsync(supplier, executor)
                .whenComplete((result, exception) -> {
                    if (exception != null) {
                        log.error("Async task execution failed", exception);
                    } else {
                        log.debug("Async task completed successfully");
                    }
                });
    }

    /**
     * 异步执行无返回值任务
     *
     * @param runnable 任务执行逻辑
     * @return CompletableFuture 包装的异步结果
     */
    public CompletableFuture<Void> executeAsync(Runnable runnable) {
        return CompletableFuture.runAsync(runnable, executor)
                .whenComplete((result, exception) -> {
                    if (exception != null) {
                        log.error("Async task execution failed", exception);
                    } else {
                        log.debug("Async task completed successfully");
                    }
                });
    }

    /**
     * 获取线程池状态信息
     */
    public long getWQueueStatus() {
        return executor.getTaskCount();
    }

    /**
     * 优雅关闭线程池
     */
    @Override
    public void close() throws IOException {
        if (executor != null && !executor.isShutdown()) {
            log.info("Shutting down AsyncExecutorUtil...");
            executor.shutdown();
            try {
                if (!executor.awaitTermination(30, TimeUnit.SECONDS)) {
                    executor.shutdownNow();
                }
            } catch (InterruptedException e) {
                executor.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
    }
}