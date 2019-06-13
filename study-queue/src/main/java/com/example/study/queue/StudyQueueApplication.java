package com.example.study.queue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class StudyQueueApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudyQueueApplication.class, args);
    }

}
