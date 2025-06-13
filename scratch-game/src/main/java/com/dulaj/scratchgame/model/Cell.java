package com.dulaj.scratchgame.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Cell {
    private final int row;
    private final int column;
    private final String symbol;
}
