package com.witbooking.redis.core.command;

import com.witbooking.redis.core.template.SortedSetTemplate;

public class ZRank implements SortedSetCommand {

    private final String key;
    private final String member;

    public ZRank(String key, String member) {
        this.key = key;
        this.member = member;
    }

    @Override
    public String execute(SortedSetTemplate sortedSetTemplate) {
        return String.valueOf(sortedSetTemplate.rank(key, member));
    }

    public static ZRank of(String key, String member) {
        return new ZRank(key, member);
    }
}
