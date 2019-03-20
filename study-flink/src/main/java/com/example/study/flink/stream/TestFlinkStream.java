package com.example.study.flink.stream;

import com.example.study.flink.util.EnvironmentUtil;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @author F.W
 * @date 2019/3/20 18:24
 */
public class TestFlinkStream {

    private static final String CONFIG_FILE_PATH = "";

    private static final long INTERVAL = -1;


    public static void main(String[] args) {
        StreamExecutionEnvironment environment = EnvironmentUtil.getEnvironment(CONFIG_FILE_PATH, INTERVAL);



    }
}
