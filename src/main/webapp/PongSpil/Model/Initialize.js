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
    connection = new WebSocket("ws://62.79.16.17:8080/socket/Jacob");

    connection.onmessage = function(event){

        if (x === 1){
            console.log("Success " + event.data);
            x++;
        }
        else if (x === 2){
            console.log("Success 2 " + event.data);

            startBtn.style.display = 'none';
            canvas.style.display = 'inline';
            setupGame(chosenScore);
            animate(runGame);

            x++;
        }
        else{
            //Retrieve information
            if(x > 3){
                var obj = JSON.parse(event.data);
                console.log("GSObjectLook: ");
                console.log(obj);
                player2Movement(obj.paddle);
                ballMovement(obj.ball);
                playerScores(obj.scores);
            }else{
                x++;
            }
            //Send information
            var gsObj = new GameStateObject("010", player1.paddle, ball, [player1.score, player2.score]);
            console.log(gsObj);
            connection.send(JSON.stringify(gsObj));

            // connection.send(JSON.stringify(new GameStateObject("010", player1.paddle, ball, [player1.score, player2.score]))); //Should be final form
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
    player1.score = scores[1];
    player2.score = scores[0];
}