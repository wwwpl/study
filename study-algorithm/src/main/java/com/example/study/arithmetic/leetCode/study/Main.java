package com.example.study.arithmetic.leetCode.study;

import java.util.ArrayList;
import java.util.List;

/**
 * 入队和出队。入队会向队列追加一个新元素，而出队会删除第一个元素。 所以我们需要一个索引来指出起点。
 *
 * @author F.W
 * @date 2019/3/19 20:40
 */
class Queue {

    private List<Integer> data;

    private int p_start;

    public Queue() {
        data = new ArrayList<Integer>();
        p_start = 0;
    }

    public boolean enQueue(int x) {
        data.add(x);
        return true;
    }

    public boolean deQueue() {
        if (isEmpty() == true) {
            return false;
        }
        p_start++;
        return true;
    }

    public int front() {
        return data.get(p_start);
    }

    public boolean isEmpty() {
        return p_start >= data.size();
    }


}

public class Main {
    public static void main(String[] args) {
        Queue queue = new Queue();
        queue.enQueue(2);
        queue.enQueue(5);
        if (queue.isEmpty()==false){
            System.out.println(queue.front());
        }
        queue.deQueue();
        if (queue.isEmpty()==false){
            System.out.println(queue.front());
        }
        queue.deQueue();
        if (queue.isEmpty()==false){
            System.out.println(queue.front());
        }
    }
}
