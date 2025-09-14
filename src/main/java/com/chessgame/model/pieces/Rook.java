package com.chessgame.model.pieces;

import com.chessgame.model.*;
import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece {
    
    public Rook(Color color) {
        super(color, PieceType.ROOK);
    }
    
    @Override
    public List<Position> getPossibleMoves(Position from, Board board) {
        List<Position> moves = new ArrayList<>();
        
        // Horizontal and vertical directions
        int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        
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
        // Must move in straight line (horizontal or vertical)
        if (from.getRow() != to.getRow() && from.getCol() != to.getCol()) {
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
