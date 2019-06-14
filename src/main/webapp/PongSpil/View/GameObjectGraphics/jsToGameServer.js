/*
var connection = null;

var chosenScore = 10;


function createConnection() {

    connection = new WebSocket("ws://localhost:8080/gameserver");

    connection.onopen = function () {
        var user = {
            "code": 1,
            "username": document.forms["loginForm"]["Username"].value,
            "password": document.forms["loginForm"]["Password"].value

        };
        console.log(user);
        connection.send(JSON.stringify(user));
    }

    connection.onmessage = function (event) {
        console.log(event.data);
        var obj = JSON.parse(event.data);

        decodeEvent(obj);
    }
}









function decodeEvent(jsonObject){

    switch (jsonObject.code) {

        case 101:
            findingGame(jsonObject);
            break;

        case 102:
            acceptGame002();
            initializeGame();
            //initialize(chosenScore);
            break;

        case 103: sendGameState103and010();
            break;

        case 10:
           // gameDataUpdatedata();
            console.log(jsonObject);
            player2Movement(jsonObject.paddle);    //Update the opponent paddle
            ballMovement(jsonObject.ball);         //Update the ball
            playerScores(jsonObject.scores);       //Update the scores
            sendGameState103and010();
            break;

        case 201: wrongUserNameOrPassword();
            break;

        case 202: userAlreadyLoggedIn();
            break;

        case 203: unableToAuthendizise();
            break;
        case 210:
            opponentDisconected();
            break;


    }

}

function connection(){
    document.getElementById("messagesFromServer").innerHTML = "You are now connected...";
    startButton.style.display = 'none';
    whileLoading(true);
}

function findingGame(jsonObject){


    document.getElementById("messagesFromServer").innerHTML = "Awating an opponent.\n Estimated to wait for a game is "+jsonObject.timeEstimate+"\n\n Please wait..."
    //whileLoading(true);
}

/**
 * Bare for sjov
 * @param run
 */

/*
function whileLoading(run){
    var run = true;
    while(run){
        var loadString = "";
        for(var i =0; i < 5; i=0) {
            loadString.concat(". ");
            document.getElementById("loading").innerHTML = loadString;

        }
    }
}

function initializeGame(){
    startButton.style.display = 'none';
    document.getElementById("loading").innerHTML = "A game has been found..."
    canvas.style.display = 'inline';
    setupGame(chosenScore);
    animate(runGame);
}


function acceptGame002(){

    var obj = {
        "code": 002
    };
    var jsonString = JSON.stringify(obj);
    connection.send(jsonString);
}

function sendGameState103and010(){
    var gsObj = new GameStateObject(10, player1.paddle, ball, [player1.score.score, player2.score.score]);
    console.log()



    //console.log(gsObj);
    //console.log(position);
    console.log("Det virker");
    connection.send(JSON.stringify(gsObj));
}

function opponentDisconected(){
    document.getElementById("messagesFromServer").innerHTML = "Opponent has been disconnected from the game\n You have won";
    document.getElementById("loading").innerHTML = "";
    canvas.style.display ="none";
    //animate(endGame());
    connection.close;
}

function userAlreadyLoggedIn(){
    document.getElementById("loading").innerHTML = "You are already logged in... Please Wait"
}

function unableToAuthendizise(){
    document.getElementById("messagesFromServer").innerHTML = "Unable to make authendication\n Please try again lator";
    connection.close();
}

function wrongUserNameOrPassword(){
    document.getElementById("messagesFromServer").innerHTML = "Wrong username or password\n Please try again";
    connection.close;


}

function gameDataUpdatedata()
{
    console.log(jsonObject);
    player2Movement(jsonObject.paddle);    //Update the opponent paddle
    ballMovement(jsonObject.ball);         //Update the ball
    playerScores(jsonObject.scores);       //Update the scores
    sendGameState103and010();
}
*/

var connection = null;

var chosenScore = 10;


function createConnection() {

    connection = new WebSocket("ws://localhost:8080/gameserver");

    connection.onopen = function () {
        var user = {
            "code": 1,
            "username": document.forms["loginForm"]["Username"].value,
            "password": document.forms["loginForm"]["Password"].value

        };
        console.log(user);
        connection.send(JSON.stringify(user));
    };

    connection.onmessage = function (event) {
        console.log(event.data);
        var obj = JSON.parse(event.data);
        decodeEvent(obj);
    }
}









function decodeEvent(jsonObject){

    switch (jsonObject.code) {

        case 101:
            findingGame(jsonObject);
            break;

        case 102:
            acceptGame002();
            initializeGame();
            //initialize(chosenScore);
            break;

        case 103: sendGameState103and010();
            break;

        case 10:
            gameDataUpdatedata(jsonObject);

            break;

        case 201: wrongUserNameOrPassword();
            break;

        case 202: userAlreadyLoggedIn();
            break;

        case 203: unableToAuthendizise();
            break;
        case 210:
            opponentDisconected();
            break;
    }

}


function gameDataUpdatedata(jsonObject){
    console.log(jsonObject);
    player2Movement(jsonObject.paddle);    //Update the opponent paddle
    ballMovement(jsonObject.ball);         //Update the ball
    playerScores(jsonObject.scores);       //Update the scores
    sendGameState103and010();
}

function connection(){
    document.getElementById("messagesFromServer").innerHTML = "You are now connected...";
    startButton.style.display = 'none';
    whileLoading(true);
}

function findingGame(jsonObject){
    document.getElementById("messagesFromServer").innerHTML = "Awating an opponent.\n Estimated to wait for a game is "+jsonObject.timeEstimate+"\n\n Please wait..."
    //whileLoading(true);
}


function initializeGame(){
    startButton.style.display = 'none';
    document.getElementById("loading").innerHTML = "A game has been found...";
    canvas.style.display = 'inline';
    setupGame(chosenScore);
    animate(runGame);
}


function acceptGame002(){
    var obj = {
        "code": 002
    };
    var jsonString = JSON.stringify(obj);
    connection.send(jsonString);
}

function sendGameState103and010(){
    var gsObj = new GameStateObject(10, player1.paddle, ball, [player1.score.score, player2.score.score]);
    console.log();
    var position = {
        "code": gsObj.code,
        "ball": {
            "x": gsObj.ball.x,
            "y": gsObj.ball.y,
            "xVel": gsObj.ball.x_speed,
            "yVel": gsObj.ball.y_speed,
            "speed": gsObj.ball.speed
        },
        "paddle": {"y": gsObj.paddle.y, "yVel": gsObj.paddle.yVel}, "scores": [player1.score.score, player2.score.score]
    };

    console.log(gsObj);
    //console.log(position);
    connection.send(JSON.stringify(gsObj));
}


function opponentDisconected(){
    document.getElementById("messagesFromServer").innerHTML = "Opponent has been disconnected from the game\n You have won";
    document.getElementById("loading").innerHTML = "";
    canvas.style.display ="none";
    //animate(endGame());
    connection.close;
}

function userAlreadyLoggedIn(){
    document.getElementById("loading").innerHTML = "You are already logged in... Please Wait"
}

function unableToAuthendizise(){
    document.getElementById("messagesFromServer").innerHTML = "Unable to make authendication\n Please try again lator";
    connection.close();
}

function wrongUserNameOrPassword(){
    document.getElementById("messagesFromServer").innerHTML = "Wrong username or password\n Please try again";
    connection.close;


}