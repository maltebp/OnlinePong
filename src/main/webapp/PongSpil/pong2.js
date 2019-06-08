








//constructors



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
var render = function(){
    context.fillStyle = "#000000";
    context.fillRect(0, 0, width, height);
    midCourtGraphics.render();
    player1.render();
    player2.render();
    ball.render();
    bottomLine.render();
};

Score.prototype.render = function() {
    context.beginPath();
    context.font = "30px Arial";
    context.fillStyle = "#FFFFFF";
    context.fillText(""+ this.score, this.x, this.y);
    context.closePath();
};
Player1.prototype.render = function(){
    this.paddle.render();
    this.score.render();
};
Player2.prototype.render = function() {
    this.paddle.render();
    this.score.render();
};

BottomLine.prototype.render = function() {
    context.beginPath();
    context.fillStyle = "#FFFFFF";
    context.fillRect(0, 405, 700, 5);
};
MidCourtLine.prototype.render = function() {
    context.beginPath();
    context.fillStyle = "#FFFFFF";
    context.fillRect(this.x, this.y, this.width, this.height);

};
MidcourtGraphics.prototype.render = function() {
    this.midCourtLine1.render();
    this.midCourtLine2.render();
    this.midCourtLine3.render();
    this.midCourtLine4.render();
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


Score.prototype.goal = function() {
    this.score++;
    if(this.score === maxScore) {
        endGame();
    }
};

//move functions
Paddle.prototype.move = function(y) {
    this.y += y;
    this.y_speed = y;
    if(this.y < 0) { //All the way to bottom
        this.y = 0;
        this.y_speed = 0;
    } else if(this.y + this.height > 400) { //All the way to the top
        this.y = 400 - this.height;
        this.y_speed = 0;
    }
};






