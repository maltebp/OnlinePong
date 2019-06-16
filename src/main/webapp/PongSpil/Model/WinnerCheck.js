        function  checkForWinner(){


            if(player2.score.score === chosenScore||player1.score.score===chosenScore) {

                    var winner = {"code": 011}

                    }

            var jsonString = JSON.stringify(winner);
            connection.send(jsonString);
                }