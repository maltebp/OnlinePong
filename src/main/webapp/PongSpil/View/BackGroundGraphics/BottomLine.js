/***
 * Visual of line to split the games playable area and the area for the score
 *
 * @param bottomLine    White line to split the areas
 *
 * @Author Jacob Riis
 */
var bottomLine = new BottomLine();              //the bottom line

/**
 * Constructor of the line
 *
 * @param x         Position on the x axis
 * @param y         Position on the y axis
 * @param width     Width of the line
 * @param height    Height of the line
 */
function BottomLine(x,y,width,height) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
}

/**
 * Rendering function
 */
BottomLine.prototype.render = function() {
    context.beginPath();
    context.fillStyle = "#FFFFFF";
    context.fillRect(0, 405, 700, 5);
};