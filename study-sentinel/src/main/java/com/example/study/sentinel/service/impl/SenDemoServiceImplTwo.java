package com.example.study.sentinel.service.impl;

import com.example.study.sentinel.service.SenDemoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author F.W
 * @date 2019/3/13 17:31
 */
@Slf4j
@Service(value = "senDemoServiceImplTwo")
public class SenDemoServiceImplTwo implements SenDemoService {

    @Override
    public void testService() {
        try {
            TimeUnit.MILLISECONDS.sleep(600);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
