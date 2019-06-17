/**
 * @author Jacob Riis
 *
 * This class handles the deletion of registrated user form the database.
 *
 * It post, to the rest api, a JSON object with a username and a password entered in the form.
 * it uses the deleteUser function in the UserService class in the Jersey package.
 *
 *
 */
$('form').on('submit', function () {
        $.ajax({
            type: 'POST',
            url: url + '/deleteUser',           //Path
            dataType: 'json',                   //What is expected back
            data: JSON.stringify(deleteUserObj), //Converts JavaScript object into string, because a server needs the data as a string.
            contentType: 'application/json',    //Writes in the header what kind of content we are dealing with.
            success: function(data) {
                alert("Success !: "+ JSON.stringify(data));
            },
            error: function(data) {
                alert('Error in operation: ' + JSON.stringify(data));
            }
        });
        return false;
});

function userDosentExist() {
    document.getElementById('deleteFailMsg').innerHTML = "User doesn't exist.";
}

