package com.witbooking.redis.core.command;

import com.witbooking.redis.core.command.dispatcher.StringsCommand;
import com.witbooking.redis.core.template.StringsTemplate;

public class DBSize implements StringsCommand {

    public static DBSize get() {
        return new DBSize();
    }

    @Override
    public String execute(StringsTemplate stringsTemplate) {
        return String.valueOf(stringsTemplate.size());
    }
}
