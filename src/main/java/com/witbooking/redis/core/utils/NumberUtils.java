package com.witbooking.redis.core.utils;

import com.witbooking.redis.core.exception.RedisException;

public final class NumberUtils {

    public static int parseToInt(String intAsString, String errorMessage) {
        try {
            return Integer.parseInt(intAsString);
        } catch (Exception ex) {
            throw new RedisException(errorMessage);
        }
    }

}
