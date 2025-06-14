package com.dulaj.scratchgame.engine;
import com.dulaj.scratchgame.config.GameConfig;
import com.dulaj.scratchgame.model.Cell;

import java.util.List;
import java.util.Optional;

public class BonusHandler {
    private final GameConfig config;

    public BonusHandler(GameConfig config) {
        this.config = config;
    }

    public BonusResult applyBonus(Cell[][] matrix, boolean hasWinningCombo, double baseReward) {
        if (!hasWinningCombo) return new BonusResult(baseReward, null);

        Optional<Cell> bonusCell = findFirstBonusSymbol(matrix);
        if (bonusCell.isEmpty()) return new BonusResult(baseReward, null);

        String bonusSymbol = bonusCell.get().symbol();
        GameConfig.SymbolDefinition def = config.getSymbols().get(bonusSymbol);
        if (def == null || !"bonus".equals(def.type)) return new BonusResult(baseReward, null);

        double finalReward = baseReward;

        switch (def.impact) {
            case "multiply_reward" -> finalReward *= def.reward_multiplier;
            case "extra_bonus" -> finalReward += def.extra != null ? def.extra : 0;
            case "miss" -> {}
        }

        return new BonusResult(finalReward, bonusSymbol);
    }

    private Optional<Cell> findFirstBonusSymbol(Cell[][] matrix) {
        for (Cell[] row : matrix) {
            for (Cell cell : row) {
                String symbol = cell.symbol();
                GameConfig.SymbolDefinition def = config.getSymbols().get(symbol);
                if (def != null && "bonus".equals(def.type)) {
                    return Optional.of(cell);
                }
            }
        }
        return Optional.empty();
    }

    public record BonusResult(double reward, String bonusSymbol) {}
}