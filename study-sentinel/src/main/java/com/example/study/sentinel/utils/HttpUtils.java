package com.example.study.sentinel.utils;

import com.example.study.domain.vo.ResultVO;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Random;
import java.util.concurrent.*;

import static java.lang.Thread.sleep;

/**
 * @author wangfei
 * @date 2018/12/11 10:02
 */
public class HttpUtils {

    private static ExecutorService executorService = Executors.newSingleThreadExecutor();

    /**
     * 向目的URL发送post请求
     * @param url       目的url
     * @param params    发送的参数
     * @return  ResultVO
     */
    public static ResultVO sendPostRequest(String url, MultiValueMap<String,String> params){
        System.out.println("111111111");
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    System.out.println("异常");
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        System.out.println("22222");
        return new ResultVO();
    }


    /**
     * 有超时时间的方法
     * @param timeout
     * @return
     */
    public static String timeoutMethod(int timeout) {
        String result = "默认";
        FutureTask<String> futureTask = new FutureTask<>(new Callable<String>() {

            @Override
            public String call() throws Exception {
                return unknowMethod();
            }
        });

        executorService.execute(futureTask);
        try {
            result = futureTask.get(timeout, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            //e.printStackTrace();
            futureTask.cancel(true);
            result = "默认";
        }

        return result;
    }

    /**
     * 这个方法的耗时不确定
     * @return
     */
    private static String unknowMethod() {
        Random random = new Random();
        int time = (random.nextInt(10) + 1) * 1000;
        System.out.println("任务将耗时： " + time + "毫秒");
        try {
            Thread.sleep(time);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return "获得方法执行后的返回值";
    }

}
