package com.witbooking.redis.core.command.dispatcher;

import com.witbooking.redis.core.template.StringsTemplate;
import org.springframework.stereotype.Service;

@Service
public class StringsDispatcher implements CommandDispatcher<StringsCommand, String> {

    private final StringsTemplate stringsTemplate;

    public StringsDispatcher(StringsTemplate stringsTemplate) {
        this.stringsTemplate = stringsTemplate;
    }

    @Override
    public String dispatch(StringsCommand command) {
        return command.execute(stringsTemplate);
    }
}
