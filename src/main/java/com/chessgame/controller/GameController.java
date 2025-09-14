package com.chessgame.controller;

import com.chessgame.model.*;
import com.chessgame.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/chess")
public class GameController {
    
    @Autowired
    private GameService gameService;
    
    @GetMapping("/")
    public String index() {
        return "index";
    }
    
    @GetMapping("/game")
    public String game(@RequestParam(required = false) String gameId, Model model) {
        Game game;
        if (gameId != null && !gameId.isEmpty()) {
            game = gameService.getGame(gameId);
            if (game == null) {
                game = gameService.createNewGame(gameId);
            }
        } else {
            game = gameService.createNewGame();
        }
        
        model.addAttribute("gameId", game.getGameId());
        model.addAttribute("currentPlayer", game.getCurrentPlayer().name());
        model.addAttribute("gameStatus", game.getStatus().name());
        
        return "game";
    }
    
    @PostMapping("/api/game/new")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> createNewGame() {
        Game game = gameService.createNewGame();
        
        Map<String, Object> response = new HashMap<>();
        response.put("gameId", game.getGameId());
        response.put("currentPlayer", game.getCurrentPlayer().name());
        response.put("status", game.getStatus().name());
        response.put("board", gameService.getBoardState(game.getGameId()));
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/api/game/{gameId}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getGameState(@PathVariable String gameId) {
        Game game = gameService.getGame(gameId);
        if (game == null) {
            return ResponseEntity.notFound().build();
        }
        
        Map<String, Object> response = new HashMap<>();
        response.put("gameId", gameId);
        response.put("currentPlayer", game.getCurrentPlayer().name());
        response.put("status", game.getStatus().name());
        response.put("board", gameService.getBoardState(gameId));
        response.put("moveHistory", game.getMoveHistory());
        
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/api/game/{gameId}/move")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> makeMove(
            @PathVariable String gameId,
            @RequestBody MoveRequest moveRequest) {
        
        boolean success = gameService.makeMove(gameId, moveRequest.getFrom(), moveRequest.getTo());
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", success);
        
        if (success) {
            Game game = gameService.getGame(gameId);
            response.put("currentPlayer", game.getCurrentPlayer().name());
            response.put("status", game.getStatus().name());
            response.put("board", gameService.getBoardState(gameId));
            response.put("isGameOver", game.isGameOver());
        }
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/api/game/{gameId}/moves/{position}")
    @ResponseBody
    public ResponseEntity<List<Position>> getValidMoves(
            @PathVariable String gameId,
            @PathVariable String position) {
        
        List<Position> validMoves = gameService.getValidMoves(gameId, position);
        if (validMoves == null) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(validMoves);
    }
    
    @DeleteMapping("/api/game/{gameId}")
    @ResponseBody
    public ResponseEntity<Void> deleteGame(@PathVariable String gameId) {
        gameService.removeGame(gameId);
        return ResponseEntity.ok().build();
    }
    
    // Inner class for move request
    public static class MoveRequest {
        private String from;
        private String to;
        
        public String getFrom() {
            return from;
        }
        
        public void setFrom(String from) {
            this.from = from;
        }
        
        public String getTo() {
            return to;
        }
        
        public void setTo(String to) {
            this.to = to;
        }
    }
}
