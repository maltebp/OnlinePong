// function initialize(chosenScore) {
//     startBtn.style.display = 'none';
//     canvas.style.display = 'inline';
//     setupGame(chosenScore);
//     animate(runGame);
// }

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
            if(x > 3){
                var obj = JSON.parse(event.data);
                if(player2.paddle.y_speed !== obj.y_speed) {
                    player2.paddle.y = obj.y;
                }
                player2.paddle.y_speed = obj.y_speed;
            }else{
                x++;
            }
            connection.send(JSON.stringify(player1.paddle));
        }
    }
}
