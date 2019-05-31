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
        System.out.println(ip+"  -start-  "+System.currentTimeMillis());
        Thread.sleep(time);
        System.out.println(ip+"  -end-  "+System.currentTimeMillis());
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
        ThreadPoolExecutor executor = new ThreadPoolExecutor(32,128,15, TimeUnit.SECONDS,new ArrayBlockingQueue<>(512),new ThreadPoolExecutor.CallerRunsPolicy());
        IpThread ipThread1 = new IpThread("10.123.1","1234","1111",1000L);
        IpThread ipThread2 = new IpThread("10.123.2","1234","1111",1000L);
        IpThread ipThread3 = new IpThread("10.123.3","1234","1111",1000L);
        IpThread ipThread4 = new IpThread("10.123.4","1234","1111",1000L);
        List<IpThread> ipThreadList =  new ArrayList<>();
        ipThreadList.add(ipThread1);
        ipThreadList.add(ipThread2);
        ipThreadList.add(ipThread3);
        ipThreadList.add(ipThread4);
        List<Future<String>> list= new ArrayList<>();
        try {
            //设置线程超时时间
            list = executor.invokeAll(ipThreadList,2,TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (Future<String> future : list){
            try {
                long start = System.nanoTime();
                System.out.println("-------"+future.get(2000,TimeUnit.SECONDS));
                System.out.println(System.nanoTime()-start);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        }

    }
}
