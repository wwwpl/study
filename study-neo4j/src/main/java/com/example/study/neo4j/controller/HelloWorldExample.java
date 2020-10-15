package com.example.study.neo4j.controller;

import org.neo4j.driver.*;

import static org.neo4j.driver.Values.parameters;

public class HelloWorldExample implements AutoCloseable {
    private final Driver driver;

    public HelloWorldExample(String uri, String user, String password) {
        driver = GraphDatabase.driver(uri, AuthTokens.basic(user, password));
    }

    @Override
    public void close() throws Exception {
        driver.close();
    }

    public void printGreeting(final String message) {
        try (Session session = driver.session()) {
            String greeting = session.writeTransaction(new TransactionWork<String>() {
                @Override
                public String execute(Transaction tx) {
                    Result result = tx.run("CREATE (a:Greeting) " +
                                    "SET a.message = $message " +
                                    "RETURN a.message + ', from node ' + id(a)",
                            parameters("message", message));
                    return result.single().get(0).asString();
                }
            });
            System.out.println(greeting);
        }
    }

    public static void main(String... args) throws Exception {
        try (HelloWorldExample greeter = new HelloWorldExample("bolt://54.234.81.31:36221", "neo4j", "specialist-trims-nameplate")) {
            greeter.printGreeting("hello, world");
            greeter.printGreeting("hello, world1");
            greeter.printGreeting("hello, world2");
        }
    }
}