package com.example.study.thread.test;

import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

/**
 * 自定义线程
 *
 * @author wangfei52
 * @date 2019年11月19日15:46:13
 */
public class CustomThread {

    private static List topics = new ArrayList();
    private static Integer size = 0;
    private static List runTopic = new ArrayList();
    private static List<Topic> threadList = new ArrayList<>();

    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(32, //核心线程数
            128, 15, //线程池所能容纳的最大线程数   非核心线程的闲置超时时间
            TimeUnit.SECONDS, //
            new ArrayBlockingQueue<Runnable>(512),//
            new ThreadPoolExecutor.CallerRunsPolicy());//


    static void startThread(String name) {
        Topic topic = new Topic(name);
        threadList.add(topic);
        executor.execute(topic);
    }

    static void startRun() {
        System.out.println(executor.getActiveCount());
        if (size != topics.size()) {
            size = topics.size();
            for (Object topic : topics) {
                if (runTopic.contains(topic)) {
                    System.out.println("线程正在运行");
                } else {
                    startThread(String.valueOf(topic));
                    runTopic.add(topic);
                }
            }
            if (CollectionUtils.isEmpty(threadList)) {
                return;
            }
            Iterator<Topic> it = threadList.iterator();
            while (it.hasNext()) {
                Topic a = it.next();
                if (!topics.contains(a.getName())) {
                    a.topic = false;
                    it.remove();
                }
            }
            System.out.println(executor.getActiveCount());
        } else {
            System.out.println("topic没有变化");
        }

    }


    public static void main(String[] args) {

        topics.add("A");
        startRun();
        topics.remove("A");
        topics.add("B");
        topics.add("C");
        startRun();
        startRun();
        startRun();
        startRun();
        startRun();
        try {
            sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        startRun();
        startRun();
        startRun();
        startRun();
    }


    static class Topic extends Thread {

        private boolean topic = true;

        Topic(String threadName) {
            super(threadName);
        }

        @Override
        public void run() {
            if (topic) {

            }
        }
    }

}
