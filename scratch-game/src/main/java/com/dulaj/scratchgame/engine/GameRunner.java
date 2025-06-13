package com.dulaj.scratchgame.engine;

import com.dulaj.scratchgame.config.GameConfig;

public class GameRunner {
    private final GameConfig config;

    public GameRunner(GameConfig config) {
        this.config = config;
    }

    public void run(int betAmount) {
        System.out.println("Running game with bet amount: " + betAmount);
        // Here you’ll call MatrixGenerator, WinEvaluator, etc.
    }
}
