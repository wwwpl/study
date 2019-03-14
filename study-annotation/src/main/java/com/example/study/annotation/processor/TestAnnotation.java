package com.example.study.annotation.processor;

import com.example.study.annotation.annotation.BeTested;

import javax.annotation.PostConstruct;

/**
 * @author F.W
 * @date 2019/3/14 14:35
 */
public class TestAnnotation {

    @BeTested(group = "AAA")
    public void test1(){}

    @BeTested(group = "BBB",owner = "WWW")
    public void test2(){}

    @PostConstruct
    public void test3(){}

}
