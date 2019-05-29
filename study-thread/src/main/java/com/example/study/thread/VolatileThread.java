package com.example.study.thread;

import ch.qos.logback.core.net.SyslogOutputStream;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author F.W
 * @date 2019/5/29 15:15
 */
public class VolatileThread extends Thread {
    public static AtomicInteger count = new AtomicInteger(0);
    public static int j;
    private static void addCount() {
        for (int i = 0; i < 100; i++) {
            count.addAndGet(1);
            j++;
        }
        System.out.println(count);
    }

    @Override
    public void run() {
        addCount();
    }
}

class VolatileThreadTest {
    public static void main(String[] args) {
        try {
            VolatileThread[] volatileThreads = new VolatileThread[100];
            for (int i = 0; i < 100; i++) {
                volatileThreads[i] = new VolatileThread();
            }
            for (int i = 0; i < 100; i++) {
                volatileThreads[i].start();
            }
            Thread.sleep(6000);
            System.out.println();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
