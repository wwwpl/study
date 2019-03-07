package com.example.study.arithmetic.test;

/**
 * @author wangfei
 * @date 2019/3/1 15:09
 */
public class SecondBigNumTest {


    private static int firstMax;
    private static int secondMax;
    private final static int length = 2;

    /**
     * 第二最大值
     *
     * @param args
     */
    public static void getSecondMax(int[] args) {
        if (args != null && args.length > length) {
            exchangeValue(args);
            for (int i : args) {
                if (i > firstMax) {
                    secondMax = firstMax;
                    firstMax = i;
                } else if (i > secondMax) {
                    secondMax = i;
                }
            }
        } else if (args.length == length) {
            exchangeValue(args);
        } else {
            System.out.println("长度不够或者数组为空");
        }

    }

    private static void exchangeValue(int[] args) {
        if (args[0] > args[1]) {
            secondMax = args[1];
            firstMax = args[0];
        } else {
            firstMax = args[1];
            secondMax = args[0];
        }
    }

    public static void main(String[] args) {
        int[] arg = {2, 154};
        getSecondMax(arg);
        System.out.println("MAX:" + firstMax + "   " + "secondMAX:" + secondMax);
    }
}
