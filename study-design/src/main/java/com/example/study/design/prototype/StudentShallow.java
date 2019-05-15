package com.example.study.design.prototype;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 浅克隆(引用对象不会改变)
 *
 * @author F.W
 * @date 2019/5/15 15:00
 */
@Data
@AllArgsConstructor
public class StudentShallow implements Cloneable {

    private String name;
    private Integer age;
    private People likePeople;


    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public void display() {
        System.out.println("我的名字:" + name + "  年龄：" + age + "     喜欢:" + likePeople.getName());
    }
}
