
//Variables:
var canvas = document.getElementById("game");
var context = canvas.getContext('2d');
var width = 700;
var height = 500;

//Functions
var animate = window.requestAnimationFrame || window.webkitRequestAnimationFrame || window.mozRequestAnimationFrame ||
    function(callback) {
        window.setTimeout(callback, 1000/60);
    };

//sets the width of the width of the canvas to width and height the same
canvas.width = width;
canvas.height = height;