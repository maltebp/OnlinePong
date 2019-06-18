/**
 * Class to control the registration HTML of the website
 */

var registerLayer = document.getElementById("registerLayer");
var registerLoading = document.getElementById("loadingLayer");
var registerError = document.getElementById("registerErrorMsg");
var registerSuccess = document.getElementById("successLayer");

var registrationUsername = "";
var registrationPassword = "";

registerError.style.display = "none";
registerSuccess.style.display = "none";



function evaluateResponse(result){

    toggleLoader(false);

    switch(result.code){

        case "1":
            currUser = registrationUsername;
            currPassw = registrationPassword;
            registerLayer.style.display = "none";
            registerSuccess.style.display = "inline";
            break;

        default:
            registerLayer.style.display = "inline";
            showError("Something went wrong. User wasn't created.");

        registrationUsername = "";
        registrationPassword = "";
    }
}



function showError(msg){
    registerError.innerHTML = msg;
    registerError.style.display = "inline";
}


function toggleLoader(toggle){
    if(toggle){
        registerLayer.style.display = "none";
        registerLoading.style.display = "inline";
    }else{
        registerLayer.style.display = "inline";
        registerLoading.style.display = "none";
    }
}


function isSamePasswords() {
    var pass = document.getElementById("password").value;
    var pass2 = document.getElementById("passwConf").value;
    return (pass === pass2);
}

function createUser(){

    if( isSamePasswords() ) {
        var userObj = $('#form').serializeJSON();
        delete userObj.passwConf;

        toggleLoader(true);

        registrationUsername = userObj.username;
        registrationPassword = userObj.password;
        apiPost("/createUser", evaluateResponse, JSON.stringify(userObj));

    } else {
        showError("Passwords doesn't match");
    }

}


$('form').on('submit', function(){
    createUser();
    return false;
});


toggleLoader(false);