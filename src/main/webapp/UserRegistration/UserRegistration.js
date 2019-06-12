/**
 * Class to control the registration HTML of the website
 */

function isSamePasswords() {
    var pass = document.getElementById("password").value;
    var pass2 = document.getElementById("password2").value;
    window.alert(pass + ", " + pass2);
    return (pass === pass2);
}

$('#form').on('submit', function(event){
    if(isSamePasswords()) {
        var userObj = $('#form').serializeJSON();

        $.ajax({
            type: 'POST',
            url: url + 'createUser/', //Hvad skal der stå her?
            dataType: 'json',
            data: JSON.stringify(userObj),
            contentType: 'application/json',
            success: function(data) {
                alert(data) //Skal lige gøres sådan som vi vil have det
            }
        });

        return false;
    }
});