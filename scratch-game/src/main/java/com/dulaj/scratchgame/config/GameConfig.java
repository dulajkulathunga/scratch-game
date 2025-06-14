package com.dulaj.scratchgame.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Setter
@Getter
public class GameConfig {
    public int columns = 3;
    public int rows = 3;

    public Map<String, SymbolDefinition> symbols;
    public Probabilities probabilities;
    public Map<String, WinCombination> win_combinations;

    public static class SymbolDefinition {
        public double reward_multiplier;
        public String type;
        public String impact;
        public Integer extra;
    }

    public static class Probabilities {
        public List<StandardSymbolProbability> standard_symbols;
        public BonusSymbolProbability bonus_symbols;
    }

    public static class StandardSymbolProbability {
        public int column;
        public int row;
        public Map<String, Integer> symbols;
    }

    public static class BonusSymbolProbability {
        public Map<String, Integer> symbols;
    }

    public static class WinCombination {
        public double reward_multiplier;
        public String when;
        public Integer count;
        public String group;
        public List<List<String>> covered_areas;
    }

    /**
     * Fetches symbol probabilities for a specific cell.
     */
    public Map<String, Integer> getStandardSymbolWeightsForCell(int row, int col) {
        if (probabilities == null || probabilities.standard_symbols == null) return null;

        return probabilities.standard_symbols.stream()
                .filter(p -> p.row == row && p.column == col)
                .findFirst()
                .map(p -> p.symbols)
                .orElse(null);
    }

    /**
     * Fallback standard symbol weights (from the first defined cell).
     */
    public Map<String, Integer> getDefaultStandardSymbolWeights() {
        if (probabilities != null && probabilities.standard_symbols != null && !probabilities.standard_symbols.isEmpty()) {
            return probabilities.standard_symbols.get(0).symbols;
        }
        return Map.of(); // Java 9+ immutable empty map
    }

    /**
     * Bonus symbol weights from config.
     */
    public Map<String, Integer> getBonusSymbolWeights() {
        if (probabilities != null && probabilities.bonus_symbols != null) {
            return probabilities.bonus_symbols.symbols;
        }
        return Map.of(); // Java 9+ immutable empty map
    }


}