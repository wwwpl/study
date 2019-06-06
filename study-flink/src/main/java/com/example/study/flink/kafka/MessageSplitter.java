package com.example.study.flink.kafka;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;

/**
 *  将获取到的每条Kafka消息根据“，”分割取出其中的主机名和内存数信息
 *
 * @author F.W
 * @date 2019/6/6 15:29
 */
public class MessageSplitter implements FlatMapFunction<String, Tuple2<String, Long>> {
    @Override
    public void flatMap(String s, Collector<Tuple2<String, Long>> collector) throws Exception {
        if (s != null && s.contains(",")) {
            String[] parts = s.split(",");
            collector.collect(new Tuple2<>(parts[1], Long.parseLong(parts[2])));
        }
    }
}
