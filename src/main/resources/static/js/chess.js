// Chess game JavaScript
class ChessGame {
    constructor(gameId, contextPath) {
        this.gameId = gameId;
        this.contextPath = contextPath;
        this.selectedSquare = null;
        this.validMoves = [];
        this.currentPlayer = 'WHITE';
        this.gameStatus = 'ACTIVE';
        this.board = null;
        
        this.pieceSymbols = {
            'K': '♔', 'Q': '♕', 'R': '♖', 'B': '♗', 'N': '♘', 'P': '♙',
            'k': '♚', 'q': '♛', 'r': '♜', 'b': '♝', 'n': '♞', 'p': '♟'
        };
        
        this.init();
    }
    
    init() {
        this.createBoard();
        this.setupEventListeners();
        this.loadGameState();
        this.setupPeriodicUpdate();
    }
    
    createBoard() {
        const boardElement = document.getElementById('chessBoard');
        boardElement.innerHTML = '';
        
        for (let row = 0; row < 8; row++) {
            for (let col = 0; col < 8; col++) {
                const square = document.createElement('div');
                square.className = `chess-square ${(row + col) % 2 === 0 ? 'light' : 'dark'}`;
                square.dataset.row = row;
                square.dataset.col = col;
                square.dataset.position = this.getNotation(row, col);
                
                square.addEventListener('click', (e) => this.handleSquareClick(e));
                
                boardElement.appendChild(square);
            }
        }
    }
    
    getNotation(row, col) {
        return String.fromCharCode(97 + col) + (8 - row);
    }
    
    getRowCol(notation) {
        const col = notation.charCodeAt(0) - 97;
        const row = 8 - parseInt(notation.charAt(1));
        return { row, col };
    }
    
    setupEventListeners() {
        // Copy game ID
        document.getElementById('copyGameId').addEventListener('click', () => {
            navigator.clipboard.writeText(this.gameId).then(() => {
                const btn = document.getElementById('copyGameId');
                const originalText = btn.textContent;
                btn.textContent = '✓ Copied!';
                setTimeout(() => {
                    btn.textContent = originalText;
                }, 2000);
            });
        });
        
        // New game button
        document.getElementById('newGameBtn').addEventListener('click', () => {
            window.location.href = `${this.contextPath}/chess/game`;
        });
        
        // Home button
        document.getElementById('homeBtn').addEventListener('click', () => {
            window.location.href = `${this.contextPath}/chess/`;
        });
        
        // Modal buttons
        document.getElementById('newGameFromModal').addEventListener('click', () => {
            window.location.href = `${this.contextPath}/chess/game`;
        });
        
        document.getElementById('closeModal').addEventListener('click', () => {
            document.getElementById('gameOverModal').style.display = 'none';
        });
    }
    
    async loadGameState() {
        try {
            const response = await fetch(`${this.contextPath}/chess/api/game/${this.gameId}`);
            if (response.ok) {
                const gameState = await response.json();
                this.updateGameState(gameState);
            }
        } catch (error) {
            console.error('Error loading game state:', error);
        }
    }
    
    updateGameState(gameState) {
        this.currentPlayer = gameState.currentPlayer;
        this.gameStatus = gameState.status;
        this.board = gameState.board;
        
        this.updateUI();
        this.renderBoard();
        this.updateMoveHistory(gameState.moveHistory);
        
        if (this.isGameOver()) {
            this.showGameOverModal();
        }
    }
    
    updateUI() {
        document.getElementById('currentPlayer').textContent = this.currentPlayer;
        document.getElementById('gameStatus').textContent = this.gameStatus;
        
        // Update player turn highlighting
        const blackPlayer = document.querySelector('.black-player');
        const whitePlayer = document.querySelector('.white-player');
        
        blackPlayer.style.opacity = this.currentPlayer === 'BLACK' ? '1' : '0.6';
        whitePlayer.style.opacity = this.currentPlayer === 'WHITE' ? '1' : '0.6';
    }
    
    renderBoard() {
        if (!this.board) return;
        
        const squares = document.querySelectorAll('.chess-square');
        const rows = this.board.split('/');
        
        squares.forEach((square, index) => {
            const row = Math.floor(index / 8);
            const col = index % 8;
            
            square.innerHTML = '';
            square.className = `chess-square ${(row + col) % 2 === 0 ? 'light' : 'dark'}`;
            
            if (rows[row] && rows[row][col] && rows[row][col] !== '.') {
                const piece = rows[row][col];
                const pieceElement = document.createElement('span');
                pieceElement.className = 'chess-piece';
                pieceElement.textContent = this.pieceSymbols[piece] || piece;
                square.appendChild(pieceElement);
            }
        });
    }
    
