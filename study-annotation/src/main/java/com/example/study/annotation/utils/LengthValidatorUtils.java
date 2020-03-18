//package com.example.study.annotation.utils;
//
//
//public class LengthValidatorUtils {
//
//
//    public static String validate(Object object) throws IllegalAccessException {
//
//        // 用过反射获取字段的实际值
//        int value = (String.valueOf(object)).length();
//        // 将字段的实际值和注解上做标示的值进行比对
//        if (value < length.min() || value > length.max()) {
//            return length.errorMsg();
//        }
//
//        return null;
//    }
//}
