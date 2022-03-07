package com.witbooking.redis.core.template;

import com.witbooking.redis.core.annotation.Concurrent;
import com.witbooking.redis.core.storage.SortedSet;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.List;

import static com.witbooking.redis.core.template.enums.Result.FALSE;
import static com.witbooking.redis.core.template.enums.Result.NIL;
import static com.witbooking.redis.core.template.enums.Result.TRUE;

@Component
public class DefaultSortedSetTemplate implements SortedSetTemplate {

    private final SortedSet sortedSet;

    public DefaultSortedSetTemplate(final SortedSet sortedSet) {
        this.sortedSet = sortedSet;
    }

    @Concurrent
    public String add(String key, Integer score, String member) {
        Assert.notNull(key, "key is required");
        Assert.notNull(score, "score is required");
        Assert.notNull(member, "member is required");

        return sortedSet.add(key, score, member) ? TRUE.getValue(): FALSE.getValue();
    }

    @Concurrent
    public String rank(String key, String member) {
        Assert.notNull(key, "key is required");
        Assert.notNull(member, "member is required");

        return sortedSet.rank(key, member).map(Object::toString).orElse(NIL.getValue());
    }

    @Concurrent
    public int size(String key) {
        Assert.notNull(key, "key is required");
        return sortedSet.size(key);
    }

    @Concurrent
    public List<String> range(String key, Integer start, Integer stop) {
        Assert.notNull(key, "key is required");
        Assert.notNull(start, "start is required");
        Assert.notNull(stop, "stop is required");

        Assert.isTrue(start >= 0, "start must be greater than 0");
        Assert.isTrue(stop >= 0, "stop must be greater than 0");
        Assert.isTrue(start < stop, "start must be less than stop");

        return sortedSet.range(key, start, stop);
    }
}