    async handleSquareClick(event) {
        const square = event.currentTarget;
        const position = square.dataset.position;
        
        if (this.isGameOver()) {
            return;
        }
        
        // If clicking on a valid move square
        if (this.validMoves.includes(position)) {
            await this.makeMove(this.selectedSquare, position);
            this.clearSelection();
            return;
        }
        
        // If clicking on the same square, deselect
        if (this.selectedSquare === position) {
            this.clearSelection();
            return;
        }
        
        // If clicking on a piece of current player
        const piece = square.querySelector('.chess-piece');
        if (piece) {
            const pieceSymbol = piece.textContent;
            const isWhitePiece = ['♔', '♕', '♖', '♗', '♘', '♙'].includes(pieceSymbol);
            const isBlackPiece = ['♚', '♛', '♜', '♝', '♞', '♟'].includes(pieceSymbol);
            
            if ((this.currentPlayer === 'WHITE' && isWhitePiece) || 
                (this.currentPlayer === 'BLACK' && isBlackPiece)) {
                this.selectSquare(position);
                await this.loadValidMoves(position);
            }
        } else {
            this.clearSelection();
        }
    }
    
    selectSquare(position) {
        this.clearSelection();
        this.selectedSquare = position;
        
        const square = document.querySelector(`[data-position="${position}"]`);
        if (square) {
            square.classList.add('selected');
        }
    }
    
    clearSelection() {
        this.selectedSquare = null;
        this.validMoves = [];
        
        document.querySelectorAll('.chess-square').forEach(square => {
            square.classList.remove('selected', 'valid-move', 'valid-capture');
        });
    }
    
    async loadValidMoves(position) {
        try {
            const response = await fetch(`${this.contextPath}/chess/api/game/${this.gameId}/moves/${position}`);
            if (response.ok) {
                const moves = await response.json();
                this.validMoves = moves.map(move => this.getNotation(move.row, move.col));
                this.highlightValidMoves();
            }
        } catch (error) {
            console.error('Error loading valid moves:', error);
        }
    }
    
    highlightValidMoves() {
        this.validMoves.forEach(position => {
            const square = document.querySelector(`[data-position="${position}"]`);
            if (square) {
                const haspiece = square.querySelector('.chess-piece');
                square.classList.add(haspiece ? 'valid-capture' : 'valid-move');
            }
        });
    }
    
    async makeMove(from, to) {
        try {
            const response = await fetch(`${this.contextPath}/chess/api/game/${this.gameId}/move`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ from, to })
            });
            
            if (response.ok) {
                const result = await response.json();
                if (result.success) {
                    this.currentPlayer = result.currentPlayer;
                    this.gameStatus = result.status;
                    this.board = result.board;
                    
                    this.updateUI();
                    this.renderBoard();
                    
                    if (result.isGameOver) {
                        this.showGameOverModal();
                    }
                }
            }
        } catch (error) {
            console.error('Error making move:', error);
        }
    }
    
    updateMoveHistory(moveHistory) {
        const moveList = document.getElementById('moveList');
        moveList.innerHTML = '';
        
        if (!moveHistory || moveHistory.length === 0) {
            moveList.innerHTML = '<p>No moves yet</p>';
            return;
        }
        
        for (let i = 0; i < moveHistory.length; i += 2) {
            const moveNumber = Math.floor(i / 2) + 1;
            const whiteMove = moveHistory[i];
            const blackMove = moveHistory[i + 1];
            
            const moveItem = document.createElement('div');
            moveItem.className = 'move-item';
            
            let moveText = `<span class="move-number">${moveNumber}.</span>`;
            moveText += `<span class="move-notation">${whiteMove.notation || whiteMove.from + '-' + whiteMove.to}</span>`;
            
            if (blackMove) {
                moveText += `<span class="move-notation">${blackMove.notation || blackMove.from + '-' + blackMove.to}</span>`;
            }
            
            moveItem.innerHTML = moveText;
            moveList.appendChild(moveItem);
        }
        
        moveList.scrollTop = moveList.scrollHeight;
    }
    
    isGameOver() {
        return this.gameStatus === 'CHECKMATE' || this.gameStatus === 'STALEMATE' || this.gameStatus === 'DRAW';
    }
    
    showGameOverModal() {
        const modal = document.getElementById('gameOverModal');
        const title = document.getElementById('gameOverTitle');
        const message = document.getElementById('gameOverMessage');
        
        let titleText = 'Game Over';
        let messageText = '';
        
        switch (this.gameStatus) {
            case 'CHECKMATE':
                titleText = 'Checkmate!';
                const winner = this.currentPlayer === 'WHITE' ? 'Black' : 'White';
                messageText = `${winner} wins by checkmate!`;
                break;
            case 'STALEMATE':
                titleText = 'Stalemate';
                messageText = 'The game ends in a draw by stalemate.';
                break;
            case 'DRAW':
                titleText = 'Draw';
                messageText = 'The game ends in a draw.';
                break;
        }
        
        title.textContent = titleText;
        message.textContent = messageText;
        modal.style.display = 'block';
    }
    
    setupPeriodicUpdate() {
        // Update game state every 5 seconds to sync with other players
        setInterval(() => {
            this.loadGameState();
        }, 5000);
    }
}

// Initialize the chess game when the page loads
document.addEventListener('DOMContentLoaded', function() {
    if (typeof gameId !== 'undefined' && typeof contextPath !== 'undefined') {
        new ChessGame(gameId, contextPath);
    }
});
