<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Chess Game - Spring MVC</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style.css">
</head>
<body>
    <div class="container">
        <header>
            <h1>♔ Chess Game ♛</h1>
            <p>A beautiful chess game built with Spring MVC</p>
        </header>
        
        <main>
            <div class="menu-card">
                <h2>Welcome to Chess</h2>
                <div class="menu-options">
                    <button id="newGameBtn" class="btn btn-primary">
                        <span class="icon">🎮</span>
                        Start New Game
                    </button>
                    
                    <div class="join-game-section">
                        <h3>Join Existing Game</h3>
                        <div class="input-group">
                            <input type="text" id="gameIdInput" placeholder="Enter Game ID" maxlength="36">
                            <button id="joinGameBtn" class="btn btn-secondary">
                                <span class="icon">🔗</span>
                                Join Game
                            </button>
                        </div>
                    </div>
                    
                    <div class="game-info">
                        <h3>How to Play</h3>
                        <ul>
                            <li>Click on a piece to select it</li>
                            <li>Valid moves will be highlighted</li>
                            <li>Click on a highlighted square to move</li>
                            <li>White moves first</li>
                            <li>Game ends with checkmate or stalemate</li>
                        </ul>
                    </div>
                </div>
            </div>
        </main>
        
        <footer>
            <p>&copy; 2024 Chess Game - Built with Spring MVC Framework</p>
        </footer>
    </div>
    
    <script src="${pageContext.request.contextPath}/static/js/index.js"></script>
</body>
</html>
