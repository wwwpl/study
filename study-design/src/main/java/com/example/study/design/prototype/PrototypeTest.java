package com.example.study.design.prototype;

/**
 * @author F.W
 * @date 2019/5/15 14:51
 */
public class PrototypeTest {

    public static void main(String[] args) throws CloneNotSupportedException {
        PrototypeMode obj1 = new PrototypeMode();
        PrototypeMode obj2 = (PrototypeMode) obj1.clone();
        System.out.println(obj1==obj2);
    }
}
