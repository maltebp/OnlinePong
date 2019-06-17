/**
 * Animates the game by using window.requestAnimationFrame
 *
 * @param width     Width of the game screen
 * @param height    Height of the game screen
 * @param canvas    HTMLCanvasElement of 'game'
 * @param context   Drawing context on canvas (2D chosen)
 *
 * @type {HTMLElement}
 */
var width = 700;
var height = 500;
var canvas = document.getElementById("game");
var context = canvas.getContext('2d');

var animate = window.requestAnimationFrame || window.webkitRequestAnimationFrame || window.mozRequestAnimationFrame ||
    function(callback) {
        window.setTimeout(callback, 1000/60);
    };

/**
 *  Sets the canvas width and height to be the chosen sizes //TODO Moveable up to other variables?
 */
canvas.width = width;
canvas.height = height;