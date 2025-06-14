package com.dulaj.scratchgame.engine;
import com.dulaj.scratchgame.config.GameConfig;
import com.dulaj.scratchgame.model.Cell;
import com.dulaj.scratchgame.model.ImpactType;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class BonusHandler {

    private final GameConfig config;

    public BonusHandler(GameConfig config) {
        this.config = config;
    }

    //Applies bonus logic to the reward, if a bonus symbol is present.
    public BonusResult applyBonus(Cell[][] matrix, boolean hasWinningCombo, double baseReward) {
        if (!hasWinningCombo) return new BonusResult(baseReward, null);

        Optional<Cell> bonusCell = findFirstBonusSymbol(matrix);
        if (bonusCell.isEmpty()) return new BonusResult(baseReward, null);

        String bonusSymbol = bonusCell.get().symbol();
        GameConfig.SymbolDefinition def = config.getSymbols().get(bonusSymbol);

        // Sanity check: ensure it's a bonus symbol
        if (Objects.isNull(def) || !"bonus".equals(def.type)) {
            return new BonusResult(baseReward, null);
        }

        double finalReward = baseReward;

        // Apply bonus logic based on impact type
        ImpactType impact = ImpactType.from(def.impact);
        switch (impact) {
            case MULTIPLY_REWARD -> finalReward *= def.reward_multiplier;
            case EXTRA_BONUS -> finalReward += Objects.requireNonNullElse(def.extra, 0);
            case MISS -> {} // Do nothing on miss
        }

        return new BonusResult(finalReward, bonusSymbol);
    }


     //Finds the first bonus symbol in the matrix.
    private Optional<Cell> findFirstBonusSymbol(Cell[][] matrix) {
        for (Cell[] row : matrix) {
            for (Cell cell : row) {
                String symbol = cell.symbol();
                GameConfig.SymbolDefinition def = config.getSymbols().get(symbol);
                if (Objects.nonNull(def) && "bonus".equals(def.type)) {
                    return Optional.of(cell);
                }
            }
        }
        return Optional.empty();
    }


    // Record to return the final reward and the bonus symbol used.
    public record BonusResult(double reward, String bonusSymbol) {}
}