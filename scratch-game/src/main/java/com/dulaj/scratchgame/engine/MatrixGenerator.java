package com.dulaj.scratchgame.engine;

import com.dulaj.scratchgame.config.GameConfig;
import com.dulaj.scratchgame.model.Cell;

import java.util.*;


// Builds the matrix using configured symbol weights (standard + bonus chance)

public class MatrixGenerator {

    private final GameConfig config;
    private final Random random = new Random();

    public MatrixGenerator(GameConfig config) {
        this.config = config;
    }


    // Generate symbol matrix based on weights

    public Cell[][] generateMatrix() {
        int rows = config.getRows();
        int cols = config.getColumns();
        var matrix = new Cell[rows][cols];

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                var symbol = pickSymbolForCell(row, col);
                matrix[row][col] = new Cell(row, col, symbol);
            }
        }

        return matrix;
    }


    // Decide whether to use standard or bonus symbol for this cell

    private String pickSymbolForCell(int row, int col) {
        var weights = config.getStandardSymbolWeightsForCell(row, col);

        // Fallback to default weights if none are defined for this cell
        if (weights == null || weights.isEmpty()) {
            weights = config.getDefaultStandardSymbolWeights();
        }

        // 15% chance to use a bonus symbol instead of regular
        return (random.nextDouble() < 0.15)
                ? pickWeightedRandom(config.getBonusSymbolWeights())
                : pickWeightedRandom(weights);
    }


    // Pick a symbol using weighted random selection

    private String pickWeightedRandom(Map<String, Integer> weights) {
        int total = weights.values().stream().mapToInt(Integer::intValue).sum();
        int roll = random.nextInt(total);
        int cumulative = 0;

        for (var entry : weights.entrySet()) {
            cumulative += entry.getValue();
            if (roll < cumulative) {
                return entry.getKey();
            }
        }

        throw new IllegalStateException("Symbol selection failed. Check weight config.");
    }
}