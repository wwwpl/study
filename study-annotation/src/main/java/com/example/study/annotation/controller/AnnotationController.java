package com.example.study.annotation.controller;

import com.example.study.annotation.model.Student;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 注解测试类
 *
 * @author wangfei52
 * @date 2020年3月18日18:27:47
 */
@RestController
public class AnnotationController {

    @PostMapping("/student")
    public Student testAnnotation(@RequestBody @Valid Student userDTO) {
        return userDTO;
    }

    @GetMapping("/")
    public Student testAnnotation() {
        Student student = new Student();
        student.setMobile("11111111");
        return student;
    }


}
