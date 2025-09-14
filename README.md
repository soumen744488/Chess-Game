# Chess Game - Spring MVC

A fully functional chess game built with Java Spring MVC framework, featuring a beautiful web interface and real-time multiplayer support.

## Features

- **Complete Chess Implementation**: All chess pieces with proper movement rules
- **Spring MVC Architecture**: Clean separation of concerns with MVC pattern
- **Beautiful Web Interface**: Modern, responsive design with CSS3
- **Real-time Multiplayer**: WebSocket support for live game updates
- **Game State Management**: Persistent game sessions with unique game IDs
- **Move Validation**: Complete chess rule validation including check/checkmate
- **Interactive UI**: Click-to-move interface with move highlighting
- **Game History**: Move history tracking and display

## Technology Stack

- **Backend**: Java 11, Spring MVC 5.3.21, Maven
- **Frontend**: HTML5, CSS3, JavaScript (ES6+)
- **Real-time**: WebSocket with STOMP protocol
- **Server**: Embedded Jetty (for development)

## Project Structure

```
chess-game/
├── src/main/java/com/chessgame/
│   ├── config/
│   │   └── WebSocketConfig.java
│   ├── controller/
│   │   ├── GameController.java
│   │   └── WebSocketController.java
│   ├── model/
│   │   ├── pieces/
│   │   │   ├── Bishop.java
│   │   │   ├── King.java
│   │   │   ├── Knight.java
│   │   │   ├── Pawn.java
│   │   │   ├── Queen.java
│   │   │   └── Rook.java
│   │   ├── Board.java
│   │   ├── Color.java
│   │   ├── Game.java
│   │   ├── Move.java
│   │   ├── Piece.java
│   │   ├── PieceType.java
│   │   └── Position.java
│   └── service/
│       └── GameService.java
├── src/main/webapp/
│   ├── WEB-INF/
│   │   ├── views/
│   │   │   ├── index.jsp
│   │   │   └── game.jsp
│   │   ├── spring-mvc-config.xml
│   │   └── web.xml
│   └── static/
│       ├── css/
│       │   ├── style.css
│       │   └── chess.css
│       └── js/
│           ├── index.js
│           └── chess.js
└── pom.xml
```

## Getting Started

### Prerequisites

- Java 11 or higher
- Maven 3.6+

### Running the Application

1. **Build the project:**
   ```bash
   cd chess-game
   mvn clean compile
   ```

2. **Run with Jetty:**
   ```bash
   mvn jetty:run
   ```

3. **Access the application:**
   - Open your browser and go to: `http://localhost:8080/chess/chess/`

### How to Play

1. **Start a New Game**: Click "Start New Game" on the home page
2. **Share Game ID**: Copy the game ID to share with another player
3. **Make Moves**: 
   - Click on a piece to select it
   - Valid moves will be highlighted in green
   - Click on a highlighted square to move
4. **Game Rules**: Standard chess rules apply including check, checkmate, and stalemate

## API Endpoints

### REST API

- `GET /chess/` - Home page
- `GET /chess/game` - Game interface
- `POST /chess/api/game/new` - Create new game
- `GET /chess/api/game/{gameId}` - Get game state
- `POST /chess/api/game/{gameId}/move` - Make a move
- `GET /chess/api/game/{gameId}/moves/{position}` - Get valid moves for position
- `DELETE /chess/api/game/{gameId}` - Delete game

### WebSocket Endpoints

- `/chess-websocket` - WebSocket connection endpoint
- `/app/game/{gameId}/move` - Send move via WebSocket
- `/app/game/{gameId}/join` - Join game via WebSocket
- `/topic/game/{gameId}` - Subscribe to game updates

## Chess Rules Implemented

- ✅ All piece movements (Pawn, Rook, Knight, Bishop, Queen, King)
- ✅ Check detection
- ✅ Checkmate detection
- ✅ Stalemate detection
- ✅ Move validation
- ✅ Turn-based gameplay
- ✅ Capture mechanics
- 🚧 Castling (TODO)
- 🚧 En passant (TODO)
- 🚧 Pawn promotion (TODO)

## Development

### Adding New Features

1. **Model Changes**: Add new classes in `com.chessgame.model`
2. **Business Logic**: Extend `GameService` for new game logic
3. **API Endpoints**: Add new endpoints in `GameController`
4. **Frontend**: Update JavaScript in `static/js/chess.js`

### Testing

Run the Maven test suite:
```bash
mvn test
```

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

This project is open source and available under the MIT License.
