package com.dulaj.scratchgame.model;

public enum SymbolType {
    STANDARD("standard"),
    BONUS("bonus");

    private final String value;

    SymbolType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static SymbolType from(String value) {
        for (SymbolType type : values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid symbol type: " + value);
    }
}