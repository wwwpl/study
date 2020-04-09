package com.example.study.thread.pool;


import static java.lang.Thread.sleep;

public class SleepThread implements Runnable {
    private String name;

    public SleepThread(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        try {
            Thread.currentThread().setName(name);
            sleep(3000);
            Thread t = Thread.currentThread();
            System.out.println("-------" + t.getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
