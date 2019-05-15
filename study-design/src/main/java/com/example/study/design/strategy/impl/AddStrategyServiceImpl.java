package com.example.study.design.strategy.impl;

import com.example.study.design.strategy.StrategyService;

/**
 * 定义了加法的策略
 *
 * @author wangfei
 * @date 2019/3/1 10:21
 */
public class AddStrategyServiceImpl implements StrategyService {

    @Override
    public int calc(int num1, int num2) {
        return num1+num2;
    }
}
