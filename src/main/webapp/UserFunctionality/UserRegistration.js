/**
 * Class to control the registration HTML of the website
 */

function isSamePasswords() {
    var pass = document.getElementById("password").value;
    var pass2 = document.getElementById("passwConf").value;
    return (pass === pass2);
}


function evaluateResponse(result){
    switch(result.code){

        case "1":
            alert("User created!");
            break;

        default:
            alert("Something went wrong" + JSON.stringify(result))
    }
}


$('form').on('submit', function(event){
    event.preventDefault();
    if(isSamePasswords()) {
        var userObj = $('#form').serializeJSON();
        delete userObj.passwConf;

        apiPost("/createUser", evaluateResponse, JSON.stringify(userObj));

        return false;
    } else {
        document.getElementById("registerFailMsg").innerHTML = "Passwords didn't match";
    }
});