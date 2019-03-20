package com.example.study.flink.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.java.utils.ParameterTool;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

/**
 * 配置文件读取的工具类
 *
 * @author F.W
 * @date 2019/3/20 16:41
 */
@Slf4j
public class PropertyUtil {

    public static ParameterTool getParameterTool(String filepath) {
        ParameterTool parameter = null;
        try {
            parameter = ParameterTool.fromPropertiesFile(filepath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return parameter;
    }

}
