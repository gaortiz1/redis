package com.witbooking.redis.core.concurrent;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.concurrent.locks.ReentrantLock;

@Aspect
@Component
public class ConcurrentAspect {

    private final ReentrantLock reLock = new ReentrantLock(true);

    @Around("@annotation(com.witbooking.redis.core.annotation.Concurrent)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        reLock.lock();
        try {
            return joinPoint.proceed();
        } finally {
            reLock.unlock();
        }
    }

}
