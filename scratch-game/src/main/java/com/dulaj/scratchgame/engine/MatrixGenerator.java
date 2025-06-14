package com.dulaj.scratchgame.engine;

import com.dulaj.scratchgame.config.GameConfig;
import com.dulaj.scratchgame.model.Cell;
import com.dulaj.scratchgame.model.Symbol;

import java.util.*;

public class MatrixGenerator {

    private final GameConfig config;
    private final Random random = new Random();

    public MatrixGenerator(GameConfig config) {
        this.config = config;
    }

    public Cell[][] generateMatrix() {
        int rows = config.getRows();
        int cols = config.getColumns();
        Cell[][] matrix = new Cell[rows][cols];

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                String symbol = pickSymbolForCell(row, col);
                matrix[row][col] = new Cell(row, col, symbol);
            }
        }

        return matrix;
    }

    private String pickSymbolForCell(int row, int col) {
        Map<String, Integer> weights = config.getStandardSymbolWeightsForCell(row, col);
        if (weights == null || weights.isEmpty()) {
            weights = config.getDefaultStandardSymbolWeights();
        }


        if (random.nextDouble() < 0.15) {
            return pickWeightedRandom(config.getBonusSymbolWeights());
        }

        return pickWeightedRandom(weights);
    }

    private String pickWeightedRandom(Map<String, Integer> weights) {
        int total = weights.values().stream().mapToInt(i -> i).sum();
        int roll = random.nextInt(total);
        int cumulative = 0;
        for (Map.Entry<String, Integer> entry : weights.entrySet()) {
            cumulative += entry.getValue();
            if (roll < cumulative) {
                return entry.getKey();
            }
        }
        throw new IllegalStateException("Failed to pick symbol");
    }
}