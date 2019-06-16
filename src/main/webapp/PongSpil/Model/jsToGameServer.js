var connection = null;

var chosenScore = 1;


function createConnection() {

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
            acceptGame002();
            initializeGame();
            //initialize(chosenScore);
            break;

        case 103: sendGameState103and010();
            break;

        case 10:
            gameDataUpdatedata(jsonObject);
            break;

        case 104:
            finishedGame(jsonObject);
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
        if(player1.score.score===chosenScore || player1.score.score === chosenScore){checkForWinner();
        }
        else{
            console.log(gsObj);
            connection.send(JSON.stringify(gsObj));
        }


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

function initializingMessage001(){
    var user = {
        "code": 1,
        "username": document.forms["loginForm"]["Username"].value,
        "password": document.forms["loginForm"]["Password"].value

    };
    console.log(user);
    connection.send(JSON.stringify(user));
    startButton.style.display = 'none';
}

function finishedGame(jsonObject){

    if(jsonObject.winner===true) {
        document.getElementById("messagesFromServer").innerHTML = "Congrats, YOU WON THE HAME!!!!";
    }else{    document.getElementById("messagesFromServer").innerHTML = "Sorry, you have lost the game:(";}

    document.getElementById("loading").innerHTML ="Rating cgange is: "+ jsonObject.ratingChange+", Upp rating change:  "+jsonObject.oppRatingChange;


}