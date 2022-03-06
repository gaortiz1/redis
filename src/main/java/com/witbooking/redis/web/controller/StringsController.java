package com.witbooking.redis.web.controller;

import com.witbooking.redis.core.command.DBSize;
import com.witbooking.redis.core.command.Del;
import com.witbooking.redis.core.command.Get;
import com.witbooking.redis.core.command.Incr;
import com.witbooking.redis.core.command.Set;
import com.witbooking.redis.core.command.dispatcher.StringsDispatcher;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Pattern;

@RestController
@RequestMapping
@Validated
public class StringsController {

    private static final String REGEX_FOR_NUMBERS_LETTERS_UNDERSCORE_MIDDLEDASH = "[a-zA-Z0-9-_]+";

    private final StringsDispatcher stringsDispatcher;

    public StringsController(StringsDispatcher stringsDispatcher) {
        this.stringsDispatcher = stringsDispatcher;
    }

    @PostMapping("/set")
    public String set(
            @RequestParam("key")
            @Pattern(
                    regexp = REGEX_FOR_NUMBERS_LETTERS_UNDERSCORE_MIDDLEDASH,
                    message = "key can only be numeric, letter, underscore(_) and middle-dash(_)"
            ) final String key,

            @RequestParam("value")
            @Pattern(
                    regexp = REGEX_FOR_NUMBERS_LETTERS_UNDERSCORE_MIDDLEDASH,
                    message = "value can only be numeric, letter, underscore(_) and middle-dash(_)"
            ) final String value,

            @RequestParam(value = "expiration", required = false) final Long expiration
    ) {
        return stringsDispatcher.dispatch(Set.with(key, value, expiration));
    }

    @DeleteMapping("/del")
    public String del(
            @RequestParam("key")
            @Pattern(
                    regexp = REGEX_FOR_NUMBERS_LETTERS_UNDERSCORE_MIDDLEDASH,
                    message = "key can only be numeric, letter, underscore(_) and middle-dash(_)"
            ) final String key
    ) {
        return stringsDispatcher.dispatch(Del.of(key));
    }

    @GetMapping("/get")
    public String get(
            @RequestParam("key")
            @Pattern(
                    regexp = REGEX_FOR_NUMBERS_LETTERS_UNDERSCORE_MIDDLEDASH,
                    message = "key can only be numeric, letter, underscore(_) and middle-dash(_)"
            ) final String key
    ) {
        return stringsDispatcher.dispatch(Get.of(key));
    }

    @GetMapping("/dbsize")
    public String dbsize() {
        return stringsDispatcher.dispatch(DBSize.get());
    }

    @GetMapping("/incr")
    public String incr(
            @RequestParam("key")
            @Pattern(
                    regexp = REGEX_FOR_NUMBERS_LETTERS_UNDERSCORE_MIDDLEDASH,
                    message = "key can only be numeric, alphabetic, underscore(_) and middle dash(_)"
            ) final String key
    ) {
        return stringsDispatcher.dispatch(Incr.of(key));
    }
}
