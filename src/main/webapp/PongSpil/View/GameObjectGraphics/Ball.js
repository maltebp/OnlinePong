function Ball(x,y) {
    this.x = x;
    this.y = y;
    this.speed = 3;
    this.x_speed = this.speed;
    this.y_speed = 0;
    this.radius = 5;
    this.incrementSpeed = 0.1;
}
Ball.prototype.render = function() {
    context.beginPath();
    context.arc(this.x, this.y, this.radius, 2 * Math.PI, false);
    context.fillStyle = "#FF0000";
    context.fill();
};

Ball.prototype.update = function(paddle1, paddle2) {
    this.x = this.x + this.x_speed;
    this.y = this.y + this.y_speed;

    var ball_left = this.x - 5;
    var ball_bottom = this.y - 5;
    var ball_right = this.x + 5;
    var ball_top = this.y + 5;

    if(ball_bottom <= 0) { // hitting the bottom wall
        this.y_speed = -this.y_speed;
    }
    else if(ball_top >= 400) { // hitting the top wall
        this.y_speed = -this.y_speed;
    }
    if(this.x < 0 || this.x > 700) { // a point was scored
        this.speed = 3;
        if (this.x < 0){
            player2.score.goal();
            this.x_speed = this.speed; // Reverts the ball's direction
        } else if (this.x > 700) {
            player1.score.goal();
            this.x_speed = -this.speed; // Reverts the ball's direction
        }
        this.y_speed = 0;
        this.x = width/2;
        this.y = 200;
    }

    var newYSpeed;
    if(ball_right > 500) {  //Boldens position foran player 1's paddle (500 på x aksen)
        if(ball_right >= paddle2.x && (ball_bottom + ball.radius / 2) < (paddle2.y + paddle2.height) &&
            (ball_top - ball.radius / 2) > paddle2.y) { // hit player 2´s paddle

            console.log("Bump you");
            this.speed += this.incrementSpeed;
            newYSpeed = this.y_speed + (paddle2.y_speed / 2);
            this.y_speed = (Math.abs(newYSpeed) > (this.speed - 1)) ? this.y_speed : newYSpeed;
            this.x_speed = -(Math.sqrt(Math.pow(this.speed, 2) - Math.pow(this.y_speed, 2)));

        }
    } else if (ball_left < 200) {
        if(ball_left <= (paddle1.x + paddle1.width) && (ball_bottom + ball.radius / 2) < (paddle1.y + paddle1.height) &&
            (ball_top - ball.radius / 2) > paddle1.y) { // hit player 1´s paddle

            console.log("Bump me");
            this.speed += this.incrementSpeed;
            newYSpeed = this.y_speed + (paddle1.y_speed / 2);
            this.y_speed = (Math.abs(newYSpeed) > (this.speed - 1)) ? this.y_speed : newYSpeed;
            this.x_speed = Math.sqrt(Math.pow(this.speed, 2) - Math.pow(this.y_speed, 2));

        }
    }
};