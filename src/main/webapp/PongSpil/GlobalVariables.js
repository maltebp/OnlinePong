/**
 * Collection of global variables
 *
 * @Author Kristian Andersen and Jacob Riis
 */
//Game objects:
var player1, player2, ball;                     //Paddle and Ball objects are created in setupGame(...)

//Variables
var keysDown = {83: false, 87: false};                  //Keys 'S' and 'W', which respectively makes the paddle go 'down' or 'up'
var startBtn = document.getElementById("startBtn");     //Reference to the 'Start Button'
var maxScore;                                           //Score the game is running 'till
var gameRunning;                                        //Boolean to control a game is being played