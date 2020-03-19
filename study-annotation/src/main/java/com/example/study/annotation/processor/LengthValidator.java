package com.example.study.annotation.processor;

import com.example.study.annotation.annotation.Length;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Slf4j
public class LengthValidator implements ConstraintValidator<Length, Object> {

    private int min;
    private int max;

    @Override
    public void initialize(Length constraintAnnotation) {
        min = constraintAnnotation.min();
        max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {

        if (value.toString().length() >= min && value.toString().length() <= max) {
            return true;
        }
        //int length = Integer.valueOf(value.toString());
        return false;
    }
}
