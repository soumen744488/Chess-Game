package com.chessgame.model;

public class Move {
    private final Position from;
    private final Position to;
    private final Piece piece;
    private final Piece capturedPiece;
    private final boolean isCapture;
    private final boolean isCheck;
    private final boolean isCheckmate;
    
    public Move(Position from, Position to, Piece piece, Piece capturedPiece) {
        this.from = from;
        this.to = to;
        this.piece = piece;
        this.capturedPiece = capturedPiece;
        this.isCapture = capturedPiece != null;
        this.isCheck = false;
        this.isCheckmate = false;
    }
    
    public Move(Position from, Position to, Piece piece, Piece capturedPiece, 
                boolean isCheck, boolean isCheckmate) {
        this.from = from;
        this.to = to;
        this.piece = piece;
        this.capturedPiece = capturedPiece;
        this.isCapture = capturedPiece != null;
        this.isCheck = isCheck;
        this.isCheckmate = isCheckmate;
    }
    
    public Position getFrom() {
        return from;
    }
    
    public Position getTo() {
        return to;
    }
    
    public Piece getPiece() {
        return piece;
    }
    
    public Piece getCapturedPiece() {
        return capturedPiece;
    }
    
    public boolean isCapture() {
        return isCapture;
    }
    
    public boolean isCheck() {
        return isCheck;
    }
    
    public boolean isCheckmate() {
        return isCheckmate;
    }
    
    public String toNotation() {
        StringBuilder notation = new StringBuilder();
        
        if (piece.getType() != PieceType.PAWN) {
            notation.append(piece.getType().getSymbol());
        }
        
        if (isCapture) {
            if (piece.getType() == PieceType.PAWN) {
                notation.append((char)('a' + from.getCol()));
            }
            notation.append("x");
        }
        
        notation.append(to.toNotation());
        
        if (isCheckmate) {
            notation.append("#");
        } else if (isCheck) {
            notation.append("+");
        }
        
        return notation.toString();
    }
    
    @Override
    public String toString() {
        return from.toNotation() + " -> " + to.toNotation();
    }
}
