
// The API for the URL
let API_URL = "/api";


/**
 * Sends a HTTP GET request to the game API using the API_URL
 *
 * @param resourceUrl   The URL for the resource (i.e. /AuthUser)
 *
 * @param successCallback   The function to run when there is a response from
 *                          the server
 * @param data   The data you want to send. May be left out (null is default).
 */
function apiPost( resourceUrl, successCallback, data=null){
    apiRequest("post", resourceUrl, successCallback, data)
}


/**
 * Sends a HTTP POST request to the game API using the API_URL
 *
 * @param resourceUrl   The URL for the resource (i.e. /AuthUser)
 *
 * @param successCallback   The function to run when there is a response from
 *                          the server
 * @param data   The data you want to send. May be left out (null is default).
 */
function apiGet( resourceUrl, successCallback, data=null){
    apiRequest("get",resourceUrl, successCallback, data)
}

/**
 * Sends a HTTP DELETE request to the game API using the API_URL
 * @param resourceUrl
 * @param successCallback
 * @param data
 */
function apiDelete( resourceUrl, successCallback, data=null){
    apiRequest("delete", resourceUrl, successCallback, data)
}

/**
 *Sends a HTTP PUT request to the game API using the API_URL
 * @param resourceUrl
 * @param successCallback
 * @param data
 */
function apiPut( resourceUrl, successCallback, data=null){
    apiRequest("put", resourceUrl, successCallback, data)
}


function apiRequest( type, resourceUrl, callback, data=null ){

    $.ajax({
        type: type,
        url: API_URL + resourceUrl,
        contentType: "application/json",
        data: data,
        success: function(result, text, xhr){
            callback(result, xhr.status);
        },
        error: function(xhr, ajaxOptions, thrownError) {
            callback(xhr.responseText, xhr.status);}
    });
}