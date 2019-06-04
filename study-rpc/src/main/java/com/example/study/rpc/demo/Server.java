package com.example.study.rpc.demo;

import java.io.IOException;

/**
 * @author F.W
 * @date 2019/6/4 16:29
 */
public interface Server {

    public void stop();

    public void start() throws IOException;

    public void register(Class serviceInterface, Class impl);

    public boolean isRunning();

    public int getPort();
}
