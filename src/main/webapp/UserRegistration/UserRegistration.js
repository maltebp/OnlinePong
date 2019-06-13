/**
 * Class to control the registration HTML of the website
 */

function isSamePasswords() {
    var pass = document.getElementById("password").value;
    var pass2 = document.getElementById("password2").value;
    return (pass === pass2);
}

$('form').on('submit', function(event){
    event.preventDefault();
    if(isSamePasswords()) {
        var userObj = $('#form').serializeJSON();
        delete userObj.passwConf;
        // var string = JSON.stringify(userObj);
        // window.alert(string);

        $.ajax({
            type: 'POST',
            url: url + '/createUser',           //Path
            dataType: 'json',                   //What is expect back
            data: JSON.stringify(userObj),
            contentType: 'application/json',    //What is want to send
            success: function(data) {
                alert("Success !: "+ JSON.stringify(data)); //Skal lige gøres sådan som vi vil have det
            },
            error: function(data) {
                alert('Error in operation: ' + JSON.stringify(data));
            }
        });
        return false;
    }
});