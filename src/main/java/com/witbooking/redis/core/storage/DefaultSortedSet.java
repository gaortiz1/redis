package com.witbooking.redis.core.storage;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class DefaultSortedSet implements SortedSet {

    private final HashMap<String, ZSet> map = new HashMap<>();

    public boolean add(String key, Integer score, String member) {
        return map.computeIfAbsent(key, k -> new DefaultZSet()).add(score, member);
    }

    @Override
    public Optional<Integer> rank(String key, String member) {
        return map.containsKey(key)
                ? map.get(key).rank(member)
                : Optional.empty();
    }

    @Override
    public List<String> range(String key, int from, int to) {
        return map.containsKey(key)
                ? map.get(key).range(from, to)
                : Collections.emptyList();
    }

    @Override
    public List<ZSet.ScoreMember> rangeWithScores(String key, int from, int to) {
        return map.containsKey(key)
                ? map.get(key).rangeWithScores(from, to)
                : Collections.emptyList();
    }

    @Override
    public int size(String key) {
        return map.containsKey(key)
                ? map.get(key).size()
                : 0;
    }
}
