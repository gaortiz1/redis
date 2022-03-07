package com.witbooking.redis.core.template.enums;

public enum Result {
    OK("OK"),
    NIL("nil"),
    TRUE("1"),
    FALSE("0");

    private String value;

    Result(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
