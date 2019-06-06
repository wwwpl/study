package com.example.study.flink.stream;

import com.example.study.flink.kafka.MessageSplitter;
import com.example.study.flink.kafka.MessageWaterEmitter;
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
            properties.setProperty("bootstrap.servers", "localhost:9092");
            properties.setProperty("group.id", "test");

            FlinkKafkaConsumer010<String> consumer =
                    new FlinkKafkaConsumer010<>("wf", new SimpleStringSchema(), properties);
            // consumer.assignTimestampsAndWatermarks(new MessageWaterEmitter());
            DataStream<Tuple2<String, Long>> keyedStream = environment
                    .addSource(consumer)
                    .flatMap(new MessageSplitter())
                    .keyBy(0)
                    .timeWindow(Time.seconds(2))
                    .apply(new WindowFunction<Tuple2<String, Long>, Tuple2<String, Long>, Tuple, TimeWindow>() {
                        public void apply(Tuple tuple, TimeWindow window, Iterable<Tuple2<String, Long>> input, Collector<Tuple2<String, Long>> out) throws Exception {
                            long sum = 0L;
                            int count = 0;
                            for (Tuple2<String, Long> record : input) {
                                sum += record.f1;
                                count++;
                            }
                            Tuple2<String, Long> result = input.iterator().next();
                            result.f1 = sum / count;
                            out.collect(result);
                        }
                    });
            keyedStream.print();
            keyedStream.writeAsText("H:\\FlinkTest\\KafkaFlinkTest.txt");
            environment.execute("Kafka-Flink Test");
        }catch (Exception e){
            System.out.println("----------"+e);
        }
    }
}
