package com.example.study.other.function;

import java.util.HashMap;
import java.util.Map;

/**
 * @author F.W
 * @date 2019/5/30 10:15
 */
public class ValueTest {

    private static Map map = new HashMap();

    static {
        map.put("1", "");
        map.put("2", "");
        map.put("3", "");
        map.put("4", "");
        map.put("5", "");
        map.put("6", "");
        map.put("7", "");
        map.put("8", "");
        map.put("9", "");
        map.put("10", "");
        map.put("11", "");
        map.put("12", "");
        map.put("13", "");
        map.put("14", "");
        map.put("15", "");
        map.put("16", "");
    }

    public static void main(String[] args) {
        String value = "1";
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            if ("1".equals(value) || "2".equals(value) || "3".equals(value) || "4".equals(value) || "5".equals(value) || "6".equals(value) || "7".equals(value) || "8".equals(value) || "9".equals(value) || "10".equals(value) || "11".equals(value) || "12".equals(value) || "13".equals(value) || "14".equals(value) || "15".equals(value) || "17".equals(value)) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        long mid = System.currentTimeMillis();
        System.out.println(mid-start);
        for (int i = 0; i < 100; i++){
            if (map.containsKey(value)){
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println(System.currentTimeMillis()-mid);

    }

}
