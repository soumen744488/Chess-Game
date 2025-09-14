// Index page JavaScript
document.addEventListener('DOMContentLoaded', function() {
    const newGameBtn = document.getElementById('newGameBtn');
    const joinGameBtn = document.getElementById('joinGameBtn');
    const gameIdInput = document.getElementById('gameIdInput');
    
    // Create new game
    newGameBtn.addEventListener('click', function() {
        window.location.href = '/chess/chess/game';
    });
    
    // Join existing game
    joinGameBtn.addEventListener('click', function() {
        const gameId = gameIdInput.value.trim();
        if (gameId) {
            window.location.href = `/chess/chess/game?gameId=${encodeURIComponent(gameId)}`;
        } else {
            alert('Please enter a valid Game ID');
        }
    });
    
    // Allow Enter key to join game
    gameIdInput.addEventListener('keypress', function(e) {
        if (e.key === 'Enter') {
            joinGameBtn.click();
        }
    });
    
    // Auto-focus on game ID input
    gameIdInput.addEventListener('focus', function() {
        this.select();
    });
});
