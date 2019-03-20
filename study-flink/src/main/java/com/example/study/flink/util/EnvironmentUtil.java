package com.example.study.flink.util;

import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * 流环境的工具类
 *
 * @author F.W
 * @date 2019/3/20 16:26
 */
public class EnvironmentUtil {

    /**
     * 重启策略的重试次数
     */
    private static final int RESTART_ATTEMPTS = 3;
    /**
     * 重启策略的重启间隔（ms）
     */
    private static final int DELAY_BETWEEN_ATTEMPTS = 1000;
    /**
     * 分钟的毫秒数
     */
    public static final long MIN_MILLISECONDS = 60 * 1000;

    public static StreamExecutionEnvironment getEnvironment(String fileName, long chkInterval) {
        StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();
        // 获取配置文件内容转化为ParameterTool
        ParameterTool parameters = PropertyUtil.getParameterTool(fileName);

        environment.getConfig().setGlobalJobParameters(parameters);
        // 设置重启策略
        environment.setRestartStrategy(RestartStrategies.fixedDelayRestart(RESTART_ATTEMPTS, DELAY_BETWEEN_ATTEMPTS));
        // 是以数据自带的时间戳字段为准，应用程序需要指定如何从record中抽取时间戳字段
        environment.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        if (chkInterval > 0) {
            // 启用和配置检查点
            environment.getCheckpointConfig().setCheckpointInterval(chkInterval * MIN_MILLISECONDS);
        }


        return environment;
    }

}
