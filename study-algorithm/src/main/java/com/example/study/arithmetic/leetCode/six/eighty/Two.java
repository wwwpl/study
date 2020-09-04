package com.example.study.arithmetic.leetCode.six.eighty;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 你现在是棒球比赛记录员。
 * 给定一个字符串列表，每个字符串可以是以下四种类型之一：
 * 1.整数（一轮的得分）：直接表示您在本轮中获得的积分数。
 * 2. "+"（一轮的得分）：表示本轮获得的得分是前两轮有效 回合得分的总和。
 * 3. "D"（一轮的得分）：表示本轮获得的得分是前一轮有效 回合得分的两倍。
 * 4. "C"（一个操作，这不是一个回合的分数）：表示您获得的最后一个有效 回合的分数是无效的，应该被移除。
 * <p>
 * 每一轮的操作都是永久性的，可能会对前一轮和后一轮产生影响。
 * 你需要返回你在所有回合中得分的总和。
 * <p>
 * 示例 1:
 * <p>
 * 输入: ["5","2","C","D","+"]
 * 输出: 30
 * 解释:
 * 第1轮：你可以得到5分。总和是：5。
 * 第2轮：你可以得到2分。总和是：7。
 * 操作1：第2轮的数据无效。总和是：5。
 * 第3轮：你可以得到10分（第2轮的数据已被删除）。总数是：15。
 * 第4轮：你可以得到5 + 10 = 15分。总数是：30。
 */
public class Two {

    public static boolean isNumericzidai(String str) {
        Pattern pattern = Pattern.compile("-?[0-9]+\\.?[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    public static int calPoints(String[] ops) {
        int[] score = new int[ops.length];
        for (int i = 0; i < ops.length; i++) {
            if (isNumericzidai(ops[i])) {
                if (i == 0) {
                    score[i] = Integer.valueOf(ops[i]);
                } else {
                    score[i] = Integer.valueOf(ops[i]);
                }
            } else if (("+").equals(ops[i])) {

            } else if (("C").equals(ops[i])) {
                scoreTotal[i] = score[i - 2];
            } else if (("D").equals(ops[i])) {
                scoreTotal[i] = 3 * score[i - 1];
            }
        }
        return scoreTotal[scoreTotal.length - 1];
    }

    public

    public static void main(String[] args) {
        args = new String[]{"5", "2", "C", "D", "+"};
        System.out.println(calPoints(args));
    }
}
