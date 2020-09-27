/**
 * Visuals of 4 lines through the middle of the playable area, to give a sense of when the ball is halfway through the
 * playable area
 *
 * @param midcourtGraphics  Object of the 4 lines
 *
 * @Author Jacob Riis
 */
var midCourtGraphics = new MidCourtGraphics();  //the object for the midcourt graphics which is for model of lines

/**
 *  Constructor of midCourtGraphic object, holding 4 instances of MidCourtLine
 */
function MidCourtGraphics() {
    this.midCourtLine1 = new MidCourtLine(347.5, 0 , 5, 50);
    this.midCourtLine2 = new MidCourtLine(347.5, 118.3333, 5, 50);
    this.midCourtLine3 = new MidCourtLine(347.5, 236.6666, 5, 50);
    this.midCourtLine4 = new MidCourtLine(347.5, 355, 5, 50);
}

