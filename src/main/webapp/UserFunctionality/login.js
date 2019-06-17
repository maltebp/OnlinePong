

var authenticating = false;

let loadingAnimation = document.getElementById("loadingAnimation");
let loginTroubleText = document.getElementById("loginTrouble");
loadingAnimation.style.display = 'none';
loginTroubleText.style.display = 'none';

// Resetting user password in browser
currPassw = null;
currUser = null;


function authenticateUser(username, password){

    let data = JSON.stringify({username : username, password: password});
    console.log(data);

    $.ajax({
        type: "post",
        url: url + "/AuthUser",
        contentType: "application/json",
        data: data,
        success : function(result){

            switch(result.code){

                case "1":
                    console.log("its working");
                    currUser = username;
                    currPassw = password;
                    switchPage("PongPage.html");
                    break;

                case "-1":
                    document.getElementById("loginTrouble").innerHTML = "Wrong username and/or password!";
                    break;

                default:
                    loginTroubleText.innerHTML = "An unexpected error occured!";
                    loginTroubleText.style.display = "inline";
            }
            loadingAnimation.style.display = 'none';
            authenticating = false;
        },
        error: function(data){
            console.log("Error occured during login!");
            console.log(data)
        }
    })
}


function dostuff(){
    if( !authenticating ) {
        authenticating = true;
        loadingAnimation.style.display = 'inline';
        loginTroubleText.style.display = 'none';
        var username = document.forms["loginForm"]["username"].value;
        var password = document.forms["loginForm"]["password"].value;
        authenticateUser(username, password);
    }
}
