package com.example.study.rpc.demo;

/**
 * @author F.W
 * @date 2019/6/4 16:27
 */
public class TestServiceImpl implements TestService {

    @Override
    public String testRpc(Integer i) {
        return "Test RPC - " + i;
    }
}
