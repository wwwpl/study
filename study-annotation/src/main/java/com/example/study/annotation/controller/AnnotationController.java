package com.example.study.annotation.controller;

import com.example.study.annotation.model.Student;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 注解测试类
 *
 * @author wangfei52
 * @date 2020年3月18日18:27:47
 */
@Controller
public class AnnotationController {

    @GetMapping("/student")
    public Student testAnnotation(@RequestBody @Validated Student userDTO) {
        return userDTO;
    }
}
