package com.example.study.arithmetic.common.sort;

/**
 * 冒泡排序
 * 1. 比较相邻的元素。如果第一个比第二大，就交换它们两个。
 * 2. 对每一对相邻元素作同样的工作，从开始第一对到结尾的最后一对。这步做完后，最后的元素会是最大的数。
 * 3. 针对所有的元素重复以上的步骤，除了最后一个
 * 4. 持续每次对越来越少的元素重复上面的步骤，直到没有任何一对数字需要比较。
 *
 * @author wangfei52
 * @Date 2019年10月22日15:31:40
 */
public class BubbleSort {

    public static void bubbleSort(int[] var) {
        for (int i = 0; i < var.length; i++) {
            for (int j = 0; j < var.length - 1 - i; j++) {
                if (var[j] > var[j + 1]) {
                    int temp = var[j];
                    var[j] = var[j + 1];
                    var[j + 1] = temp;
                }
            }
        }
    }

    public static void main(String[] args) {
        int[] var = {2, 1, 3, 4, 1};
        bubbleSort(var);
        for (int i = 0; i < var.length; i++) {
            System.out.print(var[i] + ",");
        }
    }

}
