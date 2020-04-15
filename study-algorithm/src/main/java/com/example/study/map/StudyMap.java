package com.example.study.map;

import java.util.HashMap;
import java.util.Map;

public class StudyMap {
    static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }
    public static void main(String[] args) {
        Map map = new HashMap();
        map.put("111",111);
        System.out.println("0000 0000 0100");
        System.out.println("0000 0000 0011");
        System.out.println("0000 0000 0111");
        System.out.println(4^3);
        System.out.println(32>>>16);
        System.out.println("haha".hashCode());
        System.out.println(hash("haha"));
    }
}
