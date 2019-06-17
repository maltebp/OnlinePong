        function  checkForWinner(){

            if(player2.score.score === chosenScore) {
                var winner = {"code": 11};
                var jsonString = JSON.stringify(winner);

                connection.send(jsonString);
            }

        }