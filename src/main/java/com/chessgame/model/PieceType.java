package com.chessgame.model;

public enum PieceType {
    KING("K", 1000),
    QUEEN("Q", 9),
    ROOK("R", 5),
    BISHOP("B", 3),
    KNIGHT("N", 3),
    PAWN("P", 1);
    
    private final String symbol;
    private final int value;
    
    PieceType(String symbol, int value) {
        this.symbol = symbol;
        this.value = value;
    }
    
    public String getSymbol() {
        return symbol;
    }
    
    public int getValue() {
        return value;
    }
}
