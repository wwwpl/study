package com.example.study.flink.stream;

import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.Properties;

/**
 * @author F.W
 * @date 2019/6/5 15:37
 */
public class TestKafkaStream {


    public static void main(String[] args) {

        StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();
        environment.enableCheckpointing(1000);//设置启动检查点
        environment.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);//

        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers","localhost:");
    }
}
