package com.example.study.annotation.annotation;

import com.example.study.annotation.processor.LengthValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = LengthValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Length {
    //最大值
    int min();
    //最小值
    int max();
    //自定义的错误信息
    String message() default "W.F 自定义注解";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
