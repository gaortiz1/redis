package com.witbooking.redis.core.storage;

import java.util.List;
import java.util.Optional;

public interface SortedSet {

    boolean add(String key, Integer order, String member);

    Optional<Integer> rank(String key, String member);

    List<String> range(String key, int from, int to);

    List<ZSet.ScoreMember> rangeWithScores(String key, int from, int to);

    int size(String key);
}
