package com.dulaj.scratchgame.model;

public enum WhenType {
    SAME_SYMBOLS("same_symbols"),
    LINEAR_SYMBOLS("linear_symbols");

    private final String value;

    WhenType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static WhenType from(String value) {
        for (WhenType t : values()) {
            if (t.value.equals(value)) return t;
        }
        throw new IllegalArgumentException("Invalid when type: " + value);
    }

}