package com.example.study.thread;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author F.W
 * @date 2019/6/4 17:58
 */
public class CompareThread implements Runnable {

    private static List<String> list = new ArrayList();

    private String keyWord;

    static {
        list.add("11214141");
        list.add("2");
        list.add("1");
        list.add("2412");
        list.add("12312");
        list.add("1231");
        list.add("1231");
    }

    public CompareThread(String keyWord) {
        this.keyWord = keyWord;
    }

    private boolean isBanWord(String keyWord) {
        if (StringUtils.isBlank(keyWord) || CollectionUtils.isEmpty(list)) {
            return false;
        }
        for (int i = 0; i < list.size(); i++) {
            String banWord = list.get(i);
            if (keyWord.contains(banWord)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void run() {
        System.out.println(keyWord + "---" + isBanWord(keyWord));
    }
}

class CompareThreadTest {
    public static void main(String[] args) {
        CompareThread thread = null;
        for (int i = 0; i < 10000; i++) {
            thread = new CompareThread(String.valueOf(i));
            thread.run();
        }
    }
}
