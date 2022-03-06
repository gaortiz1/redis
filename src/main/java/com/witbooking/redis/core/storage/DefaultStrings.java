package com.witbooking.redis.core.storage;

import net.jodah.expiringmap.ExpiringMap;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class DefaultStrings implements Strings {

    private final ExpiringMap<String, String> map = ExpiringMap.builder()
            .variableExpiration()
            .build();

    public String set(String key, String value, long durationInSeconds) {
        map.put(key, value, durationInSeconds, TimeUnit.SECONDS);
        return value;
    }

    @Override
    public String set(String key, int value, long duration) {
        return set(key, String.valueOf(value), duration);
    }

    @Override
    public String replace(String key, String newValue) {
        map.replace(key, newValue);
        return newValue;
    }

    @Override
    public String replace(String key, int value) {
        return replace(key, String.valueOf(value));
    }

    public Optional<String> get(String key) {
        return Optional.ofNullable(map.get(key));
    }

    public Optional<String> delete(String key) {
        return Optional.ofNullable(map.remove(key));
    }

    public int size() {
        return map.size();
    }

}
