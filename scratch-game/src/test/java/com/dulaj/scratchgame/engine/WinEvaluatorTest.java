package com.dulaj.scratchgame.engine;

import junit.framework.TestCase;

import com.dulaj.scratchgame.config.GameConfig;
import com.dulaj.scratchgame.model.Cell;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


public class WinEvaluatorTest extends TestCase {
    @Test
    public void testSameSymbol3Times() {
        GameConfig config = ConfigLoader.load("config.json");
        WinEvaluator evaluator = new WinEvaluator(config);

        Cell[][] matrix = {
                { new Cell(0, 0, "A"), new Cell(0, 1, "A"), new Cell(0, 2, "A") },
                { new Cell(1, 0, "B"), new Cell(1, 1, "C"), new Cell(1, 2, "D") },
                { new Cell(2, 0, "E"), new Cell(2, 1, "F"), new Cell(2, 2, "G") }
        };

        Map<String, java.util.List<String>> result = evaluator.evaluate(matrix);
        assertTrue(result.containsKey("A"));
        assertTrue(result.get("A").contains("same_symbol_3_times"));
    }
}