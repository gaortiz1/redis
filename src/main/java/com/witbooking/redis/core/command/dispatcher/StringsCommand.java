package com.witbooking.redis.core.command.dispatcher;

import com.witbooking.redis.core.template.StringsTemplate;

@FunctionalInterface
public interface StringsCommand {

    String execute(StringsTemplate stringsTemplate);

}
