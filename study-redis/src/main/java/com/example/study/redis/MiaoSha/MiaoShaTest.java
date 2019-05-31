package com.example.study.redis.MiaoSha;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author F.W
 * @date 2019/5/30 16:39
 */
@Component
public class MiaoShaTest {
    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    ClientService clientService;

    @PostConstruct
    public void start() {
        initProduct();
        initClient();
        printResult();
    }

    /**
     * 输出结果
     */
    public void printResult() {
        Set<String> set = redisTemplate.opsForSet().members("clientList");
        int i = 1;
        for (String value : set) {
            System.out.println("第" + i++ + "个抢到商品，" + value + " ");
        }

    }


    public void initProduct() {
        String key = "prdNum";
        String clientList = "clientList";

        if (redisTemplate.hasKey(key)) {
            redisTemplate.delete(key);
        }
        if (redisTemplate.hasKey(clientList)) {
            redisTemplate.delete(clientList);
        }
        redisTemplate.opsForValue().set(key, 10);
    }

    /*
     * 初始化顾客开始抢商品
     */
    public void initClient() {

        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        int clientNum = 100;// 模拟客户数目
        for (int i = 0; i < clientNum; i++) {
            cachedThreadPool.execute(clientService.new ThreadOperation(new People(i)));
        }
        cachedThreadPool.shutdown();

        while (true) {
            if (cachedThreadPool.isTerminated()) {
                System.out.println("所有的线程都结束了！");
                break;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}