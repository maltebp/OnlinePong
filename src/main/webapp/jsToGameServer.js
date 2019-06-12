
var connection = null;




function createConnection() {

    connection = new WebSocket("ws://localhost:8080/communication");


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
        var jsonObject = JSON.parse(event.data);
        decodeEvent(jsonObject);


    }
}








function decodeEvent(jsonObject){

    switch (jsonObject.code) {
        case 101: findingGame(jsonObject);
            break;
        case 103:



    }


    }

function connection(){
    document.getElementById("messagesFromServer").innerHTML = "You are now connected...";
    whileLoading(true);
}

function findingGame(jsonObject){
    document.getElementById("messagesFromServer").innerHTML = "Awating an opponent.\n Estimated to wait for a game is "+jsonObject.timeEstimate+"\n Please wait..."
    whileLoading(true);
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