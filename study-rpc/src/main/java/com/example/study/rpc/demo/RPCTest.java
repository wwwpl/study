package com.example.study.rpc.demo;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * @author F.W
 * @date 2019/6/4 16:40
 */
public class RPCTest {

    public static void main(String[] args) throws IOException {
        new Thread(new Runnable() {
            public void run() {
                try {
                    Server serviceServer = new ServiceCenter(8088);
                    serviceServer.register(TestService.class, TestServiceImpl.class);
                    serviceServer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        TestService service = RPCClient.getRemoteProxyObj(TestService.class, new InetSocketAddress("localhost", 8088));
        System.out.println(service.testRpc(1000));
    }
}
