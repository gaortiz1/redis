package com.witbooking.redis.core.concurrent;

import com.witbooking.redis.core.annotation.Concurrent;
import org.springframework.stereotype.Component;

@Component("concurrentComponent")
public class ConcurrentComponent {

    private int counter;

    //@Concurrent
    public void increment() {
        int temp = counter;
        counter = temp + 1;
    }

    public int getCounter() {
        return counter;
    }

}
