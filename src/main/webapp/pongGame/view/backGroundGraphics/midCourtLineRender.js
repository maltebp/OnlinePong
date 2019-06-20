/**
 * Rendering function
 */
MidCourtLine.prototype.render = function() {
    context.beginPath();
    context.fillStyle = "#FFFFFF";
    context.fillRect(this.x, this.y, this.width, this.height);

};