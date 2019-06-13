
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

        case 101: findingGame(jsonObject);
            break;

        case 102: initializeGame();
            break;

        case 103:gameStart103and10();
            break;
        
        case 10: console.log(obj);
            player2Movement(obj.paddle);    //Update the opponent paddle
            ballMovement(obj.ball);         //Update the ball
            playerScores(obj.scores);       //Update the scores
            gameStart103and10();
            break;
    }

}

function connection(){
    document.getElementById("messagesFromServer").innerHTML = "You are now connected...";
    startButton.style.display = 'none';
    whileLoading(true);
}

function findingGame(jsonObject){

    startButton.style.display = 'none';
    document.getElementById("messagesFromServer").innerHTML = "Awating an opponent.\n Estimated to wait for a game is "+jsonObject.timeEstimate+"\n\n Please wait..."
    //whileLoading(true);
}


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
    canvas.style.display = 'inline';
    setupGame(chosenScore);
    animate(runGame);
}

function gameStart103and10(){
    var gsObj = new GameStateObject(10, player1.paddle, ball, [player1.score.score, player2.score.score]);
    console.log(gsObj);
    connection.send(JSON.stringify(gsObj));
}