package com.witbooking.redis.core.command;

import com.witbooking.redis.core.command.dispatcher.StringsCommand;
import com.witbooking.redis.core.template.StringsTemplate;

public class Get implements StringsCommand {

    private final String key;

    public Get(String key) {
        this.key = key;
    }

    public static Get of(String key) {
        return new Get(key);
    }

    @Override
    public String execute(StringsTemplate stringsTemplate) {
        return stringsTemplate.get(key);
    }
}
