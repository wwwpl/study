package com.example.study.thread.completable;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureDemo {

    public static void textCompletableFuture() {

        CompletableFuture<String> completableFuture1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("111111111");
            return "1";
        });

        CompletableFuture<String> completableFuture2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("222222222");
            return "2";
        });

        CompletableFuture<Object> cfQuery = CompletableFuture.anyOf(completableFuture1, completableFuture2);

        cfQuery.thenAcceptAsync((code) -> {
            System.out.println(code);
        });
    }

    public static void main(String[] args) {
        textCompletableFuture();
    }
}
