function Score(x,y, score) {
    this.x = x;
    this.y = y;
    this.score = score;
}

Score.prototype.render = function() {
    context.beginPath();
    context.font = "30px Arial";
    context.fillStyle = "#FFFFFF";
    context.fillText(""+ this.score, this.x, this.y);
    context.closePath();
};

Score.prototype.goal = function() {
    this.score++;
    if(this.score === maxScore) {
        endGame();
    }
};