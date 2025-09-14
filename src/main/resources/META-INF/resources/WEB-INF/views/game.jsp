<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Chess Game - ${gameId}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/chess.css">
</head>
<body>
    <div class="game-container">
        <header class="game-header">
            <div class="game-info">
                <h1>♔ Chess Game ♛</h1>
                <div class="game-details">
                    <span class="game-id">Game ID: ${gameId}</span>
                    <button id="copyGameId" class="btn btn-small">📋 Copy</button>
                </div>
            </div>
            <div class="game-controls">
                <button id="newGameBtn" class="btn btn-secondary">New Game</button>
                <button id="homeBtn" class="btn btn-secondary">Home</button>
            </div>
        </header>
        
        <main class="game-main">
            <div class="game-sidebar">
                <div class="player-info">
                    <div class="player black-player">
                        <span class="player-color">⚫</span>
                        <span class="player-name">Black Player</span>
                        <div class="captured-pieces" id="blackCaptured"></div>
                    </div>
                    
                    <div class="game-status">
                        <div class="current-turn">
                            <span id="currentPlayer">${currentPlayer}</span>'s Turn
                        </div>
                        <div class="game-state">
                            Status: <span id="gameStatus">${gameStatus}</span>
                        </div>
                    </div>
                    
                    <div class="player white-player">
                        <span class="player-color">⚪</span>
                        <span class="player-name">White Player</span>
                        <div class="captured-pieces" id="whiteCaptured"></div>
                    </div>
                </div>
                
                <div class="move-history">
                    <h3>Move History</h3>
                    <div id="moveList" class="move-list"></div>
                </div>
            </div>
            
            <div class="chess-board-container">
                <div id="chessBoard" class="chess-board"></div>
                <div class="board-coordinates">
                    <div class="files">
                        <span>a</span><span>b</span><span>c</span><span>d</span>
                        <span>e</span><span>f</span><span>g</span><span>h</span>
                    </div>
                    <div class="ranks">
                        <span>8</span><span>7</span><span>6</span><span>5</span>
                        <span>4</span><span>3</span><span>2</span><span>1</span>
                    </div>
                </div>
            </div>
        </main>
        
        <div id="gameOverModal" class="modal">
            <div class="modal-content">
                <h2 id="gameOverTitle">Game Over</h2>
                <p id="gameOverMessage"></p>
                <div class="modal-actions">
                    <button id="newGameFromModal" class="btn btn-primary">New Game</button>
                    <button id="closeModal" class="btn btn-secondary">Close</button>
                </div>
            </div>
        </div>
    </div>
    
    <script>
        const gameId = '${gameId}';
        const contextPath = '${pageContext.request.contextPath}';
    </script>
    <script src="${pageContext.request.contextPath}/static/js/chess.js"></script>
</body>
</html>
