/**
 * Score model and the update
 *
 * @Author Kristian Andersen and Jacob Riis
 */

/**
 *
 * @param x
 * @param y
 * @param score
 * @constructor
 */
function Score(x,y, score) {
    this.x = x;
    this.y = y;
    this.score = score;
}

/**
 * Increments the players score by one, when a goal is scored
 */
Score.prototype.goal = function() {
    this.score++;
    /*if(this.score === maxScore) {
        endGame();
    }*/
};

/**
 * Rendering function
 */
Score.prototype.render = function() {
    context.beginPath();
    context.font = "30px Arial";
    context.fillStyle = "#FFFFFF";
    context.fillText(""+ this.score, this.x, this.y);
    context.closePath();
};