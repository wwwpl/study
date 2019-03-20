package com.example.study.flink.stream;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;

/**
 * 测试flink
 *
 * @author F.W
 * @date 2019/3/20 10:48
 */
public class SocketWindowWordCount {
    public static void main(String[] args) throws Exception {

        // 用final修饰符定义端口号，表示不可变
        final int port;
        try {
            final ParameterTool params = ParameterTool.fromArgs(args);
            port = params.getInt("port");
        } catch (Exception e) {
            System.err.println("No port specified. Please run 'SocketWindowWordCount --port <port>'");
            return;
        }

        // （1）获取执行环境
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // （2）获取数据流，例子中是从指定端口的socket中获取用户输入的文本
        DataStream<String> text = env.socketTextStream("127.0.0.1", port, "\n");

        // （3）transformation操作，对数据流实现算法
        DataStream<WordWithCount> windowCounts = text
                //将用户输入的文本流以非空白符的方式拆开来，得到单个的单词，存入命名为out的Collector中
                .flatMap(new FlatMapFunction<String, WordWithCount>() {
                    @Override
                    public void flatMap(String value, Collector<WordWithCount> out) {
                        for (String word : value.split("\\s")) {
                            out.collect(new WordWithCount(word, 1L));
                        }
                    }
                })
                //将输入的文本分为不相交的分区，每个分区包含的都是具有相同key的元素。也就是说，相同的单词被分在了同一个区域，下一步的reduce就是统计分区中的个数
                .keyBy("word")
                //滑动窗口机制，每1秒计算一次最近5秒
                .timeWindow(Time.seconds(5), Time.seconds(1))
                //一个在KeyedDataStream上“滚动”进行的reduce方法。将上一个reduce过的值和当前element结合，产生新的值并发送出。
                //此处是说，对输入的两个对象进行合并，统计该单词的数量和
                .reduce(new ReduceFunction<WordWithCount>() {
                    @Override
                    public WordWithCount reduce(WordWithCount a, WordWithCount b) {
                        return new WordWithCount(a.word, a.count + b.count);
                    }
                });

        // 单线程执行该程序
        windowCounts.print().setParallelism(1);

        env.execute("Socket Window WordCount");
    }

    // 统计单词的数据结构，包含两个变量和三个方法
    public static class WordWithCount {
        //两个变量存储输入的单词及其数量
        public String word;
        public long count;

        public WordWithCount() {}

        public WordWithCount(String word, long count) {
            this.word = word;
            this.count = count;
        }

        @Override
        public String toString() {
            return word + " : " + count;
        }
    }
}
