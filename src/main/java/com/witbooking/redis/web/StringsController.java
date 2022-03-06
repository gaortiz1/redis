package com.witbooking.redis.web;

import com.witbooking.redis.core.command.*;
import com.witbooking.redis.core.command.dispatcher.StringsDispatcher;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping()
public class StringsController {

    private final StringsDispatcher stringsDispatcher;

    public StringsController(StringsDispatcher stringsDispatcher) {
        this.stringsDispatcher = stringsDispatcher;
    }

    @PostMapping("/set")
    public String set(@RequestBody Set set) {
        return stringsDispatcher.dispatch(set);
    }

    @DeleteMapping("/del/{key}")
    public String del(@PathVariable String key) {
        return stringsDispatcher.dispatch(Del.of(key));
    }

    @GetMapping("/get/{key}")
    public String get(@PathVariable String key) {
        return stringsDispatcher.dispatch(Get.of(key));
    }

    @GetMapping("/dbsize")
    public String dbsize() {
        return stringsDispatcher.dispatch(DBSize.get());
    }

    @GetMapping("/incr/{key}")
    public String incr(@PathVariable String key) {
        return stringsDispatcher.dispatch(Incr.of(key));
    }
}
