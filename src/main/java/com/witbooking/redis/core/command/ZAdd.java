package com.witbooking.redis.core.command;

import com.witbooking.redis.core.template.SortedSetTemplate;

public class ZAdd implements SortedSetCommand{

    private final String key;
    private final Integer score;
    private final String member;

    public ZAdd(String key, Integer score, String member) {
        this.key = key;
        this.score = score;
        this.member = member;
    }

    @Override
    public String execute(SortedSetTemplate sortedSetTemplate) {
        return sortedSetTemplate.add(key, score, member);
    }

    public static ZAdd with(String key, Integer score, String member){
        return new ZAdd(key, score, member);
    }
}
