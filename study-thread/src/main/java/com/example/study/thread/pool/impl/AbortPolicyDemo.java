package com.example.study.thread.pool.impl;

import com.example.study.thread.pool.RejectedExecutionHandlerDemo;
import com.example.study.thread.pool.ThreadPoolDemo;

import java.util.concurrent.RejectedExecutionException;

/**
 * 拒绝策略 抛出异常
 */
public class AbortPolicyDemo implements RejectedExecutionHandlerDemo {

    public AbortPolicyDemo() {
    }

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolDemo executor) {
        throw new RejectedExecutionException("Task " + r.toString() +
                " rejected from " +
                executor.toString());
    }
}
