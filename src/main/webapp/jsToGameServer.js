
var codeList =["001","002","010","100"];
var connection = null;

function myFunction() {
    var x = document.forms["middleFrame"]["Username"].value;
    var y = document.forms["middleFrame"]["Password"].value;

    var msg = codeList[0]+ ","+ x+","+y;

    //var msg = buildString(codeList[0], x ,y);

    console.log(msg);

    connection = new WebSocket( "ws://localhost:8080/socket/"+msg);

}

connection.onopen = function(){
    console.log("Initializing as "+msg);
}

function buildString(code, userName, passWord){

    var msg = '{ "code : "';
    msg = msg.concat(codeList[1]);
    msg = msg.concat(", \n" + "username : " + userName +",\n password: " + passWord) + " }";


    return msg


}

