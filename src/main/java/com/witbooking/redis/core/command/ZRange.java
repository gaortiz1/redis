package com.witbooking.redis.core.command;

import com.witbooking.redis.core.template.SortedSetTemplate;

public class ZRange implements SortedSetCommand {

    private final String key;
    private final Integer start;
    private final Integer stop;

    public ZRange(String key, Integer start, Integer stop) {
        this.key = key;
        this.start = start;
        this.stop = stop;
    }

    @Override
    public String execute(SortedSetTemplate sortedSetTemplate) {
        return String.join(", ", sortedSetTemplate.range(key, start, stop));
    }

    public static ZRange range(String key, Integer start, Integer stop) {
        return new ZRange(key, start, start);
    }
}
