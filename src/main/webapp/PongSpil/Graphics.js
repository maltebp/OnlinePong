// Variables:
var midCourtGraphics = new MidcourtGraphics();  //the object for the midcourt graphics which is for objects of lines




//Functions and constructors:
function MidcourtGraphics() {
    this.midCourtLine1 = new MidCourtLine(347.5, 0 , 5, 50);
    this.midCourtLine2 = new MidCourtLine(347.5, 118.3333, 5, 50);
    this.midCourtLine3 = new MidCourtLine(347.5, 236.6666, 5, 50);
    this.midCourtLine4 = new MidCourtLine(347.5, 355, 5, 50);
}

function MidCourtLine(x,y,width,height){
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
}

function Score(x,y, score) {
    this.x = x;
    this.y = y;
    this.score = score;
}