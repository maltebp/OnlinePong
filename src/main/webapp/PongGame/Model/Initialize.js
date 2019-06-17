// /**
//  * Initialization of the game and overwriting current game state of the
//  *
//  * @param connection    The websocket address for the game server
//  *
//  * @Author Kristian Andersen, Malte Pedersen and Jacob Riis
//  */
// var connection;
//
// /**
//  * Creates connection to the game server. When an opponentName is found it sets up the game. When game is started, it generates and sends or retrieves the GameStateObject through the gameserver
//  *
//  * @param chosenScore   What score to reach for winning the game
//  */
// function initialize(chosenScore) {
//     var user = {"code": 1, "username": "Flemming", "password": "somePassWord"}; //TODO Retrieve userdata
//     var JSONuser = JSON.stringify(user);
//     connection = new WebSocket("ws://localhost:8080/gameserver");
//     //connection = new WebSocket("ws://62.79.16.17:8080/gameserver");
//
//     connection.onopen = function() {
//         connection.send(JSONuser);
//     };
//
//     connection.onmessage = function(event){
//         var obj = JSON.parse(event.data);
//         if (obj.code === 101){
//             console.log("Success " + event.data);
//         }
//         else if (obj.code === 102){
//             console.log("Success 2 " + event.data);
//
//             startButton.style.display = 'none';
//             canvas.style.display = 'inline';
//             setupGame(chosenScore);
//             animate(runGame);
//         }
//         else if(obj.code === 10) {
//             console.log(obj);
//             player2Movement(obj.paddle);    //Update the opponentName paddle
//             ballMovement(obj.ball);         //Update the ball
//             playerScores(obj.scores);       //Update the scores
//         }
//         if(obj.code === 103 || obj.code === 10) {
//             var gsObj = new GameStateObject(10, player1.paddle, ball, [player1.score.score, player2.score.score]);
//             console.log(gsObj);
//             connection.send(JSON.stringify(gsObj));
//         }
//     }
// }
//
// /**
//  * Updates the opponents paddle position and movement when he begins to move or stops moving.
//  *
//  * @param oppPaddle     The opponents paddle
//  */
// function player2Movement(oppPaddle) {
//     if(player2.paddle.y_speed !== oppPaddle.y_speed) {
//         player2.paddle.y = oppPaddle.y;
//         player2.paddle.y_speed = oppPaddle.y_speed;
//     }
// }
//
// /**
//  *
//  *
//  * @param oppBall   The balls position as the player sees it. This is only updated when it is at the opponents side, to
//  * secure a smooth experience when the player has to catch the ball
//  */
// function ballMovement(oppBall) {
//     if(ball.x > 400) {
//         ball.speed = oppBall.speed;
//         ball.x = width - oppBall.x;
//         ball.y = oppBall.y;
//         ball.x_speed = -oppBall.x_speed;
//         ball.y_speed = oppBall.y_speed;
//     }
// }
//
// /**
//  * Updates the score for the player if the opponentName says either of them have a higher score. The control is to secure
//  * the points don't reset/gets cancelled
//  *
//  * @param scores    State of the scores as opponentName sees them
//  */
// function playerScores(scores) {
//     if(scores[1] > player1.score.score || scores[0] > player2.score.score) {
//         player1.score.score = scores[1];
//         player2.score.score = scores[0];
//     }
}