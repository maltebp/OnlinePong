// Variables:
var width = 700;
var height = 500;
var midCourtGraphics = new MidcourtGraphics();  //the object for the midcourt graphics which is for objects of lines
var bottomLine = new BottomLine();              //the bottom line

var animate = window.requestAnimationFrame || window.webkitRequestAnimationFrame || window.mozRequestAnimationFrame ||
    function(callback) {
        window.setTimeout(callback, 1000/60);
    };

//sets the width of the width of the canvas to width and height the same
canvas.width = width;
canvas.height = height;

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
function BottomLine(x,y,width,height) {
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