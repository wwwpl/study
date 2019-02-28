package com.example.study.elasticsearch;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

/**
 * Elasticsearch 学习进阶之路
 *
 * @author wangfei
 * @date 2019年2月26日15:15:09
 */
@ComponentScan("com.example.study")
@MapperScan("com.example.study.dao.elasticsearch")
@SpringBootApplication
public class ElasticsearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElasticsearchApplication.class, args);
    }

}
