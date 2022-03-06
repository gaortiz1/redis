package com.witbooking.redis.core.command.dispatcher;

import com.witbooking.redis.core.command.SortedSetCommand;
import com.witbooking.redis.core.template.SortedSetTemplate;
import org.springframework.stereotype.Service;

@Service
public class SortedSetDispatcher implements CommandDispatcher<SortedSetCommand, String> {

    private final SortedSetTemplate sortedSetTemplate;

    public SortedSetDispatcher(SortedSetTemplate sortedSetTemplate) {
        this.sortedSetTemplate = sortedSetTemplate;
    }

    @Override
    public String dispatch(SortedSetCommand command) {
        return command.execute(sortedSetTemplate);
    }
}
