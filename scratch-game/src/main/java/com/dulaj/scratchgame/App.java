package com.dulaj.scratchgame;

import com.dulaj.scratchgame.config.GameConfig;
import com.dulaj.scratchgame.engine.GameRunner;
import com.dulaj.scratchgame.engine.ConfigLoader;


public class App 
{
    public static void main(String[] args) {
        if (args.length < 4) {
            System.out.println("Usage: java -jar scratch-game.jar --config <config-file> --betting-amount <amount>");
            return;
        }

        String configPath = null;
        int betAmount = 0;

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "--config" -> configPath = args[i + 1];
                case "--betting-amount" -> betAmount = Integer.parseInt(args[i + 1]);
            }
        }

        if (configPath == null || betAmount <= 0) {
            System.out.println("Invalid arguments.");
            return;
        }

        GameConfig config = ConfigLoader.load(configPath);
        new GameRunner(config).run(betAmount);
    }
}