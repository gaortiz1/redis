package com.witbooking.redis.core.concurrent;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import static org.awaitility.Awaitility.given;


@ExtendWith({SpringExtension.class})
@SpringBootTest
class ConcurrentTest {

    /*

    @Autowired
    @Qualifier("concurrentComponent")
    private ConcurrentComponent concurrentComponent;

    @Test
    void asdasd() {

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        int numberOfThreads = 1000;

        IntStream
                .range(0, numberOfThreads)
                .forEach(value -> executorService.submit(() ->  {
                    System.out.println(value);
                    concurrentComponent.increment();
                }));

        given()
                .pollExecutorService(executorService)
                .await()
                .forever()
                .until(() -> concurrentComponent.getCounter() == numberOfThreads);

    }

     */


}