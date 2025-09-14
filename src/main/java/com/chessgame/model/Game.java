package com.chessgame.model;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private Board board;
    private Color currentPlayer;
    private List<Move> moveHistory;
    private GameStatus status;
    private String gameId;
    
    public enum GameStatus {
        ACTIVE, CHECK, CHECKMATE, STALEMATE, DRAW
    }
    
    public Game() {
        this.board = new Board();
        this.currentPlayer = Color.WHITE;
        this.moveHistory = new ArrayList<>();
        this.status = GameStatus.ACTIVE;
        this.gameId = java.util.UUID.randomUUID().toString();
    }
    
    public Game(String gameId) {
        this();
        this.gameId = gameId;
    }
    
    public boolean makeMove(Position from, Position to) {
        Piece piece = board.getPiece(from);
        
        // Validate move
        if (piece == null || piece.getColor() != currentPlayer) {
            return false;
        }
        
        if (!piece.canMoveTo(from, to, board)) {
            return false;
        }
        
        // Check if move would leave king in check
        if (wouldLeaveKingInCheck(from, to)) {
            return false;
        }
        
        // Execute move
        Piece capturedPiece = board.getPiece(to);
        board.setPiece(to, piece);
        board.setPiece(from, null);
        piece.setMoved(true);
        
        // Check for check/checkmate
        Color opponent = currentPlayer.opposite();
        boolean isCheck = board.isInCheck(opponent);
        boolean isCheckmate = isCheck && isCheckmate(opponent);
        
        // Create move record
        Move move = new Move(from, to, piece, capturedPiece, isCheck, isCheckmate);
        moveHistory.add(move);
        
        // Update game status
        if (isCheckmate) {
            status = GameStatus.CHECKMATE;
        } else if (isCheck) {
            status = GameStatus.CHECK;
        } else if (isStalemate(opponent)) {
            status = GameStatus.STALEMATE;
        } else {
            status = GameStatus.ACTIVE;
        }
        
        // Switch players
        currentPlayer = opponent;
        
        return true;
    }
    
    private boolean wouldLeaveKingInCheck(Position from, Position to) {
        // Make temporary move
        Piece piece = board.getPiece(from);
        Piece capturedPiece = board.getPiece(to);
        
        board.setPiece(to, piece);
        board.setPiece(from, null);
        
        boolean inCheck = board.isInCheck(currentPlayer);
        
        // Undo temporary move
        board.setPiece(from, piece);
        board.setPiece(to, capturedPiece);
        
        return inCheck;
    }
    
    private boolean isCheckmate(Color color) {
        if (!board.isInCheck(color)) {
            return false;
        }
        
        return !hasValidMoves(color);
    }
    
    private boolean isStalemate(Color color) {
        if (board.isInCheck(color)) {
            return false;
        }
        
        return !hasValidMoves(color);
    }
    
    private boolean hasValidMoves(Color color) {
        List<Position> pieces = board.getAllPieces(color);
        
        for (Position piecePos : pieces) {
            Piece piece = board.getPiece(piecePos);
            List<Position> possibleMoves = piece.getPossibleMoves(piecePos, board);
            
            for (Position move : possibleMoves) {
                if (!wouldLeaveKingInCheck(piecePos, move)) {
                    return true;
                }
            }
        }
        
        return false;
    }
    
    public List<Position> getValidMoves(Position from) {
        Piece piece = board.getPiece(from);
        if (piece == null || piece.getColor() != currentPlayer) {
            return new ArrayList<>();
        }
        
        List<Position> possibleMoves = piece.getPossibleMoves(from, board);
        List<Position> validMoves = new ArrayList<>();
        
        for (Position move : possibleMoves) {
            if (!wouldLeaveKingInCheck(from, move)) {
                validMoves.add(move);
            }
        }
        
        return validMoves;
    }
    
    // Getters
    public Board getBoard() {
        return board;
    }
    
    public Color getCurrentPlayer() {
        return currentPlayer;
    }
    
    public List<Move> getMoveHistory() {
        return moveHistory;
    }
    
    public GameStatus getStatus() {
        return status;
    }
    
    public String getGameId() {
        return gameId;
    }
    
    public boolean isGameOver() {
        return status == GameStatus.CHECKMATE || 
               status == GameStatus.STALEMATE || 
               status == GameStatus.DRAW;
    }
}
