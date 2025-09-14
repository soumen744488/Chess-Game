package com.chessgame.controller;

import com.chessgame.model.Game;
import com.chessgame.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.Map;

@Controller
public class WebSocketController {
    
    @Autowired
    private GameService gameService;
    
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    
    @MessageMapping("/game/{gameId}/move")
    @SendTo("/topic/game/{gameId}")
    public Map<String, Object> handleMove(@DestinationVariable String gameId, GameController.MoveRequest moveRequest) {
        boolean success = gameService.makeMove(gameId, moveRequest.getFrom(), moveRequest.getTo());
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", success);
        response.put("type", "move");
        
        if (success) {
            Game game = gameService.getGame(gameId);
            response.put("currentPlayer", game.getCurrentPlayer().name());
            response.put("status", game.getStatus().name());
            response.put("board", gameService.getBoardState(gameId));
            response.put("isGameOver", game.isGameOver());
        }
        
        return response;
    }
    
    @MessageMapping("/game/{gameId}/join")
    @SendTo("/topic/game/{gameId}")
    public Map<String, Object> handleJoin(@DestinationVariable String gameId) {
        Game game = gameService.getGame(gameId);
        
        Map<String, Object> response = new HashMap<>();
        response.put("type", "player-joined");
        response.put("gameId", gameId);
        
        if (game != null) {
            response.put("currentPlayer", game.getCurrentPlayer().name());
            response.put("status", game.getStatus().name());
            response.put("board", gameService.getBoardState(gameId));
        }
        
        return response;
    }
}
