package com.example.study.design.service.impl;

import com.example.study.design.service.StrategyService;

/**
 * 定义了减法策略，始终返回正数
 *
 * @author wangfei
 * @date 2019/3/1 10:23
 */
public class SubtractStrategyServiceImpl implements StrategyService {


    @Override
    public int calc(int num1, int num2) {
        return Math.abs(num1 - num2);
    }
}
