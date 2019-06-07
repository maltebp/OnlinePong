/**
 * Småting der skal fikses:
 * Player 1 + 2 skal byttes om, så Player 1 har paddle 1 og vice versa (paddles er rigtige!)
 *
 * @param chosenScore
 */
var connection;
var x = 1;

function initialize(chosenScore) {
    connection = new WebSocket("ws://62.79.16.17:8080/findgame/Jacob");

    connection.onmessage = function(event){

        if (x === 1){
            console.log("Success " + event.data);
            x++;
        }
        else if (x === 2){
            console.log("Success 2 " + event.data);

            startBtn.style.display = 'none';
            canvas.style.display = 'inline';
            setupGame(chosenScore);
            animate(runGame);

            x++;
        }
        else{
            player2.paddle = JSON.parse(event.data); // Parses String (or data) to JSON object.
            connection.send(player1.paddle.toString());
        }
    }
}

var animate = window.requestAnimationFrame || window.webkitRequestAnimationFrame || window.mozRequestAnimationFrame || 
function(callback) {
    window.setTimeout(callback, 1000/60);
};

//Game objects:
var player1, player2, ball;                     //Paddle and Ball objects are created in setupGame(...)
var midCourtGraphics = new MidcourtGraphics();  //the object for the midcourt graphics which is for objects of lines
var bottomLine = new BottomLine();              //the bottom line

//Variables
var canvas = document.getElementById("game");
var context = canvas.getContext('2d');
var width = 700;
var height = 500;
var keysDown = {};
var startBtn = document.getElementById("startBtn");
var maxScore; //TODO
var gameRunning;



//sets the width of the witdth of the canvas to width and height the same
canvas.width = width;
canvas.height = height;

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

//constructors
function Paddle(x,y,width,height) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.y_speed = 0;
}
function Ball(x,y) {
    this.x = x;
    this.y = y;
    this.speed = 3;
    this.x_speed = this.speed;
    this.y_speed = 0;
    this.radius = 5;
}
function Player1() {
    this.paddle = new Paddle(10, 175, 10, 50);
    this.score = new Score(175, 460, 0);
}
function Player2() {
    this.paddle = new Paddle(680, 175, 10, 50);
    this.score = new Score(525, 460, 0);
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
function Score(x,y, score) {
    this.x = x;
    this.y = y;
    this.score = score;
}
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
Paddle.prototype.render = function() {
    context.beginPath();
    context.fillStyle = "#FFFFFF";
    context.fillRect(this.x, this.y, this.width, this.height);
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
// Ball.prototype.render = function() {
//     context.beginPath();
//     context.arc(this.x, this.y, this.radius, 2 * Math.PI, false);
//     context.fillStyle = "#FF0000";
//     context.fill();
// };
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
    for(var key in keysDown) {
        var value = Number(key);
        if(value === 79) { // Keyboard key 'O'
            this.paddle.move(-4);
        }
        else if(value === 76) { // Keyboard key 'L'
            this.paddle.move(4);
        }
        else {
            this.paddle.move(0);
        }
    }
};
// Ball.prototype.update = function(paddle1, paddle2) {
//     this.x = this.x + this.x_speed;
//     this.y = this.y + this.y_speed;
//
//     var ball_left = this.x - 5;
//     var ball_bottom = this.y - 5;
//     var ball_right = this.x + 5;
//     var ball_top = this.y + 5;
//
//     if(ball_bottom <= 0) { // hitting the bottom wall
//         this.y_speed = -this.y_speed;
//     }
//     else if(ball_top >= 400) { // hitting the top wall
//         this.y_speed = -this.y_speed;
//     }
//     if(this.x < 0 || this.x > 700) { // a point was scored
//         this.speed = 3;
//         if (this.x < 0){
//             player2.score.goal();
//             this.x_speed = this.speed; // Reverts the ball's direction
//         } else if (this.x > 700) {
//             player1.score.goal();
//             this.x_speed = -this.speed; // Reverts the ball's direction
//         }
//         this.y_speed = 0;
//         this.x = width/2;
//         this.y = 200;
//     }
//
//     var newYSpeed;
//     if(ball_right > 500) {// > 500 //Boldens position foran player 1's paddle (500 på x aksen)
//         if(ball_right >= paddle2.x && (ball_bottom + ball.radius / 2) < (paddle2.y + paddle2.height) &&
//             (ball_top - ball.radius / 2) > paddle2.y) { // hit player 1´s paddle
//
//             this.speed += 0.1;
//             newYSpeed = this.y_speed + (paddle2.y_speed / 2);
//             this.y_speed = (Math.abs(newYSpeed) > (this.speed - 1)) ? this.y_speed : newYSpeed;
//             this.x_speed = -(Math.sqrt(Math.pow(this.speed, 2) - Math.pow(this.y_speed, 2)));
//
//         }
//     } else if (ball_left < 200) {
//         if(ball_left <= (paddle1.x + paddle1.width) && (ball_bottom + ball.radius / 2) < (paddle1.y + paddle1.height) &&
//             (ball_top - ball.radius / 2) > paddle1.y) { // hit player 2´s paddle
//
//             this.speed += 0.1;
//             newYSpeed = this.y_speed + (paddle1.y_speed / 2);
//             this.y_speed = (Math.abs(newYSpeed) > (this.speed - 1)) ? this.y_speed : newYSpeed;
//             this.x_speed = Math.sqrt(Math.pow(this.speed, 2) - Math.pow(this.y_speed, 2));
//
//         }
//     }
// };
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






