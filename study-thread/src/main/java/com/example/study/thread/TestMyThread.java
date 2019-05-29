package com.example.study.thread;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author F.W
 * @date 2019/5/28 15:05
 */
public class TestMyThread extends Thread {

    AtomicInteger count = new AtomicInteger(10);
    int a = 10;

    @Override
    synchronized public void run() {
        super.run();
        count.getAndDecrement();
        a--;
        System.out.println(count+"-------"+a);
    }

}
class Test{
    public static void main(String[] args) {
        TestMyThread testMyThread = new TestMyThread();
        Thread a = new Thread(testMyThread,"A");
        Thread b = new Thread(testMyThread,"B");
        Thread c = new Thread(testMyThread,"C");
        Thread d = new Thread(testMyThread,"D");
        Thread e = new Thread(testMyThread,"E");
        Thread f = new Thread(testMyThread,"F");
        Thread g = new Thread(testMyThread,"G");
        Thread h = new Thread(testMyThread,"H");
        a.start();
        b.start();
        c.start();
        d.start();
        e.start();
        f.start();
        h.start();
        g.start();
//        a.interrupt();
//        a.isInterrupted();

    }

}