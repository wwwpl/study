package com.example.study.flink.stream;

import com.example.study.flink.kafka.MessageSplitter;
import com.example.study.flink.kafka.MessageWaterEmitter;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.windowing.WindowFunction;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010;
import org.apache.flink.util.Collector;

import java.util.Properties;

/**
 * @author F.W
 * @date 2019/6/5 15:37
 */
public class TestKafkaStream {


    public static void main(String[] args) throws Exception {
        try {
            StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();
            environment.enableCheckpointing(1000);//设置启动检查点
            environment.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);//

            Properties properties = new Properties();
            properties.setProperty("bootstrap.servers", "106.12.51.81:9092");
            properties.setProperty("zookeeper.connect", "106.12.51.81:2181");
            properties.setProperty("group.id", "test");

            FlinkKafkaConsumer010<String> consumer =
                    new FlinkKafkaConsumer010<>("test", new SimpleStringSchema(), properties);
            // consumer.assignTimestampsAndWatermarks(new MessageWaterEmitter());
            DataStream<Tuple2<String, Integer>> keyedStream = environment
                    .addSource(consumer).flatMap(new LineSplitter()).keyBy(0).sum(1);
            keyedStream.print();
            environment.execute("Kafka-Flink Test");
        } catch (Exception e) {
            System.out.println("----------" + e);
        }
    }

    public static final class LineSplitter implements FlatMapFunction<String, Tuple2<String, Integer>> {
        private static final long serialVersionUID = 1L;
        public void flatMap(String value, Collector<Tuple2<String, Integer>> out) {
            String[] tokens = value.toLowerCase().split("\\W+");
            for (String token : tokens) {
                if (token.length() > 0) {
                    out.collect(new Tuple2<String, Integer>(token, 1));
                }
            }
        }
    }
}
