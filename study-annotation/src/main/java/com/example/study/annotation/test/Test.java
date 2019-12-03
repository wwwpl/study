package com.example.study.annotation.test;

import com.example.study.annotation.common.ApplicationContextProvider;
import com.example.study.annotation.service.TestService;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;

/**
 * @author F.W
 * @date 2019/3/14 16:05
 */
@Slf4j
public class Test {


    @PostConstruct
    public void init(){
        log.info("ttttttttt");
        TestService testServiceImpl = ApplicationContextProvider.getBean(TestService.class);
        testServiceImpl.test();
    }

    public static void main(String[] args) {
        String test = "";
        String test2 = "      ";
        if (test.equals(test2)){
            System.out.println("1111111111111");
        }
    }
}
