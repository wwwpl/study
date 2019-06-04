package com.example.study.thread;

/**
 * @author F.W
 * @date 2019/6/3 11:16
 */
public class ThrealParmTest {

    public static void main(String[] args) throws InterruptedException {
        ThreadLocalA a = new ThreadLocalA();
        a.start();
        a.join(100);
        ThreadLocalB b = new ThreadLocalB();
        b.start();
    }
}

class ThreadLocalText extends ThreadLocal {
    @Override
    protected Object initialValue() {
        return "init-" + System.currentTimeMillis();
    }
}

class Tools {
    public static ThreadLocalText t1 = new ThreadLocalText();
}

class ThreadLocalA extends Thread {
    @Override
    public void run() {
        System.out.println(Tools.t1.get());
    }
}

class ThreadLocalB extends Thread {
    @Override
    public void run() {
        System.out.println(Tools.t1.get());
    }
}