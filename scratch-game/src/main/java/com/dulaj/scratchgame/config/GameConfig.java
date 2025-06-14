package com.dulaj.scratchgame.config;

import java.util.List;
import java.util.Map;

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
}