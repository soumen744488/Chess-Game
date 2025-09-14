package com.chessgame.service;

import com.chessgame.model.*;
import org.springframework.stereotype.Service;
import java.util.concurrent.ConcurrentHashMap;
import java.util.List;
import java.util.Map;

@Service
public class GameService {
    
    private final Map<String, Game> activeGames = new ConcurrentHashMap<>();
    
    public Game createNewGame() {
        Game game = new Game();
        activeGames.put(game.getGameId(), game);
        return game;
    }
    
    public Game createNewGame(String gameId) {
        Game game = new Game(gameId);
        activeGames.put(gameId, game);
        return game;
    }
    
    public Game getGame(String gameId) {
        return activeGames.get(gameId);
    }
    
    public boolean makeMove(String gameId, String fromNotation, String toNotation) {
        Game game = activeGames.get(gameId);
        if (game == null) {
            return false;
        }
        
        try {
            Position from = new Position(fromNotation);
            Position to = new Position(toNotation);
            return game.makeMove(from, to);
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean makeMove(String gameId, Position from, Position to) {
        Game game = activeGames.get(gameId);
        if (game == null) {
            return false;
        }
        
        return game.makeMove(from, to);
    }
    
    public List<Position> getValidMoves(String gameId, String positionNotation) {
        Game game = activeGames.get(gameId);
        if (game == null) {
            return null;
        }
        
        try {
            Position position = new Position(positionNotation);
            return game.getValidMoves(position);
        } catch (Exception e) {
            return null;
        }
    }
    
    public List<Position> getValidMoves(String gameId, Position position) {
        Game game = activeGames.get(gameId);
        if (game == null) {
            return null;
        }
        
        return game.getValidMoves(position);
    }
    
    public String getBoardState(String gameId) {
        Game game = activeGames.get(gameId);
        if (game == null) {
            return null;
        }
        
        return boardToString(game.getBoard());
    }
    
    public Game.GameStatus getGameStatus(String gameId) {
        Game game = activeGames.get(gameId);
        if (game == null) {
            return null;
        }
        
        return game.getStatus();
    }
    
    public Color getCurrentPlayer(String gameId) {
        Game game = activeGames.get(gameId);
        if (game == null) {
            return null;
        }
        
        return game.getCurrentPlayer();
    }
    
    public List<Move> getMoveHistory(String gameId) {
        Game game = activeGames.get(gameId);
        if (game == null) {
            return null;
        }
        
        return game.getMoveHistory();
    }
    
    public void removeGame(String gameId) {
        activeGames.remove(gameId);
    }
    
    public int getActiveGameCount() {
        return activeGames.size();
    }
    
    private String boardToString(Board board) {
        StringBuilder sb = new StringBuilder();
        Piece[][] pieces = board.getBoard();
        
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = pieces[row][col];
                if (piece == null) {
                    sb.append(".");
                } else {
                    sb.append(piece.getSymbol());
                }
            }
            if (row < 7) {
                sb.append("/");
            }
        }
        
        return sb.toString();
    }
}
