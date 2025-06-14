package com.dulaj.scratchgame.model;

public enum ImpactType {
    MULTIPLY_REWARD("multiply_reward"),
    EXTRA_BONUS("extra_bonus"),
    MISS("miss");

    private final String value;

    ImpactType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static ImpactType from(String value) {
        for (ImpactType t : values()) {
            if (t.value.equals(value)) return t;
        }
        throw new IllegalArgumentException("Invalid impact type: " + value);
    }
}