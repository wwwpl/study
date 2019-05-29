package com.example.study.thread;

/**
 * @author F.W
 * @date 2019/5/29 13:58
 */
public class DieThread extends Thread {

    public String username;
    public Object lock1 = new Object();
    public Object lock2 = new Object();

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public void run() {
        if ("A".equals(username)) {
            synchronized (lock1) {
                try {
                    System.out.println("username:" + username);
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lock2) {
                    System.out.println("按lock1→lock2代码的顺序执行");
                }
            }
        }
        if ("B".equals(username)) {
            synchronized (lock2) {
                try {
                    System.out.println("username:" + username);
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lock1) {
                    System.out.println("按lock2→lock1代码的顺序执行");
                }
            }
        }

    }
}

class TestDieThread {
    public static void main(String[] args) {
        try {
            DieThread dieThread = new DieThread();
            dieThread.setUsername("A");
            Thread thread1 = new Thread(dieThread);
            thread1.start();
            Thread.sleep(100);
            dieThread.setUsername("B");
            Thread thread2 = new Thread(dieThread);
            thread2.start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
