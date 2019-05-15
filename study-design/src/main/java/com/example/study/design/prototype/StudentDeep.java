package com.example.study.design.prototype;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 深克隆（引用对象会clone）
 *
 * @author F.W
 * @date 2019/5/15 15:00
 */
@Data
@AllArgsConstructor
public class StudentDeep implements Cloneable {

    private String name;
    private Integer age;
    private People likePeople;


    @Override
    public Object clone() throws CloneNotSupportedException {

        StudentDeep student = (StudentDeep) super.clone();
        student.likePeople = (People) student.getLikePeople().clone();
        return student;
    }

    public void display() {
        System.out.println("我的名字:" + name + "  年龄：" + age + "     喜欢:" + likePeople.getName());
    }
}
