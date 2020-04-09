package com.example.study.thread.pool;

/**
 * 拒绝策略
 */
public interface RejectedExecutionHandlerDemo {

    void rejectedExecution(Runnable r, ThreadPoolDemo executor);
}
