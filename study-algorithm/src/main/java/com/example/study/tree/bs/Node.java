package com.example.study.tree.bs;

/**
 * 二叉树的结构
 *
 * @author wangfei
 * @date 2020/04/14
 */
public class Node {

    //node value
    int value;
    //right node
    Node right;
    //left node
    Node left;

    Node(int value) {
        this.value = value;
        right = null;
        left = null;
    }

}
