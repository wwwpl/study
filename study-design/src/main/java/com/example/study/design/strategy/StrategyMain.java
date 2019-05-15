package com.example.study.design.strategy;

import com.example.study.design.strategy.impl.AddStrategyServiceImpl;

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
