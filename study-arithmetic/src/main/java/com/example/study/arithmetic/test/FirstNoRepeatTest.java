package com.example.study.arithmetic.test;

/**
 * 找出数组中第一个不重复的数字
 *
 * @author wangfei
 * @date 2019/3/4 10:41
 */
public class FirstNoRepeatTest {

    public static int position;


    public static void getFirstNoRepeat(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                if (i != j && arr[i] == arr[j]) {
                    break;
                }
                //循环结束未找到重复项
                if (j == arr.length - 1) {
                    position = arr.length;
                }
            }
            if (position == arr.length) {
                System.out.println("未重复数字的位置:" + i);
                System.out.println("第一个未重复数字:"+arr[i]);
                return;
            }
        }
    }

    public static void main(String[] args) {
        int[] arr = {1, 2, 2, 23, 2, 3, 4, 3, 76, 1};
        getFirstNoRepeat(arr);
    }

}
