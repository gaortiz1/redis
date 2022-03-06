package com.witbooking.redis.core.command.dispatcher;

public interface CommandDispatcher<C, R> {

    R dispatch(C command);

}
