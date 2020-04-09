package com.example.study.arithmetic.leetCode.question;

public class Three {

    public static int lengthOfLongestSubstring(String s) {
        int j = 0;
        String str = "";
        String now = "";
        for (int i = 0; i < s.length(); i++) {
            now = s.substring(i, i+1);
            if (i == 0) {
                str += now;
                j++;
                continue;
            }
            if (str.contains(now)){
                return j;
            }
            str += now;
            j++;
        }
        return j;
    }

    public static void main(String[] args) {
        System.out.printf(String.valueOf(lengthOfLongestSubstring("pwwkew")));
    }
}
