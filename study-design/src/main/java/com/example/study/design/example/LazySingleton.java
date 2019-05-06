package com.example.study.design.example;

/**
 * 懒汉式创建单例模式
 *
 * @author F.W
 * @date 2019/5/5 16:10
 */
public class LazySingleton {
    /**
     * 构造参数
     */
    private LazySingleton() {
    }

    /**
     * 新建一个null实例
     */
    private static volatile LazySingleton instance = null;

    /**
     * 获取实例
     * @return LazySingleton
     */
    public static LazySingleton getInstance() {
        if (instance == null) {
            synchronized (LazySingleton.class) {
                if (instance == null) {
                    instance = new LazySingleton();
                }
            }
        }
        return instance;
    }
}
