package com.example.study.redis.MiaoSha;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author F.W
 * @date 2019/5/31 10:46
 */
@Service
public class ClientService {
    @Autowired
    RedisTemplate redisTemplate;

    String key = "prdNum";// 商品主键
    String clientList = "clientList";//// 抢购到商品的顾客列表主键

    @Transactional
    public void go(People people) {
        try {
            Thread.sleep((int) (Math.random() * 5000));// 随机睡眠一下
        } catch (InterruptedException e1) {
        }
        while (true) {
            System.out.println("顾客:" + people.getClientName() + "开始抢商品");
            try {
                redisTemplate.watch(key);
                int prdNum = Integer.parseInt(String.valueOf(redisTemplate.opsForValue().get(key)));// 当前商品个数
                if (prdNum > 0) {
                    redisTemplate.opsForValue().set(key, String.valueOf(prdNum - 1));
                    List<Object> result = redisTemplate.exec();
                    if (result == null || result.isEmpty()) {
                        System.out.println("悲剧了，顾客:" + people.getClientName() + "没有抢到商品");// 可能是watch-key被外部修改，或者是数据操作被驳回
                    } else {
                        redisTemplate.opsForSet().add(clientList, people.getClientName());// 抢到商品记录一下
                        System.out.println("好高兴，顾客:" + people.getClientName() + "抢到商品");
                        break;
                    }
                } else {
                    System.out.println("悲剧了，库存为0，顾客:" + people.getClientName() + "没有抢到商品");
                    break;
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                redisTemplate.unwatch();
            }

        }
    }

    public class ThreadOperation implements Runnable {
        People p;

        public ThreadOperation(People p) {
            this.p = p;
        }

        @Override
        public void run() {
            go(p);
        }
    }
}
