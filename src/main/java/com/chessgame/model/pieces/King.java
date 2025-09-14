package com.chessgame.model.pieces;

import com.chessgame.model.*;
import java.util.ArrayList;
import java.util.List;

public class King extends Piece {
    
    public King(Color color) {
        super(color, PieceType.KING);
    }
    
    @Override
    public List<Position> getPossibleMoves(Position from, Board board) {
        List<Position> moves = new ArrayList<>();
        
        // King moves one square in any direction
        int[][] kingMoves = {
            {0, 1}, {0, -1}, {1, 0}, {-1, 0},
            {1, 1}, {1, -1}, {-1, 1}, {-1, -1}
        };
        
        for (int[] move : kingMoves) {
            Position newPos = from.add(move[0], move[1]);
            
            if (newPos.isValid() && 
                (board.isEmpty(newPos) || board.isOccupiedByOpponent(newPos, color))) {
                moves.add(newPos);
            }
        }
        
        // TODO: Add castling logic
        
        return moves;
    }
    
    @Override
    public boolean canMoveTo(Position from, Position to, Board board) {
        int rowDiff = Math.abs(to.getRow() - from.getRow());
        int colDiff = Math.abs(to.getCol() - from.getCol());
        
        // King moves one square in any direction
        if (rowDiff > 1 || colDiff > 1) {
            return false;
        }
        
        // Destination must be empty or contain opponent piece
        return board.isEmpty(to) || board.isOccupiedByOpponent(to, color);
    }
}
