package com.dulaj.scratchgame.common;

public enum CliArgument {
    CONFIG("--config"),
    BETTING_AMOUNT("--betting-amount");

    private final String flag;

    CliArgument(String flag) {
        this.flag = flag;
    }

    public String getFlag() {
        return flag;
    }

    public static CliArgument fromFlag(String flag) {
        for (CliArgument arg : values()) {
            if (arg.flag.equals(flag)) {
                return arg;
            }
        }
        return null;
    }
}