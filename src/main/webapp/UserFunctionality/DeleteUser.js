/**
 * @author Jacob Riis
 *
 *
 *
 */
$('form').on('submit', function (event) {
    event.preventDefault()
    var deleteUserObj = $('#deleteForm').serializeJSON();

    $.ajax({
        type: 'POST',
        url: url + '/deleteUser',           //Path
        dataType: 'json',                   //What is expect back
        data: JSON.stringify(deleteUserObj),
        contentType: 'application/json',    //What is want to send
        success: function(data) {
            alert("Success !: "+ JSON.stringify(data)); //Write so it does what we want, gets {'code':'1'} returned atm
        },
        error: function(data) {
            alert('Error in operation: ' + JSON.stringify(data));
        }
    });
    return false;
});