/**
 * Paddle object and the update and rendering functions for it
 *
 * @Author Kristian Andersen and Jacob Riis
 */

/**
 * Constructor of a player Paddle object
 *
 * @param x         Current position on the x-axis
 * @param y         Current position on the y-axis
 * @param width     Width of the paddle
 * @param height    Height of the paddle
 */
function Paddle(x,y,width,height) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.y_speed = 0;
}

/**
 * Moves the player paddle by the given speed on the y-axis
 *
 * @param y_speed   Current speed on the y-axis
 */
Paddle.prototype.move = function(y_speed) {
    this.y += y_speed;
    if(this.y < 0) { //All the way to top
        this.y = 0;
        this.y_speed = 0;
    } else if(this.y + this.height > 400) { //All the way to the bottom
        this.y = 400 - this.height;
        this.y_speed = 0;
    }
};

/**
 * Rendering function
 */
Paddle.prototype.render = function() {
    context.beginPath();
    context.fillStyle = "#FFFFFF";
    context.fillRect(this.x, this.y, this.width, this.height);
};