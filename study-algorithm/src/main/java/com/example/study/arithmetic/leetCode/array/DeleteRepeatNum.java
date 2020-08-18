package com.example.study.arithmetic.leetCode.array;

import java.math.BigDecimal;

/**
 * @author F.W
 * @date 2019/3/12 18:37
 */
public class DeleteRepeatNum {

    public static int removeDuplicates(int[] nums) {

        //判断无输入
        if (nums.length == 0) {
            return 0;
        }
        //标记计数
        int number = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != nums[number]) {
                number++;
                nums[number] = nums[i];
            }
        }
        //标记+1即为数字个数
        number += 1;
        return number;
    }

    public static void main(String[] args) {
//        int[] nums = {0,1,1,2,2,3,3,4};
//        System.out.println(removeDuplicates(nums));
        double a = 1.5026770106008058e-10;
        BigDecimal b = new BigDecimal( 12.9802322387695312e-08);
        double f1 = b.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
        System.out.println("f1 是小数点后只有两位的双精度类型数据：" + f1);
        System.out.println(a);
    }

}
