package com.dulaj.scratchgame.engine;
import com.dulaj.scratchgame.config.GameConfig;
import com.dulaj.scratchgame.model.Cell;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;


 //GameRunner handles the full scratch game execution flow.

public class GameRunner {
    private final GameConfig config;

    public GameRunner(GameConfig config) {
        this.config = config;
    }

     //Run game logic with the given bet amount.
    public void run(int betAmount) {
        System.out.println("Running game with bet amount: " + betAmount);

        // Step 1: Generate the symbol matrix
        var generator = new MatrixGenerator(config);
        Cell[][] matrix = generator.generateMatrix();

        // Step 2: Check for winning combinations in the matrix
        var evaluator = new WinEvaluator(config);
        Map<String, List<String>> wins = evaluator.evaluate(matrix);

        // Step 3: Calculate total reward based on matches and rules
        double reward = calculateReward(wins, betAmount);

        // Step 4: Apply any bonus if applicable
        var bonusHandler = new BonusHandler(config);
        var bonusResult = bonusHandler.applyBonus(matrix, !wins.isEmpty(), reward);

        // Step 5: Print final result as pretty JSON
        printFormattedResult(matrix, bonusResult, wins);
    }

    // Calculate reward by applying symbol and rule multipliers
    private double calculateReward(Map<String, List<String>> wins, int betAmount) {
        double reward = 0.0;

        for (var entry : wins.entrySet()) {
            String symbol = entry.getKey();
            List<String> appliedRules = entry.getValue();

            var symbolDef = config.getSymbols().get(symbol);

            if (Objects.nonNull(symbolDef)) {
                double symbolReward = symbolDef.reward_multiplier;

                for (String ruleKey : appliedRules) {
                    var rule = config.getWin_combinations().get(ruleKey);
                    symbolReward *= rule.reward_multiplier;
                }

                reward += betAmount * symbolReward;
            }
        }

        return reward;
    }

    // Print matrix, reward, and bonus info in JSON for easy debugging
    private void printFormattedResult(Cell[][] matrix, BonusHandler.BonusResult bonusResult, Map<String, List<String>> wins) {
        try {
            List<List<String>> matrixView = new ArrayList<>();
            for (Cell[] row : matrix) {
                List<String> rowSymbols = new ArrayList<>();
                for (Cell cell : row) {
                    rowSymbols.add(cell.symbol());
                }
                matrixView.add(rowSymbols);
            }

            Map<String, Object> output = new LinkedHashMap<>();
            output.put("matrix", matrixView);
            output.put("reward", bonusResult.reward());
            output.put("applied_winning_combinations", wins);
            output.put("applied_bonus_symbol", bonusResult.bonusSymbol());

            String json = new ObjectMapper()
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(output);

            System.out.println(json);
        } catch (Exception e) {
            System.err.println("Failed to format output: " + e.getMessage());
        }
    }
}