/**
 * This function will do 3 things
 *
 * 1. update all the objects
 * 2. it will render the objects
 * 3. it will use requestAnimationFrame to call the step function again
 */
var setupGame = function(chosenScore) {
    maxScore = chosenScore;
    player1 = new Player1();
    player2 = new Player2();
    ball = new Ball(350, 200);
    gameRunning = true;
};
var runGame = function() {
    if(gameRunning) { //Recursion since while loop crashes the program??
        update();
        render();
        animate(runGame);
    }
};
var endGame = function() {
    gameRunning = false;
    canvas.style.display = 'none';
    startBtn.style.display = 'inline';
}