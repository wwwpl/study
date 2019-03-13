package com.example.study.sentinel.service.impl;

import com.example.study.sentinel.service.SenDemoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author F.W
 * @date 2019/3/13 17:28
 */
@Slf4j
@Service(value = "senDemoServiceImplOne")
public class SenDemoServiceImplOne implements SenDemoService {

    @Override
    public void testService() {

    }
}
