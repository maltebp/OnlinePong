//Game objects:
var player1, player2, ball;                     //Paddle and Ball objects are created in setupGame(...)


//Variables
var canvas = document.getElementById("game");
var context = canvas.getContext('2d');
var keysDown = {};
var startBtn = document.getElementById("startBtn");
var maxScore;
var gameRunning;




