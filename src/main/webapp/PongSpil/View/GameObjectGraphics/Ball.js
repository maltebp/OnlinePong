/**
 * Ball object and the update and rendering function for it
 *
 * @Author Kristian Andersen and Jacob Riis
 */

/**
 * Constructor of Ball object
 *
 * @param x                 Current position of the ball on the x-axis
 * @param y                 Current position of the ball on the y-axis
 */
function Ball(x,y) {
    this.x = x;
    this.y = y;
    this.speed = 3;             //Current max speed of the ball
    this.x_speed = this.speed;  //Current horizontal speed of the ball
    this.y_speed = 0;           //Current vertical speed of the ball
    this.radius = 5;            //Radius of the ball object
    this.incrementSpeed = 0.1;  //Speed to be incremented with, when a paddle is hit
}

/**
 * Updates the position of the ball with its current speed. Controls with the calculated position of the ball if it
 * hits a player paddle or a player has scored a goal
 *
 * @param paddle1   Player one's paddle
 * @param paddle2   Player two's paddle
 */
Ball.prototype.update = function(paddle1, paddle2) {
    this.x = this.x + this.x_speed;
    this.y = this.y + this.y_speed;

    var ball_left = this.x - 5;
    var ball_right = this.x + 5;
    var ball_bottom = this.y + 5;
    var ball_top = this.y - 5;

    if(ball_top <= 0) { //Has hit the top wall
        this.y_speed = -this.y_speed;
    }
    else if(ball_bottom >= 400) { //Has hit the bottom wall
        this.y_speed = -this.y_speed;
    }
    if(this.x < 0 || this.x > 700) { //Goal
        this.speed = 3;
        if (this.x < 0){
            player2.score.goal();
            this.x_speed = this.speed; //Reverts the ball's direction
        } else if (this.x > 700) {
            player1.score.goal();
            this.x_speed = -this.speed; //Reverts the ball's direction
        }
        this.y_speed = 0;
        this.x = width/2;
        this.y = 200;
    }

    var newYSpeed; //Temporary holder for y_speed for calculations
    if(ball_right > 500) {  //Controls for player two when it is in his vicinity
        if(ball_right >= paddle2.x && (ball_bottom - ball.radius / 2) < (paddle2.y) &&
            (ball_top + ball.radius / 2) > paddle2.y + paddle2.height) { //Has hit player 2´s paddle

            this.speed += this.incrementSpeed;
            newYSpeed = this.y_speed + (paddle2.y_speed / 2);
            this.y_speed = (Math.abs(newYSpeed) > (this.speed - 1)) ? this.y_speed : newYSpeed;
            this.x_speed = -(Math.sqrt(Math.pow(this.speed, 2) - Math.pow(this.y_speed, 2)));
        }
    } else if (ball_left < 200) { //Controls for player one when it is in his vicinity
        if(ball_left <= (paddle1.x + paddle1.width) && (ball_bottom - ball.radius / 2) < (paddle1.y) &&
            (ball_top + ball.radius / 2) > paddle1.y + paddle1.height) { //Has hit player 1´s paddle

            this.speed += this.incrementSpeed;
            newYSpeed = this.y_speed + (paddle1.y_speed / 2);
            this.y_speed = (Math.abs(newYSpeed) > (this.speed - 1)) ? this.y_speed : newYSpeed;
            this.x_speed = Math.sqrt(Math.pow(this.speed, 2) - Math.pow(this.y_speed, 2));
        }
    }
};

/**
 * Rendering function
 */
Ball.prototype.render = function() {
    context.beginPath();
    context.arc(this.x, this.y, this.radius, 2 * Math.PI, false);
    context.fillStyle = "#FF0000";
    context.fill();
};