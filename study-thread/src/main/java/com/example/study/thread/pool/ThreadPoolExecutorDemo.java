package com.example.study.thread.pool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

@Slf4j
public class ThreadPoolExecutorDemo {

    static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
            2, 5, 60, TimeUnit.SECONDS
            , new ArrayBlockingQueue<>(1), new RejectedExecutionHandler() {

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            log.info("------:{},{}", r.toString(), executor.toString());
        }
    });
    static ThreadPoolDemo threadPoolDemo = new ThreadPoolDemo(
            "demo-pool",2,5,new ArrayBlockingQueue<>(1));


    public static void main(String[] args) {
        Thread A = new Thread(new SleepThread("A"));
        Thread B = new Thread(new SleepThread("B"));
        Thread C = new Thread(new SleepThread("C"));
        Thread D = new Thread(new SleepThread("D"));
        Thread E = new Thread(new SleepThread("E"));
        Thread F = new Thread(new SleepThread("F"));
        Thread G = new Thread(new SleepThread("G"));
//        threadPoolExecutor.execute(A);
//        threadPoolExecutor.execute(B);
//        threadPoolExecutor.execute(C);
//        threadPoolExecutor.execute(D);
//        threadPoolExecutor.execute(E);
//        threadPoolExecutor.execute(F);
//        threadPoolExecutor.execute(G);
        threadPoolDemo.execute(A);
        threadPoolDemo.execute(B);
        threadPoolDemo.execute(C);
        threadPoolDemo.execute(D);
        threadPoolDemo.execute(E);
        threadPoolDemo.execute(F);
        threadPoolDemo.execute(G);
    }
}
