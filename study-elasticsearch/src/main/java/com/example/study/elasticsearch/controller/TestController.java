package com.example.study.elasticsearch.controller;

import com.example.study.service.elasticsearch.TestService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author wangfei
 * @date 2019/2/26 15:47
 */
@Controller
public class TestController {

    @Autowired
    TestService testService;

    Gson gson = new Gson();

    @RequestMapping("/")
    @ResponseBody
    public String test() {
        return gson.toJson(testService.getPeople());
    }
}
