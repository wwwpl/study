package com.example.study.annotation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Length {
    //最大值
    int min();
    //最小值
    int max();
    //自定义的错误信息
    String errorMsg();
}
