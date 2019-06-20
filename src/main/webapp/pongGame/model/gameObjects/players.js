/**
 * Player model and the update and rendering functions for them
 *
 * @Author  Kristian Andersen and Jacob Riis
 */

/**
 * Constructor for Player model with their paddle and scores and their respectively starting positions
 */
function Player1() {
    this.paddle = new Paddle(10, 175, 10, 50);
    this.score = new Score(175, 460, 0);
}
function Player2() {
    this.paddle = new Paddle(680, 175, 10, 50);
    this.score = new Score(525, 460, 0);
}
/*function Player(Playerpaddle,Playerscore,id){
     this.paddle= Playerpaddle;
     this.score = Playerscore;
     this.playerId = id;
}*/

/**
 * Listens for the event of a key is pressed down on the keyboard. If the key correspond to 'W' or 'S' it sets it to
 * being pressed
 */
window.addEventListener("keydown", function(event) {
    switch(event.keyCode) {
        case 83: //'S'
            keysDown[83] = true;
            break;

        case 87: //'W'
            keysDown[87] = true;
            break;
    }
});

/**
 * Listens for the event of a key being released on the keyboard. If the key corresponds to 'W' or 'S' it sets it to no
 * longer being pressed
 */
window.addEventListener("keyup", function(event) {
    switch(event.keyCode) {
        case 83: //'S'
            keysDown[83] = false;
            break;

        case 87: //'W'
            keysDown[87] = false;
            break;
    }
});

/**
 * General update function to be called from other classes. Updates the players and the ball
 */
var update = function(){
    player1.update();
    player2.update();
    ball.update(player1.paddle, player2.paddle);
};

/**
 * Sets the y_speed of player one's paddle to the corresponding pressed keys and calls the move function to move the
 * player
 */
Player1.prototype.update = function() {
    if(keysDown["83"] === false && keysDown["87"] === true) { //Only 'W' is being pressed
        this.paddle.y_speed = -8;
    } else if(keysDown["83"] === true && keysDown["87"] === false) { //Only 'S' is being pressed
        this.paddle.y_speed = 8;
    } else {
        this.paddle.y_speed = 0;
    }
    this.paddle.move(this.paddle.y_speed);
};

/**
 * Calls move on player two's paddle
 */
Player2.prototype.update = function() {
    this.paddle.move(this.paddle.y_speed);
};

