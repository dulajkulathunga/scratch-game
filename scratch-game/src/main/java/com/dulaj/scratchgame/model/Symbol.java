package com.dulaj.scratchgame.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Symbol {
    private String name;
    private double rewardMultiplier;
    private SymbolType type;
    private ImpactType impact;
    private Integer extra;
}
