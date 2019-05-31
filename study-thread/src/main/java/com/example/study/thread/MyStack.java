package com.example.study.thread;

import java.util.ArrayList;
import java.util.List;

/**
 * @author F.W
 * @date 2019/5/30 15:21
 */
public class MyStack {

    private List list = new ArrayList();

    synchronized public void push() {
        try {
            while (list.size() == 1) {
                this.wait();
            }
            list.add("Math Random :" + Math.random());
            this.notifyAll();
            System.out.println("push :" + list.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    synchronized public String pull() {
        String returnValue = "";
        try {
            while (list.size() == 0) {
                System.out.println("pull :" + Thread.currentThread().getName() + " 线程呈wait状态");
                this.wait();
            }
            returnValue = "" + list.get(0);
            list.remove(0);
            this.notifyAll();
            System.out.println("pull :" + list.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnValue;
    }
}

class PushMyStack {
    private MyStack myStack;

    public PushMyStack(MyStack myStack) {
        super();
        this.myStack = myStack;
    }

    public void pushService() {
        myStack.push();
    }
}

class PullMyStack {
    private MyStack myStack;

    public PullMyStack(MyStack myStack) {
        super();
        this.myStack = myStack;
    }

    public void pushService() {
        System.out.println("pull=" + myStack.pull());
    }
}

class PushMyStackThread extends Thread {
    private PushMyStack pushMyStack;

    public PushMyStackThread(PushMyStack pushMyStack) {
        super();
        this.pushMyStack = pushMyStack;
    }

    @Override
    public void run() {
        while (true) {
            pushMyStack.pushService();
        }
    }
}

class PullMyStackThread extends Thread {
    private PullMyStack pullMyStack;

    public PullMyStackThread(PullMyStack pullMyStack) {
        super();
        this.pullMyStack = pullMyStack;
    }

    @Override
    public void run() {
        while (true) {
            pullMyStack.pushService();
        }
    }
}

class TestMyStack {
    public static void main(String[] args) {
        MyStack myStack = new MyStack();
        PullMyStack pullMyStack = new PullMyStack(myStack);
        PushMyStack pushMyStack = new PushMyStack(myStack);
        PushMyStackThread pushMyStackThread = new PushMyStackThread(pushMyStack);
        PullMyStackThread pullMyStackThread = new PullMyStackThread(pullMyStack);
        pushMyStackThread.start();
        pullMyStackThread.start();
        try {
            pushMyStackThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}