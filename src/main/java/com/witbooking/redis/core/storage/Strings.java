package com.witbooking.redis.core.storage;

import java.util.Optional;

public interface Strings {

    String set(String key, String value, long duration);

    String set(String key, int value, long duration);

    String replace(String key, String newValue);

    String replace(String key, int newValue);

    Optional<String> get(String key);

    Optional<String> delete(String key);

    int size();

}
