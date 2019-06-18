
var findGameLayer = document.getElementById("findGameLayer");
var findGameLoader = document.getElementById("findGameLoader");
var findGameError = document.getElementById("findGameError");
var findGameBtn = document.getElementById("findGameBtn");
var gameLayer = document.getElementById("gameLayer");
var gameFinishedLayer = document.getElementById("gameFinishedLayer");
var opponentRatingRes = document.getElementById("opponentRatingResult");
var yourRatingRes = document.getElementById("yourRatingResult");
var gameResultMsg = document.getElementById("gameResultMsg");


document.getElementById("username").innerHTML = currUser;


function showFindGameError(errormsg){
    toggleFindGameLoading(false);
    findGameError.innerHTML = errormsg;
    show(findGameError);
}

function setFindGameLoadingMsg(message){
    document.getElementById("findGameLoadingMsg").innerHTML = message;
}

function enableGame(opponentName){
    document.getElementById("opponentNameResult").innerHTML = opponentName;
    document.getElementById("opponentName").innerHTML = "Opponent: " + opponentName;
    hide(findGameLayer);
    show(gameLayer);
}

function toggleFindGameLoading(toggle){
    if(toggle){
        hide(findGameBtn);
        hide(findGameError);
        show(findGameLoader);
    }else{
        show(findGameBtn);
        hide(findGameLoader);
    }
}


function showFindGame(){
    hide(gameFinishedLayer);
    hide(gameLayer);
    hide(findGameError);
    show(findGameLayer);
    startGame();
}

function showGameResult(resultCode, ratingChange, oppRatingChange){

    hide(gameLayer);
    show(gameFinishedLayer);

    yourRatingRes.innerHTML = ratingChange;
    opponentRatingRes.innerHTML = oppRatingChange;

    switch(resultCode) {

        case 1:
            gameResultMsg.innerHTML = "YOU WON!";
            gameResultMsg.style.color = "darkgreen";
            yourRatingRes.style.color = "darkgreen";
            opponentRatingRes.style.color = "darkred";
            break;

        case 0:
            gameResultMsg.innerHTML = "YOU LOST!";
            gameResultMsg.style.color = "darkred";
            yourRatingRes.style.color = "darkred";
            opponentRatingRes.style.color = "darkgreen";
            break;

        case -1:
            gameResultMsg.innerHTML = "OPPONENT DISCONNECTED - YOU WON!";
            gameResultMsg.style.color = "black";
            yourRatingRes.style.color = "darkgreen";
            opponentRatingRes.style.color = "darkred";
            break;
    }
}



show(findGameLayer);
hide(gameLayer);
hide(gameFinishedLayer);
hide(findGameLoader);
hide(findGameError);


