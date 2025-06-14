package com.dulaj.scratchgame.engine;

import com.dulaj.scratchgame.config.GameConfig;
import com.dulaj.scratchgame.config.GameConfig;
import com.dulaj.scratchgame.model.Cell;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.util.*;

public class GameRunner {
    private final GameConfig config;

    public GameRunner(GameConfig config) {
        this.config = config;
    }

    public void run(int betAmount) {
        System.out.println("Running game with bet amount: " + betAmount);


        MatrixGenerator generator = new MatrixGenerator(config);
        Cell[][] matrix = generator.generateMatrix();


        WinEvaluator evaluator = new WinEvaluator(config);
        Map<String, List<String>> wins = evaluator.evaluate(matrix);


        double reward = 0;
        for (Map.Entry<String, List<String>> entry : wins.entrySet()) {
            String symbol = entry.getKey();
            List<String> rules = entry.getValue();

            GameConfig.SymbolDefinition symbolDef = config.getSymbols().get(symbol);
            if (symbolDef == null) continue;

            double symbolReward = symbolDef.reward_multiplier;

            for (String rule : rules) {
                double ruleMultiplier = config.getWin_combinations().get(rule).reward_multiplier;
                symbolReward *= ruleMultiplier;
            }

            reward += betAmount * symbolReward;
        }


        BonusHandler bonusHandler = new BonusHandler(config);
        BonusHandler.BonusResult bonusResult = bonusHandler.applyBonus(matrix, !wins.isEmpty(), reward);


        try {
            List<List<String>> matrixAsList = new ArrayList<>();
            for (Cell[] row : matrix) {
                List<String> rowList = new ArrayList<>();
                for (Cell cell : row) {
                    rowList.add(cell.symbol());
                }
                matrixAsList.add(rowList);
            }

            Map<String, Object> output = new LinkedHashMap<>();
            output.put("matrix", matrixAsList);
            output.put("reward", bonusResult.reward());
            output.put("applied_winning_combinations", wins);
            output.put("applied_bonus_symbol", bonusResult.bonusSymbol());

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(output);
            System.out.println(json);

        } catch (Exception e) {
            System.err.println("Failed to format output: " + e.getMessage());
            e.printStackTrace();
        }
    }
}