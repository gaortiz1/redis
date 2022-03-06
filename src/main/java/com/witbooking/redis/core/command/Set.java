package com.witbooking.redis.core.command;

import com.witbooking.redis.core.template.StringsTemplate;

import java.util.Optional;

public class Set implements StringsCommand {

    private final String key;
    private final String value;
    private final Long expirationInSeconds;

    public Set(String key, String value, Long expirationInSeconds) {
        this.key = key;
        this.value = value;
        this.expirationInSeconds = expirationInSeconds;
    }

    @Override
    public String execute(StringsTemplate stringsTemplate) {
        return Optional
                .ofNullable(expirationInSeconds)
                .map(expirationInSeconds -> stringsTemplate.set(key, value, expirationInSeconds))
                .orElseGet(() -> stringsTemplate.set(key, value));
    }

    public static Set with(String key, String value, Long expirationInSeconds) {
        return new Set(key, value, expirationInSeconds);
    }
}
