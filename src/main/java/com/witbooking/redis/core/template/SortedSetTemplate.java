package com.witbooking.redis.core.template;

import java.util.List;

public interface SortedSetTemplate {

    String add(String key, Integer score, String member);

    String rank(String key, String member);

    int size(String key);

    List<String> range(String key, Integer start, Integer stop);

}
