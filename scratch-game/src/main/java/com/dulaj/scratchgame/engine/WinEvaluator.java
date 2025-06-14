package com.dulaj.scratchgame.engine;
import com.dulaj.scratchgame.config.GameConfig;
import com.dulaj.scratchgame.model.Cell;
import com.dulaj.scratchgame.model.WhenType;

import java.util.*;


// Checks the matrix for any winning patterns based on config rules
public class WinEvaluator {

    private final GameConfig config;

    public WinEvaluator(GameConfig config) {
        this.config = config;
    }


    // Evaluate the given matrix and return matching win combinations per symbol

    public Map<String, List<String>> evaluate(Cell[][] matrix) {
        Map<String, List<String>> result = new HashMap<>();

        // Count how many times each symbol appears in the matrix
        Map<String, Long> symbolCounts = countSymbols(matrix);

        // Handle win rules based on total count of same symbol
        for (Map.Entry<String, GameConfig.WinCombination> entry : config.win_combinations.entrySet()) {
            String key = entry.getKey();
            GameConfig.WinCombination rule = entry.getValue();

            if (WhenType.from(rule.when) == WhenType.SAME_SYMBOLS) {
                for (var sym : symbolCounts.entrySet()) {
                    if (sym.getValue() >= rule.count) {
                        result.computeIfAbsent(sym.getKey(), k -> new ArrayList<>()).add(key);
                    }
                }
            }
        }

        // Handle win rules based on linear patterns (rows, columns, diagonals)
        for (Map.Entry<String, GameConfig.WinCombination> entry : config.win_combinations.entrySet()) {
            String key = entry.getKey();
            GameConfig.WinCombination rule = entry.getValue();

            if (WhenType.from(rule.when) == WhenType.LINEAR_SYMBOLS) {
                for (List<String> positions : rule.covered_areas) {
                    String symbol = null;
                    boolean allMatch = true;

                    for (String pos : positions) {
                        String[] parts = pos.split(":");
                        int row = Integer.parseInt(parts[0]);
                        int col = Integer.parseInt(parts[1]);
                        String current = matrix[row][col].symbol();

                        // Initialize symbol on first pass, then compare the rest
                        if (Objects.isNull(symbol)) {
                            symbol = current;
                        } else if (!symbol.equals(current)) {
                            allMatch = false;
                            break;
                        }
                    }

                    if (allMatch) {
                        result.computeIfAbsent(symbol, k -> new ArrayList<>()).add(key);
                    }
                }
            }
        }

        return result;
    }


      //Count occurrences of each symbol in the matrix.

    private Map<String, Long> countSymbols(Cell[][] matrix) {
        Map<String, Long> counts = new HashMap<>();
        for (Cell[] row : matrix) {
            for (Cell cell : row) {
                counts.merge(cell.symbol(), 1L, Long::sum);
            }
        }
        return counts;
    }
}