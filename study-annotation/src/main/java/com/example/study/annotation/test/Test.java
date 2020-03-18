package com.example.study.annotation.test;

import com.example.study.annotation.common.ApplicationContextProvider;
import com.example.study.annotation.model.Student;
import com.example.study.annotation.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author F.W
 * @date 2019/3/14 16:05
 */
@Slf4j
@Component
public class Test {

    @PostConstruct
    public void init() {
        log.info("ttttttttt");
        Student student = new Student();
        student.setMobile("189370550000");
        log.info(student.getMobile());
        TestService testServiceImpl = ApplicationContextProvider.getBean(TestService.class);
        testServiceImpl.test();
    }
    @Autowired

    public static void main(String[] args) {
        Student student = new Student();
        student.setMobile("189370550000");
        log.info(student.getMobile());
    }
}
