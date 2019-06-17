
// The API for the URL
let API_URL = "http://62.79.16.17:8080/rest/service";


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
 * Sends a HTTP request to the game API using the API_URL
 *
 * @param type  The type of the request method ("post" or "get")
 *
 * @param resourceUrl   The URL for the resource (i.e. /AuthUser)
 *
 * @param successCallback   The function to run when there is a response from
 *                          the server
 * @param data   The data you want to send. May be left out (null is default).
 */
function apiRequest( type, resourceUrl, successCallback, data=null ){

    $.ajax({
        type: type,
        url: API_URL + resourceUrl,
        contentType: "application/json",
        data: data,
        success: successCallback,
        error: function(data){
            console.log(data);
        }
    })
}