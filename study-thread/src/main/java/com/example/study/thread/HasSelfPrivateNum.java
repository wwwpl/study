package com.example.study.thread;

import java.util.HashSet;
import java.util.Set;

/**
 * @author F.W
 * @date 2019/5/29 9:43
 */
public class HasSelfPrivateNum {

    private int num = 0;

    public void add(String username) {
        try {
            if ("A".equals(username)) {
                num = 100;
                System.out.println("A set over");
                Thread.sleep(100);
            } else {
                num = 200;
                System.out.println("other set over");
            }
            System.out.println(username + "  num=" + num);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class ThreadA extends Thread {

    private HasSelfPrivateNum hasSelfPrivateNum;

    public ThreadA(HasSelfPrivateNum hasSelfPrivateNum) {
        super();
        this.hasSelfPrivateNum = hasSelfPrivateNum;
    }

    @Override
    public void run() {
        super.run();
        hasSelfPrivateNum.add("A");
    }
}


class ThreadB extends Thread {

    private HasSelfPrivateNum hasSelfPrivateNum;

    public ThreadB(HasSelfPrivateNum hasSelfPrivateNum) {
        super();
        this.hasSelfPrivateNum = hasSelfPrivateNum;
    }

    @Override
    public void run() {
        super.run();
        hasSelfPrivateNum.add("B");
    }
}


class TestHasSelfPrivateNum {

    public static void main(String[] args) {
        HasSelfPrivateNum hasSelfPrivateNum = new HasSelfPrivateNum();
        ThreadA threadA = new ThreadA(hasSelfPrivateNum);
        threadA.run();
        ThreadB threadB = new ThreadB(hasSelfPrivateNum);
        threadB.run();
        Set set = new HashSet();
        set.add(11);
    }
}
