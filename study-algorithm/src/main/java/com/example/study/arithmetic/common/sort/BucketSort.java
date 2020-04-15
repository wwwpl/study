package com.example.study.arithmetic.common.sort;

import java.util.ArrayList;
import java.util.List;

/**
 * 桶排序
 * 1. 设置一个定量的数组当作空桶子。
 * 2. 寻访序列，并且把项目一个一个放到对应的桶子去。
 * 3. 对每个不是空的桶子进行排序。
 * 4. 从不是空的桶子里把项目再放回原来的序列中。
 *
 * @author wangfei52
 * @date 2019年12月3日15:16:14
 */
public class BucketSort {
    //计算在第几个桶
    private static int indexFor(int a, int min, int step) {
        return (a - min) / step;
    }

    public static void bucketSort(int[] arr, int step) {
        int max = arr[0], min = arr[0];
        //找出最大数和最小数
        for (int i : arr) {
            if (max < i) {
                max = i;
            }
            if (min > i) {
                min = i;
            }
        }
        //算出桶的大小
        int bucketNum = max / step - min / step + 1;
        List buckList = new ArrayList<List<Integer>>();
        for (int i = 1; i <= bucketNum; i++) {
            buckList.add(new ArrayList<Integer>());
        }
        for (int i = 0; i < arr.length; i++) {
            int index = indexFor(arr[i], min, step);
            ((ArrayList<Integer>) buckList.get(index)).add(arr[i]);
        }
        ArrayList<Integer> bucket = null;
        int index = 0;
        for (int i = 0; i < bucketNum; i++) {
            //获取桶里元素
            bucket = (ArrayList<Integer>) buckList.get(i);
            //排序
            insertSort(bucket);
            //塞到原始数组
            for (Integer k : bucket) {
                arr[index++] = k;
            }
        }
    }

    // 把桶內元素插入排序
    private static void insertSort(List<Integer> bucket) {
        for (int i = 1; i < bucket.size(); i++) {
            int temp = bucket.get(i);
            int j = i - 1;
            for (; j >= 0 && bucket.get(j) > temp; j--) {
                bucket.set(j + 1, bucket.get(j));
            }
            bucket.set(j + 1, temp);
        }
    }

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 11, 11, 333, 12, 1, 23, 13, 41, 23, 231, 44, 1, 1, 33, 41, 2, 6, 80, 789};
        bucketSort(arr, 10);
        for (int i : arr) {
            System.out.print(i + ",");
        }
    }
}
