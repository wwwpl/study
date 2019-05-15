package com.example.study.design.singleton;

/**
 * 饿汉方式创建单例模式
 *
 * @author F.W
 * @date 2019/5/5 16:19
 */
public class HungrySingleton {

    private static final HungrySingleton instance = new HungrySingleton();

    /**
     * 构造方法
     */
    private HungrySingleton() {};

    /**
     * 返回实例
     * @return
     */
    public static HungrySingleton getInstance() {
        return instance;
    }

}
