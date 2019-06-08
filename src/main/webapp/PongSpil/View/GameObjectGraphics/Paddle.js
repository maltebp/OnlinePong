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