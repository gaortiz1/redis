package com.witbooking.redis.core.command;

import com.witbooking.redis.core.template.SortedSetTemplate;

@FunctionalInterface
public interface SortedSetCommand {

    String execute(SortedSetTemplate sortedSetTemplate);

}
