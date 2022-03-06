package com.witbooking.redis.core.template;


public interface StringsTemplate {

    String set(String key, String value);

    String set(String key, String value, long expirationInSeconds);

    String incrementsValue(String key);

    String get(String key);

    String delete(String key);

    int size();

}
