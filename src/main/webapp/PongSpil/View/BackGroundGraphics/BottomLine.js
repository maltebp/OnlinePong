var bottomLine = new BottomLine();              //the bottom line

function BottomLine(x,y,width,height) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
}

BottomLine.prototype.render = function() {
    context.beginPath();
    context.fillStyle = "#FFFFFF";
    context.fillRect(0, 405, 700, 5);
};