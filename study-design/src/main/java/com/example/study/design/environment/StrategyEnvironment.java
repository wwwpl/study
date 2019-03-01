package com.example.study.design.environment;

import com.example.study.design.service.StrategyService;

/**
 * 环境角色
 *
 * @author wangfei
 * @date 2019/3/1 10:27
 */
public class StrategyEnvironment {

    private StrategyService strategyService;

    public StrategyEnvironment(StrategyService strategyService) {
        this.strategyService = strategyService;
    }

    public int calulate(int num1, int num2) {
        return strategyService.calc(num1, num2);
    }
}
