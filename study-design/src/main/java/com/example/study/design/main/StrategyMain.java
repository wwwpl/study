package com.example.study.design.main;

import com.example.study.design.environment.StrategyEnvironment;
import com.example.study.design.service.StrategyService;
import com.example.study.design.service.impl.AddStrategyServiceImpl;

/**
 * 策略模式
 *
 * @author wangfei
 * @date 2019/3/1 10:16
 */
public class StrategyMain {

    public static void main(String[] args) {
        StrategyEnvironment addStrategyEnvironment = new StrategyEnvironment(new AddStrategyServiceImpl());
        System.out.println(addStrategyEnvironment.calulate(1, 3));
    }
}
