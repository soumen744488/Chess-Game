package com.chessgame.model.pieces;

import com.chessgame.model.*;
import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {
    
    public Pawn(Color color) {
        super(color, PieceType.PAWN);
    }
    
    @Override
    public List<Position> getPossibleMoves(Position from, Board board) {
        List<Position> moves = new ArrayList<>();
        int direction = (color == Color.WHITE) ? -1 : 1;
        int startRow = (color == Color.WHITE) ? 6 : 1;
        
        // Move forward one square
        Position oneForward = from.add(direction, 0);
        if (oneForward.isValid() && board.isEmpty(oneForward)) {
            moves.add(oneForward);
            
            // Move forward two squares from starting position
            if (from.getRow() == startRow) {
                Position twoForward = from.add(2 * direction, 0);
                if (twoForward.isValid() && board.isEmpty(twoForward)) {
                    moves.add(twoForward);
                }
            }
        }
        
        // Capture diagonally
        Position leftCapture = from.add(direction, -1);
        if (leftCapture.isValid() && board.isOccupiedByOpponent(leftCapture, color)) {
            moves.add(leftCapture);
        }
        
        Position rightCapture = from.add(direction, 1);
        if (rightCapture.isValid() && board.isOccupiedByOpponent(rightCapture, color)) {
            moves.add(rightCapture);
        }
        
        return moves;
    }
    
    @Override
    public boolean canMoveTo(Position from, Position to, Board board) {
        int direction = (color == Color.WHITE) ? -1 : 1;
        int rowDiff = to.getRow() - from.getRow();
        int colDiff = Math.abs(to.getCol() - from.getCol());
        
        // Forward move
        if (colDiff == 0 && rowDiff == direction) {
            return board.isEmpty(to);
        }
        
        // Two squares forward from starting position
        if (colDiff == 0 && rowDiff == 2 * direction && !hasMoved) {
            return board.isEmpty(to) && board.isEmpty(from.add(direction, 0));
        }
        
        // Diagonal capture
        if (colDiff == 1 && rowDiff == direction) {
            return board.isOccupiedByOpponent(to, color);
        }
        
        return false;
    }
}
