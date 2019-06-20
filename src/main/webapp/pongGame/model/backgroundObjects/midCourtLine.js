/**
 * Line to go through the middle of the playable area, to give a sense of when the ball have travelled halfway the full
 * distance of the playable area.
 *
 * @Author  Jacob Riis
 */

/**
 * Constructor of a line
 *
 * @param x         Position on the x-axis
 * @param y         Position on the y-axis
 * @param width     Width of the line
 * @param height    Height of the line
 */
function MidCourtLine(x,y,width,height){
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
}

