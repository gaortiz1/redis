package com.witbooking.redis.core.command;

import com.witbooking.redis.core.template.SortedSetTemplate;

public class ZCard implements SortedSetCommand {

    private final String key;

    public ZCard(String key) {
        this.key = key;
    }

    @Override
    public String execute(SortedSetTemplate sortedSetTemplate) {
        return String.valueOf(sortedSetTemplate.size(key));
    }

    public static ZCard of(String key) {
        return new ZCard(key);
    }
}
