package com.example.study.queue.controller;

import com.example.study.queue.listen.Listener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
public class CollectController {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Autowired
    Listener listener;

    @GetMapping(value = "/send")
    public void sendKafka() {
        try {
            kafkaTemplate.send("test", "test", "123321");
            log.info("发送kafka成功");
        } catch (Exception e) {
            log.error("发送kafka失败:{}", e);
        }
    }
}

