package com.witbooking.redis.core.template;


import java.util.Set;

public interface SortedSetTemplate {

    String add(String key, Integer score, String member);

    int rank(String key, String member);

    int sizeByKey(String key);

    Set<String> range(String key, Integer start, Integer stop);

}
