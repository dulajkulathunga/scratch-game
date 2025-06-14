package com.dulaj.scratchgame.model;

import lombok.AllArgsConstructor;
import lombok.Data;


public record Cell(int row, int column, String symbol) {
    @Override
    public String toString() {
        return symbol;
    }
}