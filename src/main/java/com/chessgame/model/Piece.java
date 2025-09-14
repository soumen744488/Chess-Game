package com.chessgame.model;

import java.util.List;

public abstract class Piece {
    protected final Color color;
    protected final PieceType type;
    protected boolean hasMoved;
    
    public Piece(Color color, PieceType type) {
        this.color = color;
        this.type = type;
        this.hasMoved = false;
    }
    
    public Color getColor() {
        return color;
    }
    
    public PieceType getType() {
        return type;
    }
    
    public boolean hasMoved() {
        return hasMoved;
    }
    
    public void setMoved(boolean moved) {
        this.hasMoved = moved;
    }
    
    public abstract List<Position> getPossibleMoves(Position from, Board board);
    
    public abstract boolean canMoveTo(Position from, Position to, Board board);
    
    public String getSymbol() {
        String symbol = type.getSymbol();
        return color == Color.WHITE ? symbol : symbol.toLowerCase();
    }
    
    @Override
    public String toString() {
        return color + " " + type;
    }
}
