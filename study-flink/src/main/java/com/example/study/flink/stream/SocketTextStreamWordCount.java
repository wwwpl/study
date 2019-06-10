package com.example.study.flink.stream;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

/**
 * @author F.W
 * @date 2019/6/10 15:18
 */
public class SocketTextStreamWordCount {

    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.err.println("USAGE:SocketTextStreamWordCount <hostname> <port>");
            return;
        }
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<String> streamSource = env.socketTextStream("localhost", 9002);

        SingleOutputStreamOperator<Tuple2<String, Integer>> sum = streamSource.flatMap(new LineSplitter()).keyBy(0).sum(1);
        sum.print();
        env.execute("Java WordCount from SocketTextStream Example");

    }

    public static final class LineSplitter implements FlatMapFunction<String, Tuple2<String, Integer>> {
        @Override
        public void flatMap(String s, Collector<Tuple2<String, Integer>> collector) {
            String[] tokens = s.toLowerCase().split("\\W+");
            for (String token : tokens) {
                if (token.length() > 0) {
                    collector.collect(new Tuple2<String, Integer>(token, 1));
                }
            }
        }
    }
}
