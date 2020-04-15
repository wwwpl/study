package com.example.study.arithmetic.leetCode;

/**
 * 给定一个由 4 位数字组成的数组，返回可以设置的符合 24 小时制的最大时间。
 * <p>
 * 最小的 24 小时制时间是 00:00，而最大的是 23:59。从 00:00 （午夜）开始算起，过得越久，时间越大。
 * <p>
 * 以长度为 5 的字符串返回答案。如果不能确定有效时间，则返回空字符串。
 */
public class NineFortyNine {

    public static String largestTimeFromDigits(int[] A) {
        int ans = -1;
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A.length; j++) {
                if (i != j) {
                    for (int m = 0; m < A.length; m++) {
                        if (m != j && i != m) {
                            int n = 6 - i - j - m;
                            int hours = 10 * A[i] + A[j];
                            int minutes = 10 * A[m] + A[n];
                            if (hours < 24 && minutes < 60) {
                                ans = Math.max(ans, hours * 60 + minutes);
                            }
                        }
                    }
                }

            }
        }
        return ans >= 0 ? String.format("%02d:%02d", ans / 60, ans % 60) : "";
    }

    public static void main(String[] args) {
        int[] a = {1,1,2,2};
        System.out.println(largestTimeFromDigits(a));
    }
}
