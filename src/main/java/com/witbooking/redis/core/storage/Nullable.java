package com.witbooking.redis.core.storage;

public interface Nullable {

    default boolean isNull() {
        return false;
    }

}
