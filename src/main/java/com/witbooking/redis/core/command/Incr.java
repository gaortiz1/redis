package com.witbooking.redis.core.command;

import com.witbooking.redis.core.template.StringsTemplate;

public class Incr implements StringsCommand {

    private final String key;

    public Incr(String key) {
        this.key = key;
    }

    @Override
    public String execute(StringsTemplate stringsTemplate) {
        return stringsTemplate.incrementsValue(key);
    }

    public static Incr of(String key) {
        return new Incr(key);
    }
}
