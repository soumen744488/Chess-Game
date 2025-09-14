package com.chessgame.model.pieces;

import com.chessgame.model.*;
import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece {
    
    public Knight(Color color) {
        super(color, PieceType.KNIGHT);
    }
    
    @Override
    public List<Position> getPossibleMoves(Position from, Board board) {
        List<Position> moves = new ArrayList<>();
        
        // Knight moves in L-shape: 2 squares in one direction, 1 in perpendicular
        int[][] knightMoves = {
            {2, 1}, {2, -1}, {-2, 1}, {-2, -1},
            {1, 2}, {1, -2}, {-1, 2}, {-1, -2}
        };
        
        for (int[] move : knightMoves) {
            Position newPos = from.add(move[0], move[1]);
            
            if (newPos.isValid() && 
                (board.isEmpty(newPos) || board.isOccupiedByOpponent(newPos, color))) {
                moves.add(newPos);
            }
        }
        
        return moves;
    }
    
    @Override
    public boolean canMoveTo(Position from, Position to, Board board) {
        int rowDiff = Math.abs(to.getRow() - from.getRow());
        int colDiff = Math.abs(to.getCol() - from.getCol());
        
        // Knight moves in L-shape
        boolean isLMove = (rowDiff == 2 && colDiff == 1) || (rowDiff == 1 && colDiff == 2);
        
        if (!isLMove) {
            return false;
        }
        
        // Destination must be empty or contain opponent piece
        return board.isEmpty(to) || board.isOccupiedByOpponent(to, color);
    }
}
