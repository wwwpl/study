package com.example.study.sentinel.controller;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.util.TimeUtil;
import com.example.study.domain.elasticsearch.People;
import com.example.study.sentinel.config.ApplicationContextUtil;
import com.example.study.sentinel.service.SenDemoService;
import com.example.study.sentinel.utils.SentinelUtils;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author F.W
 * @date 2019/3/13 17:42
 */
@Slf4j
@RestController
public class TestController {

    private static AtomicInteger pass1 = new AtomicInteger();
    private static AtomicInteger block = new AtomicInteger();
    private static AtomicInteger total = new AtomicInteger();
    private static AtomicInteger pass2 = new AtomicInteger();

    private static volatile boolean stop = false;
    private static final int threadCount = 100;
    private static int seconds = 60 + 40;
    Gson gson = new Gson();

    @GetMapping("/")
    public String getTest(People people) {
        log.info(people.getName() + "-----------");
//        tick();
//        start();
        return gson.toJson(people);
    }

    private static void start() {
        SenDemoService senDemoService = null;
        while (true) {
            int number = new Random().nextInt(10) + 1;
            if (number % 2 == 1) {
                senDemoService = (SenDemoService) ApplicationContextUtil.getBean("senDemoServiceImplOne");
            } else {
                senDemoService = (SenDemoService) ApplicationContextUtil.getBean("senDemoServiceImplTwo");
            }
            SentinelUtils.initDegradeRule(senDemoService.getClass().toString(), String.valueOf(number % 2));
            Entry entry = null;
            try {
                entry = SphU.entry(senDemoService.getClass().toString());
                senDemoService.testService();
                if (number % 2 == 1) {
                    pass1.incrementAndGet();
                } else {
                    pass2.incrementAndGet();
                }
            } catch (Exception e) {
                block.incrementAndGet();
            } finally {
                total.incrementAndGet();
                if (entry != null) {
                    entry.exit();
                }
            }

        }
    }

    private static void tick() {
        Thread timer = new Thread(new TimerTask());
        timer.setName("sentinel-timer-task");
        timer.start();
    }


    static class TimerTask implements Runnable {

        @Override
        public void run() {
            long start = System.currentTimeMillis();
            System.out.println("begin to statistic!!!");
            long oldTotal = 0;
            long oldPass1 = 0;
            long oldPass2 = 0;
            long oldBlock = 0;

            while (!stop) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                }

                long globalTotal = total.get();
                long oneSecondTotal = globalTotal - oldTotal;
                oldTotal = globalTotal;

                long globalPass1 = pass1.get();
                long oneSecondPass1 = globalPass1 - oldPass1;
                oldPass1 = globalPass1;

                long globalPass2 = pass2.get();
                long oneSecondPass2 = globalPass2 - oldPass2;
                oldPass2 = globalPass2;


                long globalBlock = block.get();
                long oneSecondBlock = globalBlock - oldBlock;
                oldBlock = globalBlock;

                System.out.println(TimeUtil.currentTimeMillis() + ", total:" + oneSecondTotal
                        + ", pass1:" + oneSecondPass1 + ", pass2:" + oneSecondPass2 + ", block:" + oneSecondBlock);

                if (seconds-- <= 0) {
                    stop = true;
                }
            }

            long cost = System.currentTimeMillis() - start;
            System.out.println("time cost: " + cost + " ms");
            System.out.println("total:" + total.get() + ", pass1:" + pass1.get() + ", pass2:" + pass2.get()
                    + ", block:" + block.get());
            System.exit(0);
        }
    }
}
