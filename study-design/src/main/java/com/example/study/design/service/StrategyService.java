package com.example.study.design.service;


/**
 * 定义抽象策略角色
 *
 * @author wangfei
 * @date 2019/3/1 10:18
 */
public interface StrategyService {

    /**
     * 定义两个数可以计算的接口
     *
     * @param num1
     * @param num2
     * @return
     */
    int calc(int num1, int num2);
}
