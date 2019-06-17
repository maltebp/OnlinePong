

var authenticating = false;

let loadingAnimation = document.getElementById("loadingAnimation");
let loginTroubleText = document.getElementById("loginTrouble");
loadingAnimation.style.display = 'none';
loginTroubleText.style.display = 'none';

// Resetting user password in browser
currPassw = null;
currUser = null;




function evaluateResponse(result){

    switch(result.code){

        case "1":
            currUser = username;
            currPassw = password;
            switchPage("PongPage.html");
            break;

        case "-1":
            showError("Wrong username and/or password!");
            break;

        default:
            showError("An unexpected error occured!");
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
        var username = document.forms["loginForm"]["username"].value;
        var password = document.forms["loginForm"]["password"].value;
        let userData = JSON.stringify({username : username, password: password});
        apiPost("/AuthUser", evaluateResponse, userData);
    }
}

