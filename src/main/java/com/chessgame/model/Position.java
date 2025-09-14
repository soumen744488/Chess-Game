package com.chessgame.model;

import java.util.Objects;

public class Position {
    private final int row;
    private final int col;
    
    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }
    
    public Position(String notation) {
        // Convert chess notation (e.g., "e4") to array indices
        this.col = notation.charAt(0) - 'a';
        this.row = 8 - (notation.charAt(1) - '0');
    }
    
    public int getRow() {
        return row;
    }
    
    public int getCol() {
        return col;
    }
    
    public boolean isValid() {
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }
    
    public String toNotation() {
        return String.valueOf((char)('a' + col)) + (8 - row);
    }
    
    public Position add(int deltaRow, int deltaCol) {
        return new Position(row + deltaRow, col + deltaCol);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Position position = (Position) obj;
        return row == position.row && col == position.col;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }
    
    @Override
    public String toString() {
        return toNotation();
    }
}
