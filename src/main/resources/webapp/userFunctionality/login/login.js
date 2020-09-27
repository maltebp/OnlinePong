

var authenticating = false;

var loginLayer = document.getElementById("loginLayer");
var loadingLayer = document.getElementById("loadingLayer");
var loginTroubleText = document.getElementById("loginTrouble");

hide(loadingLayer);
hide(loginTroubleText);

// Resetting user password in browser
currPassw = null;
currUser = null;



function evaluateResponse(result, statusCode){

    switch(statusCode){

        // Username correct
        case 204:
            switchPage("pongGame/pongPage.html");
            break;

        // Wrong password
        case 401:
            showError("Wrong password!");
            currUser = "";
            currPassw = "";
            break;

        // Wrong username
        case 404:
            showError("Wrong username!");
            currUser = "";
            currPassw = "";
            break;

        default:
            showError("An unexpected error occured!");
            console.log(result);
            currUser = "";
            currPassw = "";
    }

    show(loginLayer);
    hide(loadingLayer);
    authenticating = false;
}


function showError(errorMsg){
    loginTroubleText.innerHTML = errorMsg;
    show(loginTroubleText);
}


/* Submit the username / password written in the form
    for authentication  */
function authenticate(){
    if( !authenticating ) {
        authenticating = true;
        show(loadingLayer);
        hide(loginLayer);
        currUser = document.forms["loginForm"]["username"].value;
        currPassw = document.forms["loginForm"]["password"].value;
        let userData = JSON.stringify({username : currUser, password : currPassw});

        apiPost("/user/"+currUser+"/authenticate", evaluateResponse, userData);
    }
}

