
var connection;

var x = 1;

connection = new WebSocket("ws://62.79.16.17:8080/socket/Jacob");

connection.onopen = function() {
    console.log("hej");
    while (x < 100) {

        connection.send(x++);

    }
};
