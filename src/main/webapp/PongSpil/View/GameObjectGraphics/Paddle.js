function Paddle(x,y,width,height) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.y_speed = 0;
}

Paddle.prototype.render = function() {
    context.beginPath();
    context.fillStyle = "#FFFFFF";
    context.fillRect(this.x, this.y, this.width, this.height);
};