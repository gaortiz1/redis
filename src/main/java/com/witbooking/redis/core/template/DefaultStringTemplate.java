package com.witbooking.redis.core.template;

import com.witbooking.redis.core.annotation.Concurrent;
import com.witbooking.redis.core.storage.Strings;
import com.witbooking.redis.core.utils.NumberUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.time.temporal.ChronoUnit;

@Component
public class DefaultStringTemplate implements StringsTemplate {

    private final static long MAX_DURATION_IN_SECONDS = ChronoUnit.YEARS.getDuration().getSeconds();
    private final static int DEFAULT_VALUE_AS_NUMBER = 1;

    private final static String NIL = "nil";
    private final static String OK = "OK";
    private final static String TRUE = "1";
    private final static String FALSE = "0";


    private final Strings strings;

    public DefaultStringTemplate(Strings strings) {
        this.strings = strings;
    }

    @Concurrent
    public String set(String key, String value) {
        return set(key, value, MAX_DURATION_IN_SECONDS);
    }

    @Concurrent
    public String set(String key, String value, long expirationInSeconds) {
        Assert.notNull(key, "key is required");
        Assert.notNull(value, "value is required");
        Assert.isTrue(expirationInSeconds > 0, "invalid expire time in 'set' command");
        Assert.isTrue(expirationInSeconds < MAX_DURATION_IN_SECONDS, "value is not an integer or out of range");

        strings.get(key).ifPresentOrElse(
                currentValue -> strings.replace(key, value),
                () -> strings.set(key, value, expirationInSeconds)
        );

        return OK;
    }

    @Concurrent
    public String incrementsValue(String key) {
        Assert.notNull(key, "key is required");

        return strings.get(key)
                .map(valueAsString -> strings.replace(key, NumberUtils.parseToInt(valueAsString, "value is not an integer") + 1))
                .orElseGet(() -> strings.set(key, DEFAULT_VALUE_AS_NUMBER, MAX_DURATION_IN_SECONDS));
    }

    @Concurrent
    public String get(String key) {
        Assert.notNull(key, "key is required");
        return strings.get(key).orElse(NIL);
    }

    @Concurrent
    public String delete(String key) {
        Assert.notNull(key, "key is required");
        return strings.delete(key).map(value -> TRUE).orElse(FALSE);
    }

    @Concurrent
    public int size() {
        return strings.size();
    }
}
