package com.example.study.arithmetic.test;


import org.apache.commons.lang3.ArrayUtils;

/**
 * 寻找数组中的最大值与最小值
 *
 * @author wangfei
 * @date 2019/2/21 17:01
 */
public class MaxAndMinTest {

    static Integer MAX;
    static Integer MIN;

    /**
     * 问题分解法
     */
    public static void getMaxAndMin(Integer[] arr) {
        for (Integer i : arr) {
            if (MAX < i) {
                MAX = i;
            }
        }
        for (Integer i : arr) {
            if (MIN > i) {
                MIN = i;
            }
        }

    }

    /**
     * 取单元素法
     */
    public static void getMaxAndMin1(Integer[] arr) {
        for (Integer i : arr) {
            if (MAX < i) {
                MAX = i;
            } else if (MIN > i) {
                MIN = i;
            }
        }
    }

    /**
     * 取双元素法
     */
    public static void getMaxAndMin2(Integer[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] > arr[i + 1]) {
                if (MAX < arr[i]) {
                    MAX = arr[i];
                }
                if (MIN > arr[i + 1]) {
                    MIN = arr[i + 1];
                }
            } else {
                if (MAX < arr[i + 1]) {
                    MAX = arr[i + 1];
                }
                if (MIN > arr[i]) {
                    MIN = arr[i];
                }
            }
        }
    }

    public static void main(String[] args) {
        int[] arr = {1, 213, 2, 23, 2, 3, 4, 3, 76, 1};
        MAX = arr[0];
        MIN = arr[0];
        Integer[] a = ArrayUtils.toObject(arr);
        getMaxAndMin2(a);
        System.out.println("MAX:" + MAX + "  MIN:" + MIN);
    }

}
