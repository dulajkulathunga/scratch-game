package com.dulaj.scratchgame;

import com.dulaj.scratchgame.common.CliArgument;
import com.dulaj.scratchgame.config.GameConfig;
import com.dulaj.scratchgame.engine.ConfigLoader;
import com.dulaj.scratchgame.engine.GameRunner;


import java.util.Objects;

public class App {

    public static void main(String[] args) {
        // Validate basic usage
        if (args.length < 4) {
            printUsage();
            return;
        }

        String configPath = extractConfigPath(args);
        int betAmount = extractBettingAmount(args);

        if (isInvalid(configPath, betAmount)) {
            System.out.println("Invalid arguments. Please check the config path and betting amount.");
            return;
        }

        GameConfig config = ConfigLoader.load(configPath);
        new GameRunner(config).run(betAmount);
    }

    private static void printUsage() {
        System.out.println("Usage: java -jar scratch-game.jar --config <config-file> --betting-amount <amount>");
    }

    private static String extractConfigPath(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if (CliArgument.fromFlag(args[i]) == CliArgument.CONFIG && i + 1 < args.length) {
                return args[i + 1];
            }
        }
        return null;
    }

    private static int extractBettingAmount(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if (CliArgument.fromFlag(args[i]) == CliArgument.BETTING_AMOUNT && i + 1 < args.length) {
                try {
                    return Integer.parseInt(args[i + 1]);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid betting amount. Please enter a number.");
                    return -1;
                }
            }
        }
        return -1;
    }

    private static boolean isInvalid(String configPath, int betAmount) {
        return Objects.requireNonNullElse(configPath, "").isBlank() || betAmount <= 0;
    }
}