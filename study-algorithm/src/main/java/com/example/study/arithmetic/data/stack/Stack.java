package com.example.study.arithmetic.data.stack;

/**
 * 栈
 *
 * @author wangfei52
 * @date 2019年11月19日15:10:48
 */
public class Stack {

    private String[] stack;

    private final static Integer SIZE = 5;

    private Integer nowIndex = 0;

    public Stack() {
        this.stack = new String[SIZE];
    }

    boolean push(String value) {
        if (nowIndex > stack.length - 1) {
            System.out.println("栈已满");
            return false;
        }
        stack[nowIndex++] = value;
        return true;
    }

    String pop() {
        if (nowIndex <= 0) {
            System.out.println("空栈");
            return null;
        }
        return stack[--nowIndex];
    }

    public static void main(String[] args) {
        Stack stack = new Stack();
        stack.push("数据1");
        stack.push("数据2");
        stack.push("数据3");
        stack.push("数据4");
        stack.push("数据5");
        stack.push("数据6");
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
    }
}
