package com.example.study.design.prototype;

/**
 * @author F.W
 * @date 2019/5/15 15:09
 */
public class StudentTest {

    public static void main(String[] args) throws CloneNotSupportedException {

        StudentDeep obj1 = new StudentDeep("张三", 18,new People("linger"));
        obj1.display();
        StudentDeep obj2 = (StudentDeep) obj1.clone();
        obj2.setName("李四");
        obj2.display();
        System.out.println(obj1.getLikePeople()==obj2.getLikePeople());

        StudentShallow obj3 = new StudentShallow("张三", 18,new People("linger"));
        obj1.display();
        StudentShallow obj4 = (StudentShallow) obj3.clone();
        obj2.setName("李四");
        obj2.display();
        System.out.println(obj3.getLikePeople()==obj4.getLikePeople());


    }
}
