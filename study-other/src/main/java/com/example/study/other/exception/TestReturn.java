package com.example.study.other.exception;

/**
 * @author F.W
 * @date 2019/5/28 9:53
 */
public class TestReturn {

    private final static String TRAN_ONE = "0";
    private final static String TRAN_TWO = "4";

    public static Integer getReturn() {
        Integer a;
        try {
            a = Integer.valueOf(TRAN_ONE);
            return 1;
        } catch (Exception e) {
            return 2;
        } finally {
            try {
                a = Integer.valueOf(TRAN_TWO);
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }

    }


    public static void main(String[] args) {
        System.out.println(getReturn());
        if (TRAN_ONE.equals("1") && TRAN_TWO.equals("3") || !TRAN_ONE.equals("1")) {
            System.out.println("---------------");
        }

    }

}
