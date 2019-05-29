package com.example.study.thread;

/**
 * @author F.W
 * @date 2019/5/28 16:09
 * interrupt之后不会执行for循环外的方法体
 */
public class TestInterrupt extends Thread {

    @Override
    public void run() {
        super.run();
        try {
            for (int i = 0; i < 500000; i++) {
                if (this.isInterrupted()) {
                    System.out.println("------线程正在停止中-------");
                    throw new InterruptedException();
                }
                System.out.println("i----" + i);
            }
            System.out.println("for循环之外的执行体");
        } catch (InterruptedException ex) {
            System.out.println("---异常抓取---");
        }

    }
}

class TestDemo {
    public static void main(String[] args) throws InterruptedException {
        TestInterrupt testInterrupt = new TestInterrupt();
        testInterrupt.start();
        System.out.println(testInterrupt.getPriority());
        Thread.sleep(10);
        testInterrupt.interrupt();
        System.out.println("end");
    }
}

/**
 * interrupt之后会执行for循环外的方法体
 */
class TestInterruptTwo extends Thread {

    @Override
    public void run() {
        super.run();
        for (int i = 0; i < 500000; i++) {
            if (this.isInterrupted()) {
                System.out.println("------线程正在停止中-------");
                break;
            }
            System.out.println("i----" + i);
        }
        System.out.println("for循环之外的执行体");
    }
}