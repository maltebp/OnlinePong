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
            // var obj2 = JSON.stringify({Paddle: player1.paddle}, {Ball: ball});
            var gsObj = new GameStateObject("010", player1.paddle, ball, [player1.score, player2.score]);
            // var gsObj = new GameStateObject(player1.paddle, ball);
            console.log(gsObj);
            // console.log("Obj2");
            // console.log(obj2);
            if(x > 3){
                var obj = JSON.parse(event.data);
                console.log("GSObjectLook: ");
                console.log(obj);
                player2Movement(obj.Paddle);
                ballMovement(obj.Ball);
            }else{
                x++;
            }
            console.log(gsObj);
            connection.send(JSON.stringify(gsObj));

            // connection.send(JSON.stringify(player1.paddle)); //TODO
        }
    }
}

function player2Movement(paddle) {
    if(player2.paddle.y_speed !== paddle.y_speed) {
        player2.paddle.y = paddle.y;
    }
    player2.paddle.y_speed = paddle.y_speed;
}

function ballMovement(obj) {

}