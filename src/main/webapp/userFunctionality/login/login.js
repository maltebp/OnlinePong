

var authenticating = false;

var loginLayer = document.getElementById("loginLayer");
var loadingLayer = document.getElementById("loadingLayer");
var loginTroubleText = document.getElementById("loginTrouble");

hide(loadingLayer);
hide(loginTroubleText);

// Resetting user password in browser
currPassw = null;
currUser = null;



function evaluateResponse(result){

    switch(result.code){

        // Username correct
        case "200":
            switchPage("pongGame/pongPage.html");
            break;

        // Wrong username
        case "401":
            // Just continue to case 410...

        // Wrong password
        case "410":
            showError("Wrong username and/or password!");
            currUser = "";
            currPassw = "";
            break;

        default:
            showError("An unexpected error occured!");
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
        let userData = JSON.stringify({username : currUser, password: currPassw});
        apiPost("/AuthUser", evaluateResponse, userData);
    }
}

