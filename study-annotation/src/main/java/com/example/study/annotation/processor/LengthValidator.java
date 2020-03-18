package com.example.study.annotation.processor;

import com.example.study.annotation.annotation.Length;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Slf4j
public class LengthValidator implements ConstraintValidator<Length, Object> {

    @Override
    public void initialize(Length constraintAnnotation) {
        log.info("annotation length init");
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        log.info(value.toString());
        //int length = Integer.valueOf(value.toString());
        return false;
    }
}
