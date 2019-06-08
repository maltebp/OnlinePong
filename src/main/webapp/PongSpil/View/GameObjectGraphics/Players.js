
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

//render functions.



Player1.prototype.render = function(){
    this.paddle.render();
    this.score.render();
};
Player2.prototype.render = function() {
    this.paddle.render();
    this.score.render();
};

/**
 * how we use keys to play the game.
 * If a key is pressed down there will be an reaction. when the key is released again it will delete that event
 */
window.addEventListener("keydown", function(event) {
    keysDown[event.keyCode] = true;
});

window.addEventListener("keyup", function(event) {
    delete keysDown[event.keyCode];
});

//update functions
var update = function(){
    player1.update();
    player2.update();
    ball.update(player1.paddle, player2.paddle);
};
Player1.prototype.update = function() {
    for(var key in keysDown) {
        var value = Number(key);
        if(value === 87){ // Keyboard key 'W'
            this.paddle.move(-4);
        }
        else if (value === 83) { // Keyboard key 'S'
            this.paddle.move(4);
        }
        else {
            this.paddle.move(0);
        }
    }
};

Player2.prototype.update = function() {
    this.paddle.move(this.paddle.y_speed);
    // for(var key in keysDown) {
    //     var value = Number(key);
    //     if(value === 79) { // Keyboard key 'O'
    //         this.paddle.move(-4);
    //     }
    //     else if(value === 76) { // Keyboard key 'L'
    //         this.paddle.move(4);
    //     }
    //     else {
    //         this.paddle.move(0);
    //     }
    // }
};