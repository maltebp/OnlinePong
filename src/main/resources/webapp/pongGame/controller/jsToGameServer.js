var connection = null;
var chosenScore = 5;
var opponentName = "";
var gameFinished = false;

function startGame() {
    toggleFindGameLoading(true);
    var url = "ws://" + location.host + "/gameserver";
    console.log("Connecting to game server: " + url);
    connection = new WebSocket(url);

    connection.onopen = function () {
        initializingMessage001();
    };

    connection.onmessage = function (event) {
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
            opponentName = jsonObject.username;
            acceptGame002();
            initializeGame();
            break;

        case 103:
            sendGameState103and010();
            break;

        case 10:
            gameDataUpdatedata(jsonObject);
            break;

        case 104:
            var resultCode = (jsonObject.hasWon) ? 1 : 0;
            finishedGame(resultCode, jsonObject);
            break;

        case 201:
            wrongUserNameOrPassword();
            break;

        case 202:
            userAlreadyLoggedIn();
            break;

        case 203:
            unableToAuthendizise();
            break;

        case 210:
            finishedGame(-1, jsonObject);
            break;
    }
}

function gameDataUpdatedata(jsonObject){
    player2Movement(jsonObject.paddle);    //Update the opponent paddle
    ballMovement(jsonObject.ball);         //Update the ball
    playerScores(jsonObject.scores);       //Update the scores
    sendGameState103and010();
}

function findingGame(jsonObject){
    setFindGameLoadingMsg("Finding an opponent...");
}

function initializeGame(){
    enableGame(opponentName);
    setupGame(chosenScore);
    animate(runGame);
}

function acceptGame002(){
    var obj = {
        "code": 2
    };
    var jsonString = JSON.stringify(obj);
    connection.send(jsonString);
}

function sendGameState103and010(){
    var gsObj = new GameStateObject(10, player1.paddle, ball, [player1.score.score, player2.score.score]);
    if(player2.score.score===chosenScore){
        checkForWinner();
    }
    else{
        connection.send(JSON.stringify(gsObj));
    }
}

function opponentDisconected(){
    document.getElementById("messagesFromServer").innerHTML = "Opponent has been disconnected from the game\n You have won";
    document.getElementById("loading").innerHTML = "";
}

function userAlreadyLoggedIn(){
    showFindGameError("You're already logged in to game server");
    connection.close();
}

function unableToAuthendizise(){
    showFindGameError("Unable to authenticate user. Please, try again later.");
    connection.close();
}

function wrongUserNameOrPassword(){
    showFindGameError("Wrong username/password sent to server");
    connection.close();
}

function initializingMessage001(){
    var user = {
        "code": 1,
        "username": currUser,
        "password": currPassw
    };

    gameFinished = false;

    setFindGameLoadingMsg("Authenticating with server...");
    connection.send(JSON.stringify(user));
}

function finishedGame(result, jsonObject){
    endGame();
    connection.close();
    showGameResult(result, jsonObject.ratingChange, jsonObject.oppRatingChange);
}

function  checkForWinner(){
    if(player2.score.score === chosenScore && !gameFinished ) {
        gameFinished = true;
        console.log("Sending winner");
        var winner = {"code": 11};
        var jsonString = JSON.stringify(winner);
        connection.send(jsonString);
    }
}

/**
 * Updates the opponents paddle position and movement when he begins to move or stops moving.
 *
 * @param oppPaddle     The opponents paddle
 */
function player2Movement(oppPaddle) {
    if(player2.paddle.y_speed !== oppPaddle.y_speed) {
        player2.paddle.y = oppPaddle.y;
        player2.paddle.y_speed = oppPaddle.y_speed;
    }
}

/**
 *
 *
 * @param oppBall   The balls position as the player sees it. This is only updated when it is at the opponents side, to
 * secure a smooth experience when the player has to catch the ball
 */
function ballMovement(oppBall) {
    if(ball.x > 400) {
        ball.speed = oppBall.speed;
        ball.x = width - oppBall.x;
        ball.y = oppBall.y;
        ball.x_speed = -oppBall.x_speed;
        ball.y_speed = oppBall.y_speed;
    }
}

/**
 * Updates the score for the player if the opponent says either of them have a higher score. The control is to secure
 * the points don't reset/gets cancelled
 *
 * @param scores    State of the scores as opponent sees them
 */
function playerScores(scores) {
    if(scores[1] > player1.score.score || scores[0] > player2.score.score) {
        player1.score.score = scores[1];
        player2.score.score = scores[0];
    }
}