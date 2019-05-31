package com.example.study.redis.controller;


import com.example.study.redis.MiaoSha.MiaoShaTest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 排期管理
 *
 * @author F.W
 * @date 2019年4月8日14:22:54
 */
@Slf4j
@RestController
public class TestController  {

    @Autowired
    MiaoShaTest miaoShaTest;

    @GetMapping("/")
    public String testMiaoSha(){
        miaoShaTest.initProduct();
        miaoShaTest.initClient();
        miaoShaTest.printResult();
        return "";
    }

}
