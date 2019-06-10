/**
 * For playing locally (second player movement wont work)
 */
// function initialize(chosenScore) {
//     startBtn.style.display = 'none';
//     canvas.style.display = 'inline';
//     setupGame(chosenScore);
//     animate(runGame);
// }

/**
 * For playing with server
 */
var connection;
var x = 1;
function initialize(chosenScore) {
    var user = {"code": 1, "username": "Malte", "password": "somePassWord"}; //TODO Retrieve userdata
    var JSONuser = JSON.stringify(user);
    // connection = new WebSocket("ws://62.79.16.17:8080/communication/" + JSONuser);
    connection = new WebSocket("ws://localhost:8080/communication");
    // connection = new WebSocket("ws://62.79.16.17:8080/communication/" + "HejMalte");

    connection.onopen = function() {
        connection.send(JSONuser);
    };

    connection.onmessage = function(event){
        var obj = JSON.parse(event.data);
        // else if (x === 2){
        if (obj.code === 101){
            console.log("Success " + event.data);
            x++;
        }
        // else if (x === 2){
        else if (obj.code === 102){
            console.log("Success 2 " + event.data);

            startBtn.style.display = 'none';
            canvas.style.display = 'inline';
            setupGame(chosenScore);
            animate(runGame);

            x++;
        }
        else if(obj.code === 10) {

            //Retrieve information
            // if(x > 3){
                // var obj = JSON.parse(event.data);
                // if (obj.code === 10) {
                //     console.log("GSObjectLook: ");
                    console.log(obj);
                    player2Movement(obj.paddle);
                    ballMovement(obj.ball);
                    playerScores(obj.scores);
                // }
            // }else{
            //     x++;
            // }
            //Send information


            // connection.send(JSON.stringify(new GameStateObject("010", player1.paddle, ball, [player1.score, player2.score]))); //Should be final form
        }
        if(obj.code === 103 || obj.code === 10) {
            var gsObj = new GameStateObject(10, player1.paddle, ball, [player1.score.score, player2.score.score]);
            console.log(gsObj);
            connection.send(JSON.stringify(gsObj));
        }
    }
}

function player2Movement(oppPaddle) {
    if(player2.paddle.y_speed !== oppPaddle.y_speed) {
        player2.paddle.y = oppPaddle.y;
    }
    player2.paddle.y_speed = oppPaddle.y_speed;
}

function ballMovement(oppBall) {
    ball.speed = oppBall.speed;
    ball.x = width / 2 - oppBall.x;
    ball.y = oppBall.y;
    ball.x_speed = -oppBall.x_speed;
    ball.y_speed = oppBall.y_speed;
}

function playerScores(scores) {
    player1.score.score = scores[1];
    player2.score.score = scores[0];
}