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
    var deleteUserObj = $('#deleteForm').serializeJSON();

    console.log(deleteUserObj);

    apiPost("/deleteUser", responseFromAPI, JSON.stringify(deleteUserObj));

    return false;
});

function responseFromAPI(data) {

    switch (data.code) {

        case "1":
            document.getElementById('deleteFailMsg').innerHTML = "User deleted. ";
            break;

        default:
            document.getElementById('deleteFailMsg').innerHTML = "Some error. ";

    }
}

