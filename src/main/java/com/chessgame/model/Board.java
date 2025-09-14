package com.chessgame.model;

import com.chessgame.model.pieces.*;
import java.util.ArrayList;
import java.util.List;

public class Board {
    private Piece[][] board;
    
    public Board() {
        this.board = new Piece[8][8];
        initializeBoard();
    }
    
    public Board(Piece[][] board) {
        this.board = new Piece[8][8];
        for (int i = 0; i < 8; i++) {
            System.arraycopy(board[i], 0, this.board[i], 0, 8);
        }
    }
    
    private void initializeBoard() {
        // Initialize pawns
        for (int col = 0; col < 8; col++) {
            board[1][col] = new Pawn(Color.BLACK);
            board[6][col] = new Pawn(Color.WHITE);
        }
        
        // Initialize other pieces for black
        board[0][0] = new Rook(Color.BLACK);
        board[0][1] = new Knight(Color.BLACK);
        board[0][2] = new Bishop(Color.BLACK);
        board[0][3] = new Queen(Color.BLACK);
        board[0][4] = new King(Color.BLACK);
        board[0][5] = new Bishop(Color.BLACK);
        board[0][6] = new Knight(Color.BLACK);
        board[0][7] = new Rook(Color.BLACK);
        
        // Initialize other pieces for white
        board[7][0] = new Rook(Color.WHITE);
        board[7][1] = new Knight(Color.WHITE);
        board[7][2] = new Bishop(Color.WHITE);
        board[7][3] = new Queen(Color.WHITE);
        board[7][4] = new King(Color.WHITE);
        board[7][5] = new Bishop(Color.WHITE);
        board[7][6] = new Knight(Color.WHITE);
        board[7][7] = new Rook(Color.WHITE);
    }
    
    public Piece getPiece(Position position) {
        if (!position.isValid()) {
            return null;
        }
        return board[position.getRow()][position.getCol()];
    }
    
    public void setPiece(Position position, Piece piece) {
        if (position.isValid()) {
            board[position.getRow()][position.getCol()] = piece;
        }
    }
    
    public boolean isEmpty(Position position) {
        return getPiece(position) == null;
    }
    
    public boolean isOccupiedByOpponent(Position position, Color playerColor) {
        Piece piece = getPiece(position);
        return piece != null && piece.getColor() != playerColor;
    }
    
    public boolean isOccupiedByPlayer(Position position, Color playerColor) {
        Piece piece = getPiece(position);
        return piece != null && piece.getColor() == playerColor;
    }
    
    public Position findKing(Color color) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = board[row][col];
                if (piece instanceof King && piece.getColor() == color) {
                    return new Position(row, col);
                }
            }
        }
        return null;
    }
    
    public boolean isInCheck(Color color) {
        Position kingPosition = findKing(color);
        if (kingPosition == null) {
            return false;
        }
        
        // Check if any opponent piece can attack the king
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = board[row][col];
                if (piece != null && piece.getColor() != color) {
                    if (piece.canMoveTo(new Position(row, col), kingPosition, this)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public List<Position> getAllPieces(Color color) {
        List<Position> pieces = new ArrayList<>();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = board[row][col];
                if (piece != null && piece.getColor() == color) {
                    pieces.add(new Position(row, col));
                }
            }
        }
        return pieces;
    }
    
    public Board copy() {
        return new Board(this.board);
    }
    
    public Piece[][] getBoard() {
        return board;
    }
}
