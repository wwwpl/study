package com.example.study.annotation.service.impl;

import com.example.study.annotation.service.TestService;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService {
    @Override
    public void test() {
        System.out.println("-------------");
    }
}
