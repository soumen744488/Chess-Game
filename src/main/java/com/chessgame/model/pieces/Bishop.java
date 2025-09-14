package com.chessgame.model.pieces;

import com.chessgame.model.*;
import java.util.ArrayList;
import java.util.List;

public class Bishop extends Piece {
    
    public Bishop(Color color) {
        super(color, PieceType.BISHOP);
    }
    
    @Override
    public List<Position> getPossibleMoves(Position from, Board board) {
        List<Position> moves = new ArrayList<>();
        
        // Diagonal directions
        int[][] directions = {{1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
        
        for (int[] direction : directions) {
            for (int i = 1; i < 8; i++) {
                Position newPos = from.add(direction[0] * i, direction[1] * i);
                
                if (!newPos.isValid()) {
                    break;
                }
                
                if (board.isEmpty(newPos)) {
                    moves.add(newPos);
                } else if (board.isOccupiedByOpponent(newPos, color)) {
                    moves.add(newPos);
                    break;
                } else {
                    break; // Blocked by own piece
                }
            }
        }
        
        return moves;
    }
    
    @Override
    public boolean canMoveTo(Position from, Position to, Board board) {
        // Must move diagonally
        int rowDiff = Math.abs(to.getRow() - from.getRow());
        int colDiff = Math.abs(to.getCol() - from.getCol());
        
        if (rowDiff != colDiff) {
            return false;
        }
        
        // Check path is clear
        int rowDirection = Integer.compare(to.getRow(), from.getRow());
        int colDirection = Integer.compare(to.getCol(), from.getCol());
        
        Position current = from.add(rowDirection, colDirection);
        while (!current.equals(to)) {
            if (!board.isEmpty(current)) {
                return false;
            }
            current = current.add(rowDirection, colDirection);
        }
        
        // Destination must be empty or contain opponent piece
        return board.isEmpty(to) || board.isOccupiedByOpponent(to, color);
    }
}
