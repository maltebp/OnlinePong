var connection = null;
var chosenScore = 10;
var opponentName = "";

function startGame() {
    connection = new WebSocket("ws://localhost:8080/gameserver");

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
            finishedGame(jsonObject);
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
            opponentDisconected();
            finishedGame(jsonObject);
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

function findingGame(jsonObject){
    document.getElementById("messagesFromServer").innerHTML = "Awating an opponent.\n Estimated to wait for a game is "+jsonObject.timeEstimate+"\n\n Please wait..."
}

function initializeGame(){
    document.getElementById("messagesFromServer").innerHTML = "You're playing against " + opponentName + ", good luck!";
    document.getElementById("loading").innerHTML = "";
    document.getElementById("playerElo").innerHTML = "";
    canvas.style.display = 'inline';
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
        console.log("NO WINNER YET;");
        connection.send(JSON.stringify(gsObj));
    }
}

function opponentDisconected(){
    document.getElementById("messagesFromServer").innerHTML = "Opponent has been disconnected from the game\n You have won";
    document.getElementById("loading").innerHTML = "";
    connection.close();
}

function userAlreadyLoggedIn(){
    document.getElementById("messagesFromServer").innerHTML = "You are already logged in... Please Wait"
    document.getElementById("newGameBtn").style.display = 'inline';
}

function unableToAuthendizise(){
    document.getElementById("messagesFromServer").innerHTML = "Unable to make authendication\n Please try again lator";
    document.getElementById("newGameBtn").style.display = 'inline';
    connection.close();
}

function wrongUserNameOrPassword(){
    document.getElementById("messagesFromServer").innerHTML = "Wrong username or password\n Please try again";
    document.getElementById("newGameBtn").style.display = 'inline';
    connection.close;
}

function initializingMessage001(){
    var user = {
        "code": 1,
        "username": currUser,
        "password": currPassw
    };
    console.log(user);
    connection.send(JSON.stringify(user));
    document.getElementById("newGameBtn").style.display = 'none';
}

function finishedGame(jsonObject){
    endGame();
    currElo += jsonObject.ratingChange;
    if(jsonObject.hasWon === true) {
        document.getElementById("messagesFromServer").innerHTML = "Congrats, you won against " + opponentName + "!";
            document.getElementById("playerElo").innerHTML = "Your elo: " + currElo + "(+" + jsonObject.ratingChange + ")";
    }else{
        document.getElementById("messagesFromServer").innerHTML = "You have lost against " + opponentName;
        document.getElementById("playerElo").innerHTML = "Your elo: " + currElo + "(" + jsonObject.ratingChange + ")";
    }
    document.getElementById("messagesFromServer").innerHTML += "\nYour opponents rating changed by: " + jsonObject.oppRatingChange;
    document.getElementById("newGameBtn").style.display = 'inline';
}

function  checkForWinner(){
    if(player2.score.score === chosenScore) {
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