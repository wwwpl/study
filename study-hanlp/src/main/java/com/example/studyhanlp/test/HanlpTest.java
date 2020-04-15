package com.example.studyhanlp.test;


import com.hankcs.hanlp.model.crf.CRFSegmenter;

import java.io.IOException;
import java.util.List;

/**
 * hanlp学习
 *
 * @author wangfei52
 * @date 2019年11月21日17:56:13
 */
public class HanlpTest {

    public static void main(String[] args) throws IOException {
        CRFSegmenter segmenter = new CRFSegmenter();
        List<String> wordList = segmenter.segment("我喜欢用京东白条购买商品");
        System.out.println(wordList);
    }
}
