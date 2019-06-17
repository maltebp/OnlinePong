/**
 * Sets up and runs the game
 *
 * @Author Kristian Andersen, Jacob Riis
 */

/**
 * Sets up a new game with new game model and a chosen final score, then sets the game as running
 *
 * @param chosenScore   The score to reach to win the game
 */
var setupGame = function(chosenScore) {
    maxScore = chosenScore;
    player1 = new Player1();
    player2 = new Player2();
    ball = new Ball(350, 200);
    gameRunning = true;
};

/**
 * Loops with a recursive call through the game, calling update and rendering methods to update state of the game
 *
 * Note: Recursive call chosen since a while loop crashed the program in early development for unknown reasons
 */
var runGame = function() {
    if(gameRunning) { //Recursion since while loop crashes the program??
        update();
        render();
        animate(runGame);
    }
};

/**
 * Stops the game and returns the player to previous screen
 */
var endGame = function() {
    gameRunning = false;
    canvas.style.display = 'none';
    startButton.style.display = 'inline';

};