

var authenticating = false;

var loadingAnimation = document.getElementById("loadingAnimation");
var loginTroubleText = document.getElementById("loginTrouble");
loadingAnimation.style.display = 'none';
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

    loadingAnimation.style.display = 'none';
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
        loadingAnimation.style.display = 'inline';
        loginTroubleText.style.display = 'none';
        currUser = document.forms["loginForm"]["username"].value;
        currPassw = document.forms["loginForm"]["password"].value;
        let userData = JSON.stringify({username : currUser, password: currPassw});
        apiPost("/AuthUser", evaluateResponse, userData);
    }
}

