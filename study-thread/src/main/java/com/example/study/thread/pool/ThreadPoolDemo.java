package com.example.study.thread.pool;

import com.example.study.thread.pool.impl.AbortPolicyDemo;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 手写线程池 demo
 */
@Slf4j
public class ThreadPoolDemo implements Executor {
    /**
     * 线程name
     */
    private String name;
    /**
     * 核心线程数
     */
    private int coreSize;
    /**
     * 最大线程数
     */
    private int maxSize;
    /**
     * 队列
     */
    private BlockingQueue<Runnable> taskQueue;
    /**
     * 拒绝策略
     */
    private volatile RejectedExecutionHandlerDemo handler;
    /**
     * 默认拒绝策略
     * AbortPolicy 抛出异常
     */
    private static final RejectedExecutionHandlerDemo defaultHandler =
            new AbortPolicyDemo();

    /**
     * 当前正在运行的线程数
     * 需要修改时线程间立即感知，所以使用AtomicInteger
     * 或者也可以使用volatile并结合Unsafe做CAS操作（参考Unsafe篇章讲解）
     */
    private AtomicInteger runningCount = new AtomicInteger(0);

    /**
     * 线程序列号
     */
    private AtomicInteger sequence = new AtomicInteger(0);


    public ThreadPoolDemo(String name, int coreSize, int maxSize, BlockingQueue<Runnable> taskQueue) {
        this.name = name;
        this.coreSize = coreSize;
        this.maxSize = maxSize;
        this.taskQueue = taskQueue;
        this.handler = defaultHandler;
    }

    public ThreadPoolDemo(String name, int coreSize, int maxSize, BlockingQueue<Runnable> taskQueue, RejectedExecutionHandlerDemo handler) {
        this.name = name;
        this.coreSize = coreSize;
        this.maxSize = maxSize;
        this.taskQueue = taskQueue;
        this.handler = handler;
    }

    @Override
    public void execute(Runnable task) {
        if (task == null) {
            throw new NullPointerException("线程为空");
        }
        // 正在运行的线程数
        int count = runningCount.get();
        // 如果正在运行的线程数小于核心线程数，直接加一个线程
        if (count < coreSize) {
            // 注意，这里不一定添加成功，addWorker()方法里面还要判断一次是不是确实小
            if (addWorker(task, true)) {
                return;
            }
            // 如果添加核心线程失败，进入下面的逻辑
        }

        // 如果达到了核心线程数，先尝试让任务入队
        // 这里之所以使用offer()，是因为如果队列满了offer()会立即返回false
        if (taskQueue.offer(task)) {
            // do nothing，为了逻辑清晰这里留个空if
        } else {
            // 如果入队失败，说明队列满了，那就添加一个非核心线程
            if (!addWorker(task, false)) {
                // 如果添加非核心线程失败了，那就执行拒绝策略
                handler.rejectedExecution(task, this);
            }
        }
    }

    public boolean addWorker(Runnable firstTask, boolean core) {
        for (; ; ) {
            //正在运行的线程池数量
            int count = runningCount.get();
            //核心线程还是非核心线程
            int max = core ? coreSize : maxSize;
            if (count >= max) {
                log.info("线程池已满");
                return false;
            }
            if (runningCount.compareAndSet(count, count + 1)) {
                // 线程的名字
                String threadName = (core ? "core_" : "") + name + sequence.incrementAndGet();
                new Thread(() -> {
                    log.info("thread name:{}", Thread.currentThread().getName());
                    // 运行的任务【本篇文章由公众号“彤哥读源码”原创】
                    Runnable task = firstTask;
                    // 不断从任务队列中取任务执行，如果取出来的任务为null，则跳出循环，线程也就结束了
                    while (task != null || (task = getTask()) != null) {
                        try {
                            // 执行任务
                            task.run();
                        } finally {
                            // 任务执行完成，置为空
                            task = null;
                        }
                    }

                }, threadName).start();
                break;
            }
        }
        return true;
    }

    private Runnable getTask() {
        try {
            // take()方法会一直阻塞直到取到任务为止
            return taskQueue.take();
        } catch (InterruptedException e) {
            // 线程中断了，返回null可以结束当前线程
            // 当前线程都要结束了，理应要把runningCount的数量减一
            runningCount.decrementAndGet();
            return null;
        }
    }

}
