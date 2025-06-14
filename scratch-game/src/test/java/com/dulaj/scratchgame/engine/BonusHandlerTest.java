package com.dulaj.scratchgame.engine;

import junit.framework.TestCase;
import com.dulaj.scratchgame.config.GameConfig;
import com.dulaj.scratchgame.model.Cell;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BonusHandlerTest extends TestCase {

    @Test
    public void testApplyBonusMultiplyReward() {
        GameConfig config = ConfigLoader.load("config.json");
        BonusHandler bonusHandler = new BonusHandler(config);

        Cell[][] matrix = {
                { new Cell(0, 0, "10x"), new Cell(0, 1, "A"), new Cell(0, 2, "B") },
                { new Cell(1, 0, "C"), new Cell(1, 1, "D"), new Cell(1, 2, "E") },
                { new Cell(2, 0, "F"), new Cell(2, 1, "G"), new Cell(2, 2, "H") }
        };

        BonusHandler.BonusResult result = bonusHandler.applyBonus(matrix, true, 100);
        assertEquals("10x", result.bonusSymbol());
        assertEquals(1000.0, result.reward());
    }
}