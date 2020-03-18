package com.example.study.annotation.model;

import com.example.study.annotation.annotation.Length;
import lombok.Data;

@Data
public class Student {
    @Length(min = 11, max = 11, errorMsg = "手机号码验证验证错误，长度为11位")
    private String mobile;
}
