package com.example.study.tree.bs;

/**
 * 二叉树的操作
 *
 * @author wangfei
 * @date 2020/04/14
 */
public class BinaryTree {

    Node root;

    /**
     * 添加节点
     *
     * @param current
     * @param value
     * @return
     */
    private Node addRecursive(Node current, int value) {
        if (current == null) {
            return new Node(value);
        }
        if (value < current.value) {
            current.left = addRecursive(current.left, value);
        } else if (value > current.value) {
            current.right = addRecursive(current.right, value);
        } else {
            return current;
        }
        return current;
    }

    private void add(int value) {
        root = addRecursive(root, value);
    }

    /**
     * 是否包含节点
     *
     * @param current
     * @param value
     * @return
     */
    private Boolean containsNodeRecursive(Node current, int value) {
        if (current == null) {
            return false;
        }
        if (current.value == value) {
            return true;
        }
        return value < current.value ?
                containsNodeRecursive(current.left, value) : containsNodeRecursive(current.right, value);
    }

    public Boolean containsNode(int value) {
        return containsNodeRecursive(root, value);
    }

    /**
     * 删除节点
     *
     * @param current
     * @param value
     * @return
     */
    private Node deleteRecursive(Node current, int value) {
        if (current == null) {
            return null;
        }
        if (value == current.value) {

            if (current.left == null && current.right == null) {
                return null;
            }
            if (current.right == null) {
                return current.left;
            }
            if (current.left == null) {
                return current.right;
            }

            int smallestValue = findSmallestValue(current.right);
            current.value = smallestValue;
            current.right = deleteRecursive(current.right, smallestValue);
            return current;
        }
        if (value < current.value) {
            current.left = deleteRecursive(current.left, value);
            return current;
        }
        current.right = deleteRecursive(current.right, value);
        return current;
    }

    public void delete(int value) {
        root = deleteRecursive(root, value);
    }

    private int findSmallestValue(Node root) {
        return root.left == null ? root.value : findSmallestValue(root.left);
    }

    private BinaryTree createBinaryTree() {
        BinaryTree bt = new BinaryTree();
        bt.add(6);
        bt.add(4);
        bt.add(8);
        bt.add(3);
        bt.add(5);
        bt.add(7);
        bt.add(9);
        return bt;
    }

}
