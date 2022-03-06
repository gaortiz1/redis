package com.witbooking.redis.web.controller;

import com.witbooking.redis.core.command.ZAdd;
import com.witbooking.redis.core.command.ZCard;
import com.witbooking.redis.core.command.ZRange;
import com.witbooking.redis.core.command.ZRank;
import com.witbooking.redis.core.command.dispatcher.SortedSetDispatcher;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Pattern;

@RestController
@RequestMapping
@Validated
public class SortedSetController {

    private static final String REGEX_FOR_NUMBERS_LETTERS_UNDERSCORE_MIDDLEDASH = "[a-zA-Z0-9-_]+";

    private final SortedSetDispatcher sortedSetDispatcher;

    public SortedSetController(SortedSetDispatcher sortedSetDispatcher) {
        this.sortedSetDispatcher = sortedSetDispatcher;
    }

    @PostMapping("/zadd")
    public String zadd(
            @RequestParam("key")
            @Pattern(
                    regexp = REGEX_FOR_NUMBERS_LETTERS_UNDERSCORE_MIDDLEDASH,
                    message = "key can only be numeric, letter, underscore(_) and middle-dash(_)"
            ) final String key,
            @RequestParam(value = "score", required = false) final Integer score,
            @RequestParam("member")
            @Pattern(
                    regexp = REGEX_FOR_NUMBERS_LETTERS_UNDERSCORE_MIDDLEDASH,
                    message = "value can only be numeric, letter, underscore(_) and middle-dash(_)"
            ) final String member
    ) {
        return sortedSetDispatcher.dispatch(ZAdd.with(key, score, member));
    }

    @GetMapping("/zcard")
    public String zcard(
            @RequestParam("key")
            @Pattern(
                    regexp = REGEX_FOR_NUMBERS_LETTERS_UNDERSCORE_MIDDLEDASH,
                    message = "key can only be numeric, alphabetic, underscore(_) and middle dash(_)"
            ) final String key
    ) {
        return sortedSetDispatcher.dispatch(ZCard.of(key));
    }

    @GetMapping("/zrank")
    public String zrank(
            @RequestParam("key")
            @Pattern(
                    regexp = REGEX_FOR_NUMBERS_LETTERS_UNDERSCORE_MIDDLEDASH,
                    message = "key can only be numeric, alphabetic, underscore(_) and middle dash(_)"
            ) final String key,

            @RequestParam("member")
            @Pattern(
                    regexp = REGEX_FOR_NUMBERS_LETTERS_UNDERSCORE_MIDDLEDASH,
                    message = "value can only be numeric, letter, underscore(_) and middle-dash(_)"
            ) final String member
    ) {
        return sortedSetDispatcher.dispatch(ZRank.of(key, member));
    }

    @GetMapping("/zrange")
    public String zrange(
            @RequestParam("key")
            @Pattern(
                    regexp = REGEX_FOR_NUMBERS_LETTERS_UNDERSCORE_MIDDLEDASH,
                    message = "key can only be numeric, alphabetic, underscore(_) and middle dash(_)"
            ) final String key,
            @RequestParam(value = "start", required = false) final Integer start,
            @RequestParam(value = "stop", required = false) final Integer stop
    ) {
        return sortedSetDispatcher.dispatch(ZRange.range(key, start, stop));
    }

}
