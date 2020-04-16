package com.example.study.arithmetic.leetCode.common;

public class ListNodeUtils {

    public static ListNode addRecursive(ListNode current, int value) {
        if (current == null) {
            return new ListNode(value);
        }
        current.next = addRecursive(current.next, value);
        return current;
    }

    public static ListNode buildListNode(int[] arr) {
        if (arr == null || arr.length == 0) {
            return null;
        }
        ListNode listNode = null;
        for (int i : arr) {
            listNode = addRecursive(listNode, i);
        }
        return listNode;
    }

    public static void main(String[] args) {
        ListNode listNode = buildListNode(new int[]{2, 1, 5});
        System.out.println(listNode.value);
    }
}
