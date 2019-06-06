package com.example.study.flink.kafka;

import org.apache.flink.streaming.api.functions.AssignerWithPunctuatedWatermarks;
import org.apache.flink.streaming.api.watermark.Watermark;

import javax.annotation.Nullable;

/**
 * @author F.W
 * @date 2019/6/6 15:17
 */
public class MessageWaterEmitter implements AssignerWithPunctuatedWatermarks<String> {
    @Nullable
    @Override
    public Watermark checkAndGetNextWatermark(String s, long l) {
        if (s != null && s.contains(",")) {
            String[] parts = s.split(",");
            return new Watermark(Long.parseLong(parts[0]));
        }
        return null;
    }

    @Override
    public long extractTimestamp(String s, long l) {
        if (s != null && s.contains(",")) {
            String[] parts = s.split(",");
            return Long.parseLong(parts[0]);
        }
        return 0L;
    }
}
