

var authenticating = false;

var loginLayer = document.getElementById("loginLayer");
var loadingLayer = document.getElementById("loadingLayer");
var loginTroubleText = document.getElementById("loginTrouble");


loadingLayer.style.display = 'none';
loginTroubleText.style.display = 'none';

// Resetting user password in browser
currPassw = null;
currUser = null;




function evaluateResponse(result){

    switch(result.code){

        case "1":
            switchPage("PongPage.html");
            break;

        case "-1":
            showError("Wrong username and/or password!");
            currUser = "";
            currPassw = "";
            break;

        default:
            showError("An unexpected error occured!");
            currUser = "";
            currPassw = "";
    }

    loginLayer.style.display = 'inline';
    loadingLayer.style.display = 'none';
    authenticating = false;
}


function showError(errorMsg){
    loginTroubleText.innerHTML = errorMsg;
    loginTroubleText.style.display = "inline";
}


/* Submit the username / password written in the form
    for authentication  */
function authenticate(){
    if( !authenticating ) {
        authenticating = true;
        loadingLayer.style.display = 'inline';
        loginLayer.style.display = 'none';
        currUser = document.forms["loginForm"]["username"].value;
        currPassw = document.forms["loginForm"]["password"].value;
        let userData = JSON.stringify({username : currUser, password: currPassw});
        apiPost("/AuthUser", evaluateResponse, userData);
    }
}

