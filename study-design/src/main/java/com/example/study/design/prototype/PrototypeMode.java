package com.example.study.design.prototype;

/**
 * 原型模式(浅克隆)
 *
 * @author F.W
 * @date 2019/5/15 14:29
 */
public class PrototypeMode implements Cloneable {
    /**
     * 构造方法
     */
    PrototypeMode() {
        System.out.println("具体原型创建成功1");
    }

    public Object clone() throws CloneNotSupportedException {
        System.out.println("具体原型复制成功2");
        return super.clone();
    }
}
