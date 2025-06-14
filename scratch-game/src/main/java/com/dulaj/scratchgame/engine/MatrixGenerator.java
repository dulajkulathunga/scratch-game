package com.dulaj.scratchgame.engine;

import com.dulaj.scratchgame.config.GameConfig;

import java.util.*;
public class MatrixGenerator {
    private final GameConfig config;
    private final Random random = new Random();

    public MatrixGenerator(GameConfig config) {
        this.config = config;
    }

    public List<List<String>> generateMatrix() {
        int rows = config.rows;
        int cols = config.columns;
        List<List<String>> matrix = new ArrayList<>();

        for (int row = 0; row < rows; row++) {
            List<String> rowList = new ArrayList<>();
            for (int col = 0; col < cols; col++) {
                String symbol = getRandomStandardSymbol(col, row);
                rowList.add(symbol);
            }
            matrix.add(rowList);
        }

        applyRandomBonusSymbol(matrix);

        return matrix;
    }

    private String getRandomStandardSymbol(int col, int row) {

        GameConfig.StandardSymbolProbability prob = config.probabilities.standard_symbols.stream()
                .filter(p -> p.column == col && p.row == row)
                .findFirst()
                .orElse(config.probabilities.standard_symbols.get(0));

        Map<String, Integer> symbols = prob.symbols;
        int totalWeight = symbols.values().stream().mapToInt(i -> i).sum();
        int rand = random.nextInt(totalWeight);

        int cumulative = 0;
        for (Map.Entry<String, Integer> entry : symbols.entrySet()) {
            cumulative += entry.getValue();
            if (rand < cumulative) {
                return entry.getKey();
            }
        }

        return "F";
    }

    private void applyRandomBonusSymbol(List<List<String>> matrix) {
        List<String> bonusSymbols = new ArrayList<>(config.probabilities.bonus_symbols.symbols.keySet());
        if (bonusSymbols.isEmpty()) return;

        String bonus = getRandomBonusSymbol();
        int randRow = random.nextInt(config.rows);
        int randCol = random.nextInt(config.columns);
        matrix.get(randRow).set(randCol, bonus);
    }

    private String getRandomBonusSymbol() {
        Map<String, Integer> symbols = config.probabilities.bonus_symbols.symbols;
        int totalWeight = symbols.values().stream().mapToInt(i -> i).sum();
        int rand = random.nextInt(totalWeight);

        int cumulative = 0;
        for (Map.Entry<String, Integer> entry : symbols.entrySet()) {
            cumulative += entry.getValue();
            if (rand < cumulative) {
                return entry.getKey();
            }
        }

        return "MISS";
    }
}