package com.example.study.arithmetic.common.sort;

/**
 * 鸡尾酒排序
 * 就是**定向冒泡排序**，**鸡尾酒搅拌排序**，**搅拌排序**（也可以视作选择排序的一种变形），**涟漪排序**，**来回排序**或**快乐小时排序**，
 * 是冒泡排序的一种变形。此算法与冒泡排序的不同处在于排序时是以双向在序列中进行排序
 * @author wangfei52
 * @date 2019年11月26日15:29:32
 */
public class cockTailSort {

    public static void cockTailSort(int[] arr) {
        int i, left = 0, right = arr.length - 1;
        int temp = 0;
        while (left < right) {
            for (i = left; i < right; i++) {
                if (arr[i] > arr[i + 1]) {
                    temp = arr[i + 1];
                    arr[i + 1] = arr[i];
                    arr[i] = temp;
                }
            }
            right--;
            for (i = right; i > left; i--) {
                if (arr[i] > arr[i + 1]) {
                    temp = arr[i + 1];
                    arr[i + 1] = arr[i];
                    arr[i] = temp;
                }
            }
            left++;
        }
    }

    public static void main(String[] args) {
        int[] arr = {2, 1, 31, 1, 22, 3, 1, 2, 51, 1, 2, 1, 31, 1, 22, 3, 1, 2, 51, 1};
        cockTailSort(arr);
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + "  ");
        }
    }
}
