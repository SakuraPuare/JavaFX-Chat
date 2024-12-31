package com.server.util;

import java.util.concurrent.*;

public class ThreadPoolUtil {
    private static final int CORE_POOL_SIZE = 10;
    private static final int MAX_POOL_SIZE = 50;
    private static final int QUEUE_CAPACITY = 100;
    private static final Long KEEP_ALIVE_TIME = 1L;

    private static final ExecutorService messageExecutor = new ThreadPoolExecutor(
            CORE_POOL_SIZE,
            MAX_POOL_SIZE,
            KEEP_ALIVE_TIME,
            TimeUnit.MINUTES,
            new ArrayBlockingQueue<>(QUEUE_CAPACITY),
            new ThreadPoolExecutor.CallerRunsPolicy()
    );

    public static ExecutorService getMessageExecutor() {
        return messageExecutor;
    }

    public static void shutdown() {
        messageExecutor.shutdown();
    }
} 