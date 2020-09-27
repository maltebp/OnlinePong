/**
 * Class to control the registration HTML of the website
 */

var registerLayer = document.getElementById("registerLayer");
var registerLoading = document.getElementById("loadingLayer");
var registerError = document.getElementById("registerErrorMsg");
var registerSuccess = document.getElementById("successLayer");

var registrationUsername = "";
var registrationPassword = "";

hide(registerError);
hide(registerSuccess);



function evaluateResponse(result, status){

    console.log(result);
    toggleLoader(false);

    switch(status){

        case 201:
            currUser = registrationUsername;
            currPassw = registrationPassword;
            hide(registerLayer);
            show(registerSuccess);
            break;

        case 403:
            show(registerLayer);
            showError("Username is taken");
            break;

        default:
            show(registerLayer);
            showError("Something went wrong. User wasn't created.");
    }

    registrationUsername = "";
    registrationPassword = "";
}



function showError(msg){
    registerError.innerHTML = msg;
    show(registerError);
}


function toggleLoader(toggle){
    if(toggle){
        hide(registerLayer);
        show(registerLoading);
    }else{
        show(registerLayer);
        hide(registerLoading);
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
        apiPost("/user", evaluateResponse, JSON.stringify(userObj));

    } else {
        showError("Passwords doesn't match");
    }

}


$('form').on('submit', function(){
    createUser();
    return false;
});


toggleLoader(false);