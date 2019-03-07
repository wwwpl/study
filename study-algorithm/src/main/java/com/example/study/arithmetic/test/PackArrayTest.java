package com.example.study.arithmetic.test;

import com.google.gson.Gson;

/**
 * 合并有序数组
 *
 * @author wangfei
 * @date 2019/3/4 11:12
 */
public class PackArrayTest {

    public static int[] back;

    static Gson gson = new Gson();

    public static void packArray(int[] arr1, int[] arr2) {

        int a = 0, b = 0;

        back = new int[arr1.length + arr2.length];
        for (int i = 0; i < back.length; i++) {
            if (a < arr1.length && b < arr2.length) {
                if (arr1[a] < arr2[b]) {
                    back[i] = arr1[a];
                    a++;
                } else {
                    back[i] = arr2[b];
                    b++;
                }
            } else if (a < arr1.length) {
                back[i] = arr1[a];
                a++;
            } else if (b < arr2.length) {
                back[i] = arr2[b];
                b++;
            }
        }


    }

    public static void main(String[] args) {

        int[] arr1 = new int[]{1, 2, 4, 6, 7, 8, 123, 411, 5334, 1414141};
        int[] arr2 = new int[]{0, 1, 2, 5, 7, 89, 113, 5623, 6353, 134134};
        packArray(arr1, arr2);
        System.out.println(gson.toJson(back));
    }

}
