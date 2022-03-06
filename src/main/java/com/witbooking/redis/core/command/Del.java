package com.witbooking.redis.core.command;

import com.witbooking.redis.core.command.dispatcher.StringsCommand;
import com.witbooking.redis.core.template.StringsTemplate;

public class Del implements StringsCommand {

    private final String key;

    public Del(String key) {
        this.key = key;
    }

    public static Del of(String key) {
        return new Del(key);
    }

    @Override
    public String execute(StringsTemplate stringsTemplate) {
        return stringsTemplate.delete(key);
    }
}
