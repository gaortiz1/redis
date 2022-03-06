package com.witbooking.redis.core.command;

import com.witbooking.redis.core.command.dispatcher.StringsCommand;
import com.witbooking.redis.core.template.StringsTemplate;
import lombok.Data;

import java.util.Optional;

@Data
public class Set implements StringsCommand {

    private String key;
    private String value;
    private Long expirationInSeconds;

    public Set(String key, String value, Long expirationInSeconds) {
        this.key = key;
        this.value = value;
        this.expirationInSeconds = expirationInSeconds;
    }

    public Set() {
    }

    @Override
    public String execute(StringsTemplate stringsTemplate) {
        return Optional
                .ofNullable(expirationInSeconds)
                .map(expirationInSeconds -> stringsTemplate.set(key, value, expirationInSeconds))
                .orElseGet(() -> stringsTemplate.set(key, value));
    }

    public static Set with(String key, String value, Long duration) {
        return new Set(key, value, duration);
    }
}
