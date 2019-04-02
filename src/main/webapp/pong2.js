


function initialize() {
    startBtn.style.display = 'none';
    animate(step);
};

var animate = window.requestAnimationFrame || window.webkitRequestAnimationFrame || window.mozRequestAnimationFrame || 
function(callback) {
    window.setTimeout(callback, 1000/60);
};

/**
 * This function will do 3 things
 * 
 * 1. update all the objects
 * 2. it will render the objects
 * 3. it will use requestAnimationFrame to call the step function again
 */
var step = function() {
    update();
    render();
    animate(step);
};

// the game objects
var player1 = new Player1(); //paddle is in the player constructor
var player2 = new Player2(); //paddle is in the player constructor
var ball = new Ball(300, 200); //the ball which will start 
var midCourtGraphics = new MidcourtGraphics();
var bottomLine = new BottomLine();

//variables
var canvas = document.getElementById("game");
var context = canvas.getContext('2d');
var width = 700;
var height = 500;
var score = 0;
var keysDown = {};
var startBtn = document.getElementById("startBtn")

//sets the width of the witdth of the canvas to width and height the same
canvas.width = width;
canvas.height = height;

//constructors
function Paddle(x,y,width,height) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.x_speed = 0;
    this.y_speed = 0;
}
function Ball(x,y) {
    this.x = x;
    this.y = y;
    this.x_speed = 3;
    this.y_speed = 0;
    this.radius = 5;
}
function Player1() {
    this.paddle = new Paddle(680, 175, 10, 50);
}
function Player2() {
    this.paddle = new Paddle(10, 175, 10, 50);
}
function BottomLine(x,y,width,height) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;

}
function MidCourtLine(x,y,width,height){
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
}
function MidcourtGraphics() {
    this.midCourtLine1 = new MidCourtLine(347.5, 0 , 5, 50);
    this.midCourtLine2 = new MidCourtLine(347.5, 118.3333, 5, 50);
    this.midCourtLine3 = new MidCourtLine(347.5, 236.6666, 5, 50);
    this.midCourtLine4 = new MidCourtLine(347.5, 355, 5, 50);
}

//render functions. 
var render = function(){
    console.log(20);
    context.fillStyle = "#000000";
    context.fillRect(0, 0, width, height);
    player1.render();
    player2.render();
    ball.render();
    midCourtGraphics.render();
    bottomLine.render();
};
Paddle.prototype.render = function() {
    context.beginPath();
    context.fillStyle = "#FFFFFF";
    context.fillRect(this.x, this.y, this.width, this.height);
};
Player1.prototype.render = function(){
    this.paddle.render();
};
Player2.prototype.render = function() {
    this.paddle.render();
};
Ball.prototype.render = function() {
    context.beginPath();
    context.arc(this.x, this.y, this.radius, 2 * Math.PI, false);
    context.fillStyle = "#FF0000";
    context.fill();
};
BottomLine.prototype.render = function() {
    context.beginPath();
    context.fillRect(0, 405, 700, 5);
    context.fillStyle = "#FFFFFF";
    context.fill();
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
        if(value == 79){ // Keyboard key 'O'
            this.paddle.move(0,-4);
        }
        else if (value == 76) { // Keyboard key 'L'
            this.paddle.move(0,4);
        }
        else {
            this.paddle.move(0,0);
        }
    }
}
Player2.prototype.update = function() {
    for(var key in keysDown) {
        var value = Number(key);
        if(value == 87) { // Keyboard key 'W'
            this.paddle.move(0,-4);
        }
        else if(value == 83) { // Keyboard key 'S'
            this.paddle.move(0,4);
        }
        else {
            this.paddle.move(0,0);
        }
    }
}
Ball.prototype.update = function(paddle1, paddle2) {
    this.x = this.x + this.x_speed;
    this.y = this.y + this.y_speed;
    var left_x = this.x - 5;
    var left_y = this.y - 5;
    var right_x = this.x + 5;
    var right_y = this.y + 5;

    if(this.y - 5 < 0) { // hitting the top wall
        this.y = 5;
        this.y_speed = -this.y_speed;
    } 
    else if(this.y - 5 > 400) { // hitting the bottom wall
        this.y = 395;
        this.y_speed = -this.y_speed;
    }
    if(this.x < 0 || this.x > 700) { // a point was scored
        this.x_speed = 3;
        this.y_speed = 0;
        this.x = 300;
        this.y = 200;
    }

    if(left_x > 500) {
        if(left_x < (paddle1.x + paddle1.width) && right_x > paddle1.x && left_y < (paddle1.y + paddle1.height) && right_y > paddle1.y) {
            // hit player 1´s paddle
            this.x_speed = -3;
            this.y_speed += (paddle1.y_speed / 2);
            this.x += this.x_speed;
        }
    } 
    else {
        if(left_x < (paddle2.x + paddle2.width) && right_x > paddle2.x && left_y < (paddle2.y + paddle2.height) && right_y > paddle2.y) {
        // hit player 2´s paddle
        this.x_speed = 3;
        this.y_speed += (paddle2.y_speed / 2);
        this.x += this.x_speed;
    }
  }
};

//move functions
Paddle.prototype.move = function(x, y) {
    this.x += x;
    this.y += y;
    this.x_speed = x;
    this.y_speed = y;
    if(this.y < 0) { //All the way to bottom
        this.y = 0;
        this.y_speed = 0;
    } else if(this.y + this.height > 400) { //All the way to the top
        this.y = 400 - this.height;
        this.y_speed = 0;
    }
}



















function drawScore() {
    context.font = "16px Arial";
    context.fillStyle = "#FFFFFF";
    context.fillText("Score:" + score, 8, 20);

}

function detectCollision() {

}






