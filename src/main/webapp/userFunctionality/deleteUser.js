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


var deleteUserLayer = document.getElementById("deleteUserLayer");
var deleteSuccess = document.getElementById("deleteSuccessLayer");
var deleteError = document.getElementById("deleteErrorMsg");
var deleteUserLoader = document.getElementById("deleteUserLoader");


show(deleteUserLayer);
hide(deleteSuccess);
hide(deleteError);
hide(deleteUserLoader);

function responseFromAPI(data) {

    switch (data.code) {

        case "1":
            toggleDeleteLoading(false);
            hide(deleteUserLayer);
            show(deleteSuccess);
            break;

        default:
            showDeleteError("Some error occured during deletion!");

    }
}


function toggleDeleteLoading(toggle){
    if(toggle){
        hide(deleteUserLayer);
        show(deleteUserLoader);
    }else{
        show(deleteUserLayer);
        hide(deleteUserLoader);
    }
}


function showDeleteError(msg){
    toggleDeleteLoading(false);
    deleteError.innerHTML = msg;
    show(deleteError);
}


function deleteUser(){
    var deleteUserObj = $('#deleteForm').serializeJSON();
    toggleDeleteLoading(true);
    apiPost("/deleteUser", responseFromAPI, JSON.stringify(deleteUserObj));
}


$('form').on('submit', function () {
    deleteUser();
    return false;
});

