package com.example.study.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author F.W
 * @date 2019/5/30 10:44
 */
public class IpThread implements Callable<String> {

    private String ip;
    private String port;
    private String param;
    private Long time;

    @Override
    public String call() throws Exception {
        Thread.sleep(time);
        System.out.println(ip);
        return ip;
    }

    public IpThread(String ip, String port, String param, Long time) {
        this.ip = ip;
        this.port = port;
        this.param = param;
        this.time = time;
    }
}

class TestIpThread {


    public static void main(String[] args) {
//        for (int i = 0; i < 5; i++) {
//            test(i);
//        }
        ThreadPoolExecutor executor = new ThreadPoolExecutor(32, 128, 15, TimeUnit.SECONDS, new ArrayBlockingQueue<>(512), new ThreadPoolExecutor.CallerRunsPolicy());
        IpThread ipThread1 = new IpThread("10.123.1", "1234", "1111", 11000L);
        IpThread ipThread2 = new IpThread("10.123.2", "1234", "1111", 200L);
        IpThread ipThread3 = new IpThread("10.123.3", "1234", "1111", 200L);
        IpThread ipThread4 = new IpThread("10.123.4", "1234", "1111", 200L);
        List<IpThread> ipThreadList = new ArrayList<>();
        ipThreadList.add(ipThread1);
        ipThreadList.add(ipThread2);
        ipThreadList.add(ipThread3);
        ipThreadList.add(ipThread4);
        List<Future<String>> list = new ArrayList<>();
        try {
            //设置线程超时时间
            list = executor.invokeAll(ipThreadList, 300L, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (Future<String> future : list) {
            try {
                System.out.println("-------" + future.get(300L, TimeUnit.MILLISECONDS));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        }
    }

    public static void test(int key) {
        try {
            System.out.println(System.currentTimeMillis() + "--start--" + key);
            Thread.sleep(1000);
            System.out.println(System.currentTimeMillis() + "--end--" + key);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
